package com.cms.user.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("api" , "sample");
    }
}