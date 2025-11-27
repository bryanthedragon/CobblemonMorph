/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.Reader;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a,\u0010\u0005\u001a\n \u0004*\u0004\u0018\u00018\u00008\u0000\"\u0006\b\u0000\u0010\u0000\u0018\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0002H\u0086\b\u00a2\u0006\u0004\b\u0005\u0010\u0006\u001a,\u0010\u0005\u001a\n \u0004*\u0004\u0018\u00018\u00008\u0000\"\u0006\b\u0000\u0010\u0000\u0018\u0001*\u00020\u00012\u0006\u0010\b\u001a\u00020\u0007H\u0086\b\u00a2\u0006\u0004\b\u0005\u0010\t\u001a,\u0010\u0005\u001a\n \u0004*\u0004\u0018\u00018\u00008\u0000\"\u0006\b\u0000\u0010\u0000\u0018\u0001*\u00020\u00012\u0006\u0010\u000b\u001a\u00020\nH\u0086\b\u00a2\u0006\u0004\b\u0005\u0010\f\u001a\u0011\u0010\u000e\u001a\u00020\r*\u00020\u0002\u00a2\u0006\u0004\b\u000e\u0010\u000f\u001a#\u0010\u0013\u001a\u00020\u0010*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\n2\b\b\u0002\u0010\u0012\u001a\u00020\n\u00a2\u0006\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0015"}, d2={"T", "Lcom/google/gson/Gson;", "Lcom/google/gson/JsonElement;", "element", "kotlin.jvm.PlatformType", "fromJson", "(Lcom/google/gson/Gson;Lcom/google/gson/JsonElement;)Ljava/lang/Object;", "Ljava/io/Reader;", "reader", "(Lcom/google/gson/Gson;Ljava/io/Reader;)Ljava/lang/Object;", "", "string", "(Lcom/google/gson/Gson;Ljava/lang/String;)Ljava/lang/Object;", "Lcom/google/gson/JsonArray;", "normalizeToArray", "(Lcom/google/gson/JsonElement;)Lcom/google/gson/JsonArray;", "Lcom/google/gson/JsonObject;", "rootName", "pluralName", "singularToPluralList", "(Lcom/google/gson/JsonObject;Ljava/lang/String;Ljava/lang/String;)Lcom/google/gson/JsonObject;", "common"})
public final class GsonExtensionsKt {
    @SuppressWarnings({ "unchecked", "unused" })
    public static final /* synthetic */ <T> T fromJson(Gson $this$fromJson, Reader reader) {
        Intrinsics.checkNotNullParameter((Object)$this$fromJson, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)reader, (String)"reader");
        boolean $i$f$fromJson = false;
        Intrinsics.reifiedOperationMarker((int)4, (String)"T");
        return (T)$this$fromJson.fromJson(reader, Object.class);
    }

    @SuppressWarnings({ "unused", "unchecked" })
    public static final /* synthetic */ <T> T fromJson(Gson $this$fromJson, JsonElement element) {
        Intrinsics.checkNotNullParameter((Object)$this$fromJson, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)element, (String)"element");
        boolean $i$f$fromJson = false;
        Intrinsics.reifiedOperationMarker((int)4, (String)"T");
        return (T)$this$fromJson.fromJson(element, Object.class);
    }

    @SuppressWarnings({ "unchecked", "unused" })
    public static final /* synthetic */ <T> T fromJson(Gson $this$fromJson, String string) {
        Intrinsics.checkNotNullParameter((Object)$this$fromJson, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)string, (String)"string");
        boolean $i$f$fromJson = false;
        Intrinsics.reifiedOperationMarker((int)4, (String)"T");
        return (T)$this$fromJson.fromJson(string, Object.class);
    }

    @NotNull
    public static final JsonObject singularToPluralList(@NotNull JsonObject $this$singularToPluralList, @NotNull String rootName, @NotNull String pluralName) {
        Intrinsics.checkNotNullParameter((Object)$this$singularToPluralList, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)rootName, (String)"rootName");
        Intrinsics.checkNotNullParameter((Object)pluralName, (String)"pluralName");
        if ($this$singularToPluralList.has(rootName)) {
            if (!$this$singularToPluralList.has(pluralName)) {
                $this$singularToPluralList.add(pluralName, (JsonElement)new JsonArray());
            }
            $this$singularToPluralList.get(pluralName).getAsJsonArray().add($this$singularToPluralList.get(rootName));
            $this$singularToPluralList.remove(rootName);
        }
        return $this$singularToPluralList;
    }

    public static /* synthetic */ JsonObject singularToPluralList$default(JsonObject jsonObject, String string, String object, int n, Object object2) {
        if ((n & 2) != 0) {
            object = string + "s";
        }
        return GsonExtensionsKt.singularToPluralList(jsonObject, string, (String)object);
    }

    @NotNull
    public static final JsonArray normalizeToArray(@NotNull JsonElement $this$normalizeToArray) {
        Intrinsics.checkNotNullParameter((Object)$this$normalizeToArray, (String)"<this>");
        if ($this$normalizeToArray instanceof JsonArray) {
            return (JsonArray)$this$normalizeToArray;
        }
        JsonArray array = new JsonArray();
        array.add($this$normalizeToArray);
        return array;
    }
}

