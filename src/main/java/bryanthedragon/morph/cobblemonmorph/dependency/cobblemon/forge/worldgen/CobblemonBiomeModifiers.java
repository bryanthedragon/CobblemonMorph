/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.levelgen.GenerationStep$Decoration
 *  net.minecraft.world.level.levelgen.placement.PlacedFeature
 *  net.minecraftforge.common.world.BiomeModifier
 *  net.minecraftforge.common.world.BiomeModifier$Phase
 *  net.minecraftforge.common.world.ModifiableBiomeInfo$BiomeInfo$Builder
 *  net.minecraftforge.registries.ForgeRegistries$Keys
 *  net.minecraftforge.registries.RegisterEvent
 *  net.minecraftforge.registries.RegisterEvent$RegisterHelper
 *  net.minecraftforge.server.ServerLifecycleHooks
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.forge.worldgen;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings({ "rawtypes", "unchecked" })
public final class CobblemonBiomeModifiers
implements BiomeModifier {
    @NotNull
    public static final CobblemonBiomeModifiers INSTANCE = new CobblemonBiomeModifiers();
    @Nullable
    private static Codec<? extends BiomeModifier> codec;
    @NotNull
    private static final ArrayList<Entry> entries;

    private CobblemonBiomeModifiers() {
    }

    public final void register(@NotNull RegisterEvent event) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        event.register(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, arg_0 -> CobblemonBiomeModifiers.register$lambda$0(this, arg_0));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public final void add(@NotNull ResourceKey<PlacedFeature> feature, @NotNull GenerationStep.Decoration step, @Nullable TagKey<Biome> validTag) {
        Intrinsics.checkNotNullParameter(feature, (String)"feature");
        Intrinsics.checkNotNullParameter((Object)step, (String)"step");
        ((Collection)entries).add(new Entry(feature, step, validTag));
    }

    @SuppressWarnings("rawtypes")
    public void modify(@NotNull Holder<Biome> arg, @NotNull BiomeModifier.Phase phase, @NotNull ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        Intrinsics.checkNotNullParameter(arg, (String)"arg");
        Intrinsics.checkNotNullParameter((Object)phase, (String)"phase");
        Intrinsics.checkNotNullParameter((Object)builder, (String)"builder");
        if (phase != BiomeModifier.Phase.ADD) {
            return;
        }
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        Registry registry = server.m_206579_().m_175515_(Registries.f_256988_);
        Iterable $this$forEach$iv = entries;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Entry entry = (Entry)element$iv;
            boolean bl = false;
            if (entry.getValidTag() != null && !arg.m_203656_(entry.getValidTag())) continue;
            builder.getGenerationSettings().m_255419_(entry.getStep(), Holder.m_205709_((Object)registry.m_6246_(entry.getFeature())));
        }
    }

    @NotNull
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Codec<? extends BiomeModifier> codec() {
        Codec codec2 = codec;
        if (codec2 == null) {
            Codec codec3 = Codec.unit((Object)INSTANCE);
            codec2 = codec3;
            Intrinsics.checkNotNullExpressionValue((Object)codec3, (String)"unit(CobblemonBiomeModifiers)");
        }
        return codec2;
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static final void register$lambda$0(CobblemonBiomeModifiers this$0, RegisterEvent.RegisterHelper helper) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        codec = (@Nullable Codec<? extends BiomeModifier>) Codec.unit((Object)INSTANCE);
        helper.register(MiscUtils.cobblemonResource("inject_coded"), codec);
    }

    static {
        entries = new ArrayList();
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0082\b\u0018\u00002\u00020\u0001B-\u0012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\u0006\u0010\u000e\u001a\u00020\u0006\u0012\u000e\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t\u00a2\u0006\u0004\b\"\u0010#J\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0007\u001a\u00020\u0006H\u00c6\u0003\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0018\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\tH\u00c6\u0003\u00a2\u0006\u0004\b\u000b\u0010\fJ<\u0010\u0010\u001a\u00020\u00002\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\b\b\u0002\u0010\u000e\u001a\u00020\u00062\u0010\b\u0002\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\tH\u00c6\u0001\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u001a\u0010\u0014\u001a\u00020\u00132\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0010\u0010\u0017\u001a\u00020\u0016H\u00d6\u0001\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0010\u0010\u001a\u001a\u00020\u0019H\u00d6\u0001\u00a2\u0006\u0004\b\u001a\u0010\u001bR\u001d\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u001c\u001a\u0004\b\u001d\u0010\u0005R\u0017\u0010\u000e\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u001e\u001a\u0004\b\u001f\u0010\bR\u001f\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010 \u001a\u0004\b!\u0010\f\u00a8\u0006$"}, d2={"Lcom/cobblemon/mod/forge/worldgen/CobblemonBiomeModifiers$Entry;", "", "Lnet/minecraft/resources/ResourceKey;", "Lnet/minecraft/world/level/levelgen/placement/PlacedFeature;", "component1", "()Lnet/minecraft/resources/ResourceKey;", "Lnet/minecraft/world/gen/GenerationStep$Feature;", "component2", "()Lnet/minecraft/world/level/levelgen/GenerationStep$Decoration;", "Lnet/minecraft/tags/TagKey;", "Lnet/minecraft/world/level/biome/Biome;", "component3", "()Lnet/minecraft/tags/TagKey;", "feature", "step", "validTag", "copy", "(Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/world/level/levelgen/GenerationStep$Decoration;Lnet/minecraft/tags/TagKey;)Lcom/cobblemon/mod/forge/worldgen/CobblemonBiomeModifiers$Entry;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/resources/ResourceKey;", "getFeature", "Lnet/minecraft/world/level/levelgen/GenerationStep$Decoration;", "getStep", "Lnet/minecraft/tags/TagKey;", "getValidTag", "<init>", "(Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/world/level/levelgen/GenerationStep$Decoration;Lnet/minecraft/tags/TagKey;)V", "forge"})
    private static final class Entry {
        @NotNull
        private final ResourceKey<PlacedFeature> feature;
        @NotNull
        private final GenerationStep.Decoration step;
        @Nullable
        private final TagKey<Biome> validTag;

        public Entry(@NotNull ResourceKey<PlacedFeature> feature, @NotNull GenerationStep.Decoration step, @Nullable TagKey<Biome> validTag) {
            Intrinsics.checkNotNullParameter(feature, (String)"feature");
            Intrinsics.checkNotNullParameter((Object)step, (String)"step");
            this.feature = feature;
            this.step = step;
            this.validTag = validTag;
        }

        @NotNull
        public final ResourceKey<PlacedFeature> getFeature() {
            return this.feature;
        }

        @NotNull
        public final GenerationStep.Decoration getStep() {
            return this.step;
        }

        @Nullable
        public final TagKey<Biome> getValidTag() {
            return this.validTag;
        }

        @NotNull
        public final ResourceKey<PlacedFeature> component1() {
            return this.feature;
        }

        @NotNull
        public final GenerationStep.Decoration component2() {
            return this.step;
        }

        @Nullable
        public final TagKey<Biome> component3() {
            return this.validTag;
        }

        @NotNull
        public final Entry copy(@NotNull ResourceKey<PlacedFeature> feature, @NotNull GenerationStep.Decoration step, @Nullable TagKey<Biome> validTag) {
            Intrinsics.checkNotNullParameter(feature, (String)"feature");
            Intrinsics.checkNotNullParameter((Object)step, (String)"step");
            return new Entry(feature, step, validTag);
        }

        @SuppressWarnings("unused")
        public static /* synthetic */ Entry copy$default(Entry entry, ResourceKey resourceKey, GenerationStep.Decoration decoration, TagKey tagKey, int n, Object object) {
            if ((n & 1) != 0) {
                resourceKey = entry.feature;
            }
            if ((n & 2) != 0) {
                decoration = entry.step;
            }
            if ((n & 4) != 0) {
                tagKey = entry.validTag;
            }
            return entry.copy(resourceKey, decoration, tagKey);
        }

        @NotNull
        public String toString() {
            return "Entry(feature=" + this.feature + ", step=" + this.step + ", validTag=" + this.validTag + ")";
        }

        public int hashCode() {
            int result = this.feature.hashCode();
            result = result * 31 + this.step.hashCode();
            result = result * 31 + (this.validTag == null ? 0 : this.validTag.hashCode());
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry)other;
            if (!Intrinsics.areEqual(this.feature, entry.feature)) {
                return false;
            }
            if (this.step != entry.step) {
                return false;
            }
            return Intrinsics.areEqual(this.validTag, entry.validTag);
        }
    }
}

