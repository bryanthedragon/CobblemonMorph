/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.registry.ItemTagCondition;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\f\b\u0086\b\u0018\u00002\u00020\u0001B3\u0012\b\b\u0002\u0010\f\u001a\u00020\u0002\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0004\b!\u0010\"J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0012\u0010\t\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0004\b\t\u0010\nJ\u0012\u0010\u000b\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u000b\u0010\u0007J>\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\f\u001a\u00020\u00022\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u001a\u0010\u0014\u001a\u00020\u00132\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0010\u0010\u0016\u001a\u00020\u0002H\u00d6\u0001\u00a2\u0006\u0004\b\u0016\u0010\u0004J\u0010\u0010\u0018\u001a\u00020\u0017H\u00d6\u0001\u00a2\u0006\u0004\b\u0018\u0010\u0019R\u0017\u0010\f\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\u001a\u001a\u0004\b\u001b\u0010\u0004R\u0019\u0010\r\u001a\u0004\u0018\u00010\u00058\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u001c\u001a\u0004\b\u001d\u0010\u0007R\u0019\u0010\u000f\u001a\u0004\u0018\u00010\u00058\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u001c\u001a\u0004\b\u001e\u0010\u0007R\u0019\u0010\u000e\u001a\u0004\u0018\u00010\b8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u001f\u001a\u0004\b \u0010\n\u00a8\u0006#"}, d2={"Lcom/cobblemon/mod/common/api/fossil/NaturalMaterial;", "", "", "component1", "()I", "Lnet/minecraft/resources/ResourceLocation;", "component2", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/registry/ItemTagCondition;", "component3", "()Lcom/cobblemon/mod/common/registry/ItemTagCondition;", "component4", "content", "item", "tag", "returnItem", "copy", "(ILnet/minecraft/resources/ResourceLocation;Lcom/cobblemon/mod/common/registry/ItemTagCondition;Lnet/minecraft/resources/ResourceLocation;)Lcom/cobblemon/mod/common/api/fossil/NaturalMaterial;", "other", "", "equals", "(Ljava/lang/Object;)Z", "hashCode", "", "toString", "()Ljava/lang/String;", "I", "getContent", "Lnet/minecraft/resources/ResourceLocation;", "getItem", "getReturnItem", "Lcom/cobblemon/mod/common/registry/ItemTagCondition;", "getTag", "<init>", "(ILnet/minecraft/resources/ResourceLocation;Lcom/cobblemon/mod/common/registry/ItemTagCondition;Lnet/minecraft/resources/ResourceLocation;)V", "common"})
public final class NaturalMaterial {
    private final int content;
    @Nullable
    private final ResourceLocation item;
    @Nullable
    private final ItemTagCondition tag;
    @Nullable
    private final ResourceLocation returnItem;

    public NaturalMaterial(int content, @Nullable ResourceLocation item, @Nullable ItemTagCondition tag, @Nullable ResourceLocation returnItem) {
        this.content = content;
        this.item = item;
        this.tag = tag;
        this.returnItem = returnItem;
    }

    public /* synthetic */ NaturalMaterial(int n, ResourceLocation resourceLocation, ItemTagCondition itemTagCondition, ResourceLocation resourceLocation2, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 1) != 0) {
            n = 0;
        }
        if ((n2 & 4) != 0) {
            itemTagCondition = null;
        }
        if ((n2 & 8) != 0) {
            resourceLocation2 = null;
        }
        this(n, resourceLocation, itemTagCondition, resourceLocation2);
    }

    public final int getContent() {
        return this.content;
    }

    @Nullable
    public final ResourceLocation getItem() {
        return this.item;
    }

    @Nullable
    public final ItemTagCondition getTag() {
        return this.tag;
    }

    @Nullable
    public final ResourceLocation getReturnItem() {
        return this.returnItem;
    }

    public final int component1() {
        return this.content;
    }

    @Nullable
    public final ResourceLocation component2() {
        return this.item;
    }

    @Nullable
    public final ItemTagCondition component3() {
        return this.tag;
    }

    @Nullable
    public final ResourceLocation component4() {
        return this.returnItem;
    }

    @NotNull
    public final NaturalMaterial copy(int content, @Nullable ResourceLocation item, @Nullable ItemTagCondition tag, @Nullable ResourceLocation returnItem) {
        return new NaturalMaterial(content, item, tag, returnItem);
    }

    public static /* synthetic */ NaturalMaterial copy$default(NaturalMaterial naturalMaterial, int n, ResourceLocation resourceLocation, ItemTagCondition itemTagCondition, ResourceLocation resourceLocation2, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = naturalMaterial.content;
        }
        if ((n2 & 2) != 0) {
            resourceLocation = naturalMaterial.item;
        }
        if ((n2 & 4) != 0) {
            itemTagCondition = naturalMaterial.tag;
        }
        if ((n2 & 8) != 0) {
            resourceLocation2 = naturalMaterial.returnItem;
        }
        return naturalMaterial.copy(n, resourceLocation, itemTagCondition, resourceLocation2);
    }

    @NotNull
    public String toString() {
        return "NaturalMaterial(content=" + this.content + ", item=" + this.item + ", tag=" + this.tag + ", returnItem=" + this.returnItem + ")";
    }

    public int hashCode() {
        int result = Integer.hashCode(this.content);
        result = result * 31 + (this.item == null ? 0 : this.item.hashCode());
        result = result * 31 + (this.tag == null ? 0 : this.tag.hashCode());
        result = result * 31 + (this.returnItem == null ? 0 : this.returnItem.hashCode());
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof NaturalMaterial)) {
            return false;
        }
        NaturalMaterial naturalMaterial = (NaturalMaterial)other;
        if (this.content != naturalMaterial.content) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.item, (Object)naturalMaterial.item)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.tag, (Object)naturalMaterial.tag)) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.returnItem, (Object)naturalMaterial.returnItem);
    }
}

