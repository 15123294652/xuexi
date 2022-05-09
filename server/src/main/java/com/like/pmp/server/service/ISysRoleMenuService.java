package com.like.pmp.server.service;

import com.like.pmp.model.entity.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色与菜单对应关系 服务类
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
public interface ISysRoleMenuService extends IService<SysRoleMenu> {

    void menuSaveOrUpdate(Long roleId, List<Long> menuIdList);

    void deleteBatch(List<Long> roleIds);

    List<Long> queryMenuIdList(Long roleId);
}
