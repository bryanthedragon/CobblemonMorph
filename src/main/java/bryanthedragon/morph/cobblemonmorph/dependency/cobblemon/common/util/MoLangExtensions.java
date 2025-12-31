/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;
import com.bedrockk.molang.Expression
import com.bedrockk.molang.MoLang
import com.bedrockk.molang.ast.NumberExpression
import com.bedrockk.molang.runtime.MoLangEnvironment
import com.bedrockk.molang.runtime.MoLangRuntime
import com.bedrockk.molang.runtime.MoParams
import com.bedrockk.molang.runtime.struct.ArrayStruct
import com.bedrockk.molang.runtime.struct.ContextStruct
import com.bedrockk.molang.runtime.struct.VariableStruct
import com.bedrockk.molang.runtime.value.DoubleValue
import com.bedrockk.molang.runtime.value.MoValue
import com.bedrockk.molang.runtime.value.StringValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ListExpression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asMoLangValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.setup
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ReferenceExpression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.SingleExpression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc.NPCEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Player
import net.minecraft.world.phys.Vec3

val genericRuntime = MoLangRuntime().setup()
/** Don't run this from not-the-main thread. */
val mainThreadMoLangRuntime runtime by lazy { MoLangRuntime().setup() }

fun MoLangRuntime.resolve(expression: Expression, Map<String, MoValue> context = contextOrEmpty): MoValue = try {
//    environment.structs["context"] = ContextStruct(context)
    execute(expression, context).also {
        environment.context = ContextStruct(context) // TODO move this into molang itself, not clearing the context is helpful af
    }
//    expression.evaluate(MoScope(), environment)
} catch (Exception e) {
    throw IllegalArgumentException("Unable to evaluate expression: ${expression.getString()}", e)
}
fun MoLangRuntime.resolveDouble(expression: Expression, Map<String, MoValue> context = contextOrEmpty): Double = resolve(expression, context).asDouble()
fun MoLangRuntime.resolveFloat(expression: Expression, Map<String, MoValue> context = contextOrEmpty): Float = resolve(expression, context).asDouble().toFloat()
fun MoLangRuntime.resolveInt(expression: Expression, Map<String, MoValue> context = contextOrEmpty): Int = resolveDouble(expression, context).toInt()
fun MoLangRuntime.resolveString(expression: Expression, Map<String, MoValue> context = contextOrEmpty): String = resolve(expression, context).asString()
fun MoLangRuntime.resolveObject(expression: Expression, Map<String, MoValue> context = contextOrEmpty): ObjectValue<*> = resolve(expression, context) as ObjectValue<*>
fun MoLangRuntime.resolveBoolean(expression: Expression, Map<String, MoValue> context = contextOrEmpty): Boolean = resolve(expression, context).asDouble() != 0.0

fun MoLangRuntime.resolve(expression: ExpressionLike, Map<String, MoValue> context = contextOrEmpty): MoValue = expression.resolve(this, context)
fun MoLangRuntime.resolveDouble(expression: ExpressionLike, Map<String, MoValue> context = contextOrEmpty): Double = resolve(expression, context).asDouble()
fun MoLangRuntime.resolveFloat(expression: ExpressionLike, Map<String, MoValue> context = contextOrEmpty): Float = resolve(expression, context).asDouble().toFloat()
fun MoLangRuntime.resolveInt(expression: ExpressionLike, Map<String, MoValue> context = contextOrEmpty): Int = resolveDouble(expression, context).toInt()
fun MoLangRuntime.resolveString(expression: ExpressionLike, Map<String, MoValue> context = contextOrEmpty): String = resolve(expression, context).asString()
fun MoLangRuntime.resolveObject(expression: ExpressionLike, Map<String, MoValue> context = contextOrEmpty): ObjectValue<*> = resolve(expression, context) as ObjectValue<*>
fun MoLangRuntime.resolveBoolean(expression: ExpressionLike, Map<String, MoValue> context = contextOrEmpty): Boolean = resolve(expression, context).asDouble() != 0.0
val MoLangRuntime.contextOrEmpty: Map<String, MoValue> get() = environment.context?.map ?: hashMapOf()

fun MoLangRuntime.resolveVec3d(triple: Triple<Expression, Expression, Expression>, Map<String, MoValue> context = contextOrEmpty) =
    Vec3(
        resolveDouble(triple.first, context),
        resolveDouble(triple.second, context),
        resolveDouble(triple.third, context)
    )

fun MoLangRuntime.resolveBoolean(expression: Expression, Pokemon pokemon, Map<String, MoValue> context = contextOrEmpty): Boolean {
    environment.writePokemon(pokemon)
    return resolveBoolean(expression, context)
}

