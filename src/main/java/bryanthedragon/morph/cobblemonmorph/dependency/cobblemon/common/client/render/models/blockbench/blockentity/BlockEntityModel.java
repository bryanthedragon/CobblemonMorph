/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.NotImplementedError
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.blockentity;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockStatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\u0012\u0006\u0010$\u001a\u00020#\u00a2\u0006\u0004\b0\u00101J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u000f\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\b\u0010\tR\u0017\u0010\u000b\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000eR6\u0010\u0012\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\b\u0001\u0012\u00020\u00110\u00100\u000f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0019\u001a\u00020\u00188\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u0019\u0010\u001bR\"\u0010\u001d\u001a\u00020\u001c8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u0017\u0010$\u001a\u00020#8\u0006\u00a2\u0006\f\n\u0004\b$\u0010%\u001a\u0004\b&\u0010'R\u001a\u0010)\u001a\u00020(8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b)\u0010*\u001a\u0004\b+\u0010,R\"\u0010-\u001a\u00020\u001c8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b-\u0010\u001e\u001a\u0004\b.\u0010 \"\u0004\b/\u0010\"\u00a8\u00062"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/blockentity/BlockEntityModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "Lnet/minecraft/world/entity/Entity;", "entity", "", "getState", "(Lnet/minecraft/world/entity/Entity;)Ljava/lang/Void;", "", "registerPoses", "()V", "", "boneName", "Ljava/lang/String;", "getBoneName", "()Ljava/lang/String;", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "idleAnimations", "[Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;", "getIdleAnimations", "()[Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;", "setIdleAnimations", "([Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;)V", "", "isForLivingEntityRenderer", "Z", "()Z", "", "maxScale", "F", "getMaxScale", "()F", "setMaxScale", "(F)V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "root", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "getRoot", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "Lnet/minecraft/client/model/geom/ModelPart;", "rootPart", "Lnet/minecraft/client/model/geom/ModelPart;", "getRootPart", "()Lnet/minecraft/client/model/geom/ModelPart;", "yTranslation", "getYTranslation", "setYTranslation", "<init>", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;)V", "common"})
@SourceDebugExtension(value={"SMAP\nBlockEntityModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BlockEntityModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/blockentity/BlockEntityModel\n+ 2 ArrayIntrinsics.kt\nkotlin/ArrayIntrinsicsKt\n*L\n1#1,44:1\n26#2:45\n*S KotlinDebug\n*F\n+ 1 BlockEntityModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/blockentity/BlockEntityModel\n*L\n24#1:45\n*E\n"})
public final class BlockEntityModel
extends PoseableEntityModel<Entity> {
    @NotNull
    private final Bone root;
    @NotNull
    private final String boneName;
    @NotNull
    private final ModelPart rootPart;
    private final boolean isForLivingEntityRenderer;
    @NotNull
    private StatelessAnimation<Entity, ? extends ModelFrame>[] idleAnimations;
    private float maxScale;
    private float yTranslation;

    public BlockEntityModel(@NotNull Bone root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        super(null, 1, null);
        this.root = root;
        Object k = ((Map.Entry)CollectionsKt.first((Iterable)this.root.getChildren().entrySet())).getKey();
        Intrinsics.checkNotNullExpressionValue(k, (String)"root.children.entries.first().key");
        this.boneName = (String)k;
        Bone bone = this.root;
        Intrinsics.checkNotNull((Object)bone, (String)"null cannot be cast to non-null type net.minecraft.client.model.ModelPart");
        this.rootPart = this.registerChildWithAllChildren((ModelPart)bone, this.boneName);
        boolean $i$f$emptyArray = false;
        this.idleAnimations = new StatelessAnimation[0];
        this.maxScale = 1.0f;
    }

    @NotNull
    public final Bone getRoot() {
        return this.root;
    }

    @NotNull
    public final String getBoneName() {
        return this.boneName;
    }

    @NotNull
    public ModelPart getRootPart() {
        return this.rootPart;
    }

    @Override
    public boolean isForLivingEntityRenderer() {
        return this.isForLivingEntityRenderer;
    }

    @NotNull
    public final StatelessAnimation<Entity, ? extends ModelFrame>[] getIdleAnimations() {
        return this.idleAnimations;
    }

    public final void setIdleAnimations(@NotNull StatelessAnimation<Entity, ? extends ModelFrame>[] statelessAnimationArray) {
        Intrinsics.checkNotNullParameter(statelessAnimationArray, (String)"<set-?>");
        this.idleAnimations = statelessAnimationArray;
    }

    public final float getMaxScale() {
        return this.maxScale;
    }

    public final void setMaxScale(float f) {
        this.maxScale = f;
    }

    public final float getYTranslation() {
        return this.yTranslation;
    }

    public final void setYTranslation(float f) {
        this.yTranslation = f;
    }

    @Override
    public void registerPoses() {
        Pose closedPose = PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "CLOSED", PoseType.NONE, null, 0, null, null, null, null, null, 508, null);
        StatelessAnimation[] statelessAnimationArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "gilded_chest", "open", null, 4, null)};
        Pose openPose = PoseableEntityModel.registerPose$default(this, PoseType.OPEN, null, 0, null, null, statelessAnimationArray, null, null, 222, null);
        closedPose.getTransitions().put(openPose.getPoseName(), new Function2<Pose<Entity, ? extends ModelFrame>, Pose<Entity, ? extends ModelFrame>, BedrockStatefulAnimation<Entity>>(this){
            final /* synthetic */ BlockEntityModel this$0;
            {
                this.this$0 = $receiver;
                super(2);
            }

            @NotNull
            public final BedrockStatefulAnimation<Entity> invoke(@NotNull Pose<Entity, ? extends ModelFrame> pose, @NotNull Pose<Entity, ? extends ModelFrame> pose2) {
                Intrinsics.checkNotNullParameter(pose, (String)"<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(pose2, (String)"<anonymous parameter 1>");
                return PoseableEntityModel.bedrockStateful$default(this.this$0, "gilded_chest", "opening", null, 4, null);
            }
        });
        openPose.getTransitions().put(closedPose.getPoseName(), new Function2<Pose<Entity, ? extends ModelFrame>, Pose<Entity, ? extends ModelFrame>, BedrockStatefulAnimation<Entity>>(this){
            final /* synthetic */ BlockEntityModel this$0;
            {
                this.this$0 = $receiver;
                super(2);
            }

            @NotNull
            public final BedrockStatefulAnimation<Entity> invoke(@NotNull Pose<Entity, ? extends ModelFrame> pose, @NotNull Pose<Entity, ? extends ModelFrame> pose2) {
                Intrinsics.checkNotNullParameter(pose, (String)"<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(pose2, (String)"<anonymous parameter 1>");
                return PoseableEntityModel.bedrockStateful$default(this.this$0, "gilded_chest", "closing", null, 4, null);
            }
        });
    }

    @NotNull
    public Void getState(@NotNull Entity entity2) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        throw new NotImplementedError("This is not supported for the gilded chest");
    }
}

