package com.dream.city;

import com.dream.city.base.config.RedisConfig;
import com.dream.city.base.config.SwaggerConfig;
import com.dream.city.shiro.ShiroConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@MapperScan("com.dream.city.base.model.mapper")
@Import({RedisConfig.class, SwaggerConfig.class, ShiroConfig.class})
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
}
