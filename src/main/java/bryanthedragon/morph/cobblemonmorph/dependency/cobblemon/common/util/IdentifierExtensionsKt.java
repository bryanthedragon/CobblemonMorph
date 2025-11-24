/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u001a\u0019\u0010\u0004\u001a\u00020\u0003*\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u0001\u00a2\u0006\u0004\b\u0004\u0010\u0005\u00a8\u0006\u0006"}, d2={"Lnet/minecraft/resources/ResourceLocation;", "", "suffix", "", "endsWith", "(Lnet/minecraft/resources/ResourceLocation;Ljava/lang/String;)Z", "common"})
public final class IdentifierExtensionsKt {
    public static final boolean endsWith(@NotNull ResourceLocation $this$endsWith, @NotNull String suffix) {
        Intrinsics.checkNotNullParameter((Object)$this$endsWith, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)suffix, (String)"suffix");
        String string = $this$endsWith.toString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this.toString()");
        return StringsKt.endsWith$default((String)string, (String)suffix, (boolean)false, (int)2, null);
    }
}

