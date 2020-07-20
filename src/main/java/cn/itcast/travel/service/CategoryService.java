package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * 种类service
 * @author longteng
 */
public interface CategoryService {
    /**
     * 查询所有种类
     * @return Category
     */
    List<Category> findAll();
}
