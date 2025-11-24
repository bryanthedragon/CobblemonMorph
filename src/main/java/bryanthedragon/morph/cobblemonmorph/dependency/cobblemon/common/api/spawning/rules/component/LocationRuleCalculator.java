/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Holder
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.dimension.DimensionType
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.component;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.StringValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.component.SpawnRuleComponent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b%\u0010&J/\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u000e\u0010\u0007\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\nR\u001f\u0010\r\u001a\n \f*\u0004\u0018\u00010\u000b0\u000b8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0012\u001a\u00020\u00118\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\u0013R\"\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00150\u00148\u0002@\u0002X\u0082.\u00a2\u0006\u0006\n\u0004\b\u0017\u0010\u0018R\"\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190\u00150\u00148\u0002@\u0002X\u0082.\u00a2\u0006\u0006\n\u0004\b\u001a\u0010\u0018R\u0014\u0010\u001c\u001a\u00020\u001b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001e\u001a\u00020\u001b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001e\u0010\u001dR\u0014\u0010\u001f\u001a\u00020\u001b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001f\u0010\u001dR\u0017\u0010!\u001a\u00020 8\u0006\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$\u00a8\u0006'"}, d2={"Lcom/cobblemon/mod/common/api/spawning/rules/component/LocationRuleCalculator;", "Lcom/cobblemon/mod/common/api/spawning/rules/component/SpawnRuleComponent;", "Lnet/minecraft/server/level/ServerLevel;", "world", "Lnet/minecraft/core/BlockPos;", "pos", "Lcom/cobblemon/mod/common/api/spawning/context/calculators/SpawningContextCalculator;", "contextCalculator", "", "isAllowedPosition", "(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lcom/cobblemon/mod/common/api/spawning/context/calculators/SpawningContextCalculator;)Z", "Lcom/bedrockk/molang/Expression;", "kotlin.jvm.PlatformType", "allow", "Lcom/bedrockk/molang/Expression;", "getAllow", "()Lcom/bedrockk/molang/Expression;", "Lcom/bedrockk/molang/runtime/value/StringValue;", "reusableContext", "Lcom/bedrockk/molang/runtime/value/StringValue;", "Lcom/cobblemon/mod/common/api/molang/ObjectValue;", "Lnet/minecraft/core/Holder;", "Lnet/minecraft/world/level/dimension/DimensionType;", "reusableDimensionTypeValue", "Lcom/cobblemon/mod/common/api/molang/ObjectValue;", "Lnet/minecraft/world/level/Level;", "reusableWorldValue", "Lcom/bedrockk/molang/runtime/value/DoubleValue;", "reusableX", "Lcom/bedrockk/molang/runtime/value/DoubleValue;", "reusableY", "reusableZ", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "getRuntime", "()Lcom/bedrockk/molang/runtime/MoLangRuntime;", "<init>", "()V", "common"})
public final class LocationRuleCalculator
implements SpawnRuleComponent {
    @NotNull
    private final transient MoLangRuntime runtime = MoLangFunctions.INSTANCE.setup(new MoLangRuntime());
    @NotNull
    private final transient DoubleValue reusableX = new DoubleValue(0.0);
    @NotNull
    private final transient DoubleValue reusableY = new DoubleValue(0.0);
    @NotNull
    private final transient DoubleValue reusableZ = new DoubleValue(0.0);
    @NotNull
    private final transient StringValue reusableContext = new StringValue("");
    private transient ObjectValue<Holder<Level>> reusableWorldValue;
    private transient ObjectValue<Holder<DimensionType>> reusableDimensionTypeValue;
    private final Expression allow = MoLangExtensionsKt.asExpression("true");

    @NotNull
    public final MoLangRuntime getRuntime() {
        return this.runtime;
    }

    public final Expression getAllow() {
        return this.allow;
    }

    @Override
    public boolean isAllowedPosition(@NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull SpawningContextCalculator<?, ?> contextCalculator) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter(contextCalculator, (String)"contextCalculator");
        this.reusableX.value = pos.m_123341_();
        this.reusableY.value = pos.m_123342_();
        this.reusableZ.value = pos.m_123343_();
        this.reusableContext.value = contextCalculator.getName();
        if (this.reusableWorldValue == null) {
            Object t = world.m_9598_().m_175515_(Registries.f_256858_).m_203636_(world.m_46472_()).get();
            Intrinsics.checkNotNullExpressionValue(t, (String)"world.registryManager.ge\u2026(world.registryKey).get()");
            this.reusableWorldValue = MoLangFunctions.INSTANCE.asWorldMoLangValue((Holder<Level>)((Holder)t));
        } else {
            ObjectValue<Holder<Level>> objectValue = this.reusableWorldValue;
            if (objectValue == null) {
                Intrinsics.throwUninitializedPropertyAccessException((String)"reusableWorldValue");
                objectValue = null;
            }
            Object t = world.m_9598_().m_175515_(Registries.f_256858_).m_203636_(world.m_46472_()).get();
            Intrinsics.checkNotNullExpressionValue(t, (String)"world.registryManager.ge\u2026(world.registryKey).get()");
            objectValue.setObj((Holder<Level>)t);
        }
        if (this.reusableDimensionTypeValue == null) {
            Holder holder = world.m_204156_();
            Intrinsics.checkNotNullExpressionValue((Object)holder, (String)"world.dimensionEntry");
            this.reusableDimensionTypeValue = MoLangFunctions.INSTANCE.asDimensionTypeMoLangValue((Holder<DimensionType>)holder);
        } else {
            ObjectValue<Holder<DimensionType>> objectValue = this.reusableDimensionTypeValue;
            if (objectValue == null) {
                Intrinsics.throwUninitializedPropertyAccessException((String)"reusableDimensionTypeValue");
                objectValue = null;
            }
            Holder holder = world.m_204156_();
            Intrinsics.checkNotNullExpressionValue((Object)holder, (String)"world.dimensionEntry");
            objectValue.setObj((Holder<DimensionType>)holder);
        }
        this.runtime.getEnvironment().setSimpleVariable("x", this.reusableX);
        this.runtime.getEnvironment().setSimpleVariable("y", this.reusableY);
        this.runtime.getEnvironment().setSimpleVariable("z", this.reusableZ);
        this.runtime.getEnvironment().setSimpleVariable("context", this.reusableContext);
        MoLangEnvironment moLangEnvironment = this.runtime.getEnvironment();
        ObjectValue<Holder<Level>> objectValue = this.reusableWorldValue;
        if (objectValue == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"reusableWorldValue");
            objectValue = null;
        }
        moLangEnvironment.setSimpleVariable("world", objectValue);
        MoLangEnvironment moLangEnvironment2 = this.runtime.getEnvironment();
        ObjectValue<Holder<DimensionType>> objectValue2 = this.reusableDimensionTypeValue;
        if (objectValue2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"reusableDimensionTypeValue");
            objectValue2 = null;
        }
        moLangEnvironment2.setSimpleVariable("dimension_type", objectValue2);
        Expression expression = this.allow;
        Intrinsics.checkNotNullExpressionValue((Object)expression, (String)"allow");
        return MoLangExtensionsKt.resolveBoolean(this.runtime, expression);
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
}

