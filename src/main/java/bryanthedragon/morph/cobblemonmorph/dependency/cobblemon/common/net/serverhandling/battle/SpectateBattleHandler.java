package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PlayerBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMessagePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMusicPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.battle.SpectateBattlePacket
import java.util.ArrayList;
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@SourceDebugExtension(["SMAP\nSpectateBattleHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpectateBattleHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/battle/SpectateBattleHandler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,44:1\n800#2,11:45\n288#2,2:56\n1#3:58\n*S KotlinDebug\n*F\n+ 1 SpectateBattleHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/battle/SpectateBattleHandler\n*L\n33#1:45,11\n33#1:56,2\n*E\n"])
public object SpectateBattleHandler : ServerNetworkPacketHandler<SpectateBattlePacket> {
   public final val LOGGER: Logger = LogManager.getLogger()

   public open fun handle(packet: SpectateBattlePacket, server: MinecraftServer, player: ServerPlayer) {
      val battle: PokemonBattle = BattleRegistry.INSTANCE.getBattleByParticipatingPlayerId(packet.getTargetedEntityId());
      if (battle != null && Cobblemon.INSTANCE.getConfig().getAllowSpectating()) {
         val `$this$firstOrNull$iv`: java.lang.Iterable = battle.getActors();
         val var9: java.util.Collection = new ArrayList();

         for (Object element$iv$iv : $this$filterIsInstance$iv) {
            if (`element$iv$iv` is PlayerBattleActor) {
               var9.add(`element$iv$iv`);
            }
         }

         val it: java.util.Iterator = (var9 as java.util.List).iterator();

         var var10000: Any;
         while (true) {
            if (!it.hasNext()) {
               var10000 = null;
               break;
            }

            val var16: Any = it.next();
            if ((var16 as PlayerBattleActor).getUuid() == packet.getTargetedEntityId()) {
               var10000 = (java.util.Set)var16;
               break;
            }
         }

         val target: PlayerBattleActor = var10000 as PlayerBattleActor;
         var10000 = battle.getSpectators();
         val var10001: UUID = player.m_20148_();
         var10000.add(var10001);
         CobblemonNetwork.INSTANCE.sendPacket(player, new BattleInitializePacket(battle, null));
         CobblemonNetwork.INSTANCE.sendPacket(player, new BattleMessagePacket(battle.getChatLog()));
         if (target != null) {
            val var21: SoundEvent = target.getBattleTheme();
            if (var21 != null) {
               CobblemonNetwork.INSTANCE.sendPacket(player, new BattleMusicPacket(var21, 0.0F, 0.0F, 6, null));
            }
         }
      } else {
         LOGGER.error("Battle of player id ${packet.getTargetedEntityId()} not found (${player.m_20148_()} tried spectating)");
      }
   }

   fun handleOnNettyThread(packet: SpectateBattlePacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
