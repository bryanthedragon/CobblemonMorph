
package org.graalvm.options;

import java.util.Objects;
import org.graalvm.options.OptionCategory;
import org.graalvm.options.OptionKey;
import org.graalvm.options.OptionStability;

public final class OptionDescriptor {
    private final OptionKey<?> key;
    private final String name;
    private final String help;
    private final OptionCategory category;
    private final OptionStability stability;
    private final boolean deprecated;
    private final String deprecationMessage;
    private final String usageSyntax;
    private static final OptionDescriptor EMPTY = new OptionDescriptor(null, null, null, null, null, false, null, "");

    OptionDescriptor(OptionKey<?> key, String name, String help, OptionCategory category, OptionStability stability, boolean deprecated, String deprecationMessage, String usageSyntax) {
        this.key = key;
        this.name = name;
        this.help = help;
        this.category = category;
        this.stability = stability;
        this.deprecated = deprecated;
        this.deprecationMessage = deprecationMessage;
        this.usageSyntax = usageSyntax;
    }

    public String getName() {
        return this.name;
    }

    public OptionKey<?> getKey() {
        return this.key;
    }

    public boolean isDeprecated() {
        return this.deprecated;
    }

    public String getDeprecationMessage() {
        return this.deprecationMessage;
    }

    public boolean isOptionMap() {
        return this.getKey().getType().isOptionMap();
    }

    public OptionCategory getCategory() {
        return this.category;
    }

    public OptionStability getStability() {
        return this.stability;
    }

    public String getHelp() {
        return this.help;
    }

    public String getUsageSyntax() {
        if (this.usageSyntax == null || !this.usageSyntax.isEmpty()) {
            return this.usageSyntax;
        }
        if (!this.key.getType().isDefaultType()) {
            return "";
        }
        Object defaultValue = this.getKey().getDefaultValue();
        if (Boolean.FALSE.equals(defaultValue)) {
            return null;
        }
        if (Boolean.TRUE.equals(defaultValue)) {
            return "true|false";
        }
        if (this.isOptionMap()) {
            return "<value>";
        }
        Class<?> aClass = defaultValue.getClass();
        if (Enum.class.isAssignableFrom(aClass)) {
            return OptionDescriptor.enumUsageSyntax(defaultValue, aClass);
        }
        return "";
    }

    private static String enumUsageSyntax(Object defaultValue, Class<?> aClass) {
        Enum[] enumConstants;
        StringBuilder sb = new StringBuilder();
        Class<?> enumType = aClass;
        for (Enum constant : enumConstants = (Enum[])enumType.getEnumConstants()) {
            if (!defaultValue.equals(constant)) continue;
            sb.append(constant);
            break;
        }
        for (Enum constant : enumConstants) {
            if (defaultValue.equals(constant)) continue;
            sb.append("|");
            sb.append(constant.toString());
        }
        return sb.toString();
    }

    public String toString() {
        return "OptionDescriptor [key=" + this.key + ", help=" + this.help + ", usageSyntax=" + this.usageSyntax + ", category=" + this.category + ", deprecated=" + this.deprecated + ", optionMap=" + this.isOptionMap() + "]";
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.deprecated ? 1231 : 1237);
        result = 31 * result + (this.help == null ? 0 : this.help.hashCode());
        result = 31 * result + (this.key == null ? 0 : this.key.hashCode());
        result = 31 * result + (this.category == null ? 0 : this.category.hashCode());
        result = 31 * result + (this.name == null ? 0 : this.name.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        OptionDescriptor other = (OptionDescriptor)obj;
        return Objects.equals(this.name, other.name) && Objects.equals(this.deprecated, other.deprecated) && Objects.equals(this.help, other.help) && Objects.equals(this.key, other.key) && Objects.equals((Object)this.category, (Object)other.category);
    }

    public static <T> Builder newBuilder(OptionKey<T> key, String name) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(name);
        OptionDescriptor optionDescriptor = EMPTY;
        Objects.requireNonNull(optionDescriptor);
        return optionDescriptor.new Builder(key, name);
    }

    public final class Builder {
        private final OptionKey<?> key;
        private final String name;
        private boolean deprecated = false;
        private String deprecationMessage = "";
        private OptionCategory category = OptionCategory.INTERNAL;
        private OptionStability stability = OptionStability.EXPERIMENTAL;
        private String help = "";
        private String usageSyntax = "";

        Builder(OptionKey<?> key, String name) {
            this.key = key;
            this.name = name;
        }

        public Builder category(OptionCategory category) {
            Objects.requireNonNull(category);
            this.category = category;
            return this;
        }

        public Builder stability(OptionStability stability) {
            Objects.requireNonNull(stability);
            this.stability = stability;
            return this;
        }

        public Builder deprecated(boolean deprecated) {
            this.deprecated = deprecated;
            return this;
        }

        public Builder help(String help) {
            Objects.requireNonNull(help);
            this.help = help;
            return this;
        }

        public Builder usageSyntax(String usageSyntax) {
            this.usageSyntax = usageSyntax;
            return this;
        }

        public Builder deprecationMessage(String deprecationMessage) {
            Objects.requireNonNull(deprecationMessage);
            this.deprecationMessage = deprecationMessage;
            return this;
        }

        public OptionDescriptor build() {
            return new OptionDescriptor(this.key, this.name, this.help, this.category, this.stability, this.deprecated, this.deprecationMessage, this.usageSyntax);
        }
    }
}

