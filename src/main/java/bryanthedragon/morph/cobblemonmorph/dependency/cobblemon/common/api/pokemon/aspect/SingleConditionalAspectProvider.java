/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.aspect;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.aspect.AspectProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.FlagSpeciesFeature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\b\bf\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007H&\u00a2\u0006\u0004\b\u0005\u0010\tJ\u001d\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\n\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ\u001d\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\r\u0010\u000fR\u0014\u0010\u0012\u001a\u00020\f8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/aspect/SingleConditionalAspectProvider;", "Lcom/cobblemon/mod/common/api/pokemon/aspect/AspectProvider;", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "pokemonProperties", "", "meetsCondition", "(Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;)Z", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "properties", "", "", "provide", "(Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;)Ljava/util/Set;", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Ljava/util/Set;", "getAspect", "()Ljava/lang/String;", "aspect", "Companion", "common"})
public interface SingleConditionalAspectProvider
extends AspectProvider {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.aspect.SingleConditionalAspectProvider$Companion.$$INSTANCE;

    @NotNull
    public String getAspect();

    public boolean meetsCondition(@NotNull Pokemon var1);

    public boolean meetsCondition(@NotNull PokemonProperties var1);

    @Override
    @NotNull
    public Set<String> provide(@NotNull PokemonProperties var1);

    @Override
    @NotNull
    public Set<String> provide(@NotNull Pokemon var1);

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/aspect/SingleConditionalAspectProvider$Companion;", "", "", "name", "Lcom/cobblemon/mod/common/api/pokemon/aspect/SingleConditionalAspectProvider;", "getForFeature", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/pokemon/aspect/SingleConditionalAspectProvider;", "<init>", "()V", "common"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;

        private Companion() {
        }

        @NotNull
        public final SingleConditionalAspectProvider getForFeature(@NotNull String name) {
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            return new SingleConditionalAspectProvider(name){
                @NotNull
                private final String aspect;
                final /* synthetic */ String $name;
                {
                    this.$name = $name;
                    this.aspect = $name;
                }

                @NotNull
                public String getAspect() {
                    return this.aspect;
                }

                public boolean meetsCondition(@NotNull Pokemon pokemon) {
                    Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
                    FlagSpeciesFeature flagSpeciesFeature = (FlagSpeciesFeature)pokemon.getFeature(this.$name);
                    return flagSpeciesFeature != null ? flagSpeciesFeature.getEnabled() : false;
                }

                /*
                 * WARNING - void declaration
                 */
                public boolean meetsCondition(@NotNull PokemonProperties pokemonProperties) {
                    boolean bl;
                    block4: {
                        void $this$any$iv;
                        void $this$filterIsInstanceTo$iv$iv;
                        Intrinsics.checkNotNullParameter((Object)pokemonProperties, (String)"pokemonProperties");
                        Iterable $this$filterIsInstance$iv = pokemonProperties.getCustomProperties();
                        boolean $i$f$filterIsInstance = false;
                        Iterable iterable = $this$filterIsInstance$iv;
                        Collection destination$iv$iv = new ArrayList<E>();
                        boolean $i$f$filterIsInstanceTo = false;
                        for (T element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                            if (!(element$iv$iv instanceof FlagSpeciesFeature)) continue;
                            destination$iv$iv.add(element$iv$iv);
                        }
                        $this$filterIsInstance$iv = (List)destination$iv$iv;
                        String string = this.$name;
                        boolean $i$f$any = false;
                        if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                            bl = false;
                        } else {
                            for (T element$iv : $this$any$iv) {
                                FlagSpeciesFeature it = (FlagSpeciesFeature)element$iv;
                                boolean bl2 = false;
                                if (!(Intrinsics.areEqual((Object)it.getName(), (Object)string) && it.getEnabled())) continue;
                                bl = true;
                                break block4;
                            }
                            bl = false;
                        }
                    }
                    return bl;
                }

                @NotNull
                public Set<String> provide(@NotNull PokemonProperties properties2) {
                    return DefaultImpls.provide((SingleConditionalAspectProvider)this, properties2);
                }

                @NotNull
                public Set<String> provide(@NotNull Pokemon pokemon) {
                    return DefaultImpls.provide((SingleConditionalAspectProvider)this, pokemon);
                }

                @NotNull
                public AspectProvider register() {
                    return DefaultImpls.register(this);
                }
            };
        }

        static {
            $$INSTANCE = new Companion();
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        @NotNull
        public static Set<String> provide(@NotNull SingleConditionalAspectProvider $this, @NotNull PokemonProperties properties2) {
            Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
            return $this.meetsCondition(properties2) ? SetsKt.setOf((Object)$this.getAspect()) : SetsKt.emptySet();
        }

        @NotNull
        public static Set<String> provide(@NotNull SingleConditionalAspectProvider $this, @NotNull Pokemon pokemon) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            return $this.meetsCondition(pokemon) ? SetsKt.setOf((Object)$this.getAspect()) : SetsKt.emptySet();
        }

        @NotNull
        public static AspectProvider register(@NotNull SingleConditionalAspectProvider $this) {
            return AspectProvider.DefaultImpls.register($this);
        }
    }
}

