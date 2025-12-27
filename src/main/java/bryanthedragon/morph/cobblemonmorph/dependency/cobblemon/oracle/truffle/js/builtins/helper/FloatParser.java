package com.oracle.truffle.js.builtins.helper;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;

public final class FloatParser {
   private final TruffleString input;
   private int pos;
   private boolean isNaN;
   private final double value;

   public FloatParser(TruffleString s, FloatParserNode node) {
      this.input = s;
      this.pos = 0;
      this.isNaN = false;
      this.value = this.parse(node);
   }

   public double getResult() {
      return this.value;
   }

   private double parse(FloatParserNode node) {
      this.strDecimalLiteral(node);
      return this.isNaN ? Double.NaN : this.parseValidSubstring(node);
   }

   private double parseValidSubstring(FloatParserNode node) {
      TruffleString validSubstring = Strings.substring(true, node.substringNode, this.input, 0, this.pos);

      try {
         return Strings.parseDouble(node.parseDoubleNode, validSubstring);
      } catch (TruffleString.NumberFormatException var4) {
         this.isNaN = true;
         return Double.NaN;
      }
   }

   private void strDecimalLiteral(FloatParserNode node) {
      char currentChar = this.current(node);
      if (currentChar == '+' || currentChar == '-') {
         this.next();
         currentChar = this.current(node);
      }

      if (!JSRuntime.isAsciiDigit(currentChar) && currentChar != '.') {
         this.isNaN = true;
      } else {
         this.strUnsignedDecimalLiteral(node);
      }
   }

   private void strUnsignedDecimalLiteral(FloatParserNode node) {
      if (JSRuntime.isAsciiDigit(this.current(node))) {
         this.decimalDigits(node);
      }

      int prevPos = this.pos;
      if (this.hasNext() && this.current(node) == '.') {
         this.next();
         if (JSRuntime.isAsciiDigit(this.current(node))) {
            this.decimalDigits(node);
         }
      }

      if (this.isNaN) {
         this.pos = prevPos;
         this.isNaN = false;
      } else {
         prevPos = this.pos;
         if (this.isExponentPart(node)) {
            this.exponentPart(node);
         }

         if (this.isNaN) {
            this.pos = prevPos;
            this.isNaN = false;
         }
      }
   }

   private void next() {
      this.pos++;
   }

   private char current(FloatParserNode node) {
      return this.hasNext() ? Strings.charAt(node.charAtNode, this.input, this.pos) : '\u0000';
   }

   private boolean hasNext() {
      return this.pos < Strings.length(this.input);
   }

   private void exponentPart(FloatParserNode node) {
      node.exponentBranch.enter();

      assert this.current(node) == 'e' || this.current(node) == 'E';

      this.next();
      char currentChar = this.current(node);
      if (JSRuntime.isAsciiDigit(currentChar)) {
         this.decimalDigits(node);
      } else if (currentChar != '+' && currentChar != '-') {
         this.isNaN = true;
      } else {
         this.next();
         this.decimalDigits(node);
      }
   }

   private boolean isExponentPart(FloatParserNode node) {
      if (!this.hasNext()) {
         return false;
      } else {
         char firstChar = this.current(node);
         return firstChar == 'e' || firstChar == 'E';
      }
   }

   private void decimalDigits(FloatParserNode node) {
      char currentChar = this.current(node);

      boolean valid;
      for (valid = false; JSRuntime.isAsciiDigit(currentChar) && this.hasNext(); currentChar = this.current(node)) {
         valid = true;
         this.next();
      }

      if (!valid) {
         this.isNaN = true;
      }
   }
}
