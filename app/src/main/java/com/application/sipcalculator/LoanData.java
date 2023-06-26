package com.application.sipcalculator;

public class LoanData {
    private int loanId;
    private String loanActivityDate;
    private String loanAmount;
    private String loanRate;
    private String loanTime;
    private String loanEMI;
    private String loanInterestPayable;
    private String loanTotalAmount;

    public LoanData(int loanId, String loanActivityDate, String loanAmount, String loanRate, String loanTime, String loanEMI, String loanInterestPayable, String loanTotalAmount) {
        this.loanId=loanId;
        this.loanActivityDate=loanActivityDate;
        this.loanAmount=loanAmount;
        this.loanRate=loanRate;
        this.loanTime=loanTime;
        this.loanEMI=loanEMI;
        this.loanInterestPayable=loanInterestPayable;
        this.loanTotalAmount=loanTotalAmount;
    }

    public LoanData() {

    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public String getLoanActivityDate() {
        return loanActivityDate;
    }

    public void setLoanActivityDate(String loanActivityDate) {
        this.loanActivityDate = loanActivityDate;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(String loanRate) {
        this.loanRate = loanRate;
    }

    public String getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(String loanTime) {
        this.loanTime = loanTime;
    }

    public String getLoanEMI() {
        return loanEMI;
    }

    public void setLoanEMI(String loanEMI) {
        this.loanEMI = loanEMI;
    }

    public String getLoanInterestPayable() {
        return loanInterestPayable;
    }

    public void setLoanInterestPayable(String loanInterestPayable) {
        this.loanInterestPayable = loanInterestPayable;
    }

    public String getLoanTotalAmount() {
        return loanTotalAmount;
    }

    public void setLoanTotalAmount(String loanTotalAmount) {
        this.loanTotalAmount = loanTotalAmount;
    }
}
