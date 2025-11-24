/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.mojang.datafixers.util.Either
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  net.minecraft.core.Registry
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.TagKey
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Either;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u000e\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u00028\u00000\u00022\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00070\u00050\u0004B\u0015\u0012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00028\u00010\u0010\u00a2\u0006\u0004\b\u0015\u0010\u0016J9\u0010\u000e\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00070\u00052\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fR\u001d\u0010\u0011\u001a\b\u0012\u0004\u0012\u00028\u00010\u00108\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/util/adapters/EitherIdentifierOrTagAdapter;", "E", "Lnet/minecraft/core/Registry;", "T", "Lcom/google/gson/JsonDeserializer;", "Lcom/mojang/datafixers/util/Either;", "Lnet/minecraft/resources/ResourceLocation;", "Lnet/minecraft/tags/TagKey;", "Lcom/google/gson/JsonElement;", "element", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/mojang/datafixers/util/Either;", "Lnet/minecraft/resources/ResourceKey;", "registryKey", "Lnet/minecraft/resources/ResourceKey;", "getRegistryKey", "()Lnet/minecraft/resources/ResourceKey;", "<init>", "(Lnet/minecraft/resources/ResourceKey;)V", "common"})
public final class EitherIdentifierOrTagAdapter<E, T extends Registry<E>>
implements JsonDeserializer<Either<ResourceLocation, TagKey<E>>> {
    @NotNull
    private final ResourceKey<T> registryKey;

    public EitherIdentifierOrTagAdapter(@NotNull ResourceKey<T> registryKey) {
        Intrinsics.checkNotNullParameter(registryKey, (String)"registryKey");
        this.registryKey = registryKey;
    }

    @NotNull
    public final ResourceKey<T> getRegistryKey() {
        return this.registryKey;
    }

    @NotNull
    public Either<ResourceLocation, TagKey<E>> deserialize(@NotNull JsonElement element, @NotNull Type type, @NotNull JsonDeserializationContext ctx) {
        Either either;
        Intrinsics.checkNotNullParameter((Object)element, (String)"element");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        String string = element.getAsString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"string");
        if (StringsKt.startsWith$default((String)string, (String)"#", (boolean)false, (int)2, null)) {
            String string2 = string.substring(1);
            Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"this as java.lang.String).substring(startIndex)");
            Either either2 = Either.right((Object)TagKey.m_203882_(this.registryKey, (ResourceLocation)new ResourceLocation(string2)));
            Intrinsics.checkNotNullExpressionValue((Object)either2, (String)"{\n            Either.rig\u2026substring(1))))\n        }");
            either = either2;
        } else {
            Either either3 = Either.left((Object)new ResourceLocation(string));
            either = either3;
            Intrinsics.checkNotNullExpressionValue((Object)either3, (String)"{\n            Either.lef\u2026tifier(string))\n        }");
        }
        return either;
    }
}

