package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.AIBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.ActorType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.EntityBackedBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.FleeableBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.ai.BattleAI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ai.RandomBattleAI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleEndPacket
import java.util.UUID
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3

public open class PokemonBattleActor(uuid: UUID, pokemon: BattlePokemon, fleeDistance: Float, artificialDecider: BattleAI = (new RandomBattleAI()) as BattleAI) : AIBattleActor(
         uuid, CollectionsKt.listOf(pokemon), artificialDecider
      ),
   EntityBackedBattleActor<PokemonEntity>,
   FleeableBattleActor {
   public open val entity: PokemonEntity?
      public open get() {
         return this.pokemon.getEntity();
      }


   public open val fleeDistance: Float
   public final val pokemon: BattlePokemon
   public open val type: ActorType

   init {
      this.pokemon = pokemon;
      this.fleeDistance = fleeDistance;
      this.type = ActorType.WILD;
   }

   public override fun getName(): MutableComponent {
      return this.pokemon.getEffectedPokemon().getSpecies().getTranslatedName();
   }

   public override fun nameOwned(name: String): MutableComponent {
      val var10000: MutableComponent = Component.m_237113_(name);
      return var10000;
   }

   public override fun getWorldAndPosition(): Pair<ServerLevel, Vec3>? {
      val ownerPlayer: ServerPlayer = this.pokemon.getEffectedPokemon().getOwnerPlayer();
      if (ownerPlayer != null) {
         return TuplesKt.to(ownerPlayer.m_284548_(), ownerPlayer.m_20182_());
      } else {
         val var10000: PokemonEntity = this.getEntity();
         label18:
         if (var10000 == null) {
            return null;
         } else {
            val var4: Level = var10000.m_9236_();
            return if ((var4 as? ServerLevel) == null) null else TuplesKt.to(var4 as? ServerLevel, var10000.m_20182_());
         }
      }
   }

   public override fun sendUpdate(packet: NetworkPacket<*>) {
      super.sendUpdate(packet);
      if (packet is BattleEndPacket) {
         val var10000: PokemonEntity = this.getEntity();
         if (var10000 == null) {
            return;
         }

         var10000.setBattleId(null);
      }
   }
}
