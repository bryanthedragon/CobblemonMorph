/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import java.lang.reflect.Type;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00030\u0002J-\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bR.\u0010\u0010\u001a\u001c\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\u0004\u0012\f\u0012\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00030\r0\f8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/api/conditional/RegistryLikeAdapter;", "B", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/api/conditional/RegistryLikeCondition;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/api/conditional/RegistryLikeCondition;", "", "Lkotlin/Function1;", "getRegistryLikeConditions", "()Ljava/util/List;", "registryLikeConditions", "common"})
public interface RegistryLikeAdapter<B>
extends JsonDeserializer<RegistryLikeCondition<B>> {
    @NotNull
    public List<Function1<JsonElement, RegistryLikeCondition<B>>> getRegistryLikeConditions();

    @NotNull
    public RegistryLikeCondition<B> deserialize(@NotNull JsonElement var1, @NotNull Type var2, @NotNull JsonDeserializationContext var3);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    @SourceDebugExtension(value={"SMAP\nRegistryLikeAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RegistryLikeAdapter.kt\ncom/cobblemon/mod/common/api/conditional/RegistryLikeAdapter$DefaultImpls\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,31:1\n1#2:32\n*E\n"})
    public static final class DefaultImpls {
        @NotNull
        public static <B> RegistryLikeCondition<B> deserialize(@NotNull RegistryLikeAdapter<B> $this, @NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext ctx) {
            RegistryLikeCondition registryLikeCondition;
            block2: {
                Intrinsics.checkNotNullParameter((Object)json, (String)"json");
                Intrinsics.checkNotNullParameter((Object)type, (String)"type");
                Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
                for (Function1 it : (Iterable)$this.getRegistryLikeConditions()) {
                    boolean bl = false;
                    RegistryLikeCondition registryLikeCondition2 = (RegistryLikeCondition)it.invoke((Object)json);
                    if (registryLikeCondition2 == null) continue;
                    registryLikeCondition = registryLikeCondition2;
                    break block2;
                }
                registryLikeCondition = null;
            }
            if (registryLikeCondition == null) {
                throw new IllegalArgumentException("Unable to deserialize " + json);
            }
            return registryLikeCondition;
        }
    }
}

