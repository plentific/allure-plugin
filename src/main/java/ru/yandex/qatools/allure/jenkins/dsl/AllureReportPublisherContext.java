package ru.yandex.qatools.allure.jenkins.dsl;

import javaposse.jobdsl.dsl.Context;
import ru.yandex.qatools.allure.jenkins.config.ReportBuildPolicy;
import ru.yandex.qatools.allure.jenkins.config.ReportVersionPolicy;

/**
 * @author Marat Mavlutov <mavlyutov@yandex-team.ru>
 */
class AllureReportPublisherContext implements Context {

    public static final String FAILURE_POLICY = "FAILURE";

    private String resultsPattern;

    private String reportVersionCustom;

    private ReportVersionPolicy reportVersionPolicy;

    private ReportBuildPolicy reportBuildPolicy;

    private Boolean includeProperties;

    public AllureReportPublisherContext(String resultsPattern) {
        this.resultsPattern = resultsPattern;
        this.reportVersionCustom = null;
        this.reportBuildPolicy = ReportBuildPolicy.ALWAYS;
        this.reportVersionPolicy = ReportVersionPolicy.DEFAULT;
        this.includeProperties = true;
    }

    public String getResultsPattern() {
        return resultsPattern;
    }

    public void buildFor(String buildPolicy) {
        String policy = buildPolicy.equals(FAILURE_POLICY) ? ReportBuildPolicy.UNSUCCESSFUL.getValue() : buildPolicy;
        try {
            reportBuildPolicy = ReportBuildPolicy.valueOf(policy);
        } catch (IllegalArgumentException e) {
            reportBuildPolicy = ReportBuildPolicy.ALWAYS;
        }
    }


    public void reportVersion(String version) {
        this.reportVersionPolicy = ReportVersionPolicy.CUSTOM;
        this.reportVersionCustom = version;
    }

    public void includeProperties(boolean includeProperties) {
        this.includeProperties = includeProperties;
    }

    public String getReportVersionCustom() {
        return reportVersionCustom;
    }

    public ReportBuildPolicy getReportBuildPolicy() {
        return reportBuildPolicy;
    }

    public boolean getIncludeProperties() {
        return includeProperties;
    }

    public ReportVersionPolicy getReportVersionPolicy() {
        return reportVersionPolicy;
    }
}
