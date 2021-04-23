package com.atguigu.yygh.hosp.controller;

import com.atguigu.yygh.common.Result;
import com.atguigu.yygh.common.utils.MD5;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.atguigu.yygh.vo.hosp.HospitalSetQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

@RestController
@Api(tags = "医院设置管理")
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {
//    默认返回json
    @Autowired
    private HospitalSetService hospitalSetService;

    @ApiOperation(value = "获取当前所有")
    @GetMapping("findAll")
    public Result findAll(){
        List<HospitalSet> list = hospitalSetService.list();

        return Result.ok(list);
    }

    @DeleteMapping("{id}")
    public Result removeById(@PathVariable Long id){
        boolean b = hospitalSetService.removeById(id);
        if (b){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    @ApiOperation(value = "条件查询")
//    使用post传参，并使用json传入
    @PostMapping("findPageHospSet/{current}/{limit}")
    public Result findPageHospSet(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo){
//        传入对象，把数据
        Page<HospitalSet> page=new Page<>(current,limit);
//        分页开始
        QueryWrapper<HospitalSet> queryWrapper=new QueryWrapper<>();
        String hoscode = hospitalSetQueryVo.getHoscode();
        if (!StringUtils.isEmpty(hoscode)){
            queryWrapper.eq("hoscode",hoscode);
//            等于这一列的值
        }
        String hosname = hospitalSetQueryVo.getHosname();
        if (!StringUtils.isEmpty(hosname)){
            queryWrapper.like("hosname",hosname);
        }
//        调用方法实现分页
        Page<HospitalSet> hospitalSetPage = hospitalSetService.page(page, queryWrapper);
        return Result.ok(hospitalSetPage);
    }

    @PostMapping("saveHospitalSet")
    public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet){
        hospitalSet.setStatus(1);
//        为1可以使用
//        设置前面
        Random random=new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis()+""+random.nextInt(1000)));
//        加密
        boolean b = hospitalSetService.save(hospitalSet);
        if (b){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    @GetMapping("getHospSet/{id}")
    public Result getHospSet(@PathVariable long id){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }

    @PostMapping("updateHospitalSet")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet){
        boolean b = hospitalSetService.updateById(hospitalSet);
        if (b){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }
//    批量删除
//7 批量删除医院设置
    @DeleteMapping("batchRemove")
    public Result batchRemoveHospitalSet(@RequestBody List<Long> idList) {
        hospitalSetService.removeByIds(idList);
        return Result.ok();
    }
    //8 医院设置锁定和解锁
    @PutMapping("lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id,
                                  @PathVariable Integer status) {
        //根据id查询医院设置信息
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        //设置状态
        hospitalSet.setStatus(status);
        //调用方法
        hospitalSetService.updateById(hospitalSet);
        return Result.ok();
    }
    //9 发送签名秘钥
    @PutMapping("sendKey/{id}")
    public Result lockHospitalSet(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        //TODO 发送短信
        return Result.ok();
    }
}
