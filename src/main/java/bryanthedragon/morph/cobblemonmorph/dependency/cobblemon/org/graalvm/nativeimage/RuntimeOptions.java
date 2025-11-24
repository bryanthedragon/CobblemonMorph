
package org.graalvm.nativeimage;

import java.util.EnumSet;
import org.graalvm.nativeimage.ImageSingletons;
import org.graalvm.nativeimage.impl.RuntimeOptionsSupport;
import org.graalvm.options.OptionDescriptors;

public final class RuntimeOptions {
    private RuntimeOptions() {
    }

    public static void set(String optionName, Object value2) {
        ImageSingletons.lookup(RuntimeOptionsSupport.class).set(optionName, value2);
    }

    public static <T> T get(String optionName) {
        return ImageSingletons.lookup(RuntimeOptionsSupport.class).get(optionName);
    }

    public static OptionDescriptors getOptions(EnumSet<OptionClass> classes) {
        return ImageSingletons.lookup(RuntimeOptionsSupport.class).getOptions(classes);
    }

    public static OptionDescriptors getOptions() {
        return RuntimeOptions.getOptions(EnumSet.allOf(OptionClass.class));
    }

    public static enum OptionClass {
        VM,
        Compiler;

    }
}

