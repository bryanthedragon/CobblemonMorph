package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities;

import com.google.gson.JsonObject;

import net.minecraft.nbt.CompoundTag;

public class AbilityTemplate {
   public final Function2<AbilityTemplate, Boolean, Ability> builder;
   public final String description;
   public final String displayName;
   public final String name;

   public AbilityTemplate(String name, Function2<AbilityTemplate, Boolean, Ability> builder, String displayName, String description) {
      this.name = name;
      this.builder = builder;
      this.displayName = displayName;
      this.description = description;
   }

   public Ability create(boolean forced) {
      return (Ability) this.builder.invoke(this, forced);
   }

   public Ability create(CompoundTag nbt) {
      return create(false).loadFromNBT(nbt);
   }

   public Ability create(JsonObject json) {
      return create(false).loadFromJSON(json);
   }
}
