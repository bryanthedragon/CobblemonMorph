
package org.graalvm.nativeimage.impl;

import java.util.EnumSet;
import org.graalvm.nativeimage.RuntimeOptions;
import org.graalvm.options.OptionDescriptors;

public interface RuntimeOptionsSupport {
    public void set(String var1, Object var2);

    public <T> T get(String var1);

    public OptionDescriptors getOptions(EnumSet<RuntimeOptions.OptionClass> var1);
}

