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
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.screens.moves;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 -2\u00020\u0001:\u0001-B'\u0012\u0006\u0010#\u001a\u00020\f\u0012\u0006\u0010'\u001a\u00020\f\u0012\u0006\u0010!\u001a\u00020\u0007\u0012\u0006\u0010*\u001a\u00020)\u00a2\u0006\u0004\b+\u0010,J%\u0010\b\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0004\b\b\u0010\tJ7\u0010\u0010\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u001f\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0012\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u001f\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0012\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0016J\u0017\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u0018H\u0016\u00a2\u0006\u0004\b\u001a\u0010\u001bJ/\u0010\u001f\u001a\u00020\u00142\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\f2\u0006\u0010\u001e\u001a\u00020\u0005H\u0014\u00a2\u0006\u0004\b\u001f\u0010 R\u0014\u0010!\u001a\u00020\u00078\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b!\u0010\"R\u0017\u0010#\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b#\u0010$\u001a\u0004\b%\u0010&R\u0017\u0010'\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b'\u0010$\u001a\u0004\b(\u0010&\u00a8\u0006."}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/ReorderMoveButton;", "Lnet/minecraft/client/gui/components/Button;", "", "mouseX", "mouseY", "", "offsetY", "", "isHovered", "(DDF)Z", "d", "e", "", "i", "f", "g", "mouseDragged", "(DDIDD)Z", "pMouseX", "pMouseY", "", "onClick", "(DD)V", "onRelease", "Lnet/minecraft/client/sounds/SoundManager;", "soundManager", "playDownSound", "(Lnet/minecraft/client/sounds/SoundManager;)V", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "pPartialTicks", "renderButton", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "isUp", "Z", "pX", "I", "getPX", "()I", "pY", "getPY", "Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;", "onPress", "<init>", "(IIZLnet/minecraft/client/gui/components/Button$OnPress;)V", "Companion", "common"})
public final class ReorderMoveButton
extends Button {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final int pX;
    private final int pY;
    private final boolean isUp;
    private static final int WIDTH = 8;
    private static final int HEIGHT = 6;
    private static final float OFFSET_X = 11.5f;
    private static final int OFFSET_Y_UP = 6;
    private static final int OFFSET_Y_DOWN = 13;
    private static final float SCALE = 0.5f;
    @NotNull
    private static final ResourceLocation moveReorderUpResource = MiscUtilsKt.cobblemonResource("textures/gui/summary/summary_move_reorder_up.png");
    @NotNull
    private static final ResourceLocation moveReorderDownResource = MiscUtilsKt.cobblemonResource("textures/gui/summary/summary_move_reorder_down.png");
    private static boolean blocked;

    public ReorderMoveButton(int pX, int pY, boolean isUp, @NotNull Button.OnPress onPress) {
        Intrinsics.checkNotNullParameter((Object)onPress, (String)"onPress");
        super((int)((float)pX - 11.5f), pY + (isUp ? 6 : 13), 4, 3, (Component)Component.m_237119_(), onPress, Button.f_252438_);
        this.pX = pX;
        this.pY = pY;
        this.isUp = isUp;
    }

    public final int getPX() {
        return this.pX;
    }

    public final int getPY() {
        return this.pY;
    }

    public boolean m_7979_(double d, double e, int i, double f, double g) {
        return false;
    }

    protected void m_87963_(@NotNull GuiGraphics context, int pMouseX, int pMouseY, float pPartialTicks) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        int offsetY = this.isUp ? 6 : 13;
        PoseStack poseStack = context.m_280168_();
        float f = ((float)this.pX - 11.5f) / 0.5f;
        float f2 = (float)(this.pY + offsetY) / 0.5f;
        ResourceLocation resourceLocation = this.isUp ? moveReorderUpResource : moveReorderDownResource;
        int n = this.isHovered(pMouseX, pMouseY, offsetY) ? 6 : 0;
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"matrices");
        GuiUtilsKt.blitk$default(poseStack, resourceLocation, Float.valueOf(f), Float.valueOf(f2), 6, 8, null, n, null, 12, null, null, null, null, null, false, 0.5f, 64832, null);
    }

    public void m_7691_(double pMouseX, double pMouseY) {
        blocked = false;
    }

    public void m_5716_(double pMouseX, double pMouseY) {
        if (!blocked) {
            blocked = true;
            this.f_93717_.m_93750_((Button)this);
        }
    }

    public void m_7435_(@NotNull SoundManager soundManager) {
        Intrinsics.checkNotNullParameter((Object)soundManager, (String)"soundManager");
        soundManager.m_120367_((SoundInstance)SimpleSoundInstance.m_119752_((SoundEvent)CobblemonSounds.GUI_CLICK, (float)1.0f));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean isHovered(double mouseX, double mouseY, float offsetY) {
        float f = (float)this.pX - 11.5f;
        float f2 = (float)this.pX - 11.5f + 4.0f;
        float f3 = (float)mouseX;
        if (!(f <= f3)) return false;
        if (!(f3 <= f2)) return false;
        boolean bl = true;
        if (!bl) return false;
        f = (float)this.pY + offsetY;
        f2 = (float)this.pY + offsetY + 3.0f - 0.5f;
        f3 = (float)mouseY;
        if (!(f <= f3)) return false;
        if (!(f3 <= f2)) return false;
        return true;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00058\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\b\u0010\u0004R\u0014\u0010\t\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\t\u0010\u0004R\u0014\u0010\n\u001a\u00020\u00058\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\n\u0010\u0007R\u0014\u0010\u000b\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\u0004R\u0016\u0010\r\u001a\u00020\f8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\r\u0010\u000eR\u0014\u0010\u0010\u001a\u00020\u000f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u000f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\u0011\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/ReorderMoveButton$Companion;", "", "", "HEIGHT", "I", "", "OFFSET_X", "F", "OFFSET_Y_DOWN", "OFFSET_Y_UP", "SCALE", "WIDTH", "", "blocked", "Z", "Lnet/minecraft/resources/ResourceLocation;", "moveReorderDownResource", "Lnet/minecraft/resources/ResourceLocation;", "moveReorderUpResource", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

