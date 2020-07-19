package cn.itcast.travel.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 一个方法分发的servlet
 * @author longteng
 */
public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp){
        //1.获取请求路径,形式：/travel/user/add
        String uri = req.getRequestURI();
        //2.根据路径获取方法名
        //再用/分隔
        String[] uris = uri.split("/");
        String methodName = uris[3];
        try {
            //3.getMethod获得该类某个公有的方法
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
            //4.传递参数调用该方法
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
