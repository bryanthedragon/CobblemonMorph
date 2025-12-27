package org.graalvm.options;

public interface OptionValues {
   OptionDescriptors getDescriptors();

   @Deprecated(since = "22.0")
   default <T> void set(OptionKey<T> optionKey, T value) {
      throw new UnsupportedOperationException();
   }

   <T> T get(OptionKey<T> optionKey);

   boolean hasBeenSet(OptionKey<?> optionKey);

   default boolean hasSetOptions() {
      for (OptionDescriptor descriptor : this.getDescriptors()) {
         if (this.hasBeenSet(descriptor.getKey())) {
            return true;
         }
      }

      return false;
   }
}
