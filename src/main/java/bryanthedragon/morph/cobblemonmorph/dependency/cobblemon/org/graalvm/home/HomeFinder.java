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
      } else {
         Class<?> lookupClass = HomeFinder.class;
         ModuleLayer moduleLayer = lookupClass.getModule().getLayer();
         Iterable<HomeFinder> services;
         if (moduleLayer != null) {
            services = ServiceLoader.load(moduleLayer, HomeFinder.class);
         } else {
            services = ServiceLoader.load(HomeFinder.class, lookupClass.getClassLoader());
         }

         Iterator<HomeFinder> iterator = services.iterator();
         if (!iterator.hasNext()) {
            Iterable<HomeFinder> var6 = ServiceLoader.load(HomeFinder.class);
            iterator = var6.iterator();
         }

         try {
            return iterator.next();
         } catch (NoSuchElementException var5) {
            throw new IllegalStateException("No implementation of " + HomeFinder.class.getName() + " could be found");
         }
      }
   }
}
