/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.ExclusionStrategy
 *  com.google.gson.FieldAttributes
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.reflect.TypeToken
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.InBattleGimmickMove;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownMoveset;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0013B\t\b\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012J'\u0010\t\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\nR\u001f\u0010\r\u001a\n \f*\u0004\u0018\u00010\u000b0\u000b8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/battles/ShowdownMovesetAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/battles/ShowdownMoveset;", "Lcom/google/gson/JsonElement;", "jsonElement", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "context", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/battles/ShowdownMoveset;", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "gson", "Lcom/google/gson/Gson;", "getGson", "()Lcom/google/gson/Gson;", "<init>", "()V", "MovesetExclusionStrategy", "common"})
public final class ShowdownMovesetAdapter
implements JsonDeserializer<ShowdownMoveset> {
    @NotNull
    public static final ShowdownMovesetAdapter INSTANCE = new ShowdownMovesetAdapter();
    private static final Gson gson = new GsonBuilder().addDeserializationExclusionStrategy((ExclusionStrategy)MovesetExclusionStrategy.INSTANCE).create();

    private ShowdownMovesetAdapter() {
    }

    public final Gson getGson() {
        return gson;
    }

    @NotNull
    public ShowdownMoveset deserialize(@NotNull JsonElement jsonElement, @NotNull Type type, @NotNull JsonDeserializationContext context) {
        Intrinsics.checkNotNullParameter((Object)jsonElement, (String)"jsonElement");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        JsonObject json = jsonElement.getAsJsonObject();
        ShowdownMoveset moveset = (ShowdownMoveset)gson.fromJson((JsonElement)json, ShowdownMoveset.class);
        JsonElement jsonElement2 = json.get("maxMoves");
        if (jsonElement2 != null && (jsonElement2 = jsonElement2.getAsJsonObject()) != null) {
            JsonElement dynamaxOptions = jsonElement2;
            boolean bl = false;
            Type maxMovesToken2 = new TypeToken<List<? extends InBattleGimmickMove>>(){}.getType();
            moveset.setMaxMoves((List)new Gson().fromJson(dynamaxOptions.get("maxMoves"), maxMovesToken2));
        }
        moveset.setGimmickMapping();
        Intrinsics.checkNotNullExpressionValue((Object)moveset, (String)"moveset");
        return moveset;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ\u001d\u0010\u0005\u001a\u00020\u00042\f\u0010\u0003\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0019\u0010\t\u001a\u00020\u00042\b\u0010\b\u001a\u0004\u0018\u00010\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/battles/ShowdownMovesetAdapter$MovesetExclusionStrategy;", "Lcom/google/gson/ExclusionStrategy;", "Ljava/lang/Class;", "p0", "", "shouldSkipClass", "(Ljava/lang/Class;)Z", "Lcom/google/gson/FieldAttributes;", "field", "shouldSkipField", "(Lcom/google/gson/FieldAttributes;)Z", "<init>", "()V", "common"})
    public static final class MovesetExclusionStrategy
    implements ExclusionStrategy {
        @NotNull
        public static final MovesetExclusionStrategy INSTANCE = new MovesetExclusionStrategy();

        private MovesetExclusionStrategy() {
        }

        public boolean shouldSkipField(@Nullable FieldAttributes field) {
            FieldAttributes fieldAttributes = field;
            return Intrinsics.areEqual((Object)(fieldAttributes != null ? fieldAttributes.getName() : null), (Object)"maxMoves");
        }

        public boolean shouldSkipClass(@Nullable Class<?> p0) {
            return false;
        }
    }
}

