package com.oracle.js.parser;

import com.oracle.truffle.api.strings.TruffleString;
import java.util.Locale;

public enum TokenType {
   ERROR(TokenKind.SPECIAL, null),
   EOF(TokenKind.SPECIAL, null),
   EOL(TokenKind.SPECIAL, null),
   COMMENT(TokenKind.SPECIAL, null),
   DIRECTIVE_COMMENT(TokenKind.SPECIAL, null),
   NOT(TokenKind.UNARY, "!", 15, false),
   NE(TokenKind.BINARY, "!=", 9, true),
   NE_STRICT(TokenKind.BINARY, "!==", 9, true),
   MOD(TokenKind.BINARY, "%", 13, true),
   ASSIGN_MOD(TokenKind.BINARY, "%=", 2, false),
   BIT_AND(TokenKind.BINARY, "&", 8, true),
   AND(TokenKind.BINARY, "&&", 5, true),
   ASSIGN_BIT_AND(TokenKind.BINARY, "&=", 2, false),
   ASSIGN_AND(TokenKind.BINARY, "&&=", 2, false, 12),
   LPAREN(TokenKind.BRACKET, "(", 17, true),
   RPAREN(TokenKind.BRACKET, ")", 0, true),
   MUL(TokenKind.BINARY, "*", 13, true),
   ASSIGN_MUL(TokenKind.BINARY, "*=", 2, false),
   EXP(TokenKind.BINARY, "**", 14, false, 6),
   ASSIGN_EXP(TokenKind.BINARY, "**=", 2, false, 6),
   ADD(TokenKind.BINARY, "+", 12, true),
   INCPREFIX(TokenKind.UNARY, "++", 16, true),
   ASSIGN_ADD(TokenKind.BINARY, "+=", 2, false),
   COMMARIGHT(TokenKind.BINARY, ",", 1, true),
   SUB(TokenKind.BINARY, "-", 12, true),
   DECPREFIX(TokenKind.UNARY, "--", 16, true),
   ASSIGN_SUB(TokenKind.BINARY, "-=", 2, false),
   PERIOD(TokenKind.BRACKET, ".", 18, true),
   DIV(TokenKind.BINARY, "/", 13, true),
   ASSIGN_DIV(TokenKind.BINARY, "/=", 2, false),
   COLON(TokenKind.BINARY, ":"),
   SEMICOLON(TokenKind.BINARY, ";"),
   LT(TokenKind.BINARY, "<", 10, true),
   SHL(TokenKind.BINARY, "<<", 11, true),
   ASSIGN_SHL(TokenKind.BINARY, "<<=", 2, false),
   LE(TokenKind.BINARY, "<=", 10, true),
   ASSIGN(TokenKind.BINARY, "=", 2, false),
   EQ(TokenKind.BINARY, "==", 9, true),
   EQ_STRICT(TokenKind.BINARY, "===", 9, true),
   ARROW(TokenKind.BINARY, "=>", 2, true),
   GT(TokenKind.BINARY, ">", 10, true),
   GE(TokenKind.BINARY, ">=", 10, true),
   SAR(TokenKind.BINARY, ">>", 11, true),
   ASSIGN_SAR(TokenKind.BINARY, ">>=", 2, false),
   SHR(TokenKind.BINARY, ">>>", 11, true),
   ASSIGN_SHR(TokenKind.BINARY, ">>>=", 2, false),
   TERNARY(TokenKind.BINARY, "?", 3, false),
   LBRACKET(TokenKind.BRACKET, "[", 18, true),
   RBRACKET(TokenKind.BRACKET, "]", 0, true),
   BIT_XOR(TokenKind.BINARY, "^", 7, true),
   ASSIGN_BIT_XOR(TokenKind.BINARY, "^=", 2, false),
   LBRACE(TokenKind.BRACKET, "{"),
   BIT_OR(TokenKind.BINARY, "|", 6, true),
   ASSIGN_BIT_OR(TokenKind.BINARY, "|=", 2, false),
   OR(TokenKind.BINARY, "||", 4, true),
   ASSIGN_OR(TokenKind.BINARY, "||=", 2, false, 12),
   RBRACE(TokenKind.BRACKET, "}"),
   BIT_NOT(TokenKind.UNARY, "~", 15, false),
   ELLIPSIS(TokenKind.UNARY, "..."),
   NULLISHCOALESC(TokenKind.BINARY, "??", 4, true, 11),
   ASSIGN_NULLCOAL(TokenKind.BINARY, "??=", 2, false, 12),
   OPTIONAL_CHAIN(TokenKind.BRACKET, "?.", 18, true, 11),
   AT(TokenKind.UNARY, "@", 0, true, 14),
   ACCESSOR(TokenKind.CONTEXTUAL, "accessor"),
   AS(TokenKind.CONTEXTUAL, "as"),
   ASSERT(TokenKind.CONTEXTUAL, "assert"),
   ASYNC(TokenKind.CONTEXTUAL, "async"),
   AWAIT(TokenKind.CONTEXTUAL, "await"),
   BREAK(TokenKind.KEYWORD, "break"),
   CASE(TokenKind.KEYWORD, "case"),
   CATCH(TokenKind.KEYWORD, "catch"),
   CLASS(TokenKind.FUTURE, "class"),
   CONST(TokenKind.KEYWORD, "const"),
   CONTINUE(TokenKind.KEYWORD, "continue"),
   DEBUGGER(TokenKind.KEYWORD, "debugger"),
   DEFAULT(TokenKind.KEYWORD, "default"),
   DELETE(TokenKind.UNARY, "delete", 15, false),
   DO(TokenKind.KEYWORD, "do"),
   ELSE(TokenKind.KEYWORD, "else"),
   ENUM(TokenKind.FUTURE, "enum"),
   EXPORT(TokenKind.FUTURE, "export"),
   EXTENDS(TokenKind.FUTURE, "extends"),
   FALSE(TokenKind.LITERAL, "false"),
   FINALLY(TokenKind.KEYWORD, "finally"),
   FOR(TokenKind.KEYWORD, "for"),
   FROM(TokenKind.CONTEXTUAL, "from"),
   FUNCTION(TokenKind.KEYWORD, "function"),
   GET(TokenKind.CONTEXTUAL, "get"),
   IF(TokenKind.KEYWORD, "if"),
   IMPLEMENTS(TokenKind.FUTURESTRICT, "implements"),
   IMPORT(TokenKind.FUTURE, "import"),
   IN(TokenKind.BINARY, "in", 10, true),
   INSTANCEOF(TokenKind.BINARY, "instanceof", 10, true),
   INTERFACE(TokenKind.FUTURESTRICT, "interface"),
   LET(TokenKind.FUTURESTRICT, "let"),
   NEW(TokenKind.UNARY, "new", 18, false),
   NULL(TokenKind.LITERAL, "null"),
   OF(TokenKind.CONTEXTUAL, "of"),
   PACKAGE(TokenKind.FUTURESTRICT, "package"),
   PRIVATE(TokenKind.FUTURESTRICT, "private"),
   PROTECTED(TokenKind.FUTURESTRICT, "protected"),
   PUBLIC(TokenKind.FUTURESTRICT, "public"),
   RETURN(TokenKind.KEYWORD, "return"),
   SET(TokenKind.CONTEXTUAL, "set"),
   STATIC(TokenKind.FUTURESTRICT, "static"),
   SUPER(TokenKind.FUTURE, "super"),
   SWITCH(TokenKind.KEYWORD, "switch"),
   THIS(TokenKind.KEYWORD, "this"),
   THROW(TokenKind.KEYWORD, "throw"),
   TRUE(TokenKind.LITERAL, "true"),
   TRY(TokenKind.KEYWORD, "try"),
   TYPEOF(TokenKind.UNARY, "typeof", 15, false),
   VAR(TokenKind.KEYWORD, "var"),
   VOID(TokenKind.UNARY, "void", 15, false),
   WHILE(TokenKind.KEYWORD, "while"),
   WITH(TokenKind.KEYWORD, "with"),
   YIELD(TokenKind.FUTURESTRICT, "yield"),
   DECIMAL(TokenKind.LITERAL, null),
   NON_OCTAL_DECIMAL(TokenKind.LITERAL, null),
   HEXADECIMAL(TokenKind.LITERAL, null),
   OCTAL_LEGACY(TokenKind.LITERAL, null),
   OCTAL(TokenKind.LITERAL, null),
   BINARY_NUMBER(TokenKind.LITERAL, null),
   BIGINT(TokenKind.LITERAL, null),
   FLOATING(TokenKind.LITERAL, null),
   STRING(TokenKind.LITERAL, null),
   ESCSTRING(TokenKind.LITERAL, null),
   EXECSTRING(TokenKind.LITERAL, null),
   IDENT(TokenKind.LITERAL, null),
   REGEX(TokenKind.LITERAL, null),
   XML(TokenKind.LITERAL, null),
   OBJECT(TokenKind.LITERAL, null),
   ARRAY(TokenKind.LITERAL, null),
   TEMPLATE(TokenKind.LITERAL, null),
   TEMPLATE_HEAD(TokenKind.LITERAL, null),
   TEMPLATE_MIDDLE(TokenKind.LITERAL, null),
   TEMPLATE_TAIL(TokenKind.LITERAL, null),
   PRIVATE_IDENT(TokenKind.LITERAL, null),
   COMMALEFT(TokenKind.IR, null),
   DECPOSTFIX(TokenKind.IR, null),
   INCPOSTFIX(TokenKind.IR, null),
   SPREAD_ARGUMENT(TokenKind.IR, null),
   SPREAD_ARRAY(TokenKind.IR, null),
   SPREAD_OBJECT(TokenKind.IR, null),
   YIELD_STAR(TokenKind.IR, null),
   ASSIGN_INIT(TokenKind.IR, null),
   NAMEDEVALUATION(TokenKind.IR, null);

