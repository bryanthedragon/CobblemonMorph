package com.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.impl.FormattedStringBuilder;
import com.cobblemon.mod.relocations.ibm.icu.impl.FormattedValueStringBuilderImpl;
import com.cobblemon.mod.relocations.ibm.icu.impl.ICUCache;
import com.cobblemon.mod.relocations.ibm.icu.impl.ICUResourceBundle;
import com.cobblemon.mod.relocations.ibm.icu.impl.SimpleCache;
import com.cobblemon.mod.relocations.ibm.icu.impl.SimpleFormatterImpl;
import com.cobblemon.mod.relocations.ibm.icu.impl.Utility;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import com.cobblemon.mod.relocations.ibm.icu.util.UResourceBundle;
import java.io.InvalidObjectException;
import java.text.AttributedCharacterIterator;
import java.text.Format;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Pattern;

public final class ListFormatter {
   private final String start;
   private final String middle;
   private final ULocale locale;
   private final ListFormatter.PatternHandler patternHandler;
   private static final String compiledY = compilePattern("{0} y {1}", new StringBuilder());
   private static final String compiledE = compilePattern("{0} e {1}", new StringBuilder());
   private static final String compiledO = compilePattern("{0} o {1}", new StringBuilder());
   private static final String compiledU = compilePattern("{0} u {1}", new StringBuilder());
   private static final Pattern changeToE = Pattern.compile("(i.*|hi|hi[^ae].*)", 2);
   private static final Pattern changeToU = Pattern.compile("((o|ho|8).*|11)", 2);
   private static final String compiledVav = compilePattern("{0} ו{1}", new StringBuilder());
   private static final String compiledVavDash = compilePattern("{0} ו-{1}", new StringBuilder());
   private static final Pattern changeToVavDash = Pattern.compile("^[\\P{InHebrew}].*$");
   static ListFormatter.Cache cache = new ListFormatter.Cache();

   @Deprecated
   public ListFormatter(String two, String start, String middle, String end) {
      this(
         compilePattern(two, new StringBuilder()),
         compilePattern(start, new StringBuilder()),
         compilePattern(middle, new StringBuilder()),
         compilePattern(end, new StringBuilder()),
         null
      );
   }

   private ListFormatter(String two, String start, String middle, String end, ULocale locale) {
      this.start = start;
      this.middle = middle;
      this.locale = locale;
      this.patternHandler = this.createPatternHandler(two, end);
   }

   private static String compilePattern(String pattern, StringBuilder sb) {
      return SimpleFormatterImpl.compileToStringMinMaxArguments(pattern, sb, 2, 2);
   }

   public static ListFormatter getInstance(ULocale locale, ListFormatter.Type type, ListFormatter.Width width) {
      String styleName = typeWidthToStyleString(type, width);
      if (styleName == null) {
         throw new IllegalArgumentException("Invalid list format type/width");
      } else {
         return cache.get(locale, styleName);
      }
   }

   public static ListFormatter getInstance(Locale locale, ListFormatter.Type type, ListFormatter.Width width) {
      return getInstance(ULocale.forLocale(locale), type, width);
   }

   public static ListFormatter getInstance(ULocale locale) {
      return getInstance(locale, ListFormatter.Type.AND, ListFormatter.Width.WIDE);
   }

   public static ListFormatter getInstance(Locale locale) {
      return getInstance(ULocale.forLocale(locale), ListFormatter.Type.AND, ListFormatter.Width.WIDE);
   }

   public static ListFormatter getInstance() {
      return getInstance(ULocale.getDefault(ULocale.Category.FORMAT));
   }

   public String format(Object... items) {
      return this.format(Arrays.asList(items));
   }

   public String format(Collection<?> items) {
      return this.formatImpl(items, false).toString();
   }

   public ListFormatter.FormattedList formatToValue(Object... items) {
      return this.formatToValue(Arrays.asList(items));
   }

   public ListFormatter.FormattedList formatToValue(Collection<?> items) {
      return this.formatImpl(items, true).toValue();
   }

   ListFormatter.FormattedListBuilder formatImpl(Collection<?> items, boolean needsFields) {
      Iterator<?> it = items.iterator();
      int count = items.size();
      switch (count) {
         case 0:
            return new ListFormatter.FormattedListBuilder("", needsFields);
         case 1:
            return new ListFormatter.FormattedListBuilder(it.next(), needsFields);
         case 2:
            Object first = it.next();
            Object second = it.next();
            return new ListFormatter.FormattedListBuilder(first, needsFields).append(this.patternHandler.getTwoPattern(String.valueOf(second)), second, 1);
         default:
            ListFormatter.FormattedListBuilder builder = new ListFormatter.FormattedListBuilder(it.next(), needsFields);
            builder.append(this.start, it.next(), 1);

            for (int idx = 2; idx < count - 1; idx++) {
               builder.append(this.middle, it.next(), idx);
            }

            Object last = it.next();
            return builder.append(this.patternHandler.getEndPattern(String.valueOf(last)), last, count - 1);
      }
   }

