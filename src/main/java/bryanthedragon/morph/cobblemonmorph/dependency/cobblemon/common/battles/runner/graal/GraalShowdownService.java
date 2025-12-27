package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.graal

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BagItems
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.ShowdownService
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import com.google.gson.Gson
import com.google.gson.JsonArray
import java.io.File
import java.io.IOException
import java.net.URI
import java.nio.channels.SeekableByteChannel
import java.nio.file.AccessMode
import java.nio.file.DirectoryStream
import java.nio.file.LinkOption
import java.nio.file.OpenOption
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.DirectoryStream.Filter
import java.nio.file.attribute.FileAttribute
import java.util.Arrays
import java.util.UUID
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.HostAccess
import org.graalvm.polyglot.PolyglotAccess
import org.graalvm.polyglot.Value
import org.graalvm.polyglot.io.FileSystem
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nGraalShowdownService.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GraalShowdownService.kt\ncom/cobblemon/mod/common/battles/runner/graal/GraalShowdownService\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,195:1\n1855#2:196\n1855#2,2:197\n1856#2:199\n*S KotlinDebug\n*F\n+ 1 GraalShowdownService.kt\ncom/cobblemon/mod/common/battles/runner/graal/GraalShowdownService\n*L\n159#1:196\n161#1:197,2\n159#1:199\n*E\n"])
public class GraalShowdownService : ShowdownService {
   public final lateinit var context: Context
   public final val gson: Gson = new Gson()
   public final lateinit var sendBattleMessageFunction: Value
   public final val unbundler: GraalShowdownUnbundler = new GraalShowdownUnbundler()

   public override fun openConnection() {
      this.unbundler.attemptUnbundle();
      this.createContext();
      this.boot();
   }

   private fun createContext() {
      val var10001: Context = Context.newBuilder("js")
         .allowIO(true)
         .fileSystem(
            new FileSystem(Paths.get("./showdown")) {
               private final FileSystem default;

               {
                  this.$wd = `$wd`;
                  this.default = FileSystem.newDefaultFileSystem();
               }

               public final FileSystem getDefault() {
                  return this.default;
               }

               @Override
               public Path parsePath(@NotNull URI uri) {
                  return this.default.parsePath(uri);
               }

               @Override
               public Path parsePath(@NotNull java.lang.String path) {
                  return this.default.parsePath(path);
               }

               @Override
               public void createDirectory(@NotNull Path dir, @NotNull FileAttribute<?>... attrs) {
                  this.default.createDirectory(dir, Arrays.copyOf(attrs, attrs.length));
               }

               @Override
               public void delete(@NotNull Path path) {
                  this.default.delete(path);
               }

               @Override
               public SeekableByteChannel newByteChannel(
                  @NotNull Path path, @NotNull java.util.Set<? extends OpenOption> options, @NotNull FileAttribute<?>... attrs
               ) {
                  return this.default.newByteChannel(path, options, Arrays.copyOf(attrs, attrs.length));
               }

               @Override
               public DirectoryStream<Path> newDirectoryStream(@NotNull Path dir, @NotNull Filter<? super Path> filter) {
                  return this.default.newDirectoryStream(dir, filter);
               }

               @Override
               public Path toAbsolutePath(@NotNull Path path) {
                  return this.default.toAbsolutePath(path);
               }

               @Override
               public Path toRealPath(@NotNull Path path, @NotNull LinkOption... linkOptions) {
                  return this.default.toRealPath(path, Arrays.copyOf(linkOptions, linkOptions.length));
               }

               @Override
               public java.util.Map<java.lang.String, Object> readAttributes(
                  @NotNull Path path, @NotNull java.lang.String attributes, @NotNull LinkOption... options
               ) {
                  return this.default.readAttributes(path, attributes, Arrays.copyOf(options, options.length));
               }

               @Override
               public void checkAccess(@NotNull Path path, @NotNull java.util.Set<? extends AccessMode> modes, @NotNull LinkOption... linkOptions) {
                  if (!path.toRealPath(LinkOption.NOFOLLOW_LINKS).startsWith(this.$wd.toRealPath(LinkOption.NOFOLLOW_LINKS))) {
                     Cobblemon.INSTANCE.getLOGGER().error("Hacked JS files in datapacks or some weird file system setup that Hiroku failed to anticipate.");
                     throw new IOException(
                        "Someone has put hacked JS files into datapacks because file access is being attempted outside of controlled folders."
                     );
                  }
               }
            }
         )
         .allowExperimentalOptions(true)
         .allowPolyglotAccess(PolyglotAccess.ALL)
         .allowHostAccess(
            HostAccess.newBuilder(HostAccess.EXPLICIT).allowIterableAccess(true).allowArrayAccess(true).allowListAccess(true).allowMapAccess(true).build()
         )
         .allowCreateThread(true)
         .logHandler(GraalLogger.INSTANCE)
         .option("engine.WarnInterpreterOnly", "false")
         .option("js.commonjs-require", "true")
         .option("js.commonjs-require-cwd", "showdown")
         .option("js.commonjs-core-modules-replacements", "buffer:buffer/,crypto:crypto-browserify,path:path-browserify")
         .allowHostClassLoading(true)
         .allowNativeAccess(true)
         .allowCreateProcess(true)
         .build();
      this.setContext(var10001);
      this.getContext().eval("js", "globalThis.process = {\n    cwd: function() {\n        return '';\n    }\n}");
   }

