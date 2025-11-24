/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.NoWhenBranchMatchedException
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.aspects;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.aspect.AspectProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.aspect.SingleConditionalAspectProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Gender;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.aspects.PokemonAspectsKt;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u0017\u0010\u0001\u001a\u00020\u00008\u0006\u00a2\u0006\f\n\u0004\b\u0001\u0010\u0002\u001a\u0004\b\u0003\u0010\u0004\"\u0017\u0010\u0006\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\t\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/aspect/AspectProvider;", "GENDER_ASPECT", "Lcom/cobblemon/mod/common/api/pokemon/aspect/AspectProvider;", "getGENDER_ASPECT", "()Lcom/cobblemon/mod/common/api/pokemon/aspect/AspectProvider;", "Lcom/cobblemon/mod/common/api/pokemon/aspect/SingleConditionalAspectProvider;", "SHINY_ASPECT", "Lcom/cobblemon/mod/common/api/pokemon/aspect/SingleConditionalAspectProvider;", "getSHINY_ASPECT", "()Lcom/cobblemon/mod/common/api/pokemon/aspect/SingleConditionalAspectProvider;", "common"})
public final class PokemonAspectsKt {
    @NotNull
    private static final SingleConditionalAspectProvider SHINY_ASPECT = new SingleConditionalAspectProvider(){
        @NotNull
        private final String aspect;
        {
            this.aspect = "shiny";
        }

        @NotNull
        public String getAspect() {
            return this.aspect;
        }

        public boolean meetsCondition(@NotNull Pokemon pokemon) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            return pokemon.getShiny();
        }

        public boolean meetsCondition(@NotNull PokemonProperties pokemonProperties) {
            Intrinsics.checkNotNullParameter((Object)pokemonProperties, (String)"pokemonProperties");
            return Intrinsics.areEqual((Object)pokemonProperties.getShiny(), (Object)true);
        }

        @NotNull
        public Set<String> provide(@NotNull PokemonProperties properties2) {
            return SingleConditionalAspectProvider.DefaultImpls.provide((SingleConditionalAspectProvider)this, properties2);
        }

        @NotNull
        public Set<String> provide(@NotNull Pokemon pokemon) {
            return SingleConditionalAspectProvider.DefaultImpls.provide((SingleConditionalAspectProvider)this, pokemon);
        }

        @NotNull
        public AspectProvider register() {
            return SingleConditionalAspectProvider.DefaultImpls.register(this);
        }
    };
    @NotNull
    private static final AspectProvider GENDER_ASPECT = new AspectProvider(){

        @NotNull
        public final Set<String> getAspectsForGender(@NotNull Gender gender) {
            Intrinsics.checkNotNullParameter((Object)((Object)gender), (String)"gender");
            return SetsKt.setOf((Object)(switch (GENDER_ASPECT.WhenMappings.$EnumSwitchMapping$0[gender.ordinal()]) {
                case 1 -> "male";
                case 2 -> "female";
                case 3 -> "genderless";
                default -> throw new NoWhenBranchMatchedException();
            }));
        }

        @NotNull
        public Set<String> provide(@NotNull Pokemon pokemon) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            return this.getAspectsForGender(pokemon.getGender());
        }

        @NotNull
        public Set<String> provide(@NotNull PokemonProperties properties2) {
            Object object;
            block3: {
                block2: {
                    Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
                    object = properties2.getGender();
                    if (object == null) break block2;
                    Gender it = object;
                    boolean bl = false;
                    Set<String> set2 = this.getAspectsForGender(it);
                    object = set2;
                    if (set2 != null) break block3;
                }
                object = SetsKt.emptySet();
            }
            return object;
        }

        @NotNull
        public AspectProvider register() {
            return AspectProvider.DefaultImpls.register(this);
        }
    };

    @NotNull
    public static final SingleConditionalAspectProvider getSHINY_ASPECT() {
        return SHINY_ASPECT;
    }

    @NotNull
    public static final AspectProvider getGENDER_ASPECT() {
        return GENDER_ASPECT;
    }
}

