package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import com.bedrockk.molang.Expression
import com.bedrockk.molang.MoLang
import com.bedrockk.molang.ast.NumberExpression
import com.bedrockk.molang.runtime.MoLangEnvironment
import com.bedrockk.molang.runtime.MoLangRuntime
import com.bedrockk.molang.runtime.MoParams
import com.bedrockk.molang.runtime.MoScope
import com.bedrockk.molang.runtime.struct.VariableStruct
import com.bedrockk.molang.runtime.value.MoValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ListExpression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.SingleExpression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.world.phys.Vec3

public final val genericRuntime: MoLangRuntime = MoLangFunctions.INSTANCE.setup(new MoLangRuntime())

public fun MoLangRuntime.resolve(expression: Expression): MoValue {
   try {
      val var10000: MoValue = expression.evaluate(new MoScope(), `$this$resolve`.getEnvironment());
      return var10000;
   } catch (var4: Exception) {
      throw new IllegalArgumentException("Unable to parse expression: ${getString(expression)}", var4);
   }
}

public fun MoLangRuntime.resolveDouble(expression: Expression): Double {
   return resolve(`$this$resolveDouble`, expression).asDouble();
}

public fun MoLangRuntime.resolveFloat(expression: Expression): Float {
   return (float)resolve(`$this$resolveFloat`, expression).asDouble();
}

public fun MoLangRuntime.resolveInt(expression: Expression): Int {
   return (int)resolveDouble(`$this$resolveInt`, expression);
}

public fun MoLangRuntime.resolveString(expression: Expression): String {
   val var10000: java.lang.String = resolve(`$this$resolveString`, expression).asString();
   return var10000;
}

public fun MoLangRuntime.resolveObject(expression: Expression): ObjectValue<*> {
   val var10000: MoValue = resolve(`$this$resolveObject`, expression);
   return var10000 as ObjectValue<?>;
}

public fun MoLangRuntime.resolveBoolean(expression: Expression): Boolean {
   return resolve(`$this$resolveBoolean`, expression).asDouble() != 0.0;
}

public fun MoLangRuntime.resolve(expression: ExpressionLike): MoValue {
   return expression.resolve(`$this$resolve`);
}

public fun MoLangRuntime.resolveDouble(expression: ExpressionLike): Double {
   return resolve(`$this$resolveDouble`, expression).asDouble();
}

public fun MoLangRuntime.resolveFloat(expression: ExpressionLike): Float {
   return (float)resolve(`$this$resolveFloat`, expression).asDouble();
}

public fun MoLangRuntime.resolveInt(expression: ExpressionLike): Int {
   return (int)resolveDouble(`$this$resolveInt`, expression);
}

public fun MoLangRuntime.resolveString(expression: ExpressionLike): String {
   val var10000: java.lang.String = resolve(`$this$resolveString`, expression).asString();
   return var10000;
}

public fun MoLangRuntime.resolveObject(expression: ExpressionLike): ObjectValue<*> {
   val var10000: MoValue = resolve(`$this$resolveObject`, expression);
   return var10000 as ObjectValue<?>;
}

public fun MoLangRuntime.resolveBoolean(expression: ExpressionLike): Boolean {
   return resolve(`$this$resolveBoolean`, expression).asDouble() != 0.0;
}

public fun MoLangRuntime.resolveVec3d(triple: Triple<Expression, Expression, Expression>): Vec3 {
   return new Vec3(
      resolveDouble(`$this$resolveVec3d`, triple.getFirst() as Expression),
      resolveDouble(`$this$resolveVec3d`, triple.getSecond() as Expression),
      resolveDouble(`$this$resolveVec3d`, triple.getThird() as Expression)
   );
}

public fun MoLangRuntime.resolveBoolean(expression: Expression, pokemon: Pokemon): Boolean {
   val var10000: MoLangEnvironment = `$this$resolveBoolean`.getEnvironment();
   writePokemon(var10000, pokemon);
   return resolveBoolean(`$this$resolveBoolean`, expression);
}

public fun MoLangRuntime.resolveDouble(expression: Expression, pokemon: Pokemon): Double {
   val var10000: MoLangEnvironment = `$this$resolveDouble`.getEnvironment();
   writePokemon(var10000, pokemon);
   return resolveDouble(`$this$resolveDouble`, expression);
}

public fun MoLangRuntime.resolveInt(expression: Expression, pokemon: Pokemon): Int {
   val var10000: MoLangEnvironment = `$this$resolveInt`.getEnvironment();
   writePokemon(var10000, pokemon);
   return resolveInt(`$this$resolveInt`, expression);
}

public fun MoLangRuntime.resolveFloat(expression: Expression, pokemon: Pokemon): Float {
   val var10000: MoLangEnvironment = `$this$resolveFloat`.getEnvironment();
   writePokemon(var10000, pokemon);
   return resolveFloat(`$this$resolveFloat`, expression);
}

public fun MoLangRuntime.resolveBoolean(expression: Expression, pokemon: BattlePokemon): Boolean {
   val var10000: MoLangEnvironment = `$this$resolveBoolean`.getEnvironment();
   writePokemon(var10000, pokemon);
   return resolveBoolean(`$this$resolveBoolean`, expression);
}

