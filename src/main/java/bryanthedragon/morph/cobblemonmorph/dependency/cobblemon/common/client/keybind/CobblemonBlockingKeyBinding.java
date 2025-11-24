/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.InputConstants$Type
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.CobblemonKeyBinding;
import com.mojang.blaze3d.platform.InputConstants;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0015\u001a\u00020\u0014\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0016\u0012\u0006\u0010\u0019\u001a\u00020\u0018\u0012\u0006\u0010\u001a\u001a\u00020\u0014\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0005\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0004R\"\u0010\u0007\u001a\u00020\u00068\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\"\u0010\u000e\u001a\u00020\r8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/client/keybind/CobblemonBlockingKeyBinding;", "Lcom/cobblemon/mod/common/client/keybind/CobblemonKeyBinding;", "", "onRelease", "()V", "onTick", "", "timeDown", "F", "getTimeDown", "()F", "setTimeDown", "(F)V", "", "wasDown", "Z", "getWasDown", "()Z", "setWasDown", "(Z)V", "", "name", "Lnet/minecraft/client/util/InputUtil$Type;", "type", "", "key", "category", "<init>", "(Ljava/lang/String;Lcom/mojang/blaze3d/platform/InputConstants$Type;ILjava/lang/String;)V", "common"})
public abstract class CobblemonBlockingKeyBinding
extends CobblemonKeyBinding {
    private boolean wasDown;
    private float timeDown;

    public CobblemonBlockingKeyBinding(@NotNull String name, @NotNull InputConstants.Type type, int key, @NotNull String category) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)category, (String)"category");
        super(name, type, key, category);
    }

    public /* synthetic */ CobblemonBlockingKeyBinding(String string, InputConstants.Type type, int n, String string2, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 2) != 0) {
            type = InputConstants.Type.KEYSYM;
        }
        this(string, type, n, string2);
    }

    public final boolean getWasDown() {
        return this.wasDown;
    }

    public final void setWasDown(boolean bl) {
        this.wasDown = bl;
    }

    public final float getTimeDown() {
        return this.timeDown;
    }

    public final void setTimeDown(float f) {
        this.timeDown = f;
    }

    public void onRelease() {
    }

    @Override
    public void onTick() {
        if (this.m_90857_() && !this.wasDown) {
            this.wasDown = true;
            this.timeDown = 0.0f;
            this.onPress();
        } else if (!this.m_90857_() && this.wasDown) {
            this.onRelease();
            this.wasDown = false;
        } else if (!this.m_90857_()) {
            this.wasDown = false;
        } else if (this.wasDown) {
            this.timeDown += Minecraft.m_91087_().m_91296_();
        }
    }
}

