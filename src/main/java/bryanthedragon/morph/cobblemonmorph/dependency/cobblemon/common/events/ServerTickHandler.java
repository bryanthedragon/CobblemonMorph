/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.events;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnerManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0016\u0010\b\u001a\u00020\u00078\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\b\u0010\t\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/events/ServerTickHandler;", "", "Lnet/minecraft/server/MinecraftServer;", "server", "", "onTick", "(Lnet/minecraft/server/MinecraftServer;)V", "", "secondsTick", "I", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nServerTickHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ServerTickHandler.kt\ncom/cobblemon/mod/common/events/ServerTickHandler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,34:1\n1855#2,2:35\n*S KotlinDebug\n*F\n+ 1 ServerTickHandler.kt\ncom/cobblemon/mod/common/events/ServerTickHandler\n*L\n20#1:35,2\n*E\n"})
public final class ServerTickHandler {
    @NotNull
    public static final ServerTickHandler INSTANCE = new ServerTickHandler();
    private static int secondsTick;

    private ServerTickHandler() {
    }

    public final void onTick(@NotNull MinecraftServer server) {
        Intrinsics.checkNotNullParameter((Object)server, (String)"server");
        Iterable $this$forEach$iv = Cobblemon.INSTANCE.getBestSpawner().getSpawnerManagers();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            SpawnerManager it = (SpawnerManager)element$iv;
            boolean bl = false;
            it.onServerTick();
        }
        BattleRegistry.INSTANCE.tick();
        int n = secondsTick;
        secondsTick = n + 1;
        if (secondsTick == 20) {
            secondsTick = 0;
            for (ServerPlayer player : server.m_6846_().m_11314_()) {
                Intrinsics.checkNotNullExpressionValue((Object)player, (String)"player");
                PlayerExtensionsKt.party(player).onSecondPassed(player);
            }
        }
    }
}

