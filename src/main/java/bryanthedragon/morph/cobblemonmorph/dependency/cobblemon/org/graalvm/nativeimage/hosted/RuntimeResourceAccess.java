/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Module
 */
package org.graalvm.nativeimage.hosted;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;
import org.graalvm.nativeimage.ImageSingletons;
import org.graalvm.nativeimage.Platform;
import org.graalvm.nativeimage.Platforms;
import org.graalvm.nativeimage.impl.ConfigurationCondition;
import org.graalvm.nativeimage.impl.RuntimeResourceSupport;

@Platforms(value={Platform.HOSTED_ONLY.class})
public final class RuntimeResourceAccess {
    public static void addResource(Module module, String resourcePath) {
        Objects.requireNonNull(module);
        Objects.requireNonNull(resourcePath);
        ImageSingletons.lookup(RuntimeResourceSupport.class).addResources(ConfigurationCondition.alwaysTrue(), RuntimeResourceAccess.withModuleName(module, Pattern.quote(resourcePath)));
    }

    public static void addResource(Module module, String resourcePath, byte[] resourceContent) {
        Objects.requireNonNull(module);
        Objects.requireNonNull(resourcePath);
        Objects.requireNonNull(resourceContent);
        ImageSingletons.lookup(RuntimeResourceSupport.class).injectResource(module, resourcePath, resourceContent);
    }

    public static void addResourceBundle(Module module, String baseBundleName, Locale[] locales) {
        Objects.requireNonNull(locales);
        ImageSingletons.lookup(RuntimeResourceSupport.class).addResourceBundles(ConfigurationCondition.alwaysTrue(), RuntimeResourceAccess.withModuleName(module, baseBundleName), Arrays.asList(locales));
    }

    public static void addResourceBundle(Module module, String bundleName) {
        ImageSingletons.lookup(RuntimeResourceSupport.class).addResourceBundles(ConfigurationCondition.alwaysTrue(), RuntimeResourceAccess.withModuleName(module, bundleName));
    }

    private static String withModuleName(Module module, String str) {
        Objects.requireNonNull(module);
        Objects.requireNonNull(str);
        return (module.isNamed() ? module.getName() : "ALL-UNNAMED") + ":" + str;
    }

    private RuntimeResourceAccess() {
    }
}

