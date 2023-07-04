package com.application.sipcalculator;

public class SIPData {
    private int sipId;
    private String sipActivityDate;
    private String sipInvestment;
    private String sipRate;
    private String sipTime;
    private String sipTotalInvestment;
    private String sipTotalReturn;
    private String sipReturnAmount;



    public SIPData(int sipId, String sipActivityDate, String sipInvestment, String sipRate, String sipTime, String sipTotalInvestment, String sipTotalReturn, String sipReturnAmount) {
        this.sipId = sipId;
        this.sipActivityDate = sipActivityDate;
        this.sipInvestment = sipInvestment;
        this.sipRate = sipRate;
        this.sipTime = sipTime;
        this.sipTotalInvestment = sipTotalInvestment;
        this.sipTotalReturn = sipTotalReturn;
        this.sipReturnAmount = sipReturnAmount;
    }

    public SIPData() {

    }

    public SIPData(String sipActivityDate, String investment, String rate, String time, String totalInvestment, String totalReturn, String returnAmount) {
        this.sipActivityDate = sipActivityDate;
        this.sipInvestment = investment;
        this.sipRate = rate;
        this.sipTime = time;
        this.sipTotalInvestment = totalInvestment;
        this.sipTotalReturn = totalReturn;
        this.sipReturnAmount = returnAmount;
    }

    public int getSipId() {
        return sipId;
    }

    public void setSipId(int sipId) {
        this.sipId = sipId;
    }

    public String getSipActivityDate() {
        return sipActivityDate;
    }

    public void setSipActivityDate(String sipActivityDate) {
        this.sipActivityDate = sipActivityDate;
    }

    public String getSipInvestment() {
        return sipInvestment;
    }

    public void setSipInvestment(String sipInvestment) {
        this.sipInvestment = sipInvestment;
    }

    public String getSipRate() {
        return sipRate;
    }

    public void setSipRate(String sipRate) {
        this.sipRate = sipRate;
    }

    public String getSipTime() {
        return sipTime;
    }

    public void setSipTime(String sipTime) {
        this.sipTime = sipTime;
    }

    public String getSipTotalInvestment() {
        return sipTotalInvestment;
    }

    public void setSipTotalInvestment(String sipTotalInvestment) {
        this.sipTotalInvestment = sipTotalInvestment;
    }

    public String getSipTotalReturn() {
        return sipTotalReturn;
    }

    public void setSipTotalReturn(String sipTotalReturn) {
        this.sipTotalReturn = sipTotalReturn;
    }

    public String getSipReturnAmount() {
        return sipReturnAmount;
    }

    public void setSipReturnAmount(String sipReturnAmount) {
        this.sipReturnAmount = sipReturnAmount;
    }
}