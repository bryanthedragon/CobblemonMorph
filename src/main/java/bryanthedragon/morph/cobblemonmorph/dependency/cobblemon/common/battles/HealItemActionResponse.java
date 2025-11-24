/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionResponse;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionResponseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownMoveset;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u000b\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b%\u0010$J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001a\u0010\u0006\u001a\u00020\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u0002H\u00c6\u0001\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001a\u0010\u000b\u001a\u00020\n2\b\u0010\t\u001a\u0004\u0018\u00010\bH\u00d6\u0003\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0010\u0010\u000e\u001a\u00020\rH\u00d6\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ)\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u00102\b\u0010\u0013\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0014\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0017\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u0017H\u0016\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u0017\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u0018\u001a\u00020\u0017H\u0016\u00a2\u0006\u0004\b\u001c\u0010\u001dJ!\u0010\u001e\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u00102\b\u0010\u0013\u001a\u0004\u0018\u00010\u0012H\u0016\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u0010\u0010 \u001a\u00020\u0002H\u00d6\u0001\u00a2\u0006\u0004\b \u0010\u0004R\"\u0010\u0005\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0005\u0010!\u001a\u0004\b\"\u0010\u0004\"\u0004\b#\u0010$\u00a8\u0006&"}, d2={"Lcom/cobblemon/mod/common/battles/HealItemActionResponse;", "Lcom/cobblemon/mod/common/battles/ShowdownActionResponse;", "", "component1", "()Ljava/lang/String;", "item", "copy", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/battles/HealItemActionResponse;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;", "activeBattlePokemon", "Lcom/cobblemon/mod/common/battles/ShowdownMoveset;", "showdownMoveSet", "forceSwitch", "isValid", "(Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;Lcom/cobblemon/mod/common/battles/ShowdownMoveset;Z)Z", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "loadFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/battles/ShowdownActionResponse;", "", "saveToBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "toShowdownString", "(Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;Lcom/cobblemon/mod/common/battles/ShowdownMoveset;)Ljava/lang/String;", "toString", "Ljava/lang/String;", "getItem", "setItem", "(Ljava/lang/String;)V", "<init>", "common"})
public final class HealItemActionResponse
extends ShowdownActionResponse {
    @NotNull
    private String item;

    public HealItemActionResponse(@NotNull String item) {
        Intrinsics.checkNotNullParameter((Object)item, (String)"item");
        super(ShowdownActionResponseType.FORCE_PASS);
        this.item = item;
    }

    @NotNull
    public final String getItem() {
        return this.item;
    }

    public final void setItem(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.item = string;
    }

    @Override
    public void saveToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        super.saveToBuffer(buffer);
        buffer.m_130070_(this.item);
    }

    @Override
    @NotNull
    public ShowdownActionResponse loadFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        super.loadFromBuffer(buffer);
        String string = buffer.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"buffer.readString()");
        this.item = string;
        return this;
    }

    @Override
    public boolean isValid(@NotNull ActiveBattlePokemon activeBattlePokemon, @Nullable ShowdownMoveset showdownMoveSet, boolean forceSwitch) {
        Intrinsics.checkNotNullParameter((Object)activeBattlePokemon, (String)"activeBattlePokemon");
        return !forceSwitch;
    }

    @Override
    @NotNull
    public String toShowdownString(@NotNull ActiveBattlePokemon activeBattlePokemon, @Nullable ShowdownMoveset showdownMoveSet) {
        Intrinsics.checkNotNullParameter((Object)activeBattlePokemon, (String)"activeBattlePokemon");
        return "healitem " + activeBattlePokemon.getPNX() + " " + this.item;
    }

    @NotNull
    public final String component1() {
        return this.item;
    }

    @NotNull
    public final HealItemActionResponse copy(@NotNull String item) {
        Intrinsics.checkNotNullParameter((Object)item, (String)"item");
        return new HealItemActionResponse(item);
    }

    public static /* synthetic */ HealItemActionResponse copy$default(HealItemActionResponse healItemActionResponse, String string, int n, Object object) {
        if ((n & 1) != 0) {
            string = healItemActionResponse.item;
        }
        return healItemActionResponse.copy(string);
    }

    @NotNull
    public String toString() {
        return "HealItemActionResponse(item=" + this.item + ")";
    }

    public int hashCode() {
        return this.item.hashCode();
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HealItemActionResponse)) {
            return false;
        }
        HealItemActionResponse healItemActionResponse = (HealItemActionResponse)other;
        return Intrinsics.areEqual((Object)this.item, (Object)healItemActionResponse.item);
    }
}

