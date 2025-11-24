/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.prospecting;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.WorldSlice;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.Spawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.SpawningArea;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H&\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/api/spawning/prospecting/SpawningProspector;", "", "Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;", "spawner", "Lcom/cobblemon/mod/common/api/spawning/spawner/SpawningArea;", "area", "Lcom/cobblemon/mod/common/api/spawning/WorldSlice;", "prospect", "(Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;Lcom/cobblemon/mod/common/api/spawning/spawner/SpawningArea;)Lcom/cobblemon/mod/common/api/spawning/WorldSlice;", "common"})
public interface SpawningProspector {
    @NotNull
    public WorldSlice prospect(@NotNull Spawner var1, @NotNull SpawningArea var2);
}

