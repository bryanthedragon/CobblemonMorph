
package org.graalvm.options;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.graalvm.options.OptionDescriptor;
import org.graalvm.options.OptionDescriptorsMap;
import org.graalvm.options.UnionOptionDescriptors;

public interface OptionDescriptors
extends Iterable<OptionDescriptor> {
    public static final OptionDescriptors EMPTY = new OptionDescriptors(){

        @Override
        public Iterator<OptionDescriptor> iterator() {
            return Collections.emptyList().iterator();
        }

        @Override
        public OptionDescriptor get(String key) {
            return null;
        }
    };

    public OptionDescriptor get(String var1);

    public static OptionDescriptors createUnion(OptionDescriptors ... descriptors) {
        if (descriptors.length == 0) {
            return EMPTY;
        }
        if (descriptors.length == 1) {
            return descriptors[0];
        }
        OptionDescriptors singleNonEmpty = null;
        for (int i = 0; i < descriptors.length; ++i) {
            OptionDescriptors d = descriptors[i];
            if (d == EMPTY) continue;
            if (singleNonEmpty == null) {
                singleNonEmpty = d;
                continue;
            }
            return new UnionOptionDescriptors(descriptors);
        }
        if (singleNonEmpty == null) {
            return EMPTY;
        }
        return singleNonEmpty;
    }

    @Override
    public Iterator<OptionDescriptor> iterator();

    public static OptionDescriptors create(List<OptionDescriptor> descriptors) {
        if (descriptors == null || descriptors.isEmpty()) {
            return EMPTY;
        }
        return new OptionDescriptorsMap(descriptors);
    }
}

