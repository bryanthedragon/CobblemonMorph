/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.client.gui.components.ImageButton
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.startselection.widgets;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bBG\u0012\u0006\u0010\u0010\u001a\u00020\t\u0012\u0006\u0010\u0011\u001a\u00020\t\u0012\u0006\u0010\u0012\u001a\u00020\t\u0012\u0006\u0010\u0013\u001a\u00020\t\u0012\u0006\u0010\u0014\u001a\u00020\t\u0012\u0006\u0010\u0015\u001a\u00020\t\u0012\u0006\u0010\u0016\u001a\u00020\t\u0012\u0006\u0010\u0018\u001a\u00020\u0017\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J/\u0010\u000e\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000f\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/client/gui/startselection/widgets/ExitButton;", "Lnet/minecraft/client/gui/components/ImageButton;", "Lnet/minecraft/client/sounds/SoundManager;", "soundManager", "", "playDownSound", "(Lnet/minecraft/client/sounds/SoundManager;)V", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "pMouseX", "pMouseY", "", "pPartialTicks", "renderButton", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "pX", "pY", "pWidth", "pHeight", "pXTexStart", "pYTexStart", "pYDiffText", "Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;", "onPress", "<init>", "(IIIIIIILnet/minecraft/client/gui/components/Button$OnPress;)V", "Companion", "common"})
public final class ExitButton
extends ImageButton {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private static final float EXIT_BUTTON_WIDTH = 15.95f;
    private static final float EXIT_BUTTON_HEIGHT = 11.95f;
    @NotNull
    private static final ResourceLocation exitButtonResource = MiscUtilsKt.cobblemonResource("textures/gui/starterselection/starterselection_exit.png");

    public ExitButton(int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, int pYDiffText, @NotNull Button.OnPress onPress) {
        Intrinsics.checkNotNullParameter((Object)onPress, (String)"onPress");
        super(pX, pY, pWidth, pHeight, pXTexStart, pYTexStart, pYDiffText, exitButtonResource, onPress);
    }

    public void m_87963_(@NotNull GuiGraphics context, int pMouseX, int pMouseY, float pPartialTicks) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        boolean bl = this.f_93622_ = pMouseX >= this.m_252754_() && pMouseY >= this.m_252907_() && pMouseX < this.m_252754_() + this.f_93618_ && pMouseY < this.m_252907_() + this.f_93619_;
        if (this.m_274382_()) {
            PoseStack poseStack = context.m_280168_();
            float f = (float)this.m_252754_() + 0.075f;
            float f2 = (float)this.m_252907_() + 1.05f;
            ResourceLocation resourceLocation = exitButtonResource;
            Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"matrices");
            GuiUtilsKt.blitk$default(poseStack, resourceLocation, Float.valueOf(f), Float.valueOf(f2), Float.valueOf(11.95f), Float.valueOf(15.95f), null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
        }
    }

    public void m_7435_(@NotNull SoundManager soundManager) {
        Intrinsics.checkNotNullParameter((Object)soundManager, (String)"soundManager");
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nR\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0007\u001a\u00020\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/gui/startselection/widgets/ExitButton$Companion;", "", "", "EXIT_BUTTON_HEIGHT", "F", "EXIT_BUTTON_WIDTH", "Lnet/minecraft/resources/ResourceLocation;", "exitButtonResource", "Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

