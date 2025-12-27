package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.PlatformRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;

public object CobblemonSounds : PlatformRegistry<Registry<SoundEvent>, ResourceKey<Registry<SoundEvent>>, SoundEvent> {
   public final val BERRY_BUSH_BREAK: SoundEvent = INSTANCE.create("berry_bush.break");
   public final val BERRY_BUSH_PLACE: SoundEvent = INSTANCE.create("berry_bush.place");
   public final val BERRY_BUSH_SOUNDS: SoundType = new SoundType(1.0F, 1.0F, BERRY_BUSH_BREAK, SoundEvents.f_11992_, BERRY_BUSH_PLACE, SoundEvents.f_11990_, SoundEvents.f_11992_);
   public final val BERRY_EAT: SoundEvent = INSTANCE.create("berry.eat");
   public final val BERRY_HARVEST: SoundEvent = INSTANCE.create("berry.harvest");
   public final val BIG_ROOT_BREAK: SoundEvent = INSTANCE.create("big_root.break");
   public final val BIG_ROOT_SOUNDS: SoundType = new SoundType(1.0F, 1.0F, BIG_ROOT_BREAK, SoundEvents.f_11903_, SoundEvents.f_11904_, SoundEvents.f_11905_, SoundEvents.f_11906_);
   public final val CAN_EVOLVE: SoundEvent = INSTANCE.create("pokemon.can_evolve");
   public final val DISPLAY_CASE_ADD_ITEM: SoundEvent = INSTANCE.create("display_case.add_item");
   public final val DISPLAY_CASE_BREAK: SoundEvent = INSTANCE.create("display_case.break");
   public final val DISPLAY_CASE_HIT: SoundEvent = INSTANCE.create("display_case.hit");
   public final val DISPLAY_CASE_PLACE: SoundEvent = INSTANCE.create("display_case.place");
   public final val DISPLAY_CASE_REMOVE_ITEM: SoundEvent = INSTANCE.create("display_case.remove_item");
   public final val DISPLAY_CASE_SOUNDS: SoundType = new SoundType(1.0F, 1.0F, DISPLAY_CASE_BREAK, DISPLAY_CASE_STEP, DISPLAY_CASE_PLACE, DISPLAY_CASE_HIT, DISPLAY_CASE_STEP);
   public final val DISPLAY_CASE_STEP: SoundEvent = INSTANCE.create("display_case.step");
   public final val ENERGY_ROOT_PLACE: SoundEvent = INSTANCE.create("energy_root.place");
   public final val ENERGY_ROOT_SOUNDS: SoundType = new SoundType(1.0F, 1.0F, SoundEvents.f_11902_, SoundEvents.f_11903_, ENERGY_ROOT_PLACE, SoundEvents.f_11905_, SoundEvents.f_11906_);
   public final val EVOLVE: SoundEvent = INSTANCE.create("evolution.evolve");
   public final val EVOLVING: SoundEvent = INSTANCE.create("pokemon.evolving");
   public final val FOSSIL_MACHINE_ACTIVATE: SoundEvent = INSTANCE.create("fossil_machine.activate");
   public final val FOSSIL_MACHINE_ACTIVE_LOOP: SoundEvent = INSTANCE.create("fossil_machine.active_loop");
   public final val FOSSIL_MACHINE_ASSEMBLE: SoundEvent = INSTANCE.create("fossil_machine.assemble");
   public final val FOSSIL_MACHINE_DNA_FULL: SoundEvent = INSTANCE.create("fossil_machine.dna_full");
   public final val FOSSIL_MACHINE_FINISHED: SoundEvent = INSTANCE.create("fossil_machine.finished");
   public final val FOSSIL_MACHINE_INSERT_DNA: SoundEvent = INSTANCE.create("fossil_machine.insert_dna");
   public final val FOSSIL_MACHINE_INSERT_DNA_SMALL: SoundEvent = INSTANCE.create("fossil_machine.insert_dna_small");
   public final val FOSSIL_MACHINE_INSERT_FOSSIL: SoundEvent = INSTANCE.create("fossil_machine.insert_fossil");
   public final val FOSSIL_MACHINE_RETRIEVE_FOSSIL: SoundEvent = INSTANCE.create("fossil_machine.retrieve_fossil");
   public final val FOSSIL_MACHINE_RETRIEVE_POKEMON: SoundEvent = INSTANCE.create("fossil_machine.retrieve_pokemon");
   public final val FOSSIL_MACHINE_UNPROTECTED: SoundEvent = INSTANCE.create("fossil_machine.unprotected");
   public final val GILDED_CHEST_BREAK: SoundEvent = INSTANCE.create("gilded_chest.break");
   public final val GILDED_CHEST_CLOSE: SoundEvent = INSTANCE.create("gilded_chest.close");
   public final val GILDED_CHEST_HIT: SoundEvent = INSTANCE.create("gilded_chest.hit");
   public final val GILDED_CHEST_OPEN: SoundEvent = INSTANCE.create("gilded_chest.open");
   public final val GILDED_CHEST_PLACE: SoundEvent = INSTANCE.create("gilded_chest.place");
   public final val GILDED_CHEST_SOUNDS: SoundType = new SoundType(1.0F, 1.0F, GILDED_CHEST_BREAK, GILDED_CHEST_STEP, GILDED_CHEST_PLACE, GILDED_CHEST_HIT, GILDED_CHEST_STEP);
   public final val GILDED_CHEST_STEP: SoundEvent = INSTANCE.create("gilded_chest.step");
   public final val GIMMIGHOUL_GIVE_ITEM_SMALL: SoundEvent = INSTANCE.create("gimmighoul.give_item_small");
   public final val GIMMIGHOUL_REVEAL: SoundEvent = INSTANCE.create("gimmighoul.reveal");
   public final val GUI_CLICK: SoundEvent = INSTANCE.create("gui.click");
   public final val HEALING_MACHINE_ACTIVE: SoundEvent = INSTANCE.create("healing_machine.active");
   public final val IMPACT_BUG: SoundEvent = INSTANCE.create("impact.bug");
   public final val IMPACT_DARK: SoundEvent = INSTANCE.create("impact.dark");
   public final val IMPACT_DRAGON: SoundEvent = INSTANCE.create("impact.dragon");
   public final val IMPACT_ELECTRIC: SoundEvent = INSTANCE.create("impact.electric");
   public final val IMPACT_FAIRY: SoundEvent = INSTANCE.create("impact.fairy");
   public final val IMPACT_FIGHTING: SoundEvent = INSTANCE.create("impact.fighting");
   public final val IMPACT_FIRE: SoundEvent = INSTANCE.create("impact.fire");
   public final val IMPACT_FLYING: SoundEvent = INSTANCE.create("impact.flying");
   public final val IMPACT_GHOST: SoundEvent = INSTANCE.create("impact.ghost");
   public final val IMPACT_GRASS: SoundEvent = INSTANCE.create("impact.grass");
   public final val IMPACT_GROUND: SoundEvent = INSTANCE.create("impact.ground");
   public final val IMPACT_ICE: SoundEvent = INSTANCE.create("impact.ice");
   public final val IMPACT_NORMAL: SoundEvent = INSTANCE.create("impact.normal");
   public final val IMPACT_POISON: SoundEvent = INSTANCE.create("impact.poison");
   public final val IMPACT_PSYCHIC: SoundEvent = INSTANCE.create("impact.psychic");
   public final val IMPACT_ROCK: SoundEvent = INSTANCE.create("impact.rock");
   public final val IMPACT_STEEL: SoundEvent = INSTANCE.create("impact.steel");
   public final val IMPACT_WATER: SoundEvent = INSTANCE.create("impact.water");
   public final val ITEM_USE: SoundEvent = INSTANCE.create("item.use");
   public final val MEDICINAL_LEEK_BREAK: SoundEvent = INSTANCE.create("medicinal_leek.break");
   public final val MEDICINAL_LEEK_PLACE: SoundEvent = INSTANCE.create("medicinal_leek.plant");
   public final val MEDICINAL_LEEK_SOUNDS: SoundType = new SoundType(1.0F, 1.0F, MEDICINAL_LEEK_BREAK, SoundEvents.f_11992_, MEDICINAL_LEEK_PLACE, SoundEvents.f_11990_, SoundEvents.f_11989_);
   public final val MEDICINE_FEATHER_USE: SoundEvent = INSTANCE.create("medicine_feather.use");
   public final val MEDICINE_HERB_USE: SoundEvent = INSTANCE.create("medicine_herb.use");
   public final val MEDICINE_LIQUID_USE: SoundEvent = INSTANCE.create("medicine_liquid.use");
   public final val MEDICINE_PILLS_USE: SoundEvent = INSTANCE.create("medicine_pills.use");
   public final val MEDICINE_SPRAY_USE: SoundEvent = INSTANCE.create("medicine_spray.use");
   public final val MINT_BREAK: SoundEvent = INSTANCE.create("mint.break");
   public final val MINT_PLACE: SoundEvent = INSTANCE.create("mint.place");
   public final val MINT_SOUNDS: SoundType = new SoundType(1.0F, 1.0F, MINT_BREAK, SoundEvents.f_11992_, MINT_PLACE, SoundEvents.f_11990_, SoundEvents.f_11989_);
   public final val MULCH_PLACE: SoundEvent = INSTANCE.create("mulch.place");
   public final val MULCH_REMOVE: SoundEvent = INSTANCE.create("mulch.remove");
   public final val PC_CLICK: SoundEvent = INSTANCE.create("pc.click");
   public final val PC_DROP: SoundEvent = INSTANCE.create("pc.drop");
   public final val PC_GRAB: SoundEvent = INSTANCE.create("pc.grab");
   public final val PC_OFF: SoundEvent = INSTANCE.create("pc.off");
   public final val PC_ON: SoundEvent = INSTANCE.create("pc.on");
   public final val PC_RELEASE: SoundEvent = INSTANCE.create("pc.release");
   public final val POKE_BALL_CAPTURE_STARTED: SoundEvent = INSTANCE.create("poke_ball.capture_started");
   public final val POKE_BALL_CAPTURE_SUCCEEDED: SoundEvent = INSTANCE.create("poke_ball.capture_succeeded");
   public final val POKE_BALL_HIT: SoundEvent = INSTANCE.create("poke_ball.hit");
   public final val POKE_BALL_OPEN: SoundEvent = INSTANCE.create("poke_ball.open");
   public final val POKE_BALL_RECALL: SoundEvent = INSTANCE.create("poke_ball.recall");
   public final val POKE_BALL_SEND_OUT: SoundEvent = INSTANCE.create("poke_ball.send_out");
   public final val POKE_BALL_SHAKE: SoundEvent = INSTANCE.create("poke_ball.shake");
   public final val POKE_BALL_THROW: SoundEvent = INSTANCE.create("poke_ball.throw");
   public final val POKE_BALL_TRAIL: SoundEvent = INSTANCE.create("poke_ball.trail");
   public final val PVN_BATTLE: SoundEvent = INSTANCE.create("battle.pvn.default");
   public final val PVP_BATTLE: SoundEvent = INSTANCE.create("battle.pvp.default");
   public final val PVW_BATTLE: SoundEvent = INSTANCE.create("battle.pvw.default");
   public final val RELIC_COIN_POUCH_BREAK: SoundEvent = INSTANCE.create("relic_coin_pouch.break");
   public final val RELIC_COIN_POUCH_PLACE: SoundEvent = INSTANCE.create("relic_coin_pouch.place");
   public final val RELIC_COIN_POUCH_SOUNDS: SoundType = new SoundType(1.0F, 1.0F, RELIC_COIN_POUCH_BREAK, RELIC_COIN_SACK_STEP, RELIC_COIN_POUCH_PLACE, RELIC_COIN_SACK_HIT, RELIC_COIN_SACK_STEP);
   public final val RELIC_COIN_SACK_BREAK: SoundEvent = INSTANCE.create("relic_coin_sack.break");
   public final val RELIC_COIN_SACK_HIT: SoundEvent = INSTANCE.create("relic_coin_sack.hit");
   public final val RELIC_COIN_SACK_PLACE: SoundEvent = INSTANCE.create("relic_coin_sack.place");
   public final val RELIC_COIN_SACK_SOUNDS: SoundType = new SoundType(1.0F, 1.0F, RELIC_COIN_SACK_BREAK, RELIC_COIN_SACK_STEP, RELIC_COIN_SACK_PLACE, RELIC_COIN_SACK_HIT, RELIC_COIN_SACK_STEP);
   public final val RELIC_COIN_SACK_STEP: SoundEvent = INSTANCE.create("relic_coin_sack.step");
   public final val REVIVAL_HERB_BREAK: SoundEvent = INSTANCE.create("revival_herb.break");
   public final val REVIVAL_HERB_PLACE: SoundEvent = INSTANCE.create("revival_herb.place");
   public final val REVIVAL_HERB_SOUNDS: SoundType = new SoundType(1.0F, 1.0F, REVIVAL_HERB_BREAK, SoundEvents.f_11992_, REVIVAL_HERB_PLACE, SoundEvents.f_11990_, SoundEvents.f_11989_);
   public final val TUMBLESTONE_BLOCK_BREAK: SoundEvent = INSTANCE.create("tumblestone.block_break");
   public final val TUMBLESTONE_BLOCK_SOUNDS: SoundType = new SoundType(1.0F, 1.0F, TUMBLESTONE_BLOCK_BREAK, TUMBLESTONE_STEP, TUMBLESTONE_PLACE, TUMBLESTONE_HIT, TUMBLESTONE_STEP);
   public final val TUMBLESTONE_BREAK: SoundEvent = INSTANCE.create("tumblestone.break");
   public final val TUMBLESTONE_HIT: SoundEvent = INSTANCE.create("tumblestone.hit");
   public final val TUMBLESTONE_PLACE: SoundEvent = INSTANCE.create("tumblestone.place");
   public final val TUMBLESTONE_SOUNDS: SoundType = new SoundType(1.0F, 1.0F, TUMBLESTONE_BREAK, TUMBLESTONE_STEP, TUMBLESTONE_PLACE, TUMBLESTONE_HIT, TUMBLESTONE_STEP);
   public final val TUMBLESTONE_STEP: SoundEvent = INSTANCE.create("tumblestone.step");
   public final val VIVICHOKE_BREAK: SoundEvent = INSTANCE.create("vivichoke.break");
   public final val VIVICHOKE_PLACE: SoundEvent = INSTANCE.create("vivichoke.place");
   public final val VIVICHOKE_SOUNDS: SoundType = new SoundType(1.0F, 1.0F, VIVICHOKE_BREAK, SoundEvents.f_11992_, VIVICHOKE_PLACE, SoundEvents.f_11990_, SoundEvents.f_11989_);
   public open val registry: Registry<SoundEvent>
   public open val registryKey: ResourceKey<Registry<SoundEvent>>

   private fun create(name: String): SoundEvent {
      val var10000: Any = this.create(name, SoundEvent.m_262824_(MiscUtilsKt.cobblemonResource(name)));
      return var10000 as SoundEvent;
   }

   @JvmStatic
   fun {
      val var10000: Registry = BuiltInRegistries.f_256894_;
      registry = var10000;
      val var0: ResourceKey = Registries.f_256840_;
      registryKey = var0;
   }
}
