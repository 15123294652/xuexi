package com.like.pmp.server.service.impl;

import com.google.common.base.Joiner;
import com.like.pmp.common.utils.CommonUtil;
import com.like.pmp.model.entity.SysUserRole;
import com.like.pmp.model.mapper.SysUserRoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.like.pmp.server.service.ISysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
