package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ActiveClientBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleSide
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.BattleGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket.BattleActorDTO
import java.util.ArrayList;
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.Minecraft
import net.minecraft.client.player.LocalPlayer

@SourceDebugExtension(["SMAP\nBattleInitializeHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleInitializeHandler.kt\ncom/cobblemon/mod/common/client/net/battle/BattleInitializeHandler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,78:1\n2624#2,3:79\n1747#2,3:82\n1549#2:85\n1620#2,3:86\n1549#2:89\n1620#2,3:90\n1549#2:93\n1620#2,3:94\n*S KotlinDebug\n*F\n+ 1 BattleInitializeHandler.kt\ncom/cobblemon/mod/common/client/net/battle/BattleInitializeHandler\n*L\n28#1:79,3\n36#1:82,3\n37#1:85\n37#1:86,3\n38#1:89\n38#1:90,3\n61#1:93\n61#1:94,3\n*E\n"])
public object BattleInitializeHandler : ClientNetworkPacketHandler<BattleInitializePacket> {
   public open fun handle(packet: BattleInitializePacket, client: Minecraft) {
      val var10000: LocalPlayer = Minecraft.m_91087_().f_91074_;
      val playerUUID: UUID = if (var10000 != null) var10000.m_20148_() else null;
      val var56: CobblemonClient = CobblemonClient.INSTANCE;
      val var4: ClientBattle = new ClientBattle(packet.getBattleId(), packet.getBattleFormat());
      val `$this$handle_u24lambda_u245`: ClientBattle = var4;
      val otherSide: java.lang.Iterable = packet.getSide2().getActors();
      var var57: Boolean;
      if (otherSide is java.util.Collection && (otherSide as java.util.Collection).isEmpty()) {
         var57 = true;
      } else {
         val `$this$map$iv`: java.util.Iterator = otherSide.iterator();

         while (true) {
            if (!`$this$map$iv`.hasNext()) {
               var57 = true;
               break;
            }

            if ((`$this$map$iv`.next() as BattleInitializePacket.BattleActorDTO).getUuid() == playerUUID) {
               var57 = false;
               break;
            }
         }
      }

      val mySide: BattleInitializePacket.BattleSideDTO = if (var57) packet.getSide1() else packet.getSide2();
      val var25: BattleInitializePacket.BattleSideDTO = if (mySide == packet.getSide1()) packet.getSide2() else packet.getSide1();
      var var28: java.lang.Iterable = CollectionsKt.listOf(new BattleInitializePacket.BattleSideDTO[]{packet.getSide1(), packet.getSide2()});
      var var60: Boolean;
      if (var28 is java.util.Collection && (var28 as java.util.Collection).isEmpty()) {
         var60 = false;
      } else {
         val var37: java.util.Iterator = var28.iterator();

         while (true) {
            if (!var37.hasNext()) {
               var60 = false;
               break;
            }

            val `item$iv$iv`: java.lang.Iterable = (var37.next() as BattleInitializePacket.BattleSideDTO).getActors();
            var var59: Boolean;
            if (`item$iv$iv` is java.util.Collection && (`item$iv$iv` as java.util.Collection).isEmpty()) {
               var59 = false;
            } else {
               label151: {
                  for (Object element$iv : $this$any$iv) {
                     val var58: UUID = (`element$iv` as BattleInitializePacket.BattleActorDTO).getUuid();
                     val var10001: LocalPlayer = Minecraft.m_91087_().f_91074_;
                     if (var58 == (if (var10001 != null) var10001.m_20148_() else null)) {
                        var59 = true;
                        break label151;
                     }
                  }

                  var59 = false;
               }
            }

            if (var59) {
               var60 = true;
               break;
            }
         }
      }

      var4.setSpectating(!var60);
      val var61: java.util.List = var4.getSide1().getActors();
      var28 = mySide.getActors();
      var var40: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var28, 10));

      for (Object item$iv$ivx : var28) {
         var40.add(INSTANCE.actorFromDTO(`item$iv$ivx` as BattleInitializePacket.BattleActorDTO, !`$this$handle_u24lambda_u245`.getSpectating()));
      }

      var61.addAll(var40 as java.util.List);
      val var62: java.util.List = `$this$handle_u24lambda_u245`.getSide2().getActors();
      var28 = var25.getActors();
      var40 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var28, 10));

      for (Object item$iv$ivx : var28) {
         var40.add(INSTANCE.actorFromDTO(`item$iv$ivx` as BattleInitializePacket.BattleActorDTO, false));
      }

      var62.addAll(var40 as java.util.List);

      for (ClientBattleSide side : CollectionsKt.listOf(new ClientBattleSide[]{$this$handle_u24lambda_u245.getSide1(), $this$handle_u24lambda_u245.getSide2()})) {
         var36.setBattle(`$this$handle_u24lambda_u245`);

         for (ClientBattleActor actor : side.getActors()) {
            var42.setSide(var36);

            for (ActiveClientBattlePokemon pokemon : actor.getActivePokemon()) {
               val var63: ClientBattlePokemon = var49.getBattlePokemon();
               if (var63 != null) {
                  var63.setActor(var42);
               }
            }
         }
      }

      `$this$handle_u24lambda_u245`.setMinimised(false);
      var56.setBattle(var4);
      Minecraft.m_91087_().m_91152_(new BattleGUI());
   }

   public fun actorFromDTO(actorDTO: BattleActorDTO, isAlly: Boolean): ClientBattleActor {
      val var3: ClientBattleActor = new ClientBattleActor(actorDTO.getShowdownId(), actorDTO.getDisplayName(), actorDTO.getUuid(), actorDTO.getType());
      val `$this$actorFromDTO_u24lambda_u248`: ClientBattleActor = var3;
      val var10000: java.util.List = var3.getActivePokemon();
      val `$this$map$iv`: java.lang.Iterable = actorDTO.getActivePokemon();
      val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$map$iv`, 10));

      for (Object item$iv$iv : $this$map$iv) {
         val it: BattleInitializePacket.ActiveBattlePokemonDTO = `item$iv$iv` as BattleInitializePacket.ActiveBattlePokemonDTO;
         var var30: ClientBattleActor = `$this$actorFromDTO_u24lambda_u248`;
         val var10001: ClientBattlePokemon;
         if (it != null) {
            var10001 = new ClientBattlePokemon(
               it.getUuid(),
               it.getDisplayName(),
               it.getProperties(),
               it.getAspects(),
               it.getHpValue(),
               it.getMaxHp(),
               isAlly,
               it.getStatus(),
               it.getStatChanges()
            );
            var30 = `$this$actorFromDTO_u24lambda_u248`;
         } else {
            var10001 = null;
         }

         `destination$iv$iv`.add(new ActiveClientBattlePokemon(var30, var10001));
      }

      var10000.addAll(`destination$iv$iv` as java.util.List);
      return var3;
   }

   fun handleOnNettyThread(packet: BattleInitializePacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
