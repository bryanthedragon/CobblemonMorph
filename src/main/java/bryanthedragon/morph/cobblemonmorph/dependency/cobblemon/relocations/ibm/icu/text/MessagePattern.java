package com.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.impl.ICUConfig;
import com.cobblemon.mod.relocations.ibm.icu.impl.PatternProps;
import com.cobblemon.mod.relocations.ibm.icu.util.Freezable;
import com.cobblemon.mod.relocations.ibm.icu.util.ICUCloneNotSupportedException;
import java.util.ArrayList;
import java.util.Locale;

public final class MessagePattern implements Cloneable, Freezable<MessagePattern> {
   public static final int ARG_NAME_NOT_NUMBER = -1;
   public static final int ARG_NAME_NOT_VALID = -2;
   public static final double NO_NUMERIC_VALUE = -1.23456789E8;
   private static final int MAX_PREFIX_LENGTH = 24;
   private MessagePattern.ApostropheMode aposMode;
   private String msg;
   private ArrayList<MessagePattern.Part> parts = new ArrayList<>();
   private ArrayList<Double> numericValues;
   private boolean hasArgNames;
   private boolean hasArgNumbers;
   private boolean needsAutoQuoting;
   private volatile boolean frozen;
   private static final MessagePattern.ApostropheMode defaultAposMode = MessagePattern.ApostropheMode.valueOf(
      ICUConfig.get("com.cobblemon.mod.relocations.ibm.icu.text.MessagePattern.ApostropheMode", "DOUBLE_OPTIONAL")
   );
   private static final MessagePattern.ArgType[] argTypes = MessagePattern.ArgType.values();

   public MessagePattern() {
      this.aposMode = defaultAposMode;
   }

   public MessagePattern(MessagePattern.ApostropheMode mode) {
      this.aposMode = mode;
   }

   public MessagePattern(String pattern) {
      this.aposMode = defaultAposMode;
      this.parse(pattern);
   }

   public MessagePattern parse(String pattern) {
      this.preParse(pattern);
      this.parseMessage(0, 0, 0, MessagePattern.ArgType.NONE);
      this.postParse();
      return this;
   }

   public MessagePattern parseChoiceStyle(String pattern) {
      this.preParse(pattern);
      this.parseChoiceStyle(0, 0);
      this.postParse();
      return this;
   }

   public MessagePattern parsePluralStyle(String pattern) {
      this.preParse(pattern);
      this.parsePluralOrSelectStyle(MessagePattern.ArgType.PLURAL, 0, 0);
      this.postParse();
      return this;
   }

   public MessagePattern parseSelectStyle(String pattern) {
      this.preParse(pattern);
      this.parsePluralOrSelectStyle(MessagePattern.ArgType.SELECT, 0, 0);
      this.postParse();
      return this;
   }

   public void clear() {
      if (this.isFrozen()) {
         throw new UnsupportedOperationException("Attempt to clear() a frozen MessagePattern instance.");
      } else {
         this.msg = null;
         this.hasArgNames = this.hasArgNumbers = false;
         this.needsAutoQuoting = false;
         this.parts.clear();
         if (this.numericValues != null) {
            this.numericValues.clear();
         }
      }
   }

   public void clearPatternAndSetApostropheMode(MessagePattern.ApostropheMode mode) {
      this.clear();
      this.aposMode = mode;
   }

   @Override
   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (other != null && this.getClass() == other.getClass()) {
         MessagePattern o = (MessagePattern)other;
         return this.aposMode.equals(o.aposMode) && (this.msg == null ? o.msg == null : this.msg.equals(o.msg)) && this.parts.equals(o.parts);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return (this.aposMode.hashCode() * 37 + (this.msg != null ? this.msg.hashCode() : 0)) * 37 + this.parts.hashCode();
   }

   public MessagePattern.ApostropheMode getApostropheMode() {
      return this.aposMode;
   }

   boolean jdkAposMode() {
      return this.aposMode == MessagePattern.ApostropheMode.DOUBLE_REQUIRED;
   }

