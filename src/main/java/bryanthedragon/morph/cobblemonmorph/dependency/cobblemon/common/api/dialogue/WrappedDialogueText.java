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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B\u0011\u0012\b\b\u0002\u0010\b\u001a\u00020\u0004\u00a2\u0006\u0004\b\f\u0010\rJ \u0010\u0006\u001a\n \u0005*\u0004\u0018\u00010\u00040\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/api/dialogue/WrappedDialogueText;", "Lcom/cobblemon/mod/common/api/dialogue/DialogueText;", "Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;", "activeDialogue", "Lnet/minecraft/network/chat/MutableComponent;", "kotlin.jvm.PlatformType", "invoke", "(Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;)Lnet/minecraft/network/chat/MutableComponent;", "text", "Lnet/minecraft/network/chat/MutableComponent;", "getText", "()Lnet/minecraft/network/chat/MutableComponent;", "<init>", "(Lnet/minecraft/network/chat/MutableComponent;)V", "common"})
public final class WrappedDialogueText
implements DialogueText {
    @NotNull
    private final MutableComponent text;

    public WrappedDialogueText(@NotNull MutableComponent text) {
        Intrinsics.checkNotNullParameter((Object)text, (String)"text");
        this.text = text;
    }

    public /* synthetic */ WrappedDialogueText(MutableComponent mutableComponent, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            mutableComponent = TextKt.text("");
        }
        this(mutableComponent);
    }

    @NotNull
    public final MutableComponent getText() {
        return this.text;
    }

    @Override
    public MutableComponent invoke(@NotNull ActiveDialogue activeDialogue) {
        Intrinsics.checkNotNullParameter((Object)activeDialogue, (String)"activeDialogue");
        return this.text.m_6881_();
    }

    public WrappedDialogueText() {
        this(null, 1, null);
    }
}

