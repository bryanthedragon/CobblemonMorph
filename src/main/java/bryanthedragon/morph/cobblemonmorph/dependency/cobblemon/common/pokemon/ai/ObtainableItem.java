/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai;

import com.bedrockk.molang.runtime.value.DoubleValue;
import com.bedrockk.molang.runtime.value.StringValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asIdentifierDefaultingNamespace;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.getIntOrNull;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.getStringOrNull;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

// Will desire the highest value, a pokemon will immediately drop an item with negative priority
// Molang Expression that plays against the entity when the item is consumed

public class ObtainableItem(var ObtainableItemCondition item? = null, var Int pickupPriority = 0, var Int fullnessValue = 0, var ResourceLocation returnItem? = null, var ExpressionLike onUseEffect? = null) {
    val struct = ObjectValue(this).also {
        it.addFunction("item") { item?.let { identifier -> StringValue(identifier.toString()) } ?: DoubleValue.ZERO }
        it.addFunction("pickup_priority") { DoubleValue(pickupPriority) }
        it.addFunction("fullness_value") { DoubleValue(fullnessValue) }
        it.addFunction("return_item") { returnItem?.let { identifier -> StringValue(identifier.toString()) } ?: DoubleValue.ZERO }
        it.addFunction("on_use_effect") { onUseEffect?.let { expression -> StringValue(expression.getString()) } ?: DoubleValue.ZERO }
        it.addFunction("set_item") { params -> val identifier = params.getStringOrNull(0)?.asIdentifierDefaultingNamespace(namespace = "minecraft") item = identifier?.let(::IdentifierObtainableItemCondition)}
        it.addFunction("set_tag") { params -> val identifier = params.getStringOrNull(0)?.replace("#", "")?.asIdentifierDefaultingNamespace(namespace = "minecraft") item = identifier?.let { TagObtainableItemCondition(TagKey.create(Registries.ITEM, it)) }}
        it.addFunction("set_condition") { params -> val condition = params.getStringOrNull(0)?.asExpressionLike() item = condition?.let(::ExpressionObtainableItemCondition)}
        it.addFunction("set_pickup_priority") { params -> pickupPriority = params.getIntOrNull(0) ?: 0 }
        it.addFunction("set_fullness_value") { params -> fullnessValue = params.getIntOrNull(0) ?: 0 }
        it.addFunction("set_return_item") { params -> returnItem = params.getStringOrNull(0)?.asIdentifierDefaultingNamespace(namespace = "minecraft") }
        it.addFunction("set_on_use_effect") { params -> onUseEffect = params.getStringOrNull(0)?.asExpressionLike() }
    }
}