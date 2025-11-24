/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  io.netty.buffer.ByteBuf
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\n\b\u0086\b\u0018\u0000 '2\u00020\u0001:\u0001'B\u0017\u0012\u0006\u0010\b\u001a\u00020\u0002\u0012\u0006\u0010\t\u001a\u00020\u0005\u00a2\u0006\u0004\b%\u0010&J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J$\u0010\n\u001a\u00020\u00002\b\b\u0002\u0010\b\u001a\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\u0005H\u00c6\u0001\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001a\u0010\u000e\u001a\u00020\r2\b\u0010\f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0010\u0010\u0010\u001a\u00020\u0005H\u00d6\u0001\u00a2\u0006\u0004\b\u0010\u0010\u0007J\u0015\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0015\u0010\u0018\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0015\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001a\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u0010\u0010\u001f\u001a\u00020\u001eH\u00d6\u0001\u00a2\u0006\u0004\b\u001f\u0010 R\u0017\u0010\b\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\b\u0010!\u001a\u0004\b\"\u0010\u0004R\u0017\u0010\t\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\t\u0010#\u001a\u0004\b$\u0010\u0007\u00a8\u0006("}, d2={"Lcom/cobblemon/mod/common/api/moves/BenchedMove;", "", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "component1", "()Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "", "component2", "()I", "moveTemplate", "ppRaisedStages", "copy", "(Lcom/cobblemon/mod/common/api/moves/MoveTemplate;I)Lcom/cobblemon/mod/common/api/moves/BenchedMove;", "other", "", "equals", "(Ljava/lang/Object;)Z", "hashCode", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "saveToBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "Lcom/google/gson/JsonObject;", "json", "saveToJSON", "(Lcom/google/gson/JsonObject;)Lcom/google/gson/JsonObject;", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "saveToNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/nbt/CompoundTag;", "", "toString", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "getMoveTemplate", "I", "getPpRaisedStages", "<init>", "(Lcom/cobblemon/mod/common/api/moves/MoveTemplate;I)V", "Companion", "common"})
public final class BenchedMove {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final MoveTemplate moveTemplate;
    private final int ppRaisedStages;

    public BenchedMove(@NotNull MoveTemplate moveTemplate, int ppRaisedStages) {
        Intrinsics.checkNotNullParameter((Object)moveTemplate, (String)"moveTemplate");
        this.moveTemplate = moveTemplate;
        this.ppRaisedStages = ppRaisedStages;
    }

    @NotNull
    public final MoveTemplate getMoveTemplate() {
        return this.moveTemplate;
    }

    public final int getPpRaisedStages() {
        return this.ppRaisedStages;
    }

    @NotNull
    public final CompoundTag saveToNBT(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        nbt.m_128359_("MoveName", this.moveTemplate.getName());
        nbt.m_128344_("RaisedPPStages", (byte)this.ppRaisedStages);
        return nbt;
    }

    @NotNull
    public final JsonObject saveToJSON(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        json.addProperty("MoveName", this.moveTemplate.getName());
        json.addProperty("RaisedPPStages", (Number)this.ppRaisedStages);
        return json;
    }

    public final void saveToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130070_(this.moveTemplate.getName());
        NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, this.ppRaisedStages);
    }

    @NotNull
    public final MoveTemplate component1() {
        return this.moveTemplate;
    }

    public final int component2() {
        return this.ppRaisedStages;
    }

    @NotNull
    public final BenchedMove copy(@NotNull MoveTemplate moveTemplate, int ppRaisedStages) {
        Intrinsics.checkNotNullParameter((Object)moveTemplate, (String)"moveTemplate");
        return new BenchedMove(moveTemplate, ppRaisedStages);
    }

    public static /* synthetic */ BenchedMove copy$default(BenchedMove benchedMove, MoveTemplate moveTemplate, int n, int n2, Object object) {
        if ((n2 & 1) != 0) {
            moveTemplate = benchedMove.moveTemplate;
        }
        if ((n2 & 2) != 0) {
            n = benchedMove.ppRaisedStages;
        }
        return benchedMove.copy(moveTemplate, n);
    }

    @NotNull
    public String toString() {
        return "BenchedMove(moveTemplate=" + this.moveTemplate + ", ppRaisedStages=" + this.ppRaisedStages + ")";
    }

    public int hashCode() {
        int result = this.moveTemplate.hashCode();
        result = result * 31 + Integer.hashCode(this.ppRaisedStages);
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BenchedMove)) {
            return false;
        }
        BenchedMove benchedMove = (BenchedMove)other;
        if (!Intrinsics.areEqual((Object)this.moveTemplate, (Object)benchedMove.moveTemplate)) {
            return false;
        }
        return this.ppRaisedStages == benchedMove.ppRaisedStages;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\t\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\t\u0010\nJ\u0015\u0010\r\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0004\b\r\u0010\u000e\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/api/moves/BenchedMove$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/api/moves/BenchedMove;", "loadFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/api/moves/BenchedMove;", "Lcom/google/gson/JsonObject;", "json", "loadFromJSON", "(Lcom/google/gson/JsonObject;)Lcom/cobblemon/mod/common/api/moves/BenchedMove;", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "loadFromNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/api/moves/BenchedMove;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final BenchedMove loadFromNBT(@NotNull CompoundTag nbt) {
            Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
            String name = nbt.m_128461_("MoveName");
            Intrinsics.checkNotNullExpressionValue((Object)name, (String)"name");
            MoveTemplate moveTemplate = Moves.INSTANCE.getByName(name);
            if (moveTemplate == null) {
                moveTemplate = MoveTemplate.Companion.dummy(name);
            }
            return new BenchedMove(moveTemplate, nbt.m_128445_("RaisedPPStages"));
        }

        @NotNull
        public final BenchedMove loadFromJSON(@NotNull JsonObject json) {
            Intrinsics.checkNotNullParameter((Object)json, (String)"json");
            String name = json.get("MoveName").getAsString();
            Intrinsics.checkNotNullExpressionValue((Object)name, (String)"name");
            MoveTemplate moveTemplate = Moves.INSTANCE.getByName(name);
            if (moveTemplate == null) {
                moveTemplate = MoveTemplate.Companion.dummy(name);
            }
            return new BenchedMove(moveTemplate, json.get("RaisedPPStages").getAsInt());
        }

        @NotNull
        public final BenchedMove loadFromBuffer(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            String name = buffer.m_130277_();
            Intrinsics.checkNotNullExpressionValue((Object)name, (String)"name");
            MoveTemplate moveTemplate = Moves.INSTANCE.getByName(name);
            if (moveTemplate == null) {
                moveTemplate = MoveTemplate.Companion.dummy(name);
            }
            return new BenchedMove(moveTemplate, NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_BYTE));
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

