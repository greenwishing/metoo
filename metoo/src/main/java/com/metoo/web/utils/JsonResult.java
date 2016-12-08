package com.metoo.web.utils;

import com.metoo.exception.ErrorMap;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Map;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/4
 */
public class JsonResult {

    public static ModelAndView success() {
        return success(new ModelMap());
    }

    public static ModelAndView success(String key, Object value) {
        return success(new ModelMap(key, value));
    }

    public static ModelAndView success(Map<String, Object> model) {
        ModelMap success = new ModelMap("success", true);
        success.addAttribute("code", ErrorMap.OK.getCode());
        success.addAllAttributes(model);
        return new ModelAndView(new MappingJackson2JsonView(), success);
    }

    public static ModelAndView error(String message) {
        ModelMap error = new ModelMap("success", false);
        error.put("code", -1);
        error.put("message", message);
        return error(error);
    }

    public static ModelAndView error(ErrorMap error) {
        ModelMap result = new ModelMap();
        result.put("code", error.getCode());
        result.put("message", error.getMessage());
        return new ModelAndView(new MappingJackson2JsonView(), result);
    }

    public static ModelAndView error(Map<String, Object> model) {
        return new ModelAndView(new MappingJackson2JsonView(), model);
    }
}
