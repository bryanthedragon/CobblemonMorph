/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u0018\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u001a1\u0010\u0007\u001a\n \u0006*\u0004\u0018\u00010\u00050\u00052\u0006\u0010\u0001\u001a\u00020\u00002\u0012\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u0002\"\u00020\u0003\u00a2\u0006\u0004\b\u0007\u0010\b\u001a1\u0010\n\u001a\n \u0006*\u0004\u0018\u00010\u00050\u00052\u0006\u0010\t\u001a\u00020\u00002\u0012\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u0002\"\u00020\u0003\u00a2\u0006\u0004\b\n\u0010\b\u001a1\u0010\u000b\u001a\n \u0006*\u0004\u0018\u00010\u00050\u00052\u0006\u0010\t\u001a\u00020\u00002\u0012\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u0002\"\u00020\u0003\u00a2\u0006\u0004\b\u000b\u0010\b\u001a;\u0010\r\u001a\n \u0006*\u0004\u0018\u00010\u00050\u00052\b\b\u0002\u0010\f\u001a\u00020\u00002\u0006\u0010\u0001\u001a\u00020\u00002\u0012\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u0002\"\u00020\u0003\u00a2\u0006\u0004\b\r\u0010\u000e\u00a8\u0006\u000f"}, d2={"", "key", "", "", "objects", "Lnet/minecraft/network/chat/MutableComponent;", "kotlin.jvm.PlatformType", "battleLang", "(Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/network/chat/MutableComponent;", "subKey", "commandLang", "lang", "modId", "tooltipLang", "(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/network/chat/MutableComponent;", "common"})
public final class LocalizationUtilsKt {
    public static final MutableComponent lang(@NotNull String subKey, Object ... objects) {
        Intrinsics.checkNotNullParameter((Object)subKey, (String)"subKey");
        Intrinsics.checkNotNullParameter((Object)objects, (String)"objects");
        return MiscUtilsKt.asTranslated("cobblemon." + subKey, Arrays.copyOf(objects, objects.length));
    }

    public static final MutableComponent commandLang(@NotNull String subKey, Object ... objects) {
        Intrinsics.checkNotNullParameter((Object)subKey, (String)"subKey");
        Intrinsics.checkNotNullParameter((Object)objects, (String)"objects");
        return LocalizationUtilsKt.lang("command." + subKey, Arrays.copyOf(objects, objects.length));
    }

    public static final MutableComponent battleLang(@NotNull String key, Object ... objects) {
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        Intrinsics.checkNotNullParameter((Object)objects, (String)"objects");
        return LocalizationUtilsKt.lang("battle." + key, Arrays.copyOf(objects, objects.length));
    }

    public static final MutableComponent tooltipLang(@NotNull String modId, @NotNull String key, Object ... objects) {
        Intrinsics.checkNotNullParameter((Object)modId, (String)"modId");
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        Intrinsics.checkNotNullParameter((Object)objects, (String)"objects");
        return MiscUtilsKt.asTranslated("item." + modId + "." + key + ".tooltip", Arrays.copyOf(objects, objects.length));
    }

    public static /* synthetic */ MutableComponent tooltipLang$default(String string, String string2, Object[] objectArray, int n, Object object) {
        if ((n & 1) != 0) {
            string = "cobblemon";
        }
        return LocalizationUtilsKt.tooltipLang(string, string2, objectArray);
    }
}

