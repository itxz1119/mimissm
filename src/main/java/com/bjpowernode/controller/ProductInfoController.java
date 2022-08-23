package com.bjpowernode.controller;

import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.pojo.vo.ProductVo;
import com.bjpowernode.service.ProductInfoService;
import com.bjpowernode.utils.FileNameUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jdk.management.resource.internal.inst.SocketInputStreamRMHooks;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProductInfoController {
    //定义每页显示数据
    static final int PAGE_SIZE = 5;
    //图片名称
    String saveFilename = "";
    @Autowired
    ProductInfoService productInfoService;

    //分页显示,第一页
    @RequestMapping("/split")
    public String split(HttpServletRequest request){
        PageInfo<ProductInfo> info = null;
        ProductVo vo = (ProductVo) request.getSession().getAttribute("vo");
        if (vo != null){
            info = productInfoService.splitCondition(vo, PAGE_SIZE);
            request.getSession().removeAttribute("vo");
        }else {
            info = productInfoService.split(1, PAGE_SIZE);
        }
        //PageInfo<ProductInfo> info = productInfoService.split(1, PAGE_SIZE);
        request.setAttribute("info",info);
        return "product";

    }
    //翻页处理
    @ResponseBody
    @RequestMapping("/ajaxSplit")
    public void ajaxSplit(ProductVo vo, HttpSession session){
        //PageInfo<ProductInfo> info = productInfoService.split(page, PAGE_SIZE);
        PageInfo info = productInfoService.splitCondition(vo, PAGE_SIZE);
        session.setAttribute("info", info);
        System.out.println("翻页数据：" + info);
    }
    //多条件查询
    @ResponseBody
    @RequestMapping("/ajaxSplit1")
    public void ajaxSplit1(ProductVo vo, HttpSession session){
        PageInfo pageInfo = productInfoService.splitCondition(vo, PAGE_SIZE);
        System.out.println("多条件查询" + pageInfo);
        session.setAttribute("info", pageInfo);
    }
    //图片上传
    @ResponseBody
    @RequestMapping("/ajaxImg")
    public String ajaxImg(MultipartFile pimage, HttpServletRequest request){
        //生成图片名字；获取图片后缀
        saveFilename = FileNameUtil.getUUIDFileName() + FileNameUtil.getFileType(pimage.getOriginalFilename());
        //得到项目图片存储路径
        String realPath = request.getServletContext().getRealPath("/image_big");
        System.out.println("图片路径：" + realPath);
        //转存
        try {
            pimage.transferTo(new File(realPath + File.separator + saveFilename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("imgurl", saveFilename);
        return jsonObject.toString();
    }
    //新增商品
    @RequestMapping("/save")
    public String save(ProductInfo info, HttpServletRequest request){
        info.setpDate(new Date());
        info.setpImage(saveFilename);
        int num = -1;
        num = productInfoService.add(info);
        if (num == 1){
            request.setAttribute("msg", "添加成功");
        }else {
            request.setAttribute("msg", "添加失败");
        }
        saveFilename = "";
        return "forward:/prod/split.action";
    }
    //根据pid查询商品，回显到修改页面
    @RequestMapping("/one")
    public String selectByPid(ProductVo vo, int pid, HttpServletRequest request){
        ProductInfo productInfo = productInfoService.selectByPid(pid);
        request.setAttribute("prod", productInfo);
        request.getSession().setAttribute("vo",vo);
        System.out.println("回显的页面图片：" + productInfo.getpImage());
        return "update";
    }
    //修改操作
    @RequestMapping("/update")
    public String update(ProductInfo info, HttpServletRequest request){
        //这个是前端提交上来图片路径，也就是未修改前的图片名称
        String initPath = info.getpImage();
        System.out.println("initPath:" + initPath);
        System.out.println("saveFilename：" + saveFilename);
        info.setpDate(new Date());
        if (!saveFilename.equals("")){
            info.setpImage(saveFilename);
        }
        int num = -1;
        num = productInfoService.update(info);
        System.out.println("输出info:" + info);
        if (num == 1){
            request.setAttribute("msg", "修改成功");
        }else {
            request.setAttribute("msg", "修改失败");
        }
        saveFilename = "";
        return "forward:/prod/split.action";
    }
    /*//删除操作
    @RequestMapping("/delete")
    public String delete(int pid, HttpServletRequest request){
        int num = -1;
        num = productInfoService.delete(pid);
        if (num ==1){
            request.setAttribute("msg", "删除成功");
        }else {
            request.setAttribute("msg", "删除失败");
        }
        return "forward:/prod/deleteAjaxSplit.action";
    }*/
  /*  //删除后跳转到分页
    @ResponseBody
    @RequestMapping(value = "/deleteAjaxSplit", produces = "text/html;charset=UTF-8")
    public Object deleteAjaxSplit(HttpServletRequest request){
        PageInfo<ProductInfo> info = productInfoService.split(1, PAGE_SIZE);
        request.getSession().setAttribute("info", info);
        return request.getAttribute("msg");
    }*/
  @ResponseBody
  @RequestMapping(value = "/delete", produces = "text/html;charset=UTF-8")
  public String delete(ProductVo vo, int pid, HttpSession session){
      int num = -1;
      num = productInfoService.delete(pid);
      String msg = "";
      if (num == 1){
          msg = "删除成功";
      }else {
          msg = "删除失败";
      }
      session.setAttribute("vo", vo);
      return msg;
  }
  //批量删除
    @ResponseBody
    @RequestMapping(value = "/deleteByIds", produces = "text/html;charset=UTF-8")
    public String deleteByIds(String str,ProductVo vo, HttpSession session){
        String[] split = str.split(",");
        int num = -1;
        num = productInfoService.deleteByIds(split);
        String msg = "批量删除失败";
        if (num > 0){
            msg = "批量删除成功";
        }
        session.setAttribute("vo", vo);
        return msg;
    }


}
