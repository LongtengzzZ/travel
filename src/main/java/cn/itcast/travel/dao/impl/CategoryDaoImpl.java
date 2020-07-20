package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Category> findAll() {
        List<Category> Categorys = null;
        try{
            //定义sql
            String sql = "select * from tab_category";
            Categorys = template.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
            System.out.println("categoryDaoImpl:"+Categorys);
        }catch (Exception e){

        }
        return Categorys;
    }
}
