/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.network.chat.Component
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.type;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.type.TypeWidget;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B?\u0012\u0006\u0010\u0010\u001a\u00020\u0004\u0012\u0006\u0010\u0011\u001a\u00020\u0004\u0012\u0006\u0010\u0012\u001a\u00020\u0004\u0012\u0006\u0010\u0013\u001a\u00020\u0004\u0012\u0006\u0010\u0015\u001a\u00020\u0014\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u000f\u001a\u00020\f\u00a2\u0006\u0004\b\u0016\u0010\u0017J/\u0010\n\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007H\u0014\u00a2\u0006\u0004\b\n\u0010\u000bR\u0014\u0010\r\u001a\u00020\f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\u000e\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/type/DualTypeWidget;", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/type/TypeWidget;", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "pMouseX", "pMouseY", "", "pPartialTicks", "", "renderButton", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "Lcom/cobblemon/mod/common/api/types/ElementalType;", "mainType", "Lcom/cobblemon/mod/common/api/types/ElementalType;", "secondaryType", "pX", "pY", "pWidth", "pHeight", "Lnet/minecraft/network/chat/Component;", "pMessage", "<init>", "(IIIILnet/minecraft/network/chat/Component;Lcom/cobblemon/mod/common/api/types/ElementalType;Lcom/cobblemon/mod/common/api/types/ElementalType;)V", "common"})
public final class DualTypeWidget
extends TypeWidget {
    @NotNull
    private final ElementalType mainType;
    @NotNull
    private final ElementalType secondaryType;

    public DualTypeWidget(int pX, int pY, int pWidth, int pHeight, @NotNull Component pMessage, @NotNull ElementalType mainType, @NotNull ElementalType secondaryType) {
        Intrinsics.checkNotNullParameter((Object)pMessage, (String)"pMessage");
        Intrinsics.checkNotNullParameter((Object)mainType, (String)"mainType");
        Intrinsics.checkNotNullParameter((Object)secondaryType, (String)"secondaryType");
        super(pX, pY, pWidth, pHeight, pMessage);
        this.mainType = mainType;
        this.secondaryType = secondaryType;
    }

    protected void m_87963_(@NotNull GuiGraphics context, int pMouseX, int pMouseY, float pPartialTicks) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        PoseStack poseStack = context.m_280168_();
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"context.matrices");
        this.renderType(this.mainType, this.secondaryType, poseStack);
    }
}

