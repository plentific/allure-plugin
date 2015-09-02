package ru.yandex.qatools.allure.jenkins.utils;

import hudson.remoting.Callable;
import jenkins.security.Roles;
import org.jenkinsci.remoting.RoleChecker;
import ru.yandex.qatools.allure.jenkins.AllureReportPlugin;

import java.io.IOException;

/**
 * @author Artem Eroshenko <eroshenkoam@yandex-team.ru>
 */
public class PropertiesSettter implements Callable<String, IOException> {

    private final String issuePattern;

    private final String tmsPattern;

    public PropertiesSettter (String issuePattern, String tmsPattern) {
        this.issuePattern = issuePattern;
        this.tmsPattern = tmsPattern;
    }

    @Override
    public void checkRoles(RoleChecker roleChecker) throws SecurityException {
        roleChecker.check(this, Roles.SLAVE);
    }

    public String call() throws IOException {

        // Jenkins (non default) settings override Allure settings
        if (!AllureReportPlugin.DEFAULT_URL_PATTERN.equals(issuePattern)) {
            System.setProperty("allure.issues.tracker.pattern", issuePattern);
        }

        // Jenkins (non default) settings override Allure settings
        if (!AllureReportPlugin.DEFAULT_URL_PATTERN.equals(tmsPattern)) {
            System.setProperty("allure.tests.management.pattern", tmsPattern);
        }
        return "";
    }

};
