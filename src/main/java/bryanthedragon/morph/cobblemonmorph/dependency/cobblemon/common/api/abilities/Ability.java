/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.nbt.CompoundTag
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Abilities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityTemplate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import kotlin.jvm.internal.Intrinsics;

import net.minecraft.nbt.CompoundTag;

import org.jetbrains.annotations.NotNull;

public class Ability {
    @NotNull
    private AbilityTemplate template;
    private boolean forced;
    private int index;
    @NotNull
    private Priority priority;

    public Ability(@NotNull AbilityTemplate template, boolean forced) {
        Intrinsics.checkNotNullParameter((Object)template, (String)"template");
        this.template = template;
        this.forced = forced;
        this.index = -1;
        this.priority = Priority.LOWEST;
    }

    @NotNull
    public final AbilityTemplate getTemplate() {
        return this.template;
    }

    public final void setTemplate(@NotNull AbilityTemplate abilityTemplate) {
        Intrinsics.checkNotNullParameter((Object)abilityTemplate, (String)"<set-?>");
        this.template = abilityTemplate;
    }

    @NotNull
    public final String getName() {
        return this.template.getName();
    }

    @NotNull
    public final String getDisplayName() {
        return this.template.getDisplayName();
    }

    @NotNull
    public final String getDescription() {
        return this.template.getDescription();
    }

    public final boolean getForced() {
        return this.forced;
    }

    public final void setForced$common(boolean bl) {
        this.forced = bl;
    }

    public final int getIndex() {
        return this.index;
    }

    public final void setIndex$common(int n) {
        this.index = n;
    }

    @NotNull
    public final Priority getPriority() {
        return this.priority;
    }

    public final void setPriority$common(@NotNull Priority priority) {
        Intrinsics.checkNotNullParameter((Object)((Object)priority), (String)"<set-?>");
        this.priority = priority;
    }

    @NotNull
    public CompoundTag saveToNBT(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        nbt.putString("AbilityName", this.getName());
        nbt.putBoolean("AbilityForced", this.forced);
        nbt.putInt("AbilityIndex", this.index);
        nbt.putString("AbilityPriority", this.priority.name());
        return nbt;
    }

    @NotNull
    public JsonObject saveToJSON(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        json.addProperty("AbilityName", this.getName());
        json.addProperty("AbilityForced", Boolean.valueOf(this.forced));
        json.addProperty("AbilityIndex", (Number)this.index);
        json.addProperty("AbilityPriority", this.priority.name());
        return json;
    }

    @NotNull
    public Ability loadFromNBT(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        String string = nbt.getString("AbilityName");
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"nbt.getString(DataKeys.POKEMON_ABILITY_NAME)");
        this.template = Abilities.INSTANCE.getOrException(string);
        this.forced = nbt.getBoolean("AbilityForced");
        if (nbt.contains("AbilityIndex") && nbt.contains("AbilityPriority")) {
            this.index = nbt.getInt("AbilityIndex");
            String string2 = nbt.getString("AbilityPriority");
            Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"nbt.getString(DataKeys.POKEMON_ABILITY_PRIORITY)");
            this.priority = Priority.valueOf(string2);
        }
        return this;
    }

    @NotNull
    @SuppressWarnings("unused")
    public Ability loadFromJSON(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        String string = json.get("AbilityName").getAsString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"json.get(DataKeys.POKEMON_ABILITY_NAME).asString");
        this.template = Abilities.INSTANCE.getOrException(string);
        JsonElement jsonElement = json.get("AbilityForced");
        boolean bl = this.forced = jsonElement != null ? jsonElement.getAsBoolean() : false;
        if (json.has("AbilityIndex") && json.has("AbilityPriority")) {
            this.index = json.get("AbilityIndex").getAsInt();
            String string2 = json.get("AbilityPriority").getAsString();
            Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"json.get(DataKeys.POKEMO\u2026BILITY_PRIORITY).asString");
            this.priority = Priority.valueOf(string2);
        }
        return this;
    }
}