   private TokenType next = null;
   private final TokenKind kind;
   private final String name;
   private final TruffleString nameTS;
   private final int precedence;
   private final boolean isLeftAssociative;
   private final int ecmaScriptVersion;
   private static final TokenType[] tokenValues = values();

   private TokenType(final TokenKind kind, final String name) {
      this(kind, name, 0, false);
   }

   private TokenType(final TokenKind kind, final String name, final int precedence, final boolean isLeftAssociative) {
      this(kind, name, precedence, isLeftAssociative, 5);
   }

   private TokenType(final TokenKind kind, final String name, final int precedence, final boolean isLeftAssociative, final int ecmaScriptVersion) {
      this.kind = kind;
      this.name = name;
      this.nameTS = name == null ? null : ParserStrings.constant(name);
      this.precedence = precedence;
      this.isLeftAssociative = isLeftAssociative;
      this.ecmaScriptVersion = ecmaScriptVersion;
   }

   public boolean needsParens(final TokenType other, final boolean isLeft) {
      return other.precedence != 0 && (this.precedence > other.precedence || this.precedence == other.precedence && this.isLeftAssociative && !isLeft);
   }

   public boolean isOperator(final boolean in) {
      return this.kind == TokenKind.BINARY && (in || this != IN) && this.precedence != 0;
   }

