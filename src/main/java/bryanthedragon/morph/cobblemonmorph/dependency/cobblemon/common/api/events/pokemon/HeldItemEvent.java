/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001:\u0002\u0006\u0007R\u0014\u0010\u0005\u001a\u00020\u00028&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\u00a8\u0006\b"}, d2={"Lcom/cobblemon/mod/common/api/events/pokemon/HeldItemEvent;", "", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Post", "Pre", "common"})
public interface HeldItemEvent {
    @NotNull
    public Pokemon getPokemon();

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\f\b\u0086\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\f\u001a\u00020\u0002\u0012\u0006\u0010\r\u001a\u00020\u0005\u0012\u0006\u0010\u000e\u001a\u00020\u0005\u0012\u0006\u0010\u000f\u001a\u00020\t\u00a2\u0006\u0004\b#\u0010$J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\b\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\b\u0010\u0007J\u0010\u0010\n\u001a\u00020\tH\u00c6\u0003\u00a2\u0006\u0004\b\n\u0010\u000bJ8\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\f\u001a\u00020\u00022\b\b\u0002\u0010\r\u001a\u00020\u00052\b\b\u0002\u0010\u000e\u001a\u00020\u00052\b\b\u0002\u0010\u000f\u001a\u00020\tH\u00c6\u0001\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u001a\u0010\u0014\u001a\u00020\t2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0012H\u00d6\u0003\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0010\u0010\u0017\u001a\u00020\u0016H\u00d6\u0001\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0010\u0010\u001a\u001a\u00020\u0019H\u00d6\u0001\u00a2\u0006\u0004\b\u001a\u0010\u001bR\u0017\u0010\u000f\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u001c\u001a\u0004\b\u001d\u0010\u000bR\u001a\u0010\f\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\f\u0010\u001e\u001a\u0004\b\u001f\u0010\u0004R\u0017\u0010\r\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\r\u0010 \u001a\u0004\b!\u0010\u0007R\u0017\u0010\u000e\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010 \u001a\u0004\b\"\u0010\u0007\u00a8\u0006%"}, d2={"Lcom/cobblemon/mod/common/api/events/pokemon/HeldItemEvent$Post;", "Lcom/cobblemon/mod/common/api/events/pokemon/HeldItemEvent;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "component1", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lnet/minecraft/world/item/ItemStack;", "component2", "()Lnet/minecraft/world/item/ItemStack;", "component3", "", "component4", "()Z", "pokemon", "received", "returned", "decremented", "copy", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;Z)Lcom/cobblemon/mod/common/api/events/pokemon/HeldItemEvent$Post;", "", "other", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Z", "getDecremented", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "Lnet/minecraft/world/item/ItemStack;", "getReceived", "getReturned", "<init>", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;Z)V", "common"})
    public static final class Post
    implements HeldItemEvent {
        @NotNull
        private final Pokemon pokemon;
        @NotNull
        private final ItemStack received;
        @NotNull
        private final ItemStack returned;
        private final boolean decremented;

        public Post(@NotNull Pokemon pokemon, @NotNull ItemStack received, @NotNull ItemStack returned, boolean decremented) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Intrinsics.checkNotNullParameter((Object)received, (String)"received");
            Intrinsics.checkNotNullParameter((Object)returned, (String)"returned");
            this.pokemon = pokemon;
            this.received = received;
            this.returned = returned;
            this.decremented = decremented;
        }

        @Override
        @NotNull
        public Pokemon getPokemon() {
            return this.pokemon;
        }

        @NotNull
        public final ItemStack getReceived() {
            return this.received;
        }

        @NotNull
        public final ItemStack getReturned() {
            return this.returned;
        }

        public final boolean getDecremented() {
            return this.decremented;
        }

        @NotNull
        public final Pokemon component1() {
            return this.pokemon;
        }

        @NotNull
        public final ItemStack component2() {
            return this.received;
        }

        @NotNull
        public final ItemStack component3() {
            return this.returned;
        }

        public final boolean component4() {
            return this.decremented;
        }

