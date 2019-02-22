package com.jenking.xiaoyunhui.models.main.scoreForExcel;

public class AllScoreIntegralSum {
    private String teamName;
    private String integralSum;

    public AllScoreIntegralSum(String teamName, String integralSum) {
        this.teamName = teamName;
        this.integralSum = integralSum;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getIntegralSum() {
        return integralSum;
    }

    public void setIntegralSum(String integralSum) {
        this.integralSum = integralSum;
    }

    @Override
    public String toString() {
        return "AllScoreIntegralSum{" +
                "teamName='" + teamName + '\'' +
                ", integralSum='" + integralSum + '\'' +
                '}';
    }
}
