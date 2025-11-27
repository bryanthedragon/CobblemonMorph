/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.BufferBuilder$RenderedBuffer
 *  com.mojang.blaze3d.vertex.BufferUploader
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.Tesselator
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.RangesKt
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.util.Mth
 *  net.minecraft.world.phys.Vec2
 *  org.jetbrains.annotations.NotNull
 *  org.joml.Vector3f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.screens.stats;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatures;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SynchronizedSpeciesFeature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SynchronizedSpeciesFeatureProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.featurerenderers.SummarySpeciesFeatureRenderer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.SoundlessWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Nature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\t\n\u0002\u0010\u0007\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\u0018\u0000 V2\u00020\u0001:\u0001VB)\u0012\u0006\u0010R\u001a\u00020\u0002\u0012\u0006\u0010S\u001a\u00020\u0002\u0012\u0006\u0010?\u001a\u00020>\u0012\b\b\u0002\u0010P\u001a\u00020\u0002\u00a2\u0006\u0004\bT\u0010UJ7\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ3\u0010\u0013\u001a\u00020\n2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00020\r2\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u0013\u0010\u0014J/\u0010\u0019\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u0015H\u0002\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u001f\u0010\u001e\u001a\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u001cH\u0002\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u0017\u0010!\u001a\u00020 2\u0006\u0010\u001b\u001a\u00020\u000eH\u0002\u00a2\u0006\u0004\b!\u0010\"J\u001f\u0010&\u001a\u00020\u00022\u0006\u0010$\u001a\u00020#2\u0006\u0010%\u001a\u00020#H\u0002\u00a2\u0006\u0004\b&\u0010'J'\u0010+\u001a\u00020\u001c2\u0006\u0010(\u001a\u00020#2\u0006\u0010)\u001a\u00020#2\u0006\u0010*\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b+\u0010,J/\u0010/\u001a\u00020\n2\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010(\u001a\u00020\u00022\u0006\u0010)\u001a\u00020\u00022\u0006\u0010.\u001a\u00020-H\u0014\u00a2\u0006\u0004\b/\u00100J)\u00103\u001a\u00020\n2\u0006\u00101\u001a\u00020\u00052\b\u0010\u001b\u001a\u0004\u0018\u00010\u000e2\u0006\u00102\u001a\u00020\u001cH\u0002\u00a2\u0006\u0004\b3\u00104J[\u0010<\u001a\u00020\n2\u0006\u0010\b\u001a\u00020\u00072\b\b\u0002\u00105\u001a\u00020#2\b\b\u0002\u0010\u001d\u001a\u00020\u001c2\u0006\u00106\u001a\u00020 2\u0006\u00107\u001a\u00020 2\u0006\u00108\u001a\u00020 2\u0006\u00109\u001a\u00020 2\u0006\u0010:\u001a\u00020 2\u0006\u0010;\u001a\u00020 H\u0002\u00a2\u0006\u0004\b<\u0010=R\u0017\u0010?\u001a\u00020>8\u0006\u00a2\u0006\f\n\u0004\b?\u0010@\u001a\u0004\bA\u0010BR%\u0010F\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020E0D0C8\u0006\u00a2\u0006\f\n\u0004\bF\u0010G\u001a\u0004\bH\u0010IR\"\u0010J\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bJ\u0010K\u001a\u0004\bL\u0010M\"\u0004\bN\u0010OR\u0017\u0010P\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\bP\u0010K\u001a\u0004\bQ\u0010M\u00a8\u0006W"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/stats/StatWidget;", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/SoundlessWidget;", "", "moduleX", "moduleY", "Lcom/mojang/blaze3d/vertex/PoseStack;", "matrices", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "friendship", "", "drawFriendship", "(IILcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/gui/GuiGraphics;I)V", "", "Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;", "stats", "Lorg/joml/Vector3f;", "colour", "maximum", "drawStatHexagon", "(Ljava/util/Map;Lorg/joml/Vector3f;I)V", "Lnet/minecraft/world/phys/Vec2;", "v1", "v2", "v3", "drawTriangle", "(Lorg/joml/Vector3f;Lnet/minecraft/world/phys/Vec2;Lnet/minecraft/world/phys/Vec2;Lnet/minecraft/world/phys/Vec2;)V", "stat", "", "enableColour", "getModifiedStatColour", "(Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;Z)I", "Lnet/minecraft/network/chat/MutableComponent;", "getStatValueAsText", "(Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;)Lnet/minecraft/network/chat/MutableComponent;", "", "mouseX", "mouseY", "getTabIndexFromPos", "(DD)I", "pMouseX", "pMouseY", "pButton", "mouseClicked", "(DDI)Z", "", "pPartialTicks", "renderButton", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "pMatrixStack", "increasedStat", "renderModifiedStatIcon", "(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;Z)V", "offsetY", "hp", "spAtk", "atk", "spDef", "def", "speed", "renderTextAtVertices", "(Lnet/minecraft/client/gui/GuiGraphics;DZLnet/minecraft/network/chat/MutableComponent;Lnet/minecraft/network/chat/MutableComponent;Lnet/minecraft/network/chat/MutableComponent;Lnet/minecraft/network/chat/MutableComponent;Lnet/minecraft/network/chat/MutableComponent;Lnet/minecraft/network/chat/MutableComponent;)V", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "", "Lcom/cobblemon/mod/common/client/gui/summary/featurerenderers/SummarySpeciesFeatureRenderer;", "Lcom/cobblemon/mod/common/api/pokemon/feature/SynchronizedSpeciesFeature;", "renderableFeatures", "Ljava/util/List;", "getRenderableFeatures", "()Ljava/util/List;", "statTabIndex", "I", "getStatTabIndex", "()I", "setStatTabIndex", "(I)V", "tabIndex", "getTabIndex", "pX", "pY", "<init>", "(IILcom/cobblemon/mod/common/pokemon/Pokemon;I)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nStatWidget.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StatWidget.kt\ncom/cobblemon/mod/common/client/gui/summary/widgets/screens/stats/StatWidget\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,575:1\n800#2,11:576\n1603#2,9:587\n1855#2:596\n1856#2:598\n1612#2:599\n1179#2,2:601\n1253#2,4:603\n1179#2,2:607\n1253#2,4:609\n1#3:597\n1#3:600\n*S KotlinDebug\n*F\n+ 1 StatWidget.kt\ncom/cobblemon/mod/common/client/gui/summary/widgets/screens/stats/StatWidget\n*L\n92#1:576,11\n93#1:587,9\n93#1:596\n93#1:598\n93#1:599\n303#1:601,2\n303#1:603,4\n308#1:607,2\n308#1:609,4\n93#1:597\n*E\n"})
