
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.ArrayAccessExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.InfixParselet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.MoLangParser;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.Precedence;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.tokenizer.Token;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.tokenizer.TokenType;

public class ArrayAccessParselet
implements InfixParselet {
    @Override
    public Expression parse(MoLangParser parser, Token token, Expression leftExpr) {
        Expression index = parser.parseExpression(this.getPrecedence());
        parser.consumeToken(TokenType.ARRAY_RIGHT);
        return new ArrayAccessExpression(leftExpr, index);
    }

    @Override
    public Precedence getPrecedence() {
        return Precedence.ARRAY_ACCESS;
    }
}

