package ru.yandex.qatools.allure.jenkins.dsl;

import javaposse.jobdsl.dsl.Context;
import ru.yandex.qatools.allure.jenkins.config.AllureReportConfig;
import ru.yandex.qatools.allure.jenkins.config.ReportBuildPolicy;

/**
 * @author Marat Mavlutov <mavlyutov@yandex-team.ru>
 */
class AllureReportPublisherContext implements Context {

    public static final String FAILURE_POLICY = "FAILURE";

    private AllureReportConfig config;

    public AllureReportPublisherContext(String resultsPattern) {
        config = new AllureReportConfig(null, null, resultsPattern, ReportBuildPolicy.ALWAYS, true);
    }

    public AllureReportConfig getConfig() {
        return config;
    }

    public void buildFor(String buildPolicy) {
        String policy = buildPolicy.equals(FAILURE_POLICY) ? ReportBuildPolicy.UNSUCCESSFUL.getValue() : buildPolicy;
        getConfig().setReportBuildPolicy(ReportBuildPolicy.valueOf(policy));
    }

    public void jdk(String jdk) {
        this.getConfig().setJdk(jdk);
    }

    public void commandline(String commandline) {
        getConfig().setCommandline(commandline);
    }

    public void includeProperties(boolean includeProperties) {
        getConfig().setIncludeProperties(includeProperties);
    }
}
