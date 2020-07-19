package cn.itcast.travel.web.servlet;

import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
}
