/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueFaceProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueText;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.WrappedDialogueText;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001f\u0012\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0004\u00a2\u0006\u0004\b\u000f\u0010\u0010J#\u0010\u0006\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00022\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0004\u00a2\u0006\u0004\b\u0006\u0010\u0007R\u0019\u0010\u0005\u001a\u0004\u0018\u00010\u00048\u0006\u00a2\u0006\f\n\u0004\b\u0005\u0010\b\u001a\u0004\b\t\u0010\nR\u0019\u0010\u0003\u001a\u0004\u0018\u00010\u000b8\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\f\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/api/dialogue/DialogueSpeaker;", "", "Lnet/minecraft/network/chat/MutableComponent;", "name", "Lcom/cobblemon/mod/common/api/dialogue/DialogueFaceProvider;", "face", "of", "(Lnet/minecraft/network/chat/MutableComponent;Lcom/cobblemon/mod/common/api/dialogue/DialogueFaceProvider;)Lcom/cobblemon/mod/common/api/dialogue/DialogueSpeaker;", "Lcom/cobblemon/mod/common/api/dialogue/DialogueFaceProvider;", "getFace", "()Lcom/cobblemon/mod/common/api/dialogue/DialogueFaceProvider;", "Lcom/cobblemon/mod/common/api/dialogue/DialogueText;", "Lcom/cobblemon/mod/common/api/dialogue/DialogueText;", "getName", "()Lcom/cobblemon/mod/common/api/dialogue/DialogueText;", "<init>", "(Lcom/cobblemon/mod/common/api/dialogue/DialogueText;Lcom/cobblemon/mod/common/api/dialogue/DialogueFaceProvider;)V", "common"})
public final class DialogueSpeaker {
    @Nullable
    private final DialogueText name;
    @Nullable
    private final DialogueFaceProvider face;

    public DialogueSpeaker(@Nullable DialogueText name, @Nullable DialogueFaceProvider face) {
        this.name = name;
        this.face = face;
    }

    public /* synthetic */ DialogueSpeaker(DialogueText dialogueText, DialogueFaceProvider dialogueFaceProvider, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            dialogueText = null;
        }
        if ((n & 2) != 0) {
            dialogueFaceProvider = null;
        }
        this(dialogueText, dialogueFaceProvider);
    }

    @Nullable
    public final DialogueText getName() {
        return this.name;
    }

    @Nullable
    public final DialogueFaceProvider getFace() {
        return this.face;
    }

    @NotNull
    public final DialogueSpeaker of(@NotNull MutableComponent name, @Nullable DialogueFaceProvider face) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        return new DialogueSpeaker(new WrappedDialogueText(name), face);
    }

    public static /* synthetic */ DialogueSpeaker of$default(DialogueSpeaker dialogueSpeaker, MutableComponent mutableComponent, DialogueFaceProvider dialogueFaceProvider, int n, Object object) {
        if ((n & 1) != 0) {
            mutableComponent = TextKt.text("");
        }
        if ((n & 2) != 0) {
            dialogueFaceProvider = null;
        }
        return dialogueSpeaker.of(mutableComponent, dialogueFaceProvider);
    }

    public DialogueSpeaker() {
        this(null, null, 3, null);
    }
}

