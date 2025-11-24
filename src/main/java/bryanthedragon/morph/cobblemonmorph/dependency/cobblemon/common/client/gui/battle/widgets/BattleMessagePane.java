/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.AbstractSelectionList$Entry
 *  net.minecraft.client.gui.components.ObjectSelectionList
 *  net.minecraft.client.gui.components.ObjectSelectionList$Entry
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.FormattedCharSequence
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.widgets;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleMessageQueue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u0004\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 62\b\u0012\u0004\u0012\u00020\u00020\u0001:\u000276B\u000f\u0012\u0006\u00103\u001a\u000202\u00a2\u0006\u0004\b4\u00105J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0014\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u000f\u0010\b\u001a\u00020\u0007H\u0002\u00a2\u0006\u0004\b\b\u0010\tJ\u000f\u0010\n\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ\u000f\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ\u000f\u0010\u000f\u001a\u00020\u0004H\u0014\u00a2\u0006\u0004\b\u000f\u0010\u000bJ'\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J7\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019J/\u0010\u001e\u001a\u00020\u00072\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u001cH\u0016\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u0017\u0010\"\u001a\u00020\u00042\u0006\u0010!\u001a\u00020 H\u0002\u00a2\u0006\u0004\b\"\u0010#J\u001f\u0010$\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\fH\u0002\u00a2\u0006\u0004\b$\u0010%R\u0011\u0010'\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b&\u0010\u000bR\u0011\u0010)\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b(\u0010\u000bR\"\u0010*\u001a\u00020\u001c8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b*\u0010+\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R\u0016\u00100\u001a\u00020\u00138\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b0\u00101\u00a8\u00068"}, d2={"Lcom/cobblemon/mod/common/client/gui/battle/widgets/BattleMessagePane;", "Lnet/minecraft/client/gui/components/ObjectSelectionList;", "Lcom/cobblemon/mod/common/client/gui/battle/widgets/BattleMessagePane$BattleMessageLine;", "entry", "", "addEntry", "(Lcom/cobblemon/mod/common/client/gui/battle/widgets/BattleMessagePane$BattleMessageLine;)I", "", "correctSize", "()V", "getRowWidth", "()I", "", "getScrollAmount", "()D", "getScrollbarPositionX", "mouseX", "mouseY", "button", "", "mouseClicked", "(DDI)Z", "deltaX", "deltaY", "mouseDragged", "(DDIDD)Z", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "partialTicks", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "", "i", "scaleIt", "(Ljava/lang/Number;)I", "updateScrollingState", "(DD)V", "getAppropriateX", "appropriateX", "getAppropriateY", "appropriateY", "opacity", "F", "getOpacity", "()F", "setOpacity", "(F)V", "scrolling", "Z", "Lcom/cobblemon/mod/common/client/battle/ClientBattleMessageQueue;", "messageQueue", "<init>", "(Lcom/cobblemon/mod/common/client/battle/ClientBattleMessageQueue;)V", "Companion", "BattleMessageLine", "common"})
public final class BattleMessagePane
extends ObjectSelectionList<BattleMessageLine> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private float opacity;
    private boolean scrolling;
    public static final int LINE_HEIGHT = 10;
    public static final int LINE_WIDTH = 142;
    public static final int FRAME_WIDTH = 169;
    public static final int FRAME_HEIGHT = 55;
    public static final int FRAME_EXPANDED_HEIGHT = 101;
    public static final int TEXT_BOX_WIDTH = 153;
    public static final int TEXT_BOX_HEIGHT = 46;
    public static final int EXPAND_TOGGLE_SIZE = 5;
    @NotNull
    private static final ResourceLocation battleMessagePaneFrameResource = MiscUtilsKt.cobblemonResource("textures/gui/battle/battle_log.png");
    @NotNull
    private static final ResourceLocation battleMessagePaneFrameExpandedResource = MiscUtilsKt.cobblemonResource("textures/gui/battle/battle_log_expanded.png");
    private static boolean expanded;

    public BattleMessagePane(@NotNull ClientBattleMessageQueue messageQueue) {
        Intrinsics.checkNotNullParameter((Object)messageQueue, (String)"messageQueue");
        super(Minecraft.m_91087_(), 153, 46, 1, 47, 10);
        this.opacity = 1.0f;
        this.correctSize();
        this.m_93496_(false);
        this.m_93488_(false);
        this.m_93471_(false);
        messageQueue.subscribe((Function1<? super FormattedCharSequence, Unit>)((Function1)new Function1<FormattedCharSequence, Unit>(){

            public final void invoke(@NotNull FormattedCharSequence it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                boolean fullyScrolledDown = (double)this.m_93518_() - this.m_93517_() < 10.0;
                this.addEntry(new BattleMessageLine(this, it));
                if (fullyScrolledDown) {
                    this.m_93410_(this.m_93518_());
                }
            }
        }));
    }

    public final float getOpacity() {
        return this.opacity;
    }

    public final void setOpacity(float f) {
        this.opacity = f;
    }

    public final int getAppropriateX() {
        return this.f_93386_.m_91268_().m_85445_() - 181;
    }

    public final int getAppropriateY() {
        return this.f_93386_.m_91268_().m_85446_() - (30 + (expanded ? 101 : 55));
    }

    private final void correctSize() {
        int textBoxHeight = expanded ? 92 : 46;
        this.m_93437_(153, textBoxHeight, this.getAppropriateY() + 6, this.getAppropriateY() + 6 + textBoxHeight);
        this.m_93507_(this.getAppropriateX());
    }

    protected int addEntry(@NotNull BattleMessageLine entry) {
        Intrinsics.checkNotNullParameter((Object)((Object)entry), (String)"entry");
        return super.m_7085_((AbstractSelectionList.Entry)entry);
    }

    public int m_5759_() {
        return 80;
    }

    protected int m_5756_() {
        return this.f_93393_ + 154;
    }

    public double m_93517_() {
        return super.m_93517_();
    }

    private final int scaleIt(Number i) {
        return (int)(this.f_93386_.m_91268_().m_85449_() * (double)i.floatValue());
    }

    public void m_88315_(@NotNull GuiGraphics context, int mouseX, int mouseY, float partialTicks) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        this.correctSize();
        PoseStack poseStack = context.m_280168_();
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"context.matrices");
        GuiUtilsKt.blitk$default(poseStack, expanded ? battleMessagePaneFrameExpandedResource : battleMessagePaneFrameResource, this.f_93393_, this.getAppropriateY(), expanded ? 101 : 55, 169, null, null, null, null, null, null, null, null, Float.valueOf(this.opacity), false, 0.0f, 114624, null);
        int textBoxHeight = expanded ? 92 : 46;
        context.m_280588_(this.f_93393_ + 5, this.getAppropriateY() + 6, this.f_93393_ + 5 + this.f_93388_, this.getAppropriateY() + 6 + textBoxHeight);
        super.m_88315_(context, mouseX, mouseY, partialTicks);
        context.m_280618_();
    }

    public boolean m_6375_(double mouseX, double mouseY, int button) {
        int toggleOffsetY;
        int n = toggleOffsetY = expanded ? 92 : 46;
        if (mouseX > (double)(this.f_93393_ + 160) && mouseX < (double)(this.f_93393_ + 160 + 5) && mouseY > (double)(this.getAppropriateY() + toggleOffsetY) && mouseY < (double)(this.getAppropriateY() + toggleOffsetY + 5)) {
            expanded = !expanded;
        }
        this.updateScrollingState(mouseX, mouseY);
        if (this.scrolling) {
            this.m_7522_((GuiEventListener)this.m_93412_(mouseX, mouseY));
            this.m_7897_(true);
        }
        return super.m_6375_(mouseX, mouseY, button);
    }

    public boolean m_7979_(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.scrolling) {
            if (mouseY < (double)this.f_93390_) {
                this.m_93410_(0.0);
            } else if (mouseY > (double)this.f_93391_) {
                this.m_93410_(this.m_93518_());
            } else {
                this.m_93410_(this.m_93517_() + deltaY);
            }
        }
        return super.m_7979_(mouseX, mouseY, button, deltaX, deltaY);
    }

    private final void updateScrollingState(double mouseX, double mouseY) {
        this.scrolling = mouseX >= (double)this.m_5756_() && mouseX < (double)(this.m_5756_() + 3) && mouseY >= (double)this.f_93390_ && mouseY < (double)this.f_93391_;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0017\u0012\u0006\u0010\u001c\u001a\u00020\u001b\u0012\u0006\u0010\u0017\u001a\u00020\u0016\u00a2\u0006\u0004\b \u0010!J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J_\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0017\u001a\u00020\u00168\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001aR\u0017\u0010\u001c\u001a\u00020\u001b8\u0006\u00a2\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001f\u00a8\u0006\""}, d2={"Lcom/cobblemon/mod/common/client/gui/battle/widgets/BattleMessagePane$BattleMessageLine;", "Lnet/minecraft/client/gui/widget/AlwaysSelectedEntryListWidget$Entry;", "Lnet/minecraft/network/chat/MutableComponent;", "getNarration", "()Lnet/minecraft/network/chat/MutableComponent;", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "index", "rowTop", "rowLeft", "rowWidth", "rowHeight", "mouseX", "mouseY", "", "isHovered", "", "partialTicks", "", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIIIIIIZF)V", "Lnet/minecraft/util/FormattedCharSequence;", "line", "Lnet/minecraft/util/FormattedCharSequence;", "getLine", "()Lnet/minecraft/util/FormattedCharSequence;", "Lcom/cobblemon/mod/common/client/gui/battle/widgets/BattleMessagePane;", "pane", "Lcom/cobblemon/mod/common/client/gui/battle/widgets/BattleMessagePane;", "getPane", "()Lcom/cobblemon/mod/common/client/gui/battle/widgets/BattleMessagePane;", "<init>", "(Lcom/cobblemon/mod/common/client/gui/battle/widgets/BattleMessagePane;Lnet/minecraft/util/FormattedCharSequence;)V", "common"})
    public static final class BattleMessageLine
    extends ObjectSelectionList.Entry<BattleMessageLine> {
        @NotNull
        private final BattleMessagePane pane;
        @NotNull
        private final FormattedCharSequence line;

        public BattleMessageLine(@NotNull BattleMessagePane pane, @NotNull FormattedCharSequence line) {
            Intrinsics.checkNotNullParameter((Object)((Object)pane), (String)"pane");
            Intrinsics.checkNotNullParameter((Object)line, (String)"line");
            this.pane = pane;
            this.line = line;
        }

        @NotNull
        public final BattleMessagePane getPane() {
            return this.pane;
        }

        @NotNull
        public final FormattedCharSequence getLine() {
            return this.line;
        }

        @NotNull
        public MutableComponent getNarration() {
            return TextKt.text("");
        }

        public void m_6311_(@NotNull GuiGraphics context, int index, int rowTop, int rowLeft, int rowWidth, int rowHeight, int mouseX, int mouseY, boolean isHovered, float partialTicks) {
            Intrinsics.checkNotNullParameter((Object)context, (String)"context");
            RenderHelperKt.drawScaledText$default(context, this.line, rowLeft - 29, rowTop - 2, 0.0f, 0.0f, Float.valueOf(this.pane.getOpacity()), 0, false, false, 944, null);
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0004R\u0014\u0010\u0007\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0007\u0010\u0004R\u0014\u0010\b\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\b\u0010\u0004R\u0014\u0010\t\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\t\u0010\u0004R\u0014\u0010\n\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\n\u0010\u0004R\u0014\u0010\u000b\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\u0004R\u0014\u0010\r\u001a\u00020\f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\u000eR\u0016\u0010\u0011\u001a\u00020\u00108\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\u0012\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/client/gui/battle/widgets/BattleMessagePane$Companion;", "", "", "EXPAND_TOGGLE_SIZE", "I", "FRAME_EXPANDED_HEIGHT", "FRAME_HEIGHT", "FRAME_WIDTH", "LINE_HEIGHT", "LINE_WIDTH", "TEXT_BOX_HEIGHT", "TEXT_BOX_WIDTH", "Lnet/minecraft/resources/ResourceLocation;", "battleMessagePaneFrameExpandedResource", "Lnet/minecraft/resources/ResourceLocation;", "battleMessagePaneFrameResource", "", "expanded", "Z", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

