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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\u0004\u001a\u0004\b\b\u0010\u0006R\u0017\u0010\t\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0004\u001a\u0004\b\n\u0010\u0006R\u0017\u0010\u000b\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\u0004\u001a\u0004\b\f\u0010\u0006R\u0017\u0010\r\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u0004\u001a\u0004\b\u000e\u0010\u0006R\u0017\u0010\u000f\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0004\u001a\u0004\b\u0010\u0010\u0006\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/mechanics/BerriesMechanic;", "", "Lcom/bedrockk/molang/Expression;", "evLowerAmount", "Lcom/bedrockk/molang/Expression;", "getEvLowerAmount", "()Lcom/bedrockk/molang/Expression;", "friendshipRaiseAmount", "getFriendshipRaiseAmount", "oranRestoreAmount", "getOranRestoreAmount", "portionHealRatio", "getPortionHealRatio", "ppRestoreAmount", "getPpRestoreAmount", "sitrusHealAmount", "getSitrusHealAmount", "<init>", "()V", "common"})
public final class BerriesMechanic {
    @NotNull
    private final Expression portionHealRatio;
    @NotNull
    private final Expression sitrusHealAmount;
    @NotNull
    private final Expression friendshipRaiseAmount;
    @NotNull
    private final Expression evLowerAmount;
    @NotNull
    private final Expression ppRestoreAmount;
    @NotNull
    private final Expression oranRestoreAmount;

    public BerriesMechanic() {
        Expression expression = MoLangExtensionsKt.asExpression("0.33");
        Intrinsics.checkNotNullExpressionValue((Object)expression, (String)"0.33\".asExpression()");
        this.portionHealRatio = expression;
        Expression expression2 = MoLangExtensionsKt.asExpression("v.pokemon.max_hp * 0.33");
        Intrinsics.checkNotNullExpressionValue((Object)expression2, (String)"v.pokemon.max_hp * 0.33\".asExpression()");
        this.sitrusHealAmount = expression2;
        Expression expression3 = MoLangExtensionsKt.asExpression("v.pokemon.friendship < 100 ? 10 : (v.pokemon.friendship < 200 ? 5 : 1)");
        Intrinsics.checkNotNullExpressionValue((Object)expression3, (String)"v.pokemon.friendship < 1\u2026 ? 5 : 1)\".asExpression()");
        this.friendshipRaiseAmount = expression3;
        Expression expression4 = MoLangExtensionsKt.asExpression("10");
        Intrinsics.checkNotNullExpressionValue((Object)expression4, (String)"10\".asExpression()");
        this.evLowerAmount = expression4;
        Expression expression5 = MoLangExtensionsKt.asExpression("10");
        Intrinsics.checkNotNullExpressionValue((Object)expression5, (String)"10\".asExpression()");
        this.ppRestoreAmount = expression5;
        Expression expression6 = MoLangExtensionsKt.asExpression("10");
        Intrinsics.checkNotNullExpressionValue((Object)expression6, (String)"10\".asExpression()");
        this.oranRestoreAmount = expression6;
    }

    @NotNull
    public final Expression getPortionHealRatio() {
        return this.portionHealRatio;
    }

    @NotNull
    public final Expression getSitrusHealAmount() {
        return this.sitrusHealAmount;
    }

    @NotNull
    public final Expression getFriendshipRaiseAmount() {
        return this.friendshipRaiseAmount;
    }

    @NotNull
    public final Expression getEvLowerAmount() {
        return this.evLowerAmount;
    }

    @NotNull
    public final Expression getPpRestoreAmount() {
        return this.ppRestoreAmount;
    }

    @NotNull
    public final Expression getOranRestoreAmount() {
        return this.oranRestoreAmount;
    }
}

