/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.reflect.TypeToken
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.packs.PackType
 *  net.minecraft.server.packs.resources.ResourceManager
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.Dialogue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueFaceProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialoguePredicate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueText;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueInput;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.DialogueActionAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.DialogueFaceProviderAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.DialogueInputAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.DialoguePredicateAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.DialogueTextAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ExpressionAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ExpressionLikeAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.TextAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b3\u00104J#\u0010\u0007\u001a\u00020\u00062\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\u000b\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fR#\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00020\r8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\"\u0010\u0014\u001a\n \u0013*\u0004\u0018\u00010\u00120\u00128\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u001d\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00190\u00188\u0006\u00a2\u0006\f\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001dR\u001a\u0010\u001e\u001a\u00020\u00048\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!R \u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00000\u00188\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\"\u0010\u001b\u001a\u0004\b#\u0010\u001dR\u001a\u0010%\u001a\u00020$8\u0016X\u0096D\u00a2\u0006\f\n\u0004\b%\u0010&\u001a\u0004\b'\u0010(R\u001a\u0010*\u001a\u00020)8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b*\u0010+\u001a\u0004\b,\u0010-R>\u0010/\u001a&\u0012\f\u0012\n \u0013*\u0004\u0018\u00010\u00020\u0002 \u0013*\u0012\u0012\f\u0012\n \u0013*\u0004\u0018\u00010\u00020\u0002\u0018\u00010.0.8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b/\u00100\u001a\u0004\b1\u00102\u00a8\u00065"}, d2={"Lcom/cobblemon/mod/common/api/dialogue/Dialogues;", "Lcom/cobblemon/mod/common/api/data/JsonDataRegistry;", "Lcom/cobblemon/mod/common/api/dialogue/Dialogue;", "", "Lnet/minecraft/resources/ResourceLocation;", "data", "", "reload", "(Ljava/util/Map;)V", "Lnet/minecraft/server/level/ServerPlayer;", "player", "sync", "(Lnet/minecraft/server/level/ServerPlayer;)V", "", "dialogues", "Ljava/util/Map;", "getDialogues", "()Ljava/util/Map;", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "gson", "Lcom/google/gson/Gson;", "getGson", "()Lcom/google/gson/Gson;", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "Lcom/google/gson/GsonBuilder;", "gsonObservable", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getGsonObservable", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "observable", "getObservable", "", "resourcePath", "Ljava/lang/String;", "getResourcePath", "()Ljava/lang/String;", "Lnet/minecraft/server/packs/PackType;", "type", "Lnet/minecraft/server/packs/PackType;", "getType", "()Lnet/minecraft/server/packs/PackType;", "Lcom/google/gson/reflect/TypeToken;", "typeToken", "Lcom/google/gson/reflect/TypeToken;", "getTypeToken", "()Lcom/google/gson/reflect/TypeToken;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nDialogues.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Dialogues.kt\ncom/cobblemon/mod/common/api/dialogue/Dialogues\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,72:1\n1#2:73\n*E\n"})
public final class Dialogues
implements JsonDataRegistry<Dialogue> {
    @NotNull
    public static final Dialogues INSTANCE;
    @NotNull
    private static final ResourceLocation id;
    @NotNull
    private static final PackType type;
    @NotNull
    private static final SimpleObservable<Dialogues> observable;
    @NotNull
    private static final Map<ResourceLocation, Dialogue> dialogues;
    @NotNull
    private static final SimpleObservable<GsonBuilder> gsonObservable;
    private static final Gson gson;
    private static final TypeToken<Dialogue> typeToken;
    @NotNull
    private static final String resourcePath;

    private Dialogues() {
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return id;
    }

    @Override
    @NotNull
    public PackType getType() {
        return type;
    }

    @NotNull
    public SimpleObservable<Dialogues> getObservable() {
        return observable;
    }

    @NotNull
    public final Map<ResourceLocation, Dialogue> getDialogues() {
        return dialogues;
    }

    @NotNull
    public final SimpleObservable<GsonBuilder> getGsonObservable() {
        return gsonObservable;
    }

    @Override
    public void sync(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
    }

    @Override
    public Gson getGson() {
        return gson;
    }

    @Override
    public TypeToken<Dialogue> getTypeToken() {
        return typeToken;
    }

    @Override
    @NotNull
    public String getResourcePath() {
        return resourcePath;
    }

    @Override
    public void reload(@NotNull Map<ResourceLocation, Dialogue> data) {
        Intrinsics.checkNotNullParameter(data, (String)"data");
        dialogues.putAll(data);
        Dialogues[] dialoguesArray = new Dialogues[]{this};
        this.getObservable().emit((Dialogues[])dialoguesArray);
    }

    @Override
    public void reload(@NotNull ResourceManager manager) {
        JsonDataRegistry.DefaultImpls.reload(this, manager);
    }

    static {
        GsonBuilder gsonBuilder;
        INSTANCE = new Dialogues();
        id = MiscUtilsKt.cobblemonResource("dialogues");
        type = PackType.SERVER_DATA;
        observable = new SimpleObservable();
        dialogues = new LinkedHashMap();
        gsonObservable = new SimpleObservable();
        GsonBuilder it = gsonBuilder = new GsonBuilder().registerTypeAdapter((Type)((Object)DialogueAction.class), (Object)DialogueActionAdapter.INSTANCE).registerTypeAdapter((Type)((Object)DialoguePredicate.class), (Object)DialoguePredicateAdapter.INSTANCE).registerTypeAdapter((Type)((Object)DialogueInput.class), (Object)DialogueInputAdapter.INSTANCE).registerTypeAdapter((Type)((Object)DialogueFaceProvider.class), (Object)DialogueFaceProviderAdapter.INSTANCE).registerTypeAdapter((Type)((Object)DialogueText.class), (Object)DialogueTextAdapter.INSTANCE).registerTypeAdapter((Type)((Object)Expression.class), (Object)ExpressionAdapter.INSTANCE).registerTypeAdapter((Type)((Object)ExpressionLike.class), (Object)ExpressionLikeAdapter.INSTANCE).registerTypeAdapter((Type)((Object)MutableComponent.class), (Object)TextAdapter.INSTANCE).registerTypeAdapter((Type)((Object)ResourceLocation.class), (Object)IdentifierAdapter.INSTANCE);
        boolean bl = false;
        GsonBuilder[] gsonBuilderArray = new GsonBuilder[1];
        Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
        gsonBuilderArray[0] = it;
        gsonObservable.emit((GsonBuilder[])gsonBuilderArray);
        gson = gsonBuilder.create();
        typeToken = TypeToken.get(Dialogue.class);
        resourcePath = "dialogues";
    }
}

