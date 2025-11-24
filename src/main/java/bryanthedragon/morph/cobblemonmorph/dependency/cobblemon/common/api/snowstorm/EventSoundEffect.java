/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/EventSoundEffect;", "", "Lnet/minecraft/resources/ResourceLocation;", "sound", "Lnet/minecraft/resources/ResourceLocation;", "getSound", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "(Lnet/minecraft/resources/ResourceLocation;)V", "Companion", "common"})
public final class EventSoundEffect {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final ResourceLocation sound;
    private static final Codec<EventSoundEffect> CODEC = RecordCodecBuilder.create(EventSoundEffect::CODEC$lambda$1);

    public EventSoundEffect(@NotNull ResourceLocation sound2) {
        Intrinsics.checkNotNullParameter((Object)sound2, (String)"sound");
        this.sound = sound2;
    }

    @NotNull
    public final ResourceLocation getSound() {
        return this.sound;
    }

    private static final ResourceLocation CODEC$lambda$1$lambda$0(EventSoundEffect it) {
        return it.sound;
    }

    private static final App CODEC$lambda$1(RecordCodecBuilder.Instance instance) {
        return instance.group((App)ResourceLocation.f_135803_.fieldOf("sound").forGetter(EventSoundEffect::CODEC$lambda$1$lambda$0)).apply((Applicative)instance, EventSoundEffect::new);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nR;\u0010\u0005\u001a&\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003 \u0004*\u0012\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/EventSoundEffect$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/EventSoundEffect;", "kotlin.jvm.PlatformType", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public final Codec<EventSoundEffect> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

