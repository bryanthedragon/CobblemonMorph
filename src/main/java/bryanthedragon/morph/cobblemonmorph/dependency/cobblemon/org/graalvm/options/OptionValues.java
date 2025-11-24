
package org.graalvm.options;

import org.graalvm.options.OptionDescriptor;
import org.graalvm.options.OptionDescriptors;
import org.graalvm.options.OptionKey;

public interface OptionValues {
    public OptionDescriptors getDescriptors();

    @Deprecated(since="22.0")
    default public <T> void set(OptionKey<T> optionKey, T value2) {
        throw new UnsupportedOperationException();
    }

    public <T> T get(OptionKey<T> var1);

    public boolean hasBeenSet(OptionKey<?> var1);

    default public boolean hasSetOptions() {
        for (OptionDescriptor descriptor : this.getDescriptors()) {
            if (!this.hasBeenSet(descriptor.getKey())) continue;
            return true;
        }
        return false;
    }
}

