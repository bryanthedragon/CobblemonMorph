/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.NoWhenBranchMatchedException
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0019\u0010\u001aJ7\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\u0004\b\t\u0010\nJ+\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0003\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u0002*\u00020\u0013\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0013\u0010\u0017\u001a\u0004\u0018\u00010\u0016*\u00020\u0013\u00a2\u0006\u0004\b\u0017\u0010\u0018\u00a8\u0006\u001b"}, d2={"Lcom/cobblemon/mod/common/util/AssetLoading;", "", "Ljava/nio/file/Path;", "dir", "Lkotlin/Function1;", "", "filter", "recursive", "", "fileSearch", "(Ljava/nio/file/Path;Lkotlin/jvm/functions/Function1;Z)Ljava/util/List;", "", "suffix", "", "Ljava/io/File;", "list", "", "searchFor", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)V", "Lnet/minecraft/resources/ResourceLocation;", "toPath", "(Lnet/minecraft/resources/ResourceLocation;)Ljava/nio/file/Path;", "Ljava/net/URI;", "toURL", "(Lnet/minecraft/resources/ResourceLocation;)Ljava/net/URI;", "<init>", "()V", "common"})
public final class AssetLoading {
    @NotNull
    public static final AssetLoading INSTANCE = new AssetLoading();

    private AssetLoading() {
    }

    @Nullable
    public final Path toPath(@NotNull ResourceLocation $this$toPath) {
        Path path;
        Intrinsics.checkNotNullParameter((Object)$this$toPath, (String)"<this>");
        URI uRI = this.toURL($this$toPath);
        if (uRI != null) {
            Path path2 = Paths.get(uRI);
            path = path2;
            Intrinsics.checkNotNullExpressionValue((Object)path2, (String)"get(this)");
        } else {
            path = null;
        }
        return path;
    }

    @Nullable
    public final URI toURL(@NotNull ResourceLocation $this$toURL) {
        Intrinsics.checkNotNullParameter((Object)$this$toURL, (String)"<this>");
        String string = "/assets/%s/%s";
        Object[] objectArray = new Object[]{$this$toURL.m_135827_(), $this$toURL.m_135815_()};
        String string2 = String.format(string, Arrays.copyOf(objectArray, objectArray.length));
        Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"format(format, *args)");
        URL uRL = Cobblemon.class.getResource(string2);
        return uRL != null ? uRL.toURI() : null;
    }

    @NotNull
    public final List<Path> fileSearch(@NotNull Path dir, @NotNull Function1<? super Path, Boolean> filter, boolean recursive) {
        Intrinsics.checkNotNullParameter((Object)dir, (String)"dir");
        Intrinsics.checkNotNullParameter(filter, (String)"filter");
        List files = new ArrayList();
        Files.walkFileTree(dir, (FileVisitor<? super Path>)new SimpleFileVisitor<Path>(filter, (List<Path>)files, recursive){
            final /* synthetic */ Function1<Path, Boolean> $filter;
            final /* synthetic */ List<Path> $files;
            final /* synthetic */ boolean $recursive;
            {
                this.$filter = $filter;
                this.$files = $files;
                this.$recursive = $recursive;
            }

            @NotNull
            public FileVisitResult visitFile(@NotNull Path file, @NotNull BasicFileAttributes fileAttributes) {
                FileVisitResult fileVisitResult;
                boolean bl;
                Intrinsics.checkNotNullParameter((Object)file, (String)"file");
                Intrinsics.checkNotNullParameter((Object)fileAttributes, (String)"fileAttributes");
                if (((Boolean)this.$filter.invoke((Object)file)).booleanValue()) {
                    this.$files.add(file);
                }
                if (bl = this.$recursive) {
                    fileVisitResult = FileVisitResult.CONTINUE;
                } else if (!bl) {
                    fileVisitResult = FileVisitResult.SKIP_SUBTREE;
                } else {
                    throw new NoWhenBranchMatchedException();
                }
                return fileVisitResult;
            }
        });
        return files;
    }

    public final void searchFor(@NotNull String dir, @NotNull String suffix, @NotNull List<File> list) {
        Intrinsics.checkNotNullParameter((Object)dir, (String)"dir");
        Intrinsics.checkNotNullParameter((Object)suffix, (String)"suffix");
        Intrinsics.checkNotNullParameter(list, (String)"list");
        File file = new File(dir);
        String[] stringArray = file.list();
        if (stringArray == null) {
            return;
        }
        for (String name : stringArray) {
            File subFile = new File(dir + "/" + name);
            if (subFile.isFile()) {
                Intrinsics.checkNotNullExpressionValue((Object)name, (String)"name");
                if (StringsKt.endsWith$default((String)name, (String)suffix, (boolean)false, (int)2, null)) {
                    list.add(subFile);
                    continue;
                }
            }
            if (!subFile.isDirectory()) continue;
            this.searchFor(dir + "/" + name, suffix, list);
        }
    }
}

