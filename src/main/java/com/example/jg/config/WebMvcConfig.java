package com.example.jg.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @title WebMvcConfig
 * @Author: ZKY
 * @CreateTime: 2024-01-03  11:06
 * @Description: 静态资源映射
 */
@Slf4j
@Configuration//告诉SpringBoot这是一个配置类 == 配置文件
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Value("${JG.originalBasePath}")
    private String originalBasePath;

    @Value("${JG.analyzedBasePath}")
    private String analyzedBasePath;

    @Value("${JG.repairedBasePath}")
    private String repairedBasePath;

    @Value("${JG.kbaseBasePath}")
    private String kbaseBasePath;
    //设置静态资源映射
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始进行静态资源映射");
//        registry.addResourceHandler("/picture/**").addResourceLocations("file:D:/img/");
//        registry.addResourceHandler("/originalPicture/**").addResourceLocations("file:" + originalBasePath);
//        registry.addResourceHandler("/analyzedPicture/**").addResourceLocations("file:" + analyzedBasePath);
//        registry.addResourceHandler("/repairedPicture/**").addResourceLocations("file:" + repairedBasePath);
        registry.addResourceHandler("/originalPicture/**").addResourceLocations("file:" + originalBasePath);
        registry.addResourceHandler("/analyzedPicture/**").addResourceLocations("file:" + analyzedBasePath);
        registry.addResourceHandler("/repairedPicture/**").addResourceLocations("file:" + repairedBasePath);
        registry.addResourceHandler("/kbasePicture/**").addResourceLocations("file:" + kbaseBasePath);
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
//        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
//        registry.addResourceHandler("/swagger-ui/**").addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
        super.addResourceHandlers(registry);
    }

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器...");
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        converters.add(0,messageConverter);
    }

}
