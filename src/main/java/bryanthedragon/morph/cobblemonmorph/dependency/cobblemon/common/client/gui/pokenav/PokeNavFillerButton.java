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
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pokenav;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pokenav.PokeNavImageButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB_\u0012\u0006\u0010\u0010\u001a\u00020\u0004\u0012\u0006\u0010\u0011\u001a\u00020\u0004\u0012\u0006\u0010\u0012\u001a\u00020\u0004\u0012\u0006\u0010\u0013\u001a\u00020\u0004\u0012\u0006\u0010\u0014\u001a\u00020\u0004\u0012\u0006\u0010\u0015\u001a\u00020\u0004\u0012\u0006\u0010\u0016\u001a\u00020\u0004\u0012\u0006\u0010\u0017\u001a\u00020\u0004\u0012\u0006\u0010\u0018\u001a\u00020\u0004\u0012\u0006\u0010\u0019\u001a\u00020\u0004\u0012\u0006\u0010\u001a\u001a\u00020\u0004\u00a2\u0006\u0004\b\u001b\u0010\u001cJ/\u0010\n\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007H\u0014\u00a2\u0006\u0004\b\n\u0010\u000bJ/\u0010\u000e\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000f\u00a8\u0006\u001e"}, d2={"Lcom/cobblemon/mod/common/client/gui/pokenav/PokeNavFillerButton;", "Lcom/cobblemon/mod/common/client/gui/pokenav/PokeNavImageButton;", "Lcom/mojang/blaze3d/vertex/PoseStack;", "matrices", "", "pMouseX", "pMouseY", "", "pPartialTicks", "", "applyBlitk", "(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "renderButton", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "posX", "posY", "pX", "pY", "pWidth", "pHeight", "pXTexStart", "pYTexStart", "pYDiffText", "pTextureWidth", "pTextureHeight", "<init>", "(IIIIIIIIIII)V", "Companion", "common"})
public final class PokeNavFillerButton
extends PokeNavImageButton {
    @NotNull
    public static final Companion Companion = new Companion(null);
    public static final double RED = 0.28235;
    public static final double GREEN = 0.29412;
    public static final double BLUE = 0.3098;
    public static final double ALPHA = 0.9;
    @NotNull
    private static final ResourceLocation FILLER = MiscUtils.cobblemonResource("textures/gui/pokenav/pokenav_filler.png");

    public PokeNavFillerButton(int posX, int posY, int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, int pYDiffText, int pTextureWidth, int pTextureHeight) {
        super(posX, posY, pX, pY, pWidth, pHeight, pXTexStart, pYTexStart, pYDiffText, FILLER, pTextureWidth, pTextureHeight, PokeNavFillerButton::_init_$lambda$0, TextKt.text(""), null, 16384, null);
    }

    @Override
    public void m_87963_(@NotNull GuiGraphics context, int pMouseX, int pMouseY, float pPartialTicks) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        PoseStack poseStack = context.m_280168_();
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"context.matrices");
        this.applyBlitk(poseStack, pMouseX, pMouseY, pPartialTicks);
        context.m_280168_().m_85836_();
    }

    @Override
    protected void applyBlitk(@NotNull PoseStack matrices, int pMouseX, int pMouseY, float pPartialTicks) {
        Intrinsics.checkNotNullParameter((Object)matrices, (String)"matrices");
        ResourceLocation resourceLocation = FILLER;
        int n = this.m_252754_();
        double d = (double)this.m_252907_() + 0.25;
        int n2 = this.f_93618_;
        int n3 = this.f_93619_;
        GuiUtilsKt.blitk$default(matrices, resourceLocation, n, d, n3, n2, null, null, null, null, null, 0.28235, 0.29412, 0.3098, 0.9, false, 0.0f, 100288, null);
    }

    private static final void _init_$lambda$0(Button it) {
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fR\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0007\u001a\u00020\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\t\u0010\u0004R\u0014\u0010\n\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\n\u0010\u0004\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/client/gui/pokenav/PokeNavFillerButton$Companion;", "", "", "ALPHA", "D", "BLUE", "Lnet/minecraft/resources/ResourceLocation;", "FILLER", "Lnet/minecraft/resources/ResourceLocation;", "GREEN", "RED", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

