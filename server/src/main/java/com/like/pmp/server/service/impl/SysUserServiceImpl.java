package com.like.pmp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.like.pmp.common.utils.Constant;
import com.like.pmp.common.utils.PageUtil;
import com.like.pmp.common.utils.QueryUtil;
import com.like.pmp.model.entity.SysDept;
import com.like.pmp.model.entity.SysUser;
import com.like.pmp.model.entity.SysUserPost;
import com.like.pmp.model.entity.SysUserRole;
import com.like.pmp.model.mapper.SysUserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.like.pmp.server.service.ISysDeptService;
import com.like.pmp.server.service.ISysUserPostService;
import com.like.pmp.server.service.ISysUserRoleService;
import com.like.pmp.server.service.ISysUserService;
import com.like.pmp.server.shiro.ShiroUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private ISysUserPostService userPostService;

    @Autowired
    private ISysUserRoleService userRoleService;

    /**
     * 用户更新密码
     * @author like
     * @date 2022/4/27 17:15
     * @param userId
     * @param oldPassword
     * @param newPassword
     */
    @Override
    public boolean updateUserPassword(Long userId, String oldPassword, String newPassword) {
        SysUser user = new SysUser();
        user.setPassword(newPassword);
        int i = userMapper.update(user, new QueryWrapper<SysUser>().eq("user_id", userId).eq("password", oldPassword));
        if (i>=0){
            return true;
        }
        return false;
    }
    /**
     * 列表分页模糊查询
     * @author like
     * @date 2022/5/10 19:49
     * @param paramMap
     * @return com.like.pmp.common.utils.PageUtil
     */
    @Override
    public PageUtil queryPage(Map<String, Object> paramMap) {
       String search =  (paramMap.get("username")!=null) ? (String)paramMap.get("username"):"";
        IPage<SysUser> iPage = new QueryUtil<SysUser>().getQueryPage(paramMap);
        QueryWrapper wrapper = new QueryWrapper<SysUser>()
               .like(StringUtils.isNotBlank(search),"username",search.trim())
               .or(StringUtils.isNotBlank(search.trim()))
               .like(StringUtils.isNotBlank(search),"name",search.trim());
        IPage<SysUser> resPage =  this.page(iPage,wrapper);
        //获取部门岗位信息
        SysDept dept;
        for (SysUser user: resPage.getRecords()) {
            dept = deptService.getById(user.getDeptId());
            user.setDeptName(dept.getName());
            String postName =  userPostService.getPostNameByUserId(user.getUserId());
            user.setPostName(postName);

        }
        return new PageUtil(resPage);
    }
    /**
     * 新增用户
     * @author like
     * @date 2022/5/10 20:53
     * @param entity
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(SysUser entity) {
        if (this.getOne(new QueryWrapper<SysUser>().eq("username",entity.getUsername())) !=null){
            throw  new  RuntimeException("用户名已存在！");
        }
        entity.setCreateTime(new Date());
        //加密串
        String salt = RandomStringUtils.randomAlphanumeric(20);
        String  password = ShiroUtil.sha256(entity.getPassword(),salt);
        entity.setPassword(password);
        entity.setSalt(salt);
        this.save(entity);

        //维护用户和角色关联的关系
        userRoleService.roleSaveOrUpdate(entity.getUserId(),entity.getRoleIdList());
        //维护用户和岗位关联的关系
        userPostService.postSaveOrUpdate(entity.getUserId(),entity.getPostIdList());
    }

    /**
     * 获取用户详情包括其分配的角色岗位
     * @author like
     * @date 2022/5/10 22:01
     * @param userId
     * @return com.like.pmp.model.entity.SysUser
     */
    @Override
    public SysUser getInfo(Long userId) {
        SysUser entity = this.getById(userId);
        //获取用户分配的角色关联信息
        List<Long> roleIds = userRoleService.queryRoleIdList(userId);
        entity.setRoleIdList(roleIds);
        //获取用户分配的岗位关联信息
        List<Long> postIds = userPostService.queryRoleIdList(userId);
        entity.setPostIdList(postIds);

        return entity;
    }

    /**
     * 修改用户
     * @author like
     * @date 2022/5/10 22:16
     * @param user
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(SysUser user) {
       SysUser old =  this.getById(user.getUserId());
       if (old==null){
           return;
       }
       if (!old.getUsername().equals(user.getUsername())){
           if (this.getOne(new QueryWrapper<SysUser>().eq("username",user.getUsername())) !=null){
               throw  new  RuntimeException("修改后的用户名已存在！");
           }
       }
       if (StringUtils.isNotBlank(user.getPassword())){
           String  password = ShiroUtil.sha256(user.getPassword(),old.getSalt());
           user.setPassword(password);
       }
        this.save(user);

        //维护用户和角色关联的关系
        userRoleService.roleSaveOrUpdate(user.getUserId(),user.getRoleIdList());
        //维护用户和岗位关联的关系
        userPostService.postSaveOrUpdate(user.getUserId(),user.getPostIdList());
    }

    /**
     * 删除用户 除了删除 用户本身 信息之外，还需要删除用户~角色、用户~岗位 关联关系信息
     * @author like
     * @date 2022/5/10 22:29
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long[] ids) {
        if (ids!=null && ids.length>0){
            List<Long> userIds= Arrays.asList(ids);

            this.removeByIds(userIds);

            /*for (Long uId:userIds){
                userRoleService.remove(new QueryWrapper<SysUserRole>().eq("user_id",uId));
                userPostService.remove(new QueryWrapper<SysUserPost>().eq("user_id",uId));
            }*/

            //java8的写法
            userIds.stream().forEach(uId -> userRoleService.remove(new QueryWrapper<SysUserRole>().eq("user_id",uId)));
            userIds.stream().forEach(uId -> userPostService.remove(new QueryWrapper<SysUserPost>().eq("user_id",uId)));
        }
    }

    /**
     * 重置密码
     * @author like
     * @date 2022/5/10 22:39
     * @param ids
     */
    @Override
    public void updatePsd(Long[] ids) {
        if (ids!=null && ids.length>0){
            SysUser entity;
            for (Long uId:ids){
                entity=new SysUser();
                String salt=RandomStringUtils.randomAlphanumeric(20);
                String newPsd=ShiroUtil.sha256(Constant.DefaultPassword,salt);
                entity.setPassword(newPsd);
                entity.setSalt(salt);
                this.update(entity,new QueryWrapper<SysUser>().eq("user_id",uId));
            }
        }
    }
}
