package com.campus.huanjinzi.campusmvp.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huanjinzi on 2016/8/31.
 */
public class StudentInfo {


    private boolean success;
    private DataBean data;

    public static StudentInfo objectFromData(String str) {

        return new Gson().fromJson(str, StudentInfo.class);
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

                    public static class ItemsBean implements Serializable{
                        private String id;
                        private String xm;
                        private String mz;
                        private String xb;
                        private String zzmmm;
                        private String zjlxdm;
                        private String zjh;
                        private String csrq;
                        private String jgdm;
                        private String lb;
                        private String dh;
                        private String xjzt;
                        private String rxsj;
                        private String bj;
                        private String zy;
                        private String yx;
                        private List<?> xmpy;
                        private List<?> csddm;
                        private List<?> email;

                        public static ItemsBean objectFromData(String str) {

                            return new Gson().fromJson(str, ItemsBean.class);
                        }

                        public String getId() {
                            return id;
                        }

                        public void setId(String id) {
                            this.id = id;
                        }

                        public String getXm() {
                            return xm;
                        }

                        public void setXm(String xm) {
                            this.xm = xm;
                        }

                        public String getMz() {
                            return mz;
                        }

                        public void setMz(String mz) {
                            this.mz = mz;
                        }

                        public String getXb() {
                            return xb;
                        }

                        public void setXb(String xb) {
                            this.xb = xb;
                        }

                        public String getZzmmm() {
                            return zzmmm;
                        }

                        public void setZzmmm(String zzmmm) {
                            this.zzmmm = zzmmm;
                        }

                        public String getZjlxdm() {
                            return zjlxdm;
                        }

                        public void setZjlxdm(String zjlxdm) {
                            this.zjlxdm = zjlxdm;
                        }

                        public String getZjh() {
                            return zjh;
                        }

                        public void setZjh(String zjh) {
                            this.zjh = zjh;
                        }

                        public String getCsrq() {
                            return csrq;
                        }

                        public void setCsrq(String csrq) {
                            this.csrq = csrq;
                        }

                        public String getJgdm() {
                            return jgdm;
                        }

                        public void setJgdm(String jgdm) {
                            this.jgdm = jgdm;
                        }

                        public String getLb() {
                            return lb;
                        }

                        public void setLb(String lb) {
                            this.lb = lb;
                        }

                        public String getDh() {
                            return dh;
                        }

                        public void setDh(String dh) {
                            this.dh = dh;
                        }

                        public String getXjzt() {
                            return xjzt;
                        }

                        public void setXjzt(String xjzt) {
                            this.xjzt = xjzt;
                        }

                        public String getRxsj() {
                            return rxsj;
                        }

                        public void setRxsj(String rxsj) {
                            this.rxsj = rxsj;
                        }

                        public String getBj() {
                            return bj;
                        }

                        public void setBj(String bj) {
                            this.bj = bj;
                        }

                        public String getZy() {
                            return zy;
                        }

                        public void setZy(String zy) {
                            this.zy = zy;
                        }

                        public String getYx() {
                            return yx;
                        }

                        public void setYx(String yx) {
                            this.yx = yx;
                        }

                        public List<?> getXmpy() {
                            return xmpy;
                        }

                        public void setXmpy(List<?> xmpy) {
                            this.xmpy = xmpy;
                        }

                        public List<?> getCsddm() {
                            return csddm;
                        }

                        public void setCsddm(List<?> csddm) {
                            this.csddm = csddm;
                        }

                        public List<?> getEmail() {
                            return email;
                        }

                        public void setEmail(List<?> email) {
                            this.email = email;
                        }
                    }
                }
            }
        }
    }
}
