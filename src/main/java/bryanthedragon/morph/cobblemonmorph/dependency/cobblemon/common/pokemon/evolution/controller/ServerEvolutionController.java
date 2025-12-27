package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.controller

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution.EvolutionAcceptedEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionController
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionDisplay
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.progress.EvolutionProgress
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.progress.EvolutionProgressFactory
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.CancelableObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.evolution.AddEvolutionPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.evolution.ClearEvolutionsPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.evolution.RemoveEvolutionPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.JsonExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import java.util.ArrayList;
import java.util.Arrays
import java.util.HashSet
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.CollectionToArray
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.StringTag
import net.minecraft.nbt.Tag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundSource
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nServerEvolutionController.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ServerEvolutionController.kt\ncom/cobblemon/mod/common/pokemon/evolution/controller/ServerEvolutionController\n+ 2 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable\n+ 3 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 4 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 5 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable$postThen$1\n+ 6 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 7 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,244:1\n39#2,2:245\n41#2,2:250\n44#2,3:253\n47#2:258\n17#3,2:247\n19#3:257\n13579#4:249\n13580#4:256\n39#5:252\n288#6,2:259\n1855#6,2:261\n1855#6:263\n1856#6:265\n800#6,11:266\n800#6,11:277\n1549#6:288\n1620#6,3:289\n1549#6:292\n1620#6,3:293\n1855#6,2:296\n1855#6,2:298\n288#6,2:300\n1#7:264\n*S KotlinDebug\n*F\n+ 1 ServerEvolutionController.kt\ncom/cobblemon/mod/common/pokemon/evolution/controller/ServerEvolutionController\n*L\n47#1:245,2\n47#1:250,2\n47#1:253,3\n47#1:258\n47#1:247,2\n47#1:257\n47#1:249\n47#1:256\n47#1:252\n64#1:259,2\n76#1:261,2\n81#1:263\n81#1:265\n100#1:266,11\n105#1:277,11\n118#1:288\n118#1:289,3\n122#1:292\n122#1:293,3\n179#1:296,2\n215#1:298,2\n237#1:300,2\n*E\n"])
public class ServerEvolutionController(pokemon: Pokemon) : EvolutionController<Evolution> {
   private final val evolutions: HashSet<Evolution>
   public open val pokemon: Pokemon
   private final val progress: ArrayList<EvolutionProgress<*>>

   public open val size: Int
      public open get() {
         return this.evolutions.size();
      }


   init {
      this.pokemon = pokemon;
      this.evolutions = new HashSet<>();
      this.progress = new ArrayList<>();
   }

   public open fun start(evolution: Evolution) {
      val `$this$iv`: CancelableObservable = CobblemonEvents.EVOLUTION_ACCEPTED;
      val `event$iv`: Cancelable = new EvolutionAcceptedEvent(this.getPokemon(), evolution);
      val `this_$iv$iv`: EventObservable = `$this$iv`;
      val `events$iv$iv`: Array<Cancelable> = new Cancelable[]{`event$iv`};
      `this_$iv$iv`.emit(Arrays.copyOf(`events$iv$iv`, `events$iv$iv`.length));

      for (Object element$iv$iv$iv : events$iv$iv) {
         if (!((Cancelable)`element$iv$iv$iv`).isCanceled()) {
            val it: EvolutionAcceptedEvent = `element$iv$iv$iv` as EvolutionAcceptedEvent;
            evolution.forceEvolve(this.getPokemon());
         }
      }
   }

   public override fun progress(): Collection<EvolutionProgress<*>> {
      return CollectionsKt.toList(this.progress);
   }

   public override fun <P : EvolutionProgress<*>> trackProgress(progress: Any): Any {
      this.progress.add(progress);
      return (P)progress;
   }

   public override fun <P : EvolutionProgress<*>> progressFirstOrCreate(predicate: (EvolutionProgress<*>) -> Boolean, progressFactory: () -> Any): Any {
      val var6: java.util.Iterator = this.progress.iterator();

      var var10000: Any;
      while (true) {
         if (var6.hasNext()) {
            val `element$iv`: Any = var6.next();
            if (!predicate.invoke(`element$iv`) as java.lang.Boolean) {
               continue;
            }

            var10000 = `element$iv`;
            break;
         }

         var10000 = null;
         break;
      }

      val existing: EvolutionProgress = var10000 as EvolutionProgress;
      if (var10000 as EvolutionProgress == null) {
         val var8: EvolutionProgress = progressFactory.invoke() as EvolutionProgress;
         this.progress.add(var8);
         return (P)var8;
      } else {
         return (P)existing;
      }
   }

