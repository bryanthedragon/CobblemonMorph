package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.ArrayList;
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.jvm.internal.markers.KMappedMarker

@SourceDebugExtension(["SMAP\nClientParty.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ClientParty.kt\ncom/cobblemon/mod/common/client/storage/ClientParty\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,47:1\n1#2:48\n350#3,7:49\n*S KotlinDebug\n*F\n+ 1 ClientParty.kt\ncom/cobblemon/mod/common/client/storage/ClientParty\n*L\n38#1:49,7\n*E\n"])
public class ClientParty(uuid: UUID, slots: Int) : ClientStorage(uuid), java.lang.Iterable<Pokemon>, KMappedMarker {
   public final val slots: MutableList<Pokemon?>

   init {
      val var3: ArrayList = new ArrayList(slots);

      for (int var4 = 0; var4 < slots; var4++) {
         var3.add(null);
      }

      this.slots = var3;
   }

   public override operator fun iterator(): MutableIterator<Pokemon?> {
      return this.slots.iterator();
   }

   public override fun findByUUID(uuid: UUID): Pokemon? {
      val var3: java.util.Iterator = this.slots.iterator();

      var var10000: Any;
      while (true) {
         if (var3.hasNext()) {
            val var4: Any = var3.next();
            if (!((if (var4 as Pokemon != null) (var4 as Pokemon).getUuid() else null) == uuid)) {
               continue;
            }

            var10000 = var4;
            break;
         }

         var10000 = null;
         break;
      }

      return var10000 as Pokemon;
   }

   public open fun set(position: PartyPosition, pokemon: Pokemon?) {
      if (position.getSlot() < this.slots.size()) {
         this.slots.set(position.getSlot(), pokemon);
      }
   }

   public fun get(slot: Int): Pokemon? {
      return this.get(new PartyPosition(slot));
   }

   public open fun get(position: PartyPosition): Pokemon? {
      return if (position.getSlot() < this.slots.size() && position.getSlot() != -1) this.slots.get(position.getSlot()) else null;
   }

   public fun isEmpty(): Boolean {
      return this.slots.size() == 0;
   }

   public fun getPosition(pokemonID: UUID): Int {
      var `index$iv`: Int = 0;
      val var5: java.util.Iterator = this.slots.iterator();

      var var10000: Int;
      while (true) {
         if (!var5.hasNext()) {
            var10000 = -1;
            break;
         }

         val it: Pokemon = var5.next() as Pokemon;
         if ((if (it != null) it.getUuid() else null) == pokemonID) {
            var10000 = `index$iv`;
            break;
         }

         `index$iv`++;
      }

      return var10000;
   }

   public open fun getPosition(pokemon: Pokemon): PartyPosition? {
      var slotNumber: Int = 0;

      for (int var3 = this.slots.size(); slotNumber < var3; slotNumber++) {
         if (this.slots.get(slotNumber) == pokemon) {
            return new PartyPosition(slotNumber);
         }
      }

      return null;
   }
}
