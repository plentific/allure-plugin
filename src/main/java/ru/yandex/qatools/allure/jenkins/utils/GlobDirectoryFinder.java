package ru.yandex.qatools.allure.jenkins.utils;

import hudson.FilePath;
import hudson.remoting.VirtualChannel;
import jenkins.security.Roles;
import org.apache.tools.ant.DirectoryScanner;
import org.jenkinsci.remoting.RoleChecker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * eroshenkoam
 * 7/15/14
 */
public class GlobDirectoryFinder implements FilePath.FileCallable<List<FilePath>> {

    public static final String DEFAULT_SEPARATOR = ";";

    private String[] glob;

    public GlobDirectoryFinder(String glob) {
        this(glob, DEFAULT_SEPARATOR);
    }

    public GlobDirectoryFinder(String glob, String split) {
        this(glob.split(split));
    }

    public GlobDirectoryFinder(String[] glob) {
        this.glob = glob;
    }

    @Override
    public void checkRoles(RoleChecker roleChecker) throws SecurityException {
        roleChecker.check(this, Roles.SLAVE);
    }

    @Override
    public List<FilePath> invoke(File file, VirtualChannel channel) throws IOException, InterruptedException {
        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setBasedir(file);
        scanner.setIncludes(glob);
        scanner.setCaseSensitive(false);
        scanner.scan();

        String[] paths = scanner.getIncludedDirectories();
        List<FilePath> directories = new ArrayList<>();

        for (String path : paths) {
            directories.add(new FilePath(new File(file, path)));
        }
        return directories;
    }

    public static GlobDirectoryFinder findDirectoriesByGlob(String glob) {
        return new GlobDirectoryFinder(glob);
    }

}
