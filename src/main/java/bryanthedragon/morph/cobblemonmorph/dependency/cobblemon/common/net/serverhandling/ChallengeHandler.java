package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingFunctionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleBuilder
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ErroredBattleStart
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleChallengeNotificationPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.BattleChallengePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import java.util.UUID
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function1
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import org.jetbrains.annotations.NotNull

public object ChallengeHandler : ServerNetworkPacketHandler<BattleChallengePacket> {
   public open fun handle(packet: BattleChallengePacket, server: MinecraftServer, player: ServerPlayer) {
      if (!player.m_5833_()) {
         var var10000: Entity = player.m_9236_().m_6815_(packet.getTargetedEntityId());
         if (var10000 != null) {
            label64: {
               if (var10000 is PokemonEntity) {
                  val owner: LivingEntity = (var10000 as PokemonEntity).m_269323_();
                  if (owner != null) {
                     var10000 = owner as Entity;
                     break label64;
                  }
               }

               var10000 = var10000;
            }

            val var16: Pokemon = PlayerExtensionsKt.party(player).get(packet.getSelectedPokemonId());
            val var17: UUID = if (var16 != null) var16.getUuid() else null;
            if (var17 != null) {
               if (var10000 is PokemonEntity) {
                  if (!(var10000 as PokemonEntity).canBattle(player as Player)) {
                     return;
                  }

                  BattleBuilder.pve$default(BattleBuilder.INSTANCE, player, var10000 as PokemonEntity, var17, null, false, false, 0.0F, null, 248, null)
                     .ifErrored((new Function1<ErroredBattleStart, Unit>(player) {
                        {
                           super(1);
                           this.$player = `$player`;
                        }

                        public final void invoke(@NotNull ErroredBattleStart it) {
                           it.sendTo(this.$player as Entity, <unrepresentable>.INSTANCE);
                        }
                     }) as (ErroredBattleStart?) -> Unit);
               } else if (var10000 is ServerPlayer) {
                  if (player == var10000) {
                     return;
                  }

                  val existingChallenge: BattleRegistry.BattleChallenge = BattleRegistry.INSTANCE.getPvpChallenges().get((var10000 as ServerPlayer).m_20148_());
                  var existingChallengePokemon: UUID = if (existingChallenge != null) existingChallenge.getSelectedPokemonId() else null;
                  if (existingChallenge != null && !existingChallenge.isExpired() && existingChallenge.getChallengedPlayerUUID() == player.m_20148_()) {
                     val var20: PlayerPartyStore = PlayerExtensionsKt.party(var10000 as ServerPlayer);
                     if (var20.get(existingChallengePokemon) == null) {
                        if (CollectionsKt.none(PlayerExtensionsKt.party(var10000 as ServerPlayer))) {
                           player.m_213846_(LocalizationUtilsKt.battleLang("error.no_pokemon_opponent") as Component);
                           var10000.m_213846_(LocalizationUtilsKt.battleLang("error.no_pokemon") as Component);
                           val var22: BattleRegistry = BattleRegistry.INSTANCE;
                           val var25: UUID = (var10000 as ServerPlayer).m_20148_();
                           BattleRegistry.removeChallenge$default(var22, var25, null, 2, null);
                           return;
                        }

                        existingChallengePokemon = (CollectionsKt.first(PlayerExtensionsKt.party(var10000 as ServerPlayer)) as Pokemon).getUuid();
                     }

                     BattleBuilder.pvp1v1$default(
                        BattleBuilder.INSTANCE, player, var10000 as ServerPlayer, var17, existingChallengePokemon, null, false, false, null, 240, null
                     );
                     val var21: BattleRegistry = BattleRegistry.INSTANCE;
                     val var24: UUID = (var10000 as ServerPlayer).m_20148_();
                     BattleRegistry.removeChallenge$default(var21, var24, null, 2, null);
                  } else {
                     val var10002: UUID = UUID.randomUUID();
                     val var10003: UUID = (var10000 as ServerPlayer).m_20148_();
                     val var12: BattleRegistry.BattleChallenge = new BattleRegistry.BattleChallenge(var10002, var10003, var17, 0, 8, null);
                     val var13: java.util.Map = BattleRegistry.INSTANCE.getPvpChallenges();
                     val var18: UUID = player.m_20148_();
                     var13.put(var18, var12);
                     SchedulingFunctionsKt.afterOnServer$default(0, (float)var12.getExpiryTimeSeconds(), (new Function0<Unit>(player, var12) {
                        {
                           super(0);
                           this.$player = `$player`;
                           this.$challenge = `$challenge`;
                        }

                        public final void invoke() {
                           val var10000: BattleRegistry = BattleRegistry.INSTANCE;
                           val var10001: UUID = this.$player.m_20148_();
                           var10000.removeChallenge(var10001, this.$challenge.getChallengeId());
                        }
                     }) as Function0, 1, null);
                     val var19: CobblemonNetwork = CobblemonNetwork.INSTANCE;
                     val var10001: ServerPlayer = var10000 as ServerPlayer;
                     val var10004: UUID = var12.getChallengeId();
                     val var10005: UUID = player.m_20148_();
                     val var10006: MutableComponent = player.m_7755_().m_6881_();
                     var19.sendPacket(var10001, new BattleChallengeNotificationPacket(var10004, var10005, TextKt.aqua(var10006)));
                     val var14: Array<Any> = new Object[1];
                     val var26: Component = (var10000 as ServerPlayer).m_7755_();
                     var14[0] = var26;
                     val var23: MutableComponent = LocalizationUtilsKt.lang("challenge.sender", var14);
                     player.m_213846_(TextKt.yellow(var23) as Component);
                  }
               }
            }
         }
      }
   }

   fun handleOnNettyThread(packet: BattleChallengePacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
