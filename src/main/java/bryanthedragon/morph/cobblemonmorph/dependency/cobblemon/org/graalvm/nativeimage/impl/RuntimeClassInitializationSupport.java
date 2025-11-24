
package org.graalvm.nativeimage.impl;

public interface RuntimeClassInitializationSupport {
    public void initializeAtRunTime(String var1, String var2);

    public void initializeAtBuildTime(String var1, String var2);

    public void rerunInitialization(String var1, String var2);

    public void initializeAtRunTime(Class<?> var1, String var2);

    public void rerunInitialization(Class<?> var1, String var2);

    public void initializeAtBuildTime(Class<?> var1, String var2);
}

