package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import com.mojang.brigadier.ImmutableStringReader
import com.mojang.brigadier.Message
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.Arrays
import java.util.Locale
import net.minecraft.ResourceLocationException
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation

public fun ResourceLocation.extractTo(directory: File) {
   val var5: Array<Any> = new Object[]{`$this$extractTo`.m_135827_(), `$this$extractTo`.m_135815_()};
   val var10001: java.lang.String = java.lang.String.format("/assets/%s/%s", Arrays.copyOf(var5, var5.length));
   val var3: InputStream = Cobblemon.class.getResourceAsStream(var10001);
   if (var3 == null) {
      throw new Exception("Could not read $`$this$extractTo`");
   } else {
      Files.copy(var3, directory.toPath(), StandardCopyOption.REPLACE_EXISTING);
      var3.close();
   }
}

public fun String.asIdentifierDefaultingNamespace(namespace: String = "cobblemon"): ResourceLocation {
   val var10000: java.lang.String = `$this$asIdentifierDefaultingNamespace`.toLowerCase(Locale.ROOT);
   return if (StringsKt.contains$default(var10000, ":", false, 2, null))
      new ResourceLocation(StringsKt.substringBefore$default(var10000, ":", null, 2, null), StringsKt.substringAfter$default(var10000, ":", null, 2, null))
      else
      new ResourceLocation(namespace, var10000);
}

@JvmSynthetic
fun `asIdentifierDefaultingNamespace$default`(var0: java.lang.String, var1: java.lang.String, var2: Int, var3: Any): ResourceLocation {
   if ((var2 and 1) != 0) {
      var1 = "cobblemon";
   }

   return asIdentifierDefaultingNamespace(var0, var1);
}

public fun StringReader.asIdentifierDefaultingNamespace(namespace: String = "cobblemon"): ResourceLocation {
   val start: Int = `$this$asIdentifierDefaultingNamespace`.getCursor();

   while ($this$asIdentifierDefaultingNamespace.canRead() && ResourceLocation.m_135816_($this$asIdentifierDefaultingNamespace.peek())) {
      `$this$asIdentifierDefaultingNamespace`.skip();
   }

   var var10000: java.lang.String = `$this$asIdentifierDefaultingNamespace`.getString();
   var10000 = var10000.substring(start, `$this$asIdentifierDefaultingNamespace`.getCursor());
   val raw: java.lang.String = var10000;

   try {
      return asIdentifierDefaultingNamespace(raw, namespace);
   } catch (var6: ResourceLocationException) {
      `$this$asIdentifierDefaultingNamespace`.setCursor(start);
      val var8: CommandSyntaxException = new SimpleCommandExceptionType(Component.m_237115_("argument.id.invalid") as Message)
         .createWithContext(`$this$asIdentifierDefaultingNamespace` as ImmutableStringReader);
      throw var8 as java.lang.Throwable;
   }
}

@JvmSynthetic
fun `asIdentifierDefaultingNamespace$default`(var0: StringReader, var1: java.lang.String, var2: Int, var3: Any): ResourceLocation {
   if ((var2 and 1) != 0) {
      var1 = "cobblemon";
   }

   return asIdentifierDefaultingNamespace(var0, var1);
}
