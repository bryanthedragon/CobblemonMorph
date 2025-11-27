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
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.categories.DamageCategory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\u0004\n\u0002\b\n\u0018\u0000 \u001a2\u00020\u0001:\u0001\u001aB)\u0012\u0006\u0010\u0012\u001a\u00020\u0011\u0012\u0006\u0010\u0016\u001a\u00020\u0011\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0012\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0016\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0013\u001a\u0004\b\u0017\u0010\u0015\u00a8\u0006\u001b"}, d2={"Lcom/cobblemon/mod/common/client/gui/MoveCategoryIcon;", "", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "render", "(Lnet/minecraft/client/gui/GuiGraphics;)V", "Lcom/cobblemon/mod/common/api/moves/categories/DamageCategory;", "category", "Lcom/cobblemon/mod/common/api/moves/categories/DamageCategory;", "getCategory", "()Lcom/cobblemon/mod/common/api/moves/categories/DamageCategory;", "", "opacity", "F", "getOpacity", "()F", "", "x", "Ljava/lang/Number;", "getX", "()Ljava/lang/Number;", "y", "getY", "<init>", "(Ljava/lang/Number;Ljava/lang/Number;Lcom/cobblemon/mod/common/api/moves/categories/DamageCategory;F)V", "Companion", "common"})
public final class MoveCategoryIcon {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Number x;
    @NotNull
    private final Number y;
    @NotNull
    private final DamageCategory category;
    private final float opacity;
    private static final int WIDTH = 24;
    private static final int HEIGHT = 16;
    private static final float SCALE = 0.5f;
    @NotNull
    private static final ResourceLocation categoriesResource = MiscUtils.cobblemonResource("textures/gui/categories.png");

    public MoveCategoryIcon(@NotNull Number x, @NotNull Number y, @NotNull DamageCategory category, float opacity) {
        Intrinsics.checkNotNullParameter((Object)x, (String)"x");
        Intrinsics.checkNotNullParameter((Object)y, (String)"y");
        Intrinsics.checkNotNullParameter((Object)category, (String)"category");
        this.x = x;
        this.y = y;
        this.category = category;
        this.opacity = opacity;
    }

    public /* synthetic */ MoveCategoryIcon(Number number, Number number2, DamageCategory damageCategory, float f, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 8) != 0) {
            f = 1.0f;
        }
        this(number, number2, damageCategory, f);
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
    public final DamageCategory getCategory() {
        return this.category;
    }

    public final float getOpacity() {
        return this.opacity;
    }

    public final void render(@NotNull GuiGraphics context) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        PoseStack poseStack = context.m_280168_();
        ResourceLocation resourceLocation = categoriesResource;
        float f = this.x.floatValue() / 0.5f;
        float f2 = this.y.floatValue() / 0.5f;
        int n = 16 * this.category.getTextureXMultiplier();
        float f3 = this.opacity;
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"matrices");
        GuiUtilsKt.blitk$default(poseStack, resourceLocation, Float.valueOf(f), Float.valueOf(f2), 16, 24, null, n, null, 48, null, null, null, null, Float.valueOf(f3), false, 0.5f, 48448, null);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rR\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00058\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\b\u0010\u0004R\u0014\u0010\n\u001a\u00020\t8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/client/gui/MoveCategoryIcon$Companion;", "", "", "HEIGHT", "I", "", "SCALE", "F", "WIDTH", "Lnet/minecraft/resources/ResourceLocation;", "categoriesResource", "Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