   public override fun closeConnection() {
      this.getContext().close();
   }

   private fun boot() {
      this.getContext().eval("js", FilesKt.readText$default(new File("showdown/index.js"), null, 1, null));
      val var10001: Value = this.getContext().getBindings("js").getMember("sendBattleMessage");
      this.setSendBattleMessageFunction(var10001);
   }

   public override fun startBattle(battle: PokemonBattle, messages: Array<String>) {
      this.getContext().getBindings("js").getMember("startBattle").execute(this, battle.getBattleId().toString(), messages);
   }

   public override fun send(battleId: UUID, messages: Array<String>) {
      this.sendToShowdown(battleId, messages);
   }

   public override fun getAbilityIds(): JsonArray {
      val var10000: Any = this.gson.fromJson(this.getContext().getBindings("js").getMember("getCobbledAbilityIds").execute().asString(), JsonArray.class);
      return var10000 as JsonArray;
   }

   public override fun getMoves(): JsonArray {
      val var10000: Any = this.gson.fromJson(this.getContext().getBindings("js").getMember("getCobbledMoves").execute().asString(), JsonArray.class);
      return var10000 as JsonArray;
   }

   public override fun getItemIds(): JsonArray {
      val var10000: Any = this.gson.fromJson(this.getContext().getBindings("js").getMember("getCobbledItemIds").execute().asString(), JsonArray.class);
      return var10000 as JsonArray;
   }

   public override fun registerSpecies() {
      val receiveSpeciesDataFn: Value = this.getContext().getBindings("js").getMember("receiveSpeciesData");
      val jsArray: Value = this.getContext().eval("js", "new Array();");
      var index: Long = 0L;

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val species: Species = `element$iv` as Species;
         jsArray.setArrayElement(index++, this.gson.toJson(new PokemonSpecies.ShowdownSpecies(`element$iv` as Species, null)));

         val `$this$forEach$ivx`: java.lang.Iterable;
         for (Object element$ivx : $this$forEach$ivx) {
            val form: FormData = `element$ivx` as FormData;
            if (!(`element$ivx` as FormData == species.getStandardForm())) {
               jsArray.setArrayElement(index++, this.gson.toJson(new PokemonSpecies.ShowdownSpecies(species, form)));
            }
         }
      }

      receiveSpeciesDataFn.execute(jsArray);
   }

   public override fun indicateSpeciesInitialized() {
      this.getContext().getBindings("js").getMember("afterCobbledSpeciesInit").execute();
   }

   public override fun registerBagItems() {
      val receiveBagItemDataFn: Value = this.getContext().getBindings("js").getMember("receiveBagItemData");

      for (Entry var3 : BagItems.INSTANCE.getBagItemsScripts$common().entrySet()) {
         receiveBagItemDataFn.execute(
            var3.getKey() as java.lang.String, StringsKt.replace$default(var3.getValue() as java.lang.String, "\n", " ", false, 4, null)
         );
      }
   }

   private fun sendToShowdown(battleId: UUID, messages: Array<String>) {
      this.getSendBattleMessageFunction().execute(battleId.toString(), messages);
   }

   @HostAccess.Export
   public fun sendFromShowdown(battleId: String, message: String) {
      val var10000: ShowdownInterpreter = ShowdownInterpreter.INSTANCE;
      val var10001: UUID = UUID.fromString(battleId);
      var10000.interpretMessage(var10001, message);
   }

   @HostAccess.Export
   public fun log(message: String) {
      Cobblemon.INSTANCE.getLOGGER().info(message);
   }
}
