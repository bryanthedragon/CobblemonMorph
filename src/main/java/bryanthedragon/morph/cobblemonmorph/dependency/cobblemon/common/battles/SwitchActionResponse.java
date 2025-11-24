/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionRequest;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionResponse;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionResponseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownMoveset;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownPokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownSide;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\n\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b'\u0010&J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001a\u0010\u0006\u001a\u00020\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u0002H\u00c6\u0001\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001a\u0010\u000b\u001a\u00020\n2\b\u0010\t\u001a\u0004\u0018\u00010\bH\u00d6\u0003\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0010\u0010\u000e\u001a\u00020\rH\u00d6\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ)\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u00102\b\u0010\u0013\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0014\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0017\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u0017H\u0016\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u0017\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u0018\u001a\u00020\u0017H\u0016\u00a2\u0006\u0004\b\u001c\u0010\u001dJ!\u0010\u001f\u001a\u00020\u001e2\u0006\u0010\u0011\u001a\u00020\u00102\b\u0010\u0013\u001a\u0004\u0018\u00010\u0012H\u0016\u00a2\u0006\u0004\b\u001f\u0010 J\u0010\u0010!\u001a\u00020\u001eH\u00d6\u0001\u00a2\u0006\u0004\b!\u0010\"R\"\u0010\u0005\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0005\u0010#\u001a\u0004\b$\u0010\u0004\"\u0004\b%\u0010&\u00a8\u0006("}, d2={"Lcom/cobblemon/mod/common/battles/SwitchActionResponse;", "Lcom/cobblemon/mod/common/battles/ShowdownActionResponse;", "Ljava/util/UUID;", "component1", "()Ljava/util/UUID;", "newPokemonId", "copy", "(Ljava/util/UUID;)Lcom/cobblemon/mod/common/battles/SwitchActionResponse;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;", "activeBattlePokemon", "Lcom/cobblemon/mod/common/battles/ShowdownMoveset;", "showdownMoveSet", "forceSwitch", "isValid", "(Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;Lcom/cobblemon/mod/common/battles/ShowdownMoveset;Z)Z", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "loadFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/battles/ShowdownActionResponse;", "", "saveToBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "", "toShowdownString", "(Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;Lcom/cobblemon/mod/common/battles/ShowdownMoveset;)Ljava/lang/String;", "toString", "()Ljava/lang/String;", "Ljava/util/UUID;", "getNewPokemonId", "setNewPokemonId", "(Ljava/util/UUID;)V", "<init>", "common"})
@SourceDebugExtension(value={"SMAP\nShowdownActionRequest.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ShowdownActionRequest.kt\ncom/cobblemon/mod/common/battles/SwitchActionResponse\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,452:1\n1#2:453\n1747#3,3:454\n350#3,7:457\n*S KotlinDebug\n*F\n+ 1 ShowdownActionRequest.kt\ncom/cobblemon/mod/common/battles/SwitchActionResponse\n*L\n212#1:454,3\n218#1:457,7\n*E\n"})
public final class SwitchActionResponse
extends ShowdownActionResponse {
    @NotNull
    private UUID newPokemonId;

    public SwitchActionResponse(@NotNull UUID newPokemonId) {
        Intrinsics.checkNotNullParameter((Object)newPokemonId, (String)"newPokemonId");
        super(ShowdownActionResponseType.SWITCH);
        this.newPokemonId = newPokemonId;
    }

    @NotNull
    public final UUID getNewPokemonId() {
        return this.newPokemonId;
    }

    public final void setNewPokemonId(@NotNull UUID uUID) {
        Intrinsics.checkNotNullParameter((Object)uUID, (String)"<set-?>");
        this.newPokemonId = uUID;
    }

    @Override
    public void saveToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        super.saveToBuffer(buffer);
        buffer.m_130077_(this.newPokemonId);
    }

    @Override
    @NotNull
    public ShowdownActionResponse loadFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        super.loadFromBuffer(buffer);
        UUID uUID = buffer.m_130259_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"buffer.readUuid()");
        this.newPokemonId = uUID;
        return this;
    }

    @Override
    public boolean isValid(@NotNull ActiveBattlePokemon activeBattlePokemon, @Nullable ShowdownMoveset showdownMoveSet, boolean forceSwitch) {
        boolean bl;
        Object v0;
        Object it;
        block10: {
            Intrinsics.checkNotNullParameter((Object)activeBattlePokemon, (String)"activeBattlePokemon");
            Iterable iterable = activeBattlePokemon.getActor().getPokemonList();
            for (Object t : iterable) {
                it = (BattlePokemon)t;
                boolean bl2 = false;
                if (!Intrinsics.areEqual((Object)((BattlePokemon)it).getUuid(), (Object)this.newPokemonId)) continue;
                v0 = t;
                break block10;
            }
            v0 = null;
        }
        BattlePokemon pokemon = v0;
        if (pokemon == null) {
            bl = false;
        } else {
            Object object = activeBattlePokemon.getActor().getRequest();
            Boolean bl3 = object != null && (object = ((ShowdownActionRequest)object).getSide()) != null && (object = ((ShowdownSide)object).getPokemon()) != null && (object = object.get(0)) != null ? Boolean.valueOf(((ShowdownPokemon)object).getReviving()) : null;
            Intrinsics.checkNotNull(bl3);
            if (!bl3.booleanValue() && pokemon.getHealth() <= 0) {
                bl = false;
            } else if (showdownMoveSet != null && showdownMoveSet.getTrapped()) {
                bl = false;
            } else {
                boolean bl4;
                block11: {
                    Iterable $this$any$iv = activeBattlePokemon.getActor().getSide().getActivePokemon();
                    boolean $i$f$any = false;
                    if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                        bl4 = false;
                    } else {
                        for (Object element$iv : $this$any$iv) {
                            it = (ActiveBattlePokemon)element$iv;
                            boolean bl5 = false;
                            BattlePokemon battlePokemon = ((ActiveBattlePokemon)it).getBattlePokemon();
                            if (!Intrinsics.areEqual((Object)(battlePokemon != null ? battlePokemon.getUuid() : null), (Object)this.newPokemonId)) continue;
                            bl4 = true;
                            break block11;
                        }
                        bl4 = false;
                    }
                }
                bl = !bl4;
            }
        }
        return bl;
    }

    @Override
    @NotNull
    public String toShowdownString(@NotNull ActiveBattlePokemon activeBattlePokemon, @Nullable ShowdownMoveset showdownMoveSet) {
        int n;
        block2: {
            Intrinsics.checkNotNullParameter((Object)activeBattlePokemon, (String)"activeBattlePokemon");
            List<BattlePokemon> $this$indexOfFirst$iv = activeBattlePokemon.getActor().getPokemonList();
            boolean $i$f$indexOfFirst = false;
            int index$iv = 0;
            Iterator<BattlePokemon> iterator = $this$indexOfFirst$iv.iterator();
            while (iterator.hasNext()) {
                BattlePokemon item$iv;
                BattlePokemon it = item$iv = iterator.next();
                boolean bl = false;
                if (Intrinsics.areEqual((Object)it.getUuid(), (Object)this.newPokemonId)) {
                    n = index$iv;
                    break block2;
                }
                ++index$iv;
            }
            n = -1;
        }
        return "switch " + (n + 1);
    }

    @NotNull
    public final UUID component1() {
        return this.newPokemonId;
    }

    @NotNull
    public final SwitchActionResponse copy(@NotNull UUID newPokemonId) {
        Intrinsics.checkNotNullParameter((Object)newPokemonId, (String)"newPokemonId");
        return new SwitchActionResponse(newPokemonId);
    }

    public static /* synthetic */ SwitchActionResponse copy$default(SwitchActionResponse switchActionResponse, UUID uUID, int n, Object object) {
        if ((n & 1) != 0) {
            uUID = switchActionResponse.newPokemonId;
        }
        return switchActionResponse.copy(uUID);
    }

    @NotNull
    public String toString() {
        return "SwitchActionResponse(newPokemonId=" + this.newPokemonId + ")";
    }

    public int hashCode() {
        return this.newPokemonId.hashCode();
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SwitchActionResponse)) {
            return false;
        }
        SwitchActionResponse switchActionResponse = (SwitchActionResponse)other;
        return Intrinsics.areEqual((Object)this.newPokemonId, (Object)switchActionResponse.newPokemonId);
    }
}

