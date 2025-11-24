/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u0000 \f2\u00020\u0001:\u0001\fJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u001d\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u00072\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\b\u0010\tJ\u0015\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00040\u0007H\u0016\u00a2\u0006\u0004\b\n\u0010\u000b\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/api/moves/animations/keyframes/ActionEffectKeyframe;", "", "Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;", "context", "", "interrupt", "(Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;)V", "Ljava/util/concurrent/CompletableFuture;", "play", "(Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;)Ljava/util/concurrent/CompletableFuture;", "skip", "()Ljava/util/concurrent/CompletableFuture;", "Companion", "common"})
public interface ActionEffectKeyframe {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.ActionEffectKeyframe$Companion.$$INSTANCE;

    @NotNull
    public CompletableFuture<Unit> play(@NotNull ActionEffectContext var1);

    public void interrupt(@NotNull ActionEffectContext var1);

    @NotNull
    public CompletableFuture<Unit> skip();

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010J$\u0010\u0007\u001a\u00020\u0006\"\n\b\u0000\u0010\u0003\u0018\u0001*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0086\b\u00a2\u0006\u0004\b\u0007\u0010\bR+\u0010\u000b\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00020\n0\t8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/api/moves/animations/keyframes/ActionEffectKeyframe$Companion;", "", "Lcom/cobblemon/mod/common/api/moves/animations/keyframes/ActionEffectKeyframe;", "T", "", "type", "", "register", "(Ljava/lang/String;)V", "", "Ljava/lang/Class;", "types", "Ljava/util/Map;", "getTypes", "()Ljava/util/Map;", "<init>", "()V", "common"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;
        @NotNull
        private static final Map<String, Class<? extends ActionEffectKeyframe>> types;

        private Companion() {
        }

        @NotNull
        public final Map<String, Class<? extends ActionEffectKeyframe>> getTypes() {
            return types;
        }

        public final /* synthetic */ <T extends ActionEffectKeyframe> void register(String type) {
            Intrinsics.checkNotNullParameter((Object)type, (String)"type");
            boolean $i$f$register = false;
            Map<String, Class<? extends ActionEffectKeyframe>> map = this.getTypes();
            Intrinsics.reifiedOperationMarker((int)4, (String)"T");
            map.put(type, ActionEffectKeyframe.class);
        }

        static {
            $$INSTANCE = new Companion();
            types = new LinkedHashMap();
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static void interrupt(@NotNull ActionEffectKeyframe $this, @NotNull ActionEffectContext context) {
            Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        }

        @NotNull
        public static CompletableFuture<Unit> skip(@NotNull ActionEffectKeyframe $this) {
            CompletableFuture<Unit> completableFuture = CompletableFuture.completedFuture(Unit.INSTANCE);
            Intrinsics.checkNotNullExpressionValue(completableFuture, (String)"completedFuture(Unit)");
            return completableFuture;
        }
    }
}

