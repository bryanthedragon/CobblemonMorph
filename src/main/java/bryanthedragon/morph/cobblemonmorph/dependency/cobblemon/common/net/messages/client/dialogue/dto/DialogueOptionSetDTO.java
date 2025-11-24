/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.IntIterator
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.RangesKt
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.dto;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Decodable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Encodable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.dto.DialogueOptionDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u00012\u00020\u0002B!\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u0012\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0017\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0017\u0010\b\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\b\u0010\u0007R\"\u0010\n\u001a\u00020\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR(\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u00108\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueOptionSetDTO;", "Lcom/cobblemon/mod/common/api/net/Encodable;", "Lcom/cobblemon/mod/common/api/net/Decodable;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "encode", "", "deadline", "F", "getDeadline", "()F", "setDeadline", "(F)V", "", "Lcom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueOptionDTO;", "options", "Ljava/util/List;", "getOptions", "()Ljava/util/List;", "setOptions", "(Ljava/util/List;)V", "<init>", "(FLjava/util/List;)V", "common"})
@SourceDebugExtension(value={"SMAP\nDialogueOptionSetDTO.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DialogueOptionSetDTO.kt\ncom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueOptionSetDTO\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,32:1\n1855#2,2:33\n1549#2:35\n1620#2,2:36\n1622#2:39\n1#3:38\n*S KotlinDebug\n*F\n+ 1 DialogueOptionSetDTO.kt\ncom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueOptionSetDTO\n*L\n22#1:33,2\n30#1:35\n30#1:36,2\n30#1:39\n*E\n"})
public final class DialogueOptionSetDTO
implements Encodable,
Decodable {
    private float deadline;
    @NotNull
    private List<DialogueOptionDTO> options;

    public DialogueOptionSetDTO(float deadline, @NotNull List<DialogueOptionDTO> options) {
        Intrinsics.checkNotNullParameter(options, (String)"options");
        this.deadline = deadline;
        this.options = options;
    }

    public /* synthetic */ DialogueOptionSetDTO(float f, List list, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            f = 0.0f;
        }
        if ((n & 2) != 0) {
            list = CollectionsKt.emptyList();
        }
        this(f, list);
    }

    public final float getDeadline() {
        return this.deadline;
    }

    public final void setDeadline(float f) {
        this.deadline = f;
    }

    @NotNull
    public final List<DialogueOptionDTO> getOptions() {
        return this.options;
    }

    public final void setOptions(@NotNull List<DialogueOptionDTO> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.options = list;
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.writeFloat(this.deadline);
        buffer.writeInt(this.options.size());
        Iterable $this$forEach$iv = this.options;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            DialogueOptionDTO option = (DialogueOptionDTO)element$iv;
            boolean bl = false;
            option.encode(buffer);
        }
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void decode(@NotNull FriendlyByteBuf buffer) {
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        this.deadline = buffer.readFloat();
        int size = buffer.readInt();
        Iterable iterable = (Iterable)RangesKt.until((int)0, (int)size);
        DialogueOptionSetDTO dialogueOptionSetDTO = this;
        boolean $i$f$map = false;
        void var5_6 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        Iterator iterator = $this$mapTo$iv$iv.iterator();
        while (iterator.hasNext()) {
            DialogueOptionDTO dialogueOptionDTO;
            int item$iv$iv;
            int n = item$iv$iv = ((IntIterator)iterator).nextInt();
            Collection collection = destination$iv$iv;
            boolean bl = false;
            DialogueOptionDTO $this$decode_u24lambda_u242_u24lambda_u241 = dialogueOptionDTO = new DialogueOptionDTO(null, null, false, 7, null);
            boolean bl2 = false;
            $this$decode_u24lambda_u242_u24lambda_u241.decode(buffer);
            collection.add(dialogueOptionDTO);
        }
        dialogueOptionSetDTO.options = (List)destination$iv$iv;
    }

    public DialogueOptionSetDTO() {
        this(0.0f, null, 3, null);
    }
}

