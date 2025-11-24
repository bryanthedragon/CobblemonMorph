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
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.EntityProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.UsersProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.ConditionalActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.EntityConditionalActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingFunctionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.Poseable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.effect.SpawnSnowstormEntityParticlePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0007\u00a2\u0006\u0004\b\u001f\u0010 J\u001d\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\n\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR$\u0010\u000f\u001a\u0004\u0018\u00010\u000e8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\t8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0015\u0010\u000b\u001a\u0004\b\u0016\u0010\rR\"\u0010\u0017\u001a\u00020\u000e8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0017\u0010\u0010\u001a\u0004\b\u0018\u0010\u0012\"\u0004\b\u0019\u0010\u0014R\u001a\u0010\u001b\u001a\u00020\u001a8\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001e\u00a8\u0006!"}, d2={"Lcom/cobblemon/mod/common/api/moves/animations/keyframes/EntityParticlesActionEffectKeyframe;", "Lcom/cobblemon/mod/common/api/moves/animations/keyframes/ConditionalActionEffectKeyframe;", "Lcom/cobblemon/mod/common/api/moves/animations/keyframes/EntityConditionalActionEffectKeyframe;", "Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;", "context", "Ljava/util/concurrent/CompletableFuture;", "", "playWhenTrue", "(Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;)Ljava/util/concurrent/CompletableFuture;", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "delay", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "getDelay", "()Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "", "effect", "Ljava/lang/String;", "getEffect", "()Ljava/lang/String;", "setEffect", "(Ljava/lang/String;)V", "entityCondition", "getEntityCondition", "locator", "getLocator", "setLocator", "", "visibilityRange", "I", "getVisibilityRange", "()I", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nEntityParticlesActionEffectKeyframe.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EntityParticlesActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/EntityParticlesActionEffectKeyframe\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,55:1\n800#2,11:56\n1360#2:67\n1446#2,2:68\n766#2:70\n857#2,2:71\n1448#2,3:73\n766#2:77\n857#2,2:78\n1855#2,2:80\n1#3:76\n*S KotlinDebug\n*F\n+ 1 EntityParticlesActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/EntityParticlesActionEffectKeyframe\n*L\n38#1:56,11\n39#1:67\n39#1:68,2\n39#1:70\n39#1:71,2\n39#1:73,3\n47#1:77\n47#1:78,2\n47#1:80,2\n*E\n"})
public final class EntityParticlesActionEffectKeyframe
extends ConditionalActionEffectKeyframe
implements EntityConditionalActionEffectKeyframe {
    @NotNull
    private final ExpressionLike entityCondition = MoLangExtensionsKt.asExpressionLike("q.entity.is_user");
    @Nullable
    private String effect;
    @NotNull
    private String locator = "root";
    @NotNull
    private final ExpressionLike delay = MoLangExtensionsKt.asExpressionLike("0");
    private final int visibilityRange;

    public EntityParticlesActionEffectKeyframe() {
        this.visibilityRange = 200;
    }

    @Override
    @NotNull
    public ExpressionLike getEntityCondition() {
        return this.entityCondition;
    }

    @Nullable
    public final String getEffect() {
        return this.effect;
    }

    public final void setEffect(@Nullable String string) {
        this.effect = string;
    }

    @NotNull
    public final String getLocator() {
        return this.locator;
    }

    public final void setLocator(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.locator = string;
    }

    @NotNull
    public final ExpressionLike getDelay() {
        return this.delay;
    }

    public final int getVisibilityRange() {
        return this.visibilityRange;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public CompletableFuture<Unit> playWhenTrue(@NotNull ActionEffectContext context) {
        void $this$forEach$iv;
        void $this$filterTo$iv$iv;
        Object $this$flatMapTo$iv$iv;
        Object element$iv$iv2;
        Iterable $this$filterIsInstanceTo$iv$iv;
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Iterable $this$filterIsInstance$iv = context.getProviders();
        boolean $i$f$filterIsInstance = false;
        Iterable iterable = $this$filterIsInstance$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        for (Object element$iv$iv2 : $this$filterIsInstanceTo$iv$iv) {
            if (!(element$iv$iv2 instanceof EntityProvider)) continue;
            destination$iv$iv.add(element$iv$iv2);
        }
        Iterable $this$flatMap$iv = (List)destination$iv$iv;
        boolean $i$f$flatMap22 = false;
        $this$filterIsInstanceTo$iv$iv = $this$flatMap$iv;
        destination$iv$iv = new ArrayList();
        boolean $i$f$flatMapTo = false;
        Iterator iterator = $this$flatMapTo$iv$iv.iterator();
        while (iterator.hasNext()) {
            void $this$filterTo$iv$iv2;
            element$iv$iv2 = iterator.next();
            EntityProvider prov = (EntityProvider)element$iv$iv2;
            boolean bl = false;
            Iterable $this$filter$iv = prov.getEntities();
            boolean $i$f$filter = false;
            Iterable iterable2 = $this$filter$iv;
            Collection destination$iv$iv2 = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv3 : $this$filterTo$iv$iv2) {
                Entity it = (Entity)element$iv$iv3;
                boolean bl2 = false;
                if (!this.test(context, it, prov instanceof UsersProvider)) continue;
                destination$iv$iv2.add(element$iv$iv3);
            }
            Iterable list$iv$iv = (List)destination$iv$iv2;
            CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
        }
        List entities2 = (List)destination$iv$iv;
        try {
            Object object;
            block12: {
                block11: {
                    object = this.effect;
                    if (object == null || (object = MoLangExtensionsKt.asExpressionLike((String)object)) == null || (object = object.resolveString(context.getRuntime())) == null) break block11;
                    element$iv$iv2 = object;
                    Object it = element$iv$iv2;
                    boolean bl = false;
                    object = !Intrinsics.areEqual(it, (Object)"0.0") ? element$iv$iv2 : null;
                    if (object != null) break block12;
                }
                object = this.effect;
            }
            $this$flatMapTo$iv$iv = object;
        }
        catch (Exception e) {
            $this$flatMapTo$iv$iv = this.effect;
        }
        String $i$f$flatMap22 = $this$flatMapTo$iv$iv;
        if ($i$f$flatMap22 == null || ($this$flatMapTo$iv$iv = ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default($i$f$flatMap22, null, 1, null)) == null) {
            return this.skip();
        }
        Object effectIdentifier = $this$flatMapTo$iv$iv;
        Iterable $this$filter$iv = entities2;
        boolean $i$f$filter = false;
        Iterable e = $this$filter$iv;
        Collection destination$iv$iv3 = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv4 : $this$filterTo$iv$iv) {
            Entity it = (Entity)element$iv$iv4;
            boolean bl = false;
            if (!(it instanceof Poseable)) continue;
            destination$iv$iv3.add(element$iv$iv4);
        }
        $this$filter$iv = (List)destination$iv$iv3;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Entity entity2 = (Entity)element$iv;
            boolean bl = false;
            SpawnSnowstormEntityParticlePacket packet = new SpawnSnowstormEntityParticlePacket((ResourceLocation)effectIdentifier, entity2.m_19879_(), this.locator);
            Level level = entity2.m_9236_();
            Intrinsics.checkNotNull((Object)level, (String)"null cannot be cast to non-null type net.minecraft.server.world.ServerWorld");
            List players2 = ((ServerLevel)level).m_8795_(arg_0 -> EntityParticlesActionEffectKeyframe.playWhenTrue$lambda$5$lambda$4((Function1)new Function1<ServerPlayer, Boolean>(entity2, this){
                final /* synthetic */ Entity $entity;
                final /* synthetic */ EntityParticlesActionEffectKeyframe this$0;
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
            Intrinsics.checkNotNullExpressionValue((Object)players2, (String)"players");
            packet.sendToPlayers(players2);
        }
        return SchedulingFunctionsKt.delayedFuture$default(0, this.delay.resolveFloat(context.getRuntime()), true, 1, null);
    }

    @Override
    public boolean test(@NotNull ActionEffectContext context, @NotNull Entity entity2, boolean isUser) {
        return EntityConditionalActionEffectKeyframe.DefaultImpls.test(this, context, entity2, isUser);
    }

    private static final boolean playWhenTrue$lambda$5$lambda$4(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }
}

