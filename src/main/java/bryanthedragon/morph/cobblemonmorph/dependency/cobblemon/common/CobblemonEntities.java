/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.boat.CobblemonBoatEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.boat.CobblemonChestBoatEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.fishing.PokeRodFishingBobberEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.generic.GenericBedrockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc.NPCEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.PlatformRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

public final class CobblemonEntities : PlatformRegistry<Registry<EntityType<?>>, ResourceKey<Registry<EntityType<?>>>, EntityType<?>>() {


    @Override public static final registry: Registry<EntityType<?>> = BuiltInRegistries.ENTITY_TYPE;
    @Override public static final resourceKey: ResourceKey<Registry<EntityType<?>>> = Registries.ENTITY_TYPE;
    
    public static final POKEMON_KEY = cobblemonResource("pokemon");
    public static final POKEMON: EntityType<PokemonEntity> = this.create(POKEMON_KEY.path,EntityType.Builder.of({ _, world -> PokemonEntity(world) }, MobCategory.CREATURE).build(POKEMON_KEY.toString()));

    public static final EMPTY_POKEBALL_KEY = cobblemonResource("empty_pokeball");
    public static final EMPTY_POKEBALL: EntityType<EmptyPokeBallEntity> = this.create(EMPTY_POKEBALL_KEY.path, EntityType.Builder.of({ _, world -> EmptyPokeBallEntity(PokeBalls.POKE_BALL, world) }, MobCategory.MISC).build(EMPTY_POKEBALL_KEY.toString()));

    public static final BOAT_KEY = cobblemonResource("boat");
    public static final BOAT: EntityType<CobblemonBoatEntity> = this.create(BOAT_KEY.path, EntityType.Builder.of(::CobblemonBoatEntity, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10).build(BOAT_KEY.toString()));

    public static final CHEST_BOAT_KEY = cobblemonResource("chest_boat");
    public static final CHEST_BOAT: EntityType<CobblemonChestBoatEntity> = this.create(CHEST_BOAT_KEY.path, EntityType.Builder.of(::CobblemonChestBoatEntity, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10).build(CHEST_BOAT_KEY.toString()));

    public static final POKE_BOBBER_KEY = cobblemonResource("poke_bobber");
    public static final POKE_BOBBER: EntityType<PokeRodFishingBobberEntity> = this.create(POKE_BOBBER_KEY.path, EntityType.Builder.of(::PokeRodFishingBobberEntity, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(10).build(POKE_BOBBER_KEY.toString()));

    public static final GENERIC_BEDROCK_ENTITY_KEY = cobblemonResource("generic_bedrock");
    public static final GENERIC_BEDROCK_ENTITY: EntityType<GenericBedrockEntity> = this.create(GENERIC_BEDROCK_ENTITY_KEY.path, EntityType.Builder.of({ _, world -> GenericBedrockEntity(world) }, MobCategory.MISC).build(GENERIC_BEDROCK_ENTITY_KEY.toString()));

    public static final NPC_KEY = cobblemonResource("npc");
    public static final NPC: EntityType<NPCEntity> = create(NPC_KEY.path, EntityType.Builder.of({ _, world -> NPCEntity(world) }, MobCategory.CREATURE).build("$NPC_KEY"));

    fun registerAttributes(consumer: (EntityType<out LivingEntity>, AttributeSupplier.Builder) -> Unit) {
        consumer(POKEMON, PokemonEntity.createAttributes())consumer(NPC, NPCEntity.createAttributes())
    }
}