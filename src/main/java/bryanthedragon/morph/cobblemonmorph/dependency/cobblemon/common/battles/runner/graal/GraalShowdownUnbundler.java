/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  kotlin.Metadata
 *  kotlin.io.CloseableKt
 *  kotlin.io.FilesKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.graal;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.FileUtils;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Path;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001:\u0001\u0011B\u0007\u00a2\u0006\u0004\b\u0010\u0010\u0004J\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0011\u0010\u0006\u001a\u0004\u0018\u00010\u0005H\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0019\u0010\n\u001a\u0004\u0018\u00010\u00052\u0006\u0010\t\u001a\u00020\bH\u0002\u00a2\u0006\u0004\b\n\u0010\u000bR\u001c\u0010\u000e\u001a\n \r*\u0004\u0018\u00010\f0\f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/battles/runner/graal/GraalShowdownUnbundler;", "", "", "attemptUnbundle", "()V", "Lcom/cobblemon/mod/common/battles/runner/graal/GraalShowdownUnbundler$ShowdownMetadata;", "loadShowdownMetadata", "()Lcom/cobblemon/mod/common/battles/runner/graal/GraalShowdownUnbundler$ShowdownMetadata;", "Ljava/io/File;", "target", "readShowdownMetadata", "(Ljava/io/File;)Lcom/cobblemon/mod/common/battles/runner/graal/GraalShowdownUnbundler$ShowdownMetadata;", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "gson", "Lcom/google/gson/Gson;", "<init>", "ShowdownMetadata", "common"})
