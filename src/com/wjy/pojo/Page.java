package com.wjy.pojo;

import java.util.List;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 10:42 2021/1/13
 * @E-mail: 15611562852@163.com
 */

/**
 * @Description: 很多地方都用到T，故采用泛型
 * @param:  T 具体模块的bean类
 * @return:
 */
public class Page<T> {

    public static final Integer PAGE_SIZE = 4;

    private Integer pageNo;
    private Integer pageTotal;
    private Integer pageTotalCount;
    private Integer pageSize = PAGE_SIZE;
    private List<T> items;
//    因为许多页面都要用到分页，只是请求的地址不同而已，所以抽取出来
    private String url;


    @Override
    public String toString() {
        return "Page{" +
                "pageNo=" + pageNo +
                ", pageTotal=" + pageTotal +
                ", pageTotalCount=" + pageTotalCount +
                ", pageSize=" + pageSize +
                ", items=" + items +
                ", url='" + url + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        //        数据边界的有效检查
        if (pageNo < 1) {
            pageNo = 1;
        } else if (pageNo > pageTotal) {
            pageNo = pageTotal;
        }
        this.pageNo = pageNo;
    }

    public Integer getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

    public Integer getPageTotalCount() {
        return pageTotalCount;
    }

    public void setPageTotalCount(Integer pageTotalCount) {
        this.pageTotalCount = pageTotalCount;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
