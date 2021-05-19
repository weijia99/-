package com.atguigu.yygh.user.service.impl;

import com.atguigu.yygh.common.ResultCodeEnum;
import com.atguigu.yygh.common.exception.HospitalException;
import com.atguigu.yygh.common.helper.JwtHelper;
import com.atguigu.yygh.model.user.UserInfo;
import com.atguigu.yygh.user.mapper.UserInfoMapper;
import com.atguigu.yygh.user.service.UserInfoService;
import com.atguigu.yygh.vo.user.LoginVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserInfoServiceImpl extends
        ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService
{
    @Autowired
    private RedisTemplate<String ,String> redisTemplate;

    @Override
    public Map<String, Object> loginUser(LoginVo loginVo) {
        String phone = loginVo.getPhone();
        String code = loginVo.getCode();
        //校验参数
        if(StringUtils.isEmpty(phone) ||
                StringUtils.isEmpty(code)) {
            throw new HospitalException(ResultCodeEnum.PARAM_ERROR);
        }
        // TODO 判断手机验证码和输入的验证码是否一致
        //校验校验验证码
        String mobleCode = redisTemplate.opsForValue().get(phone);
        if(!code.equals(mobleCode)) {
            throw new HospitalException(ResultCodeEnum.CODE_ERROR);
        }


        // 判断是否是第一次登录:根据手机号查询数据库
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        UserInfo userInfo = baseMapper.selectOne(wrapper);

        // 如果是第一次使用手机登录
        if (userInfo == null) {
            // 添加信息到数据库
            userInfo = new UserInfo();
            userInfo.setName("");
            userInfo.setPhone(phone);
            userInfo.setStatus(1);
            baseMapper.insert(userInfo);
        }

        // 校验是否被禁用
        if (userInfo.getStatus() == 0) {
            throw new HospitalException(ResultCodeEnum.LOGIN_DISABLED_ERROR);
        }

        // 不是第一次,就直接登录


        // 返回登录信息


        // 返回登录用户名


        // 返回tocken信息
        HashMap<String, Object> map = new HashMap<>();
        String name = userInfo.getName();
        // 如果用户名称为空，就去得到昵称
        if (StringUtils.isEmpty(name)) {
            name = userInfo.getNickName();
        }
        // 如果昵称还为空，就去得到手机号
        if (StringUtils.isEmpty(name)) {
            name = userInfo.getPhone();
        }
        map.put("name", name);

        String token = JwtHelper.createToken(userInfo.getId(), name);
        map.put("token",token);


        return map;


    }
}
