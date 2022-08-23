package com.bjpowernode.service.impl;

import com.bjpowernode.mapper.ProductInfoMapper;
import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.pojo.ProductType;
import com.bjpowernode.pojo.vo.ProductVo;
import com.bjpowernode.service.ProductInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    ProductInfoMapper productInfoMapper;
//查询所有商品
    @Override
    public List<ProductInfo> getAll() {
        return productInfoMapper.getAll();
    }
//分页查询
    @Override
    public PageInfo<ProductInfo> split(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ProductInfo> list = productInfoMapper.getAll();
        return new PageInfo<>(list);
    }

    @Override
    public int add(ProductInfo info) {
        return productInfoMapper.add(info);
    }

    @Override
    public ProductInfo selectByPid(int pid) {
        return productInfoMapper.selectByPid(pid);
    }

    @Override
    public int update(ProductInfo info) {
        return productInfoMapper.update(info);
    }

    @Override
    public int delete(int pid) {
        return productInfoMapper.delete(pid);
    }

    @Override
    public int deleteByIds(String[] split) {
        return productInfoMapper.deleteByIds(split);
    }

    @Override
    public PageInfo splitCondition(ProductVo vo, int pagesize) {
        PageHelper.startPage(vo.getPage(), pagesize);
        List list = productInfoMapper.splitCondition(vo);
        return new PageInfo(list);
    }


}
