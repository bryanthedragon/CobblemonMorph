package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.source.Source;
import java.util.function.Predicate;

public final class SuspensionFilter {
   private final boolean ignoreLanguageContextInitialization;
   private final boolean includeInternal;
   private final Predicate<Source> sourcePredicate;

   private SuspensionFilter() {
      this.ignoreLanguageContextInitialization = false;
      this.includeInternal = false;
      this.sourcePredicate = null;
   }

   private SuspensionFilter(boolean ignoreLanguageContextInitialization, boolean includeInternal, Predicate<Source> sourcePredicate) {
      this.ignoreLanguageContextInitialization = ignoreLanguageContextInitialization;
      this.includeInternal = includeInternal;
      this.sourcePredicate = sourcePredicate;
   }

   public static SuspensionFilter.Builder newBuilder() {
      return new SuspensionFilter().new Builder();
   }

   public boolean isIgnoreLanguageContextInitialization() {
      return this.ignoreLanguageContextInitialization;
   }

   boolean isInternalIncluded() {
      return this.includeInternal;
   }

   Predicate<Source> getSourcePredicate() {
      return this.sourcePredicate;
   }

   public final class Builder {
      private boolean ignoreLanguageContextInitialization;
      private boolean includeInternal = false;
      private Predicate<Source> sourcePredicate;

      private Builder() {
      }

      public SuspensionFilter.Builder ignoreLanguageContextInitialization(boolean ignore) {
         this.ignoreLanguageContextInitialization = ignore;
         return this;
      }

      public SuspensionFilter.Builder includeInternal(boolean internal) {
         this.includeInternal = internal;
         return this;
      }

      public SuspensionFilter.Builder sourceIs(Predicate<Source> filter) {
         this.sourcePredicate = filter;
         return this;
      }

      public SuspensionFilter build() {
         return new SuspensionFilter(this.ignoreLanguageContextInitialization, this.includeInternal, this.sourcePredicate);
      }
   }
}
