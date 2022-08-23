package com.bjpowernode.mapper;

import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.pojo.vo.ProductVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductInfoMapper {


    //查询所有商品
    List<ProductInfo> getAll();

    int add(ProductInfo info);

    ProductInfo selectByPid(@Param("pid") int pid);

    int update(ProductInfo info);

    int delete(int pid);

    int deleteByIds(String[] split);

    List splitCondition(ProductVo vo);
}