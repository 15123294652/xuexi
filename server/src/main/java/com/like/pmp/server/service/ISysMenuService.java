package com.like.pmp.server.service;

import com.like.pmp.model.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
public interface ISysMenuService extends IService<SysMenu> {

    List<SysMenu> queryAll();

    List<SysMenu> queryNotButtonList();

    List<SysMenu> queryByParentId(Long menuId);

    void delete(Long menuId);

    List<SysMenu> getUserMenuList(Long userId);
}
