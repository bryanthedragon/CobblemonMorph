
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.binaryop.ArrowExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.binaryop.BooleanAndExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.binaryop.BooleanOrExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.binaryop.CoalesceExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.binaryop.DivideExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.binaryop.EqualExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.binaryop.GreaterExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.binaryop.GreaterOrEqualExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.binaryop.MinusExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.binaryop.NotEqualExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.binaryop.PlusExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.binaryop.PowExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.binaryop.SmallerExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.binaryop.SmallerOrEqualExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.InfixParselet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.MoLangParser;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.Precedence;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.tokenizer.Token;

public final class GenericBinaryOpParselet
implements InfixParselet {
    private final Precedence precedence;

    @SuppressWarnings("incomplete-switch")
    public Expression parse(MoLangParser parser, Token token, Expression leftExpr) {
        Expression rightExpr = parser.parseExpression(this.getPrecedence());
        switch (token.getType()) {
            case ARROW: {
                return new ArrowExpression(leftExpr, rightExpr);
            }
            case AND: {
                return new BooleanAndExpression(leftExpr, rightExpr);
            }
            case OR: {
                return new BooleanOrExpression(leftExpr, rightExpr);
            }
            case COALESCE: {
                return new CoalesceExpression(leftExpr, rightExpr);
            }
            case SLASH: {
                return new DivideExpression(leftExpr, rightExpr);
            }
            case EQUALS: {
                return new EqualExpression(leftExpr, rightExpr);
            }
            case GREATER: {
                return new GreaterExpression(leftExpr, rightExpr);
            }
            case GREATER_OR_EQUALS: {
                return new GreaterOrEqualExpression(leftExpr, rightExpr);
            }
            case MINUS: {
                return new MinusExpression(leftExpr, rightExpr);
            }
            case NOT_EQUALS: {
                return new NotEqualExpression(leftExpr, rightExpr);
            }
            case PLUS: {
                return new PlusExpression(leftExpr, rightExpr);
            }
            case ASTERISK: {
                return new PowExpression(leftExpr, rightExpr);
            }
            case SMALLER: {
                return new SmallerExpression(leftExpr, rightExpr);
            }
            case SMALLER_OR_EQUALS: {
                return new SmallerOrEqualExpression(leftExpr, rightExpr);
            }
        }
        return null;
    }

    public Precedence getPrecedence() {
        return this.precedence;
    }

    public GenericBinaryOpParselet(Precedence precedence) {
        this.precedence = precedence;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GenericBinaryOpParselet)) {
            return false;
        }
        GenericBinaryOpParselet other = (GenericBinaryOpParselet)o;
        Precedence this$precedence = this.getPrecedence();
        Precedence other$precedence = other.getPrecedence();
        return !(this$precedence == null ? other$precedence != null : !((Object)((Object)this$precedence)).equals((Object)other$precedence));
    }
    
    @SuppressWarnings("unused")
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Precedence $precedence = this.getPrecedence();
        result = result * 59 + ($precedence == null ? 43 : ((Object)((Object)$precedence)).hashCode());
        return result;
    }

    public String toString() {
        return "GenericBinaryOpParselet(precedence=" + this.getPrecedence() + ")";
    }
}

