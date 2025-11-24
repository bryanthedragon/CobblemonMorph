/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonArray
 *  kotlin.Metadata
 *  kotlin.io.FilesKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.graal;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BagItems;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.ShowdownService;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.graal.GraalLogger;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.graal.GraalShowdownUnbundler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessMode;
import java.nio.file.DirectoryStream;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.PolyglotAccess;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.io.FileSystem;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b9\u0010\u0004J\u000f\u0010\u0003\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0005\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0004J\u000f\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u000f\u0010\n\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\n\u0010\tJ\u000f\u0010\u000b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\u000b\u0010\tJ\u000f\u0010\f\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\f\u0010\u0004J\u0017\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\rH\u0007\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u000f\u0010\u0011\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0004J\u000f\u0010\u0012\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0004J\u000f\u0010\u0013\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0004J%\u0010\u0018\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00142\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\r0\u0016H\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u001f\u0010\u001a\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH\u0007\u00a2\u0006\u0004\b\u001a\u0010\u001bJ%\u0010\u001c\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00142\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\r0\u0016H\u0002\u00a2\u0006\u0004\b\u001c\u0010\u0019J%\u0010\u001f\u001a\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u001d2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\r0\u0016H\u0016\u00a2\u0006\u0004\b\u001f\u0010 R\"\u0010\"\u001a\u00020!8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u0017\u0010)\u001a\u00020(8\u0006\u00a2\u0006\f\n\u0004\b)\u0010*\u001a\u0004\b+\u0010,R\"\u0010.\u001a\u00020-8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b.\u0010/\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\u0017\u00105\u001a\u0002048\u0006\u00a2\u0006\f\n\u0004\b5\u00106\u001a\u0004\b7\u00108\u00a8\u0006:"}, d2={"Lcom/cobblemon/mod/common/battles/runner/graal/GraalShowdownService;", "Lcom/cobblemon/mod/common/battles/runner/ShowdownService;", "", "boot", "()V", "closeConnection", "createContext", "Lcom/google/gson/JsonArray;", "getAbilityIds", "()Lcom/google/gson/JsonArray;", "getItemIds", "getMoves", "indicateSpeciesInitialized", "", "message", "log", "(Ljava/lang/String;)V", "openConnection", "registerBagItems", "registerSpecies", "Ljava/util/UUID;", "battleId", "", "messages", "send", "(Ljava/util/UUID;[Ljava/lang/String;)V", "sendFromShowdown", "(Ljava/lang/String;Ljava/lang/String;)V", "sendToShowdown", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "startBattle", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;[Ljava/lang/String;)V", "Lorg/graalvm/polyglot/Context;", "context", "Lorg/graalvm/polyglot/Context;", "getContext", "()Lorg/graalvm/polyglot/Context;", "setContext", "(Lorg/graalvm/polyglot/Context;)V", "Lcom/google/gson/Gson;", "gson", "Lcom/google/gson/Gson;", "getGson", "()Lcom/google/gson/Gson;", "Lorg/graalvm/polyglot/Value;", "sendBattleMessageFunction", "Lorg/graalvm/polyglot/Value;", "getSendBattleMessageFunction", "()Lorg/graalvm/polyglot/Value;", "setSendBattleMessageFunction", "(Lorg/graalvm/polyglot/Value;)V", "Lcom/cobblemon/mod/common/battles/runner/graal/GraalShowdownUnbundler;", "unbundler", "Lcom/cobblemon/mod/common/battles/runner/graal/GraalShowdownUnbundler;", "getUnbundler", "()Lcom/cobblemon/mod/common/battles/runner/graal/GraalShowdownUnbundler;", "<init>", "common"})
@SourceDebugExtension(value={"SMAP\nGraalShowdownService.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GraalShowdownService.kt\ncom/cobblemon/mod/common/battles/runner/graal/GraalShowdownService\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,195:1\n1855#2:196\n1855#2,2:197\n1856#2:199\n*S KotlinDebug\n*F\n+ 1 GraalShowdownService.kt\ncom/cobblemon/mod/common/battles/runner/graal/GraalShowdownService\n*L\n159#1:196\n161#1:197,2\n159#1:199\n*E\n"})
public final class GraalShowdownService
implements ShowdownService {
    public transient Context context;
    public transient Value sendBattleMessageFunction;
    @NotNull
    private final transient GraalShowdownUnbundler unbundler = new GraalShowdownUnbundler();
    @NotNull
    private final transient Gson gson = new Gson();

    @NotNull
    public final Context getContext() {
        Context context = this.context;
        if (context != null) {
            return context;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"context");
        return null;
    }

    public final void setContext(@NotNull Context context) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"<set-?>");
        this.context = context;
    }

    @NotNull
    public final Value getSendBattleMessageFunction() {
        Value value2 = this.sendBattleMessageFunction;
        if (value2 != null) {
            return value2;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"sendBattleMessageFunction");
        return null;
    }

    public final void setSendBattleMessageFunction(@NotNull Value value2) {
        Intrinsics.checkNotNullParameter((Object)value2, (String)"<set-?>");
        this.sendBattleMessageFunction = value2;
    }

    @NotNull
    public final GraalShowdownUnbundler getUnbundler() {
        return this.unbundler;
    }

    @NotNull
    public final Gson getGson() {
        return this.gson;
    }

    @Override
    public void openConnection() {
        this.unbundler.attemptUnbundle();
        this.createContext();
        this.boot();
    }

    private final void createContext() {
        Path wd = Paths.get("./showdown", new String[0]);
        HostAccess access = HostAccess.newBuilder(HostAccess.EXPLICIT).allowIterableAccess(true).allowArrayAccess(true).allowListAccess(true).allowMapAccess(true).build();
        String[] stringArray = new String[]{"js"};
        Context context = Context.newBuilder(stringArray).allowIO(true).fileSystem(new FileSystem(wd){
            private final FileSystem default;
            final /* synthetic */ Path $wd;
            {
                this.$wd = $wd;
                this.default = FileSystem.newDefaultFileSystem();
            }

            public final FileSystem getDefault() {
                return this.default;
            }

            public Path parsePath(@NotNull URI uri) {
                Intrinsics.checkNotNullParameter((Object)uri, (String)"uri");
                return this.default.parsePath(uri);
            }

            public Path parsePath(@NotNull String path) {
                Intrinsics.checkNotNullParameter((Object)path, (String)"path");
                return this.default.parsePath(path);
            }

            public void createDirectory(@NotNull Path dir, FileAttribute<?> ... attrs) {
                Intrinsics.checkNotNullParameter((Object)dir, (String)"dir");
                Intrinsics.checkNotNullParameter(attrs, (String)"attrs");
                this.default.createDirectory(dir, Arrays.copyOf(attrs, attrs.length));
            }

            public void delete(@NotNull Path path) {
                Intrinsics.checkNotNullParameter((Object)path, (String)"path");
                this.default.delete(path);
            }

            public SeekableByteChannel newByteChannel(@NotNull Path path, @NotNull Set<? extends OpenOption> options, FileAttribute<?> ... attrs) {
                Intrinsics.checkNotNullParameter((Object)path, (String)"path");
                Intrinsics.checkNotNullParameter(options, (String)"options");
                Intrinsics.checkNotNullParameter(attrs, (String)"attrs");
                return this.default.newByteChannel(path, options, Arrays.copyOf(attrs, attrs.length));
            }

            public DirectoryStream<Path> newDirectoryStream(@NotNull Path dir, @NotNull DirectoryStream.Filter<? super Path> filter) {
                Intrinsics.checkNotNullParameter((Object)dir, (String)"dir");
                Intrinsics.checkNotNullParameter(filter, (String)"filter");
                return this.default.newDirectoryStream(dir, filter);
            }

            public Path toAbsolutePath(@NotNull Path path) {
                Intrinsics.checkNotNullParameter((Object)path, (String)"path");
                return this.default.toAbsolutePath(path);
            }

            public Path toRealPath(@NotNull Path path, LinkOption ... linkOptions) {
                Intrinsics.checkNotNullParameter((Object)path, (String)"path");
                Intrinsics.checkNotNullParameter((Object)linkOptions, (String)"linkOptions");
                return this.default.toRealPath(path, Arrays.copyOf(linkOptions, linkOptions.length));
            }

            public Map<String, Object> readAttributes(@NotNull Path path, @NotNull String attributes, LinkOption ... options) {
                Intrinsics.checkNotNullParameter((Object)path, (String)"path");
                Intrinsics.checkNotNullParameter((Object)attributes, (String)"attributes");
                Intrinsics.checkNotNullParameter((Object)options, (String)"options");
                return this.default.readAttributes(path, attributes, Arrays.copyOf(options, options.length));
            }

            public void checkAccess(@NotNull Path path, @NotNull Set<? extends AccessMode> modes, LinkOption ... linkOptions) {
                Intrinsics.checkNotNullParameter((Object)path, (String)"path");
                Intrinsics.checkNotNullParameter(modes, (String)"modes");
                Intrinsics.checkNotNullParameter((Object)linkOptions, (String)"linkOptions");
                LinkOption[] linkOptionArray = new LinkOption[]{LinkOption.NOFOLLOW_LINKS};
                Path path2 = path.toRealPath(linkOptionArray);
                linkOptionArray = new LinkOption[]{LinkOption.NOFOLLOW_LINKS};
                if (!path2.startsWith(this.$wd.toRealPath(linkOptionArray))) {
                    Cobblemon.INSTANCE.getLOGGER().error("Hacked JS files in datapacks or some weird file system setup that Hiroku failed to anticipate.");
                    throw new IOException("Someone has put hacked JS files into datapacks because file access is being attempted outside of controlled folders.");
                }
            }
        }).allowExperimentalOptions(true).allowPolyglotAccess(PolyglotAccess.ALL).allowHostAccess(access).allowCreateThread(true).logHandler(GraalLogger.INSTANCE).option("engine.WarnInterpreterOnly", "false").option("js.commonjs-require", "true").option("js.commonjs-require-cwd", "showdown").option("js.commonjs-core-modules-replacements", "buffer:buffer/,crypto:crypto-browserify,path:path-browserify").allowHostClassLoading(true).allowNativeAccess(true).allowCreateProcess(true).build();
        Intrinsics.checkNotNullExpressionValue((Object)context, (String)"wd = Paths.get(\"./showdo\u2026rue)\n            .build()");
        this.setContext(context);
        this.getContext().eval("js", "globalThis.process = {\n    cwd: function() {\n        return '';\n    }\n}");
    }

    @Override
    public void closeConnection() {
        this.getContext().close();
    }

    private final void boot() {
        this.getContext().eval("js", FilesKt.readText$default((File)new File("showdown/index.js"), null, (int)1, null));
        Value value2 = this.getContext().getBindings("js").getMember("sendBattleMessage");
        Intrinsics.checkNotNullExpressionValue((Object)value2, (String)"context.getBindings(\"js\"\u2026mber(\"sendBattleMessage\")");
        this.setSendBattleMessageFunction(value2);
    }

    @Override
    public void startBattle(@NotNull PokemonBattle battle2, @NotNull String[] messages) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Intrinsics.checkNotNullParameter((Object)messages, (String)"messages");
        Value startBattleFunction = this.getContext().getBindings("js").getMember("startBattle");
        Object[] objectArray = new Object[]{this, battle2.getBattleId().toString(), messages};
        startBattleFunction.execute(objectArray);
    }

    @Override
    public void send(@NotNull UUID battleId, @NotNull String[] messages) {
        Intrinsics.checkNotNullParameter((Object)battleId, (String)"battleId");
        Intrinsics.checkNotNullParameter((Object)messages, (String)"messages");
        this.sendToShowdown(battleId, messages);
    }

    @Override
    @NotNull
    public JsonArray getAbilityIds() {
        Value getCobbledAbilityIdsFn = this.getContext().getBindings("js").getMember("getCobbledAbilityIds");
        String arrayResult = getCobbledAbilityIdsFn.execute(new Object[0]).asString();
        Object object = this.gson.fromJson(arrayResult, JsonArray.class);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"gson.fromJson(arrayResult, JsonArray::class.java)");
        return (JsonArray)object;
    }

    @Override
    @NotNull
    public JsonArray getMoves() {
        Value getCobbledMovesFn = this.getContext().getBindings("js").getMember("getCobbledMoves");
        String arrayResult = getCobbledMovesFn.execute(new Object[0]).asString();
        Object object = this.gson.fromJson(arrayResult, JsonArray.class);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"gson.fromJson(arrayResult, JsonArray::class.java)");
        return (JsonArray)object;
    }

    @Override
    @NotNull
    public JsonArray getItemIds() {
        Value getCobbledItemIdsFn = this.getContext().getBindings("js").getMember("getCobbledItemIds");
        String arrayResult = getCobbledItemIdsFn.execute(new Object[0]).asString();
        Object object = this.gson.fromJson(arrayResult, JsonArray.class);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"gson.fromJson(arrayResult, JsonArray::class.java)");
        return (JsonArray)object;
    }

    @Override
    public void registerSpecies() {
        Value receiveSpeciesDataFn = this.getContext().getBindings("js").getMember("receiveSpeciesData");
        Value jsArray = this.getContext().eval("js", "new Array();");
        long index = 0L;
        Iterable $this$forEach$iv = PokemonSpecies.INSTANCE.getSpecies();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Species species = (Species)element$iv;
            boolean bl = false;
            long l = index;
            index = l + 1L;
            jsArray.setArrayElement(l, this.gson.toJson((Object)new PokemonSpecies.ShowdownSpecies(species, null)));
            Iterable $this$forEach$iv2 = species.getForms();
            boolean $i$f$forEach2 = false;
            for (Object element$iv2 : $this$forEach$iv2) {
                FormData form2 = (FormData)element$iv2;
                boolean bl2 = false;
                if (Intrinsics.areEqual((Object)form2, (Object)species.getStandardForm())) continue;
                long l2 = index;
                index = l2 + 1L;
                jsArray.setArrayElement(l2, this.gson.toJson((Object)new PokemonSpecies.ShowdownSpecies(species, form2)));
            }
        }
        Object[] objectArray = new Object[]{jsArray};
        receiveSpeciesDataFn.execute(objectArray);
    }

    @Override
    public void indicateSpeciesInitialized() {
        Value afterCobbledSpeciesInitFn = this.getContext().getBindings("js").getMember("afterCobbledSpeciesInit");
        afterCobbledSpeciesInitFn.execute(new Object[0]);
    }

    @Override
    public void registerBagItems() {
        Value receiveBagItemDataFn = this.getContext().getBindings("js").getMember("receiveBagItemData");
        for (Map.Entry<String, String> entry : BagItems.INSTANCE.getBagItemsScripts$common().entrySet()) {
            String itemId = entry.getKey();
            String js = entry.getValue();
            Object[] objectArray = new Object[]{itemId, StringsKt.replace$default((String)js, (String)"\n", (String)" ", (boolean)false, (int)4, null)};
            receiveBagItemDataFn.execute(objectArray);
        }
    }

    private final void sendToShowdown(UUID battleId, String[] messages) {
        Object[] objectArray = new Object[]{battleId.toString(), messages};
        this.getSendBattleMessageFunction().execute(objectArray);
    }

    @HostAccess.Export
    public final void sendFromShowdown(@NotNull String battleId, @NotNull String message) {
        Intrinsics.checkNotNullParameter((Object)battleId, (String)"battleId");
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        UUID uUID = UUID.fromString(battleId);
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"fromString(battleId)");
        ShowdownInterpreter.INSTANCE.interpretMessage(uUID, message);
    }

    @HostAccess.Export
    public final void log(@NotNull String message) {
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        Cobblemon.INSTANCE.getLOGGER().info(message);
    }
}

