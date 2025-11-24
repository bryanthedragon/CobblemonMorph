/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.ApiStatus$Internal
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.brewing.ingredient;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.brewing.ingredient.CobblemonIngredient;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007H\u0016\u00a2\u0006\u0004\b\b\u0010\tR\u0017\u0010\u000b\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/brewing/ingredient/CobblemonItemIngredient;", "Lcom/cobblemon/mod/common/brewing/ingredient/CobblemonIngredient;", "Lnet/minecraft/world/item/ItemStack;", "stack", "", "matches", "(Lnet/minecraft/world/item/ItemStack;)Z", "", "matchingStacks", "()Ljava/util/List;", "Lnet/minecraft/world/item/Item;", "item", "Lnet/minecraft/world/item/Item;", "getItem", "()Lnet/minecraft/world/item/Item;", "<init>", "(Lnet/minecraft/world/item/Item;)V", "common"})
@ApiStatus.Internal
public final class CobblemonItemIngredient
implements CobblemonIngredient {
    @NotNull
    private final Item item;

    public CobblemonItemIngredient(@NotNull Item item) {
        Intrinsics.checkNotNullParameter((Object)item, (String)"item");
        this.item = item;
    }

    @NotNull
    public final Item getItem() {
        return this.item;
    }

    @Override
    public boolean matches(@NotNull ItemStack stack) {
        Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
        return stack.m_150930_(this.item);
    }

    @Override
    @NotNull
    public List<ItemStack> matchingStacks() {
        return CollectionsKt.listOf((Object)this.item.m_7968_());
    }
}

