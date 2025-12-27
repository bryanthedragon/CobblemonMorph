package com.oracle.js.parser;

import com.oracle.js.parser.ir.IdentNode;
import com.oracle.js.parser.ir.LiteralNode;
import com.oracle.truffle.api.strings.TruffleString;
import java.math.BigInteger;
import java.util.function.Function;

public abstract class AbstractParser {
   private static final String MSG_EXPECTED = "expected";
   private static final String MSG_EXPECTED_STMT = "expected.stmt";
   private static final String MSG_PARSER_ERROR = "parser.error.";
   protected final Source source;
   protected final ErrorManager errors;
   protected TokenStream stream;
   protected int k;
   protected long previousToken;
   protected long token;
   protected TokenType type;
   protected TokenType last;
   protected int start;
   protected int finish;
   protected int line;
   protected int linePosition;
   protected Lexer lexer;
   protected boolean isStrictMode;
   protected final int lineOffset;
   private static final String SOURCE_URL_PREFIX = "sourceURL=";

   protected AbstractParser(final Source source, final ErrorManager errors, final boolean strict, final int lineOffset) {
      if (source.getLength() > 268435455) {
         throw new RuntimeException("Source exceeds size limit of 268435455 bytes");
      } else {
         this.source = source;
         this.errors = errors;
         this.k = -1;
         this.token = Token.toDesc(TokenType.EOL, 0, 1);
         this.type = TokenType.EOL;
         this.last = TokenType.EOL;
         this.isStrictMode = strict;
         this.lineOffset = lineOffset;
      }
   }

   protected final long getToken(final int i) {
      while (i > this.stream.last()) {
         if (this.stream.isFull()) {
            this.stream.grow();
         }

         this.lexer.lexify();
      }

      return this.stream.get(i);
   }

   protected final TokenType T(final int i) {
      return Token.descType(this.getToken(i));
   }

   protected final TokenType next() {
      do {
         this.nextOrEOL();
      } while (this.type == TokenType.EOL || this.type == TokenType.COMMENT);

      return this.type;
   }

   protected final TokenType nextOrEOL() {
      do {
         this.nextToken();
         if (this.type == TokenType.DIRECTIVE_COMMENT) {
            this.checkDirectiveComment();
         }
      } while (this.type == TokenType.COMMENT || this.type == TokenType.DIRECTIVE_COMMENT);

      return this.type;
   }

   private void checkDirectiveComment() {
      if (this.source.getExplicitURL() == null) {
         String comment = (String)this.lexer.getValueOf(this.token, this.isStrictMode);
         if (comment.regionMatches(4, "sourceURL=", 0, "sourceURL=".length())) {
            this.source.setExplicitURL(comment.substring(4 + "sourceURL=".length()));
         }
      }
   }

   private TokenType nextToken() {
      if (this.type != TokenType.EOF) {
         this.k++;
         long lastToken = this.token;
         boolean comment = this.type == TokenType.COMMENT;
         if (!comment) {
            this.last = this.type;
            this.previousToken = this.token;
         }

         this.token = this.getToken(this.k);
         this.type = Token.descType(this.token);
         if (!comment && this.last != TokenType.EOL) {
            this.finish = this.start + Token.descLength(lastToken);
         }

         if (this.type == TokenType.EOL) {
            this.line = Token.descLength(this.token);
            this.linePosition = Token.descPosition(this.token);
         } else {
            this.start = Token.descPosition(this.token);
         }
      }

      return this.type;
   }

   protected static String message(final String msgId, final String... args) {
      return ECMAErrors.getMessage("parser.error." + msgId, args);
   }

   protected static String message(final String msgId, IdentNode ident) {
      return ECMAErrors.getMessage("parser.error." + msgId, ident.getName());
   }

   protected final ParserException error(final String message, final long errorToken) {
      return this.error(JSErrorType.SyntaxError, message, errorToken);
   }

   protected final ParserException error(final JSErrorType errorType, final String message, final long errorToken) {
      int position = Token.descPosition(errorToken);
      int lineNum = this.source.getLine(position);
      int columnNum = this.source.getColumn(position);
      return new ParserException(errorType, message, this.source, lineNum, columnNum, errorToken);
   }

   protected final ParserException error(final String message) {
      return this.error(JSErrorType.SyntaxError, message);
   }

   protected final ParserException error(final JSErrorType errorType, final String message) {
      int position = Token.descPosition(this.token);
      int column = this.source.getColumn(position);
      return new ParserException(errorType, message, this.source, this.line, column, this.token);
   }

   protected final void warning(final JSErrorType errorType, final String message, final long errorToken) {
      this.errors.warning(this.error(errorType, message, errorToken));
   }

   protected final String expectMessage(final TokenType expected) {
      String tokenString = Token.toString(this.source, this.token);
      String msg;
      if (expected == null) {
         msg = message("expected.stmt", tokenString);
      } else {
         msg = message("expected", expected.getNameOrType(), tokenString);
      }

      return msg;
   }

