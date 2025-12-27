package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nClientStorageManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ClientStorageManager.kt\ncom/cobblemon/mod/common/client/storage/ClientStorageManager\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,155:1\n1747#2,3:156\n288#2,2:159\n*S KotlinDebug\n*F\n+ 1 ClientStorageManager.kt\ncom/cobblemon/mod/common/client/storage/ClientStorageManager\n*L\n33#1:156,3\n63#1:159,2\n*E\n"])
public class ClientStorageManager {
   public final var myParty: ClientParty
   public final val partyStores: MutableMap<UUID, ClientParty>
   public final val pcStores: MutableMap<UUID, ClientPC>
   private final var selectedPokemon: UUID?
   public final var selectedSlot: Int

   public fun shiftSelected(forward: Boolean) {
      val `$this$any$iv`: java.lang.Iterable = this.myParty.getSlots();
      var var10000: Boolean;
      if (`$this$any$iv` is java.util.Collection && (`$this$any$iv` as java.util.Collection).isEmpty()) {
         var10000 = false;
      } else {
         label63: {
            for (Object element$iv : $this$any$iv) {
               if (`element$iv` as Pokemon != null) {
                  var10000 = true;
                  break label63;
               }
            }

            var10000 = false;
         }
      }

      if (!var10000) {
         this.selectedSlot = 0;
         this.selectedPokemon = null;
      } else {
         this.selectedSlot += if (forward) 1 else -1;
         if (this.selectedSlot >= this.myParty.getSlots().size()) {
            this.selectedSlot = -1;
            this.shiftSelected(forward);
         } else if (this.selectedSlot < 0) {
            this.selectedSlot = this.myParty.getSlots().size();
            this.shiftSelected(forward);
         } else if (this.myParty.get(this.selectedSlot) == null) {
            this.shiftSelected(forward);
         } else {
            val var10001: Pokemon = this.myParty.get(this.selectedSlot);
            this.selectedPokemon = if (var10001 != null) var10001.getUuid() else null;
         }
      }
   }

   public fun switchToPokemon(pokemon: UUID) {
      this.selectedPokemon = pokemon;
      this.selectedSlot = CollectionsKt.indexOf(this.myParty, this.myParty.findByUUID(pokemon));
      this.checkSelectedPokemon();
   }

   public fun checkSelectedPokemon() {
      if (this.selectedSlot == -1) {
         var var10000: Any;
         label41: {
            val `$this$firstOrNull$iv`: java.lang.Iterable;
            for (Object element$iv : $this$firstOrNull$iv) {
               if (`element$iv` as Pokemon != null) {
                  var10000 = (Pokemon)`element$iv`;
                  break label41;
               }
            }

            var10000 = null;
         }

         var10000 = var10000;
         if (var10000 == null) {
            return;
         }

         this.selectedSlot = this.myParty.getSlots().indexOf(var10000);
         this.selectedPokemon = var10000.getUuid();
      } else if (this.selectedPokemon == null) {
         var var13: ClientStorageManager;
         var var16: UUID;
         label47: {
            var13 = this;
            val var10001: Pokemon = this.myParty.get(new PartyPosition(this.selectedSlot));
            if (var10001 != null) {
               var16 = var10001.getUuid();
               if (var16 != null) {
                  break label47;
               }
            }

            val var10: ClientStorageManager = this;
            this.selectedSlot = -1;
            var10.checkSelectedPokemon();
            var16 = null;
            var13 = this;
         }

         var13.selectedPokemon = var16;
      } else {
         var var14: ClientParty = this.myParty;
         var var17: UUID = this.selectedPokemon;
         if (var14.getPosition(var17) != this.selectedSlot) {
            var14 = this.myParty;
            var17 = this.selectedPokemon;
            val var9: Int = var14.getPosition(var17);
            if (var9 != -1) {
               this.selectedSlot = var9;
            } else {
               this.selectedPokemon = null;
               this.checkSelectedPokemon();
            }
         } else if (this.selectedSlot >= this.myParty.getSlots().size()) {
            this.selectedSlot = -1;
            this.checkSelectedPokemon();
         }
      }
   }

