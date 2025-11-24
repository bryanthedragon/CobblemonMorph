/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Module
 */
package org.graalvm.nativeimage.impl;

import java.util.Collection;
import java.util.Locale;
import org.graalvm.nativeimage.impl.ConfigurationCondition;

public interface RuntimeResourceSupport {
    public void addResources(ConfigurationCondition var1, String var2);

    public void injectResource(Module var1, String var2, byte[] var3);

    public void ignoreResources(ConfigurationCondition var1, String var2);

    public void addResourceBundles(ConfigurationCondition var1, String var2);

    public void addResourceBundles(ConfigurationCondition var1, String var2, Collection<Locale> var3);
}

