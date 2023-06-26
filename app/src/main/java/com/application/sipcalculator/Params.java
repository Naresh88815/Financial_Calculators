package com.application.sipcalculator;

public class Params {
    public static final int DB_VERSION=1;
    public static final String DB_NAME="history";
    public static final String SIP_TABLE_NAME="sip";
    public static final String LOAN_TABLE_NAME="loan";

    //Keys of the SIP table
    public static final String KEY_SIP_ID="sipId";
    public static final String KEY_SIP_ACTIVITY_DATE="sipActivityDate";
    public static final String KEY_INVESTMENT="sipInvestment";
    public static final String KEY_RATE="sipRate";
    public static final String KEY_TIME="sipTime";
    public static final String KEY_TOTAL_INVESTMENT="sipTotalInvestment";
    public static final String KEY_TOTAL_RETURN="sipTotalReturn";
    public static final String KEY_RETURN_AMOUNT="sipReturnAmount";

    //Keys of the loan table
    public static final String KEY_LOAN_ID="loanId";
    public static final String KEY_LOAN_ACTIVITY_DATE="loanActivityDate";
    public static final String KEY_lOAN_AMOUNT="loanAmount";
    public static final String KEY_LOAN_RATE="loanRate";
    public static final String KEY_LOAN_TIME="loanTime";
    public static final String KEY_EMI="loanEMI";
    public static final String KEY_INTEREST_PAYABLE="loanInterestPayable";
    public static final String KEY_LOAN_TOTAL_AMOUNT="loanTotalAmount";


}
