package com.ironoc.db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class VersionController {

    private final BuildProperties buildProperties;

    public VersionController(@Autowired BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @ModelAttribute("applicationVersion")
    public String getApplicationVersion() {
        return "Version: " + buildProperties.getVersion();
    }
}
