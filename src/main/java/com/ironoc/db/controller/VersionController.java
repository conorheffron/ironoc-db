package com.ironoc.db.controller;

import module org.springframework;

@ControllerAdvice
public class VersionController {

    @Autowired
    private BuildProperties buildProperties;

    @ModelAttribute("applicationVersion")
    public String getApplicationVersion() {
        return "Version: " + buildProperties.getVersion();
    }
}