        @NotNull
        public final Post copy(@NotNull Pokemon pokemon, @NotNull ItemStack received, @NotNull ItemStack returned, boolean decremented) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Intrinsics.checkNotNullParameter((Object)received, (String)"received");
            Intrinsics.checkNotNullParameter((Object)returned, (String)"returned");
            return new Post(pokemon, received, returned, decremented);
        }

        public static /* synthetic */ Post copy$default(Post post2, Pokemon pokemon, ItemStack itemStack, ItemStack itemStack2, boolean bl, int n, Object object) {
            if ((n & 1) != 0) {
                pokemon = post2.pokemon;
            }
            if ((n & 2) != 0) {
                itemStack = post2.received;
            }
            if ((n & 4) != 0) {
                itemStack2 = post2.returned;
            }
            if ((n & 8) != 0) {
                bl = post2.decremented;
            }
            return post2.copy(pokemon, itemStack, itemStack2, bl);
        }

        @NotNull
        public String toString() {
            return "Post(pokemon=" + this.pokemon + ", received=" + this.received + ", returned=" + this.returned + ", decremented=" + this.decremented + ")";
        }

        public int hashCode() {
            int result = this.pokemon.hashCode();
            result = result * 31 + this.received.hashCode();
            result = result * 31 + this.returned.hashCode();
            int n = this.decremented ? 1 : 0;
            if (n != 0) {
                n = 1;
            }
            result = result * 31 + n;
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Post)) {
                return false;
            }
            Post post2 = (Post)other;
            if (!Intrinsics.areEqual((Object)this.pokemon, (Object)post2.pokemon)) {
                return false;
            }
            if (!Intrinsics.areEqual((Object)this.received, (Object)post2.received)) {
                return false;
            }
            if (!Intrinsics.areEqual((Object)this.returned, (Object)post2.returned)) {
                return false;
            }
            return this.decremented == post2.decremented;
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0011\b\u0086\b\u0018\u00002\u00020\u00012\u00020\u0002B'\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\u0006\u0010\u000e\u001a\u00020\u0006\u0012\u0006\u0010\u000f\u001a\u00020\u0006\u0012\u0006\u0010\u0010\u001a\u00020\n\u00a2\u0006\u0004\b)\u0010*J\u0010\u0010\u0004\u001a\u00020\u0003H\u00c6\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0007\u001a\u00020\u0006H\u00c6\u0003\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0010\u0010\t\u001a\u00020\u0006H\u00c6\u0003\u00a2\u0006\u0004\b\t\u0010\bJ\u0010\u0010\u000b\u001a\u00020\nH\u00c6\u0003\u00a2\u0006\u0004\b\u000b\u0010\fJ8\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u00062\b\b\u0002\u0010\u0010\u001a\u00020\nH\u00c6\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u001a\u0010\u0015\u001a\u00020\n2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0013H\u00d6\u0003\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0010\u0010\u0018\u001a\u00020\u0017H\u00d6\u0001\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0010\u0010\u001b\u001a\u00020\u001aH\u00d6\u0001\u00a2\u0006\u0004\b\u001b\u0010\u001cR\"\u0010\u0010\u001a\u00020\n8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0010\u0010\u001d\u001a\u0004\b\u001e\u0010\f\"\u0004\b\u001f\u0010 R\u001a\u0010\r\u001a\u00020\u00038\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\r\u0010!\u001a\u0004\b\"\u0010\u0005R\"\u0010\u000e\u001a\u00020\u00068\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000e\u0010#\u001a\u0004\b$\u0010\b\"\u0004\b%\u0010&R\"\u0010\u000f\u001a\u00020\u00068\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000f\u0010#\u001a\u0004\b'\u0010\b\"\u0004\b(\u0010&\u00a8\u0006+"}, d2={"Lcom/cobblemon/mod/common/api/events/pokemon/HeldItemEvent$Pre;", "Lcom/cobblemon/mod/common/api/events/pokemon/HeldItemEvent;", "Lcom/cobblemon/mod/common/api/events/Cancelable;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "component1", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lnet/minecraft/world/item/ItemStack;", "component2", "()Lnet/minecraft/world/item/ItemStack;", "component3", "", "component4", "()Z", "pokemon", "receiving", "returning", "decrement", "copy", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;Z)Lcom/cobblemon/mod/common/api/events/pokemon/HeldItemEvent$Pre;", "", "other", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Z", "getDecrement", "setDecrement", "(Z)V", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "Lnet/minecraft/world/item/ItemStack;", "getReceiving", "setReceiving", "(Lnet/minecraft/world/item/ItemStack;)V", "getReturning", "setReturning", "<init>", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;Z)V", "common"})
    public static final class Pre
    extends Cancelable
    implements HeldItemEvent {
        @NotNull
        private final Pokemon pokemon;
        @NotNull
        private ItemStack receiving;
        @NotNull
        private ItemStack returning;
        private boolean decrement;

        public Pre(@NotNull Pokemon pokemon, @NotNull ItemStack receiving, @NotNull ItemStack returning, boolean decrement) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Intrinsics.checkNotNullParameter((Object)receiving, (String)"receiving");
            Intrinsics.checkNotNullParameter((Object)returning, (String)"returning");
            this.pokemon = pokemon;
            this.receiving = receiving;
            this.returning = returning;
            this.decrement = decrement;
        }

        @Override
        @NotNull
        public Pokemon getPokemon() {
            return this.pokemon;
        }

        @NotNull
        public final ItemStack getReceiving() {
            return this.receiving;
        }

        public final void setReceiving(@NotNull ItemStack itemStack) {
            Intrinsics.checkNotNullParameter((Object)itemStack, (String)"<set-?>");
            this.receiving = itemStack;
        }

        @NotNull
        public final ItemStack getReturning() {
            return this.returning;
        }

        public final void setReturning(@NotNull ItemStack itemStack) {
            Intrinsics.checkNotNullParameter((Object)itemStack, (String)"<set-?>");
            this.returning = itemStack;
        }

        public final boolean getDecrement() {
            return this.decrement;
        }

        public final void setDecrement(boolean bl) {
            this.decrement = bl;
        }

        @NotNull
        public final Pokemon component1() {
            return this.pokemon;
        }

        @NotNull
        public final ItemStack component2() {
            return this.receiving;
        }

        @NotNull
        public final ItemStack component3() {
            return this.returning;
        }

        public final boolean component4() {
            return this.decrement;
        }

        @NotNull
        public final Pre copy(@NotNull Pokemon pokemon, @NotNull ItemStack receiving, @NotNull ItemStack returning, boolean decrement) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Intrinsics.checkNotNullParameter((Object)receiving, (String)"receiving");
            Intrinsics.checkNotNullParameter((Object)returning, (String)"returning");
            return new Pre(pokemon, receiving, returning, decrement);
        }

        public static /* synthetic */ Pre copy$default(Pre pre, Pokemon pokemon, ItemStack itemStack, ItemStack itemStack2, boolean bl, int n, Object object) {
            if ((n & 1) != 0) {
                pokemon = pre.pokemon;
            }
            if ((n & 2) != 0) {
                itemStack = pre.receiving;
            }
            if ((n & 4) != 0) {
                itemStack2 = pre.returning;
            }
            if ((n & 8) != 0) {
                bl = pre.decrement;
            }
            return pre.copy(pokemon, itemStack, itemStack2, bl);
        }

        @NotNull
        public String toString() {
            return "Pre(pokemon=" + this.pokemon + ", receiving=" + this.receiving + ", returning=" + this.returning + ", decrement=" + this.decrement + ")";
        }

        public int hashCode() {
            int result = this.pokemon.hashCode();
            result = result * 31 + this.receiving.hashCode();
            result = result * 31 + this.returning.hashCode();
            int n = this.decrement ? 1 : 0;
            if (n != 0) {
                n = 1;
            }
            result = result * 31 + n;
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Pre)) {
                return false;
            }
            Pre pre = (Pre)other;
            if (!Intrinsics.areEqual((Object)this.pokemon, (Object)pre.pokemon)) {
                return false;
            }
            if (!Intrinsics.areEqual((Object)this.receiving, (Object)pre.receiving)) {
                return false;
            }
            if (!Intrinsics.areEqual((Object)this.returning, (Object)pre.returning)) {
                return false;
            }
            return this.decrement == pre.decrement;
        }
    }
}

