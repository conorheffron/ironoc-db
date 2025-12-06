package com.ironoc.db.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.webmvc.error.ErrorController;// TODO needed for spring boot 4
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class CustomErrorController implements ErrorController {

    protected static final String PATH = "/error";

    @GetMapping(value = PATH)
    public String error(HttpServletRequest request) {
        log.error("Unexpected error occurred. {}, The HTTP status is: {}",
                request.getAttribute(RequestDispatcher.ERROR_MESSAGE),
                request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        log.error("Bad request for {}. Redirecting to home",
                request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));
        return "error404";
    }
}
