package com.campus.huanjinzi.campusmvp;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by huanjinzi on 2016/9/6.
 */
public class Cj {

    private int currentPage;
    private int currentResult;
    private boolean entityOrField;
    private int showCount;
    private String sortName;
    private String sortOrder;
    private int totalPage;
    private int totalResult;
    private List<ItemsBean> items;

    public static Cj objectFromData(String str) {

        return new Gson().fromJson(str, Cj.class);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentResult() {
        return currentResult;
    }

    public void setCurrentResult(int currentResult) {
        this.currentResult = currentResult;
    }

    public boolean isEntityOrField() {
        return entityOrField;
    }

    public void setEntityOrField(boolean entityOrField) {
        this.entityOrField = entityOrField;
    }

    public int getShowCount() {
        return showCount;
    }

    public void setShowCount(int showCount) {
        this.showCount = showCount;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        private String bh_id;
        private String bj;
        private String cj;
        private String jd;
        private String jgmc;
        private String jgpxzd;
        private String jxb_id;
        private String kcgsmc;
        private String kch;
        private String kclbmc;
        private String kcmc;
        private String kcxzdm;
        private String kcxzmc;
        private String kkbmmc;
        private String ksxz;
        private String listnav;
        private String njdm_id;
        private String njmc;
        private boolean pageable;
        private QueryModelBean queryModel;
        private boolean rangeable;
        private String totalResult;
        private String xb;
        private String xf;
        private String xh;
        private String xh_id;
        private String xm;
        private String xnm;
        private String xnmmc;
        private String xqm;
        private String xqmmc;
        private String zyh_id;
        private String zymc;

        public static ItemsBean objectFromData(String str) {

            return new Gson().fromJson(str, ItemsBean.class);
        }

        public String getBh_id() {
            return bh_id;
        }

        public void setBh_id(String bh_id) {
            this.bh_id = bh_id;
        }

        public String getBj() {
            return bj;
        }

        public void setBj(String bj) {
            this.bj = bj;
        }

        public String getCj() {
            return cj;
        }

        public void setCj(String cj) {
            this.cj = cj;
        }

        public String getJd() {
            return jd;
        }

        public void setJd(String jd) {
            this.jd = jd;
        }

        public String getJgmc() {
            return jgmc;
        }

        public void setJgmc(String jgmc) {
            this.jgmc = jgmc;
        }

        public String getJgpxzd() {
            return jgpxzd;
        }

        public void setJgpxzd(String jgpxzd) {
            this.jgpxzd = jgpxzd;
        }

        public String getJxb_id() {
            return jxb_id;
        }

        public void setJxb_id(String jxb_id) {
            this.jxb_id = jxb_id;
        }

        public String getKcgsmc() {
            return kcgsmc;
        }

        public void setKcgsmc(String kcgsmc) {
            this.kcgsmc = kcgsmc;
        }

        public String getKch() {
            return kch;
        }

        public void setKch(String kch) {
            this.kch = kch;
        }

        public String getKclbmc() {
            return kclbmc;
        }

        public void setKclbmc(String kclbmc) {
            this.kclbmc = kclbmc;
        }

        public String getKcmc() {
            return kcmc;
        }

        public void setKcmc(String kcmc) {
            this.kcmc = kcmc;
        }

        public String getKcxzdm() {
            return kcxzdm;
        }

        public void setKcxzdm(String kcxzdm) {
            this.kcxzdm = kcxzdm;
        }

        public String getKcxzmc() {
            return kcxzmc;
        }

        public void setKcxzmc(String kcxzmc) {
            this.kcxzmc = kcxzmc;
        }

        public String getKkbmmc() {
            return kkbmmc;
        }

        public void setKkbmmc(String kkbmmc) {
            this.kkbmmc = kkbmmc;
        }

        public String getKsxz() {
            return ksxz;
        }

        public void setKsxz(String ksxz) {
            this.ksxz = ksxz;
        }

        public String getListnav() {
            return listnav;
        }

        public void setListnav(String listnav) {
            this.listnav = listnav;
        }

        public String getNjdm_id() {
            return njdm_id;
        }

        public void setNjdm_id(String njdm_id) {
            this.njdm_id = njdm_id;
        }

        public String getNjmc() {
            return njmc;
        }

        public void setNjmc(String njmc) {
            this.njmc = njmc;
        }

        public boolean isPageable() {
            return pageable;
        }

        public void setPageable(boolean pageable) {
            this.pageable = pageable;
        }

        public QueryModelBean getQueryModel() {
            return queryModel;
        }

        public void setQueryModel(QueryModelBean queryModel) {
            this.queryModel = queryModel;
        }

        public boolean isRangeable() {
            return rangeable;
        }

        public void setRangeable(boolean rangeable) {
            this.rangeable = rangeable;
        }

        public String getTotalResult() {
            return totalResult;
        }

        public void setTotalResult(String totalResult) {
            this.totalResult = totalResult;
        }

        public String getXb() {
            return xb;
        }

        public void setXb(String xb) {
            this.xb = xb;
        }

        public String getXf() {
            return xf;
        }

        public void setXf(String xf) {
            this.xf = xf;
        }

        public String getXh() {
            return xh;
        }

        public void setXh(String xh) {
            this.xh = xh;
        }

        public String getXh_id() {
            return xh_id;
        }

        public void setXh_id(String xh_id) {
            this.xh_id = xh_id;
        }

        public String getXm() {
            return xm;
        }

        public void setXm(String xm) {
            this.xm = xm;
        }

        public String getXnm() {
            return xnm;
        }

        public void setXnm(String xnm) {
            this.xnm = xnm;
        }

        public String getXnmmc() {
            return xnmmc;
        }

        public void setXnmmc(String xnmmc) {
            this.xnmmc = xnmmc;
        }

        public String getXqm() {
            return xqm;
        }

        public void setXqm(String xqm) {
            this.xqm = xqm;
        }

        public String getXqmmc() {
            return xqmmc;
        }

        public void setXqmmc(String xqmmc) {
            this.xqmmc = xqmmc;
        }

        public String getZyh_id() {
            return zyh_id;
        }

        public void setZyh_id(String zyh_id) {
            this.zyh_id = zyh_id;
        }

        public String getZymc() {
            return zymc;
        }

        public void setZymc(String zymc) {
            this.zymc = zymc;
        }

        public static class QueryModelBean {
            private int currentPage;
            private int currentResult;
            private boolean entityOrField;
            private int showCount;
            private int totalPage;
            private int totalResult;

            public static QueryModelBean objectFromData(String str) {

                return new Gson().fromJson(str, QueryModelBean.class);
            }

            public int getCurrentPage() {
                return currentPage;
            }

            public void setCurrentPage(int currentPage) {
                this.currentPage = currentPage;
            }

            public int getCurrentResult() {
                return currentResult;
            }

            public void setCurrentResult(int currentResult) {
                this.currentResult = currentResult;
            }

            public boolean isEntityOrField() {
                return entityOrField;
            }

            public void setEntityOrField(boolean entityOrField) {
                this.entityOrField = entityOrField;
            }

            public int getShowCount() {
                return showCount;
            }

            public void setShowCount(int showCount) {
                this.showCount = showCount;
            }

            public int getTotalPage() {
                return totalPage;
            }

            public void setTotalPage(int totalPage) {
                this.totalPage = totalPage;
            }

            public int getTotalResult() {
                return totalResult;
            }

            public void setTotalResult(int totalResult) {
                this.totalResult = totalResult;
            }
        }
    }
}
