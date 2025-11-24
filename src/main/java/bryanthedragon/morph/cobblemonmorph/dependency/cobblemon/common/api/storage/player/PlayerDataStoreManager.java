/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Player
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ScheduledTask;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ServerTaskTracker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.factory.JsonPlayerDataStoreFactory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.factory.PlayerDataStoreFactory;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u001c\u0010\u000fJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u0007\u00a2\u0006\u0004\b\t\u0010\nJ\u000f\u0010\f\u001a\u00020\u000bH\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\r\u0010\u000e\u001a\u00020\b\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0015\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0015\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u0013\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0015\u0010\u0019\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\u0017\u00a2\u0006\u0004\b\u0019\u0010\u001aR\u0016\u0010\u0014\u001a\u00020\u00138\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\u001b\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/api/storage/player/PlayerDataStoreManager;", "", "Lnet/minecraft/world/entity/player/Player;", "player", "Lcom/cobblemon/mod/common/api/storage/player/PlayerData;", "get", "(Lnet/minecraft/world/entity/player/Player;)Lcom/cobblemon/mod/common/api/storage/player/PlayerData;", "Lnet/minecraft/server/level/ServerPlayer;", "", "onPlayerDisconnect", "(Lnet/minecraft/server/level/ServerPlayer;)V", "Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask;", "registerSaveScheduler", "()Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask;", "saveAll", "()V", "playerData", "saveSingle", "(Lcom/cobblemon/mod/common/api/storage/player/PlayerData;)V", "Lcom/cobblemon/mod/common/api/storage/player/factory/PlayerDataStoreFactory;", "factory", "setFactory", "(Lcom/cobblemon/mod/common/api/storage/player/factory/PlayerDataStoreFactory;)V", "Lnet/minecraft/server/MinecraftServer;", "server", "setup", "(Lnet/minecraft/server/MinecraftServer;)V", "Lcom/cobblemon/mod/common/api/storage/player/factory/PlayerDataStoreFactory;", "<init>", "common"})
public final class PlayerDataStoreManager {
    @NotNull
    private PlayerDataStoreFactory factory = new JsonPlayerDataStoreFactory();

    public final void setFactory(@NotNull PlayerDataStoreFactory factory) {
        Intrinsics.checkNotNullParameter((Object)factory, (String)"factory");
        this.factory = factory;
    }

    private final ScheduledTask registerSaveScheduler() {
        return new ScheduledTask.Builder().execute((Function1<? super ScheduledTask, Unit>)((Function1)new Function1<ScheduledTask, Unit>(this){
            final /* synthetic */ PlayerDataStoreManager this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            public final void invoke(@NotNull ScheduledTask it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                this.this$0.saveAll();
            }
        })).delay(30.0f).interval(120.0f).infiniteIterations().tracker(ServerTaskTracker.INSTANCE).build();
    }

    public final void setup(@NotNull MinecraftServer server) {
        block0: {
            Intrinsics.checkNotNullParameter((Object)server, (String)"server");
            this.registerSaveScheduler();
            PlayerDataStoreFactory playerDataStoreFactory = this.factory;
            JsonPlayerDataStoreFactory jsonPlayerDataStoreFactory = playerDataStoreFactory instanceof JsonPlayerDataStoreFactory ? (JsonPlayerDataStoreFactory)playerDataStoreFactory : null;
            if (jsonPlayerDataStoreFactory == null) break block0;
            jsonPlayerDataStoreFactory.setup(server);
        }
    }

    @NotNull
    public final PlayerData get(@NotNull Player player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        UUID uUID = player.m_20148_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
        return this.factory.load(uUID);
    }

    public final void saveAll() {
        this.factory.saveAll();
    }

    public final void saveSingle(@NotNull PlayerData playerData) {
        Intrinsics.checkNotNullParameter((Object)playerData, (String)"playerData");
        this.factory.save(playerData);
    }

    public final void onPlayerDisconnect(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        UUID uUID = player.m_20148_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
        this.factory.onPlayerDisconnect(uUID);
    }
}

