/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.model.geom.ModelPart
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokeball;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.EmptyPokeBallClientDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockStatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.PokeBallFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokeball.PokeBallModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.model.geom.ModelPart;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u000f\u0012\u0006\u0010'\u001a\u00020\u000b\u00a2\u0006\u0004\b(\u0010)J\u0017\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u000f\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\t\u0010\nR\u001a\u0010\f\u001a\u00020\u000b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0011\u001a\u00020\u00108\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0011\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u000b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0014\u0010\r\u001a\u0004\b\u0015\u0010\u000fR2\u0010\u0019\u001a\u0012\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00170\u0016j\u0002`\u00188\u0016@\u0016X\u0096.\u00a2\u0006\u0012\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR2\u0010\u001f\u001a\u0012\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00170\u0016j\u0002`\u00188\u0016@\u0016X\u0096.\u00a2\u0006\u0012\n\u0004\b\u001f\u0010\u001a\u001a\u0004\b \u0010\u001c\"\u0004\b!\u0010\u001eR\u001a\u0010\"\u001a\u00020\u000b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\"\u0010\r\u001a\u0004\b#\u0010\u000fR2\u0010$\u001a\u0012\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00170\u0016j\u0002`\u00188\u0016@\u0016X\u0096.\u00a2\u0006\u0012\n\u0004\b$\u0010\u001a\u001a\u0004\b%\u0010\u001c\"\u0004\b&\u0010\u001e\u00a8\u0006*"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokeball/PokeBallModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/PokeBallFrame;", "entity", "Lcom/cobblemon/mod/common/client/entity/EmptyPokeBallClientDelegate;", "getState", "(Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;)Lcom/cobblemon/mod/common/client/entity/EmptyPokeBallClientDelegate;", "", "registerPoses", "()V", "Lnet/minecraft/client/model/geom/ModelPart;", "base", "Lnet/minecraft/client/model/geom/ModelPart;", "getBase", "()Lnet/minecraft/client/model/geom/ModelPart;", "", "isForLivingEntityRenderer", "Z", "()Z", "lid", "getLid", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokeball/PokeBallPose;", "midair", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "getMidair", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "setMidair", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;)V", "open", "getOpen", "setOpen", "rootPart", "getRootPart", "shut", "getShut", "setShut", "root", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "common"})
public class PokeBallModel
extends PoseableEntityModel<EmptyPokeBallEntity>
implements PokeBallFrame {
    @NotNull
    private final ModelPart rootPart;
    @NotNull
    private final ModelPart base;
    @NotNull
    private final ModelPart lid;
    private final boolean isForLivingEntityRenderer;
    public Pose<EmptyPokeBallEntity, ModelFrame> shut;
    public Pose<EmptyPokeBallEntity, ModelFrame> open;
    public Pose<EmptyPokeBallEntity, ModelFrame> midair;

    public PokeBallModel(@NotNull ModelPart root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        super(null, 1, null);
        this.rootPart = this.registerChildWithAllChildren(root, "poke_ball");
        this.base = this.getPart("bottom");
        this.lid = this.getPart("lid");
    }

    @NotNull
    public ModelPart getRootPart() {
        return this.rootPart;
    }

    @Override
    @NotNull
    public ModelPart getBase() {
        return this.base;
    }

    @Override
    @NotNull
    public ModelPart getLid() {
        return this.lid;
    }

    @Override
    public boolean isForLivingEntityRenderer() {
        return this.isForLivingEntityRenderer;
    }

    @NotNull
    public Pose<EmptyPokeBallEntity, ModelFrame> getShut() {
        Pose<EmptyPokeBallEntity, ModelFrame> pose = this.shut;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"shut");
        return null;
    }

    public void setShut(@NotNull Pose<EmptyPokeBallEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.shut = pose;
    }

    @NotNull
    public Pose<EmptyPokeBallEntity, ModelFrame> getOpen() {
        Pose<EmptyPokeBallEntity, ModelFrame> pose = this.open;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"open");
        return null;
    }

    public void setOpen(@NotNull Pose<EmptyPokeBallEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.open = pose;
    }

    @NotNull
    public Pose<EmptyPokeBallEntity, ModelFrame> getMidair() {
        Pose<EmptyPokeBallEntity, ModelFrame> pose = this.midair;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"midair");
        return null;
    }

    public void setMidair(@NotNull Pose<EmptyPokeBallEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.midair = pose;
    }

    @NotNull
    public EmptyPokeBallClientDelegate getState(@NotNull EmptyPokeBallEntity entity2) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        EntitySideDelegate<EmptyPokeBallEntity> entitySideDelegate = entity2.getDelegate();
        Intrinsics.checkNotNull(entitySideDelegate, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.EmptyPokeBallClientDelegate");
        return (EmptyPokeBallClientDelegate)entitySideDelegate;
    }

    @Override
    public void registerPoses() {
        Object object = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "poke_ball", "throw", null, 4, null)};
        this.setMidair(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "flying", SetsKt.setOf((Object)((Object)PoseType.NONE)), (Function1)registerPoses.1.INSTANCE, 0, null, null, object, null, null, 432, null));
        object = SetsKt.setOf((Object)((Object)PoseType.NONE));
        StatelessAnimation[] statelessAnimationArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "poke_ball", "shut_idle", null, 4, null)};
        StatelessAnimation[] statelessAnimationArray2 = statelessAnimationArray;
        this.setShut(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "shut", (Set)object, null, 0, null, null, statelessAnimationArray2, null, null, 436, null));
        object = SetsKt.setOf((Object)((Object)PoseType.NONE));
        statelessAnimationArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "poke_ball", "open_idle", null, 4, null)};
        statelessAnimationArray2 = statelessAnimationArray;
        this.setOpen(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "open", (Set)object, null, 0, null, null, statelessAnimationArray2, null, null, 436, null));
        this.getShut().getTransitions().put(this.getOpen().getPoseName(), (Function2<Pose<EmptyPokeBallEntity, ModelFrame>, Pose<EmptyPokeBallEntity, ModelFrame>, StatefulAnimation<EmptyPokeBallEntity, ModelFrame>>)new Function2<Pose<EmptyPokeBallEntity, ? extends ModelFrame>, Pose<EmptyPokeBallEntity, ? extends ModelFrame>, BedrockStatefulAnimation<EmptyPokeBallEntity>>(this){
            final /* synthetic */ PokeBallModel this$0;
            {
                this.this$0 = $receiver;
                super(2);
            }

            @NotNull
            public final BedrockStatefulAnimation<EmptyPokeBallEntity> invoke(@NotNull Pose<EmptyPokeBallEntity, ? extends ModelFrame> pose, @NotNull Pose<EmptyPokeBallEntity, ? extends ModelFrame> pose2) {
                Intrinsics.checkNotNullParameter(pose, (String)"<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(pose2, (String)"<anonymous parameter 1>");
                return PoseableEntityModel.bedrockStateful$default(this.this$0, "poke_ball", "open", null, 4, null);
            }
        });
        this.getOpen().getTransitions().put(this.getShut().getPoseName(), (Function2<Pose<EmptyPokeBallEntity, ModelFrame>, Pose<EmptyPokeBallEntity, ModelFrame>, StatefulAnimation<EmptyPokeBallEntity, ModelFrame>>)new Function2<Pose<EmptyPokeBallEntity, ? extends ModelFrame>, Pose<EmptyPokeBallEntity, ? extends ModelFrame>, BedrockStatefulAnimation<EmptyPokeBallEntity>>(this){
            final /* synthetic */ PokeBallModel this$0;
            {
                this.this$0 = $receiver;
                super(2);
            }

            @NotNull
            public final BedrockStatefulAnimation<EmptyPokeBallEntity> invoke(@NotNull Pose<EmptyPokeBallEntity, ? extends ModelFrame> pose, @NotNull Pose<EmptyPokeBallEntity, ? extends ModelFrame> pose2) {
                Intrinsics.checkNotNullParameter(pose, (String)"<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(pose2, (String)"<anonymous parameter 1>");
                return PoseableEntityModel.bedrockStateful$default(this.this$0, "poke_ball", "shut", null, 4, null);
            }
        });
        Map<String, Function2<Pose<EmptyPokeBallEntity, ModelFrame>, Pose<EmptyPokeBallEntity, ModelFrame>, StatefulAnimation<EmptyPokeBallEntity, ModelFrame>>> map = this.getMidair().getTransitions();
        String string = this.getOpen().getPoseName();
        Function2<Pose<EmptyPokeBallEntity, ModelFrame>, Pose<EmptyPokeBallEntity, ModelFrame>, StatefulAnimation<EmptyPokeBallEntity, ModelFrame>> function2 = this.getShut().getTransitions().get(this.getOpen().getPoseName());
        Intrinsics.checkNotNull(function2);
        map.put(string, function2);
    }
}

