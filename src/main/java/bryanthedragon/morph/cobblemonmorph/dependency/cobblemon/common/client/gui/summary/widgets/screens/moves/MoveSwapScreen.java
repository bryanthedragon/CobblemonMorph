/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.Triple
 *  kotlin.collections.ArraysKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.AbstractSelectionList$Entry
 *  net.minecraft.client.gui.components.ObjectSelectionList$Entry
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.screens.moves;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.MoveCategoryIcon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.TypeIcon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.Summary;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.common.SummaryScrollList;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.screens.moves.MovesWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.BenchMovePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.SimpleMathExtensionsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u0000 \u00172\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002\u0017\u0018B'\u0012\u0006\u0010\u0013\u001a\u00020\u0004\u0012\u0006\u0010\u0014\u001a\u00020\u0004\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\"\u0010\r\u001a\u00020\f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/MoveSwapScreen;", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/common/SummaryScrollList;", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/MoveSwapScreen$MoveSlot;", "entry", "", "addEntry", "(Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/MoveSwapScreen$MoveSlot;)I", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/MovesWidget;", "movesWidget", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/MovesWidget;", "getMovesWidget", "()Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/MovesWidget;", "Lcom/cobblemon/mod/common/api/moves/Move;", "replacedMove", "Lcom/cobblemon/mod/common/api/moves/Move;", "getReplacedMove", "()Lcom/cobblemon/mod/common/api/moves/Move;", "setReplacedMove", "(Lcom/cobblemon/mod/common/api/moves/Move;)V", "x", "y", "<init>", "(IILcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/MovesWidget;Lcom/cobblemon/mod/common/api/moves/Move;)V", "Companion", "MoveSlot", "common"})
public final class MoveSwapScreen
extends SummaryScrollList<MoveSlot> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final MovesWidget movesWidget;
    @NotNull
    private Move replacedMove;
    public static final int SLOT_HEIGHT = 18;
    public static final int SLOT_SPACING = 3;
    @NotNull
    private static final ResourceLocation moveResource = MiscUtilsKt.cobblemonResource("textures/gui/summary/summary_move_condensed.png");
    @NotNull
    private static final ResourceLocation moveOverlayResource = MiscUtilsKt.cobblemonResource("textures/gui/summary/summary_move_overlay_condensed.png");

    public MoveSwapScreen(int x, int y, @NotNull MovesWidget movesWidget, @NotNull Move replacedMove) {
        Intrinsics.checkNotNullParameter((Object)((Object)movesWidget), (String)"movesWidget");
        Intrinsics.checkNotNullParameter((Object)replacedMove, (String)"replacedMove");
        MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.moves.switch", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"ui.moves.switch\")");
        super(x, y, mutableComponent, 21);
        this.movesWidget = movesWidget;
        this.replacedMove = replacedMove;
    }

    @NotNull
    public final MovesWidget getMovesWidget() {
        return this.movesWidget;
    }

    @NotNull
    public final Move getReplacedMove() {
        return this.replacedMove;
    }

    public final void setReplacedMove(@NotNull Move move) {
        Intrinsics.checkNotNullParameter((Object)move, (String)"<set-?>");
        this.replacedMove = move;
    }

    public int addEntry(@NotNull MoveSlot entry) {
        Intrinsics.checkNotNullParameter((Object)((Object)entry), (String)"entry");
        return super.m_7085_((AbstractSelectionList.Entry)entry);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\n\u0010\u000bR\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0007\u001a\u00020\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\t\u0010\b\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/MoveSwapScreen$Companion;", "", "", "SLOT_HEIGHT", "I", "SLOT_SPACING", "Lnet/minecraft/resources/ResourceLocation;", "moveOverlayResource", "Lnet/minecraft/resources/ResourceLocation;", "moveResource", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u001f\u0012\u0006\u0010\"\u001a\u00020!\u0012\u0006\u0010\u001d\u001a\u00020\u001c\u0012\u0006\u0010&\u001a\u00020\b\u00a2\u0006\u0004\b*\u0010+J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J'\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ_\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\u0017H\u0016\u00a2\u0006\u0004\b\u001a\u0010\u001bR\u0017\u0010\u001d\u001a\u00020\u001c8\u0006\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 R\u0017\u0010\"\u001a\u00020!8\u0006\u00a2\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%R\u0017\u0010&\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b&\u0010'\u001a\u0004\b(\u0010)\u00a8\u0006,"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/MoveSwapScreen$MoveSlot;", "Lnet/minecraft/client/gui/widget/AlwaysSelectedEntryListWidget$Entry;", "Lnet/minecraft/network/chat/MutableComponent;", "getNarration", "()Lnet/minecraft/network/chat/MutableComponent;", "", "d", "e", "", "i", "", "mouseClicked", "(DDI)Z", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "index", "rowTop", "rowLeft", "rowWidth", "rowHeight", "mouseX", "mouseY", "isHovered", "", "partialTicks", "", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIIIIIIZF)V", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "move", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "getMove", "()Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/MoveSwapScreen;", "pane", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/MoveSwapScreen;", "getPane", "()Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/MoveSwapScreen;", "ppRaisedStages", "I", "getPpRaisedStages", "()I", "<init>", "(Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/MoveSwapScreen;Lcom/cobblemon/mod/common/api/moves/MoveTemplate;I)V", "common"})
    public static final class MoveSlot
    extends ObjectSelectionList.Entry<MoveSlot> {
        @NotNull
        private final MoveSwapScreen pane;
        @NotNull
        private final MoveTemplate move;
        private final int ppRaisedStages;

        public MoveSlot(@NotNull MoveSwapScreen pane, @NotNull MoveTemplate move, int ppRaisedStages) {
            Intrinsics.checkNotNullParameter((Object)((Object)pane), (String)"pane");
            Intrinsics.checkNotNullParameter((Object)move, (String)"move");
            this.pane = pane;
            this.move = move;
            this.ppRaisedStages = ppRaisedStages;
        }

        @NotNull
        public final MoveSwapScreen getPane() {
            return this.pane;
        }

        @NotNull
        public final MoveTemplate getMove() {
            return this.move;
        }

        public final int getPpRaisedStages() {
            return this.ppRaisedStages;
        }

        @NotNull
        public MutableComponent getNarration() {
            return this.move.getDisplayName();
        }

        public void m_6311_(@NotNull GuiGraphics context, int index, int rowTop, int rowLeft, int rowWidth, int rowHeight, int mouseX, int mouseY, boolean isHovered, float partialTicks) {
            Intrinsics.checkNotNullParameter((Object)context, (String)"context");
            PoseStack matrices = context.m_280168_();
            int tweakedRowTop = rowTop - 1 + 1;
            Triple<Double, Double, Double> rgb = SimpleMathExtensionsKt.toRGB(this.move.getElementalType().getHue());
            Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
            GuiUtilsKt.blitk$default(matrices, moveResource, rowLeft, tweakedRowTop, 18, rowWidth, null, isHovered ? 18 : 0, null, 36, null, (Number)rgb.getFirst(), (Number)rgb.getSecond(), (Number)rgb.getThird(), null, false, 0.0f, 116032, null);
            GuiUtilsKt.blitk$default(matrices, moveOverlayResource, rowLeft, tweakedRowTop, 18, rowWidth, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
            new TypeIcon(rowLeft - 9, tweakedRowTop, this.move.getElementalType(), null, false, false, 0.0f, 0.0f, 0.0f, 504, null).render(context);
            new MoveCategoryIcon(rowLeft + 77, (double)tweakedRowTop + 1.5, this.move.getDamageCategory(), 0.0f, 8, null).render(context);
            RenderHelperKt.drawScaledText$default(context, null, this.move.getDisplayName(), rowLeft + 14, (double)tweakedRowTop + 3.5, 0.5f, null, 0, 0, false, true, null, null, 7106, null);
            ResourceLocation resourceLocation = MovesWidget.Companion.getMovesPowerIconResource();
            float f = (float)(rowLeft + 10) / 0.5f;
            float f2 = (float)(tweakedRowTop + 11) / 0.5f;
            GuiUtilsKt.blitk$default(matrices, resourceLocation, Float.valueOf(f), Float.valueOf(f2), 10, 10, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
            resourceLocation = MovesWidget.Companion.getMovesAccuracyIconResource();
            f = (float)(rowLeft + 30) / 0.5f;
            f2 = (float)(tweakedRowTop + 11) / 0.5f;
            GuiUtilsKt.blitk$default(matrices, resourceLocation, Float.valueOf(f), Float.valueOf(f2), 10, 10, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
            resourceLocation = MovesWidget.Companion.getMovesEffectIconResource();
            double d = ((double)rowLeft + 53.5) / (double)0.5f;
            float f3 = (float)(tweakedRowTop + 11) / 0.5f;
            GuiUtilsKt.blitk$default(matrices, resourceLocation, d, Float.valueOf(f3), 10, 10, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
            MutableComponent movePower = (int)this.move.getPower() > 0 ? TextKt.text(String.valueOf((int)this.move.getPower())) : TextKt.text("\u2014");
            RenderHelperKt.drawScaledText$default(context, null, movePower, (double)rowLeft + 16.5, tweakedRowTop + 12, 0.5f, null, 0, 0, false, true, null, null, 7106, null);
            RenderHelperKt.drawScaledText$default(context, null, TextKt.text(this.pane.getMovesWidget().format(this.move.getAccuracy())), rowLeft + 37, tweakedRowTop + 12, 0.5f, null, 0, 0, false, true, null, null, 7106, null);
            Double d2 = (Double)ArraysKt.firstOrNull((Object[])this.move.getEffectChances());
            RenderHelperKt.drawScaledText$default(context, null, TextKt.text(this.pane.getMovesWidget().format(d2 != null ? d2 : 0.0)), (double)rowLeft + 60.5, tweakedRowTop + 12, 0.5f, null, 0, 0, false, true, null, null, 7106, null);
            int pp = this.move.getPp() + this.ppRaisedStages * this.move.getPp() / 5;
            Object[] objectArray = new Object[]{pp};
            MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.moves.pp", objectArray);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"ui.moves.pp\", pp)");
            RenderHelperKt.drawScaledText$default(context, null, mutableComponent, rowLeft + 76, tweakedRowTop + 12, 0.5f, null, 0, 0, false, true, null, null, 7106, null);
        }

        public boolean m_6375_(double d, double e, int i) {
            if (this.m_5953_(d, e)) {
                Pokemon pokemon = this.pane.getMovesWidget().getSummary().getSelectedPokemon$common();
                boolean isParty = CollectionsKt.contains((Iterable)CobblemonClient.INSTANCE.getStorage().getMyParty(), (Object)pokemon);
                UUID uUID = pokemon.getUuid();
                Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"pokemon.uuid");
                CobblemonNetwork.INSTANCE.sendPacketToServer(new BenchMovePacket(isParty, uUID, this.pane.getReplacedMove().getTemplate(), this.move));
                this.pane.getMovesWidget().getSummary().playSound(CobblemonSounds.GUI_CLICK);
                Summary.displaySideScreen$default(this.pane.getMovesWidget().getSummary(), 0, null, 2, null);
                return true;
            }
            return false;
        }
    }
}

