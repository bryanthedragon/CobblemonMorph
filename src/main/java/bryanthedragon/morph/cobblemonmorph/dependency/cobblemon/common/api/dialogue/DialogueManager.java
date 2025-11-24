/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ActiveDialogue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.Dialogue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.DialogueClosedPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.DialogueOpenedPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u001d\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0015\u0010\t\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\t\u0010\nR#\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/api/dialogue/DialogueManager;", "", "Lnet/minecraft/server/level/ServerPlayer;", "playerEntity", "Lcom/cobblemon/mod/common/api/dialogue/Dialogue;", "dialogue", "", "startDialogue", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/api/dialogue/Dialogue;)V", "stopDialogue", "(Lnet/minecraft/server/level/ServerPlayer;)V", "", "Ljava/util/UUID;", "Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;", "activeDialogues", "Ljava/util/Map;", "getActiveDialogues", "()Ljava/util/Map;", "<init>", "()V", "common"})
public final class DialogueManager {
    @NotNull
    public static final DialogueManager INSTANCE = new DialogueManager();
    @NotNull
    private static final Map<UUID, ActiveDialogue> activeDialogues = new LinkedHashMap();

    private DialogueManager() {
    }

    @NotNull
    public final Map<UUID, ActiveDialogue> getActiveDialogues() {
        return activeDialogues;
    }

    public final void startDialogue(@NotNull ServerPlayer playerEntity, @NotNull Dialogue dialogue2) {
        Intrinsics.checkNotNullParameter((Object)playerEntity, (String)"playerEntity");
        Intrinsics.checkNotNullParameter((Object)dialogue2, (String)"dialogue");
        ActiveDialogue activeDialogue = new ActiveDialogue(playerEntity, dialogue2);
        Map<UUID, ActiveDialogue> map = activeDialogues;
        UUID uUID = playerEntity.m_20148_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"playerEntity.uuid");
        map.put(uUID, activeDialogue);
        DialogueOpenedPacket packet = new DialogueOpenedPacket(activeDialogue, true);
        CobblemonNetwork.INSTANCE.sendPacket(playerEntity, packet);
    }

    public final void stopDialogue(@NotNull ServerPlayer playerEntity) {
        Intrinsics.checkNotNullParameter((Object)playerEntity, (String)"playerEntity");
        ActiveDialogue activeDialogue = PlayerExtensionsKt.getActiveDialogue(playerEntity);
        if (activeDialogue == null) {
            return;
        }
        ActiveDialogue activeDialogue2 = activeDialogue;
        new DialogueClosedPacket(activeDialogue2.getDialogueId()).sendToPlayer(playerEntity);
        activeDialogues.remove(activeDialogue2.getDialogueId());
    }
}

