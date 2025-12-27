package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket
import com.mojang.brigadier.ImmutableStringReader
import com.mojang.brigadier.Message
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.commands.SharedSuggestionProvider
import net.minecraft.network.chat.MutableComponent

@SourceDebugExtension(["SMAP\nSpawnBucketArgumentType.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawnBucketArgumentType.kt\ncom/cobblemon/mod/common/command/argument/SpawnBucketArgumentType\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,49:1\n1#2:50\n1549#3:51\n1620#3,3:52\n*S KotlinDebug\n*F\n+ 1 SpawnBucketArgumentType.kt\ncom/cobblemon/mod/common/command/argument/SpawnBucketArgumentType\n*L\n45#1:51\n45#1:52,3\n*E\n"])
public class SpawnBucketArgumentType : ArgumentType<SpawnBucket> {
   public open fun parse(reader: StringReader): SpawnBucket {
      val name: java.lang.String = reader.readString();
      val var4: java.util.Iterator = Cobblemon.INSTANCE.getBestSpawner().getConfig().getBuckets().iterator();

      var var10000: Any;
      while (true) {
         if (var4.hasNext()) {
            val var5: Any = var4.next();
            if (!StringsKt.equals((var5 as SpawnBucket).getName(), name, true)) {
               continue;
            }

            var10000 = (SpawnBucket)var5;
            break;
         }

         var10000 = null;
         break;
      }

      var10000 = var10000;
      if (var10000 == null) {
         val var9: CommandSyntaxException = new SimpleCommandExceptionType(INVALID_BUCKET as Message).createWithContext(reader as ImmutableStringReader);
         throw var9 as java.lang.Throwable;
      } else {
         return var10000;
      }
   }

   public open fun <S : Any> listSuggestions(context: CommandContext<Any>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
      val `$this$map$iv`: java.lang.Iterable = Cobblemon.INSTANCE.getBestSpawner().getConfig().getBuckets();
      val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$map$iv`, 10));

      for (Object item$iv$iv : $this$map$iv) {
         `destination$iv$iv`.add((`item$iv$iv` as SpawnBucket).getName());
      }

      val var10000: CompletableFuture = SharedSuggestionProvider.m_82970_(`destination$iv$iv` as java.util.List, builder);
      return var10000;
   }

   public open fun getExamples(): List<String> {
      return EXAMPLES;
   }

   public companion object {
      public final val EXAMPLES: List<String>
      public final val INVALID_BUCKET: MutableComponent

      public fun spawnBucket(): SpawnBucketArgumentType {
         return new SpawnBucketArgumentType();
      }

      public fun <S> getSpawnBucket(context: CommandContext<Any>, name: String): SpawnBucket {
         val var10000: Any = context.getArgument(name, SpawnBucket.class);
         return var10000 as SpawnBucket;
      }
   }
}
