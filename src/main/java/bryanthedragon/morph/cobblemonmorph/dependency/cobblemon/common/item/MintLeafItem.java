/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.item.Item$Properties
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.MintBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.CobblemonItem;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/item/MintLeafItem;", "Lcom/cobblemon/mod/common/item/CobblemonItem;", "Lcom/cobblemon/mod/common/block/MintBlock$MintType;", "mintType", "Lcom/cobblemon/mod/common/block/MintBlock$MintType;", "getMintType", "()Lcom/cobblemon/mod/common/block/MintBlock$MintType;", "<init>", "(Lcom/cobblemon/mod/common/block/MintBlock$MintType;)V", "common"})
public final class MintLeafItem
extends CobblemonItem {
    @NotNull
    private final MintBlock.MintType mintType;

    public MintLeafItem(@NotNull MintBlock.MintType mintType) {
        Intrinsics.checkNotNullParameter((Object)((Object)mintType), (String)"mintType");
        super(new Item.Properties());
        this.mintType = mintType;
    }

    @NotNull
    public final MintBlock.MintType getMintType() {
        return this.mintType;
    }
}