   public String getPatternString() {
      return this.msg;
   }

   public boolean hasNamedArguments() {
      return this.hasArgNames;
   }

   public boolean hasNumberedArguments() {
      return this.hasArgNumbers;
   }

   @Override
   public String toString() {
      return this.msg;
   }

   public static int validateArgumentName(String name) {
      return !PatternProps.isIdentifier(name) ? -2 : parseArgNumber(name, 0, name.length());
   }

   public String autoQuoteApostropheDeep() {
      if (!this.needsAutoQuoting) {
         return this.msg;
      } else {
         StringBuilder modified = null;
         int count = this.countParts();
         int i = count;

         while (i > 0) {
            MessagePattern.Part part;
            if ((part = this.getPart(--i)).getType() == MessagePattern.Part.Type.INSERT_CHAR) {
               if (modified == null) {
                  modified = new StringBuilder(this.msg.length() + 10).append(this.msg);
               }

               modified.insert(part.index, (char)part.value);
            }
         }

         return modified == null ? this.msg : modified.toString();
      }
   }

   public int countParts() {
      return this.parts.size();
   }

   public MessagePattern.Part getPart(int i) {
      return this.parts.get(i);
   }

   public MessagePattern.Part.Type getPartType(int i) {
      return this.parts.get(i).type;
   }

   public int getPatternIndex(int partIndex) {
      return this.parts.get(partIndex).index;
   }

   public String getSubstring(MessagePattern.Part part) {
      int index = part.index;
      return this.msg.substring(index, index + part.length);
   }

   public boolean partSubstringMatches(MessagePattern.Part part, String s) {
      return part.length == s.length() && this.msg.regionMatches(part.index, s, 0, part.length);
   }

   public double getNumericValue(MessagePattern.Part part) {
      MessagePattern.Part.Type type = part.type;
      if (type == MessagePattern.Part.Type.ARG_INT) {
         return part.value;
      } else {
         return type == MessagePattern.Part.Type.ARG_DOUBLE ? this.numericValues.get(part.value) : -1.23456789E8;
      }
   }

   public double getPluralOffset(int pluralStart) {
      MessagePattern.Part part = this.parts.get(pluralStart);
      return part.type.hasNumericValue() ? this.getNumericValue(part) : 0.0;
   }

   public int getLimitPartIndex(int start) {
      int limit = this.parts.get(start).limitPartIndex;
      return limit < start ? start : limit;
   }

   @Override
   public Object clone() {
      return this.isFrozen() ? this : this.cloneAsThawed();
   }

   public MessagePattern cloneAsThawed() {
      MessagePattern newMsg;
      try {
         newMsg = (MessagePattern)super.clone();
      } catch (CloneNotSupportedException var3) {
         throw new ICUCloneNotSupportedException(var3);
      }

      newMsg.parts = (ArrayList<MessagePattern.Part>)this.parts.clone();
      if (this.numericValues != null) {
         newMsg.numericValues = (ArrayList<Double>)this.numericValues.clone();
      }

      newMsg.frozen = false;
      return newMsg;
   }

   public MessagePattern freeze() {
      this.frozen = true;
      return this;
   }

   @Override
   public boolean isFrozen() {
      return this.frozen;
   }

   private void preParse(String pattern) {
      if (this.isFrozen()) {
         throw new UnsupportedOperationException("Attempt to parse(" + prefix(pattern) + ") on frozen MessagePattern instance.");
      } else {
         this.msg = pattern;
         this.hasArgNames = this.hasArgNumbers = false;
         this.needsAutoQuoting = false;
         this.parts.clear();
         if (this.numericValues != null) {
            this.numericValues.clear();
         }
      }
   }

   private void postParse() {
   }