   public override fun saveToNBT(): Tag {
      val nbt: CompoundTag = new CompoundTag();
      val pendingList: ListTag = new ListTag();

      val progressList: java.lang.Iterable;
      for (Object element$iv : progressList) {
         (pendingList as java.util.Collection).add(StringTag.m_129297_((`element$iv` as Evolution).getId()));
      }

      nbt.m_128365_("pending", pendingList as Tag);
      val var14: ListTag = new ListTag();

      val var15: java.lang.Iterable;
      for (Object element$iv : var15) {
         val var19: EvolutionProgress = var18 as EvolutionProgress;
         val var10: java.util.Collection = var14 as java.util.Collection;
         val var11: Tag = var19.saveToNBT();
         (var11 as CompoundTag).m_128359_("id", var19.id().toString());
         var10.add(var11);
      }

      nbt.m_128365_("progress", var14 as Tag);
      return nbt as Tag;
   }

   public override fun loadFromNBT(nbt: Tag) {
      this.clear();
      val var12: ListTag;
      val var13: ListTag;
      if (nbt is CompoundTag) {
         var var10000: ListTag = (nbt as CompoundTag).m_128437_("pending", 8);
         var12 = var10000;
         var10000 = (nbt as CompoundTag).m_128437_("progress", 10);
         var13 = var10000;
      } else {
         val var27: ListTag = nbt as? ListTag;
         if ((nbt as? ListTag) == null) {
            return;
         }

         var12 = var27;
         var13 = new ListTag();
      }

      var tag: java.lang.Iterable = var12 as java.lang.Iterable;
      var progress: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$filterIsInstance$iv) {
         if (`element$iv$iv` is StringTag) {
            progress.add(`element$iv$iv`);
         }
      }

      for (StringTag tagx : (java.util.List)destination$iv$iv) {
         val var18: java.lang.String = tagx.m_7916_();
         val var28: Evolution = this.findEvolutionFromId(var18);
         if (var28 != null) {
            this.add(var28);
         }
      }

      tag = var13 as java.lang.Iterable;
      progress = new ArrayList();

      for (Object element$iv$ivx : $this$filterIsInstance$iv) {
         if (`element$iv$ivx` is CompoundTag) {
            progress.add(`element$iv$ivx`);
         }
      }

