
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

    public static <T, R> R process(JSContext context, TruffleString replaceStr, int maxGroupNumber, boolean parseNamedCaptureGroups, BranchProfile hasDollarProfile, Consumer<T, R> consumer, T node) {
        new ReplaceStringParser<T>(context, replaceStr, maxGroupNumber, parseNamedCaptureGroups).process(consumer, node, hasDollarProfile);
        return consumer.getResult();
    }

    @CompilerDirectives.TruffleBoundary
    public static Token[] parse(JSContext context, TruffleString replaceStr, int maxGroupNumber, boolean parseNamedCaptureGroups) {
        TokenConsumer consumer = new TokenConsumer();
        new ReplaceStringParser<Void>(context, replaceStr, maxGroupNumber, parseNamedCaptureGroups).process(consumer, null, BranchProfile.create());
        return consumer.getResult();
    }

    public static <T, R> R processParsed(Token[] tokens, Consumer<T, R> consumer, T node) {
        block8: for (Token t : tokens) {
            switch (t.getKind()) {
                case literal: {
                    consumer.literal(node, ((LiteralToken)t).getStart(), ((LiteralToken)t).getEnd());
                    continue block8;
                }
                case match: {
                    consumer.match(node);
                    continue block8;
                }
                case matchHead: {
                    consumer.matchHead(node);
                    continue block8;
                }
                case matchTail: {
                    consumer.matchTail(node);
                    continue block8;
                }
                case captureGroup: {
                    consumer.captureGroup(node, ((CaptureGroupToken)t).getGroupNumber(), ((CaptureGroupToken)t).getLiteralStart(), ((CaptureGroupToken)t).getLiteralEnd());
                    continue block8;
                }
                case namedCaptureGroup: {
                    consumer.namedCaptureGroup(node, ((NamedCaptureGroupToken)t).getGroupName());
                }
            }
        }
        return consumer.getResult();
    }

    public void process(Consumer<T, ?> consumer, T node, BranchProfile hasDollarProfile) {
        while (this.hasNext()) {
            this.parseNextDollar(consumer, node, hasDollarProfile);
        }
    }

    private boolean hasNext() {
        return this.index < Strings.length(this.replaceStr);
    }

    private void parseNextDollar(Consumer<T, ?> consumer, T node, BranchProfile hasDollarProfile) {
        assert (this.hasNext());
        int dollarPos = Strings.indexOf(this.replaceStr, 36, this.index);
        if (dollarPos < 0 || dollarPos + 1 == Strings.length(this.replaceStr)) {
            this.literal(consumer, node, Strings.length(this.replaceStr), Strings.length(this.replaceStr));
            return;
        }
        hasDollarProfile.enter();
        char ch = Strings.charAt(this.replaceStr, dollarPos + 1);
        switch (ch) {
            case '$': {
                this.literal(consumer, node, dollarPos + 1, dollarPos + 2);
                return;
            }
            case '&': {
                this.match(consumer, node, dollarPos, dollarPos + 2);
                return;
            }
            case '`': {
                this.matchHead(consumer, node, dollarPos, dollarPos + 2);
                return;
            }
            case '\'': {
                this.matchTail(consumer, node, dollarPos, dollarPos + 2);
                return;
            }
            case '<': {
                int groupNameStart;
                int groupNameEnd;
                if (!this.parseNamedCaptureGroups || (groupNameEnd = Strings.indexOf(this.replaceStr, 62, groupNameStart = dollarPos + 2)) < 0) break;
                this.namedCaptureGroup(consumer, node, dollarPos, Strings.substring(this.context, this.replaceStr, groupNameStart, groupNameEnd - groupNameStart), groupNameEnd + 1);
                return;
            }
            default: {
                int groupNumber;
                if (!this.isDigit(ch)) break;
                int firstDigit = ch - 48;
                if (Strings.length(this.replaceStr) > dollarPos + 2 && this.isDigit(Strings.charAt(this.replaceStr, dollarPos + 2)) && 0 < (groupNumber = firstDigit * 10 + (Strings.charAt(this.replaceStr, dollarPos + 2) - 48)) && groupNumber < this.maxGroupNumber) {
                    this.captureGroup(consumer, node, dollarPos, groupNumber, dollarPos + 3);
                    return;
                }
                if (0 >= firstDigit || firstDigit >= this.maxGroupNumber) break;
                this.captureGroup(consumer, node, dollarPos, firstDigit, dollarPos + 2);
                return;
            }
        }
        this.literal(consumer, node, dollarPos + 2, dollarPos + 2);
    }

    private void literal(Consumer<T, ?> consumer, T node, int literalEnd, int nextIndex) {
        consumer.literal(node, this.index, literalEnd);
        this.index = nextIndex;
    }

    private void match(Consumer<T, ?> consumer, T node, int literalEnd, int nextIndex) {
        consumer.literal(node, this.index, literalEnd);
        consumer.match(node);
        this.index = nextIndex;
    }

    private void matchHead(Consumer<T, ?> consumer, T node, int literalEnd, int nextIndex) {
        consumer.literal(node, this.index, literalEnd);
        consumer.matchHead(node);
        this.index = nextIndex;
    }

    private void matchTail(Consumer<T, ?> consumer, T node, int literalEnd, int nextIndex) {
        consumer.literal(node, this.index, literalEnd);
        consumer.matchTail(node);
        this.index = nextIndex;
    }

    private void captureGroup(Consumer<T, ?> consumer, T node, int literalEnd, int groupNumber, int nextIndex) {
        consumer.literal(node, this.index, literalEnd);
        consumer.captureGroup(node, groupNumber, literalEnd, nextIndex);
        this.index = nextIndex;
    }

    private void namedCaptureGroup(Consumer<T, ?> consumer, T node, int literalEnd, TruffleString groupName, int nextIndex) {
        consumer.literal(node, this.index, literalEnd);
        consumer.namedCaptureGroup(node, groupName);
        this.index = nextIndex;
    }

    private boolean isDigit(char ch) {
        return this.maxGroupNumber > 0 && '0' <= ch && ch <= '9';
    }

    public static class NamedCaptureGroupToken
    extends Token {
        private final TruffleString groupNameStr;

        public NamedCaptureGroupToken(TruffleString groupName) {
            super(Token.Kind.namedCaptureGroup);
            this.groupNameStr = groupName;
        }

        public TruffleString getGroupName() {
            return this.groupNameStr;
        }
    }

    public static class CaptureGroupToken
    extends Token {
        private final int groupNumber;
        private final int literalStart;
        private final int literalEnd;

        public CaptureGroupToken(int groupNumber, int literalStart, int literalEnd) {
            super(Token.Kind.captureGroup);
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

    public static class LiteralToken
    extends Token {
        private final int start;
        private final int end;

        public LiteralToken(int start2, int end2) {
            super(Token.Kind.literal);
            this.start = start2;
            this.end = end2;
        }

        public int getStart() {
            return this.start;
        }

        public int getEnd() {
            return this.end;
        }
    }

    public static class Token {
        private final Kind kind;

        public Token(Kind kind) {
            this.kind = kind;
        }

        public Kind getKind() {
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

    private static final class TokenConsumer
    implements Consumer<Void, Token[]> {
        private final ArrayList<Token> tokens = new ArrayList();

        private TokenConsumer() {
        }

        @Override
        public void literal(Void node, int start2, int end2) {
            this.tokens.add(new LiteralToken(start2, end2));
        }

        @Override
        public void match(Void node) {
            this.tokens.add(new Token(Token.Kind.match));
        }

        @Override
        public void matchHead(Void node) {
            this.tokens.add(new Token(Token.Kind.matchHead));
        }

        @Override
        public void matchTail(Void node) {
            this.tokens.add(new Token(Token.Kind.matchTail));
        }

        @Override
        public void captureGroup(Void node, int groupNumber, int literalStart, int literalEnd) {
            this.tokens.add(new CaptureGroupToken(groupNumber, literalStart, literalEnd));
        }

        @Override
        public void namedCaptureGroup(Void node, TruffleString groupName) {
            this.tokens.add(new NamedCaptureGroupToken(groupName));
        }

        @Override
        public Token[] getResult() {
            return this.tokens.toArray(new Token[0]);
        }
    }

    public static interface Consumer<T, R> {
        public void literal(T var1, int var2, int var3);

        public void match(T var1);

        public void matchHead(T var1);

        public void matchTail(T var1);

        public void captureGroup(T var1, int var2, int var3, int var4);

        public void namedCaptureGroup(T var1, TruffleString var2);

        public R getResult();
    }
}

