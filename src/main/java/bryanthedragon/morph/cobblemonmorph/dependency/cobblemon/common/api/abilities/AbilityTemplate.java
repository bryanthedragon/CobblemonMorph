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
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\n\u0018\u00002\u00020\u0001BP\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0016\u0012)\b\u0002\u0010\u0010\u001a#\u0012\u0004\u0012\u00020\u0000\u0012\u0013\u0012\u00110\u0007\u00a2\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\u00040\r\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u0016\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0016\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\u0005\u0010\tJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\u0005\u0010\fRC\u0010\u0010\u001a#\u0012\u0004\u0012\u00020\u0000\u0012\u0013\u0012\u00110\u0007\u00a2\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\u00040\r8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0017\u001a\u00020\u00168\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001aR\u0017\u0010\u001b\u001a\u00020\u00168\u0006\u00a2\u0006\f\n\u0004\b\u001b\u0010\u0018\u001a\u0004\b\u001c\u0010\u001aR\u0017\u0010\u000f\u001a\u00020\u00168\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0018\u001a\u0004\b\u001d\u0010\u001a\u00a8\u0006 "}, d2={"Lcom/cobblemon/mod/common/api/abilities/AbilityTemplate;", "", "Lcom/google/gson/JsonObject;", "json", "Lcom/cobblemon/mod/common/api/abilities/Ability;", "create", "(Lcom/google/gson/JsonObject;)Lcom/cobblemon/mod/common/api/abilities/Ability;", "", "forced", "(Z)Lcom/cobblemon/mod/common/api/abilities/Ability;", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "(Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/api/abilities/Ability;", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "builder", "Lkotlin/jvm/functions/Function2;", "getBuilder", "()Lkotlin/jvm/functions/Function2;", "setBuilder", "(Lkotlin/jvm/functions/Function2;)V", "", "description", "Ljava/lang/String;", "getDescription", "()Ljava/lang/String;", "displayName", "getDisplayName", "getName", "<init>", "(Ljava/lang/String;Lkotlin/jvm/functions/Function2;Ljava/lang/String;Ljava/lang/String;)V", "common"})
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

    public /* synthetic */ AbilityTemplate(String string, Function2 function2, String object, String object2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            string = "";
        }
        if ((n & 2) != 0) {
            function2 = 1.INSTANCE;
        }
        if ((n & 4) != 0) {
            object = "cobblemon.ability." + string;
        }
        if ((n & 8) != 0) {
            object2 = "cobblemon.ability." + string + ".desc";
        }
        this(string, (Function2<? super AbilityTemplate, ? super Boolean, ? extends Ability>)function2, (String)object, (String)object2);
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final Function2<AbilityTemplate, Boolean, Ability> getBuilder() {
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

