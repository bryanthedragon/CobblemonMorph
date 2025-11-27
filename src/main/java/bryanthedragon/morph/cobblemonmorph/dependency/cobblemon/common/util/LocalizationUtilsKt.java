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

import java.util.Arrays;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import net.minecraft.network.chat.MutableComponent;

import org.jetbrains.annotations.NotNull;

public final class LocalizationUtilsKt {
    public static final MutableComponent lang(@NotNull String subKey, Object ... objects) {
        Intrinsics.checkNotNullParameter((Object)subKey, (String)"subKey");
        Intrinsics.checkNotNullParameter((Object)objects, (String)"objects");
        return MiscUtils.asTranslated("cobblemon." + subKey, Arrays.copyOf(objects, objects.length));
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
        return MiscUtils.asTranslated("item." + modId + "." + key + ".tooltip", Arrays.copyOf(objects, objects.length));
    }

    public static /* synthetic */ MutableComponent tooltipLang$default(String string, String string2, Object[] objectArray, int n, Object object) {
        if ((n & 1) != 0) {
            string = "cobblemon";
        }
        return LocalizationUtilsKt.tooltipLang(string, string2, objectArray);
    }
}

