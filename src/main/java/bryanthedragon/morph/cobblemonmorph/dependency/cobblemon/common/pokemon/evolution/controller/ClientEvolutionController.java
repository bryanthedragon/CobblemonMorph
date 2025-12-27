package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.controller

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionController
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionDisplay
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.progress.EvolutionProgress
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pokemon.update.evolution.AcceptEvolutionPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import java.util.HashSet
import kotlin.jvm.internal.CollectionToArray
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.network.FriendlyByteBuf

@SourceDebugExtension(["SMAP\nClientEvolutionController.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ClientEvolutionController.kt\ncom/cobblemon/mod/common/pokemon/evolution/controller/ClientEvolutionController\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,96:1\n1855#2,2:97\n*S KotlinDebug\n*F\n+ 1 ClientEvolutionController.kt\ncom/cobblemon/mod/common/pokemon/evolution/controller/ClientEvolutionController\n*L\n71#1:97,2\n*E\n"])
public class ClientEvolutionController(pokemon: Pokemon) : EvolutionController<EvolutionDisplay> {
   private final val evolutions: HashSet<EvolutionDisplay>
   public open val pokemon: Pokemon

   public open val size: Int
      public open get() {
         return this.evolutions.size();
      }


   init {
      this.pokemon = pokemon;
      this.evolutions = new HashSet<>();
   }

   public open fun start(evolution: EvolutionDisplay) {
      CobblemonNetwork.INSTANCE.sendPacketToServer(new AcceptEvolutionPacket(this.getPokemon(), evolution));
   }

   public override fun progress(): Collection<EvolutionProgress<*>> {
      return CollectionsKt.emptyList();
   }

   public override fun <P : EvolutionProgress<*>> trackProgress(progress: Any): Any {
      return (P)progress;
   }

   public override fun <P : EvolutionProgress<*>> progressFirstOrCreate(predicate: (EvolutionProgress<*>) -> Boolean, progressFactory: () -> Any): Any {
      return (P)(progressFactory.invoke() as EvolutionProgress);
   }

   public override fun saveToNBT(): Tag {
      return (new CompoundTag()) as Tag;
   }

   public override fun loadFromNBT(nbt: Tag) {
   }

   public override fun saveToJson(): JsonElement {
      return (new JsonArray()) as JsonElement;
   }

   public override fun loadFromJson(json: JsonElement) {
   }

   public override fun saveToBuffer(buffer: FriendlyByteBuf, toClient: Boolean) {
   }

   public override fun loadFromBuffer(buffer: FriendlyByteBuf) {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val it: EvolutionDisplay = `element$iv` as EvolutionDisplay;
         this.add(it);
      }
   }

   public open fun add(element: EvolutionDisplay): Boolean {
      return this.evolutions.add(element);
   }

   public override fun addAll(elements: Collection<EvolutionDisplay>): Boolean {
      return this.evolutions.addAll(elements);
   }

   public override fun clear() {
      this.evolutions.clear();
   }

   public override operator fun iterator(): MutableIterator<EvolutionDisplay> {
      val var10000: java.util.Iterator = this.evolutions.iterator();
      return var10000;
   }

   public open fun remove(element: EvolutionDisplay): Boolean {
      return this.evolutions.remove(element);
   }

   public override fun removeAll(elements: Collection<EvolutionDisplay>): Boolean {
      return this.evolutions.removeAll(CollectionsKt.toSet(elements));
   }

   public override fun retainAll(elements: Collection<EvolutionDisplay>): Boolean {
      return this.evolutions.retainAll(CollectionsKt.toSet(elements));
   }

   public open operator fun contains(element: EvolutionDisplay): Boolean {
      return this.evolutions.contains(element);
   }

   public override fun containsAll(elements: Collection<EvolutionDisplay>): Boolean {
      return this.evolutions.containsAll(elements);
   }

   public override fun isEmpty(): Boolean {
      return this.evolutions.isEmpty();
   }

   override fun <T> toArray(array: Array<T>): Array<T> {
      return (T[])CollectionToArray.toArray(this, array);
   }

   override fun toArray(): Array<Any> {
      return CollectionToArray.toArray(this);
   }
}
