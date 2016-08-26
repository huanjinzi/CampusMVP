package com.campus.huanjinzi.campusmvp.data.cjbean;

import java.util.List;

public class CJBean
{
    private int currentPage;

    private int currentResult;

    private boolean entityOrField;

    private List<Items> items;

    private int showCount;

    private String sortName;

    private String sortOrder;

    private int totalPage;

    private int totalResult;

    public void setCurrentPage(int currentPage){
        this.currentPage = currentPage;
    }
    public int getCurrentPage(){
        return this.currentPage;
    }
    public void setCurrentResult(int currentResult){
        this.currentResult = currentResult;
    }
    public int getCurrentResult(){
        return this.currentResult;
    }
    public void setEntityOrField(boolean entityOrField){
        this.entityOrField = entityOrField;
    }
    public boolean getEntityOrField(){
        return this.entityOrField;
    }
    public void setItems(List<Items> items){
        this.items = items;
    }
    public List<Items> getItems(){
        return this.items;
    }
    public void setShowCount(int showCount){
        this.showCount = showCount;
    }
    public int getShowCount(){
        return this.showCount;
    }
    public void setSortName(String sortName){
        this.sortName = sortName;
    }
    public String getSortName(){
        return this.sortName;
    }
    public void setSortOrder(String sortOrder){
        this.sortOrder = sortOrder;
    }
    public String getSortOrder(){
        return this.sortOrder;
    }
    public void setTotalPage(int totalPage){
        this.totalPage = totalPage;
    }
    public int getTotalPage(){
        return this.totalPage;
    }
    public void setTotalResult(int totalResult){
        this.totalResult = totalResult;
    }
    public int getTotalResult(){
        return this.totalResult;
    }

    // TODO: 2016/8/21 Items类的位置

    // TODO: 2016/8/21 QueryModel类的位置
}