   private int parseMessage(int index, int msgStartLength, int nestingLevel, MessagePattern.ArgType parentType) {
      if (nestingLevel > 32767) {
         throw new IndexOutOfBoundsException();
      } else {
         int msgStart = this.parts.size();
         this.addPart(MessagePattern.Part.Type.MSG_START, index, msgStartLength, nestingLevel);
         index += msgStartLength;

         while (index < this.msg.length()) {
            char c = this.msg.charAt(index++);
            if (c != '\'') {
               if (parentType.hasPluralStyle() && c == '#') {
                  this.addPart(MessagePattern.Part.Type.REPLACE_NUMBER, index - 1, 1, 0);
               } else if (c != '{') {
                  if (nestingLevel > 0 && c == '}' || parentType == MessagePattern.ArgType.CHOICE && c == '|') {
                     int limitLength = parentType == MessagePattern.ArgType.CHOICE && c == '}' ? 0 : 1;
                     this.addLimitPart(msgStart, MessagePattern.Part.Type.MSG_LIMIT, index - 1, limitLength, nestingLevel);
                     return parentType == MessagePattern.ArgType.CHOICE ? index - 1 : index;
                  }
               } else {
                  index = this.parseArg(index - 1, 1, nestingLevel);
               }
            } else if (index == this.msg.length()) {
               this.addPart(MessagePattern.Part.Type.INSERT_CHAR, index, 0, 39);
               this.needsAutoQuoting = true;
            } else {
               c = this.msg.charAt(index);
               if (c == '\'') {
                  this.addPart(MessagePattern.Part.Type.SKIP_SYNTAX, index++, 1, 0);
               } else if (this.aposMode == MessagePattern.ApostropheMode.DOUBLE_REQUIRED
                  || c == '{'
                  || c == '}'
                  || parentType == MessagePattern.ArgType.CHOICE && c == '|'
                  || parentType.hasPluralStyle() && c == '#') {
                  this.addPart(MessagePattern.Part.Type.SKIP_SYNTAX, index - 1, 1, 0);

                  while (true) {
                     index = this.msg.indexOf(39, index + 1);
                     if (index < 0) {
                        index = this.msg.length();
                        this.addPart(MessagePattern.Part.Type.INSERT_CHAR, index, 0, 39);
                        this.needsAutoQuoting = true;
                        break;
                     }

                     if (index + 1 >= this.msg.length() || this.msg.charAt(index + 1) != '\'') {
                        this.addPart(MessagePattern.Part.Type.SKIP_SYNTAX, index++, 1, 0);
                        break;
                     }

                     this.addPart(MessagePattern.Part.Type.SKIP_SYNTAX, ++index, 1, 0);
                  }
               } else {
                  this.addPart(MessagePattern.Part.Type.INSERT_CHAR, index, 0, 39);
                  this.needsAutoQuoting = true;
               }
            }
         }

         if (nestingLevel > 0 && !this.inTopLevelChoiceMessage(nestingLevel, parentType)) {
            throw new IllegalArgumentException("Unmatched '{' braces in message " + this.prefix());
         } else {
            this.addLimitPart(msgStart, MessagePattern.Part.Type.MSG_LIMIT, index, 0, nestingLevel);
            return index;
         }
      }
   }

