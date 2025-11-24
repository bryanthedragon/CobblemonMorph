/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.dto;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ActiveDialogue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialoguePage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueText;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Decodable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Encodable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\t\b\u0016\u00a2\u0006\u0004\b\u001b\u0010\u001cB\u0019\b\u0016\u0012\u0006\u0010\u001e\u001a\u00020\u001d\u0012\u0006\u0010 \u001a\u00020\u001f\u00a2\u0006\u0004\b\u001b\u0010!J\u0017\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0017\u0010\b\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\b\u0010\u0007R(\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R(\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0012\u0010\f\u001a\u0004\b\u0013\u0010\u000e\"\u0004\b\u0014\u0010\u0010R$\u0010\u0015\u001a\u0004\u0018\u00010\n8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a\u00a8\u0006\""}, d2={"Lcom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialoguePageDTO;", "Lcom/cobblemon/mod/common/api/net/Encodable;", "Lcom/cobblemon/mod/common/api/net/Decodable;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "encode", "", "", "clientActions", "Ljava/util/List;", "getClientActions", "()Ljava/util/List;", "setClientActions", "(Ljava/util/List;)V", "Lnet/minecraft/network/chat/MutableComponent;", "lines", "getLines", "setLines", "speaker", "Ljava/lang/String;", "getSpeaker", "()Ljava/lang/String;", "setSpeaker", "(Ljava/lang/String;)V", "<init>", "()V", "Lcom/cobblemon/mod/common/api/dialogue/DialoguePage;", "dialoguePage", "Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;", "activeDialogue", "(Lcom/cobblemon/mod/common/api/dialogue/DialoguePage;Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;)V", "common"})
@SourceDebugExtension(value={"SMAP\nDialoguePageDTO.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DialoguePageDTO.kt\ncom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialoguePageDTO\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,47:1\n1549#2:48\n1620#2,3:49\n1549#2:52\n1620#2,3:53\n1855#2,2:56\n*S KotlinDebug\n*F\n+ 1 DialoguePageDTO.kt\ncom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialoguePageDTO\n*L\n28#1:48\n28#1:49,3\n29#1:52\n29#1:53,3\n36#1:56,2\n*E\n"})
public final class DialoguePageDTO
implements Encodable,
Decodable {
    @Nullable
    private String speaker;
    @NotNull
    private List<MutableComponent> lines;
    @NotNull
    private List<String> clientActions;

    @Nullable
    public final String getSpeaker() {
        return this.speaker;
    }

    public final void setSpeaker(@Nullable String string) {
        this.speaker = string;
    }

    @NotNull
    public final List<MutableComponent> getLines() {
        return this.lines;
    }

    public final void setLines(@NotNull List<MutableComponent> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.lines = list;
    }

    @NotNull
    public final List<String> getClientActions() {
        return this.clientActions;
    }

    public final void setClientActions(@NotNull List<String> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.clientActions = list;
    }

    public DialoguePageDTO() {
        this.lines = new ArrayList();
        this.clientActions = new ArrayList();
    }

    public DialoguePageDTO(@NotNull DialoguePage dialoguePage, @NotNull ActiveDialogue activeDialogue) {
        Expression it;
        Collection collection;
        Iterable $this$mapTo$iv$iv;
        Iterable $this$map$iv;
        Intrinsics.checkNotNullParameter((Object)dialoguePage, (String)"dialoguePage");
        Intrinsics.checkNotNullParameter((Object)activeDialogue, (String)"activeDialogue");
        this.lines = new ArrayList();
        this.clientActions = new ArrayList();
        this.speaker = dialoguePage.getSpeaker();
        Iterable iterable = dialoguePage.getLines();
        DialoguePageDTO dialoguePageDTO = this;
        boolean $i$f$map = false;
        void var5_6 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            DialogueText dialogueText = (DialogueText)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            collection.add(it.invoke(activeDialogue));
        }
        dialoguePageDTO.lines = CollectionsKt.toMutableList((Collection)((List)destination$iv$iv));
        $this$map$iv = dialoguePage.getClientActions();
        dialoguePageDTO = this;
        $i$f$map = false;
        $this$mapTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            it = (Expression)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            collection.add(it.getOriginalString());
        }
        dialoguePageDTO.clientActions = CollectionsKt.toMutableList((Collection)((List)destination$iv$iv));
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_236821_((Object)this.speaker, (arg_0, arg_1) -> DialoguePageDTO.encode$lambda$2(buffer, arg_0, arg_1));
        buffer.m_236828_((Collection)this.lines, (arg_0, arg_1) -> DialoguePageDTO.encode$lambda$3(buffer, arg_0, arg_1));
        buffer.writeInt(this.clientActions.size());
        Iterable $this$forEach$iv = this.clientActions;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            String it = (String)element$iv;
            boolean bl = false;
            buffer.m_130070_(it);
        }
    }

    @Override
    public void decode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        this.speaker = (String)buffer.m_236868_(arg_0 -> DialoguePageDTO.decode$lambda$5(buffer, arg_0));
        List list = buffer.m_236845_(DialoguePageDTO::decode$lambda$6);
        Intrinsics.checkNotNullExpressionValue((Object)list, (String)"buffer.readList { it.readText().copy() }");
        this.lines = CollectionsKt.toMutableList((Collection)list);
        int clientActionsSize = buffer.readInt();
        for (int i = 0; i < clientActionsSize; ++i) {
            String string = buffer.m_130277_();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"buffer.readString()");
            this.clientActions.add(string);
        }
    }

    private static final void encode$lambda$2(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, String value2) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        $buffer.m_130070_(value2);
    }

    private static final void encode$lambda$3(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, MutableComponent value2) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        $buffer.m_130083_((Component)value2);
    }

    private static final String decode$lambda$5(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        return $buffer.m_130277_();
    }

    private static final MutableComponent decode$lambda$6(FriendlyByteBuf it) {
        return it.m_130238_().m_6881_();
    }
}

