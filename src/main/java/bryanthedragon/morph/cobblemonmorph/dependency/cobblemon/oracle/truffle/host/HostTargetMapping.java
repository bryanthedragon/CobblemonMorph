package com.oracle.truffle.host;

import java.util.function.Function;
import java.util.function.Predicate;
import org.graalvm.polyglot.HostAccess;

final class HostTargetMapping implements Comparable<HostTargetMapping> {
   final Class<Object> sourceType;
   final Class<Object> targetType;
   final Predicate<Object> accepts;
   final Function<Object, Object> converter;
   final int hostPriority;

   <S, T> HostTargetMapping(
      Class<S> sourceType, Class<T> targetType, Predicate<S> accepts, Function<S, T> converter, HostAccess.TargetMappingPrecedence precedence
   ) {
      this.sourceType = sourceType;
      this.targetType = targetType;
      this.accepts = accepts;
      this.converter = converter;
      this.hostPriority = toHostPriority(precedence);
   }

   private static int toHostPriority(HostAccess.TargetMappingPrecedence p) {
      switch (p) {
         case HIGHEST:
            return 0;
         case HIGH:
            return 1;
         case LOW:
            return 2;
         case LOWEST:
            return 8;
         default:
            throw new AssertionError("invalid precedence");
      }
   }

   public int compareTo(HostTargetMapping o) {
      return Integer.compare(this.hostPriority, o.hostPriority);
   }
}
