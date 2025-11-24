/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.RenderableFace;
import kotlin.Metadata;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001b\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u00a2\u0006\u0004\b\f\u0010\rR\u0019\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0019\u0010\b\u001a\u0004\u0018\u00010\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/client/gui/dialogue/DialogueRenderableSpeaker;", "", "Lcom/cobblemon/mod/common/client/gui/dialogue/RenderableFace;", "face", "Lcom/cobblemon/mod/common/client/gui/dialogue/RenderableFace;", "getFace", "()Lcom/cobblemon/mod/common/client/gui/dialogue/RenderableFace;", "Lnet/minecraft/network/chat/MutableComponent;", "name", "Lnet/minecraft/network/chat/MutableComponent;", "getName", "()Lnet/minecraft/network/chat/MutableComponent;", "<init>", "(Lnet/minecraft/network/chat/MutableComponent;Lcom/cobblemon/mod/common/client/gui/dialogue/RenderableFace;)V", "common"})
public final class DialogueRenderableSpeaker {
    @Nullable
    private final MutableComponent name;
    @Nullable
    private final RenderableFace face;

    public DialogueRenderableSpeaker(@Nullable MutableComponent name, @Nullable RenderableFace face) {
        this.name = name;
        this.face = face;
    }

    @Nullable
    public final MutableComponent getName() {
        return this.name;
    }

    @Nullable
    public final RenderableFace getFace() {
        return this.face;
    }
}

