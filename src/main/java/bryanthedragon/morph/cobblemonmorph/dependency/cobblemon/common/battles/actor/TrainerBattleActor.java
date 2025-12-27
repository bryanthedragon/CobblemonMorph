package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.AIBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.ActorType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.ai.BattleAI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import java.util.UUID
import net.minecraft.network.chat.MutableComponent

public class TrainerBattleActor(trainerName: String, uuid: UUID, pokemonList: List<BattlePokemon>, artificialDecider: BattleAI) : AIBattleActor(
      uuid, pokemonList, artificialDecider
   ) {
   public final val trainerName: String
   public open val type: ActorType

   init {
      this.trainerName = trainerName;
      this.type = ActorType.NPC;
   }

   public override fun getName(): MutableComponent {
      return MiscUtilsKt.asTranslated(this.trainerName);
   }

   public override fun nameOwned(name: String): MutableComponent {
      val var2: Array<Any> = new Object[2];
      val var10003: MutableComponent = this.getName();
      var2[0] = var10003;
      var2[1] = name;
      val var10000: MutableComponent = LocalizationUtilsKt.battleLang("owned_pokemon", var2);
      return var10000;
   }
}