public fun MoLangRuntime.resolveDouble(expression: Expression, pokemon: BattlePokemon): Double {
   val var10000: MoLangEnvironment = `$this$resolveDouble`.getEnvironment();
   writePokemon(var10000, pokemon);
   return resolveDouble(`$this$resolveDouble`, expression);
}

public fun MoLangRuntime.resolveInt(expression: Expression, pokemon: BattlePokemon): Int {
   val var10000: MoLangEnvironment = `$this$resolveInt`.getEnvironment();
   writePokemon(var10000, pokemon);
   return resolveInt(`$this$resolveInt`, expression);
}

public fun MoLangRuntime.resolveFloat(expression: Expression, pokemon: BattlePokemon): Float {
   val var10000: MoLangEnvironment = `$this$resolveFloat`.getEnvironment();
   writePokemon(var10000, pokemon);
   return resolveFloat(`$this$resolveFloat`, expression);
}

public fun Expression.getString(): String {
   var var10000: java.lang.String = `$this$getString`.getOriginalString();
   if (var10000 == null) {
      var10000 = "0";
   }

   return var10000;
}

public fun Double.asExpression(): NumberExpression {
   return new NumberExpression(`$this$asExpression`);
}

public fun String.asExpression(): Expression {
   try {
      return MoLang.createParser(if (`$this$asExpression` == "") "0.0" else `$this$asExpression`).parseExpression();
   } catch (var3: Exception) {
      Cobblemon.INSTANCE.getLOGGER().error("Failed to parse MoLang expression: $`$this$asExpression`");
      throw var3;
   }
}

public fun String.asExpressions(): MutableList<Expression> {
   try {
      return MoLang.createParser(if (`$this$asExpressions` == "") "0.0" else `$this$asExpressions`).parse();
   } catch (var3: Exception) {
      Cobblemon.INSTANCE.getLOGGER().error("Failed to parse MoLang expressions: $`$this$asExpressions`");
      throw var3;
   }
}

public fun String.asExpressionLike(): ExpressionLike {
   try {
      val ls: java.util.List = MoLang.createParser(if (`$this$asExpressionLike` == "") "0.0" else `$this$asExpressionLike`).parse();
      val var10000: ExpressionLike;
      if (ls.size() == 1) {
         val var10002: Any = ls.get(0);
         var10000 = new SingleExpression(var10002 as Expression);
      } else {
         var10000 = new ListExpression(ls);
      }

      return var10000;
   } catch (var3: Exception) {
      Cobblemon.INSTANCE.getLOGGER().error("Failed to parse MoLang expressions: $`$this$asExpressionLike`");
      throw var3;
   }
}

public fun MoLangEnvironment.writePokemon(pokemon: Pokemon) {
   val pokemonStruct: VariableStruct = new VariableStruct();
   pokemon.writeVariables(pokemonStruct);
   `$this$writePokemon`.setSimpleVariable("pokemon", pokemonStruct);
}

public fun MoLangEnvironment.writePokemon(pokemon: BattlePokemon) {
   val pokemonStruct: VariableStruct = new VariableStruct();
   pokemon.writeVariables(pokemonStruct);
   `$this$writePokemon`.setSimpleVariable("pokemon", pokemonStruct);
}

public fun List<String>.asExpressionLike(): ExpressionLike {
   return asExpressionLike(CollectionsKt.joinToString$default(`$this$asExpressionLike`, "\n", null, null, 0, null, null, 62, null));
}

public fun List<Expression>.resolve(runtime: MoLangRuntime): MoValue {
   return runtime.execute(`$this$resolve`);
}

public fun List<Expression>.resolveDouble(runtime: MoLangRuntime): Double {
   return resolve(`$this$resolveDouble`, runtime).asDouble();
}

public fun List<Expression>.resolveInt(runtime: MoLangRuntime): Int {
   return (int)resolveDouble(`$this$resolveInt`, runtime);
}

public fun List<Expression>.resolveBoolean(runtime: MoLangRuntime): Boolean {
   return resolveDouble(`$this$resolveBoolean`, runtime) == 1.0;
}

public fun List<Expression>.resolveObject(runtime: MoLangRuntime): ObjectValue<*> {
   val var10000: MoValue = resolve(`$this$resolveObject`, runtime);
   return var10000 as ObjectValue<?>;
}

public fun MoParams.getStringOrNull(index: Int): String? {
   return if (`$this$getStringOrNull`.getParams().size() > index) `$this$getStringOrNull`.getString(index) else null;
}

public fun MoParams.getDoubleOrNull(index: Int): Double? {
   return if (`$this$getDoubleOrNull`.getParams().size() > index) `$this$getDoubleOrNull`.getDouble(index) else null;
}

public fun MoParams.getBooleanOrNull(index: Int): Boolean? {
   return if (`$this$getBooleanOrNull`.getParams().size() > index) `$this$getBooleanOrNull`.getDouble(index) == 1.0 else null;
}
