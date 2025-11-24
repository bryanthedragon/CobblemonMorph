/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Lazy
 *  kotlin.LazyKt
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.StructureManager
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.levelgen.structure.Structure
 *  net.minecraft.world.level.material.Fluid
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.VariableStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.RegisteredSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.Spawner;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0096\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\b\b&\u0018\u0000 U2\u00020\u0001:\u0002UVB\u0007\u00a2\u0006\u0004\bS\u0010TJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\r\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\r\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\nH&\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0017\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u000fH\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0017\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\u000fH\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0017\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\u000fH\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0016R\u001b\u0010\u001d\u001a\u00020\u00188FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010!\u001a\u00020\u001e8F\u00a2\u0006\u0006\u001a\u0004\b\u001f\u0010 R!\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00180\"8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b#\u0010\u001a\u001a\u0004\b$\u0010%R!\u0010*\u001a\b\u0012\u0004\u0012\u00020'0\"8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b(\u0010\u001a\u001a\u0004\b)\u0010%R\u0014\u0010-\u001a\u00020\u00148&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b+\u0010,R\u0014\u00101\u001a\u00020.8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b/\u00100R!\u00105\u001a\b\u0012\u0004\u0012\u0002020\"8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b3\u0010\u001a\u001a\u0004\b4\u0010%R\u001a\u0010:\u001a\b\u0012\u0004\u0012\u000207068&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b8\u00109R\u0014\u0010>\u001a\u00020;8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b<\u0010=R\u001b\u0010A\u001a\u00020;8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b?\u0010\u001a\u001a\u0004\b@\u0010=R\u0014\u0010D\u001a\u00020\n8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\bB\u0010CR\u0014\u0010F\u001a\u00020;8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\bE\u0010=R\u0011\u0010J\u001a\u00020G8F\u00a2\u0006\u0006\u001a\u0004\bH\u0010IR\u0014\u0010K\u001a\u00020\u00078\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bK\u0010LR\u0016\u0010M\u001a\u00020\u00148\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\bM\u0010NR\u0014\u0010R\u001a\u00020O8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\bP\u0010Q\u00a8\u0006W"}, d2={"Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "", "Lnet/minecraft/world/entity/Entity;", "entity", "", "afterSpawn", "(Lnet/minecraft/world/entity/Entity;)V", "Lcom/bedrockk/molang/runtime/struct/VariableStruct;", "getOrSetupStruct", "()Lcom/bedrockk/molang/runtime/struct/VariableStruct;", "Lnet/minecraft/core/BlockPos;", "pos", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext$StructureChunkCache;", "getStructureCache", "(Lnet/minecraft/core/BlockPos;)Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext$StructureChunkCache;", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "detail", "", "getWeight", "(Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;)F", "", "postFilter", "(Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;)Z", "preFilter", "Lnet/minecraft/world/level/biome/Biome;", "biome$delegate", "Lkotlin/Lazy;", "getBiome", "()Lnet/minecraft/world/level/biome/Biome;", "biome", "Lnet/minecraft/resources/ResourceLocation;", "getBiomeName", "()Lnet/minecraft/resources/ResourceLocation;", "biomeName", "Lnet/minecraft/core/Registry;", "biomeRegistry$delegate", "getBiomeRegistry", "()Lnet/minecraft/core/Registry;", "biomeRegistry", "Lnet/minecraft/world/level/block/Block;", "blockRegistry$delegate", "getBlockRegistry", "blockRegistry", "getCanSeeSky", "()Z", "canSeeSky", "Lcom/cobblemon/mod/common/api/spawning/SpawnCause;", "getCause", "()Lcom/cobblemon/mod/common/api/spawning/SpawnCause;", "cause", "Lnet/minecraft/world/level/material/Fluid;", "fluidRegistry$delegate", "getFluidRegistry", "fluidRegistry", "", "Lcom/cobblemon/mod/common/api/spawning/influence/SpawningInfluence;", "getInfluences", "()Ljava/util/List;", "influences", "", "getLight", "()I", "light", "moonPhase$delegate", "getMoonPhase", "moonPhase", "getPosition", "()Lnet/minecraft/core/BlockPos;", "position", "getSkyLight", "skyLight", "Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;", "getSpawner", "()Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;", "spawner", "struct", "Lcom/bedrockk/molang/runtime/struct/VariableStruct;", "structCompiled", "Z", "Lnet/minecraft/server/level/ServerLevel;", "getWorld", "()Lnet/minecraft/server/level/ServerLevel;", "world", "<init>", "()V", "Companion", "StructureChunkCache", "common"})
@SourceDebugExtension(value={"SMAP\nSpawningContext.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawningContext.kt\ncom/cobblemon/mod/common/api/spawning/context/SpawningContext\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,187:1\n2624#2,3:188\n1855#2,2:191\n*S KotlinDebug\n*F\n+ 1 SpawningContext.kt\ncom/cobblemon/mod/common/api/spawning/context/SpawningContext\n*L\n150#1:188,3\n160#1:191,2\n*E\n"})
public abstract class SpawningContext {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Lazy moonPhase$delegate = LazyKt.lazy((Function0)((Function0)new Function0<Integer>(this){
        final /* synthetic */ SpawningContext this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Integer invoke() {
            return this.this$0.getWorld().m_46941_();
        }
    }));
    @NotNull
    private final Lazy biome$delegate = LazyKt.lazy((Function0)((Function0)new Function0<Biome>(this){
        final /* synthetic */ SpawningContext this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        public final Biome invoke() {
            return (Biome)this.this$0.getWorld().m_204166_(this.this$0.getPosition()).m_203334_();
        }
    }));
    @NotNull
    private final Lazy biomeRegistry$delegate = LazyKt.lazy((Function0)((Function0)new Function0<Registry<Biome>>(this){
        final /* synthetic */ SpawningContext this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        public final Registry<Biome> invoke() {
            return this.this$0.getWorld().m_9598_().m_175515_(Registries.f_256952_);
        }
    }));
    @NotNull
    private final Lazy blockRegistry$delegate = LazyKt.lazy((Function0)((Function0)new Function0<Registry<Block>>(this){
        final /* synthetic */ SpawningContext this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        public final Registry<Block> invoke() {
            return this.this$0.getWorld().m_9598_().m_175515_(Registries.f_256747_);
        }
    }));
    @NotNull
    private final Lazy fluidRegistry$delegate = LazyKt.lazy((Function0)((Function0)new Function0<Registry<Fluid>>(this){
        final /* synthetic */ SpawningContext this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        public final Registry<Fluid> invoke() {
            return this.this$0.getWorld().m_9598_().m_175515_(Registries.f_256808_);
        }
    }));
    @NotNull
    private final VariableStruct struct = new VariableStruct();
    private boolean structCompiled;
    @NotNull
    private static final List<RegisteredSpawningContext<?>> contexts = new ArrayList();

