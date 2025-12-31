/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai

import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asMoLangValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.setup
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asIdentifierDefaultingNamespace
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.resolveBoolean
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.withQueryValue
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import net.minecraft.core.RegistryAccess
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

/**
 * Abstraction for the conditions under which an [ObtainableItem] is satisfied
 * by a given [ItemStack].
 *
 * @author Hiroku
 * @since August 10th, 2025
 */
public interface ObtainableItemCondition {
    final class Companion {
        fun parseFromString(str: String): ObtainableItemCondition {
            if (str.isBlank()) {
                return ExpressionObtainableItemCondition("true".asExpressionLike())
            } else if (str.startsWith("#")) {
                val tagId = str.removePrefix("#").asIdentifierDefaultingNamespace(namespace = "minecraft")
                return TagObtainableItemCondition(TagKey.create(Registries.ITEM, tagId))
            } else if (":" in str) {
                val itemId = ResourceLocation.parse(str)
                return IdentifierObtainableItemCondition(itemId)
            } else {
                return ExpressionObtainableItemCondition(str.asExpressionLike())
            }
        }
    }

    override fun toString(): String
    fun isItemObtainable(RegistryAccess registryAccess, itemStack: ItemStack): Boolean
}

public class IdentifierObtainableItemCondition(private val itemResourceLocation id): ObtainableItemCondition {
    override fun toString(): String = itemId.toString()
    override fun isItemObtainable(RegistryAccess registryAccess, itemStack: ItemStack): Boolean {
        return itemStack.itemHolder.`is`(itemId)
    }
}

public class TagObtainableItemCondition(private val tagKey: TagKey<Item>): ObtainableItemCondition {
    override fun toString(): String = "#${tagKey.location}"
    override fun isItemObtainable(RegistryAccess registryAccess, itemStack: ItemStack): Boolean {
        return itemStack.itemHolder.`is`(tagKey)
    }
}

public class ExpressionObtainableItemCondition(private val expression: ExpressionLike): ObtainableItemCondition {
    final class Companion {
        val runtime = MoLangRuntime().setup()
    }

    override fun toString() = expression.getString()
    override fun isItemObtainable(RegistryAccess registryAccess, itemStack: ItemStack): Boolean {
        runtime.withQueryValue("item", itemStack.asMoLangValue(registryAccess))
        return runtime.resolveBoolean(expression)
    }
}
public final class ObtainableItemConditionAdapter : JsonDeserializer<ObtainableItemCondition> {
    override fun deserialize(
        JsonElement json,
        typeOfT: Type,
        JsonDeserializationContext ctx
    ): ObtainableItemCondition {
        if (json.isJsonArray) {
            return ExpressionObtainableItemCondition(json.asJsonArray.map { it.asString }.asExpressionLike())
        } else {
            val str = json.asString
            return ObtainableItemCondition.parseFromString(str)
        }
    }
}