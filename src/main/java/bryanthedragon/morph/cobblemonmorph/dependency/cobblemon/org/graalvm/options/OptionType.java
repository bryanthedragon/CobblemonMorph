
package org.graalvm.options;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import org.graalvm.options.OptionMap;

public final class OptionType<T> {
    private static final Consumer<?> EMPTY_VALIDATOR = new Consumer<Object>(){

        @Override
        public void accept(Object t) {
        }
    };
    private final String name;
    private final Converter<T> converter;
    private final Consumer<T> validator;
    private final boolean isOptionMap;
    private final boolean isDefaultType;
    private static final Map<Class<?>, OptionType<?>> DEFAULTTYPES = new HashMap();

    public OptionType(String name, final Function<String, T> stringConverter, Consumer<T> validator) {
        this(name, new Converter<T>(){

            @Override
            public T convert(T previousValue, String key, String value2) {
                return stringConverter.apply(value2);
            }
        }, validator, false, false);
    }

    private OptionType(String name, Converter<T> converter, Consumer<T> validator, boolean isOptionMap, boolean isDefaultType) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(converter);
        Objects.requireNonNull(validator);
        this.name = name;
        this.converter = converter;
        this.validator = validator;
        this.isOptionMap = isOptionMap;
        this.isDefaultType = isDefaultType;
    }

    private OptionType(String name, final Function<String, T> stringConverter, boolean isDefaultType) {
        this(name, new Converter<T>(){

            @Override
            public T convert(T previousValue, String key, String value2) {
                return stringConverter.apply(value2);
            }
        }, EMPTY_VALIDATOR, false, isDefaultType);
    }

    public OptionType(String name, Function<String, T> stringConverter) {
        this(name, stringConverter, EMPTY_VALIDATOR);
    }

    @Deprecated(since="19.0")
    public OptionType(String name, T defaultValue, Function<String, T> stringConverter, Consumer<T> validator) {
        this(name, stringConverter, validator);
    }

    @Deprecated(since="19.0")
    public OptionType(String name, T defaultValue, Function<String, T> stringConverter) {
        this(name, stringConverter);
    }

    @Deprecated(since="19.0")
    public T getDefaultValue() {
        return null;
    }

    public String getName() {
        return this.name;
    }

    public T convert(String value2) {
        T v = this.converter.convert(null, null, value2);
        this.validate(v);
        return v;
    }

    public T convert(Object previousValue, String nameSuffix, String value2) {
        Object v = this.converter.convert(previousValue, nameSuffix, value2);
        this.validate(v);
        return (T)v;
    }

    public void validate(T value2) {
        this.validator.accept(value2);
    }

    public String toString() {
        return "OptionType[name=" + this.name + "]";
    }

    public static <T> OptionType<T> defaultType(T value2) {
        return OptionType.defaultType(value2.getClass());
    }

    static <V> OptionType<OptionMap<V>> mapOf(Class<V> valueClass) {
        final OptionType<V> valueType = OptionType.defaultType(valueClass);
        if (valueType == null) {
            return null;
        }
        return new OptionType<OptionMap<V>>("OptionMap", new Converter<OptionMap<V>>(){

            @Override
            public OptionMap<V> convert(OptionMap<V> previousValue, String key, String value2) {
                OptionMap map = previousValue;
                if (map == null || map.entrySet().isEmpty()) {
                    map = new OptionMap(new HashMap());
                }
                map.backingMap.put(key, valueType.convert(map.get(key), key, value2));
                return map;
            }
        }, EMPTY_VALIDATOR, true, true);
    }

    public static <T> OptionType<T> defaultType(Class<T> clazz) {
        OptionType<?> type = DEFAULTTYPES.get(clazz);
        if (type != null) {
            return type;
        }
        if (Enum.class.isAssignableFrom(clazz)) {
            return OptionType.defaultEnumType(clazz);
        }
        return null;
    }

    private static <T> OptionType<T> defaultEnumType(final Class<T> clazz) {
        return new OptionType<T>(clazz.getSimpleName(), new Function<String, T>(){
            final Map<String, Enum<?>> validValues = new HashMap();
            {
                Class enumType = clazz;
                for (Enum constant : (Enum[])enumType.getEnumConstants()) {
                    this.validValues.put(constant.toString(), constant);
                }
            }

            @Override
            public T apply(String t) {
                Enum<?> value2;
                Class enumType = clazz;
                if (t != null && (value2 = this.validValues.get(t)) != null) {
                    return value2;
                }
                StringBuilder b = new StringBuilder();
                String sep = "";
                for (Enum constant : (Enum[])enumType.getEnumConstants()) {
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
        DEFAULTTYPES.put(Boolean.class, new OptionType<Boolean>("Boolean", new Function<String, Boolean>(){

            @Override
            public Boolean apply(String t) {
                if ("true".equals(t)) {
                    return Boolean.TRUE;
                }
                if ("false".equals(t)) {
                    return Boolean.FALSE;
                }
                throw new IllegalArgumentException(String.format("Invalid boolean option value '%s'. The value of the option must be '%s' or '%s'.", t, "true", "false"));
            }
        }, true));
        DEFAULTTYPES.put(Byte.class, new OptionType<Byte>("Byte", new Function<String, Byte>(){

            @Override
            public Byte apply(String t) {
                try {
                    return Byte.parseByte(t);
                }
                catch (NumberFormatException e) {
                    throw new IllegalArgumentException(e.getMessage(), e);
                }
            }
        }, true));
        DEFAULTTYPES.put(Integer.class, new OptionType<Integer>("Integer", new Function<String, Integer>(){

            @Override
            public Integer apply(String t) {
                try {
                    return Integer.parseInt(t);
                }
                catch (NumberFormatException e) {
                    throw new IllegalArgumentException(e.getMessage(), e);
                }
            }
        }, true));
        DEFAULTTYPES.put(Long.class, new OptionType<Long>("Long", new Function<String, Long>(){

            @Override
            public Long apply(String t) {
                try {
                    return Long.parseLong(t);
                }
                catch (NumberFormatException e) {
                    throw new IllegalArgumentException(e.getMessage(), e);
                }
            }
        }, true));
        DEFAULTTYPES.put(Float.class, new OptionType<Float>("Float", new Function<String, Float>(){

            @Override
            public Float apply(String t) {
                try {
                    return Float.valueOf(Float.parseFloat(t));
                }
                catch (NumberFormatException e) {
                    throw new IllegalArgumentException(e.getMessage(), e);
                }
            }
        }, true));
        DEFAULTTYPES.put(Double.class, new OptionType<Double>("Double", new Function<String, Double>(){

            @Override
            public Double apply(String t) {
                try {
                    return Double.parseDouble(t);
                }
                catch (NumberFormatException e) {
                    throw new IllegalArgumentException(e.getMessage(), e);
                }
            }
        }, true));
        DEFAULTTYPES.put(String.class, new OptionType<String>("String", new Function<String, String>(){

            @Override
            public String apply(String t) {
                return t;
            }
        }, true));
    }

    @FunctionalInterface
    private static interface Converter<T> {
        public T convert(T var1, String var2, String var3);
    }
}

