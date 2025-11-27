/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.StringValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;

import kotlin.jvm.internal.Intrinsics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ExpressionLikeDialogueAction implements DialogueAction {
    @NotNull
    private final ExpressionLike expression;

    public ExpressionLikeDialogueAction(@NotNull ExpressionLike expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"expression");
        this.expression = expression;
    }

    @NotNull
    public final ExpressionLike getExpression() {
        return this.expression;
    }

    @Override
    public void invoke(@NotNull ActiveDialogue dialogue2, @Nullable String input) {
        Intrinsics.checkNotNullParameter((Object)dialogue2, (String)"dialogue");
        if (input != null) {
            dialogue2.getRuntime().getEnvironment().setSimpleVariable("selected_option", new StringValue(input));
        }
        MoLangExtensionsKt.resolve(dialogue2.getRuntime(), this.expression);
    }
}

