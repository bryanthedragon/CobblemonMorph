/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.component;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \u00022\u00020\u0001:\u0001\u0002\u00a8\u0006\u0003"}, d2={"Lcom/cobblemon/mod/common/api/spawning/rules/component/SpawnRuleComponent;", "Lcom/cobblemon/mod/common/api/spawning/influence/SpawningInfluence;", "Companion", "common"})
public interface SpawnRuleComponent
extends SpawningInfluence {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.component.SpawnRuleComponent$Companion.$$INSTANCE;

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010J$\u0010\u0007\u001a\u00020\u0006\"\n\b\u0000\u0010\u0003\u0018\u0001*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0086\b\u00a2\u0006\u0004\b\u0007\u0010\bR+\u0010\u000b\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00020\n0\t8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/api/spawning/rules/component/SpawnRuleComponent$Companion;", "", "Lcom/cobblemon/mod/common/api/spawning/rules/component/SpawnRuleComponent;", "T", "", "type", "", "register", "(Ljava/lang/String;)V", "", "Ljava/lang/Class;", "types", "Ljava/util/Map;", "getTypes", "()Ljava/util/Map;", "<init>", "()V", "common"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;
        @NotNull
        private static final Map<String, Class<? extends SpawnRuleComponent>> types;

        private Companion() {
        }

        @NotNull
        public final Map<String, Class<? extends SpawnRuleComponent>> getTypes() {
            return types;
        }

        public final /* synthetic */ <T extends SpawnRuleComponent> void register(String type) {
            Intrinsics.checkNotNullParameter((Object)type, (String)"type");
            boolean $i$f$register = false;
            Map<String, Class<? extends SpawnRuleComponent>> map = this.getTypes();
            Intrinsics.reifiedOperationMarker((int)4, (String)"T");
            map.put(type, SpawnRuleComponent.class);
        }

        static {
            $$INSTANCE = new Companion();
            types = new LinkedHashMap();
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static boolean isExpired(@NotNull SpawnRuleComponent $this) {
            return SpawningInfluence.DefaultImpls.isExpired($this);
        }

        public static boolean affectSpawnable(@NotNull SpawnRuleComponent $this, @NotNull SpawnDetail detail, @NotNull SpawningContext ctx) {
            Intrinsics.checkNotNullParameter((Object)detail, (String)"detail");
            Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
            return SpawningInfluence.DefaultImpls.affectSpawnable($this, detail, ctx);
        }

        public static float affectWeight(@NotNull SpawnRuleComponent $this, @NotNull SpawnDetail detail, @NotNull SpawningContext ctx, float weight) {
            Intrinsics.checkNotNullParameter((Object)detail, (String)"detail");
            Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
            return SpawningInfluence.DefaultImpls.affectWeight($this, detail, ctx, weight);
        }

        public static void affectAction(@NotNull SpawnRuleComponent $this, @NotNull SpawnAction<?> action2) {
            Intrinsics.checkNotNullParameter(action2, (String)"action");
            SpawningInfluence.DefaultImpls.affectAction($this, action2);
        }

        public static void affectSpawn(@NotNull SpawnRuleComponent $this, @NotNull Entity entity2) {
            Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
            SpawningInfluence.DefaultImpls.affectSpawn($this, entity2);
        }

        public static float affectBucketWeight(@NotNull SpawnRuleComponent $this, @NotNull SpawnBucket bucket, float weight) {
            Intrinsics.checkNotNullParameter((Object)bucket, (String)"bucket");
            return SpawningInfluence.DefaultImpls.affectBucketWeight($this, bucket, weight);
        }

        public static boolean isAllowedPosition(@NotNull SpawnRuleComponent $this, @NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull SpawningContextCalculator<?, ?> contextCalculator) {
            Intrinsics.checkNotNullParameter((Object)world, (String)"world");
            Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
            Intrinsics.checkNotNullParameter(contextCalculator, (String)"contextCalculator");
            return SpawningInfluence.DefaultImpls.isAllowedPosition($this, world, pos, contextCalculator);
        }
    }
}

