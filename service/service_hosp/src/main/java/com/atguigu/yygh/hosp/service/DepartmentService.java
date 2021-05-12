package com.atguigu.yygh.hosp.service;

import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.vo.hosp.DepartmentQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

public interface DepartmentService {
    void save(Map<String, Object> paramMap);
//    Page<Department> selectPage(Integer page, Integer limit, DepartmentQueryVo departmentQueryVo);

    org.springframework.data.domain.Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo);

    void remove(String hoscode, String depcode);
}
