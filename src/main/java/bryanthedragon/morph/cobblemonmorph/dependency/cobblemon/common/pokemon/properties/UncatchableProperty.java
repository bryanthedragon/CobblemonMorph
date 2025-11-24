/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonPropertyType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.FlagProperty;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\r\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0015\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u001b\u0010\n\u001a\u0004\u0018\u00010\u00022\b\u0010\t\u001a\u0004\u0018\u00010\u0006H\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0015\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\u000f\u0010\u0010J\r\u0010\u0011\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0004R \u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00060\u00058\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\bR\u001a\u0010\u0015\u001a\u00020\u000e8\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018\u00a8\u0006\u001b"}, d2={"Lcom/cobblemon/mod/common/pokemon/properties/UncatchableProperty;", "Lcom/cobblemon/mod/common/api/properties/CustomPokemonPropertyType;", "Lcom/cobblemon/mod/common/pokemon/properties/FlagProperty;", "catchable", "()Lcom/cobblemon/mod/common/pokemon/properties/FlagProperty;", "", "", "examples", "()Ljava/util/Set;", "value", "fromString", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/pokemon/properties/FlagProperty;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "pokemonEntity", "", "isCatchable", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)Z", "uncatchable", "keys", "Ljava/util/Set;", "getKeys", "needsKey", "Z", "getNeedsKey", "()Z", "<init>", "()V", "common"})
public final class UncatchableProperty
implements CustomPokemonPropertyType<FlagProperty> {
    @NotNull
    public static final UncatchableProperty INSTANCE = new UncatchableProperty();
    @NotNull
    private static final Set<String> keys = SetsKt.setOf((Object)"uncatchable");
    private static final boolean needsKey = true;

    private UncatchableProperty() {
    }

    @NotNull
    public Set<String> getKeys() {
        return keys;
    }

    @Override
    public boolean getNeedsKey() {
        return needsKey;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    @Nullable
    public FlagProperty fromString(@Nullable String value2) {
        FlagProperty flagProperty;
        Object[] objectArray;
        block3: {
            block2: {
                if (value2 == null) break block2;
                objectArray = new String[]{"true", "yes"};
                List list = CollectionsKt.listOf((Object[])objectArray);
                String string = value2.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                if (!list.contains(string)) break block3;
            }
            flagProperty = this.uncatchable();
            return flagProperty;
        }
        objectArray = new String[]{"false", "no"};
        List list = CollectionsKt.listOf((Object[])objectArray);
        String string = value2.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
        if (!list.contains(string)) return null;
        flagProperty = this.catchable();
        return flagProperty;
    }

    @NotNull
    public final FlagProperty catchable() {
        return new FlagProperty((String)CollectionsKt.first((Iterable)this.getKeys()), true);
    }

    @NotNull
    public final FlagProperty uncatchable() {
        return new FlagProperty((String)CollectionsKt.first((Iterable)this.getKeys()), false);
    }

    public final boolean isCatchable(@NotNull PokemonEntity pokemonEntity) {
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        return !PokemonProperties.Companion.parse$default(PokemonProperties.Companion, (String)CollectionsKt.first((Iterable)this.getKeys()), null, null, 6, null).matches(pokemonEntity);
    }

    @NotNull
    public Set<String> examples() {
        Object[] objectArray = new String[]{"yes", "no"};
        return SetsKt.setOf((Object[])objectArray);
    }
}

