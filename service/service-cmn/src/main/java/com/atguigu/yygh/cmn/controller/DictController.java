package com.atguigu.yygh.cmn.controller;

import com.atguigu.yygh.cmn.service.DictService;
import com.atguigu.yygh.common.Result;
import com.atguigu.yygh.model.cmn.Dict;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/admin/cmn/dict")
public class DictController {
    @Autowired
    private DictService dictService;

    @ApiOperation(value = "根据数据id查询子数据列表")
    @GetMapping("findChildData/{id}")
    public Result findChildData(@PathVariable Long id){

        List<Dict> list=dictService.findChildData(id);
        return Result.ok(list);
    }

    @ApiOperation(value="导出")
    @GetMapping("exportData")
    public void exportData(HttpServletResponse response){

        dictService.exportData(response);
    }

    @ApiOperation(value = "导入")
    @PostMapping("importData")
    public Result importData(MultipartFile file) {
        dictService.importData(file);
        //todo：前端没有完成
        return Result.ok();
    }


}