   private ListFormatter.PatternHandler createPatternHandler(String two, String end) {
      if (this.locale != null) {
         String language = this.locale.getLanguage();
         if (language.equals("es")) {
            boolean twoIsY = two.equals(compiledY);
            boolean endIsY = end.equals(compiledY);
            if (!twoIsY && !endIsY) {
               boolean twoIsO = two.equals(compiledO);
               boolean endIsO = end.equals(compiledO);
               if (!twoIsO && !endIsO) {
                  return new ListFormatter.StaticHandler(two, end);
               }

               return new ListFormatter.ContextualHandler(changeToU, twoIsO ? compiledU : two, two, endIsO ? compiledU : end, end);
            }

            return new ListFormatter.ContextualHandler(changeToE, twoIsY ? compiledE : two, two, endIsY ? compiledE : end, end);
         } else if (language.equals("he") || language.equals("iw")) {
            boolean twoIsVav = two.equals(compiledVav);
            boolean endIsVav = end.equals(compiledVav);
            if (twoIsVav || endIsVav) {
               return new ListFormatter.ContextualHandler(changeToVavDash, twoIsVav ? compiledVavDash : two, two, endIsVav ? compiledVavDash : end, end);
            }
         }
      }

      return new ListFormatter.StaticHandler(two, end);
   }

   public String getPatternForNumItems(int count) {
      if (count <= 0) {
         throw new IllegalArgumentException("count must be > 0");
      } else {
         ArrayList<String> list = new ArrayList<>();

         for (int i = 0; i < count; i++) {
            list.add(String.format("{%d}", i));
         }

         return this.format(list);
      }
   }

   @Deprecated
   public ULocale getLocale() {
      return this.locale;
   }

   static String typeWidthToStyleString(ListFormatter.Type type, ListFormatter.Width width) {
      switch (type) {
         case AND:
            switch (width) {
               case WIDE:
                  return "standard";
               case SHORT:
                  return "standard-short";
               case NARROW:
                  return "standard-narrow";
               default:
                  return null;
            }
         case OR:
            switch (width) {
               case WIDE:
                  return "or";
               case SHORT:
                  return "or-short";
               case NARROW:
                  return "or-narrow";
               default:
                  return null;
            }
         case UNITS:
            switch (width) {
               case WIDE:
                  return "unit";
               case SHORT:
                  return "unit-short";
               case NARROW:
                  return "unit-narrow";
            }
      }

      return null;
   }

   private static class Cache {
      private final ICUCache<String, ListFormatter> cache = new SimpleCache<>();

      private Cache() {
      }

      public ListFormatter get(ULocale locale, String style) {
         String key = String.format("%s:%s", locale.toString(), style);
         ListFormatter result = this.cache.get(key);
         if (result == null) {
            result = load(locale, style);
            this.cache.put(key, result);
         }

         return result;
      }

      private static ListFormatter load(ULocale ulocale, String style) {
         ICUResourceBundle r = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", ulocale);
         StringBuilder sb = new StringBuilder();
         return new ListFormatter(
            ListFormatter.compilePattern(r.getWithFallback("listPattern/" + style + "/2").getString(), sb),
            ListFormatter.compilePattern(r.getWithFallback("listPattern/" + style + "/start").getString(), sb),
            ListFormatter.compilePattern(r.getWithFallback("listPattern/" + style + "/middle").getString(), sb),
            ListFormatter.compilePattern(r.getWithFallback("listPattern/" + style + "/end").getString(), sb),
            ulocale
         );
      }
   }

   private static final class ContextualHandler implements ListFormatter.PatternHandler {
      private final Pattern regexp;
      private final String thenTwoPattern;
      private final String elseTwoPattern;
      private final String thenEndPattern;
      private final String elseEndPattern;

      ContextualHandler(Pattern regexp, String thenTwo, String elseTwo, String thenEnd, String elseEnd) {
         this.regexp = regexp;
         this.thenTwoPattern = thenTwo;
         this.elseTwoPattern = elseTwo;
         this.thenEndPattern = thenEnd;
         this.elseEndPattern = elseEnd;
      }

      @Override
      public String getTwoPattern(String text) {
         return this.regexp.matcher(text).matches() ? this.thenTwoPattern : this.elseTwoPattern;
      }

      @Override
      public String getEndPattern(String text) {
         return this.regexp.matcher(text).matches() ? this.thenEndPattern : this.elseEndPattern;
      }
   }

