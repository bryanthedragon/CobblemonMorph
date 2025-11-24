/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.sound.battle.BattleMusicController;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.sound.battle.BattleMusicInstance;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMusicPacket;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/net/battle/BattleMusicHandler;", "Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/client/battle/BattleMusicPacket;", "packet", "Lnet/minecraft/client/Minecraft;", "client", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/client/battle/BattleMusicPacket;Lnet/minecraft/client/Minecraft;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nBattleMusicHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleMusicHandler.kt\ncom/cobblemon/mod/common/client/net/battle/BattleMusicHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,42:1\n1#2:43\n*E\n"})
public final class BattleMusicHandler
implements ClientNetworkPacketHandler<BattleMusicPacket> {
    @NotNull
    public static final BattleMusicHandler INSTANCE = new BattleMusicHandler();

    private BattleMusicHandler() {
    }

    @Override
    public void handle(@NotNull BattleMusicPacket packet, @NotNull Minecraft client) {
        BattleMusicInstance battleMusicInstance;
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        Intrinsics.checkNotNullParameter((Object)client, (String)"client");
        SoundManager soundManager = client.m_91106_();
        SoundEvent soundEvent = packet.getMusic();
        if (soundEvent != null) {
            SoundEvent it = soundEvent;
            boolean bl = false;
            battleMusicInstance = new BattleMusicInstance(it, packet.getVolume(), packet.getPitch());
        } else {
            battleMusicInstance = null;
        }
        BattleMusicInstance newMusic = battleMusicInstance;
        BattleMusicInstance currMusic = BattleMusicController.INSTANCE.getMusic();
        if (newMusic == null) {
            BattleMusicController.INSTANCE.endMusic();
        } else if (!soundManager.m_120403_((SoundInstance)currMusic)) {
            BattleMusicController.INSTANCE.initializeMusic(newMusic);
        } else if (!Intrinsics.areEqual((Object)newMusic.m_7904_(), (Object)currMusic.m_7904_())) {
            BattleMusicController.INSTANCE.switchMusic(newMusic);
        } else {
            SoundEvent soundEvent2 = packet.getMusic();
            Cobblemon.INSTANCE.getLOGGER().error("Ignored BattleMusicPacket from server: " + (ResourceLocation)(soundEvent2 != null ? soundEvent2.m_11660_() : null));
        }
    }

    @Override
    public void handleOnNettyThread(@NotNull BattleMusicPacket packet) {
        ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet);
    }
}

