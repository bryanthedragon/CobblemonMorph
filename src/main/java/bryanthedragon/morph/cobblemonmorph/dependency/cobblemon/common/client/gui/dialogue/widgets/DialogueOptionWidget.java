/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.widgets;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.ParentWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.DialogueScreen;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.dialogue.InputToDialoguePacket;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u000b\u0018\u00002\u00020\u0001BW\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u0012\u0006\u0010\"\u001a\u00020!\u0012\u0006\u0010)\u001a\u00020(\u0012\u0006\u0010\u001d\u001a\u00020\u0007\u0012\u0006\u0010-\u001a\u00020\u0005\u0012\u0006\u0010.\u001a\u00020\u0005\u0012\u0006\u0010/\u001a\u00020\u0005\u0012\u0006\u00100\u001a\u00020\u0005\u0012\u0006\u0010&\u001a\u00020\u0018\u0012\u0006\u0010\u0019\u001a\u00020\u0018\u00a2\u0006\u0004\b1\u00102J'\u0010\b\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ/\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u000eH\u0014\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0014\u001a\u00020\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0019\u001a\u00020\u00188\u0006\u00a2\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001cR\u0017\u0010\u001d\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 R\u0017\u0010\"\u001a\u00020!8\u0006\u00a2\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%R\u0017\u0010&\u001a\u00020\u00188\u0006\u00a2\u0006\f\n\u0004\b&\u0010\u001a\u001a\u0004\b'\u0010\u001cR\u0017\u0010)\u001a\u00020(8\u0006\u00a2\u0006\f\n\u0004\b)\u0010*\u001a\u0004\b+\u0010,\u00a8\u00063"}, d2={"Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueOptionWidget;", "Lcom/cobblemon/mod/common/api/gui/ParentWidget;", "", "pMouseX", "pMouseY", "", "pButton", "", "mouseClicked", "(DDI)Z", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "mouseX", "mouseY", "", "delta", "", "renderButton", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "Lcom/cobblemon/mod/common/client/gui/dialogue/DialogueScreen;", "dialogueScreen", "Lcom/cobblemon/mod/common/client/gui/dialogue/DialogueScreen;", "getDialogueScreen", "()Lcom/cobblemon/mod/common/client/gui/dialogue/DialogueScreen;", "Lnet/minecraft/resources/ResourceLocation;", "overlayTexture", "Lnet/minecraft/resources/ResourceLocation;", "getOverlayTexture", "()Lnet/minecraft/resources/ResourceLocation;", "selectable", "Z", "getSelectable", "()Z", "Lnet/minecraft/network/chat/MutableComponent;", "text", "Lnet/minecraft/network/chat/MutableComponent;", "getText", "()Lnet/minecraft/network/chat/MutableComponent;", "texture", "getTexture", "", "value", "Ljava/lang/String;", "getValue", "()Ljava/lang/String;", "x", "y", "width", "height", "<init>", "(Lcom/cobblemon/mod/common/client/gui/dialogue/DialogueScreen;Lnet/minecraft/network/chat/MutableComponent;Ljava/lang/String;ZIIIILnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;)V", "common"})
public final class DialogueOptionWidget
extends ParentWidget {
    @NotNull
    private final DialogueScreen dialogueScreen;
    @NotNull
    private final MutableComponent text;
    @NotNull
    private final String value;
    private final boolean selectable;
    @NotNull
    private final ResourceLocation texture;
    @NotNull
    private final ResourceLocation overlayTexture;

    public DialogueOptionWidget(@NotNull DialogueScreen dialogueScreen, @NotNull MutableComponent text, @NotNull String value2, boolean selectable, int x, int y, int width, int height, @NotNull ResourceLocation texture, @NotNull ResourceLocation overlayTexture) {
        Intrinsics.checkNotNullParameter((Object)((Object)dialogueScreen), (String)"dialogueScreen");
        Intrinsics.checkNotNullParameter((Object)text, (String)"text");
        Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
        Intrinsics.checkNotNullParameter((Object)texture, (String)"texture");
        Intrinsics.checkNotNullParameter((Object)overlayTexture, (String)"overlayTexture");
        super(x, y, width, height, (Component)text);
        this.dialogueScreen = dialogueScreen;
        this.text = text;
        this.value = value2;
        this.selectable = selectable;
        this.texture = texture;
        this.overlayTexture = overlayTexture;
    }

    @NotNull
    public final DialogueScreen getDialogueScreen() {
        return this.dialogueScreen;
    }

    @NotNull
    public final MutableComponent getText() {
        return this.text;
    }

    @NotNull
    public final String getValue() {
        return this.value;
    }

    public final boolean getSelectable() {
        return this.selectable;
    }

    @NotNull
    public final ResourceLocation getTexture() {
        return this.texture;
    }

    @NotNull
    public final ResourceLocation getOverlayTexture() {
        return this.overlayTexture;
    }

    protected void m_87963_(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        ResourceLocation resourceLocation = this.texture;
        PoseStack poseStack = context.m_280168_();
        int n = this.m_252754_();
        int n2 = this.m_252907_();
        int n3 = this.f_93618_;
        int n4 = this.f_93619_;
        int n5 = this.selectable && this.m_274382_() ? 24 : 0;
        int n6 = this.f_93619_ * 2;
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"matrices");
        GuiUtilsKt.blitk$default(poseStack, resourceLocation, n, n2, n4, n3, null, n5, null, n6, null, null, null, null, null, false, 0.0f, 130368, null);
        resourceLocation = this.overlayTexture;
        poseStack = context.m_280168_();
        n = this.m_252754_();
        n2 = this.m_252907_();
        n3 = this.f_93618_;
        n4 = this.f_93619_;
        n5 = this.f_93618_;
        n6 = this.f_93619_;
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"matrices");
        GuiUtilsKt.blitk$default(poseStack, resourceLocation, n, n2, n4, n3, null, null, n5, n6, null, null, null, null, null, false, 0.0f, 130240, null);
        resourceLocation = this.text;
        int n7 = this.m_252754_() + this.f_93618_ / 2;
        n = this.m_252907_() + this.f_93619_ / 2 - 4;
        n2 = this.selectable ? 0xFFFFFF : 0x808080;
        GuiUtilsKt.drawCenteredText$default(context, null, (Component)resourceLocation, n7, n, n2, true, 2, null);
    }

    @Override
    public boolean m_6375_(double pMouseX, double pMouseY, int pButton) {
        if (!this.f_93622_) {
            return false;
        }
        if (!this.selectable || this.dialogueScreen.getWaitingForServerUpdate()) {
            return true;
        }
        this.dialogueScreen.sendToServer(new InputToDialoguePacket(this.dialogueScreen.getDialogueDTO().getDialogueInput().getInputId(), this.value));
        return true;
    }
}

