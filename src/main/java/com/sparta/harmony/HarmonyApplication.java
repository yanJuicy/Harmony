package com.sparta.harmony;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication  (exclude = SecurityAutoConfiguration.class)
@EnableAspectJAutoProxy
@EnableJpaAuditing
public class HarmonyApplication {

    public static void main(String[] args) {
        SpringApplication.run(HarmonyApplication.class, args);
    }

}
