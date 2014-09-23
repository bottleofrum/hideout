package com.lylynx.hideout.admin.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/.console/.partials", method = RequestMethod.GET)
public class AdminPartialsController {

    @RequestMapping(value = "/**", method = RequestMethod.GET)
    public String getPartial(HttpServletRequest request) {

        final String pathInfo = request.getRequestURI().substring(request.getContextPath().length());
        String partial = pathInfo.replace("/.console/.partials/", "");

        return "admin/partials/" + partial;
    }

}
