/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.sherds;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\b\u001a\u00020\u0002\u0012\u0006\u0010\t\u001a\u00020\u0005\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J$\u0010\n\u001a\u00020\u00002\b\b\u0002\u0010\b\u001a\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\u0005H\u00c6\u0001\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001a\u0010\u000e\u001a\u00020\r2\b\u0010\f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0010\u0010\u0011\u001a\u00020\u0010H\u00d6\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0010\u0010\u0014\u001a\u00020\u0013H\u00d6\u0001\u00a2\u0006\u0004\b\u0014\u0010\u0015R\u0017\u0010\t\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0016\u001a\u0004\b\u0017\u0010\u0007R\u0017\u0010\b\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\u0018\u001a\u0004\b\u0019\u0010\u0004\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/sherds/CobblemonSherd;", "", "Lnet/minecraft/resources/ResourceLocation;", "component1", "()Lnet/minecraft/resources/ResourceLocation;", "Lnet/minecraft/world/item/Item;", "component2", "()Lnet/minecraft/world/item/Item;", "patternId", "item", "copy", "(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/world/item/Item;)Lcom/cobblemon/mod/common/sherds/CobblemonSherd;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/world/item/Item;", "getItem", "Lnet/minecraft/resources/ResourceLocation;", "getPatternId", "<init>", "(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/world/item/Item;)V", "common"})
public final class CobblemonSherd {
    @NotNull
    private final ResourceLocation patternId;
    @NotNull
    private final Item item;

    public CobblemonSherd(@NotNull ResourceLocation patternId, @NotNull Item item) {
        Intrinsics.checkNotNullParameter((Object)patternId, (String)"patternId");
        Intrinsics.checkNotNullParameter((Object)item, (String)"item");
        this.patternId = patternId;
        this.item = item;
    }

    @NotNull
    public final ResourceLocation getPatternId() {
        return this.patternId;
    }

    @NotNull
    public final Item getItem() {
        return this.item;
    }

    @NotNull
    public final ResourceLocation component1() {
        return this.patternId;
    }

    @NotNull
    public final Item component2() {
        return this.item;
    }

    @NotNull
    public final CobblemonSherd copy(@NotNull ResourceLocation patternId, @NotNull Item item) {
        Intrinsics.checkNotNullParameter((Object)patternId, (String)"patternId");
        Intrinsics.checkNotNullParameter((Object)item, (String)"item");
        return new CobblemonSherd(patternId, item);
    }

    public static /* synthetic */ CobblemonSherd copy$default(CobblemonSherd cobblemonSherd, ResourceLocation resourceLocation, Item item, int n, Object object) {
        if ((n & 1) != 0) {
            resourceLocation = cobblemonSherd.patternId;
        }
        if ((n & 2) != 0) {
            item = cobblemonSherd.item;
        }
        return cobblemonSherd.copy(resourceLocation, item);
    }

    @NotNull
    public String toString() {
        return "CobblemonSherd(patternId=" + this.patternId + ", item=" + this.item + ")";
    }

    public int hashCode() {
        int result = this.patternId.hashCode();
        result = result * 31 + this.item.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CobblemonSherd)) {
            return false;
        }
        CobblemonSherd cobblemonSherd = (CobblemonSherd)other;
        if (!Intrinsics.areEqual((Object)this.patternId, (Object)cobblemonSherd.patternId)) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.item, (Object)cobblemonSherd.item);
    }
}

