/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\u0004\b\u0012\u0010\u0013J/\u0010\b\u001a\u00020\u0007\"\b\b\u0000\u0010\u0003*\u00020\u00022\u0006\u0010\u0004\u001a\u00028\u00002\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H\u0016\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockInstructionKeyframe;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockEffectKeyframe;", "Lnet/minecraft/world/entity/Entity;", "T", "entity", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "state", "", "run", "(Lnet/minecraft/world/entity/Entity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;)V", "", "Lcom/bedrockk/molang/Expression;", "expressions", "Ljava/util/List;", "getExpressions", "()Ljava/util/List;", "", "seconds", "<init>", "(FLjava/util/List;)V", "common"})
@SourceDebugExtension(value={"SMAP\nBedrockAnimation.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BedrockAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockInstructionKeyframe\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,346:1\n1855#2,2:347\n*S KotlinDebug\n*F\n+ 1 BedrockAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockInstructionKeyframe\n*L\n134#1:347,2\n*E\n"})
public final class BedrockInstructionKeyframe
extends BedrockEffectKeyframe {
    @NotNull
    private final List<Expression> expressions;

    public BedrockInstructionKeyframe(float seconds, @NotNull List<? extends Expression> expressions) {
        Intrinsics.checkNotNullParameter(expressions, (String)"expressions");
        super(seconds);
        this.expressions = expressions;
    }

    @NotNull
    public final List<Expression> getExpressions() {
        return this.expressions;
    }

    @Override
    public <T extends Entity> void run(@NotNull T entity2, @NotNull PoseableEntityState<T> state) {
        Intrinsics.checkNotNullParameter(entity2, (String)"entity");
        Intrinsics.checkNotNullParameter(state, (String)"state");
        Iterable $this$forEach$iv = this.expressions;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Expression expression = (Expression)element$iv;
            boolean bl = false;
            MoLangExtensionsKt.resolve(state.getRuntime(), expression);
        }
    }
}

