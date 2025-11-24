
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.TernaryExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.InfixParselet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.MoLangParser;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.Precedence;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.tokenizer.Token;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.tokenizer.TokenType;

public class TernaryParselet
implements InfixParselet {
    @Override
    public Expression parse(MoLangParser parser, Token token, Expression leftExpr) {
        if (parser.matchToken(TokenType.COLON)) {
            return new TernaryExpression(leftExpr, null, parser.parseExpression(this.getPrecedence()));
        }
        Expression thenExpr = parser.parseExpression(this.getPrecedence());
        if (!parser.matchToken(TokenType.COLON)) {
            return new TernaryExpression(leftExpr, thenExpr, null);
        }
        return new TernaryExpression(leftExpr, thenExpr, parser.parseExpression(this.getPrecedence()));
    }

    @Override
    public Precedence getPrecedence() {
        return Precedence.CONDITIONAL;
    }
}

