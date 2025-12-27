package com.oracle.truffle.polyglot;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.graalvm.options.OptionDescriptor;
import org.graalvm.options.OptionDescriptors;
import org.graalvm.options.OptionKey;
import org.graalvm.options.OptionStability;
import org.graalvm.options.OptionValues;

final class OptionValuesImpl implements OptionValues {
   private static final float FUZZY_MATCH_THRESHOLD = 0.7F;
   static final String SYSTEM_PROPERTY_PREFIX = "polyglot.";
   private final OptionDescriptors descriptors;
   private final Map<OptionKey<?>, Object> values;
   private final Map<OptionKey<?>, String> unparsedValues;

   OptionValuesImpl(OptionDescriptors descriptors, boolean preserveUnparsedValues) {
      Objects.requireNonNull(descriptors);
      this.descriptors = descriptors;
      this.values = new HashMap<>();
      this.unparsedValues = preserveUnparsedValues ? new HashMap<>() : null;
   }

   @Override
   public int hashCode() {
      int result = 31 + this.descriptors.hashCode();
      return 31 * result + this.values.hashCode();
   }

   @Override
   public boolean equals(Object obj) {
      if (!(obj instanceof OptionValues)) {
         return super.equals(obj);
      } else if (this == obj) {
         return true;
      } else {
         OptionValues other = (OptionValues)obj;
         if (!this.getDescriptors().equals(other.getDescriptors())) {
            return false;
         } else if (!this.hasSetOptions() && !other.hasSetOptions()) {
            return true;
         } else {
            if (other instanceof OptionValuesImpl) {
               OptionValuesImpl otherOptions = (OptionValuesImpl)other;
               if (!this.values.equals(otherOptions.values)) {
                  return false;
               }
            } else {
               for (OptionDescriptor descriptor : this.getDescriptors()) {
                  OptionKey<?> key = descriptor.getKey();
                  if (!this.slowCompareKey(key, other)) {
                     return false;
                  }
               }
            }

            return true;
         }
      }
   }

   private boolean slowCompareKey(OptionKey<?> key, OptionValues other) {
      boolean set = this.hasBeenSet(key);
      return set != other.hasBeenSet(key) ? false : !set || this.get(key).equals(other.get(key));
   }

   public void putAll(PolyglotEngineImpl engine, Map<String, String> providedValues, boolean allowExperimentalOptions) {
      for (String key : providedValues.keySet()) {
         this.put(engine, key, providedValues.get(key), allowExperimentalOptions);
      }
   }

   public void put(PolyglotEngineImpl engine, String key, String value, boolean allowExperimentalOptions) {
      OptionDescriptor descriptor = this.findDescriptor(engine, key, allowExperimentalOptions);
      OptionKey<?> optionKey = descriptor.getKey();
      Object previousValue;
      if (this.values.containsKey(optionKey)) {
         previousValue = this.values.get(optionKey);
      } else {
         previousValue = optionKey.getDefaultValue();
      }

      String name = descriptor.getName();
      String suffix = null;
      if (descriptor.isOptionMap()) {
         suffix = key.substring(name.length());

         assert suffix.isEmpty() || suffix.startsWith(".");

         if (suffix.startsWith(".")) {
            suffix = suffix.substring(1);
         }
      }

      Object convertedValue;
      try {
         convertedValue = optionKey.getType().convert(previousValue, suffix, value);
      } catch (IllegalArgumentException var12) {
         throw PolyglotEngineException.illegalArgument(var12);
      }

      this.values.put(descriptor.getKey(), convertedValue);
      if (this.unparsedValues != null) {
         this.unparsedValues.put(descriptor.getKey(), value);
      }
   }

   private OptionValuesImpl(OptionValuesImpl copy) {
      this.values = new HashMap<>(copy.values);
      this.descriptors = copy.descriptors;
      this.unparsedValues = copy.unparsedValues;
   }

