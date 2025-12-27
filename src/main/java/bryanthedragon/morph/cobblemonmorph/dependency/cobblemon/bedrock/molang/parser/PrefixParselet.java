package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.parser.tokenizer.Token;

public interface PrefixParselet {
   Expression parse(MoLangParser var1, Token var2);
}
