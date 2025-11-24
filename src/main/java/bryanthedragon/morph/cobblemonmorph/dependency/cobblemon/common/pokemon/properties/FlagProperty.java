/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonProperty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\f\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\r\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0010\u001a\u00020\n\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u000f\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u000b\u0010\fR\u0017\u0010\r\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\tR\u0017\u0010\u0010\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/pokemon/properties/FlagProperty;", "Lcom/cobblemon/mod/common/api/properties/CustomPokemonProperty;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "apply", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "", "asString", "()Ljava/lang/String;", "", "matches", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "key", "Ljava/lang/String;", "getKey", "remove", "Z", "getRemove", "()Z", "<init>", "(Ljava/lang/String;Z)V", "common"})
@SourceDebugExtension(value={"SMAP\nFlagProperty.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FlagProperty.kt\ncom/cobblemon/mod/common/pokemon/properties/FlagProperty\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,33:1\n1747#2,3:34\n*S KotlinDebug\n*F\n+ 1 FlagProperty.kt\ncom/cobblemon/mod/common/pokemon/properties/FlagProperty\n*L\n32#1:34,3\n*E\n"})
public final class FlagProperty
implements CustomPokemonProperty {
    @NotNull
    private final String key;
    private final boolean remove;

    public FlagProperty(@NotNull String key, boolean remove2) {
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        this.key = key;
        this.remove = remove2;
    }

    public /* synthetic */ FlagProperty(String string, boolean bl, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            bl = false;
        }
        this(string, bl);
    }

    @NotNull
    public final String getKey() {
        return this.key;
    }

    public final boolean getRemove() {
        return this.remove;
    }

    @Override
    @NotNull
    public String asString() {
        return this.key;
    }

    @Override
    public void apply(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        if (this.remove) {
            pokemon.getCustomProperties().removeIf(arg_0 -> FlagProperty.apply$lambda$0((Function1)new Function1<CustomPokemonProperty, Boolean>(this){
                final /* synthetic */ FlagProperty this$0;
                {
                    this.this$0 = $receiver;
                    super(1);
                }

                @NotNull
                public final Boolean invoke(@NotNull CustomPokemonProperty it) {
                    Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                    return it instanceof FlagProperty && Intrinsics.areEqual((Object)((FlagProperty)it).getKey(), (Object)this.this$0.getKey());
                }
            }, arg_0));
        } else {
            pokemon.getCustomProperties().add(this);
        }
    }

    @Override
    public boolean matches(@NotNull Pokemon pokemon) {
        boolean bl;
        block3: {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Iterable $this$any$iv = pokemon.getCustomProperties();
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    CustomPokemonProperty it = (CustomPokemonProperty)element$iv;
                    boolean bl2 = false;
                    if (!(it instanceof FlagProperty && Intrinsics.areEqual((Object)((FlagProperty)it).key, (Object)this.key))) continue;
                    bl = true;
                    break block3;
                }
                bl = false;
            }
        }
        return bl;
    }

    @Override
    public void apply(@NotNull PokemonEntity pokemonEntity) {
        CustomPokemonProperty.DefaultImpls.apply(this, pokemonEntity);
    }

    @Override
    public boolean matches(@NotNull PokemonEntity pokemonEntity) {
        return CustomPokemonProperty.DefaultImpls.matches(this, pokemonEntity);
    }

    private static final boolean apply$lambda$0(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }
}

