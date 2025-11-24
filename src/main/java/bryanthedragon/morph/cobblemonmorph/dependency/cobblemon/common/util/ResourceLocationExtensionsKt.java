/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.ImmutableStringReader
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  net.minecraft.ResourceLocationException
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.io.File;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.minecraft.ResourceLocationException;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u001a\u001b\u0010\u0004\u001a\u00020\u0003*\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0001\u00a2\u0006\u0004\b\u0004\u0010\u0005\u001a\u001b\u0010\u0004\u001a\u00020\u0003*\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u0001\u00a2\u0006\u0004\b\u0004\u0010\u0006\u001a\u0019\u0010\n\u001a\u00020\t*\u00020\u00032\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\n\u0010\u000b\u00a8\u0006\f"}, d2={"Lcom/mojang/brigadier/StringReader;", "", "namespace", "Lnet/minecraft/resources/ResourceLocation;", "asIdentifierDefaultingNamespace", "(Lcom/mojang/brigadier/StringReader;Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;", "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;", "Ljava/io/File;", "directory", "", "extractTo", "(Lnet/minecraft/resources/ResourceLocation;Ljava/io/File;)V", "common"})
public final class ResourceLocationExtensionsKt {
    public static final void extractTo(@NotNull ResourceLocation $this$extractTo, @NotNull File directory) {
        Intrinsics.checkNotNullParameter((Object)$this$extractTo, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)directory, (String)"directory");
        String string = "/assets/%s/%s";
        Object[] objectArray = new Object[]{$this$extractTo.m_135827_(), $this$extractTo.m_135815_()};
        String string2 = String.format(string, Arrays.copyOf(objectArray, objectArray.length));
        Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"format(format, *args)");
        CopyOption[] copyOptionArray = Cobblemon.class.getResourceAsStream(string2);
        if (copyOptionArray == null) {
            throw new Exception("Could not read " + $this$extractTo);
        }
        CopyOption[] stream = copyOptionArray;
        copyOptionArray = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING};
        Files.copy((InputStream)stream, directory.toPath(), copyOptionArray);
        stream.close();
    }

    @NotNull
    public static final ResourceLocation asIdentifierDefaultingNamespace(@NotNull String $this$asIdentifierDefaultingNamespace, @NotNull String namespace) {
        Intrinsics.checkNotNullParameter((Object)$this$asIdentifierDefaultingNamespace, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)namespace, (String)"namespace");
        String string = $this$asIdentifierDefaultingNamespace.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
        String id = string;
        return StringsKt.contains$default((CharSequence)id, (CharSequence)":", (boolean)false, (int)2, null) ? new ResourceLocation(StringsKt.substringBefore$default((String)id, (String)":", null, (int)2, null), StringsKt.substringAfter$default((String)id, (String)":", null, (int)2, null)) : new ResourceLocation(namespace, id);
    }

    public static /* synthetic */ ResourceLocation asIdentifierDefaultingNamespace$default(String string, String string2, int n, Object object) {
        if ((n & 1) != 0) {
            string2 = "cobblemon";
        }
        return ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace(string, string2);
    }

    @NotNull
    public static final ResourceLocation asIdentifierDefaultingNamespace(@NotNull StringReader $this$asIdentifierDefaultingNamespace, @NotNull String namespace) {
        Intrinsics.checkNotNullParameter((Object)$this$asIdentifierDefaultingNamespace, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)namespace, (String)"namespace");
        int start2 = $this$asIdentifierDefaultingNamespace.getCursor();
        while ($this$asIdentifierDefaultingNamespace.canRead() && ResourceLocation.m_135816_((char)$this$asIdentifierDefaultingNamespace.peek())) {
            $this$asIdentifierDefaultingNamespace.skip();
        }
        String string = $this$asIdentifierDefaultingNamespace.getString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this.string");
        String string2 = string;
        int n = $this$asIdentifierDefaultingNamespace.getCursor();
        String string3 = string2.substring(start2, n);
        Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"this as java.lang.String\u2026ing(startIndex, endIndex)");
        String raw = string3;
        try {
            return ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace(raw, namespace);
        }
        catch (ResourceLocationException e) {
            $this$asIdentifierDefaultingNamespace.setCursor(start2);
            CommandSyntaxException commandSyntaxException = new SimpleCommandExceptionType((Message)Component.m_237115_((String)"argument.id.invalid")).createWithContext((ImmutableStringReader)$this$asIdentifierDefaultingNamespace);
            Intrinsics.checkNotNullExpressionValue((Object)commandSyntaxException, (String)"SimpleCommandExceptionTy\u2026).createWithContext(this)");
            throw (Throwable)commandSyntaxException;
        }
    }

    public static /* synthetic */ ResourceLocation asIdentifierDefaultingNamespace$default(StringReader stringReader, String string, int n, Object object) {
        if ((n & 1) != 0) {
            string = "cobblemon";
        }
        return ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace(stringReader, string);
    }
}

