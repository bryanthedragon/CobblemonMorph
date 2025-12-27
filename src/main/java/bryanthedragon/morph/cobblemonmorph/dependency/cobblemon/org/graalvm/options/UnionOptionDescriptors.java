package org.graalvm.options;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

final class UnionOptionDescriptors implements OptionDescriptors {
   final OptionDescriptors[] descriptorsList;

   UnionOptionDescriptors(OptionDescriptors[] descriptors) {
      this.descriptorsList = Arrays.copyOf(descriptors, descriptors.length);
   }

   @Override
   public Iterator<OptionDescriptor> iterator() {
      return new Iterator<OptionDescriptor>() {
         Iterator<OptionDescriptor> descriptors = UnionOptionDescriptors.this.descriptorsList[0].iterator();
         int descriptorsIndex = 0;
         OptionDescriptor next = null;

         @Override
         public boolean hasNext() {
            return this.fetchNext() != null;
         }

         private OptionDescriptor fetchNext() {
            if (this.next != null) {
               return this.next;
            } else if (this.descriptors.hasNext()) {
               this.next = this.descriptors.next();
               return this.next;
            } else if (this.descriptorsIndex < UnionOptionDescriptors.this.descriptorsList.length - 1) {
               this.descriptorsIndex++;
               this.descriptors = UnionOptionDescriptors.this.descriptorsList[this.descriptorsIndex].iterator();
               return this.fetchNext();
            } else {
               return null;
            }
         }

         public OptionDescriptor next() {
            OptionDescriptor fetchedNext = this.fetchNext();
            if (fetchedNext != null) {
               this.next = null;
               return fetchedNext;
            } else {
               throw new NoSuchElementException();
            }
         }
      };
   }

   @Override
   public OptionDescriptor get(String value) {
      for (OptionDescriptors descriptors : this.descriptorsList) {
         OptionDescriptor descriptor = descriptors.get(value);
         if (descriptor != null) {
            return descriptor;
         }
      }

      return null;
   }
}
