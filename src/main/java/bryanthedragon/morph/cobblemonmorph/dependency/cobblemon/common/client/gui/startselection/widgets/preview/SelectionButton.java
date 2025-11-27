/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.startselection.widgets.preview;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B/\u0012\u0006\u0010\u0010\u001a\u00020\t\u0012\u0006\u0010\u0011\u001a\u00020\t\u0012\u0006\u0010\u0012\u001a\u00020\t\u0012\u0006\u0010\u0013\u001a\u00020\t\u0012\u0006\u0010\u0015\u001a\u00020\u0014\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0019\u0010\u0005\u001a\u00020\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J/\u0010\u000e\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\fH\u0014\u00a2\u0006\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/client/gui/startselection/widgets/preview/SelectionButton;", "Lnet/minecraft/client/gui/components/Button;", "Lnet/minecraft/client/sounds/SoundManager;", "soundManager", "", "playDownSound", "(Lnet/minecraft/client/sounds/SoundManager;)V", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "mouseX", "mouseY", "", "delta", "renderButton", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "pX", "pY", "pWidth", "pHeight", "Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;", "onPress", "<init>", "(IIIILnet/minecraft/client/gui/components/Button$OnPress;)V", "Companion", "common"})
public final class SelectionButton
extends Button {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private static final ResourceLocation buttonTexture = MiscUtils.cobblemonResource("textures/gui/starterselection/starterselection_button.png");
    public static final int BUTTON_WIDTH = 56;
    public static final int BUTTON_HEIGHT = 12;
    private static final float SCALE = 0.7f;

    public SelectionButton(int pX, int pY, int pWidth, int pHeight, @NotNull Button.OnPress onPress) {
        Intrinsics.checkNotNullParameter((Object)onPress, (String)"onPress");
        super(pX, pY, pWidth, pHeight, (Component)Component.m_237113_((String)"SelectionButton"), onPress, Button.f_252438_);
    }

    public void m_7435_(@Nullable SoundManager soundManager) {
        Minecraft.m_91087_().m_91106_().m_120367_((SoundInstance)SimpleSoundInstance.m_119752_((SoundEvent)CobblemonSounds.GUI_CLICK, (float)1.0f));
    }

    protected void m_87963_(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        ResourceLocation resourceLocation;
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        PoseStack matrices = context.m_280168_();
        if (this.m_274382_()) {
            resourceLocation = buttonTexture;
            var7_7 = (double)this.m_252754_() + 0.6;
            double d = (double)this.m_252907_() + 0.6;
            Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
            GuiUtilsKt.blitk$default(matrices, resourceLocation, var7_7, d, 11.75, 55.75, null, null, null, null, null, Float.valueOf(0.75f), Float.valueOf(0.75f), Float.valueOf(0.75f), null, false, 0.0f, 116672, null);
        } else {
            resourceLocation = buttonTexture;
            var7_7 = (double)this.m_252754_() + 0.6;
            double d = (double)this.m_252907_() + 0.6;
            Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
            GuiUtilsKt.blitk$default(matrices, resourceLocation, var7_7, d, 11.75, 55.75, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
        }
        resourceLocation = LocalizationUtilsKt.lang("ui.starter.choosebutton", new Object[0]);
        int n = this.m_252754_() + 28;
        double d = (double)(this.m_252907_() + 6) - 2.4;
        Intrinsics.checkNotNullExpressionValue((Object)resourceLocation, (String)"lang(\"ui.starter.choosebutton\")");
        RenderHelperKt.drawScaledText$default(context, null, (MutableComponent)resourceLocation, n, d, 0.7f, null, 68, 0xFFFFFF, true, true, null, null, 6210, null);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rR\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0007\u001a\u00020\u00068\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0007\u0010\bR\u0014\u0010\n\u001a\u00020\t8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/client/gui/startselection/widgets/preview/SelectionButton$Companion;", "", "", "BUTTON_HEIGHT", "I", "BUTTON_WIDTH", "", "SCALE", "F", "Lnet/minecraft/resources/ResourceLocation;", "buttonTexture", "Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

