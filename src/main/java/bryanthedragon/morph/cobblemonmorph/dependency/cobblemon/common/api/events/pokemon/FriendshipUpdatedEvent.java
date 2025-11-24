/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\b\u001a\u00020\u0002\u0012\u0006\u0010\t\u001a\u00020\u0005\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J$\u0010\n\u001a\u00020\u00002\b\b\u0002\u0010\b\u001a\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\u0005H\u00c6\u0001\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001a\u0010\u000e\u001a\u00020\r2\b\u0010\f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0010\u0010\u0010\u001a\u00020\u0005H\u00d6\u0001\u00a2\u0006\u0004\b\u0010\u0010\u0007J\u0010\u0010\u0012\u001a\u00020\u0011H\u00d6\u0001\u00a2\u0006\u0004\b\u0012\u0010\u0013R\"\u0010\t\u001a\u00020\u00058\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\t\u0010\u0014\u001a\u0004\b\u0015\u0010\u0007\"\u0004\b\u0016\u0010\u0017R\u0017\u0010\b\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\u0018\u001a\u0004\b\u0019\u0010\u0004\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/api/events/pokemon/FriendshipUpdatedEvent;", "", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "component1", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "", "component2", "()I", "pokemon", "newFriendship", "copy", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;I)Lcom/cobblemon/mod/common/api/events/pokemon/FriendshipUpdatedEvent;", "other", "", "equals", "(Ljava/lang/Object;)Z", "hashCode", "", "toString", "()Ljava/lang/String;", "I", "getNewFriendship", "setNewFriendship", "(I)V", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "<init>", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;I)V", "common"})
public final class FriendshipUpdatedEvent {
    @NotNull
    private final Pokemon pokemon;
    private int newFriendship;

    public FriendshipUpdatedEvent(@NotNull Pokemon pokemon, int newFriendship) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        this.pokemon = pokemon;
        this.newFriendship = newFriendship;
    }

    @NotNull
    public final Pokemon getPokemon() {
        return this.pokemon;
    }

    public final int getNewFriendship() {
        return this.newFriendship;
    }

    public final void setNewFriendship(int n) {
        this.newFriendship = n;
    }

    @NotNull
    public final Pokemon component1() {
        return this.pokemon;
    }

    public final int component2() {
        return this.newFriendship;
    }

    @NotNull
    public final FriendshipUpdatedEvent copy(@NotNull Pokemon pokemon, int newFriendship) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        return new FriendshipUpdatedEvent(pokemon, newFriendship);
    }

    public static /* synthetic */ FriendshipUpdatedEvent copy$default(FriendshipUpdatedEvent friendshipUpdatedEvent, Pokemon pokemon, int n, int n2, Object object) {
        if ((n2 & 1) != 0) {
            pokemon = friendshipUpdatedEvent.pokemon;
        }
        if ((n2 & 2) != 0) {
            n = friendshipUpdatedEvent.newFriendship;
        }
        return friendshipUpdatedEvent.copy(pokemon, n);
    }

    @NotNull
    public String toString() {
        return "FriendshipUpdatedEvent(pokemon=" + this.pokemon + ", newFriendship=" + this.newFriendship + ")";
    }

    public int hashCode() {
        int result = this.pokemon.hashCode();
        result = result * 31 + Integer.hashCode(this.newFriendship);
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FriendshipUpdatedEvent)) {
            return false;
        }
        FriendshipUpdatedEvent friendshipUpdatedEvent = (FriendshipUpdatedEvent)other;
        if (!Intrinsics.areEqual((Object)this.pokemon, (Object)friendshipUpdatedEvent.pokemon)) {
            return false;
        }
        return this.newFriendship == friendshipUpdatedEvent.newFriendship;
    }
}

