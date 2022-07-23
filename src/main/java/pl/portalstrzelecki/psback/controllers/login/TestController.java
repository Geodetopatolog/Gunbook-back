package pl.portalstrzelecki.psback.controllers.login;

import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {
    @GetMapping("/hello/test")
    public String sayHello() {
        return "hello";
    }


    @GetMapping("/hello/test2")
    public String sayHello2() {
        return "hello";
    }

}
