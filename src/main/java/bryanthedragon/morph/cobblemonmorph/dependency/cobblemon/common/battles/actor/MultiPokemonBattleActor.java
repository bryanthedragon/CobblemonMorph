package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.AIBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.ActorType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.ai.BattleAI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ai.RandomBattleAI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import java.util.UUID
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

public class MultiPokemonBattleActor(pokemonList: List<BattlePokemon>,
   artificialDecider: BattleAI = (new RandomBattleAI()) as BattleAI,
   uuid: UUID = UUID.randomUUID()
) : AIBattleActor(uuid, pokemonList, artificialDecider) {
   public open val type: ActorType = ActorType.WILD

   public override fun getName(): MutableComponent {
      return TextKt.text("Wild Pokémon");
   }

   public override fun nameOwned(name: String): MutableComponent {
      val var10000: MutableComponent = Component.m_237113_(name);
      return var10000;
   }
}
