/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerDataExtension;
import com.google.gson.JsonObject;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\t\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0017\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u000f\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u000f\u0010\t\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\t\u0010\nR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u000b\u001a\u0004\b\f\u0010\n\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/api/storage/player/DummyPlayerDataExtension;", "Lcom/cobblemon/mod/common/api/storage/player/PlayerDataExtension;", "Lcom/google/gson/JsonObject;", "json", "deserialize", "(Lcom/google/gson/JsonObject;)Lcom/cobblemon/mod/common/api/storage/player/PlayerDataExtension;", "", "name", "()Ljava/lang/String;", "serialize", "()Lcom/google/gson/JsonObject;", "Lcom/google/gson/JsonObject;", "getJson", "<init>", "(Lcom/google/gson/JsonObject;)V", "common"})
public final class DummyPlayerDataExtension
implements PlayerDataExtension {
    @NotNull
    private final JsonObject json;

    public DummyPlayerDataExtension(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        this.json = json;
    }

    @NotNull
    public final JsonObject getJson() {
        return this.json;
    }

    @Override
    @NotNull
    public String name() {
        String string = this.json.get(PlayerDataExtension.Companion.getNAME_KEY()).getAsString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this.json.get(PlayerData\u2026ension.NAME_KEY).asString");
        return string;
    }

    @Override
    @NotNull
    public JsonObject serialize() {
        return this.json;
    }

    @Override
    @NotNull
    public PlayerDataExtension deserialize(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        return new DummyPlayerDataExtension(json);
    }
}

