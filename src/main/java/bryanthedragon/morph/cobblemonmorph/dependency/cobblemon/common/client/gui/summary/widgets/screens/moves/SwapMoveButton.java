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
 *  net.minecraft.client.gui.components.events.GuiEventListener
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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.screens.moves.MoveSwapScreen;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.screens.moves.MovesWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 52\u00020\u0001:\u00015B/\u0012\u0006\u0010+\u001a\u00020\n\u0012\u0006\u0010/\u001a\u00020\n\u0012\u0006\u0010\u001e\u001a\u00020\u001d\u0012\u0006\u0010%\u001a\u00020$\u0012\u0006\u00102\u001a\u000201\u00a2\u0006\u0004\b3\u00104J\u001d\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J7\u0010\u000e\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0017\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0011\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014J/\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\u0019H\u0014\u00a2\u0006\u0004\b\u001b\u0010\u001cR\"\u0010\u001e\u001a\u00020\u001d8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\"\u0010%\u001a\u00020$8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b%\u0010&\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u0017\u0010+\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b+\u0010,\u001a\u0004\b-\u0010.R\u0017\u0010/\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b/\u0010,\u001a\u0004\b0\u0010.\u00a8\u00066"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/SwapMoveButton;", "Lnet/minecraft/client/gui/components/Button;", "", "mouseX", "mouseY", "", "isHovered", "(DD)Z", "d", "e", "", "i", "f", "g", "mouseDragged", "(DDIDD)Z", "Lnet/minecraft/client/sounds/SoundManager;", "soundManager", "", "playDownSound", "(Lnet/minecraft/client/sounds/SoundManager;)V", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "pMouseX", "pMouseY", "", "pPartialTicks", "renderButton", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "move", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "getMove", "()Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "setMove", "(Lcom/cobblemon/mod/common/api/moves/MoveTemplate;)V", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/MovesWidget;", "movesWidget", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/MovesWidget;", "getMovesWidget", "()Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/MovesWidget;", "setMovesWidget", "(Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/MovesWidget;)V", "pX", "I", "getPX", "()I", "pY", "getPY", "Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;", "onPress", "<init>", "(IILcom/cobblemon/mod/common/api/moves/MoveTemplate;Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/MovesWidget;Lnet/minecraft/client/gui/components/Button$OnPress;)V", "Companion", "common"})
public final class SwapMoveButton
extends Button {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final int pX;
    private final int pY;
    @NotNull
    private MoveTemplate move;
    @NotNull
    private MovesWidget movesWidget;
    private static final int WIDTH = 12;
    private static final int HEIGHT = 18;
    private static final float OFFSET_X = 114.5f;
    private static final float OFFSET_Y = 6.5f;
    private static final float SCALE = 0.5f;
    @NotNull
    private static final ResourceLocation switchMoveButtonResource = MiscUtilsKt.cobblemonResource("textures/gui/summary/summary_move_swap.png");

    public SwapMoveButton(int pX, int pY, @NotNull MoveTemplate move, @NotNull MovesWidget movesWidget, @NotNull Button.OnPress onPress) {
        Intrinsics.checkNotNullParameter((Object)move, (String)"move");
        Intrinsics.checkNotNullParameter((Object)((Object)movesWidget), (String)"movesWidget");
        Intrinsics.checkNotNullParameter((Object)onPress, (String)"onPress");
        super((int)((float)pX + 114.5f), (int)((float)pY + 6.5f), 6, 9, (Component)Component.m_237119_(), onPress, Button.f_252438_);
        this.pX = pX;
        this.pY = pY;
        this.move = move;
        this.movesWidget = movesWidget;
    }

    public final int getPX() {
        return this.pX;
    }

    public final int getPY() {
        return this.pY;
    }

    @NotNull
    public final MoveTemplate getMove() {
        return this.move;
    }

    public final void setMove(@NotNull MoveTemplate moveTemplate) {
        Intrinsics.checkNotNullParameter((Object)moveTemplate, (String)"<set-?>");
        this.move = moveTemplate;
    }

    @NotNull
    public final MovesWidget getMovesWidget() {
        return this.movesWidget;
    }

    public final void setMovesWidget(@NotNull MovesWidget movesWidget) {
        Intrinsics.checkNotNullParameter((Object)((Object)movesWidget), (String)"<set-?>");
        this.movesWidget = movesWidget;
    }

    public boolean m_7979_(double d, double e, int i, double f, double g) {
        return false;
    }

    protected void m_87963_(@NotNull GuiGraphics context, int pMouseX, int pMouseY, float pPartialTicks) {
        boolean bl;
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        GuiEventListener swapScreen = this.movesWidget.getSummary().getSideScreen();
        if (swapScreen instanceof MoveSwapScreen) {
            Move move = ((MoveSwapScreen)swapScreen).getReplacedMove();
            bl = Intrinsics.areEqual((Object)(move != null ? move.getTemplate() : null), (Object)this.move);
        } else {
            bl = false;
        }
        boolean selected = bl;
        PoseStack poseStack = context.m_280168_();
        ResourceLocation resourceLocation = switchMoveButtonResource;
        float f = ((float)this.pX + 114.5f) / 0.5f;
        float f2 = ((float)this.pY + 6.5f) / 0.5f;
        int n = this.isHovered(pMouseX, pMouseY) || selected ? 18 : 0;
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"matrices");
        GuiUtilsKt.blitk$default(poseStack, resourceLocation, Float.valueOf(f), Float.valueOf(f2), 18, 12, null, n, null, 36, null, null, null, null, null, false, 0.5f, 64832, null);
    }

    public void m_7435_(@NotNull SoundManager soundManager) {
        Intrinsics.checkNotNullParameter((Object)soundManager, (String)"soundManager");
        soundManager.m_120367_((SoundInstance)SimpleSoundInstance.m_119752_((SoundEvent)CobblemonSounds.GUI_CLICK, (float)1.0f));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean isHovered(double mouseX, double mouseY) {
        float f = (float)this.pX + 114.5f;
        float f2 = (float)this.pX + 114.5f + 6.0f;
        float f3 = (float)mouseX;
        if (!(f <= f3)) return false;
        if (!(f3 <= f2)) return false;
        boolean bl = true;
        if (!bl) return false;
        f = (float)this.pY + 6.5f;
        f2 = (float)this.pY + 6.5f + 9.0f - 0.5f;
        f3 = (float)mouseY;
        if (!(f <= f3)) return false;
        if (!(f3 <= f2)) return false;
        return true;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00058\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u00058\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\b\u0010\u0007R\u0014\u0010\t\u001a\u00020\u00058\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\t\u0010\u0007R\u0014\u0010\n\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\n\u0010\u0004R\u0014\u0010\f\u001a\u00020\u000b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\f\u0010\r\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/moves/SwapMoveButton$Companion;", "", "", "HEIGHT", "I", "", "OFFSET_X", "F", "OFFSET_Y", "SCALE", "WIDTH", "Lnet/minecraft/resources/ResourceLocation;", "switchMoveButtonResource", "Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

