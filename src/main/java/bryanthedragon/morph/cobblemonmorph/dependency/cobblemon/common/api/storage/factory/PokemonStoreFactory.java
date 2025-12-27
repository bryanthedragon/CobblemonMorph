package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.factory

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PCBlockEntity
import java.util.UUID
import net.minecraft.server.level.ServerPlayer

public interface PokemonStoreFactory {
   public abstract fun getPlayerParty(playerID: UUID): PlayerPartyStore? {
   }

   public abstract fun getPC(playerID: UUID): PCStore? {
   }

   public open fun getPCForPlayer(player: ServerPlayer, pcBlockEntity: PCBlockEntity): PCStore? {
   }

   public abstract fun <E : StorePosition, T : PokemonStore<Any>> getCustomStore(storeClass: Class<Any>, uuid: UUID): Any? {
   }

   public abstract fun shutdown() {
   }

   public abstract fun onPlayerDisconnect(playerID: UUID) {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun getPCForPlayer(`$this`: PokemonStoreFactory, player: ServerPlayer, pcBlockEntity: PCBlockEntity): PCStore? {
         val var10001: UUID = player.m_20148_();
         return `$this`.getPC(var10001);
      }
   }
}
