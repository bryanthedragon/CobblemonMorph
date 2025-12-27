package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.gui

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.Summary
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.PokemonDTO
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.ui.SummaryUIPacket
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.Minecraft

@SourceDebugExtension(["SMAP\nSummaryUIPacketHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SummaryUIPacketHandler.kt\ncom/cobblemon/mod/common/client/net/gui/SummaryUIPacketHandler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,28:1\n1549#2:29\n1620#2,3:30\n*S KotlinDebug\n*F\n+ 1 SummaryUIPacketHandler.kt\ncom/cobblemon/mod/common/client/net/gui/SummaryUIPacketHandler\n*L\n21#1:29\n21#1:30,3\n*E\n"])
public object SummaryUIPacketHandler : ClientNetworkPacketHandler<SummaryUIPacket> {
   public open fun handle(packet: SummaryUIPacket, client: Minecraft) {
      try {
         val var10000: Summary.Companion = Summary.Companion;
         val e: java.lang.Iterable = packet.getPokemon();
         val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(e, 10));

         for (Object item$iv$iv : e) {
            `destination$iv$iv`.add((`item$iv$iv` as PokemonDTO).create());
         }

         Summary.Companion.open$default(var10000, `destination$iv$iv` as java.util.List, packet.getEditable(), 0, 4, null);
      } catch (var14: Exception) {
         Cobblemon.INSTANCE.getLOGGER().debug("Failed to open the summary from the SummaryUI packet handler", var14);
      }
   }

   fun handleOnNettyThread(packet: SummaryUIPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
