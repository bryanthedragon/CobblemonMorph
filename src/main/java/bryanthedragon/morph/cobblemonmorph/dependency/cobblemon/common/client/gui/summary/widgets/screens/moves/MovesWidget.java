/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.ArraysKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.screens.moves;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.MultiLineLabelK;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.Summary;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.SoundlessWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.screens.moves.MoveSlotWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage.ClientParty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.RequestMoveSwapPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 -2\u00020\u0001:\u0001-B\u001f\u0012\u0006\u0010)\u001a\u00020\t\u0012\u0006\u0010*\u001a\u00020\t\u0012\u0006\u0010%\u001a\u00020$\u00a2\u0006\u0004\b+\u0010,J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J/\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\fH\u0014\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u001d\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0013\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0017\u0010\u0018\u001a\u00020\u000e2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0017\u00a2\u0006\u0004\b\u0018\u0010\u0019R\u0016\u0010\u001a\u001a\u00020\t8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00110\u001c8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001d\u0010\u001eR$\u0010\u001f\u001a\u0004\u0018\u00010\u00178\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\"\"\u0004\b#\u0010\u0019R\u0017\u0010%\u001a\u00020$8\u0006\u00a2\u0006\f\n\u0004\b%\u0010&\u001a\u0004\b'\u0010(\u00a8\u0006."}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/MovesWidget;", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/SoundlessWidget;", "", "input", "", "format", "(D)Ljava/lang/String;", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "pMouseX", "pMouseY", "", "pPartialTicks", "", "renderButton", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/MoveSlotWidget;", "move", "", "up", "reorderMove", "(Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/MoveSlotWidget;Z)V", "Lcom/cobblemon/mod/common/api/moves/Move;", "selectMove", "(Lcom/cobblemon/mod/common/api/moves/Move;)V", "index", "I", "", "moves", "Ljava/util/List;", "selectedMove", "Lcom/cobblemon/mod/common/api/moves/Move;", "getSelectedMove", "()Lcom/cobblemon/mod/common/api/moves/Move;", "setSelectedMove", "Lcom/cobblemon/mod/common/client/gui/summary/Summary;", "summary", "Lcom/cobblemon/mod/common/client/gui/summary/Summary;", "getSummary", "()Lcom/cobblemon/mod/common/client/gui/summary/Summary;", "pX", "pY", "<init>", "(IILcom/cobblemon/mod/common/client/gui/summary/Summary;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nMovesWidget.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MovesWidget.kt\ncom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/MovesWidget\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,222:1\n1549#2:223\n1620#2,3:224\n2634#2:227\n1855#2,2:229\n1#3:228\n*S KotlinDebug\n*F\n+ 1 MovesWidget.kt\ncom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/MovesWidget\n*L\n53#1:223\n53#1:224,3\n61#1:227\n76#1:229,2\n61#1:228\n*E\n"})
public final class MovesWidget
extends SoundlessWidget {
    @NotNull
    public static final Companion Companion;
    @NotNull
    private final Summary summary;
    @Nullable
    private Move selectedMove;
    private int index;
    @NotNull
    private final List<MoveSlotWidget> moves;
    private static final int WIDTH = 134;
    private static final int HEIGHT = 148;
    public static final int MOVE_ICON_SIZE = 10;
    public static final float SCALE = 0.5f;
    @NotNull
    private static final DecimalFormat decimalFormat;
    @NotNull
    private static final ResourceLocation movesBaseResource;
    @NotNull
    private static final ResourceLocation movesPowerIconResource;
    @NotNull
    private static final ResourceLocation movesAccuracyIconResource;
    @NotNull
    private static final ResourceLocation movesEffectIconResource;

    /*
     * WARNING - void declaration
     */
    public MovesWidget(int pX, int pY, @NotNull Summary summary) {
        void $this$onEach$iv;
        void $this$mapTo$iv$iv;
        Iterable $this$map$iv;
        Intrinsics.checkNotNullParameter((Object)summary, (String)"summary");
        MutableComponent mutableComponent = Component.m_237113_((String)"MovesWidget");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"literal(\"MovesWidget\")");
        super(pX, pY, 134, 148, (Component)mutableComponent);
        this.summary = summary;
        this.index = -1;
        Iterable iterable = this.summary.getSelectedPokemon$common().getMoveSet().getMoves();
        MovesWidget movesWidget = this;
        boolean $i$f$map = false;
        void var6_7 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void move;
            Move move2 = (Move)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            int n = this.index;
            this.index = n + 1;
            collection.add(new MoveSlotWidget(this.m_252754_() + 13, this.m_252907_() + 6 + 25 * this.index, (Move)move, this));
        }
        $this$map$iv = CollectionsKt.toMutableList((Collection)((List)destination$iv$iv));
        boolean $i$f$onEach = false;
        void $this$onEach_u24lambda_u2416$iv = var6_7 = $this$onEach$iv;
        boolean bl = false;
        for (Object element$iv : $this$onEach_u24lambda_u2416$iv) {
            MoveSlotWidget it = (MoveSlotWidget)((Object)element$iv);
            boolean bl2 = false;
            this.addWidget((GuiEventListener)it);
        }
        movesWidget.moves = (List)var6_7;
    }

    @NotNull
    public final Summary getSummary() {
        return this.summary;
    }

    @Nullable
    public final Move getSelectedMove() {
        return this.selectedMove;
    }

    public final void setSelectedMove(@Nullable Move move) {
        this.selectedMove = move;
    }

    /*
     * Unable to fully structure code
     */
    protected void m_87963_(@NotNull GuiGraphics context, int pMouseX, int pMouseY, float pPartialTicks) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        matrices = context.m_280168_();
        var6_6 = MovesWidget.movesBaseResource;
        var7_7 = this.m_252754_();
        var8_10 = this.m_252907_();
        var9_14 = this.f_93618_;
        var10_17 = this.f_93619_;
        Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
        GuiUtilsKt.blitk$default(matrices, var6_6, var7_7, var8_10, var10_17, var9_14, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
        $this$forEach$iv = this.moves;
        $i$f$forEach = false;
        for (T element$iv : $this$forEach$iv) {
            it = (MoveSlotWidget)element$iv;
            $i$a$-forEach-MovesWidget$renderButton$1 = false;
            it.m_88315_(context, pMouseX, pMouseY, pPartialTicks);
        }
        $this$forEach$iv = MovesWidget.movesPowerIconResource;
        $i$f$forEach = (float)(this.m_252754_() + 7) / 0.5f;
        var8_12 = ((double)this.m_252907_() + 114.5) / (double)0.5f;
        GuiUtilsKt.blitk$default(matrices, (ResourceLocation)$this$forEach$iv, Float.valueOf($i$f$forEach), var8_12, 10, 10, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
        $this$forEach$iv = MovesWidget.movesAccuracyIconResource;
        $i$f$forEach = (float)(this.m_252754_() + 7) / 0.5f;
        var8_12 = ((double)this.m_252907_() + 125.5) / (double)0.5f;
        GuiUtilsKt.blitk$default(matrices, (ResourceLocation)$this$forEach$iv, Float.valueOf($i$f$forEach), var8_12, 10, 10, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
        $this$forEach$iv = MovesWidget.movesEffectIconResource;
        $i$f$forEach = (float)(this.m_252754_() + 7) / 0.5f;
        var8_12 = ((double)this.m_252907_() + 136.5) / (double)0.5f;
        GuiUtilsKt.blitk$default(matrices, (ResourceLocation)$this$forEach$iv, Float.valueOf($i$f$forEach), var8_12, 10, 10, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
        v0 = LocalizationUtilsKt.lang("ui.power", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)v0, (String)"lang(\"ui.power\")");
        RenderHelperKt.drawScaledText$default(context, null, v0, this.m_252754_() + 14, this.m_252907_() + 115, 0.5f, null, 0, 0, false, true, null, null, 7106, null);
        v1 = LocalizationUtilsKt.lang("ui.accuracy", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)v1, (String)"lang(\"ui.accuracy\")");
        RenderHelperKt.drawScaledText$default(context, null, v1, this.m_252754_() + 14, this.m_252907_() + 126, 0.5f, null, 0, 0, false, true, null, null, 7106, null);
        v2 = LocalizationUtilsKt.lang("ui.effect", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)v2, (String)"lang(\"ui.effect\")");
        RenderHelperKt.drawScaledText$default(context, null, v2, this.m_252754_() + 14, this.m_252907_() + 137, 0.5f, null, 0, 0, false, true, null, null, 7106, null);
        mcFont = Minecraft.m_91087_().f_91062_;
        if (this.selectedMove == null) ** GOTO lbl-1000
        v3 = this.selectedMove;
        Intrinsics.checkNotNull((Object)v3);
        if ((int)v3.getPower() > 0) {
            v4 = this.selectedMove;
            Intrinsics.checkNotNull((Object)v4);
            v5 = TextKt.text(String.valueOf((int)v4.getPower()));
        } else lbl-1000:
        // 2 sources

        {
            v5 = TextKt.text("\u2014");
        }
        movePower = v5;
        RenderHelperKt.drawScaledText$default(context, null, movePower, (double)this.m_252754_() + 62.5 - (double)((float)mcFont.m_92852_((FormattedText)movePower) * 0.5f), this.m_252907_() + 115, 0.5f, null, 0, 0, false, true, null, null, 7106, null);
        if (this.selectedMove != null) {
            v6 = this.selectedMove;
            Intrinsics.checkNotNull((Object)v6);
            v7 = TextKt.text(this.format(v6.getAccuracy()));
        } else {
            v7 = TextKt.text("\u2014");
        }
        moveAccuracy = v7;
        RenderHelperKt.drawScaledText$default(context, null, moveAccuracy, (double)this.m_252754_() + 62.5 - (double)((float)mcFont.m_92852_((FormattedText)moveAccuracy) * 0.5f), this.m_252907_() + 126, 0.5f, null, 0, 0, false, true, null, null, 7106, null);
        if (this.selectedMove != null) {
            v8 = this.selectedMove;
            Intrinsics.checkNotNull((Object)v8);
            v9 = (Double)ArraysKt.firstOrNull((Object[])v8.getEffectChances());
            v10 = TextKt.text(this.format(v9 != null ? v9 : 0.0));
        } else {
            v10 = TextKt.text("\u2014");
        }
        moveEffect = v10;
        RenderHelperKt.drawScaledText$default(context, null, moveEffect, (double)this.m_252754_() + 62.5 - (double)((float)mcFont.m_92852_((FormattedText)moveEffect) * 0.5f), this.m_252907_() + 137, 0.5f, null, 0, 0, false, true, null, null, 7106, null);
        if (this.selectedMove != null) {
            matrices.m_85836_();
            matrices.m_85841_(0.5f, 0.5f, 1.0f);
            v11 = this.selectedMove;
            Intrinsics.checkNotNull((Object)v11);
            MultiLineLabelK.Companion.create((Component)v11.getDescription(), Float.valueOf(114.0f), 5).renderLeftAligned(context, Float.valueOf((float)(this.m_252754_() + 70) / 0.5f), Float.valueOf((float)(this.m_252907_() + 115) / 0.5f), 11.0, 0xFFFFFF, true);
            matrices.m_85849_();
        }
    }

    public final void reorderMove(@NotNull MoveSlotWidget move, boolean up) {
        Intrinsics.checkNotNullParameter((Object)((Object)move), (String)"move");
        int movePos = this.moves.indexOf((Object)move);
        if (this.moves.size() <= movePos || movePos == -1) {
            return;
        }
        int targetSlot = 0;
        if (up) {
            targetSlot = movePos - 1;
            if (targetSlot == -1) {
                targetSlot = this.moves.size() - 1;
            }
        } else {
            targetSlot = movePos + 1;
            if (targetSlot >= this.moves.size()) {
                targetSlot = 0;
            }
        }
        ClientParty clientParty = CobblemonClient.INSTANCE.getStorage().getMyParty();
        UUID uUID = this.summary.getSelectedPokemon$common().getUuid();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"summary.selectedPokemon.uuid");
        CobblemonNetwork.INSTANCE.sendPacketToServer(new RequestMoveSwapPacket(movePos, targetSlot, clientParty.getPosition(uUID)));
    }

    @NotNull
    public final String format(double input) {
        if (input <= 0.0) {
            return "\u2014";
        }
        return decimalFormat.format(input) + "%";
    }

    public final void selectMove(@Nullable Move move) {
        Move move2;
        if (Intrinsics.areEqual((Object)this.selectedMove, (Object)move) || (move2 = move) == null) {
            move2 = null;
        }
        this.selectedMove = move2;
    }

    static {
        DecimalFormat decimalFormat;
        Companion = new Companion(null);
        DecimalFormat it = decimalFormat = new DecimalFormat("#.##");
        boolean bl = false;
        it.setRoundingMode(RoundingMode.CEILING);
        MovesWidget.decimalFormat = decimalFormat;
        movesBaseResource = MiscUtilsKt.cobblemonResource("textures/gui/summary/summary_moves_base.png");
        movesPowerIconResource = MiscUtilsKt.cobblemonResource("textures/gui/summary/summary_moves_icon_power.png");
        movesAccuracyIconResource = MiscUtilsKt.cobblemonResource("textures/gui/summary/summary_moves_icon_accuracy.png");
        movesEffectIconResource = MiscUtilsKt.cobblemonResource("textures/gui/summary/summary_moves_icon_effect.png");
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0007\u001a\u00020\u00068\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\t\u0010\u0004R\u0014\u0010\u000b\u001a\u00020\n8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\fR\u0017\u0010\u000e\u001a\u00020\r8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\r8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\u000fR\u0017\u0010\u0013\u001a\u00020\r8\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u000f\u001a\u0004\b\u0014\u0010\u0011R\u0017\u0010\u0015\u001a\u00020\r8\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\u000f\u001a\u0004\b\u0016\u0010\u0011\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/MovesWidget$Companion;", "", "", "HEIGHT", "I", "MOVE_ICON_SIZE", "", "SCALE", "F", "WIDTH", "Ljava/text/DecimalFormat;", "decimalFormat", "Ljava/text/DecimalFormat;", "Lnet/minecraft/resources/ResourceLocation;", "movesAccuracyIconResource", "Lnet/minecraft/resources/ResourceLocation;", "getMovesAccuracyIconResource", "()Lnet/minecraft/resources/ResourceLocation;", "movesBaseResource", "movesEffectIconResource", "getMovesEffectIconResource", "movesPowerIconResource", "getMovesPowerIconResource", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getMovesPowerIconResource() {
            return movesPowerIconResource;
        }

        @NotNull
        public final ResourceLocation getMovesAccuracyIconResource() {
            return movesAccuracyIconResource;
        }

        @NotNull
        public final ResourceLocation getMovesEffectIconResource() {
            return movesEffectIconResource;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

