package com.like.pmp.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author like
 * @date 2022年04月26日 16:10
 */
@Controller
public class SysPageController {

    @RequestMapping("modules/{module}/{page}.html")
    public String page(@PathVariable String module, @PathVariable String page){
        return "modules/"+module+"/"+page;
    }


    @RequestMapping("/login.html")
    public String login(){
         return "login";
     }

    @RequestMapping(value = {"index.html","/"})
    public String index(){
        return "index";
    }

    @RequestMapping(value = {"main.html"})
    public String main(){
        return "main";
    }

    @RequestMapping(value = {"404.html"})
    public String notFound(){
        return "404";
    }
}
