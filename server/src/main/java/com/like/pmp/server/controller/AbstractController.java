package com.like.pmp.server.controller;

import com.like.pmp.model.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

/**
 * @author like
 * @date 2022年04月26日 17:20
 */
@Controller
public abstract class AbstractController {
    //日志
    protected Logger log = LoggerFactory.getLogger(getClass());

    //获取当前登录用户的详情
    public Object getUser(){
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        return user;
    }

}
