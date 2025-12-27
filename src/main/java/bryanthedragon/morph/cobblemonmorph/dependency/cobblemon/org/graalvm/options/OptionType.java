package org.graalvm.options;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public final class OptionType<T> {
   private static final Consumer<?> EMPTY_VALIDATOR = new Consumer<Object>() {
      @Override
      public void accept(Object t) {
      }
   };
   private final String name;
   private final OptionType.Converter<T> converter;
   private final Consumer<T> validator;
   private final boolean isOptionMap;
   private final boolean isDefaultType;
   private static final Map<Class<?>, OptionType<?>> DEFAULTTYPES = new HashMap<>();

   public OptionType(String name, Function<String, T> stringConverter, Consumer<T> validator) {
      this(name, new OptionType.Converter<T>() {
         @Override
         public T convert(T previousValue, String key, String value) {
            return stringConverter.apply(value);
         }
      }, validator, false, false);
   }

   private OptionType(String name, OptionType.Converter<T> converter, Consumer<T> validator, boolean isOptionMap, boolean isDefaultType) {
      Objects.requireNonNull(name);
      Objects.requireNonNull(converter);
      Objects.requireNonNull(validator);
      this.name = name;
      this.converter = converter;
      this.validator = validator;
      this.isOptionMap = isOptionMap;
      this.isDefaultType = isDefaultType;
   }

   private OptionType(String name, Function<String, T> stringConverter, boolean isDefaultType) {
      this(name, new OptionType.Converter<T>() {
         @Override
         public T convert(T previousValue, String key, String value) {
            return stringConverter.apply(value);
         }
      }, (Consumer<T>)EMPTY_VALIDATOR, false, isDefaultType);
   }

   public OptionType(String name, Function<String, T> stringConverter) {
      this(name, stringConverter, (Consumer<T>)EMPTY_VALIDATOR);
   }

   @Deprecated(since = "19.0")
   public OptionType(String name, T defaultValue, Function<String, T> stringConverter, Consumer<T> validator) {
      this(name, stringConverter, validator);
   }

   @Deprecated(since = "19.0")
   public OptionType(String name, T defaultValue, Function<String, T> stringConverter) {
      this(name, stringConverter);
   }

   @Deprecated(since = "19.0")
   public T getDefaultValue() {
      return null;
   }

   public String getName() {
      return this.name;
   }

   public T convert(String value) {
      T v = this.converter.convert(null, null, value);
      this.validate(v);
      return v;
   }

   public T convert(Object previousValue, String nameSuffix, String value) {
      T v = this.converter.convert((T)previousValue, nameSuffix, value);
      this.validate(v);
      return v;
   }

   public void validate(T value) {
      this.validator.accept(value);
   }

   @Override
   public String toString() {
      return "OptionType[name=" + this.name + "]";
   }

   public static <T> OptionType<T> defaultType(T value) {
      return defaultType((Class<T>)value.getClass());
   }

   static <V> OptionType<OptionMap<V>> mapOf(Class<V> valueClass) {
      final OptionType<V> valueType = defaultType(valueClass);
      return valueType == null ? null : new OptionType<>("OptionMap", new OptionType.Converter<OptionMap<V>>() {
         public OptionMap<V> convert(OptionMap<V> previousValue, String key, String value) {
            OptionMap<V> map = previousValue;
            if (previousValue == null || previousValue.entrySet().isEmpty()) {
               map = new OptionMap<>(new HashMap<>());
            }

            map.backingMap.put(key, valueType.convert(map.get(key), key, value));
            return map;
         }
      }, (Consumer<OptionMap<V>>)EMPTY_VALIDATOR, true, true);
   }

   public static <T> OptionType<T> defaultType(Class<T> clazz) {
      OptionType<T> type = (OptionType<T>)DEFAULTTYPES.get(clazz);
      if (type != null) {
         return type;
      } else {
         return Enum.class.isAssignableFrom(clazz) ? defaultEnumType(clazz) : null;
      }
   }

   private static <T> OptionType<T> defaultEnumType(Class<T> clazz) {
      return new OptionType<>(clazz.getSimpleName(), new Function<String, T>() {
         final Map<String, Enum<?>> validValues = new HashMap<>();

         {
            Class<? extends Enum<?>> enumType = clazz;

            for (Enum<?> constant : enumType.getEnumConstants()) {
               this.validValues.put(constant.toString(), constant);
            }
         }

         public T apply(String t) {
            Class<? extends Enum> enumType = clazz;
            if (t != null) {
               Enum value = this.validValues.get(t);
               if (value != null) {
                  return (T)value;
               }
            }

            StringBuilder b = new StringBuilder();
            String sep = "";

            for (Enum constant : enumType.getEnumConstants()) {
               b.append(sep);
               b.append('\'');
               b.append(constant.toString());
               b.append('\'');
               sep = ", ";
            }

            throw new IllegalArgumentException("Invalid option value '" + t + "'. Valid options values are: " + b.toString());
         }
      }, true);
   }

   boolean isOptionMap() {
      return this.isOptionMap;
   }

   boolean isDefaultType() {
      return this.isDefaultType;
   }

   static {
      DEFAULTTYPES.put(
         Boolean.class,
         new OptionType<>(
            "Boolean",
            new Function<String, Boolean>() {
               public Boolean apply(String t) {
                  if ("true".equals(t)) {
                     return Boolean.TRUE;
                  } else if ("false".equals(t)) {
                     return Boolean.FALSE;
                  } else {
                     throw new IllegalArgumentException(
                        String.format("Invalid boolean option value '%s'. The value of the option must be '%s' or '%s'.", t, "true", "false")
                     );
                  }
               }
            },
            true
         )
      );
      DEFAULTTYPES.put(Byte.class, new OptionType<>("Byte", new Function<String, Byte>() {
         public Byte apply(String t) {
            try {
               return Byte.parseByte(t);
            } catch (NumberFormatException var3) {
               throw new IllegalArgumentException(var3.getMessage(), var3);
            }
         }
      }, true));
      DEFAULTTYPES.put(Integer.class, new OptionType<>("Integer", new Function<String, Integer>() {
         public Integer apply(String t) {
            try {
               return Integer.parseInt(t);
            } catch (NumberFormatException var3) {
               throw new IllegalArgumentException(var3.getMessage(), var3);
            }
         }
      }, true));
      DEFAULTTYPES.put(Long.class, new OptionType<>("Long", new Function<String, Long>() {
         public Long apply(String t) {
            try {
               return Long.parseLong(t);
            } catch (NumberFormatException var3) {
               throw new IllegalArgumentException(var3.getMessage(), var3);
            }
         }
      }, true));
      DEFAULTTYPES.put(Float.class, new OptionType<>("Float", new Function<String, Float>() {
         public Float apply(String t) {
            try {
               return Float.parseFloat(t);
            } catch (NumberFormatException var3) {
               throw new IllegalArgumentException(var3.getMessage(), var3);
            }
         }
      }, true));
      DEFAULTTYPES.put(Double.class, new OptionType<>("Double", new Function<String, Double>() {
         public Double apply(String t) {
            try {
               return Double.parseDouble(t);
            } catch (NumberFormatException var3) {
               throw new IllegalArgumentException(var3.getMessage(), var3);
            }
         }
      }, true));
      DEFAULTTYPES.put(String.class, new OptionType<>("String", new Function<String, String>() {
         public String apply(String t) {
            return t;
         }
      }, true));
   }

   @FunctionalInterface
   private interface Converter<T> {
      T convert(T previousValue, String key, String value);
   }
}
