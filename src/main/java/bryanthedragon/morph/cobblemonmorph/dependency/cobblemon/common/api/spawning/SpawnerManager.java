/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.server.MinecraftServer
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnerManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.Spawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.TickingSpawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DistributionUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.gamerules.CobblemonGameRules;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0016\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u001b\u0010\u000eJ\u0019\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J'\u0010\n\u001a\r\u0012\t\u0012\u00078\u0000\u00a2\u0006\u0002\b\t0\b\"\n\b\u0000\u0010\u0007\u0018\u0001*\u00020\u0004H\u0086\b\u00a2\u0006\u0004\b\n\u0010\u000bJ\u000f\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ\u000f\u0010\u000f\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u000eJ\u0017\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0017\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0012R\u001d\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u000bR\u001d\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00040\u00148\u0006\u00a2\u0006\f\n\u0004\b\u0019\u0010\u0017\u001a\u0004\b\u001a\u0010\u000b\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/api/spawning/SpawnerManager;", "", "", "name", "Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;", "getSpawnerByName", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;", "T", "", "Lkotlin/internal/NoInfer;", "getSpawnersOfType", "()Ljava/util/List;", "", "onServerStarted", "()V", "onServerTick", "spawner", "registerSpawner", "(Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;)V", "unregisterSpawner", "", "Lcom/cobblemon/mod/common/api/spawning/influence/SpawningInfluence;", "influences", "Ljava/util/List;", "getInfluences", "spawners", "getSpawners", "<init>", "common"})
@SourceDebugExtension(value={"SMAP\nSpawnerManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawnerManager.kt\ncom/cobblemon/mod/common/api/spawning/SpawnerManager\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,60:1\n31#1:73\n800#2,11:61\n800#2,11:74\n1855#2,2:85\n1#3:72\n*S KotlinDebug\n*F\n+ 1 SpawnerManager.kt\ncom/cobblemon/mod/common/api/spawning/SpawnerManager\n*L\n58#1:73\n31#1:61,11\n58#1:74,11\n58#1:85,2\n*E\n"})
public class SpawnerManager {
    @NotNull
    private final List<Spawner> spawners = new ArrayList();
    @NotNull
    private final List<SpawningInfluence> influences = new ArrayList();

    @NotNull
    public final List<Spawner> getSpawners() {
        return this.spawners;
    }

    @NotNull
    public final List<SpawningInfluence> getInfluences() {
        return this.influences;
    }

    /*
     * WARNING - void declaration
     */
    public final /* synthetic */ <T extends Spawner> List<T> getSpawnersOfType() {
        void $this$filterIsInstanceTo$iv$iv;
        boolean $i$f$getSpawnersOfType = false;
        Iterable $this$filterIsInstance$iv = this.getSpawners();
        boolean $i$f$filterIsInstance = false;
        Iterable iterable = $this$filterIsInstance$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            Intrinsics.reifiedOperationMarker((int)3, (String)"T");
            if (!(element$iv$iv instanceof Object)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    @Nullable
    public Spawner getSpawnerByName(@NotNull String name) {
        Object v0;
        block1: {
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            Iterable iterable = this.spawners;
            for (Object t : iterable) {
                Spawner it = (Spawner)t;
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)it.getName(), (Object)name)) continue;
                v0 = t;
                break block1;
            }
            v0 = null;
        }
        return v0;
    }

    public void registerSpawner(@NotNull Spawner spawner) {
        Intrinsics.checkNotNullParameter((Object)spawner, (String)"spawner");
        this.spawners.add(spawner);
        if (!(spawner instanceof TickingSpawner)) {
            spawner.getInfluences().addAll((Collection<SpawningInfluence>)this.influences);
        }
    }

    public void unregisterSpawner(@NotNull Spawner spawner) {
        Intrinsics.checkNotNullParameter((Object)spawner, (String)"spawner");
        this.spawners.remove(spawner);
        if (!(spawner instanceof TickingSpawner)) {
            spawner.getInfluences().removeAll((Collection)this.influences);
        }
    }

    public void onServerStarted() {
        this.spawners.clear();
    }

    /*
     * WARNING - void declaration
     */
    public void onServerTick() {
        void $this$filterIsInstanceTo$iv$iv$iv;
        block6: {
            block5: {
                if (!Cobblemon.INSTANCE.getConfig().getEnableSpawning()) break block5;
                MinecraftServer minecraftServer = DistributionUtilsKt.server();
                boolean bl = minecraftServer != null && (minecraftServer = minecraftServer.m_129900_()) != null ? !minecraftServer.m_46207_(CobblemonGameRules.DO_POKEMON_SPAWNING) : false;
                if (!bl) break block6;
            }
            return;
        }
        this.influences.removeIf(arg_0 -> SpawnerManager.onServerTick$lambda$1(onServerTick.1.INSTANCE, arg_0));
        SpawnerManager this_$iv = this;
        boolean $i$f$getSpawnersOfType = false;
        Iterable $this$filterIsInstance$iv$iv = this_$iv.getSpawners();
        boolean $i$f$filterIsInstance = false;
        Iterable iterable = $this$filterIsInstance$iv$iv;
        Collection destination$iv$iv$iv = new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        for (Object element$iv$iv$iv : $this$filterIsInstanceTo$iv$iv$iv) {
            if (!(element$iv$iv$iv instanceof TickingSpawner)) continue;
            destination$iv$iv$iv.add(element$iv$iv$iv);
        }
        Iterable $this$forEach$iv = (List)destination$iv$iv$iv;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            TickingSpawner p0 = (TickingSpawner)element$iv;
            boolean bl = false;
            p0.tick();
        }
    }

    private static final boolean onServerTick$lambda$1(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }
}

