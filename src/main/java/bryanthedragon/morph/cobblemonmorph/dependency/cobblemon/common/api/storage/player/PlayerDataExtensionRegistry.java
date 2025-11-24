/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerDataExtension;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010%\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\r\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\b\u0010\tJ\u001f\u0010\f\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u000b\u0018\u00010\n2\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u001d\u0010\u000e\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000b0\n2\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u000e\u0010\rJ/\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00022\u000e\u0010\u000f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000b0\n2\b\b\u0002\u0010\u0010\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u001f\u0010\u0013\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u000b\u0018\u00010\n2\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0013\u0010\rR(\u0010\u0015\u001a\u0016\u0012\u0004\u0012\u00020\u0002\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u000b0\n0\u00148\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0015\u0010\u0016\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/api/storage/player/PlayerDataExtensionRegistry;", "", "", "name", "", "contains", "(Ljava/lang/String;)Z", "", "count", "()I", "Ljava/lang/Class;", "Lcom/cobblemon/mod/common/api/storage/player/PlayerDataExtension;", "get", "(Ljava/lang/String;)Ljava/lang/Class;", "getOrException", "extension", "overwrite", "register", "(Ljava/lang/String;Ljava/lang/Class;Z)Z", "remove", "", "allExtensions", "Ljava/util/Map;", "<init>", "()V", "common"})
public final class PlayerDataExtensionRegistry {
    @NotNull
    public static final PlayerDataExtensionRegistry INSTANCE = new PlayerDataExtensionRegistry();
    @NotNull
    private static final Map<String, Class<? extends PlayerDataExtension>> allExtensions = new LinkedHashMap();

    private PlayerDataExtensionRegistry() {
    }

    public final boolean register(@NotNull String name, @NotNull Class<? extends PlayerDataExtension> extension, boolean overwrite) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter(extension, (String)"extension");
        if (allExtensions.containsKey(name) && !overwrite) {
            return false;
        }
        allExtensions.put(name, extension);
        return true;
    }

    public static /* synthetic */ boolean register$default(PlayerDataExtensionRegistry playerDataExtensionRegistry, String string, Class clazz, boolean bl, int n, Object object) {
        if ((n & 4) != 0) {
            bl = false;
        }
        return playerDataExtensionRegistry.register(string, clazz, bl);
    }

    @Nullable
    public final Class<? extends PlayerDataExtension> get(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        return allExtensions.get(name);
    }

    @NotNull
    public final Class<? extends PlayerDataExtension> getOrException(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Class<? extends PlayerDataExtension> clazz = this.get(name);
        if (clazz == null) {
            throw new IllegalStateException("PlayerDataExtension with name " + name + " was not found.");
        }
        return clazz;
    }

    public final int count() {
        return allExtensions.size();
    }

    @Nullable
    public final Class<? extends PlayerDataExtension> remove(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        return allExtensions.remove(name);
    }

    public final boolean contains(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        return allExtensions.containsKey(name);
    }
}

