package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.forge.mixin;

import com.google.gson.JsonElement;
import java.util.Map;
import java.util.function.BiConsumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootDataManager;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition.IContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LootDataManager.class)
public class LootManagerMixin {
   @Unique
   private static final String LOAD_CONDITIONS = "cobblemon:forge_load_conditions";

   @Redirect(method = "m_278660_", at = @At(value = "INVOKE", target = "Ljava/util/Map;forEach(Ljava/util/function/BiConsumer;)V"), remap = false)
   private static void cobblemon$supportICondition(Map<ResourceLocation, JsonElement> map, BiConsumer<ResourceLocation, JsonElement> consumer) {
      map.forEach((identifier, jsonElement) -> {
         if (jsonElement.isJsonObject() && CraftingHelper.processConditions(jsonElement.getAsJsonObject(), "cobblemon:forge_load_conditions", IContext.EMPTY)) {
            consumer.accept(identifier, jsonElement);
         } else {
            consumer.accept(identifier, jsonElement);
         }
      });
   }
}
