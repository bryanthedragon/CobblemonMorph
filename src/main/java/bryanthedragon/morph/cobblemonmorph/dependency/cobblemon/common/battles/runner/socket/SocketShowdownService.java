package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.socket

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
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.net.InetAddress
import java.net.Socket
import java.nio.charset.Charset
import java.util.ArrayList;
import java.util.UUID
import java.util.Map.Entry
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.Intrinsics
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nSocketShowdownService.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SocketShowdownService.kt\ncom/cobblemon/mod/common/battles/runner/socket/SocketShowdownService\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,169:1\n1855#2,2:170\n1855#2:172\n1855#2,2:173\n1856#2:175\n*S KotlinDebug\n*F\n+ 1 SocketShowdownService.kt\ncom/cobblemon/mod/common/battles/runner/socket/SocketShowdownService\n*L\n72#1:170,2\n139#1:172\n141#1:173,2\n139#1:175\n*E\n"])
public class SocketShowdownService(host: String = "localhost", port: Int = 18468, localPort: Int = 0) : ShowdownService {
   public final val gson: Gson
   public final val host: String
   public final val localPort: Int
   public final val port: Int
   private final lateinit var reader: BufferedReader
   private final lateinit var socket: Socket
   private final lateinit var writer: OutputStreamWriter

   init {
      this.host = host;
      this.port = port;
      this.localPort = localPort;
      this.gson = new Gson();
   }

   public override fun openConnection() {
      this.socket = new Socket(InetAddress.getLocalHost(), this.port, InetAddress.getLocalHost(), this.localPort);
      var var10001: Socket = this.socket;
      if (this.socket == null) {
         Intrinsics.throwUninitializedPropertyAccessException("socket");
         var10001 = null;
      }

      val var3: OutputStream = var10001.getOutputStream();
      val var4: Charset = Charset.forName("ascii");
      this.writer = new OutputStreamWriter(var3, var4);
      val var5: BufferedReader = new BufferedReader;
      val var10003: InputStreamReader = new InputStreamReader;
      var var10005: Socket = this.socket;
      if (this.socket == null) {
         Intrinsics.throwUninitializedPropertyAccessException("socket");
         var10005 = null;
      }

      var10003./* $VF: Unable to resugar constructor */<init>(var10005.getInputStream());
      var5./* $VF: Unable to resugar constructor */<init>(var10003);
      this.reader = var5;
   }

   public override fun closeConnection() {
      var var10000: Socket = this.socket;
      if (this.socket == null) {
         Intrinsics.throwUninitializedPropertyAccessException("socket");
         var10000 = null;
      }

      var10000.close();
   }

   public override fun startBattle(battle: PokemonBattle, messages: Array<String>) {
      var var10000: OutputStreamWriter = this.writer;
      if (this.writer == null) {
         Intrinsics.throwUninitializedPropertyAccessException("writer");
         var10000 = null;
      }

      var10000.write(">startbattle ${battle.getBattleId()}\n");
      this.acknowledge(<unrepresentable>.INSTANCE);
      val var10001: UUID = battle.getBattleId();
      this.send(var10001, messages);
   }

   public override fun send(battleId: UUID, messages: Array<String>) {
      for (java.lang.String message : messages) {
         var var10000: OutputStreamWriter = this.writer;
         if (this.writer == null) {
            Intrinsics.throwUninitializedPropertyAccessException("writer");
            var10000 = null;
         }

         var10000.write("$battleId~$message\n");
         var10000 = this.writer;
         if (this.writer == null) {
            Intrinsics.throwUninitializedPropertyAccessException("writer");
            var10000 = null;
         }

         var10000.flush();

         val `$this$forEach$iv`: java.lang.Iterable;
         for (Object element$iv : $this$forEach$iv) {
            this.interpretMessage(battleId, `element$iv` as java.lang.String);
         }
      }
   }

   private fun read(reader: BufferedReader, size: Int): String {
      val buffer: CharArray = new char[size];

      while (reader.read(buffer) == 0) {
      }

      return new java.lang.String(buffer);
   }

   private fun readMessage(): String {
      var var10001: BufferedReader = this.reader;
      if (this.reader == null) {
         Intrinsics.throwUninitializedPropertyAccessException("reader");
         var10001 = null;
      }

      val payloadSize: Int = Integer.parseInt(this.read(var10001, 8));
      var10001 = this.reader;
      if (this.reader == null) {
         Intrinsics.throwUninitializedPropertyAccessException("reader");
         var10001 = null;
      }

      return this.read(var10001, payloadSize);
   }

   private fun readBattleInput(): List<String> {
      val lines: java.util.List = new ArrayList();
      var var10001: BufferedReader = this.reader;
      if (this.reader == null) {
         Intrinsics.throwUninitializedPropertyAccessException("reader");
         var10001 = null;
      }

      val numLines: Int = Integer.parseInt(this.read(var10001, 8));
      if (numLines != 0) {
         for (int i = 0; i < numLines; i++) {
            lines.add(this.readMessage());
         }
      }

      return lines;
   }

   private fun interpretMessage(battleId: UUID, message: String) {
      ShowdownInterpreter.INSTANCE.interpretMessage(battleId, message);
   }

