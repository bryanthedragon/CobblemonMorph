/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.screens.info;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.MultiLineLabelK;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.SoundlessWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.common.NatureInfoUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.screens.info.InfoOneLineWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u001f\u0012\u0006\u0010\u000f\u001a\u00020\u0004\u0012\u0006\u0010\u0010\u001a\u00020\u0004\u0012\u0006\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\u0011\u0010\u0012J/\u0010\n\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007H\u0014\u00a2\u0006\u0004\b\n\u0010\u000bR\u0014\u0010\r\u001a\u00020\f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\r\u0010\u000e\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/info/InfoWidget;", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/SoundlessWidget;", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "pMouseX", "pMouseY", "", "pPartialTicks", "", "renderButton", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pX", "pY", "<init>", "(IILcom/cobblemon/mod/common/pokemon/Pokemon;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nInfoWidget.kt\nKotlin\n*S Kotlin\n*F\n+ 1 InfoWidget.kt\ncom/cobblemon/mod/common/client/gui/summary/widgets/screens/info/InfoWidget\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,208:1\n1549#2:209\n1620#2,3:210\n2661#2,7:213\n*S KotlinDebug\n*F\n+ 1 InfoWidget.kt\ncom/cobblemon/mod/common/client/gui/summary/widgets/screens/info/InfoWidget\n*L\n82#1:209\n82#1:210,3\n82#1:213,7\n*E\n"})
public final class InfoWidget
extends SoundlessWidget {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Pokemon pokemon;
    private static final int WIDTH = 134;
    private static final int HEIGHT = 148;
    @NotNull
    private static final ResourceLocation infoBaseResource = MiscUtils.cobblemonResource("textures/gui/summary/summary_info_base.png");
    private static final int ROW_HEIGHT = 15;

    public InfoWidget(int pX, int pY, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        MutableComponent mutableComponent = Component.m_237113_((String)"InfoWidget");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"literal(\"InfoWidget\")");
        super(pX, pY, 134, 148, (Component)mutableComponent);
        this.pokemon = pokemon;
    }

    /*
     * WARNING - void declaration
     */
    protected void m_87963_(@NotNull GuiGraphics context, int pMouseX, int pMouseY, float pPartialTicks) {
        void $this$reduce$iv;
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        PoseStack matrices = context.m_280168_();
        ResourceLocation resourceLocation = infoBaseResource;
        int n = this.m_252754_();
        int n2 = this.m_252907_();
        int n3 = this.f_93618_;
        int n4 = this.f_93619_;
        Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
        GuiUtilsKt.blitk$default(matrices, resourceLocation, n, n2, n4, n3, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
        Object dexNo = String.valueOf(this.pokemon.getSpecies().getNationalPokedexNumber());
        while (((String)dexNo).length() < 3) {
            dexNo = "0" + (String)dexNo;
        }
        int n5 = this.m_252754_();
        int n6 = this.m_252907_();
        MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.info.pokedex_number", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"ui.info.pokedex_number\")");
        InfoOneLineWidget pokedexNumberWidget = new InfoOneLineWidget(n5, n6, this.f_93618_, 0, mutableComponent, TextKt.text((String)dexNo), 8, null);
        pokedexNumberWidget.m_88315_(context, pMouseX, pMouseY, pPartialTicks);
        int n7 = this.m_252754_();
        int n8 = this.m_252907_() + 15;
        MutableComponent mutableComponent2 = LocalizationUtilsKt.lang("ui.info.species", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent2, (String)"lang(\"ui.info.species\")");
        InfoOneLineWidget speciesWidget = new InfoOneLineWidget(n7, n8, this.f_93618_, 0, mutableComponent2, this.pokemon.getSpecies().getTranslatedName(), 8, null);
        speciesWidget.m_88315_(context, pMouseX, pMouseY, pPartialTicks);
        Iterable $this$map$iv = this.pokemon.getTypes();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, (int)10));
        boolean $i$f$mapTo2 = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            ElementalType elementalType = (ElementalType)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(it.getDisplayName().m_6881_());
        }
        $this$map$iv = (List)destination$iv$iv;
        boolean $i$f$reduce = false;
        Iterator iterator$iv = $this$reduce$iv.iterator();
        if (!iterator$iv.hasNext()) {
            throw new UnsupportedOperationException("Empty collection can't be reduced.");
        }
        Object accumulator$iv = iterator$iv.next();
        while (iterator$iv.hasNext()) {
            void next;
            MutableComponent $i$f$mapTo2 = (MutableComponent)iterator$iv.next();
            MutableComponent acc = (MutableComponent)accumulator$iv;
            boolean bl = false;
            Intrinsics.checkNotNullExpressionValue((Object)acc, (String)"acc");
            MutableComponent mutableComponent3 = TextKt.plus(acc, "/");
            Intrinsics.checkNotNullExpressionValue((Object)next, (String)"next");
            accumulator$iv = TextKt.plus(mutableComponent3, (Component)next);
        }
        MutableComponent type = (MutableComponent)accumulator$iv;
        int n9 = this.m_252754_();
        int n10 = this.m_252907_() + 30;
        MutableComponent mutableComponent4 = LocalizationUtilsKt.lang("ui.info.type", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent4, (String)"lang(\"ui.info.type\")");
        Intrinsics.checkNotNullExpressionValue((Object)type, (String)"type");
        InfoOneLineWidget typeWidget = new InfoOneLineWidget(n9, n10, this.f_93618_, 0, mutableComponent4, type, 8, null);
        typeWidget.m_88315_(context, pMouseX, pMouseY, pPartialTicks);
        String string = this.pokemon.getOriginalTrainerName();
        if (string == null) {
            string = "";
        }
        MutableComponent mutableComponent5 = Component.m_237113_((String)string);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent5, (String)"literal(pokemon.originalTrainerName ?: \"\")");
        MutableComponent otName = mutableComponent5;
        int n11 = this.m_252754_();
        int n12 = this.m_252907_() + 45;
        MutableComponent mutableComponent6 = LocalizationUtilsKt.lang("ui.info.original_trainer", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent6, (String)"lang(\"ui.info.original_trainer\")");
        InfoOneLineWidget otWidget = new InfoOneLineWidget(n11, n12, this.f_93618_, 0, mutableComponent6, otName, 8, null);
        otWidget.m_88315_(context, pMouseX, pMouseY, pPartialTicks);
        MutableComponent natureText = NatureInfoUtilsKt.reformatNatureTextIfMinted(this.pokemon);
        int n13 = this.m_252754_();
        int n14 = this.m_252907_() + 60;
        MutableComponent mutableComponent7 = LocalizationUtilsKt.lang("ui.info.nature", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent7, (String)"lang(\"ui.info.nature\")");
        InfoOneLineWidget natureWidget = new InfoOneLineWidget(n13, n14, this.f_93618_, 0, mutableComponent7, natureText, 8, null);
        natureWidget.m_88315_(context, pMouseX, pMouseY, pPartialTicks);
        int n15 = this.m_252754_();
        int n16 = this.m_252907_() + 75;
        MutableComponent mutableComponent8 = LocalizationUtilsKt.lang("ui.info.ability", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent8, (String)"lang(\"ui.info.ability\")");
        MutableComponent mutableComponent9 = TextKt.bold(mutableComponent8);
        MutableComponent mutableComponent10 = MiscUtils.asTranslated(this.pokemon.getAbility().getDisplayName());
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent10, (String)"pokemon.ability.displayName.asTranslated()");
        InfoOneLineWidget abilityWidget = new InfoOneLineWidget(n15, n16, this.f_93618_, 0, mutableComponent9, TextKt.bold(mutableComponent10), 8, null);
        abilityWidget.m_88315_(context, pMouseX, pMouseY, pPartialTicks);
        float smallTextScale = 0.5f;
        matrices.m_85836_();
        matrices.m_85841_(smallTextScale, smallTextScale, 1.0f);
        MutableComponent mutableComponent11 = MiscUtils.asTranslated(this.pokemon.getAbility().getDescription());
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent11, (String)"pokemon.ability.description.asTranslated()");
        MultiLineLabelK.Companion.create((Component)mutableComponent11, Float.valueOf((float)117 / smallTextScale), 3).renderLeftAligned(context, Float.valueOf((float)(this.m_252754_() + 8) / smallTextScale), ((double)this.m_252907_() + 94.5) / (double)smallTextScale, 5.5 / (double)smallTextScale, 0xFFFFFF, true);
        matrices.m_85849_();
        MutableComponent mutableComponent12 = LocalizationUtilsKt.lang("ui.info.experience_points", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent12, (String)"lang(\"ui.info.experience_points\")");
        RenderHelperKt.drawScaledText$default(context, null, mutableComponent12, (double)this.m_252754_() + 72.5, this.m_252907_() + 125, smallTextScale, null, 0, 0, false, true, null, null, 7106, null);
        MutableComponent mutableComponent13 = LocalizationUtilsKt.lang("ui.info.to_next_level", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent13, (String)"lang(\"ui.info.to_next_level\")");
        RenderHelperKt.drawScaledText$default(context, null, mutableComponent13, (double)this.m_252754_() + 72.5, this.m_252907_() + 137, smallTextScale, null, 0, 0, false, true, null, null, 7106, null);
        Font mcFont = Minecraft.m_91087_().f_91062_;
        MutableComponent experience = TextKt.text(String.valueOf(this.pokemon.getExperience()));
        int experienceForThisLevel = this.pokemon.getExperience() - (this.pokemon.getLevel() == 1 ? 0 : this.pokemon.getExperienceGroup().getExperience(this.pokemon.getLevel()));
        int experienceToNext = this.pokemon.getExperienceGroup().getExperience(this.pokemon.getLevel() + 1) - this.pokemon.getExperienceGroup().getExperience(this.pokemon.getLevel());
        RenderHelperKt.drawScaledText$default(context, null, experience, Float.valueOf((float)(this.m_252754_() + 127) - (float)mcFont.m_92852_((FormattedText)experience) * smallTextScale), this.m_252907_() + 125, smallTextScale, null, 0, 0, false, true, null, null, 7106, null);
        RenderHelperKt.drawScaledText$default(context, null, TextKt.text(String.valueOf(experienceToNext)), Float.valueOf((float)(this.m_252754_() + 127) - (float)mcFont.m_92852_((FormattedText)TextKt.text(String.valueOf(experienceToNext))) * smallTextScale), this.m_252907_() + 137, smallTextScale, null, 0, 0, false, true, null, null, 7106, null);
        float expRatio = (float)experienceForThisLevel / (float)experienceToNext;
        int expBarWidthMax = 55;
        float expBarWidth = expRatio * (float)expBarWidthMax;
        ResourceLocation resourceLocation2 = CobblemonResources.INSTANCE.getWHITE();
        int n17 = this.m_252754_() + 72;
        int n18 = this.m_252907_() + 131;
        float f = expBarWidth / expRatio;
        float f2 = (float)expBarWidthMax - expBarWidth;
        GuiUtilsKt.blitk$default(matrices, resourceLocation2, n17, n18, 1, Float.valueOf(expBarWidth), Float.valueOf(f2), null, Float.valueOf(f), null, null, 0.2, 0.65, 0.84, null, false, 0.0f, 116352, null);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\n\u0010\u000bR\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0004R\u0014\u0010\b\u001a\u00020\u00078\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\b\u0010\t\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/info/InfoWidget$Companion;", "", "", "HEIGHT", "I", "ROW_HEIGHT", "WIDTH", "Lnet/minecraft/resources/ResourceLocation;", "infoBaseResource", "Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

