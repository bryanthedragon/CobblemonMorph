/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Renderable
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.core.Vec3i
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.widgets;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.DialogueScreen;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0018\u0018\u0000 *2\u00020\u00012\u00020\u0002:\u0001*B/\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u0012\u0006\u0010$\u001a\u00020\b\u0012\u0006\u0010&\u001a\u00020\b\u0012\u0006\u0010\"\u001a\u00020\b\u0012\u0006\u0010\u0018\u001a\u00020\b\u00a2\u0006\u0004\b(\u0010)J\u000f\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005J/\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0017\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0014\u001a\u00020\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0018\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR\"\u0010\u001c\u001a\u00020\u000b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u0017\u0010\"\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\"\u0010\u0019\u001a\u0004\b#\u0010\u001bR\u0017\u0010$\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b$\u0010\u0019\u001a\u0004\b%\u0010\u001bR\u0017\u0010&\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b&\u0010\u0019\u001a\u0004\b'\u0010\u001b\u00a8\u0006+"}, d2={"Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueTimerWidget;", "Lnet/minecraft/client/gui/components/Renderable;", "Lnet/minecraft/client/gui/components/events/GuiEventListener;", "", "isFocused", "()Z", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "mouseX", "mouseY", "", "delta", "", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "focused", "setFocused", "(Z)V", "Lcom/cobblemon/mod/common/client/gui/dialogue/DialogueScreen;", "dialogueScreen", "Lcom/cobblemon/mod/common/client/gui/dialogue/DialogueScreen;", "getDialogueScreen", "()Lcom/cobblemon/mod/common/client/gui/dialogue/DialogueScreen;", "height", "I", "getHeight", "()I", "ratio", "F", "getRatio", "()F", "setRatio", "(F)V", "width", "getWidth", "x", "getX", "y", "getY", "<init>", "(Lcom/cobblemon/mod/common/client/gui/dialogue/DialogueScreen;IIII)V", "Companion", "common"})
public final class DialogueTimerWidget
implements Renderable,
GuiEventListener {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final DialogueScreen dialogueScreen;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private float ratio;
    @NotNull
    private static final ResourceLocation timerResource = MiscUtilsKt.cobblemonResource("textures/gui/dialogue/dialogue_bar.png");
    @NotNull
    private static final Vec3i BG_COLOUR = new Vec3i(128, 128, 128);

    public DialogueTimerWidget(@NotNull DialogueScreen dialogueScreen, int x, int y, int width, int height) {
        Intrinsics.checkNotNullParameter((Object)((Object)dialogueScreen), (String)"dialogueScreen");
        this.dialogueScreen = dialogueScreen;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.ratio = 1.0f;
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

    public final int getWidth() {
        return this.width;
    }

    public final int getHeight() {
        return this.height;
    }

    public boolean m_93696_() {
        return false;
    }

    public void m_93692_(boolean focused) {
    }

    public final float getRatio() {
        return this.ratio;
    }

    public final void setRatio(float f) {
        this.ratio = f;
    }

    public void m_88315_(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        if (this.ratio < 0.0f || this.ratio > 1.0f || this.dialogueScreen.getWaitingForServerUpdate() || !this.dialogueScreen.getDialogueDTO().getDialogueInput().getShowTimer()) {
            return;
        }
        ResourceLocation resourceLocation = CobblemonResources.INSTANCE.getWHITE();
        PoseStack poseStack = context.m_280168_();
        int n = this.x + 3;
        int n2 = this.y + 2;
        int n3 = this.width - 5;
        int n4 = this.height - 2;
        float f = (float)BG_COLOUR.m_123341_() / 255.0f;
        float f2 = (float)BG_COLOUR.m_123342_() / 255.0f;
        float f3 = (float)BG_COLOUR.m_123343_() / 255.0f;
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"matrices");
        GuiUtilsKt.blitk$default(poseStack, resourceLocation, n, n2, n4, n3, null, null, null, null, null, Float.valueOf(f), Float.valueOf(f2), Float.valueOf(f3), null, false, 0.0f, 83904, null);
        context.m_280246_(1.0f, 1.0f, 1.0f, 1.0f);
        resourceLocation = timerResource;
        poseStack = context.m_280168_();
        n = this.x;
        n2 = this.y;
        n3 = this.width;
        n4 = this.height;
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"matrices");
        GuiUtilsKt.blitk$default(poseStack, resourceLocation, n, n2, n4, n3, null, null, null, null, null, null, null, null, null, false, 0.0f, 98240, null);
        resourceLocation = CobblemonResources.INSTANCE.getWHITE();
        poseStack = context.m_280168_();
        float f4 = (float)this.x + (float)3;
        float f5 = (float)this.y + (float)2;
        float f6 = (float)this.width * this.ratio - (float)4;
        n4 = this.height - 5;
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"matrices");
        GuiUtilsKt.blitk$default(poseStack, resourceLocation, Float.valueOf(f4), Float.valueOf(f5), n4, Float.valueOf(f6), null, null, 1, 1, null, Float.valueOf(0.75686276f), Float.valueOf(0.6313726f), Float.valueOf(0.1254902f), null, false, 0.0f, 83136, null);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\n\u0010\u000bR\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0017\u0010\u0006\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\t\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueTimerWidget$Companion;", "", "Lnet/minecraft/core/Vec3i;", "BG_COLOUR", "Lnet/minecraft/core/Vec3i;", "Lnet/minecraft/resources/ResourceLocation;", "timerResource", "Lnet/minecraft/resources/ResourceLocation;", "getTimerResource", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getTimerResource() {
            return timerResource;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

