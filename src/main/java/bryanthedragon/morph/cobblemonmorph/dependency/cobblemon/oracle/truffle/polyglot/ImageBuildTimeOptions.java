
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.Option;
import org.graalvm.options.OptionCategory;
import org.graalvm.options.OptionKey;
import org.graalvm.options.OptionType;

@Option.Group(value={"image-build-time"})
final class ImageBuildTimeOptions {
    static final String PREINITIALIZE_CONTEXTS_NAME = "PreinitializeContexts";
    @Option(name="PreinitializeContexts", category=OptionCategory.EXPERT, help="Pre-initialize language contexts for the given languages.")
    static final OptionKey<String> PreinitializeContexts = new OptionKey<String>(null, OptionType.defaultType(String.class));
    static final String PREINITIALIZE_CONTEXTS_WITH_NATIVE_NAME = "PreinitializeContextsWithNative";
    @Option(name="PreinitializeContextsWithNative", category=OptionCategory.EXPERT, help="Pre-initialize language contexts with native access privileges.")
    static final OptionKey<Boolean> PreinitializeContextsWithNative = new OptionKey<Boolean>(false);
    static final String DISABLE_PRIVILEGES_NAME = "DisablePrivileges";
    @Option(name="DisablePrivileges", category=OptionCategory.EXPERT, help="Disable Context privileges so the related code can be excluded from the image.")
    static final OptionKey<String> DisablePrivileges = new OptionKey<String>("");

    ImageBuildTimeOptions() {
    }

    static String get(String optionName) {
        String property = ImageBuildTimeOptions.getPropertyName(optionName);
        return System.getProperty(property, "");
    }

    static Boolean getBoolean(String optionName) {
        String property = ImageBuildTimeOptions.getPropertyName(optionName);
        return Boolean.getBoolean(property);
    }

    private static String getPropertyName(String optionName) {
        return "polyglot.image-build-time." + optionName;
    }
}

