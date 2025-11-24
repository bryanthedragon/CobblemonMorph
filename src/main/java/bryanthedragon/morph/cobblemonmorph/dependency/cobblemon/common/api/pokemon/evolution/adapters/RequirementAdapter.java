/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonSerializer
 *  kotlin.Metadata
 *  kotlin.reflect.KClass
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import kotlin.Metadata;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0012\u0004\u0012\u00020\u00020\u0003J/\u0010\n\u001a\u00020\t\"\b\b\u0000\u0010\u0004*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00052\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007H&\u00a2\u0006\u0004\b\n\u0010\u000b\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/evolution/adapters/RequirementAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/requirement/EvolutionRequirement;", "Lcom/google/gson/JsonSerializer;", "T", "", "id", "Lkotlin/reflect/KClass;", "type", "", "registerType", "(Ljava/lang/String;Lkotlin/reflect/KClass;)V", "common"})
public interface RequirementAdapter
extends JsonDeserializer<EvolutionRequirement>,
JsonSerializer<EvolutionRequirement> {
    public <T extends EvolutionRequirement> void registerType(@NotNull String var1, @NotNull KClass<T> var2);
}

