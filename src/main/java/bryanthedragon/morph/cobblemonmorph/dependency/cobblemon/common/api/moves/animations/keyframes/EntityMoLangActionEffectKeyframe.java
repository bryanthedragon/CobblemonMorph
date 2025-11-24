/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.EntityProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.UsersProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.ConditionalActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.EntityConditionalActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingFunctionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.effect.RunPosableMoLangPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0007\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u001d\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bR\"\u0010\n\u001a\u00020\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\t8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0010\u0010\u000b\u001a\u0004\b\u0011\u0010\rR\u001d\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00130\u00128\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0019\u001a\u00020\u00188\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001c\u00a8\u0006\u001f"}, d2={"Lcom/cobblemon/mod/common/api/moves/animations/keyframes/EntityMoLangActionEffectKeyframe;", "Lcom/cobblemon/mod/common/api/moves/animations/keyframes/ConditionalActionEffectKeyframe;", "Lcom/cobblemon/mod/common/api/moves/animations/keyframes/EntityConditionalActionEffectKeyframe;", "Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;", "context", "Ljava/util/concurrent/CompletableFuture;", "", "playWhenTrue", "(Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;)Ljava/util/concurrent/CompletableFuture;", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "delay", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "getDelay", "()Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "setDelay", "(Lcom/cobblemon/mod/common/api/molang/ExpressionLike;)V", "entityCondition", "getEntityCondition", "", "", "expressions", "Ljava/util/Set;", "getExpressions", "()Ljava/util/Set;", "", "visibilityRange", "I", "getVisibilityRange", "()I", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nEntityMoLangActionEffectKeyframe.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EntityMoLangActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/EntityMoLangActionEffectKeyframe\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,52:1\n800#2,11:53\n1360#2:64\n1446#2,2:65\n766#2:67\n857#2,2:68\n1448#2,3:70\n1855#2,2:73\n*S KotlinDebug\n*F\n+ 1 EntityMoLangActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/EntityMoLangActionEffectKeyframe\n*L\n39#1:53,11\n40#1:64\n40#1:65,2\n40#1:67\n40#1:68,2\n40#1:70,3\n46#1:73,2\n*E\n"})
public final class EntityMoLangActionEffectKeyframe
extends ConditionalActionEffectKeyframe
implements EntityConditionalActionEffectKeyframe {
    @NotNull
    private ExpressionLike delay = MoLangExtensionsKt.asExpressionLike("0");
    @NotNull
    private final Set<String> expressions = new LinkedHashSet();
    private final int visibilityRange;
    @NotNull
    private final ExpressionLike entityCondition = MoLangExtensionsKt.asExpressionLike("q.entity.is_user");

    public EntityMoLangActionEffectKeyframe() {
        this.visibilityRange = 200;
    }

    @NotNull
    public final ExpressionLike getDelay() {
        return this.delay;
    }

    public final void setDelay(@NotNull ExpressionLike expressionLike) {
        Intrinsics.checkNotNullParameter((Object)expressionLike, (String)"<set-?>");
        this.delay = expressionLike;
    }

    @NotNull
    public final Set<String> getExpressions() {
        return this.expressions;
    }

    public final int getVisibilityRange() {
        return this.visibilityRange;
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
        void $this$flatMapTo$iv$iv;
        Iterable $this$filterIsInstanceTo$iv$iv;
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Iterable $this$filterIsInstance$iv = context.getProviders();
        boolean $i$f$filterIsInstance = false;
        Iterable iterable = $this$filterIsInstance$iv;
        Collection destination$iv$iv = new ArrayList();
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
            void $this$filterTo$iv$iv;
            EntityProvider prov = (EntityProvider)element$iv$iv;
            boolean bl = false;
            Iterable $this$filter$iv = prov.getEntities();
            boolean $i$f$filter = false;
            Iterable iterable2 = $this$filter$iv;
            Collection destination$iv$iv2 = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv2 : $this$filterTo$iv$iv) {
                Entity it = (Entity)element$iv$iv2;
                boolean bl2 = false;
                if (!this.test(context, it, prov instanceof UsersProvider)) continue;
                destination$iv$iv2.add(element$iv$iv2);
            }
            Iterable list$iv$iv = (List)destination$iv$iv2;
            CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
        }
        List entities2 = (List)destination$iv$iv;
        for (Entity entity2 : entities2) {
            Level level = entity2.m_9236_();
            Intrinsics.checkNotNull((Object)level, (String)"null cannot be cast to non-null type net.minecraft.server.world.ServerWorld");
            ServerLevel world = (ServerLevel)level;
            List players2 = world.m_8795_(arg_0 -> EntityMoLangActionEffectKeyframe.playWhenTrue$lambda$2((Function1)new Function1<ServerPlayer, Boolean>(entity2, this){
                final /* synthetic */ Entity $entity;
                final /* synthetic */ EntityMoLangActionEffectKeyframe this$0;
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
            RunPosableMoLangPacket pkt = new RunPosableMoLangPacket(entity2.m_19879_(), this.expressions);
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

    private static final boolean playWhenTrue$lambda$2(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }
}

