package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.FuncCallExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.NameExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.MoLangParser;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.PrefixParselet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.tokenizer.Token;

import java.util.ArrayList;
import java.util.List;

public class NameParselet implements PrefixParselet {
    @Override
    public Expression parse(MoLangParser parser, Token token) {
        List<Expression> args = parser.parseArgs();
        String name = parser.fixNameShortcut(token.getText());
        ArrayList<String> names = new ArrayList<String>(List.of(name.split("\\.")));
        NameExpression nameExpr = new NameExpression(names);
        String nameHead = parser.getNameHead(name);
        if (args.size() > 0 || nameHead.equals("query") || nameHead.equals("math") || nameHead.equals("script")) {
            return new FuncCallExpression(nameExpr, args.toArray(new Expression[args.size()]));
        }
        return nameExpr;
    }
}

