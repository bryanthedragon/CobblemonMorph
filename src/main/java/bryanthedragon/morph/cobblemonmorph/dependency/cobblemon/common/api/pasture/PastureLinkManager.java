/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Position
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture.PastureLink;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.ClosePasturePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MapExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u001d\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\u000b\u001a\u0004\u0018\u00010\u00042\u0006\u0010\n\u001a\u00020\t\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\r\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\r\u0010\u000eJ\u001d\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0004\b\u0013\u0010\u0014R#\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00040\u00158\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/api/pasture/PastureLinkManager;", "", "Ljava/util/UUID;", "playerId", "Lcom/cobblemon/mod/common/api/pasture/PastureLink;", "link", "", "createLink", "(Ljava/util/UUID;Lcom/cobblemon/mod/common/api/pasture/PastureLink;)V", "Lnet/minecraft/server/level/ServerPlayer;", "player", "getLinkByPlayer", "(Lnet/minecraft/server/level/ServerPlayer;)Lcom/cobblemon/mod/common/api/pasture/PastureLink;", "getLinkByPlayerId", "(Ljava/util/UUID;)Lcom/cobblemon/mod/common/api/pasture/PastureLink;", "Lnet/minecraft/server/level/ServerLevel;", "world", "Lnet/minecraft/core/BlockPos;", "pos", "removeAt", "(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;)V", "", "links", "Ljava/util/Map;", "getLinks", "()Ljava/util/Map;", "<init>", "()V", "common"})
public final class PastureLinkManager {
    @NotNull
    public static final PastureLinkManager INSTANCE = new PastureLinkManager();
    @NotNull
    private static final Map<UUID, PastureLink> links = new LinkedHashMap();

    private PastureLinkManager() {
    }

    @NotNull
    public final Map<UUID, PastureLink> getLinks() {
        return links;
    }

    @Nullable
    public final PastureLink getLinkByPlayerId(@NotNull UUID playerId) {
        Intrinsics.checkNotNullParameter((Object)playerId, (String)"playerId");
        return links.get(playerId);
    }

    public final void createLink(@NotNull UUID playerId, @NotNull PastureLink link) {
        Intrinsics.checkNotNullParameter((Object)playerId, (String)"playerId");
        Intrinsics.checkNotNullParameter((Object)link, (String)"link");
        links.put(playerId, link);
    }

    @Nullable
    public final PastureLink getLinkByPlayer(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        UUID uUID = player.m_20148_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
        PastureLink link = this.getLinkByPlayerId(uUID);
        if (!(link == null || Intrinsics.areEqual((Object)link.getDimension(), (Object)player.m_9236_().m_220362_().m_135782_()) && link.getPos().m_203195_((Position)player.m_20182_(), 10.0))) {
            links.remove(player.m_20148_());
            return null;
        }
        return link;
    }

    public final void removeAt(@NotNull ServerLevel world, @NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        MapExtensionsKt.removeIf(links, (Function1)new Function1<Map.Entry<? extends UUID, ? extends PastureLink>, Boolean>(world, pos){
            final /* synthetic */ ServerLevel $world;
            final /* synthetic */ BlockPos $pos;
            {
                this.$world = $world;
                this.$pos = $pos;
                super(1);
            }

            @NotNull
            public final Boolean invoke(@NotNull Map.Entry<UUID, PastureLink> entry) {
                boolean shouldRemove;
                block0: {
                    Intrinsics.checkNotNullParameter(entry, (String)"<name for destructuring parameter 0>");
                    UUID uuid2 = entry.getKey();
                    PastureLink pastureLink = entry.getValue();
                    shouldRemove = Intrinsics.areEqual((Object)pastureLink.getDimension(), (Object)this.$world.m_220362_().m_135782_()) && Intrinsics.areEqual((Object)pastureLink.getPos(), (Object)this.$pos);
                    ServerPlayer serverPlayer = PlayerExtensionsKt.getPlayer(uuid2);
                    if (serverPlayer == null) break block0;
                    CobblemonNetwork.INSTANCE.sendPacket(serverPlayer, new ClosePasturePacket());
                }
                return shouldRemove;
            }
        });
    }
}

