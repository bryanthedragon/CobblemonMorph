/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectTimeline;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffects;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.ConditionalActionEffectKeyframe;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u001d\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007R\u0019\u0010\t\u001a\u0004\u0018\u00010\b8\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\u000e\u001a\u00020\r8\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/api/moves/animations/keyframes/RunActionEffectKeyframe;", "Lcom/cobblemon/mod/common/api/moves/animations/keyframes/ConditionalActionEffectKeyframe;", "Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;", "context", "Ljava/util/concurrent/CompletableFuture;", "", "playWhenTrue", "(Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;)Ljava/util/concurrent/CompletableFuture;", "Lnet/minecraft/resources/ResourceLocation;", "actionEffect", "Lnet/minecraft/resources/ResourceLocation;", "getActionEffect", "()Lnet/minecraft/resources/ResourceLocation;", "", "waitForActionEffect", "Z", "getWaitForActionEffect", "()Z", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nRunActionEffectKeyframe.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RunActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/RunActionEffectKeyframe\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,34:1\n1#2:35\n*E\n"})
public final class RunActionEffectKeyframe
extends ConditionalActionEffectKeyframe {
    @Nullable
    private final ResourceLocation actionEffect;
    private final boolean waitForActionEffect;

    public RunActionEffectKeyframe() {
        this.waitForActionEffect = true;
    }

    @Nullable
    public final ResourceLocation getActionEffect() {
        return this.actionEffect;
    }

    public final boolean getWaitForActionEffect() {
        return this.waitForActionEffect;
    }

    @Override
    @NotNull
    public CompletableFuture<Unit> playWhenTrue(@NotNull ActionEffectContext context) {
        CompletableFuture<Object> completableFuture;
        block4: {
            block2: {
                block3: {
                    CompletableFuture<Unit> completableFuture2;
                    Intrinsics.checkNotNullParameter((Object)context, (String)"context");
                    if (this.actionEffect == null) break block2;
                    completableFuture = ActionEffects.INSTANCE.getActionEffects().get(this.actionEffect);
                    if (completableFuture == null || (completableFuture = ((ActionEffectTimeline)((Object)completableFuture)).run(context)) == null) break block3;
                    CompletableFuture<Unit> it = completableFuture2 = completableFuture;
                    boolean bl = false;
                    completableFuture = this.waitForActionEffect ? completableFuture2 : null;
                    if (completableFuture != null) break block4;
                }
                completableFuture = this.skip();
                break block4;
            }
            completableFuture = this.skip();
        }
        return completableFuture;
    }
}

