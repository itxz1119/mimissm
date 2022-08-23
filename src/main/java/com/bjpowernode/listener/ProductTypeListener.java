package com.bjpowernode.listener;

import com.bjpowernode.pojo.ProductType;
import com.bjpowernode.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener
public class ProductTypeListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
      //手工获取spring容器中的对象
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext-*.xml");
        //获取ProductTypeServiceImpl对象
        ProductTypeService service = (ProductTypeService) context.getBean("ProductTypeServiceImpl");
        //调用查询方法
        List<ProductType> typeList = service.getType();
        //放入全局作用域
        servletContextEvent.getServletContext().setAttribute("typeList",typeList);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
