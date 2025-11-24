/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001f\u0010\u0006\u001a\n \u0005*\u0004\u0018\u00010\u00040\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007R\u001d\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\b8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/api/molang/ListExpression;", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lcom/bedrockk/molang/runtime/value/MoValue;", "kotlin.jvm.PlatformType", "resolve", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;)Lcom/bedrockk/molang/runtime/value/MoValue;", "", "Lcom/bedrockk/molang/Expression;", "exprs", "Ljava/util/List;", "getExprs", "()Ljava/util/List;", "<init>", "(Ljava/util/List;)V", "common"})
public final class ListExpression
implements ExpressionLike {
    @NotNull
    private final List<Expression> exprs;

    public ListExpression(@NotNull List<? extends Expression> exprs) {
        Intrinsics.checkNotNullParameter(exprs, (String)"exprs");
        this.exprs = exprs;
    }

    @NotNull
    public final List<Expression> getExprs() {
        return this.exprs;
    }

    @Override
    public MoValue resolve(@NotNull MoLangRuntime runtime2) {
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        return MoLangExtensionsKt.resolve(this.exprs, runtime2);
    }

    @Override
    public double resolveDouble(@NotNull MoLangRuntime runtime2) {
        return ExpressionLike.DefaultImpls.resolveDouble(this, runtime2);
    }

    @Override
    public float resolveFloat(@NotNull MoLangRuntime runtime2) {
        return ExpressionLike.DefaultImpls.resolveFloat(this, runtime2);
    }

    @Override
    public String resolveString(@NotNull MoLangRuntime runtime2) {
        return ExpressionLike.DefaultImpls.resolveString(this, runtime2);
    }

    @Override
    public int resolveInt(@NotNull MoLangRuntime runtime2) {
        return ExpressionLike.DefaultImpls.resolveInt(this, runtime2);
    }

    @Override
    public boolean resolveBoolean(@NotNull MoLangRuntime runtime2) {
        return ExpressionLike.DefaultImpls.resolveBoolean(this, runtime2);
    }

    @Override
    @NotNull
    public ObjectValue<?> resolveObject(@NotNull MoLangRuntime runtime2) {
        return ExpressionLike.DefaultImpls.resolveObject(this, runtime2);
    }
}

