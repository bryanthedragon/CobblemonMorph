/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.markers.KMappedMarker
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ModDependant;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010)\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010!\n\u0002\b\u0006\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0007\u00a2\u0006\u0004\b%\u0010&J\r\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007H\u0096\u0002\u00a2\u0006\u0004\b\b\u0010\tR\"\u0010\n\u001a\u00020\u00048\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\u0006\"\u0004\b\r\u0010\u000eR(\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R(\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0017\u0010\u0012\u001a\u0004\b\u0018\u0010\u0014\"\u0004\b\u0019\u0010\u0016R\"\u0010\u001b\u001a\u00020\u001a8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R(\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00020!8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\"\u0010\u0012\u001a\u0004\b#\u0010\u0014\"\u0004\b$\u0010\u0016\u00a8\u0006'"}, d2={"Lcom/cobblemon/mod/common/api/spawning/SpawnSet;", "", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "Lcom/cobblemon/mod/common/api/ModDependant;", "", "isEnabled", "()Z", "", "iterator", "()Ljava/util/Iterator;", "enabled", "Z", "getEnabled", "setEnabled", "(Z)V", "", "", "neededInstalledMods", "Ljava/util/List;", "getNeededInstalledMods", "()Ljava/util/List;", "setNeededInstalledMods", "(Ljava/util/List;)V", "neededUninstalledMods", "getNeededUninstalledMods", "setNeededUninstalledMods", "Ljava/nio/file/Path;", "path", "Ljava/nio/file/Path;", "getPath", "()Ljava/nio/file/Path;", "setPath", "(Ljava/nio/file/Path;)V", "", "spawns", "getSpawns", "setSpawns", "<init>", "()V", "common"})
public final class SpawnSet
implements Iterable<SpawnDetail>,
ModDependant,
KMappedMarker {
    private boolean enabled = true;
    @NotNull
    private List<String> neededInstalledMods = CollectionsKt.emptyList();
    @NotNull
    private List<String> neededUninstalledMods = CollectionsKt.emptyList();
    @NotNull
    private List<SpawnDetail> spawns = new ArrayList();
    public transient Path path;

    public final boolean getEnabled() {
        return this.enabled;
    }

    public final void setEnabled(boolean bl) {
        this.enabled = bl;
    }

    @Override
    @NotNull
    public List<String> getNeededInstalledMods() {
        return this.neededInstalledMods;
    }

    @Override
    public void setNeededInstalledMods(@NotNull List<String> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.neededInstalledMods = list;
    }

    @Override
    @NotNull
    public List<String> getNeededUninstalledMods() {
        return this.neededUninstalledMods;
    }

    @Override
    public void setNeededUninstalledMods(@NotNull List<String> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.neededUninstalledMods = list;
    }

    @NotNull
    public final List<SpawnDetail> getSpawns() {
        return this.spawns;
    }

    public final void setSpawns(@NotNull List<SpawnDetail> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.spawns = list;
    }

    @NotNull
    public final Path getPath() {
        Path path = this.path;
        if (path != null) {
            return path;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"path");
        return null;
    }

    public final void setPath(@NotNull Path path) {
        Intrinsics.checkNotNullParameter((Object)path, (String)"<set-?>");
        this.path = path;
    }

    public final boolean isEnabled() {
        return this.enabled && this.isModDependencySatisfied();
    }

    @Override
    @NotNull
    public Iterator<SpawnDetail> iterator() {
        return this.spawns.iterator();
    }

    @Override
    public boolean isModDependencySatisfied() {
        return ModDependant.DefaultImpls.isModDependencySatisfied(this);
    }
}

