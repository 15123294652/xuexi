package com.like.pmp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.like.pmp.common.utils.PageUtil;
import com.like.pmp.common.utils.QueryUtil;
import com.like.pmp.model.entity.SysRole;
import com.like.pmp.model.mapper.SysRoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.like.pmp.server.service.ISysRoleDeptService;
import com.like.pmp.server.service.ISysRoleMenuService;
import com.like.pmp.server.service.ISysRoleService;
import com.like.pmp.server.service.ISysUserRoleService;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.dc.pr.PRError;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private ISysRoleMenuService roleMenuService;

    @Autowired
    private ISysRoleDeptService roleDeptService;

    @Autowired
    private ISysUserRoleService userRoleService;
    /**
     * 分页列表模糊查询
     * @author like
     * @date 2022/5/9 20:10
     * @param paramMap
     * @return com.like.pmp.common.utils.PageUtil
     */
    @Override
    public PageUtil queryPage(Map<String, Object> paramMap) {
        String search = (paramMap.get("search") != null) ? (String) paramMap.get("search") :"";
        IPage<SysRole> queryPage = new QueryUtil<SysRole>().getQueryPage(paramMap);
        QueryWrapper wrapper = new QueryWrapper<SysRole>().like(StringUtils.isNotBlank(search),"role_name",search);
        IPage<SysRole> resPage = this.page(queryPage, wrapper);
        return new PageUtil(resPage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void savaRole(SysRole entity) {
        entity.setCreateTime(DateTime.now().toDate());
        this.save(entity);

        //插入角色菜单关联信息
        roleMenuService.menuSaveOrUpdate(entity.getRoleId(),entity.getMenuIdList());
        //插入角色部门关联信息
        roleDeptService.deptSaveOrUpdate(entity.getRoleId(),entity.getDeptIdList());
    }

    /**
     * 修改角色
     * @author like
     * @date 2022/5/9 22:25
     * @param entity
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(SysRole entity) {
        //更新角色本身
        this.updateById(entity);
        //更新角色~菜单关联信息
        roleMenuService.menuSaveOrUpdate(entity.getRoleId(),entity.getMenuIdList());
        //更新角色~部门关联信息
        roleDeptService.deptSaveOrUpdate(entity.getRoleId(),entity.getDeptIdList());
    }

    /**
     * 批量删除
     * @author like
     * @date 2022/5/9 22:36
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] ids) {
        List<Long> roleIds = Arrays.asList(ids);
        //删掉角色本身
        this.removeByIds(roleIds);
        //删除角色~部门关联数据
        roleDeptService.deleteBatch(roleIds);
        //删除角色~部门关联数据
        roleMenuService.deleteBatch(roleIds);
        //删除角色~用户关联数据
        userRoleService.deleteBatch(roleIds);
    }
}
