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
 *  net.minecraft.client.gui.components.AbstractSelectionList$Entry
 *  net.minecraft.client.gui.components.ObjectSelectionList
 *  net.minecraft.client.gui.components.ObjectSelectionList$Entry
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.locale.Language
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.FormattedCharSequence
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.widgets;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.DialogueScreen;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.dto.DialogueDTO;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.dto.DialogueInputDTO;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.dialogue.InputToDialoguePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
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
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u0004\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 K2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002KLBA\u0012\u0006\u00102\u001a\u000201\u0012\b\b\u0002\u0010A\u001a\u00020\u0004\u0012\b\b\u0002\u0010C\u001a\u00020\u0004\u0012\u0006\u00106\u001a\u00020\u0004\u0012\u0006\u0010E\u001a\u00020\u0004\u0012\f\u0010H\u001a\b\u0012\u0004\u0012\u00020G0F\u00a2\u0006\u0004\bI\u0010JJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0014\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u000f\u0010\b\u001a\u00020\u0007H\u0002\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\f\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\nH\u0014\u00a2\u0006\u0004\b\f\u0010\rJ\u000f\u0010\u000e\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u000f\u0010\u0011\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u000f\u0010\u0013\u001a\u00020\u0004H\u0014\u00a2\u0006\u0004\b\u0013\u0010\u000fJ'\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019J7\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u001c\u0010\u001dJ/\u0010 \u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u001eH\u0016\u00a2\u0006\u0004\b \u0010!J\u0017\u0010$\u001a\u00020\u00042\u0006\u0010#\u001a\u00020\"H\u0002\u00a2\u0006\u0004\b$\u0010%J\u001f\u0010&\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u0010H\u0002\u00a2\u0006\u0004\b&\u0010'R\u0011\u0010)\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b(\u0010\u000fR\u0011\u0010+\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b*\u0010\u000fR\u0017\u0010-\u001a\u00020,8\u0006\u00a2\u0006\f\n\u0004\b-\u0010.\u001a\u0004\b/\u00100R\u0017\u00102\u001a\u0002018\u0006\u00a2\u0006\f\n\u0004\b2\u00103\u001a\u0004\b4\u00105R\u0017\u00106\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b6\u00107\u001a\u0004\b8\u0010\u000fR\"\u00109\u001a\u00020\u001e8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b9\u0010:\u001a\u0004\b;\u0010<\"\u0004\b=\u0010>R\u0016\u0010?\u001a\u00020\u00178\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b?\u0010@R\u0017\u0010A\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\bA\u00107\u001a\u0004\bB\u0010\u000fR\u0017\u0010C\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\bC\u00107\u001a\u0004\bD\u0010\u000f\u00a8\u0006M"}, d2={"Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueBox;", "Lnet/minecraft/client/gui/components/ObjectSelectionList;", "Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueBox$DialogueLine;", "entry", "", "addEntry", "(Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueBox$DialogueLine;)I", "", "correctSize", "()V", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "enableScissor", "(Lnet/minecraft/client/gui/GuiGraphics;)V", "getRowWidth", "()I", "", "getScrollAmount", "()D", "getScrollbarPositionX", "mouseX", "mouseY", "button", "", "mouseClicked", "(DDI)Z", "deltaX", "deltaY", "mouseDragged", "(DDIDD)Z", "", "partialTicks", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "", "i", "scaleIt", "(Ljava/lang/Number;)I", "updateScrollingState", "(DD)V", "getAppropriateX", "appropriateX", "getAppropriateY", "appropriateY", "Lcom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueDTO;", "dialogue", "Lcom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueDTO;", "getDialogue", "()Lcom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueDTO;", "Lcom/cobblemon/mod/common/client/gui/dialogue/DialogueScreen;", "dialogueScreen", "Lcom/cobblemon/mod/common/client/gui/dialogue/DialogueScreen;", "getDialogueScreen", "()Lcom/cobblemon/mod/common/client/gui/dialogue/DialogueScreen;", "frameWidth", "I", "getFrameWidth", "opacity", "F", "getOpacity", "()F", "setOpacity", "(F)V", "scrolling", "Z", "x", "getX", "y", "getY", "height", "", "Lnet/minecraft/network/chat/MutableComponent;", "messages", "<init>", "(Lcom/cobblemon/mod/common/client/gui/dialogue/DialogueScreen;IIIILjava/util/List;)V", "Companion", "DialogueLine", "common"})
@SourceDebugExtension(value={"SMAP\nDialogueBox.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DialogueBox.kt\ncom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueBox\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,194:1\n1360#2:195\n1446#2,5:196\n1855#2,2:201\n*S KotlinDebug\n*F\n+ 1 DialogueBox.kt\ncom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueBox\n*L\n64#1:195\n64#1:196,5\n65#1:201,2\n*E\n"})
public final class DialogueBox
extends ObjectSelectionList<DialogueLine> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final DialogueScreen dialogueScreen;
    private final int x;
    private final int y;
    private final int frameWidth;
    @NotNull
    private final DialogueDTO dialogue;
    private float opacity;
    private boolean scrolling;
    public static final int LINE_HEIGHT = 10;
    public static final int LINE_WIDTH = 142;
    @NotNull
    private static final ResourceLocation boxResource = MiscUtilsKt.cobblemonResource("textures/gui/dialogue/dialogue_box.png");

    /*
     * WARNING - void declaration
     */
    public DialogueBox(@NotNull DialogueScreen dialogueScreen, int x, int y, int frameWidth, int height, @NotNull List<? extends MutableComponent> messages) {
        void $this$forEach$iv;
        void $this$flatMapTo$iv$iv;
        Iterable $this$flatMap$iv;
        Intrinsics.checkNotNullParameter((Object)((Object)dialogueScreen), (String)"dialogueScreen");
        Intrinsics.checkNotNullParameter(messages, (String)"messages");
        super(Minecraft.m_91087_(), frameWidth - 14, height, 1, 1 + height, 10);
        this.dialogueScreen = dialogueScreen;
        this.x = x;
        this.y = y;
        this.frameWidth = frameWidth;
        this.dialogue = this.dialogueScreen.getDialogueDTO();
        this.opacity = 1.0f;
        this.correctSize();
        this.m_93496_(false);
        this.m_93488_(false);
        this.m_93471_(false);
        Font textRenderer = Minecraft.m_91087_().f_91062_;
        Iterable iterable = messages;
        boolean $i$f$flatMap = false;
        Iterator iterator = $this$flatMap$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$flatMapTo = false;
        for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
            MutableComponent it = (MutableComponent)element$iv$iv;
            boolean bl = false;
            List list = Language.m_128107_().m_128112_(textRenderer.m_92865_().m_92414_((FormattedText)it, 142, it.m_7383_()));
            Intrinsics.checkNotNullExpressionValue((Object)list, (String)"getInstance().reorder(te\u2026t, LINE_WIDTH, it.style))");
            Iterable list$iv$iv = list;
            CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
        }
        $this$flatMap$iv = (List)destination$iv$iv;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            FormattedCharSequence it = (FormattedCharSequence)element$iv;
            boolean bl = false;
            Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
            this.addEntry(new DialogueLine(it));
        }
    }

    public /* synthetic */ DialogueBox(DialogueScreen dialogueScreen, int n, int n2, int n3, int n4, List list, int n5, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n5 & 2) != 0) {
            n = 0;
        }
        if ((n5 & 4) != 0) {
            n2 = 0;
        }
        this(dialogueScreen, n, n2, n3, n4, list);
    }

    @NotNull
    public final DialogueScreen getDialogueScreen() {
        return this.dialogueScreen;
    }

    public final int getX() {
        return this.x;
    }

    public final int getY() {
        return this.y;
    }

    public final int getFrameWidth() {
        return this.frameWidth;
    }

    @NotNull
    public final DialogueDTO getDialogue() {
        return this.dialogue;
    }

    public final float getOpacity() {
        return this.opacity;
    }

    public final void setOpacity(float f) {
        this.opacity = f;
    }

    public final int getAppropriateX() {
        return this.x;
    }

    public final int getAppropriateY() {
        return this.y;
    }

    private final void correctSize() {
        int textBoxHeight = this.f_93389_;
        this.m_93437_(this.f_93388_, textBoxHeight, this.getAppropriateY() + 6, this.getAppropriateY() + 6 + textBoxHeight);
        this.m_93507_(this.getAppropriateX() + 8);
    }

    protected int addEntry(@NotNull DialogueLine entry) {
        Intrinsics.checkNotNullParameter((Object)((Object)entry), (String)"entry");
        return super.m_7085_((AbstractSelectionList.Entry)entry);
    }

    public int m_5759_() {
        return 80;
    }

    protected int m_5756_() {
        return this.f_93393_ + 144;
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
        GuiUtilsKt.blitk$default(poseStack, boxResource, this.f_93393_ - 8, this.getAppropriateY() - 1, this.f_93389_ + 12, this.frameWidth, null, null, null, null, null, null, null, null, Float.valueOf(this.opacity), false, 0.0f, 114624, null);
        super.m_88315_(context, mouseX, mouseY, partialTicks);
    }

    protected void m_280310_(@NotNull GuiGraphics context) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        int textBoxHeight = this.f_93389_;
        context.m_280588_(this.f_93393_, this.getAppropriateY() + 7, this.f_93393_ + this.f_93388_ - 10, this.getAppropriateY() + 7 + textBoxHeight);
    }

    public boolean m_6375_(double mouseX, double mouseY, int button) {
        DialogueInputDTO.InputType[] inputTypeArray;
        int toggleOffsetY = 92;
        if (!this.dialogueScreen.getWaitingForServerUpdate() && mouseX > (double)this.f_93393_ && mouseX < (double)(this.f_93393_ + 160) && mouseY > (double)this.getAppropriateY() && mouseY < (double)(this.getAppropriateY() + this.f_93389_) && this.dialogue.getDialogueInput().getAllowSkip() && CollectionsKt.listOf((Object[])(inputTypeArray = new DialogueInputDTO.InputType[]{DialogueInputDTO.InputType.NONE, DialogueInputDTO.InputType.AUTO_CONTINUE})).contains((Object)this.dialogue.getDialogueInput().getInputType())) {
            this.dialogueScreen.sendToServer(new InputToDialoguePacket(this.dialogue.getDialogueInput().getInputId(), "skip!"));
            return true;
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

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nR\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0007\u001a\u00020\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueBox$Companion;", "", "", "LINE_HEIGHT", "I", "LINE_WIDTH", "Lnet/minecraft/resources/ResourceLocation;", "boxResource", "Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\u0012\u0006\u0010\u0017\u001a\u00020\u0016\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J_\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0017\u001a\u00020\u00168\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueBox$DialogueLine;", "Lnet/minecraft/client/gui/widget/AlwaysSelectedEntryListWidget$Entry;", "Lnet/minecraft/network/chat/MutableComponent;", "getNarration", "()Lnet/minecraft/network/chat/MutableComponent;", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "index", "rowTop", "rowLeft", "rowWidth", "rowHeight", "mouseX", "mouseY", "", "isHovered", "", "partialTicks", "", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIIIIIIZF)V", "Lnet/minecraft/util/FormattedCharSequence;", "line", "Lnet/minecraft/util/FormattedCharSequence;", "getLine", "()Lnet/minecraft/util/FormattedCharSequence;", "<init>", "(Lnet/minecraft/util/FormattedCharSequence;)V", "common"})
    public static final class DialogueLine
    extends ObjectSelectionList.Entry<DialogueLine> {
        @NotNull
        private final FormattedCharSequence line;

        public DialogueLine(@NotNull FormattedCharSequence line) {
            Intrinsics.checkNotNullParameter((Object)line, (String)"line");
            this.line = line;
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
            RenderHelperKt.drawScaledText$default(context, this.line, rowLeft - 38, rowTop - 2, 0.0f, 0.0f, null, 0, false, false, 1008, null);
        }
    }
}

