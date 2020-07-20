package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;

import java.util.List;


/**
 * 种类dao
 * @author longteng
 */
public interface CategoryDao {
    /**
     * 查询所有种类
     * @return Category
     */
    List<Category> findAll();
}
