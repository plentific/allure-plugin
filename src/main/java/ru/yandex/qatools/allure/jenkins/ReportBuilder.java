package ru.yandex.qatools.allure.jenkins;

import hudson.EnvVars;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.TaskListener;
import hudson.util.ArgumentListBuilder;
import ru.yandex.qatools.allure.jenkins.tools.AllureCommandlineInstallation;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;

/**
 * @author charlie (Dmitry Baev).
 */
public class ReportBuilder {

    private final FilePath workspace;

    private final Launcher launcher;

    private final TaskListener listener;

    private final EnvVars envVars;

    private final AllureCommandlineInstallation commandline;

    public ReportBuilder(@Nonnull Launcher launcher, @Nonnull TaskListener listener, @Nonnull FilePath workspace,
                         @Nonnull EnvVars envVars, @Nonnull AllureCommandlineInstallation commandline) {
        this.workspace = workspace;
        this.launcher = launcher;
        this.listener = listener;
        this.envVars = envVars;
        this.commandline = commandline;
    }

    public ArgumentListBuilder getArguments(@Nonnull List<FilePath> resultsPaths, @Nonnull FilePath reportPath) //NOSONAR
            throws IOException, InterruptedException {
        ArgumentListBuilder arguments = new ArgumentListBuilder();
        arguments.add(commandline.getExecutable(launcher));
        arguments.add("generate");
        for (FilePath resultsPath : resultsPaths) {
            arguments.addQuoted(resultsPath.getRemote());
        }
        arguments.add("-o");
        arguments.addQuoted(reportPath.getRemote());
        return arguments;
    }

    public int build(@Nonnull List<FilePath> resultsPaths, @Nonnull FilePath reportPath) //NOSONAR
            throws IOException, InterruptedException {
        return launcher.launch().cmds(getArguments(resultsPaths, reportPath))
                .envs(envVars).stdout(listener).pwd(workspace).join();
    }
}
