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
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J'\u0010\n\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\u0004\u00a2\u0006\u0004\b\n\u0010\u000bR\u001f\u0010\u000e\u001a\n \r*\u0004\u0018\u00010\f0\f8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0005\u0010\u0010R#\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\f0\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/mechanics/RemediesMechanic;", "", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "", "getFriendshipDrop", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;)I", "", "type", "default", "getHealingAmount", "(Ljava/lang/String;Lcom/bedrockk/molang/runtime/MoLangRuntime;I)I", "Lcom/bedrockk/molang/Expression;", "kotlin.jvm.PlatformType", "friendshipDrop", "Lcom/bedrockk/molang/Expression;", "()Lcom/bedrockk/molang/Expression;", "", "healingAmounts", "Ljava/util/Map;", "getHealingAmounts", "()Ljava/util/Map;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nRemediesMechanic.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RemediesMechanic.kt\ncom/cobblemon/mod/common/mechanics/RemediesMechanic\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,22:1\n1#2:23\n*E\n"})
public final class RemediesMechanic {
    @NotNull
    private final Map<String, Expression> healingAmounts = new LinkedHashMap();
    private final Expression friendshipDrop = MoLangExtensionsKt.asExpression("10");

    @NotNull
    public final Map<String, Expression> getHealingAmounts() {
        return this.healingAmounts;
    }

    public final Expression getFriendshipDrop() {
        return this.friendshipDrop;
    }

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

