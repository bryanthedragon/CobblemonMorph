/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\f\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u0017\u0012\u0006\u0010\u0010\u001a\u00020\u000b\u0012\u0006\u0010\u0014\u001a\u00020\u000b\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u001d\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J-\u0010\u000e\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0010\u001a\u00020\u000b8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0014\u001a\u00020\u000b8\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0011\u001a\u0004\b\u0015\u0010\u0013\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleBackButton;", "", "", "mouseX", "mouseY", "", "isHovered", "(DD)Z", "Lcom/mojang/blaze3d/vertex/PoseStack;", "matrices", "", "", "delta", "", "render", "(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V", "x", "F", "getX", "()F", "y", "getY", "<init>", "(FF)V", "Companion", "common"})
public final class BattleBackButton {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final float x;
    private final float y;
    public static final int WIDTH = 58;
    public static final int HEIGHT = 34;
    public static final float SCALE = 0.5f;

    public BattleBackButton(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public final float getX() {
        return this.x;
    }

    public final float getY() {
        return this.y;
    }

    public final void render(@NotNull PoseStack matrices, int mouseX, int mouseY, float delta) {
        Intrinsics.checkNotNullParameter((Object)matrices, (String)"matrices");
        GuiUtilsKt.blitk$default(matrices, MiscUtilsKt.cobblemonResource("textures/gui/battle/battle_back.png"), Float.valueOf(this.x * (float)2), Float.valueOf(this.y * (float)2), 34, 58, null, this.isHovered(mouseX, mouseY) ? 34 : 0, null, 68, null, null, null, null, null, false, 0.5f, 64832, null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean isHovered(double mouseX, double mouseY) {
        float f = this.x;
        float f2 = this.x + 29.0f;
        float f3 = (float)mouseX;
        if (!(f <= f3)) return false;
        if (!(f3 <= f2)) return false;
        boolean bl = true;
        if (!bl) return false;
        f = this.y;
        f2 = this.y + 17.0f;
        f3 = (float)mouseY;
        if (!(f <= f3)) return false;
        if (!(f3 <= f2)) return false;
        return true;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nR\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00058\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\b\u0010\u0004\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleBackButton$Companion;", "", "", "HEIGHT", "I", "", "SCALE", "F", "WIDTH", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

