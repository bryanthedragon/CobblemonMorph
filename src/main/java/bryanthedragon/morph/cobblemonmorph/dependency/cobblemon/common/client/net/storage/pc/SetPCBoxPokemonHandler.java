package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage.ClientBox
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage.ClientPC
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.PokemonDTO
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.SetPCBoxPokemonPacket
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.Minecraft

@SourceDebugExtension(["SMAP\nSetPCBoxPokemonHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SetPCBoxPokemonHandler.kt\ncom/cobblemon/mod/common/client/net/storage/pc/SetPCBoxPokemonHandler\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,26:1\n215#2,2:27\n*S KotlinDebug\n*F\n+ 1 SetPCBoxPokemonHandler.kt\ncom/cobblemon/mod/common/client/net/storage/pc/SetPCBoxPokemonHandler\n*L\n23#1:27,2\n*E\n"])
public object SetPCBoxPokemonHandler : ClientNetworkPacketHandler<SetPCBoxPokemonPacket> {
   public open fun handle(packet: SetPCBoxPokemonPacket, client: Minecraft) {
      val var10000: ClientPC = CobblemonClient.INSTANCE.getStorage().getPcStores().get(packet.getStoreID());
      if (var10000 != null) {
         val pc: ClientPC = var10000;
         val boxNumber: Int = packet.getBoxNumber();

         while (pc.getBoxes().size() <= boxNumber) {
            pc.getBoxes().add(new ClientBox());
         }

         pc.getBoxes().set(boxNumber, new ClientBox());

         for (Entry element$iv : packet.getPokemon().entrySet()) {
            pc.getBoxes()
               .get(packet.getBoxNumber())
               .getSlots()
               .set((`element$iv`.getKey() as java.lang.Number).intValue(), (`element$iv`.getValue() as PokemonDTO).create());
         }
      }
   }

   fun handleOnNettyThread(packet: SetPCBoxPokemonPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