   public int getLength() {
      assert this.name != null : "Token name not set";

      return this.name.length();
   }

   public String getName() {
      return this.name;
   }

   public TruffleString getNameTS() {
      return this.nameTS;
   }

   public String getNameOrType() {
      return this.name == null ? super.name().toLowerCase(Locale.ENGLISH) : this.getName();
   }

   public TokenType getNext() {
      return this.next;
   }

   void setNext(final TokenType next) {
      this.next = next;
   }

   public TokenKind getKind() {
      return this.kind;
   }

   public int getPrecedence() {
      return this.precedence;
   }

   public boolean isLeftAssociative() {
      return this.isLeftAssociative;
   }

   public int getECMAScriptVersion() {
      return this.ecmaScriptVersion;
   }

   boolean startsWith(final char c) {
      return this.name != null && !this.name.isEmpty() && this.name.charAt(0) == c;
   }

   static TokenType[] getValues() {
      return tokenValues;
   }

   @Override
   public String toString() {
      return this.getNameOrType();
   }

   public boolean isAssignment() {
      switch (this) {
         case ASSIGN:
         case ASSIGN_INIT:
         case ASSIGN_ADD:
         case ASSIGN_BIT_AND:
         case ASSIGN_BIT_OR:
         case ASSIGN_BIT_XOR:
         case ASSIGN_DIV:
         case ASSIGN_MOD:
         case ASSIGN_EXP:
         case ASSIGN_MUL:
         case ASSIGN_SAR:
         case ASSIGN_SHL:
         case ASSIGN_SHR:
         case ASSIGN_SUB:
         case ASSIGN_AND:
         case ASSIGN_OR:
         case ASSIGN_NULLCOAL:
            return true;
         default:
            return false;
      }
   }

   public boolean isContextualKeyword() {
      return this.kind == TokenKind.CONTEXTUAL;
   }

   public boolean isFutureStrict() {
      return this.kind == TokenKind.FUTURESTRICT;
   }
}
