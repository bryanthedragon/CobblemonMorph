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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ActiveDialogue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\n\u001a\u00020\t\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\"\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0096\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\n\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/api/dialogue/ExpressionLikeDialogueAction;", "Lcom/cobblemon/mod/common/api/dialogue/DialogueAction;", "Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;", "dialogue", "", "input", "", "invoke", "(Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;Ljava/lang/String;)V", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "expression", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "getExpression", "()Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "<init>", "(Lcom/cobblemon/mod/common/api/molang/ExpressionLike;)V", "common"})
public final class ExpressionLikeDialogueAction
implements DialogueAction {
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