   public override fun getAbilityIds(): JsonArray {
      var var10000: OutputStreamWriter = this.writer;
      if (this.writer == null) {
         Intrinsics.throwUninitializedPropertyAccessException("writer");
         var10000 = null;
      }

      var10000.write(">getCobbledAbilityIds");
      var10000 = this.writer;
      if (this.writer == null) {
         Intrinsics.throwUninitializedPropertyAccessException("writer");
         var10000 = null;
      }

      var10000.flush();
      val var3: Any = this.gson.fromJson(this.readMessage(), JsonArray.class);
      return var3 as JsonArray;
   }

   public override fun getMoves(): JsonArray {
      var var10000: OutputStreamWriter = this.writer;
      if (this.writer == null) {
         Intrinsics.throwUninitializedPropertyAccessException("writer");
         var10000 = null;
      }

      var10000.write(">getCobbledMoves\n");
      var10000 = this.writer;
      if (this.writer == null) {
         Intrinsics.throwUninitializedPropertyAccessException("writer");
         var10000 = null;
      }

      var10000.flush();
      val var3: Any = this.gson.fromJson(this.readMessage(), JsonArray.class);
      return var3 as JsonArray;
   }

   public override fun getItemIds(): JsonArray {
      var var10000: OutputStreamWriter = this.writer;
      if (this.writer == null) {
         Intrinsics.throwUninitializedPropertyAccessException("writer");
         var10000 = null;
      }

      var10000.write(">getCobbledItemIds");
      var10000 = this.writer;
      if (this.writer == null) {
         Intrinsics.throwUninitializedPropertyAccessException("writer");
         var10000 = null;
      }

      var10000.flush();
      val var3: Any = this.gson.fromJson(this.readMessage(), JsonArray.class);
      return var3 as JsonArray;
   }

   private fun sendSpeciesData(species: Species, form: FormData?) {
      var var10000: OutputStreamWriter = this.writer;
      if (this.writer == null) {
         Intrinsics.throwUninitializedPropertyAccessException("writer");
         var10000 = null;
      }

      var10000.write(">receiveSpeciesData ${this.gson.toJson(new PokemonSpecies.ShowdownSpecies(species, form))}\n");
      acknowledge$default(this, null, 1, null);
   }

   private fun sendBagItem(itemId: String, js: String) {
      var var10000: OutputStreamWriter = this.writer;
      if (this.writer == null) {
         Intrinsics.throwUninitializedPropertyAccessException("writer");
         var10000 = null;
      }

      var10000.write(">receiveBagItemData $itemId $js");
      this.acknowledge((new Function0<Unit>(itemId) {
         {
            super(0);
            this.$itemId = `$itemId`;
         }

         public final void invoke() {
            Cobblemon.INSTANCE.getLOGGER().error("Failed to send bag item to Showdown: ${this.$itemId}");
         }
      }) as () -> Unit);
   }

   public override fun registerSpecies() {
      var var10000: OutputStreamWriter = this.writer;
      if (this.writer == null) {
         Intrinsics.throwUninitializedPropertyAccessException("writer");
         var10000 = null;
      }

      var10000.write(">resetSpeciesData\n");
      acknowledge$default(this, null, 1, null);

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val species: Species = `element$iv` as Species;
         this.sendSpeciesData(`element$iv` as Species, null);

         val `$this$forEach$ivx`: java.lang.Iterable;
         for (Object element$ivx : $this$forEach$ivx) {
            val form: FormData = `element$ivx` as FormData;
            if (!(`element$ivx` as FormData == species.getStandardForm())) {
               this.sendSpeciesData(species, form);
            }
         }
      }
   }

   public fun acknowledge(ifFails: () -> Unit = <unrepresentable>.INSTANCE as Function0) {
      var var10000: OutputStreamWriter = this.writer;
      if (this.writer == null) {
         Intrinsics.throwUninitializedPropertyAccessException("writer");
         var10000 = null;
      }

      var10000.flush();
      val ack: CharArray = new char[3];
      var var3: BufferedReader = this.reader;
      if (this.reader == null) {
         Intrinsics.throwUninitializedPropertyAccessException("reader");
         var3 = null;
      }

      var3.read(ack);
      if (!(new java.lang.String(ack) == "ACK")) {
         ifFails.invoke();
      }
   }

   public override fun registerBagItems() {
      for (Entry var2 : BagItems.INSTANCE.getBagItemsScripts$common().entrySet()) {
         this.sendBagItem(var2.getKey() as java.lang.String, StringsKt.replace$default(var2.getValue() as java.lang.String, "\n", " ", false, 4, null));
      }
   }

   public override fun indicateSpeciesInitialized() {
      var var10000: OutputStreamWriter = this.writer;
      if (this.writer == null) {
         Intrinsics.throwUninitializedPropertyAccessException("writer");
         var10000 = null;
      }

      var10000.write(">afterCobbledSpeciesInit");
      acknowledge$default(this, null, 1, null);
   }

   fun SocketShowdownService() {
      this(null, 0, 0, 7, null);
   }
}
