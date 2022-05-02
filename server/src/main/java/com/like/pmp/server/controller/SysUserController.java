package com.like.pmp.server.controller;

import com.like.pmp.common.response.BaseResponse;
import com.like.pmp.common.response.StatusCode;
import com.like.pmp.model.entity.SysUser;
import com.like.pmp.server.service.ISysUserService;
import com.like.pmp.server.shiro.ShiroUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author like
 * @date 2022年04月27日 16:32
 * 用户管理
 */
@Controller
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController{

    @Autowired
    private ISysUserService userService;

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
}
