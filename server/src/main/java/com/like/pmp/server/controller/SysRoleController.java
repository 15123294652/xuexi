package com.like.pmp.server.controller;

import com.google.common.collect.Maps;
import com.like.pmp.common.response.BaseResponse;
import com.like.pmp.common.response.StatusCode;
import com.like.pmp.common.utils.PageUtil;
import com.like.pmp.common.utils.ValidatorUtil;
import com.like.pmp.model.entity.SysRole;
import com.like.pmp.server.service.ISysRoleDeptService;
import com.like.pmp.server.service.ISysRoleMenuService;
import com.like.pmp.server.service.ISysRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author like
 * @date 2022年05月09日 20:05
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController{
    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysRoleMenuService roleMenuService;

    @Autowired
    private ISysRoleDeptService roleDeptService;

    /**
     * 分页模糊查询
     * @author like
     * @date 2022/5/9 20:08
     * @param paramMap
     * @return com.like.pmp.common.response.BaseResponse
     */
    @GetMapping("/list")
    public BaseResponse list(@RequestParam  Map<String,Object> paramMap){

        BaseResponse response = new BaseResponse(StatusCode.Success);

        try {
            Map<String,Object> resMap =  Maps.newHashMap();
            PageUtil page = roleService.queryPage(paramMap);
            resMap.put("page",page);
            response.setData(resMap);
        }catch (Exception e){
            return new BaseResponse(StatusCode.Fail,e.getMessage());
        }
        return response;
    }
    /**
     * 新增角色
     * @author like
     * @date 2022/5/9 21:04
     * @param entity
     * @param result
     * @return com.like.pmp.common.response.BaseResponse
     */
    @PostMapping(value = "/save",consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse save(@RequestBody @Validated SysRole entity, BindingResult result){
        String res = ValidatorUtil.checkResult(result);
        if(StringUtils.isNotBlank(res)){
            return new BaseResponse(StatusCode.InvalidParams.getCode(),res);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
        log.info("新增角色~接收到的数据:{}",entity);
            roleService.savaRole(entity);
        }catch (Exception e){
            return new BaseResponse(StatusCode.Fail,e.getMessage());
        }
        return response;
    }
    /**
     * 获取角色详情
     * @author like
     * @date 2022/5/9 22:21
     * @param id
     * @return com.like.pmp.common.response.BaseResponse
     */
    @GetMapping("/info/{id}")
    public BaseResponse info(@PathVariable Long id){
        if (id == null || id == 0){
            return new BaseResponse(StatusCode.InvalidCode);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        HashMap<String, Object>  resMap= Maps.newHashMap();
        try {
            SysRole entity = roleService.getById(id);
            //角色对应的菜单列表
            List<Long> resMenuList = roleMenuService.queryMenuIdList(entity.getRoleId());
            entity.setMenuIdList(resMenuList);
            //角色对应的部门列表
            List<Long> resDeptList  = roleDeptService.queryDeptIdList(entity.getRoleId());
            entity.setDeptIdList(resDeptList);
            resMap.put("role",entity);
        }catch (Exception e){
            return new BaseResponse(StatusCode.Fail,e.getMessage());
        }
        response.setData(resMap);
        return response;
    }
    /**
     * 修改角色
     * @author like
     * @date 2022/5/9 22:49
     * @param entity
     * @param result
     * @return com.like.pmp.common.response.BaseResponse
     */
    @PostMapping(value = "/update",consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse update(@RequestBody @Validated SysRole entity, BindingResult result){
        String res = ValidatorUtil.checkResult(result);
        if(StringUtils.isNotBlank(res)){
            return new BaseResponse(StatusCode.InvalidParams.getCode(),res);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            log.info("修改角色~接收到的数据:{}",entity);
            roleService.updateRole(entity);
        }catch (Exception e){
            return new BaseResponse(StatusCode.Fail,e.getMessage());
        }
        return response;
    }
    /**
     * 删除角色
     * @author like
     * @date 2022/5/9 22:49
     * @param ids
     * @return com.like.pmp.common.response.BaseResponse
     */
    @PostMapping("/delete")
    public BaseResponse delete(@RequestBody Long[] ids){

        if(ids == null || ids.length <= 0 ){
            return new BaseResponse(StatusCode.InvalidParams);
        }

        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            log.info("删除角色~接收到的数据:{}",ids);
            roleService.deleteBatch(ids);
        }catch (Exception e){
            return new BaseResponse(StatusCode.Fail,e.getMessage());
        }
        return response;
    }
    /**
     * 角色列表
     * @author like
     * @date 2022/5/10 20:36
     * @return com.like.pmp.common.response.BaseResponse
     */
    @GetMapping("/select")
    public BaseResponse select(){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        HashMap<String, Object> resMap = Maps.newHashMap();
        try {
            log.info("角色列表~select");
            resMap.put("list", roleService.list());
        }catch (Exception e){
            return new BaseResponse(StatusCode.Fail,e.getMessage());
        }
        response.setData(resMap);
        return response;
    }
}
