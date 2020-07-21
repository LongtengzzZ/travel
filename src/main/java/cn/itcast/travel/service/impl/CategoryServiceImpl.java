package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 查询种类的service
 * @author longteng
 */
public class CategoryServiceImpl implements CategoryService {
    private CategoryDao dao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        //1.获取jedis客户端
        Jedis jedis = JedisUtil.getJedis();
        //1.3查询sortedset中的分数(cid)和值(cname)，Tuple中封装了分数和值。
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);

        List<Category> cs = null;
        //2.判断查询的集合是否为空
        if (categorys == null || categorys.size() == 0) {
            System.out.println("从数据库查询....");
            //3.如果为空,需要从数据库查询,在将数据存入redis
            //3.1 从数据库查询
            cs = dao.findAll();
            //3.2 将集合数据存储到redis中的 category的key
            for (int i = 0; i < cs.size(); i++) {

                jedis.zadd("category", cs.get(i).getCid(), cs.get(i).getCname());
            }
        } else {
            System.out.println("从redis中查询.....");
            //4.如果不为空,将set的数据存入list
            cs = new ArrayList<Category>();
            for (Tuple tuple : categorys) {
                Category category = new Category();
                category.setCid((int)tuple.getScore());
                category.setCname(tuple.getElement());
                cs.add(category);
            }
        }
            return cs;
    }
}
