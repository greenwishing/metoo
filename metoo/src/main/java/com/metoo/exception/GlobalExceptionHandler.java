package com.metoo.exception;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * User: Zhang xiaomei
 * Date: 2016/11/24
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception e) throws Exception {
        ModelMap model = new ModelMap();
        model.put("url", request.getRequestURL().toString());
        model.put("exception", e);
        return new ModelAndView("error", model);
    }

    @ExceptionHandler(value = MetooFormException.class)
    @ResponseBody
    public ErrorResult<String> jsonErrorHandler(HttpServletRequest request, MetooFormException e) throws Exception {
        return new ErrorResult<>(e.getError(), request.getRequestURL().toString(), null);
    }
}
