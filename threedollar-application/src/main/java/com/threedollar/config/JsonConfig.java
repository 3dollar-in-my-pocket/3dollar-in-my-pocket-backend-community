package com.threedollar.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.threedollar.common.util.JsonUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return JsonUtils.getObjectMapper();
    }

}
