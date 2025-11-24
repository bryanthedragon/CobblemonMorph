/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.components.Renderable
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Renderable;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0004\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\u001a\u0019\u0010\u0004\u001a\u00020\u0003*\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u0001\u00a2\u0006\u0004\b\u0004\u0010\u0005\u00a8\u0006\u0006"}, d2={"Lnet/minecraft/client/gui/components/Renderable;", "", "value", "", "scaleIt", "(Lnet/minecraft/client/gui/components/Renderable;Ljava/lang/Number;)I", "common"})
public final class DrawableExtensionsKt {
    public static final int scaleIt(@NotNull Renderable $this$scaleIt, @NotNull Number value2) {
        Intrinsics.checkNotNullParameter((Object)$this$scaleIt, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
        return (int)(Minecraft.m_91087_().m_91268_().m_85449_() * (double)value2.floatValue());
    }
}

