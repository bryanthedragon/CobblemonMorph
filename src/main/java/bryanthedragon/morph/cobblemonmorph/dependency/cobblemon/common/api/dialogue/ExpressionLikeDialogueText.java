/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ActiveDialogue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueText;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0011\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\f\u0010\rJ\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/api/dialogue/ExpressionLikeDialogueText;", "Lcom/cobblemon/mod/common/api/dialogue/DialogueText;", "Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;", "activeDialogue", "Lnet/minecraft/network/chat/MutableComponent;", "invoke", "(Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;)Lnet/minecraft/network/chat/MutableComponent;", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "expression", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "getExpression", "()Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "<init>", "(Lcom/cobblemon/mod/common/api/molang/ExpressionLike;)V", "common"})
public final class ExpressionLikeDialogueText
implements DialogueText {
    @NotNull
    private final ExpressionLike expression;

    public ExpressionLikeDialogueText(@NotNull ExpressionLike expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"expression");
        this.expression = expression;
    }

    public /* synthetic */ ExpressionLikeDialogueText(ExpressionLike expressionLike, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            expressionLike = MoLangExtensionsKt.asExpressionLike("''");
        }
        this(expressionLike);
    }

    @NotNull
    public final ExpressionLike getExpression() {
        return this.expression;
    }

    @Override
    @NotNull
    public MutableComponent invoke(@NotNull ActiveDialogue activeDialogue) {
        Intrinsics.checkNotNullParameter((Object)activeDialogue, (String)"activeDialogue");
        return TextKt.text(MoLangExtensionsKt.resolveString(activeDialogue.getRuntime(), this.expression));
    }

    public ExpressionLikeDialogueText() {
        this(null, 1, null);
    }
}

