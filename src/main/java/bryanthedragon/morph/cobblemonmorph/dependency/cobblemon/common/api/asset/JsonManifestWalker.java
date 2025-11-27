/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  kotlin.Metadata
 *  kotlin.io.FilesKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.asset;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;

import org.jetbrains.annotations.NotNull;

public final class JsonManifestWalker {
    @NotNull
    public static final JsonManifestWalker INSTANCE = new JsonManifestWalker();

    private JsonManifestWalker() {
    }

    @SuppressWarnings("unused")
    public final void build(@NotNull String manifestPath) throws IOException {
        Intrinsics.checkNotNullParameter((Object)manifestPath, (String)"manifestPath");
        File file = new File(manifestPath);
        file.createNewFile();
        File folder = file.getParentFile();
        folder.mkdir();
        File[] members = folder.listFiles(JsonManifestWalker::build$lambda$0);
        JsonArray jsonArray = new JsonArray();
        Intrinsics.checkNotNullExpressionValue((Object)members, (String)"members");
        File[] $this$forEach$iv = members;
        int n = $this$forEach$iv.length;
        for (int i = 0; i < n; ++i) {
            File element$iv;
            File it = element$iv = $this$forEach$iv[i];
            boolean bl = false;
            Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
            Intrinsics.checkNotNullExpressionValue((Object)folder, (String)"folder");
            jsonArray.add(FilesKt.relativeTo((File)it, (File)folder).toString());
        }
        PrintWriter pw = new PrintWriter(file);
        new GsonBuilder().setPrettyPrinting().create().toJson((JsonElement)jsonArray, (Appendable)pw);
        pw.flush();
        pw.close();
    }

    /*
     * WARNING - void declaration
     */
    @SuppressWarnings({"rawtypes","unchecked", "unused"})
    @NotNull
    public final <T> List<T> load(@NotNull Class<T> clazz, @NotNull String folder, @NotNull Gson gson2) {
        Intrinsics.checkNotNullParameter(clazz, (String)"clazz");
        Intrinsics.checkNotNullParameter((Object)folder, (String)"folder");
        Intrinsics.checkNotNullParameter((Object)gson2, (String)"gson");
        String manifestPath = "/assets/cobblemon/" + folder + "/_MANIFEST.json";
        InputStream inputStream = Cobblemon.class.getResourceAsStream(manifestPath);
        Intrinsics.checkNotNull((Object)inputStream);
        InputStream manifest = inputStream;
        String folderPath = manifestPath.substring(0, manifestPath.lastIndexOf("/"));
        List list = new ArrayList();
        Gson gson3 = gson2;
        Reader reader$iv = new InputStreamReader(manifest);
        boolean $i$f$fromJson = false;
        JsonArray array = (JsonArray)gson3.fromJson(reader$iv, JsonArray.class);
        Intrinsics.checkNotNullExpressionValue((Object)array, (String)"array");
        for (Object element$iv : array) {
            InputStream inputStream2;
            JsonElement it = (JsonElement)element$iv;
            boolean bl = false;
            String path = it.getAsString();
            InputStream inputStream3 = inputStream2 = Cobblemon.INSTANCE.getClass().getResourceAsStream(folderPath + "/" + path);
            if (inputStream3 == null) {
                JsonManifestWalker $this$load_u24lambda_u243_u24lambda_u242 = INSTANCE;
                boolean bl2 = false;
                Cobblemon.INSTANCE.getLOGGER().error("manifest contains element " + path + " which was not found.");
                continue;
            }
            Intrinsics.checkNotNullExpressionValue((Object)inputStream3, (String)"Cobblemon.javaClass.getR\u2026urn@forEach\n            }");
            InputStream stream = inputStream2;
            try {
                list.add(gson2.fromJson((Reader)new InputStreamReader(stream), clazz));
            }
            catch (Exception exception) {
                Cobblemon.INSTANCE.getLOGGER().error("Issue loading manifest component: " + path);
                exception.printStackTrace();
            }
        }
        return list;
    }

    private static final boolean build$lambda$0(File f) {
        Intrinsics.checkNotNullParameter((Object)f, (String)"f");
        return Intrinsics.areEqual((Object)FilesKt.getExtension((File)f), (Object)"json") && !Intrinsics.areEqual((Object)FilesKt.getNameWithoutExtension((File)f), (Object)"_MANIFEST");
    }
}

