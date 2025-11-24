/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.NotImplementedError
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.fossil;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.ModelQuirk;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\u0012\u0006\u00106\u001a\u000205\u00a2\u0006\u0004\b7\u00108J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u000f\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\b\u0010\tR\u0017\u0010\u000b\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0010\u001a\u00020\u000f8\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0010\u0010\u0012R\"\u0010\u0014\u001a\u00020\u00138\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001b\u001a\u00020\u001a8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001eR6\u0010\"\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\b\u0001\u0012\u00020!0 0\u001f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R2\u0010)\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u0002\u0012\u0002\b\u00030(0\u001f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b)\u0010*\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\"\u0010/\u001a\u00020\u00138\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b/\u0010\u0015\u001a\u0004\b0\u0010\u0017\"\u0004\b1\u0010\u0019R\"\u00102\u001a\u00020\u00138\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b2\u0010\u0015\u001a\u0004\b3\u0010\u0017\"\u0004\b4\u0010\u0019\u00a8\u00069"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/fossil/FossilModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "Lnet/minecraft/world/entity/Entity;", "entity", "", "getState", "(Lnet/minecraft/world/entity/Entity;)Ljava/lang/Void;", "", "registerPoses", "()V", "", "boneName", "Ljava/lang/String;", "getBoneName", "()Ljava/lang/String;", "", "isForLivingEntityRenderer", "Z", "()Z", "", "maxScale", "F", "getMaxScale", "()F", "setMaxScale", "(F)V", "Lnet/minecraft/client/model/geom/ModelPart;", "rootPart", "Lnet/minecraft/client/model/geom/ModelPart;", "getRootPart", "()Lnet/minecraft/client/model/geom/ModelPart;", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "tankAnimations", "[Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;", "getTankAnimations", "()[Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;", "setTankAnimations", "([Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;)V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/quirk/ModelQuirk;", "tankQuirks", "[Lcom/cobblemon/mod/common/client/render/models/blockbench/quirk/ModelQuirk;", "getTankQuirks", "()[Lcom/cobblemon/mod/common/client/render/models/blockbench/quirk/ModelQuirk;", "setTankQuirks", "([Lcom/cobblemon/mod/common/client/render/models/blockbench/quirk/ModelQuirk;)V", "yGrowthPoint", "getYGrowthPoint", "setYGrowthPoint", "yTranslation", "getYTranslation", "setYTranslation", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "root", "<init>", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;)V", "common"})
@SourceDebugExtension(value={"SMAP\nFossilModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FossilModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/fossil/FossilModel\n+ 2 ArrayIntrinsics.kt\nkotlin/ArrayIntrinsicsKt\n*L\n1#1,43:1\n26#2:44\n26#2:45\n*S KotlinDebug\n*F\n+ 1 FossilModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/fossil/FossilModel\n*L\n32#1:44\n33#1:45\n*E\n"})
public final class FossilModel
extends PoseableEntityModel<Entity> {
    private final boolean isForLivingEntityRenderer;
    @NotNull
    private final String boneName;
    @NotNull
    private final ModelPart rootPart;
    private float yGrowthPoint;
    private float maxScale;
    private float yTranslation;
    @NotNull
    private StatelessAnimation<Entity, ? extends ModelFrame>[] tankAnimations;
    @NotNull
    private ModelQuirk<Entity, ?>[] tankQuirks;

    public FossilModel(@NotNull Bone root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        super(null, 1, null);
        Object k = ((Map.Entry)CollectionsKt.first((Iterable)root.getChildren().entrySet())).getKey();
        Intrinsics.checkNotNullExpressionValue(k, (String)"root.children.entries.first().key");
        this.boneName = (String)k;
        this.rootPart = this.registerChildWithAllChildren((ModelPart)root, this.boneName);
        this.maxScale = 1.0f;
        boolean $i$f$emptyArray = false;
        this.tankAnimations = new StatelessAnimation[0];
        $i$f$emptyArray = false;
        this.tankQuirks = new ModelQuirk[0];
    }

    @Override
    public boolean isForLivingEntityRenderer() {
        return this.isForLivingEntityRenderer;
    }

    @NotNull
    public final String getBoneName() {
        return this.boneName;
    }

    @NotNull
    public ModelPart getRootPart() {
        return this.rootPart;
    }

    public final float getYGrowthPoint() {
        return this.yGrowthPoint;
    }

    public final void setYGrowthPoint(float f) {
        this.yGrowthPoint = f;
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

    @NotNull
    public final StatelessAnimation<Entity, ? extends ModelFrame>[] getTankAnimations() {
        return this.tankAnimations;
    }

    public final void setTankAnimations(@NotNull StatelessAnimation<Entity, ? extends ModelFrame>[] statelessAnimationArray) {
        Intrinsics.checkNotNullParameter(statelessAnimationArray, (String)"<set-?>");
        this.tankAnimations = statelessAnimationArray;
    }

    @NotNull
    public final ModelQuirk<Entity, ?>[] getTankQuirks() {
        return this.tankQuirks;
    }

    public final void setTankQuirks(@NotNull ModelQuirk<Entity, ?>[] modelQuirkArray) {
        Intrinsics.checkNotNullParameter(modelQuirkArray, (String)"<set-?>");
        this.tankQuirks = modelQuirkArray;
    }

    @Override
    public void registerPoses() {
        PoseableEntityModel.registerPose$default(this, PoseType.SLEEP, null, 0, null, null, this.tankAnimations, null, this.tankQuirks, 94, null);
    }

    @NotNull
    public Void getState(@NotNull Entity entity2) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        throw new NotImplementedError("This is not supported for fossil models");
    }
}

