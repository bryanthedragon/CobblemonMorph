/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.IntIterator
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.RangesKt
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.FriendlyByteBuf$Reader
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.dto;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ActiveDialogue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ArtificialDialogueFaceProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueFaceProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueSpeaker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueText;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ExpressionLikeDialogueFaceProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.PlayerDialogueFaceProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ReferenceDialogueFaceProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueAutoContinueInput;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueInput;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueOptionSetInput;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueTextInput;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Decodable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Encodable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.dto.DialogueInputDTO;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.dto.DialoguePageDTO;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.dto.DialogueSpeakerDTO;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import java.lang.invoke.LambdaMetafactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\t\b\u0016\u00a2\u0006\u0004\b'\u0010(B\u0019\b\u0016\u0012\u0006\u0010*\u001a\u00020)\u0012\u0006\u0010,\u001a\u00020+\u00a2\u0006\u0004\b'\u0010-J\u0017\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0017\u0010\b\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\b\u0010\u0007R\"\u0010\n\u001a\u00020\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\"\u0010\u0011\u001a\u00020\u00108\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\"\u0010\u0018\u001a\u00020\u00178\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR0\u0010!\u001a\u0010\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020 \u0018\u00010\u001e8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&\u00a8\u0006."}, d2={"Lcom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueDTO;", "Lcom/cobblemon/mod/common/api/net/Encodable;", "Lcom/cobblemon/mod/common/api/net/Decodable;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "encode", "Lcom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialoguePageDTO;", "currentPageDTO", "Lcom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialoguePageDTO;", "getCurrentPageDTO", "()Lcom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialoguePageDTO;", "setCurrentPageDTO", "(Lcom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialoguePageDTO;)V", "Ljava/util/UUID;", "dialogueId", "Ljava/util/UUID;", "getDialogueId", "()Ljava/util/UUID;", "setDialogueId", "(Ljava/util/UUID;)V", "Lcom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueInputDTO;", "dialogueInput", "Lcom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueInputDTO;", "getDialogueInput", "()Lcom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueInputDTO;", "setDialogueInput", "(Lcom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueInputDTO;)V", "", "", "Lcom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueSpeakerDTO;", "speakers", "Ljava/util/Map;", "getSpeakers", "()Ljava/util/Map;", "setSpeakers", "(Ljava/util/Map;)V", "<init>", "()V", "Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;", "activeDialogue", "", "includeFaces", "(Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;Z)V", "common"})
@SourceDebugExtension(value={"SMAP\nDialogueDTO.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DialogueDTO.kt\ncom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueDTO\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,117:1\n135#2,9:118\n215#2:127\n216#2:129\n144#2:130\n215#2,2:131\n1#3:128\n1179#4,2:133\n1253#4,4:135\n*S KotlinDebug\n*F\n+ 1 DialogueDTO.kt\ncom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueDTO\n*L\n37#1:118,9\n37#1:127\n37#1:129\n37#1:130\n65#1:131,2\n37#1:128\n99#1:133,2\n99#1:135,4\n*E\n"})
public final class DialogueDTO
implements Encodable,
Decodable {
    public UUID dialogueId;
    @Nullable
    private Map<String, DialogueSpeakerDTO> speakers;
    public DialoguePageDTO currentPageDTO;
    public DialogueInputDTO dialogueInput;

    @NotNull
    public final UUID getDialogueId() {
        UUID uUID = this.dialogueId;
        if (uUID != null) {
            return uUID;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"dialogueId");
        return null;
    }

    public final void setDialogueId(@NotNull UUID uUID) {
        Intrinsics.checkNotNullParameter((Object)uUID, (String)"<set-?>");
        this.dialogueId = uUID;
    }

    @Nullable
    public final Map<String, DialogueSpeakerDTO> getSpeakers() {
        return this.speakers;
    }

    public final void setSpeakers(@Nullable Map<String, DialogueSpeakerDTO> map) {
        this.speakers = map;
    }

    @NotNull
    public final DialoguePageDTO getCurrentPageDTO() {
        DialoguePageDTO dialoguePageDTO = this.currentPageDTO;
        if (dialoguePageDTO != null) {
            return dialoguePageDTO;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"currentPageDTO");
        return null;
    }

    public final void setCurrentPageDTO(@NotNull DialoguePageDTO dialoguePageDTO) {
        Intrinsics.checkNotNullParameter((Object)dialoguePageDTO, (String)"<set-?>");
        this.currentPageDTO = dialoguePageDTO;
    }

    @NotNull
    public final DialogueInputDTO getDialogueInput() {
        DialogueInputDTO dialogueInputDTO = this.dialogueInput;
        if (dialogueInputDTO != null) {
            return dialogueInputDTO;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"dialogueInput");
        return null;
    }

    public final void setDialogueInput(@NotNull DialogueInputDTO dialogueInputDTO) {
        Intrinsics.checkNotNullParameter((Object)dialogueInputDTO, (String)"<set-?>");
        this.dialogueInput = dialogueInputDTO;
    }

    public DialogueDTO() {
    }

    /*
     * WARNING - void declaration
     */
    public DialogueDTO(@NotNull ActiveDialogue activeDialogue, boolean includeFaces) {
        DialogueInput input;
        Map map;
        Intrinsics.checkNotNullParameter((Object)activeDialogue, (String)"activeDialogue");
        UUID uUID = activeDialogue.getDialogueId();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"activeDialogue.dialogueId");
        this.setDialogueId(uUID);
        DialogueDTO dialogueDTO = this;
        if (includeFaces) {
            void $this$mapNotNullTo$iv$iv;
            void $this$mapNotNull$iv;
            Map<String, DialogueSpeaker> map2 = activeDialogue.getDialogueReference().getSpeakers();
            DialogueDTO dialogueDTO2 = dialogueDTO;
            boolean $i$f$mapNotNull = false;
            void var5_7 = $this$mapNotNull$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$mapNotNullTo = false;
            void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
            boolean $i$f$forEach = false;
            Iterator iterator = $this$forEach$iv$iv$iv.entrySet().iterator();
            while (iterator.hasNext()) {
                Pair pair;
                Map.Entry element$iv$iv$iv;
                Map.Entry element$iv$iv = element$iv$iv$iv = iterator.next();
                boolean bl = false;
                Map.Entry entry = element$iv$iv;
                boolean bl2 = false;
                String key = (String)entry.getKey();
                DialogueSpeaker value2 = (DialogueSpeaker)entry.getValue();
                if (value2.getFace() instanceof ExpressionLikeDialogueFaceProvider) {
                    MoValue resolved = MoLangExtensionsKt.resolve(activeDialogue.getRuntime(), ((ExpressionLikeDialogueFaceProvider)value2.getFace()).getProviderExpression());
                    if (resolved instanceof ObjectValue && ((ObjectValue)resolved).getObj() instanceof DialogueFaceProvider) {
                        DialogueText dialogueText = value2.getName();
                        Object object = dialogueText != null ? dialogueText.invoke(activeDialogue) : null;
                        Object t = ((ObjectValue)resolved).getObj();
                        Intrinsics.checkNotNull(t, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueFaceProvider");
                        pair = TuplesKt.to((Object)key, (Object)new DialogueSpeakerDTO((MutableComponent)object, (DialogueFaceProvider)t));
                    } else {
                        pair = null;
                    }
                } else {
                    DialogueText dialogueText = value2.getName();
                    pair = TuplesKt.to((Object)key, (Object)new DialogueSpeakerDTO((MutableComponent)(dialogueText != null ? dialogueText.invoke(activeDialogue) : null), value2.getFace()));
                }
                if (pair == null) continue;
                Pair it$iv$iv = pair;
                boolean bl3 = false;
                destination$iv$iv.add(it$iv$iv);
            }
            dialogueDTO = dialogueDTO2;
            map = MapsKt.toMap((Iterable)((List)destination$iv$iv));
        } else {
            map = null;
        }
        dialogueDTO.speakers = map;
        this.setCurrentPageDTO(new DialoguePageDTO(activeDialogue.getCurrentPage(), activeDialogue));
        DialogueInput dialogueInput = input = activeDialogue.getActiveInput().getDialogueInput();
        this.setDialogueInput(dialogueInput instanceof DialogueOptionSetInput ? new DialogueInputDTO((DialogueOptionSetInput)input, activeDialogue) : (dialogueInput instanceof DialogueAutoContinueInput ? new DialogueInputDTO((DialogueAutoContinueInput)input, activeDialogue) : (dialogueInput instanceof DialogueTextInput ? new DialogueInputDTO((DialogueTextInput)input, activeDialogue) : new DialogueInputDTO(activeDialogue))));
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130077_(this.getDialogueId());
        this.getCurrentPageDTO().encode(buffer);
        this.getDialogueInput().encode(buffer);
        buffer.m_236821_(this.speakers, (arg_0, arg_1) -> DialogueDTO.encode$lambda$5(buffer, arg_0, arg_1));
    }

    @Override
    public void decode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        UUID uUID = buffer.m_130259_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"buffer.readUuid()");
        this.setDialogueId(uUID);
        this.setCurrentPageDTO(new DialoguePageDTO());
        this.getCurrentPageDTO().decode(buffer);
        this.setDialogueInput(new DialogueInputDTO());
        this.getDialogueInput().decode(buffer);
        this.speakers = (Map)buffer.m_236868_(arg_0 -> DialogueDTO.decode$lambda$10(buffer, arg_0));
    }

    private static final void encode$lambda$5$lambda$4$lambda$1(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, MutableComponent v) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        $buffer.m_130083_((Component)v);
    }

    private static final void encode$lambda$5$lambda$4$lambda$2(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, DialogueFaceProvider v) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        DialogueFaceProvider dialogueFaceProvider = v;
        $buffer.m_130070_(dialogueFaceProvider instanceof ReferenceDialogueFaceProvider ? "reference" : (dialogueFaceProvider instanceof ArtificialDialogueFaceProvider ? "artificial" : "player"));
    }

    private static final void encode$lambda$5$lambda$4$lambda$3(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, String aspect) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        $buffer.m_130070_(aspect);
    }

    private static final void encode$lambda$5(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, Map speakers) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        $buffer.writeInt(speakers.size());
        Intrinsics.checkNotNullExpressionValue((Object)speakers, (String)"speakers");
        Map $this$forEach$iv = speakers;
        boolean $i$f$forEach = false;
        Iterator iterator = $this$forEach$iv.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry element$iv;
            Map.Entry entry = element$iv = iterator.next();
            boolean bl = false;
            String key = (String)entry.getKey();
            DialogueSpeakerDTO value2 = (DialogueSpeakerDTO)entry.getValue();
            $buffer.m_130070_(key);
            $buffer.m_236821_((Object)value2.getName(), (arg_0, arg_1) -> DialogueDTO.encode$lambda$5$lambda$4$lambda$1($buffer, arg_0, arg_1));
            $buffer.m_236821_((Object)value2.getFace(), (arg_0, arg_1) -> DialogueDTO.encode$lambda$5$lambda$4$lambda$2($buffer, arg_0, arg_1));
            if (value2.getFace() instanceof ArtificialDialogueFaceProvider) {
                $buffer.m_130070_(((ArtificialDialogueFaceProvider)value2.getFace()).getModelType());
                $buffer.m_130085_(((ArtificialDialogueFaceProvider)value2.getFace()).getIdentifier());
                $buffer.m_236828_((Collection)((ArtificialDialogueFaceProvider)value2.getFace()).getAspects(), (arg_0, arg_1) -> DialogueDTO.encode$lambda$5$lambda$4$lambda$3($buffer, arg_0, arg_1));
                continue;
            }
            if (value2.getFace() instanceof ReferenceDialogueFaceProvider) {
                $buffer.writeInt(((ReferenceDialogueFaceProvider)value2.getFace()).getEntityId());
                continue;
            }
            if (!(value2.getFace() instanceof PlayerDialogueFaceProvider)) continue;
            $buffer.m_130077_(((PlayerDialogueFaceProvider)value2.getFace()).getPlayerId());
        }
    }

    private static final MutableComponent decode$lambda$10$lambda$9$lambda$6(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        return $buffer.m_130238_().m_6881_();
    }

    private static final String decode$lambda$10$lambda$9$lambda$7(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        return $buffer.m_130277_();
    }

    private static final String decode$lambda$10$lambda$9$lambda$8(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        return $buffer.m_130277_();
    }

    /*
     * Unable to fully structure code
     */
    private static final Map decode$lambda$10(FriendlyByteBuf $buffer, FriendlyByteBuf var1_1) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        size = $buffer.readInt();
        $this$associate$iv = (Iterable)RangesKt.until((int)0, (int)size);
        $i$f$associate = false;
        capacity$iv = RangesKt.coerceAtLeast((int)MapsKt.mapCapacity((int)CollectionsKt.collectionSizeOrDefault((Iterable)$this$associate$iv, (int)10)), (int)16);
        var6_6 = $this$associate$iv;
        destination$iv$iv = new LinkedHashMap<K, V>(capacity$iv);
        $i$f$associateTo = false;
        var9_9 = $this$associateTo$iv$iv.iterator();
        while (var9_9.hasNext()) {
            element$iv$iv = ((IntIterator)var9_9).nextInt();
            var11_11 = destination$iv$iv;
            it = element$iv$iv;
            $i$a$-associate-DialogueDTO$decode$1$1 = false;
            key = $buffer.m_130277_();
            name = (MutableComponent)$buffer.m_236868_((FriendlyByteBuf.Reader)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, decode$lambda$10$lambda$9$lambda$6(net.minecraft.network.FriendlyByteBuf net.minecraft.network.FriendlyByteBuf ), (Lnet/minecraft/network/FriendlyByteBuf;)Lnet/minecraft/network/chat/MutableComponent;)((FriendlyByteBuf)$buffer));
            faceType = (String)$buffer.m_236868_((FriendlyByteBuf.Reader)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, decode$lambda$10$lambda$9$lambda$7(net.minecraft.network.FriendlyByteBuf net.minecraft.network.FriendlyByteBuf ), (Lnet/minecraft/network/FriendlyByteBuf;)Ljava/lang/String;)((FriendlyByteBuf)$buffer));
            var17_18 = faceType;
            if (var17_18 == null) ** GOTO lbl-1000
            tmp = -1;
            switch (var17_18.hashCode()) {
                case -925155509: {
                    if (var17_18.equals("reference")) {
                        tmp = 1;
                    }
                    break;
                }
                case 248019002: {
                    if (var17_18.equals("artificial")) {
                        tmp = 2;
                    }
                    break;
                }
                case -985752863: {
                    if (var17_18.equals("player")) {
                        tmp = 3;
                    }
                    break;
                }
            }
            switch (tmp) {
                case 1: {
                    v0 = TuplesKt.to((Object)key, (Object)new DialogueSpeakerDTO(name, new ReferenceDialogueFaceProvider($buffer.readInt())));
                    break;
                }
                case 2: {
                    modelType = $buffer.m_130277_();
                    identifier = $buffer.m_130281_();
                    v1 = $buffer.m_236845_((FriendlyByteBuf.Reader)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, decode$lambda$10$lambda$9$lambda$8(net.minecraft.network.FriendlyByteBuf net.minecraft.network.FriendlyByteBuf ), (Lnet/minecraft/network/FriendlyByteBuf;)Ljava/lang/String;)((FriendlyByteBuf)$buffer));
                    Intrinsics.checkNotNullExpressionValue((Object)v1, (String)"buffer.readList { buffer.readString() }");
                    aspects = CollectionsKt.toSet((Iterable)v1);
                    Intrinsics.checkNotNullExpressionValue((Object)modelType, (String)"modelType");
                    Intrinsics.checkNotNullExpressionValue((Object)identifier, (String)"identifier");
                    v0 = TuplesKt.to((Object)key, (Object)new DialogueSpeakerDTO(name, new ArtificialDialogueFaceProvider(modelType, identifier, aspects)));
                    break;
                }
                case 3: {
                    v2 = $buffer.m_130259_();
                    Intrinsics.checkNotNullExpressionValue((Object)v2, (String)"buffer.readUuid()");
                    v0 = TuplesKt.to((Object)key, (Object)new DialogueSpeakerDTO(name, new PlayerDialogueFaceProvider(v2)));
                    break;
                }
                default: lbl-1000:
                // 2 sources

                {
                    v0 = TuplesKt.to((Object)key, (Object)new DialogueSpeakerDTO(name, null));
                }
            }
            var12_12 = v0;
            var11_11.put(var12_12.getFirst(), var12_12.getSecond());
        }
        return destination$iv$iv;
    }
}

