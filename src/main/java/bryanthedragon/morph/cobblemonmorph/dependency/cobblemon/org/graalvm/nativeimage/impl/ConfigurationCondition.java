
package org.graalvm.nativeimage.impl;

import java.util.Objects;

public final class ConfigurationCondition
implements Comparable<ConfigurationCondition> {
    private final String typeName;
    private static final ConfigurationCondition OBJECT_REACHABLE = new ConfigurationCondition(Object.class.getTypeName());

    public static ConfigurationCondition alwaysTrue() {
        return OBJECT_REACHABLE;
    }

    public static ConfigurationCondition create(String typeReachability) {
        Objects.requireNonNull(typeReachability);
        if (ConfigurationCondition.OBJECT_REACHABLE.typeName.equals(typeReachability)) {
            return OBJECT_REACHABLE;
        }
        return new ConfigurationCondition(typeReachability);
    }

    private ConfigurationCondition(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ConfigurationCondition condition2 = (ConfigurationCondition)o;
        return Objects.equals(this.typeName, condition2.typeName);
    }

    public int hashCode() {
        return Objects.hash(this.typeName);
    }

    @Override
    public int compareTo(ConfigurationCondition o) {
        return this.typeName.compareTo(o.typeName);
    }

    public String toString() {
        return "[typeReachable: \"" + this.typeName + "\"]";
    }
}

