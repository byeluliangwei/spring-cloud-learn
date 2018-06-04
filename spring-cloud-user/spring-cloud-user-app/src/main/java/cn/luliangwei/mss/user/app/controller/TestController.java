package cn.luliangwei.mss.user.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String getName(@RequestParam("name") String name) {
        return "------------Boot ---------" + name;
    } 
}
