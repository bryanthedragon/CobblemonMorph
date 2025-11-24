/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0004\n\u0002\b\n\u0018\u0000 '2\u00020\u0001:\u0001'B]\u0012\u0006\u0010\u001f\u001a\u00020\u001e\u0012\u0006\u0010#\u001a\u00020\u001e\u0012\u0006\u0010\u001c\u001a\u00020\u0015\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0015\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0013\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\f\u0012\b\b\u0002\u0010\u0011\u001a\u00020\f\u00a2\u0006\u0004\b%\u0010&J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0011\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u000e\u001a\u0004\b\u0012\u0010\u0010R\u0017\u0010\u0013\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u000e\u001a\u0004\b\u0014\u0010\u0010R\u0019\u0010\u0016\u001a\u0004\u0018\u00010\u00158\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\u001a\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\u001a\u0010\t\u001a\u0004\b\u001b\u0010\u000bR\u0017\u0010\u001c\u001a\u00020\u00158\u0006\u00a2\u0006\f\n\u0004\b\u001c\u0010\u0017\u001a\u0004\b\u001d\u0010\u0019R\u0017\u0010\u001f\u001a\u00020\u001e8\u0006\u00a2\u0006\f\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\"R\u0017\u0010#\u001a\u00020\u001e8\u0006\u00a2\u0006\f\n\u0004\b#\u0010 \u001a\u0004\b$\u0010\"\u00a8\u0006("}, d2={"Lcom/cobblemon/mod/common/client/gui/TypeIcon;", "", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "render", "(Lnet/minecraft/client/gui/GuiGraphics;)V", "", "centeredX", "Z", "getCenteredX", "()Z", "", "doubleCenteredOffset", "F", "getDoubleCenteredOffset", "()F", "opacity", "getOpacity", "secondaryOffset", "getSecondaryOffset", "Lcom/cobblemon/mod/common/api/types/ElementalType;", "secondaryType", "Lcom/cobblemon/mod/common/api/types/ElementalType;", "getSecondaryType", "()Lcom/cobblemon/mod/common/api/types/ElementalType;", "small", "getSmall", "type", "getType", "", "x", "Ljava/lang/Number;", "getX", "()Ljava/lang/Number;", "y", "getY", "<init>", "(Ljava/lang/Number;Ljava/lang/Number;Lcom/cobblemon/mod/common/api/types/ElementalType;Lcom/cobblemon/mod/common/api/types/ElementalType;ZZFFF)V", "Companion", "common"})
public final class TypeIcon {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Number x;
    @NotNull
    private final Number y;
    @NotNull
    private final ElementalType type;
    @Nullable
    private final ElementalType secondaryType;
    private final boolean centeredX;
    private final boolean small;
    private final float secondaryOffset;
    private final float doubleCenteredOffset;
    private final float opacity;
    private static final int TYPE_ICON_DIAMETER = 36;
    private static final float SCALE = 0.5f;
    @NotNull
    private static final ResourceLocation typesResource = MiscUtilsKt.cobblemonResource("textures/gui/types.png");
    @NotNull
    private static final ResourceLocation smallTypesResource = MiscUtilsKt.cobblemonResource("textures/gui/types_small.png");

    public TypeIcon(@NotNull Number x, @NotNull Number y, @NotNull ElementalType type, @Nullable ElementalType secondaryType, boolean centeredX, boolean small, float secondaryOffset, float doubleCenteredOffset, float opacity) {
        Intrinsics.checkNotNullParameter((Object)x, (String)"x");
        Intrinsics.checkNotNullParameter((Object)y, (String)"y");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        this.x = x;
        this.y = y;
        this.type = type;
        this.secondaryType = secondaryType;
        this.centeredX = centeredX;
        this.small = small;
        this.secondaryOffset = secondaryOffset;
        this.doubleCenteredOffset = doubleCenteredOffset;
        this.opacity = opacity;
    }

    public /* synthetic */ TypeIcon(Number number, Number number2, ElementalType elementalType, ElementalType elementalType2, boolean bl, boolean bl2, float f, float f2, float f3, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 8) != 0) {
            elementalType2 = null;
        }
        if ((n & 0x10) != 0) {
            bl = false;
        }
        if ((n & 0x20) != 0) {
            bl2 = false;
        }
        if ((n & 0x40) != 0) {
            f = 15.0f;
        }
        if ((n & 0x80) != 0) {
            f2 = 7.5f;
        }
        if ((n & 0x100) != 0) {
            f3 = 1.0f;
        }
        this(number, number2, elementalType, elementalType2, bl, bl2, f, f2, f3);
    }

    @NotNull
    public final Number getX() {
        return this.x;
    }

    @NotNull
    public final Number getY() {
        return this.y;
    }

    @NotNull
    public final ElementalType getType() {
        return this.type;
    }

    @Nullable
    public final ElementalType getSecondaryType() {
        return this.secondaryType;
    }

    public final boolean getCenteredX() {
        return this.centeredX;
    }

    public final boolean getSmall() {
        return this.small;
    }

    public final float getSecondaryOffset() {
        return this.secondaryOffset;
    }

    public final float getDoubleCenteredOffset() {
        return this.doubleCenteredOffset;
    }

    public final float getOpacity() {
        return this.opacity;
    }

    public final void render(@NotNull GuiGraphics context) {
        float offsetX;
        int diameter;
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        int n = diameter = this.small ? 18 : 36;
        float f = this.centeredX ? (float)(diameter / 2) * 0.5f + (this.secondaryType != null ? this.doubleCenteredOffset : 0.0f) : (offsetX = 0.0f);
        if (this.secondaryType != null) {
            PoseStack poseStack = context.m_280168_();
            Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"context.matrices");
            ResourceLocation resourceLocation = this.small ? smallTypesResource : typesResource;
            Number number = Float.valueOf((this.x.floatValue() + this.secondaryOffset - offsetX) / 0.5f);
            Number number2 = Float.valueOf(this.y.floatValue() / 0.5f);
            Number number3 = diameter;
            Number number4 = diameter;
            float f2 = diameter;
            ElementalType elementalType = this.secondaryType;
            Intrinsics.checkNotNull((Object)elementalType);
            GuiUtilsKt.blitk$default(poseStack, resourceLocation, number, number2, number3, number4, (double)(f2 * (float)elementalType.getTextureXMultiplier()) + 0.1, null, diameter * 18, null, null, null, null, null, Float.valueOf(this.opacity), false, 0.5f, 48768, null);
        }
        PoseStack poseStack = context.m_280168_();
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"context.matrices");
        GuiUtilsKt.blitk$default(poseStack, this.small ? smallTypesResource : typesResource, Float.valueOf((this.x.floatValue() - offsetX) / 0.5f), Float.valueOf(this.y.floatValue() / 0.5f), diameter, diameter, (double)((float)diameter * (float)this.type.getTextureXMultiplier()) + 0.1, null, diameter * 18, null, null, null, null, null, Float.valueOf(this.opacity), false, 0.5f, 48768, null);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rR\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00058\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0007R\u0014\u0010\t\u001a\u00020\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\n\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/client/gui/TypeIcon$Companion;", "", "", "SCALE", "F", "", "TYPE_ICON_DIAMETER", "I", "Lnet/minecraft/resources/ResourceLocation;", "smallTypesResource", "Lnet/minecraft/resources/ResourceLocation;", "typesResource", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

