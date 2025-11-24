
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.AssignExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.InfixParselet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.MoLangParser;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.Precedence;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.tokenizer.Token;

public class AssignParselet
implements InfixParselet {
    @Override
    public Expression parse(MoLangParser parser, Token token, Expression leftExpr) {
        return new AssignExpression(leftExpr, parser.parseExpression(this.getPrecedence()));
    }

    @Override
    public Precedence getPrecedence() {
        return Precedence.ASSIGNMENT;
    }
}

