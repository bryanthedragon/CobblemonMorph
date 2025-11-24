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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\bf\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u0003J\u001f\u0010\b\u001a\u00020\u00072\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00028\u0000H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u001f\u0010\n\u001a\u00020\u00072\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00028\u0000H&\u00a2\u0006\u0004\b\n\u0010\tR\u0014\u0010\r\u001a\u00028\u00018&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/evolution/ContextEvolution;", "RC", "TC", "Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "context", "", "attemptEvolution", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Ljava/lang/Object;)Z", "testContext", "getRequiredContext", "()Ljava/lang/Object;", "requiredContext", "common"})
public interface ContextEvolution<RC, TC>
extends Evolution {
    public TC getRequiredContext();

    public boolean attemptEvolution(@NotNull Pokemon var1, RC var2);

    public boolean testContext(@NotNull Pokemon var1, RC var2);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static <RC, TC> boolean attemptEvolution(@NotNull ContextEvolution<RC, TC> $this, @NotNull Pokemon pokemon, RC context) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            if ($this.testContext(pokemon, context) && Evolution.DefaultImpls.test($this, pokemon)) {
                return Evolution.DefaultImpls.evolve($this, pokemon);
            }
            return false;
        }

        public static <RC, TC> boolean test(@NotNull ContextEvolution<RC, TC> $this, @NotNull Pokemon pokemon) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            return Evolution.DefaultImpls.test($this, pokemon);
        }

        public static <RC, TC> boolean evolve(@NotNull ContextEvolution<RC, TC> $this, @NotNull Pokemon pokemon) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            return Evolution.DefaultImpls.evolve($this, pokemon);
        }

        public static <RC, TC> void forceEvolve(@NotNull ContextEvolution<RC, TC> $this, @NotNull Pokemon pokemon) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Evolution.DefaultImpls.forceEvolve($this, pokemon);
        }

        public static <RC, TC> void evolutionMethod(@NotNull ContextEvolution<RC, TC> $this, @NotNull Pokemon pokemon) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Evolution.DefaultImpls.evolutionMethod($this, pokemon);
        }

        public static <RC, TC> void applyTo(@NotNull ContextEvolution<RC, TC> $this, @NotNull Pokemon pokemon) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Evolution.DefaultImpls.applyTo($this, pokemon);
        }
    }
}

