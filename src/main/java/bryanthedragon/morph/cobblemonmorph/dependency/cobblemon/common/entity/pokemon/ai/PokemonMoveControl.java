/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.control.MoveControl
 *  net.minecraft.world.entity.ai.control.MoveControl$Operation
 *  net.minecraft.world.entity.ai.navigation.PathNavigation
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.pathfinder.BlockPathTypes
 *  net.minecraft.world.level.pathfinder.NodeEvaluator
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonBehaviourFlag;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.PokemonMoveControl;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai.FormPokemonBehaviour;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.WorldExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry.AngleExtensionsKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u000f\u0012\u0006\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u001f\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u000f\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\t\u0010\nR\u0017\u0010\f\u001a\u00020\u000b8\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/entity/pokemon/ai/PokemonMoveControl;", "Lnet/minecraft/world/entity/ai/control/MoveControl;", "", "xMovement", "zMovement", "", "isWalkable", "(FF)Z", "", "tick", "()V", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "pokemonEntity", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "getPokemonEntity", "()Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "<init>", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)V", "Companion", "common"})
public final class PokemonMoveControl
extends MoveControl {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final PokemonEntity pokemonEntity;
    public static final double VERY_CLOSE = 0.002500000277905201;

    public PokemonMoveControl(@NotNull PokemonEntity pokemonEntity) {
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        super((Mob)pokemonEntity);
        this.pokemonEntity = pokemonEntity;
    }

    @NotNull
    public final PokemonEntity getPokemonEntity() {
        return this.pokemonEntity;
    }

    public void m_8126_() {
        PersistentStatusContainer persistentStatusContainer = this.pokemonEntity.getPokemon().getStatus();
        if (Intrinsics.areEqual((Object)(persistentStatusContainer != null ? persistentStatusContainer.getStatus() : null), (Object)Statuses.INSTANCE.getSLEEP()) || this.pokemonEntity.m_21224_()) {
            this.pokemonEntity.m_7910_(0.0f);
            this.pokemonEntity.f_20901_ = 0.0f;
            return;
        }
        FormPokemonBehaviour behaviour = this.pokemonEntity.getBehaviour();
        PoseType[] poseTypeArray = new PoseType[]{PoseType.FLY, PoseType.HOVER};
        float mediumSpeed = SetsKt.setOf((Object[])poseTypeArray).contains((Object)this.pokemonEntity.getCurrentPoseType()) ? behaviour.getMoving().getFly().getFlySpeedHorizontal() : (this.pokemonEntity.m_204029_(FluidTags.f_13131_) || this.pokemonEntity.m_204029_(FluidTags.f_13132_) ? behaviour.getMoving().getSwim().getSwimSpeed() : behaviour.getMoving().getWalk().getWalkSpeed());
        float baseSpeed = (float)this.f_24974_.m_21133_(Attributes.f_22279_) * (float)this.f_24978_;
        float adjustedSpeed = baseSpeed * mediumSpeed;
        if (this.f_24981_ == MoveControl.Operation.STRAFE) {
            float zMovement;
            float movingDistanceTotal = Mth.m_14116_((float)(this.f_24979_ * this.f_24979_ + this.f_24980_ * this.f_24980_));
            if (movingDistanceTotal < 1.0f) {
                movingDistanceTotal = 1.0f;
            }
            movingDistanceTotal = adjustedSpeed / movingDistanceTotal;
            float adjustedForward = this.f_24979_ * movingDistanceTotal;
            float adjustedStrafe = this.f_24980_ * movingDistanceTotal;
            float xComponent = -Mth.m_14031_((float)AngleExtensionsKt.toRadians(Float.valueOf(this.f_24974_.m_146908_())));
            float zComponent = Mth.m_14089_((float)AngleExtensionsKt.toRadians(Float.valueOf(this.f_24974_.m_146908_())));
            float xMovement = adjustedForward * zComponent - adjustedStrafe * xComponent;
            if (!this.isWalkable(xMovement, zMovement = adjustedStrafe * zComponent + adjustedForward * xComponent)) {
                this.f_24979_ = 1.0f;
                this.f_24980_ = 0.0f;
            }
            this.f_24974_.m_7910_(adjustedSpeed);
            this.f_24974_.m_21564_(this.f_24979_);
            this.f_24974_.m_21570_(this.f_24980_);
            this.f_24981_ = MoveControl.Operation.WAIT;
        } else if (this.f_24981_ == MoveControl.Operation.MOVE_TO) {
            boolean closeHorizontally;
            if (!this.pokemonEntity.isFlying() && !this.pokemonEntity.m_6069_()) {
                this.f_24981_ = MoveControl.Operation.WAIT;
            }
            double xDist = this.f_24975_ - this.f_24974_.m_20185_();
            double zDist = this.f_24977_ - this.f_24974_.m_20189_();
            double yDist = this.f_24976_ - this.f_24974_.m_20186_();
            if (xDist * xDist + yDist * yDist + zDist * zDist < 0.002500000277905201) {
                this.f_24974_.m_21564_(0.0f);
                this.f_24974_.m_21567_(0.0f);
                if (this.pokemonEntity.isFlying() || this.pokemonEntity.m_6069_()) {
                    this.f_24981_ = MoveControl.Operation.WAIT;
                    this.f_24974_.m_20256_(Vec3.f_82478_);
                }
                return;
            }
            double horizontalDistanceFromTarget = xDist * xDist + zDist * zDist;
            boolean bl = closeHorizontally = horizontalDistanceFromTarget < 0.002500000277905201;
            if (!closeHorizontally) {
                float angleToTarget = AngleExtensionsKt.toDegrees(Mth.m_14136_((double)zDist, (double)xDist)) - 90.0f;
                float currentMovingAngle = this.f_24974_.m_146908_();
                float steppedAngle = Mth.m_14148_((float)currentMovingAngle, (float)angleToTarget, (float)((float)100 * mediumSpeed));
                this.f_24974_.m_146922_(steppedAngle);
            }
            Level level = this.f_24974_.m_9236_();
            Intrinsics.checkNotNullExpressionValue((Object)level, (String)"entity.world");
            BlockGetter blockGetter = (BlockGetter)level;
            AABB aABB = this.f_24974_.m_20191_();
            Intrinsics.checkNotNullExpressionValue((Object)aABB, (String)"entity.boundingBox");
            Pair<Boolean, Boolean> pair = WorldExtensionsKt.getWaterAndLavaIn(blockGetter, aABB);
            boolean inWater = (Boolean)pair.component1();
            boolean inLava = (Boolean)pair.component2();
            boolean inFluid = inWater || inLava;
            boolean verticalHandled = false;
            BlockPos blockPos2 = this.f_24974_.m_20183_();
            BlockState blockState = this.f_24974_.m_9236_().m_8055_(blockPos2);
            VoxelShape voxelShape = blockState.m_60812_((BlockGetter)this.f_24974_.m_9236_(), blockPos2);
            if (this.pokemonEntity.getBehaviourFlag(PokemonBehaviourFlag.FLYING) || inFluid) {
                verticalHandled = true;
                this.f_24974_.f_20901_ = 0.0f;
                this.f_24974_.m_7910_(0.0f);
                Function1 refine2 = tick.refine.1.INSTANCE;
                Vec3 fullDistance = new Vec3(xDist, ((Number)refine2.invoke((Object)(yDist + 0.05))).doubleValue(), zDist);
                Vec3 direction = fullDistance.m_82541_();
                double scale = Math.min((double)adjustedSpeed, fullDistance.m_82553_());
                this.f_24974_.m_20256_(direction.m_82490_(scale));
                xDist = fullDistance.f_82479_;
                zDist = fullDistance.f_82481_;
                yDist = fullDistance.f_82480_;
            } else {
                float forwardSpeed = Math.min(adjustedSpeed, Math.max((float)horizontalDistanceFromTarget / (float)2, 0.15f));
                this.f_24974_.m_7910_(forwardSpeed);
            }
            if (!verticalHandled) {
                boolean closeEnoughToJump;
                boolean tooBigToStep = yDist > (double)this.f_24974_.m_274421_();
                double xComponent = -((double)Mth.m_14031_((float)AngleExtensionsKt.toRadians(Float.valueOf(this.f_24974_.m_146908_()))));
                double zComponent = Mth.m_14089_((float)AngleExtensionsKt.toRadians(Float.valueOf(this.f_24974_.m_146908_())));
                Vec3 motion = new Vec3(xComponent, 0.0, zComponent).m_82541_();
                Vec3 offset = motion.m_82490_((double)this.f_24974_.m_6113_());
                boolean bl2 = closeEnoughToJump = !this.f_24974_.m_20229_(offset.f_82479_, 0.0, offset.f_82481_);
                if (tooBigToStep && closeEnoughToJump || !voxelShape.m_83281_() && this.f_24974_.m_20186_() < voxelShape.m_83297_(Direction.Axis.Y) + (double)blockPos2.m_123342_() && !blockState.m_204336_(BlockTags.f_13103_) && !blockState.m_204336_(BlockTags.f_13039_)) {
                    this.f_24974_.m_21569_().m_24901_();
                    this.f_24981_ = MoveControl.Operation.JUMPING;
                }
            }
            if (closeHorizontally && Math.abs(yDist) < 0.002500000277905201) {
                this.f_24981_ = MoveControl.Operation.WAIT;
            }
        } else if (this.f_24981_ == MoveControl.Operation.JUMPING) {
            this.f_24974_.m_7910_(adjustedSpeed);
            this.f_24974_.f_20901_ = 0.0f;
            if (this.f_24974_.m_20096_() || this.pokemonEntity.getBehaviourFlag(PokemonBehaviourFlag.FLYING)) {
                this.f_24981_ = MoveControl.Operation.WAIT;
            }
        } else {
            this.f_24974_.m_21564_(0.0f);
            this.f_24974_.f_20901_ = 0.0f;
        }
        if (this.f_24981_ == MoveControl.Operation.WAIT && !this.f_24974_.m_21573_().m_26572_()) {
            if (this.f_24974_.m_20096_() && behaviour.getMoving().getWalk().getCanWalk() && this.pokemonEntity.getBehaviourFlag(PokemonBehaviourFlag.FLYING)) {
                this.pokemonEntity.setBehaviourFlag(PokemonBehaviourFlag.FLYING, false);
            }
            if (this.f_24974_.m_204029_(FluidTags.f_13131_) && behaviour.getMoving().getSwim().getCanSwimInWater()) {
                this.pokemonEntity.m_21567_(0.2f);
            }
        }
    }

    private final boolean isWalkable(float xMovement, float zMovement) {
        NodeEvaluator pathNodeMaker;
        PathNavigation entityNavigation = this.f_24974_.m_21573_();
        return entityNavigation == null || (pathNodeMaker = entityNavigation.m_26575_()) == null || pathNodeMaker.m_8086_((BlockGetter)this.f_24974_.m_9236_(), Mth.m_14107_((double)(this.f_24974_.m_20185_() + (double)xMovement)), this.f_24974_.m_146904_(), Mth.m_14107_((double)(this.f_24974_.m_20189_() + (double)zMovement))) == BlockPathTypes.WALKABLE;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0006\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/entity/pokemon/ai/PokemonMoveControl$Companion;", "", "", "VERY_CLOSE", "D", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

