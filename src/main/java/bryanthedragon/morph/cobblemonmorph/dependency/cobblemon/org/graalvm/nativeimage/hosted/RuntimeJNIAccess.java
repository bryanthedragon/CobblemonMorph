
package org.graalvm.nativeimage.hosted;

import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import org.graalvm.nativeimage.ImageSingletons;
import org.graalvm.nativeimage.Platform;
import org.graalvm.nativeimage.Platforms;
import org.graalvm.nativeimage.impl.ConfigurationCondition;
import org.graalvm.nativeimage.impl.RuntimeJNIAccessSupport;

@Platforms(value={Platform.HOSTED_ONLY.class})
public final class RuntimeJNIAccess {
    public static void register(Class<?> ... classes) {
        ImageSingletons.lookup(RuntimeJNIAccessSupport.class).register(ConfigurationCondition.alwaysTrue(), classes);
    }

    public static void register(Executable ... methods) {
        ImageSingletons.lookup(RuntimeJNIAccessSupport.class).register(ConfigurationCondition.alwaysTrue(), false, methods);
    }

    public static void register(Field ... fields) {
        ImageSingletons.lookup(RuntimeJNIAccessSupport.class).register(ConfigurationCondition.alwaysTrue(), false, fields);
    }

    private RuntimeJNIAccess() {
    }
}

