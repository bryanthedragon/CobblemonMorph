/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Ref$BooleanRef
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectTimeline;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffects;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.TargetsProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.UsersProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.ActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.MoveToTargetActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingFunctionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.PokemonNavigation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u001d\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007R\"\u0010\t\u001a\u00020\b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\b8\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u000f\u0010\n\u001a\u0004\b\u0010\u0010\fR\u0017\u0010\u0012\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u0019\u0010\u0017\u001a\u0004\u0018\u00010\u00168\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/api/moves/animations/keyframes/MoveToTargetActionEffectKeyframe;", "Lcom/cobblemon/mod/common/api/moves/animations/keyframes/ActionEffectKeyframe;", "Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;", "context", "Ljava/util/concurrent/CompletableFuture;", "", "play", "(Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;)Ljava/util/concurrent/CompletableFuture;", "", "proximity", "F", "getProximity", "()F", "setProximity", "(F)V", "speed", "getSpeed", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "timeout", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "getTimeout", "()Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "Lnet/minecraft/resources/ResourceLocation;", "timeoutActionEffect", "Lnet/minecraft/resources/ResourceLocation;", "getTimeoutActionEffect", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nMoveToTargetActionEffectKeyframe.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MoveToTargetActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/MoveToTargetActionEffectKeyframe\n+ 2 ActionEffectTimeline.kt\ncom/cobblemon/mod/common/api/moves/animations/ActionEffectContext\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,75:1\n73#2:76\n73#2:88\n800#3,11:77\n800#3,11:89\n1#4:100\n*S KotlinDebug\n*F\n+ 1 MoveToTargetActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/MoveToTargetActionEffectKeyframe\n*L\n32#1:76\n33#1:88\n32#1:77,11\n33#1:89,11\n*E\n"})
public final class MoveToTargetActionEffectKeyframe
implements ActionEffectKeyframe {
    private final float speed;
    @NotNull
    private final ExpressionLike timeout = MoLangExtensionsKt.asExpressionLike("4");
    private float proximity = -1.0f;
    @Nullable
    private final ResourceLocation timeoutActionEffect;

    public MoveToTargetActionEffectKeyframe() {
        this.speed = 1.0f;
    }

    public final float getSpeed() {
        return this.speed;
    }

    @NotNull
    public final ExpressionLike getTimeout() {
        return this.timeout;
    }

    public final float getProximity() {
        return this.proximity;
    }

    public final void setProximity(float f) {
        this.proximity = f;
    }

    @Nullable
    public final ResourceLocation getTimeoutActionEffect() {
        return this.timeoutActionEffect;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public CompletableFuture<Unit> play(@NotNull ActionEffectContext context) {
        Object object;
        float proximity;
        Ref.BooleanRef timedOut;
        CompletableFuture<Unit> future2;
        Object target;
        PokemonEntity user;
        block9: {
            block8: {
                void $this$filterIsInstanceTo$iv$iv$iv;
                void $this$filterIsInstanceTo$iv$iv$iv2;
                Intrinsics.checkNotNullParameter((Object)context, (String)"context");
                ActionEffectContext this_$iv = context;
                boolean $i$f$findOneProvider = false;
                Iterable $this$filterIsInstance$iv$iv = this_$iv.getProviders();
                boolean $i$f$filterIsInstance22 = false;
                Iterable iterable = $this$filterIsInstance$iv$iv;
                Collection destination$iv$iv$iv = new ArrayList();
                boolean $i$f$filterIsInstanceTo = false;
                for (Object element$iv$iv$iv : $this$filterIsInstanceTo$iv$iv$iv2) {
                    if (!(element$iv$iv$iv instanceof UsersProvider)) continue;
                    destination$iv$iv$iv.add(element$iv$iv$iv);
                }
                Object object2 = (UsersProvider)CollectionsKt.firstOrNull((List)((List)destination$iv$iv$iv));
                Entity entity2 = object2 != null && (object2 = ((UsersProvider)object2).getEntities()) != null ? (Entity)CollectionsKt.firstOrNull((List)object2) : null;
                PokemonEntity pokemonEntity = entity2 instanceof PokemonEntity ? (PokemonEntity)entity2 : null;
                if (pokemonEntity == null) {
                    CompletableFuture<Unit> completableFuture = CompletableFuture.completedFuture(Unit.INSTANCE);
                    Intrinsics.checkNotNullExpressionValue(completableFuture, (String)"completedFuture(Unit)");
                    return completableFuture;
                }
                user = pokemonEntity;
                ActionEffectContext this_$iv2 = context;
                boolean $i$f$findOneProvider2 = false;
                Iterable $this$filterIsInstance$iv$iv2 = this_$iv2.getProviders();
                boolean $i$f$filterIsInstance32 = false;
                Iterable $i$f$filterIsInstance22 = $this$filterIsInstance$iv$iv2;
                Collection destination$iv$iv$iv2 = new ArrayList();
                boolean $i$f$filterIsInstanceTo2 = false;
                for (Object element$iv$iv$iv : $this$filterIsInstanceTo$iv$iv$iv) {
                    if (!(element$iv$iv$iv instanceof TargetsProvider)) continue;
                    destination$iv$iv$iv2.add(element$iv$iv$iv);
                }
                Object object3 = (TargetsProvider)CollectionsKt.firstOrNull((List)((List)destination$iv$iv$iv2));
                if (object3 == null || (object3 = ((TargetsProvider)object3).getEntities()) == null || (object3 = (Entity)CollectionsKt.firstOrNull((List)object3)) == null) {
                    CompletableFuture<Unit> completableFuture = CompletableFuture.completedFuture(Unit.INSTANCE);
                    Intrinsics.checkNotNullExpressionValue(completableFuture, (String)"completedFuture(Unit)");
                    return completableFuture;
                }
                target = object3;
                future2 = new CompletableFuture<Unit>();
                timedOut = new Ref.BooleanRef();
                Float $i$f$filterIsInstance32 = Float.valueOf(this.proximity);
                float it = ((Number)$i$f$filterIsInstance32).floatValue();
                boolean bl = false;
                Float f = !(it == -1.0f) ? $i$f$filterIsInstance32 : null;
                proximity = f != null ? f.floatValue() : (float)(Math.sqrt((double)2 * Math.pow(user.m_20191_().m_82362_(), 2)) + (double)1.5f + Math.sqrt((double)2 * Math.pow(target.m_20191_().m_82362_(), 2)));
                if (!(proximity <= ($this$filterIsInstance$iv$iv2 = target.m_20270_((Entity)user)) ? $this$filterIsInstance$iv$iv2 <= 20.0f : false)) {
                    future2.complete(Unit.INSTANCE);
                    return future2;
                }
                object = this.timeoutActionEffect;
                if (object == null) break block8;
                ResourceLocation it2 = object;
                boolean bl2 = false;
                ActionEffectTimeline actionEffectTimeline = ActionEffects.INSTANCE.getActionEffects().get(it2);
                object = actionEffectTimeline;
                if (actionEffectTimeline != null) break block9;
            }
            object = ActionEffectTimeline.Companion.getNONE();
        }
        Object timeoutEffect = object;
        PokemonNavigation nav = user.getNavigation();
        PokemonNavigation.NavigationContext navContext2 = new PokemonNavigation.NavigationContext(null, null, (Function0)new Function0<Unit>(future2, timedOut){
            final /* synthetic */ CompletableFuture<Unit> $future;
            final /* synthetic */ Ref.BooleanRef $timedOut;
            {
                this.$future = $future;
                this.$timedOut = $timedOut;
                super(0);
            }

            public final void invoke() {
                if (!this.$future.isDone() && !this.$timedOut.element) {
                    this.$future.complete(Unit.INSTANCE);
                }
            }
        }, (Function0)new Function0<Unit>(future2, timedOut, (ActionEffectTimeline)timeoutEffect, context){
            final /* synthetic */ CompletableFuture<Unit> $future;
            final /* synthetic */ Ref.BooleanRef $timedOut;
            final /* synthetic */ ActionEffectTimeline $timeoutEffect;
            final /* synthetic */ ActionEffectContext $context;
            {
                this.$future = $future;
                this.$timedOut = $timedOut;
                this.$timeoutEffect = $timeoutEffect;
                this.$context = $context;
                super(0);
            }

            public final void invoke() {
                if (!this.$future.isDone() && !this.$timedOut.element) {
                    this.$timedOut.element = true;
                    this.$timeoutEffect.run(this.$context).thenApply(arg_0 -> play.navContext.2.invoke$lambda$0((Function1)new Function1<Unit, Boolean>(this.$future){
                        final /* synthetic */ CompletableFuture<Unit> $future;
                        {
                            this.$future = $future;
                            super(1);
                        }

                        public final Boolean invoke(Unit it) {
                            return this.$future.complete(Unit.INSTANCE);
                        }
                    }, arg_0));
                }
            }

            private static final Boolean invoke$lambda$0(Function1 $tmp0, Object p0) {
                Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
                return (Boolean)$tmp0.invoke(p0);
            }
        }, false, proximity, 19, null);
        SchedulingFunctionsKt.afterOnServer$default(0, this.timeout.resolveFloat(context.getRuntime()), (Function0)new Function0<Unit>(future2, timedOut, (ActionEffectTimeline)timeoutEffect, context, nav){
            final /* synthetic */ CompletableFuture<Unit> $future;
            final /* synthetic */ Ref.BooleanRef $timedOut;
            final /* synthetic */ ActionEffectTimeline $timeoutEffect;
            final /* synthetic */ ActionEffectContext $context;
            final /* synthetic */ PokemonNavigation $nav;
            {
                this.$future = $future;
                this.$timedOut = $timedOut;
                this.$timeoutEffect = $timeoutEffect;
                this.$context = $context;
                this.$nav = $nav;
                super(0);
            }

            public final void invoke() {
                if (!this.$future.isDone() && !this.$timedOut.element) {
                    this.$timedOut.element = true;
                    this.$timeoutEffect.run(this.$context).thenApply(arg_0 -> play.1.invoke$lambda$0((Function1)new Function1<Unit, Boolean>(this.$future){
                        final /* synthetic */ CompletableFuture<Unit> $future;
                        {
                            this.$future = $future;
                            super(1);
                        }

                        public final Boolean invoke(Unit it) {
                            return this.$future.complete(Unit.INSTANCE);
                        }
                    }, arg_0));
                    this.$nav.m_26573_();
                }
            }

            private static final Boolean invoke$lambda$0(Function1 $tmp0, Object p0) {
                Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
                return (Boolean)$tmp0.invoke(p0);
            }
        }, 1, null);
        nav.startMovingTo(target.m_20185_(), target.m_20186_(), target.m_20189_(), this.speed, navContext2);
        return future2;
    }

    @Override
    public void interrupt(@NotNull ActionEffectContext context) {
        ActionEffectKeyframe.DefaultImpls.interrupt(this, context);
    }

    @Override
    @NotNull
    public CompletableFuture<Unit> skip() {
        return ActionEffectKeyframe.DefaultImpls.skip(this);
    }
}

