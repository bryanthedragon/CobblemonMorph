/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.InputConstants$Key
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.KeyMapping
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.accessor.KeyBindingAccessor;
import com.mojang.blaze3d.platform.InputConstants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.KeyMapping;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0011\u0010\u0002\u001a\u00020\u0001*\u00020\u0000\u00a2\u0006\u0004\b\u0002\u0010\u0003\u00a8\u0006\u0004"}, d2={"Lnet/minecraft/client/KeyMapping;", "Lnet/minecraft/client/util/InputUtil$Key;", "boundKey", "(Lnet/minecraft/client/KeyMapping;)Lcom/mojang/blaze3d/platform/InputConstants$Key;", "common"})
public final class CurrentKeyAccessorKt {
    @NotNull
    public static final InputConstants.Key boundKey(@NotNull KeyMapping $this$boundKey) {
        Intrinsics.checkNotNullParameter((Object)$this$boundKey, (String)"<this>");
        InputConstants.Key key = ((KeyBindingAccessor)$this$boundKey).boundKey();
        Intrinsics.checkNotNullExpressionValue((Object)key, (String)"this as KeyBindingAccessor).boundKey()");
        return key;
    }
}

