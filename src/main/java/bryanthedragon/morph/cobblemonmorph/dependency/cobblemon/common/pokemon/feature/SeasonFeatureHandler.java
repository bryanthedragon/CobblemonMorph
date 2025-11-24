/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.LevelAccessor
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.feature;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.StringSpeciesFeature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.feature.CobblemonSeason;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004\u00a2\u0006\u0004\b\u0007\u0010\bJ%\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0004\b\u0007\u0010\r\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/pokemon/feature/SeasonFeatureHandler;", "", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lcom/cobblemon/mod/common/pokemon/feature/CobblemonSeason;", "season", "", "updateSeason", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/pokemon/feature/CobblemonSeason;)V", "Lnet/minecraft/world/level/LevelAccessor;", "world", "Lnet/minecraft/core/BlockPos;", "pos", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;)V", "<init>", "()V", "common"})
public final class SeasonFeatureHandler {
    @NotNull
    public static final SeasonFeatureHandler INSTANCE = new SeasonFeatureHandler();

    private SeasonFeatureHandler() {
    }

    public final void updateSeason(@NotNull Pokemon pokemon, @NotNull LevelAccessor world, @NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        this.updateSeason(pokemon, Cobblemon.INSTANCE.getSeasonResolver().invoke(world, pos));
    }

    public final void updateSeason(@NotNull Pokemon pokemon, @Nullable CobblemonSeason season) {
        String newSeason;
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        StringSpeciesFeature stringSpeciesFeature = (StringSpeciesFeature)pokemon.getFeature("season");
        if (stringSpeciesFeature == null) {
            return;
        }
        StringSpeciesFeature feature = stringSpeciesFeature;
        String currentSeason = feature.getValue();
        Object object = season;
        if (object != null && (object = object.name()) != null) {
            String string = ((String)object).toLowerCase(Locale.ROOT);
            v3 = string;
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
        } else {
            v3 = newSeason = null;
        }
        if (!Intrinsics.areEqual((Object)currentSeason, (Object)newSeason) && newSeason != null) {
            feature.setValue(newSeason);
            pokemon.updateAspects();
            pokemon.markFeatureDirty(feature);
        }
    }
}

