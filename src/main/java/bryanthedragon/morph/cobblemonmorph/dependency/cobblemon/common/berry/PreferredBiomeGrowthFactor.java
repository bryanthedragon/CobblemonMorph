/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.random.Random
 *  kotlin.ranges.IntRange
 *  kotlin.ranges.RangesKt
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Holder
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.berry;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.GrowthFactor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u001a2\u00020\u0001:\u0001\u001aB\u000f\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u00a2\u0006\u0004\b\u0018\u0010\u0019J'\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u000f\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\u000f\u0010\u000e\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u000e\u0010\rJ\u000f\u0010\u0010\u001a\u00020\u000fH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u000f\u0010\u0012\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u0012\u0010\rR\u0017\u0010\u0014\u001a\u00020\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017\u00a8\u0006\u001b"}, d2={"Lcom/cobblemon/mod/common/berry/PreferredBiomeGrowthFactor;", "Lcom/cobblemon/mod/common/api/berry/GrowthFactor;", "Lnet/minecraft/world/level/LevelReader;", "world", "Lnet/minecraft/world/level/block/state/BlockState;", "state", "Lnet/minecraft/core/BlockPos;", "pos", "", "isValid", "(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)Z", "", "maxYield", "()I", "minYield", "", "validateArguments", "()V", "yield", "Lkotlin/ranges/IntRange;", "bonusYield", "Lkotlin/ranges/IntRange;", "getBonusYield", "()Lkotlin/ranges/IntRange;", "<init>", "(Lkotlin/ranges/IntRange;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nBiomeGrowthFactor.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BiomeGrowthFactor.kt\ncom/cobblemon/mod/common/berry/PreferredBiomeGrowthFactor\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,53:1\n1747#2,3:54\n*S KotlinDebug\n*F\n+ 1 BiomeGrowthFactor.kt\ncom/cobblemon/mod/common/berry/PreferredBiomeGrowthFactor\n*L\n37#1:54,3\n*E\n"})
public final class PreferredBiomeGrowthFactor
implements GrowthFactor {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final IntRange bonusYield;
    @NotNull
    private static final ResourceLocation ID = MiscUtils.cobblemonResource("preferred_biome");

    public PreferredBiomeGrowthFactor(@NotNull IntRange bonusYield) {
        Intrinsics.checkNotNullParameter((Object)bonusYield, (String)"bonusYield");
        this.bonusYield = bonusYield;
    }

    @NotNull
    public final IntRange getBonusYield() {
        return this.bonusYield;
    }

    @Override
    public void validateArguments() {
        if (this.bonusYield.getFirst() < 0 || this.bonusYield.getLast() < 0) {
            throw new IllegalArgumentException(ID + " bonusYield must be a positive range");
        }
    }

    @Override
    public boolean isValid(@NotNull LevelReader world, @NotNull BlockState state, @NotNull BlockPos pos) {
        boolean bl;
        block4: {
            Intrinsics.checkNotNullParameter((Object)world, (String)"world");
            Intrinsics.checkNotNullParameter((Object)state, (String)"state");
            Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
            Holder biome2 = world.m_204166_(pos);
            Block block = state.m_60734_();
            Intrinsics.checkNotNull((Object)block, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock");
            BerryBlock block2 = (BerryBlock)block;
            Object object = block2.berry();
            if (object == null || (object = ((Berry)object).getPreferredBiomeTags()) == null) {
                object = CollectionsKt.emptyList();
            }
            Object biomeTags = object;
            Iterable $this$any$iv = (Iterable)biomeTags;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    TagKey it = (TagKey)element$iv;
                    boolean bl2 = false;
                    if (!biome2.m_203656_(it)) continue;
                    bl = true;
                    break block4;
                }
                bl = false;
            }
        }
        return bl;
    }

    @Override
    public int yield() {
        return RangesKt.random((IntRange)this.bonusYield, (Random)((Random)Random.Default));
    }

    @Override
    public int minYield() {
        return this.bonusYield.getFirst();
    }

    @Override
    public int maxYield() {
        return this.bonusYield.getLast();
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/berry/PreferredBiomeGrowthFactor$Companion;", "", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

