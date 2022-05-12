package com.like.pmp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.like.pmp.common.utils.Constant;
import com.like.pmp.model.entity.SysMenu;
import com.like.pmp.model.entity.SysRoleMenu;
import com.like.pmp.model.entity.SysUser;
import com.like.pmp.model.mapper.SysMenuMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.like.pmp.model.mapper.SysRoleMenuMapper;
import com.like.pmp.model.mapper.SysUserMapper;
import com.like.pmp.server.service.ISysMenuService;
import com.like.pmp.server.service.ISysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 菜单管理 服务实现类
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {
    @Autowired
    private SysMenuMapper menuMapper;
    @Autowired
    private ISysRoleMenuService roleMenuService;
    @Autowired
    private SysUserMapper userMapper;
    @Override
    public List<SysMenu> queryAll() {
       return menuMapper.queryAll();
    }

    @Override
    public List<SysMenu> queryNotButtonList() {
        return menuMapper.queryNotButtonList();
    }

    @Override
    public List<SysMenu> queryByParentId(Long menuId) {
        return menuMapper.queryByButtonList(menuId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long menuId) {
        this.removeById(menuId);
       // roleMenuService.removeById(new QueryWrapper<SysRoleMenu>().eq("menu_id",menuId));
    }

    /**
     * 获取首页菜单栏
     * @author like
     * @date 2022/5/12 22:04
     * @param userId
     * @return java.util.List<com.like.pmp.model.entity.SysMenu>
     */
    @Override
    public List<SysMenu> getUserMenuList(Long userId) {
        List<SysMenu> list = Lists.newLinkedList();
        if (userId.equals(Constant.SUPER_ADMIN)){
            list = getAllMenuList(null);
        }else{
            //非超级管理员~根据分配的角色~菜单关联信息
            List<Long> menuIdList = userMapper.queryAllMenuId(userId);
            list = getAllMenuList(menuIdList);
        }
        return list;
    }
    /**
     * 获取所有菜单列表
     * @author like
     * @date 2022/5/12 22:07
     * @return java.util.List<com.like.pmp.model.entity.SysMenu>
     */
    private List<SysMenu> getAllMenuList(List<Long> menuIdList){
        List<SysMenu> menuList = queryListByParentId(0L,menuIdList);
        //递归获取一级菜单下的所有子菜单
        getMenuTrees(menuList,menuIdList);
        return menuList;

    }
    /**
     *  根据父id查询子菜单列表~找出一级菜单
     * @author like
     * @date 2022/5/12 22:16
     * @param parentId
     * @param menuIdList 正对于非超级管理员
     * @return java.util.List<com.like.pmp.model.entity.SysMenu>
     */
    private List<SysMenu> queryListByParentId(Long parentId,List<Long> menuIdList){
        List<SysMenu> menuList= menuMapper.queryListParentId(parentId);
        if(menuIdList == null || menuIdList.isEmpty()){
            return menuList;
        }
        List<SysMenu> userMenuList = Lists.newLinkedList();
        for (SysMenu entity: menuList){
            if (menuIdList.contains(entity.getMenuId())){
                userMenuList.add(entity);
            }
        }
        return userMenuList;
    }
    /**
     *
     * @author like
     * @date 2022/5/12 22:33
     * @return java.util.List<com.like.pmp.model.entity.SysMenu>
     */
    private List<SysMenu> getMenuTrees(List<SysMenu> menuList,List<Long> menuIdList){
        List<SysMenu> subMenuList = Lists.newLinkedList();
        List<SysMenu> tempList;
        for (SysMenu entity:menuList){
            //当前菜单的子菜单为空递归终止
            if (entity.getType() == Constant.MenuType.CATALOG.getValue()){
                tempList=queryListByParentId(entity.getMenuId(),menuIdList);
                entity.setList(getMenuTrees(tempList,menuIdList));
            }

            subMenuList.add(entity);
        }
        return subMenuList;
    }
}
