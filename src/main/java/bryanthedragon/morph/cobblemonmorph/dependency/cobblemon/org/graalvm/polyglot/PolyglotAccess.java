package org.graalvm.polyglot;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.EconomicSet;
import org.graalvm.collections.Equivalence;
import org.graalvm.collections.MapCursor;
import org.graalvm.collections.UnmodifiableEconomicMap;
import org.graalvm.collections.UnmodifiableEconomicSet;

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
      this.evalAccess = copyMap(access);
      this.bindingsAccess = bindingsAccess;
   }

   private static EconomicMap<String, UnmodifiableEconomicSet<String>> copyMap(EconomicMap<String, EconomicSet<String>> values) {
      if (values == null) {
         return null;
      } else {
         EconomicMap<String, UnmodifiableEconomicSet<String>> newMap = EconomicMap.create(values.size());
         MapCursor<String, EconomicSet<String>> cursor = values.getEntries();

         while (cursor.advance()) {
            newMap.put(cursor.getKey(), EconomicSet.create(Equivalence.DEFAULT, cursor.getValue()));
         }

         return newMap;
      }
   }

   String validate(Set<String> availableLanguages) {
      if (this.evalAccess != null) {
         MapCursor<String, UnmodifiableEconomicSet<String>> entries = this.evalAccess.getEntries();

         while (entries.advance()) {
            String invalidKey = null;
            if (!availableLanguages.contains(entries.getKey())) {
               invalidKey = entries.getKey();
            }

            if (invalidKey == null) {
               for (String entry : entries.getValue()) {
                  if (!availableLanguages.contains(entry)) {
                     invalidKey = entry;
                     break;
                  }
               }
            }

            if (invalidKey != null) {
               return String.format(
                  "Language '%s' configured in polyglot evaluation rule %s -> %s is not installed or available.",
                  invalidKey,
                  entries.getKey(),
                  toStringSet(entries.getValue())
               );
            }
         }
      }

      if (this.bindingsAccess != null) {
         for (String language : this.bindingsAccess) {
            if (!availableLanguages.contains(language)) {
               return String.format("Language '%s' configured in polyglot bindings access rule is not installed or available.", language);
            }
         }
      }

      return null;
   }

   static String toStringSet(UnmodifiableEconomicSet<String> set) {
      StringBuilder b = new StringBuilder();
      String sep = "";

      for (String entry : set) {
         b.append(sep);
         b.append(entry);
         sep = ", ";
      }

      return b.toString();
   }

   UnmodifiableEconomicSet<String> getEvalAccess(String language) {
      if (this.allAccess) {
         return null;
      } else if (this.evalAccess == null) {
         return EMPTY;
      } else {
         UnmodifiableEconomicSet<String> a = this.evalAccess.get(language);
         return a == null ? EMPTY : a;
      }
   }

   UnmodifiableEconomicMap<String, UnmodifiableEconomicSet<String>> getEvalAccess() {
      if (this.allAccess) {
         return null;
      } else {
         return (UnmodifiableEconomicMap<String, UnmodifiableEconomicSet<String>>)(this.evalAccess == null ? EMPTY_EVAL_ACCESS : this.evalAccess);
      }
   }

   UnmodifiableEconomicSet<String> getBindingsAccess() {
      if (this.allAccess) {
         return null;
      } else {
         return (UnmodifiableEconomicSet<String>)(this.bindingsAccess == null ? EMPTY : this.bindingsAccess);
      }
   }

   public static PolyglotAccess.Builder newBuilder() {
      return NONE.new Builder();
   }

   public final class Builder {
      private EconomicMap<String, EconomicSet<String>> evalAccess;
      private EconomicSet<String> bindingsAccess;

      Builder() {
      }

      public PolyglotAccess.Builder allowEvalBetween(String... languages) {
         Objects.requireNonNull(languages);
         if (this.evalAccess == null) {
            this.evalAccess = EconomicMap.create();
         }

         for (String language : languages) {
            Objects.requireNonNull(language);
            EconomicSet<String> languageAccess = this.evalAccess.get(language);
            if (languageAccess == null) {
               languageAccess = EconomicSet.create();
               this.evalAccess.put(language, languageAccess);
            }

            languageAccess.addAll(Arrays.asList(languages));
         }

         return this;
      }

      public PolyglotAccess.Builder denyEvalBetween(String... languages) {
         Objects.requireNonNull(languages);
         if (this.evalAccess != null) {
            for (String language : languages) {
               Objects.requireNonNull(language);
               EconomicSet<String> languageAccess = this.evalAccess.get(language);
               if (languageAccess != null) {
                  languageAccess.removeAll(Arrays.asList(languages));
               }
            }
         }

         return this;
      }

      public PolyglotAccess.Builder allowEval(String from, String to) {
         Objects.requireNonNull(from);
         Objects.requireNonNull(to);
         if (this.evalAccess == null) {
            this.evalAccess = EconomicMap.create();
         }

         EconomicSet<String> languageAccess = this.evalAccess.get(from);
         if (languageAccess == null) {
            languageAccess = EconomicSet.create();
            this.evalAccess.put(from, languageAccess);
         }

         languageAccess.add(to);
         return this;
      }

      public PolyglotAccess.Builder denyEval(String from, String to) {
         Objects.requireNonNull(from);
         Objects.requireNonNull(to);
         if (this.evalAccess != null) {
            EconomicSet<String> languageAccess = this.evalAccess.get(from);
            if (languageAccess != null) {
               languageAccess.remove(to);
            }
         }

         return this;
      }

      public PolyglotAccess.Builder allowBindingsAccess(String language) {
         Objects.requireNonNull(language);
         if (this.bindingsAccess == null) {
            this.bindingsAccess = EconomicSet.create();
         }

         this.bindingsAccess.add(language);
         return this;
      }

      public PolyglotAccess.Builder denyBindingsAccess(String language) {
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
