/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectTimeline;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.ActionEffectKeyframe;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B[\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u000e\b\u0002\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a\u0012\u000e\b\u0002\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00010\u0012\u0012\u0006\u0010&\u001a\u00020%\u0012\b\b\u0002\u0010\f\u001a\u00020\u000b\u0012\b\b\u0002\u0010 \u001a\u00020\u000b\u0012\u000e\b\u0002\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012\u00a2\u0006\u0004\b*\u0010+J\u001f\u0010\u0004\u001a\t\u0018\u00018\u0000\u00a2\u0006\u0002\b\u0003\"\u0006\b\u0000\u0010\u0002\u0018\u0001H\u0086\b\u00a2\u0006\u0004\b\u0004\u0010\u0005R\u0017\u0010\u0007\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\nR\"\u0010\f\u001a\u00020\u000b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R(\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00130\u00128\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001d\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a8\u0006\u00a2\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001fR\"\u0010 \u001a\u00020\u000b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b \u0010\r\u001a\u0004\b!\u0010\u000f\"\u0004\b\"\u0010\u0011R\u001d\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00010\u00128\u0006\u00a2\u0006\f\n\u0004\b#\u0010\u0015\u001a\u0004\b$\u0010\u0017R\u0017\u0010&\u001a\u00020%8\u0006\u00a2\u0006\f\n\u0004\b&\u0010'\u001a\u0004\b(\u0010)\u00a8\u0006,"}, d2={"Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;", "", "T", "Lkotlin/internal/NoInfer;", "findOneProvider", "()Ljava/lang/Object;", "Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectTimeline;", "actionEffect", "Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectTimeline;", "getActionEffect", "()Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectTimeline;", "", "canBeInterrupted", "Z", "getCanBeInterrupted", "()Z", "setCanBeInterrupted", "(Z)V", "", "Lcom/cobblemon/mod/common/api/moves/animations/keyframes/ActionEffectKeyframe;", "currentKeyframes", "Ljava/util/List;", "getCurrentKeyframes", "()Ljava/util/List;", "setCurrentKeyframes", "(Ljava/util/List;)V", "", "", "holds", "Ljava/util/Set;", "getHolds", "()Ljava/util/Set;", "interrupted", "getInterrupted", "setInterrupted", "providers", "getProviders", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "getRuntime", "()Lcom/bedrockk/molang/runtime/MoLangRuntime;", "<init>", "(Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectTimeline;Ljava/util/Set;Ljava/util/List;Lcom/bedrockk/molang/runtime/MoLangRuntime;ZZLjava/util/List;)V", "common"})
@SourceDebugExtension(value={"SMAP\nActionEffectTimeline.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ActionEffectTimeline.kt\ncom/cobblemon/mod/common/api/moves/animations/ActionEffectContext\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,97:1\n800#2,11:98\n*S KotlinDebug\n*F\n+ 1 ActionEffectTimeline.kt\ncom/cobblemon/mod/common/api/moves/animations/ActionEffectContext\n*L\n73#1:98,11\n*E\n"})
public final class ActionEffectContext {
    @NotNull
    private final ActionEffectTimeline actionEffect;
    @NotNull
    private final Set<String> holds;
    @NotNull
    private final List<Object> providers;
    @NotNull
    private final MoLangRuntime runtime;
    private boolean canBeInterrupted;
    private boolean interrupted;
    @NotNull
    private List<ActionEffectKeyframe> currentKeyframes;

    public ActionEffectContext(@NotNull ActionEffectTimeline actionEffect, @NotNull Set<String> holds, @NotNull List<Object> providers, @NotNull MoLangRuntime runtime2, boolean canBeInterrupted, boolean interrupted, @NotNull List<ActionEffectKeyframe> currentKeyframes) {
        Intrinsics.checkNotNullParameter((Object)actionEffect, (String)"actionEffect");
        Intrinsics.checkNotNullParameter(holds, (String)"holds");
        Intrinsics.checkNotNullParameter(providers, (String)"providers");
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        Intrinsics.checkNotNullParameter(currentKeyframes, (String)"currentKeyframes");
        this.actionEffect = actionEffect;
        this.holds = holds;
        this.providers = providers;
        this.runtime = runtime2;
        this.canBeInterrupted = canBeInterrupted;
        this.interrupted = interrupted;
        this.currentKeyframes = currentKeyframes;
    }

    public /* synthetic */ ActionEffectContext(ActionEffectTimeline actionEffectTimeline, Set set2, List list, MoLangRuntime moLangRuntime, boolean bl, boolean bl2, List list2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            set2 = new LinkedHashSet();
        }
        if ((n & 4) != 0) {
            list = new ArrayList();
        }
        if ((n & 0x10) != 0) {
            bl = false;
        }
        if ((n & 0x20) != 0) {
            bl2 = false;
        }
        if ((n & 0x40) != 0) {
            list2 = new ArrayList();
        }
        this(actionEffectTimeline, set2, list, moLangRuntime, bl, bl2, list2);
    }

    @NotNull
    public final ActionEffectTimeline getActionEffect() {
        return this.actionEffect;
    }

    @NotNull
    public final Set<String> getHolds() {
        return this.holds;
    }

    @NotNull
    public final List<Object> getProviders() {
        return this.providers;
    }

    @NotNull
    public final MoLangRuntime getRuntime() {
        return this.runtime;
    }

    public final boolean getCanBeInterrupted() {
        return this.canBeInterrupted;
    }

    public final void setCanBeInterrupted(boolean bl) {
        this.canBeInterrupted = bl;
    }

    public final boolean getInterrupted() {
        return this.interrupted;
    }

    public final void setInterrupted(boolean bl) {
        this.interrupted = bl;
    }

    @NotNull
    public final List<ActionEffectKeyframe> getCurrentKeyframes() {
        return this.currentKeyframes;
    }

    public final void setCurrentKeyframes(@NotNull List<ActionEffectKeyframe> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.currentKeyframes = list;
    }

    /*
     * WARNING - void declaration
     */
    public final /* synthetic */ <T> T findOneProvider() {
        void $this$filterIsInstanceTo$iv$iv;
        boolean $i$f$findOneProvider = false;
        Iterable $this$filterIsInstance$iv = this.getProviders();
        boolean $i$f$filterIsInstance = false;
        Iterable iterable = $this$filterIsInstance$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            Intrinsics.reifiedOperationMarker((int)3, (String)"T");
            if (!(element$iv$iv instanceof Object)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (T)CollectionsKt.firstOrNull((List)((List)destination$iv$iv));
    }
}

