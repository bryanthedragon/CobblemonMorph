/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.entity.SpawnEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.EntitySpawnResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.BlockPosExtensionsKt;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\b\u0012\u0004\u0012\u00020\u00040\u0003B\u0017\u0012\u0006\u0010\u000f\u001a\u00020\u000e\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0011\u0010\u0005\u001a\u0004\u0018\u00018\u0000H&\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0011\u0010\u0007\u001a\u0004\u0018\u00010\u0004H\u0014\u00a2\u0006\u0004\b\u0007\u0010\bR\u001d\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\t8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/api/spawning/detail/SingleEntitySpawnAction;", "Lnet/minecraft/world/entity/Entity;", "T", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnAction;", "Lcom/cobblemon/mod/common/api/spawning/detail/EntitySpawnResult;", "createEntity", "()Lnet/minecraft/world/entity/Entity;", "run", "()Lcom/cobblemon/mod/common/api/spawning/detail/EntitySpawnResult;", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "entity", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getEntity", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "ctx", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "detail", "<init>", "(Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;)V", "common"})
@SourceDebugExtension(value={"SMAP\nSingleEntitySpawnAction.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SingleEntitySpawnAction.kt\ncom/cobblemon/mod/common/api/spawning/detail/SingleEntitySpawnAction\n+ 2 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable\n+ 3 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 4 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 5 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable$postThen$1\n*L\n1#1,56:1\n39#2,2:57\n41#2,2:62\n44#2,3:65\n47#2:70\n17#3,2:59\n19#3:69\n13579#4:61\n13580#4:68\n39#5:64\n*S KotlinDebug\n*F\n+ 1 SingleEntitySpawnAction.kt\ncom/cobblemon/mod/common/api/spawning/detail/SingleEntitySpawnAction\n*L\n34#1:57,2\n34#1:62,2\n34#1:65,3\n34#1:70\n34#1:59,2\n34#1:69\n34#1:61\n34#1:68\n34#1:64\n*E\n"})
public abstract class SingleEntitySpawnAction<T extends Entity>
extends SpawnAction<EntitySpawnResult> {
    @NotNull
    private final SimpleObservable<T> entity;

    /*
     * WARNING - void declaration
     */
    public SingleEntitySpawnAction(@NotNull SpawningContext ctx, @NotNull SpawnDetail detail) {
        void $this$entity_u24lambda_u241;
        SimpleObservable simpleObservable;
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        Intrinsics.checkNotNullParameter((Object)detail, (String)"detail");
        super(ctx, detail);
        SimpleObservable simpleObservable2 = simpleObservable = new SimpleObservable();
        SingleEntitySpawnAction singleEntitySpawnAction = this;
        boolean bl = false;
        Observable.DefaultImpls.subscribe$default((Observable)$this$entity_u24lambda_u241, null, new Function1<T, Unit>(ctx){
            final /* synthetic */ SpawningContext $ctx;
            {
                this.$ctx = $ctx;
                super(1);
            }

            public final void invoke(@NotNull T entity2) {
                Intrinsics.checkNotNullParameter(entity2, (String)"entity");
                this.$ctx.afterSpawn((Entity)entity2);
            }
        }, 1, null);
        singleEntitySpawnAction.entity = simpleObservable;
    }

    @Nullable
    public abstract T createEntity();

    /*
     * WARNING - void declaration
     */
    @Override
    @Nullable
    protected EntitySpawnResult run() {
        EntitySpawnResult entitySpawnResult;
        void this_$iv$iv;
        void $this$iv;
        T t = this.createEntity();
        if (t == null) {
            return null;
        }
        T e = t;
        e.m_146884_(BlockPosExtensionsKt.toVec3d(this.getCtx().getPosition()).m_82520_(0.5, 1.0, 0.5));
        boolean shouldSpawn = false;
        Entity[] entityArray = CobblemonEvents.ENTITY_SPAWN;
        Cancelable event$iv = new SpawnEvent<T>(e, this.getCtx());
        boolean $i$f$postThen = false;
        EventObservable eventObservable = (EventObservable)$this$iv;
        Cancelable[] cancelableArray = new Cancelable[]{event$iv};
        Cancelable[] events$iv$iv = cancelableArray;
        boolean $i$f$post = false;
        this_$iv$iv.emit(Arrays.copyOf(events$iv$iv, events$iv$iv.length));
        Cancelable[] $this$forEach$iv$iv$iv = events$iv$iv;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv$iv$iv.length;
        for (int i = 0; i < n; ++i) {
            Cancelable it;
            Cancelable element$iv$iv$iv;
            Cancelable it$iv = element$iv$iv$iv = $this$forEach$iv$iv$iv[i];
            boolean bl = false;
            if (it$iv.isCanceled()) {
                Cancelable cancelable = it$iv;
                boolean bl2 = false;
                it = cancelable;
                continue;
            }
            it = (SpawnEvent)it$iv;
            boolean bl3 = false;
            this.getCtx().getWorld().m_7967_(e);
            shouldSpawn = true;
        }
        if (shouldSpawn) {
            entityArray = new Entity[]{e};
            this.entity.emit(entityArray);
            entitySpawnResult = new EntitySpawnResult(CollectionsKt.listOf(e));
        } else {
            entitySpawnResult = null;
        }
        return entitySpawnResult;
    }

    @NotNull
    public final SimpleObservable<T> getEntity() {
        return this.entity;
    }
}

