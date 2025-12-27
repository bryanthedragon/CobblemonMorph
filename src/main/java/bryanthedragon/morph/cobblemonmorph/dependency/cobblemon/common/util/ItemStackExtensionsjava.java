package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import com.google.gson.JsonElement
import com.mojang.serialization.DynamicOps
import com.mojang.serialization.JsonOps
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.NbtOps
import net.minecraft.nbt.Tag
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack

public fun ItemStack.saveToJson(): JsonElement {
   val var10000: Any = NbtOps.f_128958_.convertTo(JsonOps.INSTANCE as DynamicOps, `$this$saveToJson`.m_41739_(new CompoundTag()) as Tag);
   return var10000 as JsonElement;
}

public fun ItemStack.isHeld(player: ServerPlayer): Boolean {
   val var10000: java.lang.Iterable = player.m_6167_();
   return CollectionsKt.contains(var10000, `$this$isHeld`) && !`$this$isHeld`.m_41619_();
}
