/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.AIBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.ActorType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.EntityBackedBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.FleeableBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.ai.BattleAI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ai.RandomBattleAI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleEndPacket;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u00022\u00020\u0004B)\u0012\u0006\u0010)\u001a\u00020(\u0012\u0006\u0010\u001f\u001a\u00020\u001e\u0012\u0006\u0010\u001a\u001a\u00020\u0019\u0012\b\b\u0002\u0010+\u001a\u00020*\u00a2\u0006\u0004\b,\u0010-J\u000f\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001d\u0010\u000b\u001a\u0010\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n\u0018\u00010\bH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u001b\u0010\u0014\u001a\u00020\u00132\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u0011H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015R\u0016\u0010\u0018\u001a\u0004\u0018\u00010\u00038VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\u001a\u001a\u00020\u00198\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001dR\u0017\u0010\u001f\u001a\u00020\u001e8\u0006\u00a2\u0006\f\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\"R\u001a\u0010$\u001a\u00020#8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b$\u0010%\u001a\u0004\b&\u0010'\u00a8\u0006."}, d2={"Lcom/cobblemon/mod/common/battles/actor/PokemonBattleActor;", "Lcom/cobblemon/mod/common/api/battles/model/actor/AIBattleActor;", "Lcom/cobblemon/mod/common/api/battles/model/actor/EntityBackedBattleActor;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lcom/cobblemon/mod/common/api/battles/model/actor/FleeableBattleActor;", "Lnet/minecraft/network/chat/MutableComponent;", "getName", "()Lnet/minecraft/network/chat/MutableComponent;", "Lkotlin/Pair;", "Lnet/minecraft/server/level/ServerLevel;", "Lnet/minecraft/world/phys/Vec3;", "getWorldAndPosition", "()Lkotlin/Pair;", "", "name", "nameOwned", "(Ljava/lang/String;)Lnet/minecraft/network/chat/MutableComponent;", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "packet", "", "sendUpdate", "(Lcom/cobblemon/mod/common/api/net/NetworkPacket;)V", "getEntity", "()Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "entity", "", "fleeDistance", "F", "getFleeDistance", "()F", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "pokemon", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "getPokemon", "()Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "Lcom/cobblemon/mod/common/api/battles/model/actor/ActorType;", "type", "Lcom/cobblemon/mod/common/api/battles/model/actor/ActorType;", "getType", "()Lcom/cobblemon/mod/common/api/battles/model/actor/ActorType;", "Ljava/util/UUID;", "uuid", "Lcom/cobblemon/mod/common/api/battles/model/ai/BattleAI;", "artificialDecider", "<init>", "(Ljava/util/UUID;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;FLcom/cobblemon/mod/common/api/battles/model/ai/BattleAI;)V", "common"})
public class PokemonBattleActor
extends AIBattleActor
implements EntityBackedBattleActor<PokemonEntity>,
FleeableBattleActor {
    @NotNull
    private final BattlePokemon pokemon;
    private final float fleeDistance;
    @NotNull
    private final ActorType type;

    public PokemonBattleActor(@NotNull UUID uuid2, @NotNull BattlePokemon pokemon, float fleeDistance, @NotNull BattleAI artificialDecider) {
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)artificialDecider, (String)"artificialDecider");
        super(uuid2, CollectionsKt.listOf((Object)pokemon), artificialDecider);
        this.pokemon = pokemon;
        this.fleeDistance = fleeDistance;
        this.type = ActorType.WILD;
    }

    public /* synthetic */ PokemonBattleActor(UUID uUID, BattlePokemon battlePokemon, float f, BattleAI battleAI, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 8) != 0) {
            battleAI = new RandomBattleAI();
        }
        this(uUID, battlePokemon, f, battleAI);
    }

    @NotNull
    public final BattlePokemon getPokemon() {
        return this.pokemon;
    }

    @Override
    public float getFleeDistance() {
        return this.fleeDistance;
    }

    @Override
    @NotNull
    public MutableComponent getName() {
        return this.pokemon.getEffectedPokemon().getSpecies().getTranslatedName();
    }

    @Override
    @NotNull
    public MutableComponent nameOwned(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        MutableComponent mutableComponent = Component.m_237113_((String)name);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"literal(name)");
        return mutableComponent;
    }

    @Override
    @Nullable
    public Pair<ServerLevel, Vec3> getWorldAndPosition() {
        ServerPlayer ownerPlayer = this.pokemon.getEffectedPokemon().getOwnerPlayer();
        if (ownerPlayer != null) {
            return TuplesKt.to((Object)ownerPlayer.m_284548_(), (Object)ownerPlayer.m_20182_());
        }
        PokemonEntity pokemonEntity = this.getEntity();
        if (pokemonEntity == null) {
            return null;
        }
        PokemonEntity entity2 = pokemonEntity;
        Level level = entity2.m_9236_();
        ServerLevel serverLevel = level instanceof ServerLevel ? (ServerLevel)level : null;
        if (serverLevel == null) {
            return null;
        }
        ServerLevel world = serverLevel;
        return TuplesKt.to((Object)world, (Object)entity2.m_20182_());
    }

    @Override
    public void sendUpdate(@NotNull NetworkPacket<?> packet) {
        Intrinsics.checkNotNullParameter(packet, (String)"packet");
        super.sendUpdate(packet);
        if (packet instanceof BattleEndPacket) {
            PokemonEntity pokemonEntity = this.getEntity();
            if (pokemonEntity == null) {
                return;
            }
            PokemonEntity entity2 = pokemonEntity;
            entity2.setBattleId(null);
        }
    }

    @Override
    @Nullable
    public PokemonEntity getEntity() {
        return this.pokemon.getEntity();
    }

    @Override
    @NotNull
    public ActorType getType() {
        return this.type;
    }
}

