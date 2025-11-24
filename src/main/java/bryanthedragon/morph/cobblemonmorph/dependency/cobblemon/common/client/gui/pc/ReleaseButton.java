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
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.StorageWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB'\u0012\u0006\u0010\u0017\u001a\u00020\u000f\u0012\u0006\u0010\u0018\u001a\u00020\u000f\u0012\u0006\u0010\u0015\u001a\u00020\u0014\u0012\u0006\u0010\u001a\u001a\u00020\u0019\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u001d\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0017\u0010\u000b\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ/\u0010\u0012\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\u000f2\u0006\u0010\u0004\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0015\u001a\u00020\u00148\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0015\u0010\u0016\u00a8\u0006\u001e"}, d2={"Lcom/cobblemon/mod/common/client/gui/pc/ReleaseButton;", "Lnet/minecraft/client/gui/components/Button;", "", "mouseX", "mouseY", "", "isHovered", "(DD)Z", "Lnet/minecraft/client/sounds/SoundManager;", "pHandler", "", "playDownSound", "(Lnet/minecraft/client/sounds/SoundManager;)V", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "", "delta", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "Lcom/cobblemon/mod/common/client/gui/pc/StorageWidget;", "parent", "Lcom/cobblemon/mod/common/client/gui/pc/StorageWidget;", "x", "y", "Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;", "onPress", "<init>", "(IILcom/cobblemon/mod/common/client/gui/pc/StorageWidget;Lnet/minecraft/client/gui/components/Button$OnPress;)V", "Companion", "common"})
public final class ReleaseButton
extends Button {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final StorageWidget parent;
    private static final int WIDTH = 58;
    private static final int HEIGHT = 16;
    @NotNull
    private static final ResourceLocation buttonResource = MiscUtilsKt.cobblemonResource("textures/gui/pc/pc_release_button.png");

    public ReleaseButton(int x, int y, @NotNull StorageWidget parent, @NotNull Button.OnPress onPress) {
        Intrinsics.checkNotNullParameter((Object)((Object)parent), (String)"parent");
        Intrinsics.checkNotNullParameter((Object)onPress, (String)"onPress");
        super(x, y, 58, 16, (Component)Component.m_237113_((String)"Release"), onPress, Button.f_252438_);
        this.parent = parent;
    }

    public void m_88315_(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        if (this.parent.canDeleteSelected() && !this.parent.getDisplayConfirmRelease()) {
            PoseStack poseStack = context.m_280168_();
            ResourceLocation resourceLocation = buttonResource;
            int n = this.m_252754_();
            int n2 = this.m_252907_();
            int n3 = this.isHovered(mouseX, mouseY) ? 16 : 0;
            Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"matrices");
            GuiUtilsKt.blitk$default(poseStack, resourceLocation, n, n2, 16, 58, null, n3, null, 32, null, null, null, null, null, false, 0.0f, 130368, null);
            ResourceLocation resourceLocation2 = CobblemonResources.INSTANCE.getDEFAULT_LARGE();
            MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.pc.release", new Object[0]);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"ui.pc.release\")");
            RenderHelperKt.drawScaledText$default(context, resourceLocation2, TextKt.bold(mutableComponent), this.m_252754_() + 29, (double)this.m_252907_() + 3.5, 0.0f, null, 0, 0, true, true, null, null, 6624, null);
        }
    }

    public void m_7435_(@NotNull SoundManager pHandler) {
        Intrinsics.checkNotNullParameter((Object)pHandler, (String)"pHandler");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean isHovered(double mouseX, double mouseY) {
        float f = this.m_252754_();
        float f2 = (float)this.m_252754_() + (float)58;
        float f3 = (float)mouseX;
        if (!(f <= f3)) return false;
        if (!(f3 <= f2)) return false;
        boolean bl = true;
        if (!bl) return false;
        f = this.m_252907_();
        f2 = (float)this.m_252907_() + (float)16;
        f3 = (float)mouseY;
        if (!(f <= f3)) return false;
        if (!(f3 <= f2)) return false;
        return true;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nR\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0007\u001a\u00020\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/gui/pc/ReleaseButton$Companion;", "", "", "HEIGHT", "I", "WIDTH", "Lnet/minecraft/resources/ResourceLocation;", "buttonResource", "Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

