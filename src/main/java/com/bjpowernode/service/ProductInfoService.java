package com.bjpowernode.service;


import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.pojo.ProductType;
import com.bjpowernode.pojo.vo.ProductVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductInfoService {

//查询所有商品
    List<ProductInfo> getAll();
    //分页显示所有商品
    PageInfo<ProductInfo> split(int pageNum, int pageSize);


    int add(ProductInfo info);

    ProductInfo selectByPid(int pid);

    int update(ProductInfo info);

    int delete(int pid);

    int deleteByIds(String[] split);

    PageInfo splitCondition(ProductVo vo, int pagesize);
}
