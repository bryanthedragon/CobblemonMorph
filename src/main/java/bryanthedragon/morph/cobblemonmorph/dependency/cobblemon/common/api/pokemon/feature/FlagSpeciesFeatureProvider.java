/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.collections.ArraysKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.random.Random
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.aspect.AspectProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.FlagSpeciesFeature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SynchronizedSpeciesFeatureProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonPropertyType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.featurerenderers.SummarySpeciesFeatureRenderer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u000e\n\u0002\u0010\u0011\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0012\u0004\u0012\u00020\u00020\u00032\u00020\u0004B\t\b\u0010\u00a2\u0006\u0004\b?\u0010@B\u0017\b\u0016\u0012\f\u00104\u001a\b\u0012\u0004\u0012\u00020\f03\u00a2\u0006\u0004\b?\u00109B\u001f\b\u0016\u0012\f\u00104\u001a\b\u0012\u0004\u0012\u00020\f03\u0012\u0006\u0010'\u001a\u00020-\u00a2\u0006\u0004\b?\u0010AB\u001d\b\u0016\u0012\u0012\u00104\u001a\n\u0012\u0006\b\u0001\u0012\u00020\f0B\"\u00020\f\u00a2\u0006\u0004\b?\u0010CJ\u0017\u0010\b\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\n\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\n\u0010\tJ\u0015\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ\u001b\u0010\u0010\u001a\u0004\u0018\u00010\u00022\b\u0010\u000f\u001a\u0004\u0018\u00010\fH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0019\u0010\u0014\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0013\u001a\u00020\u0012H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u001f\u0010\u0017\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u00162\u0006\u0010\u0013\u001a\u00020\u0012H\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u001a\u0010\u0019\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0013\u001a\u00020\u0012H\u0096\u0002\u00a2\u0006\u0004\b\u0019\u0010\u0015J\u001a\u0010\u0019\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u001b\u001a\u00020\u001aH\u0096\u0002\u00a2\u0006\u0004\b\u0019\u0010\u001cJ\u001a\u0010\u0019\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u001e\u001a\u00020\u001dH\u0096\u0002\u00a2\u0006\u0004\b\u0019\u0010\u001fJ\"\u0010\u0019\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010 \u001a\u00020\fH\u0096\u0002\u00a2\u0006\u0004\b\u0019\u0010!J\u001d\u0010$\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010#\u001a\u00020\"H\u0016\u00a2\u0006\u0004\b$\u0010%J\u001d\u0010$\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\u0013\u001a\u00020\u0012H\u0016\u00a2\u0006\u0004\b$\u0010&R$\u0010'\u001a\u0004\u0018\u00010\f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b'\u0010(\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\"\u0010.\u001a\u00020-8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b.\u0010/\u001a\u0004\b.\u00100\"\u0004\b1\u00102R(\u00104\u001a\b\u0012\u0004\u0012\u00020\f038\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b4\u00105\u001a\u0004\b6\u00107\"\u0004\b8\u00109R\u0014\u0010;\u001a\u00020-8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b:\u00100R\"\u0010<\u001a\u00020-8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b<\u0010/\u001a\u0004\b=\u00100\"\u0004\b>\u00102\u00a8\u0006D"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/feature/FlagSpeciesFeatureProvider;", "Lcom/cobblemon/mod/common/api/pokemon/feature/SynchronizedSpeciesFeatureProvider;", "Lcom/cobblemon/mod/common/api/pokemon/feature/FlagSpeciesFeature;", "Lcom/cobblemon/mod/common/api/properties/CustomPokemonPropertyType;", "Lcom/cobblemon/mod/common/api/pokemon/aspect/AspectProvider;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "encode", "", "", "examples", "()Ljava/util/Set;", "value", "fromString", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/pokemon/feature/FlagSpeciesFeature;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "get", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lcom/cobblemon/mod/common/api/pokemon/feature/FlagSpeciesFeature;", "Lcom/cobblemon/mod/common/client/gui/summary/featurerenderers/SummarySpeciesFeatureRenderer;", "getRenderer", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lcom/cobblemon/mod/common/client/gui/summary/featurerenderers/SummarySpeciesFeatureRenderer;", "invoke", "Lcom/google/gson/JsonObject;", "json", "(Lcom/google/gson/JsonObject;)Lcom/cobblemon/mod/common/api/pokemon/feature/FlagSpeciesFeature;", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "(Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/api/pokemon/feature/FlagSpeciesFeature;", "name", "(Lnet/minecraft/network/FriendlyByteBuf;Ljava/lang/String;)Lcom/cobblemon/mod/common/api/pokemon/feature/FlagSpeciesFeature;", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "properties", "provide", "(Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;)Ljava/util/Set;", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Ljava/util/Set;", "default", "Ljava/lang/String;", "getDefault", "()Ljava/lang/String;", "setDefault", "(Ljava/lang/String;)V", "", "isAspect", "Z", "()Z", "setAspect", "(Z)V", "", "keys", "Ljava/util/List;", "getKeys", "()Ljava/util/List;", "setKeys", "(Ljava/util/List;)V", "getNeedsKey", "needsKey", "visible", "getVisible", "setVisible", "<init>", "()V", "(Ljava/util/List;Z)V", "", "([Ljava/lang/String;)V", "common"})
@SourceDebugExtension(value={"SMAP\nFlagSpeciesFeature.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FlagSpeciesFeature.kt\ncom/cobblemon/mod/common/api/pokemon/feature/FlagSpeciesFeatureProvider\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,185:1\n1#2:186\n800#3,11:187\n*S KotlinDebug\n*F\n+ 1 FlagSpeciesFeature.kt\ncom/cobblemon/mod/common/api/pokemon/feature/FlagSpeciesFeatureProvider\n*L\n179#1:187,11\n*E\n"})
public final class FlagSpeciesFeatureProvider
implements SynchronizedSpeciesFeatureProvider<FlagSpeciesFeature>,
CustomPokemonPropertyType<FlagSpeciesFeature>,
AspectProvider {
    @NotNull
    private List<String> keys;
    @Nullable
    private String default;
    private boolean isAspect;
    private boolean visible;

    @NotNull
    public List<String> getKeys() {
        return this.keys;
    }

    public void setKeys(@NotNull List<String> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.keys = list;
    }

    @Override
    public boolean getNeedsKey() {
        return true;
    }

    @Nullable
    public final String getDefault() {
        return this.default;
    }

    public final void setDefault(@Nullable String string) {
        this.default = string;
    }

    public final boolean isAspect() {
        return this.isAspect;
    }

    public final void setAspect(boolean bl) {
        this.isAspect = bl;
    }

    @Override
    public boolean getVisible() {
        return this.visible;
    }

    @Override
    public void setVisible(boolean bl) {
        this.visible = bl;
    }

    @Override
    @Nullable
    public FlagSpeciesFeature invoke(@NotNull FriendlyByteBuf buffer, @NotNull String name) {
        FlagSpeciesFeature flagSpeciesFeature;
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        if (this.getKeys().contains(name)) {
            FlagSpeciesFeature flagSpeciesFeature2;
            FlagSpeciesFeature it = flagSpeciesFeature2 = new FlagSpeciesFeature(name);
            boolean bl = false;
            it.decode(buffer);
            flagSpeciesFeature = flagSpeciesFeature2;
        } else {
            flagSpeciesFeature = null;
        }
        return flagSpeciesFeature;
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_236828_((Collection)this.getKeys(), (arg_0, arg_1) -> FlagSpeciesFeatureProvider.encode$lambda$1(buffer, arg_0, arg_1));
        buffer.m_236821_((Object)this.default, (arg_0, arg_1) -> FlagSpeciesFeatureProvider.encode$lambda$2(buffer, arg_0, arg_1));
        buffer.writeBoolean(this.isAspect);
    }

    @Override
    public void decode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        List list = buffer.m_236845_(FlagSpeciesFeatureProvider::decode$lambda$3);
        Intrinsics.checkNotNullExpressionValue((Object)list, (String)"buffer.readList { it.readString() }");
        this.setKeys(list);
        this.default = (String)buffer.m_236868_(FlagSpeciesFeatureProvider::decode$lambda$4);
        this.isAspect = buffer.readBoolean();
    }

    @Override
    @Nullable
    public SummarySpeciesFeatureRenderer<FlagSpeciesFeature> getRenderer(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        return null;
    }

    @NotNull
    public Set<String> examples() {
        Object[] objectArray = new String[]{"true", "false"};
        return SetsKt.setOf((Object[])objectArray);
    }

    public FlagSpeciesFeatureProvider() {
        this.keys = CollectionsKt.emptyList();
        this.isAspect = true;
        this.setKeys(CollectionsKt.emptyList());
    }

    public FlagSpeciesFeatureProvider(@NotNull List<String> keys) {
        Intrinsics.checkNotNullParameter(keys, (String)"keys");
        this.keys = CollectionsKt.emptyList();
        this.isAspect = true;
        this.setKeys(keys);
    }

    public FlagSpeciesFeatureProvider(@NotNull List<String> keys, boolean bl) {
        Intrinsics.checkNotNullParameter(keys, (String)"keys");
        this.keys = CollectionsKt.emptyList();
        this.isAspect = true;
        this.setKeys(keys);
        this.default = String.valueOf(bl);
    }

    public FlagSpeciesFeatureProvider(String ... keys) {
        Intrinsics.checkNotNullParameter((Object)keys, (String)"keys");
        this(ArraysKt.toList((Object[])keys));
    }

    @Override
    @Nullable
    public FlagSpeciesFeature get(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        return (FlagSpeciesFeature)pokemon.getFeature((String)CollectionsKt.first((List)this.getKeys()));
    }

    @Override
    @Nullable
    public FlagSpeciesFeature invoke(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        FlagSpeciesFeature flagSpeciesFeature = this.get(pokemon);
        if (flagSpeciesFeature == null) {
            String string = this.default;
            if (Intrinsics.areEqual((Object)string, (Object)"random")) {
                flagSpeciesFeature = new FlagSpeciesFeature((String)CollectionsKt.first((List)this.getKeys()), Random.Default.nextBoolean());
            } else {
                Object[] objectArray = new String[]{"true", "false"};
                flagSpeciesFeature = CollectionsKt.contains((Iterable)SetsKt.setOf((Object[])objectArray), (Object)string) ? new FlagSpeciesFeature((String)CollectionsKt.first((List)this.getKeys()), Boolean.parseBoolean(this.default)) : null;
            }
        }
        return flagSpeciesFeature;
    }

    @Override
    @Nullable
    public FlagSpeciesFeature invoke(@NotNull CompoundTag nbt) {
        FlagSpeciesFeature flagSpeciesFeature;
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        if (nbt.m_128441_((String)CollectionsKt.first((List)this.getKeys()))) {
            FlagSpeciesFeature flagSpeciesFeature2;
            FlagSpeciesFeature it = flagSpeciesFeature2 = new FlagSpeciesFeature((String)CollectionsKt.first((List)this.getKeys()), false);
            boolean bl = false;
            it.loadFromNBT(nbt);
            flagSpeciesFeature = flagSpeciesFeature2;
        } else {
            flagSpeciesFeature = null;
        }
        return flagSpeciesFeature;
    }

    @Override
    @Nullable
    public FlagSpeciesFeature invoke(@NotNull JsonObject json) {
        FlagSpeciesFeature flagSpeciesFeature;
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        if (json.has((String)CollectionsKt.first((List)this.getKeys()))) {
            FlagSpeciesFeature flagSpeciesFeature2;
            FlagSpeciesFeature it = flagSpeciesFeature2 = new FlagSpeciesFeature((String)CollectionsKt.first((List)this.getKeys()), false);
            boolean bl = false;
            it.loadFromJSON(json);
            flagSpeciesFeature = flagSpeciesFeature2;
        } else {
            flagSpeciesFeature = null;
        }
        return flagSpeciesFeature;
    }

    @Override
    @Nullable
    public FlagSpeciesFeature fromString(@Nullable String value2) {
        boolean isWeirdValue;
        boolean bl = isWeirdValue = value2 != null && !this.examples().contains(value2);
        if (isWeirdValue) {
            return null;
        }
        return value2 == null ? new FlagSpeciesFeature((String)CollectionsKt.first((List)this.getKeys()), true) : new FlagSpeciesFeature((String)CollectionsKt.first((List)this.getKeys()), Boolean.parseBoolean(value2));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    @NotNull
    public Set<String> provide(@NotNull Pokemon pokemon) {
        Set set2;
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        if (this.isAspect) {
            FlagSpeciesFeature flagSpeciesFeature = (FlagSpeciesFeature)pokemon.getFeature((String)CollectionsKt.first((List)this.getKeys()));
            boolean bl = flagSpeciesFeature != null ? flagSpeciesFeature.getEnabled() : false;
            if (bl) {
                set2 = SetsKt.setOf((Object)CollectionsKt.first((List)this.getKeys()));
                return set2;
            }
        }
        set2 = SetsKt.emptySet();
        return set2;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    @NotNull
    public Set<String> provide(@NotNull PokemonProperties properties2) {
        Set set2;
        Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
        if (this.isAspect) {
            Object v0;
            block4: {
                void $this$filterIsInstanceTo$iv$iv;
                Iterable $this$filterIsInstance$iv = properties2.getCustomProperties();
                boolean $i$f$filterIsInstance = false;
                Iterable iterable = $this$filterIsInstance$iv;
                Collection destination$iv$iv = new ArrayList();
                boolean $i$f$filterIsInstanceTo = false;
                for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                    if (!(element$iv$iv instanceof FlagSpeciesFeature)) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                Iterable iterable2 = (List)destination$iv$iv;
                for (Object e : iterable2) {
                    FlagSpeciesFeature it = (FlagSpeciesFeature)e;
                    boolean bl = false;
                    if (!Intrinsics.areEqual((Object)it.getName(), (Object)CollectionsKt.first((List)this.getKeys()))) continue;
                    v0 = e;
                    break block4;
                }
                v0 = null;
            }
            FlagSpeciesFeature flagSpeciesFeature = v0;
            boolean bl = flagSpeciesFeature != null ? flagSpeciesFeature.getEnabled() : false;
            if (bl) {
                set2 = SetsKt.setOf((Object)CollectionsKt.first((List)this.getKeys()));
                return set2;
            }
        }
        set2 = SetsKt.emptySet();
        return set2;
    }

    @Override
    @NotNull
    public AspectProvider register() {
        return AspectProvider.DefaultImpls.register(this);
    }

    private static final void encode$lambda$1(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, String value2) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        $buffer.m_130070_(value2);
    }

    private static final void encode$lambda$2(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, String value2) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        $buffer.m_130070_(value2);
    }

    private static final String decode$lambda$3(FriendlyByteBuf it) {
        return it.m_130277_();
    }

    private static final String decode$lambda$4(FriendlyByteBuf it) {
        return it.m_130277_();
    }
}

