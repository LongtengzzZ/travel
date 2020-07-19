package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 用户的servlet
 * @author longteng
 */
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    /**
     * 用户注册功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //验证校验
        String check = req.getParameter("check");
        //从sesion中获取验证码
        HttpSession session = req.getSession();
        String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//为了保证验证码只能使用一次
        //比较
        if(checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){
            //验证码错误,创建验证信息对象
            ResultInfo info = new ResultInfo();
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            //将info对象序列化为json
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            resp.setContentType("application/json;charset=utf-8");
            resp.getWriter().write(json);
            return;
        }
        //1.不为空，获取数据
        Map<String, String[]> map = req.getParameterMap();

        //2.封装对象
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //3.调用service完成注册
        UserService service = new UserServiceImpl();
        boolean flag = service.regist(user);
        ResultInfo info = new ResultInfo();
        //4.响应结果
        if(flag){
            //注册成功
            info.setFlag(true);
        }else{
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败!");
        }

        //将info对象序列化为json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);

        //将json数据写回客户端
        //设置content-type
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);
    }

    /**
     * 登录功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取参数
        String check = req.getParameter("check");
        System.out.println(check);
        //2.验证码校验
        HttpSession session = req.getSession();
        String checkJudge = (String)session.getAttribute("CHECKCODE_SERVER");
        System.out.println(checkJudge);
        //为了保证验证码只能使用一次
        session.removeAttribute("CHECKCODE_SERVER");
        ResultInfo info = new ResultInfo();
        //失败。获得的验证码为空，session中的验证码为空，两者验证码不相等
        if (check == null || checkJudge == null || !check.equalsIgnoreCase(checkJudge)){
            //失败
            info.setFlag(false);
            info.setErrorMsg("验证码错误，请从新输入");
            //将失败信息发送会页面
            // 作用？？？
//            return;
        }
        //成功
        User user = new User();
        Map<String, String[]> map = req.getParameterMap();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(user);
        //调用service方法
        UserService service = new UserServiceImpl();
        User returnUser = service.login(user);
        //判断user是否为null
        if (returnUser == null){
            //错误
            info.setFlag(false);
            info.setErrorMsg("用户名或者密码错误");
        }else{
            //正确
            info.setFlag(true);
            info.setErrorMsg("登录成功");
            req.getSession().setAttribute("user",returnUser);
        }
        //把info序列化为json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        //设置编码
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);
    }

    /**
     * 退出功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.销毁session
        request.getSession().invalidate();

        //2.跳转登录页面
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    /**
     * 查询是否存在用户
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void findUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从session中获取登录用户
        Object user = req.getSession().getAttribute("user");
        //将user写回客户端
        ObjectMapper mapper = new ObjectMapper();
        //设置编码
        resp.setContentType("application/json;charset=utf-8");
        String json = mapper.writeValueAsString(user);
        System.out.println(json);
        //写回客户端
        resp.getWriter().write(json);
    }


}