    @NotNull
    public abstract SpawnCause getCause();

    @NotNull
    public final Spawner getSpawner() {
        return this.getCause().getSpawner();
    }

    @NotNull
    public abstract ServerLevel getWorld();

    @NotNull
    public abstract BlockPos getPosition();

    public abstract int getLight();

    public abstract int getSkyLight();

    public abstract boolean getCanSeeSky();

    @NotNull
    public abstract List<SpawningInfluence> getInfluences();

    @NotNull
    public abstract StructureChunkCache getStructureCache(@NotNull BlockPos var1);

    public final int getMoonPhase() {
        Lazy lazy = this.moonPhase$delegate;
        return ((Number)lazy.getValue()).intValue();
    }

    @NotNull
    public final Biome getBiome() {
        Lazy lazy = this.biome$delegate;
        Object object = lazy.getValue();
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"<get-biome>(...)");
        return (Biome)object;
    }

    @NotNull
    public final Registry<Biome> getBiomeRegistry() {
        Lazy lazy = this.biomeRegistry$delegate;
        Object object = lazy.getValue();
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"<get-biomeRegistry>(...)");
        return (Registry)object;
    }

    @NotNull
    public final Registry<Block> getBlockRegistry() {
        Lazy lazy = this.blockRegistry$delegate;
        Object object = lazy.getValue();
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"<get-blockRegistry>(...)");
        return (Registry)object;
    }

    @NotNull
    public final Registry<Fluid> getFluidRegistry() {
        Lazy lazy = this.fluidRegistry$delegate;
        Object object = lazy.getValue();
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"<get-fluidRegistry>(...)");
        return (Registry)object;
    }

    @NotNull
    public final ResourceLocation getBiomeName() {
        ResourceLocation resourceLocation = this.getBiomeRegistry().m_7981_((Object)this.getBiome());
        Intrinsics.checkNotNull((Object)resourceLocation);
        return resourceLocation;
    }

    public boolean preFilter(@NotNull SpawnDetail detail) {
        boolean bl;
        block3: {
            Intrinsics.checkNotNullParameter((Object)detail, (String)"detail");
            Iterable $this$none$iv = this.getInfluences();
            boolean $i$f$none = false;
            if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                bl = true;
            } else {
                for (Object element$iv : $this$none$iv) {
                    SpawningInfluence it = (SpawningInfluence)element$iv;
                    boolean bl2 = false;
                    if (!(!it.affectSpawnable(detail, this))) continue;
                    bl = false;
                    break block3;
                }
                bl = true;
            }
        }
        return bl;
    }

    public boolean postFilter(@NotNull SpawnDetail detail) {
        Intrinsics.checkNotNullParameter((Object)detail, (String)"detail");
        return true;
    }

    public void afterSpawn(@NotNull Entity entity2) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        Iterable $this$forEach$iv = this.getInfluences();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            SpawningInfluence it = (SpawningInfluence)element$iv;
            boolean bl = false;
            it.affectSpawn(entity2);
        }
    }

    public float getWeight(@NotNull SpawnDetail detail) {
        Intrinsics.checkNotNullParameter((Object)detail, (String)"detail");
        float weight = detail.getWeight();
        for (SpawningInfluence influence : CollectionsKt.plus((Collection)this.getInfluences(), (Iterable)detail.getWeightMultipliers())) {
            weight = influence.affectWeight(detail, this, weight);
        }
        return weight;
    }

    @NotNull
    public final VariableStruct getOrSetupStruct() {
        if (this.structCompiled) {
            return this.struct;
        }
        this.struct.setDirectly("light", new DoubleValue(this.getLight()));
        this.struct.setDirectly("x", new DoubleValue(this.getPosition().m_123341_()));
        this.struct.setDirectly("y", new DoubleValue(this.getPosition().m_123342_()));
        this.struct.setDirectly("z", new DoubleValue(this.getPosition().m_123343_()));
        this.struct.setDirectly("moon_phase", new DoubleValue(this.getMoonPhase()));
        this.struct.setDirectly("world", new ObjectValue(this.getWorld().m_9598_().m_175515_(Registries.f_256858_).m_263177_((Object)this.getWorld()), null, null, 6, null));
        this.struct.setDirectly("biome", new ObjectValue(this.getBiomeRegistry().m_263177_((Object)this.getBiome()), null, null, 6, null));
        this.structCompiled = true;
        return this.struct;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u001b\u0010\u0005\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u001b\u0010\t\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00042\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\t\u0010\nJ7\u0010\u0010\u001a\u00020\u000f\"\b\b\u0000\u0010\u000b*\u00020\u00022\u0006\u0010\b\u001a\u00020\u00072\f\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00000\f2\b\b\u0002\u0010\u000e\u001a\u00020\u0007\u00a2\u0006\u0004\b\u0010\u0010\u0011R!\u0010\u0013\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u00128\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext$Companion;", "", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "ctx", "Lcom/cobblemon/mod/common/api/spawning/context/RegisteredSpawningContext;", "getByClass", "(Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;)Lcom/cobblemon/mod/common/api/spawning/context/RegisteredSpawningContext;", "", "name", "getByName", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/spawning/context/RegisteredSpawningContext;", "T", "Ljava/lang/Class;", "clazz", "defaultCondition", "", "register", "(Ljava/lang/String;Ljava/lang/Class;Ljava/lang/String;)V", "", "contexts", "Ljava/util/List;", "getContexts", "()Ljava/util/List;", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nSpawningContext.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawningContext.kt\ncom/cobblemon/mod/common/api/spawning/context/SpawningContext$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,187:1\n1#2:188\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final List<RegisteredSpawningContext<?>> getContexts() {
            return contexts;
        }

        @Nullable
        public final RegisteredSpawningContext<?> getByName(@NotNull String name) {
            Object v0;
            block1: {
                Intrinsics.checkNotNullParameter((Object)name, (String)"name");
                Iterable iterable = this.getContexts();
                for (Object t : iterable) {
                    RegisteredSpawningContext it = (RegisteredSpawningContext)t;
                    boolean bl = false;
                    if (!Intrinsics.areEqual((Object)it.getName(), (Object)name)) continue;
                    v0 = t;
                    break block1;
                }
                v0 = null;
            }
            return v0;
        }

        @Nullable
        public final RegisteredSpawningContext<?> getByClass(@NotNull SpawningContext ctx) {
            Object v0;
            block1: {
                Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
                Iterable iterable = this.getContexts();
                for (Object t : iterable) {
                    RegisteredSpawningContext it = (RegisteredSpawningContext)t;
                    boolean bl = false;
                    if (!Intrinsics.areEqual(it.getClazz(), ctx.getClass())) continue;
                    v0 = t;
                    break block1;
                }
                v0 = null;
            }
            return v0;
        }

        public final <T extends SpawningContext> void register(@NotNull String name, @NotNull Class<T> clazz, @NotNull String defaultCondition) {
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            Intrinsics.checkNotNullParameter(clazz, (String)"clazz");
            Intrinsics.checkNotNullParameter((Object)defaultCondition, (String)"defaultCondition");
            this.getContexts().add(new RegisteredSpawningContext<T>(name, clazz, defaultCondition));
        }

        public static /* synthetic */ void register$default(Companion companion, String string, Class clazz, String string2, int n, Object object) {
            if ((n & 4) != 0) {
                string2 = "basic";
            }
            companion.register(string, clazz, string2);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b$\u0010%J+\u0010\n\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\u0004\b\n\u0010\u000bJ%\u0010\n\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\n\u0010\u000eJ\u001d\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0010\u0010\u0011R\u001d\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\f0\u00128\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R#\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u00128\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0014\u001a\u0004\b\u0018\u0010\u0016R\"\u0010\u0019\u001a\u00020\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR#\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u00128\u0006\u00a2\u0006\f\n\u0004\b\u001f\u0010\u0014\u001a\u0004\b \u0010\u0016R#\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070!0\u00128\u0006\u00a2\u0006\f\n\u0004\b\"\u0010\u0014\u001a\u0004\b#\u0010\u0016\u00a8\u0006&"}, d2={"Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext$StructureChunkCache;", "", "Lnet/minecraft/world/level/StructureManager;", "structureAccess", "Lnet/minecraft/core/BlockPos;", "pos", "Lnet/minecraft/tags/TagKey;", "Lnet/minecraft/world/level/levelgen/structure/Structure;", "tagKey", "", "check", "(Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/core/BlockPos;Lnet/minecraft/tags/TagKey;)Z", "Lnet/minecraft/resources/ResourceLocation;", "id", "(Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/core/BlockPos;Lnet/minecraft/resources/ResourceLocation;)Z", "", "loadStructures", "(Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/core/BlockPos;)V", "", "foundIdentifiers", "Ljava/util/Set;", "getFoundIdentifiers", "()Ljava/util/Set;", "foundTags", "getFoundTags", "loadedStructures", "Z", "getLoadedStructures", "()Z", "setLoadedStructures", "(Z)V", "missingTags", "getMissingTags", "Lnet/minecraft/core/Holder;", "structures", "getStructures", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nSpawningContext.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawningContext.kt\ncom/cobblemon/mod/common/api/spawning/context/SpawningContext$StructureChunkCache\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,187:1\n1855#2,2:188\n*S KotlinDebug\n*F\n+ 1 SpawningContext.kt\ncom/cobblemon/mod/common/api/spawning/context/SpawningContext$StructureChunkCache\n*L\n123#1:188,2\n*E\n"})
    public static final class StructureChunkCache {
        @NotNull
        private final Set<TagKey<Structure>> missingTags = new LinkedHashSet();
        @NotNull
        private final Set<TagKey<Structure>> foundTags = new LinkedHashSet();
        @NotNull
        private final Set<ResourceLocation> foundIdentifiers = new LinkedHashSet();
        private boolean loadedStructures;
        @NotNull
        private final Set<Holder<Structure>> structures = new LinkedHashSet();

        @NotNull
        public final Set<TagKey<Structure>> getMissingTags() {
            return this.missingTags;
        }

        @NotNull
        public final Set<TagKey<Structure>> getFoundTags() {
            return this.foundTags;
        }

        @NotNull
        public final Set<ResourceLocation> getFoundIdentifiers() {
            return this.foundIdentifiers;
        }

        public final boolean getLoadedStructures() {
            return this.loadedStructures;
        }

        public final void setLoadedStructures(boolean bl) {
            this.loadedStructures = bl;
        }

        @NotNull
        public final Set<Holder<Structure>> getStructures() {
            return this.structures;
        }

        public final void loadStructures(@NotNull StructureManager structureAccess, @NotNull BlockPos pos) {
            Intrinsics.checkNotNullParameter((Object)structureAccess, (String)"structureAccess");
            Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
            Registry registry = structureAccess.m_220521_().m_175515_(Registries.f_256944_);
            structureAccess.m_220477_(new ChunkPos(pos), arg_0 -> StructureChunkCache.loadStructures$lambda$0(registry, this, arg_0));
            this.loadedStructures = true;
        }

        public final boolean check(@NotNull StructureManager structureAccess, @NotNull BlockPos pos, @NotNull TagKey<Structure> tagKey) {
            Intrinsics.checkNotNullParameter((Object)structureAccess, (String)"structureAccess");
            Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
            Intrinsics.checkNotNullParameter(tagKey, (String)"tagKey");
            if (!this.loadedStructures) {
                this.loadStructures(structureAccess, pos);
            }
            if (this.missingTags.contains(tagKey)) {
                return false;
            }
            if (this.foundTags.contains(tagKey)) {
                return true;
            }
            Iterable $this$forEach$iv = this.structures;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                Holder structure = (Holder)element$iv;
                boolean bl = false;
                if (!structure.m_203656_(tagKey)) continue;
                this.foundTags.add(tagKey);
                return true;
            }
            this.missingTags.add(tagKey);
            return false;
        }

        public final boolean check(@NotNull StructureManager structureAccess, @NotNull BlockPos pos, @NotNull ResourceLocation id) {
            Intrinsics.checkNotNullParameter((Object)structureAccess, (String)"structureAccess");
            Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
            Intrinsics.checkNotNullParameter((Object)id, (String)"id");
            if (!this.loadedStructures) {
                this.loadStructures(structureAccess, pos);
            }
            return this.foundIdentifiers.contains(id);
        }

        private static final boolean loadStructures$lambda$0(Registry $registry, StructureChunkCache this$0, Structure structure) {
            Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
            Holder holder = $registry.m_263177_((Object)structure);
            if (holder == null) {
                return true;
            }
            Holder entry = holder;
            this$0.structures.add((Holder<Structure>)entry);
            Set<ResourceLocation> set2 = this$0.foundIdentifiers;
            ResourceLocation resourceLocation = ((ResourceKey)entry.m_203543_().get()).m_135782_();
            Intrinsics.checkNotNullExpressionValue((Object)resourceLocation, (String)"entry.key.get().value");
            set2.add(resourceLocation);
            return false;
        }
    }
}

