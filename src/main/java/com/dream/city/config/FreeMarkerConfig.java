package com.dream.city.config;

import com.dream.city.shiro.freemarker.ShiroTags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Locale;

@Configuration
public class FreeMarkerConfig {

    @Autowired
    private freemarker.template.Configuration configuration;

    @PostConstruct
    public void setSharedVariable() {
    	try {
			configuration.setSharedVariable("shiro", new ShiroTags());
            configuration.setEncoding(Locale.getDefault(),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
