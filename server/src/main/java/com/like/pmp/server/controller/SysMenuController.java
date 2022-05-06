package com.like.pmp.server.controller;

import com.google.common.collect.Maps;
import com.like.pmp.common.response.BaseResponse;
import com.like.pmp.common.response.StatusCode;
import com.like.pmp.common.utils.Constant;
import com.like.pmp.model.entity.SysMenu;
import com.like.pmp.server.service.ISysMenuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author like
 * @date 2022年05月06日 20:16
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController{
    @Autowired
    private ISysMenuService menuService;
    /**
     * 菜单列表
     * @author like
     * @date 2022/5/6 20:55
     * @return java.util.List<com.like.pmp.model.entity.SysMenu>
     */
    @GetMapping("/list")
    public List<SysMenu> list(){
        return menuService.queryAll();
    }
    /**
     * 获取树形的层级列表
     * @author like
     * @date 2022/5/6 21:02
     * @return com.like.pmp.common.response.BaseResponse
     */
    @GetMapping("/select")
    public BaseResponse select(){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        HashMap<String, Object> resMap = Maps.newHashMap();
        try {
            List<SysMenu> menuList = menuService.queryNotButtonList();
            SysMenu root = new SysMenu();
            root.setMenuId(Constant.TOP_MENU_ID);
            root.setName(Constant.TOP_MENU_NAME);
            root.setParentId(-1L);
            menuList.add(root);
            resMap.put("menuList", menuList);
        }catch (Exception e){
            return new BaseResponse(StatusCode.Fail,e.getMessage());
        }
        response.setData(resMap);
        return response;
    }
    /**
     * 新增菜单
     * @author like
     * @date 2022/5/6 21:15
     * @return com.like.pmp.common.response.BaseResponse
     */
    @PostMapping(value = "/save",consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse save(@RequestBody SysMenu entity){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            log.info("新增菜单~接收到的数据:{}",entity);
            String result = validateForm(entity);
            if (StringUtils.isNotBlank(result)){
                return new BaseResponse(StatusCode.Fail.getCode(),result);
            }
            menuService.save(entity);
        }catch (Exception e){
            return new BaseResponse(StatusCode.Fail,e.getMessage());
        }
        return response;
    }
    /**
     * 获取菜单详情
     * @author like
     * @date 2022/5/6 21:46
     * @param menuId
     * @return com.like.pmp.common.response.BaseResponse
     */
    @GetMapping("/info/{menuId}")
    public BaseResponse info(@PathVariable Long menuId){
        if (menuId == null || menuId <= 0){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        HashMap<String, Object> resMap = Maps.newHashMap();
        try {
            resMap.put("menu",menuService.getById(menuId));
        }catch (Exception e){
            return new BaseResponse(StatusCode.Fail);
        }
        response.setData(resMap);
        return response;

    }
    @PostMapping(value = "/update",consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse update(@RequestBody SysMenu entity){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            log.info("修改菜单~接受到的参数:{}",entity);
            String result = validateForm(entity);
            if (StringUtils.isNotBlank(result)){
                return new BaseResponse(StatusCode.Fail.getCode(),result);
            }
            menuService.updateById(entity);
        }catch (Exception e){
            return new BaseResponse(StatusCode.Fail,e.getMessage());
        }
        return response;
    }

    @PostMapping(value = "/delete")
    public BaseResponse delete(Long menuId){
        if (menuId == null || menuId <= 0){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            log.info("删除菜单~接收到的数据:{}",menuId);
            //如果删除菜单中有子菜单必须先删除子菜单
            SysMenu entity = menuService.getById(menuId);
            if (entity == null){
                return new BaseResponse(StatusCode.InvalidParams);
            }
            List<SysMenu> menuList = menuService.queryByParentId(entity.getMenuId());
            if (menuList.size() > 0 ){
                return new BaseResponse(StatusCode.MenuHasSubMenuListCanNotDelete);
            }
            menuService.delete(menuId);
        }catch (Exception e){
            return new BaseResponse(StatusCode.Fail,e.getMessage());
        }
        return response;
    }

    /**
     * 验证参数是否正确
     * @author like
     * @date 2022/5/6 21:22
     * @param menu
     * @return java.lang.String
     */
    private String validateForm(SysMenu menu) {
        if (StringUtils.isBlank(menu.getName())) {
            return "菜单名称不能为空";
        }
        if (menu.getParentId() == null) {
            return "上级菜单不能为空";
        }

        //菜单
        if (menu.getType() == Constant.MenuType.MENU.getValue()) {
            if (StringUtils.isBlank(menu.getUrl())) {
                return "菜单链接url不能为空";
            }
        }

        //上级菜单类型
        int parentType = Constant.MenuType.CATALOG.getValue();

        if (menu.getParentId() != 0) {
            SysMenu parentMenu = menuService.getById(menu.getParentId());
            parentType = parentMenu.getType();
        }

        //目录、菜单
        if (menu.getType() == Constant.MenuType.CATALOG.getValue() || menu.getType() == Constant.MenuType.MENU.getValue()) {
            if (parentType != Constant.MenuType.CATALOG.getValue()) {
                return "上级菜单只能为目录类型";
            }
            return "";
        }

        //按钮
        if (menu.getType() == Constant.MenuType.BUTTON.getValue()) {
            if (parentType != Constant.MenuType.MENU.getValue()) {
                return "上级菜单只能为菜单类型";
            }
            return "";
        }

        return "";
    }
}
