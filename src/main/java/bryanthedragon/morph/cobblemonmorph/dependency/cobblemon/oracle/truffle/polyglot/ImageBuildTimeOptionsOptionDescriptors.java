
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.polyglot.ImageBuildTimeOptions;
import java.util.Arrays;
import java.util.Iterator;
import org.graalvm.options.OptionCategory;
import org.graalvm.options.OptionDescriptor;
import org.graalvm.options.OptionDescriptors;
import org.graalvm.options.OptionStability;

@GeneratedBy(value=ImageBuildTimeOptions.class)
final class ImageBuildTimeOptionsOptionDescriptors
implements OptionDescriptors {
    ImageBuildTimeOptionsOptionDescriptors() {
    }

    @Override
    public OptionDescriptor get(String optionName) {
        switch (optionName) {
            case "image-build-time.DisablePrivileges": {
                return OptionDescriptor.newBuilder(ImageBuildTimeOptions.DisablePrivileges, "image-build-time.DisablePrivileges").deprecated(false).help("Disable Context privileges so the related code can be excluded from the image.").usageSyntax("").category(OptionCategory.EXPERT).stability(OptionStability.EXPERIMENTAL).build();
            }
            case "image-build-time.PreinitializeContexts": {
                return OptionDescriptor.newBuilder(ImageBuildTimeOptions.PreinitializeContexts, "image-build-time.PreinitializeContexts").deprecated(false).help("Pre-initialize language contexts for the given languages.").usageSyntax("").category(OptionCategory.EXPERT).stability(OptionStability.EXPERIMENTAL).build();
            }
            case "image-build-time.PreinitializeContextsWithNative": {
                return OptionDescriptor.newBuilder(ImageBuildTimeOptions.PreinitializeContextsWithNative, "image-build-time.PreinitializeContextsWithNative").deprecated(false).help("Pre-initialize language contexts with native access privileges.").usageSyntax("").category(OptionCategory.EXPERT).stability(OptionStability.EXPERIMENTAL).build();
            }
        }
        return null;
    }

    @Override
    public Iterator<OptionDescriptor> iterator() {
        return Arrays.asList(OptionDescriptor.newBuilder(ImageBuildTimeOptions.DisablePrivileges, "image-build-time.DisablePrivileges").deprecated(false).help("Disable Context privileges so the related code can be excluded from the image.").usageSyntax("").category(OptionCategory.EXPERT).stability(OptionStability.EXPERIMENTAL).build(), OptionDescriptor.newBuilder(ImageBuildTimeOptions.PreinitializeContexts, "image-build-time.PreinitializeContexts").deprecated(false).help("Pre-initialize language contexts for the given languages.").usageSyntax("").category(OptionCategory.EXPERT).stability(OptionStability.EXPERIMENTAL).build(), OptionDescriptor.newBuilder(ImageBuildTimeOptions.PreinitializeContextsWithNative, "image-build-time.PreinitializeContextsWithNative").deprecated(false).help("Pre-initialize language contexts with native access privileges.").usageSyntax("").category(OptionCategory.EXPERT).stability(OptionStability.EXPERIMENTAL).build()).iterator();
    }
}

