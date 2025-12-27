package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity

import com.bedrockk.molang.runtime.MoLangEnvironment
import com.bedrockk.molang.runtime.struct.QueryStruct

public interface MoLangEntity {
   public abstract fun applyQueries(queries: QueryStruct) {
   }

   public abstract fun applyVariables(environment: MoLangEnvironment) {
   }
}
