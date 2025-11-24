/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.featurerenderers;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.IntSpeciesFeature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.featurerenderers.SummarySpeciesFeatureRenderer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001BG\u0012\u0006\u0010 \u001a\u00020\u001f\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u0012\u0006\u0010\u001d\u001a\u00020\u0018\u0012\u0006\u0010\u0019\u001a\u00020\u0018\u0012\u0006\u0010\u000f\u001a\u00020\u000e\u0012\u0006\u0010,\u001a\u00020$\u0012\u0006\u0010%\u001a\u00020$\u0012\u0006\u0010\t\u001a\u00020\b\u00a2\u0006\u0004\b.\u0010/J7\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\f\u0010\rR\u0017\u0010\u000f\u001a\u00020\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0014\u001a\u00020\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0019\u001a\u00020\u00188\u0006\u00a2\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001cR\u0017\u0010\u001d\u001a\u00020\u00188\u0006\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001a\u001a\u0004\b\u001e\u0010\u001cR\u001a\u0010 \u001a\u00020\u001f8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b \u0010!\u001a\u0004\b\"\u0010#R\u0017\u0010%\u001a\u00020$8\u0006\u00a2\u0006\f\n\u0004\b%\u0010&\u001a\u0004\b'\u0010(R\u0017\u0010\t\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\t\u0010)\u001a\u0004\b*\u0010+R\u0017\u0010,\u001a\u00020$8\u0006\u00a2\u0006\f\n\u0004\b,\u0010&\u001a\u0004\b-\u0010(\u00a8\u00060"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/featurerenderers/BarSummarySpeciesFeatureRenderer;", "Lcom/cobblemon/mod/common/client/gui/summary/featurerenderers/SummarySpeciesFeatureRenderer;", "Lcom/cobblemon/mod/common/api/pokemon/feature/IntSpeciesFeature;", "Lnet/minecraft/client/gui/GuiGraphics;", "drawContext", "", "x", "y", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "feature", "", "render", "(Lnet/minecraft/client/gui/GuiGraphics;FFLcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/api/pokemon/feature/IntSpeciesFeature;)V", "Lnet/minecraft/world/phys/Vec3;", "colour", "Lnet/minecraft/world/phys/Vec3;", "getColour", "()Lnet/minecraft/world/phys/Vec3;", "Lnet/minecraft/network/chat/MutableComponent;", "displayName", "Lnet/minecraft/network/chat/MutableComponent;", "getDisplayName", "()Lnet/minecraft/network/chat/MutableComponent;", "", "max", "I", "getMax", "()I", "min", "getMin", "", "name", "Ljava/lang/String;", "getName", "()Ljava/lang/String;", "Lnet/minecraft/resources/ResourceLocation;", "overlay", "Lnet/minecraft/resources/ResourceLocation;", "getOverlay", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "underlay", "getUnderlay", "<init>", "(Ljava/lang/String;Lnet/minecraft/network/chat/MutableComponent;IILnet/minecraft/world/phys/Vec3;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "common"})
public final class BarSummarySpeciesFeatureRenderer
implements SummarySpeciesFeatureRenderer<IntSpeciesFeature> {
    @NotNull
    private final String name;
    @NotNull
    private final MutableComponent displayName;
    private final int min;
    private final int max;
    @NotNull
    private final Vec3 colour;
    @NotNull
    private final ResourceLocation underlay;
    @NotNull
    private final ResourceLocation overlay;
    @NotNull
    private final Pokemon pokemon;

    public BarSummarySpeciesFeatureRenderer(@NotNull String name, @NotNull MutableComponent displayName, int min2, int max2, @NotNull Vec3 colour, @NotNull ResourceLocation underlay, @NotNull ResourceLocation overlay2, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)displayName, (String)"displayName");
        Intrinsics.checkNotNullParameter((Object)colour, (String)"colour");
        Intrinsics.checkNotNullParameter((Object)underlay, (String)"underlay");
        Intrinsics.checkNotNullParameter((Object)overlay2, (String)"overlay");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        this.name = name;
        this.displayName = displayName;
        this.min = min2;
        this.max = max2;
        this.colour = colour;
        this.underlay = underlay;
        this.overlay = overlay2;
        this.pokemon = pokemon;
    }

    @Override
    @NotNull
    public String getName() {
        return this.name;
    }

    @NotNull
    public final MutableComponent getDisplayName() {
        return this.displayName;
    }

    public final int getMin() {
        return this.min;
    }

    public final int getMax() {
        return this.max;
    }

    @NotNull
    public final Vec3 getColour() {
        return this.colour;
    }

    @NotNull
    public final ResourceLocation getUnderlay() {
        return this.underlay;
    }

    @NotNull
    public final ResourceLocation getOverlay() {
        return this.overlay;
    }

    @NotNull
    public final Pokemon getPokemon() {
        return this.pokemon;
    }

    @Override
    public void render(@NotNull GuiGraphics drawContext, float x, float y, @NotNull Pokemon pokemon, @NotNull IntSpeciesFeature feature) {
        Intrinsics.checkNotNullParameter((Object)drawContext, (String)"drawContext");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)feature, (String)"feature");
        int value2 = feature.getValue();
        float barRatio = (float)(value2 - this.min) / (float)(this.max - this.min);
        int barWidth = Mth.m_14167_((float)(barRatio * (float)108));
        PoseStack poseStack = drawContext.m_280168_();
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"drawContext.matrices");
        GuiUtilsKt.blitk$default(poseStack, this.underlay, Float.valueOf(x), Float.valueOf(y), 28, 124, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
        double red = this.colour.f_82479_ / (double)255.0f;
        double green = this.colour.f_82480_ / (double)255.0f;
        double blue = this.colour.f_82481_ / (double)255.0f;
        PoseStack poseStack2 = drawContext.m_280168_();
        Intrinsics.checkNotNullExpressionValue((Object)poseStack2, (String)"drawContext.matrices");
        GuiUtilsKt.blitk$default(poseStack2, CobblemonResources.INSTANCE.getWHITE(), Float.valueOf(x + (float)8), Float.valueOf(y + (float)16), 10, barWidth, null, null, null, null, null, red, green, blue, null, false, 0.0f, 116672, null);
        PoseStack poseStack3 = drawContext.m_280168_();
        Intrinsics.checkNotNullExpressionValue((Object)poseStack3, (String)"drawContext.matrices");
        GuiUtilsKt.blitk$default(poseStack3, this.overlay, Float.valueOf(x / 0.5f), Float.valueOf((y + (float)16) / 0.5f), 20, 248, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
        RenderHelperKt.drawScaledText$default(drawContext, CobblemonResources.INSTANCE.getDEFAULT_LARGE(), TextKt.bold(this.displayName), Float.valueOf(x + (float)62), (double)y + 2.5, 0.0f, null, 0, 0, true, true, null, null, 6624, null);
        RenderHelperKt.drawScaledText$default(drawContext, null, TextKt.text(String.valueOf(value2)), Float.valueOf(x + (float)11), Float.valueOf(y + (float)6), 0.5f, null, 0, 0, true, false, null, null, 7618, null);
        RenderHelperKt.drawScaledText$default(drawContext, null, TextKt.text(Mth.m_14143_((float)(barRatio * (float)100)) + "%"), Float.valueOf(x + (float)113), Float.valueOf(y + (float)6), 0.5f, null, 0, 0, true, false, null, null, 7618, null);
    }

    @Override
    public boolean render(@NotNull GuiGraphics drawContext, float x, float y, @NotNull Pokemon pokemon) {
        return SummarySpeciesFeatureRenderer.DefaultImpls.render(this, drawContext, x, y, pokemon);
    }
}

