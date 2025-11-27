/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.widgets;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.DialogueScreen;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.dto.DialogueInputDTO;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.dialogue.InputToDialoguePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001B9\u0012\u0006\u0010\u0017\u001a\u00020\u0016\u0012\u0006\u0010\u001b\u001a\u00020\u0002\u0012\u0006\u0010\u001c\u001a\u00020\u0002\u0012\u0006\u0010\u001d\u001a\u00020\u0002\u0012\u0006\u0010\u001e\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u001f\u001a\u00020\u0002\u00a2\u0006\u0004\b \u0010!J'\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ'\u0010\r\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ/\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0017\u001a\u00020\u00168\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006\""}, d2={"Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueTextInputWidget;", "Lnet/minecraft/client/gui/components/EditBox;", "", "keyCode", "scanCode", "modifiers", "", "keyPressed", "(III)Z", "", "mouseX", "mouseY", "button", "mouseClicked", "(DDI)Z", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "delta", "", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "Lcom/cobblemon/mod/common/client/gui/dialogue/DialogueScreen;", "dialogueScreen", "Lcom/cobblemon/mod/common/client/gui/dialogue/DialogueScreen;", "getDialogueScreen", "()Lcom/cobblemon/mod/common/client/gui/dialogue/DialogueScreen;", "x", "y", "width", "height", "maxLength", "<init>", "(Lcom/cobblemon/mod/common/client/gui/dialogue/DialogueScreen;IIIII)V", "common"})
public final class DialogueTextInputWidget
extends EditBox {
    @NotNull
    private final DialogueScreen dialogueScreen;

    public DialogueTextInputWidget(@NotNull DialogueScreen dialogueScreen, int x, int y, int width, int height, int maxLength) {
        Intrinsics.checkNotNullParameter((Object)((Object)dialogueScreen), (String)"dialogueScreen");
        super(Minecraft.m_91087_().f_91062_, x, y, width, height, (Component)TextKt.text("gui_dialogue_text_input"));
        this.dialogueScreen = dialogueScreen;
        this.m_94199_(maxLength);
        this.m_93692_(true);
    }

    public /* synthetic */ DialogueTextInputWidget(DialogueScreen dialogueScreen, int n, int n2, int n3, int n4, int n5, int n6, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n6 & 0x20) != 0) {
            n5 = 100;
        }
        this(dialogueScreen, n, n2, n3, n4, n5);
    }

    @NotNull
    public final DialogueScreen getDialogueScreen() {
        return this.dialogueScreen;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean m_6375_(double mouseX, double mouseY, int button) {
        if (this.dialogueScreen.getDialogueDTO().getDialogueInput().getInputType() != DialogueInputDTO.InputType.TEXT) return false;
        if (this.dialogueScreen.getWaitingForServerUpdate()) {
            return false;
        }
        int n = this.m_252754_();
        int n2 = this.m_252754_() + this.f_93618_;
        int n3 = (int)mouseX;
        if (n > n3) return false;
        if (n3 > n2) return false;
        boolean bl = true;
        if (!bl) return false;
        n = this.m_252907_();
        n2 = this.m_252907_() + this.f_93619_;
        n3 = (int)mouseY;
        if (n > n3) return false;
        if (n3 > n2) return false;
        boolean bl2 = true;
        if (!bl2) return false;
        this.m_93692_(true);
        return true;
    }

    public void m_88315_(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        if (this.dialogueScreen.getDialogueDTO().getDialogueInput().getInputType() != DialogueInputDTO.InputType.TEXT || this.dialogueScreen.getWaitingForServerUpdate()) {
            return;
        }
        if (this.m_94207_() != this.m_94155_().length()) {
            this.m_94201_();
        }
        PoseStack poseStack = context.m_280168_();
        int n = this.m_252754_();
        int n2 = this.m_252907_();
        int n3 = this.f_93618_;
        int n4 = this.f_93619_;
        ResourceLocation resourceLocation = MiscUtils.cobblemonResource("textures/gui/dialogue/dialogue_text_input.png");
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"matrices");
        GuiUtilsKt.blitk$default(poseStack, resourceLocation, n, n2, n4, n3, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
        Object object = this.m_93696_() ? this.m_94155_() + "|" : this.m_94155_();
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"if (isFocused) \"$text|\" else text");
        poseStack = TextKt.text((String)object);
        float f = (float)this.m_252754_() + (float)this.f_93618_ / 2.0f;
        n2 = this.m_252907_() + this.f_93619_ / 2 - 4;
        GuiUtilsKt.drawCenteredText$default(context, null, (Component)poseStack, Float.valueOf(f), n2, 0xFFFFFF, true, 2, null);
    }

    public boolean m_7933_(int keyCode, int scanCode, int modifiers) {
        switch (keyCode) {
            case 256: 
            case 257: 
            case 335: {
                UUID uUID = this.dialogueScreen.getDialogueDTO().getDialogueInput().getInputId();
                String string = this.m_94155_();
                Intrinsics.checkNotNullExpressionValue((Object)string, (String)"text");
                this.dialogueScreen.sendToServer(new InputToDialoguePacket(uUID, ((Object)StringsKt.trim((CharSequence)string)).toString()));
            }
        }
        return super.m_7933_(keyCode, scanCode, modifiers);
    }
}

