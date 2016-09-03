package com.campus.huanjinzi.campusmvp.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by huanjinzi on 2016/8/31.
 */
public class ResponseBean {



    private DataBean data;

    public static ResponseBean objectFromData(String str) {

        return new Gson().fromJson(str, ResponseBean.class);
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * return : {"success":true,"info":{"id":"222013322270099","attributes":{"ACPNAME":"6KKB5qyi","e_rdn":"QUNQVUlEPTIyMjAxMzMyMjI3MDA5OSxDT05UQUlORVJJRD1wZXJzb24sY249dXNlcg==","ACPNICKNAME":"aHVhbmppbnpp","ACPSCHOOLEMAIL":"aHVhbmppbnppQGVtYWlsLnN3dS5lZHUuY24=","ACPORGDN":"MzIy","tgt":"VEdULTk4MTMtRHlXN2lPMWdNVW1rR0R4NlBJTzVCSUVnek9XdzBtZU5IcXZFdWlnNDNRRTJxdnFiNFItaHR0cDovLzIyMi4xOTguMTIwLjIwNjo4MDgyL2Nhcw==","ACPUSERGROUPDN":"Ymtz","ACPUID":"MjIyMDEzMzIyMjcwMDk5"}}}
         */

        private GetUserInfoByUserNameResponseBean getUserInfoByUserNameResponse;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public GetUserInfoByUserNameResponseBean getGetUserInfoByUserNameResponse() {
            return getUserInfoByUserNameResponse;
        }

        public void setGetUserInfoByUserNameResponse(GetUserInfoByUserNameResponseBean getUserInfoByUserNameResponse) {
            this.getUserInfoByUserNameResponse = getUserInfoByUserNameResponse;
        }

        public static class GetUserInfoByUserNameResponseBean {
            /**
             * success : true
             * info : {"id":"222013322270099","attributes":{"ACPNAME":"6KKB5qyi","e_rdn":"QUNQVUlEPTIyMjAxMzMyMjI3MDA5OSxDT05UQUlORVJJRD1wZXJzb24sY249dXNlcg==","ACPNICKNAME":"aHVhbmppbnpp","ACPSCHOOLEMAIL":"aHVhbmppbnppQGVtYWlsLnN3dS5lZHUuY24=","ACPORGDN":"MzIy","tgt":"VEdULTk4MTMtRHlXN2lPMWdNVW1rR0R4NlBJTzVCSUVnek9XdzBtZU5IcXZFdWlnNDNRRTJxdnFiNFItaHR0cDovLzIyMi4xOTguMTIwLjIwNjo4MDgyL2Nhcw==","ACPUSERGROUPDN":"Ymtz","ACPUID":"MjIyMDEzMzIyMjcwMDk5"}}
             */

            @SerializedName("return")
            private ReturnBean returnX;

            public static GetUserInfoByUserNameResponseBean objectFromData(String str) {

                return new Gson().fromJson(str, GetUserInfoByUserNameResponseBean.class);
            }

            public ReturnBean getReturnX() {
                return returnX;
            }

            public void setReturnX(ReturnBean returnX) {
                this.returnX = returnX;
            }

            public static class ReturnBean {
                /**
                 * id : 222013322270099
                 * attributes : {"ACPNAME":"6KKB5qyi","e_rdn":"QUNQVUlEPTIyMjAxMzMyMjI3MDA5OSxDT05UQUlORVJJRD1wZXJzb24sY249dXNlcg==","ACPNICKNAME":"aHVhbmppbnpp","ACPSCHOOLEMAIL":"aHVhbmppbnppQGVtYWlsLnN3dS5lZHUuY24=","ACPORGDN":"MzIy","tgt":"VEdULTk4MTMtRHlXN2lPMWdNVW1rR0R4NlBJTzVCSUVnek9XdzBtZU5IcXZFdWlnNDNRRTJxdnFiNFItaHR0cDovLzIyMi4xOTguMTIwLjIwNjo4MDgyL2Nhcw==","ACPUSERGROUPDN":"Ymtz","ACPUID":"MjIyMDEzMzIyMjcwMDk5"}
                 */

                private InfoBean info;

                public static ReturnBean objectFromData(String str) {

                    return new Gson().fromJson(str, ReturnBean.class);
                }

                public InfoBean getInfo() {
                    return info;
                }

                public void setInfo(InfoBean info) {
                    this.info = info;
                }

                public static class InfoBean {
                    private String id;

                    public static InfoBean objectFromData(String str) {

                        return new Gson().fromJson(str, InfoBean.class);
                    }

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }
                }
            }
        }
    }
}
