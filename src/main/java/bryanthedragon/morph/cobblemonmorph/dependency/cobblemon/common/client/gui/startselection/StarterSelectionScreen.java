/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.Triple
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.components.toasts.Toast$Visibility
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.startselection;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.MultiLineLabelK;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.startselection.widgets.CategoryList;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.startselection.widgets.ExitButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.startselection.widgets.preview.ArrowButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.startselection.widgets.preview.SelectionButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.startselection.widgets.preview.StarterRoundabout;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.ModelWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.type.DualTypeWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.type.SingleTypeWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.type.TypeWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.starter.RenderableStarterCategory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.SelectStarterPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.RenderablePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.SimpleMathExtensionsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 62\u00020\u0001:\u00016B\u0015\u0012\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00020\"\u00a2\u0006\u0004\b4\u00105J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u000f\u0010\u0007\u001a\u00020\u0004H\u0014\u00a2\u0006\u0004\b\u0007\u0010\bJ\u000f\u0010\t\u001a\u00020\u0004H\u0002\u00a2\u0006\u0004\b\t\u0010\bJ\u000f\u0010\u000b\u001a\u00020\nH\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ/\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u000f\u0010\u0015\u001a\u00020\u0004H\u0002\u00a2\u0006\u0004\b\u0015\u0010\bJ\u000f\u0010\u0016\u001a\u00020\nH\u0002\u00a2\u0006\u0004\b\u0016\u0010\fJ\u000f\u0010\u0018\u001a\u00020\u0017H\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019J'\u0010\u001f\u001a\u00020\u001e2\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020\nH\u0002\u00a2\u0006\u0004\b\u001f\u0010 J\u000f\u0010!\u001a\u00020\u0004H\u0002\u00a2\u0006\u0004\b!\u0010\bR\u001a\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00020\"8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b#\u0010$R\u0016\u0010%\u001a\u00020\u00028\u0002@\u0002X\u0082.\u00a2\u0006\u0006\n\u0004\b%\u0010&R\u0016\u0010'\u001a\u00020\u001a8\u0002@\u0002X\u0082.\u00a2\u0006\u0006\n\u0004\b'\u0010(R\u0016\u0010)\u001a\u00020\n8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b)\u0010*R\u0016\u0010,\u001a\u00020+8\u0002@\u0002X\u0082.\u00a2\u0006\u0006\n\u0004\b,\u0010-R\u0016\u0010/\u001a\u00020.8\u0002@\u0002X\u0082.\u00a2\u0006\u0006\n\u0004\b/\u00100R\u0016\u00101\u001a\u00020.8\u0002@\u0002X\u0082.\u00a2\u0006\u0006\n\u0004\b1\u00100R\u0016\u00102\u001a\u00020.8\u0002@\u0002X\u0082.\u00a2\u0006\u0006\n\u0004\b2\u00100R\u0016\u0010\u001f\u001a\u00020\u001e8\u0002@\u0002X\u0082.\u00a2\u0006\u0006\n\u0004\b\u001f\u00103\u00a8\u00067"}, d2={"Lcom/cobblemon/mod/common/client/gui/startselection/StarterSelectionScreen;", "Lnet/minecraft/client/gui/screens/Screen;", "Lcom/cobblemon/mod/common/config/starter/RenderableStarterCategory;", "category", "", "changeCategory", "(Lcom/cobblemon/mod/common/config/starter/RenderableStarterCategory;)V", "init", "()V", "left", "", "leftOfCurrentSelection", "()I", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "mouseX", "mouseY", "", "delta", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "right", "rightOfCurrentSelection", "", "shouldPause", "()Z", "Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;", "pokemon", "x", "y", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/type/TypeWidget;", "typeWidget", "(Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;II)Lcom/cobblemon/mod/common/client/gui/summary/widgets/type/TypeWidget;", "updateSelection", "", "categories", "Ljava/util/List;", "currentCategory", "Lcom/cobblemon/mod/common/config/starter/RenderableStarterCategory;", "currentPokemon", "Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;", "currentSelection", "I", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/ModelWidget;", "modelWidget", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/ModelWidget;", "Lcom/cobblemon/mod/common/client/gui/startselection/widgets/preview/StarterRoundabout;", "starterRoundaboutCenter", "Lcom/cobblemon/mod/common/client/gui/startselection/widgets/preview/StarterRoundabout;", "starterRoundaboutLeft", "starterRoundaboutRight", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/type/TypeWidget;", "<init>", "(Ljava/util/List;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nStarterSelectionScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StarterSelectionScreen.kt\ncom/cobblemon/mod/common/client/gui/startselection/StarterSelectionScreen\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,328:1\n1#2:329\n*E\n"})
public final class StarterSelectionScreen
extends Screen {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final List<RenderableStarterCategory> categories;
    private int currentSelection;
    private RenderableStarterCategory currentCategory;
    private ModelWidget modelWidget;
    private RenderablePokemon currentPokemon;
    private TypeWidget typeWidget;
    private StarterRoundabout starterRoundaboutCenter;
    private StarterRoundabout starterRoundaboutLeft;
    private StarterRoundabout starterRoundaboutRight;
    private static final int BASE_WIDTH = 200;
    private static final int BASE_HEIGHT = 175;
    @NotNull
    private static final ResourceLocation base = MiscUtils.cobblemonResource("textures/gui/starterselection/starterselection_base.png");
    @NotNull
    private static final ResourceLocation baseUnderlay = MiscUtils.cobblemonResource("textures/gui/starterselection/starterselection_base_underlay.png");
    @NotNull
    private static final ResourceLocation baseFrame = MiscUtils.cobblemonResource("textures/gui/starterselection/starterselection_base_frame.png");
    @NotNull
    private static final ResourceLocation singleTypeBackground = MiscUtils.cobblemonResource("textures/gui/starterselection/starterselection_type_slot1.png");
    @NotNull
    private static final ResourceLocation doubleTypeBackground = MiscUtils.cobblemonResource("textures/gui/starterselection/starterselection_type_slot2.png");

    public StarterSelectionScreen(@NotNull List<RenderableStarterCategory> categories) {
        Intrinsics.checkNotNullParameter(categories, (String)"categories");
        super((Component)MiscUtils.asTranslated("cobblemon.ui.starter.title"));
        this.categories = categories;
    }

    protected void m_7856_() {
        ModelWidget modelWidget;
        RenderablePokemon renderablePokemon;
        super.m_7856_();
        if (CobblemonClient.INSTANCE.getCheckedStarterScreen() && CobblemonClient.INSTANCE.getOverlay().getStarterToast().getNextVisibility$common() != Toast.Visibility.HIDE) {
            CobblemonClient.INSTANCE.getOverlay().getStarterToast().setNextVisibility$common(Toast.Visibility.HIDE);
        }
        int x = (this.f_96543_ - 200) / 2;
        int y = (this.f_96544_ - 175) / 2;
        if (this.categories.isEmpty()) {
            Cobblemon.INSTANCE.getLOGGER().warn("Empty category list while opening StarterSelectionUI");
            return;
        }
        List<RenderableStarterCategory> list = this.categories;
        int n = x - 2;
        int n2 = y + 8;
        this.m_142416_((GuiEventListener)new CategoryList(71, 164, 6, 5, 57, 20, list, n, n2, null, this, 512, null));
        ArrowButton rightButton = new ArrowButton(x + 183, y + 151, 9, 14, 0, 0, 0, true, null, arg_0 -> StarterSelectionScreen.init$lambda$0(this, arg_0), 368, null);
        ArrowButton leftButton = new ArrowButton(x + 72, y + 151, 9, 14, 0, 0, 0, false, null, arg_0 -> StarterSelectionScreen.init$lambda$1(this, arg_0), 368, null);
        this.m_142416_((GuiEventListener)rightButton);
        this.m_142416_((GuiEventListener)leftButton);
        this.currentCategory = (RenderableStarterCategory)CollectionsKt.first(this.categories);
        RenderableStarterCategory renderableStarterCategory = this.currentCategory;
        if (renderableStarterCategory == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"currentCategory");
            renderableStarterCategory = null;
        }
        if ((renderablePokemon = (this.currentPokemon = renderableStarterCategory.getPokemon().get(this.currentSelection))) == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"currentPokemon");
            renderablePokemon = null;
        }
        RenderablePokemon $this$init_u24lambda_u242 = renderablePokemon;
        boolean bl = false;
        this.modelWidget = new ModelWidget(x + 85, y + 50, 102, 100, $this$init_u24lambda_u242, 2.0f, 0.0f, 0.0, 192, null);
        this.typeWidget = this.typeWidget($this$init_u24lambda_u242, x, y);
        ModelWidget modelWidget2 = this.modelWidget;
        if (modelWidget2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"modelWidget");
            modelWidget2 = null;
        }
        this.m_142416_((GuiEventListener)modelWidget2);
        SelectionButton selectionButton = new SelectionButton(x + 106, y + 124, 56, 12, arg_0 -> StarterSelectionScreen.init$lambda$3(this, arg_0));
        this.m_142416_((GuiEventListener)selectionButton);
        int n3 = this.f_96544_ / 2 + 84;
        RenderablePokemon renderablePokemon2 = this.currentPokemon;
        if (renderablePokemon2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"currentPokemon");
            renderablePokemon2 = null;
        }
        if ((modelWidget = this.modelWidget) == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"modelWidget");
            modelWidget = null;
        }
        this.starterRoundaboutCenter = new StarterRoundabout(x + 119, n3, 30, 30, renderablePokemon2, null, modelWidget.getRotVec(), 32, null);
        int n4 = this.f_96544_ / 2 + 84;
        RenderableStarterCategory renderableStarterCategory2 = this.currentCategory;
        if (renderableStarterCategory2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"currentCategory");
            renderableStarterCategory2 = null;
        }
        Function2 function2 = (Function2)new Function2<Double, Double, Unit>(this){
            final /* synthetic */ StarterSelectionScreen this$0;
            {
                this.this$0 = $receiver;
                super(2);
            }

            public final void invoke(double d, double d2) {
                StarterSelectionScreen.access$left(this.this$0);
            }
        };
        ModelWidget modelWidget3 = this.modelWidget;
        if (modelWidget3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"modelWidget");
            modelWidget3 = null;
        }
        this.starterRoundaboutLeft = new StarterRoundabout(x + 89, n4, 30, 30, renderableStarterCategory2.getPokemon().get(this.leftOfCurrentSelection()), (Function2<? super Double, ? super Double, Unit>)function2, modelWidget3.getRotVec());
        int n5 = this.f_96544_ / 2 + 84;
        RenderableStarterCategory renderableStarterCategory3 = this.currentCategory;
        if (renderableStarterCategory3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"currentCategory");
            renderableStarterCategory3 = null;
        }
        Function2 function22 = (Function2)new Function2<Double, Double, Unit>(this){
            final /* synthetic */ StarterSelectionScreen this$0;
            {
                this.this$0 = $receiver;
                super(2);
            }

            public final void invoke(double d, double d2) {
                StarterSelectionScreen.access$right(this.this$0);
            }
        };
        ModelWidget modelWidget4 = this.modelWidget;
        if (modelWidget4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"modelWidget");
            modelWidget4 = null;
        }
        this.starterRoundaboutRight = new StarterRoundabout(x + 149, n5, 30, 30, renderableStarterCategory3.getPokemon().get(this.rightOfCurrentSelection()), (Function2<? super Double, ? super Double, Unit>)function22, modelWidget4.getRotVec());
        StarterRoundabout starterRoundabout = this.starterRoundaboutLeft;
        if (starterRoundabout == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"starterRoundaboutLeft");
            starterRoundabout = null;
        }
        this.m_142416_((GuiEventListener)starterRoundabout);
        StarterRoundabout starterRoundabout2 = this.starterRoundaboutCenter;
        if (starterRoundabout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"starterRoundaboutCenter");
            starterRoundabout2 = null;
        }
        this.m_142416_((GuiEventListener)starterRoundabout2);
        StarterRoundabout starterRoundabout3 = this.starterRoundaboutRight;
        if (starterRoundabout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"starterRoundaboutRight");
            starterRoundabout3 = null;
        }
        this.m_142416_((GuiEventListener)starterRoundabout3);
        this.m_142416_((GuiEventListener)new ExitButton(x + 181, y + 2, 16, 12, 0, 0, 0, StarterSelectionScreen::init$lambda$4));
    }

    public void m_88315_(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        Number number;
        double d;
        RenderablePokemon renderablePokemon;
        ElementalType elementalType;
        int y;
        int x;
        PoseStack matrices;
        block15: {
            ElementalType elementalType2;
            block14: {
                Intrinsics.checkNotNullParameter((Object)context, (String)"context");
                matrices = context.m_280168_();
                x = (this.f_96543_ - 200) / 2;
                y = (this.f_96544_ - 175) / 2;
                Triple<Double, Double, Double> triple = baseUnderlay;
                Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
                GuiUtilsKt.blitk$default(matrices, (ResourceLocation)triple, x, y, 175, 200, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
                triple = base;
                GuiUtilsKt.blitk$default(matrices, (ResourceLocation)triple, x, y, 175, 200, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
                RenderablePokemon renderablePokemon2 = this.currentPokemon;
                if (renderablePokemon2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException((String)"currentPokemon");
                    renderablePokemon2 = null;
                }
                triple = SimpleMathExtensionsKt.toRGB(renderablePokemon2.getForm().getPrimaryType().getHue());
                double r = ((Number)triple.component1()).doubleValue();
                double g = ((Number)triple.component2()).doubleValue();
                double b = ((Number)triple.component3()).doubleValue();
                ResourceLocation resourceLocation = baseFrame;
                GuiUtilsKt.blitk$default(matrices, resourceLocation, x, y, 175, 200, null, null, null, null, null, r, g, b, null, false, 0.0f, 116672, null);
                resourceLocation = CobblemonResources.INSTANCE.getDEFAULT_LARGE();
                MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.starter.title", new Object[0]);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"ui.starter.title\")");
                MutableComponent mutableComponent2 = TextKt.bold(mutableComponent);
                int n = x + 125;
                float f = (float)y + 3.0f;
                RenderHelperKt.drawScaledText$default(context, resourceLocation, mutableComponent2, n, Float.valueOf(f), 1.4f, null, 120, 0, true, true, null, null, 6464, null);
                RenderablePokemon renderablePokemon3 = this.currentPokemon;
                if (renderablePokemon3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException((String)"currentPokemon");
                    renderablePokemon3 = null;
                }
                MutableComponent pokemonName = renderablePokemon3.getSpecies().getTranslatedName();
                float scale = 0.8f;
                n = x + 94;
                double d2 = (double)y + 19.5;
                RenderHelperKt.drawScaledText$default(context, null, pokemonName, n, d2, scale, null, 50, 0, true, false, null, null, 6466, null);
                float scale2 = 0.6f;
                matrices.m_85836_();
                matrices.m_85841_(scale2, scale2, 1.0f);
                RenderablePokemon renderablePokemon4 = this.currentPokemon;
                if (renderablePokemon4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException((String)"currentPokemon");
                    renderablePokemon4 = null;
                }
                MutableComponent mutableComponent3 = MiscUtils.asTranslated((String)CollectionsKt.first(renderablePokemon4.getForm().getPokedex()));
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent3, (String)"currentPokemon.form.pokedex.first().asTranslated()");
                MultiLineLabelK.Companion.create((Component)mutableComponent3, 127, 4).renderLeftAligned(context, Float.valueOf((float)(x + 119) / scale2 + (float)4), (double)((float)(y + 18) / scale2) + 4.0, 8.0 / (double)scale2 - 1.25, 0xFFFFFF, false);
                matrices.m_85849_();
                RenderablePokemon renderablePokemon5 = this.currentPokemon;
                if (renderablePokemon5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException((String)"currentPokemon");
                    renderablePokemon5 = null;
                }
                if ((elementalType2 = renderablePokemon5.getForm().getSecondaryType()) == null) break block14;
                ElementalType it = elementalType2;
                boolean bl = false;
                ResourceLocation resourceLocation2 = doubleTypeBackground;
                elementalType2 = resourceLocation2;
                if (resourceLocation2 != null) break block15;
            }
            elementalType2 = elementalType = singleTypeBackground;
        }
        if ((renderablePokemon = this.currentPokemon) == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"currentPokemon");
            renderablePokemon = null;
        }
        ElementalType elementalType3 = renderablePokemon.getForm().getSecondaryType();
        if (elementalType3 != null) {
            ElementalType it = elementalType3;
            boolean bl = false;
            d = (double)x + 76.75;
        } else {
            d = (double)x + 85.25;
        }
        double d3 = d;
        double d4 = (double)y + 29.4;
        RenderablePokemon renderablePokemon6 = this.currentPokemon;
        if (renderablePokemon6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"currentPokemon");
            renderablePokemon6 = null;
        }
        ElementalType elementalType4 = renderablePokemon6.getForm().getSecondaryType();
        if (elementalType4 != null) {
            ElementalType it = elementalType4;
            boolean bl = false;
            number = 35.25;
        } else {
            number = 19;
        }
        Integer n = number;
        GuiUtilsKt.blitk$default(matrices, (ResourceLocation)elementalType, d3, d4, 19.25, n, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
        TypeWidget typeWidget = this.typeWidget;
        if (typeWidget == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"typeWidget");
            typeWidget = null;
        }
        typeWidget.m_88315_(context, mouseX, mouseY, delta);
        super.m_88315_(context, mouseX, mouseY, delta);
    }

    public final void changeCategory(@NotNull RenderableStarterCategory category) {
        Intrinsics.checkNotNullParameter((Object)category, (String)"category");
        this.currentCategory = category;
        this.currentSelection = 0;
        this.updateSelection();
    }

    private final void right() {
        Minecraft.m_91087_().m_91106_().m_120367_((SoundInstance)SimpleSoundInstance.m_119752_((SoundEvent)CobblemonSounds.GUI_CLICK, (float)1.0f));
        this.currentSelection = this.rightOfCurrentSelection();
        this.updateSelection();
    }

    private final int rightOfCurrentSelection() {
        RenderableStarterCategory renderableStarterCategory = this.currentCategory;
        if (renderableStarterCategory == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"currentCategory");
            renderableStarterCategory = null;
        }
        return this.currentSelection + 1 <= renderableStarterCategory.getPokemon().size() - 1 ? this.currentSelection + 1 : 0;
    }

    private final void left() {
        Minecraft.m_91087_().m_91106_().m_120367_((SoundInstance)SimpleSoundInstance.m_119752_((SoundEvent)CobblemonSounds.GUI_CLICK, (float)1.0f));
        this.currentSelection = this.leftOfCurrentSelection();
        this.updateSelection();
    }

    private final int leftOfCurrentSelection() {
        int n;
        if (this.currentSelection - 1 == -1) {
            RenderableStarterCategory renderableStarterCategory = this.currentCategory;
            if (renderableStarterCategory == null) {
                Intrinsics.throwUninitializedPropertyAccessException((String)"currentCategory");
                renderableStarterCategory = null;
            }
            n = renderableStarterCategory.getPokemon().size() - 1;
        } else {
            n = this.currentSelection - 1;
        }
        return n;
    }

    /*
     * WARNING - void declaration
     */
    private final void updateSelection() {
        RenderableStarterCategory renderableStarterCategory;
        RenderablePokemon renderablePokemon;
        RenderableStarterCategory renderableStarterCategory2;
        void it;
        RenderablePokemon renderablePokemon2;
        RenderableStarterCategory renderableStarterCategory3 = this.currentCategory;
        if (renderableStarterCategory3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"currentCategory");
            renderableStarterCategory3 = null;
        }
        RenderablePokemon renderablePokemon3 = renderablePokemon2 = renderableStarterCategory3.getPokemon().get(this.currentSelection);
        StarterSelectionScreen starterSelectionScreen = this;
        boolean bl = false;
        ModelWidget modelWidget = this.modelWidget;
        if (modelWidget == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"modelWidget");
            modelWidget = null;
        }
        modelWidget.setPokemon((RenderablePokemon)it);
        this.typeWidget = this.typeWidget((RenderablePokemon)it, (this.f_96543_ - 200) / 2, (this.f_96544_ - 175) / 2);
        starterSelectionScreen.currentPokemon = renderablePokemon2;
        StarterRoundabout starterRoundabout = this.starterRoundaboutLeft;
        if (starterRoundabout == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"starterRoundaboutLeft");
            starterRoundabout = null;
        }
        if ((renderableStarterCategory2 = this.currentCategory) == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"currentCategory");
            renderableStarterCategory2 = null;
        }
        starterRoundabout.setPokemon(renderableStarterCategory2.getPokemon().get(this.leftOfCurrentSelection()));
        StarterRoundabout starterRoundabout2 = this.starterRoundaboutCenter;
        if (starterRoundabout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"starterRoundaboutCenter");
            starterRoundabout2 = null;
        }
        if ((renderablePokemon = this.currentPokemon) == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"currentPokemon");
            renderablePokemon = null;
        }
        starterRoundabout2.setPokemon(renderablePokemon);
        StarterRoundabout starterRoundabout3 = this.starterRoundaboutRight;
        if (starterRoundabout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"starterRoundaboutRight");
            starterRoundabout3 = null;
        }
        if ((renderableStarterCategory = this.currentCategory) == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"currentCategory");
            renderableStarterCategory = null;
        }
        starterRoundabout3.setPokemon(renderableStarterCategory.getPokemon().get(this.rightOfCurrentSelection()));
    }

    private final TypeWidget typeWidget(RenderablePokemon pokemon, int x, int y) {
        TypeWidget typeWidget;
        ElementalType elementalType = pokemon.getForm().getSecondaryType();
        if (elementalType != null) {
            ElementalType it = elementalType;
            boolean bl = false;
            Component component = Component.m_130674_((String)"What?");
            Intrinsics.checkNotNullExpressionValue((Object)component, (String)"of(\"What?\")");
            typeWidget = new DualTypeWidget(x + 77, y + 30, 18, 18, component, pokemon.getForm().getPrimaryType(), it);
        } else {
            typeWidget = new SingleTypeWidget(x + 85, y + 30, 18, 18, pokemon.getForm().getPrimaryType(), false);
        }
        return typeWidget;
    }

    public boolean m_7043_() {
        return true;
    }

    private static final void init$lambda$0(StarterSelectionScreen this$0, Button it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        this$0.right();
    }

    private static final void init$lambda$1(StarterSelectionScreen this$0, Button it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        this$0.left();
    }

    private static final void init$lambda$3(StarterSelectionScreen this$0, Button it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        RenderableStarterCategory renderableStarterCategory = this$0.currentCategory;
        if (renderableStarterCategory == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"currentCategory");
            renderableStarterCategory = null;
        }
        CobblemonNetwork.INSTANCE.sendPacketToServer(new SelectStarterPacket(renderableStarterCategory.getName(), this$0.currentSelection));
        Minecraft.m_91087_().m_91152_(null);
    }

    private static final void init$lambda$4(Button it) {
        Minecraft.m_91087_().m_91152_(null);
        Minecraft.m_91087_().m_91106_().m_120367_((SoundInstance)SimpleSoundInstance.m_119752_((SoundEvent)CobblemonSounds.GUI_CLICK, (float)1.0f));
    }

    public static final /* synthetic */ void access$left(StarterSelectionScreen $this) {
        $this.left();
    }

    public static final /* synthetic */ void access$right(StarterSelectionScreen $this) {
        $this.right();
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\r\u0010\u000eR\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0007\u001a\u00020\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\t\u0010\bR\u0014\u0010\n\u001a\u00020\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\n\u0010\bR\u0014\u0010\u000b\u001a\u00020\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\bR\u0014\u0010\f\u001a\u00020\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\f\u0010\b\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/client/gui/startselection/StarterSelectionScreen$Companion;", "", "", "BASE_HEIGHT", "I", "BASE_WIDTH", "Lnet/minecraft/resources/ResourceLocation;", "base", "Lnet/minecraft/resources/ResourceLocation;", "baseFrame", "baseUnderlay", "doubleTypeBackground", "singleTypeBackground", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

