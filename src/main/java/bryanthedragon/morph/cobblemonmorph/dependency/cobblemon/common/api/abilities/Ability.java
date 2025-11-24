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
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\b\u0016\u0018\u00002\u00020\u0001B\u0019\b\u0000\u0012\u0006\u0010-\u001a\u00020,\u0012\u0006\u0010\u0016\u001a\u00020\u0014\u00a2\u0006\u0004\b3\u00104J\u0017\u0010\u0004\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0017\u0010\b\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\n\u001a\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0017\u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\f\u0010\rR\u0011\u0010\u0011\u001a\u00020\u000e8F\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0013\u001a\u00020\u000e8F\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0010R*\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00148\u0006@@X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR*\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u0015\u001a\u00020\u001c8\u0006@@X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u0011\u0010$\u001a\u00020\u000e8F\u00a2\u0006\u0006\u001a\u0004\b#\u0010\u0010R*\u0010&\u001a\u00020%2\u0006\u0010\u0015\u001a\u00020%8\u0006@@X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b&\u0010'\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\"\u0010-\u001a\u00020,8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b-\u0010.\u001a\u0004\b/\u00100\"\u0004\b1\u00102\u00a8\u00065"}, d2={"Lcom/cobblemon/mod/common/api/abilities/Ability;", "", "Lcom/google/gson/JsonObject;", "json", "loadFromJSON", "(Lcom/google/gson/JsonObject;)Lcom/cobblemon/mod/common/api/abilities/Ability;", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "loadFromNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/api/abilities/Ability;", "saveToJSON", "(Lcom/google/gson/JsonObject;)Lcom/google/gson/JsonObject;", "saveToNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/nbt/CompoundTag;", "", "getDescription", "()Ljava/lang/String;", "description", "getDisplayName", "displayName", "", "<set-?>", "forced", "Z", "getForced", "()Z", "setForced$common", "(Z)V", "", "index", "I", "getIndex", "()I", "setIndex$common", "(I)V", "getName", "name", "Lcom/cobblemon/mod/common/api/Priority;", "priority", "Lcom/cobblemon/mod/common/api/Priority;", "getPriority", "()Lcom/cobblemon/mod/common/api/Priority;", "setPriority$common", "(Lcom/cobblemon/mod/common/api/Priority;)V", "Lcom/cobblemon/mod/common/api/abilities/AbilityTemplate;", "template", "Lcom/cobblemon/mod/common/api/abilities/AbilityTemplate;", "getTemplate", "()Lcom/cobblemon/mod/common/api/abilities/AbilityTemplate;", "setTemplate", "(Lcom/cobblemon/mod/common/api/abilities/AbilityTemplate;)V", "<init>", "(Lcom/cobblemon/mod/common/api/abilities/AbilityTemplate;Z)V", "common"})
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
        nbt.m_128359_("AbilityName", this.getName());
        nbt.m_128379_("AbilityForced", this.forced);
        nbt.m_128405_("AbilityIndex", this.index);
        nbt.m_128359_("AbilityPriority", this.priority.name());
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
        String string = nbt.m_128461_("AbilityName");
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"nbt.getString(DataKeys.POKEMON_ABILITY_NAME)");
        this.template = Abilities.INSTANCE.getOrException(string);
        this.forced = nbt.m_128471_("AbilityForced");
        if (nbt.m_128441_("AbilityIndex") && nbt.m_128441_("AbilityPriority")) {
            this.index = nbt.m_128451_("AbilityIndex");
            String string2 = nbt.m_128461_("AbilityPriority");
            Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"nbt.getString(DataKeys.POKEMON_ABILITY_PRIORITY)");
            this.priority = Priority.valueOf(string2);
        }
        return this;
    }

    @NotNull
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

