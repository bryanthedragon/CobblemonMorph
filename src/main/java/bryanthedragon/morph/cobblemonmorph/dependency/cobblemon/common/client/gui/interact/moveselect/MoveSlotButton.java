/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.Triple
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.moveselect;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.MoveCategoryIcon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.TypeIcon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.SimpleMathExtensionsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 &2\u00020\u0001:\u0001&BA\u0012\u0006\u0010 \u001a\u00020\t\u0012\u0006\u0010!\u001a\u00020\t\u0012\u0006\u0010\u0016\u001a\u00020\u0015\u0012\u0006\u0010\u001a\u001a\u00020\t\u0012\u0006\u0010\u001e\u001a\u00020\t\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0010\u0012\u0006\u0010#\u001a\u00020\"\u00a2\u0006\u0004\b$\u0010%J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J/\u0010\u000e\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0011\u001a\u00020\u00108\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R\u0017\u0010\u0016\u001a\u00020\u00158\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\u001a\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001dR\u0017\u0010\u001e\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\u001e\u0010\u001b\u001a\u0004\b\u001f\u0010\u001d\u00a8\u0006'"}, d2={"Lcom/cobblemon/mod/common/client/gui/interact/moveselect/MoveSlotButton;", "Lnet/minecraft/client/gui/components/Button;", "Lnet/minecraft/client/sounds/SoundManager;", "soundManager", "", "playDownSound", "(Lnet/minecraft/client/sounds/SoundManager;)V", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "pMouseX", "pMouseY", "", "pPartialTicks", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "", "enabled", "Z", "getEnabled", "()Z", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "move", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "getMove", "()Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "pp", "I", "getPp", "()I", "ppMax", "getPpMax", "x", "y", "Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;", "onPress", "<init>", "(IILcom/cobblemon/mod/common/api/moves/MoveTemplate;IIZLnet/minecraft/client/gui/components/Button$OnPress;)V", "Companion", "common"})
public final class MoveSlotButton
extends Button {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final MoveTemplate move;
    private final int pp;
    private final int ppMax;
    private final boolean enabled;
    @NotNull
    private static final ResourceLocation moveResource = MiscUtilsKt.cobblemonResource("textures/gui/summary/summary_move.png");
    @NotNull
    private static final ResourceLocation moveOverlayResource = MiscUtilsKt.cobblemonResource("textures/gui/summary/summary_move_overlay.png");
    public static final int WIDTH = 108;
    public static final int HEIGHT = 22;

    public MoveSlotButton(int x, int y, @NotNull MoveTemplate move, int pp, int ppMax, boolean enabled, @NotNull Button.OnPress onPress) {
        Intrinsics.checkNotNullParameter((Object)move, (String)"move");
        Intrinsics.checkNotNullParameter((Object)onPress, (String)"onPress");
        super(x, y, 108, 22, (Component)Component.m_237113_((String)"Move"), onPress, MoveSlotButton::_init_$lambda$0);
        this.move = move;
        this.pp = pp;
        this.ppMax = ppMax;
        this.enabled = enabled;
    }

    public /* synthetic */ MoveSlotButton(int n, int n2, MoveTemplate moveTemplate, int n3, int n4, boolean bl, Button.OnPress onPress, int n5, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n5 & 0x20) != 0) {
            bl = true;
        }
        this(n, n2, moveTemplate, n3, n4, bl, onPress);
    }

    @NotNull
    public final MoveTemplate getMove() {
        return this.move;
    }

    public final int getPp() {
        return this.pp;
    }

    public final int getPpMax() {
        return this.ppMax;
    }

    public final boolean getEnabled() {
        return this.enabled;
    }

    public void m_88315_(@NotNull GuiGraphics context, int pMouseX, int pMouseY, float pPartialTicks) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        this.f_93622_ = pMouseX >= this.m_252754_() && pMouseY >= this.m_252907_() && pMouseX < this.m_252754_() + this.f_93618_ && pMouseY < this.m_252907_() + this.f_93619_ && this.enabled;
        MoveTemplate moveTemplate = Moves.INSTANCE.getByNameOrDummy(this.move.getName());
        Triple<Double, Double, Double> rgb = SimpleMathExtensionsKt.toRGB(moveTemplate.getElementalType().getHue());
        double alpha = this.enabled ? 1.0 : 0.5;
        PoseStack matrices = context.m_280168_();
        ResourceLocation resourceLocation = moveResource;
        int n = this.m_252754_();
        int n2 = this.m_252907_();
        int n3 = this.m_274382_() ? 22 : 0;
        double d = ((Number)rgb.getFirst()).doubleValue();
        double d2 = ((Number)rgb.getSecond()).doubleValue();
        double d3 = ((Number)rgb.getThird()).doubleValue();
        Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
        GuiUtilsKt.blitk$default(matrices, resourceLocation, n, n2, 22, 108, null, n3, null, 44, null, d, d2, d3, alpha, false, 0.0f, 99648, null);
        resourceLocation = moveOverlayResource;
        n = this.m_252754_();
        n2 = this.m_252907_();
        GuiUtilsKt.blitk$default(matrices, resourceLocation, n, n2, 22, 108, null, null, null, null, null, null, null, null, alpha, false, 0.0f, 114624, null);
        if (this.pp != -1 && this.ppMax != -1) {
            MutableComponent mutableComponent = Component.m_237113_((String)(this.pp + "/" + this.ppMax));
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"literal(\"$pp/$ppMax\")");
            MutableComponent movePPText = TextKt.bold(mutableComponent);
            if (this.pp <= Mth.m_14143_((float)((float)this.ppMax / 2.0f))) {
                movePPText = this.pp == 0 ? TextKt.red(movePPText) : TextKt.gold(movePPText);
            }
            RenderHelperKt.drawScaledText$default(context, CobblemonResources.INSTANCE.getDEFAULT_LARGE(), movePPText, this.m_252754_() + 93, this.m_252907_() + 13, 0.0f, null, 0, 0, true, false, null, null, 7648, null);
        }
        new TypeIcon(this.m_252754_() + 2, this.m_252907_() + 2, moveTemplate.getElementalType(), null, false, false, 0.0f, 0.0f, 0.0f, 504, null).render(context);
        new MoveCategoryIcon(this.m_252754_() + 66, (double)this.m_252907_() + 13.5, this.move.getDamageCategory(), 0.0f, 8, null).render(context);
        RenderHelperKt.drawScaledText$default(context, CobblemonResources.INSTANCE.getDEFAULT_LARGE(), TextKt.bold(this.move.getDisplayName()), this.m_252754_() + 28, this.m_252907_() + 2, 0.0f, null, 0, 0, false, true, null, null, 7136, null);
    }

    public void m_7435_(@NotNull SoundManager soundManager) {
        Intrinsics.checkNotNullParameter((Object)soundManager, (String)"soundManager");
    }

    private static final MutableComponent _init_$lambda$0(Supplier it) {
        return TextKt.text("");
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\n\u0010\u000bR\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0007\u001a\u00020\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\t\u0010\b\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/client/gui/interact/moveselect/MoveSlotButton$Companion;", "", "", "HEIGHT", "I", "WIDTH", "Lnet/minecraft/resources/ResourceLocation;", "moveOverlayResource", "Lnet/minecraft/resources/ResourceLocation;", "moveResource", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

