/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.type;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.type.TypeWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B9\u0012\u0006\u0010\u0012\u001a\u00020\u0004\u0012\u0006\u0010\u0013\u001a\u00020\u0004\u0012\u0006\u0010\u0014\u001a\u00020\u0004\u0012\u0006\u0010\u0015\u001a\u00020\u0004\u0012\u0006\u0010\u0010\u001a\u00020\u000f\u0012\b\b\u0002\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\u0016\u0010\u0017J/\u0010\n\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007H\u0014\u00a2\u0006\u0004\b\n\u0010\u000bR\u0014\u0010\r\u001a\u00020\f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\r\u0010\u000eR\u0014\u0010\u0010\u001a\u00020\u000f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/type/SingleTypeWidget;", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/type/TypeWidget;", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "pMouseX", "pMouseY", "", "pPartialTicks", "", "renderButton", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "", "renderText", "Z", "Lcom/cobblemon/mod/common/api/types/ElementalType;", "type", "Lcom/cobblemon/mod/common/api/types/ElementalType;", "pX", "pY", "pWidth", "pHeight", "<init>", "(IIIILcom/cobblemon/mod/common/api/types/ElementalType;Z)V", "common"})
public final class SingleTypeWidget
extends TypeWidget {
    @NotNull
    private final ElementalType type;
    private final boolean renderText;

    public SingleTypeWidget(int pX, int pY, int pWidth, int pHeight, @NotNull ElementalType type, boolean renderText) {
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        MutableComponent mutableComponent = Component.m_237113_((String)("SingleTypeWidget - " + type.getName()));
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"literal(\"SingleTypeWidget - ${type.name}\")");
        super(pX, pY, pWidth, pHeight, (Component)mutableComponent);
        this.type = type;
        this.renderText = renderText;
    }

    public /* synthetic */ SingleTypeWidget(int n, int n2, int n3, int n4, ElementalType elementalType, boolean bl, int n5, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n5 & 0x20) != 0) {
            bl = true;
        }
        this(n, n2, n3, n4, elementalType, bl);
    }

    protected void m_87963_(@NotNull GuiGraphics context, int pMouseX, int pMouseY, float pPartialTicks) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        PoseStack matrices = context.m_280168_();
        matrices.m_85836_();
        matrices.m_85837_(0.35, 0.0, 0.0);
        TypeWidget typeWidget = this;
        Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
        TypeWidget.renderType$default(typeWidget, this.type, matrices, 0, 0, 12, null);
        matrices.m_85849_();
        if (this.renderText) {
            matrices.m_85836_();
            MutableComponent mutableComponent = this.type.getDisplayName();
            float f = (float)this.m_252754_() + 35.5f;
            float f2 = (float)this.m_252907_() + 3.0f;
            RenderHelperKt.drawScaledText$default(context, null, mutableComponent, Float.valueOf(f), Float.valueOf(f2), 0.6f, null, 40, 0xFFFFFF, true, false, null, null, 6210, null);
        }
    }
}

