
package org.graalvm.polyglot;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.EconomicSet;
import org.graalvm.collections.Equivalence;
import org.graalvm.collections.UnmodifiableEconomicMap;
import org.graalvm.collections.UnmodifiableEconomicSet;
import org.graalvm.collections.UnmodifiableMapCursor;

public final class PolyglotAccess {
    private static final UnmodifiableEconomicSet<String> EMPTY = EconomicSet.create();
    private static final UnmodifiableEconomicMap<String, UnmodifiableEconomicSet<String>> EMPTY_EVAL_ACCESS = EconomicMap.create();
    private final EconomicMap<String, UnmodifiableEconomicSet<String>> evalAccess;
    private final EconomicSet<String> bindingsAccess;
    private final boolean allAccess;
    public static final PolyglotAccess NONE = new PolyglotAccess(false, null, null);
    public static final PolyglotAccess ALL = new PolyglotAccess(true, null, null);

    PolyglotAccess(boolean allAccess, EconomicMap<String, EconomicSet<String>> access, EconomicSet<String> bindingsAccess) {
        this.allAccess = allAccess;
        this.evalAccess = PolyglotAccess.copyMap(access);
        this.bindingsAccess = bindingsAccess;
    }

    private static EconomicMap<String, UnmodifiableEconomicSet<String>> copyMap(EconomicMap<String, EconomicSet<String>> values) {
        if (values == null) {
            return null;
        }
        EconomicMap<String, UnmodifiableEconomicSet<String>> newMap = EconomicMap.create(values.size());
        UnmodifiableMapCursor cursor = values.getEntries();
        while (cursor.advance()) {
            newMap.put((String)cursor.getKey(), EconomicSet.create(Equivalence.DEFAULT, (UnmodifiableEconomicSet)cursor.getValue()));
        }
        return newMap;
    }

    String validate(Set<String> availableLanguages) {
        if (this.evalAccess != null) {
            UnmodifiableMapCursor entries = this.evalAccess.getEntries();
            while (entries.advance()) {
                String invalidKey = null;
                if (!availableLanguages.contains(entries.getKey())) {
                    invalidKey = (String)entries.getKey();
                }
                if (invalidKey == null) {
                    for (String entry : (UnmodifiableEconomicSet)entries.getValue()) {
                        if (availableLanguages.contains(entry)) continue;
                        invalidKey = entry;
                        break;
                    }
                }
                if (invalidKey == null) continue;
                return String.format("Language '%s' configured in polyglot evaluation rule %s -> %s is not installed or available.", invalidKey, entries.getKey(), PolyglotAccess.toStringSet((UnmodifiableEconomicSet)entries.getValue()));
            }
        }
        if (this.bindingsAccess != null) {
            for (String language : this.bindingsAccess) {
                if (availableLanguages.contains(language)) continue;
                return String.format("Language '%s' configured in polyglot bindings access rule is not installed or available.", language);
            }
        }
        return null;
    }

    static String toStringSet(UnmodifiableEconomicSet<String> set2) {
        StringBuilder b = new StringBuilder();
        String sep = "";
        for (String entry : set2) {
            b.append(sep);
            b.append(entry);
            sep = ", ";
        }
        return b.toString();
    }

    UnmodifiableEconomicSet<String> getEvalAccess(String language) {
        if (this.allAccess) {
            return null;
        }
        if (this.evalAccess == null) {
            return EMPTY;
        }
        UnmodifiableEconomicSet a = (UnmodifiableEconomicSet)this.evalAccess.get(language);
        if (a == null) {
            return EMPTY;
        }
        return a;
    }

    UnmodifiableEconomicMap<String, UnmodifiableEconomicSet<String>> getEvalAccess() {
        if (this.allAccess) {
            return null;
        }
        if (this.evalAccess == null) {
            return EMPTY_EVAL_ACCESS;
        }
        return this.evalAccess;
    }

    UnmodifiableEconomicSet<String> getBindingsAccess() {
        if (this.allAccess) {
            return null;
        }
        if (this.bindingsAccess == null) {
            return EMPTY;
        }
        return this.bindingsAccess;
    }

    public static Builder newBuilder() {
        return NONE.new Builder();
    }

    public final class Builder {
        private EconomicMap<String, EconomicSet<String>> evalAccess;
        private EconomicSet<String> bindingsAccess;

        Builder() {
        }

        public Builder allowEvalBetween(String ... languages) {
            Objects.requireNonNull(languages);
            if (this.evalAccess == null) {
                this.evalAccess = EconomicMap.create();
            }
            for (String language : languages) {
                Objects.requireNonNull(language);
                EconomicSet<String> languageAccess = (EconomicSet<String>)this.evalAccess.get(language);
                if (languageAccess == null) {
                    languageAccess = EconomicSet.create();
                    this.evalAccess.put(language, languageAccess);
                }
                languageAccess.addAll(Arrays.asList(languages));
            }
            return this;
        }

        public Builder denyEvalBetween(String ... languages) {
            Objects.requireNonNull(languages);
            if (this.evalAccess != null) {
                for (String language : languages) {
                    Objects.requireNonNull(language);
                    EconomicSet languageAccess = (EconomicSet)this.evalAccess.get(language);
                    if (languageAccess == null) continue;
                    languageAccess.removeAll(Arrays.asList(languages));
                }
            }
            return this;
        }

        public Builder allowEval(String from, String to) {
            EconomicSet<String> languageAccess;
            Objects.requireNonNull(from);
            Objects.requireNonNull(to);
            if (this.evalAccess == null) {
                this.evalAccess = EconomicMap.create();
            }
            if ((languageAccess = (EconomicSet<String>)this.evalAccess.get(from)) == null) {
                languageAccess = EconomicSet.create();
                this.evalAccess.put(from, languageAccess);
            }
            languageAccess.add(to);
            return this;
        }

        public Builder denyEval(String from, String to) {
            EconomicSet languageAccess;
            Objects.requireNonNull(from);
            Objects.requireNonNull(to);
            if (this.evalAccess != null && (languageAccess = (EconomicSet)this.evalAccess.get(from)) != null) {
                languageAccess.remove(to);
            }
            return this;
        }

        public Builder allowBindingsAccess(String language) {
            Objects.requireNonNull(language);
            if (this.bindingsAccess == null) {
                this.bindingsAccess = EconomicSet.create();
            }
            this.bindingsAccess.add(language);
            return this;
        }

        public Builder denyBindingsAccess(String language) {
            Objects.requireNonNull(language);
            if (this.bindingsAccess != null) {
                this.bindingsAccess.remove(language);
            }
            return this;
        }

        public PolyglotAccess build() {
            return new PolyglotAccess(false, this.evalAccess, this.bindingsAccess);
        }
    }
}

