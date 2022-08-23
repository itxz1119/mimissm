package com.bjpowernode.mapper;

import com.bjpowernode.pojo.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper {

    Admin login(@Param("name") String name, @Param("pwd") String pwd);

    //Admin login(@Param("name") String name, @Param("pwd") String pwd);
    Admin login2(@Param("name") String name, @Param("pwd") String pwd);
    /*
    *
    * master....
    * */



}
