package com.syouketu.code.generator.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class CodeGeneratorApplicationTests {

    @Test
    public void contextLoads() {
        String s = "crm_abc";
        System.out.println(s.substring(0, s.indexOf("_")));
    }

}
