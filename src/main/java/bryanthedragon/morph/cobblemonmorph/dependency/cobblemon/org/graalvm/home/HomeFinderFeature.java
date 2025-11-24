
package org.graalvm.home;

import org.graalvm.home.HomeFinder;
import org.graalvm.home.impl.DefaultHomeFinder;
import org.graalvm.nativeimage.ImageSingletons;
import org.graalvm.nativeimage.hosted.Feature;

class HomeFinderFeature
implements Feature {
    HomeFinderFeature() {
    }

    @Override
    public String getURL() {
        return "https://github.com/oracle/graal/blob/master/sdk/src/org.graalvm.home/src/org/graalvm/home/HomeFinder.java";
    }

    @Override
    public String getDescription() {
        return "Finds GraalVM paths and its version number";
    }

    @Override
    public void afterRegistration(Feature.AfterRegistrationAccess access) {
        ImageSingletons.add(HomeFinder.class, new DefaultHomeFinder());
    }
}

