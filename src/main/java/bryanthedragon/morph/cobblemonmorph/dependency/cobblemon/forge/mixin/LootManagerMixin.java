/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.storage.loot.LootDataManager
 *  net.minecraftforge.common.crafting.CraftingHelper
 *  net.minecraftforge.common.crafting.conditions.ICondition$IContext
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Redirect
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.forge.mixin;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Map;
import java.util.function.BiConsumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootDataManager;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={LootDataManager.class})
public class LootManagerMixin {
    @Unique
    private static final String LOAD_CONDITIONS = "cobblemon:forge_load_conditions";

    @SuppressWarnings("null")
    @Redirect(method={"m_278660_"}, at=@At(value="INVOKE", target="Ljava/util/Map;forEach(Ljava/util/function/BiConsumer;)V"), remap=false)
    private static void cobblemon$supportICondition(Map<ResourceLocation, JsonElement> map, BiConsumer<ResourceLocation, JsonElement> consumer) {
        map.forEach((identifier, jsonElement) -> {
            if (jsonElement.isJsonObject() && CraftingHelper.processConditions((JsonObject)jsonElement.getAsJsonObject(), (String)LOAD_CONDITIONS, (ICondition.IContext)ICondition.IContext.EMPTY)) {
                consumer.accept((ResourceLocation)identifier, (JsonElement)jsonElement);
                return;
            }
            consumer.accept((ResourceLocation)identifier, (JsonElement)jsonElement);
        });
    }
}

