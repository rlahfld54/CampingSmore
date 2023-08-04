package com.green.campingsmore;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@AutoConfigureMockMvc
@Import({ MockMvcConfig.MockMvc.class })
public @interface MockMvcConfig {

    class MockMvc {
        @Bean
        MockMvcBuilderCustomizer utf8Config() {
            return builder -> builder.addFilter(
                    new CharacterEncodingFilter("UTF-8", true));
        }
    }
}
