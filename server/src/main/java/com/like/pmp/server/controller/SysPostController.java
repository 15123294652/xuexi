package com.like.pmp.server.controller;

import com.google.common.collect.Maps;
import com.like.pmp.common.response.BaseResponse;
import com.like.pmp.common.response.StatusCode;
import com.like.pmp.common.utils.PageUtil;
import com.like.pmp.common.utils.ValidatorUtil;
import com.like.pmp.model.entity.SysPost;
import com.like.pmp.server.service.ISysPostService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author like
 * @date 2022年04月27日 21:25
 * 岗位管理
 */
@RestController
@RequestMapping("/sys/post")
public class SysPostController extends AbstractController {
    @Autowired
    private ISysPostService postService;
    /*
     * 分页列表模糊查询
     * @author like
     * @date 2022/4/28 20:35
     * @param paramMap
     * @return com.like.pmp.common.response.BaseResponse
     */
    @RequestMapping("/list")
    public BaseResponse list(@RequestParam Map<String, Object> paramMap){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        Map<String, Object> resMap = new HashMap<>();
        try{
            PageUtil pageUtil = postService.queryPage(paramMap);
            resMap.put("page",pageUtil);
        }catch (Exception e){
            return new BaseResponse(StatusCode.Fail,e.getMessage());
        }
        response.setData(resMap);
        return response;
    }
    /*
     * 新增岗位模块
     * @author like
     * @date 2022/4/28 20:35
     * @param post
     * @param result
     * @return com.like.pmp.common.response.BaseResponse
     */
    @PostMapping(value = "/save",consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse save(@RequestBody @Validated SysPost post, BindingResult result){
        log.info("新增岗位～接受数据:{}",post);
        String res = ValidatorUtil.checkResult(result);
        if (StringUtils.isNotBlank(res)){
            return new BaseResponse(StatusCode.InvalidParams.getCode(),res);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);

        try {
          postService.savePost(post);
        }catch (Exception e){
            return  new BaseResponse(StatusCode.Fail,e.getMessage());
        }

        return response;
    }

    @GetMapping("/info/{id}")
    public BaseResponse postInfo(@PathVariable Long id){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        Map<String, Object> map = new HashMap<>();
        try {
            log.info("岗位详情～拉取到的数据：{}",id);
            map.put("post",postService.getById(id));
        }catch (Exception e){
            return new BaseResponse(StatusCode.Fail,e.getMessage());
        }
        response.setData(map);
        return response;
    }

    @PostMapping(value = "/update",consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse update(@RequestBody @Validated SysPost post, BindingResult result){

        String res = ValidatorUtil.checkResult(result);
        if (StringUtils.isNotBlank(res)){
            return new BaseResponse(StatusCode.InvalidParams.getCode(),res);
        }
        if (post.getPostId() == null || post.getPostId()<=0){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);

        try {
            log.info("修改岗位～接受数据:{}",post);
            postService.updatePost(post);
        }catch (Exception e){
            return  new BaseResponse(StatusCode.Fail,e.getMessage());
        }

        return response;
    }

    /**
     * 批量删除岗位信息
     * @author like
     * @date  21:03
 * @param ids
 * @return com.like.pmp.common.response.BaseResponse
     */
    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse delete(@RequestBody Long[] ids){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try{
        log.info("删除岗位～接收到数据:{}", Arrays.asList(ids));
        postService.deletepath(ids);
        }catch (Exception e){
            return new BaseResponse(StatusCode.Fail,e.getMessage());
        }

        return response;
    }

    /**
     * 岗位列表
     * @author like
     * @date 2022/5/10 20:41
     * @return com.like.pmp.common.response.BaseResponse
     */
    @GetMapping("/select")
    public BaseResponse select(){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        HashMap<String, Object> resMap = Maps.newHashMap();
        try {
            log.info("角色列表~select");
            resMap.put("list", postService.list());
        }catch (Exception e){
            return new BaseResponse(StatusCode.Fail,e.getMessage());
        }
        response.setData(resMap);
        return response;
    }

}
