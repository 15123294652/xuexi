package com.like.pmp.server.exception;

import com.like.pmp.common.exception.CommonException;
import com.like.pmp.common.response.BaseResponse;
import com.like.pmp.common.response.StatusCode;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author like
 * @date 2022年05月11日 20:39
 * 通用异常处理器
 */
@RestControllerAdvice
public class CommonExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(CommonExceptionHandler.class);

    /**
     * 处理 访问 没有经过授权的 异常
     * @author like
     * @date 2022/5/11 20:59
     * @param null
     * @return null
     */
    @ExceptionHandler(AuthorizationException.class)
    public BaseResponse handleAuthorizationException(AuthorizationException e){
        log.info("访问了没有经过授权的操作或者资源：",e.fillInStackTrace());

        return new BaseResponse(StatusCode.CurrUserHasNotPermission);
    }

    /**
     * 自定义异常
     * @author like
     * @date 2022/5/11 20:59
     * @param e
     * @return com.like.pmp.common.response.BaseResponse
     */
    @ExceptionHandler(CommonException.class)
    public BaseResponse handleRRException(CommonException e){
        BaseResponse response=new BaseResponse(e.getCode(),e.getMessage());
        return response;
    }

    /**
     * 数据库出现重复数据记录的异常
     * @author like
     * @date 2022/5/11 20:59
     * @param e
     * @return com.like.pmp.common.response.BaseResponse
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public BaseResponse handleDuplicateKeyException(DuplicateKeyException e){
        BaseResponse response=new BaseResponse(StatusCode.UnknownError);

        log.error(e.getMessage(), e);
        response.setMsg("数据库中已存在该记录!");

        return response;
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse handleException(Exception e){
        BaseResponse response=new BaseResponse(StatusCode.UnknownError);
        log.error(e.getMessage(), e);
        return response;

    }
}
