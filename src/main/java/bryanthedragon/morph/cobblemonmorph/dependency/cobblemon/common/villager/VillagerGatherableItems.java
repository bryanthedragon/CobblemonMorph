/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.SetsKt
 *  net.minecraft.world.item.Item
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.villager;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/villager/VillagerGatherableItems;", "", "", "Lnet/minecraft/world/item/Item;", "villagerGatherableItems", "Ljava/util/Set;", "getVillagerGatherableItems", "()Ljava/util/Set;", "<init>", "()V", "common"})
public final class VillagerGatherableItems {
    @NotNull
    public static final VillagerGatherableItems INSTANCE = new VillagerGatherableItems();
    @NotNull
    private static final Set<Item> villagerGatherableItems;

    private VillagerGatherableItems() {
    }

    @NotNull
    public final Set<Item> getVillagerGatherableItems() {
        return villagerGatherableItems;
    }

    static {
        Object[] objectArray = new Item[]{CobblemonItems.BLUE_MINT_SEEDS, CobblemonItems.CYAN_MINT_SEEDS, CobblemonItems.GREEN_MINT_SEEDS, CobblemonItems.PINK_MINT_SEEDS, CobblemonItems.RED_MINT_SEEDS, CobblemonItems.REVIVAL_HERB, CobblemonItems.WHITE_MINT_SEEDS, CobblemonItems.VIVICHOKE_SEEDS};
        villagerGatherableItems = SetsKt.setOf((Object[])objectArray);
    }
}