   private int parseArg(int index, int argStartLength, int nestingLevel) {
      int argStart = this.parts.size();
      MessagePattern.ArgType argType = MessagePattern.ArgType.NONE;
      this.addPart(MessagePattern.Part.Type.ARG_START, index, argStartLength, argType.ordinal());
      int var11;
      int nameIndex = var11 = this.skipWhiteSpace(index + argStartLength);
      if (var11 == this.msg.length()) {
         throw new IllegalArgumentException("Unmatched '{' braces in message " + this.prefix());
      } else {
         index = this.skipIdentifier(var11);
         int number = this.parseArgNumber(nameIndex, index);
         if (number >= 0) {
            int length = index - nameIndex;
            if (length > 65535 || number > 32767) {
               throw new IndexOutOfBoundsException("Argument number too large: " + this.prefix(nameIndex));
            }

            this.hasArgNumbers = true;
            this.addPart(MessagePattern.Part.Type.ARG_NUMBER, nameIndex, length, number);
         } else {
            if (number != -1) {
               throw new IllegalArgumentException("Bad argument syntax: " + this.prefix(nameIndex));
            }

            int length = index - nameIndex;
            if (length > 65535) {
               throw new IndexOutOfBoundsException("Argument name too long: " + this.prefix(nameIndex));
            }

            this.hasArgNames = true;
            this.addPart(MessagePattern.Part.Type.ARG_NAME, nameIndex, length, 0);
         }

         index = this.skipWhiteSpace(index);
         if (index == this.msg.length()) {
            throw new IllegalArgumentException("Unmatched '{' braces in message " + this.prefix());
         } else {
            char c = this.msg.charAt(index);
            if (c != '}') {
               if (c != ',') {
                  throw new IllegalArgumentException("Bad argument syntax: " + this.prefix(nameIndex));
               }

               int var14;
               int typeIndex = var14 = this.skipWhiteSpace(index + 1);

               while (var14 < this.msg.length() && isArgTypeChar(this.msg.charAt(var14))) {
                  var14++;
               }

               int length = var14 - typeIndex;
               index = this.skipWhiteSpace(var14);
               if (index == this.msg.length()) {
                  throw new IllegalArgumentException("Unmatched '{' braces in message " + this.prefix());
               }

               if (length == 0 || (c = this.msg.charAt(index)) != ',' && c != '}') {
                  throw new IllegalArgumentException("Bad argument syntax: " + this.prefix(nameIndex));
               }

               if (length > 65535) {
                  throw new IndexOutOfBoundsException("Argument type name too long: " + this.prefix(nameIndex));
               }

               argType = MessagePattern.ArgType.SIMPLE;
               if (length == 6) {
                  if (this.isChoice(typeIndex)) {
                     argType = MessagePattern.ArgType.CHOICE;
                  } else if (this.isPlural(typeIndex)) {
                     argType = MessagePattern.ArgType.PLURAL;
                  } else if (this.isSelect(typeIndex)) {
                     argType = MessagePattern.ArgType.SELECT;
                  }
               } else if (length == 13 && this.isSelect(typeIndex) && this.isOrdinal(typeIndex + 6)) {
                  argType = MessagePattern.ArgType.SELECTORDINAL;
               }

               this.parts.get(argStart).value = (short)argType.ordinal();
               if (argType == MessagePattern.ArgType.SIMPLE) {
                  this.addPart(MessagePattern.Part.Type.ARG_TYPE, typeIndex, length, 0);
               }

               if (c == '}') {
                  if (argType != MessagePattern.ArgType.SIMPLE) {
                     throw new IllegalArgumentException("No style field for complex argument: " + this.prefix(nameIndex));
                  }
               } else {
                  index++;
                  if (argType == MessagePattern.ArgType.SIMPLE) {
                     index = this.parseSimpleStyle(index);
                  } else if (argType == MessagePattern.ArgType.CHOICE) {
                     index = this.parseChoiceStyle(index, nestingLevel);
                  } else {
                     index = this.parsePluralOrSelectStyle(argType, index, nestingLevel);
                  }
               }
            }

            this.addLimitPart(argStart, MessagePattern.Part.Type.ARG_LIMIT, index, 1, argType.ordinal());
            return index + 1;
         }
      }
   }

   private int parseSimpleStyle(int index) {
      int start = index;
      int nestedBraces = 0;

      while (index < this.msg.length()) {
         char c = this.msg.charAt(index++);
         if (c == '\'') {
            index = this.msg.indexOf(39, index);
            if (index < 0) {
               throw new IllegalArgumentException("Quoted literal argument style text reaches to the end of the message: " + this.prefix(start));
            }

            index++;
         } else if (c == '{') {
            nestedBraces++;
         } else if (c == '}') {
            if (nestedBraces <= 0) {
               index--;
               int length = index - start;
               if (length > 65535) {
                  throw new IndexOutOfBoundsException("Argument style text too long: " + this.prefix(start));
               }

               this.addPart(MessagePattern.Part.Type.ARG_STYLE, start, length, 0);
               return index;
            }

            nestedBraces--;
         }
      }

      throw new IllegalArgumentException("Unmatched '{' braces in message " + this.prefix());
   }

