package com.lylynx.hideout.admin.mvc;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminConsoleController {

    @RequestMapping(value = "/.console", method = RequestMethod.GET)
    public String showConsole() {
        return "admin/console";
    }

}
