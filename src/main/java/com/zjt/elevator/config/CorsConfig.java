package com.zjt.elevator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.concurrent.*;

/**
 * @author zjt.
 * @version 1.0
 * @Date: 2021/4/10 18:41
 */
///跨域访问配置
@Configuration
@Order(1)
public class CorsConfig {
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);  //sessionid 多次访问一致
        corsConfiguration.addAllowedOrigin("*"); // 允许任何域名使用
        corsConfiguration.addAllowedHeader("*"); // 允许任何头
        corsConfiguration.addAllowedMethod("*"); // 允许任何方法（post、get等）
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 对接口配置跨域设置
        return new CorsFilter(source);
    }

    @Bean("localThreadPoolExecutor")
    public ThreadPoolExecutor threadPoolExecutor(){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                9,
                15,
                50000L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(30),
                new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
