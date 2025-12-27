package org.graalvm.options;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public interface OptionDescriptors extends Iterable<OptionDescriptor> {
   OptionDescriptors EMPTY = new OptionDescriptors() {
      @Override
      public Iterator<OptionDescriptor> iterator() {
         return Collections.<OptionDescriptor>emptyList().iterator();
      }

      @Override
      public OptionDescriptor get(String key) {
         return null;
      }
   };

   OptionDescriptor get(String optionName);

   static OptionDescriptors createUnion(OptionDescriptors... descriptors) {
      if (descriptors.length == 0) {
         return EMPTY;
      } else if (descriptors.length == 1) {
         return descriptors[0];
      } else {
         OptionDescriptors singleNonEmpty = null;

         for (int i = 0; i < descriptors.length; i++) {
            OptionDescriptors d = descriptors[i];
            if (d != EMPTY) {
               if (singleNonEmpty != null) {
                  return new UnionOptionDescriptors(descriptors);
               }

               singleNonEmpty = d;
            }
         }

         return singleNonEmpty == null ? EMPTY : singleNonEmpty;
      }
   }

   @Override
   Iterator<OptionDescriptor> iterator();

   static OptionDescriptors create(List<OptionDescriptor> descriptors) {
      return (OptionDescriptors)(descriptors != null && !descriptors.isEmpty() ? new OptionDescriptorsMap(descriptors) : EMPTY);
   }
}