fun MoLangRuntime.resolveDouble(expression: Expression, Pokemon pokemon, Map<String, MoValue> context = contextOrEmpty): Double {
    environment.writePokemon(pokemon)
    return resolveDouble(expression, context)
}

fun MoLangRuntime.resolveInt(expression: Expression, Pokemon pokemon, Map<String, MoValue> context = contextOrEmpty): Int {
    environment.writePokemon(pokemon)
    return resolveInt(expression, context)
}

fun MoLangRuntime.resolveInt(expression: ExpressionLike, Pokemon pokemon, Map<String, MoValue> context = contextOrEmpty): Int {
    environment.writePokemon(pokemon)
    return resolveInt(expression, context)
}

fun MoLangRuntime.resolveFloat(expression: Expression, Pokemon pokemon, Map<String, MoValue> context = contextOrEmpty): Float {
    environment.writePokemon(pokemon)
    return resolveFloat(expression, context)
}


fun MoLangRuntime.resolveBoolean(expression: Expression, pokemon: BattlePokemon, Map<String, MoValue> context = contextOrEmpty): Boolean {
    environment.writePokemon(pokemon)
    return resolveBoolean(expression, context)
}

fun MoLangRuntime.resolveDouble(expression: Expression, pokemon: BattlePokemon, Map<String, MoValue> context = contextOrEmpty): Double {
    environment.writePokemon(pokemon)
    return resolveDouble(expression, context)
}

fun MoLangRuntime.resolveInt(expression: Expression, pokemon: BattlePokemon, Map<String, MoValue> context = contextOrEmpty): Int {
    environment.writePokemon(pokemon)
    return resolveInt(expression, context)
}

fun MoLangRuntime.resolveInt(expression: ExpressionLike, pokemon: BattlePokemon, Map<String, MoValue> context = contextOrEmpty): Int {
    environment.writePokemon(pokemon)
    return resolveInt(expression, context)
}

fun MoLangRuntime.resolveFloat(expression: Expression, pokemon: BattlePokemon, Map<String, MoValue> context = contextOrEmpty): Float {
    environment.writePokemon(pokemon)
    return resolveFloat(expression, context)
}

fun MoLangRuntime.resolveFloat(expression: ExpressionLike, Pokemon pokemon, Map<String, MoValue> context = contextOrEmpty): Float {
    environment.writePokemon(pokemon)
    return resolveFloat(expression, context)
}


fun MoLangRuntime.resolveFloat(expression: ExpressionLike, pokemon: BattlePokemon, Map<String, MoValue> context = contextOrEmpty): Float {
    environment.writePokemon(pokemon)
    return resolveFloat(expression, context)
}

fun <T> MoLangRuntime.queryObject(String name, vararg args: MoValue): T? {
    val params = MoParams(environment, args.toList())
    val value = environment.query.functions.get(name)?.apply(params) ?: return null
    if (value !is ObjectValue<*>) {
        return null
    }
    return value.obj as? T
}

fun MoLangRuntime.queryDouble(String name, vararg args: MoValue): Double? {
    val params = MoParams(environment, args.toList())
    val value = environment.query.functions.get(name)?.apply(params) ?: return null
    if (value !is DoubleValue) {
        return null
    }
    return value.asDouble()
}

fun MoLangRuntime.queryString(String name, vararg args: MoValue): String? {
    val params = MoParams(environment, args.toList())
    val value = environment.query.functions.get(name)?.apply(params) ?: return null
    if (value !is StringValue) {
        return null
    }
    return value.asString()
}

fun MoLangRuntime.queryBoolean(String name, vararg args: MoValue): Boolean? {
    val params = MoParams(environment, args.toList())
    val value = environment.query.functions.get(name)?.apply(params) ?: return null
    if (value !is DoubleValue) {
        return null
    }
    return value.asDouble() != 0.0
}


fun Expression.getString() = originalString ?: "0"
fun Double.asExpressionLike() = SingleExpression(NumberExpression(this))
fun String.asExpressions() = try {
    MoLang.createParser(if (this == "") "0.0" else this).parse()
} catch (exc: Exception) {
    Cobblemon.LOGGER.error("Failed to parse MoLang expressions: $this")
    throw exc
}

fun String.asExpression() = try {
    MoLang.createParser(if (this == "") "0.0" else this).parseExpression()
} catch (exc: Exception) {
    Cobblemon.LOGGER.error("Failed to parse MoLang expressions: $this")
    throw exc
}

fun String.asExpressionLike() = try {
    val identifier = ResourceLocation.tryParse(this)
    if (":" in this && identifier != null) {
        ReferenceExpression(identifier)
    } else {
        val ls = MoLang.createParser(if (this == "") "0.0" else this).parse()
        if (ls.size == 1) {
            SingleExpression(ls[0])
        } else {
            ListExpression(ls)
        }
    }
} catch (exc: Exception) {
    Cobblemon.LOGGER.error("Failed to parse MoLang expressions: $this")
    throw exc
}

