/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  kotlin.Lazy
 *  kotlin.LazyKt
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.util.Mth
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.navigation.GroundPathNavigation
 *  net.minecraft.world.entity.ai.navigation.PathNavigation
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.pathfinder.BlockPathTypes
 *  net.minecraft.world.level.pathfinder.Node
 *  net.minecraft.world.level.pathfinder.NodeEvaluator
 *  net.minecraft.world.level.pathfinder.Path
 *  net.minecraft.world.level.pathfinder.PathComputationType
 *  net.minecraft.world.level.pathfinder.PathFinder
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonBehaviourFlag;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai.MoveBehaviour;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai.OmniPathNodeMaker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.BlockPosExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.WorldExtensionsKt;
import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001:\u0001WB\u0017\u0012\u0006\u0010\u001e\u001a\u00020\u001d\u0012\u0006\u0010N\u001a\u00020M\u00a2\u0006\u0004\bU\u0010VJ\u000f\u0010\u0003\u001a\u00020\u0002H\u0014\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u000f\u0010\b\u001a\u00020\u0002H\u0014\u00a2\u0006\u0004\b\b\u0010\u0004J\u0017\u0010\f\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\tH\u0014\u00a2\u0006\u0004\b\f\u0010\rJ\u001f\u0010\u0012\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\t\u00a2\u0006\u0004\b\u0012\u0010\u0013J!\u0010\u0016\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0017J!\u0010\u0016\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0013J\r\u0010\u0018\u001a\u00020\t\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u000f\u0010\u001b\u001a\u00020\u001aH\u0014\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u001d\u0010 \u001a\u00020\u00052\u0006\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020\u000e\u00a2\u0006\u0004\b \u0010!J\u000f\u0010\"\u001a\u00020\u0005H\u0014\u00a2\u0006\u0004\b\"\u0010\u0007J\u0015\u0010$\u001a\u00020\u00022\u0006\u0010#\u001a\u00020\u0005\u00a2\u0006\u0004\b$\u0010%J!\u0010)\u001a\u00020\u00052\b\u0010&\u001a\u0004\u0018\u00010\u00112\u0006\u0010(\u001a\u00020'H\u0016\u00a2\u0006\u0004\b)\u0010*J7\u00100\u001a\u00020\u00022\u0006\u0010+\u001a\u00020'2\u0006\u0010,\u001a\u00020'2\u0006\u0010-\u001a\u00020'2\b\b\u0002\u0010(\u001a\u00020'2\u0006\u0010/\u001a\u00020.\u00a2\u0006\u0004\b0\u00101J\u000f\u00102\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b2\u0010\u0004J\u000f\u00103\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b3\u0010\u0004R$\u00105\u001a\u0004\u0018\u0001048\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b5\u00106\u001a\u0004\b7\u00108\"\u0004\b9\u0010:R\"\u0010<\u001a\u00020;8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b<\u0010=\u001a\u0004\b>\u0010?\"\u0004\b@\u0010AR\u001b\u0010G\u001a\u00020B8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\bC\u0010D\u001a\u0004\bE\u0010FR\"\u0010/\u001a\u00020.8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b/\u0010H\u001a\u0004\bI\u0010J\"\u0004\bK\u0010LR\u0017\u0010N\u001a\u00020M8\u0006\u00a2\u0006\f\n\u0004\bN\u0010O\u001a\u0004\bP\u0010QR\u0017\u0010\u001e\u001a\u00020\u001d8\u0006\u00a2\u0006\f\n\u0004\b\u001e\u0010R\u001a\u0004\bS\u0010T\u00a8\u0006X"}, d2={"Lcom/cobblemon/mod/common/entity/pokemon/ai/PokemonNavigation;", "Lnet/minecraft/world/entity/ai/navigation/GroundPathNavigation;", "", "adjustPath", "()V", "", "canSwim", "()Z", "continueFollowingPath", "", "range", "Lnet/minecraft/world/level/pathfinder/PathFinder;", "createPathNodeNavigator", "(I)Lnet/minecraft/world/level/pathfinder/PathFinder;", "Lnet/minecraft/core/BlockPos;", "target", "distance", "Lnet/minecraft/world/level/pathfinder/Path;", "findPath", "(Lnet/minecraft/core/BlockPos;I)Lnet/minecraft/world/level/pathfinder/Path;", "Lnet/minecraft/world/entity/Entity;", "entity", "findPathTo", "(Lnet/minecraft/world/entity/Entity;I)Lnet/minecraft/world/level/pathfinder/Path;", "getPathfindingY", "()I", "Lnet/minecraft/world/phys/Vec3;", "getPos", "()Lnet/minecraft/world/phys/Vec3;", "Lnet/minecraft/world/level/Level;", "world", "pos", "isAirborne", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Z", "isAtValidPosition", "canPathThroughFire", "setCanPathThroughFire", "(Z)V", "path", "", "speed", "startMovingAlong", "(Lnet/minecraft/world/level/pathfinder/Path;D)Z", "x", "y", "z", "Lcom/cobblemon/mod/common/entity/pokemon/ai/PokemonNavigation$NavigationContext;", "navigationContext", "startMovingTo", "(DDDDLcom/cobblemon/mod/common/entity/pokemon/ai/PokemonNavigation$NavigationContext;)V", "stop", "tick", "Lnet/minecraft/world/level/pathfinder/Node;", "cachedCurrentNode", "Lnet/minecraft/world/level/pathfinder/Node;", "getCachedCurrentNode", "()Lnet/minecraft/world/level/pathfinder/Node;", "setCachedCurrentNode", "(Lnet/minecraft/world/level/pathfinder/Node;)V", "", "currentNodeDistance", "F", "getCurrentNodeDistance", "()F", "setCurrentNodeDistance", "(F)V", "Lcom/cobblemon/mod/common/pokemon/ai/MoveBehaviour;", "moving$delegate", "Lkotlin/Lazy;", "getMoving", "()Lcom/cobblemon/mod/common/pokemon/ai/MoveBehaviour;", "moving", "Lcom/cobblemon/mod/common/entity/pokemon/ai/PokemonNavigation$NavigationContext;", "getNavigationContext", "()Lcom/cobblemon/mod/common/entity/pokemon/ai/PokemonNavigation$NavigationContext;", "setNavigationContext", "(Lcom/cobblemon/mod/common/entity/pokemon/ai/PokemonNavigation$NavigationContext;)V", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "pokemonEntity", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "getPokemonEntity", "()Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lnet/minecraft/world/level/Level;", "getWorld", "()Lnet/minecraft/world/level/Level;", "<init>", "(Lnet/minecraft/world/level/Level;Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)V", "NavigationContext", "common"})
public final class PokemonNavigation
extends GroundPathNavigation {
    @NotNull
    private final Level world;
    @NotNull
    private final PokemonEntity pokemonEntity;
    @NotNull
    private final Lazy moving$delegate;
    @Nullable
    private Node cachedCurrentNode;
    private float currentNodeDistance;
    @NotNull
    private NavigationContext navigationContext;

    public PokemonNavigation(@NotNull Level world, @NotNull PokemonEntity pokemonEntity) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        super((Mob)pokemonEntity, world);
        this.world = world;
        this.pokemonEntity = pokemonEntity;
        this.moving$delegate = LazyKt.lazy((Function0)((Function0)new Function0<MoveBehaviour>(this){
            final /* synthetic */ PokemonNavigation this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final MoveBehaviour invoke() {
                return this.this$0.getPokemonEntity().getBehaviour().getMoving();
            }
        }));
        this.navigationContext = new NavigationContext(null, null, null, null, false, 0.0f, 63, null);
    }

    @NotNull
    public final Level getWorld() {
        return this.world;
    }

    @NotNull
    public final PokemonEntity getPokemonEntity() {
        return this.pokemonEntity;
    }

    @NotNull
    public final MoveBehaviour getMoving() {
        Lazy lazy = this.moving$delegate;
        return (MoveBehaviour)lazy.getValue();
    }

    @Nullable
    public final Node getCachedCurrentNode() {
        return this.cachedCurrentNode;
    }

    public final void setCachedCurrentNode(@Nullable Node node) {
        this.cachedCurrentNode = node;
    }

    public final float getCurrentNodeDistance() {
        return this.currentNodeDistance;
    }

    public final void setCurrentNodeDistance(float f) {
        this.currentNodeDistance = f;
    }

    @NotNull
    public final NavigationContext getNavigationContext() {
        return this.navigationContext;
    }

    public final void setNavigationContext(@NotNull NavigationContext navigationContext) {
        Intrinsics.checkNotNullParameter((Object)navigationContext, (String)"<set-?>");
        this.navigationContext = navigationContext;
    }

    @NotNull
    protected PathFinder m_5532_(int range) {
        this.f_26508_ = new OmniPathNodeMaker();
        this.f_26508_.m_77351_(true);
        return new PathFinder(this.f_26508_, range);
    }

    protected boolean m_7632_() {
        Level level = this.f_26494_.m_9236_();
        Intrinsics.checkNotNullExpressionValue((Object)level, (String)"entity.world");
        BlockGetter blockGetter = (BlockGetter)level;
        AABB aABB = this.f_26494_.m_20191_();
        Intrinsics.checkNotNullExpressionValue((Object)aABB, (String)"entity.boundingBox");
        boolean isTouchingLava = (Boolean)WorldExtensionsKt.getWaterAndLavaIn(blockGetter, aABB).component2();
        boolean isAtValidPosition = !this.f_26494_.m_20077_() && !this.f_26494_.m_204029_(FluidTags.f_13132_) || isTouchingLava && this.getMoving().getSwim().getCanSwimInLava() || this.f_26494_.m_20159_();
        return isAtValidPosition;
    }

    public boolean m_26576_() {
        return this.getMoving().getSwim().getCanSwimInWater();
    }

    public final void setCanPathThroughFire(boolean canPathThroughFire) {
        NodeEvaluator nodeEvaluator = this.f_26508_;
        Intrinsics.checkNotNull((Object)nodeEvaluator, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai.OmniPathNodeMaker");
        OmniPathNodeMaker omniPathNodeMaker = (OmniPathNodeMaker)nodeEvaluator;
        omniPathNodeMaker.setCanPathThroughFire(canPathThroughFire);
    }

    @NotNull
    protected Vec3 m_7475_() {
        return new Vec3(this.f_26494_.m_20185_(), (double)this.getPathfindingY(), this.f_26494_.m_20189_());
    }

    protected void m_7636_() {
        Vec3 vec3d;
        block17: {
            Node currentNode;
            block16: {
                boolean closeEnough;
                Vec3 targetVec;
                vec3d = this.m_7475_();
                BlockPos blockPos2 = this.m_26567_();
                Vec3 vec3 = blockPos2 != null && (blockPos2 = BlockPosExtensionsKt.toVec3d(blockPos2)) != null ? blockPos2.m_82520_(0.5, 0.0, 0.5) : (targetVec = null);
                if (targetVec != null && targetVec.m_82554_(vec3d) <= (double)this.navigationContext.getDestinationProximity() && this.f_26496_ != null) {
                    BlockPathTypes node;
                    this.f_26496_ = null;
                    this.cachedCurrentNode = null;
                    this.navigationContext.getOnArrival().invoke();
                    Path path = this.f_26496_;
                    BlockPathTypes blockPathTypes = path != null && (path = path.m_77401_()) != null ? path.f_77282_ : (node = null);
                    if (node != null && node != BlockPathTypes.OPEN && this.pokemonEntity.couldStopFlying()) {
                        this.pokemonEntity.setBehaviourFlag(PokemonBehaviourFlag.FLYING, false);
                    }
                    return;
                }
                this.f_26505_ = this.f_26494_.m_20205_() > 0.75f ? this.f_26494_.m_20205_() / 2.0f : 0.75f - this.f_26494_.m_20205_() / 2.0f;
                Path path = this.f_26496_;
                Intrinsics.checkNotNull((Object)path);
                currentNode = path.m_77401_();
                if (!Intrinsics.areEqual((Object)currentNode, (Object)this.cachedCurrentNode)) {
                    this.cachedCurrentNode = currentNode;
                    this.currentNodeDistance = (float)currentNode.m_164701_().m_82554_(this.pokemonEntity.m_20182_());
                } else if (this.cachedCurrentNode != null) {
                    Node node = this.cachedCurrentNode;
                    Intrinsics.checkNotNull((Object)node);
                    if (node.m_164701_().m_82554_(this.pokemonEntity.m_20182_()) > (double)(this.currentNodeDistance + 1.0f)) {
                        this.m_26569_();
                        this.navigationContext.getOnRecalculate().invoke((Object)true);
                        return;
                    }
                }
                Path path2 = this.f_26496_;
                Intrinsics.checkNotNull((Object)path2);
                Vec3 targetVec3d = path2.m_77380_((Entity)this.f_26494_);
                double d = Math.abs(this.f_26494_.m_20185_() - targetVec3d.f_82479_);
                double e = Math.abs(this.f_26494_.m_20186_() - targetVec3d.f_82480_);
                double f = Math.abs(this.f_26494_.m_20189_() - targetVec3d.f_82481_);
                boolean bl = closeEnough = d < (double)this.f_26505_ && f < (double)this.f_26505_ && e < 1.0;
                if (closeEnough) break block16;
                PathNavigation pathNavigation = this.f_26494_.m_21573_();
                Path path3 = this.f_26496_;
                Intrinsics.checkNotNull((Object)path3);
                if (!pathNavigation.m_264193_(path3.m_77401_().f_77282_) || !this.m_26559_(vec3d)) break block17;
            }
            Path path = this.f_26496_;
            Intrinsics.checkNotNull((Object)path);
            path.m_77374_();
            Path path4 = this.f_26496_;
            Intrinsics.checkNotNull((Object)path4);
            if (path4.m_77392_()) {
                this.f_26496_ = null;
                this.navigationContext.getOnArrival().invoke();
                if (currentNode.f_77282_ != BlockPathTypes.OPEN && this.pokemonEntity.couldStopFlying()) {
                    this.pokemonEntity.setBehaviourFlag(PokemonBehaviourFlag.FLYING, false);
                }
            } else {
                Path path5 = this.f_26496_;
                Intrinsics.checkNotNull((Object)path5);
                Node newNode = path5.m_77401_();
                if (currentNode.f_77282_ != newNode.f_77282_) {
                    if (newNode.f_77282_ == BlockPathTypes.OPEN) {
                        this.pokemonEntity.setBehaviourFlag(PokemonBehaviourFlag.FLYING, true);
                    } else if (currentNode.f_77282_ != BlockPathTypes.OPEN && this.pokemonEntity.couldStopFlying()) {
                        this.pokemonEntity.setBehaviourFlag(PokemonBehaviourFlag.FLYING, false);
                    }
                }
            }
        }
        this.m_6481_(vec3d);
    }

    public final boolean isAirborne(@NotNull Level world, @NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        return world.m_8055_(pos).m_60647_((BlockGetter)world, pos, PathComputationType.AIR) && world.m_8055_(pos.m_6625_(1)).m_60647_((BlockGetter)world, pos.m_6625_(1), PathComputationType.AIR) && world.m_8055_(pos.m_6625_(2)).m_60647_((BlockGetter)world, pos.m_6625_(2), PathComputationType.AIR);
    }

    public void m_7638_() {
        super.m_7638_();
    }

    @Nullable
    public final Path findPath(@NotNull BlockPos target, int distance) {
        Intrinsics.checkNotNullParameter((Object)target, (String)"target");
        return this.m_26551_((Set)ImmutableSet.of((Object)target), 8, false, distance);
    }

    @Nullable
    public Path m_7864_(@NotNull BlockPos target, int distance) {
        Path path;
        Intrinsics.checkNotNullParameter((Object)target, (String)"target");
        BlockPos target2 = target;
        BlockPos blockPos2 = null;
        if (this.world.m_8055_(target2).m_60795_() && !this.pokemonEntity.getBehaviour().getMoving().getFly().getCanFly()) {
            BlockPos blockPos3 = target2.m_7495_();
            Intrinsics.checkNotNullExpressionValue((Object)blockPos3, (String)"target.down()");
            blockPos2 = blockPos3;
            while (blockPos2.m_123342_() > this.world.m_141937_() && this.world.m_8055_(blockPos2).m_60795_()) {
                Intrinsics.checkNotNullExpressionValue((Object)blockPos2.m_7495_(), (String)"blockPos.down()");
            }
            while (blockPos2.m_123342_() < this.world.m_151558_() && this.world.m_8055_(blockPos2).m_60795_()) {
                Intrinsics.checkNotNullExpressionValue((Object)blockPos2.m_7494_(), (String)"blockPos.up()");
            }
            target2 = blockPos2;
        }
        if (!this.world.m_8055_(target2).m_280296_()) {
            path = this.findPath(target2, distance);
        } else {
            BlockPos blockPos4 = target2.m_7494_();
            Intrinsics.checkNotNullExpressionValue((Object)blockPos4, (String)"target.up()");
            blockPos2 = blockPos4;
            while (blockPos2.m_123342_() < this.world.m_151558_() && this.world.m_8055_(blockPos2).m_280296_()) {
                Intrinsics.checkNotNullExpressionValue((Object)blockPos2.m_7494_(), (String)"blockPos.up()");
            }
            path = this.findPath(blockPos2, distance);
        }
        Path path2 = path;
        return path2;
    }

    public final void startMovingTo(double x, double y, double z, double speed, @NotNull NavigationContext navigationContext) {
        Intrinsics.checkNotNullParameter((Object)navigationContext, (String)"navigationContext");
        this.navigationContext = navigationContext;
        this.m_26519_(x, y, z, speed);
    }

    public static /* synthetic */ void startMovingTo$default(PokemonNavigation pokemonNavigation, double d, double d2, double d3, double d4, NavigationContext navigationContext, int n, Object object) {
        if ((n & 8) != 0) {
            d4 = 1.0;
        }
        pokemonNavigation.startMovingTo(d, d2, d3, d4, navigationContext);
    }

    @Nullable
    public Path m_6570_(@NotNull Entity entity2, int distance) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        BlockPos blockPos2 = entity2.m_20183_();
        Intrinsics.checkNotNullExpressionValue((Object)blockPos2, (String)"entity.blockPos");
        return this.m_7864_(blockPos2, distance);
    }

    public boolean m_26536_(@Nullable Path path, double speed) {
        if (path != null && path.m_77398_() > 0) {
            Node node = path.m_77375_(0);
            Intrinsics.checkNotNull((Object)node);
            Node node2 = node;
            if (node2.f_77282_ == BlockPathTypes.OPEN && this.pokemonEntity.getForm().getBehaviour().getMoving().getFly().getCanFly() && !this.pokemonEntity.isFlying()) {
                this.pokemonEntity.setBehaviourFlag(PokemonBehaviourFlag.FLYING, true);
            }
        }
        return super.m_26536_(path, speed);
    }

    protected void m_6804_() {
        super.m_6804_();
        Path path = this.m_26570_();
        if (path == null) {
            return;
        }
        Path path2 = path;
        int i = 2;
        block0: while (i < path2.m_77398_()) {
            Node firstNode = path2.m_77375_(i - 2);
            Node middleNode = path2.m_77375_(i - 1);
            Node nextNode = path2.m_77375_(i);
            BlockPos blockPos2 = middleNode.m_77288_().m_121996_((Vec3i)firstNode.m_77288_());
            Intrinsics.checkNotNullExpressionValue((Object)blockPos2, (String)"middleNode.blockPos.subtract(firstNode.blockPos)");
            Vec3 directionToMiddle = BlockPosExtensionsKt.toVec3d(blockPos2).m_82541_();
            BlockPathTypes nodeType = firstNode.f_77282_;
            if (nodeType != middleNode.f_77282_ || nodeType != nextNode.f_77282_ || nodeType == BlockPathTypes.WALKABLE) {
                ++i;
                continue;
            }
            BlockPos blockPos3 = nextNode.m_77288_().m_121996_((Vec3i)middleNode.m_77288_());
            Intrinsics.checkNotNullExpressionValue((Object)blockPos3, (String)"nextNode.blockPos.subtract(middleNode.blockPos)");
            Vec3 directionToEnd = BlockPosExtensionsKt.toVec3d(blockPos3).m_82541_();
            if (Math.acos(directionToMiddle.m_82526_(directionToEnd)) > 1.0471975511965976) {
                ++i;
                continue;
            }
            BlockPos blockPos4 = nextNode.m_77288_().m_121996_((Vec3i)firstNode.m_77288_());
            Intrinsics.checkNotNullExpressionValue((Object)blockPos4, (String)"nextNode.blockPos.subtract(firstNode.blockPos)");
            Vec3 directionFromFirstToEnd = BlockPosExtensionsKt.toVec3d(blockPos4);
            double length = directionFromFirstToEnd.m_82553_();
            Vec3 vec3 = directionFromFirstToEnd.m_82541_();
            Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"directionFromFirstToEnd.normalize()");
            directionFromFirstToEnd = vec3;
            int dist = 1;
            int n = (int)Math.ceil(length) * 2;
            if (dist <= n) {
                while (true) {
                    Vec3 vec = firstNode.m_164701_().m_82549_(directionFromFirstToEnd.m_82490_((double)dist / 2.0));
                    BlockPathTypes interveningNodeType = this.pokemonEntity.getNavigation().f_26508_.m_7209_((BlockGetter)this.world, (int)vec.f_82479_, (int)vec.f_82480_, (int)vec.f_82481_, (Mob)this.pokemonEntity);
                    if (interveningNodeType != nodeType) {
                        ++i;
                        continue block0;
                    }
                    if (dist == n) break;
                    ++dist;
                }
            }
            List remainingNodes = new ArrayList();
            for (int j = i; j < path2.m_77398_(); ++j) {
                Node node = path2.m_77375_(j);
                Intrinsics.checkNotNullExpressionValue((Object)node, (String)"path.getNode(j)");
                remainingNodes.add(node);
            }
            path2.m_77388_(i + remainingNodes.size() - 1);
            int n2 = remainingNodes.size();
            for (int k = 0; k < n2; ++k) {
                path2.m_77377_(i - 1 + k, (Node)remainingNodes.get(k));
            }
        }
    }

    public final int getPathfindingY() {
        boolean inSwimmableFluid;
        boolean bl = inSwimmableFluid = this.f_26494_.m_204029_(FluidTags.f_13131_) && this.getMoving().getSwim().getCanSwimInWater() || this.f_26494_.m_204029_(FluidTags.f_13132_) && this.getMoving().getSwim().getCanSwimInLava();
        if (!inSwimmableFluid) {
            return Mth.m_14107_((double)(this.f_26494_.m_20186_() + 0.5));
        }
        return this.f_26494_.m_146904_();
    }

    public void m_26573_() {
        super.m_26573_();
        this.currentNodeDistance = -1.0f;
        this.cachedCurrentNode = null;
        this.f_26496_ = null;
        this.f_26508_.m_6802_();
        if (this.pokemonEntity.couldStopFlying()) {
            Level level = this.pokemonEntity.m_9236_();
            Intrinsics.checkNotNullExpressionValue((Object)level, (String)"pokemonEntity.world");
            BlockPos blockPos2 = this.pokemonEntity.m_20183_();
            Intrinsics.checkNotNullExpressionValue((Object)blockPos2, (String)"pokemonEntity.blockPos");
            if (!this.isAirborne(level, blockPos2)) {
                this.pokemonEntity.setBehaviourFlag(PokemonBehaviourFlag.FLYING, false);
            }
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\r\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000f\b\u0086\b\u0018\u00002\u00020\u0001B\u0085\u0001\u0012#\b\u0002\u0010\u0016\u001a\u001d\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u00070\u0002\u0012#\b\u0002\u0010\u0017\u001a\u001d\u0012\u0013\u0012\u00110\n\u00a2\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\u00070\u0002\u0012\u000e\b\u0002\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00070\r\u0012\u000e\b\u0002\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00070\r\u0012\b\b\u0002\u0010\u001a\u001a\u00020\n\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u0013\u00a2\u0006\u0004\b1\u00102J+\u0010\b\u001a\u001d\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u00070\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\b\u0010\tJ+\u0010\f\u001a\u001d\u0012\u0013\u0012\u00110\n\u00a2\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\u00070\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\f\u0010\tJ\u0016\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00070\rH\u00c6\u0003\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00070\rH\u00c6\u0003\u00a2\u0006\u0004\b\u0010\u0010\u000fJ\u0010\u0010\u0011\u001a\u00020\nH\u00c6\u0003\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0010\u0010\u0014\u001a\u00020\u0013H\u00c6\u0003\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u008e\u0001\u0010\u001c\u001a\u00020\u00002#\b\u0002\u0010\u0016\u001a\u001d\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u00070\u00022#\b\u0002\u0010\u0017\u001a\u001d\u0012\u0013\u0012\u00110\n\u00a2\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\u00070\u00022\u000e\b\u0002\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00070\r2\u000e\b\u0002\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00070\r2\b\b\u0002\u0010\u001a\u001a\u00020\n2\b\b\u0002\u0010\u001b\u001a\u00020\u0013H\u00c6\u0001\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u001a\u0010\u001f\u001a\u00020\n2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u001f\u0010 J\u0010\u0010\"\u001a\u00020!H\u00d6\u0001\u00a2\u0006\u0004\b\"\u0010#J\u0010\u0010%\u001a\u00020$H\u00d6\u0001\u00a2\u0006\u0004\b%\u0010&R\u0017\u0010\u001b\u001a\u00020\u00138\u0006\u00a2\u0006\f\n\u0004\b\u001b\u0010'\u001a\u0004\b(\u0010\u0015R\u001d\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00070\r8\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010)\u001a\u0004\b*\u0010\u000fR\u001d\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00070\r8\u0006\u00a2\u0006\f\n\u0004\b\u0019\u0010)\u001a\u0004\b+\u0010\u000fR2\u0010\u0016\u001a\u001d\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u00070\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010,\u001a\u0004\b-\u0010\tR2\u0010\u0017\u001a\u001d\u0012\u0013\u0012\u00110\n\u00a2\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\u00070\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010,\u001a\u0004\b.\u0010\tR\u0017\u0010\u001a\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u001a\u0010/\u001a\u0004\b0\u0010\u0012\u00a8\u00063"}, d2={"Lcom/cobblemon/mod/common/entity/pokemon/ai/PokemonNavigation$NavigationContext;", "", "Lkotlin/Function1;", "Lnet/minecraft/world/damagesource/DamageSource;", "Lkotlin/ParameterName;", "name", "damage", "", "component1", "()Lkotlin/jvm/functions/Function1;", "", "dueToDistance", "component2", "Lkotlin/Function0;", "component3", "()Lkotlin/jvm/functions/Function0;", "component4", "component5", "()Z", "", "component6", "()F", "onHit", "onRecalculate", "onArrival", "onCannotReach", "sprinting", "destinationProximity", "copy", "(Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;ZF)Lcom/cobblemon/mod/common/entity/pokemon/ai/PokemonNavigation$NavigationContext;", "other", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "F", "getDestinationProximity", "Lkotlin/jvm/functions/Function0;", "getOnArrival", "getOnCannotReach", "Lkotlin/jvm/functions/Function1;", "getOnHit", "getOnRecalculate", "Z", "getSprinting", "<init>", "(Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;ZF)V", "common"})
    public static final class NavigationContext {
        @NotNull
        private final Function1<DamageSource, Unit> onHit;
        @NotNull
        private final Function1<Boolean, Unit> onRecalculate;
        @NotNull
        private final Function0<Unit> onArrival;
        @NotNull
        private final Function0<Unit> onCannotReach;
        private final boolean sprinting;
        private final float destinationProximity;

        public NavigationContext(@NotNull Function1<? super DamageSource, Unit> onHit, @NotNull Function1<? super Boolean, Unit> onRecalculate, @NotNull Function0<Unit> onArrival, @NotNull Function0<Unit> onCannotReach, boolean sprinting, float destinationProximity) {
            Intrinsics.checkNotNullParameter(onHit, (String)"onHit");
            Intrinsics.checkNotNullParameter(onRecalculate, (String)"onRecalculate");
            Intrinsics.checkNotNullParameter(onArrival, (String)"onArrival");
            Intrinsics.checkNotNullParameter(onCannotReach, (String)"onCannotReach");
            this.onHit = onHit;
            this.onRecalculate = onRecalculate;
            this.onArrival = onArrival;
            this.onCannotReach = onCannotReach;
            this.sprinting = sprinting;
            this.destinationProximity = destinationProximity;
        }

        public /* synthetic */ NavigationContext(Function1 function1, Function1 function12, Function0 function0, Function0 function02, boolean bl, float f, int n, DefaultConstructorMarker defaultConstructorMarker) {
            if ((n & 1) != 0) {
                function1 = 1.INSTANCE;
            }
            if ((n & 2) != 0) {
                function12 = 2.INSTANCE;
            }
            if ((n & 4) != 0) {
                function0 = 3.INSTANCE;
            }
            if ((n & 8) != 0) {
                function02 = 4.INSTANCE;
            }
            if ((n & 0x10) != 0) {
                bl = false;
            }
            if ((n & 0x20) != 0) {
                f = 0.5f;
            }
            this((Function1<? super DamageSource, Unit>)function1, (Function1<? super Boolean, Unit>)function12, (Function0<Unit>)function0, (Function0<Unit>)function02, bl, f);
        }

        @NotNull
        public final Function1<DamageSource, Unit> getOnHit() {
            return this.onHit;
        }

        @NotNull
        public final Function1<Boolean, Unit> getOnRecalculate() {
            return this.onRecalculate;
        }

        @NotNull
        public final Function0<Unit> getOnArrival() {
            return this.onArrival;
        }

        @NotNull
        public final Function0<Unit> getOnCannotReach() {
            return this.onCannotReach;
        }

        public final boolean getSprinting() {
            return this.sprinting;
        }

        public final float getDestinationProximity() {
            return this.destinationProximity;
        }

        @NotNull
        public final Function1<DamageSource, Unit> component1() {
            return this.onHit;
        }

        @NotNull
        public final Function1<Boolean, Unit> component2() {
            return this.onRecalculate;
        }

        @NotNull
        public final Function0<Unit> component3() {
            return this.onArrival;
        }

        @NotNull
        public final Function0<Unit> component4() {
            return this.onCannotReach;
        }

        public final boolean component5() {
            return this.sprinting;
        }

        public final float component6() {
            return this.destinationProximity;
        }

        @NotNull
        public final NavigationContext copy(@NotNull Function1<? super DamageSource, Unit> onHit, @NotNull Function1<? super Boolean, Unit> onRecalculate, @NotNull Function0<Unit> onArrival, @NotNull Function0<Unit> onCannotReach, boolean sprinting, float destinationProximity) {
            Intrinsics.checkNotNullParameter(onHit, (String)"onHit");
            Intrinsics.checkNotNullParameter(onRecalculate, (String)"onRecalculate");
            Intrinsics.checkNotNullParameter(onArrival, (String)"onArrival");
            Intrinsics.checkNotNullParameter(onCannotReach, (String)"onCannotReach");
            return new NavigationContext(onHit, onRecalculate, onArrival, onCannotReach, sprinting, destinationProximity);
        }

        public static /* synthetic */ NavigationContext copy$default(NavigationContext navigationContext, Function1 function1, Function1 function12, Function0 function0, Function0 function02, boolean bl, float f, int n, Object object) {
            if ((n & 1) != 0) {
                function1 = navigationContext.onHit;
            }
            if ((n & 2) != 0) {
                function12 = navigationContext.onRecalculate;
            }
            if ((n & 4) != 0) {
                function0 = navigationContext.onArrival;
            }
            if ((n & 8) != 0) {
                function02 = navigationContext.onCannotReach;
            }
            if ((n & 0x10) != 0) {
                bl = navigationContext.sprinting;
            }
            if ((n & 0x20) != 0) {
                f = navigationContext.destinationProximity;
            }
            return navigationContext.copy(function1, function12, function0, function02, bl, f);
        }

        @NotNull
        public String toString() {
            return "NavigationContext(onHit=" + this.onHit + ", onRecalculate=" + this.onRecalculate + ", onArrival=" + this.onArrival + ", onCannotReach=" + this.onCannotReach + ", sprinting=" + this.sprinting + ", destinationProximity=" + this.destinationProximity + ")";
        }

        public int hashCode() {
            int result = this.onHit.hashCode();
            result = result * 31 + this.onRecalculate.hashCode();
            result = result * 31 + this.onArrival.hashCode();
            result = result * 31 + this.onCannotReach.hashCode();
            int n = this.sprinting ? 1 : 0;
            if (n != 0) {
                n = 1;
            }
            result = result * 31 + n;
            result = result * 31 + Float.hashCode(this.destinationProximity);
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof NavigationContext)) {
                return false;
            }
            NavigationContext navigationContext = (NavigationContext)other;
            if (!Intrinsics.areEqual(this.onHit, navigationContext.onHit)) {
                return false;
            }
            if (!Intrinsics.areEqual(this.onRecalculate, navigationContext.onRecalculate)) {
                return false;
            }
            if (!Intrinsics.areEqual(this.onArrival, navigationContext.onArrival)) {
                return false;
            }
            if (!Intrinsics.areEqual(this.onCannotReach, navigationContext.onCannotReach)) {
                return false;
            }
            if (this.sprinting != navigationContext.sprinting) {
                return false;
            }
            return Float.compare(this.destinationProximity, navigationContext.destinationProximity) == 0;
        }

        public NavigationContext() {
            this(null, null, null, null, false, 0.0f, 63, null);
        }
    }
}

