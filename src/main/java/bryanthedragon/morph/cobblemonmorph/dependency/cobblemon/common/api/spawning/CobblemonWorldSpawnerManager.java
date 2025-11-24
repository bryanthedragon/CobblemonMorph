/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnerManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.PlayerSpawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.PlayerSpawnerFactory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.PlatformEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DistributionUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.gamerules.CobblemonGameRules;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0007\u0010\u0006R.\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/api/spawning/CobblemonWorldSpawnerManager;", "Lcom/cobblemon/mod/common/api/spawning/SpawnerManager;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "onPlayerLogin", "(Lnet/minecraft/server/level/ServerPlayer;)V", "onPlayerLogout", "", "Ljava/util/UUID;", "Lcom/cobblemon/mod/common/api/spawning/spawner/PlayerSpawner;", "spawnersForPlayers", "Ljava/util/Map;", "getSpawnersForPlayers", "()Ljava/util/Map;", "setSpawnersForPlayers", "(Ljava/util/Map;)V", "<init>", "()V", "common"})
public final class CobblemonWorldSpawnerManager
extends SpawnerManager {
    @NotNull
    public static final CobblemonWorldSpawnerManager INSTANCE = new CobblemonWorldSpawnerManager();
    @NotNull
    private static Map<UUID, PlayerSpawner> spawnersForPlayers = new LinkedHashMap();

    private CobblemonWorldSpawnerManager() {
    }

    @NotNull
    public final Map<UUID, PlayerSpawner> getSpawnersForPlayers() {
        return spawnersForPlayers;
    }

    public final void setSpawnersForPlayers(@NotNull Map<UUID, PlayerSpawner> map) {
        Intrinsics.checkNotNullParameter(map, (String)"<set-?>");
        spawnersForPlayers = map;
    }

    public final void onPlayerLogin(@NotNull ServerPlayer player) {
        block4: {
            block3: {
                Intrinsics.checkNotNullParameter((Object)player, (String)"player");
                if (!Cobblemon.INSTANCE.getConfig().getEnableSpawning()) break block3;
                MinecraftServer minecraftServer = DistributionUtilsKt.server();
                boolean bl = minecraftServer != null && (minecraftServer = minecraftServer.m_129900_()) != null ? !minecraftServer.m_46207_(CobblemonGameRules.DO_POKEMON_SPAWNING) : false;
                if (!bl) break block4;
            }
            return;
        }
        PlayerSpawner spawner = PlayerSpawnerFactory.INSTANCE.create(this, player);
        Map<UUID, PlayerSpawner> map = spawnersForPlayers;
        UUID uUID = player.m_20148_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
        map.put(uUID, spawner);
        this.registerSpawner(spawner);
    }

    public final void onPlayerLogout(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        PlayerSpawner spawner = spawnersForPlayers.get(player.m_20148_());
        if (spawner != null) {
            spawnersForPlayers.remove(player.m_20148_());
            this.unregisterSpawner(spawner);
        }
    }

    static {
        Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_PLAYER_LOGIN, null, 1.INSTANCE, 1, null);
        Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_PLAYER_LOGOUT, null, 2.INSTANCE, 1, null);
    }
}

