/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonElement
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.world.level.material.Fluid
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeIdentifierCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeTagCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.FluidLikeConditionAdapter;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import java.lang.reflect.Type;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rR<\u0010\b\u001a$\u0012 \u0012\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0014\u0012\u0012\u0012\f\u0012\n \u0007*\u0004\u0018\u00010\u00020\u0002\u0018\u00010\u00060\u00040\u00038\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/util/adapters/FluidLikeConditionAdapter;", "Lcom/cobblemon/mod/common/api/conditional/RegistryLikeAdapter;", "Lnet/minecraft/world/level/material/Fluid;", "", "Lkotlin/Function1;", "Lcom/google/gson/JsonElement;", "Lcom/cobblemon/mod/common/api/conditional/RegistryLikeCondition;", "kotlin.jvm.PlatformType", "registryLikeConditions", "Ljava/util/List;", "getRegistryLikeConditions", "()Ljava/util/List;", "<init>", "()V", "common"})
public final class FluidLikeConditionAdapter
implements RegistryLikeAdapter<Fluid> {
    @NotNull
    public static final FluidLikeConditionAdapter INSTANCE = new FluidLikeConditionAdapter();
    @NotNull
    private static final List<Function1<JsonElement, RegistryLikeCondition<Fluid>>> registryLikeConditions;

    private FluidLikeConditionAdapter() {
    }

    @Override
    @NotNull
    public List<Function1<JsonElement, RegistryLikeCondition<Fluid>>> getRegistryLikeConditions() {
        return registryLikeConditions;
    }

    @Override
    @NotNull
    public RegistryLikeCondition<Fluid> deserialize(@NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext ctx) {
        return RegistryLikeAdapter.DefaultImpls.deserialize(this, json, type, ctx);
    }

    static {
        Object[] objectArray = new Function1[2];
        ResourceKey resourceKey = Registries.f_256808_;
        Intrinsics.checkNotNullExpressionValue((Object)resourceKey, (String)"FLUID");
        objectArray[0] = RegistryLikeTagCondition.Companion.resolver(resourceKey, registryLikeConditions.1.INSTANCE);
        objectArray[1] = RegistryLikeIdentifierCondition.Companion.resolver(registryLikeConditions.2.INSTANCE);
        registryLikeConditions = CollectionsKt.mutableListOf((Object[])objectArray);
    }
}

