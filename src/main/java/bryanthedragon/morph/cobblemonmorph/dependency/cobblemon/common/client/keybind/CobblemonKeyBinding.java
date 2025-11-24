/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.InputConstants$Type
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.KeyMapping
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.KeyMapping;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\f\u001a\u00020\u0006\u00a2\u0006\u0004\b\r\u0010\u000eJ\u000f\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0005\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0004\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/client/keybind/CobblemonKeyBinding;", "Lnet/minecraft/client/KeyMapping;", "", "onPress", "()V", "onTick", "", "name", "Lnet/minecraft/client/util/InputUtil$Type;", "type", "", "key", "category", "<init>", "(Ljava/lang/String;Lcom/mojang/blaze3d/platform/InputConstants$Type;ILjava/lang/String;)V", "common"})
public abstract class CobblemonKeyBinding
extends KeyMapping {
    public CobblemonKeyBinding(@NotNull String name, @NotNull InputConstants.Type type, int key, @NotNull String category) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)category, (String)"category");
        super(name, type, key, category);
    }

    public /* synthetic */ CobblemonKeyBinding(String string, InputConstants.Type type, int n, String string2, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 2) != 0) {
            type = InputConstants.Type.KEYSYM;
        }
        this(string, type, n, string2);
    }

    public abstract void onPress();

    public void onTick() {
        if (this.m_90859_()) {
            this.onPress();
        }
    }
}

