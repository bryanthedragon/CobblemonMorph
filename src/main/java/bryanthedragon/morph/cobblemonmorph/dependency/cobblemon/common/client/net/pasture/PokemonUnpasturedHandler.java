package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.pasture

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SettableObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pasture.PasturePCGUIConfiguration
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.PCGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.PCGUIConfiguration
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.OpenPasturePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.PokemonUnpasturedPacket
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen

@SourceDebugExtension(["SMAP\nPokemonUnpasturedHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonUnpasturedHandler.kt\ncom/cobblemon/mod/common/client/net/pasture/PokemonUnpasturedHandler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,29:1\n819#2:30\n847#2,2:31\n*S KotlinDebug\n*F\n+ 1 PokemonUnpasturedHandler.kt\ncom/cobblemon/mod/common/client/net/pasture/PokemonUnpasturedHandler\n*L\n27#1:30\n27#1:31,2\n*E\n"])
public object PokemonUnpasturedHandler : ClientNetworkPacketHandler<PokemonUnpasturedPacket> {
   public open fun handle(packet: PokemonUnpasturedPacket, client: Minecraft) {
      val `$i$f$filterNot`: Screen = Minecraft.m_91087_().f_91080_;
      val var4: PCGUIConfiguration = if ((`$i$f$filterNot` as? PCGUI) != null) (`$i$f$filterNot` as? PCGUI).getConfiguration() else null;
      val pastureGuiConfiguration: PasturePCGUIConfiguration = var4 as? PasturePCGUIConfiguration;
      if ((var4 as? PasturePCGUIConfiguration) != null) {
         val var10000: SettableObservable = pastureGuiConfiguration.getPasturedPokemon();
         if (var10000 != null) {
            val `$this$filterNot$iv`: java.lang.Iterable = pastureGuiConfiguration.getPasturedPokemon().get();
            val `destination$iv$iv`: java.util.Collection = new ArrayList();

            for (Object element$iv$iv : $this$filterNot$iv) {
               if (!((`element$iv$iv` as OpenPasturePacket.PasturePokemonDataDTO).getPokemonId() == packet.getPokemonId())) {
                  `destination$iv$iv`.add(`element$iv$iv`);
               }
            }

            var10000.set(`destination$iv$iv` as java.util.List);
         }
      }
   }

   fun handleOnNettyThread(packet: PokemonUnpasturedPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
