/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.util;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\u001a\u001b\u0010\u0003\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0000\u00a2\u0006\u0004\b\u0003\u0010\u0004\u001a\u0011\u0010\u0007\u001a\u00020\u0006*\u00020\u0005\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, d2={"Lkotlin/Function0;", "", "action", "runOnRender", "(Lkotlin/jvm/functions/Function0;)V", "Lnet/minecraft/resources/ResourceLocation;", "", "exists", "(Lnet/minecraft/resources/ResourceLocation;)Z", "common"})
public final class ClientDistributionUtilsKt {
    public static final boolean exists(@NotNull ResourceLocation $this$exists) {
        Intrinsics.checkNotNullParameter((Object)$this$exists, (String)"<this>");
        return Minecraft.m_91087_().m_91098_().m_213713_($this$exists).isPresent();
    }

    public static final void runOnRender(@NotNull Function0<Unit> action2) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        Minecraft.m_91087_().execute(() -> ClientDistributionUtilsKt.runOnRender$lambda$0(action2));
    }

    private static final void runOnRender$lambda$0(Function0 $tmp0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke();
    }
}

