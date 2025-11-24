/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.EntityProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.UsersProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.ConditionalActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.EntityConditionalActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingFunctionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.animation.PlayPoseableAnimationPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\t\u0018\u00002\u00020\u00012\u00020\u0002B\u0007\u00a2\u0006\u0004\b)\u0010*J\u001d\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bR(\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\"\u0010\u0012\u001a\u00020\u00118\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u00118\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0013\u001a\u0004\b\u0019\u0010\u0015R(\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\"\u0010#\u001a\u00020\"8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b#\u0010$\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(\u00a8\u0006+"}, d2={"Lcom/cobblemon/mod/common/api/moves/animations/keyframes/AnimationActionEffectKeyframe;", "Lcom/cobblemon/mod/common/api/moves/animations/keyframes/ConditionalActionEffectKeyframe;", "Lcom/cobblemon/mod/common/api/moves/animations/keyframes/EntityConditionalActionEffectKeyframe;", "Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;", "context", "Ljava/util/concurrent/CompletableFuture;", "", "playWhenTrue", "(Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;)Ljava/util/concurrent/CompletableFuture;", "", "", "animation", "Ljava/util/Set;", "getAnimation", "()Ljava/util/Set;", "setAnimation", "(Ljava/util/Set;)V", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "delay", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "getDelay", "()Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "setDelay", "(Lcom/cobblemon/mod/common/api/molang/ExpressionLike;)V", "entityCondition", "getEntityCondition", "", "Lcom/bedrockk/molang/Expression;", "variables", "Ljava/util/List;", "getVariables", "()Ljava/util/List;", "setVariables", "(Ljava/util/List;)V", "", "visibilityRange", "I", "getVisibilityRange", "()I", "setVisibilityRange", "(I)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nAnimationActionEffectKeyframe.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AnimationActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/AnimationActionEffectKeyframe\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,54:1\n800#2,11:55\n1360#2:66\n1446#2,2:67\n766#2:69\n857#2,2:70\n1448#2,3:72\n1549#2:75\n1620#2,3:76\n1549#2:79\n1620#2,2:80\n1622#2:83\n1855#2,2:84\n1#3:82\n*S KotlinDebug\n*F\n+ 1 AnimationActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/AnimationActionEffectKeyframe\n*L\n31#1:55,11\n32#1:66\n32#1:67,2\n32#1:69\n32#1:70,2\n32#1:72,3\n34#1:75\n34#1:76,3\n37#1:79\n37#1:80,2\n37#1:83\n49#1:84,2\n*E\n"})
public final class AnimationActionEffectKeyframe
extends ConditionalActionEffectKeyframe
implements EntityConditionalActionEffectKeyframe {
    @NotNull
    private ExpressionLike delay = MoLangExtensionsKt.asExpressionLike("0");
    private int visibilityRange = 200;
    @NotNull
    private Set<String> animation = SetsKt.setOf((Object)"physical");
    @NotNull
    private List<? extends Expression> variables = CollectionsKt.emptyList();
    @NotNull
    private final ExpressionLike entityCondition = MoLangExtensionsKt.asExpressionLike("q.entity.is_user");

    @NotNull
    public final ExpressionLike getDelay() {
        return this.delay;
    }

    public final void setDelay(@NotNull ExpressionLike expressionLike) {
        Intrinsics.checkNotNullParameter((Object)expressionLike, (String)"<set-?>");
        this.delay = expressionLike;
    }

    public final int getVisibilityRange() {
        return this.visibilityRange;
    }

    public final void setVisibilityRange(int n) {
        this.visibilityRange = n;
    }

    @NotNull
    public final Set<String> getAnimation() {
        return this.animation;
    }

    public final void setAnimation(@NotNull Set<String> set2) {
        Intrinsics.checkNotNullParameter(set2, (String)"<set-?>");
        this.animation = set2;
    }

    @NotNull
    public final List<Expression> getVariables() {
        return this.variables;
    }

    public final void setVariables(@NotNull List<? extends Expression> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.variables = list;
    }

    @Override
    @NotNull
    public ExpressionLike getEntityCondition() {
        return this.entityCondition;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public CompletableFuture<Unit> playWhenTrue(@NotNull ActionEffectContext context) {
        void $this$mapTo$iv$iv;
        Collection collection;
        void $this$mapTo$iv$iv2;
        Object object;
        String $this$filterTo$iv$iv;
        void $this$flatMapTo$iv$iv;
        Iterable $this$filterIsInstanceTo$iv$iv;
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Iterable $this$filterIsInstance$iv = context.getProviders();
        boolean $i$f$filterIsInstance = false;
        Iterable iterable = $this$filterIsInstance$iv;
        Iterable destination$iv$iv = new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (!(element$iv$iv instanceof EntityProvider)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        Iterable $this$flatMap$iv = (List)destination$iv$iv;
        boolean $i$f$flatMap = false;
        $this$filterIsInstanceTo$iv$iv = $this$flatMap$iv;
        destination$iv$iv = new ArrayList();
        boolean $i$f$flatMapTo = false;
        for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
            EntityProvider prov = (EntityProvider)element$iv$iv;
            boolean bl = false;
            Iterable $this$filter$iv = prov.getEntities();
            boolean $i$f$filter = false;
            Iterable iterable2 = $this$filter$iv;
            Collection destination$iv$iv2 = new ArrayList();
            boolean $i$f$filterTo = false;
            object = $this$filterTo$iv$iv.iterator();
            while (object.hasNext()) {
                Object element$iv$iv2 = object.next();
                Entity it = (Entity)element$iv$iv2;
                boolean bl2 = false;
                if (!this.test(context, it, prov instanceof UsersProvider)) continue;
                destination$iv$iv2.add(element$iv$iv2);
            }
            Iterable list$iv$iv = (List)destination$iv$iv2;
            CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
        }
        List entities2 = (List)destination$iv$iv;
        Iterable $this$map$iv = this.variables;
        boolean $i$f$map = false;
        destination$iv$iv = $this$map$iv;
        Iterable destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv2) {
            void it;
            Expression bl = (Expression)item$iv$iv;
            collection = destination$iv$iv3;
            boolean bl3 = false;
            collection.add(it.getOriginalString());
        }
        Set expressions = CollectionsKt.toSet((Iterable)((List)destination$iv$iv3));
        Iterable $this$map$iv2 = this.animation;
        boolean $i$f$map2 = false;
        destination$iv$iv3 = $this$map$iv2;
        Collection destination$iv$iv4 = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv2, (int)10));
        boolean $i$f$mapTo2 = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            String bl3 = (String)item$iv$iv;
            collection = destination$iv$iv4;
            boolean bl = false;
            try {
                String it2 = $this$filterTo$iv$iv = MoLangExtensionsKt.asExpressionLike((String)it).resolveString(context.getRuntime());
                boolean bl4 = false;
                String string = !Intrinsics.areEqual((Object)it2, (Object)"0.0") ? $this$filterTo$iv$iv : null;
                if (string == null) {
                    string = it;
                }
                object = string;
            }
            catch (Exception e) {
                object = it;
            }
            collection.add(object);
        }
        Set animation = CollectionsKt.toSet((Iterable)((List)destination$iv$iv4));
        for (Entity entity2 : entities2) {
            Level level = entity2.m_9236_();
            Intrinsics.checkNotNull((Object)level, (String)"null cannot be cast to non-null type net.minecraft.server.world.ServerWorld");
            ServerLevel world = (ServerLevel)level;
            List players2 = world.m_8795_(arg_0 -> AnimationActionEffectKeyframe.playWhenTrue$lambda$5((Function1)new Function1<ServerPlayer, Boolean>(entity2, this){
                final /* synthetic */ Entity $entity;
                final /* synthetic */ AnimationActionEffectKeyframe this$0;
                {
                    this.$entity = $entity;
                    this.this$0 = $receiver;
                    super(1);
                }

                @NotNull
                public final Boolean invoke(ServerPlayer it) {
                    return it.m_20270_(this.$entity) <= (float)this.this$0.getVisibilityRange();
                }
            }, arg_0));
            PlayPoseableAnimationPacket pkt = new PlayPoseableAnimationPacket(entity2.m_19879_(), animation, expressions);
            Intrinsics.checkNotNullExpressionValue((Object)players2, (String)"players");
            Iterable $this$forEach$iv = players2;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                ServerPlayer it = (ServerPlayer)element$iv;
                boolean bl = false;
                Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
                CobblemonNetwork.INSTANCE.sendPacket(it, pkt);
            }
        }
        return SchedulingFunctionsKt.delayedFuture$default(0, this.delay.resolveFloat(context.getRuntime()), true, 1, null);
    }

    @Override
    public boolean test(@NotNull ActionEffectContext context, @NotNull Entity entity2, boolean isUser) {
        return EntityConditionalActionEffectKeyframe.DefaultImpls.test(this, context, entity2, isUser);
    }

    private static final boolean playWhenTrue$lambda$5(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }
}

