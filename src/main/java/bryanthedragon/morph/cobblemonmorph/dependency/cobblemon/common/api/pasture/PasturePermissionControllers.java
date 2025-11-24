/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.PrioritizedList;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture.PasturePermissionController;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture.PasturePermissions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PokemonPastureBlockEntity;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u001d\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0007\u0010\bR\u001d\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\t8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/api/pasture/PasturePermissionControllers;", "", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lcom/cobblemon/mod/common/block/entity/PokemonPastureBlockEntity;", "pastureBlockEntity", "Lcom/cobblemon/mod/common/api/pasture/PasturePermissions;", "permit", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/block/entity/PokemonPastureBlockEntity;)Lcom/cobblemon/mod/common/api/pasture/PasturePermissions;", "Lcom/cobblemon/mod/common/api/PrioritizedList;", "Lcom/cobblemon/mod/common/api/pasture/PasturePermissionController;", "controllers", "Lcom/cobblemon/mod/common/api/PrioritizedList;", "getControllers", "()Lcom/cobblemon/mod/common/api/PrioritizedList;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nPasturePermissionControllers.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PasturePermissionControllers.kt\ncom/cobblemon/mod/common/api/pasture/PasturePermissionControllers\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,39:1\n1#2:40\n*E\n"})
public final class PasturePermissionControllers {
    @NotNull
    public static final PasturePermissionControllers INSTANCE = new PasturePermissionControllers();
    @NotNull
    private static final PrioritizedList<PasturePermissionController> controllers = new PrioritizedList();

    private PasturePermissionControllers() {
    }

    @NotNull
    public final PrioritizedList<PasturePermissionController> getControllers() {
        return controllers;
    }

    @NotNull
    public final PasturePermissions permit(@NotNull ServerPlayer player, @NotNull PokemonPastureBlockEntity pastureBlockEntity) {
        PasturePermissions pasturePermissions;
        PasturePermissions pasturePermissions2;
        block2: {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter((Object)((Object)pastureBlockEntity), (String)"pastureBlockEntity");
            for (PasturePermissionController it : (Iterable)controllers) {
                boolean bl = false;
                PasturePermissions pasturePermissions3 = it.permit(player, pastureBlockEntity);
                if (pasturePermissions3 == null) continue;
                pasturePermissions2 = pasturePermissions3;
                break block2;
            }
            pasturePermissions2 = pasturePermissions = null;
        }
        if (pasturePermissions2 == null) {
            pasturePermissions = new PasturePermissions(true, true, pastureBlockEntity.getMaxTethered());
        }
        return pasturePermissions;
    }
}

