package org.graalvm.polyglot;

import java.util.function.Consumer;
import java.util.function.Predicate;

public final class ResourceLimits {
   private static final ResourceLimits EMPTY = new ResourceLimits(null);
   final Object receiver;

   ResourceLimits(Object receiver) {
      this.receiver = receiver;
   }

   public static ResourceLimits.Builder newBuilder() {
      return EMPTY.new Builder();
   }

   public final class Builder {
      long statementLimit;
      Predicate<Source> statementLimitSourceFilter;
      Consumer<ResourceLimitEvent> onLimit;

      Builder() {
      }

      public ResourceLimits.Builder statementLimit(long limit, Predicate<Source> sourceFilter) {
         if (limit < 0L) {
            throw new IllegalArgumentException("The statement limit must not be negative.");
         } else {
            this.statementLimit = limit;
            this.statementLimitSourceFilter = sourceFilter;
            return this;
         }
      }

      public ResourceLimits.Builder onLimit(Consumer<ResourceLimitEvent> onLimit) {
         this.onLimit = onLimit;
         return this;
      }

      public ResourceLimits build() {
         return new ResourceLimits(Engine.getImpl().buildLimits(this.statementLimit, this.statementLimitSourceFilter, this.onLimit));
      }
   }
}
