/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.MinecraftServer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ActiveDialogue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueInput;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DistributionUtilsKt;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\n\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\u0010\u001a\u00020\u000f\u00a2\u0006\u0004\b\"\u0010#J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\r\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\b\u0010\tR\u0017\u0010\u000b\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u0010\u001a\u00020\u000f8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013R\u001f\u0010\u0016\u001a\n \u0015*\u0004\u0018\u00010\u00140\u00148\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\u001b\u001a\u00020\u001a8\u0006\u00a2\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001eR\u0017\u0010\u001f\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\t\u00a8\u0006$"}, d2={"Lcom/cobblemon/mod/common/api/dialogue/input/ActiveInput;", "", "", "input", "", "handle", "(Ljava/lang/String;)V", "Lcom/bedrockk/molang/runtime/value/MoValue;", "toMoLangStruct", "()Lcom/bedrockk/molang/runtime/value/MoValue;", "Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;", "activeDialogue", "Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;", "getActiveDialogue", "()Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;", "Lcom/cobblemon/mod/common/api/dialogue/input/DialogueInput;", "dialogueInput", "Lcom/cobblemon/mod/common/api/dialogue/input/DialogueInput;", "getDialogueInput", "()Lcom/cobblemon/mod/common/api/dialogue/input/DialogueInput;", "Ljava/util/UUID;", "kotlin.jvm.PlatformType", "inputId", "Ljava/util/UUID;", "getInputId", "()Ljava/util/UUID;", "", "startTime", "J", "getStartTime", "()J", "struct", "Lcom/bedrockk/molang/runtime/value/MoValue;", "getStruct", "<init>", "(Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;Lcom/cobblemon/mod/common/api/dialogue/input/DialogueInput;)V", "common"})
public final class ActiveInput {
    @NotNull
    private final ActiveDialogue activeDialogue;
    @NotNull
    private final DialogueInput dialogueInput;
    private final UUID inputId;
    private final long startTime;
    @NotNull
    private final MoValue struct;

    public ActiveInput(@NotNull ActiveDialogue activeDialogue, @NotNull DialogueInput dialogueInput) {
        Intrinsics.checkNotNullParameter((Object)activeDialogue, (String)"activeDialogue");
        Intrinsics.checkNotNullParameter((Object)dialogueInput, (String)"dialogueInput");
        this.activeDialogue = activeDialogue;
        this.dialogueInput = dialogueInput;
        this.inputId = UUID.randomUUID();
        MinecraftServer minecraftServer = DistributionUtilsKt.server();
        Intrinsics.checkNotNull((Object)minecraftServer);
        this.startTime = minecraftServer.m_129783_().m_46467_();
        this.struct = this.toMoLangStruct();
    }

    @NotNull
    public final ActiveDialogue getActiveDialogue() {
        return this.activeDialogue;
    }

    @NotNull
    public final DialogueInput getDialogueInput() {
        return this.dialogueInput;
    }

    public final UUID getInputId() {
        return this.inputId;
    }

    public final long getStartTime() {
        return this.startTime;
    }

    @NotNull
    public final MoValue getStruct() {
        return this.struct;
    }

    public final void handle(@NotNull String input) {
        Intrinsics.checkNotNullParameter((Object)input, (String)"input");
        MinecraftServer minecraftServer = DistributionUtilsKt.server();
        Intrinsics.checkNotNull((Object)minecraftServer);
        float secondsToChoose = (float)(minecraftServer.m_129783_().m_46467_() - this.startTime) / 20.0f;
        this.activeDialogue.getRuntime().getEnvironment().setSimpleVariable("seconds_taken_to_input", new DoubleValue(Float.valueOf(secondsToChoose)));
        this.dialogueInput.handle(this, input);
    }

    @NotNull
    public final MoValue toMoLangStruct() {
        return this.dialogueInput.toMoLangStruct(this);
    }
}

