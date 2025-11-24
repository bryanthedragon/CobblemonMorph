/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005\u00a8\u0006\u0006"}, d2={"Lcom/cobblemon/mod/common/item/CobblemonItem;", "Lnet/minecraft/world/item/Item;", "Lnet/minecraft/item/Item$Settings;", "settings", "<init>", "(Lnet/minecraft/world/item/Item$Properties;)V", "common"})
public class CobblemonItem
extends Item {
    public CobblemonItem(@NotNull Item.Properties settings) {
        Intrinsics.checkNotNullParameter((Object)settings, (String)"settings");
        super(settings);
    }
}

