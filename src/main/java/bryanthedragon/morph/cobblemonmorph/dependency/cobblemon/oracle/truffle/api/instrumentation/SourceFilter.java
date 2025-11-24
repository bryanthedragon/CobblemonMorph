
package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.instrumentation.SourceSectionFilter;
import com.oracle.truffle.api.source.Source;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public final class SourceFilter {
    public static final SourceFilter ANY = SourceFilter.newBuilder().build();
    final SourceSectionFilter.EventFilterExpression[] expressions;

    private SourceFilter(SourceSectionFilter.EventFilterExpression[] expressions) {
        this.expressions = expressions;
    }

    public static Builder newBuilder() {
        return new SourceFilter(null).new Builder();
    }

    public final class Builder {
        private List<SourceSectionFilter.EventFilterExpression> expressions = new ArrayList<SourceSectionFilter.EventFilterExpression>();
        private boolean includeInternal = true;

        private Builder() {
        }

        public Builder sourceIs(Source ... source) {
            SourceSectionFilter.verifyNotNull(source);
            this.expressions.add(new SourceSectionFilter.EventFilterExpression.SourceIs(source));
            return this;
        }

        public Builder sourceIs(Predicate<Source> predicate) {
            if (predicate == null) {
                throw new IllegalArgumentException("Source predicate must not be null.");
            }
            this.expressions.add(new SourceSectionFilter.EventFilterExpression.SourceFilterIs(predicate));
            return this;
        }

        public Builder languageIs(final String ... languageIds) {
            SourceSectionFilter.verifyNotNull(languageIds);
            this.expressions.add(new SourceSectionFilter.EventFilterExpression.SourceFilterIs(new Predicate<Source>(){

                @Override
                public boolean test(Source source) {
                    String language = source.getLanguage();
                    if (language != null) {
                        for (String otherLanguage : languageIds) {
                            if (!otherLanguage.equals(language)) continue;
                            return true;
                        }
                    }
                    return false;
                }

                public String toString() {
                    return String.format("language ID is one-of %s", Arrays.toString(languageIds));
                }
            }));
            return this;
        }

        public Builder includeInternal(boolean internal) {
            this.includeInternal = internal;
            return this;
        }

        public SourceFilter build() {
            if (!this.includeInternal) {
                this.expressions.add(new SourceSectionFilter.EventFilterExpression.SourceFilterIs(new Predicate<Source>(){

                    @Override
                    public boolean test(Source source) {
                        return !source.isInternal();
                    }

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

