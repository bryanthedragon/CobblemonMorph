/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball;

import com.bedrockk.molang.runtime.struct.QueryStruct;
import com.bedrockk.molang.runtime.value.DoubleValue;
import com.bedrockk.molang.runtime.value.StringValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CaptureEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CatchRateModifier;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokeBallItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec.CodecUtils;
import com.mojang.serialization.Codec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;

/**
 * Base poke ball object
 * It is intended that there is one poke ball object initialized for a given poke ball type.
 *
 * @property name the poke ball registry name
 * @property catchRateModifier The [CatchRateModifier] of this Pokéball.
 * @property effects list of all [CaptureEffect]s applicable to the Pokéball
 * @property waterDragValue The value of the water drag modifier when the entity travels, default is 0.8.
 * @property model2d The identifier for the resource this Pokéball will use for the 2d model.
 * @property model3d The identifier for the resource this Pokéball will use for the 3d model.
 */
public open class PokeBall(ResourceLocation name, CatchRateModifier catchRateModifier = CatchRateModifier.DUMMY, List<CaptureEffect> effects, Float waterDragValue, ResourceLocation model2d, ResourceLocation model3d, Float throwPower, Boolean ancient) {
    val struct = QueryStruct(hashMapOf()).addFunction("name") { StringValue(name.toString()) }.addFunction("water_drag_value") { DoubleValue(waterDragValue) }.addFunction("throw_power") { DoubleValue(throwPower) }.addFunction("is_ancient") { DoubleValue(ancient) }.addFunction("item") {  } // requires a registry which is hard

    // This gets attached during item registry
    internal lateinit var item: PokeBallItem

    PokeBallItem item() { 
        return this.item;
    }

    ItemStack stack(Int count = 1) { 
        return ItemStack(this.item(), count);
    }

    @Deprecated("This is a temporary solution for the safari ball dilemma", ReplaceWith("target.currentHealth"))
    internal Int hpForCalculation(Pokemon target) { 
        if (this.name == PokeBalls.SAFARI_BALL.name) {
            target.maxHealth;
        }
        else {
            target.currentHealth;
        }
    }

    final class Companion {
        Codec<PokeBall> BY_IDENTIFIER_CODEC = CodecUtils.createByIdentifierCodec(PokeBalls::getPokeBall, PokeBall::name) { identifier -> "No PokeBall for ID $identifier" };
    }
}