
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.ForEachExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.MoLangParser;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.PrefixParselet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.tokenizer.Token;
import java.util.List;

public class ForEachParselet
implements PrefixParselet {
    @Override
    public Expression parse(MoLangParser parser, Token token) {
        List<Expression> args = parser.parseArgs();
        if (args.size() != 3) {
            throw new RuntimeException("ForEach: Expected 3 argument, " + args.size() + " argument given");
        }
        return new ForEachExpression(args.get(0), args.get(1), args.get(2));
    }
}