   public fun locatePokemon(storeID: UUID, pokemonID: UUID): Pokemon? {
      val var10000: ClientParty = this.partyStores.get(storeID);
      if (var10000 != null) {
         val var3: Pokemon = var10000.findByUUID(pokemonID);
         if (var3 != null) {
            return var3;
         }
      }

      val var4: ClientPC = this.pcStores.get(storeID);
      return if (var4 != null) var4.findByUUID(pokemonID) else null;
   }

   public fun createParty(mine: Boolean, uuid: UUID, slots: Int) {
      val party: ClientParty = new ClientParty(uuid, slots);
      this.partyStores.put(uuid, party);
      if (mine) {
         this.myParty = party;
         this.checkSelectedPokemon();
      }
   }

   public fun setPartyPokemon(storeID: UUID, position: PartyPosition, pokemon: Pokemon) {
      val var10000: ClientParty = this.partyStores.get(storeID);
      if (var10000 == null) {
         Cobblemon.INSTANCE.getLOGGER().error("Tried setting a Pokémon in position $position for party store $storeID but no such store found.");
      } else {
         var10000.set(position, pokemon);
         this.checkSelectedPokemon();
      }
   }

   public fun setPCPokemon(storeID: UUID, position: PCPosition, pokemon: Pokemon) {
      val var10000: ClientPC = this.pcStores.get(storeID);
      if (var10000 == null) {
         Cobblemon.INSTANCE.getLOGGER().error("Tried setting a Pokémon in position $position for PC store $storeID but no such store found.");
      } else {
         var10000.set(position, pokemon);
      }
   }

   public fun setPartyStore(storeID: UUID) {
      val var10001: ClientParty = this.partyStores.get(storeID);
      if (var10001 == null) {
         throw new IllegalArgumentException("Was told to set party store to $storeID but no such store is known!");
      } else {
         this.myParty = var10001;
         this.checkSelectedPokemon();
      }
   }

   public fun removeFromParty(storeID: UUID, pokemonID: UUID) {
      val var10000: ClientParty = this.partyStores.get(storeID);
      if (var10000 != null) {
         var10000.remove(pokemonID);
      }

      this.checkSelectedPokemon();
   }

   public fun moveInParty(storeID: UUID, pokemonID: UUID, newPosition: PartyPosition) {
      val var10000: ClientParty = this.partyStores.get(storeID);
      if (var10000 != null) {
         var10000.move(pokemonID, newPosition);
      }

      this.checkSelectedPokemon();
   }

   public fun swapInParty(storeID: UUID, pokemonID1: UUID, pokemonID2: UUID) {
      val var10000: ClientParty = this.partyStores.get(storeID);
      if (var10000 != null) {
         var10000.swap(pokemonID1, pokemonID2);
      }

      this.checkSelectedPokemon();
   }

   public fun swapInPC(storeID: UUID, pokemonID1: UUID, pokemonID2: UUID) {
      val var10000: ClientPC = this.pcStores.get(storeID);
      if (var10000 != null) {
         var10000.swap(pokemonID1, pokemonID2);
      }
   }

   public fun moveInPC(storeID: UUID, pokemonID: UUID, newPosition: PCPosition) {
      val var10000: ClientPC = this.pcStores.get(storeID);
      if (var10000 != null) {
         var10000.move(pokemonID, newPosition);
      }

      this.checkSelectedPokemon();
   }

   public fun removeFromPC(storeID: UUID, pokemonID: UUID) {
      val var10000: ClientPC = this.pcStores.get(storeID);
      if (var10000 != null) {
         var10000.remove(pokemonID);
      }
   }

   public fun onLogin() {
      val var10003: UUID = UUID.randomUUID();
      this.myParty = new ClientParty(var10003, 1);
      this.checkSelectedPokemon();
   }

   public fun onLogout() {
      this.partyStores.clear();
      this.pcStores.clear();
   }
}
