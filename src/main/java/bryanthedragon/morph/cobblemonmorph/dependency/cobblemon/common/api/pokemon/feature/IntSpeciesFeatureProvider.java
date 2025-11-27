/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.google.gson.annotations.SerializedName
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Decodable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Encodable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.IntSpeciesFeature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SynchronizedSpeciesFeatureProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonPropertyType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.featurerenderers.BarSummarySpeciesFeatureRenderer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.featurerenderers.SummarySpeciesFeatureRenderer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\f\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0012\u0004\u0012\u00020\u00020\u0003:\u0001IB\u0007\u00a2\u0006\u0004\bG\u0010HJ\u0017\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\t\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\t\u0010\bJ\u0015\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\u001b\u0010\u000f\u001a\u0004\u0018\u00010\u00022\b\u0010\u000e\u001a\u0004\u0018\u00010\u000bH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u001e\u0010\u0014\u001a\t\u0018\u00010\u0002\u00a2\u0006\u0002\b\u00132\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u001f\u0010\u0017\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u00162\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u001a\u0010\u0019\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0012\u001a\u00020\u0011H\u0096\u0002\u00a2\u0006\u0004\b\u0019\u0010\u0015J\u001a\u0010\u0019\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u001b\u001a\u00020\u001aH\u0096\u0002\u00a2\u0006\u0004\b\u0019\u0010\u001cJ\u001a\u0010\u0019\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u001e\u001a\u00020\u001dH\u0096\u0002\u00a2\u0006\u0004\b\u0019\u0010\u001fJ\"\u0010\u0019\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010 \u001a\u00020\u000bH\u0096\u0002\u00a2\u0006\u0004\b\u0019\u0010!R$\u0010#\u001a\u0004\u0018\u00010\"8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b#\u0010$\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R$\u0010*\u001a\u0004\u0018\u00010)8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b*\u0010+\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R(\u00100\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b0\u00101\u001a\u0004\b2\u0010\r\"\u0004\b3\u00104R\"\u00105\u001a\u00020\"8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b5\u00106\u001a\u0004\b7\u00108\"\u0004\b9\u0010:R\"\u0010;\u001a\u00020\"8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b;\u00106\u001a\u0004\b<\u00108\"\u0004\b=\u0010:R\u0014\u0010A\u001a\u00020>8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b?\u0010@R\"\u0010B\u001a\u00020>8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\bB\u0010C\u001a\u0004\bD\u0010@\"\u0004\bE\u0010F\u00a8\u0006J"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/feature/IntSpeciesFeatureProvider;", "Lcom/cobblemon/mod/common/api/pokemon/feature/SynchronizedSpeciesFeatureProvider;", "Lcom/cobblemon/mod/common/api/pokemon/feature/IntSpeciesFeature;", "Lcom/cobblemon/mod/common/api/properties/CustomPokemonPropertyType;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "encode", "", "", "examples", "()Ljava/util/List;", "value", "fromString", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/pokemon/feature/IntSpeciesFeature;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lkotlin/internal/NoInfer;", "get", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lcom/cobblemon/mod/common/api/pokemon/feature/IntSpeciesFeature;", "Lcom/cobblemon/mod/common/client/gui/summary/featurerenderers/SummarySpeciesFeatureRenderer;", "getRenderer", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lcom/cobblemon/mod/common/client/gui/summary/featurerenderers/SummarySpeciesFeatureRenderer;", "invoke", "Lcom/google/gson/JsonObject;", "json", "(Lcom/google/gson/JsonObject;)Lcom/cobblemon/mod/common/api/pokemon/feature/IntSpeciesFeature;", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "(Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/api/pokemon/feature/IntSpeciesFeature;", "name", "(Lnet/minecraft/network/FriendlyByteBuf;Ljava/lang/String;)Lcom/cobblemon/mod/common/api/pokemon/feature/IntSpeciesFeature;", "", "default", "Ljava/lang/Integer;", "getDefault", "()Ljava/lang/Integer;", "setDefault", "(Ljava/lang/Integer;)V", "Lcom/cobblemon/mod/common/api/pokemon/feature/IntSpeciesFeatureProvider$DisplayData;", "display", "Lcom/cobblemon/mod/common/api/pokemon/feature/IntSpeciesFeatureProvider$DisplayData;", "getDisplay", "()Lcom/cobblemon/mod/common/api/pokemon/feature/IntSpeciesFeatureProvider$DisplayData;", "setDisplay", "(Lcom/cobblemon/mod/common/api/pokemon/feature/IntSpeciesFeatureProvider$DisplayData;)V", "keys", "Ljava/util/List;", "getKeys", "setKeys", "(Ljava/util/List;)V", "max", "I", "getMax", "()I", "setMax", "(I)V", "min", "getMin", "setMin", "", "getNeedsKey", "()Z", "needsKey", "visible", "Z", "getVisible", "setVisible", "(Z)V", "<init>", "()V", "DisplayData", "common"})
@SourceDebugExtension(value={"SMAP\nIntSpeciesFeature.kt\nKotlin\n*S Kotlin\n*F\n+ 1 IntSpeciesFeature.kt\ncom/cobblemon/mod/common/api/pokemon/feature/IntSpeciesFeatureProvider\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,181:1\n1#2:182\n800#3,11:183\n*S KotlinDebug\n*F\n+ 1 IntSpeciesFeature.kt\ncom/cobblemon/mod/common/api/pokemon/feature/IntSpeciesFeatureProvider\n*L\n149#1:183,11\n*E\n"})
public final class IntSpeciesFeatureProvider
implements SynchronizedSpeciesFeatureProvider<IntSpeciesFeature>,
CustomPokemonPropertyType<IntSpeciesFeature> {
    @NotNull
    private List<String> keys = CollectionsKt.emptyList();
    private boolean visible;
    @Nullable
    private Integer default;
    private int min;
    private int max = 100;
    @Nullable
    private DisplayData display;

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

    @Override
    public boolean getVisible() {
        return this.visible;
    }

    @Override
    public void setVisible(boolean bl) {
        this.visible = bl;
    }

    @Nullable
    public final Integer getDefault() {
        return this.default;
    }

    public final void setDefault(@Nullable Integer n) {
        this.default = n;
    }

    public final int getMin() {
        return this.min;
    }

    public final void setMin(int n) {
        this.min = n;
    }

    public final int getMax() {
        return this.max;
    }

    public final void setMax(int n) {
        this.max = n;
    }

    @Nullable
    public final DisplayData getDisplay() {
        return this.display;
    }

    public final void setDisplay(@Nullable DisplayData displayData) {
        this.display = displayData;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    @Nullable
    public IntSpeciesFeature fromString(@Nullable String value2) {
        String string = value2;
        Object object = string;
        if (string == null) return null;
        Integer n = StringsKt.toIntOrNull((String)object);
        object = n;
        if (n == null) return null;
        Object object2 = object;
        int it = ((Number)object2).intValue();
        boolean bl = false;
        int n2 = this.min;
        if (it > this.max) return null;
        if (n2 > it) return null;
        boolean bl2 = true;
        if (!bl2) return null;
        Object object3 = object2;
        object = object3;
        if (object3 == null) return null;
        it = ((Number)object).intValue();
        boolean bl3 = false;
        IntSpeciesFeature intSpeciesFeature = new IntSpeciesFeature((String)CollectionsKt.first((List)this.getKeys()), it);
        return intSpeciesFeature;
    }

    @NotNull
    public List<String> examples() {
        return CollectionsKt.emptyList();
    }

    @Override
    @Nullable
    public IntSpeciesFeature invoke(@NotNull FriendlyByteBuf buffer, @NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        return this.getKeys().contains(name) ? new IntSpeciesFeature(name, buffer.readInt()) : null;
    }

    @Override
    @Nullable
    public IntSpeciesFeature invoke(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        IntSpeciesFeature intSpeciesFeature = this.get(pokemon);
        if (intSpeciesFeature == null) {
            Integer n = this.default;
            if (n != null) {
                int it = ((Number)n).intValue();
                boolean bl = false;
                intSpeciesFeature = new IntSpeciesFeature((String)CollectionsKt.first((List)this.getKeys()), it);
            } else {
                intSpeciesFeature = null;
            }
        }
        return intSpeciesFeature;
    }

    @Override
    @Nullable
    public IntSpeciesFeature invoke(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        return nbt.m_128441_((String)CollectionsKt.first((List)this.getKeys())) ? new IntSpeciesFeature((String)CollectionsKt.first((List)this.getKeys()), nbt.m_128451_((String)CollectionsKt.first((List)this.getKeys()))) : null;
    }

    @Override
    @Nullable
    public IntSpeciesFeature invoke(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        return json.has((String)CollectionsKt.first((List)this.getKeys())) ? new IntSpeciesFeature((String)CollectionsKt.first((List)this.getKeys()), json.get((String)CollectionsKt.first((List)this.getKeys())).getAsInt()) : null;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @Nullable
    public IntSpeciesFeature get(@NotNull Pokemon pokemon) {
        Object v0;
        block2: {
            void $this$filterIsInstanceTo$iv$iv;
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Iterable $this$filterIsInstance$iv = pokemon.getFeatures();
            boolean $i$f$filterIsInstance = false;
            Iterable iterable = $this$filterIsInstance$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterIsInstanceTo = false;
            for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                if (!(element$iv$iv instanceof IntSpeciesFeature)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            Iterable iterable2 = (List)destination$iv$iv;
            for (Object e : iterable2) {
                IntSpeciesFeature it = (IntSpeciesFeature)e;
                boolean bl = false;
                if (!this.getKeys().contains(it.getName())) continue;
                v0 = e;
                break block2;
            }
            v0 = null;
        }
        return v0;
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_236828_((Collection)this.getKeys(), (arg_0, arg_1) -> IntSpeciesFeatureProvider.encode$lambda$4(buffer, arg_0, arg_1));
        buffer.m_236821_((Object)this.default, (arg_0, arg_1) -> IntSpeciesFeatureProvider.encode$lambda$5(buffer, arg_0, arg_1));
        buffer.writeInt(this.min);
        buffer.writeInt(this.max);
        buffer.m_236821_((Object)this.display, (arg_0, arg_1) -> IntSpeciesFeatureProvider.encode$lambda$6(buffer, arg_0, arg_1));
    }

    @Override
    public void decode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        List list = buffer.m_236845_(arg_0 -> IntSpeciesFeatureProvider.decode$lambda$7(buffer, arg_0));
        Intrinsics.checkNotNullExpressionValue((Object)list, (String)"buffer.readList { buffer.readString() }");
        this.setKeys(list);
        this.default = (Integer)buffer.m_236868_(arg_0 -> IntSpeciesFeatureProvider.decode$lambda$8(buffer, arg_0));
        this.min = buffer.readInt();
        this.max = buffer.readInt();
        this.display = (DisplayData)buffer.m_236868_(arg_0 -> IntSpeciesFeatureProvider.decode$lambda$10(buffer, arg_0));
    }

    @Override
    @Nullable
    public SummarySpeciesFeatureRenderer<IntSpeciesFeature> getRenderer(@NotNull Pokemon pokemon) {
        BarSummarySpeciesFeatureRenderer barSummarySpeciesFeatureRenderer;
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        DisplayData displayData = this.display;
        if (displayData != null) {
            ResourceLocation resourceLocation;
            DisplayData it = displayData;
            boolean bl = false;
            String string = (String)CollectionsKt.first((List)this.getKeys());
            MutableComponent mutableComponent = MiscUtils.asTranslated(it.getName());
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"it.name.asTranslated()");
            Vec3 vec3 = it.getColour();
            ResourceLocation resourceLocation2 = it.getUnderlay();
            if (resourceLocation2 == null) {
                resourceLocation2 = MiscUtils.cobblemonResource("textures/gui/summary/summary_stats_other_bar.png");
            }
            if ((resourceLocation = it.getOverlay()) == null) {
                resourceLocation = MiscUtils.cobblemonResource("textures/gui/summary/summary_stats_generic_overlay.png");
            }
            barSummarySpeciesFeatureRenderer = new BarSummarySpeciesFeatureRenderer(string, mutableComponent, this.min, this.max, vec3, resourceLocation2, resourceLocation, pokemon);
        } else {
            barSummarySpeciesFeatureRenderer = null;
        }
        return barSummarySpeciesFeatureRenderer;
    }

    private static final void encode$lambda$4(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, String value2) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        $buffer.m_130070_(value2);
    }

    private static final void encode$lambda$5(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, Integer value2) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        Intrinsics.checkNotNullExpressionValue((Object)value2, (String)"value");
        $buffer.writeInt(value2.intValue());
    }

    private static final void encode$lambda$6(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, DisplayData value2) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        value2.encode($buffer);
    }

    private static final String decode$lambda$7(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        return $buffer.m_130277_();
    }

    private static final Integer decode$lambda$8(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        return $buffer.readInt();
    }

    private static final DisplayData decode$lambda$10(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        DisplayData displayData;
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        DisplayData it2 = displayData = new DisplayData();
        boolean bl = false;
        it2.decode($buffer);
        return displayData;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u00012\u00020\u0002B\u0007\u00a2\u0006\u0004\b!\u0010\"J\u0017\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0017\u0010\b\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\b\u0010\u0007R\"\u0010\n\u001a\u00020\t8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\"\u0010\u0011\u001a\u00020\u00108\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R$\u0010\u0018\u001a\u0004\u0018\u00010\u00178\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR$\u0010\u001e\u001a\u0004\u0018\u00010\u00178\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001e\u0010\u0019\u001a\u0004\b\u001f\u0010\u001b\"\u0004\b \u0010\u001d\u00a8\u0006#"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/feature/IntSpeciesFeatureProvider$DisplayData;", "Lcom/cobblemon/mod/common/api/net/Encodable;", "Lcom/cobblemon/mod/common/api/net/Decodable;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "encode", "Lnet/minecraft/world/phys/Vec3;", "colour", "Lnet/minecraft/world/phys/Vec3;", "getColour", "()Lnet/minecraft/world/phys/Vec3;", "setColour", "(Lnet/minecraft/world/phys/Vec3;)V", "", "name", "Ljava/lang/String;", "getName", "()Ljava/lang/String;", "setName", "(Ljava/lang/String;)V", "Lnet/minecraft/resources/ResourceLocation;", "overlay", "Lnet/minecraft/resources/ResourceLocation;", "getOverlay", "()Lnet/minecraft/resources/ResourceLocation;", "setOverlay", "(Lnet/minecraft/resources/ResourceLocation;)V", "underlay", "getUnderlay", "setUnderlay", "<init>", "()V", "common"})
    public static final class DisplayData
    implements Encodable,
    Decodable {
        @NotNull
        private String name = "";
        @SerializedName(value="colour", alternate={"color"})
        @NotNull
        private Vec3 colour = new Vec3(255.0, 255.0, 255.0);
        @Nullable
        private ResourceLocation underlay;
        @Nullable
        private ResourceLocation overlay;

        @NotNull
        public final String getName() {
            return this.name;
        }

        public final void setName(@NotNull String string) {
            Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
            this.name = string;
        }

        @NotNull
        public final Vec3 getColour() {
            return this.colour;
        }

        public final void setColour(@NotNull Vec3 vec3) {
            Intrinsics.checkNotNullParameter((Object)vec3, (String)"<set-?>");
            this.colour = vec3;
        }

        @Nullable
        public final ResourceLocation getUnderlay() {
            return this.underlay;
        }

        public final void setUnderlay(@Nullable ResourceLocation resourceLocation) {
            this.underlay = resourceLocation;
        }

        @Nullable
        public final ResourceLocation getOverlay() {
            return this.overlay;
        }

        public final void setOverlay(@Nullable ResourceLocation resourceLocation) {
            this.overlay = resourceLocation;
        }

        @Override
        public void decode(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            String string = buffer.m_130277_();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"buffer.readString()");
            this.name = string;
            this.colour = new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
            this.underlay = (ResourceLocation)buffer.m_236868_(arg_0 -> DisplayData.decode$lambda$0(buffer, arg_0));
            this.overlay = (ResourceLocation)buffer.m_236868_(arg_0 -> DisplayData.decode$lambda$1(buffer, arg_0));
        }

        @Override
        public void encode(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            buffer.m_130070_(this.name);
            buffer.writeDouble(this.colour.f_82479_);
            buffer.writeDouble(this.colour.f_82480_);
            buffer.writeDouble(this.colour.f_82481_);
            buffer.m_236821_((Object)this.underlay, (arg_0, arg_1) -> DisplayData.encode$lambda$2(buffer, arg_0, arg_1));
            buffer.m_236821_((Object)this.overlay, (arg_0, arg_1) -> DisplayData.encode$lambda$3(buffer, arg_0, arg_1));
        }

        private static final ResourceLocation decode$lambda$0(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
            Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
            return $buffer.m_130281_();
        }

        private static final ResourceLocation decode$lambda$1(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
            Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
            return $buffer.m_130281_();
        }

        private static final void encode$lambda$2(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, ResourceLocation value2) {
            Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
            $buffer.m_130085_(value2);
        }

        private static final void encode$lambda$3(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, ResourceLocation value2) {
            Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
            $buffer.m_130085_(value2);
        }
    }
}

