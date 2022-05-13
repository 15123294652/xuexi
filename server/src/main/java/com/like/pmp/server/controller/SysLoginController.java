package com.like.pmp.server.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.like.pmp.common.response.BaseResponse;
import com.like.pmp.common.response.StatusCode;
import com.like.pmp.server.annotation.LogAnnotation;
import com.like.pmp.server.shiro.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * @author like
 * @date 2022年04月26日 17:19
 */
@Controller
@RequestMapping("/sys")
public class SysLoginController extends AbstractController{

    @Autowired
    private Producer producer;

    /**
     * 生成验证码
     * @author like
     * @date 2022/4/26 22:10
     */
    @RequestMapping("/captcha.jpg")
    public void captcha(HttpServletResponse response) throws Exception {
        System.out.println("11111");
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        ShiroUtil.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);

        System.out.println("验证码："+text);
    }

    /**
     * 用户登录
     * @author like
     * @date 2022/4/26 17:23
     * @return com.like.pmp.common.response.BaseResponse
     */
    @ResponseBody
    @RequestMapping("/login")
    public BaseResponse login(String username,String password,String captcha){
        log.info("用户名：{},密码：{},验证码：{}",username,password,captcha);
        //校验验证码
        String kaptcha=ShiroUtil.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
        if (!kaptcha.equals(captcha)){
            return new BaseResponse(StatusCode.InvalidCode);
        }
        try {
            Subject subject = SecurityUtils.getSubject();
            if (!subject.isAuthenticated()){
                UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                subject.login(token);
            }

        }catch (Exception e){
            return new BaseResponse(StatusCode.Fail,e.getMessage());
        }
        return new BaseResponse(StatusCode.Success);
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "/logout")
    public String logout(){
        //销毁当前的shiro的用户session
        ShiroUtil.logout();
        return "redirect:login.html";
    }

}
