/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  juuxel.adorn.block.variant.BlockVariant
 *  juuxel.adorn.block.variant.BlockVariant$Wood
 *  juuxel.adorn.block.variant.BlockVariantSet
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  org.jetbrains.annotations.ApiStatus$Internal
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.integration.adorn;

import java.util.List;
import juuxel.adorn.block.variant.BlockVariant;
import juuxel.adorn.block.variant.BlockVariantSet;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c7\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR \u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/integration/adorn/AdornCompatibility;", "Ljuuxel/adorn/block/variant/BlockVariantSet;", "", "Ljuuxel/adorn/block/variant/BlockVariant;", "woodVariants", "Ljava/util/List;", "getWoodVariants", "()Ljava/util/List;", "<init>", "()V", "common"})
@ApiStatus.Internal
public final class AdornCompatibility
implements BlockVariantSet {
    @NotNull
    public static final AdornCompatibility INSTANCE = new AdornCompatibility();
    @NotNull
    private static final List<BlockVariant> woodVariants = CollectionsKt.listOf((Object)new BlockVariant.Wood("cobblemon/apricorn"));

    private AdornCompatibility() {
    }

    @NotNull
    public List<BlockVariant> getWoodVariants() {
        return woodVariants;
    }
}

