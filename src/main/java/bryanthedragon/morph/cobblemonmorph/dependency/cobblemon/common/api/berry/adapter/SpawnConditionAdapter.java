/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonSerializer
 *  kotlin.Metadata
 *  kotlin.reflect.KClass
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.adapter;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.spawncondition.BerrySpawnCondition;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import kotlin.Metadata;
import kotlin.reflect.KClass;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0012\u0004\u0012\u00020\u00020\u0003J'\u0010\t\u001a\u00020\b2\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H&\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/api/berry/adapter/SpawnConditionAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/api/berry/spawncondition/BerrySpawnCondition;", "Lcom/google/gson/JsonSerializer;", "Lkotlin/reflect/KClass;", "type", "Lnet/minecraft/resources/ResourceLocation;", "identifier", "", "register", "(Lkotlin/reflect/KClass;Lnet/minecraft/resources/ResourceLocation;)V", "common"})
public interface SpawnConditionAdapter
extends JsonDeserializer<BerrySpawnCondition>,
JsonSerializer<BerrySpawnCondition> {
    public void register(@NotNull KClass<? extends BerrySpawnCondition> var1, @NotNull ResourceLocation var2);
}

