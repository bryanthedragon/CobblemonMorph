/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.InputConstants$Type
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.CobblemonKeyBinding;
import com.mojang.blaze3d.platform.InputConstants;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\r\u001a\u00020\f\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u000e\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u0012\u0006\u0010\u0012\u001a\u00020\f\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004R$\u0010\u0006\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/client/keybind/CobblemonCustomKeyBinding;", "Lcom/cobblemon/mod/common/client/keybind/CobblemonKeyBinding;", "", "onPress", "()V", "Ljava/lang/Runnable;", "run", "Ljava/lang/Runnable;", "getRun", "()Ljava/lang/Runnable;", "setRun", "(Ljava/lang/Runnable;)V", "", "name", "Lnet/minecraft/client/util/InputUtil$Type;", "type", "", "key", "category", "<init>", "(Ljava/lang/String;Lcom/mojang/blaze3d/platform/InputConstants$Type;ILjava/lang/String;)V", "common"})
public abstract class CobblemonCustomKeyBinding
extends CobblemonKeyBinding {
    @Nullable
    private Runnable run;

    public CobblemonCustomKeyBinding(@NotNull String name, @NotNull InputConstants.Type type, int key, @NotNull String category) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)category, (String)"category");
        super(name, type, key, category);
    }

    public /* synthetic */ CobblemonCustomKeyBinding(String string, InputConstants.Type type, int n, String string2, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 2) != 0) {
            type = InputConstants.Type.KEYSYM;
        }
        this(string, type, n, string2);
    }

    @Nullable
    public final Runnable getRun() {
        return this.run;
    }

    public final void setRun(@Nullable Runnable runnable) {
        this.run = runnable;
    }

    @Override
    public void onPress() {
        block0: {
            Runnable runnable = this.run;
            if (runnable == null) break block0;
            runnable.run();
        }
    }
}

