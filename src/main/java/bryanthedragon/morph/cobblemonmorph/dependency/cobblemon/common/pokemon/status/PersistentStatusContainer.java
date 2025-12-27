package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses
import com.google.gson.JsonObject
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation

public class PersistentStatusContainer(status: PersistentStatus, secondsLeft: Int = 0) {
   public final var secondsLeft: Int
   public final val status: PersistentStatus

   init {
      this.status = status;
      this.secondsLeft = secondsLeft;
   }

   public fun isExpired(): Boolean {
      return this.secondsLeft <= 0;
   }

   public fun tickTimer() {
      this.secondsLeft += -1;
   }

   public fun saveToNBT(nbt: CompoundTag): CompoundTag {
      nbt.m_128359_("StatusName", this.status.getName().toString());
      nbt.m_128405_("StatusTimer", this.secondsLeft);
      return nbt;
   }

   public fun saveToJSON(json: JsonObject): JsonObject {
      json.addProperty("StatusName", this.status.getName().toString());
      json.addProperty("StatusTimer", this.secondsLeft);
      return json;
   }

   public companion object {
      public fun loadFromNBT(nbt: CompoundTag): PersistentStatusContainer? {
         val statusId: java.lang.String = nbt.m_128461_("StatusName");
         val activeSeconds: Int = nbt.m_128451_("StatusTimer");
         if (statusId.length() == 0) {
            return null;
         } else {
            val var10000: Status = Statuses.INSTANCE.getStatus(new ResourceLocation(statusId));
            if (var10000 == null) {
               return null;
            } else {
               return if (var10000 !is PersistentStatus) null else new PersistentStatusContainer(var10000 as PersistentStatus, activeSeconds);
            }
         }
      }

      public fun loadFromJSON(json: JsonObject): PersistentStatusContainer? {
         val statusId: java.lang.String = json.get("StatusName").getAsString();
         val activeSeconds: Int = json.get("StatusTimer").getAsInt();
         if (statusId.length() == 0) {
            return null;
         } else {
            val var10000: Status = Statuses.INSTANCE.getStatus(new ResourceLocation(statusId));
            if (var10000 == null) {
               return null;
            } else {
               return if (var10000 !is PersistentStatus) null else new PersistentStatusContainer(var10000 as PersistentStatus, activeSeconds);
            }
         }
      }
   }
}
