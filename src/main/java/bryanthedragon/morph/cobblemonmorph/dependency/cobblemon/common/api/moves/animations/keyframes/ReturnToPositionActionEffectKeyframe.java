/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Ref$BooleanRef
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectTimeline;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffects;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.UsersProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.ActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.ReturnToPositionActionEffectKeyframe;
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
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u001d\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007R\u001a\u0010\t\u001a\u00020\b8\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u000e\u001a\u00020\r8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u0019\u0010\u0013\u001a\u0004\u0018\u00010\u00128\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/api/moves/animations/keyframes/ReturnToPositionActionEffectKeyframe;", "Lcom/cobblemon/mod/common/api/moves/animations/keyframes/ActionEffectKeyframe;", "Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;", "context", "Ljava/util/concurrent/CompletableFuture;", "", "play", "(Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;)Ljava/util/concurrent/CompletableFuture;", "", "speed", "F", "getSpeed", "()F", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "timeout", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "getTimeout", "()Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "Lnet/minecraft/resources/ResourceLocation;", "timeoutActionEffect", "Lnet/minecraft/resources/ResourceLocation;", "getTimeoutActionEffect", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nReturnToPositionActionEffectKeyframe.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ReturnToPositionActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/ReturnToPositionActionEffectKeyframe\n+ 2 ActionEffectTimeline.kt\ncom/cobblemon/mod/common/api/moves/animations/ActionEffectContext\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,70:1\n73#2:71\n800#3,11:72\n1#4:83\n*S KotlinDebug\n*F\n+ 1 ReturnToPositionActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/ReturnToPositionActionEffectKeyframe\n*L\n30#1:71\n30#1:72,11\n*E\n"})
public final class ReturnToPositionActionEffectKeyframe
implements ActionEffectKeyframe {
    private final float speed;
    @NotNull
    private final ExpressionLike timeout = MoLangExtensionsKt.asExpressionLike("4");
    @Nullable
    private final ResourceLocation timeoutActionEffect;

    public ReturnToPositionActionEffectKeyframe() {
        this.speed = 1.0f;
    }

    public final float getSpeed() {
        return this.speed;
    }

    @NotNull
    public final ExpressionLike getTimeout() {
        return this.timeout;
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
        Ref.BooleanRef timedOut;
        Vec3 pos;
        CompletableFuture<Unit> future2;
        PokemonEntity user;
        block8: {
            block7: {
                void $this$filterIsInstanceTo$iv$iv$iv;
                Intrinsics.checkNotNullParameter((Object)context, (String)"context");
                ActionEffectContext this_$iv = context;
                boolean $i$f$findOneProvider = false;
                Object $this$filterIsInstance$iv$iv = this_$iv.getProviders();
                boolean $i$f$filterIsInstance = false;
                Iterable iterable = $this$filterIsInstance$iv$iv;
                Collection destination$iv$iv$iv = new ArrayList();
                boolean $i$f$filterIsInstanceTo = false;
                for (Object element$iv$iv$iv : $this$filterIsInstanceTo$iv$iv$iv) {
                    if (!(element$iv$iv$iv instanceof UsersProvider)) continue;
                    destination$iv$iv$iv.add(element$iv$iv$iv);
                }
                Object object2 = (UsersProvider)CollectionsKt.firstOrNull((List)((List)destination$iv$iv$iv));
                Entity entity2 = object2 != null && (object2 = ((UsersProvider)object2).getEntities()) != null ? (Entity)CollectionsKt.firstOrNull((List)object2) : null;
                PokemonEntity pokemonEntity = entity2 instanceof PokemonEntity ? (PokemonEntity)entity2 : null;
                if (pokemonEntity == null) {
                    return this.skip();
                }
                user = pokemonEntity;
                future2 = new CompletableFuture<Unit>();
                MoValue moValue = context.getRuntime().getEnvironment().getValue(SetsKt.setOf((Object)(user.m_20149_() + "-pos")).iterator());
                $this$filterIsInstance$iv$iv = moValue != null ? moValue.value() : null;
                ObjectValue objectValue = $this$filterIsInstance$iv$iv instanceof ObjectValue ? (ObjectValue)$this$filterIsInstance$iv$iv : null;
                this_$iv = objectValue != null ? objectValue.getObj() : null;
                Vec3 vec3 = this_$iv instanceof Vec3 ? (Vec3)this_$iv : null;
                if (vec3 == null) {
                    return this.skip();
                }
                pos = vec3;
                timedOut = new Ref.BooleanRef();
                if (pos.m_82554_(user.m_20182_()) > 20.0) {
                    future2.complete(Unit.INSTANCE);
                    return future2;
                }
                object = this.timeoutActionEffect;
                if (object == null) break block7;
                ResourceLocation it = object;
                boolean bl = false;
                ActionEffectTimeline actionEffectTimeline = ActionEffects.INSTANCE.getActionEffects().get(it);
                object = actionEffectTimeline;
                if (actionEffectTimeline != null) break block8;
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
        }, false, 0.0f, 51, null);
        SchedulingFunctionsKt.after$default(0, this.timeout.resolveFloat(context.getRuntime()), true, (Function0)new Function0<Unit>(future2, timedOut, (ActionEffectTimeline)timeoutEffect, context, nav){
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
        nav.startMovingTo(pos.f_82479_, pos.f_82480_, pos.f_82481_, this.speed, navContext2);
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

