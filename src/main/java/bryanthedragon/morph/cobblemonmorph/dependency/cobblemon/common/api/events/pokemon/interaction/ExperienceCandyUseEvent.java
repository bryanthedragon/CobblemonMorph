/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.interaction;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.CandyItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.AddExperienceResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001:\u0002\u000e\u000fR\u0014\u0010\u0005\u001a\u00020\u00028&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004R\u0014\u0010\t\u001a\u00020\u00068&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\r\u001a\u00020\n8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/api/events/pokemon/interaction/ExperienceCandyUseEvent;", "", "Lcom/cobblemon/mod/common/item/interactive/CandyItem;", "getItem", "()Lcom/cobblemon/mod/common/item/interactive/CandyItem;", "item", "Lnet/minecraft/server/level/ServerPlayer;", "getPlayer", "()Lnet/minecraft/server/level/ServerPlayer;", "player", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Post", "Pre", "common"})
public interface ExperienceCandyUseEvent {
    @NotNull
    public ServerPlayer getPlayer();

    @NotNull
    public Pokemon getPokemon();

    @NotNull
    public CandyItem getItem();

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u0012\u0006\u0010\u0016\u001a\u00020\u0015\u0012\u0006\u0010\f\u001a\u00020\u000b\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\r\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0004R\u0017\u0010\u0007\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\nR\u001a\u0010\f\u001a\u00020\u000b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0011\u001a\u00020\u00108\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0016\u001a\u00020\u00158\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/api/events/pokemon/interaction/ExperienceCandyUseEvent$Post;", "Lcom/cobblemon/mod/common/api/events/pokemon/interaction/ExperienceCandyUseEvent;", "", "wasCandyConsumed", "()Z", "wasExperienceGiven", "Lcom/cobblemon/mod/common/pokemon/AddExperienceResult;", "experienceResult", "Lcom/cobblemon/mod/common/pokemon/AddExperienceResult;", "getExperienceResult", "()Lcom/cobblemon/mod/common/pokemon/AddExperienceResult;", "Lcom/cobblemon/mod/common/item/interactive/CandyItem;", "item", "Lcom/cobblemon/mod/common/item/interactive/CandyItem;", "getItem", "()Lcom/cobblemon/mod/common/item/interactive/CandyItem;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lnet/minecraft/server/level/ServerPlayer;", "getPlayer", "()Lnet/minecraft/server/level/ServerPlayer;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "<init>", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/item/interactive/CandyItem;Lcom/cobblemon/mod/common/pokemon/AddExperienceResult;)V", "common"})
    public static final class Post
    implements ExperienceCandyUseEvent {
        @NotNull
        private final ServerPlayer player;
        @NotNull
        private final Pokemon pokemon;
        @NotNull
        private final CandyItem item;
        @NotNull
        private final AddExperienceResult experienceResult;

        public Post(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull CandyItem item, @NotNull AddExperienceResult experienceResult) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Intrinsics.checkNotNullParameter((Object)item, (String)"item");
            Intrinsics.checkNotNullParameter((Object)experienceResult, (String)"experienceResult");
            this.player = player;
            this.pokemon = pokemon;
            this.item = item;
            this.experienceResult = experienceResult;
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
        public CandyItem getItem() {
            return this.item;
        }

        @NotNull
        public final AddExperienceResult getExperienceResult() {
            return this.experienceResult;
        }

        public final boolean wasExperienceGiven() {
            return this.experienceResult.getExperienceAdded() > 0;
        }

        public final boolean wasCandyConsumed() {
            return this.experienceResult.getExperienceAdded() > 0 && !this.getPlayer().m_7500_();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B/\u0012\u0006\u0010\u0012\u001a\u00020\u0011\u0012\u0006\u0010\u0017\u001a\u00020\u0016\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u00a2\u0006\u0004\b\u001b\u0010\u001cR\u0017\u0010\u0004\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007R\"\u0010\b\u001a\u00020\u00038\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\b\u0010\u0005\u001a\u0004\b\t\u0010\u0007\"\u0004\b\n\u0010\u000bR\u001a\u0010\r\u001a\u00020\f8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0012\u001a\u00020\u00118\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0017\u001a\u00020\u00168\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/api/events/pokemon/interaction/ExperienceCandyUseEvent$Pre;", "Lcom/cobblemon/mod/common/api/events/pokemon/interaction/ExperienceCandyUseEvent;", "Lcom/cobblemon/mod/common/api/events/Cancelable;", "", "baseExperienceYield", "I", "getBaseExperienceYield", "()I", "experienceYield", "getExperienceYield", "setExperienceYield", "(I)V", "Lcom/cobblemon/mod/common/item/interactive/CandyItem;", "item", "Lcom/cobblemon/mod/common/item/interactive/CandyItem;", "getItem", "()Lcom/cobblemon/mod/common/item/interactive/CandyItem;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lnet/minecraft/server/level/ServerPlayer;", "getPlayer", "()Lnet/minecraft/server/level/ServerPlayer;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "<init>", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/item/interactive/CandyItem;II)V", "common"})
    public static final class Pre
    extends Cancelable
    implements ExperienceCandyUseEvent {
        @NotNull
        private final ServerPlayer player;
        @NotNull
        private final Pokemon pokemon;
        @NotNull
        private final CandyItem item;
        private final int baseExperienceYield;
        private int experienceYield;

        public Pre(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull CandyItem item, int baseExperienceYield, int experienceYield) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Intrinsics.checkNotNullParameter((Object)item, (String)"item");
            this.player = player;
            this.pokemon = pokemon;
            this.item = item;
            this.baseExperienceYield = baseExperienceYield;
            this.experienceYield = experienceYield;
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
        public CandyItem getItem() {
            return this.item;
        }

        public final int getBaseExperienceYield() {
            return this.baseExperienceYield;
        }

        public final int getExperienceYield() {
            return this.experienceYield;
        }

        public final void setExperienceYield(int n) {
            this.experienceYield = n;
        }
    }
}

