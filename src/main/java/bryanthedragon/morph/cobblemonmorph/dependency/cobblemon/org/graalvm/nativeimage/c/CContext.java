
package org.graalvm.nativeimage.c;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collections;
import java.util.List;
import org.graalvm.nativeimage.Platform;
import org.graalvm.nativeimage.Platforms;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
@Platforms(value={Platform.HOSTED_ONLY.class})
public @interface CContext {
    public Class<? extends Directives> value();

    public static interface Directives {
        default public boolean isInConfiguration() {
            return true;
        }

        default public List<String> getHeaderFiles() {
            return Collections.emptyList();
        }

        default public List<String> getMacroDefinitions() {
            return Collections.emptyList();
        }

        default public List<String> getOptions() {
            return Collections.emptyList();
        }

        default public List<String> getLibraries() {
            return Collections.emptyList();
        }

        default public List<String> getLibraryPaths() {
            return Collections.emptyList();
        }
    }
}

