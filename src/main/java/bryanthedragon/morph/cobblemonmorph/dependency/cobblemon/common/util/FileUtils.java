/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.io.CloseableKt
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\r\u0010\u000eJ\u001f\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001d\u0010\u000b\u001a\u00020\n2\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0004\u00a2\u0006\u0004\b\u000b\u0010\f\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/util/FileUtils;", "", "Ljava/util/zip/ZipEntry;", "zipEntry", "Ljava/nio/file/Path;", "targetDir", "checkPath", "(Ljava/util/zip/ZipEntry;Ljava/nio/file/Path;)Ljava/nio/file/Path;", "source", "target", "", "unzipFile", "(Ljava/nio/file/Path;Ljava/nio/file/Path;)V", "<init>", "()V", "common"})
public final class FileUtils {
    @NotNull
    public static final FileUtils INSTANCE = new FileUtils();

    private FileUtils() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void unzipFile(@NotNull Path source, @NotNull Path target) {
        Intrinsics.checkNotNullParameter((Object)source, (String)"source");
        Intrinsics.checkNotNullParameter((Object)target, (String)"target");
        Closeable closeable = new ZipInputStream(new FileInputStream(source.toFile()));
        Throwable throwable = null;
        try {
            ZipInputStream zis = (ZipInputStream)closeable;
            boolean bl = false;
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                Path newPath = INSTANCE.checkPath(zipEntry, target);
                if (!zipEntry.isDirectory()) {
                    if (newPath.getParent() != null && Files.notExists(newPath.getParent(), new LinkOption[0])) {
                        Files.createDirectories(newPath.getParent(), new FileAttribute[0]);
                    }
                    CopyOption[] copyOptionArray = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING};
                    Files.copy(zis, newPath, copyOptionArray);
                } else {
                    Files.createDirectories(newPath, new FileAttribute[0]);
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
            Unit unit = Unit.INSTANCE;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            CloseableKt.closeFinally((Closeable)closeable, (Throwable)throwable);
        }
    }

    private final Path checkPath(ZipEntry zipEntry, Path targetDir) {
        Path targetDirPath;
        Path targetDirResolved = targetDir.resolve(zipEntry.getName());
        Path normalizePath = targetDirResolved.normalize().toAbsolutePath();
        if (!normalizePath.startsWith(targetDirPath = targetDir.normalize().toAbsolutePath())) {
            throw new IOException("Bad zip entry: " + zipEntry.getName());
        }
        Intrinsics.checkNotNullExpressionValue((Object)normalizePath, (String)"normalizePath");
        return normalizePath;
    }
}

