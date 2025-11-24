/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionResponseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownMoveset;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt;
import io.netty.buffer.ByteBuf;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b&\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\u000f\u0012\u0006\u0010\u0015\u001a\u00020\u0014\u00a2\u0006\u0004\b\u0019\u0010\u001aJ)\u0010\b\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0007\u001a\u00020\u0006H&\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\f\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\u0017\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J!\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H&\u00a2\u0006\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0015\u001a\u00020\u00148\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/battles/ShowdownActionResponse;", "", "Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;", "activeBattlePokemon", "Lcom/cobblemon/mod/common/battles/ShowdownMoveset;", "showdownMoveSet", "", "forceSwitch", "isValid", "(Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;Lcom/cobblemon/mod/common/battles/ShowdownMoveset;Z)Z", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "loadFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/battles/ShowdownActionResponse;", "", "saveToBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "", "toShowdownString", "(Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;Lcom/cobblemon/mod/common/battles/ShowdownMoveset;)Ljava/lang/String;", "Lcom/cobblemon/mod/common/battles/ShowdownActionResponseType;", "type", "Lcom/cobblemon/mod/common/battles/ShowdownActionResponseType;", "getType", "()Lcom/cobblemon/mod/common/battles/ShowdownActionResponseType;", "<init>", "(Lcom/cobblemon/mod/common/battles/ShowdownActionResponseType;)V", "Companion", "common"})
public abstract class ShowdownActionResponse {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final ShowdownActionResponseType type;

    public ShowdownActionResponse(@NotNull ShowdownActionResponseType type) {
        Intrinsics.checkNotNullParameter((Object)((Object)type), (String)"type");
        this.type = type;
    }

    @NotNull
    public final ShowdownActionResponseType getType() {
        return this.type;
    }

    public void saveToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, this.type.ordinal());
    }

    @NotNull
    public ShowdownActionResponse loadFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        return this;
    }

    public abstract boolean isValid(@NotNull ActiveBattlePokemon var1, @Nullable ShowdownMoveset var2, boolean var3);

    @NotNull
    public abstract String toShowdownString(@NotNull ActiveBattlePokemon var1, @Nullable ShowdownMoveset var2);

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/battles/ShowdownActionResponse$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/battles/ShowdownActionResponse;", "loadFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/battles/ShowdownActionResponse;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ShowdownActionResponse loadFromBuffer(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            ShowdownActionResponseType type = ShowdownActionResponseType.values()[NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_BYTE)];
            return ((ShowdownActionResponse)type.getLoader().invoke((Object)buffer)).loadFromBuffer(buffer);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

