package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ObjectIdMap;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 分类数据展示servlet
 * @author longteng
 */
@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {

    private CategoryService service = new CategoryServiceImpl();

    /**
     *查询所有种类
     * @param req
     * @param resp
     * @throws IOException
     */
    public void findAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //1.调用service查询所有
        List<Category> categorys = service.findAll();
        //2.把对象序列换为json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(categorys);
        System.out.println("查询到的category："+json);
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);
    }
}
