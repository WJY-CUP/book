package com.wjy.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 14:53 2021/1/1
 * @E-mail: 15611562852@163.com
 */

public class WebUtils {

    // 自己new的IOC容器不会随着项目停止而关闭，随着项目多次启动关闭会导致内存泄露，
    // 故使用ContextLoader+Spring监听器自动管理IOC容器的销毁与创建
    // private static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

    /**
     * @Description: 从IOC容器中获取组件
     * @param:
     * @return:
     */
    public static <T>T getBean(Class<T> clazz) {
        //使用ContextLoader+Spring监听器自动管理IOC容器的销毁与创建,不会出现内存泄露问题
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        return context.getBean(clazz);
    }

    /**
     * @Description: 使用BeanUtils工具类快速获取提交参数给对象
     * @param:  map 注意此处也可以传入request对象，但是这样会导致DAO层与service层使用web层的HttpServletRequest,API耦合了，不好
     * @return:
     */
    public static <T>T copyParamToBean(Map value, T bean) {

        //使用BeanUtils快速获取提交参数给对象
        System.out.println("使用BeanUtils快速获取提交参数给对象");
        try {
            System.out.println(bean);
            BeanUtils.populate(bean, value);
            System.out.println(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }



    /**
     * @Description: 将字符串转化为整形，deleteBookById()需要用到
     * @param:
     * @return:
     */
    public static int parseInt(String s, int defaultValue) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
//            e.printStackTrace();
        }
        return defaultValue;
    }


    public static void parseInt(String pageNo) {
    }
}
