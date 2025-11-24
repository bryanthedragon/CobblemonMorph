/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.MutablePropertyReference1
 *  kotlin.jvm.internal.MutablePropertyReference1Impl
 *  kotlin.jvm.internal.Reflection
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.reflect.KProperty
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.CobblemonSpawnRules;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnerManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnPool;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.SpawnRule;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.PlayerSpawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.PlayerSpawnerFactory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MutableLazy;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MutableLazyKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KProperty;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u001d\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0007\u0010\bRE\u0010\u000e\u001a%\u0012!\u0012\u001f\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u000b\u0012\b\b\f\u0012\u0004\b\b(\u0005\u0012\u0006\u0012\u0004\u0018\u00010\r0\n0\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R+\u0010\u001c\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00148F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001b\u00a8\u0006\u001f"}, d2={"Lcom/cobblemon/mod/common/api/spawning/spawner/PlayerSpawnerFactory;", "", "Lcom/cobblemon/mod/common/api/spawning/SpawnerManager;", "spawnerManager", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lcom/cobblemon/mod/common/api/spawning/spawner/PlayerSpawner;", "create", "(Lcom/cobblemon/mod/common/api/spawning/SpawnerManager;Lnet/minecraft/server/level/ServerPlayer;)Lcom/cobblemon/mod/common/api/spawning/spawner/PlayerSpawner;", "", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "Lcom/cobblemon/mod/common/api/spawning/influence/SpawningInfluence;", "influenceBuilders", "Ljava/util/List;", "getInfluenceBuilders", "()Ljava/util/List;", "setInfluenceBuilders", "(Ljava/util/List;)V", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnPool;", "<set-?>", "spawns$delegate", "Lcom/cobblemon/mod/common/util/MutableLazy;", "getSpawns", "()Lcom/cobblemon/mod/common/api/spawning/detail/SpawnPool;", "setSpawns", "(Lcom/cobblemon/mod/common/api/spawning/detail/SpawnPool;)V", "spawns", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nPlayerSpawnerFactory.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PlayerSpawnerFactory.kt\ncom/cobblemon/mod/common/api/spawning/spawner/PlayerSpawnerFactory\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,59:1\n1603#2,9:60\n1855#2:69\n1856#2:71\n1612#2:72\n766#2:73\n857#2,2:74\n1360#2:76\n1446#2,5:77\n1#3:70\n*S KotlinDebug\n*F\n+ 1 PlayerSpawnerFactory.kt\ncom/cobblemon/mod/common/api/spawning/spawner/PlayerSpawnerFactory\n*L\n50#1:60,9\n50#1:69\n50#1:71\n50#1:72\n54#1:73\n54#1:74,2\n54#1:76\n54#1:77,5\n50#1:70\n*E\n"})
public final class PlayerSpawnerFactory {
    @NotNull
    public static final PlayerSpawnerFactory INSTANCE;
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties;
    @NotNull
    private static final MutableLazy spawns$delegate;
    @NotNull
    private static List<Function1<ServerPlayer, SpawningInfluence>> influenceBuilders;

    private PlayerSpawnerFactory() {
    }

    @NotNull
    public final SpawnPool getSpawns() {
        return (SpawnPool)spawns$delegate.getValue(this, $$delegatedProperties[0]);
    }

    public final void setSpawns(@NotNull SpawnPool spawnPool) {
        Intrinsics.checkNotNullParameter((Object)spawnPool, (String)"<set-?>");
        spawns$delegate.setValue(this, $$delegatedProperties[0], spawnPool);
    }

    @NotNull
    public final List<Function1<ServerPlayer, SpawningInfluence>> getInfluenceBuilders() {
        return influenceBuilders;
    }

    public final void setInfluenceBuilders(@NotNull List<Function1<ServerPlayer, SpawningInfluence>> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        influenceBuilders = list;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final PlayerSpawner create(@NotNull SpawnerManager spawnerManager, @NotNull ServerPlayer player) {
        void $this$flatMapTo$iv$iv;
        void $this$flatMap$iv;
        SpawnRule p0;
        void $this$filterTo$iv$iv;
        Iterable $this$filter$iv;
        PlayerSpawner playerSpawner;
        void $this$mapNotNullTo$iv$iv;
        Intrinsics.checkNotNullParameter((Object)spawnerManager, (String)"spawnerManager");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Iterable $this$mapNotNull$iv = influenceBuilders;
        boolean $i$f$mapNotNull = false;
        Iterable iterable = $this$mapNotNull$iv;
        Iterable destination$iv$iv = new ArrayList();
        boolean $i$f$mapNotNullTo = false;
        void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
        boolean $i$f$forEach22 = false;
        Iterator iterator = $this$forEach$iv$iv$iv.iterator();
        while (iterator.hasNext()) {
            SpawningInfluence it$iv$iv;
            Object element$iv$iv$iv;
            Object element$iv$iv = element$iv$iv$iv = iterator.next();
            boolean bl = false;
            Function1 it = (Function1)element$iv$iv;
            boolean bl2 = false;
            if ((SpawningInfluence)it.invoke((Object)player) == null) continue;
            boolean bl3 = false;
            destination$iv$iv.add(it$iv$iv);
        }
        List influences = (List)destination$iv$iv;
        PlayerSpawner it = playerSpawner = new PlayerSpawner(player, this.getSpawns(), spawnerManager);
        boolean bl = false;
        it.getInfluences().addAll(influences);
        destination$iv$iv = CobblemonSpawnRules.INSTANCE.getRules().values();
        List<SpawningInfluence> list = it.getInfluences();
        boolean $i$f$filter = false;
        void $i$f$forEach22 = $this$filter$iv;
        Collection destination$iv$iv2 = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            p0 = (SpawnRule)element$iv$iv;
            boolean bl4 = false;
            if (!p0.getEnabled()) continue;
            destination$iv$iv2.add(element$iv$iv);
        }
        $this$filter$iv = (List)destination$iv$iv2;
        boolean $i$f$flatMap = false;
        $this$filterTo$iv$iv = $this$flatMap$iv;
        destination$iv$iv2 = new ArrayList();
        boolean $i$f$flatMapTo = false;
        for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
            p0 = (SpawnRule)element$iv$iv;
            boolean bl5 = false;
            Iterable list$iv$iv = p0.getComponents();
            CollectionsKt.addAll((Collection)destination$iv$iv2, (Iterable)list$iv$iv);
        }
        list.addAll((List)destination$iv$iv2);
        return playerSpawner;
    }

    static {
        Object[] objectArray = new KProperty[]{Reflection.mutableProperty1((MutablePropertyReference1)((MutablePropertyReference1)new MutablePropertyReference1Impl(PlayerSpawnerFactory.class, "spawns", "getSpawns()Lcom/cobblemon/mod/common/api/spawning/detail/SpawnPool;", 0)))};
        $$delegatedProperties = objectArray;
        INSTANCE = new PlayerSpawnerFactory();
        spawns$delegate = MutableLazyKt.mutableLazy(spawns.2.INSTANCE);
        objectArray = new Function1[]{influenceBuilders.1.INSTANCE, influenceBuilders.2.INSTANCE};
        influenceBuilders = CollectionsKt.mutableListOf((Object[])objectArray);
    }
}

