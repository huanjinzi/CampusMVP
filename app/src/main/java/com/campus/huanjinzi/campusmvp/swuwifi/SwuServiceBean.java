package com.campus.huanjinzi.campusmvp.swuwifi;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by huanjinzi on 2016/10/16.
 */

public class SwuServiceBean implements Serializable{

    private String userUuid;
    private String userId;
    private String userName;
    private String lastLoginSelfTime;
    private String packageUuid;
    private String packageName;
    private int onlineNum;
    private String fee;
    private String showFee;
    private String prefee;
    private String showPrefee;
    private String greetings;
    private String emotionalWordsInIndex;
    private String emotionalWordsInLogin;
    private String accountInfoUuid;
    private String needFee;
    private String feeExpirationTime;
    private String haveDays;
    private int userState;
    private boolean canPrefund;
    private boolean isExpiring;
    private String expireTip;

    public static SwuServiceBean objectFromData(String str) {

        return new Gson().fromJson(str, SwuServiceBean.class);
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastLoginSelfTime() {
        return lastLoginSelfTime;
    }

    public void setLastLoginSelfTime(String lastLoginSelfTime) {
        this.lastLoginSelfTime = lastLoginSelfTime;
    }

    public String getPackageUuid() {
        return packageUuid;
    }

    public void setPackageUuid(String packageUuid) {
        this.packageUuid = packageUuid;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getOnlineNum() {
        return onlineNum;
    }

    public void setOnlineNum(int onlineNum) {
        this.onlineNum = onlineNum;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getShowFee() {
        return showFee;
    }

    public void setShowFee(String showFee) {
        this.showFee = showFee;
    }

    public String getPrefee() {
        return prefee;
    }

    public void setPrefee(String prefee) {
        this.prefee = prefee;
    }

    public String getShowPrefee() {
        return showPrefee;
    }

    public void setShowPrefee(String showPrefee) {
        this.showPrefee = showPrefee;
    }

    public String getGreetings() {
        return greetings;
    }

    public void setGreetings(String greetings) {
        this.greetings = greetings;
    }

    public String getEmotionalWordsInIndex() {
        return emotionalWordsInIndex;
    }

    public void setEmotionalWordsInIndex(String emotionalWordsInIndex) {
        this.emotionalWordsInIndex = emotionalWordsInIndex;
    }

    public String getEmotionalWordsInLogin() {
        return emotionalWordsInLogin;
    }

    public void setEmotionalWordsInLogin(String emotionalWordsInLogin) {
        this.emotionalWordsInLogin = emotionalWordsInLogin;
    }

    public String getAccountInfoUuid() {
        return accountInfoUuid;
    }

    public void setAccountInfoUuid(String accountInfoUuid) {
        this.accountInfoUuid = accountInfoUuid;
    }

    public String getNeedFee() {
        return needFee;
    }

    public void setNeedFee(String needFee) {
        this.needFee = needFee;
    }

    public String getFeeExpirationTime() {
        return feeExpirationTime;
    }

    public void setFeeExpirationTime(String feeExpirationTime) {
        this.feeExpirationTime = feeExpirationTime;
    }

    public String getHaveDays() {
        return haveDays;
    }

    public void setHaveDays(String haveDays) {
        this.haveDays = haveDays;
    }

    public int getUserState() {
        return userState;
    }

    public void setUserState(int userState) {
        this.userState = userState;
    }

    public boolean isCanPrefund() {
        return canPrefund;
    }

    public void setCanPrefund(boolean canPrefund) {
        this.canPrefund = canPrefund;
    }

    public boolean isIsExpiring() {
        return isExpiring;
    }

    public void setIsExpiring(boolean isExpiring) {
        this.isExpiring = isExpiring;
    }

    public String getExpireTip() {
        return expireTip;
    }

    public void setExpireTip(String expireTip) {
        this.expireTip = expireTip;
    }
}
