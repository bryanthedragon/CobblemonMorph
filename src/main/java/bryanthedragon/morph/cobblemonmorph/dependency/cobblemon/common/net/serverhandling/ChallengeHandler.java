/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingFunctionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleBuilder;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ErroredBattleStart;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleChallengeNotificationPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.BattleChallengePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.ChallengeHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/net/serverhandling/ChallengeHandler;", "Lcom/cobblemon/mod/common/api/net/ServerNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/server/BattleChallengePacket;", "packet", "Lnet/minecraft/server/MinecraftServer;", "server", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/server/BattleChallengePacket;Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/server/level/ServerPlayer;)V", "<init>", "()V", "common"})
public final class ChallengeHandler
implements ServerNetworkPacketHandler<BattleChallengePacket> {
    @NotNull
    public static final ChallengeHandler INSTANCE = new ChallengeHandler();

    private ChallengeHandler() {
    }

    @Override
    public void handle(@NotNull BattleChallengePacket packet, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        LivingEntity owner;
        Entity it;
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        Intrinsics.checkNotNullParameter((Object)server, (String)"server");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        if (player.m_5833_()) {
            return;
        }
        Entity entity2 = player.m_9236_().m_6815_(packet.getTargetedEntityId());
        if (entity2 != null) {
            it = entity2;
            boolean bl = false;
        } else {
            return;
        }
        Entity targetedEntity = it instanceof PokemonEntity && (owner = ((PokemonEntity)it).m_269323_()) != null ? (Entity)owner : it;
        Pokemon pokemon = PlayerExtensionsKt.party(player).get(packet.getSelectedPokemonId());
        UUID uUID = pokemon != null ? pokemon.getUuid() : null;
        if (uUID == null) {
            return;
        }
        UUID leadingPokemon = uUID;
        Entity entity3 = targetedEntity;
        if (entity3 instanceof PokemonEntity) {
            if (!((PokemonEntity)targetedEntity).canBattle((Player)player)) {
                return;
            }
            BattleBuilder.pve$default(BattleBuilder.INSTANCE, player, (PokemonEntity)targetedEntity, leadingPokemon, null, false, false, 0.0f, null, 248, null).ifErrored((Function1<? super ErroredBattleStart, Unit>)((Function1)new Function1<ErroredBattleStart, Unit>(player){
                final /* synthetic */ ServerPlayer $player;
                {
                    this.$player = $player;
                    super(1);
                }

                public final void invoke(@NotNull ErroredBattleStart it) {
                    Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                    it.sendTo((Entity)this.$player, (Function1<? super MutableComponent, ? extends MutableComponent>)((Function1)handle.1.INSTANCE));
                }
            }));
        } else if (entity3 instanceof ServerPlayer) {
            UUID existingChallengePokemon;
            BattleRegistry.BattleChallenge existingChallenge;
            if (Intrinsics.areEqual((Object)player, (Object)targetedEntity)) {
                return;
            }
            BattleRegistry.BattleChallenge battleChallenge = existingChallenge = BattleRegistry.INSTANCE.getPvpChallenges().get(((ServerPlayer)targetedEntity).m_20148_());
            UUID uUID2 = existingChallengePokemon = battleChallenge != null ? battleChallenge.getSelectedPokemonId() : null;
            if (existingChallenge != null && !existingChallenge.isExpired() && Intrinsics.areEqual((Object)existingChallenge.getChallengedPlayerUUID(), (Object)player.m_20148_())) {
                PlayerPartyStore playerPartyStore = PlayerExtensionsKt.party((ServerPlayer)targetedEntity);
                UUID uUID3 = existingChallengePokemon;
                Intrinsics.checkNotNull((Object)uUID3);
                if (playerPartyStore.get(uUID3) == null) {
                    if (CollectionsKt.none((Iterable)PlayerExtensionsKt.party((ServerPlayer)targetedEntity))) {
                        player.m_213846_((Component)LocalizationUtilsKt.battleLang("error.no_pokemon_opponent", new Object[0]));
                        targetedEntity.m_213846_((Component)LocalizationUtilsKt.battleLang("error.no_pokemon", new Object[0]));
                        UUID uUID4 = ((ServerPlayer)targetedEntity).m_20148_();
                        Intrinsics.checkNotNullExpressionValue((Object)uUID4, (String)"targetedEntity.uuid");
                        BattleRegistry.removeChallenge$default(BattleRegistry.INSTANCE, uUID4, null, 2, null);
                        return;
                    }
                    existingChallengePokemon = ((Pokemon)CollectionsKt.first((Iterable)PlayerExtensionsKt.party((ServerPlayer)targetedEntity))).getUuid();
                }
                BattleBuilder.pvp1v1$default(BattleBuilder.INSTANCE, player, (ServerPlayer)targetedEntity, leadingPokemon, existingChallengePokemon, null, false, false, null, 240, null);
                UUID uUID5 = ((ServerPlayer)targetedEntity).m_20148_();
                Intrinsics.checkNotNullExpressionValue((Object)uUID5, (String)"targetedEntity.uuid");
                BattleRegistry.removeChallenge$default(BattleRegistry.INSTANCE, uUID5, null, 2, null);
            } else {
                UUID uUID6 = UUID.randomUUID();
                Intrinsics.checkNotNullExpressionValue((Object)uUID6, (String)"randomUUID()");
                UUID uUID7 = ((ServerPlayer)targetedEntity).m_20148_();
                Intrinsics.checkNotNullExpressionValue((Object)uUID7, (String)"targetedEntity.uuid");
                BattleRegistry.BattleChallenge challenge = new BattleRegistry.BattleChallenge(uUID6, uUID7, leadingPokemon, 0, 8, null);
                Map<UUID, BattleRegistry.BattleChallenge> map = BattleRegistry.INSTANCE.getPvpChallenges();
                UUID uUID8 = player.m_20148_();
                Intrinsics.checkNotNullExpressionValue((Object)uUID8, (String)"player.uuid");
                map.put(uUID8, challenge);
                SchedulingFunctionsKt.afterOnServer$default(0, challenge.getExpiryTimeSeconds(), (Function0)new Function0<Unit>(player, challenge){
                    final /* synthetic */ ServerPlayer $player;
                    final /* synthetic */ BattleRegistry.BattleChallenge $challenge;
                    {
                        this.$player = $player;
                        this.$challenge = $challenge;
                        super(0);
                    }

                    public final void invoke() {
                        UUID uUID = this.$player.m_20148_();
                        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
                        BattleRegistry.INSTANCE.removeChallenge(uUID, this.$challenge.getChallengeId());
                    }
                }, 1, null);
                ServerPlayer serverPlayer = (ServerPlayer)targetedEntity;
                UUID uUID9 = challenge.getChallengeId();
                UUID uUID10 = player.m_20148_();
                Intrinsics.checkNotNullExpressionValue((Object)uUID10, (String)"player.uuid");
                MutableComponent mutableComponent = player.m_7755_().m_6881_();
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"player.name.copy()");
                CobblemonNetwork.INSTANCE.sendPacket(serverPlayer, new BattleChallengeNotificationPacket(uUID9, uUID10, TextKt.aqua(mutableComponent)));
                Object[] objectArray = new Object[1];
                Intrinsics.checkNotNullExpressionValue((Object)((ServerPlayer)targetedEntity).m_7755_(), (String)"targetedEntity.name");
                MutableComponent mutableComponent2 = LocalizationUtilsKt.lang("challenge.sender", objectArray);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent2, (String)"lang(\"challenge.sender\", targetedEntity.name)");
                player.m_213846_((Component)TextKt.yellow(mutableComponent2));
            }
        }
    }

    @Override
    public void handleOnNettyThread(@NotNull BattleChallengePacket packet, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet, server, player);
    }
}

