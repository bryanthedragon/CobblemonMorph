package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.ArrayList;
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nClientPC.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ClientPC.kt\ncom/cobblemon/mod/common/client/storage/ClientPC\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,55:1\n1#2:56\n1855#3:57\n1855#3,2:58\n1856#3:60\n*S KotlinDebug\n*F\n+ 1 ClientPC.kt\ncom/cobblemon/mod/common/client/storage/ClientPC\n*L\n18#1:57\n19#1:58,2\n18#1:60\n*E\n"])
public class ClientPC(uuid: UUID, boxCount: Int) : ClientStorage(uuid) {
   public final val boxes: MutableList<ClientBox>

   init {
      val var3: ArrayList = new ArrayList(boxCount);

      for (int var4 = 0; var4 < boxCount; var4++) {
         var3.add(new ClientBox());
      }

      this.boxes = var3;
   }

   public override fun findByUUID(uuid: UUID): Pokemon? {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val `$this$forEach$ivx`: java.lang.Iterable;
         for (Object element$ivx : $this$forEach$ivx) {
            val it: Pokemon = `element$ivx` as Pokemon;
            if (`element$ivx` as Pokemon != null && (`element$ivx` as Pokemon).getUuid() == uuid) {
               return it;
            }
         }
      }

      return null;
   }

   public open fun set(position: PCPosition, pokemon: Pokemon?) {
      if (this.boxes.size() > position.getBox()) {
         val box: ClientBox = this.boxes.get(position.getBox());
         if (position.getSlot() < 30) {
            box.getSlots().set(position.getSlot(), pokemon);
         }
      }
   }

   public open fun get(position: PCPosition): Pokemon? {
      return if (position.getSlot() < 30 && position.getBox() < this.boxes.size())
         this.boxes.get(position.getBox()).getSlots().get(position.getSlot())
         else
         null;
   }

   public open fun getPosition(pokemon: Pokemon): PCPosition? {
      var boxNumber: Int = 0;

      for (int var3 = this.boxes.size(); boxNumber < var3; boxNumber++) {
         val box: ClientBox = this.boxes.get(boxNumber);
         var slotNumber: Int = 0;

         for (int var6 = box.getSlots().size(); slotNumber < var6; slotNumber++) {
            if (box.getSlots().get(slotNumber) == pokemon) {
               return new PCPosition(boxNumber, slotNumber);
            }
         }
      }

      return null;
   }
}
