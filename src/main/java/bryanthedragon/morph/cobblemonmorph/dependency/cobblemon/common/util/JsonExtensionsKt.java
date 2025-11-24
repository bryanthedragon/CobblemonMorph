/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.JsonOps
 *  kotlin.Metadata
 *  kotlin.jvm.JvmName
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.nbt.NbtOps
 *  net.minecraft.nbt.Tag
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import java.util.Collection;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0010\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0004\n\u0002\b\u0003\u001a\u0011\u0010\u0002\u001a\u00020\u0001*\u00020\u0000\u00a2\u0006\u0004\b\u0002\u0010\u0003\u001a'\u0010\b\u001a\u0004\u0018\u00010\u0000*\u00020\u00042\u0012\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005\"\u00020\u0006\u00a2\u0006\u0004\b\b\u0010\t\u001a7\u0010\u000e\u001a\u00028\u0000\"\u000e\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u00028\u00000\n*\b\u0012\u0004\u0012\u00028\u00000\u00052\u0006\u0010\f\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\u0006\u00a2\u0006\u0004\b\u000e\u0010\u000f\u001a\u0011\u0010\u0011\u001a\u00020\u0010*\u00020\u0004\u00a2\u0006\u0004\b\u0011\u0010\u0012\u001a\u0011\u0010\u0013\u001a\u00020\u0010*\u00020\u0004\u00a2\u0006\u0004\b\u0013\u0010\u0012\u001a\u0019\u0010\u0018\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00000\u0014H\u0007\u00a2\u0006\u0004\b\u0016\u0010\u0017\u001a\u0019\u0010\u0018\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00100\u0014H\u0007\u00a2\u0006\u0004\b\u0019\u0010\u0017\u001a\u0019\u0010\u0018\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u001a0\u0014H\u0007\u00a2\u0006\u0004\b\u001b\u0010\u0017\u001a\u0019\u0010\u0018\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u0014H\u0007\u00a2\u0006\u0004\b\u001c\u0010\u0017\u00a8\u0006\u001d"}, d2={"Lcom/google/gson/JsonElement;", "Lnet/minecraft/nbt/Tag;", "asNbt", "(Lcom/google/gson/JsonElement;)Lnet/minecraft/nbt/Tag;", "Lcom/google/gson/JsonObject;", "", "", "names", "getFirst", "(Lcom/google/gson/JsonObject;[Ljava/lang/String;)Lcom/google/gson/JsonElement;", "", "T", "element", "name", "getFromJSON", "([Ljava/lang/Enum;Lcom/google/gson/JsonElement;Ljava/lang/String;)Ljava/lang/Enum;", "", "isEmpty", "(Lcom/google/gson/JsonObject;)Z", "isNotEmpty", "", "Lcom/google/gson/JsonArray;", "toJsonArrayJsonElement", "(Ljava/util/Collection;)Lcom/google/gson/JsonArray;", "toJsonArray", "toJsonArrayBoolean", "", "toJsonArrayNumber", "toJsonArrayString", "common"})
@SourceDebugExtension(value={"SMAP\nJsonExtensions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JsonExtensions.kt\ncom/cobblemon/mod/common/util/JsonExtensionsKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,73:1\n1855#2,2:74\n1855#2,2:76\n1855#2,2:78\n1855#2,2:80\n1109#3,2:82\n*S KotlinDebug\n*F\n+ 1 JsonExtensions.kt\ncom/cobblemon/mod/common/util/JsonExtensionsKt\n*L\n23#1:74,2\n32#1:76,2\n41#1:78,2\n50#1:80,2\n62#1:82,2\n*E\n"})
public final class JsonExtensionsKt {
    @JvmName(name="toJsonArrayString")
    @NotNull
    public static final JsonArray toJsonArrayString(@NotNull Collection<String> $this$toJsonArray) {
        Intrinsics.checkNotNullParameter($this$toJsonArray, (String)"<this>");
        JsonArray array = new JsonArray();
        if ($this$toJsonArray.isEmpty()) {
            return array;
        }
        Iterable $this$forEach$iv = $this$toJsonArray;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            String it = (String)element$iv;
            boolean bl = false;
            array.add(it);
        }
        return array;
    }

    @JvmName(name="toJsonArrayBoolean")
    @NotNull
    public static final JsonArray toJsonArrayBoolean(@NotNull Collection<Boolean> $this$toJsonArray) {
        Intrinsics.checkNotNullParameter($this$toJsonArray, (String)"<this>");
        JsonArray array = new JsonArray();
        if ($this$toJsonArray.isEmpty()) {
            return array;
        }
        Iterable $this$forEach$iv = $this$toJsonArray;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            boolean it = (Boolean)element$iv;
            boolean bl = false;
            array.add(Boolean.valueOf(it));
        }
        return array;
    }

    @JvmName(name="toJsonArrayNumber")
    @NotNull
    public static final JsonArray toJsonArrayNumber(@NotNull Collection<? extends Number> $this$toJsonArray) {
        Intrinsics.checkNotNullParameter($this$toJsonArray, (String)"<this>");
        JsonArray array = new JsonArray();
        if ($this$toJsonArray.isEmpty()) {
            return array;
        }
        Iterable $this$forEach$iv = $this$toJsonArray;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Number it = (Number)element$iv;
            boolean bl = false;
            array.add(it);
        }
        return array;
    }

    @JvmName(name="toJsonArrayJsonElement")
    @NotNull
    public static final JsonArray toJsonArrayJsonElement(@NotNull Collection<? extends JsonElement> $this$toJsonArray) {
        Intrinsics.checkNotNullParameter($this$toJsonArray, (String)"<this>");
        JsonArray array = new JsonArray();
        if ($this$toJsonArray.isEmpty()) {
            return array;
        }
        Iterable $this$forEach$iv = $this$toJsonArray;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            JsonElement it = (JsonElement)element$iv;
            boolean bl = false;
            array.add(it);
        }
        return array;
    }

    public static final boolean isEmpty(@NotNull JsonObject $this$isEmpty) {
        Intrinsics.checkNotNullParameter((Object)$this$isEmpty, (String)"<this>");
        return $this$isEmpty.size() <= 0;
    }

    public static final boolean isNotEmpty(@NotNull JsonObject $this$isNotEmpty) {
        Intrinsics.checkNotNullParameter((Object)$this$isNotEmpty, (String)"<this>");
        return $this$isNotEmpty.size() > 0;
    }

    @NotNull
    public static final Tag asNbt(@NotNull JsonElement $this$asNbt) {
        Intrinsics.checkNotNullParameter((Object)$this$asNbt, (String)"<this>");
        Object object = JsonOps.INSTANCE.convertTo((DynamicOps)NbtOps.f_128958_, $this$asNbt);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"INSTANCE.convertTo(NbtOps.INSTANCE, this)");
        return (Tag)object;
    }

    @NotNull
    public static final <T extends Enum<T>> T getFromJSON(@NotNull T[] $this$getFromJSON, @NotNull JsonElement element, @NotNull String name) {
        T element$iv;
        block2: {
            Intrinsics.checkNotNullParameter($this$getFromJSON, (String)"<this>");
            Intrinsics.checkNotNullParameter((Object)element, (String)"element");
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            String type = ((JsonObject)element).get(name).getAsString();
            T[] $this$first$iv = $this$getFromJSON;
            boolean $i$f$first = false;
            int n = $this$first$iv.length;
            for (int i = 0; i < n; ++i) {
                T it = element$iv = $this$first$iv[i];
                boolean bl = false;
                if (!StringsKt.equals((String)type, (String)((Enum)it).name(), (boolean)true)) {
                    continue;
                }
                break block2;
            }
            throw new NoSuchElementException("Array contains no element matching the predicate.");
        }
        return element$iv;
    }

    @Nullable
    public static final JsonElement getFirst(@NotNull JsonObject $this$getFirst, String ... names) {
        Intrinsics.checkNotNullParameter((Object)$this$getFirst, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)names, (String)"names");
        for (String name : names) {
            JsonElement element = $this$getFirst.get(name);
            if (element == null) continue;
            return element;
        }
        return null;
    }
}

