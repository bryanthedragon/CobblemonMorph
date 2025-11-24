/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonArray
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.socket;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BagItems;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.ShowdownService;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.socket.SocketShowdownService;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B%\u0012\b\b\u0002\u0010:\u001a\u00020\u0011\u0012\b\b\u0002\u0010A\u001a\u00020\u0018\u0012\b\b\u0002\u0010=\u001a\u00020\u0018\u00a2\u0006\u0004\bJ\u0010KJ\u001d\u0010\u0005\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u000f\u0010\u0007\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u000f\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ\u000f\u0010\f\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\f\u0010\u000bJ\u000f\u0010\r\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\r\u0010\u000bJ\u000f\u0010\u000e\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u000e\u0010\bJ\u001f\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0011H\u0002\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u000f\u0010\u0015\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0015\u0010\bJ\u001f\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u0018H\u0002\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0015\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00110\u001cH\u0002\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u000f\u0010\u001f\u001a\u00020\u0011H\u0002\u00a2\u0006\u0004\b\u001f\u0010 J\u000f\u0010!\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b!\u0010\bJ\u000f\u0010\"\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\"\u0010\bJ%\u0010%\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u000f2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00110#H\u0016\u00a2\u0006\u0004\b%\u0010&J\u001f\u0010)\u001a\u00020\u00032\u0006\u0010'\u001a\u00020\u00112\u0006\u0010(\u001a\u00020\u0011H\u0002\u00a2\u0006\u0004\b)\u0010*J!\u0010/\u001a\u00020\u00032\u0006\u0010,\u001a\u00020+2\b\u0010.\u001a\u0004\u0018\u00010-H\u0002\u00a2\u0006\u0004\b/\u00100J%\u00103\u001a\u00020\u00032\u0006\u00102\u001a\u0002012\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00110#H\u0016\u00a2\u0006\u0004\b3\u00104R\u0017\u00106\u001a\u0002058\u0006\u00a2\u0006\f\n\u0004\b6\u00107\u001a\u0004\b8\u00109R\u0017\u0010:\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b:\u0010;\u001a\u0004\b<\u0010 R\u0017\u0010=\u001a\u00020\u00188\u0006\u00a2\u0006\f\n\u0004\b=\u0010>\u001a\u0004\b?\u0010@R\u0017\u0010A\u001a\u00020\u00188\u0006\u00a2\u0006\f\n\u0004\bA\u0010>\u001a\u0004\bB\u0010@R\u0016\u0010\u0017\u001a\u00020\u00168\u0002@\u0002X\u0082.\u00a2\u0006\u0006\n\u0004\b\u0017\u0010CR\u0016\u0010E\u001a\u00020D8\u0002@\u0002X\u0082.\u00a2\u0006\u0006\n\u0004\bE\u0010FR\u0016\u0010H\u001a\u00020G8\u0002@\u0002X\u0082.\u00a2\u0006\u0006\n\u0004\bH\u0010I\u00a8\u0006L"}, d2={"Lcom/cobblemon/mod/common/battles/runner/socket/SocketShowdownService;", "Lcom/cobblemon/mod/common/battles/runner/ShowdownService;", "Lkotlin/Function0;", "", "ifFails", "acknowledge", "(Lkotlin/jvm/functions/Function0;)V", "closeConnection", "()V", "Lcom/google/gson/JsonArray;", "getAbilityIds", "()Lcom/google/gson/JsonArray;", "getItemIds", "getMoves", "indicateSpeciesInitialized", "Ljava/util/UUID;", "battleId", "", "message", "interpretMessage", "(Ljava/util/UUID;Ljava/lang/String;)V", "openConnection", "Ljava/io/BufferedReader;", "reader", "", "size", "read", "(Ljava/io/BufferedReader;I)Ljava/lang/String;", "", "readBattleInput", "()Ljava/util/List;", "readMessage", "()Ljava/lang/String;", "registerBagItems", "registerSpecies", "", "messages", "send", "(Ljava/util/UUID;[Ljava/lang/String;)V", "itemId", "js", "sendBagItem", "(Ljava/lang/String;Ljava/lang/String;)V", "Lcom/cobblemon/mod/common/pokemon/Species;", "species", "Lcom/cobblemon/mod/common/pokemon/FormData;", "form", "sendSpeciesData", "(Lcom/cobblemon/mod/common/pokemon/Species;Lcom/cobblemon/mod/common/pokemon/FormData;)V", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "startBattle", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;[Ljava/lang/String;)V", "Lcom/google/gson/Gson;", "gson", "Lcom/google/gson/Gson;", "getGson", "()Lcom/google/gson/Gson;", "host", "Ljava/lang/String;", "getHost", "localPort", "I", "getLocalPort", "()I", "port", "getPort", "Ljava/io/BufferedReader;", "Ljava/net/Socket;", "socket", "Ljava/net/Socket;", "Ljava/io/OutputStreamWriter;", "writer", "Ljava/io/OutputStreamWriter;", "<init>", "(Ljava/lang/String;II)V", "common"})
@SourceDebugExtension(value={"SMAP\nSocketShowdownService.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SocketShowdownService.kt\ncom/cobblemon/mod/common/battles/runner/socket/SocketShowdownService\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,169:1\n1855#2,2:170\n1855#2:172\n1855#2,2:173\n1856#2:175\n*S KotlinDebug\n*F\n+ 1 SocketShowdownService.kt\ncom/cobblemon/mod/common/battles/runner/socket/SocketShowdownService\n*L\n72#1:170,2\n139#1:172\n141#1:173,2\n139#1:175\n*E\n"})
public final class SocketShowdownService
implements ShowdownService {
    @NotNull
    private final String host;
    private final int port;
    private final int localPort;
    private Socket socket;
    private OutputStreamWriter writer;
    private BufferedReader reader;
    @NotNull
    private final Gson gson;

    public SocketShowdownService(@NotNull String host, int port, int localPort) {
        Intrinsics.checkNotNullParameter((Object)host, (String)"host");
        this.host = host;
        this.port = port;
        this.localPort = localPort;
        this.gson = new Gson();
    }

    public /* synthetic */ SocketShowdownService(String string, int n, int n2, int n3, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n3 & 1) != 0) {
            string = "localhost";
        }
        if ((n3 & 2) != 0) {
            n = 18468;
        }
        if ((n3 & 4) != 0) {
            n2 = 0;
        }
        this(string, n, n2);
    }

    @NotNull
    public final String getHost() {
        return this.host;
    }

    public final int getPort() {
        return this.port;
    }

    public final int getLocalPort() {
        return this.localPort;
    }

    @NotNull
    public final Gson getGson() {
        return this.gson;
    }

    @Override
    public void openConnection() {
        this.socket = new Socket(InetAddress.getLocalHost(), this.port, InetAddress.getLocalHost(), this.localPort);
        Socket socket = this.socket;
        if (socket == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"socket");
            socket = null;
        }
        OutputStream outputStream = socket.getOutputStream();
        Intrinsics.checkNotNullExpressionValue((Object)outputStream, (String)"socket.getOutputStream()");
        OutputStream outputStream2 = outputStream;
        Charset charset = Charset.forName("ascii");
        Intrinsics.checkNotNullExpressionValue((Object)charset, (String)"forName(\"ascii\")");
        Charset charset2 = charset;
        this.writer = new OutputStreamWriter(outputStream2, charset2);
        Socket socket2 = this.socket;
        if (socket2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"socket");
            socket2 = null;
        }
        this.reader = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
    }

    @Override
    public void closeConnection() {
        Socket socket = this.socket;
        if (socket == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"socket");
            socket = null;
        }
        socket.close();
    }

    @Override
    public void startBattle(@NotNull PokemonBattle battle2, @NotNull String[] messages) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Intrinsics.checkNotNullParameter((Object)messages, (String)"messages");
        OutputStreamWriter outputStreamWriter = this.writer;
        if (outputStreamWriter == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"writer");
            outputStreamWriter = null;
        }
        outputStreamWriter.write(">startbattle " + battle2.getBattleId() + "\n");
        this.acknowledge((Function0<Unit>)((Function0)startBattle.1.INSTANCE));
        UUID uUID = battle2.getBattleId();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"battle.battleId");
        this.send(uUID, messages);
    }

    @Override
    public void send(@NotNull UUID battleId, @NotNull String[] messages) {
        Intrinsics.checkNotNullParameter((Object)battleId, (String)"battleId");
        Intrinsics.checkNotNullParameter((Object)messages, (String)"messages");
        for (String message : messages) {
            OutputStreamWriter outputStreamWriter = this.writer;
            if (outputStreamWriter == null) {
                Intrinsics.throwUninitializedPropertyAccessException((String)"writer");
                outputStreamWriter = null;
            }
            outputStreamWriter.write(battleId + "~" + message + "\n");
            OutputStreamWriter outputStreamWriter2 = this.writer;
            if (outputStreamWriter2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException((String)"writer");
                outputStreamWriter2 = null;
            }
            outputStreamWriter2.flush();
            Iterable $this$forEach$iv = this.readBattleInput();
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                String it = (String)element$iv;
                boolean bl = false;
                this.interpretMessage(battleId, it);
            }
        }
    }

    private final String read(BufferedReader reader, int size) {
        char[] buffer = new char[size];
        while (reader.read(buffer) == 0) {
        }
        return new String(buffer);
    }

    private final String readMessage() {
        BufferedReader bufferedReader = this.reader;
        if (bufferedReader == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"reader");
            bufferedReader = null;
        }
        int payloadSize = Integer.parseInt(this.read(bufferedReader, 8));
        BufferedReader bufferedReader2 = this.reader;
        if (bufferedReader2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"reader");
            bufferedReader2 = null;
        }
        String payload = this.read(bufferedReader2, payloadSize);
        return payload;
    }

    private final List<String> readBattleInput() {
        int numLines;
        List lines = new ArrayList();
        BufferedReader bufferedReader = this.reader;
        if (bufferedReader == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"reader");
            bufferedReader = null;
        }
        if ((numLines = Integer.parseInt(this.read(bufferedReader, 8))) != 0) {
            for (int i = 0; i < numLines; ++i) {
                lines.add(this.readMessage());
            }
        }
        return lines;
    }

    private final void interpretMessage(UUID battleId, String message) {
        ShowdownInterpreter.INSTANCE.interpretMessage(battleId, message);
    }

    @Override
    @NotNull
    public JsonArray getAbilityIds() {
        OutputStreamWriter outputStreamWriter = this.writer;
        if (outputStreamWriter == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"writer");
            outputStreamWriter = null;
        }
        outputStreamWriter.write(">getCobbledAbilityIds");
        OutputStreamWriter outputStreamWriter2 = this.writer;
        if (outputStreamWriter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"writer");
            outputStreamWriter2 = null;
        }
        outputStreamWriter2.flush();
        String response = this.readMessage();
        Object object = this.gson.fromJson(response, JsonArray.class);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"gson.fromJson(response, JsonArray::class.java)");
        return (JsonArray)object;
    }

    @Override
    @NotNull
    public JsonArray getMoves() {
        OutputStreamWriter outputStreamWriter = this.writer;
        if (outputStreamWriter == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"writer");
            outputStreamWriter = null;
        }
        outputStreamWriter.write(">getCobbledMoves\n");
        OutputStreamWriter outputStreamWriter2 = this.writer;
        if (outputStreamWriter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"writer");
            outputStreamWriter2 = null;
        }
        outputStreamWriter2.flush();
        String response = this.readMessage();
        Object object = this.gson.fromJson(response, JsonArray.class);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"gson.fromJson(response, JsonArray::class.java)");
        return (JsonArray)object;
    }

    @Override
    @NotNull
    public JsonArray getItemIds() {
        OutputStreamWriter outputStreamWriter = this.writer;
        if (outputStreamWriter == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"writer");
            outputStreamWriter = null;
        }
        outputStreamWriter.write(">getCobbledItemIds");
        OutputStreamWriter outputStreamWriter2 = this.writer;
        if (outputStreamWriter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"writer");
            outputStreamWriter2 = null;
        }
        outputStreamWriter2.flush();
        String response = this.readMessage();
        Object object = this.gson.fromJson(response, JsonArray.class);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"gson.fromJson(response, JsonArray::class.java)");
        return (JsonArray)object;
    }

    private final void sendSpeciesData(Species species, FormData form2) {
        OutputStreamWriter outputStreamWriter = this.writer;
        if (outputStreamWriter == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"writer");
            outputStreamWriter = null;
        }
        outputStreamWriter.write(">receiveSpeciesData " + this.gson.toJson((Object)new PokemonSpecies.ShowdownSpecies(species, form2)) + "\n");
        SocketShowdownService.acknowledge$default(this, null, 1, null);
    }

    private final void sendBagItem(String itemId, String js) {
        OutputStreamWriter outputStreamWriter = this.writer;
        if (outputStreamWriter == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"writer");
            outputStreamWriter = null;
        }
        outputStreamWriter.write(">receiveBagItemData " + itemId + " " + js);
        this.acknowledge((Function0<Unit>)((Function0)new Function0<Unit>(itemId){
            final /* synthetic */ String $itemId;
            {
                this.$itemId = $itemId;
                super(0);
            }

            public final void invoke() {
                Cobblemon.INSTANCE.getLOGGER().error("Failed to send bag item to Showdown: " + this.$itemId);
            }
        }));
    }

    @Override
    public void registerSpecies() {
        OutputStreamWriter outputStreamWriter = this.writer;
        if (outputStreamWriter == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"writer");
            outputStreamWriter = null;
        }
        outputStreamWriter.write(">resetSpeciesData\n");
        SocketShowdownService.acknowledge$default(this, null, 1, null);
        Iterable $this$forEach$iv = PokemonSpecies.INSTANCE.getSpecies();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Species species = (Species)element$iv;
            boolean bl = false;
            this.sendSpeciesData(species, null);
            Iterable $this$forEach$iv2 = species.getForms();
            boolean $i$f$forEach2 = false;
            for (Object element$iv2 : $this$forEach$iv2) {
                FormData form2 = (FormData)element$iv2;
                boolean bl2 = false;
                if (Intrinsics.areEqual((Object)form2, (Object)species.getStandardForm())) continue;
                this.sendSpeciesData(species, form2);
            }
        }
    }

    public final void acknowledge(@NotNull Function0<Unit> ifFails) {
        Intrinsics.checkNotNullParameter(ifFails, (String)"ifFails");
        OutputStreamWriter outputStreamWriter = this.writer;
        if (outputStreamWriter == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"writer");
            outputStreamWriter = null;
        }
        outputStreamWriter.flush();
        char[] ack = new char[3];
        BufferedReader bufferedReader = this.reader;
        if (bufferedReader == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"reader");
            bufferedReader = null;
        }
        bufferedReader.read(ack);
        if (!Intrinsics.areEqual((Object)new String(ack), (Object)"ACK")) {
            ifFails.invoke();
        }
    }

    public static /* synthetic */ void acknowledge$default(SocketShowdownService socketShowdownService, Function0 function0, int n, Object object) {
        if ((n & 1) != 0) {
            function0 = acknowledge.1.INSTANCE;
        }
        socketShowdownService.acknowledge((Function0<Unit>)function0);
    }

    @Override
    public void registerBagItems() {
        for (Map.Entry<String, String> entry : BagItems.INSTANCE.getBagItemsScripts$common().entrySet()) {
            String itemId = entry.getKey();
            String js = entry.getValue();
            this.sendBagItem(itemId, StringsKt.replace$default((String)js, (String)"\n", (String)" ", (boolean)false, (int)4, null));
        }
    }

    @Override
    public void indicateSpeciesInitialized() {
        OutputStreamWriter outputStreamWriter = this.writer;
        if (outputStreamWriter == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"writer");
            outputStreamWriter = null;
        }
        outputStreamWriter.write(">afterCobbledSpeciesInit");
        SocketShowdownService.acknowledge$default(this, null, 1, null);
    }

    public SocketShowdownService() {
        this(null, 0, 0, 7, null);
    }
}

