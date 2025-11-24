/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mechanics;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u000b\u0010\fR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\u0004\u001a\u0004\b\b\u0010\u0006R\u0017\u0010\t\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0004\u001a\u0004\b\n\u0010\u0006\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/mechanics/PotionsMechanic;", "", "Lcom/bedrockk/molang/Expression;", "hyperPotionRestoreAmount", "Lcom/bedrockk/molang/Expression;", "getHyperPotionRestoreAmount", "()Lcom/bedrockk/molang/Expression;", "potionRestoreAmount", "getPotionRestoreAmount", "superPotionRestoreAmount", "getSuperPotionRestoreAmount", "<init>", "()V", "common"})
public final class PotionsMechanic {
    @NotNull
    private final Expression potionRestoreAmount;
    @NotNull
    private final Expression superPotionRestoreAmount;
    @NotNull
    private final Expression hyperPotionRestoreAmount;

    public PotionsMechanic() {
        Expression expression = MoLangExtensionsKt.asExpression("60");
        Intrinsics.checkNotNullExpressionValue((Object)expression, (String)"60\".asExpression()");
        this.potionRestoreAmount = expression;
        Expression expression2 = MoLangExtensionsKt.asExpression("100");
        Intrinsics.checkNotNullExpressionValue((Object)expression2, (String)"100\".asExpression()");
        this.superPotionRestoreAmount = expression2;
        Expression expression3 = MoLangExtensionsKt.asExpression("150");
        Intrinsics.checkNotNullExpressionValue((Object)expression3, (String)"150\".asExpression()");
        this.hyperPotionRestoreAmount = expression3;
    }

    @NotNull
    public final Expression getPotionRestoreAmount() {
        return this.potionRestoreAmount;
    }

    @NotNull
    public final Expression getSuperPotionRestoreAmount() {
        return this.superPotionRestoreAmount;
    }

    @NotNull
    public final Expression getHyperPotionRestoreAmount() {
        return this.hyperPotionRestoreAmount;
    }
}

