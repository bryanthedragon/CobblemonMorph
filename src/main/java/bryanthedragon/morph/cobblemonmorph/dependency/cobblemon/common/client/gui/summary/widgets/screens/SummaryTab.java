/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.screens;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u001b\u001a\u00020\t\u0012\u0006\u0010\u001c\u001a\u00020\t\u0012\u0006\u0010\u0017\u001a\u00020\u0016\u0012\u0006\u0010\u001e\u001a\u00020\u001d\u00a2\u0006\u0004\b\u001f\u0010 J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J/\u0010\u000e\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\fH\u0014\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0017\u0010\u0012\u001a\u00020\u00042\b\b\u0002\u0010\u0011\u001a\u00020\u0010\u00a2\u0006\u0004\b\u0012\u0010\u0013R\u0016\u0010\u0014\u001a\u00020\u00108\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0017\u001a\u00020\u00168\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006!"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/SummaryTab;", "Lnet/minecraft/client/gui/components/Button;", "Lnet/minecraft/client/sounds/SoundManager;", "soundManager", "", "playDownSound", "(Lnet/minecraft/client/sounds/SoundManager;)V", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "pMouseX", "pMouseY", "", "pPartialTicks", "renderButton", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "", "state", "toggleTab", "(Z)V", "isActive", "Z", "Lnet/minecraft/network/chat/MutableComponent;", "label", "Lnet/minecraft/network/chat/MutableComponent;", "getLabel", "()Lnet/minecraft/network/chat/MutableComponent;", "pX", "pY", "Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;", "onPress", "<init>", "(IILnet/minecraft/network/chat/MutableComponent;Lnet/minecraft/client/gui/components/Button$OnPress;)V", "common"})
public final class SummaryTab
extends Button {
    @NotNull
    private final MutableComponent label;
    private boolean isActive;

    public SummaryTab(int pX, int pY, @NotNull MutableComponent label, @NotNull Button.OnPress onPress) {
        Intrinsics.checkNotNullParameter((Object)label, (String)"label");
        Intrinsics.checkNotNullParameter((Object)onPress, (String)"onPress");
        super(pX, pY, 50, 13, (Component)label, onPress, Button.f_252438_);
        this.label = label;
    }

    @NotNull
    public final MutableComponent getLabel() {
        return this.label;
    }

    protected void m_87963_(@NotNull GuiGraphics context, int pMouseX, int pMouseY, float pPartialTicks) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        PoseStack matrices = context.m_280168_();
        if (this.isActive) {
            ResourceLocation resourceLocation = MiscUtilsKt.cobblemonResource("textures/gui/summary/summary_tab.png");
            int n = this.m_252754_();
            int n2 = this.m_252907_();
            int n3 = this.f_93618_;
            int n4 = this.f_93619_;
            Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
            GuiUtilsKt.blitk$default(matrices, resourceLocation, n, n2, n4, n3, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
        }
        RenderHelperKt.drawScaledText$default(context, CobblemonResources.INSTANCE.getDEFAULT_LARGE(), TextKt.bold(this.label), this.m_252754_() + 25, this.m_252907_() + 3, 0.0f, null, 0, 0, true, true, null, null, 6624, null);
    }

    public void m_7435_(@NotNull SoundManager soundManager) {
        Intrinsics.checkNotNullParameter((Object)soundManager, (String)"soundManager");
    }

    public final void toggleTab(boolean state) {
        this.isActive = state;
    }

    public static /* synthetic */ void toggleTab$default(SummaryTab summaryTab, boolean bl, int n, Object object) {
        if ((n & 1) != 0) {
            bl = true;
        }
        summaryTab.toggleTab(bl);
    }
}

