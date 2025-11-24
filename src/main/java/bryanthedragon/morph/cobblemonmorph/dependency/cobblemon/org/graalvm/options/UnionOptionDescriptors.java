
package org.graalvm.options;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.graalvm.options.OptionDescriptor;
import org.graalvm.options.OptionDescriptors;

final class UnionOptionDescriptors
implements OptionDescriptors {
    final OptionDescriptors[] descriptorsList;

    UnionOptionDescriptors(OptionDescriptors[] descriptors) {
        this.descriptorsList = Arrays.copyOf(descriptors, descriptors.length);
    }

    @Override
    public Iterator<OptionDescriptor> iterator() {
        return new Iterator<OptionDescriptor>(){
            Iterator<OptionDescriptor> descriptors;
            int descriptorsIndex;
            OptionDescriptor next;
            {
                this.descriptors = UnionOptionDescriptors.this.descriptorsList[0].iterator();
                this.descriptorsIndex = 0;
                this.next = null;
            }

            @Override
            public boolean hasNext() {
                return this.fetchNext() != null;
            }

            private OptionDescriptor fetchNext() {
                if (this.next != null) {
                    return this.next;
                }
                if (this.descriptors.hasNext()) {
                    this.next = this.descriptors.next();
                    return this.next;
                }
                if (this.descriptorsIndex < UnionOptionDescriptors.this.descriptorsList.length - 1) {
                    ++this.descriptorsIndex;
                    this.descriptors = UnionOptionDescriptors.this.descriptorsList[this.descriptorsIndex].iterator();
                    return this.fetchNext();
                }
                return null;
            }

            @Override
            public OptionDescriptor next() {
                OptionDescriptor fetchedNext = this.fetchNext();
                if (fetchedNext != null) {
                    this.next = null;
                    return fetchedNext;
                }
                throw new NoSuchElementException();
            }
        };
    }

    @Override
    public OptionDescriptor get(String value2) {
        for (OptionDescriptors descriptors : this.descriptorsList) {
            OptionDescriptor descriptor = descriptors.get(value2);
            if (descriptor == null) continue;
            return descriptor;
        }
        return null;
    }
}

