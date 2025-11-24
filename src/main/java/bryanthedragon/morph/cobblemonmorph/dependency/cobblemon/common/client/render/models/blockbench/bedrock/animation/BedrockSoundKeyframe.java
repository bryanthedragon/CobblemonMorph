/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockEffectKeyframe;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0010\u001a\u00020\u000f\u0012\u0006\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\u0011\u0010\u0012J/\u0010\b\u001a\u00020\u0007\"\b\b\u0000\u0010\u0003*\u00020\u00022\u0006\u0010\u0004\u001a\u00028\u00002\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H\u0016\u00a2\u0006\u0004\b\b\u0010\tR\u0017\u0010\u000b\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockSoundKeyframe;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockEffectKeyframe;", "Lnet/minecraft/world/entity/Entity;", "T", "entity", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "state", "", "run", "(Lnet/minecraft/world/entity/Entity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;)V", "Lnet/minecraft/resources/ResourceLocation;", "sound", "Lnet/minecraft/resources/ResourceLocation;", "getSound", "()Lnet/minecraft/resources/ResourceLocation;", "", "seconds", "<init>", "(FLnet/minecraft/resources/ResourceLocation;)V", "common"})
public final class BedrockSoundKeyframe
extends BedrockEffectKeyframe {
    @NotNull
    private final ResourceLocation sound;

    public BedrockSoundKeyframe(float seconds, @NotNull ResourceLocation sound2) {
        Intrinsics.checkNotNullParameter((Object)sound2, (String)"sound");
        super(seconds);
        this.sound = sound2;
    }

    @NotNull
    public final ResourceLocation getSound() {
        return this.sound;
    }

    @Override
    public <T extends Entity> void run(@NotNull T entity2, @NotNull PoseableEntityState<T> state) {
        Intrinsics.checkNotNullParameter(entity2, (String)"entity");
        Intrinsics.checkNotNullParameter(state, (String)"state");
        SoundEvent soundEvent = SoundEvent.m_262824_((ResourceLocation)this.sound);
        if (soundEvent != null) {
            Minecraft.m_91087_().m_91106_().m_120367_((SoundInstance)new SimpleSoundInstance(soundEvent, SoundSource.NEUTRAL, 1.0f, 1.0f, entity2.m_9236_().f_46441_, entity2.m_20185_(), entity2.m_20186_(), entity2.m_20189_()));
        }
    }
}