   private int parseChoiceStyle(int index, int nestingLevel) {
      index = this.skipWhiteSpace(index);
      if (index != this.msg.length() && this.msg.charAt(index) != '}') {
         while (true) {
            int var8 = this.skipDouble(index);
            int length = var8 - index;
            if (length == 0) {
               throw new IllegalArgumentException("Bad choice pattern syntax: " + this.prefix(index));
            }

            if (length > 65535) {
               throw new IndexOutOfBoundsException("Choice number too long: " + this.prefix(index));
            }

            this.parseDouble(index, var8, true);
            index = this.skipWhiteSpace(var8);
            if (index == this.msg.length()) {
               throw new IllegalArgumentException("Bad choice pattern syntax: " + this.prefix(index));
            }

            char c = this.msg.charAt(index);
            if (c != '#' && c != '<' && c != 8804) {
               throw new IllegalArgumentException("Expected choice separator (#<≤) instead of '" + c + "' in choice pattern " + this.prefix(index));
            }

            this.addPart(MessagePattern.Part.Type.ARG_SELECTOR, index, 1, 0);
            index = this.parseMessage(++index, 0, nestingLevel + 1, MessagePattern.ArgType.CHOICE);
            if (index == this.msg.length()) {
               return index;
            }

            if (this.msg.charAt(index) == '}') {
               if (!this.inMessageFormatPattern(nestingLevel)) {
                  throw new IllegalArgumentException("Bad choice pattern syntax: " + this.prefix(index));
               }

               return index;
            }

            index = this.skipWhiteSpace(index + 1);
         }
      } else {
         throw new IllegalArgumentException("Missing choice argument pattern in " + this.prefix());
      }
   }

   private int parsePluralOrSelectStyle(MessagePattern.ArgType argType, int index, int nestingLevel) {
      int start = index;
      boolean isEmpty = true;
      boolean hasOther = false;

      while (true) {
         index = this.skipWhiteSpace(index);
         boolean eos = index == this.msg.length();
         if (!eos && this.msg.charAt(index) != '}') {
            if (argType.hasPluralStyle() && this.msg.charAt(index) == '=') {
               index = this.skipDouble(index + 1);
               int length = index - index;
               if (length == 1) {
                  throw new IllegalArgumentException("Bad " + argType.toString().toLowerCase(Locale.ENGLISH) + " pattern syntax: " + this.prefix(start));
               }

               if (length > 65535) {
                  throw new IndexOutOfBoundsException("Argument selector too long: " + this.prefix(index));
               }

               this.addPart(MessagePattern.Part.Type.ARG_SELECTOR, index, length, 0);
               this.parseDouble(index + 1, index, false);
            } else {
               index = this.skipIdentifier(index);
               int lengthx = index - index;
               if (lengthx == 0) {
                  throw new IllegalArgumentException("Bad " + argType.toString().toLowerCase(Locale.ENGLISH) + " pattern syntax: " + this.prefix(start));
               }

               if (argType.hasPluralStyle() && lengthx == 6 && index < this.msg.length() && this.msg.regionMatches(index, "offset:", 0, 7)) {
                  if (!isEmpty) {
                     throw new IllegalArgumentException("Plural argument 'offset:' (if present) must precede key-message pairs: " + this.prefix(start));
                  }

                  int valueIndex = this.skipWhiteSpace(index + 1);
                  index = this.skipDouble(valueIndex);
                  if (index == valueIndex) {
                     throw new IllegalArgumentException("Missing value for plural 'offset:' " + this.prefix(start));
                  }

                  if (index - valueIndex > 65535) {
                     throw new IndexOutOfBoundsException("Plural offset value too long: " + this.prefix(valueIndex));
                  }

                  this.parseDouble(valueIndex, index, false);
                  isEmpty = false;
                  continue;
               }

               if (lengthx > 65535) {
                  throw new IndexOutOfBoundsException("Argument selector too long: " + this.prefix(index));
               }

               this.addPart(MessagePattern.Part.Type.ARG_SELECTOR, index, lengthx, 0);
               if (this.msg.regionMatches(index, "other", 0, lengthx)) {
                  hasOther = true;
               }
            }

            index = this.skipWhiteSpace(index);
            if (index != this.msg.length() && this.msg.charAt(index) == '{') {
               index = this.parseMessage(index, 1, nestingLevel + 1, argType);
               isEmpty = false;
               continue;
            }

            throw new IllegalArgumentException(
               "No message fragment after " + argType.toString().toLowerCase(Locale.ENGLISH) + " selector: " + this.prefix(index)
            );
         }

         if (eos == this.inMessageFormatPattern(nestingLevel)) {
            throw new IllegalArgumentException("Bad " + argType.toString().toLowerCase(Locale.ENGLISH) + " pattern syntax: " + this.prefix(start));
         }

         if (!hasOther) {
            throw new IllegalArgumentException("Missing 'other' keyword in " + argType.toString().toLowerCase(Locale.ENGLISH) + " pattern in " + this.prefix());
         }

         return index;
      }
   }

