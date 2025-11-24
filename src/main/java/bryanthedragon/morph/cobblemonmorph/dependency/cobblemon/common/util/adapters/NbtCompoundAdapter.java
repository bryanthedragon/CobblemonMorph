/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonPrimitive
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtUtils
 *  net.minecraft.nbt.SnbtPrinterTagVisitor
 *  net.minecraft.nbt.Tag
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.SnbtPrinterTagVisitor;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0012\u0004\u0012\u00020\u00020\u0003B\t\b\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012J/\u0010\u000b\u001a\n \n*\u0004\u0018\u00010\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\u000f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u000eH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/util/adapters/NbtCompoundAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lnet/minecraft/nbt/CompoundTag;", "Lcom/google/gson/JsonSerializer;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "kotlin.jvm.PlatformType", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/nbt/CompoundTag;", "nbt", "Lcom/google/gson/JsonSerializationContext;", "serialize", "(Lnet/minecraft/nbt/CompoundTag;Ljava/lang/reflect/Type;Lcom/google/gson/JsonSerializationContext;)Lcom/google/gson/JsonElement;", "<init>", "()V", "common"})
public final class NbtCompoundAdapter
implements JsonDeserializer<CompoundTag>,
JsonSerializer<CompoundTag> {
    @NotNull
    public static final NbtCompoundAdapter INSTANCE = new NbtCompoundAdapter();

    private NbtCompoundAdapter() {
    }

    public CompoundTag deserialize(@NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext ctx) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        return NbtUtils.m_178024_((String)json.getAsString());
    }

    @NotNull
    public JsonElement serialize(@NotNull CompoundTag nbt, @NotNull Type type, @NotNull JsonSerializationContext ctx) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        return (JsonElement)new JsonPrimitive(new SnbtPrinterTagVisitor("", 0, (List)new ArrayList()).m_178141_((Tag)nbt));
    }
}

