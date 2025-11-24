/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Holder
 *  net.minecraft.world.level.LevelAccessor
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.feature;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.SeasonResolver;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonBiomeTags;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.feature.CobblemonSeason;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\"\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0096\u0002\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/pokemon/feature/TagSeasonResolver;", "Lcom/cobblemon/mod/common/api/SeasonResolver;", "Lnet/minecraft/world/level/LevelAccessor;", "world", "Lnet/minecraft/core/BlockPos;", "pos", "Lcom/cobblemon/mod/common/pokemon/feature/CobblemonSeason;", "invoke", "(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;)Lcom/cobblemon/mod/common/pokemon/feature/CobblemonSeason;", "<init>", "()V", "common"})
public final class TagSeasonResolver
implements SeasonResolver {
    @NotNull
    public static final TagSeasonResolver INSTANCE = new TagSeasonResolver();

    private TagSeasonResolver() {
    }

    @Override
    @Nullable
    public CobblemonSeason invoke(@NotNull LevelAccessor world, @NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Holder biome2 = world.m_204166_(pos);
        return biome2.m_203656_(CobblemonBiomeTags.IS_WINTER) ? CobblemonSeason.WINTER : (biome2.m_203656_(CobblemonBiomeTags.IS_SPRING) ? CobblemonSeason.SPRING : (biome2.m_203656_(CobblemonBiomeTags.IS_AUTUMN) ? CobblemonSeason.AUTUMN : (biome2.m_203656_(CobblemonBiomeTags.IS_SUMMER) ? CobblemonSeason.SUMMER : null)));
    }
}

