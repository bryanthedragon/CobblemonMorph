/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.PlatformRegistry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;

public final class CobblemonSounds : PlatformRegistry<Registry<SoundEvent>, ResourceKey<Registry<SoundEvent>>, SoundEvent>() {

    @Override public static final registry: Registry<SoundEvent> = BuiltInRegistries.SOUND_EVENT;
    @Override public static final resourceKey: ResourceKey<Registry<SoundEvent>> = Registries.SOUND_EVENT;

    public static final GUI_CLICK = this.create("gui.click");
    public static final GUI_TRADE = this.create("gui.trade");
    public static final PC_ON = this.create("pc.on");
    public static final PC_OFF = this.create("pc.off");
    public static final PC_GRAB = this.create("pc.grab");
    public static final PC_DROP = this.create("pc.drop");
    public static final PC_RELEASE = this.create("pc.release");
    public static final PC_CLICK = this.create("pc.click");
    public static final PC_WALLPAPER_UNLOCK = this.create("pc.wallpaper.unlock");
    public static final NPC_GIBBER_GENERIC = this.create("entity.npc.gibber.generic");
    public static final NPC_GIBBER_SPEECH_TYPE_1_1 = this.create("entity.npc.gibber.speech_type_1_1");
    public static final NPC_GIBBER_SPEECH_TYPE_1_2 = this.create("entity.npc.gibber.speech_type_1_2");
    public static final NPC_GIBBER_SPEECH_TYPE_1_3 = this.create("entity.npc.gibber.speech_type_1_3");
    public static final VILLAGER_WORK_NURSE = this.create("entity.villager.work_nurse");
    public static final HEALING_MACHINE_ACTIVE = this.create("block.healing_machine.active");
    public static final POKE_BALL_HIT = this.create("poke_ball.hit");
    public static final POKE_BALL_SEND_OUT = this.create("poke_ball.send_out");
    public static final POKE_BALL_SHINY_SEND_OUT = this.create("poke_ball.shiny_send_out");
    public static final POKE_BALL_RECALL = this.create("poke_ball.recall");
    public static final POKE_BALL_THROW = this.create("poke_ball.throw");
    public static final POKE_BALL_TRAIL = this.create("poke_ball.trail");
    public static final POKEDEX_OPEN = this.create("item.pokedex.open");
    public static final POKEDEX_CLOSE = this.create("item.pokedex.close");
    public static final POKEDEX_CLICK = this.create("item.pokedex.click");
    public static final POKEDEX_CLICK_SHORT = this.create("item.pokedex.click_short");
    public static final POKEDEX_SCAN_OPEN = this.create("item.pokedex.scan_open");
    public static final POKEDEX_SCAN_CLOSE = this.create("item.pokedex.scan_close");
    public static final POKEDEX_SCAN_LOOP = this.create("item.pokedex.scan_loop");
    public static final POKEDEX_SCAN_DETAIL = this.create("item.pokedex.scan_detail");
    public static final POKEDEX_SCAN_REGISTER_POKEMON = this.create("item.pokedex.scan_register_pokemon");
    public static final POKEDEX_SCAN_REGISTER_ASPECT = this.create("item.pokedex.scan_register_aspect");
    public static final POKEDEX_SCAN_ZOOM_INCREMENT = this.create("item.pokedex.scan_zoom_increment");
    public static final ITEM_USE = this.create("item.use");
    public static final EVOLUTION_NOTIFICATION = this.create("evolution.notification");
    public static final EVOLUTION_UI = this.create("evolution.ui");
    public static final EVOLUTION = this.create("evolution.full");
    public static final LEVELUP_START = this.create("gui.levelup_start");
    public static final LEVELUP = this.create("gui.levelup");
    public static final PVN_BATTLE = this.create("battle.pvn.default");
    public static final PVP_BATTLE = this.create("battle.pvp.default");
    public static final PVW_BATTLE = this.create("battle.pvw.default");
    public static final MEDICINE_CANDY_USE = this.create("item.medicine.candy.use");
    public static final MEDICINE_HERB_USE = this.create("item.medicine.herb.use");
    public static final MEDICINE_LIQUID_USE = this.create("item.medicine.liquid.use");
    public static final MEDICINE_PILLS_USE = this.create("item.medicine.pills.use");
    public static final MEDICINE_SPRAY_USE = this.create("item.medicine.spray.use");
    public static final MEDICINE_FEATHER_USE = this.create("item.medicine.feather.use");
    public static final MOCHI_USE = this.create("item.medicine.feather.use");
    public static final MULCH_PLACE = this.create("block.mulch.place");
    public static final MULCH_REMOVE = this.create("block.mulch.remove");
    public static final FOSSIL_MACHINE_ACTIVATE = this.create("block.fossil_machine.activate");
    public static final FOSSIL_MACHINE_ACTIVE_LOOP = this.create("block.fossil_machine.active_loop");
    public static final FOSSIL_MACHINE_ASSEMBLE = this.create("block.fossil_machine.assemble");
    public static final FOSSIL_MACHINE_DNA_FULL = this.create("block.fossil_machine.dna_full");
    public static final FOSSIL_MACHINE_FINISHED = this.create("block.fossil_machine.finished");
    public static final FOSSIL_MACHINE_INSERT_DNA = this.create("block.fossil_machine.insert_dna");
    public static final FOSSIL_MACHINE_INSERT_DNA_SMALL = this.create("block.fossil_machine.insert_dna_small");
    public static final FOSSIL_MACHINE_INSERT_FOSSIL = this.create("block.fossil_machine.insert_fossil");
    public static final FOSSIL_MACHINE_RETRIEVE_FOSSIL = this.create("block.fossil_machine.retrieve_fossil");
    public static final FOSSIL_MACHINE_RETRIEVE_POKEMON = this.create("block.fossil_machine.retrieve_pokemon");
    public static final FOSSIL_MACHINE_UNPROTECTED = this.create("block.fossil_machine.unprotected");
    public static final RELIC_COIN_SACK_BREAK = this.create("block.relic_coin_sack.break");
    public static final RELIC_COIN_SACK_HIT = this.create("block.relic_coin_sack.hit");
    public static final RELIC_COIN_SACK_STEP = this.create("block.relic_coin_sack.step");
    public static final RELIC_COIN_SACK_PLACE = this.create("block.relic_coin_sack.place");
    public static final RELIC_COIN_POUCH_BREAK = this.create("block.relic_coin_pouch.break");
    public static final RELIC_COIN_POUCH_PLACE = this.create("block.relic_coin_pouch.place");
    public static final FISHING_NOTIFICATION = this.create("fishing.notification");
    public static final FISHING_SPLASH_BIG = this.create("fishing.splash_big");
    public static final FISHING_SPLASH_SMALL = this.create("fishing.splash_small");
    public static final FISHING_BOBBER_LAND = this.create("fishing.bobber_land");
    public static final FISHING_ROD_CAST = this.create("fishing.rod_cast");
    public static final FISHING_ROD_REEL_IN = this.create("fishing.rod_reel_in");
    public static final FISHING_BAIT_ATTACH = this.create("fishing.bait_attach");
    public static final FISHING_BAIT_DETACH = this.create("fishing.bait_detach");
    public static final TUMBLESTONE_BREAK = this.create("block.tumblestone.break");
    public static final TUMBLESTONE_BLOCK_BREAK = this.create("block.tumblestone.block_break");
    public static final TUMBLESTONE_HIT = this.create("block.tumblestone.hit");
    public static final TUMBLESTONE_PLACE = this.create("block.tumblestone.place");
    public static final TUMBLESTONE_STEP = this.create("block.tumblestone.step");
    public static final EVOLUTION_STONE_BLOCK_BREAK = this.create("block.evolution_stone_block.break");
    public static final EVOLUTION_STONE_BLOCK_HIT = this.create("block.evolution_stone_block.hit");
    public static final EVOLUTION_STONE_BLOCK_PLACE = this.create("block.evolution_stone_block.place");
    public static final EVOLUTION_STONE_BLOCK_STEP = this.create("block.evolution_stone_block.step");
    public static final TATAMI_BLOCK_BREAK = this.create("block.tatami.break");
    public static final TATAMI_BLOCK_HIT = this.create("block.tatami.hit");
    public static final TATAMI_BLOCK_PLACE = this.create("block.tatami.place");
    public static final TATAMI_BLOCK_STEP = this.create("block.tatami.step");
    public static final TATAMI_MAT_BREAK = this.create("block.tatami_mat.break");
    public static final TATAMI_MAT_PLACE = this.create("block.tatami_mat.place");
    public static final GIMMIGHOUL_GIVE_ITEM = this.create("pokemon.gimmighoul.give_item");
    public static final GIMMIGHOUL_REVEAL = this.create("pokemon.gimmighoul.reveal");
    public static final BERRY_BUSH_BREAK = this.create("block.berry_bush.break");
    public static final BERRY_BUSH_PLACE = this.create("block.berry_bush.place");
    public static final BERRY_HARVEST = this.create("block.berry_bush.harvest");
    public static final BERRY_EAT = this.create("item.berry.eat");
    public static final BERRY_EAT_FULL = this.create("item.berry.eat.full");
    public static final BIG_ROOT_BREAK = this.create("block.big_root.break");
    public static final ENERGY_ROOT_PLACE = this.create("block.energy_root.place");
    public static final VIVICHOKE_BREAK = this.create("block.vivichoke.break");
    public static final VIVICHOKE_PLACE = this.create("block.vivichoke.place");
    public static final HEARTY_GRAIN_BALE_BREAK = this.create("block.hearty_grain_bale.break");
    public static final HEARTY_GRAIN_BALE_PLACE = this.create("block.hearty_grain_bale.place");
    public static final HEARTY_GRAIN_BALE_HIT = this.create("block.hearty_grain_bale.hit");
    public static final HEARTY_GRAIN_BALE_STEP = this.create("block.hearty_grain_bale.step");
    public static final HEARTY_GRAINS_BREAK = this.create("block.hearty_grains.break");
    public static final HEARTY_GRAINS_PLACE = this.create("block.hearty_grains.place");
    public static final HEARTY_GRAINS_BREAK_WATER = this.create("block.hearty_grains.break_water");
    public static final HEARTY_GRAINS_PLACE_WATER = this.create("block.hearty_grains.place_water");
    public static final MINT_BREAK = this.create("block.mint.break");
    public static final MINT_PLACE = this.create("block.mint.place");
    public static final REVIVAL_HERB_BREAK = this.create("block.revival_herb.break");
    public static final REVIVAL_HERB_PLACE = this.create("block.revival_herb.place");
    public static final MEDICINAL_LEEK_BREAK = this.create("block.medicinal_leek.break");
    public static final MEDICINAL_LEEK_PLACE = this.create("block.medicinal_leek.plant");
    public static final GILDED_CHEST_OPEN = this.create("block.gilded_chest.open");
    public static final GILDED_CHEST_CLOSE = this.create("block.gilded_chest.close");
    public static final GILDED_CHEST_STEP = this.create("block.gilded_chest.step");
    public static final GILDED_CHEST_HIT = this.create("block.gilded_chest.hit");
    public static final GILDED_CHEST_BREAK = this.create("block.gilded_chest.break");
    public static final GILDED_CHEST_PLACE = this.create("block.gilded_chest.place");
    public static final RELIC_COIN_SACK_SOUNDS = SoundType(1f, 1.1f, RELIC_COIN_SACK_BREAK, RELIC_COIN_SACK_STEP, RELIC_COIN_SACK_PLACE, RELIC_COIN_SACK_HIT, RELIC_COIN_SACK_STEP);
    public static final RELIC_COIN_POUCH_SOUNDS = SoundType(1f, 1.1f, RELIC_COIN_POUCH_BREAK, RELIC_COIN_SACK_STEP, RELIC_COIN_POUCH_PLACE, RELIC_COIN_SACK_HIT, RELIC_COIN_SACK_STEP);
    public static final RIDE_LOOP_LEATHER = create("ride.loop.leather");
    public static final RIDE_LOOP_PLUMAGE = create("ride.loop.plumage");
    public static final MOVE_QUICKATTACK_TARGET = this.create("move.quickattack.target");
    public static final MOVE_PURSUIT_TARGET = this.create("move.pursuit.target");
    public static final MOVE_PSYCHIC_TARGET = this.create("move.psychic.target");
    public static final IMPACT_NORMAL = this.create("impact.normal");
    public static final IMPACT_BUG = this.create("impact.bug");
    public static final IMPACT_DARK = this.create("impact.dark");
    public static final IMPACT_DRAGON = this.create("impact.dragon");
    public static final IMPACT_ELECTRIC = this.create("impact.electric");
    public static final IMPACT_FAIRY = this.create("impact.fairy");
    public static final IMPACT_FIGHTING = this.create("impact.fighting");
    public static final IMPACT_FIRE = this.create("impact.fire");
    public static final IMPACT_FLYING = this.create("impact.flying");
    public static final IMPACT_GHOST = this.create("impact.ghost");
    public static final IMPACT_GRASS = this.create("impact.grass");
    public static final IMPACT_GROUND = this.create("impact.ground");
    public static final IMPACT_ICE = this.create("impact.ice");
    public static final IMPACT_POISON = this.create("impact.poison");
    public static final IMPACT_PSYCHIC = this.create("impact.psychic");
    public static final IMPACT_ROCK = this.create("impact.rock");
    public static final IMPACT_STEEL = this.create("impact.steel");
    public static final IMPACT_WATER = this.create("impact.water");

