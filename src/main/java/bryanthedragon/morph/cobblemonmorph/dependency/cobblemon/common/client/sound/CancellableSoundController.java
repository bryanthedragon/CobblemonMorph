/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.sound;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.sound.CancellableSoundInstance;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\b\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0002\u00a2\u0006\u0004\b\b\u0010\u0006J\u001d\u0010\b\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0004\b\b\u0010\rR\u001c\u0010\u0010\u001a\n \u000f*\u0004\u0018\u00010\u000e0\u000e8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\u0011R,\u0010\u0014\u001a\u001a\u0012\u0004\u0012\u00020\t\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00130\u00120\u00128\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\u0015\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/client/sound/CancellableSoundController;", "", "Lcom/cobblemon/mod/common/client/sound/CancellableSoundInstance;", "newSound", "", "playSound", "(Lcom/cobblemon/mod/common/client/sound/CancellableSoundInstance;)V", "soundInstance", "stopSound", "Lnet/minecraft/core/BlockPos;", "blockPos", "Lnet/minecraft/resources/ResourceLocation;", "identifier", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/resources/ResourceLocation;)V", "Lnet/minecraft/client/sounds/SoundManager;", "kotlin.jvm.PlatformType", "manager", "Lnet/minecraft/client/sounds/SoundManager;", "", "Lnet/minecraft/client/resources/sounds/SoundInstance;", "playingSounds", "Ljava/util/Map;", "<init>", "()V", "common"})
public final class CancellableSoundController {
    @NotNull
    public static final CancellableSoundController INSTANCE = new CancellableSoundController();
    private static final SoundManager manager = Minecraft.m_91087_().m_91106_();
    @NotNull
    private static final Map<BlockPos, Map<ResourceLocation, SoundInstance>> playingSounds = MapsKt.toMutableMap((Map)MapsKt.emptyMap());

    private CancellableSoundController() {
    }

    public final void playSound(@NotNull CancellableSoundInstance newSound) {
        Intrinsics.checkNotNullParameter((Object)((Object)newSound), (String)"newSound");
        manager.m_120367_((SoundInstance)newSound);
        Map idMap = playingSounds.get(newSound.getPos());
        SoundInstance soundInstance = null;
        if (idMap == null) {
            idMap = MapsKt.toMutableMap((Map)MapsKt.emptyMap());
        } else {
            soundInstance = (SoundInstance)idMap.get(newSound.m_7904_());
        }
        if (soundInstance != null) {
            manager.m_120399_(soundInstance);
        }
        Map map = idMap;
        ResourceLocation resourceLocation = newSound.m_7904_();
        Intrinsics.checkNotNullExpressionValue((Object)resourceLocation, (String)"newSound.id");
        map.put(resourceLocation, newSound);
        playingSounds.put(newSound.getPos(), idMap);
    }

    public final void stopSound(@NotNull CancellableSoundInstance soundInstance) {
        Intrinsics.checkNotNullParameter((Object)((Object)soundInstance), (String)"soundInstance");
        BlockPos blockPos2 = soundInstance.getPos();
        ResourceLocation resourceLocation = soundInstance.m_5891_().m_119787_();
        Intrinsics.checkNotNullExpressionValue((Object)resourceLocation, (String)"soundInstance.sound.identifier");
        this.stopSound(blockPos2, resourceLocation);
    }

    public final void stopSound(@NotNull BlockPos blockPos2, @NotNull ResourceLocation identifier) {
        SoundInstance soundInstance;
        Intrinsics.checkNotNullParameter((Object)blockPos2, (String)"blockPos");
        Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
        Map<ResourceLocation, SoundInstance> idMap = playingSounds.get(blockPos2);
        if (idMap != null && (soundInstance = idMap.get(identifier)) != null) {
            manager.m_120399_(soundInstance);
            idMap.remove(identifier);
            if (idMap.keySet().size() == 0) {
                playingSounds.remove(blockPos2);
            }
        }
    }
}

