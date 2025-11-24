/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.dto;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Decodable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Encodable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\t\u0018\u00002\u00020\u00012\u00020\u0002B%\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0010\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0017\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u0017\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0017\u0010\b\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\b\u0010\u0007R\"\u0010\n\u001a\u00020\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\"\u0010\u0011\u001a\u00020\u00108\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\"\u0010\u0018\u001a\u00020\u00178\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001d\u00a8\u0006 "}, d2={"Lcom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueOptionDTO;", "Lcom/cobblemon/mod/common/api/net/Encodable;", "Lcom/cobblemon/mod/common/api/net/Decodable;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "encode", "", "selectable", "Z", "getSelectable", "()Z", "setSelectable", "(Z)V", "Lnet/minecraft/network/chat/MutableComponent;", "text", "Lnet/minecraft/network/chat/MutableComponent;", "getText", "()Lnet/minecraft/network/chat/MutableComponent;", "setText", "(Lnet/minecraft/network/chat/MutableComponent;)V", "", "value", "Ljava/lang/String;", "getValue", "()Ljava/lang/String;", "setValue", "(Ljava/lang/String;)V", "<init>", "(Lnet/minecraft/network/chat/MutableComponent;Ljava/lang/String;Z)V", "common"})
public final class DialogueOptionDTO
implements Encodable,
Decodable {
    @NotNull
    private MutableComponent text;
    @NotNull
    private String value;
    private boolean selectable;

    public DialogueOptionDTO(@NotNull MutableComponent text, @NotNull String value2, boolean selectable) {
        Intrinsics.checkNotNullParameter((Object)text, (String)"text");
        Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
        this.text = text;
        this.value = value2;
        this.selectable = selectable;
    }

    public /* synthetic */ DialogueOptionDTO(MutableComponent mutableComponent, String string, boolean bl, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            mutableComponent = TextKt.text("");
        }
        if ((n & 2) != 0) {
            string = "";
        }
        if ((n & 4) != 0) {
            bl = true;
        }
        this(mutableComponent, string, bl);
    }

    @NotNull
    public final MutableComponent getText() {
        return this.text;
    }

    public final void setText(@NotNull MutableComponent mutableComponent) {
        Intrinsics.checkNotNullParameter((Object)mutableComponent, (String)"<set-?>");
        this.text = mutableComponent;
    }

    @NotNull
    public final String getValue() {
        return this.value;
    }

    public final void setValue(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.value = string;
    }

    public final boolean getSelectable() {
        return this.selectable;
    }

    public final void setSelectable(boolean bl) {
        this.selectable = bl;
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130083_((Component)this.text);
        buffer.m_130070_(this.value);
        buffer.writeBoolean(this.selectable);
    }

    @Override
    public void decode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        MutableComponent mutableComponent = buffer.m_130238_().m_6881_();
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"buffer.readText().copy()");
        this.text = mutableComponent;
        String string = buffer.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"buffer.readString()");
        this.value = string;
        this.selectable = buffer.readBoolean();
    }

    public DialogueOptionDTO() {
        this(null, null, false, 7, null);
    }
}

