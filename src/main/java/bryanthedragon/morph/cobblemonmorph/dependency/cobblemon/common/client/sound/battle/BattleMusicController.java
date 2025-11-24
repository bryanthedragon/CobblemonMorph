/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.sound.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.sound.battle.BattleMusicInstance;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.SoundExtensionsKt;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0019\u0010\u0004J\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0015\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0015\u0010\t\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0004\b\t\u0010\bR\u001d\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0012\u001a\n \u0011*\u0004\u0018\u00010\u00100\u00108\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\u0013R$\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u00058\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/client/sound/battle/BattleMusicController;", "", "", "endMusic", "()V", "Lcom/cobblemon/mod/common/client/sound/battle/BattleMusicInstance;", "newMusic", "initializeMusic", "(Lcom/cobblemon/mod/common/client/sound/battle/BattleMusicInstance;)V", "switchMusic", "", "Lnet/minecraft/sounds/SoundSource;", "filteredCategories", "Ljava/util/List;", "getFilteredCategories", "()Ljava/util/List;", "Lnet/minecraft/client/sounds/SoundManager;", "kotlin.jvm.PlatformType", "manager", "Lnet/minecraft/client/sounds/SoundManager;", "<set-?>", "music", "Lcom/cobblemon/mod/common/client/sound/battle/BattleMusicInstance;", "getMusic", "()Lcom/cobblemon/mod/common/client/sound/battle/BattleMusicInstance;", "<init>", "common"})
@SourceDebugExtension(value={"SMAP\nBattleMusicController.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleMusicController.kt\ncom/cobblemon/mod/common/client/sound/battle/BattleMusicController\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,61:1\n1855#2,2:62\n*S KotlinDebug\n*F\n+ 1 BattleMusicController.kt\ncom/cobblemon/mod/common/client/sound/battle/BattleMusicController\n*L\n45#1:62,2\n*E\n"})
public final class BattleMusicController {
    @NotNull
    public static final BattleMusicController INSTANCE = new BattleMusicController();
    @NotNull
    private static BattleMusicInstance music;
    @NotNull
    private static final List<SoundSource> filteredCategories;
    private static final SoundManager manager;

    private BattleMusicController() {
    }

    @NotNull
    public final BattleMusicInstance getMusic() {
        return music;
    }

    @NotNull
    public final List<SoundSource> getFilteredCategories() {
        return filteredCategories;
    }

    public final void initializeMusic(@NotNull BattleMusicInstance newMusic) {
        Intrinsics.checkNotNullParameter((Object)((Object)newMusic), (String)"newMusic");
        music = newMusic;
        manager.m_120367_((SoundInstance)music);
        if (manager.m_120403_((SoundInstance)music)) {
            Iterable $this$forEach$iv = filteredCategories;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                SoundSource it = (SoundSource)element$iv;
                boolean bl = false;
                SoundManager soundManager = manager;
                Intrinsics.checkNotNullExpressionValue((Object)soundManager, (String)"manager");
                SoundExtensionsKt.pauseSounds(soundManager, null, it);
            }
            SoundManager soundManager = manager;
            Intrinsics.checkNotNullExpressionValue((Object)soundManager, (String)"manager");
            SoundExtensionsKt.resumeSounds(soundManager, music.m_7904_(), SoundSource.MUSIC);
        }
    }

    public final void switchMusic(@NotNull BattleMusicInstance newMusic) {
        Intrinsics.checkNotNullParameter((Object)((Object)newMusic), (String)"newMusic");
        manager.m_120399_((SoundInstance)music);
        music = newMusic;
        manager.m_120367_((SoundInstance)music);
    }

    public final void endMusic() {
        music.setFade();
    }

    static {
        SoundEvent soundEvent = SoundEvents.f_271165_;
        Intrinsics.checkNotNullExpressionValue((Object)soundEvent, (String)"INTENTIONALLY_EMPTY");
        music = new BattleMusicInstance(soundEvent, 0.0f, 0.0f);
        Object[] objectArray = new SoundSource[]{SoundSource.AMBIENT, SoundSource.MUSIC, SoundSource.RECORDS};
        filteredCategories = CollectionsKt.listOf((Object[])objectArray);
        manager = Minecraft.m_91087_().m_91106_();
    }
}

