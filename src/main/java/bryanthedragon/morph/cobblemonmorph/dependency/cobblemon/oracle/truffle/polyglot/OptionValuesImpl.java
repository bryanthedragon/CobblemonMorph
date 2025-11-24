
package com.oracle.truffle.polyglot;

import com.oracle.truffle.polyglot.PolyglotEngineException;
import com.oracle.truffle.polyglot.PolyglotEngineImpl;
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

final class OptionValuesImpl
implements OptionValues {
    private static final float FUZZY_MATCH_THRESHOLD = 0.7f;
    static final String SYSTEM_PROPERTY_PREFIX = "polyglot.";
    private final OptionDescriptors descriptors;
    private final Map<OptionKey<?>, Object> values;
    private final Map<OptionKey<?>, String> unparsedValues;

    OptionValuesImpl(OptionDescriptors descriptors, boolean preserveUnparsedValues) {
        Objects.requireNonNull(descriptors);
        this.descriptors = descriptors;
        this.values = new HashMap();
        this.unparsedValues = preserveUnparsedValues ? new HashMap() : null;
    }

    public int hashCode() {
        int result = 31 + this.descriptors.hashCode();
        result = 31 * result + this.values.hashCode();
        return result;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof OptionValues)) {
            return super.equals(obj);
        }
        if (this == obj) {
            return true;
        }
        OptionValues other = (OptionValues)obj;
        if (!this.getDescriptors().equals(other.getDescriptors())) {
            return false;
        }
        if (!this.hasSetOptions() && !other.hasSetOptions()) {
            return true;
        }
        if (other instanceof OptionValuesImpl) {
            OptionValuesImpl otherOptions = (OptionValuesImpl)other;
            if (!this.values.equals(otherOptions.values)) {
                return false;
            }
        } else {
            for (OptionDescriptor descriptor : this.getDescriptors()) {
                OptionKey<?> key = descriptor.getKey();
                if (this.slowCompareKey(key, other)) continue;
                return false;
            }
        }
        return true;
    }

    private boolean slowCompareKey(OptionKey<?> key, OptionValues other) {
        boolean set2 = this.hasBeenSet(key);
        if (set2 != other.hasBeenSet(key)) {
            return false;
        }
        return !set2 || this.get(key).equals(other.get(key));
    }

    public void putAll(PolyglotEngineImpl engine, Map<String, String> providedValues, boolean allowExperimentalOptions) {
        for (String key : providedValues.keySet()) {
            this.put(engine, key, providedValues.get(key), allowExperimentalOptions);
        }
    }

    public void put(PolyglotEngineImpl engine, String key, String value2, boolean allowExperimentalOptions) {
        Object convertedValue;
        OptionDescriptor descriptor = this.findDescriptor(engine, key, allowExperimentalOptions);
        OptionKey<?> optionKey = descriptor.getKey();
        Object previousValue = this.values.containsKey(optionKey) ? this.values.get(optionKey) : optionKey.getDefaultValue();
        String name = descriptor.getName();
        String suffix = null;
        if (descriptor.isOptionMap()) {
            suffix = key.substring(name.length());
            assert (suffix.isEmpty() || suffix.startsWith("."));
            if (suffix.startsWith(".")) {
                suffix = suffix.substring(1);
            }
        }
        try {
            convertedValue = optionKey.getType().convert(previousValue, suffix, value2);
        }
        catch (IllegalArgumentException e) {
            throw PolyglotEngineException.illegalArgument(e);
        }
        this.values.put(descriptor.getKey(), convertedValue);
        if (this.unparsedValues != null) {
            this.unparsedValues.put(descriptor.getKey(), value2);
        }
    }

    private OptionValuesImpl(OptionValuesImpl copy) {
        this.values = new HashMap(copy.values);
        this.descriptors = copy.descriptors;
        this.unparsedValues = copy.unparsedValues;
    }

    private <T> boolean contains(OptionKey<T> optionKey) {
        for (OptionDescriptor descriptor : this.descriptors) {
            if (descriptor.getKey() != optionKey) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean hasBeenSet(OptionKey<?> optionKey) {
        assert (this.contains(optionKey));
        return this.values.containsKey(optionKey);
    }

    OptionValuesImpl copy() {
        return new OptionValuesImpl(this);
    }

    void copyInto(OptionValuesImpl target) {
        if (!target.values.isEmpty()) {
            throw new IllegalStateException("Values must be empty.");
        }
        target.values.putAll(this.values);
    }

    @Override
    public OptionDescriptors getDescriptors() {
        return this.descriptors;
    }

    @Override
    public <T> T get(OptionKey<T> optionKey) {
        assert (this.contains(optionKey));
        Object value2 = this.values.get(optionKey);
        if (value2 == null) {
            return optionKey.getDefaultValue();
        }
        return (T)value2;
    }

    @Override
    public <T> void set(OptionKey<T> optionKey, T value2) {
        throw new UnsupportedOperationException("OptionValues#set() is no longer supported");
    }

    @Override
    public boolean hasSetOptions() {
        return !this.values.isEmpty();
    }

    String getUnparsedOptionValue(OptionKey<?> key) {
        if (this.unparsedValues == null) {
            throw new IllegalStateException("Unparsed values are not supported");
        }
        return this.unparsedValues.get(key);
    }

    private OptionDescriptor findDescriptor(PolyglotEngineImpl engine, String key, boolean allowExperimentalOptions) {
        OptionDescriptor descriptor = this.descriptors.get(key);
        if (descriptor == null) {
            throw this.failNotFound(engine, key);
        }
        if (!allowExperimentalOptions && descriptor.getStability() == OptionStability.EXPERIMENTAL) {
            throw OptionValuesImpl.failExperimental(key);
        }
        return descriptor;
    }

    private static RuntimeException failExperimental(String key) {
        String message = String.format("Option '%s' is experimental and must be enabled with allowExperimentalOptions(boolean) in Context.Builder or Engine.Builder. ", key) + "Do not use experimental options in production environments.";
        return PolyglotEngineException.illegalArgument(message);
    }

    private RuntimeException failNotFound(PolyglotEngineImpl engine, String key) {
        OptionDescriptors allOptions;
        Exception errorOptions = null;
        try {
            allOptions = engine == null ? this.descriptors : engine.getAllOptions();
        }
        catch (Exception e) {
            errorOptions = e;
            allOptions = this.descriptors;
        }
        RuntimeException error = OptionValuesImpl.failNotFound(allOptions, key);
        if (errorOptions != null) {
            error.addSuppressed(errorOptions);
        }
        throw error;
    }

    static RuntimeException failNotFound(OptionDescriptors allOptions, String key) {
        List<OptionDescriptor> matches2 = OptionValuesImpl.fuzzyMatch(allOptions, key);
        Formatter msg = new Formatter();
        msg.format("Could not find option with name %s.", key);
        Iterator iterator = matches2.iterator();
        if (iterator.hasNext()) {
            msg.format("%nDid you mean one of the following?", new Object[0]);
            for (OptionDescriptor match : matches2) {
                msg.format("%n    %s=<%s>", match.getName(), match.getKey().getType().getName());
            }
        }
        throw PolyglotEngineException.illegalArgument(msg.toString());
    }

    static List<OptionDescriptor> fuzzyMatch(OptionDescriptors descriptors, String optionKey) {
        ArrayList<OptionDescriptor> matches2 = new ArrayList<OptionDescriptor>();
        for (OptionDescriptor option : descriptors) {
            float score = OptionValuesImpl.stringSimiliarity(option.getName(), optionKey);
            if (!(score >= 0.7f)) continue;
            matches2.add(option);
        }
        return matches2;
    }

    private static float stringSimiliarity(String str1, String str2) {
        int hit = 0;
        block0: for (int i = 0; i < str1.length() - 1; ++i) {
            for (int j = 0; j < str2.length() - 1; ++j) {
                if (str1.charAt(i) != str2.charAt(j) || str1.charAt(i + 1) != str2.charAt(j + 1)) continue;
                ++hit;
                continue block0;
            }
        }
        return 2.0f * (float)hit / (float)(str1.length() + str2.length());
    }

    public String toString() {
        Map<OptionKey<?>, Object> options = this.unparsedValues != null ? this.unparsedValues : this.values;
        StringBuilder b = new StringBuilder("{");
        String sep = "";
        for (OptionDescriptor descriptor : this.getDescriptors()) {
            OptionKey<?> key = descriptor.getKey();
            if (!this.hasBeenSet(key)) continue;
            b.append(sep);
            b.append(descriptor.getName());
            b.append("=");
            b.append(options.get(key));
            sep = ", ";
        }
        b.append("}");
        return b.toString();
    }
}

