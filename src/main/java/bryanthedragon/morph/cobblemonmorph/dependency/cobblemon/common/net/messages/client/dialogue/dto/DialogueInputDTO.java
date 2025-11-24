/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.dto;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ActiveDialogue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueAutoContinueInput;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueOption;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueOptionSetInput;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueTextInput;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueTimeout;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Decodable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Encodable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.dto.DialogueOptionDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u00012\u00020\u0002:\u0001AB\u0019\b\u0016\u0012\u0006\u00104\u001a\u000203\u0012\u0006\u00106\u001a\u000205\u00a2\u0006\u0004\b7\u00108B\u0019\b\u0016\u0012\u0006\u0010:\u001a\u000209\u0012\u0006\u00106\u001a\u000205\u00a2\u0006\u0004\b7\u0010;B\u0019\b\u0016\u0012\u0006\u0010=\u001a\u00020<\u0012\u0006\u00106\u001a\u000205\u00a2\u0006\u0004\b7\u0010>B\u0011\b\u0016\u0012\u0006\u00106\u001a\u000205\u00a2\u0006\u0004\b7\u0010?B\u0007\u00a2\u0006\u0004\b7\u0010@J\u0017\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0017\u0010\b\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\b\u0010\u0007R\"\u0010\n\u001a\u00020\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\"\u0010\u0011\u001a\u00020\u00108\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\"\u0010\u0018\u001a\u00020\u00178\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\"\u0010\u001f\u001a\u00020\u001e8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R(\u0010'\u001a\b\u0012\u0004\u0012\u00020&0%8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b'\u0010(\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\"\u0010-\u001a\u00020\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b-\u0010\u000b\u001a\u0004\b.\u0010\r\"\u0004\b/\u0010\u000fR\"\u00100\u001a\u00020\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b0\u0010\u000b\u001a\u0004\b1\u0010\r\"\u0004\b2\u0010\u000f\u00a8\u0006B"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueInputDTO;", "Lcom/cobblemon/mod/common/api/net/Encodable;", "Lcom/cobblemon/mod/common/api/net/Decodable;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "encode", "", "allowSkip", "Z", "getAllowSkip", "()Z", "setAllowSkip", "(Z)V", "", "deadline", "F", "getDeadline", "()F", "setDeadline", "(F)V", "Ljava/util/UUID;", "inputId", "Ljava/util/UUID;", "getInputId", "()Ljava/util/UUID;", "setInputId", "(Ljava/util/UUID;)V", "Lcom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueInputDTO$InputType;", "inputType", "Lcom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueInputDTO$InputType;", "getInputType", "()Lcom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueInputDTO$InputType;", "setInputType", "(Lcom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueInputDTO$InputType;)V", "", "Lcom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueOptionDTO;", "options", "Ljava/util/List;", "getOptions", "()Ljava/util/List;", "setOptions", "(Ljava/util/List;)V", "showTimer", "getShowTimer", "setShowTimer", "vertical", "getVertical", "setVertical", "Lcom/cobblemon/mod/common/api/dialogue/input/DialogueOptionSetInput;", "optionSet", "Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;", "activeDialogue", "<init>", "(Lcom/cobblemon/mod/common/api/dialogue/input/DialogueOptionSetInput;Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;)V", "Lcom/cobblemon/mod/common/api/dialogue/input/DialogueAutoContinueInput;", "autoContinue", "(Lcom/cobblemon/mod/common/api/dialogue/input/DialogueAutoContinueInput;Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;)V", "Lcom/cobblemon/mod/common/api/dialogue/input/DialogueTextInput;", "text", "(Lcom/cobblemon/mod/common/api/dialogue/input/DialogueTextInput;Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;)V", "(Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;)V", "()V", "InputType", "common"})
@SourceDebugExtension(value={"SMAP\nDialogueInputDTO.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DialogueInputDTO.kt\ncom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueInputDTO\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,119:1\n1549#2:120\n1620#2,3:121\n1855#2,2:124\n*S KotlinDebug\n*F\n+ 1 DialogueInputDTO.kt\ncom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueInputDTO\n*L\n46#1:120\n46#1:121,3\n87#1:124,2\n*E\n"})
public final class DialogueInputDTO
implements Encodable,
Decodable {
    @NotNull
    private UUID inputId;
    @NotNull
    private InputType inputType;
    private float deadline;
    private boolean showTimer;
    @NotNull
    private List<DialogueOptionDTO> options;
    private boolean vertical;
    private boolean allowSkip;

    public DialogueInputDTO() {
        UUID uUID = UUID.randomUUID();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"randomUUID()");
        this.inputId = uUID;
        this.inputType = InputType.NONE;
        this.deadline = -1.0f;
        this.showTimer = true;
        this.options = new ArrayList();
        this.allowSkip = true;
    }

    @NotNull
    public final UUID getInputId() {
        return this.inputId;
    }

    public final void setInputId(@NotNull UUID uUID) {
        Intrinsics.checkNotNullParameter((Object)uUID, (String)"<set-?>");
        this.inputId = uUID;
    }

    @NotNull
    public final InputType getInputType() {
        return this.inputType;
    }

    public final void setInputType(@NotNull InputType inputType) {
        Intrinsics.checkNotNullParameter((Object)((Object)inputType), (String)"<set-?>");
        this.inputType = inputType;
    }

    public final float getDeadline() {
        return this.deadline;
    }

    public final void setDeadline(float f) {
        this.deadline = f;
    }

    public final boolean getShowTimer() {
        return this.showTimer;
    }

    public final void setShowTimer(boolean bl) {
        this.showTimer = bl;
    }

    @NotNull
    public final List<DialogueOptionDTO> getOptions() {
        return this.options;
    }

    public final void setOptions(@NotNull List<DialogueOptionDTO> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.options = list;
    }

    public final boolean getVertical() {
        return this.vertical;
    }

    public final void setVertical(boolean bl) {
        this.vertical = bl;
    }

    public final boolean getAllowSkip() {
        return this.allowSkip;
    }

    public final void setAllowSkip(boolean bl) {
        this.allowSkip = bl;
    }

    /*
     * WARNING - void declaration
     */
    public DialogueInputDTO(@NotNull DialogueOptionSetInput optionSet, @NotNull ActiveDialogue activeDialogue) {
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Intrinsics.checkNotNullParameter((Object)optionSet, (String)"optionSet");
        Intrinsics.checkNotNullParameter((Object)activeDialogue, (String)"activeDialogue");
        this();
        UUID uUID = activeDialogue.getActiveInput().getInputId();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"activeDialogue.activeInput.inputId");
        this.inputId = uUID;
        Iterable iterable = optionSet.getVisibleOptions(activeDialogue);
        DialogueInputDTO dialogueInputDTO = this;
        boolean $i$f$map = false;
        void var5_6 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            DialogueOption dialogueOption = (DialogueOption)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(new DialogueOptionDTO(it.getText().invoke(activeDialogue), it.getValue(), it.isSelectable().invoke(activeDialogue)));
        }
        dialogueInputDTO.options = CollectionsKt.toMutableList((Collection)((List)destination$iv$iv));
        DialogueTimeout dialogueTimeout = optionSet.getTimeout();
        this.deadline = dialogueTimeout != null ? dialogueTimeout.getDuration() : -1.0f;
        DialogueTimeout dialogueTimeout2 = optionSet.getTimeout();
        this.showTimer = dialogueTimeout2 != null ? dialogueTimeout2.getShowTimer() : true;
        this.inputType = InputType.OPTION;
        this.vertical = optionSet.getVertical();
    }

    public DialogueInputDTO(@NotNull DialogueAutoContinueInput autoContinue, @NotNull ActiveDialogue activeDialogue) {
        Intrinsics.checkNotNullParameter((Object)autoContinue, (String)"autoContinue");
        Intrinsics.checkNotNullParameter((Object)activeDialogue, (String)"activeDialogue");
        this();
        UUID uUID = activeDialogue.getActiveInput().getInputId();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"activeDialogue.activeInput.inputId");
        this.inputId = uUID;
        DialogueTimeout dialogueTimeout = autoContinue.getTimeout();
        this.deadline = dialogueTimeout != null ? dialogueTimeout.getDuration() : -1.0f;
        this.inputType = InputType.AUTO_CONTINUE;
        this.allowSkip = autoContinue.getAllowSkip();
        this.showTimer = autoContinue.getShowTimer();
    }

    public DialogueInputDTO(@NotNull DialogueTextInput text, @NotNull ActiveDialogue activeDialogue) {
        Intrinsics.checkNotNullParameter((Object)text, (String)"text");
        Intrinsics.checkNotNullParameter((Object)activeDialogue, (String)"activeDialogue");
        this();
        UUID uUID = activeDialogue.getActiveInput().getInputId();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"activeDialogue.activeInput.inputId");
        this.inputId = uUID;
        DialogueTimeout dialogueTimeout = text.getTimeout();
        this.deadline = dialogueTimeout != null ? dialogueTimeout.getDuration() : -1.0f;
        this.inputType = InputType.TEXT;
        DialogueTimeout dialogueTimeout2 = text.getTimeout();
        this.showTimer = dialogueTimeout2 != null ? dialogueTimeout2.getShowTimer() : true;
    }

    public DialogueInputDTO(@NotNull ActiveDialogue activeDialogue) {
        Intrinsics.checkNotNullParameter((Object)activeDialogue, (String)"activeDialogue");
        this();
        UUID uUID = activeDialogue.getActiveInput().getInputId();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"activeDialogue.activeInput.inputId");
        this.inputId = uUID;
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130077_(this.inputId);
        buffer.m_130068_((Enum)this.inputType);
        buffer.writeFloat(this.deadline);
        buffer.writeBoolean(this.showTimer);
        switch (WhenMappings.$EnumSwitchMapping$0[this.inputType.ordinal()]) {
            case 1: {
                buffer.writeBoolean(this.vertical);
                buffer.writeInt(this.options.size());
                Iterable $this$forEach$iv = this.options;
                boolean $i$f$forEach = false;
                for (Object element$iv : $this$forEach$iv) {
                    DialogueOptionDTO it = (DialogueOptionDTO)element$iv;
                    boolean bl = false;
                    it.encode(buffer);
                }
                break;
            }
            case 2: {
                buffer.writeBoolean(this.allowSkip);
                break;
            }
        }
    }

    @Override
    public void decode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        UUID uUID = buffer.m_130259_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"buffer.readUuid()");
        this.inputId = uUID;
        Enum enum_ = buffer.m_130066_(InputType.class);
        Intrinsics.checkNotNullExpressionValue((Object)enum_, (String)"buffer.readEnumConstant(InputType::class.java)");
        this.inputType = (InputType)enum_;
        this.deadline = buffer.readFloat();
        this.showTimer = buffer.readBoolean();
        switch (WhenMappings.$EnumSwitchMapping$0[this.inputType.ordinal()]) {
            case 1: {
                this.vertical = buffer.readBoolean();
                int size = buffer.readInt();
                for (int i = 0; i < size; ++i) {
                    DialogueOptionDTO option = new DialogueOptionDTO(null, null, false, 7, null);
                    option.decode(buffer);
                    this.options.add(option);
                }
                break;
            }
            case 2: {
                this.allowSkip = buffer.readBoolean();
                break;
            }
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007\u00a8\u0006\b"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueInputDTO$InputType;", "", "<init>", "(Ljava/lang/String;I)V", "OPTION", "TEXT", "AUTO_CONTINUE", "NONE", "common"})
    public static final class InputType
    extends Enum<InputType> {
        public static final /* enum */ InputType OPTION = new InputType();
        public static final /* enum */ InputType TEXT = new InputType();
        public static final /* enum */ InputType AUTO_CONTINUE = new InputType();
        public static final /* enum */ InputType NONE = new InputType();
        private static final /* synthetic */ InputType[] $VALUES;

        public static InputType[] values() {
            return (InputType[])$VALUES.clone();
        }

        public static InputType valueOf(String value2) {
            return Enum.valueOf(InputType.class, value2);
        }

        static {
            $VALUES = inputTypeArray = new InputType[]{InputType.OPTION, InputType.TEXT, InputType.AUTO_CONTINUE, InputType.NONE};
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[InputType.values().length];
            try {
                nArray[InputType.OPTION.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[InputType.AUTO_CONTINUE.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

