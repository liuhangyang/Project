package xupt.se.ttms.model;


import xupt.se.config.Config;

import java.util.List;

/**
 * Created by lc on 2016/5/28.
 */
public class Pager<T> {

    private int pageSize = Config.pageSize;  //每页数量
    private int curPage = 1;   //当前页
    private int total = 0;    //总数
    private int pageTotal = 1; //总页数
    private int actTotal = 0; //当前页实际数量


    private List<T> dataList;

    public Pager() {
    }

    public Pager(int pageSize, int curPage) {
        this.pageSize = pageSize;
        this.curPage = curPage;
    }

    public Pager(int pageSize, int total, int curPage, int pageTotal, List<T> dataList) {
        this.pageSize = pageSize;
        this.total = total;
        this.curPage = curPage;
        this.pageTotal = pageTotal;
        this.dataList = dataList;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
        this.actTotal = dataList.size();
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
        if(this.total != 0)
            this.pageTotal = (int)Math.ceil((float)this.total/this.pageSize);

        if(curPage > pageTotal){ //如果当前页码大于总页码，设为总页码
            curPage = pageTotal;
        }

    }
}