   private static int parseArgNumber(CharSequence s, int start, int limit) {
      if (start >= limit) {
         return -2;
      } else {
         char c = s.charAt(start++);
         int number;
         boolean badNumber;
         if (c == '0') {
            if (start == limit) {
               return 0;
            }

            number = 0;
            badNumber = true;
         } else {
            if ('1' > c || c > '9') {
               return -1;
            }

            number = c - '0';
            badNumber = false;
         }

         while (start < limit) {
            c = s.charAt(start++);
            if ('0' > c || c > '9') {
               return -1;
            }

            if (number >= 214748364) {
               badNumber = true;
            }

            number = number * 10 + (c - '0');
         }

         return badNumber ? -2 : number;
      }
   }

   private int parseArgNumber(int start, int limit) {
      return parseArgNumber(this.msg, start, limit);
   }

   private void parseDouble(int start, int limit, boolean allowInfinity) {
      assert start < limit;

      int value = 0;
      int isNegative = 0;
      int index = start + 1;
      char c = this.msg.charAt(start);
      if (c == '-') {
         isNegative = 1;
         if (index == limit) {
            throw new NumberFormatException("Bad syntax for numeric value: " + this.msg.substring(start, limit));
         }

         c = this.msg.charAt(index++);
      } else if (c == '+') {
         if (index == limit) {
            throw new NumberFormatException("Bad syntax for numeric value: " + this.msg.substring(start, limit));
         }

         c = this.msg.charAt(index++);
      }

      if (c != 8734) {
         while ('0' <= c && c <= '9') {
            value = value * 10 + (c - '0');
            if (value > 32767 + isNegative) {
               break;
            }

            if (index == limit) {
               this.addPart(MessagePattern.Part.Type.ARG_INT, start, limit - start, isNegative != 0 ? -value : value);
               return;
            }

            c = this.msg.charAt(index++);
         }

         double numericValue = Double.parseDouble(this.msg.substring(start, limit));
         this.addArgDoublePart(numericValue, start, limit - start);
      } else if (allowInfinity && index == limit) {
         this.addArgDoublePart(isNegative != 0 ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY, start, limit - start);
      } else {
         throw new NumberFormatException("Bad syntax for numeric value: " + this.msg.substring(start, limit));
      }
   }

   static void appendReducedApostrophes(String s, int start, int limit, StringBuilder sb) {
      int doubleApos = -1;

      while (true) {
         int i = s.indexOf(39, start);
         if (i < 0 || i >= limit) {
            sb.append(s, start, limit);
            return;
         }

         if (i == doubleApos) {
            sb.append('\'');
            start++;
            doubleApos = -1;
         } else {
            sb.append(s, start, i);
            doubleApos = start = i + 1;
         }
      }
   }

   private int skipWhiteSpace(int index) {
      return PatternProps.skipWhiteSpace(this.msg, index);
   }

   private int skipIdentifier(int index) {
      return PatternProps.skipIdentifier(this.msg, index);
   }

