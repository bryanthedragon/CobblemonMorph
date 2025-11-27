/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.nbt.CompoundTag
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Ability;

import com.google.gson.JsonObject;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

import net.minecraft.nbt.CompoundTag;

import org.jetbrains.annotations.NotNull;

public final class AbilityTemplate {
    @NotNull
    private final String name;
    @NotNull
    private Function2<? super AbilityTemplate, ? super Boolean, ? extends Ability> builder;
    @NotNull
    private final String displayName;
    @NotNull
    private final String description;

    public AbilityTemplate(@NotNull String name, @NotNull Function2<? super AbilityTemplate, ? super Boolean, ? extends Ability> builder, @NotNull String displayName, @NotNull String description) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter(builder, (String)"builder");
        Intrinsics.checkNotNullParameter((Object)displayName, (String)"displayName");
        Intrinsics.checkNotNullParameter((Object)description, (String)"description");
        this.name = name;
        this.builder = builder;
        this.displayName = displayName;
        this.description = description;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public /* synthetic */ AbilityTemplate(String string, Function2 function2, String object, String object2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        this(string, (Function2<? super AbilityTemplate, ? super Boolean, ? extends Ability>)function2, (String)object, (String)object2);
        if ((n & 1) != 0) {
            string = "";
        }
        if ((n & 2) != 0) {
            function2 = (abilityTemplate, forced) -> null;
        }
        if ((n & 4) != 0) {
            object = "cobblemon.ability." + string;
        }
        if ((n & 8) != 0) {
            object2 = "cobblemon.ability." + string + ".desc";
        }
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final Function2<? super AbilityTemplate, ? super Boolean, ? extends Ability> getBuilder() {
        return this.builder;
    }

    public final void setBuilder(@NotNull Function2<? super AbilityTemplate, ? super Boolean, ? extends Ability> function2) {
        Intrinsics.checkNotNullParameter(function2, (String)"<set-?>");
        this.builder = function2;
    }

    @NotNull
    public final String getDisplayName() {
        return this.displayName;
    }

    @NotNull
    public final String getDescription() {
        return this.description;
    }

    @NotNull
    public final Ability create(boolean forced) {
        return (Ability)this.builder.invoke((Object)this, (Object)forced);
    }

    public static /* synthetic */ Ability create$default(AbilityTemplate abilityTemplate, boolean bl, int n, Object object) {
        if ((n & 1) != 0) {
            bl = false;
        }
        return abilityTemplate.create(bl);
    }

    @NotNull
    public final Ability create(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        return AbilityTemplate.create$default(this, false, 1, null).loadFromNBT(nbt);
    }

    @NotNull
    public final Ability create(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        return AbilityTemplate.create$default(this, false, 1, null).loadFromJSON(json);
    }

    public AbilityTemplate() {
        this(null, null, null, null, 15, null);
    }
}

