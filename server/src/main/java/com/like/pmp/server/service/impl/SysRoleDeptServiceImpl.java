package com.like.pmp.server.service.impl;

import com.google.common.base.Joiner;
import com.like.pmp.common.utils.CommonUtil;
import com.like.pmp.model.entity.SysDept;
import com.like.pmp.model.entity.SysRoleDept;
import com.like.pmp.model.entity.SysRoleMenu;
import com.like.pmp.model.mapper.SysRoleDeptMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.like.pmp.server.service.ISysRoleDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 角色与部门对应关系 服务实现类
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
@Service
public class SysRoleDeptServiceImpl extends ServiceImpl<SysRoleDeptMapper, SysRoleDept> implements ISysRoleDeptService {

    @Autowired
    private SysRoleDeptMapper roleDeptMapper;
    /**
     * 维护角色部门关联信息
     * @author like
     * @date 2022/5/9 21:16
     * @param roleId
     * @param menuIdList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deptSaveOrUpdate(Long roleId, List<Long> deptIdList) {
        //需要先清除旧的关联数据，在插入新的关联信息（批量删除）
        deleteBatch(Arrays.asList(roleId));

        SysRoleDept roleDept;
        if (deptIdList != null || !deptIdList.isEmpty()){
            for (Long deptId:deptIdList) {
                roleDept = new SysRoleDept();
                roleDept.setRoleId(roleId);
                roleDept.setDeptId(deptId);
                this.save(roleDept);
            }
        }
    }

    /**
     * 根据角色id批量删除
     * @author like
     * @date 2022/5/9 21:22
     * @param roleIds
     */
    @Override
    public void deleteBatch(List<Long> roleIds) {
        if (roleIds != null && !roleIds.isEmpty()){
            String delIds = Joiner.on(",").join(roleIds);
            roleDeptMapper.deleteBatch(CommonUtil.concatStrToInt(delIds,","));
        }
    }

    @Override
    public List<Long> queryDeptIdList(Long roleId) {
        return roleDeptMapper.queryDeptIdList(roleId);
    }
}