   private int skipDouble(int index) {
      while (index < this.msg.length()) {
         char c = this.msg.charAt(index);
         if ((c >= '0' || "+-.".indexOf(c) >= 0) && (c <= '9' || c == 'e' || c == 'E' || c == 8734)) {
            index++;
            continue;
         }
         break;
      }

      return index;
   }

   private static boolean isArgTypeChar(int c) {
      return 97 <= c && c <= 122 || 65 <= c && c <= 90;
   }

   private boolean isChoice(int index) {
      char c;
      char var8;
      char var9;
      char var10;
      char var11;
      char var12;
      return ((c = this.msg.charAt(index++)) == 'c' || c == 'C')
         && ((var8 = this.msg.charAt(index++)) == 'h' || var8 == 'H')
         && ((var9 = this.msg.charAt(index++)) == 'o' || var9 == 'O')
         && ((var10 = this.msg.charAt(index++)) == 'i' || var10 == 'I')
         && ((var11 = this.msg.charAt(index++)) == 'c' || var11 == 'C')
         && ((var12 = this.msg.charAt(index)) == 'e' || var12 == 'E');
   }

   private boolean isPlural(int index) {
      char c;
      char var8;
      char var9;
      char var10;
      char var11;
      char var12;
      return ((c = this.msg.charAt(index++)) == 'p' || c == 'P')
         && ((var8 = this.msg.charAt(index++)) == 'l' || var8 == 'L')
         && ((var9 = this.msg.charAt(index++)) == 'u' || var9 == 'U')
         && ((var10 = this.msg.charAt(index++)) == 'r' || var10 == 'R')
         && ((var11 = this.msg.charAt(index++)) == 'a' || var11 == 'A')
         && ((var12 = this.msg.charAt(index)) == 'l' || var12 == 'L');
   }

   private boolean isSelect(int index) {
      char c;
      char var8;
      char var9;
      char var10;
      char var11;
      char var12;
      return ((c = this.msg.charAt(index++)) == 's' || c == 'S')
         && ((var8 = this.msg.charAt(index++)) == 'e' || var8 == 'E')
         && ((var9 = this.msg.charAt(index++)) == 'l' || var9 == 'L')
         && ((var10 = this.msg.charAt(index++)) == 'e' || var10 == 'E')
         && ((var11 = this.msg.charAt(index++)) == 'c' || var11 == 'C')
         && ((var12 = this.msg.charAt(index)) == 't' || var12 == 'T');
   }

   private boolean isOrdinal(int index) {
      char c;
      char var9;
      char var10;
      char var11;
      char var12;
      char var13;
      char var14;
      return ((c = this.msg.charAt(index++)) == 'o' || c == 'O')
         && ((var9 = this.msg.charAt(index++)) == 'r' || var9 == 'R')
         && ((var10 = this.msg.charAt(index++)) == 'd' || var10 == 'D')
         && ((var11 = this.msg.charAt(index++)) == 'i' || var11 == 'I')
         && ((var12 = this.msg.charAt(index++)) == 'n' || var12 == 'N')
         && ((var13 = this.msg.charAt(index++)) == 'a' || var13 == 'A')
         && ((var14 = this.msg.charAt(index)) == 'l' || var14 == 'L');
   }

   private boolean inMessageFormatPattern(int nestingLevel) {
      return nestingLevel > 0 || this.parts.get(0).type == MessagePattern.Part.Type.MSG_START;
   }

   private boolean inTopLevelChoiceMessage(int nestingLevel, MessagePattern.ArgType parentType) {
      return nestingLevel == 1 && parentType == MessagePattern.ArgType.CHOICE && this.parts.get(0).type != MessagePattern.Part.Type.MSG_START;
   }

   private void addPart(MessagePattern.Part.Type type, int index, int length, int value) {
      this.parts.add(new MessagePattern.Part(type, index, length, value));
   }

   private void addLimitPart(int start, MessagePattern.Part.Type type, int index, int length, int value) {
      this.parts.get(start).limitPartIndex = this.parts.size();
      this.addPart(type, index, length, value);
   }

