package it.de.seven.fate.moa.util;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolverSystem;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;

public final class DeploymentUtil {

    private static final MavenResolverSystem RESOLVER = Maven.resolver();
    private static final PomEquippedResolveStage DEPENDENCIES = RESOLVER.loadPomFromFile("pom.xml").importRuntimeAndTestDependencies();

    private DeploymentUtil() {
    }

    public static WebArchive createDeployment(Class... classes) {

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackages(true, "de.seven.fate.moa.dao") //
                .addPackages(true, "de.seven.fate.moa.dto") //
                .addPackages(true, "de.seven.fate.moa.model") //
                .addPackages(true, "de.seven.fate.moa.resources") //
                .addClasses(classes) //
                .addAsLibraries(DEPENDENCIES.resolve("commons-codec:commons-codec").withTransitivity().asFile()) //
                .addAsLibraries(DEPENDENCIES.resolve("org.apache.commons:commons-lang3").withTransitivity().asFile()) //
                .addAsLibraries(DEPENDENCIES.resolve("de.seven.fate:model-builder").withTransitivity().asFile()) //
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml") //
                .addAsWebInfResource("test-ds.xml") //
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml") //
                .addAsWebInfResource("WEB-INF/web.xml", "web.xml");

    }

}