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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.interaction;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.interaction.EntityInteraction;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import kotlin.Metadata;
import kotlin.reflect.KClass;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\bf\u0018\u0000*\f\b\u0000\u0010\u0002*\u0006\u0012\u0002\b\u00030\u00012\b\u0012\u0004\u0012\u00028\u00000\u00032\b\u0012\u0004\u0012\u00028\u00000\u0004J'\u0010\n\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00052\u000e\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u0007H&\u00a2\u0006\u0004\b\n\u0010\u000b\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/api/interaction/EntityInteractionTypeAdapter;", "Lcom/cobblemon/mod/common/api/interaction/EntityInteraction;", "T", "Lcom/google/gson/JsonDeserializer;", "Lcom/google/gson/JsonSerializer;", "Lnet/minecraft/resources/ResourceLocation;", "identifier", "Lkotlin/reflect/KClass;", "type", "", "registerInteraction", "(Lnet/minecraft/resources/ResourceLocation;Lkotlin/reflect/KClass;)V", "common"})
public interface EntityInteractionTypeAdapter<T extends EntityInteraction<?>>
extends JsonDeserializer<T>,
JsonSerializer<T> {
    public void registerInteraction(@NotNull ResourceLocation var1, @NotNull KClass<? extends T> var2);
}

