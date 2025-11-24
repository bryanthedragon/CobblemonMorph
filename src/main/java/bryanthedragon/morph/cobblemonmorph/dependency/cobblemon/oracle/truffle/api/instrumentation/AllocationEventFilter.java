
package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.nodes.LanguageInfo;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class AllocationEventFilter {
    public static final AllocationEventFilter ANY = AllocationEventFilter.newBuilder().build();
    private final Set<LanguageInfo> languageSet;

    AllocationEventFilter(Set<LanguageInfo> languages) {
        this.languageSet = languages;
    }

    public static Builder newBuilder() {
        return new AllocationEventFilter(null).new Builder();
    }

    boolean contains(LanguageInfo li) {
        if (this.languageSet == null) {
            return true;
        }
        return this.languageSet.contains(li);
    }

    public class Builder {
        LanguageInfo[] langs;

        Builder() {
        }

        public Builder languages(LanguageInfo ... languages) {
            if (languages.length == 0) {
                throw new IllegalArgumentException("At least one language must be provided.");
            }
            this.langs = languages;
            return this;
        }

        public AllocationEventFilter build() {
            Set<Object> langSet;
            if (this.langs == null) {
                langSet = null;
            } else if (this.langs.length == 1) {
                langSet = Collections.singleton(this.langs[0]);
            } else {
                langSet = new HashSet();
                for (LanguageInfo li : this.langs) {
                    langSet.add(li);
                }
            }
            return new AllocationEventFilter(langSet);
        }
    }
}

