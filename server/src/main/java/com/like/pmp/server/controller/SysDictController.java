package com.like.pmp.server.controller;

/**
 * @author like
 * @date 2022年05月13日 20:51
 * 字典管理控制器
 */
import com.google.common.collect.Maps;
import com.like.pmp.common.response.BaseResponse;
import com.like.pmp.common.response.StatusCode;
import com.like.pmp.common.utils.PageUtil;
import com.like.pmp.common.utils.ValidatorUtil;
import com.like.pmp.model.entity.SysDict;
import com.like.pmp.server.service.ISysDictService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("sys/dict")
public class SysDictController {
    @Autowired
    private ISysDictService dictService;
    /**
     * 日志列表
     * @author like
     * @date 2022/5/13 20:56
     * @param params
     * @return com.like.pmp.common.response.BaseResponse
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:dict:list")
    public BaseResponse list(@RequestParam Map<String, Object> params){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            PageUtil page = dictService.queryPage(params);

            Map<String,Object> resMap= Maps.newHashMap();
            resMap.put("page", page);

            response.setData(resMap);
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }


    /**
     * 详情
     * @author like
     * @date 2022/5/13 20:56
     * @param id
     * @return com.like.pmp.common.response.BaseResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:dict:info")
    public BaseResponse info(@PathVariable Long id){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        Map<String,Object> resMap=Maps.newHashMap();
        try {
            SysDict entity = dictService.getById(id);

            resMap.put("dict", entity);
            response.setData(resMap);
        }catch (Exception e){
            response=new BaseResponse(StatusCode.UpdatePasswordFail);
        }
        return response;
    }

    /**
     * 新增
     * @author like
     * @date 2022/5/13 20:57
     * @param dict
     * @param result
     * @return com.like.pmp.common.response.BaseResponse
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:dict:save")
    public BaseResponse save(@RequestBody @Validated SysDict dict, BindingResult result){
        String res= ValidatorUtil.checkResult(result);
        if (StringUtils.isNotBlank(res)){
            return new BaseResponse(StatusCode.Fail.getCode(),res);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            dictService.save(dict);

        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    /**
     * 修改
     * @author like
     * @date 2022/5/13 20:57
     * @param dict
     * @param result
     * @return com.like.pmp.common.response.BaseResponse
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:dict:update")
    public BaseResponse update(@RequestBody @Validated SysDict dict, BindingResult result){
        String res= ValidatorUtil.checkResult(result);
        if (StringUtils.isNotBlank(res)){
            return new BaseResponse(StatusCode.Fail.getCode(),res);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            dictService.updateById(dict);

        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    /**
     * 删除
     * @author like
     * @date 2022/5/13 20:57
     * @param ids
     * @return com.like.pmp.common.response.BaseResponse
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:dict:delete")
    public BaseResponse delete(@RequestBody Long[] ids){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        Map<String,Object> resMap= Maps.newHashMap();
        try {
            dictService.removeByIds(Arrays.asList(ids));
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }
}
