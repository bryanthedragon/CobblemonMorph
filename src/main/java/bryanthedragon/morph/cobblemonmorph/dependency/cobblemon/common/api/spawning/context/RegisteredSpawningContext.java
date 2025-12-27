package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon

public data RegisteredSpawningContext<T extends SpawningContext>(name: String, clazz: Class<Any>, defaultCondition: String) {
   public final val clazz: Class<Any>
   public final val defaultCondition: String
   public final val name: String

   init {
      this.name = name;
      this.clazz = clazz;
      this.defaultCondition = defaultCondition;
   }

   public fun getWeight(): Float {
      val var10000: java.lang.Float = Cobblemon.INSTANCE.getBestSpawner().getConfig().getContextWeights().get(this.name);
      return var10000 ?: 1.0F;
   }

   public operator fun component1(): String {
      return this.name;
   }

   public operator fun component2(): Class<Any> {
      return this.clazz;
   }

   public operator fun component3(): String {
      return this.defaultCondition;
   }

   public fun copy(name: String = this.name, clazz: Class<Any> = this.clazz, defaultCondition: String = this.defaultCondition): RegisteredSpawningContext<Any> {
      return new RegisteredSpawningContext<>(name, clazz, defaultCondition);
   }

   public override fun toString(): String {
      return "RegisteredSpawningContext(name=${this.name}, clazz=${this.clazz}, defaultCondition=${this.defaultCondition})";
   }

   public override fun hashCode(): Int {
      return (this.name.hashCode() * 31 + this.clazz.hashCode()) * 31 + this.defaultCondition.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is RegisteredSpawningContext) {
         return false;
      } else {
         val var2: RegisteredSpawningContext = other as RegisteredSpawningContext;
         if (!(this.name == (other as RegisteredSpawningContext).name)) {
            return false;
         } else if (!(this.clazz == var2.clazz)) {
            return false;
         } else {
            return this.defaultCondition == var2.defaultCondition;
         }
      }
   }
}
