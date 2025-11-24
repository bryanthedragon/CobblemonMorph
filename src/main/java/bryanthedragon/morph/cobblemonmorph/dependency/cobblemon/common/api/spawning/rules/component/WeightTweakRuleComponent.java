/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.component;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.component.SpawnRuleComponent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.AllSpawnDetailSelector;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.AllSpawningContextSelector;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.SpawnDetailSelector;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.SpawningContextSelector;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u001d\u0010\u001eJ'\u0010\b\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\b\u0010\tR\u0017\u0010\u000b\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u0010\u001a\u00020\u000f8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0015\u001a\u00020\u00148\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018R\u0017\u0010\u0007\u001a\u00020\u00198\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\u001a\u001a\u0004\b\u001b\u0010\u001c\u00a8\u0006\u001f"}, d2={"Lcom/cobblemon/mod/common/api/spawning/rules/component/WeightTweakRuleComponent;", "Lcom/cobblemon/mod/common/api/spawning/rules/component/SpawnRuleComponent;", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "detail", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "ctx", "", "weight", "affectWeight", "(Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;F)F", "Lcom/cobblemon/mod/common/api/spawning/rules/selector/SpawningContextSelector;", "contextSelector", "Lcom/cobblemon/mod/common/api/spawning/rules/selector/SpawningContextSelector;", "getContextSelector", "()Lcom/cobblemon/mod/common/api/spawning/rules/selector/SpawningContextSelector;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "getRuntime", "()Lcom/bedrockk/molang/runtime/MoLangRuntime;", "Lcom/cobblemon/mod/common/api/spawning/rules/selector/SpawnDetailSelector;", "spawnSelector", "Lcom/cobblemon/mod/common/api/spawning/rules/selector/SpawnDetailSelector;", "getSpawnSelector", "()Lcom/cobblemon/mod/common/api/spawning/rules/selector/SpawnDetailSelector;", "Lcom/bedrockk/molang/Expression;", "Lcom/bedrockk/molang/Expression;", "getWeight", "()Lcom/bedrockk/molang/Expression;", "<init>", "()V", "common"})
public final class WeightTweakRuleComponent
implements SpawnRuleComponent {
    @NotNull
    private final SpawnDetailSelector spawnSelector = AllSpawnDetailSelector.INSTANCE;
    @NotNull
    private final SpawningContextSelector contextSelector = AllSpawningContextSelector.INSTANCE;
    @NotNull
    private final Expression weight;
    @NotNull
    private final transient MoLangRuntime runtime;

    public WeightTweakRuleComponent() {
        Expression expression = MoLangExtensionsKt.asExpression("v.weight");
        Intrinsics.checkNotNullExpressionValue((Object)expression, (String)"v.weight\".asExpression()");
        this.weight = expression;
        this.runtime = MoLangFunctions.INSTANCE.setup(new MoLangRuntime());
    }

    @NotNull
    public final SpawnDetailSelector getSpawnSelector() {
        return this.spawnSelector;
    }

    @NotNull
    public final SpawningContextSelector getContextSelector() {
        return this.contextSelector;
    }

    @NotNull
    public final Expression getWeight() {
        return this.weight;
    }

    @NotNull
    public final MoLangRuntime getRuntime() {
        return this.runtime;
    }

    @Override
    public float affectWeight(@NotNull SpawnDetail detail, @NotNull SpawningContext ctx, float weight) {
        float f;
        Intrinsics.checkNotNullParameter((Object)detail, (String)"detail");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        if (this.spawnSelector.selects(detail) && this.contextSelector.selects(ctx)) {
            this.runtime.getEnvironment().setSimpleVariable("spawn", detail.getStruct());
            this.runtime.getEnvironment().setSimpleVariable("weight", new DoubleValue(weight));
            f = MoLangExtensionsKt.resolveFloat(this.runtime, this.weight);
        } else {
            f = weight;
        }
        return f;
    }

    @Override
    public boolean isExpired() {
        return SpawnRuleComponent.DefaultImpls.isExpired(this);
    }

    @Override
    public boolean affectSpawnable(@NotNull SpawnDetail detail, @NotNull SpawningContext ctx) {
        return SpawnRuleComponent.DefaultImpls.affectSpawnable(this, detail, ctx);
    }

    @Override
    public void affectAction(@NotNull SpawnAction<?> action2) {
        SpawnRuleComponent.DefaultImpls.affectAction(this, action2);
    }

    @Override
    public void affectSpawn(@NotNull Entity entity2) {
        SpawnRuleComponent.DefaultImpls.affectSpawn(this, entity2);
    }

    @Override
    public float affectBucketWeight(@NotNull SpawnBucket bucket, float weight) {
        return SpawnRuleComponent.DefaultImpls.affectBucketWeight(this, bucket, weight);
    }

    @Override
    public boolean isAllowedPosition(@NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull SpawningContextCalculator<?, ?> contextCalculator) {
        return SpawnRuleComponent.DefaultImpls.isAllowedPosition(this, world, pos, contextCalculator);
    }
}