   protected final String expectMessage(final TokenType expected, final long errorToken) {
      String expectedName = expected.getNameOrType();
      String tokenString = Token.toString(this.source, errorToken);
      return message("expected", expectedName, tokenString);
   }

   protected final void expect(final TokenType expected) throws ParserException {
      this.expectDontAdvance(expected);
      this.next();
   }

   protected final void expectDontAdvance(final TokenType expected) throws ParserException {
      if (this.type != expected) {
         throw this.error(this.expectMessage(expected));
      }
   }

   protected final Object getValueNoEscape() {
      try {
         return this.lexer.getValueOf(this.token, this.isStrictMode, false);
      } catch (ParserException var2) {
         this.errors.error(var2);
         return null;
      }
   }

   protected final Object getValue() {
      return this.getValue(this.token);
   }

   protected final Object getValue(final long valueToken) {
      try {
         return this.lexer.getValueOf(valueToken, this.isStrictMode);
      } catch (ParserException var4) {
         this.errors.error(var4);
         return null;
      }
   }

   protected final boolean isNonStrictModeIdent() {
      return !this.isStrictMode && this.type.getKind() == TokenKind.FUTURESTRICT;
   }

   protected final IdentNode getIdent() {
      long identToken = this.token;
      if (this.type == TokenType.IDENT) {
         TruffleString ident = (TruffleString)this.getValue(identToken);
         this.next();
         return this.createIdentNode(identToken, this.finish, ident);
      } else if (!this.type.isContextualKeyword() && !this.isNonStrictModeIdent()) {
         throw this.error(this.expectMessage(TokenType.IDENT));
      } else {
         TruffleString ident = this.lexer.stringIntern(this.type.getNameTS());
         this.next();
         return new IdentNode(identToken, this.finish, ident);
      }
   }

   protected IdentNode createIdentNode(final long identToken, final int identFinish, final TruffleString name) {
      assert this.isInterned(name) : name;

      return new IdentNode(identToken, identFinish, name);
   }

   private boolean isInterned(final TruffleString name) {
      return isSame(this.lexer.stringIntern(name.toJavaStringUncached()), name);
   }

   private static boolean isSame(Object a, Object b) {
      return a == b;
   }

   protected final boolean isIdentifierName() {
      return this.isIdentifierName(this.token);
   }

   protected final boolean isIdentifierName(long currentToken) {
      TokenType currentType = Token.descType(currentToken);

      assert currentType != TokenType.IDENT;

      TokenKind kind = currentType.getKind();
      if (kind == TokenKind.KEYWORD || kind == TokenKind.FUTURE || kind == TokenKind.FUTURESTRICT || kind == TokenKind.CONTEXTUAL) {
         return true;
      } else if (kind == TokenKind.LITERAL) {
         switch (currentType) {
            case FALSE:
            case NULL:
            case TRUE:
               return true;
            default:
               return false;
         }
      } else {
         long identToken = Token.recast(currentToken, TokenType.IDENT);
         TruffleString ident = (TruffleString)this.getValue(identToken);
         return ident != null && !ident.isEmpty() && Character.isJavaIdentifierStart(ident.toJavaStringUncached().charAt(0));
      }
   }

   protected final IdentNode getIdentifierName() {
      if (this.type == TokenType.IDENT) {
         return this.getIdent();
      } else if (this.isIdentifierName()) {
         long identToken = Token.recast(this.token, TokenType.IDENT);
         TruffleString ident = (TruffleString)this.getValue(identToken);

         assert this.isInterned(ident);

         this.next();
         return this.createIdentNode(identToken, this.finish, ident);
      } else {
         this.expect(TokenType.IDENT);
         return null;
      }
   }

   protected final LiteralNode<?> getLiteral() throws ParserException {
      long literalToken = this.token;
      Object value = this.getValue();
      this.next();
      LiteralNode<?> node = null;
      if (value == null) {
         node = LiteralNode.newInstance(literalToken, this.finish);
      } else if (value instanceof BigInteger) {
         node = LiteralNode.newInstance(literalToken, this.finish, (BigInteger)value);
      } else if (value instanceof Number) {
         node = LiteralNode.newInstance(literalToken, this.finish, (Number)value, this.getNumberToStringConverter());
      } else if (value instanceof TruffleString) {
         node = LiteralNode.newInstance(literalToken, (TruffleString)value);
      } else if (value instanceof Lexer.LexerToken) {
         this.validateLexerToken((Lexer.LexerToken)value);
         node = LiteralNode.newInstance(literalToken, this.finish, (Lexer.LexerToken)value);
      } else {
         assert false : "unknown type for LiteralNode: " + value.getClass();
      }

      return node;
   }

   protected void validateLexerToken(final Lexer.LexerToken lexerToken) {
   }

   protected Function<Number, TruffleString> getNumberToStringConverter() {
      return null;
   }
}
