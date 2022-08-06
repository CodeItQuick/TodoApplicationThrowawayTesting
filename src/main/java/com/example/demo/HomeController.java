package com.example.demo;

@RestController
public class HomeController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
