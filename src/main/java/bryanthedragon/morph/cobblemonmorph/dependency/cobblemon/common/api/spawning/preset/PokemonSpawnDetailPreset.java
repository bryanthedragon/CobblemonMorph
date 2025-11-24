/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.ranges.IntRange
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.preset;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.PokemonSpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.preset.SpawnDetailPreset;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0007\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R$\u0010\b\u001a\u0004\u0018\u00010\u00078\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR$\u0010\u000f\u001a\u0004\u0018\u00010\u000e8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/api/spawning/preset/PokemonSpawnDetailPreset;", "Lcom/cobblemon/mod/common/api/spawning/preset/SpawnDetailPreset;", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "spawnDetail", "", "apply", "(Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;)V", "Lkotlin/ranges/IntRange;", "levelRange", "Lkotlin/ranges/IntRange;", "getLevelRange", "()Lkotlin/ranges/IntRange;", "setLevelRange", "(Lkotlin/ranges/IntRange;)V", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "pokemon", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "getPokemon", "()Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "setPokemon", "(Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;)V", "<init>", "()V", "Companion", "common"})
public final class PokemonSpawnDetailPreset
extends SpawnDetailPreset {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @Nullable
    private PokemonProperties pokemon;
    @Nullable
    private IntRange levelRange;
    @NotNull
    public static final String NAME = "pokemon";

    @Nullable
    public final PokemonProperties getPokemon() {
        return this.pokemon;
    }

    public final void setPokemon(@Nullable PokemonProperties pokemonProperties) {
        this.pokemon = pokemonProperties;
    }

    @Nullable
    public final IntRange getLevelRange() {
        return this.levelRange;
    }

    public final void setLevelRange(@Nullable IntRange intRange) {
        this.levelRange = intRange;
    }

    @Override
    public void apply(@NotNull SpawnDetail spawnDetail) {
        Intrinsics.checkNotNullParameter((Object)spawnDetail, (String)"spawnDetail");
        super.apply(spawnDetail);
        if (spawnDetail instanceof PokemonSpawnDetail) {
            PokemonProperties pokemon = this.pokemon;
            if (pokemon != null) {
                ((PokemonSpawnDetail)spawnDetail).setPokemon(PokemonProperties.Companion.parse$default(PokemonProperties.Companion, ((PokemonSpawnDetail)spawnDetail).getPokemon().getOriginalString() + " " + pokemon.getOriginalString(), null, null, 6, null));
            }
            if (this.levelRange != null) {
                ((PokemonSpawnDetail)spawnDetail).setLevelRange(this.levelRange);
            }
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/api/spawning/preset/PokemonSpawnDetailPreset$Companion;", "", "", "NAME", "Ljava/lang/String;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

