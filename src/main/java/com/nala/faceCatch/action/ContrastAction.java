package com.nala.faceCatch.action;

import com.nala.faceCatch.service.face.Contrast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * create by lizenn
 * create date 2018/7/21
 * description
 */


@Controller
@RequestMapping(value = "/contrast")

public class ContrastAction {

    @Autowired
    private Contrast contrast;

    @ResponseBody
    @RequestMapping(value = "/gain/analysis/result")
    public Map<String, Object> getContrastResult() {

        Map<String, Object> map = contrast.match();

        return map;
    }

}
