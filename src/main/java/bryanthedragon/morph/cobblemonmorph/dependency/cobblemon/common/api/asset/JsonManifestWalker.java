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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0017\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0000\u00a2\u0006\u0004\b\u0005\u0010\u0006J7\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e\"\u0004\b\u0000\u0010\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\t2\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/api/asset/JsonManifestWalker;", "", "", "manifestPath", "", "build$common", "(Ljava/lang/String;)V", "build", "T", "Ljava/lang/Class;", "clazz", "folder", "Lcom/google/gson/Gson;", "gson", "", "load", "(Ljava/lang/Class;Ljava/lang/String;Lcom/google/gson/Gson;)Ljava/util/List;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nJsonManifestWalker.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JsonManifestWalker.kt\ncom/cobblemon/mod/common/api/asset/JsonManifestWalker\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 GsonExtensions.kt\ncom/cobblemon/mod/common/util/GsonExtensionsKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,82:1\n13579#2,2:83\n17#3:85\n1855#4,2:86\n*S KotlinDebug\n*F\n+ 1 JsonManifestWalker.kt\ncom/cobblemon/mod/common/api/asset/JsonManifestWalker\n*L\n48#1:83,2\n65#1:85\n66#1:86,2\n*E\n"})
public final class JsonManifestWalker {
    @NotNull
    public static final JsonManifestWalker INSTANCE = new JsonManifestWalker();

    private JsonManifestWalker() {
    }

    public final void build$common(@NotNull String manifestPath) {
        Intrinsics.checkNotNullParameter((Object)manifestPath, (String)"manifestPath");
        File file = new File(manifestPath);
        file.createNewFile();
        File folder = file.getParentFile();
        folder.mkdir();
        File[] members = folder.listFiles(JsonManifestWalker::build$lambda$0);
        JsonArray jsonArray = new JsonArray();
        Intrinsics.checkNotNullExpressionValue((Object)members, (String)"members");
        File[] $this$forEach$iv = members;
        boolean $i$f$forEach = false;
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
    @NotNull
    public final <T> List<T> load(@NotNull Class<T> clazz, @NotNull String folder, @NotNull Gson gson2) {
        void $this$fromJson$iv;
        Intrinsics.checkNotNullParameter(clazz, (String)"clazz");
        Intrinsics.checkNotNullParameter((Object)folder, (String)"folder");
        Intrinsics.checkNotNullParameter((Object)gson2, (String)"gson");
        String manifestPath = "/assets/cobblemon/" + folder + "/_MANIFEST.json";
        InputStream inputStream = Cobblemon.class.getResourceAsStream(manifestPath);
        Intrinsics.checkNotNull((Object)inputStream);
        InputStream manifest = inputStream;
        String folderPath = StringsKt.substringBeforeLast$default((String)manifestPath, (String)"/", null, (int)2, null);
        List list = new ArrayList();
        Gson gson3 = gson2;
        Reader reader$iv = new InputStreamReader(manifest);
        boolean $i$f$fromJson = false;
        JsonArray array = (JsonArray)$this$fromJson$iv.fromJson(reader$iv, JsonArray.class);
        Intrinsics.checkNotNullExpressionValue((Object)array, (String)"array");
        Iterable $this$forEach$iv = (Iterable)array;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
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

