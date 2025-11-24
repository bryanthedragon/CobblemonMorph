/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.SetsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonPropertyType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.HiddenAbilityProperty;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0015\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0019\u0010\b\u001a\u00020\u00022\b\u0010\u0007\u001a\u0004\u0018\u00010\u0004H\u0016\u00a2\u0006\u0004\b\b\u0010\tR \u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00040\u00038\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\u0006R\u001a\u0010\u000e\u001a\u00020\r8\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/pokemon/properties/HiddenAbilityPropertyType;", "Lcom/cobblemon/mod/common/api/properties/CustomPokemonPropertyType;", "Lcom/cobblemon/mod/common/pokemon/properties/HiddenAbilityProperty;", "", "", "examples", "()Ljava/util/Set;", "value", "fromString", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/pokemon/properties/HiddenAbilityProperty;", "keys", "Ljava/util/Set;", "getKeys", "", "needsKey", "Z", "getNeedsKey", "()Z", "<init>", "()V", "common"})
public final class HiddenAbilityPropertyType
implements CustomPokemonPropertyType<HiddenAbilityProperty> {
    @NotNull
    public static final HiddenAbilityPropertyType INSTANCE = new HiddenAbilityPropertyType();
    @NotNull
    private static final Set<String> keys;
    private static final boolean needsKey;

    private HiddenAbilityPropertyType() {
    }

    @NotNull
    public Set<String> getKeys() {
        return keys;
    }

    @Override
    public boolean getNeedsKey() {
        return needsKey;
    }

    @Override
    @NotNull
    public HiddenAbilityProperty fromString(@Nullable String value2) {
        return new HiddenAbilityProperty();
    }

    @NotNull
    public Set<String> examples() {
        return SetsKt.emptySet();
    }

    static {
        Object[] objectArray = new String[]{"hiddenability", "ha"};
        keys = SetsKt.setOf((Object[])objectArray);
        needsKey = true;
    }
}

