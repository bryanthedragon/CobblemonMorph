/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.ApiStatus$Internal
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.brewing.ingredient;

import java.util.List;
import kotlin.Metadata;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bw\u0018\u00002\u00020\u0001J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007H&\u00a2\u0006\u0004\b\b\u0010\t\u0082\u0001\u0002\n\u000b\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/brewing/ingredient/CobblemonIngredient;", "", "Lnet/minecraft/world/item/ItemStack;", "stack", "", "matches", "(Lnet/minecraft/world/item/ItemStack;)Z", "", "matchingStacks", "()Ljava/util/List;", "Lcom/cobblemon/mod/common/brewing/ingredient/CobblemonItemIngredient;", "Lcom/cobblemon/mod/common/brewing/ingredient/CobblemonPotionIngredient;", "common"})
@ApiStatus.Internal
public interface CobblemonIngredient {
    public boolean matches(@NotNull ItemStack var1);

    @NotNull
    public List<ItemStack> matchingStacks();
}

