/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.advancements.critereon.NbtPredicate
 *  net.minecraft.world.item.Item
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.predicate;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\b\b\u0002\u0010\n\u001a\u00020\u0006\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0007\u001a\u00020\u0006H\u00c6\u0003\u00a2\u0006\u0004\b\u0007\u0010\bJ*\u0010\u000b\u001a\u00020\u00002\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\b\b\u0002\u0010\n\u001a\u00020\u0006H\u00c6\u0001\u00a2\u0006\u0004\b\u000b\u0010\fJ\u001a\u0010\u000f\u001a\u00020\u000e2\b\u0010\r\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0010\u0010\u0012\u001a\u00020\u0011H\u00d6\u0001\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0010\u0010\u0015\u001a\u00020\u0014H\u00d6\u0001\u00a2\u0006\u0004\b\u0015\u0010\u0016R\u001d\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0017\u001a\u0004\b\u0018\u0010\u0005R\u0017\u0010\n\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u0019\u001a\u0004\b\u001a\u0010\b\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/predicate/NbtItemPredicate;", "", "Lcom/cobblemon/mod/common/api/conditional/RegistryLikeCondition;", "Lnet/minecraft/world/item/Item;", "component1", "()Lcom/cobblemon/mod/common/api/conditional/RegistryLikeCondition;", "Lnet/minecraft/advancements/critereon/NbtPredicate;", "component2", "()Lnet/minecraft/advancements/critereon/NbtPredicate;", "item", "nbt", "copy", "(Lcom/cobblemon/mod/common/api/conditional/RegistryLikeCondition;Lnet/minecraft/advancements/critereon/NbtPredicate;)Lcom/cobblemon/mod/common/pokemon/evolution/predicate/NbtItemPredicate;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/api/conditional/RegistryLikeCondition;", "getItem", "Lnet/minecraft/advancements/critereon/NbtPredicate;", "getNbt", "<init>", "(Lcom/cobblemon/mod/common/api/conditional/RegistryLikeCondition;Lnet/minecraft/advancements/critereon/NbtPredicate;)V", "common"})
public final class NbtItemPredicate {
    @NotNull
    private final RegistryLikeCondition<Item> item;
    @NotNull
    private final NbtPredicate nbt;

    public NbtItemPredicate(@NotNull RegistryLikeCondition<Item> item, @NotNull NbtPredicate nbt) {
        Intrinsics.checkNotNullParameter(item, (String)"item");
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        this.item = item;
        this.nbt = nbt;
    }

    public /* synthetic */ NbtItemPredicate(RegistryLikeCondition registryLikeCondition, NbtPredicate nbtPredicate, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            NbtPredicate nbtPredicate2 = NbtPredicate.f_57471_;
            Intrinsics.checkNotNullExpressionValue((Object)nbtPredicate2, (String)"ANY");
            nbtPredicate = nbtPredicate2;
        }
        this(registryLikeCondition, nbtPredicate);
    }

    @NotNull
    public final RegistryLikeCondition<Item> getItem() {
        return this.item;
    }

    @NotNull
    public final NbtPredicate getNbt() {
        return this.nbt;
    }

    @NotNull
    public final RegistryLikeCondition<Item> component1() {
        return this.item;
    }

    @NotNull
    public final NbtPredicate component2() {
        return this.nbt;
    }

    @NotNull
    public final NbtItemPredicate copy(@NotNull RegistryLikeCondition<Item> item, @NotNull NbtPredicate nbt) {
        Intrinsics.checkNotNullParameter(item, (String)"item");
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        return new NbtItemPredicate(item, nbt);
    }

    public static /* synthetic */ NbtItemPredicate copy$default(NbtItemPredicate nbtItemPredicate, RegistryLikeCondition registryLikeCondition, NbtPredicate nbtPredicate, int n, Object object) {
        if ((n & 1) != 0) {
            registryLikeCondition = nbtItemPredicate.item;
        }
        if ((n & 2) != 0) {
            nbtPredicate = nbtItemPredicate.nbt;
        }
        return nbtItemPredicate.copy(registryLikeCondition, nbtPredicate);
    }

    @NotNull
    public String toString() {
        return "NbtItemPredicate(item=" + this.item + ", nbt=" + this.nbt + ")";
    }

    public int hashCode() {
        int result = this.item.hashCode();
        result = result * 31 + this.nbt.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof NbtItemPredicate)) {
            return false;
        }
        NbtItemPredicate nbtItemPredicate = (NbtItemPredicate)other;
        if (!Intrinsics.areEqual(this.item, nbtItemPredicate.item)) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.nbt, (Object)nbtItemPredicate.nbt);
    }
}

