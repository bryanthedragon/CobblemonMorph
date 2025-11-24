/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.levelgen.structure.Structure
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.template.EntityQueryRequirement;
import java.util.Iterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u0007\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bR\u001f\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000eR\u001f\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\f\u001a\u0004\b\u0010\u0010\u000e\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/requirements/StructureRequirement;", "Lcom/cobblemon/mod/common/pokemon/evolution/requirements/template/EntityQueryRequirement;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lnet/minecraft/world/entity/LivingEntity;", "queriedEntity", "", "check", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lnet/minecraft/world/entity/LivingEntity;)Z", "Lcom/cobblemon/mod/common/api/conditional/RegistryLikeCondition;", "Lnet/minecraft/world/level/levelgen/structure/Structure;", "structureAnticondition", "Lcom/cobblemon/mod/common/api/conditional/RegistryLikeCondition;", "getStructureAnticondition", "()Lcom/cobblemon/mod/common/api/conditional/RegistryLikeCondition;", "structureCondition", "getStructureCondition", "<init>", "()V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nStructureRequirement.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StructureRequirement.kt\ncom/cobblemon/mod/common/pokemon/evolution/requirements/StructureRequirement\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,38:1\n187#2,3:39\n*S KotlinDebug\n*F\n+ 1 StructureRequirement.kt\ncom/cobblemon/mod/common/pokemon/evolution/requirements/StructureRequirement\n*L\n31#1:39,3\n*E\n"})
public final class StructureRequirement
implements EntityQueryRequirement {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @Nullable
    private final RegistryLikeCondition<Structure> structureCondition;
    @Nullable
    private final RegistryLikeCondition<Structure> structureAnticondition;
    @NotNull
    public static final String ADAPTER_VARIANT = "structure";

    @Nullable
    public final RegistryLikeCondition<Structure> getStructureCondition() {
        return this.structureCondition;
    }

    @Nullable
    public final RegistryLikeCondition<Structure> getStructureAnticondition() {
        return this.structureAnticondition;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean check(@NotNull Pokemon pokemon, @NotNull LivingEntity queriedEntity) {
        Object k;
        Map.Entry it;
        Map.Entry element$iv;
        Iterator iterator;
        boolean $i$f$any;
        Map $this$any$iv;
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)queriedEntity, (String)"queriedEntity");
        Map structures = queriedEntity.m_9236_().m_46865_(queriedEntity.m_20183_()).m_62769_();
        Registry registry = queriedEntity.m_9236_().m_9598_().m_175515_(Registries.f_256944_);
        if (this.structureCondition != null) {
            Object k2;
            Intrinsics.checkNotNullExpressionValue((Object)structures, (String)"structures");
            $this$any$iv = structures;
            $i$f$any = false;
            if ($this$any$iv.isEmpty()) {
                return false;
            }
            iterator = $this$any$iv.entrySet().iterator();
            do {
                if (!iterator.hasNext()) return false;
                it = element$iv = iterator.next();
                boolean bl = false;
                k2 = it.getKey();
                Intrinsics.checkNotNullExpressionValue(k2, (String)"it.key");
                Intrinsics.checkNotNullExpressionValue((Object)registry, (String)"registry");
            } while (!this.structureCondition.fits((Structure)k2, (Registry<Structure>)registry));
            boolean bl = true;
            if (!bl) return false;
        }
        if (this.structureAnticondition == null) return true;
        Intrinsics.checkNotNullExpressionValue((Object)structures, (String)"structures");
        $this$any$iv = structures;
        $i$f$any = false;
        if ($this$any$iv.isEmpty()) {
            return true;
        }
        iterator = $this$any$iv.entrySet().iterator();
        do {
            if (!iterator.hasNext()) return true;
            it = element$iv = iterator.next();
            boolean bl = false;
            k = it.getKey();
            Intrinsics.checkNotNullExpressionValue(k, (String)"it.key");
            Intrinsics.checkNotNullExpressionValue((Object)registry, (String)"registry");
        } while (!this.structureAnticondition.fits((Structure)k, (Registry<Structure>)registry));
        return false;
    }

    @Override
    public boolean check(@NotNull Pokemon pokemon) {
        return EntityQueryRequirement.DefaultImpls.check(this, pokemon);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/requirements/StructureRequirement$Companion;", "", "", "ADAPTER_VARIANT", "Ljava/lang/String;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

