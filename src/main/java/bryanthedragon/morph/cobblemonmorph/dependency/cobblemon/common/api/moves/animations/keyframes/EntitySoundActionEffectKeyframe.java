/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.entity.Entity
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
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\t\u0018\u00002\u00020\u00012\u00020\u0002B\u0007\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u001d\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\n\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\t8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000b\u001a\u0004\b\u000f\u0010\rR$\u0010\u0011\u001a\u0004\u0018\u00010\u00108\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/api/moves/animations/keyframes/EntitySoundActionEffectKeyframe;", "Lcom/cobblemon/mod/common/api/moves/animations/keyframes/ConditionalActionEffectKeyframe;", "Lcom/cobblemon/mod/common/api/moves/animations/keyframes/EntityConditionalActionEffectKeyframe;", "Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;", "context", "Ljava/util/concurrent/CompletableFuture;", "", "playWhenTrue", "(Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;)Ljava/util/concurrent/CompletableFuture;", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "delay", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "getDelay", "()Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "entityCondition", "getEntityCondition", "", "sound", "Ljava/lang/String;", "getSound", "()Ljava/lang/String;", "setSound", "(Ljava/lang/String;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nEntitySoundActionEffectKeyframe.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EntitySoundActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/EntitySoundActionEffectKeyframe\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,50:1\n800#2,11:51\n1360#2:62\n1446#2,2:63\n766#2:65\n857#2,2:66\n1448#2,3:68\n1855#2,2:72\n1#3:71\n*S KotlinDebug\n*F\n+ 1 EntitySoundActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/EntitySoundActionEffectKeyframe\n*L\n34#1:51,11\n35#1:62\n35#1:63,2\n35#1:65\n35#1:66,2\n35#1:68,3\n43#1:72,2\n*E\n"})
public final class EntitySoundActionEffectKeyframe
extends ConditionalActionEffectKeyframe
implements EntityConditionalActionEffectKeyframe {
    @NotNull
    private final ExpressionLike entityCondition = MoLangExtensionsKt.asExpressionLike("q.entity.is_user");
    @Nullable
    private String sound;
    @NotNull
    private final ExpressionLike delay = MoLangExtensionsKt.asExpressionLike("0");

    @Override
    @NotNull
    public ExpressionLike getEntityCondition() {
        return this.entityCondition;
    }

    @Nullable
    public final String getSound() {
        return this.sound;
    }

    public final void setSound(@Nullable String string) {
        this.sound = string;
    }

    @NotNull
    public final ExpressionLike getDelay() {
        return this.delay;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public CompletableFuture<Unit> playWhenTrue(@NotNull ActionEffectContext context) {
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
            void $this$filterTo$iv$iv;
            element$iv$iv2 = iterator.next();
            EntityProvider prov = (EntityProvider)element$iv$iv2;
            boolean bl = false;
            Iterable $this$filter$iv = prov.getEntities();
            boolean $i$f$filter = false;
            Iterable iterable2 = $this$filter$iv;
            Collection destination$iv$iv2 = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv3 : $this$filterTo$iv$iv) {
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
                    object = this.sound;
                    if (object == null || (object = MoLangExtensionsKt.asExpressionLike((String)object)) == null || (object = object.resolveString(context.getRuntime())) == null) break block11;
                    Object it = element$iv$iv2 = object;
                    boolean bl = false;
                    object = !Intrinsics.areEqual(it, (Object)"0.0") ? element$iv$iv2 : null;
                    if (object != null) break block12;
                }
                object = this.sound;
            }
            $this$flatMapTo$iv$iv = object;
        }
        catch (Exception e) {
            $this$flatMapTo$iv$iv = this.sound;
        }
        String $i$f$flatMap22 = $this$flatMapTo$iv$iv;
        if ($i$f$flatMap22 == null || ($this$flatMapTo$iv$iv = ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default($i$f$flatMap22, null, 1, null)) == null) {
            return this.skip();
        }
        Object soundIdentifier = $this$flatMapTo$iv$iv;
        Iterable $this$forEach$iv = entities2;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            SoundEvent soundEvent;
            Entity entity2 = (Entity)element$iv;
            boolean bl = false;
            if ((SoundEvent)entity2.m_9236_().m_9598_().m_175515_(Registries.f_256840_).m_7745_((ResourceLocation)soundIdentifier) == null) {
                return this.skip();
            }
            Intrinsics.checkNotNullExpressionValue((Object)soundEvent, (String)"entity.world.registryMan\u2026ntifier) ?: return skip()");
            entity2.m_5496_(soundEvent, 1.0f, 1.0f);
        }
        return SchedulingFunctionsKt.delayedFuture$default(0, this.delay.resolveFloat(context.getRuntime()), true, 1, null);
    }

    @Override
    public boolean test(@NotNull ActionEffectContext context, @NotNull Entity entity2, boolean isUser) {
        return EntityConditionalActionEffectKeyframe.DefaultImpls.test(this, context, entity2, isUser);
    }
}

