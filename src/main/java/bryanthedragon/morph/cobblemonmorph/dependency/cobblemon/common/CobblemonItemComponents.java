/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.components.*;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.PlatformRegistry;

import com.mojang.serialization.Codec;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public final class CobblemonItemComponents : PlatformRegistry<Registry<DataComponentType<?>>, ResourceKey<Registry<DataComponentType<?>>>, DataComponentType<?>>() {

public static final POKEMON_ITEM: DataComponentType<PokemonItemComponent> = create("pokemon_item", DataComponentType.builder<PokemonItemComponent>().persistent(PokemonItemComponent.CODEC).networkSynchronized(PokemonItemComponent.PACKET_CODEC).build());

public static final HELD_ITEM_EFFECT: DataComponentType<HeldItemEffectComponent> = create("held_item_effect", DataComponentType.builder<HeldItemEffectComponent>().persistent(HeldItemEffectComponent.CODEC).networkSynchronized(HeldItemEffectComponent.PACKET_CODEC).build());

public static final BAIT: DataComponentType<RodBaitComponent> = create("bait", DataComponentType.builder<RodBaitComponent>().persistent(RodBaitComponent.CODEC).networkSynchronized(RodBaitComponent.PACKET_CODEC).build());

public static final POT_DATA: DataComponentType<PotComponent> = create("cooking_pot_item", DataComponentType.builder<PotComponent>().persistent(PotComponent.CODEC).networkSynchronized(PotComponent.PACKET_CODEC).build());

public static final BAIT_EFFECTS: DataComponentType<BaitEffectsComponent> = create("bait_effects", DataComponentType.builder<BaitEffectsComponent>().persistent(BaitEffectsComponent.CODEC).networkSynchronized(BaitEffectsComponent.PACKET_CODEC).build());

public static final FLAVOUR: DataComponentType<FlavourComponent> = create("flavour", DataComponentType.builder<FlavourComponent>().persistent(FlavourComponent.CODEC).networkSynchronized(FlavourComponent.PACKET_CODEC).build());

public static final RIDE_BOOST: DataComponentType<RideBoostsComponent> = create("ride_boosts", DataComponentType.builder<RideBoostsComponent>().persistent(RideBoostsComponent.CODEC).networkSynchronized(RideBoostsComponent.PACKET_CODEC).build());

public static final FOOD_COLOUR: DataComponentType<FoodColourComponent> = create("food_colour", DataComponentType.builder<FoodColourComponent>().persistent(FoodColourComponent.CODEC).networkSynchronized(FoodColourComponent.PACKET_CODEC).build());

public static final INGREDIENT: DataComponentType<IngredientComponent> = create("ingredient", DataComponentType.builder<IngredientComponent>().persistent(IngredientComponent.CODEC).networkSynchronized(IngredientComponent.PACKET_CODEC).build());

public static final FOOD: DataComponentType<FoodComponent> = create("food", DataComponentType.builder<FoodComponent>().persistent(FoodComponent.CODEC).networkSynchronized(FoodComponent.PACKET_CODEC).build());

    public static final MOB_EFFECTS: DataComponentType<MobEffectsComponent> = create("mob_effects", DataComponentType.builder<MobEffectsComponent>().persistent(MobEffectsComponent.CODEC).networkSynchronized(MobEffectsComponent.PACKET_CODEC).build());

    public static final CRAFTED: DataComponentType<Boolean> = create("crafted", DataComponentType.builder<Boolean>().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());

    fun register() {
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ResourceLocation.parse("cobblemon:pokemon_item"), POKEMON_ITEM);
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ResourceLocation.parse("cobblemon:bait"), BAIT);
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ResourceLocation.parse("cobblemon:cooking_pot_item"), POT_DATA);
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ResourceLocation.parse("cobblemon:bait_effects"), BAIT_EFFECTS);
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ResourceLocation.parse("cobblemon:flavour"), FLAVOUR);
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ResourceLocation.parse("cobblemon:ride_boosts"), RIDE_BOOST);
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ResourceLocation.parse("cobblemon:food_colour"), FOOD_COLOUR);
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ResourceLocation.parse("cobblemon:ingredient"), INGREDIENT);
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ResourceLocation.parse("cobblemon:food"), FOOD);
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ResourceLocation.parse("cobblemon:mob_effects"), MOB_EFFECTS);
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ResourceLocation.parse("cobblemon:held_item_effect"), HELD_ITEM_EFFECT);
    }

    @Override public static final registry = BuiltInRegistries.DATA_COMPONENT_TYPE;
    @Override public static final resourceKey = Registries.DATA_COMPONENT_TYPE;

}