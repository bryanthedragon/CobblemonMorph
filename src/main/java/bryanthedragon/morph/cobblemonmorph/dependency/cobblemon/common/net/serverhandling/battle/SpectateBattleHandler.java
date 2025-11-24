/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PlayerBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMessagePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMusicPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.battle.SpectateBattlePacket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012J'\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\nR\u001f\u0010\r\u001a\n \f*\u0004\u0018\u00010\u000b0\u000b8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/net/serverhandling/battle/SpectateBattleHandler;", "Lcom/cobblemon/mod/common/api/net/ServerNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/server/battle/SpectateBattlePacket;", "packet", "Lnet/minecraft/server/MinecraftServer;", "server", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/server/battle/SpectateBattlePacket;Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/server/level/ServerPlayer;)V", "Lorg/apache/logging/log4j/Logger;", "kotlin.jvm.PlatformType", "LOGGER", "Lorg/apache/logging/log4j/Logger;", "getLOGGER", "()Lorg/apache/logging/log4j/Logger;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nSpectateBattleHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpectateBattleHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/battle/SpectateBattleHandler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,44:1\n800#2,11:45\n288#2,2:56\n1#3:58\n*S KotlinDebug\n*F\n+ 1 SpectateBattleHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/battle/SpectateBattleHandler\n*L\n33#1:45,11\n33#1:56,2\n*E\n"})
public final class SpectateBattleHandler
implements ServerNetworkPacketHandler<SpectateBattlePacket> {
    @NotNull
    public static final SpectateBattleHandler INSTANCE = new SpectateBattleHandler();
    private static final Logger LOGGER = LogManager.getLogger();

    private SpectateBattleHandler() {
    }

    public final Logger getLOGGER() {
        return LOGGER;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void handle(@NotNull SpectateBattlePacket packet, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        Intrinsics.checkNotNullParameter((Object)server, (String)"server");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        PokemonBattle battle2 = BattleRegistry.INSTANCE.getBattleByParticipatingPlayerId(packet.getTargetedEntityId());
        if (battle2 != null && Cobblemon.INSTANCE.getConfig().getAllowSpectating()) {
            Object v0;
            block5: {
                void $this$firstOrNull$iv;
                Iterator $this$filterIsInstanceTo$iv$iv;
                Iterable $this$filterIsInstance$iv = battle2.getActors();
                boolean $i$f$filterIsInstance = false;
                Iterable iterable = $this$filterIsInstance$iv;
                Collection destination$iv$iv = new ArrayList();
                boolean $i$f$filterIsInstanceTo = false;
                Iterator iterator = $this$filterIsInstanceTo$iv$iv.iterator();
                while (iterator.hasNext()) {
                    Object element$iv$iv = iterator.next();
                    if (!(element$iv$iv instanceof PlayerBattleActor)) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                $this$filterIsInstance$iv = (List)destination$iv$iv;
                boolean $i$f$firstOrNull = false;
                for (Object element$iv : $this$firstOrNull$iv) {
                    PlayerBattleActor it = (PlayerBattleActor)element$iv;
                    boolean bl = false;
                    if (!Intrinsics.areEqual((Object)it.getUuid(), (Object)packet.getTargetedEntityId())) continue;
                    v0 = element$iv;
                    break block5;
                }
                v0 = null;
            }
            PlayerBattleActor target = v0;
            Set<UUID> set2 = battle2.getSpectators();
            UUID uUID = player.m_20148_();
            Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
            set2.add(uUID);
            CobblemonNetwork.INSTANCE.sendPacket(player, new BattleInitializePacket(battle2, null));
            CobblemonNetwork.INSTANCE.sendPacket(player, new BattleMessagePacket(battle2.getChatLog()));
            PlayerBattleActor playerBattleActor = target;
            if (playerBattleActor != null && (playerBattleActor = playerBattleActor.getBattleTheme()) != null) {
                PlayerBattleActor it = playerBattleActor;
                boolean bl = false;
                CobblemonNetwork.INSTANCE.sendPacket(player, new BattleMusicPacket((SoundEvent)it, 0.0f, 0.0f, 6, null));
            }
        } else {
            LOGGER.error("Battle of player id " + packet.getTargetedEntityId() + " not found (" + player.m_20148_() + " tried spectating)");
        }
    }

    @Override
    public void handleOnNettyThread(@NotNull SpectateBattlePacket packet, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet, server, player);
    }
}

