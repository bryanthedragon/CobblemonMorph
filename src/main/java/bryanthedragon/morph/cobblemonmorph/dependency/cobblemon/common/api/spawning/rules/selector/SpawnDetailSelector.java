/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\bf\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0005\u0010\u0006\u00a8\u0006\b"}, d2={"Lcom/cobblemon/mod/common/api/spawning/rules/selector/SpawnDetailSelector;", "", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "spawnDetail", "", "selects", "(Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;)Z", "Companion", "common"})
public interface SpawnDetailSelector {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.SpawnDetailSelector$Companion.$$INSTANCE;

    public boolean selects(@NotNull SpawnDetail var1);

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010J$\u0010\u0007\u001a\u00020\u0006\"\n\b\u0000\u0010\u0003\u0018\u0001*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0086\b\u00a2\u0006\u0004\b\u0007\u0010\bR+\u0010\u000b\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00020\n0\t8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/api/spawning/rules/selector/SpawnDetailSelector$Companion;", "", "Lcom/cobblemon/mod/common/api/spawning/rules/selector/SpawnDetailSelector;", "T", "", "type", "", "register", "(Ljava/lang/String;)V", "", "Ljava/lang/Class;", "types", "Ljava/util/Map;", "getTypes", "()Ljava/util/Map;", "<init>", "()V", "common"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;
        @NotNull
        private static final Map<String, Class<? extends SpawnDetailSelector>> types;

        private Companion() {
        }

        @NotNull
        public final Map<String, Class<? extends SpawnDetailSelector>> getTypes() {
            return types;
        }

        public final /* synthetic */ <T extends SpawnDetailSelector> void register(String type) {
            Intrinsics.checkNotNullParameter((Object)type, (String)"type");
            boolean $i$f$register = false;
            Map<String, Class<? extends SpawnDetailSelector>> map = this.getTypes();
            Intrinsics.reifiedOperationMarker((int)4, (String)"T");
            map.put(type, SpawnDetailSelector.class);
        }

        static {
            $$INSTANCE = new Companion();
            types = new LinkedHashMap();
        }
    }
}

