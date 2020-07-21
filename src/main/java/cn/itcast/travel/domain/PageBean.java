package cn.itcast.travel.domain;

import java.util.List;

/**
 * 分页相关
 */
public class PageBean<T> {
    //总记录数
    private int totalCount;
    //总页数
    private int totalPage;
    //每页记录条数
    private int pageSize;
    //当前页码
    private int currentPage;
    //每页显示的数据集合
    private List<T> list;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", pageSize=" + pageSize +
                ", currentPage=" + currentPage +
                ", list=" + list +
                '}';
    }
}
