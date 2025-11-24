
package org.graalvm.options;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.graalvm.options.OptionDescriptor;
import org.graalvm.options.OptionDescriptors;

class OptionDescriptorsMap
implements OptionDescriptors {
    final Map<String, OptionDescriptor> descriptors = new LinkedHashMap<String, OptionDescriptor>();
    final List<String> prefixes = new ArrayList<String>();

    OptionDescriptorsMap(List<OptionDescriptor> descriptorList) {
        for (OptionDescriptor descriptor : descriptorList) {
            if (descriptor.isOptionMap()) {
                this.prefixes.add(descriptor.getName());
            }
            this.descriptors.put(descriptor.getName(), descriptor);
        }
    }

    @Override
    public OptionDescriptor get(String optionName) {
        if (!this.prefixes.isEmpty()) {
            for (String prefix : this.prefixes) {
                if (!optionName.startsWith(prefix + ".") && !optionName.equals(prefix)) continue;
                return this.descriptors.get(prefix);
            }
        }
        return this.descriptors.get(optionName);
    }

    @Override
    public Iterator<OptionDescriptor> iterator() {
        return this.descriptors.values().iterator();
    }
}

