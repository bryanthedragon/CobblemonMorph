/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.alchemy.Potion
 *  net.minecraft.world.item.alchemy.PotionUtils
 *  org.jetbrains.annotations.ApiStatus$Internal
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.brewing.ingredient;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.brewing.ingredient.CobblemonIngredient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007H\u0016\u00a2\u0006\u0004\b\b\u0010\tR\u0017\u0010\u000b\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/brewing/ingredient/CobblemonPotionIngredient;", "Lcom/cobblemon/mod/common/brewing/ingredient/CobblemonIngredient;", "Lnet/minecraft/world/item/ItemStack;", "stack", "", "matches", "(Lnet/minecraft/world/item/ItemStack;)Z", "", "matchingStacks", "()Ljava/util/List;", "Lnet/minecraft/world/item/alchemy/Potion;", "potion", "Lnet/minecraft/world/item/alchemy/Potion;", "getPotion", "()Lnet/minecraft/world/item/alchemy/Potion;", "<init>", "(Lnet/minecraft/world/item/alchemy/Potion;)V", "common"})
@ApiStatus.Internal
public final class CobblemonPotionIngredient
implements CobblemonIngredient {
    @NotNull
    private final Potion potion;

    public CobblemonPotionIngredient(@NotNull Potion potion) {
        Intrinsics.checkNotNullParameter((Object)potion, (String)"potion");
        this.potion = potion;
    }

    @NotNull
    public final Potion getPotion() {
        return this.potion;
    }

    @Override
    public boolean matches(@NotNull ItemStack stack) {
        Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
        Potion potion = PotionUtils.m_43579_((ItemStack)stack);
        return Intrinsics.areEqual((Object)potion, (Object)this.potion);
    }

    @Override
    @NotNull
    public List<ItemStack> matchingStacks() {
        ArrayList list = new ArrayList();
        ((Collection)list).add(PotionUtils.m_43549_((ItemStack)Items.f_42589_.m_7968_(), (Potion)this.potion));
        ((Collection)list).add(PotionUtils.m_43549_((ItemStack)Items.f_42736_.m_7968_(), (Potion)this.potion));
        ((Collection)list).add(PotionUtils.m_43549_((ItemStack)Items.f_42739_.m_7968_(), (Potion)this.potion));
        return list;
    }
}

