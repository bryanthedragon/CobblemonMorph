/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.StringSpeciesFeature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SynchronizedSpeciesFeatureProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonPropertyType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.featurerenderers.SummarySpeciesFeatureRenderer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
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
@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0012\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0012\u0004\u0012\u00020\u00020\u00032\u00020\u0004B\t\b\u0010\u00a2\u0006\u0004\bJ\u0010KBE\u0012\f\u0010A\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\n\b\u0002\u00108\u001a\u0004\u0018\u00010\f\u0012\u000e\b\u0002\u00103\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\b\b\u0002\u0010<\u001a\u00020;\u0012\b\b\u0002\u0010-\u001a\u00020\f\u00a2\u0006\u0004\bJ\u0010LJ\u0017\u0010\b\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\n\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\n\u0010\tJ\u0015\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ\u001b\u0010\u0010\u001a\u0004\u0018\u00010\u00022\b\u0010\u000f\u001a\u0004\u0018\u00010\fH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0019\u0010\u0014\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0013\u001a\u00020\u0012H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0013\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\f0\u0016\u00a2\u0006\u0004\b\u0017\u0010\u000eJ\u0015\u0010\u0019\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u001f\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u001b2\u0006\u0010\u0013\u001a\u00020\u0012H\u0016\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u001a\u0010\u001e\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0013\u001a\u00020\u0012H\u0096\u0002\u00a2\u0006\u0004\b\u001e\u0010\u0015J\u001a\u0010\u001e\u001a\u0004\u0018\u00010\u00022\u0006\u0010 \u001a\u00020\u001fH\u0096\u0002\u00a2\u0006\u0004\b\u001e\u0010!J\u001a\u0010\u001e\u001a\u0004\u0018\u00010\u00022\u0006\u0010#\u001a\u00020\"H\u0096\u0002\u00a2\u0006\u0004\b\u001e\u0010$J\"\u0010\u001e\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010%\u001a\u00020\fH\u0096\u0002\u00a2\u0006\u0004\b\u001e\u0010&J\u001d\u0010*\u001a\b\u0012\u0004\u0012\u00020\f0)2\u0006\u0010(\u001a\u00020'H\u0016\u00a2\u0006\u0004\b*\u0010+J\u001d\u0010*\u001a\b\u0012\u0004\u0012\u00020\f0)2\u0006\u0010\u0013\u001a\u00020\u0012H\u0016\u00a2\u0006\u0004\b*\u0010,R\"\u0010-\u001a\u00020\f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b-\u0010.\u001a\u0004\b/\u00100\"\u0004\b1\u00102R(\u00103\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b3\u00104\u001a\u0004\b5\u0010\u000e\"\u0004\b6\u00107R$\u00108\u001a\u0004\u0018\u00010\f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b8\u0010.\u001a\u0004\b9\u00100\"\u0004\b:\u00102R\"\u0010<\u001a\u00020;8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b<\u0010=\u001a\u0004\b<\u0010>\"\u0004\b?\u0010@R(\u0010A\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\bA\u00104\u001a\u0004\bB\u0010\u000e\"\u0004\bC\u00107R\"\u0010D\u001a\u00020;8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\bD\u0010=\u001a\u0004\bE\u0010>\"\u0004\bF\u0010@R\"\u0010G\u001a\u00020;8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\bG\u0010=\u001a\u0004\bH\u0010>\"\u0004\bI\u0010@\u00a8\u0006M"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/feature/ChoiceSpeciesFeatureProvider;", "Lcom/cobblemon/mod/common/api/pokemon/feature/SynchronizedSpeciesFeatureProvider;", "Lcom/cobblemon/mod/common/api/pokemon/feature/StringSpeciesFeature;", "Lcom/cobblemon/mod/common/api/properties/CustomPokemonPropertyType;", "Lcom/cobblemon/mod/common/api/pokemon/aspect/AspectProvider;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "encode", "", "", "examples", "()Ljava/util/List;", "value", "fromString", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/pokemon/feature/StringSpeciesFeature;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "get", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lcom/cobblemon/mod/common/api/pokemon/feature/StringSpeciesFeature;", "", "getAllAspects", "feature", "getAspect", "(Lcom/cobblemon/mod/common/api/pokemon/feature/StringSpeciesFeature;)Ljava/lang/String;", "Lcom/cobblemon/mod/common/client/gui/summary/featurerenderers/SummarySpeciesFeatureRenderer;", "getRenderer", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lcom/cobblemon/mod/common/client/gui/summary/featurerenderers/SummarySpeciesFeatureRenderer;", "invoke", "Lcom/google/gson/JsonObject;", "json", "(Lcom/google/gson/JsonObject;)Lcom/cobblemon/mod/common/api/pokemon/feature/StringSpeciesFeature;", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "(Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/api/pokemon/feature/StringSpeciesFeature;", "name", "(Lnet/minecraft/network/FriendlyByteBuf;Ljava/lang/String;)Lcom/cobblemon/mod/common/api/pokemon/feature/StringSpeciesFeature;", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "properties", "", "provide", "(Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;)Ljava/util/Set;", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Ljava/util/Set;", "aspectFormat", "Ljava/lang/String;", "getAspectFormat", "()Ljava/lang/String;", "setAspectFormat", "(Ljava/lang/String;)V", "choices", "Ljava/util/List;", "getChoices", "setChoices", "(Ljava/util/List;)V", "default", "getDefault", "setDefault", "", "isAspect", "Z", "()Z", "setAspect", "(Z)V", "keys", "getKeys", "setKeys", "needsKey", "getNeedsKey", "setNeedsKey", "visible", "getVisible", "setVisible", "<init>", "()V", "(Ljava/util/List;Ljava/lang/String;Ljava/util/List;ZLjava/lang/String;)V", "common"})
@SourceDebugExtension(value={"SMAP\nChoiceSpeciesFeatureProvider.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ChoiceSpeciesFeatureProvider.kt\ncom/cobblemon/mod/common/api/pokemon/feature/ChoiceSpeciesFeatureProvider\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,142:1\n1#2:143\n1855#3,2:144\n800#3,11:146\n*S KotlinDebug\n*F\n+ 1 ChoiceSpeciesFeatureProvider.kt\ncom/cobblemon/mod/common/api/pokemon/feature/ChoiceSpeciesFeatureProvider\n*L\n71#1:144,2\n132#1:146,11\n*E\n"})
public class ChoiceSpeciesFeatureProvider
implements SynchronizedSpeciesFeatureProvider<StringSpeciesFeature>,
CustomPokemonPropertyType<StringSpeciesFeature>,
AspectProvider {
    @NotNull
    private List<String> keys;
    @Nullable
    private String default;
    @NotNull
    private List<String> choices;
    private boolean isAspect;
    @NotNull
    private String aspectFormat;
    private boolean needsKey;
    private boolean visible;

    public ChoiceSpeciesFeatureProvider(@NotNull List<String> keys, @Nullable String string, @NotNull List<String> choices, boolean isAspect, @NotNull String aspectFormat) {
        Intrinsics.checkNotNullParameter(keys, (String)"keys");
        Intrinsics.checkNotNullParameter(choices, (String)"choices");
        Intrinsics.checkNotNullParameter((Object)aspectFormat, (String)"aspectFormat");
        this.keys = keys;
        this.default = string;
        this.choices = choices;
        this.isAspect = isAspect;
        this.aspectFormat = aspectFormat;
        this.needsKey = true;
    }

    public /* synthetic */ ChoiceSpeciesFeatureProvider(List list, String string, List list2, boolean bl, String string2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            string = null;
        }
        if ((n & 4) != 0) {
            list2 = CollectionsKt.emptyList();
        }
        if ((n & 8) != 0) {
            bl = true;
        }
        if ((n & 0x10) != 0) {
            string2 = "{{choice}}";
        }
        this(list, string, list2, bl, string2);
    }

    @NotNull
    public List<String> getKeys() {
        return this.keys;
    }

    public void setKeys(@NotNull List<String> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.keys = list;
    }

    @Nullable
    public final String getDefault() {
        return this.default;
    }

    public final void setDefault(@Nullable String string) {
        this.default = string;
    }

    @NotNull
    public final List<String> getChoices() {
        return this.choices;
    }

    public final void setChoices(@NotNull List<String> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.choices = list;
    }

    public final boolean isAspect() {
        return this.isAspect;
    }

    public final void setAspect(boolean bl) {
        this.isAspect = bl;
    }

    @NotNull
    public final String getAspectFormat() {
        return this.aspectFormat;
    }

    public final void setAspectFormat(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.aspectFormat = string;
    }

    @Override
    public boolean getNeedsKey() {
        return this.needsKey;
    }

    public void setNeedsKey(boolean bl) {
        this.needsKey = bl;
    }

    @Override
    public boolean getVisible() {
        return this.visible;
    }

    @Override
    public void setVisible(boolean bl) {
        this.visible = bl;
    }

    @NotNull
    public final String getAspect(@NotNull StringSpeciesFeature feature) {
        Intrinsics.checkNotNullParameter((Object)feature, (String)"feature");
        return MiscUtils.substitute(this.aspectFormat, "choice", feature.getValue());
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_236828_((Collection)this.getKeys(), (arg_0, arg_1) -> ChoiceSpeciesFeatureProvider.encode$lambda$0(buffer, arg_0, arg_1));
        buffer.m_236821_((Object)this.default, (arg_0, arg_1) -> ChoiceSpeciesFeatureProvider.encode$lambda$1(buffer, arg_0, arg_1));
        buffer.m_236828_((Collection)this.choices, (arg_0, arg_1) -> ChoiceSpeciesFeatureProvider.encode$lambda$2(buffer, arg_0, arg_1));
        buffer.writeBoolean(this.isAspect);
        buffer.m_130070_(this.aspectFormat);
        buffer.writeBoolean(this.getNeedsKey());
    }

    @Override
    public void decode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        List list = buffer.m_236845_(arg_0 -> ChoiceSpeciesFeatureProvider.decode$lambda$3(buffer, arg_0));
        Intrinsics.checkNotNullExpressionValue((Object)list, (String)"buffer.readList { buffer.readString() }");
        this.setKeys(list);
        this.default = (String)buffer.m_236868_(arg_0 -> ChoiceSpeciesFeatureProvider.decode$lambda$4(buffer, arg_0));
        List list2 = buffer.m_236845_(arg_0 -> ChoiceSpeciesFeatureProvider.decode$lambda$5(buffer, arg_0));
        Intrinsics.checkNotNullExpressionValue((Object)list2, (String)"buffer.readList { buffer.readString() }");
        this.choices = list2;
        this.isAspect = buffer.readBoolean();
        String string = buffer.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"buffer.readString()");
        this.aspectFormat = string;
        this.setNeedsKey(buffer.readBoolean());
    }

    @Override
    @Nullable
    public SummarySpeciesFeatureRenderer<StringSpeciesFeature> getRenderer(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        return null;
    }

    @Override
    @Nullable
    public StringSpeciesFeature invoke(@NotNull FriendlyByteBuf buffer, @NotNull String name) {
        StringSpeciesFeature stringSpeciesFeature;
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        if (this.getKeys().contains(name)) {
            StringSpeciesFeature stringSpeciesFeature2;
            StringSpeciesFeature it = stringSpeciesFeature2 = new StringSpeciesFeature(name, "");
            boolean bl = false;
            it.decode(buffer);
            stringSpeciesFeature = stringSpeciesFeature2;
        } else {
            stringSpeciesFeature = null;
        }
        return stringSpeciesFeature;
    }

    @NotNull
    public final List<String> getAllAspects() {
        List aspects = CollectionsKt.toMutableList((Collection)this.choices);
        Iterable $this$forEach$iv = this.choices;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            String it = (String)element$iv;
            boolean bl = false;
            aspects.set(this.choices.indexOf(it), MiscUtils.substitute(this.aspectFormat, "choice", it));
        }
        return aspects;
    }

    @NotNull
    public List<String> examples() {
        return this.choices;
    }

    public ChoiceSpeciesFeatureProvider() {
        this(CollectionsKt.emptyList(), null, null, false, null, 30, null);
    }

    @Override
    @Nullable
    public StringSpeciesFeature get(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        return (StringSpeciesFeature)pokemon.getFeature((String)CollectionsKt.first((List)this.getKeys()));
    }

    @Override
    @Nullable
    public StringSpeciesFeature invoke(@NotNull Pokemon pokemon) {
        StringSpeciesFeature stringSpeciesFeature;
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        StringSpeciesFeature existing = this.get(pokemon);
        if (existing != null && this.choices.contains(existing.getValue())) {
            stringSpeciesFeature = existing;
        } else {
            String string;
            if (CollectionsKt.contains((Iterable)this.choices, (Object)this.default)) {
                String string2 = this.default;
                string = string2;
                Intrinsics.checkNotNull((Object)string2);
            } else if (Intrinsics.areEqual((Object)this.default, (Object)"random")) {
                string = (String)CollectionsKt.randomOrNull((Collection)this.choices, (Random)((Random)Random.Default));
                if (string == null) {
                    throw new IllegalStateException("The 'choices' list is empty for species feature provider: " + CollectionsKt.joinToString$default((Iterable)this.getKeys(), null, null, null, (int)0, null, null, (int)63, null));
                }
            } else {
                return null;
            }
            String value2 = string;
            stringSpeciesFeature = this.fromString(value2);
        }
        return stringSpeciesFeature;
    }

    @Override
    @Nullable
    public StringSpeciesFeature invoke(@NotNull CompoundTag nbt) {
        StringSpeciesFeature stringSpeciesFeature;
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        if (nbt.m_128441_((String)CollectionsKt.first((List)this.getKeys()))) {
            StringSpeciesFeature stringSpeciesFeature2;
            StringSpeciesFeature it = stringSpeciesFeature2 = new StringSpeciesFeature((String)CollectionsKt.first((List)this.getKeys()), "");
            boolean bl = false;
            it.loadFromNBT(nbt);
            stringSpeciesFeature = stringSpeciesFeature2;
        } else {
            stringSpeciesFeature = null;
        }
        return stringSpeciesFeature;
    }

    @Override
    @Nullable
    public StringSpeciesFeature invoke(@NotNull JsonObject json) {
        StringSpeciesFeature stringSpeciesFeature;
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        if (json.has((String)CollectionsKt.first((List)this.getKeys()))) {
            StringSpeciesFeature stringSpeciesFeature2;
            StringSpeciesFeature it = stringSpeciesFeature2 = new StringSpeciesFeature((String)CollectionsKt.first((List)this.getKeys()), "");
            boolean bl = false;
            it.loadFromJSON(json);
            stringSpeciesFeature = stringSpeciesFeature2;
        } else {
            stringSpeciesFeature = null;
        }
        return stringSpeciesFeature;
    }

    @Override
    @Nullable
    public StringSpeciesFeature fromString(@Nullable String value2) {
        String lower;
        String string = value2;
        if (string != null) {
            String string2 = string.toLowerCase(Locale.ROOT);
            v2 = string2;
            Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
        } else {
            v2 = lower = null;
        }
        if (lower == null || !this.choices.contains(lower)) {
            return null;
        }
        return new StringSpeciesFeature((String)CollectionsKt.first((List)this.getKeys()), lower);
    }

    @Override
    @NotNull
    public Set<String> provide(@NotNull Pokemon pokemon) {
        Object object;
        block4: {
            block2: {
                block3: {
                    Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
                    if (!this.isAspect) break block2;
                    object = this.get(pokemon);
                    if (object == null) break block3;
                    Object it = object;
                    boolean bl = false;
                    Set set2 = SetsKt.setOf((Object)this.getAspect((StringSpeciesFeature)it));
                    object = set2;
                    if (set2 != null) break block4;
                }
                object = SetsKt.emptySet();
                break block4;
            }
            object = SetsKt.emptySet();
        }
        return object;
    }

    /*
     * WARNING - void declaration
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
                    if (!(element$iv$iv instanceof StringSpeciesFeature)) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                Iterable iterable2 = (List)destination$iv$iv;
                for (Object e : iterable2) {
                    StringSpeciesFeature it = (StringSpeciesFeature)e;
                    boolean bl = false;
                    if (!Intrinsics.areEqual((Object)it.getName(), (Object)CollectionsKt.first((List)this.getKeys()))) continue;
                    v0 = e;
                    break block4;
                }
                v0 = null;
            }
            StringSpeciesFeature feature = v0;
            set2 = feature != null ? SetsKt.setOf((Object)this.getAspect(feature)) : SetsKt.emptySet();
        } else {
            set2 = SetsKt.emptySet();
        }
        return set2;
    }

    @Override
    @NotNull
    public AspectProvider register() {
        return AspectProvider.DefaultImpls.register(this);
    }

    private static final void encode$lambda$0(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, String value2) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        $buffer.m_130070_(value2);
    }

    private static final void encode$lambda$1(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, String value2) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        $buffer.m_130070_(value2);
    }

    private static final void encode$lambda$2(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, String value2) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        $buffer.m_130070_(value2);
    }

    private static final String decode$lambda$3(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        return $buffer.m_130277_();
    }

    private static final String decode$lambda$4(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        return $buffer.m_130277_();
    }

    private static final String decode$lambda$5(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        return $buffer.m_130277_();
    }
}

