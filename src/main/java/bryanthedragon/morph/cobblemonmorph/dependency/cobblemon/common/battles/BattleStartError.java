/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.AlreadyInBattleError;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BusyError;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.CanceledError;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.InsufficientPokemonError;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0005\u0010\u0006\u00a8\u0006\b"}, d2={"Lcom/cobblemon/mod/common/battles/BattleStartError;", "", "Lnet/minecraft/world/entity/Entity;", "entity", "Lnet/minecraft/network/chat/MutableComponent;", "getMessageFor", "(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/network/chat/MutableComponent;", "Companion", "common"})
public interface BattleStartError {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleStartError$Companion.$$INSTANCE;

    @NotNull
    public MutableComponent getMessageFor(@NotNull Entity var1);

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\u0005\u0010\tJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\u0005\u0010\fJ\u0017\u0010\u0010\u001a\u00020\u000f2\b\u0010\u000e\u001a\u0004\u0018\u00010\r\u00a2\u0006\u0004\b\u0010\u0010\u0011J%\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0012\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0015\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u0018\u001a\u00020\r\u00a2\u0006\u0004\b\u001a\u0010\u001b\u00a8\u0006\u001e"}, d2={"Lcom/cobblemon/mod/common/battles/BattleStartError$Companion;", "", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "actor", "Lcom/cobblemon/mod/common/battles/AlreadyInBattleError;", "alreadyInBattle", "(Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;)Lcom/cobblemon/mod/common/battles/AlreadyInBattleError;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "pokemonEntity", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)Lcom/cobblemon/mod/common/battles/AlreadyInBattleError;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "(Lnet/minecraft/server/level/ServerPlayer;)Lcom/cobblemon/mod/common/battles/AlreadyInBattleError;", "Lnet/minecraft/network/chat/MutableComponent;", "reason", "Lcom/cobblemon/mod/common/battles/CanceledError;", "canceledByEvent", "(Lnet/minecraft/network/chat/MutableComponent;)Lcom/cobblemon/mod/common/battles/CanceledError;", "", "requiredCount", "hadCount", "Lcom/cobblemon/mod/common/battles/InsufficientPokemonError;", "insufficientPokemon", "(Lnet/minecraft/server/level/ServerPlayer;II)Lcom/cobblemon/mod/common/battles/InsufficientPokemonError;", "targetName", "Lcom/cobblemon/mod/common/battles/BusyError;", "targetIsBusy", "(Lnet/minecraft/network/chat/MutableComponent;)Lcom/cobblemon/mod/common/battles/BusyError;", "<init>", "()V", "common"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;

        private Companion() {
        }

        @NotNull
        public final AlreadyInBattleError alreadyInBattle(@NotNull ServerPlayer player) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            UUID uUID = player.m_20148_();
            Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
            Component component = player.m_5446_();
            Intrinsics.checkNotNullExpressionValue((Object)component, (String)"player.displayName");
            return new AlreadyInBattleError(uUID, component);
        }

        @NotNull
        public final AlreadyInBattleError alreadyInBattle(@NotNull PokemonEntity pokemonEntity) {
            Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
            UUID uUID = pokemonEntity.m_20148_();
            Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"pokemonEntity.uuid");
            Component component = pokemonEntity.m_5446_();
            Intrinsics.checkNotNullExpressionValue((Object)component, (String)"pokemonEntity.displayName");
            return new AlreadyInBattleError(uUID, component);
        }

        @NotNull
        public final AlreadyInBattleError alreadyInBattle(@NotNull BattleActor actor) {
            Intrinsics.checkNotNullParameter((Object)actor, (String)"actor");
            return new AlreadyInBattleError(actor.getUuid(), (Component)actor.getName());
        }

        @NotNull
        public final BusyError targetIsBusy(@NotNull MutableComponent targetName) {
            Intrinsics.checkNotNullParameter((Object)targetName, (String)"targetName");
            return new BusyError(targetName);
        }

        @NotNull
        public final InsufficientPokemonError insufficientPokemon(@NotNull ServerPlayer player, int requiredCount, int hadCount) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            return new InsufficientPokemonError(player, requiredCount, hadCount);
        }

        @NotNull
        public final CanceledError canceledByEvent(@Nullable MutableComponent reason) {
            return new CanceledError(reason);
        }

        static {
            $$INSTANCE = new Companion();
        }
    }
}

