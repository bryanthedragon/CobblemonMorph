package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.source.Source;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public final class SourceFilter {
   public static final SourceFilter ANY = newBuilder().build();
   final SourceSectionFilter.EventFilterExpression[] expressions;

   private SourceFilter(SourceSectionFilter.EventFilterExpression[] expressions) {
      this.expressions = expressions;
   }

   public static SourceFilter.Builder newBuilder() {
      return new SourceFilter(null).new Builder();
   }

   public final class Builder {
      private List<SourceSectionFilter.EventFilterExpression> expressions = new ArrayList<>();
      private boolean includeInternal = true;

      private Builder() {
      }

      public SourceFilter.Builder sourceIs(Source... source) {
         SourceSectionFilter.verifyNotNull(source);
         this.expressions.add(new SourceSectionFilter.EventFilterExpression.SourceIs(source));
         return this;
      }

      public SourceFilter.Builder sourceIs(Predicate<Source> predicate) {
         if (predicate == null) {
            throw new IllegalArgumentException("Source predicate must not be null.");
         } else {
            this.expressions.add(new SourceSectionFilter.EventFilterExpression.SourceFilterIs(predicate));
            return this;
         }
      }

      public SourceFilter.Builder languageIs(String... languageIds) {
         SourceSectionFilter.verifyNotNull(languageIds);
         this.expressions.add(new SourceSectionFilter.EventFilterExpression.SourceFilterIs(new Predicate<Source>() {
            public boolean test(Source source) {
               String language = source.getLanguage();
               if (language != null) {
                  for (String otherLanguage : languageIds) {
                     if (otherLanguage.equals(language)) {
                        return true;
                     }
                  }
               }

               return false;
            }

            @Override
            public String toString() {
               return String.format("language ID is one-of %s", Arrays.toString((Object[])languageIds));
            }
         }));
         return this;
      }

      public SourceFilter.Builder includeInternal(boolean internal) {
         this.includeInternal = internal;
         return this;
      }

      public SourceFilter build() {
         if (!this.includeInternal) {
            this.expressions.add(new SourceSectionFilter.EventFilterExpression.SourceFilterIs(new Predicate<Source>() {
               public boolean test(Source source) {
                  return !source.isInternal();
               }

               @Override
               public String toString() {
                  return "source is not internal";
               }
            }));
         }

         Collections.sort(this.expressions);
         return new SourceFilter(this.expressions.toArray(new SourceSectionFilter.EventFilterExpression[0]));
      }
   }
}
