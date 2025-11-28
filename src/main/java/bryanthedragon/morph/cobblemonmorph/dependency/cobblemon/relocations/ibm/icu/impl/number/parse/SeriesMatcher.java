package com.cobblemon.mod.relocations.ibm.icu.impl.number.parse;

import com.cobblemon.mod.relocations.ibm.icu.impl.StringSegment;
import java.util.ArrayList;
import java.util.List;

public class SeriesMatcher implements NumberParseMatcher {
   protected List<NumberParseMatcher> matchers = null;
   protected boolean frozen = false;

   public void addMatcher(NumberParseMatcher matcher) {
      assert !this.frozen;

      if (this.matchers == null) {
         this.matchers = new ArrayList<>();
      }

      this.matchers.add(matcher);
   }

   public void freeze() {
      this.frozen = true;
   }

   public int length() {
      return this.matchers == null ? 0 : this.matchers.size();
   }

   @Override
   public boolean match(StringSegment segment, ParsedNumber result) {
      assert this.frozen;

      if (this.matchers == null) {
         return false;
      } else {
         ParsedNumber backup = new ParsedNumber();
         backup.copyFrom(result);
         int initialOffset = segment.getOffset();
         boolean maybeMore = true;
         int i = 0;

         while (i < this.matchers.size()) {
            NumberParseMatcher matcher = this.matchers.get(i);
            int matcherOffset = segment.getOffset();
            if (segment.length() != 0) {
               maybeMore = matcher.match(segment, result);
            } else {
               maybeMore = true;
            }

            boolean success = segment.getOffset() != matcherOffset;
            boolean isFlexible = matcher instanceof NumberParseMatcher.Flexible;
            if (!success || !isFlexible) {
               if (success) {
                  i++;
                  if (i < this.matchers.size() && segment.getOffset() != result.charEnd && result.charEnd > matcherOffset) {
                     segment.setOffset(result.charEnd);
                  }
               } else {
                  if (!isFlexible) {
                     segment.setOffset(initialOffset);
                     result.copyFrom(backup);
                     return maybeMore;
                  }

                  i++;
               }
            }
         }

         return maybeMore;
      }
   }

   @Override
   public boolean smokeTest(StringSegment segment) {
      assert this.frozen;

      if (this.matchers == null) {
         return false;
      } else {
         assert !(this.matchers.get(0) instanceof NumberParseMatcher.Flexible);

         return this.matchers.get(0).smokeTest(segment);
      }
   }

   @Override
   public void postProcess(ParsedNumber result) {
      assert this.frozen;

      if (this.matchers != null) {
         for (int i = 0; i < this.matchers.size(); i++) {
            NumberParseMatcher matcher = this.matchers.get(i);
            matcher.postProcess(result);
         }
      }
   }

   @Override
   public String toString() {
      return "<SeriesMatcher " + this.matchers + ">";
   }
}
