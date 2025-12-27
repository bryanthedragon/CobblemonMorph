package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties

public interface CustomPokemonPropertyType<T extends CustomPokemonProperty> {
   public val keys: Iterable<String>
   public val needsKey: Boolean

   public abstract fun fromString(value: String?): Any? {
   }

   public abstract fun examples(): Collection<String> {
   }
}
