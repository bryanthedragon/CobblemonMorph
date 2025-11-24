/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.starter;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.starter.RenderableStarterCategory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.RenderablePokemon;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000b\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\r\u001a\u00020\u0005\u0012\u0006\u0010\u000e\u001a\u00020\u0005\u0012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\u0004\b\u001f\u0010 J\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\b\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\b\u0010\u0007J\u0016\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u00c6\u0003\u00a2\u0006\u0004\b\u000b\u0010\fJ4\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\r\u001a\u00020\u00052\b\b\u0002\u0010\u000e\u001a\u00020\u00052\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u00c6\u0001\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u001a\u0010\u0014\u001a\u00020\u00132\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0010\u0010\u0017\u001a\u00020\u0016H\u00d6\u0001\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0010\u0010\u0019\u001a\u00020\u0005H\u00d6\u0001\u00a2\u0006\u0004\b\u0019\u0010\u0007R\u0017\u0010\u000e\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u001a\u001a\u0004\b\u001b\u0010\u0007R\u0017\u0010\r\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u001a\u001a\u0004\b\u001c\u0010\u0007R\u001d\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\n0\t8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u001d\u001a\u0004\b\u001e\u0010\f\u00a8\u0006!"}, d2={"Lcom/cobblemon/mod/common/config/starter/StarterCategory;", "", "Lcom/cobblemon/mod/common/config/starter/RenderableStarterCategory;", "asRenderableStarterCategory", "()Lcom/cobblemon/mod/common/config/starter/RenderableStarterCategory;", "", "component1", "()Ljava/lang/String;", "component2", "", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "component3", "()Ljava/util/List;", "name", "displayName", "pokemon", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)Lcom/cobblemon/mod/common/config/starter/StarterCategory;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "toString", "Ljava/lang/String;", "getDisplayName", "getName", "Ljava/util/List;", "getPokemon", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)V", "common"})
@SourceDebugExtension(value={"SMAP\nStarterCategory.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StarterCategory.kt\ncom/cobblemon/mod/common/config/starter/StarterCategory\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,30:1\n1549#2:31\n1620#2,3:32\n*S KotlinDebug\n*F\n+ 1 StarterCategory.kt\ncom/cobblemon/mod/common/config/starter/StarterCategory\n*L\n20#1:31\n20#1:32,3\n*E\n"})
public final class StarterCategory {
    @NotNull
    private final String name;
    @NotNull
    private final String displayName;
    @NotNull
    private final List<PokemonProperties> pokemon;

    public StarterCategory(@NotNull String name, @NotNull String displayName, @NotNull List<? extends PokemonProperties> pokemon) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)displayName, (String)"displayName");
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        this.name = name;
        this.displayName = displayName;
        this.pokemon = pokemon;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final String getDisplayName() {
        return this.displayName;
    }

    @NotNull
    public final List<PokemonProperties> getPokemon() {
        return this.pokemon;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final RenderableStarterCategory asRenderableStarterCategory() {
        Collection<RenderablePokemon> collection;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Iterable iterable = this.pokemon;
        String string = this.displayName;
        String string2 = this.name;
        boolean $i$f$map = false;
        void var3_5 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            PokemonProperties pokemonProperties = (PokemonProperties)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            collection.add(it.asRenderablePokemon());
        }
        collection = (List)destination$iv$iv;
        List list = collection;
        String string3 = string;
        String string4 = string2;
        return new RenderableStarterCategory(string4, string3, list);
    }

    @NotNull
    public final String component1() {
        return this.name;
    }

    @NotNull
    public final String component2() {
        return this.displayName;
    }

    @NotNull
    public final List<PokemonProperties> component3() {
        return this.pokemon;
    }

    @NotNull
    public final StarterCategory copy(@NotNull String name, @NotNull String displayName, @NotNull List<? extends PokemonProperties> pokemon) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)displayName, (String)"displayName");
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        return new StarterCategory(name, displayName, pokemon);
    }

    public static /* synthetic */ StarterCategory copy$default(StarterCategory starterCategory, String string, String string2, List list, int n, Object object) {
        if ((n & 1) != 0) {
            string = starterCategory.name;
        }
        if ((n & 2) != 0) {
            string2 = starterCategory.displayName;
        }
        if ((n & 4) != 0) {
            list = starterCategory.pokemon;
        }
        return starterCategory.copy(string, string2, list);
    }

    @NotNull
    public String toString() {
        return "StarterCategory(name=" + this.name + ", displayName=" + this.displayName + ", pokemon=" + this.pokemon + ")";
    }

    public int hashCode() {
        int result = this.name.hashCode();
        result = result * 31 + this.displayName.hashCode();
        result = result * 31 + ((Object)this.pokemon).hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof StarterCategory)) {
            return false;
        }
        StarterCategory starterCategory = (StarterCategory)other;
        if (!Intrinsics.areEqual((Object)this.name, (Object)starterCategory.name)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.displayName, (Object)starterCategory.displayName)) {
            return false;
        }
        return Intrinsics.areEqual(this.pokemon, starterCategory.pokemon);
    }
}

