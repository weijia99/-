package com.atguigu.yygh.cmn.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@Repository
@FeignClient("service-cmn")
//要注册的模块
public interface DictFeignClient {
    @GetMapping(value = "/admin/cmn/dict/getName/{DictCode}/{value}")
    String getName(@PathVariable("DictCode") String DictCode, @PathVariable("value") String value);
//要调用的方法
    @GetMapping(value = "/admin/cmn/dict/getName/{value}")
    String getName(@PathVariable("value") String value);

}