fun Double.asExpression() = toString().asExpression() // Use the string route because it remembers the original string value for serialization

fun MoLangEnvironment.writePokemon(Pokemon pokemon) {
    setSimpleVariable("pokemon", pokemon.struct)
}

fun MoLangEnvironment.writePokemon(pokemon: BattlePokemon) {
    setSimpleVariable("pokemon", pokemon.effectedPokemon.struct)
}

fun List<String>.asExpressionLike() = joinToString(separator = "\n").asExpressionLike()
fun List<Expression>.resolve(MoLangRuntime runtime, Map<String, MoValue> context = runtime.contextOrEmpty) = runtime.execute(this, context)
fun List<Expression>.resolveDouble(MoLangRuntime runtime, Map<String, MoValue> context = runtime.contextOrEmpty) = resolve(runtime, context).asDouble()
fun List<Expression>.resolveInt(MoLangRuntime runtime, Map<String, MoValue> context = runtime.contextOrEmpty) = resolveDouble(runtime, context).toInt()
fun List<Expression>.resolveBoolean(MoLangRuntime runtime, Map<String, MoValue> context = runtime.contextOrEmpty) = resolveDouble(runtime, context) == 1.0
fun List<Expression>.resolveObject(MoLangRuntime runtime, Map<String, MoValue> context = runtime.contextOrEmpty) = resolve(runtime, context) as ObjectValue<*>

fun <T : MoValue> MoParams.getOrNull(Int index) = if (params.size > index) get<T>(index) else null
fun MoParams.getStringOrNull(Int index) = if (params.size > index) getString(index) else null
fun MoParams.getDoubleOrNull(Int index) = if (params.size > index) getDouble(index) else null
fun MoParams.getBoolean(Int index) = getDouble(index) == 1.0
fun MoParams.getBooleanOrNull(Int index) = if (params.size > index) getDouble(index) == 1.0 else null
fun MoParams.getIntOrNull(Int index) = if (params.size > index) getDouble(index).toInt() else null

fun MoLangRuntime.withQueryValue(String name, value: MoValue): MoLangRuntime {
    environment.query.functions.put(name) { value }
    return this
}

fun MoLangRuntime.withPlayerValue(String name = "player", value: Player) = withQueryValue(name, value.asMoLangValue())

//fun MoLangRuntime.withPokemonValue(String name = "pokemon", value: Pokemon) = withQueryValue(name, value.asMoLangValue())
fun MoLangRuntime.withNPCValue(String name = "npc", value: NPCEntity) = withQueryValue(name, value.struct)

fun ArrayStruct.getDouble(Int index) = map["$index"]!!.asDouble()
fun ArrayStruct.getString(Int index) = map["$index"]!!.asString()
fun ArrayStruct.asBlockPos() = BlockPos(getDouble(0).toInt(), getDouble(1).toInt(), getDouble(2).toInt())
fun ArrayStruct.asVec3d() = Vec3(getDouble(0), getDouble(1), getDouble(2))

fun <T> VariableStruct.getObject(String name): T? {
    val value = map[name] ?: return null
    if (value !is ObjectValue<*>) {
        return null
    }
    return value.obj as? T
}

fun <T> VariableStruct.getObjectList(String name): List<T> {
    val value = map[name] ?: return emptyList()
    if (value !is ArrayStruct) {
        return emptyList()
    }
    return value.map.values.mapNotNull { (it as? ObjectValue<T>)?.obj }
}

fun MoLangRuntime.clone(): MoLangRuntime {
    val runtime = MoLangRuntime()
    runtime.environment.cloneFrom(environment)
    return runtime
}

fun MoLangEnvironment.cloneFrom(other: MoLangEnvironment): MoLangEnvironment {
    query.functions.putAll(other.query.functions)
    variable.map.putAll(other.variable.map)
    if (other.context != null) {
        context = ContextStruct()
        context.map.putAll(other.context.map)
    }
    return this
}

fun BlockPos.toArrayStruct() = listOf(x, y, z).asArrayValue(::DoubleValue)

fun <T> Collection<T>.asArrayValue(mapper: (T) -> MoValue): ArrayStruct {
    val array = ArrayStruct()
    forEachIndexed { index, value -> array.setDirectly("$index", mapper(value)) }
    return array
}

fun Iterable<MoValue>.asArrayValue(): ArrayStruct {
    val array = ArrayStruct()
    forEachIndexed { index, value -> array.setDirectly("$index", value) }
    return array
}

fun MoLangEnvironment.createDuplicateRuntime(): MoLangRuntime {
    val runtime = MoLangRuntime()
    runtime.environment.cloneFrom(this)
    return runtime
}