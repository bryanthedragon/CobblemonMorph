package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive

import com.bedrockk.molang.Expression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack

public enum PotionType(amountToHeal: () -> Expression, curesStatus: Boolean) : BagItem {
   POTION,
   SUPER_POTION,
   HYPER_POTION,
   MAX_POTION,
   FULL_RESTORE
   public final val amountToHeal: () -> Expression
   public final val curesStatus: Boolean

   init {
      this.amountToHeal = amountToHeal;
      this.curesStatus = curesStatus;
   }

   override fun canStillUse(player: ServerPlayer, battle: PokemonBattle, actor: BattleActor, target: BattlePokemon, stack: ItemStack): Boolean {
      return BagItem.DefaultImpls.canStillUse(this, player, battle, actor, target, stack);
   }

   public class FULL_RESTORE : PotionType {
      public open val itemName: String = "item.cobblemon.full_restore"

      fun FULL_RESTORE(`$enum$name`: java.lang.String, `$enum$ordinal`: Int) {
         super(<unrepresentable>.INSTANCE, true, null);
      }

      public override fun getShowdownInput(actor: BattleActor, battlePokemon: BattlePokemon, data: String?): String {
         return "full_restore";
      }

      public override fun canUse(battle: PokemonBattle, target: BattlePokemon): Boolean {
         return target.getHealth() < target.getMaxHealth() && target.getHealth() > 0;
      }
   }

   public class HYPER_POTION : PotionType {
      public open val itemName: String = "item.cobblemon.hyper_potion"

      fun HYPER_POTION(`$enum$name`: java.lang.String, `$enum$ordinal`: Int) {
         super(<unrepresentable>.INSTANCE, false, null);
      }

      public override fun getShowdownInput(actor: BattleActor, battlePokemon: BattlePokemon, data: String?): String {
         return "potion ${MoLangExtensionsKt.resolveInt(MoLangExtensionsKt.getGenericRuntime(), this.getAmountToHeal().invoke() as Expression, battlePokemon)}";
      }

      public override fun canUse(battle: PokemonBattle, target: BattlePokemon): Boolean {
         return target.getHealth() < target.getMaxHealth() && target.getHealth() > 0;
      }
   }

   public class MAX_POTION : PotionType {
      public open val itemName: String = "item.cobblemon.max_potion"

      fun MAX_POTION(`$enum$name`: java.lang.String, `$enum$ordinal`: Int) {
         super(<unrepresentable>.INSTANCE, false, null);
      }

      public override fun getShowdownInput(actor: BattleActor, battlePokemon: BattlePokemon, data: String?): String {
         return "potion ${battlePokemon.getMaxHealth() - battlePokemon.getHealth()}";
      }

      public override fun canUse(battle: PokemonBattle, target: BattlePokemon): Boolean {
         return target.getHealth() < target.getMaxHealth() && target.getHealth() > 0;
      }
   }

   public class POTION : PotionType {
      public open val itemName: String = "item.cobblemon.potion"

      fun POTION(`$enum$name`: java.lang.String, `$enum$ordinal`: Int) {
         super(<unrepresentable>.INSTANCE, false, null);
      }

      public override fun getShowdownInput(actor: BattleActor, battlePokemon: BattlePokemon, data: String?): String {
         return "potion ${MoLangExtensionsKt.resolveInt(MoLangExtensionsKt.getGenericRuntime(), this.getAmountToHeal().invoke() as Expression, battlePokemon)}";
      }

      public override fun canUse(battle: PokemonBattle, target: BattlePokemon): Boolean {
         return target.getHealth() < target.getMaxHealth() && target.getHealth() > 0;
      }
   }

   public class SUPER_POTION : PotionType {
      public open val itemName: String = "item.cobblemon.super_potion"

      fun SUPER_POTION(`$enum$name`: java.lang.String, `$enum$ordinal`: Int) {
         super(<unrepresentable>.INSTANCE, false, null);
      }

      public override fun getShowdownInput(actor: BattleActor, battlePokemon: BattlePokemon, data: String?): String {
         return "potion ${MoLangExtensionsKt.resolveInt(MoLangExtensionsKt.getGenericRuntime(), this.getAmountToHeal().invoke() as Expression, battlePokemon)}";
      }

      public override fun canUse(battle: PokemonBattle, target: BattlePokemon): Boolean {
         return target.getHealth() < target.getMaxHealth() && target.getHealth() > 0;
      }
   }
}
