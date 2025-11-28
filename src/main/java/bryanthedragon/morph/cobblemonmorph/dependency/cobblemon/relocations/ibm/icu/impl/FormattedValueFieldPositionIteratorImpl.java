package com.cobblemon.mod.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.text.ConstrainedFieldPosition;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.FieldPosition;
import java.text.Format.Field;
import java.util.List;

public class FormattedValueFieldPositionIteratorImpl {
   private FormattedValueFieldPositionIteratorImpl() {
   }

   public static boolean nextPosition(List<FieldPosition> attributes, ConstrainedFieldPosition cfpos) {
      int numFields = attributes.size();

      int i;
      for (i = (int)cfpos.getInt64IterationContext(); i < numFields; i++) {
         FieldPosition fpos = attributes.get(i);
         Field field = fpos.getFieldAttribute();
         Object value = null;
         if (field instanceof FormattedValueFieldPositionIteratorImpl.FieldWithValue) {
            value = ((FormattedValueFieldPositionIteratorImpl.FieldWithValue)field).value;
            field = ((FormattedValueFieldPositionIteratorImpl.FieldWithValue)field).field;
         }

         if (cfpos.matchesField(field, value)) {
            int start = fpos.getBeginIndex();
            int limit = fpos.getEndIndex();
            cfpos.setState(field, value, start, limit);
            break;
         }
      }

      cfpos.setInt64IterationContext(i == numFields ? i : i + 1);
      return i < numFields;
   }

   public static AttributedCharacterIterator toCharacterIterator(CharSequence cs, List<FieldPosition> attributes) {
      AttributedString as = new AttributedString(cs.toString());

      for (int i = 0; i < attributes.size(); i++) {
         FieldPosition fp = attributes.get(i);
         Field field = fp.getFieldAttribute();
         Object value = field;
         if (field instanceof FormattedValueFieldPositionIteratorImpl.FieldWithValue) {
            value = ((FormattedValueFieldPositionIteratorImpl.FieldWithValue)field).value;
            field = ((FormattedValueFieldPositionIteratorImpl.FieldWithValue)field).field;
         }

         as.addAttribute(field, value, fp.getBeginIndex(), fp.getEndIndex());
      }

      return as.getIterator();
   }

   public static void addOverlapSpans(List<FieldPosition> attributes, Field spanField, int firstIndex) {
      int s1a = Integer.MAX_VALUE;
      int s1b = 0;
      int s2a = Integer.MAX_VALUE;
      int s2b = 0;
      int numFields = attributes.size();

      for (int i = 0; i < numFields; i++) {
         FieldPosition fp1 = attributes.get(i);

         for (int j = i + 1; j < numFields; j++) {
            FieldPosition fp2 = attributes.get(j);
            if (fp1.getFieldAttribute() == fp2.getFieldAttribute()) {
               s1a = Math.min(s1a, fp1.getBeginIndex());
               s1b = Math.max(s1b, fp1.getEndIndex());
               s2a = Math.min(s2a, fp2.getBeginIndex());
               s2b = Math.max(s2b, fp2.getEndIndex());
               break;
            }
         }
      }

      if (s1a != Integer.MAX_VALUE) {
         FieldPosition newPos = new FieldPosition(new FormattedValueFieldPositionIteratorImpl.FieldWithValue(spanField, firstIndex));
         newPos.setBeginIndex(s1a);
         newPos.setEndIndex(s1b);
         attributes.add(newPos);
         newPos = new FieldPosition(new FormattedValueFieldPositionIteratorImpl.FieldWithValue(spanField, 1 - firstIndex));
         newPos.setBeginIndex(s2a);
         newPos.setEndIndex(s2b);
         attributes.add(newPos);
      }
   }

   public static void sort(List<FieldPosition> attributes) {
      int numFields = attributes.size();

      boolean isSorted;
      do {
         isSorted = true;

         for (int i = 0; i < numFields - 1; i++) {
            FieldPosition fp1 = attributes.get(i);
            FieldPosition fp2 = attributes.get(i + 1);
            long comparison = 0L;
            if (fp1.getBeginIndex() != fp2.getBeginIndex()) {
               comparison = fp2.getBeginIndex() - fp1.getBeginIndex();
            } else if (fp1.getEndIndex() != fp2.getEndIndex()) {
               comparison = fp1.getEndIndex() - fp2.getEndIndex();
            } else if (fp1.getFieldAttribute() != fp2.getFieldAttribute()) {
               boolean fp1isSpan = fp1.getFieldAttribute() instanceof FormattedValueFieldPositionIteratorImpl.FieldWithValue;
               boolean fp2isSpan = fp2.getFieldAttribute() instanceof FormattedValueFieldPositionIteratorImpl.FieldWithValue;
               if (fp1isSpan && !fp2isSpan) {
                  comparison = 1L;
               } else if (fp2isSpan && !fp1isSpan) {
                  comparison = -1L;
               } else {
                  comparison = fp1.hashCode() - fp2.hashCode();
               }
            }

            if (comparison < 0L) {
               isSorted = false;
               attributes.set(i, fp2);
               attributes.set(i + 1, fp1);
            }
         }
      } while (!isSorted);
   }

   private static class FieldWithValue extends Field {
      private static final long serialVersionUID = -3850076447157793465L;
      public final Field field;
      public final int value;

      public FieldWithValue(Field field, int value) {
         super(field.toString());
         this.field = field;
         this.value = value;
      }
   }
}
