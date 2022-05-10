package com.like.pmp.server.service;

import com.like.pmp.model.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户与角色对应关系 服务类
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
public interface ISysUserRoleService extends IService<SysUserRole> {

    void deleteBatch(List<Long> roleIds);

    void roleSaveOrUpdate(Long userId,List<Long> roleIds);

    List<Long> queryRoleIdList(Long userId);
}
