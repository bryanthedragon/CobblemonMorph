/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLink;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLinkManager;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010%\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J3\u0010\u0005\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\t2\u0014\b\u0002\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b\u00a2\u0006\u0004\b\u0005\u0010\u000fJ\u0017\u0010\u0010\u001a\u0004\u0018\u00010\u00022\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0017\u0010\u0013\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0012\u001a\u00020\f\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0015\u0010\u0015\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\u0015\u0010\u0016R \u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00020\u00178\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0018\u0010\u0019\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/api/storage/pc/link/PCLinkManager;", "", "Lcom/cobblemon/mod/common/api/storage/pc/link/PCLink;", "pcLink", "", "addLink", "(Lcom/cobblemon/mod/common/api/storage/pc/link/PCLink;)V", "Ljava/util/UUID;", "playerID", "Lcom/cobblemon/mod/common/api/storage/pc/PCStore;", "pcStore", "Lkotlin/Function1;", "Lnet/minecraft/server/level/ServerPlayer;", "", "condition", "(Ljava/util/UUID;Lcom/cobblemon/mod/common/api/storage/pc/PCStore;Lkotlin/jvm/functions/Function1;)V", "getLink", "(Ljava/util/UUID;)Lcom/cobblemon/mod/common/api/storage/pc/link/PCLink;", "player", "getPC", "(Lnet/minecraft/server/level/ServerPlayer;)Lcom/cobblemon/mod/common/api/storage/pc/PCStore;", "removeLink", "(Ljava/util/UUID;)V", "", "links", "Ljava/util/Map;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nPCLinkManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PCLinkManager.kt\ncom/cobblemon/mod/common/api/storage/pc/link/PCLinkManager\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,42:1\n1#2:43\n*E\n"})
public final class PCLinkManager {
    @NotNull
    public static final PCLinkManager INSTANCE = new PCLinkManager();
    @NotNull
    private static final Map<UUID, PCLink> links = new LinkedHashMap();

    private PCLinkManager() {
    }

    @Nullable
    public final PCLink getLink(@NotNull UUID playerID) {
        Intrinsics.checkNotNullParameter((Object)playerID, (String)"playerID");
        return links.get(playerID);
    }

    public final void addLink(@NotNull PCLink pcLink) {
        Intrinsics.checkNotNullParameter((Object)pcLink, (String)"pcLink");
        links.put(pcLink.getPlayerID(), pcLink);
    }

    public final void addLink(@NotNull UUID playerID, @NotNull PCStore pcStore, @NotNull Function1<? super ServerPlayer, Boolean> condition2) {
        Intrinsics.checkNotNullParameter((Object)playerID, (String)"playerID");
        Intrinsics.checkNotNullParameter((Object)pcStore, (String)"pcStore");
        Intrinsics.checkNotNullParameter(condition2, (String)"condition");
        links.put(playerID, new PCLink(pcStore, playerID, condition2){
            final /* synthetic */ Function1<ServerPlayer, Boolean> $condition;
            {
                this.$condition = $condition;
                super($pcStore, $playerID);
            }

            public boolean isPermitted(@NotNull ServerPlayer player) {
                Intrinsics.checkNotNullParameter((Object)player, (String)"player");
                return (Boolean)this.$condition.invoke((Object)player);
            }
        });
    }

    public static /* synthetic */ void addLink$default(PCLinkManager pCLinkManager, UUID uUID, PCStore pCStore, Function1 function1, int n, Object object) {
        if ((n & 4) != 0) {
            function1 = addLink.1.INSTANCE;
        }
        pCLinkManager.addLink(uUID, pCStore, (Function1<? super ServerPlayer, Boolean>)function1);
    }

    public final void removeLink(@NotNull UUID playerID) {
        Intrinsics.checkNotNullParameter((Object)playerID, (String)"playerID");
        links.remove(playerID);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Nullable
    public final PCStore getPC(@NotNull ServerPlayer player) {
        PCLink pCLink;
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        UUID uUID = player.m_20148_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
        PCLink pCLink2 = this.getLink(uUID);
        PCLink pCLink3 = pCLink2;
        if (pCLink2 == null) return null;
        PCLink it = pCLink = pCLink3;
        boolean bl = false;
        if (!it.isPermitted(player)) return null;
        PCLink pCLink4 = pCLink;
        pCLink3 = pCLink4;
        if (pCLink4 == null) return null;
        PCStore pCStore = pCLink3.getPc();
        return pCStore;
    }
}

