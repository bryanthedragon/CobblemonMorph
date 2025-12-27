package org.graalvm.nativeimage.impl;

import java.util.EnumSet;
import org.graalvm.nativeimage.RuntimeOptions;
import org.graalvm.options.OptionDescriptors;

public interface RuntimeOptionsSupport {
   void set(String optionName, Object value);

   <T> T get(String optionName);

   OptionDescriptors getOptions(EnumSet<RuntimeOptions.OptionClass> classes);
}
