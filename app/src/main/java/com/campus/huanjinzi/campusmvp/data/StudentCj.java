package com.campus.huanjinzi.campusmvp.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by huanjinzi on 2016/9/1.
 */
public class StudentCj {

    private boolean success;
    private DataBean data;

    public static StudentCj objectFromData(String str) {

        return new Gson().fromJson(str, StudentCj.class);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private GetDataResponseBean getDataResponse;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public GetDataResponseBean getGetDataResponse() {
            return getDataResponse;
        }

        public void setGetDataResponse(GetDataResponseBean getDataResponse) {
            this.getDataResponse = getDataResponse;
        }

        public static class GetDataResponseBean {
            @SerializedName("return")
            private ReturnBean returnX;

            public static GetDataResponseBean objectFromData(String str) {

                return new Gson().fromJson(str, GetDataResponseBean.class);
            }

            public ReturnBean getReturnX() {
                return returnX;
            }

            public void setReturnX(ReturnBean returnX) {
                this.returnX = returnX;
            }

            public static class ReturnBean {
                private BodyBean Body;

                public static ReturnBean objectFromData(String str) {

                    return new Gson().fromJson(str, ReturnBean.class);
                }

                public BodyBean getBody() {
                    return Body;
                }

                public void setBody(BodyBean Body) {
                    this.Body = Body;
                }

                public static class BodyBean {
                    private String code;
                    private String flag;
                    private String msg;
                    private String rows;
                    private String total;
                    private List<ItemsBean> items;

                    public static BodyBean objectFromData(String str) {

                        return new Gson().fromJson(str, BodyBean.class);
                    }

                    public String getCode() {
                        return code;
                    }

                    public void setCode(String code) {
                        this.code = code;
                    }

                    public String getFlag() {
                        return flag;
                    }

                    public void setFlag(String flag) {
                        this.flag = flag;
                    }

                    public String getMsg() {
                        return msg;
                    }

                    public void setMsg(String msg) {
                        this.msg = msg;
                    }

                    public String getRows() {
                        return rows;
                    }

                    public void setRows(String rows) {
                        this.rows = rows;
                    }

                    public String getTotal() {
                        return total;
                    }

                    public void setTotal(String total) {
                        this.total = total;
                    }

                    public List<ItemsBean> getItems() {
                        return items;
                    }

                    public void setItems(List<ItemsBean> items) {
                        this.items = items;
                    }

                    public static class ItemsBean {
                        private String xnm;
                        private String xqdm;
                        private String xqm;
                        private String jd;
                        private String xf;
                        private String jxb;
                        private String kcmc;
                        private String xh;
                        private String kslx;
                        private String cj;

                        public static ItemsBean objectFromData(String str) {

                            return new Gson().fromJson(str, ItemsBean.class);
                        }

                        public String getXnm() {
                            return xnm;
                        }

                        public void setXnm(String xnm) {
                            this.xnm = xnm;
                        }

                        public String getXqdm() {
                            return xqdm;
                        }

                        public void setXqdm(String xqdm) {
                            this.xqdm = xqdm;
                        }

                        public String getXqm() {
                            return xqm;
                        }

                        public void setXqm(String xqm) {
                            this.xqm = xqm;
                        }

                        public String getJd() {
                            return jd;
                        }

                        public void setJd(String jd) {
                            this.jd = jd;
                        }

                        public String getXf() {
                            return xf;
                        }

                        public void setXf(String xf) {
                            this.xf = xf;
                        }

                        public String getJxb() {
                            return jxb;
                        }

                        public void setJxb(String jxb) {
                            this.jxb = jxb;
                        }

                        public String getKcmc() {
                            return kcmc;
                        }

                        public void setKcmc(String kcmc) {
                            this.kcmc = kcmc;
                        }

                        public String getXh() {
                            return xh;
                        }

                        public void setXh(String xh) {
                            this.xh = xh;
                        }

                        public String getKslx() {
                            return kslx;
                        }

                        public void setKslx(String kslx) {
                            this.kslx = kslx;
                        }

                        public String getCj() {
                            return cj;
                        }

                        public void setCj(String cj) {
                            this.cj = cj;
                        }
                    }
                }
            }
        }
    }
}
