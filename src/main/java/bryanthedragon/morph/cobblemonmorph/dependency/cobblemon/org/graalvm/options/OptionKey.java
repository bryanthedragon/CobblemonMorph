package org.graalvm.options;

import java.util.Objects;

public final class OptionKey<T> {
   private final OptionType<T> type;
   private final T defaultValue;

   public OptionKey(T defaultValue) {
      Objects.requireNonNull(defaultValue);
      this.defaultValue = defaultValue;
      this.type = OptionType.defaultType(defaultValue);
      if (this.type == null) {
         throw new IllegalArgumentException(
            "No default type specified for type " + defaultValue.getClass().getName() + ". Specify the option type explicitly to resolve this."
         );
      }
   }

   public OptionKey(T defaultValue, OptionType<T> type) {
      Objects.requireNonNull(type);
      this.defaultValue = defaultValue;
      this.type = type;
   }

   public static <V> OptionKey<OptionMap<V>> mapOf(Class<V> valueClass) {
      OptionType<OptionMap<V>> type = OptionType.mapOf(valueClass);
      if (type == null) {
         throw new IllegalArgumentException("No default type specified for type " + valueClass.getName());
      } else {
         return new OptionKey<>(OptionMap.empty(), type);
      }
   }

   public OptionType<T> getType() {
      return this.type;
   }

   public T getDefaultValue() {
      return this.defaultValue;
   }

   public T getValue(OptionValues values) {
      return values.get(this);
   }

   public boolean hasBeenSet(OptionValues values) {
      return values.hasBeenSet(this);
   }
}
