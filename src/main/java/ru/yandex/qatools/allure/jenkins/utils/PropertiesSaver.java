package ru.yandex.qatools.allure.jenkins.utils;

import hudson.FilePath;
import hudson.remoting.VirtualChannel;
import jenkins.security.Roles;
import org.jenkinsci.remoting.RoleChecker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * eroshenkoam
 * 7/16/14
 */
public class PropertiesSaver implements FilePath.FileCallable {

    private Map<String, String> map;

    private String comment;

    public PropertiesSaver(Map<String, String> map, String comment) {
        this.comment = comment;
        this.map = map;
    }

    @Override
    public void checkRoles(RoleChecker roleChecker) throws SecurityException {
        roleChecker.check(this, Roles.SLAVE);
    }

    @Override
    public Object invoke(File f, VirtualChannel channel) throws IOException, InterruptedException {
        Properties properties = new Properties();
        properties.putAll(map);
        properties.store(new FileOutputStream(f), comment);
        return properties;
    }

}
