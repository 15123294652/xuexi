package com.like.pmp.server.controller;

import com.google.common.collect.Maps;
import com.like.pmp.common.response.BaseResponse;
import com.like.pmp.common.response.StatusCode;
import com.like.pmp.common.utils.Constant;
import com.like.pmp.common.utils.PageUtil;
import com.like.pmp.common.utils.ValidatorUtil;
import com.like.pmp.model.entity.SysUser;
import com.like.pmp.server.annotation.LogAnnotation;
import com.like.pmp.server.service.ISysUserService;
import com.like.pmp.server.shiro.ShiroUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author like
 * @date 2022年04月27日 16:32
 * 用户管理
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController{

    @Autowired
    private ISysUserService userService;
    /**
     * 获取当前登录用户的详情
     * @author like
     * @date 2022/5/10 19:42
     * @return com.like.pmp.common.response.BaseResponse
     */
     @RequestMapping("/info")
     @ResponseBody
     public BaseResponse currInfo(){
        BaseResponse  response = new BaseResponse(StatusCode.Success);
        Map<String, Object> map = new HashMap<>();
      try {
         map.put("user",getUser());
        }catch (Exception e){
          return new BaseResponse(StatusCode.Fail,e.getMessage());
        }
        response.setData(map);
        return  response;
     }
    /**
     * 修改密码
     * @author like
     * @date 2022/5/10 19:43
     * @param password
     * @param newPassword
     * @return com.like.pmp.common.response.BaseResponse
     */
     @RequestMapping("/password")
     @ResponseBody
    public BaseResponse upadteUserPassword(String password,String newPassword){
         if (StringUtils.isBlank(password) || StringUtils.isBlank(newPassword)){
             return new BaseResponse(StatusCode.PasswordCanNotBlank);
         }
         BaseResponse response = new BaseResponse(StatusCode.Success);
         try {
             //先校验旧密码是否正确
             SysUser user = (SysUser) getUser();
             final String salt = user.getSalt();
             String oldPsd= ShiroUtil.sha256(password,salt);
             if(!user.getPassword().equals(oldPsd)){
                 return new BaseResponse(StatusCode.OldPasswordNotMatch);
             }
             //旧密码正确更新新密码
             String newPsd = ShiroUtil.sha256(newPassword, salt);
             boolean b = userService.updateUserPassword(user.getUserId(), oldPsd, newPsd);
             if (!b == true){
                return new BaseResponse(StatusCode.Fail,"更新密码失败");
             }
         }catch (Exception e){
             return new BaseResponse(StatusCode.Fail,e.getMessage());
         }
         response.setData("更新密码成功");
         return response;
     }
     /**
      * 分页列表
      * @author like
      * @date 2022/5/10 19:44
      * @return com.like.pmp.common.response.BaseResponse
      */
     @GetMapping("/list")
     @RequiresPermissions(value = {"sys:user:list"})
     @LogAnnotation("分页查询")
     public BaseResponse list(@RequestParam Map<String,Object> paramMap){
         BaseResponse response = new BaseResponse(StatusCode.Success);
         Map<String,Object> resMap = Maps.newHashMap();
         try {
             log.info("用户模块~列表模糊查询:{}",paramMap);
             PageUtil page = userService.queryPage(paramMap);
             resMap.put("page",page);
         }catch (Exception e){
             return new BaseResponse(StatusCode.Fail,e.getMessage());
         }
         response.setData(resMap);
         return response;
     }
     /**
      * 新增用户
      * @author like
      * @date 2022/5/10 20:44
      * @return com.like.pmp.common.response.BaseResponse
      */
     @PostMapping(value = "/save",consumes = MediaType.APPLICATION_JSON_VALUE)
     @RequiresPermissions(value = {"sys:user:save"})
     public BaseResponse save(@RequestBody SysUser entity, BindingResult result){
         String res = ValidatorUtil.checkResult(result);
         if (StringUtils.isNotBlank(res)){
             return new BaseResponse(StatusCode.InvalidParams,res);
         }
         if (StringUtils.isBlank(entity.getPassword())){
             return new BaseResponse(StatusCode.PasswordCanNotBlank);
         }
         BaseResponse response = new BaseResponse(StatusCode.Success);
         try {
            userService.saveUser(entity);
         }catch (Exception e){
             return new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
         }
         return response;
     }

    /**
     * 获取详情
     * @author like
     * @date 2022/5/10 22:00
     * @param userId
     * @return com.like.pmp.common.response.BaseResponse
     */
    @RequestMapping("/info/{userId}")
    @RequiresPermissions(value = {"sys:user:list"})
    @LogAnnotation("用户获取详情")
    public BaseResponse info(@PathVariable Long userId){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        Map<String,Object> resMap=Maps.newHashMap();
        try {
            log.info("用户模块~获取详情：{}",userId);

            resMap.put("user",userService.getInfo(userId));
            response.setData(resMap);
        }catch (Exception e){
            response=new BaseResponse(StatusCode.UpdatePasswordFail);
        }
        return response;
    }

    /**
     * 修改
     * @author like
     * @date 2022/5/10 22:00
     * @param user
     * @param result
     * @return com.like.pmp.common.response.BaseResponse
     */
    @RequestMapping("/update")
    @RequiresPermissions(value = {"sys:user:update"})
    public BaseResponse update(@RequestBody @Validated SysUser user, BindingResult result){
        String res= ValidatorUtil.checkResult(result);
        if (StringUtils.isNotBlank(res)){
            return new BaseResponse(StatusCode.InvalidParams.getCode(),res);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            log.info("用户模块~修改用户：{}",user);

            userService.updateUser(user);
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    /**
     * 删除
     * @author like
     * @date 2022/5/10 22:17
     * @param ids
     * @return com.like.pmp.common.response.BaseResponse
     */
    @RequestMapping("/delete")
    @RequiresPermissions(value = {"sys:user:delete"})
    public BaseResponse delete(@RequestBody Long[] ids){
        if (ids==null || ids.length<=0){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        //超级管理员~admin不能删除；当前登录用户不能删
        //if (Arrays.asList(ids).contains(Constant.SUPER_ADMIN)){
        if (ArrayUtils.contains(ids,Constant.SUPER_ADMIN)){
            return new BaseResponse(StatusCode.SysUserCanNotBeDelete);
        }
        if (ArrayUtils.contains(ids,getUserId())){
            return new BaseResponse(StatusCode.CurrUserCanNotBeDelete);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            userService.deleteUser(ids);

        }catch (Exception e){
            response=new BaseResponse(StatusCode.UpdatePasswordFail);
        }
        return response;
    }

    /**
     * 重置密码
     * @author like
     * @date 2022/5/10 22:17
     * @param ids
     * @return com.like.pmp.common.response.BaseResponse
     */

    @RequestMapping("/psd/reset")
    @RequiresPermissions(value = {"sys:user:resetPsd"})
    public BaseResponse restPsd(@RequestBody Long[] ids){
        if (ids==null || ids.length<=0){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        //超级管理员~admin不能删除；当前登录用户不能删
        if (ArrayUtils.contains(ids, Constant.SUPER_ADMIN) || ArrayUtils.contains(ids,getUserId())){
            return new BaseResponse(StatusCode.SysUserAndCurrUserCanNotResetPsd);
        }

        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            userService.updatePsd(ids);

        }catch (Exception e){
            response=new BaseResponse(StatusCode.UpdatePasswordFail);
        }
        return response;
    }
}
