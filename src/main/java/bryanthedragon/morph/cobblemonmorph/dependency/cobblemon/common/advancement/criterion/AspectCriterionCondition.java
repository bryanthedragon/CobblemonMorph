/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.advancements.critereon.ContextAwarePredicate
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.SimpleCriterionCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import org.jetbrains.annotations.NotNull;

public final class AspectCriterionCondition extends SimpleCriterionCondition<Map<ResourceLocation, Set<String>>> {
    @NotNull
    private ResourceLocation pokemon;
    @NotNull
    private List<String> aspects;

    @SuppressWarnings({ "removal", "rawtypes", "unchecked" })
    public AspectCriterionCondition(@NotNull ResourceLocation id, @NotNull ContextAwarePredicate predicate) {
        super(id, predicate);
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)predicate, (String)"predicate");
        this.pokemon = new ResourceLocation("cobblemon:pikachu");
        this.aspects = new ArrayList();
    }

    @NotNull
    public final ResourceLocation getPokemon() {
        return this.pokemon;
    }

    public final void setPokemon(@NotNull ResourceLocation resourceLocation) {
        Intrinsics.checkNotNullParameter((Object)resourceLocation, (String)"<set-?>");
        this.pokemon = resourceLocation;
    }

    @NotNull
    public final List<String> getAspects() {
        return this.aspects;
    }

    public final void setAspects(@NotNull List<String> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.aspects = list;
    }

    /*
     * WARNING - void declaration
     */
    @SuppressWarnings("rawtypes")
    public void toJson(@NotNull JsonObject json) {
        JsonArray jsonArray;
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        JsonArray jsonArray2 = jsonArray = new JsonArray(this.aspects.size());
        String string = "aspects";
        JsonObject jsonObject = json;
        boolean bl = false;
        Iterable $this$forEach$iv = this.aspects;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            void it;
            String aspect = (String)element$iv;
            boolean bl2 = false;
            it.add(aspect);
        }
        Unit unit = Unit.INSTANCE;
        jsonObject.add(string, (JsonElement)jsonArray);
        json.addProperty("pokemon", this.pokemon.toString());
    }

    @SuppressWarnings({ "unused", "rawtypes" })
    public void fromJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        this.aspects.clear();
        JsonArray jsonArray = json.getAsJsonArray("aspects");
        Intrinsics.checkNotNullExpressionValue((Object)jsonArray, (String)"json.getAsJsonArray(\"aspects\")");
        Iterable $this$forEach$iv = (Iterable)jsonArray;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            JsonElement element = (JsonElement)element$iv;
            boolean bl = false;
            String string = element.getAsString();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"element.asString");
            this.aspects.add(string);
        }
        String string = json.get("pokemon").getAsString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"json.get(\"pokemon\").asString");
        this.pokemon = ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(string, null, 1, null);
    }

    @SuppressWarnings({ "rawtypes", "unchecked", "unused" })
    public boolean matches(@NotNull ServerPlayer player, @NotNull Map<ResourceLocation, Set<String>> context) {
        boolean bl;
        block3: {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter(context, (String)"context");
            Set<String> caughtAspects = context.getOrDefault(this.pokemon, new LinkedHashSet());
            Iterable $this$all$iv = this.aspects;
            boolean $i$f$all = false;
            if ($this$all$iv instanceof Collection && ((Collection)$this$all$iv).isEmpty()) {
                bl = true;
            } else {
                for (Object element$iv : $this$all$iv) {
                    String it = (String)element$iv;
                    boolean bl2 = false;
                    if (caughtAspects.contains(it)) continue;
                    bl = false;
                    break block3;
                }
                bl = true;
            }
        }
        return bl;
    }
}