@SourceDebugExtension(value={"SMAP\nGraalShowdownUnbundler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GraalShowdownUnbundler.kt\ncom/cobblemon/mod/common/battles/runner/graal/GraalShowdownUnbundler\n+ 2 GsonExtensions.kt\ncom/cobblemon/mod/common/util/GsonExtensionsKt\n*L\n1#1,96:1\n17#2:97\n17#2:98\n*S KotlinDebug\n*F\n+ 1 GraalShowdownUnbundler.kt\ncom/cobblemon/mod/common/battles/runner/graal/GraalShowdownUnbundler\n*L\n76#1:97\n86#1:98\n*E\n"})
public final class GraalShowdownUnbundler {
    private final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    public final void attemptUnbundle() {
        File showdownDir = new File("showdown");
        ShowdownMetadata metadata = this.loadShowdownMetadata();
        if (!showdownDir.exists() || Cobblemon.INSTANCE.getConfig().getAutoUpdateShowdown()) {
            showdownDir.mkdirs();
            File showdownZip = new File(showdownDir, "showdown.zip");
            File showdownMetadataFile = new File(showdownDir, "showdown.json");
            boolean extract = true;
            if (showdownMetadataFile.exists()) {
                ShowdownMetadata current = this.readShowdownMetadata(showdownMetadataFile);
                ShowdownMetadata showdownMetadata = metadata;
                Intrinsics.checkNotNull((Object)showdownMetadata);
                double d = showdownMetadata.getShowdownVersion();
                ShowdownMetadata showdownMetadata2 = current;
                Intrinsics.checkNotNull((Object)showdownMetadata2);
                if (d == showdownMetadata2.getShowdownVersion()) {
                    extract = false;
                } else {
                    Cobblemon.INSTANCE.getLOGGER().info("Updating showdown service to version " + metadata.getShowdownVersion() + ", from version " + current.getShowdownVersion() + "...");
                    File backupDir = new File("showdown-backup");
                    if (backupDir.exists() && backupDir.isDirectory()) {
                        FilesKt.deleteRecursively((File)backupDir);
                    }
                    FilesKt.copyTo$default((File)showdownDir, (File)backupDir, (boolean)false, (int)0, (int)6, null);
                }
            }
            if (extract) {
                ResourceLocationExtensionsKt.extractTo(new ResourceLocation("cobblemon", "showdown.zip"), showdownZip);
                ResourceLocationExtensionsKt.extractTo(new ResourceLocation("cobblemon", "showdown.json"), showdownMetadataFile);
                Path path = showdownZip.toPath();
                Intrinsics.checkNotNullExpressionValue((Object)path, (String)"showdownZip.toPath()");
                Path path2 = showdownDir.toPath();
                Intrinsics.checkNotNullExpressionValue((Object)path2, (String)"showdownDir.toPath()");
                FileUtils.INSTANCE.unzipFile(path, path2);
                showdownZip.delete();
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    private final ShowdownMetadata loadShowdownMetadata() {
        try {
            void $this$fromJson$iv;
            InputStream inputStream = this.getClass().getResourceAsStream("/assets/cobblemon/showdown.json");
            Intrinsics.checkNotNull((Object)inputStream);
            InputStream inputStream2 = inputStream;
            Gson gson2 = this.gson;
            Intrinsics.checkNotNullExpressionValue((Object)gson2, (String)"gson");
            Gson gson3 = gson2;
            Reader reader$iv = new InputStreamReader(inputStream2);
            boolean $i$f$fromJson = false;
            return (ShowdownMetadata)$this$fromJson$iv.fromJson(reader$iv, ShowdownMetadata.class);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final ShowdownMetadata readShowdownMetadata(File target) {
        try {
            Closeable closeable = new InputStreamReader(new FileInputStream(target));
            Throwable throwable = null;
            try {
                InputStreamReader it = (InputStreamReader)closeable;
                boolean bl = false;
                Gson gson2 = this.gson;
                Intrinsics.checkNotNullExpressionValue((Object)gson2, (String)"gson");
                Gson $this$fromJson$iv = gson2;
                boolean $i$f$fromJson = false;
                ShowdownMetadata showdownMetadata = (ShowdownMetadata)$this$fromJson$iv.fromJson((Reader)it, ShowdownMetadata.class);
                return showdownMetadata;
            }
            catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            }
            finally {
                CloseableKt.closeFinally((Closeable)closeable, (Throwable)throwable);
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0082\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001a\u0010\u0006\u001a\u00020\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u0002H\u00c6\u0001\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001a\u0010\n\u001a\u00020\t2\b\u0010\b\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0010\u0010\r\u001a\u00020\fH\u00d6\u0001\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0010\u0010\u0010\u001a\u00020\u000fH\u00d6\u0001\u00a2\u0006\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0005\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0012\u001a\u0004\b\u0013\u0010\u0004\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/battles/runner/graal/GraalShowdownUnbundler$ShowdownMetadata;", "", "", "component1", "()D", "showdownVersion", "copy", "(D)Lcom/cobblemon/mod/common/battles/runner/graal/GraalShowdownUnbundler$ShowdownMetadata;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "D", "getShowdownVersion", "<init>", "(D)V", "common"})
    private static final class ShowdownMetadata {
        private final double showdownVersion;

        public ShowdownMetadata(double showdownVersion) {
            this.showdownVersion = showdownVersion;
        }

        public final double getShowdownVersion() {
            return this.showdownVersion;
        }

        public final double component1() {
            return this.showdownVersion;
        }

        @NotNull
        public final ShowdownMetadata copy(double showdownVersion) {
            return new ShowdownMetadata(showdownVersion);
        }

        public static /* synthetic */ ShowdownMetadata copy$default(ShowdownMetadata showdownMetadata, double d, int n, Object object) {
            if ((n & 1) != 0) {
                d = showdownMetadata.showdownVersion;
            }
            return showdownMetadata.copy(d);
        }

        @NotNull
        public String toString() {
            return "ShowdownMetadata(showdownVersion=" + this.showdownVersion + ")";
        }

        public int hashCode() {
            return Double.hashCode(this.showdownVersion);
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ShowdownMetadata)) {
                return false;
            }
            ShowdownMetadata showdownMetadata = (ShowdownMetadata)other;
            return Double.compare(this.showdownVersion, showdownMetadata.showdownVersion) == 0;
        }
    }
}

