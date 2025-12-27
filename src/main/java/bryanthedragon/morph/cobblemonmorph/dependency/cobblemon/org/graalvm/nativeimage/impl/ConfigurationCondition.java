package org.graalvm.nativeimage.impl;

import java.util.Objects;

public final class ConfigurationCondition implements Comparable<ConfigurationCondition> {
   private final String typeName;
   private static final ConfigurationCondition OBJECT_REACHABLE = new ConfigurationCondition(Object.class.getTypeName());

   public static ConfigurationCondition alwaysTrue() {
      return OBJECT_REACHABLE;
   }

   public static ConfigurationCondition create(String typeReachability) {
      Objects.requireNonNull(typeReachability);
      return OBJECT_REACHABLE.typeName.equals(typeReachability) ? OBJECT_REACHABLE : new ConfigurationCondition(typeReachability);
   }

   private ConfigurationCondition(String typeName) {
      this.typeName = typeName;
   }

   public String getTypeName() {
      return this.typeName;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ConfigurationCondition condition = (ConfigurationCondition)o;
         return Objects.equals(this.typeName, condition.typeName);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.typeName);
   }

   public int compareTo(ConfigurationCondition o) {
      return this.typeName.compareTo(o.typeName);
   }

   @Override
   public String toString() {
      return "[typeReachable: \"" + this.typeName + "\"]";
   }
}
