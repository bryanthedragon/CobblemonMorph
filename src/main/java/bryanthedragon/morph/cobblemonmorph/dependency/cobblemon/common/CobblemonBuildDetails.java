package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;
public object CobblemonBuildDetails {
   public const val BRANCH: String = "HEAD"
   public const val GIT_COMMIT: String = "df8f078d13702ab9a000438910b822ceffbb2248"
   public const val MOD_ID: String = "cobblemon"
   public const val SNAPSHOT: Boolean = false
   public const val VERSION: String = "1.5.2"

   public fun smallCommitHash(): String {
      val var10000: java.lang.String = "df8f078d13702ab9a000438910b822ceffbb2248".substring(0, 7);
      return var10000;
   }
}
