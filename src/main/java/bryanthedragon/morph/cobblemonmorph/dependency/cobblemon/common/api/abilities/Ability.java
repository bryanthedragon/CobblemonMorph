package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Abilities;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.nbt.CompoundTag;

public class Ability {
   private final AbilityTemplate template;
   private boolean forced;
   private int index;
   private Priority priority;

   public Ability(AbilityTemplate template, boolean forced) {
      this.template = template;
      this.forced = forced;
      this.index = -1;
      this.priority = Priority.LOWEST;
   }

   public final String getDescription() {
      return this.template.getDescription();
   }

   public final String getDisplayName() {
      return this.template.getDisplayName();
   }

   public boolean isForced() {
      return forced;
   }

   public void setForced(boolean forced) {
      this.forced = forced;
   }

   public int getIndex() {
      return index;
   }

   public void setIndex(int index) {
      this.index = index;
   }

   public final String getName() {
      return this.template.getName();
   }

   public Priority getPriority() {
      return priority;
   }

   public void setPriority(Priority priority) {
      this.priority = priority;
   }

   public final AbilityTemplate getTemplate() {
      return template;
   }

   public CompoundTag saveToNBT(CompoundTag nbt) {
      nbt.m_128359_("AbilityName", this.getName());
      nbt.m_128379_("AbilityForced", this.forced);
      nbt.m_128405_("AbilityIndex", this.index);
      nbt.m_128359_("AbilityPriority", this.priority.name());
      return nbt;
   }

   public JsonObject saveToJSON(JsonObject json) {
      json.addProperty("AbilityName", this.getName());
      json.addProperty("AbilityForced", this.forced);
      json.addProperty("AbilityIndex", this.index);
      json.addProperty("AbilityPriority", this.priority.name());
      return json;
   }

   public Ability loadFromNBT(CompoundTag nbt) {
      Abilities var10001 = Abilities.INSTANCE;
      String var10002 = nbt.m_128461_("AbilityName");
      this.template = var10001.getOrException(var10002);
      this.forced = nbt.m_128471_("AbilityForced");
      if (nbt.m_128441_("AbilityIndex") && nbt.m_128441_("AbilityPriority")) {
         this.index = nbt.m_128451_("AbilityIndex");
         String var2 = nbt.m_128461_("AbilityPriority");
         this.priority = Priority.valueOf(var2);
      }

      return this;
   }

   public Ability loadFromJSON(JsonObject json) {
      Abilities var10001 = Abilities.INSTANCE;
      String var10002 = json.get("AbilityName").getAsString();
      this.template = var10001.getOrException(var10002);
      JsonElement var2 = json.get("AbilityForced");
      this.forced = var2 != null && var2.getAsBoolean();
      if (json.has("AbilityIndex") && json.has("AbilityPriority")) {
         this.index = json.get("AbilityIndex").getAsInt();
         String var3 = json.get("AbilityPriority").getAsString();
         this.priority = Priority.valueOf(var3);
      }

      return this;
   }
}
