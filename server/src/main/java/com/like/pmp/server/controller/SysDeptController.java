package com.like.pmp.server.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.like.pmp.common.response.BaseResponse;
import com.like.pmp.common.response.StatusCode;
import com.like.pmp.common.utils.Constant;
import com.like.pmp.common.utils.ValidatorUtil;
import com.like.pmp.model.entity.SysDept;
import com.like.pmp.server.service.ISysDeptService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author like
 * @date 2022年05月05日 20:08
 * 部门控制层
 */
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController extends AbstractController {
    @Autowired
    private ISysDeptService deptService;

    /**
     * 获取一级/顶级部门的DeptID
     * @author like
     * @date 2022/5/5 20:49
     * @return com.like.pmp.common.response.BaseResponse
     */
    @GetMapping("/info")
    public BaseResponse info(){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        HashMap<String, Object> resMap = Maps.newHashMap();
        Long deptId = 0L;
        try {
            if (!getUserId().equals(Constant.SUPER_ADMIN)){

            }
        }catch (Exception e){
            return new BaseResponse(StatusCode.Fail,e.getMessage());
        }
        resMap.put("deptId",deptId);
        response.setData(resMap);
        return response;
    }
    /**
     * 获取部门列表
     * @author like
     * @date 2022/5/5 20:26
     * @return java.util.List<com.like.pmp.model.entity.SysDept>
     */
    @GetMapping("/list")
    public List<SysDept> list(){
        List<SysDept> sysDepts = deptService.queryAll(Maps.newHashMap());
        return sysDepts;
    }
    /**
     * 获取部门树
     * @author like
     * @date 2022/5/5 21:10
     * @return com.like.pmp.common.response.BaseResponse
     */
    @GetMapping("/select")
    public BaseResponse select(){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        HashMap<String, Object> repMap = Maps.newHashMap();
        List<SysDept> deptList;
        try{
            deptList = deptService.queryAll(Maps.newHashMap());
        }catch (Exception e){
            return new BaseResponse(StatusCode.Fail,e.getMessage());
        }
        repMap.put("deptList",deptList);
        response.setData(repMap);
        return response;
    }
    /**
     * 新增部门
     * @author like
     * @date 2022/5/5 21:28
     * @param sysDept
     * @param result
     * @return com.like.pmp.common.response.BaseResponse
     */
    @PostMapping(value = "/save",consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse add(@RequestBody @Validated SysDept sysDept, BindingResult result){
        String res = ValidatorUtil.checkResult(result);
        if (StringUtils.isNotBlank(res)){
            return new BaseResponse(StatusCode.Fail.getCode(),res);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try{
            log.info("新增部门~接受到的数据:{}",sysDept);
            deptService.save(sysDept);
        }catch (Exception e){
            return new BaseResponse(StatusCode.Fail,e.getMessage());
        }
        return response;
    }
    /**
     * 获取部门详情
     * @author like
     * @date 2022/5/5 21:40
     * @param deptId
     * @return com.like.pmp.common.response.BaseResponse
     */
    @GetMapping("/detail/{deptId}")
    public BaseResponse detail(@PathVariable Long deptId){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        HashMap<String, Object> resMap = Maps.newHashMap();
        try {
            resMap.put("dept",deptService.getById(deptId));
        }catch (Exception e){
            return new BaseResponse(StatusCode.Fail,e.getMessage());
        }
        response.setData(resMap);
        return response;

    }
    /**
     * 修改部门
     * @author like
     * @date 2022/5/5 21:40
     * @return com.like.pmp.common.response.BaseResponse
     */
    @PostMapping(value = "/update",consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse update(@RequestBody @Validated SysDept sysDept, BindingResult result){
        String res = ValidatorUtil.checkResult(result);
        if (StringUtils.isNotBlank(res)){
            return new BaseResponse(StatusCode.Fail.getCode(),res);
        }
        if (sysDept.getDeptId() == null || sysDept.getDeptId()<=0){
            return new BaseResponse(StatusCode.InvalidCode);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            log.info("修改部门~接受到的数据:{}",sysDept);
            deptService.updateById(sysDept);
        }catch (Exception e){
            return new BaseResponse(StatusCode.Fail,e.getMessage());
        }
        return response;
    }

    @PostMapping(value = "/delete")
    public BaseResponse delete(Long deptId){
        if (deptId == null ||deptId<=0){
            return new BaseResponse(StatusCode.InvalidCode);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            log.info("删除部门~接受到的数据:{}",deptId);
            //如果当前部门有子部门需要先删除所有子部门
            List<Long> subIds = deptService.queryDeptIds(deptId);
            if (subIds.size() > 0){
                return new BaseResponse(StatusCode.DeptHasSubDeptCanNotBeDelete);
            }
            deptService.removeById(deptId);
        }catch (Exception e){
            return new BaseResponse(StatusCode.Fail,e.getMessage());
        }
        return response;
    }
}
