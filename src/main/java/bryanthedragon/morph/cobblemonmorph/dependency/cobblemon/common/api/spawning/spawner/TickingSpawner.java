/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnerManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.EntitySpawnResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnPool;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.selection.FlatContextWeightedSelector;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.selection.SpawningSelector;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.Spawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.TickingSpawner;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\u0007\n\u0002\b\u000f\b&\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010?\u001a\u00020>\u0012\u0006\u0010M\u001a\u00020\u0015\u0012\u0006\u0010:\u001a\u000209\u00a2\u0006\u0004\b^\u0010_J+\u0010\u0007\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\u0006\u0010\u0005\u001a\u00028\u0000H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u000f\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0015\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0013\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\r0\u0010\u00a2\u0006\u0004\b\u0011\u0010\u000fJ\u0011\u0010\u0013\u001a\u0004\u0018\u00010\u0012H\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u000f\u0010\u0016\u001a\u00020\u0015H\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u000f\u0010\u0019\u001a\u00020\u0018H\u0016\u00a2\u0006\u0004\b\u0019\u0010\u001aJ%\u0010 \u001a\u0010\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u001f\u0018\u00010\u001d2\u0006\u0010\u001c\u001a\u00020\u001bH&\u00a2\u0006\u0004\b \u0010!J\u0017\u0010#\u001a\u00020\u00062\u0006\u0010\"\u001a\u00020\u0015H\u0016\u00a2\u0006\u0004\b#\u0010$J\u0017\u0010&\u001a\u00020\u00062\u0006\u0010%\u001a\u00020\u0018H\u0016\u00a2\u0006\u0004\b&\u0010'J\u000f\u0010(\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b(\u0010)R\"\u0010*\u001a\u00020\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b*\u0010+\u001a\u0004\b,\u0010\u000b\"\u0004\b-\u0010.R \u0010/\u001a\b\u0012\u0004\u0012\u00020\r0\f8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b/\u00100\u001a\u0004\b1\u0010\u000fR\"\u00103\u001a\u0002028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b3\u00104\u001a\u0004\b5\u00106\"\u0004\b7\u00108R\u0017\u0010:\u001a\u0002098\u0006\u00a2\u0006\f\n\u0004\b:\u0010;\u001a\u0004\b<\u0010=R\u001a\u0010?\u001a\u00020>8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b?\u0010@\u001a\u0004\bA\u0010BR\"\u0010D\u001a\u00020C8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bD\u0010E\u001a\u0004\bF\u0010G\"\u0004\bH\u0010IR\u0016\u0010%\u001a\u00020\u00188\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b%\u0010JR\u001d\u0010K\u001a\b\u0012\u0004\u0012\u00020\u00120\f8\u0006\u00a2\u0006\f\n\u0004\bK\u00100\u001a\u0004\bL\u0010\u000fR\"\u0010M\u001a\u00020\u00158\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bM\u0010N\u001a\u0004\bO\u0010\u0017\"\u0004\bP\u0010$R\"\u0010R\u001a\u00020Q8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bR\u0010S\u001a\u0004\bT\u0010U\"\u0004\bV\u0010WR\u001c\u0010Z\u001a\u00020Q8&@&X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\bX\u0010U\"\u0004\bY\u0010WR\"\u0010[\u001a\u00020Q8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b[\u0010S\u001a\u0004\b\\\u0010U\"\u0004\b]\u0010W\u00a8\u0006`"}, d2={"Lcom/cobblemon/mod/common/api/spawning/spawner/TickingSpawner;", "Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;", "R", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnAction;", "action", "result", "", "afterSpawn", "(Lcom/cobblemon/mod/common/api/spawning/detail/SpawnAction;Ljava/lang/Object;)V", "", "canSpawn", "()Z", "", "Lcom/cobblemon/mod/common/api/spawning/influence/SpawningInfluence;", "copyInfluences", "()Ljava/util/List;", "", "getAllInfluences", "Lnet/minecraft/world/entity/Entity;", "getCauseEntity", "()Lnet/minecraft/world/entity/Entity;", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnPool;", "getSpawnPool", "()Lcom/cobblemon/mod/common/api/spawning/detail/SpawnPool;", "Lcom/cobblemon/mod/common/api/spawning/selection/SpawningSelector;", "getSpawningSelector", "()Lcom/cobblemon/mod/common/api/spawning/selection/SpawningSelector;", "Lcom/cobblemon/mod/common/api/spawning/SpawnCause;", "cause", "Lkotlin/Pair;", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "run", "(Lcom/cobblemon/mod/common/api/spawning/SpawnCause;)Lkotlin/Pair;", "spawnPool", "setSpawnPool", "(Lcom/cobblemon/mod/common/api/spawning/detail/SpawnPool;)V", "selector", "setSpawningSelector", "(Lcom/cobblemon/mod/common/api/spawning/selection/SpawningSelector;)V", "tick", "()V", "active", "Z", "getActive", "setActive", "(Z)V", "influences", "Ljava/util/List;", "getInfluences", "", "lastSpawnTime", "J", "getLastSpawnTime", "()J", "setLastSpawnTime", "(J)V", "Lcom/cobblemon/mod/common/api/spawning/SpawnerManager;", "manager", "Lcom/cobblemon/mod/common/api/spawning/SpawnerManager;", "getManager", "()Lcom/cobblemon/mod/common/api/spawning/SpawnerManager;", "", "name", "Ljava/lang/String;", "getName", "()Ljava/lang/String;", "", "removalCheckTicks", "I", "getRemovalCheckTicks", "()I", "setRemovalCheckTicks", "(I)V", "Lcom/cobblemon/mod/common/api/spawning/selection/SpawningSelector;", "spawnedEntities", "getSpawnedEntities", "spawns", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnPool;", "getSpawns", "setSpawns", "", "tickTimerMultiplier", "F", "getTickTimerMultiplier", "()F", "setTickTimerMultiplier", "(F)V", "getTicksBetweenSpawns", "setTicksBetweenSpawns", "ticksBetweenSpawns", "ticksUntilNextSpawn", "getTicksUntilNextSpawn", "setTicksUntilNextSpawn", "<init>", "(Ljava/lang/String;Lcom/cobblemon/mod/common/api/spawning/detail/SpawnPool;Lcom/cobblemon/mod/common/api/spawning/SpawnerManager;)V", "common"})
public abstract class TickingSpawner
implements Spawner {
    @NotNull
    private final String name;
    @NotNull
    private SpawnPool spawns;
    @NotNull
    private final SpawnerManager manager;
    @NotNull
    private SpawningSelector selector;
    @NotNull
    private final List<SpawningInfluence> influences;
    private boolean active;
    @NotNull
    private final List<Entity> spawnedEntities;
    private long lastSpawnTime;
    private float ticksUntilNextSpawn;
    private float tickTimerMultiplier;
    private int removalCheckTicks;

    public TickingSpawner(@NotNull String name, @NotNull SpawnPool spawns2, @NotNull SpawnerManager manager) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)spawns2, (String)"spawns");
        Intrinsics.checkNotNullParameter((Object)manager, (String)"manager");
        this.name = name;
        this.spawns = spawns2;
        this.manager = manager;
        this.selector = new FlatContextWeightedSelector();
        this.influences = new ArrayList();
        this.active = true;
        this.spawnedEntities = new ArrayList();
        this.ticksUntilNextSpawn = 100.0f;
        this.tickTimerMultiplier = 1.0f;
    }

    @Override
    @NotNull
    public String getName() {
        return this.name;
    }

    @NotNull
    public final SpawnPool getSpawns() {
        return this.spawns;
    }

    public final void setSpawns(@NotNull SpawnPool spawnPool) {
        Intrinsics.checkNotNullParameter((Object)spawnPool, (String)"<set-?>");
        this.spawns = spawnPool;
    }

    @NotNull
    public final SpawnerManager getManager() {
        return this.manager;
    }

    @Override
    @NotNull
    public List<SpawningInfluence> getInfluences() {
        return this.influences;
    }

    @Override
    public boolean canSpawn() {
        return this.active;
    }

    @Override
    @NotNull
    public SpawningSelector getSpawningSelector() {
        return this.selector;
    }

    @Override
    public void setSpawningSelector(@NotNull SpawningSelector selector2) {
        Intrinsics.checkNotNullParameter((Object)selector2, (String)"selector");
        this.selector = selector2;
    }

    @Override
    @NotNull
    public SpawnPool getSpawnPool() {
        return this.spawns;
    }

    @Override
    public void setSpawnPool(@NotNull SpawnPool spawnPool) {
        Intrinsics.checkNotNullParameter((Object)spawnPool, (String)"spawnPool");
        this.spawns = spawnPool;
    }

    @Nullable
    public abstract Pair<SpawningContext, SpawnDetail> run(@NotNull SpawnCause var1);

    public final boolean getActive() {
        return this.active;
    }

    public final void setActive(boolean bl) {
        this.active = bl;
    }

    @NotNull
    public final List<Entity> getSpawnedEntities() {
        return this.spawnedEntities;
    }

    public final long getLastSpawnTime() {
        return this.lastSpawnTime;
    }

    public final void setLastSpawnTime(long l) {
        this.lastSpawnTime = l;
    }

    public final float getTicksUntilNextSpawn() {
        return this.ticksUntilNextSpawn;
    }

    public final void setTicksUntilNextSpawn(float f) {
        this.ticksUntilNextSpawn = f;
    }

    public abstract float getTicksBetweenSpawns();

    public abstract void setTicksBetweenSpawns(float var1);

    public final float getTickTimerMultiplier() {
        return this.tickTimerMultiplier;
    }

    public final void setTickTimerMultiplier(float f) {
        this.tickTimerMultiplier = f;
    }

    public final int getRemovalCheckTicks() {
        return this.removalCheckTicks;
    }

    public final void setRemovalCheckTicks(int n) {
        this.removalCheckTicks = n;
    }

    public void tick() {
        int n = this.removalCheckTicks;
        this.removalCheckTicks = n + 1;
        this.getInfluences().removeIf(arg_0 -> TickingSpawner.tick$lambda$0(tick.1.INSTANCE, arg_0));
        if (this.removalCheckTicks == 60) {
            this.spawnedEntities.removeIf(arg_0 -> TickingSpawner.tick$lambda$1(tick.2.INSTANCE, arg_0));
            this.removalCheckTicks = 0;
        }
        if (!this.active) {
            return;
        }
        this.ticksUntilNextSpawn -= this.tickTimerMultiplier;
        if (this.ticksUntilNextSpawn <= 0.0f) {
            Pair<SpawningContext, SpawnDetail> spawn = this.run(new SpawnCause(this, this.chooseBucket(), this.getCauseEntity()));
            this.ticksUntilNextSpawn = this.getTicksBetweenSpawns();
            if (spawn != null) {
                SpawningContext ctx = (SpawningContext)spawn.getFirst();
                SpawnDetail detail = (SpawnDetail)spawn.getSecond();
                SpawnAction<?> spawnAction = detail.doSpawn(ctx);
                spawnAction.complete();
            }
        }
    }

    @Override
    public <R> void afterSpawn(@NotNull SpawnAction<R> action2, R result) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        Spawner.DefaultImpls.afterSpawn(this, action2, result);
        if (result instanceof EntitySpawnResult) {
            this.spawnedEntities.addAll((Collection<Entity>)((EntitySpawnResult)result).getEntities());
        }
        this.lastSpawnTime = System.currentTimeMillis();
    }

    @Nullable
    public Entity getCauseEntity() {
        return null;
    }

    @NotNull
    public final List<SpawningInfluence> getAllInfluences() {
        return CollectionsKt.plus((Collection)this.getInfluences(), (Iterable)this.manager.getInfluences());
    }

    @Override
    @NotNull
    public List<SpawningInfluence> copyInfluences() {
        return CollectionsKt.toMutableList((Collection)this.getAllInfluences());
    }

    @Override
    @NotNull
    public List<SpawnDetail> getMatchingSpawns(@NotNull SpawningContext ctx) {
        return Spawner.DefaultImpls.getMatchingSpawns(this, ctx);
    }

    @Override
    @NotNull
    public SpawnBucket chooseBucket() {
        return Spawner.DefaultImpls.chooseBucket(this);
    }

    private static final boolean tick$lambda$0(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }

    private static final boolean tick$lambda$1(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }
}

