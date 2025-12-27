package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.PrioritizedList
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.factory.PokemonStoreFactory
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PCBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.party.SetPartyReferencePacket
import java.util.ArrayList;
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.level.ServerPlayer

@SourceDebugExtension(["SMAP\nPokemonStoreManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonStoreManager.kt\ncom/cobblemon/mod/common/api/storage/PokemonStoreManager\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,106:1\n1855#2,2:107\n1855#2,2:110\n1855#2,2:112\n1#3:109\n*S KotlinDebug\n*F\n+ 1 PokemonStoreManager.kt\ncom/cobblemon/mod/common/api/storage/PokemonStoreManager\n*L\n45#1:107,2\n96#1:110,2\n97#1:112,2\n*E\n"])
public open class PokemonStoreManager {
   private final val factories: PrioritizedList<PokemonStoreFactory> = new PrioritizedList()

   public open fun registerFactory(priority: Priority, factory: PokemonStoreFactory) {
      this.factories.add(priority, factory);
   }

   public open fun unregisterFactory(factory: PokemonStoreFactory) {
      factory.shutdown();
      this.factories.remove(factory);
   }

   public open fun unregisterAll() {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         this.unregisterFactory(`element$iv` as PokemonStoreFactory);
      }
   }

   public open fun getParty(player: ServerPlayer): PlayerPartyStore {
      val var10001: UUID = player.m_20148_();
      return this.getParty(var10001);
   }

   @Throws(com/cobblemon/mod/common/api/storage/NoPokemonStoreException::class)
   public open fun getParty(playerID: UUID): PlayerPartyStore {
      val var2: java.util.Iterator = this.factories.iterator();

      var var10000: PlayerPartyStore;
      while (true) {
         if (var2.hasNext()) {
            val var5: PlayerPartyStore = (var2.next() as PokemonStoreFactory).getPlayerParty(playerID);
            if (var5 == null) {
               continue;
            }

            var10000 = var5;
            break;
         }

         var10000 = null;
         break;
      }

      if (var10000 == null) {
         throw new NoPokemonStoreException(
            "No factory was able to provide a party for $playerID - this should not be possible unless someone has removed the default provider!"
         );
      } else {
         return var10000;
      }
   }

   @Throws(com/cobblemon/mod/common/api/storage/NoPokemonStoreException::class)
   public open fun getPC(playerID: UUID): PCStore {
      val var2: java.util.Iterator = this.factories.iterator();

      var var10000: PCStore;
      while (true) {
         if (var2.hasNext()) {
            val var5: PCStore = (var2.next() as PokemonStoreFactory).getPC(playerID);
            if (var5 == null) {
               continue;
            }

            var10000 = var5;
            break;
         }

         var10000 = null;
         break;
      }

      if (var10000 == null) {
         throw new NoPokemonStoreException(
            "No factory was able to provide a PC for $playerID - this should not be possible unless someone has removed the default provider!"
         );
      } else {
         return var10000;
      }
   }

   public open fun getPCForPlayer(player: ServerPlayer, pcBlockEntity: PCBlockEntity): PCStore? {
      val var3: java.util.Iterator = this.factories.iterator();

      var var10000: PCStore;
      while (true) {
         if (var3.hasNext()) {
            val var6: PCStore = (var3.next() as PokemonStoreFactory).getPCForPlayer(player, pcBlockEntity);
            if (var6 == null) {
               continue;
            }

            var10000 = var6;
            break;
         }

         var10000 = null;
         break;
      }

      return var10000;
   }

   public open fun getParties(playerID: UUID): Iterable<PartyStore> {
      val parties: java.util.List = new ArrayList();

      for (PokemonStoreFactory factory : this.factories) {
         val var10000: PlayerPartyStore = factory.getPlayerParty(playerID);
         if (var10000 != null) {
            parties.add(var10000);
         }
      }

      return parties;
   }

   public open fun getPCs(playerID: UUID): Iterable<PCStore> {
      val pcs: java.util.List = new ArrayList();

      for (PokemonStoreFactory factory : this.factories) {
         val var10000: PCStore = factory.getPC(playerID);
         if (var10000 != null) {
            pcs.add(var10000);
         }
      }

      return pcs;
   }

   public open fun <E : StorePosition, T : PokemonStore<Any>> getCustomStore(storeClass: Class<Any>, uuid: UUID): Any? {
      for (PokemonStoreFactory factory : this.factories) {
         val var5: PokemonStore = factory.getCustomStore(storeClass, uuid);
         if (var5 != null) {
            return (T)var5;
         }
      }

      return null;
   }

   public open fun onPlayerDataSync(player: ServerPlayer) {
      val var10001: UUID = player.m_20148_();
      val parties: java.lang.Iterable = this.getParties(var10001);

      for (Object element$iv : parties) {
         (`element$iv` as PartyStore).sendTo(player);
      }
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         (var11 as PCStore).sendTo(player);
      }

      CobblemonNetwork.INSTANCE.sendPacket(player, new SetPartyReferencePacket((CollectionsKt.first(parties) as PartyStore).getUuid()));
   }

   public open fun onPlayerDisconnect(player: ServerPlayer) {
      for (PokemonStoreFactory factory : this.factories) {
         val var10001: UUID = player.m_20148_();
         factory.onPlayerDisconnect(var10001);
      }
   }
}