    // Note to self or whoever's peeking, block sounds have to be at 1.1f pitch
    // For some reason, the game pitches them down and compensating for it here returns them to normal
    
    public static final TUMBLESTONE_SOUNDS = SoundType(1f, 1.1f, TUMBLESTONE_BREAK, TUMBLESTONE_STEP, TUMBLESTONE_PLACE, TUMBLESTONE_HIT, TUMBLESTONE_STEP)
    public static final TUMBLESTONE_BLOCK_SOUNDS = SoundType(1f, 1.1f, TUMBLESTONE_BLOCK_BREAK, TUMBLESTONE_STEP, TUMBLESTONE_PLACE, TUMBLESTONE_HIT, TUMBLESTONE_STEP)
    public static final EVOLUTION_STONE_BLOCK_SOUNDS = SoundType(1f, 1.1f, EVOLUTION_STONE_BLOCK_BREAK, EVOLUTION_STONE_BLOCK_STEP, EVOLUTION_STONE_BLOCK_PLACE, EVOLUTION_STONE_BLOCK_HIT, EVOLUTION_STONE_BLOCK_STEP)
    public static final TATAMI_BLOCK_SOUNDS = SoundType(1f, 1.1f, TATAMI_BLOCK_BREAK, TATAMI_BLOCK_STEP, TATAMI_BLOCK_PLACE, TATAMI_BLOCK_HIT, TATAMI_BLOCK_STEP);
    public static final TATAMI_MAT_SOUNDS = SoundType(1f, 1.1f, TATAMI_MAT_BREAK, TATAMI_BLOCK_STEP, TATAMI_MAT_PLACE, TATAMI_BLOCK_HIT, TATAMI_BLOCK_STEP);
    public static final BERRY_BUSH_SOUNDS = SoundType(0.8f, 1.1f, BERRY_BUSH_BREAK, SoundEvents.GRASS_STEP, BERRY_BUSH_PLACE, SoundEvents.GRASS_HIT, SoundEvents.GRASS_STEP);
    public static final BIG_ROOT_SOUNDS = SoundType(0.8f, 1.1f, BIG_ROOT_BREAK, SoundEvents.ROOTS_STEP, ENERGY_ROOT_PLACE, SoundEvents.ROOTS_HIT, SoundEvents.ROOTS_FALL);
    public static final ENERGY_ROOT_SOUNDS = SoundType(0.8f, 1.1f, BIG_ROOT_BREAK, SoundEvents.ROOTS_STEP, ENERGY_ROOT_PLACE, SoundEvents.ROOTS_HIT, SoundEvents.ROOTS_FALL);
    public static final MEDICINAL_LEEK_SOUNDS = SoundType(1f, 1.1f, MEDICINAL_LEEK_BREAK, SoundEvents.GRASS_STEP, MEDICINAL_LEEK_PLACE, SoundEvents.GRASS_HIT, SoundEvents.GRASS_FALL);
    public static final VIVICHOKE_SOUNDS = SoundType(0.6f, 1.1f, VIVICHOKE_BREAK, SoundEvents.GRASS_STEP, VIVICHOKE_PLACE, SoundEvents.GRASS_HIT, SoundEvents.GRASS_FALL);
    public static final HEARTY_GRAIN_BALE_SOUNDS = SoundType(0.6f, 1.1f, HEARTY_GRAIN_BALE_BREAK, HEARTY_GRAIN_BALE_STEP, HEARTY_GRAIN_BALE_PLACE, HEARTY_GRAIN_BALE_HIT, HEARTY_GRAIN_BALE_STEP);
    public static final HEARTY_GRAINS_SOUNDS = SoundType(0.6f, 1.1f, HEARTY_GRAINS_BREAK, HEARTY_GRAIN_BALE_STEP, HEARTY_GRAINS_PLACE, HEARTY_GRAIN_BALE_HIT, HEARTY_GRAIN_BALE_STEP);
    public static final HEARTY_GRAINS_WATER_SOUNDS = SoundType(0.6f, 1.1f, HEARTY_GRAINS_BREAK_WATER, SoundEvents.GRASS_STEP, HEARTY_GRAINS_PLACE_WATER, SoundEvents.GRASS_HIT, SoundEvents.GRASS_FALL)
    public static final MINT_SOUNDS = SoundType(0.6f, 1.1f, MINT_BREAK, SoundEvents.GRASS_STEP, MINT_PLACE, SoundEvents.GRASS_HIT, SoundEvents.GRASS_FALL);
    public static final REVIVAL_HERB_SOUNDS = SoundType(0.6f, 1.1f, REVIVAL_HERB_BREAK, SoundEvents.GRASS_STEP, REVIVAL_HERB_PLACE, SoundEvents.GRASS_HIT, SoundEvents.GRASS_FALL );
    public static final GILDED_CHEST_SOUNDS = SoundType(1f, 1.1f, GILDED_CHEST_BREAK, GILDED_CHEST_STEP, GILDED_CHEST_PLACE, GILDED_CHEST_HIT, GILDED_CHEST_STEP);
    public static final DISPLAY_CASE_ADD_ITEM = this.create("block.display_case.add_item");
    public static final DISPLAY_CASE_REMOVE_ITEM = this.create("block.display_case.remove_item");
    public static final DISPLAY_CASE_BREAK = this.create("block.display_case.break");
    public static final DISPLAY_CASE_HIT = this.create("block.display_case.hit");
    public static final DISPLAY_CASE_PLACE = this.create("block.display_case.place");
    public static final DISPLAY_CASE_STEP = this.create("block.display_case.step");
    public static final DISPLAY_CASE_SOUNDS = SoundType(1f, 1f, DISPLAY_CASE_BREAK, DISPLAY_CASE_STEP, DISPLAY_CASE_PLACE, DISPLAY_CASE_HIT, DISPLAY_CASE_STEP);
    public static final CAMPFIRE_POT_SET = this.create("block.campfire_pot.set");
    public static final CAMPFIRE_POT_RETRIEVE = this.create("block.campfire_pot.retrieve");
    public static final CAMPFIRE_POT_OPEN = this.create("block.campfire_pot.open");
    public static final CAMPFIRE_POT_CLOSE = this.create("block.campfire_pot.close");
    public static final CAMPFIRE_POT_TAKE_ITEM = this.create("block.campfire_pot.take_item");
    public static final CAMPFIRE_POT_ACTIVE = this.create("block.campfire_pot.active");
    public static final CAMPFIRE_POT_AMBIENT = this.create("block.campfire_pot.ambient");
    public static final CAMPFIRE_POT_COOK = this.create("block.campfire_pot.cook");
    public static final CAMPFIRE_POT_PLACE = this.create("block.campfire_pot.place");
    public static final CAMPFIRE_POT_BREAK = this.create("block.campfire_pot.break");
    public static final CAMPFIRE_POT_HIT = this.create("block.campfire_pot.hit");
    public static final CAMPFIRE_POT_STEP = this.create("block.campfire_pot.step");
    public static final CAMPFIRE_POT_SOUNDS = SoundType(1f, 1.1f, CAMPFIRE_POT_BREAK, CAMPFIRE_POT_STEP, CAMPFIRE_POT_PLACE, CAMPFIRE_POT_HIT, CAMPFIRE_POT_STEP);

    private CobblemonSounds create(String name): SoundEvent = this.create(name, SoundEvent.createVariableRangeEvent(cobblemonResource(name)))
}
