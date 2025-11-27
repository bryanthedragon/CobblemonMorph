package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet.ArrayAccessParselet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet.AssignParselet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet.BooleanNotParselet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet.BooleanParselet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet.BracketScopeParselet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet.BreakParselet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet.ContinueParselet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet.ForEachParselet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet.GenericBinaryOpParselet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet.GroupParselet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet.LoopParselet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet.NameParselet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet.NumberParselet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet.ReturnParselet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet.StringParselet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet.TernaryParselet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet.ThisParselet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet.UnaryMinusParselet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet.UnaryPlusParselet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.tokenizer.Token;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.tokenizer.TokenIterator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.tokenizer.TokenType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MoLangParser {
    private static final Map<TokenType, PrefixParselet> prefixParselets = new HashMap<TokenType, PrefixParselet>();
    private static final Map<TokenType, InfixParselet> infixParselets = new HashMap<TokenType, InfixParselet>();
    private final TokenIterator tokenIterator;
    private final String originalString;
    private final List<Token> readTokens = new ArrayList<Token>();

    public MoLangParser(TokenIterator iterator, String originalString) {
        this.tokenIterator = iterator;
        this.originalString = originalString;
    }

    public List<Expression> parse() {
        Expression expr;
        ArrayList<Expression> exprs = new ArrayList<Expression>();
        while ((expr = this.parseExpression()) != null) {
            exprs.add(expr);
            if (this.matchToken(TokenType.SEMICOLON)) continue;
        }
        return exprs;
    }

    public Expression parseExpression() {
        return this.parseExpression(Precedence.ANYTHING);
    }

    public Expression parseExpression(Precedence precedence) {
        Token token = this.consumeToken();
        if (token.getType().equals((Object)TokenType.EOF)) {
            return null;
        }
        PrefixParselet parselet = prefixParselets.get((Object)token.getType());
        if (parselet == null) {
            throw new RuntimeException("Cannot parse " + token.getType().name() + " expression");
        }
        Expression expr = parselet.parse(this, token);
        this.initExpr(expr, token);
        return this.parseInfixExpression(expr, precedence);
    }

    private Expression parseInfixExpression(Expression left, Precedence precedence) {
        while (precedence.ordinal() < this.getPrecedence().ordinal()) {
            Token token = this.consumeToken();
            left = infixParselets.get((Object)token.getType()).parse(this, token, left);
            this.initExpr(left, token);
        }
        return left;
    }

    private void initExpr(Expression expression, Token token) {
        expression.getAttributes().put("position", token.getPosition());
        expression.setOriginalString(this.originalString);
    }

    private Precedence getPrecedence() {
        InfixParselet parselet;
        Token token = this.readToken();
        if (token != null && (parselet = infixParselets.get((Object)token.getType())) != null) {
            return parselet.getPrecedence();
        }
        return Precedence.ANYTHING;
    }

    public List<Expression> parseArgs() {
        ArrayList<Expression> args = new ArrayList<Expression>();
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
        switch (splits[0]) {
            case "q": {
                splits[0] = "query";
                break;
            }
            case "v": {
                splits[0] = "variable";
                break;
            }
            case "t": {
                splits[0] = "temp";
                break;
            }
            case "c": {
                splits[0] = "context";
                break;
            }
            case "s": {
                splits[0] = "script";
            }
        }
        return String.join((CharSequence)".", splits);
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
        if (expectedType != null && !token.getType().equals((Object)expectedType)) {
            throw new RuntimeException("Expected token " + expectedType.name() + " and " + token.getType().name() + " given");
        }
        return this.readTokens.remove(0);
    }

    public boolean matchToken(TokenType expectedType) {
        return this.matchToken(expectedType, true);
    }

    public boolean matchToken(TokenType expectedType, boolean consume) {
        Token token = this.readToken();
        if (token == null || !token.getType().equals((Object)expectedType)) {
            return false;
        }
        if (consume) {
            this.consumeToken();
        }
        return true;
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
        prefixParselets.put(TokenType.NAME, new NameParselet());
        prefixParselets.put(TokenType.STRING, new StringParselet());
        prefixParselets.put(TokenType.NUMBER, new NumberParselet());
        prefixParselets.put(TokenType.TRUE, new BooleanParselet());
        prefixParselets.put(TokenType.FALSE, new BooleanParselet());
        prefixParselets.put(TokenType.RETURN, new ReturnParselet());
        prefixParselets.put(TokenType.CONTINUE, new ContinueParselet());
        prefixParselets.put(TokenType.BREAK, new BreakParselet());
        prefixParselets.put(TokenType.LOOP, new LoopParselet());
        prefixParselets.put(TokenType.FOR_EACH, new ForEachParselet());
        prefixParselets.put(TokenType.THIS, new ThisParselet());
        prefixParselets.put(TokenType.BRACKET_LEFT, new GroupParselet());
        prefixParselets.put(TokenType.CURLY_BRACKET_LEFT, new BracketScopeParselet());
        prefixParselets.put(TokenType.MINUS, new UnaryMinusParselet());
        prefixParselets.put(TokenType.PLUS, new UnaryPlusParselet());
        prefixParselets.put(TokenType.BANG, new BooleanNotParselet());
        infixParselets.put(TokenType.QUESTION, new TernaryParselet());
        infixParselets.put(TokenType.ARRAY_LEFT, new ArrayAccessParselet());
        infixParselets.put(TokenType.PLUS, new GenericBinaryOpParselet(Precedence.SUM));
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
        infixParselets.put(TokenType.ARROW, new GenericBinaryOpParselet(Precedence.ARROW));
        infixParselets.put(TokenType.ASSIGN, new AssignParselet());
    }
}

