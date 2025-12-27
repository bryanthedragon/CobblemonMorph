package com.oracle.truffle.js.builtins.helper;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;
import java.util.ArrayList;

public final class ReplaceStringParser<T> {
   private final JSContext context;
   private final TruffleString replaceStr;
   private final int maxGroupNumber;
   private final boolean parseNamedCaptureGroups;
   private int index = 0;

   private ReplaceStringParser(JSContext context, TruffleString replaceStr, int maxGroupNumber, boolean parseNamedCaptureGroups) {
      this.context = context;
      this.replaceStr = replaceStr;
      this.maxGroupNumber = maxGroupNumber;
      this.parseNamedCaptureGroups = parseNamedCaptureGroups;
   }

   public static <T, R> R process(
      JSContext context,
      TruffleString replaceStr,
      int maxGroupNumber,
      boolean parseNamedCaptureGroups,
      BranchProfile hasDollarProfile,
      ReplaceStringParser.Consumer<T, R> consumer,
      T node
   ) {
      new ReplaceStringParser<T>(context, replaceStr, maxGroupNumber, parseNamedCaptureGroups).process(consumer, node, hasDollarProfile);
      return consumer.getResult();
   }

   @CompilerDirectives.TruffleBoundary
   public static ReplaceStringParser.Token[] parse(JSContext context, TruffleString replaceStr, int maxGroupNumber, boolean parseNamedCaptureGroups) {
      ReplaceStringParser.TokenConsumer consumer = new ReplaceStringParser.TokenConsumer();
      new ReplaceStringParser<Void>(context, replaceStr, maxGroupNumber, parseNamedCaptureGroups).process(consumer, null, BranchProfile.create());
      return consumer.getResult();
   }

   public static <T, R> R processParsed(ReplaceStringParser.Token[] tokens, ReplaceStringParser.Consumer<T, R> consumer, T node) {
      for (ReplaceStringParser.Token t : tokens) {
         switch (t.getKind()) {
            case literal:
               consumer.literal(node, ((ReplaceStringParser.LiteralToken)t).getStart(), ((ReplaceStringParser.LiteralToken)t).getEnd());
               break;
            case match:
               consumer.match(node);
               break;
            case matchHead:
               consumer.matchHead(node);
               break;
            case matchTail:
               consumer.matchTail(node);
               break;
            case captureGroup:
               consumer.captureGroup(
                  node,
                  ((ReplaceStringParser.CaptureGroupToken)t).getGroupNumber(),
                  ((ReplaceStringParser.CaptureGroupToken)t).getLiteralStart(),
                  ((ReplaceStringParser.CaptureGroupToken)t).getLiteralEnd()
               );
               break;
            case namedCaptureGroup:
               consumer.namedCaptureGroup(node, ((ReplaceStringParser.NamedCaptureGroupToken)t).getGroupName());
         }
      }

      return consumer.getResult();
   }

   public void process(ReplaceStringParser.Consumer<T, ?> consumer, T node, BranchProfile hasDollarProfile) {
      while (this.hasNext()) {
         this.parseNextDollar(consumer, node, hasDollarProfile);
      }
   }

   private boolean hasNext() {
      return this.index < Strings.length(this.replaceStr);
   }

   private void parseNextDollar(ReplaceStringParser.Consumer<T, ?> consumer, T node, BranchProfile hasDollarProfile) {
      assert this.hasNext();

      int dollarPos = Strings.indexOf(this.replaceStr, 36, this.index);
      if (dollarPos >= 0 && dollarPos + 1 != Strings.length(this.replaceStr)) {
         hasDollarProfile.enter();
         char ch = Strings.charAt(this.replaceStr, dollarPos + 1);
         switch (ch) {
            case '$':
               this.literal(consumer, node, dollarPos + 1, dollarPos + 2);
               return;
            case '&':
               this.match(consumer, node, dollarPos, dollarPos + 2);
               return;
            case '\'':
               this.matchTail(consumer, node, dollarPos, dollarPos + 2);
               return;
            case '<':
               if (this.parseNamedCaptureGroups) {
                  int groupNameStart = dollarPos + 2;
                  int groupNameEnd = Strings.indexOf(this.replaceStr, 62, groupNameStart);
                  if (groupNameEnd >= 0) {
                     this.namedCaptureGroup(
                        consumer,
                        node,
                        dollarPos,
                        Strings.substring(this.context, this.replaceStr, groupNameStart, groupNameEnd - groupNameStart),
                        groupNameEnd + 1
                     );
                     return;
                  }
               }
               break;
            case '`':
               this.matchHead(consumer, node, dollarPos, dollarPos + 2);
               return;
            default:
               if (this.isDigit(ch)) {
                  int firstDigit = ch - '0';
                  if (Strings.length(this.replaceStr) > dollarPos + 2 && this.isDigit(Strings.charAt(this.replaceStr, dollarPos + 2))) {
                     int groupNumber = firstDigit * 10 + (Strings.charAt(this.replaceStr, dollarPos + 2) - '0');
                     if (0 < groupNumber && groupNumber < this.maxGroupNumber) {
                        this.captureGroup(consumer, node, dollarPos, groupNumber, dollarPos + 3);
                        return;
                     }
                  }

                  if (0 < firstDigit && firstDigit < this.maxGroupNumber) {
                     this.captureGroup(consumer, node, dollarPos, firstDigit, dollarPos + 2);
                     return;
                  }
               }
         }

         this.literal(consumer, node, dollarPos + 2, dollarPos + 2);
      } else {
         this.literal(consumer, node, Strings.length(this.replaceStr), Strings.length(this.replaceStr));
      }
   }

