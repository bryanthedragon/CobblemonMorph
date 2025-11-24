/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player;

import com.google.gson.JsonObject;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\bf\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bJ\u0017\u0010\u0004\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u000f\u0010\u0007\u001a\u00020\u0006H&\u00a2\u0006\u0004\b\u0007\u0010\bJ\u000f\u0010\t\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/api/storage/player/PlayerDataExtension;", "", "Lcom/google/gson/JsonObject;", "json", "deserialize", "(Lcom/google/gson/JsonObject;)Lcom/cobblemon/mod/common/api/storage/player/PlayerDataExtension;", "", "name", "()Ljava/lang/String;", "serialize", "()Lcom/google/gson/JsonObject;", "Companion", "common"})
public interface PlayerDataExtension {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerDataExtension$Companion.$$INSTANCE;

    @NotNull
    public String name();

    @NotNull
    public JsonObject serialize();

    @NotNull
    public PlayerDataExtension deserialize(@NotNull JsonObject var1);

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/api/storage/player/PlayerDataExtension$Companion;", "", "", "NAME_KEY", "Ljava/lang/String;", "getNAME_KEY", "()Ljava/lang/String;", "<init>", "()V", "common"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;
        @NotNull
        private static final String NAME_KEY;

        private Companion() {
        }

        @NotNull
        public final String getNAME_KEY() {
            return NAME_KEY;
        }

        static {
            $$INSTANCE = new Companion();
            NAME_KEY = "name";
        }
    }
}