   public static final class Field extends Format.Field {
      private static final long serialVersionUID = -8071145668708265437L;
      public static ListFormatter.Field LITERAL = new ListFormatter.Field("literal");
      public static ListFormatter.Field ELEMENT = new ListFormatter.Field("element");

      private Field(String name) {
         super(name);
      }

      @Deprecated
      @Override
      protected Object readResolve() throws InvalidObjectException {
         if (this.getName().equals(LITERAL.getName())) {
            return LITERAL;
         } else if (this.getName().equals(ELEMENT.getName())) {
            return ELEMENT;
         } else {
            throw new InvalidObjectException("An invalid object.");
         }
      }
   }

   public static final class FormattedList implements FormattedValue {
      private final FormattedStringBuilder string;

      FormattedList(FormattedStringBuilder string) {
         this.string = string;
      }

      @Override
      public String toString() {
         return this.string.toString();
      }

      @Override
      public int length() {
         return this.string.length();
      }

      @Override
      public char charAt(int index) {
         return this.string.charAt(index);
      }

      @Override
      public CharSequence subSequence(int start, int end) {
         return this.string.subString(start, end);
      }

      @Override
      public <A extends Appendable> A appendTo(A appendable) {
         return Utility.appendTo(this.string, appendable);
      }

      @Override
      public boolean nextPosition(ConstrainedFieldPosition cfpos) {
         return FormattedValueStringBuilderImpl.nextPosition(this.string, cfpos, null);
      }

      @Override
      public AttributedCharacterIterator toCharacterIterator() {
         return FormattedValueStringBuilderImpl.toCharacterIterator(this.string, null);
      }
   }

   static class FormattedListBuilder {
      private FormattedStringBuilder string = new FormattedStringBuilder();
      boolean needsFields;

      public FormattedListBuilder(Object start, boolean needsFields) {
         this.needsFields = needsFields;
         this.string.setAppendableField(ListFormatter.Field.LITERAL);
         this.appendElement(start, 0);
      }

      public ListFormatter.FormattedListBuilder append(String compiledPattern, Object next, int position) {
         assert SimpleFormatterImpl.getArgumentLimit(compiledPattern) == 2;

         this.string.setAppendIndex(0);
         long state = 0L;

         while (true) {
            state = SimpleFormatterImpl.IterInternal.step(state, compiledPattern, this.string);
            if (state == -1L) {
               return this;
            }

            int argIndex = SimpleFormatterImpl.IterInternal.getArgIndex(state);
            if (argIndex == 0) {
               this.string.setAppendIndex(this.string.length());
            } else {
               this.appendElement(next, position);
            }
         }
      }

      private void appendElement(Object element, int position) {
         String elementString = element.toString();
         if (this.needsFields) {
            FormattedValueStringBuilderImpl.SpanFieldPlaceholder field = new FormattedValueStringBuilderImpl.SpanFieldPlaceholder();
            field.spanField = ListFormatter.SpanField.LIST_SPAN;
            field.normalField = ListFormatter.Field.ELEMENT;
            field.value = position;
            field.start = -1;
            field.length = elementString.length();
            this.string.append(elementString, field);
         } else {
            this.string.append(elementString, null);
         }
      }

      public void appendTo(Appendable appendable) {
         Utility.appendTo(this.string, appendable);
      }

      public int getOffset(int fieldPositionFoundIndex) {
         return FormattedValueStringBuilderImpl.findSpan(this.string, fieldPositionFoundIndex);
      }

      @Override
      public String toString() {
         return this.string.toString();
      }

      public ListFormatter.FormattedList toValue() {
         return new ListFormatter.FormattedList(this.string);
      }
   }

   private interface PatternHandler {
      String getTwoPattern(String var1);

      String getEndPattern(String var1);
   }

   public static final class SpanField extends UFormat.SpanField {
      private static final long serialVersionUID = 3563544214705634403L;
      public static final ListFormatter.SpanField LIST_SPAN = new ListFormatter.SpanField("list-span");

      private SpanField(String name) {
         super(name);
      }

      @Deprecated
      @Override
      protected Object readResolve() throws InvalidObjectException {
         if (this.getName().equals(LIST_SPAN.getName())) {
            return LIST_SPAN;
         } else {
            throw new InvalidObjectException("An invalid object.");
         }
      }
   }

   private static final class StaticHandler implements ListFormatter.PatternHandler {
      private final String twoPattern;
      private final String endPattern;

      StaticHandler(String two, String end) {
         this.twoPattern = two;
         this.endPattern = end;
      }

      @Override
      public String getTwoPattern(String text) {
         return this.twoPattern;
      }

      @Override
      public String getEndPattern(String text) {
         return this.endPattern;
      }
   }

   public static enum Type {
      AND,
      OR,
      UNITS;
   }

   public static enum Width {
      WIDE,
      SHORT,
      NARROW;
   }
}
