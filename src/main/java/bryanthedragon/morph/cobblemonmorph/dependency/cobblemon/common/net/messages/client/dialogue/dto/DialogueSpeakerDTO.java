/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.dto;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueFaceProvider;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001d\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u00a2\u0006\u0004\b\f\u0010\rR\u0019\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0019\u0010\b\u001a\u0004\u0018\u00010\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueSpeakerDTO;", "", "Lcom/cobblemon/mod/common/api/dialogue/DialogueFaceProvider;", "face", "Lcom/cobblemon/mod/common/api/dialogue/DialogueFaceProvider;", "getFace", "()Lcom/cobblemon/mod/common/api/dialogue/DialogueFaceProvider;", "Lnet/minecraft/network/chat/MutableComponent;", "name", "Lnet/minecraft/network/chat/MutableComponent;", "getName", "()Lnet/minecraft/network/chat/MutableComponent;", "<init>", "(Lnet/minecraft/network/chat/MutableComponent;Lcom/cobblemon/mod/common/api/dialogue/DialogueFaceProvider;)V", "common"})
public final class DialogueSpeakerDTO {
    @Nullable
    private final MutableComponent name;
    @Nullable
    private final DialogueFaceProvider face;

    public DialogueSpeakerDTO(@Nullable MutableComponent name, @Nullable DialogueFaceProvider face) {
        this.name = name;
        this.face = face;
    }

    public /* synthetic */ DialogueSpeakerDTO(MutableComponent mutableComponent, DialogueFaceProvider dialogueFaceProvider, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            mutableComponent = null;
        }
        this(mutableComponent, dialogueFaceProvider);
    }

    @Nullable
    public final MutableComponent getName() {
        return this.name;
    }

    @Nullable
    public final DialogueFaceProvider getFace() {
        return this.face;
    }
}

