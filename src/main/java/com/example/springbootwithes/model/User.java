package com.example.springbootwithes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * User
 * 建立一个实体类，这个类是用来测试的
 *
 * @author Lee
 * @date 2022/4/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(value = "user")
public class User {

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private String sex;
}
