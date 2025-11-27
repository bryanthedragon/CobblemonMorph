/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.type;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.SoundlessWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B/\u0012\u0006\u0010\f\u001a\u00020\u000b\u0012\u0006\u0010\r\u001a\u00020\u000b\u0012\u0006\u0010\u000f\u001a\u00020\u000b\u0012\u0006\u0010\u0010\u001a\u00020\u000b\u0012\u0006\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0004\b\u0013\u0010\u0014J%\u0010\b\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0004\b\b\u0010\tJ1\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\r\u001a\u00020\u000b\u00a2\u0006\u0004\b\b\u0010\u000e\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/type/TypeWidget;", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/SoundlessWidget;", "Lcom/cobblemon/mod/common/api/types/ElementalType;", "mainType", "secondaryType", "Lcom/mojang/blaze3d/vertex/PoseStack;", "pMatrixStack", "", "renderType", "(Lcom/cobblemon/mod/common/api/types/ElementalType;Lcom/cobblemon/mod/common/api/types/ElementalType;Lcom/mojang/blaze3d/vertex/PoseStack;)V", "type", "", "pX", "pY", "(Lcom/cobblemon/mod/common/api/types/ElementalType;Lcom/mojang/blaze3d/vertex/PoseStack;II)V", "pWidth", "pHeight", "Lnet/minecraft/network/chat/Component;", "pMessage", "<init>", "(IIIILnet/minecraft/network/chat/Component;)V", "Companion", "common"})
public abstract class TypeWidget
extends SoundlessWidget {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private static final ResourceLocation typeResource = MiscUtils.cobblemonResource("textures/gui/types.png");
    private static final double OFFSET = 0.5;

    public TypeWidget(int pX, int pY, int pWidth, int pHeight, @NotNull Component pMessage) {
        Intrinsics.checkNotNullParameter((Object)pMessage, (String)"pMessage");
        super(pX, pY, pWidth, pHeight, pMessage);
    }

    public final void renderType(@NotNull ElementalType type, @NotNull PoseStack pMatrixStack, int pX, int pY) {
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)pMatrixStack, (String)"pMatrixStack");
        ResourceLocation resourceLocation = typeResource;
        double d = (double)pX + 0.5;
        int n = this.f_93618_;
        int n2 = this.f_93619_;
        double d2 = (double)((float)this.f_93618_ * (float)type.getTextureXMultiplier()) + 0.1;
        int n3 = this.f_93618_ * 18;
        GuiUtilsKt.blitk$default(pMatrixStack, resourceLocation, d, pY, n2, n, d2, null, n3, null, null, null, null, null, null, false, 0.0f, 130688, null);
    }

    public static /* synthetic */ void renderType$default(TypeWidget typeWidget, ElementalType elementalType, PoseStack poseStack, int n, int n2, int n3, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: renderType");
        }
        if ((n3 & 4) != 0) {
            n = typeWidget.m_252754_();
        }
        if ((n3 & 8) != 0) {
            n2 = typeWidget.m_252907_();
        }
        typeWidget.renderType(elementalType, poseStack, n, n2);
    }

    public final void renderType(@NotNull ElementalType mainType, @NotNull ElementalType secondaryType, @NotNull PoseStack pMatrixStack) {
        Intrinsics.checkNotNullParameter((Object)mainType, (String)"mainType");
        Intrinsics.checkNotNullParameter((Object)secondaryType, (String)"secondaryType");
        Intrinsics.checkNotNullParameter((Object)pMatrixStack, (String)"pMatrixStack");
        TypeWidget.renderType$default(this, secondaryType, pMatrixStack, this.m_252754_() + 16, 0, 8, null);
        TypeWidget.renderType$default(this, mainType, pMatrixStack, 0, 0, 12, null);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\n\u0010\u000bR\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0017\u0010\u0006\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\t\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/type/TypeWidget$Companion;", "", "", "OFFSET", "D", "Lnet/minecraft/resources/ResourceLocation;", "typeResource", "Lnet/minecraft/resources/ResourceLocation;", "getTypeResource", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getTypeResource() {
            return typeResource;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

