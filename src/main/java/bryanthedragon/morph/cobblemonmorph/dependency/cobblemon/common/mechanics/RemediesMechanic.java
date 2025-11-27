/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mechanics;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;

import java.util.LinkedHashMap;
import java.util.Map;

import kotlin.jvm.internal.Intrinsics;

import org.jetbrains.annotations.NotNull;

public final class RemediesMechanic {
    @NotNull
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private final Map<String, Expression> healingAmounts = new LinkedHashMap();
    private final Expression friendshipDrop = MoLangExtensionsKt.asExpression("10");

    @NotNull
    public final Map<String, Expression> getHealingAmounts() {
        return this.healingAmounts;
    }

    public final Expression getFriendshipDrop() {
        return this.friendshipDrop;
    }

    @SuppressWarnings("unused")
    public final int getHealingAmount(@NotNull String type, @NotNull MoLangRuntime runtime2, int n) {
        int n2;
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        Expression expression = this.healingAmounts.get(type);
        if (expression != null) {
            Expression it = expression;
            boolean bl = false;
            n2 = MoLangExtensionsKt.resolveInt(runtime2, it);
        } else {
            n2 = n;
        }
        return n2;
    }

    public static /* synthetic */ int getHealingAmount$default(RemediesMechanic remediesMechanic, String string, MoLangRuntime moLangRuntime, int n, int n2, Object object) {
        if ((n2 & 4) != 0) {
            n = 20;
        }
        return remediesMechanic.getHealingAmount(string, moLangRuntime, n);
    }

    public final int getFriendshipDrop(@NotNull MoLangRuntime runtime2) {
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        Expression expression = this.friendshipDrop;
        Intrinsics.checkNotNullExpressionValue((Object)expression, (String)"friendshipDrop");
        return MoLangExtensionsKt.resolveInt(runtime2, expression);
    }
}

