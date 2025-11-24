/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.factory;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerData;
import java.util.UUID;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\b\bf\u0018\u00002\u00020\u0001J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\b\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\u000b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0004H&\u00a2\u0006\u0004\b\u000b\u0010\fJ\u000f\u0010\r\u001a\u00020\u0007H&\u00a2\u0006\u0004\b\r\u0010\u000e\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/api/storage/player/factory/PlayerDataStoreFactory;", "", "Ljava/util/UUID;", "uuid", "Lcom/cobblemon/mod/common/api/storage/player/PlayerData;", "load", "(Ljava/util/UUID;)Lcom/cobblemon/mod/common/api/storage/player/PlayerData;", "", "onPlayerDisconnect", "(Ljava/util/UUID;)V", "data", "save", "(Lcom/cobblemon/mod/common/api/storage/player/PlayerData;)V", "saveAll", "()V", "common"})
public interface PlayerDataStoreFactory {
    @NotNull
    public PlayerData load(@NotNull UUID var1);

    public void save(@NotNull PlayerData var1);

    public void saveAll();

    public void onPlayerDisconnect(@NotNull UUID var1);
}

