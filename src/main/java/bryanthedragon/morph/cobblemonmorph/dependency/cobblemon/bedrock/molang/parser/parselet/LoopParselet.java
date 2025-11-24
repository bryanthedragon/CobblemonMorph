
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.LoopExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.MoLangParser;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.PrefixParselet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.tokenizer.Token;
import java.util.List;

public class LoopParselet
implements PrefixParselet {
    @Override
    public Expression parse(MoLangParser parser, Token token) {
        List<Expression> args = parser.parseArgs();
        if (args.size() != 2) {
            throw new RuntimeException("Loop: Expected 2 argument, " + args.size() + " argument given");
        }
        return new LoopExpression(args.get(0), args.get(1));
    }
}

