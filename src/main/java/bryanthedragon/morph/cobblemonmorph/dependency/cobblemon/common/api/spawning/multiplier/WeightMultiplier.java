/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.multiplier;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0012\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u001b\u0010\u001cJ'\u0010\b\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\b\u0010\tR,\u0010\f\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000b0\n8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R,\u0010\u0012\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000b0\n8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0012\u0010\r\u001a\u0004\b\u0013\u0010\u000f\"\u0004\b\u0014\u0010\u0011R\"\u0010\u0015\u001a\u00020\u00068\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/api/spawning/multiplier/WeightMultiplier;", "Lcom/cobblemon/mod/common/api/spawning/influence/SpawningInfluence;", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "detail", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "ctx", "", "weight", "affectWeight", "(Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;F)F", "", "Lcom/cobblemon/mod/common/api/spawning/condition/SpawningCondition;", "anticonditions", "Ljava/util/List;", "getAnticonditions", "()Ljava/util/List;", "setAnticonditions", "(Ljava/util/List;)V", "conditions", "getConditions", "setConditions", "multiplier", "F", "getMultiplier", "()F", "setMultiplier", "(F)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nWeightMultiplier.kt\nKotlin\n*S Kotlin\n*F\n+ 1 WeightMultiplier.kt\ncom/cobblemon/mod/common/api/spawning/multiplier/WeightMultiplier\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,38:1\n1747#2,3:39\n2624#2,3:42\n*S KotlinDebug\n*F\n+ 1 WeightMultiplier.kt\ncom/cobblemon/mod/common/api/spawning/multiplier/WeightMultiplier\n*L\n34#1:39,3\n35#1:42,3\n*E\n"})
public final class WeightMultiplier
implements SpawningInfluence {
    @NotNull
    private List<SpawningCondition<?>> conditions = new ArrayList();
    @NotNull
    private List<SpawningCondition<?>> anticonditions = new ArrayList();
    private float multiplier = 1.0f;

    @NotNull
    public final List<SpawningCondition<?>> getConditions() {
        return this.conditions;
    }

    public final void setConditions(@NotNull List<SpawningCondition<?>> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.conditions = list;
    }

    @NotNull
    public final List<SpawningCondition<?>> getAnticonditions() {
        return this.anticonditions;
    }

    public final void setAnticonditions(@NotNull List<SpawningCondition<?>> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.anticonditions = list;
    }

    public final float getMultiplier() {
        return this.multiplier;
    }

    public final void setMultiplier(float f) {
        this.multiplier = f;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public float affectWeight(@NotNull SpawnDetail detail, @NotNull SpawningContext ctx, float weight) {
        block9: {
            block10: {
                block8: {
                    Intrinsics.checkNotNullParameter((Object)detail, (String)"detail");
                    Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
                    if (this.conditions.isEmpty()) break block10;
                    $this$any$iv = this.conditions;
                    $i$f$any = false;
                    if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                        v0 = false;
                    } else {
                        for (T element$iv : $this$any$iv) {
                            it = (SpawningCondition)element$iv;
                            $i$a$-any-WeightMultiplier$affectWeight$meetsConditions$1 = false;
                            if (!it.isSatisfiedBy(ctx)) continue;
                            v0 = true;
                            break block8;
                        }
                        v0 = false;
                    }
                }
                if (!v0) ** GOTO lbl-1000
            }
            if (this.anticonditions.isEmpty()) ** GOTO lbl-1000
            $this$none$iv = this.anticonditions;
            $i$f$none = false;
            if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                v1 = true;
            } else {
                for (T element$iv : $this$none$iv) {
                    it = (SpawningCondition)element$iv;
                    $i$a$-none-WeightMultiplier$affectWeight$meetsConditions$2 = false;
                    if (!it.isSatisfiedBy(ctx)) continue;
                    v1 = false;
                    break block9;
                }
                v1 = true;
            }
        }
        if (v1) lbl-1000:
        // 2 sources

        {
            v2 = true;
        } else lbl-1000:
        // 2 sources

        {
            v2 = false;
        }
        meetsConditions = v2;
        return meetsConditions != false ? this.multiplier * weight : weight;
    }

    @Override
    public boolean isExpired() {
        return SpawningInfluence.DefaultImpls.isExpired(this);
    }

    @Override
    public boolean affectSpawnable(@NotNull SpawnDetail detail, @NotNull SpawningContext ctx) {
        return SpawningInfluence.DefaultImpls.affectSpawnable(this, detail, ctx);
    }

    @Override
    public void affectAction(@NotNull SpawnAction<?> action2) {
        SpawningInfluence.DefaultImpls.affectAction(this, action2);
    }

    @Override
    public void affectSpawn(@NotNull Entity entity2) {
        SpawningInfluence.DefaultImpls.affectSpawn(this, entity2);
    }

    @Override
    public float affectBucketWeight(@NotNull SpawnBucket bucket, float weight) {
        return SpawningInfluence.DefaultImpls.affectBucketWeight(this, bucket, weight);
    }

    @Override
    public boolean isAllowedPosition(@NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull SpawningContextCalculator<?, ?> contextCalculator) {
        return SpawningInfluence.DefaultImpls.isAllowedPosition(this, world, pos, contextCalculator);
    }
}

