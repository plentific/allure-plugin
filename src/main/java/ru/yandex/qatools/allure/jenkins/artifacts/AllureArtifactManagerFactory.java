package ru.yandex.qatools.allure.jenkins.artifacts;

import hudson.Extension;
import hudson.model.Run;
import jenkins.model.ArtifactManager;
import jenkins.model.ArtifactManagerFactory;
import jenkins.model.ArtifactManagerFactoryDescriptor;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * @author ehborisov
 */
public final class AllureArtifactManagerFactory extends ArtifactManagerFactory {

    @Override
    public ArtifactManager managerFor(Run<?,?> build) {
        return new AllureArtifactManager(build);
    }

    @Extension
    public static final class DescriptorImpl extends ArtifactManagerFactoryDescriptor {

        @Override
        public String getDisplayName() {
            return "Allure artifact manager factory";
        }

    }

}