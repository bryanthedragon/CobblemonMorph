/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.Triple
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.screens.moves;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.MoveCategoryIcon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.TypeIcon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.Summary;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.SoundlessWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.screens.moves.MovesWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.screens.moves.ReorderMoveButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.screens.moves.SwapMoveButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.SimpleMathExtensionsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 &2\u00020\u0001:\u0001&B'\u0012\u0006\u0010\"\u001a\u00020\u0005\u0012\u0006\u0010#\u001a\u00020\u0005\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u0012\u0006\u0010\u001d\u001a\u00020\u001c\u00a2\u0006\u0004\b$\u0010%J'\u0010\b\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ/\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u000eH\u0014\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0014\u001a\u00020\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0019\u001a\u00020\u00188\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0019\u0010\u001aR\u0014\u0010\u001b\u001a\u00020\u00188\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001b\u0010\u001aR\u0014\u0010\u001d\u001a\u00020\u001c8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001d\u0010\u001eR\u0014\u0010 \u001a\u00020\u001f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b \u0010!\u00a8\u0006'"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/MoveSlotWidget;", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/SoundlessWidget;", "", "mouseX", "mouseY", "", "button", "", "mouseClicked", "(DDI)Z", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "pMouseX", "pMouseY", "", "pPartialTicks", "", "renderButton", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "Lcom/cobblemon/mod/common/api/moves/Move;", "move", "Lcom/cobblemon/mod/common/api/moves/Move;", "getMove", "()Lcom/cobblemon/mod/common/api/moves/Move;", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/ReorderMoveButton;", "moveDownButton", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/ReorderMoveButton;", "moveUpButton", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/MovesWidget;", "movesWidget", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/MovesWidget;", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/SwapMoveButton;", "switchMoveButton", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/SwapMoveButton;", "pX", "pY", "<init>", "(IILcom/cobblemon/mod/common/api/moves/Move;Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/MovesWidget;)V", "Companion", "common"})
public final class MoveSlotWidget
extends SoundlessWidget {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Move move;
    @NotNull
    private final MovesWidget movesWidget;
    @NotNull
    private final ReorderMoveButton moveUpButton;
    @NotNull
    private final ReorderMoveButton moveDownButton;
    @NotNull
    private final SwapMoveButton switchMoveButton;
    @NotNull
    private static final ResourceLocation moveResource = MiscUtilsKt.cobblemonResource("textures/gui/summary/summary_move.png");
    @NotNull
    private static final ResourceLocation moveOverlayResource = MiscUtilsKt.cobblemonResource("textures/gui/summary/summary_move_overlay.png");
    @NotNull
    private static final ResourceLocation moveSelectedOverlayResource = MiscUtilsKt.cobblemonResource("textures/gui/summary/summary_move_selected_overlay.png");
    public static final int MOVE_WIDTH = 108;
    public static final int MOVE_HEIGHT = 22;

    /*
     * WARNING - void declaration
     */
    public MoveSlotWidget(int pX, int pY, @NotNull Move move, @NotNull MovesWidget movesWidget) {
        void $this$switchMoveButton_u24lambda_u245;
        Button $this$moveDownButton_u24lambda_u243;
        ReorderMoveButton $this$moveUpButton_u24lambda_u241;
        Intrinsics.checkNotNullParameter((Object)move, (String)"move");
        Intrinsics.checkNotNullParameter((Object)((Object)movesWidget), (String)"movesWidget");
        MutableComponent mutableComponent = Component.m_237113_((String)move.getName());
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"literal(move.name)");
        super(pX, pY, 108, 22, (Component)mutableComponent);
        this.move = move;
        this.movesWidget = movesWidget;
        Button button = new ReorderMoveButton(this.m_252754_(), this.m_252907_(), true, arg_0 -> MoveSlotWidget.moveUpButton$lambda$0(this, arg_0));
        ReorderMoveButton reorderMoveButton = button;
        MoveSlotWidget moveSlotWidget = this;
        boolean bl = false;
        this.addWidget((GuiEventListener)$this$moveUpButton_u24lambda_u241);
        moveSlotWidget.moveUpButton = button;
        button = new ReorderMoveButton(this.m_252754_(), this.m_252907_(), false, arg_0 -> MoveSlotWidget.moveDownButton$lambda$2(this, arg_0));
        $this$moveUpButton_u24lambda_u241 = button;
        moveSlotWidget = this;
        boolean bl2 = false;
        this.addWidget((GuiEventListener)$this$moveDownButton_u24lambda_u243);
        moveSlotWidget.moveDownButton = button;
        $this$moveDownButton_u24lambda_u243 = button = new SwapMoveButton(this.m_252754_(), this.m_252907_(), this.move.getTemplate(), this.movesWidget, arg_0 -> MoveSlotWidget.switchMoveButton$lambda$4(this, arg_0));
        moveSlotWidget = this;
        boolean bl3 = false;
        this.addWidget((GuiEventListener)$this$switchMoveButton_u24lambda_u245);
        moveSlotWidget.switchMoveButton = button;
    }

    @NotNull
    public final Move getMove() {
        return this.move;
    }

    protected void m_87963_(@NotNull GuiGraphics context, int pMouseX, int pMouseY, float pPartialTicks) {
        int n;
        int n2;
        ResourceLocation resourceLocation;
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        PoseStack matrices = context.m_280168_();
        this.f_93622_ = pMouseX >= this.m_252754_() && pMouseY >= this.m_252907_() && pMouseX < this.m_252754_() + this.f_93618_ && pMouseY < this.m_252907_() + this.f_93619_;
        MoveTemplate moveTemplate = Moves.INSTANCE.getByNameOrDummy(this.move.getName());
        Triple<Double, Double, Double> rgb = SimpleMathExtensionsKt.toRGB(moveTemplate.getElementalType().getHue());
        if (Intrinsics.areEqual((Object)this.movesWidget.getSelectedMove(), (Object)this.move)) {
            resourceLocation = moveSelectedOverlayResource;
            n2 = this.m_252754_() - 1;
            n = this.m_252907_() - 1;
            Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
            GuiUtilsKt.blitk$default(matrices, resourceLocation, n2, n, 24, 110, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
        }
        resourceLocation = moveResource;
        n2 = this.m_252754_();
        n = this.m_252907_();
        int n3 = this.m_274382_() ? 22 : 0;
        double d = ((Number)rgb.getFirst()).doubleValue();
        double d2 = ((Number)rgb.getSecond()).doubleValue();
        double d3 = ((Number)rgb.getThird()).doubleValue();
        Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
        GuiUtilsKt.blitk$default(matrices, resourceLocation, n2, n, 22, 108, null, n3, null, 44, null, d, d2, d3, null, false, 0.0f, 116032, null);
        resourceLocation = moveOverlayResource;
        n2 = this.m_252754_();
        n = this.m_252907_();
        GuiUtilsKt.blitk$default(matrices, resourceLocation, n2, n, 22, 108, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
        MutableComponent mutableComponent = Component.m_237113_((String)(this.move.getCurrentPp() + "/" + this.move.getMaxPp()));
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"literal(\"${move.currentPp}/${move.maxPp}\")");
        MutableComponent movePPText = TextKt.bold(mutableComponent);
        if (this.move.getCurrentPp() <= Mth.m_14143_((float)((float)this.move.getMaxPp() / 2.0f))) {
            movePPText = this.move.getCurrentPp() == 0 ? TextKt.red(movePPText) : TextKt.gold(movePPText);
        }
        RenderHelperKt.drawScaledText$default(context, CobblemonResources.INSTANCE.getDEFAULT_LARGE(), movePPText, this.m_252754_() + 93, this.m_252907_() + 13, 0.0f, null, 0, 0, true, false, null, null, 7648, null);
        new TypeIcon(this.m_252754_() + 2, this.m_252907_() + 2, moveTemplate.getElementalType(), null, false, false, 0.0f, 0.0f, 0.0f, 504, null).render(context);
        new MoveCategoryIcon(this.m_252754_() + 66, (double)this.m_252907_() + 13.5, this.move.getDamageCategory(), 0.0f, 8, null).render(context);
        RenderHelperKt.drawScaledText$default(context, CobblemonResources.INSTANCE.getDEFAULT_LARGE(), TextKt.bold(this.move.getDisplayName()), this.m_252754_() + 28, this.m_252907_() + 2, 0.0f, null, 0, 0, false, true, null, null, 7136, null);
        this.moveUpButton.m_88315_(context, pMouseX, pMouseY, pPartialTicks);
        this.moveDownButton.m_88315_(context, pMouseX, pMouseY, pPartialTicks);
        this.switchMoveButton.m_88315_(context, pMouseX, pMouseY, pPartialTicks);
    }

    @Override
    public boolean m_6375_(double mouseX, double mouseY, int button) {
        if (this.m_274382_()) {
            this.movesWidget.selectMove(this.move);
        }
        return super.m_6375_(mouseX, mouseY, button);
    }

    private static final void moveUpButton$lambda$0(MoveSlotWidget this$0, Button it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        this$0.movesWidget.selectMove(null);
        this$0.movesWidget.reorderMove(this$0, true);
    }

    private static final void moveDownButton$lambda$2(MoveSlotWidget this$0, Button it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        this$0.movesWidget.selectMove(null);
        this$0.movesWidget.reorderMove(this$0, false);
    }

    private static final void switchMoveButton$lambda$4(MoveSlotWidget this$0, Button it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        this$0.movesWidget.selectMove(null);
        if (this$0.movesWidget.getSummary().getSideScreenIndex() == 1) {
            Summary.displaySideScreen$default(this$0.movesWidget.getSummary(), 0, null, 2, null);
        } else {
            this$0.movesWidget.getSummary().displaySideScreen(1, this$0.move);
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fR\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0007\u001a\u00020\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\t\u0010\bR\u0014\u0010\n\u001a\u00020\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\n\u0010\b\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/MoveSlotWidget$Companion;", "", "", "MOVE_HEIGHT", "I", "MOVE_WIDTH", "Lnet/minecraft/resources/ResourceLocation;", "moveOverlayResource", "Lnet/minecraft/resources/ResourceLocation;", "moveResource", "moveSelectedOverlayResource", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

