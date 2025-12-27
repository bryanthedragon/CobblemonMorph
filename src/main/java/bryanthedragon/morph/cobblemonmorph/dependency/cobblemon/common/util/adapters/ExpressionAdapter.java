package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import com.bedrockk.molang.Expression
import com.bedrockk.molang.MoLang
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

public object ExpressionAdapter : JsonDeserializer<Expression> {
   public open fun deserialize(json: JsonElement, typeOfT: Type, ctx: JsonDeserializationContext): Expression {
      val var10000: Expression = MoLang.createParser(json.getAsString()).parseExpression();
      return var10000;
   }
}
