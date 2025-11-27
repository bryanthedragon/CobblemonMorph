/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;

import kotlin.jvm.internal.Intrinsics;

import org.jetbrains.annotations.NotNull;

public final class ExpressionLikeDialogueFaceProvider implements DialogueFaceProvider {
    @NotNull
    private final ExpressionLike providerExpression;

    public ExpressionLikeDialogueFaceProvider(@NotNull ExpressionLike providerExpression) {
        Intrinsics.checkNotNullParameter((Object)providerExpression, (String)"providerExpression");
        this.providerExpression = providerExpression;
    }

    @NotNull
    public final ExpressionLike getProviderExpression() {
        return this.providerExpression;
    }
}

