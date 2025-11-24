/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\t\u001a\u00020\u00048&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/evolution/PassiveEvolution;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "attemptEvolution", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "getPermanent", "()Z", "permanent", "common"})
public interface PassiveEvolution
extends Evolution {
    public boolean attemptEvolution(@NotNull Pokemon var1);

    public boolean getPermanent();

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static boolean attemptEvolution(@NotNull PassiveEvolution $this, @NotNull Pokemon pokemon) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            if (Evolution.DefaultImpls.test($this, pokemon)) {
                return Evolution.DefaultImpls.evolve($this, pokemon);
            }
            return false;
        }

        public static boolean test(@NotNull PassiveEvolution $this, @NotNull Pokemon pokemon) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            return Evolution.DefaultImpls.test($this, pokemon);
        }

        public static boolean evolve(@NotNull PassiveEvolution $this, @NotNull Pokemon pokemon) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            return Evolution.DefaultImpls.evolve($this, pokemon);
        }

        public static void forceEvolve(@NotNull PassiveEvolution $this, @NotNull Pokemon pokemon) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Evolution.DefaultImpls.forceEvolve($this, pokemon);
        }

        public static void evolutionMethod(@NotNull PassiveEvolution $this, @NotNull Pokemon pokemon) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Evolution.DefaultImpls.evolutionMethod($this, pokemon);
        }

        public static void applyTo(@NotNull PassiveEvolution $this, @NotNull Pokemon pokemon) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Evolution.DefaultImpls.applyTo($this, pokemon);
        }
    }
}

