/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.ModuleLayer
 */
package org.graalvm.home;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.ServiceLoader;
import org.graalvm.nativeimage.ImageInfo;
import org.graalvm.nativeimage.ImageSingletons;

public abstract class HomeFinder {
    public abstract Path getHomeFolder();

    public abstract String getVersion();

    public abstract Map<String, Path> getLanguageHomes();

    public abstract Map<String, Path> getToolHomes();

    public static HomeFinder getInstance() {
        if (ImageInfo.inImageCode() && ImageSingletons.contains(HomeFinder.class)) {
            return ImageSingletons.lookup(HomeFinder.class);
        }
        Class<HomeFinder> lookupClass = HomeFinder.class;
        ModuleLayer moduleLayer = lookupClass.getModule().getLayer();
        ServiceLoader<HomeFinder> services = moduleLayer != null ? ServiceLoader.load((ModuleLayer)moduleLayer, HomeFinder.class) : ServiceLoader.load(HomeFinder.class, lookupClass.getClassLoader());
        Iterator iterator = services.iterator();
        if (!iterator.hasNext()) {
            services = ServiceLoader.load(HomeFinder.class);
            iterator = services.iterator();
        }
        try {
            return (HomeFinder)iterator.next();
        }
        catch (NoSuchElementException e) {
            throw new IllegalStateException("No implementation of " + HomeFinder.class.getName() + " could be found");
        }
    }
}

