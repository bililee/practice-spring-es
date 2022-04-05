package com.example.springbootwithes.controller;

import com.example.springbootwithes.service.EsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * TestController
 *
 * @author Lee
 * @date 2022/4/5
 */
@RestController
public class TestController {

    @Resource
    public EsService esService;

    @RequestMapping(value = "test/say_hey")
    public String sayHey() {
        boolean isExists = esService.checkIndexExists("data");
        System.out.println(isExists);
        esService.createIndex("data");
        boolean isExists2 = esService.checkIndexExists("data");
        System.out.println(isExists2);
        return "hey";
    }

}
