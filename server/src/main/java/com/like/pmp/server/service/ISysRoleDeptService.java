package com.like.pmp.server.service;

import com.like.pmp.model.entity.SysRoleDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色与部门对应关系 服务类
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
public interface ISysRoleDeptService extends IService<SysRoleDept> {

    void deptSaveOrUpdate(Long roleId, List<Long> menuIdList);

    void deleteBatch(List<Long> roleIds);

    List<Long> queryDeptIdList(Long roleId);
}
