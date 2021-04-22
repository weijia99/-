package com.atguigu.yygh.hosp.controller;

import com.atguigu.yygh.hosp.service.HospitalSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {
//    默认返回json
    @Autowired
    private HospitalSetService hospitalSetService;
}
