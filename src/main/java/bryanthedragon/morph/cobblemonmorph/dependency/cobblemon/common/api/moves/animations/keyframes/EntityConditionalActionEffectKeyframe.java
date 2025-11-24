/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoParams;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.QueryStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.StringValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext;
import java.util.HashMap;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J'\u0010\b\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\b\u0010\tR\u0014\u0010\r\u001a\u00020\n8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/api/moves/animations/keyframes/EntityConditionalActionEffectKeyframe;", "", "Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;", "context", "Lnet/minecraft/world/entity/Entity;", "entity", "", "isUser", "test", "(Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;Lnet/minecraft/world/entity/Entity;Z)Z", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "getEntityCondition", "()Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "entityCondition", "common"})
public interface EntityConditionalActionEffectKeyframe {
    @NotNull
    public ExpressionLike getEntityCondition();

    public boolean test(@NotNull ActionEffectContext var1, @NotNull Entity var2, boolean var3);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static boolean test(@NotNull EntityConditionalActionEffectKeyframe $this, @NotNull ActionEffectContext context, @NotNull Entity entity2, boolean isUser) {
            Intrinsics.checkNotNullParameter((Object)context, (String)"context");
            Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
            MoLangEnvironment moLangEnvironment = context.getRuntime().getEnvironment();
            Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment, (String)"context.runtime.environment");
            MoLangFunctions.getQueryStruct$default(MoLangFunctions.INSTANCE, moLangEnvironment, null, 1, null).addFunction("entity", arg_0 -> DefaultImpls.test$lambda$2(entity2, isUser, arg_0));
            return $this.getEntityCondition().resolveBoolean(context.getRuntime());
        }

        private static Object test$lambda$2$lambda$0(Entity $entity, MoParams it) {
            Intrinsics.checkNotNullParameter((Object)$entity, (String)"$entity");
            return new StringValue($entity.m_20149_());
        }

        private static Object test$lambda$2$lambda$1(boolean $isUser, MoParams it) {
            return new DoubleValue($isUser);
        }

        private static Object test$lambda$2(Entity $entity, boolean $isUser, MoParams it) {
            Intrinsics.checkNotNullParameter((Object)$entity, (String)"$entity");
            return new QueryStruct(new HashMap<String, Function<MoParams, Object>>()).addFunction("uuid", arg_0 -> DefaultImpls.test$lambda$2$lambda$0($entity, arg_0)).addFunction("is_user", arg_0 -> DefaultImpls.test$lambda$2$lambda$1($isUser, arg_0));
        }
    }
}