public final class StatWidget
extends SoundlessWidget {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Pokemon pokemon;
    private final int tabIndex;
    private int statTabIndex;
    @NotNull
    private final List<SummarySpeciesFeatureRenderer<? extends SynchronizedSpeciesFeature>> renderableFeatures;
    private static final int STATS = 0;
    private static final int IV = 1;
    private static final int EV = 2;
    private static final int BASE = 3;
    private static final int OTHER = 4;
    private static final int WIDTH = 134;
    private static final int HEIGHT = 148;
    public static final float SCALE = 0.5f;
    private static final int WHITE = 0xFFFFFF;
    private static final int GREY = 0xAAAAAA;
    private static final int BLUE = 5540859;
    private static final int RED = 16471124;
    @NotNull
    private static final ResourceLocation statsBaseResource = MiscUtils.cobblemonResource("textures/gui/summary/summary_stats_chart_base.png");
    @NotNull
    private static final ResourceLocation statsChartResource = MiscUtils.cobblemonResource("textures/gui/summary/summary_stats_chart.png");
    @NotNull
    private static final ResourceLocation statsOtherBaseResource = MiscUtils.cobblemonResource("textures/gui/summary/summary_stats_other_base.png");
    @NotNull
    private static final ResourceLocation statsOtherBarTemplate = MiscUtils.cobblemonResource("textures/gui/summary/summary_stats_other_bar.png");
    @NotNull
    private static final ResourceLocation friendshipOverlayResource = MiscUtils.cobblemonResource("textures/gui/summary/summary_stats_friendship_overlay.png");
    @NotNull
    private static final ResourceLocation tabMarkerResource = MiscUtils.cobblemonResource("textures/gui/summary/summary_stats_tab_marker.png");
    @NotNull
    private static final ResourceLocation statIncreaseResource = MiscUtils.cobblemonResource("textures/gui/summary/summary_stats_icon_increase.png");
    @NotNull
    private static final ResourceLocation statDecreaseResource = MiscUtils.cobblemonResource("textures/gui/summary/summary_stats_icon_decrease.png");
    private static final MutableComponent statsLabel = LocalizationUtilsKt.lang("ui.stats", new Object[0]);
    private static final MutableComponent baseLabel = LocalizationUtilsKt.lang("ui.stats.base", new Object[0]);
    private static final MutableComponent ivLabel = LocalizationUtilsKt.lang("ui.stats.ivs", new Object[0]);
    private static final MutableComponent evLabel = LocalizationUtilsKt.lang("ui.stats.evs", new Object[0]);
    private static final MutableComponent otherLabel = LocalizationUtilsKt.lang("ui.stats.other", new Object[0]);
    private static final MutableComponent hpLabel = LocalizationUtilsKt.lang("ui.stats.hp", new Object[0]);
    private static final MutableComponent spAtkLabel = LocalizationUtilsKt.lang("ui.stats.sp_atk", new Object[0]);
    private static final MutableComponent atkLabel = LocalizationUtilsKt.lang("ui.stats.atk", new Object[0]);
    private static final MutableComponent spDefLabel = LocalizationUtilsKt.lang("ui.stats.sp_def", new Object[0]);
    private static final MutableComponent defLabel = LocalizationUtilsKt.lang("ui.stats.def", new Object[0]);
    private static final MutableComponent speedLabel = LocalizationUtilsKt.lang("ui.stats.speed", new Object[0]);

    /*
     * WARNING - void declaration
     */
    public StatWidget(int pX, int pY, @NotNull Pokemon pokemon, int tabIndex) {
        void $this$mapNotNullTo$iv$iv;
        void $this$mapNotNull$iv;
        void $this$filterIsInstanceTo$iv$iv;
        Iterable $this$filterIsInstance$iv;
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        MutableComponent mutableComponent = Component.m_237113_((String)"StatWidget");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"literal(\"StatWidget\")");
        super(pX, pY, 134, 148, (Component)mutableComponent);
        this.pokemon = pokemon;
        this.statTabIndex = this.tabIndex = tabIndex;
        Iterable iterable = SpeciesFeatures.INSTANCE.getFeaturesFor(this.pokemon.getSpecies());
        StatWidget statWidget = this;
        boolean $i$f$filterIsInstance = false;
        void var7_8 = $this$filterIsInstance$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (!(element$iv$iv instanceof SynchronizedSpeciesFeatureProvider)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        $this$filterIsInstance$iv = (List)destination$iv$iv;
        boolean $i$f$mapNotNull = false;
        $this$filterIsInstanceTo$iv$iv = $this$mapNotNull$iv;
        destination$iv$iv = new ArrayList();
        boolean $i$f$mapNotNullTo = false;
        void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
        boolean $i$f$forEach = false;
        Iterator iterator = $this$forEach$iv$iv$iv.iterator();
        while (iterator.hasNext()) {
            SummarySpeciesFeatureRenderer it$iv$iv;
            Object element$iv$iv$iv;
            Object element$iv$iv = element$iv$iv$iv = iterator.next();
            boolean bl = false;
            SynchronizedSpeciesFeatureProvider it = (SynchronizedSpeciesFeatureProvider)element$iv$iv;
            boolean bl2 = false;
            if (it.getRenderer(this.pokemon) == null) continue;
            boolean bl3 = false;
            destination$iv$iv.add(it$iv$iv);
        }
        statWidget.renderableFeatures = (List)destination$iv$iv;
    }

    public /* synthetic */ StatWidget(int n, int n2, Pokemon pokemon, int n3, int n4, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n4 & 8) != 0) {
            n3 = 0;
        }
        this(n, n2, pokemon, n3);
    }

    @NotNull
    public final Pokemon getPokemon() {
        return this.pokemon;
    }

    public final int getTabIndex() {
        return this.tabIndex;
    }

    public final int getStatTabIndex() {
        return this.statTabIndex;
    }

    public final void setStatTabIndex(int n) {
        this.statTabIndex = n;
    }

    @NotNull
    public final List<SummarySpeciesFeatureRenderer<? extends SynchronizedSpeciesFeature>> getRenderableFeatures() {
        return this.renderableFeatures;
    }

    private final void drawTriangle(Vector3f colour, Vec2 v1, Vec2 v2, Vec2 v3) {
        ResourceLocation it = CobblemonResources.INSTANCE.getWHITE();
        boolean bl = false;
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)it);
        RenderSystem.setShaderColor((float)colour.x, (float)colour.y, (float)colour.z, (float)0.6f);
        BufferBuilder bufferBuilder = Tesselator.m_85913_().m_85915_();
        bufferBuilder.m_166779_(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.f_85814_);
        bufferBuilder.m_5483_((double)v1.f_82470_, (double)v1.f_82471_, 10.0).m_5752_();
        bufferBuilder.m_5483_((double)v2.f_82470_, (double)v2.f_82471_, 10.0).m_5752_();
        bufferBuilder.m_5483_((double)v3.f_82470_, (double)v3.f_82471_, 10.0).m_5752_();
        BufferUploader.m_231202_((BufferBuilder.RenderedBuffer)bufferBuilder.m_231175_());
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    private final void drawStatHexagon(Map<Stat, Integer> stats, Vector3f colour, int maximum) {
        double hexLeftX = (double)this.m_252754_() + 25.5;
        int hexTopY = this.m_252907_() + 22;
        double hexAttackY = (double)hexTopY + 24.5;
        double hexDefenceY = hexAttackY + 47.0;
        double hexBottomY = hexDefenceY + 24.5;
        double hexRightX = (double)this.m_252754_() + 108.5;
        double hexCenterX = (hexLeftX + hexRightX) / (double)2;
        double hexCenterY = ((double)hexTopY + hexBottomY) / (double)2;
        float minTriangleSize = 8.0f;
        float minXTriangleLen = (float)Math.sin(Math.toRadians(61.0)) * minTriangleSize * 0.95f;
        float minYTriangleLen = (float)Math.cos(Math.toRadians(60.0)) * minTriangleSize;
        float triangleLongEdge = (float)(hexCenterY - (double)hexTopY - (double)minTriangleSize);
        float triangleMediumEdge = (float)((double)triangleLongEdge * Math.sin(Math.toRadians(61.0)));
        float triangleShortEdge = (float)((double)triangleLongEdge * Math.cos(Math.toRadians(61.0)));
        float hpRatio = RangesKt.coerceIn((float)((float)((Number)stats.getOrDefault(Stats.HP, 0)).intValue() / (float)maximum), (float)0.0f, (float)1.0f);
        float atkRatio = RangesKt.coerceIn((float)((float)((Number)stats.getOrDefault(Stats.ATTACK, 0)).intValue() / (float)maximum), (float)0.0f, (float)1.0f);
        float defRatio = RangesKt.coerceIn((float)((float)((Number)stats.getOrDefault(Stats.DEFENCE, 0)).intValue() / (float)maximum), (float)0.0f, (float)1.0f);
        float spAtkRatio = RangesKt.coerceIn((float)((float)((Number)stats.getOrDefault(Stats.SPECIAL_ATTACK, 0)).intValue() / (float)maximum), (float)0.0f, (float)1.0f);
        float spDefRatio = RangesKt.coerceIn((float)((float)((Number)stats.getOrDefault(Stats.SPECIAL_DEFENCE, 0)).intValue() / (float)maximum), (float)0.0f, (float)1.0f);
        float spdRatio = RangesKt.coerceIn((float)((float)((Number)stats.getOrDefault(Stats.SPEED, 0)).intValue() / (float)maximum), (float)0.0f, (float)1.0f);
        Vec2 hpPoint = new Vec2((float)hexCenterX, (float)hexCenterY - minTriangleSize - hpRatio * triangleLongEdge);
        Vec2 attackPoint = new Vec2((float)hexCenterX + minXTriangleLen + atkRatio * triangleMediumEdge, (float)hexCenterY - minYTriangleLen - atkRatio * triangleShortEdge);
        Vec2 defencePoint = new Vec2((float)hexCenterX + minXTriangleLen + defRatio * triangleMediumEdge, (float)hexCenterY + minYTriangleLen + defRatio * triangleShortEdge);
        Vec2 specialAttackPoint = new Vec2((float)hexCenterX - minXTriangleLen - spAtkRatio * triangleMediumEdge, (float)hexCenterY - minYTriangleLen - spAtkRatio * triangleShortEdge);
        Vec2 specialDefencePoint = new Vec2((float)hexCenterX - minXTriangleLen - spDefRatio * triangleMediumEdge, (float)hexCenterY + minYTriangleLen + spDefRatio * triangleShortEdge);
        Vec2 speedPoint = new Vec2((float)hexCenterX, (float)hexCenterY + minTriangleSize + spdRatio * triangleLongEdge);
        Vec2 centerPoint = new Vec2((float)hexCenterX, (float)hexCenterY);
        this.drawTriangle(colour, hpPoint, centerPoint, attackPoint);
        this.drawTriangle(colour, attackPoint, centerPoint, defencePoint);
        this.drawTriangle(colour, defencePoint, centerPoint, speedPoint);
        this.drawTriangle(colour, speedPoint, centerPoint, specialDefencePoint);
        this.drawTriangle(colour, specialDefencePoint, centerPoint, specialAttackPoint);
        this.drawTriangle(colour, specialAttackPoint, centerPoint, hpPoint);
    }

    private final void drawFriendship(int moduleX, int moduleY, PoseStack matrices, GuiGraphics context, int friendship) {
        float barRatio = (float)friendship / 255.0f;
        int barWidth = Mth.m_14167_((float)(barRatio * (float)108));
        GuiUtilsKt.blitk$default(matrices, statsOtherBarTemplate, moduleX, moduleY, 28, 124, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
        int red = 1;
        Number green = this.pokemon.getFriendship() >= 160 ? (Number)0.28 : (Number)0.56;
        Number blue = this.pokemon.getFriendship() >= 160 ? (Number)0.4 : (Number)0.64;
        GuiUtilsKt.blitk$default(matrices, CobblemonResources.INSTANCE.getWHITE(), moduleX + 8, moduleY + 18, 8, barWidth, null, null, null, null, null, red, green, blue, null, false, 0.0f, 116672, null);
        GuiUtilsKt.blitk$default(matrices, friendshipOverlayResource, Float.valueOf((float)moduleX / 0.5f), Float.valueOf((float)(moduleY + 16) / 0.5f), 20, 248, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
        ResourceLocation resourceLocation = CobblemonResources.INSTANCE.getDEFAULT_LARGE();
        MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.stats.friendship", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"ui.stats.friendship\")");
        RenderHelperKt.drawScaledText$default(context, resourceLocation, TextKt.bold(mutableComponent), moduleX + 62, (double)moduleY + 2.5, 0.0f, null, 0, 0, true, true, null, null, 6624, null);
        RenderHelperKt.drawScaledText$default(context, null, TextKt.text(String.valueOf(friendship)), moduleX + 11, moduleY + 6, 0.5f, null, 0, 0, true, false, null, null, 7618, null);
        RenderHelperKt.drawScaledText$default(context, null, TextKt.text(Mth.m_14143_((float)(barRatio * (float)100)) + "%"), moduleX + 113, moduleY + 6, 0.5f, null, 0, 0, true, false, null, null, 7618, null);
    }

    /*
     * WARNING - void declaration
     */
    protected void m_87963_(@NotNull GuiGraphics context, int pMouseX, int pMouseY, float pPartialTicks) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        boolean renderChart = this.statTabIndex != 4;
        PoseStack matrices = context.m_280168_();
        ResourceLocation resourceLocation = renderChart ? statsBaseResource : statsOtherBaseResource;
        int n = this.m_252754_();
        int n2 = this.m_252907_();
        int n3 = this.f_93618_;
        int n4 = this.f_93619_;
        Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
        GuiUtilsKt.blitk$default(matrices, resourceLocation, n, n2, n4, n3, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
        if (renderChart) {
            resourceLocation = statsChartResource;
            double d = ((double)this.m_252754_() + 25.5) / (double)0.5f;
            float f = (float)(this.m_252907_() + 22) / 0.5f;
            GuiUtilsKt.blitk$default(matrices, resourceLocation, d, Float.valueOf(f), 192, 166, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
        }
        switch (this.statTabIndex) {
            case 0: {
                Pair[] pairArray = new Pair[]{TuplesKt.to((Object)Stats.HP, (Object)this.pokemon.getHp()), TuplesKt.to((Object)Stats.ATTACK, (Object)this.pokemon.getAttack()), TuplesKt.to((Object)Stats.DEFENCE, (Object)this.pokemon.getDefence()), TuplesKt.to((Object)Stats.SPECIAL_ATTACK, (Object)this.pokemon.getSpecialAttack()), TuplesKt.to((Object)Stats.SPECIAL_DEFENCE, (Object)this.pokemon.getSpecialDefence()), TuplesKt.to((Object)Stats.SPEED, (Object)this.pokemon.getSpeed())};
                this.drawStatHexagon(MapsKt.mapOf((Pair[])pairArray), new Vector3f(0.19607843f, 0.84313726f, 1.0f), 400);
                break;
            }
            case 3: {
                this.drawStatHexagon(this.pokemon.getForm().getBaseStats(), new Vector3f(1.0f, 0.41960785f, 0.19607843f), 200);
                break;
            }
            case 1: {
                boolean bl;
                Map.Entry it;
                Map map;
                void $this$associateTo$iv$iv;
                void $this$associate$iv;
                Iterable iterable = this.pokemon.getIvs();
                StatWidget statWidget = this;
                boolean bl2 = false;
                int capacity$iv = RangesKt.coerceAtLeast((int)MapsKt.mapCapacity((int)CollectionsKt.collectionSizeOrDefault((Iterable)$this$associate$iv, (int)10)), (int)16);
                void var11_26 = $this$associate$iv;
                Map destination$iv$iv = new LinkedHashMap(capacity$iv);
                boolean $i$f$associateTo = false;
                for (Object element$iv$iv : $this$associateTo$iv$iv) {
                    map = destination$iv$iv;
                    it = (Map.Entry)element$iv$iv;
                    bl = false;
                    it = TuplesKt.to(it.getKey(), it.getValue());
                    map.put(it.getFirst(), it.getSecond());
                }
                statWidget.drawStatHexagon(destination$iv$iv, new Vector3f(0.84705883f, 0.39215687f, 1.0f), 31);
                break;
            }
            case 2: {
                boolean bl;
                Map.Entry it;
                Map map;
                Iterable $this$associate$iv = this.pokemon.getEvs();
                StatWidget statWidget = this;
                boolean bl3 = false;
                int capacity$iv = RangesKt.coerceAtLeast((int)MapsKt.mapCapacity((int)CollectionsKt.collectionSizeOrDefault((Iterable)$this$associate$iv, (int)10)), (int)16);
                Iterable $this$associateTo$iv$iv = $this$associate$iv;
                Map destination$iv$iv = new LinkedHashMap(capacity$iv);
                boolean $i$f$associateTo = false;
                for (Object element$iv$iv : $this$associateTo$iv$iv) {
                    map = destination$iv$iv;
                    it = (Map.Entry)element$iv$iv;
                    bl = false;
                    Pair pair = TuplesKt.to(it.getKey(), it.getValue());
                    map.put(pair.getFirst(), pair.getSecond());
                }
                statWidget.drawStatHexagon(destination$iv$iv, new Vector3f(1.0f, 1.0f, 0.39215687f), 252);
            }
        }
        MutableComponent mutableComponent = statsLabel;
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"statsLabel");
        RenderHelperKt.drawScaledText$default(context, null, TextKt.bold(mutableComponent), this.m_252754_() + 29, this.m_252907_() + 143, 0.5f, null, 0, this.statTabIndex == 0 ? 0xFFFFFF : 0xAAAAAA, true, false, null, null, 7362, null);
        MutableComponent mutableComponent2 = ivLabel;
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent2, (String)"ivLabel");
        RenderHelperKt.drawScaledText$default(context, null, TextKt.bold(mutableComponent2), this.m_252754_() + 48, this.m_252907_() + 143, 0.5f, null, 0, this.statTabIndex == 1 ? 0xFFFFFF : 0xAAAAAA, true, false, null, null, 7362, null);
        MutableComponent mutableComponent3 = evLabel;
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent3, (String)"evLabel");
        RenderHelperKt.drawScaledText$default(context, null, TextKt.bold(mutableComponent3), this.m_252754_() + 67, this.m_252907_() + 143, 0.5f, null, 0, this.statTabIndex == 2 ? 0xFFFFFF : 0xAAAAAA, true, false, null, null, 7362, null);
        MutableComponent mutableComponent4 = baseLabel;
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent4, (String)"baseLabel");
        RenderHelperKt.drawScaledText$default(context, null, TextKt.bold(mutableComponent4), this.m_252754_() + 86, this.m_252907_() + 143, 0.5f, null, 0, this.statTabIndex == 3 ? 0xFFFFFF : 0xAAAAAA, true, false, null, null, 7362, null);
        MutableComponent mutableComponent5 = otherLabel;
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent5, (String)"otherLabel");
        RenderHelperKt.drawScaledText$default(context, null, TextKt.bold(mutableComponent5), this.m_252754_() + 105, this.m_252907_() + 143, 0.5f, null, 0, this.statTabIndex == 4 ? 0xFFFFFF : 0xAAAAAA, true, false, null, null, 7362, null);
        resourceLocation = context.m_280168_();
        ResourceLocation resourceLocation2 = tabMarkerResource;
        float f = (float)(this.m_252754_() + 27 + this.statTabIndex * 19) / 0.5f;
        float capacity$iv = (float)(this.m_252907_() + 140) / 0.5f;
        Intrinsics.checkNotNullExpressionValue((Object)resourceLocation, (String)"matrices");
        GuiUtilsKt.blitk$default((PoseStack)resourceLocation, resourceLocation2, Float.valueOf(f), Float.valueOf(capacity$iv), 4, 8, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
        if (renderChart) {
            MutableComponent mutableComponent6 = hpLabel;
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent6, (String)"hpLabel");
            MutableComponent mutableComponent7 = TextKt.bold(mutableComponent6);
            MutableComponent mutableComponent8 = spAtkLabel;
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent8, (String)"spAtkLabel");
            MutableComponent mutableComponent9 = TextKt.bold(mutableComponent8);
            MutableComponent mutableComponent10 = atkLabel;
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent10, (String)"atkLabel");
            MutableComponent mutableComponent11 = TextKt.bold(mutableComponent10);
            MutableComponent mutableComponent12 = spDefLabel;
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent12, (String)"spDefLabel");
            MutableComponent mutableComponent13 = TextKt.bold(mutableComponent12);
            MutableComponent mutableComponent14 = defLabel;
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent14, (String)"defLabel");
            MutableComponent mutableComponent15 = TextKt.bold(mutableComponent14);
            MutableComponent mutableComponent16 = speedLabel;
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent16, (String)"speedLabel");
            StatWidget.renderTextAtVertices$default(this, context, 0.0, false, mutableComponent7, mutableComponent9, mutableComponent11, mutableComponent13, mutableComponent15, TextKt.bold(mutableComponent16), 6, null);
            this.renderTextAtVertices(context, 5.5, false, this.getStatValueAsText(Stats.HP), this.getStatValueAsText(Stats.SPECIAL_ATTACK), this.getStatValueAsText(Stats.ATTACK), this.getStatValueAsText(Stats.SPECIAL_DEFENCE), this.getStatValueAsText(Stats.DEFENCE), this.getStatValueAsText(Stats.SPEED));
            if (this.statTabIndex == 0) {
                Nature nature = this.pokemon.getEffectiveNature();
                this.renderModifiedStatIcon(matrices, nature.getIncreasedStat(), true);
                this.renderModifiedStatIcon(matrices, nature.getDecreasedStat(), false);
            }
        } else {
            int drawY = this.m_252907_() + 11;
            this.drawFriendship(this.m_252754_() + 5, drawY, matrices, context, this.pokemon.getFriendship());
            drawY += 30;
            for (SummarySpeciesFeatureRenderer summarySpeciesFeatureRenderer : this.renderableFeatures) {
                boolean rendered = summarySpeciesFeatureRenderer.render(context, (float)this.m_252754_() + 5.0f, drawY, this.pokemon);
                if (!rendered) continue;
                drawY += 30;
            }
        }
    }

    @Override
    public boolean m_6375_(double pMouseX, double pMouseY, int pButton) {
        int index = this.getTabIndexFromPos(pMouseX, pMouseY);
        boolean bl = 0 <= index ? index < 5 : false;
        if (bl) {
            this.statTabIndex = index;
            Minecraft.m_91087_().m_91106_().m_120367_((SoundInstance)SimpleSoundInstance.m_119752_((SoundEvent)CobblemonSounds.GUI_CLICK, (float)1.0f));
        }
        return super.m_6375_(pMouseX, pMouseY, pButton);
    }

    private final MutableComponent getStatValueAsText(Stat stat) {
        String value2 = switch (this.statTabIndex) {
            case 0 -> {
                if (stat == Stats.HP) {
                    yield this.pokemon.getCurrentHealth() + " / " + this.pokemon.getHp();
                }
                yield String.valueOf(this.pokemon.getStat(stat));
            }
            case 3 -> String.valueOf(this.pokemon.getForm().getBaseStats().get(stat));
            case 1 -> String.valueOf(this.pokemon.getIvs().getOrDefault(stat));
            case 2 -> String.valueOf(this.pokemon.getEvs().getOrDefault(stat));
            default -> "0";
        };
        return TextKt.text(value2);
    }

    private final void renderModifiedStatIcon(PoseStack pMatrixStack, Stat stat, boolean increasedStat) {
        if (stat != null) {
            double posX = this.m_252754_();
            double posY = this.m_252907_();
            Stat stat2 = stat;
            if (stat2 == Stats.HP) {
                posX += (double)65;
                posY += (double)6;
            } else if (stat2 == Stats.SPECIAL_ATTACK) {
                posX += (double)10;
                posY += (double)38;
            } else if (stat2 == Stats.ATTACK) {
                posX += (double)120;
                posY += (double)38;
            } else if (stat2 == Stats.SPECIAL_DEFENCE) {
                posX += (double)10;
                posY += (double)89;
            } else if (stat2 == Stats.DEFENCE) {
                posX += (double)120;
                posY += (double)89;
            } else if (stat2 == Stats.SPEED) {
                posX += (double)65;
                posY += (double)120;
            }
            stat2 = increasedStat ? statIncreaseResource : statDecreaseResource;
            double d = posX / (double)0.5f;
            double d2 = posY / (double)0.5f;
            GuiUtilsKt.blitk$default(pMatrixStack, (ResourceLocation)stat2, d, d2, 6, 8, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
        }
    }

    private final int getModifiedStatColour(Stat stat, boolean enableColour) {
        if (this.statTabIndex == 0 && enableColour) {
            Nature nature = this.pokemon.getEffectiveNature();
            if (Intrinsics.areEqual((Object)nature.getIncreasedStat(), (Object)stat)) {
                return 16471124;
            }
            if (Intrinsics.areEqual((Object)nature.getDecreasedStat(), (Object)stat)) {
                return 5540859;
            }
        }
        return 0xFFFFFF;
    }

    private final void renderTextAtVertices(GuiGraphics context, double offsetY, boolean enableColour, MutableComponent hp, MutableComponent spAtk, MutableComponent atk, MutableComponent spDef, MutableComponent def, MutableComponent speed) {
        RenderHelperKt.drawScaledText$default(context, null, hp, this.m_252754_() + 67, (double)this.m_252907_() + 10.5 + offsetY, 0.5f, null, 0, this.getModifiedStatColour(Stats.HP, enableColour), true, false, null, null, 7362, null);
        RenderHelperKt.drawScaledText$default(context, null, spAtk, this.m_252754_() + 12, (double)this.m_252907_() + 42.5 + offsetY, 0.5f, null, 0, this.getModifiedStatColour(Stats.SPECIAL_ATTACK, enableColour), true, false, null, null, 7362, null);
        RenderHelperKt.drawScaledText$default(context, null, atk, this.m_252754_() + 122, (double)this.m_252907_() + 42.5 + offsetY, 0.5f, null, 0, this.getModifiedStatColour(Stats.ATTACK, enableColour), true, false, null, null, 7362, null);
        RenderHelperKt.drawScaledText$default(context, null, spDef, this.m_252754_() + 12, (double)this.m_252907_() + 93.5 + offsetY, 0.5f, null, 0, this.getModifiedStatColour(Stats.SPECIAL_DEFENCE, enableColour), true, false, null, null, 7362, null);
        RenderHelperKt.drawScaledText$default(context, null, def, this.m_252754_() + 122, (double)this.m_252907_() + 93.5 + offsetY, 0.5f, null, 0, this.getModifiedStatColour(Stats.DEFENCE, enableColour), true, false, null, null, 7362, null);
        RenderHelperKt.drawScaledText$default(context, null, speed, this.m_252754_() + 67, (double)this.m_252907_() + 124.5 + offsetY, 0.5f, null, 0, this.getModifiedStatColour(Stats.SPEED, enableColour), true, false, null, null, 7362, null);
    }

    static /* synthetic */ void renderTextAtVertices$default(StatWidget statWidget, GuiGraphics guiGraphics, double d, boolean bl, MutableComponent mutableComponent, MutableComponent mutableComponent2, MutableComponent mutableComponent3, MutableComponent mutableComponent4, MutableComponent mutableComponent5, MutableComponent mutableComponent6, int n, Object object) {
        if ((n & 2) != 0) {
            d = 0.0;
        }
        if ((n & 4) != 0) {
            bl = true;
        }
        statWidget.renderTextAtVertices(guiGraphics, d, bl, mutableComponent, mutableComponent2, mutableComponent3, mutableComponent4, mutableComponent5, mutableComponent6);
    }

    private final int getTabIndexFromPos(double mouseX, double mouseY) {
        double left = (double)this.m_252754_() + 19.5;
        double top = (double)this.m_252907_() + 140.0;
        boolean bl = left <= mouseX ? mouseX <= left + 95.0 : false;
        if (bl) {
            boolean bl2 = top <= mouseY ? mouseY <= top + 9.0 : false;
            if (bl2) {
                double startX = left;
                double endX = left + (double)19;
                for (int index = 0; index < 5; ++index) {
                    boolean bl3 = startX <= mouseX ? mouseX <= endX : false;
                    if (bl3) {
                        return index;
                    }
                    startX += (double)19;
                    endX += (double)19;
                }
            }
        }
        return -1;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0013\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b*\u0010+R\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0004R\u0014\u0010\u0007\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0007\u0010\u0004R\u0014\u0010\b\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\b\u0010\u0004R\u0014\u0010\t\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\t\u0010\u0004R\u0014\u0010\n\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\n\u0010\u0004R\u0014\u0010\u000b\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\u0004R\u0014\u0010\r\u001a\u00020\f8\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\u0004R\u0014\u0010\u0010\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\u0004R\u0014\u0010\u0011\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\u0004R\u001c\u0010\u0014\u001a\n \u0013*\u0004\u0018\u00010\u00120\u00128\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\u0015R\u001c\u0010\u0016\u001a\n \u0013*\u0004\u0018\u00010\u00120\u00128\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0016\u0010\u0015R\u001c\u0010\u0017\u001a\n \u0013*\u0004\u0018\u00010\u00120\u00128\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0017\u0010\u0015R\u001c\u0010\u0018\u001a\n \u0013*\u0004\u0018\u00010\u00120\u00128\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0018\u0010\u0015R\u0014\u0010\u001a\u001a\u00020\u00198\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001a\u0010\u001bR\u001c\u0010\u001c\u001a\n \u0013*\u0004\u0018\u00010\u00120\u00128\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001c\u0010\u0015R\u001c\u0010\u001d\u001a\n \u0013*\u0004\u0018\u00010\u00120\u00128\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001d\u0010\u0015R\u001c\u0010\u001e\u001a\n \u0013*\u0004\u0018\u00010\u00120\u00128\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001e\u0010\u0015R\u001c\u0010\u001f\u001a\n \u0013*\u0004\u0018\u00010\u00120\u00128\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001f\u0010\u0015R\u001c\u0010 \u001a\n \u0013*\u0004\u0018\u00010\u00120\u00128\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b \u0010\u0015R\u001c\u0010!\u001a\n \u0013*\u0004\u0018\u00010\u00120\u00128\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b!\u0010\u0015R\u0014\u0010\"\u001a\u00020\u00198\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\"\u0010\u001bR\u0014\u0010#\u001a\u00020\u00198\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b#\u0010\u001bR\u0014\u0010$\u001a\u00020\u00198\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b$\u0010\u001bR\u0014\u0010%\u001a\u00020\u00198\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b%\u0010\u001bR\u001c\u0010&\u001a\n \u0013*\u0004\u0018\u00010\u00120\u00128\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b&\u0010\u0015R\u0014\u0010'\u001a\u00020\u00198\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b'\u0010\u001bR\u0014\u0010(\u001a\u00020\u00198\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b(\u0010\u001bR\u0014\u0010)\u001a\u00020\u00198\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b)\u0010\u001b\u00a8\u0006,"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/stats/StatWidget$Companion;", "", "", "BASE", "I", "BLUE", "EV", "GREY", "HEIGHT", "IV", "OTHER", "RED", "", "SCALE", "F", "STATS", "WHITE", "WIDTH", "Lnet/minecraft/network/chat/MutableComponent;", "kotlin.jvm.PlatformType", "atkLabel", "Lnet/minecraft/network/chat/MutableComponent;", "baseLabel", "defLabel", "evLabel", "Lnet/minecraft/resources/ResourceLocation;", "friendshipOverlayResource", "Lnet/minecraft/resources/ResourceLocation;", "hpLabel", "ivLabel", "otherLabel", "spAtkLabel", "spDefLabel", "speedLabel", "statDecreaseResource", "statIncreaseResource", "statsBaseResource", "statsChartResource", "statsLabel", "statsOtherBarTemplate", "statsOtherBaseResource", "tabMarkerResource", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

