package com.youshe.mcp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.SpringApplication;
/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: zlf
 * @Date: 2021/04/05/19:18
 * @Description:
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.youshe"})
public class MCPApplication {

    public static void main(String[] args) {
        SpringApplication.run(MCPApplication.class,args);
    }
}
