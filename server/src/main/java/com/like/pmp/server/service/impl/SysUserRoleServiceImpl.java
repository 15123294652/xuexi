package com.like.pmp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Joiner;
import com.like.pmp.common.utils.CommonUtil;
import com.like.pmp.model.entity.SysUserRole;
import com.like.pmp.model.mapper.SysUserRoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.like.pmp.server.service.ISysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 用户与角色对应关系 服务实现类
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {
    @Autowired
    private SysUserRoleMapper userRoleMapper;
    /**
     *
     * @author like
     * @date 2022/5/9 22:42
     * @param roleIds
     */
    @Override
    public void deleteBatch(List<Long> roleIds) {
        if (roleIds != null && !roleIds.isEmpty()){
            String delIds = Joiner.on(",").join(roleIds);
            userRoleMapper.deleteBatch(CommonUtil.concatStrToInt(delIds,","));
        }
    }

    /**
     * 维护用户角色关系之间的关联关系
     * @author like
     * @date 2022/5/10 21:02
     * @param userId
     * @param roleIds
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void roleSaveOrUpdate(Long userId, List<Long> roleIds) {
        //清除之前用户与角色关系
        this.remove(new QueryWrapper<SysUserRole>().eq("user_id",userId));
        if (roleIds.size() > 0 ){
            SysUserRole sysUserRole;
            for (Long roleId:roleIds){
                sysUserRole = new SysUserRole();
                sysUserRole.setRoleId(roleId);
                sysUserRole.setUserId(userId);
                this.save(sysUserRole);
            }
        }
    }

    /**
     * 获取用户角色关联信息
     * @author like
     * @date 2022/5/10 22:08
     * @param userId
     * @return java.util.List<java.lang.Long>
     */
    @Override
    public List<Long> queryRoleIdList(Long userId) {
        return userRoleMapper.queryRoleIdList(userId);
    }

}
