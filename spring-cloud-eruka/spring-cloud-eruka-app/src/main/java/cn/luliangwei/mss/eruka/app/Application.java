package cn.luliangwei.mss.eruka.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 
 * spring cloud eruka server.
 * </p>
 *
 * @author luliangwei
 * @since 0.0.1
 */
@SpringBootApplication
@EnableEurekaServer
public class Application 
{
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}
