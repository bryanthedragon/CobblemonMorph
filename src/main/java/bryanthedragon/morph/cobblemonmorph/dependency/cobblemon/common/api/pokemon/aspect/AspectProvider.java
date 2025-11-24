/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.aspect;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u0000 \r2\u00020\u0001:\u0001\rJ\u001d\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001d\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\t\u001a\u00020\bH&\u00a2\u0006\u0004\b\u0006\u0010\nJ\u000f\u0010\u000b\u001a\u00020\u0000H\u0016\u00a2\u0006\u0004\b\u000b\u0010\f\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/aspect/AspectProvider;", "", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "properties", "", "", "provide", "(Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;)Ljava/util/Set;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Ljava/util/Set;", "register", "()Lcom/cobblemon/mod/common/api/pokemon/aspect/AspectProvider;", "Companion", "common"})
public interface AspectProvider {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.aspect.AspectProvider$Companion.$$INSTANCE;

    @NotNull
    public Set<String> provide(@NotNull Pokemon var1);

    @NotNull
    public Set<String> provide(@NotNull PokemonProperties var1);

    @NotNull
    public AspectProvider register();

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0015\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0015\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u001d\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/aspect/AspectProvider$Companion;", "", "Lcom/cobblemon/mod/common/api/pokemon/aspect/AspectProvider;", "provider", "register", "(Lcom/cobblemon/mod/common/api/pokemon/aspect/AspectProvider;)Lcom/cobblemon/mod/common/api/pokemon/aspect/AspectProvider;", "", "unregister", "(Lcom/cobblemon/mod/common/api/pokemon/aspect/AspectProvider;)V", "", "providers", "Ljava/util/List;", "getProviders", "()Ljava/util/List;", "<init>", "()V", "common"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;
        @NotNull
        private static final List<AspectProvider> providers;

        private Companion() {
        }

        @NotNull
        public final List<AspectProvider> getProviders() {
            return providers;
        }

        @NotNull
        public final AspectProvider register(@NotNull AspectProvider provider) {
            Intrinsics.checkNotNullParameter((Object)provider, (String)"provider");
            providers.add(provider);
            return provider;
        }

        public final void unregister(@NotNull AspectProvider provider) {
            Intrinsics.checkNotNullParameter((Object)provider, (String)"provider");
            providers.remove(provider);
        }

        static {
            $$INSTANCE = new Companion();
            providers = new ArrayList();
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        @NotNull
        public static AspectProvider register(@NotNull AspectProvider $this) {
            return Companion.register($this);
        }
    }
}