   private void literal(ReplaceStringParser.Consumer<T, ?> consumer, T node, int literalEnd, int nextIndex) {
      consumer.literal(node, this.index, literalEnd);
      this.index = nextIndex;
   }

   private void match(ReplaceStringParser.Consumer<T, ?> consumer, T node, int literalEnd, int nextIndex) {
      consumer.literal(node, this.index, literalEnd);
      consumer.match(node);
      this.index = nextIndex;
   }

   private void matchHead(ReplaceStringParser.Consumer<T, ?> consumer, T node, int literalEnd, int nextIndex) {
      consumer.literal(node, this.index, literalEnd);
      consumer.matchHead(node);
      this.index = nextIndex;
   }

   private void matchTail(ReplaceStringParser.Consumer<T, ?> consumer, T node, int literalEnd, int nextIndex) {
      consumer.literal(node, this.index, literalEnd);
      consumer.matchTail(node);
      this.index = nextIndex;
   }

   private void captureGroup(ReplaceStringParser.Consumer<T, ?> consumer, T node, int literalEnd, int groupNumber, int nextIndex) {
      consumer.literal(node, this.index, literalEnd);
      consumer.captureGroup(node, groupNumber, literalEnd, nextIndex);
      this.index = nextIndex;
   }

   private void namedCaptureGroup(ReplaceStringParser.Consumer<T, ?> consumer, T node, int literalEnd, TruffleString groupName, int nextIndex) {
      consumer.literal(node, this.index, literalEnd);
      consumer.namedCaptureGroup(node, groupName);
      this.index = nextIndex;
   }

   private boolean isDigit(char ch) {
      return this.maxGroupNumber > 0 && '0' <= ch && ch <= '9';
   }

   public static class CaptureGroupToken extends ReplaceStringParser.Token {
      private final int groupNumber;
      private final int literalStart;
      private final int literalEnd;

      public CaptureGroupToken(int groupNumber, int literalStart, int literalEnd) {
         super(ReplaceStringParser.Token.Kind.captureGroup);
         this.groupNumber = groupNumber;
         this.literalStart = literalStart;
         this.literalEnd = literalEnd;
      }

      public int getGroupNumber() {
         return this.groupNumber;
      }

      public int getLiteralStart() {
         return this.literalStart;
      }

      public int getLiteralEnd() {
         return this.literalEnd;
      }
   }

   public interface Consumer<T, R> {
      void literal(T node, int start, int end);

      void match(T node);

      void matchHead(T node);

      void matchTail(T node);

      void captureGroup(T node, int groupNumber, int literalStart, int literalEnd);

      void namedCaptureGroup(T node, TruffleString groupName);

      R getResult();
   }

   public static class LiteralToken extends ReplaceStringParser.Token {
      private final int start;
      private final int end;

      public LiteralToken(int start, int end) {
         super(ReplaceStringParser.Token.Kind.literal);
         this.start = start;
         this.end = end;
      }

      public int getStart() {
         return this.start;
      }

      public int getEnd() {
         return this.end;
      }
   }

   public static class NamedCaptureGroupToken extends ReplaceStringParser.Token {
      private final TruffleString groupNameStr;

      public NamedCaptureGroupToken(TruffleString groupName) {
         super(ReplaceStringParser.Token.Kind.namedCaptureGroup);
         this.groupNameStr = groupName;
      }

      public TruffleString getGroupName() {
         return this.groupNameStr;
      }
   }

   public static class Token {
      private final ReplaceStringParser.Token.Kind kind;

      public Token(ReplaceStringParser.Token.Kind kind) {
         this.kind = kind;
      }

      public ReplaceStringParser.Token.Kind getKind() {
         return this.kind;
      }

      public static enum Kind {
         literal,
         match,
         matchHead,
         matchTail,
         captureGroup,
         namedCaptureGroup;
      }
   }

   private static final class TokenConsumer implements ReplaceStringParser.Consumer<Void, ReplaceStringParser.Token[]> {
      private final ArrayList<ReplaceStringParser.Token> tokens = new ArrayList<>();

      public void literal(Void node, int start, int end) {
         this.tokens.add(new ReplaceStringParser.LiteralToken(start, end));
      }

      public void match(Void node) {
         this.tokens.add(new ReplaceStringParser.Token(ReplaceStringParser.Token.Kind.match));
      }

      public void matchHead(Void node) {
         this.tokens.add(new ReplaceStringParser.Token(ReplaceStringParser.Token.Kind.matchHead));
      }

      public void matchTail(Void node) {
         this.tokens.add(new ReplaceStringParser.Token(ReplaceStringParser.Token.Kind.matchTail));
      }

      public void captureGroup(Void node, int groupNumber, int literalStart, int literalEnd) {
         this.tokens.add(new ReplaceStringParser.CaptureGroupToken(groupNumber, literalStart, literalEnd));
      }

      public void namedCaptureGroup(Void node, TruffleString groupName) {
         this.tokens.add(new ReplaceStringParser.NamedCaptureGroupToken(groupName));
      }

      public ReplaceStringParser.Token[] getResult() {
         return this.tokens.toArray(new ReplaceStringParser.Token[0]);
      }
   }
}
