/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cB\u001f\u0012\u0006\u0010\u0016\u001a\u00020\u000f\u0012\u0006\u0010\u0017\u001a\u00020\u000f\u0012\u0006\u0010\u0019\u001a\u00020\u0018\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u001d\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0017\u0010\u000b\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ/\u0010\u0014\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0012H\u0014\u00a2\u0006\u0004\b\u0014\u0010\u0015\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/client/gui/ExitButton;", "Lnet/minecraft/client/gui/components/Button;", "", "mouseX", "mouseY", "", "isHovered", "(DD)Z", "Lnet/minecraft/client/sounds/SoundManager;", "soundManager", "", "playDownSound", "(Lnet/minecraft/client/sounds/SoundManager;)V", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "pMouseX", "pMouseY", "", "pPartialTicks", "renderButton", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "pX", "pY", "Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;", "onPress", "<init>", "(IILnet/minecraft/client/gui/components/Button$OnPress;)V", "Companion", "common"})
public final class ExitButton
extends Button {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private static final float WIDTH = 26.0f;
    private static final float HEIGHT = 13.0f;
    private static final float SCALE = 0.5f;
    @NotNull
    private static final ResourceLocation buttonResource = MiscUtilsKt.cobblemonResource("textures/gui/common/back_button.png");
    @NotNull
    private static final ResourceLocation iconResource = MiscUtilsKt.cobblemonResource("textures/gui/common/back_button_icon.png");

    public ExitButton(int pX, int pY, @NotNull Button.OnPress onPress) {
        Intrinsics.checkNotNullParameter((Object)onPress, (String)"onPress");
        super(pX, pY, 26, 13, (Component)Component.m_237113_((String)"Exit"), onPress, Button.f_252438_);
    }

    protected void m_87963_(@NotNull GuiGraphics context, int pMouseX, int pMouseY, float pPartialTicks) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        PoseStack poseStack = context.m_280168_();
        ResourceLocation resourceLocation = buttonResource;
        int n = this.m_252754_();
        int n2 = this.m_252907_();
        Number number = this.isHovered(pMouseX, pMouseY) ? (Number)Float.valueOf(13.0f) : (Number)0;
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"matrices");
        GuiUtilsKt.blitk$default(poseStack, resourceLocation, n, n2, Float.valueOf(13.0f), Float.valueOf(26.0f), null, number, null, Float.valueOf(26.0f), null, null, null, null, null, false, 0.0f, 130368, null);
        poseStack = context.m_280168_();
        resourceLocation = iconResource;
        float f = (float)(this.m_252754_() + 7) / 0.5f;
        float f2 = (float)(this.m_252907_() + 4) / 0.5f;
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"matrices");
        GuiUtilsKt.blitk$default(poseStack, resourceLocation, Float.valueOf(f), Float.valueOf(f2), 11, 21, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
    }

    public void m_7435_(@NotNull SoundManager soundManager) {
        Intrinsics.checkNotNullParameter((Object)soundManager, (String)"soundManager");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean isHovered(double mouseX, double mouseY) {
        float f = this.m_252754_();
        float f2 = (float)this.m_252754_() + 26.0f;
        float f3 = (float)mouseX;
        if (!(f <= f3)) return false;
        if (!(f3 <= f2)) return false;
        boolean bl = true;
        if (!bl) return false;
        f = this.m_252907_();
        f2 = (float)this.m_252907_() + 13.0f;
        f3 = (float)mouseY;
        if (!(f <= f3)) return false;
        if (!(f3 <= f2)) return false;
        return true;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fR\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0004R\u0014\u0010\b\u001a\u00020\u00078\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u00078\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\n\u0010\t\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/client/gui/ExitButton$Companion;", "", "", "HEIGHT", "F", "SCALE", "WIDTH", "Lnet/minecraft/resources/ResourceLocation;", "buttonResource", "Lnet/minecraft/resources/ResourceLocation;", "iconResource", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

