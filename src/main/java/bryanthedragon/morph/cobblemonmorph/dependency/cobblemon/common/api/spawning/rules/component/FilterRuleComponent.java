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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.BooleanExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\n\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR\u0017\u0010\u000f\u001a\u00020\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0014\u001a\u00020\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0019\u001a\u00020\u00188\u0006\u00a2\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001c\u00a8\u0006\u001f"}, d2={"Lcom/cobblemon/mod/common/api/spawning/rules/component/FilterRuleComponent;", "Lcom/cobblemon/mod/common/api/spawning/rules/component/SpawnRuleComponent;", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "detail", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "ctx", "", "affectSpawnable", "(Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;)Z", "Lcom/bedrockk/molang/Expression;", "allow", "Lcom/bedrockk/molang/Expression;", "getAllow", "()Lcom/bedrockk/molang/Expression;", "Lcom/cobblemon/mod/common/api/spawning/rules/selector/SpawningContextSelector;", "contextSelector", "Lcom/cobblemon/mod/common/api/spawning/rules/selector/SpawningContextSelector;", "getContextSelector", "()Lcom/cobblemon/mod/common/api/spawning/rules/selector/SpawningContextSelector;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "getRuntime", "()Lcom/bedrockk/molang/runtime/MoLangRuntime;", "Lcom/cobblemon/mod/common/api/spawning/rules/selector/SpawnDetailSelector;", "spawnSelector", "Lcom/cobblemon/mod/common/api/spawning/rules/selector/SpawnDetailSelector;", "getSpawnSelector", "()Lcom/cobblemon/mod/common/api/spawning/rules/selector/SpawnDetailSelector;", "<init>", "()V", "common"})
public final class FilterRuleComponent
implements SpawnRuleComponent {
    @NotNull
    private final transient MoLangRuntime runtime = MoLangFunctions.INSTANCE.setup(new MoLangRuntime());
    @NotNull
    private final SpawnDetailSelector spawnSelector = AllSpawnDetailSelector.INSTANCE;
    @NotNull
    private final SpawningContextSelector contextSelector = AllSpawningContextSelector.INSTANCE;
    @NotNull
    private final Expression allow = new BooleanExpression(true);

    @NotNull
    public final MoLangRuntime getRuntime() {
        return this.runtime;
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
    public final Expression getAllow() {
        return this.allow;
    }

    @Override
    public boolean affectSpawnable(@NotNull SpawnDetail detail, @NotNull SpawningContext ctx) {
        boolean bl;
        Intrinsics.checkNotNullParameter((Object)detail, (String)"detail");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        if (this.spawnSelector.selects(detail) && this.contextSelector.selects(ctx)) {
            this.runtime.getEnvironment().setSimpleVariable("spawn", detail.getStruct());
            this.runtime.getEnvironment().setSimpleVariable("context", ctx.getOrSetupStruct());
            bl = MoLangExtensionsKt.resolveBoolean(this.runtime, this.allow);
        } else {
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean isExpired() {
        return SpawnRuleComponent.DefaultImpls.isExpired(this);
    }

    @Override
    public float affectWeight(@NotNull SpawnDetail detail, @NotNull SpawningContext ctx, float weight) {
        return SpawnRuleComponent.DefaultImpls.affectWeight(this, detail, ctx, weight);
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

