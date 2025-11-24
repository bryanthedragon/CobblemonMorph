/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.storage;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001:\u0002\u000e\u000fR\u0014\u0010\u0005\u001a\u00020\u00028&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004R\u0014\u0010\t\u001a\u00020\u00068&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0018\u0010\r\u001a\u0006\u0012\u0002\b\u00030\n8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/api/events/storage/ReleasePokemonEvent;", "", "Lnet/minecraft/server/level/ServerPlayer;", "getPlayer", "()Lnet/minecraft/server/level/ServerPlayer;", "player", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "getStorage", "()Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "storage", "Post", "Pre", "common"})
public interface ReleasePokemonEvent {
    @NotNull
    public ServerPlayer getPlayer();

    @NotNull
    public Pokemon getPokemon();

    @NotNull
    public PokemonStore<?> getStorage();

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\f\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0003\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\b\u001a\u00020\u00078\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u001e\u0010\r\u001a\u0006\u0012\u0002\b\u00030\f8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/api/events/storage/ReleasePokemonEvent$Post;", "Lcom/cobblemon/mod/common/api/events/storage/ReleasePokemonEvent;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lnet/minecraft/server/level/ServerPlayer;", "getPlayer", "()Lnet/minecraft/server/level/ServerPlayer;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "storage", "Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "getStorage", "()Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "<init>", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/api/storage/PokemonStore;)V", "common"})
    public static final class Post
    implements ReleasePokemonEvent {
        @NotNull
        private final ServerPlayer player;
        @NotNull
        private final Pokemon pokemon;
        @NotNull
        private final PokemonStore<?> storage;

        public Post(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull PokemonStore<?> storage) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Intrinsics.checkNotNullParameter(storage, (String)"storage");
            this.player = player;
            this.pokemon = pokemon;
            this.storage = storage;
        }

        @Override
        @NotNull
        public ServerPlayer getPlayer() {
            return this.player;
        }

        @Override
        @NotNull
        public Pokemon getPokemon() {
            return this.pokemon;
        }

        @Override
        @NotNull
        public PokemonStore<?> getStorage() {
            return this.storage;
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B#\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\b\u0012\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\r\u00a2\u0006\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0004\u001a\u00020\u00038\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\t\u001a\u00020\b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\fR\u001e\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/api/events/storage/ReleasePokemonEvent$Pre;", "Lcom/cobblemon/mod/common/api/events/storage/ReleasePokemonEvent;", "Lcom/cobblemon/mod/common/api/events/Cancelable;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lnet/minecraft/server/level/ServerPlayer;", "getPlayer", "()Lnet/minecraft/server/level/ServerPlayer;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "storage", "Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "getStorage", "()Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "<init>", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/api/storage/PokemonStore;)V", "common"})
    public static final class Pre
    extends Cancelable
    implements ReleasePokemonEvent {
        @NotNull
        private final ServerPlayer player;
        @NotNull
        private final Pokemon pokemon;
        @NotNull
        private final PokemonStore<?> storage;

        public Pre(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull PokemonStore<?> storage) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Intrinsics.checkNotNullParameter(storage, (String)"storage");
            this.player = player;
            this.pokemon = pokemon;
            this.storage = storage;
        }

        @Override
        @NotNull
        public ServerPlayer getPlayer() {
            return this.player;
        }

        @Override
        @NotNull
        public Pokemon getPokemon() {
            return this.pokemon;
        }

        @Override
        @NotNull
        public PokemonStore<?> getStorage() {
            return this.storage;
        }
    }
}

