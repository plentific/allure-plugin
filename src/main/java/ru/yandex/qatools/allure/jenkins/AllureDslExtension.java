package ru.yandex.qatools.allure.jenkins;

import hudson.Extension;
import javaposse.jobdsl.dsl.Context;
import javaposse.jobdsl.dsl.helpers.publisher.PublisherContext;
import javaposse.jobdsl.plugin.ContextExtensionPoint;
import javaposse.jobdsl.plugin.DslExtensionMethod;
import ru.yandex.qatools.allure.jenkins.config.AllureReportConfig;
import ru.yandex.qatools.allure.jenkins.config.ReportBuildPolicy;
import ru.yandex.qatools.allure.jenkins.config.ReportVersionPolicy;

/**
 * Created by mavlyutov on 03/08/15.
 */
@Extension(optional = true)
public class AllureDslExtension extends ContextExtensionPoint {

    @DslExtensionMethod(context = PublisherContext.class)
    public Object allure(String resultsPattern, Runnable closure) {

        AllureReportPublisherContext context = new AllureReportPublisherContext(resultsPattern);
        executeInContext(closure, context);

        AllureReportConfig config = new AllureReportConfig(
            context.getResultsPattern(),
            context.getReportVersionCustom(),
            context.getReportVersionPolicy(),
            context.getReportBuildPolicy(),
            context.getIncludeProperties()
        );

        return new AllureReportPublisher(config);
    }

    @DslExtensionMethod(context = PublisherContext.class)
    public Object allure(String resultsPattern) {
        return new AllureReportPublisher(AllureReportConfig.newInstance(resultsPattern, true));
    }
}

class AllureReportPublisherContext implements Context {

    private String resultsPattern;

    private String reportVersionCustom;

    private ReportBuildPolicy reportBuildPolicy;

    private ReportVersionPolicy reportVersionPolicy;

    private Boolean includeProperties;

    public AllureReportPublisherContext(String resultsPattern) {
        this.resultsPattern = resultsPattern;
        this.reportVersionCustom = null;
        this.reportVersionPolicy = ReportVersionPolicy.DEFAULT;
        this.reportBuildPolicy = ReportBuildPolicy.ALWAYS;
        this.includeProperties = true;
    }

    public String getResultsPattern() {
        return resultsPattern;
    }

    public void buildFor(String buildPolicy) {
        switch (buildPolicy.trim().toUpperCase()) {
            case "UNSTABLE":
                reportBuildPolicy = ReportBuildPolicy.UNSTABLE;
                break;
            case "FAILURE":
                reportBuildPolicy = ReportBuildPolicy.UNSUCCESSFUL;
                break;
            default:
                reportBuildPolicy = ReportBuildPolicy.ALWAYS;
                break;
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

    public ReportVersionPolicy getReportVersionPolicy() {
        return reportVersionPolicy;
    }

    public ReportBuildPolicy getReportBuildPolicy() {
        return reportBuildPolicy;
    }

    public boolean getIncludeProperties() {
        return includeProperties;
    }

}
