/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.UsersProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.ConditionalActionEffectKeyframe;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\b\u0010\tJ\u001d\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/moves/animations/keyframes/SavePositionActionEffectKeyframe;", "Lcom/cobblemon/mod/common/api/moves/animations/keyframes/ConditionalActionEffectKeyframe;", "Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;", "context", "Ljava/util/concurrent/CompletableFuture;", "", "playWhenTrue", "(Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;)Ljava/util/concurrent/CompletableFuture;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nSavePositionActionEffectKeyframe.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SavePositionActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/SavePositionActionEffectKeyframe\n+ 2 ActionEffectTimeline.kt\ncom/cobblemon/mod/common/api/moves/animations/ActionEffectContext\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,22:1\n73#2:23\n800#3,11:24\n*S KotlinDebug\n*F\n+ 1 SavePositionActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/SavePositionActionEffectKeyframe\n*L\n18#1:23\n18#1:24,11\n*E\n"})
public final class SavePositionActionEffectKeyframe
extends ConditionalActionEffectKeyframe {
    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public CompletableFuture<Unit> playWhenTrue(@NotNull ActionEffectContext context) {
        void $this$filterIsInstanceTo$iv$iv$iv;
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        ActionEffectContext this_$iv = context;
        boolean $i$f$findOneProvider = false;
        Iterable $this$filterIsInstance$iv$iv = this_$iv.getProviders();
        boolean $i$f$filterIsInstance = false;
        Iterable iterable = $this$filterIsInstance$iv$iv;
        Collection destination$iv$iv$iv = new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        for (Object element$iv$iv$iv : $this$filterIsInstanceTo$iv$iv$iv) {
            if (!(element$iv$iv$iv instanceof UsersProvider)) continue;
            destination$iv$iv$iv.add(element$iv$iv$iv);
        }
        Object object = (UsersProvider)CollectionsKt.firstOrNull((List)((List)destination$iv$iv$iv));
        if (object == null || (object = ((UsersProvider)object).getEntities()) == null || (object = (Entity)CollectionsKt.firstOrNull((List)object)) == null) {
            return this.skip();
        }
        Object user = object;
        context.getRuntime().getEnvironment().setSimpleVariable(user.m_20149_() + "-pos", new ObjectValue(user.m_20182_(), null, null, 6, null));
        return this.skip();
    }
}