   private void addArgDoublePart(double numericValue, int start, int length) {
      int numericIndex;
      if (this.numericValues == null) {
         this.numericValues = new ArrayList<>();
         numericIndex = 0;
      } else {
         numericIndex = this.numericValues.size();
         if (numericIndex > 32767) {
            throw new IndexOutOfBoundsException("Too many numeric values");
         }
      }

      this.numericValues.add(numericValue);
      this.addPart(MessagePattern.Part.Type.ARG_DOUBLE, start, length, numericIndex);
   }

   private static String prefix(String s, int start) {
      StringBuilder prefix = new StringBuilder(44);
      if (start == 0) {
         prefix.append("\"");
      } else {
         prefix.append("[at pattern index ").append(start).append("] \"");
      }

      int substringLength = s.length() - start;
      if (substringLength <= 24) {
         prefix.append(start == 0 ? s : s.substring(start));
      } else {
         int limit = start + 24 - 4;
         if (Character.isHighSurrogate(s.charAt(limit - 1))) {
            limit--;
         }

         prefix.append(s, start, limit).append(" ...");
      }

      return prefix.append("\"").toString();
   }

   private static String prefix(String s) {
      return prefix(s, 0);
   }

   private String prefix(int start) {
      return prefix(this.msg, start);
   }

   private String prefix() {
      return prefix(this.msg, 0);
   }

   public static enum ApostropheMode {
      DOUBLE_OPTIONAL,
      DOUBLE_REQUIRED;
   }

   public static enum ArgType {
      NONE,
      SIMPLE,
      CHOICE,
      PLURAL,
      SELECT,
      SELECTORDINAL;

      public boolean hasPluralStyle() {
         return this == PLURAL || this == SELECTORDINAL;
      }
   }

   public static final class Part {
      private static final int MAX_LENGTH = 65535;
      private static final int MAX_VALUE = 32767;
      private final MessagePattern.Part.Type type;
      private final int index;
      private final char length;
      private short value;
      private int limitPartIndex;

      private Part(MessagePattern.Part.Type t, int i, int l, int v) {
         this.type = t;
         this.index = i;
         this.length = (char)l;
         this.value = (short)v;
      }

      public MessagePattern.Part.Type getType() {
         return this.type;
      }

      public int getIndex() {
         return this.index;
      }

      public int getLength() {
         return this.length;
      }

      public int getLimit() {
         return this.index + this.length;
      }

      public int getValue() {
         return this.value;
      }

      public MessagePattern.ArgType getArgType() {
         MessagePattern.Part.Type type = this.getType();
         return type != MessagePattern.Part.Type.ARG_START && type != MessagePattern.Part.Type.ARG_LIMIT
            ? MessagePattern.ArgType.NONE
            : MessagePattern.argTypes[this.value];
      }

      @Override
      public String toString() {
         String valueString = this.type != MessagePattern.Part.Type.ARG_START && this.type != MessagePattern.Part.Type.ARG_LIMIT
            ? Integer.toString(this.value)
            : this.getArgType().name();
         return this.type.name() + "(" + valueString + ")@" + this.index;
      }

      @Override
      public boolean equals(Object other) {
         if (this == other) {
            return true;
         } else if (other != null && this.getClass() == other.getClass()) {
            MessagePattern.Part o = (MessagePattern.Part)other;
            return this.type.equals(o.type)
               && this.index == o.index
               && this.length == o.length
               && this.value == o.value
               && this.limitPartIndex == o.limitPartIndex;
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         return ((this.type.hashCode() * 37 + this.index) * 37 + this.length) * 37 + this.value;
      }

      public static enum Type {
         MSG_START,
         MSG_LIMIT,
         SKIP_SYNTAX,
         INSERT_CHAR,
         REPLACE_NUMBER,
         ARG_START,
         ARG_LIMIT,
         ARG_NUMBER,
         ARG_NAME,
         ARG_TYPE,
         ARG_STYLE,
         ARG_SELECTOR,
         ARG_INT,
         ARG_DOUBLE;

         public boolean hasNumericValue() {
            return this == ARG_INT || this == ARG_DOUBLE;
         }
      }
   }
}
