package org.graalvm.nativeimage.impl;

import java.util.Collection;
import java.util.Locale;

public interface RuntimeResourceSupport {
   void addResources(ConfigurationCondition condition, String pattern);

   void injectResource(Module module, String resourcePath, byte[] resourceContent);

   void ignoreResources(ConfigurationCondition condition, String pattern);

   void addResourceBundles(ConfigurationCondition condition, String name);

   void addResourceBundles(ConfigurationCondition condition, String basename, Collection<Locale> locales);
}
