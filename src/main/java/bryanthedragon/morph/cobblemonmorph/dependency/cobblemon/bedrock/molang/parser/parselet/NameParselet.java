package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.ast.FuncCallExpression;
import com.bedrockk.molang.ast.NameExpression;
import com.bedrockk.molang.parser.MoLangParser;
import com.bedrockk.molang.parser.PrefixParselet;
import com.bedrockk.molang.parser.tokenizer.Token;
import java.util.ArrayList;
import java.util.List;

public class NameParselet implements PrefixParselet {
   @Override
   public Expression parse(MoLangParser parser, Token token) {
      List<Expression> args = parser.parseArgs();
      String name = parser.fixNameShortcut(token.getText());
      ArrayList<String> names = new ArrayList<>(List.of(name.split("\\.")));
      Expression nameExpr = new NameExpression(names);
      String nameHead = parser.getNameHead(name);
      return (Expression)(args.size() <= 0 && !nameHead.equals("query") && !nameHead.equals("math") && !nameHead.equals("script")
         ? nameExpr
         : new FuncCallExpression(nameExpr, args.toArray(new Expression[args.size()])));
   }
}
