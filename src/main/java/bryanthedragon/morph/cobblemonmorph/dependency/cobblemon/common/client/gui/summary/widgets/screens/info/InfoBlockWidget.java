/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.screens.info;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.SoundlessWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B?\u0012\u0006\u0010\u0014\u001a\u00020\u0004\u0012\u0006\u0010\u0015\u001a\u00020\u0004\u0012\u0006\u0010\u0016\u001a\u00020\u0004\u0012\u0006\u0010\u0017\u001a\u00020\u0004\u0012\u0006\u0010\u0010\u001a\u00020\u000f\u0012\u0006\u0010\u0012\u001a\u00020\u0004\u0012\u0006\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\u0018\u0010\u0019J/\u0010\n\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007H\u0014\u00a2\u0006\u0004\b\n\u0010\u000bR\u0014\u0010\r\u001a\u00020\f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\r\u0010\u000eR\u0014\u0010\u0010\u001a\u00020\u000f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00048\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\u0013\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/info/InfoBlockWidget;", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/SoundlessWidget;", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "pMouseX", "pMouseY", "", "pPartialTicks", "", "renderButton", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "Lnet/minecraft/resources/ResourceLocation;", "font", "Lnet/minecraft/resources/ResourceLocation;", "Lnet/minecraft/network/chat/MutableComponent;", "text", "Lnet/minecraft/network/chat/MutableComponent;", "withinRowVerticalTextOffset", "I", "pX", "pY", "blockWidth", "blockHeight", "<init>", "(IIIILnet/minecraft/network/chat/MutableComponent;ILnet/minecraft/resources/ResourceLocation;)V", "common"})
public final class InfoBlockWidget
extends SoundlessWidget {
    @NotNull
    private final MutableComponent text;
    private final int withinRowVerticalTextOffset;
    @NotNull
    private final ResourceLocation font;

    public InfoBlockWidget(int pX, int pY, int blockWidth, int blockHeight, @NotNull MutableComponent text, int withinRowVerticalTextOffset, @NotNull ResourceLocation font) {
        Intrinsics.checkNotNullParameter((Object)text, (String)"text");
        Intrinsics.checkNotNullParameter((Object)font, (String)"font");
        MutableComponent mutableComponent = Component.m_237113_((String)"InfoBlockWidget");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"literal(\"InfoBlockWidget\")");
        super(pX, pY, blockWidth, blockHeight, (Component)mutableComponent);
        this.text = text;
        this.withinRowVerticalTextOffset = withinRowVerticalTextOffset;
        this.font = font;
    }

    protected void m_87963_(@NotNull GuiGraphics context, int pMouseX, int pMouseY, float pPartialTicks) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        RenderHelperKt.drawScaledText$default(context, this.font, TextKt.bold(this.text), this.m_252754_(), this.m_252907_() + this.withinRowVerticalTextOffset, 0.0f, null, 0, 0, false, true, pMouseX, pMouseY, 992, null);
    }
}

