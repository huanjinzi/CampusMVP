package com.campus.huanjinzi.campusmvp.data.cjbean;

/**
 * Created by huanjinzi on 2016/8/23.
 */
public class QueryModel {
        private int currentPage;

        private int currentResult;

        private boolean entityOrField;

        private int showCount;

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
        public void setShowCount(int showCount){
            this.showCount = showCount;
        }
        public int getShowCount(){
            return this.showCount;
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
    }
