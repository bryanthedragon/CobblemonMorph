package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.parser.parselet.ArrayAccessParselet;
import com.bedrockk.molang.parser.parselet.AssignParselet;
import com.bedrockk.molang.parser.parselet.BooleanNotParselet;
import com.bedrockk.molang.parser.parselet.BooleanParselet;
import com.bedrockk.molang.parser.parselet.BracketScopeParselet;
import com.bedrockk.molang.parser.parselet.BreakParselet;
import com.bedrockk.molang.parser.parselet.ContinueParselet;
import com.bedrockk.molang.parser.parselet.ForEachParselet;
import com.bedrockk.molang.parser.parselet.GenericBinaryOpParselet;
import com.bedrockk.molang.parser.parselet.GroupParselet;
import com.bedrockk.molang.parser.parselet.LoopParselet;
import com.bedrockk.molang.parser.parselet.NameParselet;
import com.bedrockk.molang.parser.parselet.NumberParselet;
import com.bedrockk.molang.parser.parselet.ReturnParselet;
import com.bedrockk.molang.parser.parselet.StringParselet;
import com.bedrockk.molang.parser.parselet.TernaryParselet;
import com.bedrockk.molang.parser.parselet.ThisParselet;
import com.bedrockk.molang.parser.parselet.UnaryMinusParselet;
import com.bedrockk.molang.parser.parselet.UnaryPlusParselet;
import com.bedrockk.molang.parser.tokenizer.Token;
import com.bedrockk.molang.parser.tokenizer.TokenIterator;
import com.bedrockk.molang.parser.tokenizer.TokenType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MoLangParser {
   private static final Map<TokenType, PrefixParselet> prefixParselets = new HashMap<>();
   private static final Map<TokenType, InfixParselet> infixParselets = new HashMap<>();
   private final TokenIterator tokenIterator;
   private final String originalString;
   private final List<Token> readTokens = new ArrayList<>();

   public MoLangParser(TokenIterator iterator, String originalString) {
      this.tokenIterator = iterator;
      this.originalString = originalString;
   }

   public List<Expression> parse() {
      List<Expression> exprs = new ArrayList<>();

      do {
         Expression expr = this.parseExpression();
         if (expr == null) {
            break;
         }
         exprs.add(expr);
      } 
      while (this.matchToken(TokenType.SEMICOLON));
      return exprs;
   }

   public Expression parseExpression() {
      return this.parseExpression(Precedence.ANYTHING);
   }

   public Expression parseExpression(Precedence precedence) {
      Token token = this.consumeToken();
      if (token.getType().equals(TokenType.EOF)) {
         return null;
      } 
      else {
         PrefixParselet parselet = prefixParselets.get(token.getType());
         if (parselet == null) {
            throw new RuntimeException("Cannot parse " + token.getType().name() + " expression");
         } 
         else {
            Expression expr = parselet.parse(this, token);
            this.initExpr(expr, token);
            return this.parseInfixExpression(expr, precedence);
         }
      }
   }

   private Expression parseInfixExpression(Expression left, Precedence precedence) {
      while (precedence.ordinal() < this.getPrecedence().ordinal()) {
         Token token = this.consumeToken();
         left = infixParselets.get(token.getType()).parse(this, token, left);
         this.initExpr(left, token);
      }
      return left;
   }

   private void initExpr(Expression expression, Token token) {
      expression.getAttributes().put("position", token.getPosition());
      expression.setOriginalString(this.originalString);
   }

   private Precedence getPrecedence() {
      Token token = this.readToken();
      if (token != null) {
         InfixParselet parselet = infixParselets.get(token.getType());
         if (parselet != null) {
            return parselet.getPrecedence();
         }
      }
      return Precedence.ANYTHING;
   }

   public List<Expression> parseArgs() {
      List<Expression> args = new ArrayList<>();
      if (this.matchToken(TokenType.BRACKET_LEFT) && !this.matchToken(TokenType.BRACKET_RIGHT)) {
         do {
            args.add(this.parseExpression());
         } while (this.matchToken(TokenType.COMMA));
         this.consumeToken(TokenType.BRACKET_RIGHT);
      }

      return args;
   }

   public String fixNameShortcut(String name) {
      String[] splits = name.split("\\.");
      String var3 = splits[0];
      switch (var3) {
         case "q":
            splits[0] = "query";
            break;
         case "v":
            splits[0] = "variable";
            break;
         case "t":
            splits[0] = "temp";
            break;
         case "c":
            splits[0] = "context";
            break;
         case "s":
            splits[0] = "script";
      }

      return String.join(".", splits);
   }

   public String getNameHead(String name) {
      return name.split("\\.")[0];
   }

   public Token consumeToken() {
      return this.consumeToken(null);
   }

   public Token consumeToken(TokenType expectedType) {
      this.tokenIterator.step();
      Token token = this.readToken();
      if (expectedType != null && !token.getType().equals(expectedType)) {
         throw new RuntimeException("Expected token " + expectedType.name() + " and " + token.getType().name() + " given");
      } 
      else {
         return this.readTokens.remove(0);
      }
   }

   public boolean matchToken(TokenType expectedType) {
      return this.matchToken(expectedType, true);
   }

   public boolean matchToken(TokenType expectedType, boolean consume) {
      Token token = this.readToken();
      if (token != null && token.getType().equals(expectedType)) {
         if (consume) {
            this.consumeToken();
         }
         return true;
      } 
      else {
         return false;
      }
   }

   private Token readToken() {
      return this.readToken(0);
   }

   private Token readToken(int distance) {
      while (distance >= this.readTokens.size()) {
         this.readTokens.add(this.tokenIterator.next());
      }
      return this.readTokens.get(distance);
   }

   static {
      prefixParselets.put(TokenType.NAME, (PrefixParselet) new NameParselet());
      prefixParselets.put(TokenType.STRING, (PrefixParselet) new StringParselet());
      prefixParselets.put(TokenType.NUMBER, (PrefixParselet) new NumberParselet());
      prefixParselets.put(TokenType.TRUE, (PrefixParselet) new BooleanParselet());
      prefixParselets.put(TokenType.FALSE, (PrefixParselet) new BooleanParselet());
      prefixParselets.put(TokenType.RETURN, (PrefixParselet) new ReturnParselet());
      prefixParselets.put(TokenType.CONTINUE, (PrefixParselet) new ContinueParselet());
      prefixParselets.put(TokenType.BREAK, (PrefixParselet) new BreakParselet());
      prefixParselets.put(TokenType.LOOP, (PrefixParselet) new LoopParselet());
      prefixParselets.put(TokenType.FOR_EACH, (PrefixParselet) new ForEachParselet());
      prefixParselets.put(TokenType.THIS, (PrefixParselet) new ThisParselet());
      prefixParselets.put(TokenType.BRACKET_LEFT, (PrefixParselet) new GroupParselet());
      prefixParselets.put(TokenType.CURLY_BRACKET_LEFT, (PrefixParselet) new BracketScopeParselet());
      prefixParselets.put(TokenType.MINUS, (PrefixParselet) new UnaryMinusParselet());
      prefixParselets.put(TokenType.PLUS, (PrefixParselet) new UnaryPlusParselet());
      prefixParselets.put(TokenType.BANG, (PrefixParselet) new BooleanNotParselet());
      infixParselets.put(TokenType.QUESTION, (InfixParselet) new TernaryParselet());
      infixParselets.put(TokenType.ARRAY_LEFT, (InfixParselet) new ArrayAccessParselet());
      infixParselets.put(TokenType.PLUS,  new GenericBinaryOpParselet(Precedence.SUM));
      infixParselets.put(TokenType.MINUS, new GenericBinaryOpParselet(Precedence.SUM));
      infixParselets.put(TokenType.SLASH, new GenericBinaryOpParselet(Precedence.PRODUCT));
      infixParselets.put(TokenType.ASTERISK, new GenericBinaryOpParselet(Precedence.PRODUCT));
      infixParselets.put(TokenType.EQUALS, new GenericBinaryOpParselet(Precedence.COMPARE));
      infixParselets.put(TokenType.NOT_EQUALS, new GenericBinaryOpParselet(Precedence.COMPARE));
      infixParselets.put(TokenType.GREATER, new GenericBinaryOpParselet(Precedence.COMPARE));
      infixParselets.put(TokenType.GREATER_OR_EQUALS, new GenericBinaryOpParselet(Precedence.COMPARE));
      infixParselets.put(TokenType.SMALLER, new GenericBinaryOpParselet(Precedence.COMPARE));
      infixParselets.put(TokenType.SMALLER_OR_EQUALS, new GenericBinaryOpParselet(Precedence.COMPARE));
      infixParselets.put(TokenType.AND, new GenericBinaryOpParselet(Precedence.AND));
      infixParselets.put(TokenType.OR, new GenericBinaryOpParselet(Precedence.OR));
      infixParselets.put(TokenType.COALESCE, new GenericBinaryOpParselet(Precedence.COALESCE));
      infixParselets.put(TokenType.ARROW,  new GenericBinaryOpParselet(Precedence.ARROW));
      infixParselets.put(TokenType.ASSIGN, (InfixParselet) new AssignParselet());
   }
}
