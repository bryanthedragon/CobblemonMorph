/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.JsonOps
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtOps
 *  net.minecraft.nbt.Tag
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import com.google.gson.JsonElement;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0019\u0010\u0004\u001a\u00020\u0003*\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u0001\u00a2\u0006\u0004\b\u0004\u0010\u0005\u001a\u0011\u0010\u0007\u001a\u00020\u0006*\u00020\u0000\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, d2={"Lnet/minecraft/world/item/ItemStack;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "isHeld", "(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/server/level/ServerPlayer;)Z", "Lcom/google/gson/JsonElement;", "saveToJson", "(Lnet/minecraft/world/item/ItemStack;)Lcom/google/gson/JsonElement;", "common"})
public final class ItemStackExtensionsKt {
    @NotNull
    public static final JsonElement saveToJson(@NotNull ItemStack $this$saveToJson) {
        Intrinsics.checkNotNullParameter((Object)$this$saveToJson, (String)"<this>");
        Object object = NbtOps.f_128958_.convertTo((DynamicOps)JsonOps.INSTANCE, (Tag)$this$saveToJson.m_41739_(new CompoundTag()));
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"INSTANCE.convertTo(JsonO\u2026.writeNbt(NbtCompound()))");
        return (JsonElement)object;
    }

    public static final boolean isHeld(@NotNull ItemStack $this$isHeld, @NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)$this$isHeld, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Iterable iterable = player.m_6167_();
        Intrinsics.checkNotNullExpressionValue((Object)iterable, (String)"player.handItems");
        return CollectionsKt.contains((Iterable)iterable, (Object)$this$isHeld) && !$this$isHeld.m_41619_();
    }
}

