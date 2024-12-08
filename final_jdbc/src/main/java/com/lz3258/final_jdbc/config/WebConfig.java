package com.lz3258.final_jdbc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 配置全局的跨域策略
        registry.addMapping("/**")  // 匹配所有路径
                .allowedOrigins("http://localhost:3000")  // 允许所有来源，生产环境建议指定具体域名
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 允许的 HTTP 方法
                .allowedHeaders("*")  // 允许的请求头
                .allowCredentials(true)  // 允许发送 Cookie
                .maxAge(3600);  // 预检请求的缓存时间（秒）
    }
}
