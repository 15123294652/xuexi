package com.like.pmp.server.controller;

import com.like.pmp.common.response.BaseResponse;
import com.like.pmp.common.response.StatusCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author like
 * @date 2022年04月26日 14:11
 */
@Controller
@RequestMapping("/base")
public class BaseController {
    private static final Logger log = LoggerFactory.getLogger(BaseController.class);

    @RequestMapping(value = "/info")
    @ResponseBody
    public BaseResponse info(@RequestParam("name") String name){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        if (StringUtils.isNotBlank(name)){
            name="测试用列";
        }
         response.setData(name);
        System.out.println("aa");
        return response;
    }

}