   private <T> boolean contains(OptionKey<T> optionKey) {
      for (OptionDescriptor descriptor : this.descriptors) {
         if (descriptor.getKey() == optionKey) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean hasBeenSet(OptionKey<?> optionKey) {
      assert this.contains(optionKey);

      return this.values.containsKey(optionKey);
   }

   OptionValuesImpl copy() {
      return new OptionValuesImpl(this);
   }

   void copyInto(OptionValuesImpl target) {
      if (!target.values.isEmpty()) {
         throw new IllegalStateException("Values must be empty.");
      } else {
         target.values.putAll(this.values);
      }
   }

   @Override
   public OptionDescriptors getDescriptors() {
      return this.descriptors;
   }

   @Override
   public <T> T get(OptionKey<T> optionKey) {
      assert this.contains(optionKey);

      Object value = this.values.get(optionKey);
      return (T)(value == null ? optionKey.getDefaultValue() : value);
   }

   @Override
   public <T> void set(OptionKey<T> optionKey, T value) {
      throw new UnsupportedOperationException("OptionValues#set() is no longer supported");
   }

   @Override
   public boolean hasSetOptions() {
      return !this.values.isEmpty();
   }

   String getUnparsedOptionValue(OptionKey<?> key) {
      if (this.unparsedValues == null) {
         throw new IllegalStateException("Unparsed values are not supported");
      } else {
         return this.unparsedValues.get(key);
      }
   }

   private OptionDescriptor findDescriptor(PolyglotEngineImpl engine, String key, boolean allowExperimentalOptions) {
      OptionDescriptor descriptor = this.descriptors.get(key);
      if (descriptor == null) {
         throw this.failNotFound(engine, key);
      } else if (!allowExperimentalOptions && descriptor.getStability() == OptionStability.EXPERIMENTAL) {
         throw failExperimental(key);
      } else {
         return descriptor;
      }
   }

   private static RuntimeException failExperimental(String key) {
      String message = String.format(
            "Option '%s' is experimental and must be enabled with allowExperimentalOptions(boolean) in Context.Builder or Engine.Builder. ", key
         )
         + "Do not use experimental options in production environments.";
      return PolyglotEngineException.illegalArgument(message);
   }

   private RuntimeException failNotFound(PolyglotEngineImpl engine, String key) {
      Exception errorOptions = null;

      OptionDescriptors allOptions;
      try {
         allOptions = engine == null ? this.descriptors : engine.getAllOptions();
      } catch (Exception var6) {
         errorOptions = var6;
         allOptions = this.descriptors;
      }

      RuntimeException error = failNotFound(allOptions, key);
      if (errorOptions != null) {
         error.addSuppressed(errorOptions);
      }

      throw error;
   }

   static RuntimeException failNotFound(OptionDescriptors allOptions, String key) {
      Iterable<OptionDescriptor> matches = fuzzyMatch(allOptions, key);
      Formatter msg = new Formatter();
      msg.format("Could not find option with name %s.", key);
      Iterator<OptionDescriptor> iterator = matches.iterator();
      if (iterator.hasNext()) {
         msg.format("%nDid you mean one of the following?");

         for (OptionDescriptor match : matches) {
            msg.format("%n    %s=<%s>", match.getName(), match.getKey().getType().getName());
         }
      }

      throw PolyglotEngineException.illegalArgument(msg.toString());
   }

   static List<OptionDescriptor> fuzzyMatch(OptionDescriptors descriptors, String optionKey) {
      List<OptionDescriptor> matches = new ArrayList<>();

      for (OptionDescriptor option : descriptors) {
         float score = stringSimiliarity(option.getName(), optionKey);
         if (score >= 0.7F) {
            matches.add(option);
         }
      }

      return matches;
   }

   private static float stringSimiliarity(String str1, String str2) {
      int hit = 0;

      for (int i = 0; i < str1.length() - 1; i++) {
         for (int j = 0; j < str2.length() - 1; j++) {
            if (str1.charAt(i) == str2.charAt(j) && str1.charAt(i + 1) == str2.charAt(j + 1)) {
               hit++;
               break;
            }
         }
      }

      return 2.0F * hit / (str1.length() + str2.length());
   }

   @Override
   public String toString() {
      Map<OptionKey<?>, ? extends Object> options;
      if (this.unparsedValues != null) {
         options = this.unparsedValues;
      } else {
         options = this.values;
      }

      StringBuilder b = new StringBuilder("{");
      String sep = "";

      for (OptionDescriptor descriptor : this.getDescriptors()) {
         OptionKey<?> key = descriptor.getKey();
         if (this.hasBeenSet(key)) {
            b.append(sep);
            b.append(descriptor.getName());
            b.append("=");
            b.append(options.get(key));
            sep = ", ";
         }
      }

      b.append("}");
      return b.toString();
   }
}