      for (CompoundTag tagxx : (java.util.List)destination$iv$iv) {
         val var29: EvolutionProgressFactory = EvolutionProgressFactory.INSTANCE;
         val var10001: java.lang.String = tagxx.m_128461_("id");
         val var30: EvolutionProgress = var29.create(var10001);
         if (var30 != null) {
            var30.loadFromNBT(tagxx as Tag);
            if (var30.shouldKeep(this.getPokemon())) {
               this.progress.add(var30);
            }
         }
      }
   }

   public override fun saveToJson(): JsonElement {
      val json: JsonObject = new JsonObject();
      val progressArray: java.lang.Iterable = this.evolutions;
      val `$this$mapTo$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(this.evolutions, 10));

      for (Object item$iv$iv : progressArray) {
         `$this$mapTo$iv$iv`.add((`item$iv$iv` as Evolution).getId());
      }

      json.add("pending", JsonExtensionsKt.toJsonArrayString(`$this$mapTo$iv$iv`) as JsonElement);
      val var18: java.lang.Iterable = this.progress;
      val `destination$iv$ivx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(this.progress, 10));

      for (Object item$iv$iv : var18) {
         val var13: JsonElement = (var22 as EvolutionProgress).saveToJson();
         (var13 as JsonObject).addProperty("id", (var22 as EvolutionProgress).id().toString());
         `destination$iv$ivx`.add(var13 as JsonObject);
      }

      json.add("progress", JsonExtensionsKt.toJsonArrayJsonElement(`destination$iv$ivx`) as JsonElement);
      return json as JsonElement;
   }

   public override fun loadFromJson(json: JsonElement) {
      this.clear();
      val var10: JsonArray;
      val var11: JsonArray;
      if (json is JsonObject) {
         var var10000: JsonArray = (json as JsonObject).getAsJsonArray("pending");
         var10 = var10000;
         var10000 = (json as JsonObject).getAsJsonArray("progress");
         var11 = var10000;
      } else {
         val var16: JsonArray = json as? JsonArray;
         if ((json as? JsonArray) == null) {
            return;
         }

         var10 = var16;
         var11 = new JsonArray();
      }

      for (JsonElement element : var10) {
         val var17: java.lang.String = if ((element as? JsonPrimitive) != null) (element as? JsonPrimitive).getAsString() else null;
         if (var17 != null) {
            val var18: Evolution = this.findEvolutionFromId(var17);
            if (var18 != null) {
               this.add(var18);
            }
         }
      }

      for (JsonElement elementx : var11) {
         val var19: JsonObject = elementx as? JsonObject;
         if ((elementx as? JsonObject) != null) {
            val var20: EvolutionProgressFactory = EvolutionProgressFactory.INSTANCE;
            val var10001: java.lang.String = var19.get("id").getAsString();
            val var21: EvolutionProgress = var20.create(var10001);
            if (var21 != null) {
               var21.loadFromJson(var19 as JsonElement);
               if (var21.shouldKeep(this.getPokemon())) {
                  this.progress.add(var21);
               }
            }
         }
      }
   }

   public override fun saveToBuffer(buffer: FriendlyByteBuf, toClient: Boolean) {
      if (toClient) {
         buffer.m_236828_(this.evolutions, ServerEvolutionController::saveToBuffer$lambda$9);
      }
   }

   public override fun loadFromBuffer(buffer: FriendlyByteBuf) {
   }

   public open fun add(element: Evolution): Boolean {
      if (this.evolutions.add(element)) {
         var var10000: ServerPlayer = this.getPokemon().getOwnerPlayer();
         if (var10000 != null) {
            val var10001: MutableComponent = MiscUtilsKt.asTranslated("cobblemon.ui.evolve.hint", this.getPokemon().getDisplayName());
            var10000.m_213846_(TextKt.green(var10001) as Component);
         }

         this.getPokemon().notify(new AddEvolutionPacket(this.getPokemon(), element));
         var10000 = this.getPokemon().getOwnerPlayer();
         if (var10000 != null) {
            var10000.m_6330_(CobblemonSounds.CAN_EVOLVE, SoundSource.NEUTRAL, 1.0F, 1.0F);
         }

         return true;
      } else {
         return false;
      }
   }

   public override fun addAll(elements: Collection<Evolution>): Boolean {
      var result: Boolean = false;

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         if (this.add(`element$iv` as Evolution)) {
            result = true;
         }
      }

      return result;
   }

   public override fun clear() {
      val pokemon: Pokemon = this.getPokemon();
      if (!this.evolutions.isEmpty()) {
         this.evolutions.clear();
         this.getPokemon().notify(new ClearEvolutionsPacket((new Function0<Pokemon>(pokemon) {
            {
               super(0);
               this.$pokemon = `$pokemon`;
            }

            @NotNull
            public final Pokemon invoke() {
               return this.$pokemon;
            }
         }) as () -> Pokemon));
      }

      this.progress.clear();
   }

   public open operator fun contains(element: Evolution): Boolean {
      return this.evolutions.contains(element);
   }

   public override fun containsAll(elements: Collection<Evolution>): Boolean {
      return this.evolutions.containsAll(elements);
   }

   public override fun isEmpty(): Boolean {
      return this.evolutions.isEmpty();
   }

   public override operator fun iterator(): MutableIterator<Evolution> {
      val var10000: java.util.Iterator = this.evolutions.iterator();
      return var10000;
   }

   public open fun remove(element: Evolution): Boolean {
      if (this.evolutions.remove(element)) {
         this.getPokemon().notify(new RemoveEvolutionPacket(this.getPokemon(), element));
         return true;
      } else {
         return false;
      }
   }

   public override fun removeAll(elements: Collection<Evolution>): Boolean {
      var result: Boolean = false;

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         if (this.remove(`element$iv` as Evolution)) {
            result = true;
         }
      }

      return result;
   }

   public override fun retainAll(elements: Collection<Evolution>): Boolean {
      var result: Boolean = false;
      val comparedSet: java.util.Set = CollectionsKt.toSet(elements);
      val iterator: java.util.Iterator = this.iterator();

      while (iterator.hasNext()) {
         val var10000: Any = iterator.next();
         if (!comparedSet.contains(var10000 as Evolution)) {
            iterator.remove();
            result = true;
         }
      }

      return result;
   }

   private fun findEvolutionFromId(id: String): Evolution? {
      val var4: java.util.Iterator = this.getPokemon().getEvolutions().iterator();

      var var10000: Any;
      while (true) {
         if (var4.hasNext()) {
            val `element$iv`: Any = var4.next();
            if (!StringsKt.equals((`element$iv` as Evolution).getId(), id, true)) {
               continue;
            }

            var10000 = `element$iv`;
            break;
         }

         var10000 = null;
         break;
      }

      return var10000 as Evolution;
   }

   @JvmStatic
   fun `saveToBuffer$lambda$9`(`this$0`: ServerEvolutionController, pb: FriendlyByteBuf, value: Evolution) {
      val var10000: AddEvolutionPacket.Companion = AddEvolutionPacket.Companion;
      val var10001: AddEvolutionPacket.Companion = AddEvolutionPacket.Companion;
      val var3: EvolutionDisplay = var10001.convertToDisplay$common(value, `this$0`.getPokemon());
      var10000.encode$common(var3, pb);
   }

   override fun <T> toArray(array: Array<T>): Array<T> {
      return (T[])CollectionToArray.toArray(this, array);
   }

   override fun toArray(): Array<Any> {
      return CollectionToArray.toArray(this);
   }

   public companion object {
      private const val ID: String
      private const val PENDING: String
      private const val PROGRESS: String
   }
}
