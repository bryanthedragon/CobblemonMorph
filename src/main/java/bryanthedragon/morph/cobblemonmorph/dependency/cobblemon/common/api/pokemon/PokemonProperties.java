package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon

import com.bedrockk.molang.runtime.struct.VariableStruct
import com.bedrockk.molang.runtime.value.DoubleValue
import com.bedrockk.molang.runtime.value.StringValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Abilities
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Ability
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbility
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.aspect.AspectProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonProperty
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonPropertyType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.tera.TeraType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.tera.TeraTypes
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.EVs
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Gender
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.IVs
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Nature
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.OriginalTrainerType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.PokemonStats
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.RenderablePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DistributionUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.StringExtensionsKt
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.mojang.authlib.GameProfile
import java.util.ArrayList;
import java.util.LinkedHashSet
import java.util.Locale
import java.util.NoSuchElementException
import java.util.Optional
import java.util.UUID
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.random.Random
import net.minecraft.ResourceLocationException
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.StringTag
import net.minecraft.nbt.Tag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.Component.Serializer
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.MinecraftServer
import net.minecraft.server.players.GameProfileCache
import net.minecraft.world.level.Level

@SourceDebugExtension(["SMAP\nPokemonProperties.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonProperties.kt\ncom/cobblemon/mod/common/api/pokemon/PokemonProperties\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,675:1\n1855#2,2:676\n1855#2,2:678\n288#2,2:681\n1855#2,2:683\n1855#2,2:685\n1855#2,2:687\n2624#2,3:689\n2624#2,3:692\n1855#2,2:695\n1855#2,2:697\n1855#2,2:699\n1855#2,2:701\n1549#2:703\n1620#2,3:704\n1855#2,2:707\n1855#2,2:709\n1549#2:711\n1620#2,3:712\n1855#2,2:715\n1855#2,2:717\n1855#2,2:719\n1855#2,2:721\n1855#2,2:723\n1855#2,2:725\n288#2,2:727\n1#3:680\n*S KotlinDebug\n*F\n+ 1 PokemonProperties.kt\ncom/cobblemon/mod/common/api/pokemon/PokemonProperties\n*L\n300#1:676,2\n306#1:678,2\n323#1:681,2\n332#1:683,2\n334#1:685,2\n339#1:687,2\n374#1:689,3\n379#1:692,3\n407#1:695,2\n410#1:697,2\n448#1:699,2\n455#1:701,2\n515#1:703\n515#1:704,3\n515#1:707,2\n544#1:709,2\n573#1:711\n573#1:712,3\n573#1:715,2\n603#1:717,2\n621#1:719,2\n624#1:721,2\n633#1:723,2\n650#1:725,2\n670#1:727,2\n*E\n"])
public open class PokemonProperties {
   public final var ability: String?
   public final var aspects: Set<String> = SetsKt.emptySet()
   public final var customProperties: MutableList<CustomPokemonProperty> = (new ArrayList()) as java.util.List
   public final var dmaxLevel: Int?
   public final var evs: EVs?
   public final var form: String?
   public final var friendship: Int?
   public final var gender: Gender?
   public final var gmaxFactor: Boolean?
   public final var ivs: IVs?
   public final var level: Int?
   public final var nature: String?
   public final var nickname: MutableComponent?
   public final var originalString: String = ""
   public final var originalTrainer: String?
   public final var originalTrainerType: OriginalTrainerType?
   public final var pokeball: String?
   public final var shiny: Boolean?
   public final var species: String?
   public final var status: String?
   public final var teraType: String?
   public final var tradeable: Boolean?

   public fun asRenderablePokemon(): RenderablePokemon {
      var var10000: Species;
      label20: {
         if (this.species != null) {
            val it: java.lang.String = this.species;

            var var3: Species;
            try {
               var3 = PokemonSpecies.INSTANCE.getByIdentifier(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(it, null, 1, null));
            } catch (var7: ResourceLocationException) {
               var3 = PokemonSpecies.INSTANCE.random();
            }

            var10000 = var3;
            if (var3 != null) {
               break label20;
            }
         }

         var10000 = PokemonSpecies.INSTANCE.random();
      }

      return new RenderablePokemon(var10000, this.aspects);
   }

   public fun apply(pokemon: Pokemon) {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         (`element$iv` as CustomPokemonProperty).apply(pokemon);
      }

      this.commonApply(pokemon);
   }

   public fun apply(pokemonEntity: PokemonEntity) {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         (`element$iv` as CustomPokemonProperty).apply(pokemonEntity);
      }

      this.commonApply(pokemonEntity.getPokemon());
   }

   private fun commonApply(pokemon: Pokemon) {
      if (this.species != null) {
         val var5: java.lang.String = this.species;

         var var7: Species;
         try {
            var7 = if (var5 == "random")
               CollectionsKt.random(PokemonSpecies.INSTANCE.getImplemented(), Random.Default as Random) as Species
               else
               PokemonSpecies.INSTANCE.getByIdentifier(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(var5, null, 1, null));
         } catch (var13: ResourceLocationException) {
            var7 = null;
         }

         if (var7 != null) {
            pokemon.setSpecies(var7);
         }
      }

      if (this.nickname != null) {
         pokemon.setNickname(this.nickname);
      }

      if (this.form != null) {
         val var33: java.lang.String = this.form;
         val uuid: java.util.Iterator = pokemon.getSpecies().getForms().iterator();

         var var10000: Any;
         while (true) {
            if (!uuid.hasNext()) {
               var10000 = null;
               break;
            }

            val var10: Any = uuid.next();
            if (StringsKt.equals((var10 as FormData).formOnlyShowdownId(), var33, true)) {
               var10000 = var10;
               break;
            }
         }

         var10000 = var10000 as FormData;
         if (var10000 as FormData != null) {
            pokemon.setForm((FormData)var10000);
         }
      }

      if (this.shiny != null) {
         pokemon.setShiny(this.shiny);
      }

      if (this.gender != null) {
         pokemon.setGender(this.gender);
      }

      if (this.level != null) {
         pokemon.setLevel(this.level.intValue());
      }

      if (this.friendship != null) {
         Pokemon.setFriendship$default(pokemon, this.friendship.intValue(), false, 2, null);
      }

      if (this.pokeball != null) {
         val var85: PokeBall = PokeBalls.INSTANCE
            .getPokeBall(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(this.pokeball, null, 1, null));
         if (var85 != null) {
            pokemon.setCaughtBall(var85);
         }
      }

      if (this.nature != null) {
         val var86: Nature = Natures.INSTANCE.getNature(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(this.nature, null, 1, null));
         if (var86 != null) {
            pokemon.setNature(var86);
         }
      }

      if (this.ability != null) {
         val var87: Ability = this.createAbility(this.ability, pokemon.getForm());
         if (var87 != null) {
            pokemon.updateAbility(var87);
         }
      }

      if (this.status != null) {
         val var88: Status = Statuses.INSTANCE.getStatus(this.status);
         if (var88 != null) {
            if (var88 is PersistentStatus) {
               pokemon.applyStatus(var88 as PersistentStatus);
            }
         }
      }

      val var14: java.lang.Iterable;
      for (Object element$iv : var14) {
         (var43 as CustomPokemonProperty).apply(pokemon);
      }

      if (this.ivs != null) {
         val var60: java.lang.Iterable;
         for (Object element$iv : var60) {
            pokemon.setIV((var77 as Entry).getKey() as Stat, ((var77 as Entry).getValue() as java.lang.Number).intValue());
         }
      }

      if (this.evs != null) {
         val var61: java.lang.Iterable;
         for (Object element$iv : var61) {
            pokemon.setEV((var78 as Entry).getKey() as Stat, ((var78 as Entry).getValue() as java.lang.Number).intValue());
         }
      }

      if (this.teraType != null) {
         val var89: TeraType = TeraTypes.get(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(this.teraType, null, 1, null));
         if (var89 != null) {
            pokemon.setTeraType(var89);
         }
      }

      if (this.dmaxLevel != null) {
         pokemon.setDmaxLevel(this.dmaxLevel.intValue());
      }

      if (this.gmaxFactor != null) {
         pokemon.setGmaxFactor(this.gmaxFactor);
      }

      if (this.tradeable != null) {
         pokemon.setTradeable(this.tradeable);
      }

      if (this.originalTrainerType != null) {
         if (this.originalTrainerType === OriginalTrainerType.NONE) {
            pokemon.removeOriginalTrainer();
            this.originalTrainer = null;
         }
      }

      if (this.originalTrainer != null) {
         val var31: java.lang.String = this.originalTrainer;
         var var90: OriginalTrainerType = this.originalTrainerType;
         if (this.originalTrainerType == null) {
            var90 = pokemon.getOriginalTrainerType();
         }

         switch (PokemonProperties.WhenMappings.$EnumSwitchMapping$0[var90.ordinal()]) {
            case 1:
               val var73: Int = var31.length();
               label178:
               if (3 <= var73 && var73 < 17) {
                  val var91: MinecraftServer = DistributionUtilsKt.server();
                  if (var91 != null) {
                     val var92: GameProfileCache = var91.m_129927_();
                     if (var92 != null) {
                        val var93: Optional = var92.m_10996_(var31);
                        if (var93 != null) {
                           val var94: GameProfile = var93.get() as GameProfile;
                           if (var94 != null) {
                              var95 = var94.getId();
                              break label178;
                           }
                        }
                     }
                  }

                  var95 = null;
               } else {
                  var95 = if (var73 == 36) UUID.fromString(var31) else null;
               }

               if (var95 != null) {
                  pokemon.setOriginalTrainer(var95);
               }
               break;
            case 2:
               pokemon.setOriginalTrainer(var31);
            default:
         }

         pokemon.refreshOriginalTrainer();
      }

      pokemon.updateAspects();
   }

   public fun matches(pokemon: Pokemon): Boolean {
      if (this.commonMatches(pokemon)) {
         val `$this$none$iv`: java.lang.Iterable = this.customProperties;
         var var10000: Boolean;
         if (this.customProperties is java.util.Collection && this.customProperties.isEmpty()) {
            var10000 = true;
         } else {
            label42: {
               for (Object element$iv : $this$none$iv) {
                  if (!(`element$iv` as CustomPokemonProperty).matches(pokemon)) {
                     var10000 = false;
                     break label42;
                  }
               }

               var10000 = true;
            }
         }

         if (var10000) {
            return true;
         }
      }

      return false;
   }

   public fun matches(pokemonEntity: PokemonEntity): Boolean {
      if (this.commonMatches(pokemonEntity.getPokemon())) {
         val `$this$none$iv`: java.lang.Iterable = this.customProperties;
         var var10000: Boolean;
         if (this.customProperties is java.util.Collection && this.customProperties.isEmpty()) {
            var10000 = true;
         } else {
            label42: {
               for (Object element$iv : $this$none$iv) {
                  if (!(`element$iv` as CustomPokemonProperty).matches(pokemonEntity)) {
                     var10000 = false;
                     break label42;
                  }
               }

               var10000 = true;
            }
         }

         if (var10000) {
            return true;
         }
      }

      return false;
   }

   private fun commonMatches(pokemon: Pokemon): Boolean {
      label373: {
         var var2: Int = this.level;
         if (this.level != null) {
            val `$this$forEach$iv`: Int = if (this.level.intValue() != pokemon.getLevel()) var2 else null;
            if (`$this$forEach$iv` != null) {
               val var57: Int = `$this$forEach$iv`.intValue();
               return false;
            }
         }

         val var10: java.lang.Boolean = this.shiny;
         if (this.shiny != null) {
            val var25: java.lang.Boolean = if (this.shiny != pokemon.getShiny()) var10 else null;
            if (var25 != null) {
               val var56: Boolean = var25;
               return false;
            }
         }

         val var11: Gender = this.gender;
         if (this.gender != null && (if (this.gender != pokemon.getGender()) var11 else null) != null) {
            return false;
         } else {
            if (this.species != null) {
               val `$i$f$forEach`: java.lang.String = this.species;

               try {
                  label355: {
                     val var10000: Species;
                     if (`$i$f$forEach` == "random") {
                        var10000 = CollectionsKt.random(PokemonSpecies.INSTANCE.getSpecies(), Random.Default as Random) as Species;
                     } else {
                        var10000 = PokemonSpecies.INSTANCE
                           .getByIdentifier(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(`$i$f$forEach`, null, 1, null));
                        if (var10000 == null) {
                           break label355;
                        }
                     }

                     if (!(pokemon.getSpecies() == var10000)) {
                        return false;
                     }
                  }
               } catch (var9: ResourceLocationException) {
                  return false;
               }
            }

            val var12: MutableComponent = this.nickname;
            if (this.nickname != null) {
               val var94: java.lang.String = this.nickname.getString();
               val var10001: MutableComponent = pokemon.getNickname();
               if ((if (!(var94 == (if (var10001 != null) var10001.getString() else null))) var12 else null) != null) {
                  return false;
               }
            }

            val var13: java.lang.String = this.form;
            if (this.form != null && (if (!StringsKt.equals(this.form, pokemon.getForm().getName(), true)) var13 else null) != null) {
               return false;
            } else {
               var2 = this.friendship;
               if (this.friendship != null) {
                  val var29: Int = if (this.friendship.intValue() != pokemon.getFriendship()) var2 else null;
                  if (var29 != null) {
                     val var55: Int = var29.intValue();
                     return false;
                  }
               }

               val var15: java.lang.String = this.pokeball;
               if (this.pokeball != null && (if (!(this.pokeball == pokemon.getCaughtBall().getName().toString())) var15 else null) != null) {
                  return false;
               } else {
                  val var16: java.lang.String = this.nature;
                  if (this.nature != null && (if (!(this.nature == pokemon.getNature().getName().toString())) var16 else null) != null) {
                     return false;
                  } else {
                     val var17: java.lang.String = this.ability;
                     if (this.ability != null && (if (!(this.ability == pokemon.getAbility().getName())) var17 else null) != null) {
                        return false;
                     } else {
                        val var18: java.lang.String = this.status;
                        if (this.status != null) {
                           var var99: java.lang.String;
                           label318: {
                              val var97: PersistentStatusContainer = pokemon.getStatus();
                              if (var97 != null) {
                                 val var98: PersistentStatus = var97.getStatus();
                                 if (var98 != null) {
                                    var99 = var98.getShowdownName();
                                    break label318;
                                 }
                              }

                              var99 = null;
                           }

                           if ((if (!(var18 == var99)) var18 else null) != null) {
                              return false;
                           }
                        }

                        if (this.ivs != null) {
                           val var34: java.lang.Iterable;
                           for (Object element$iv : var34) {
                              val var95: Int = ((var68 as Entry).getValue() as java.lang.Number).intValue();
                              val var100: Int = pokemon.getIvs().get((var68 as Entry).getKey() as Stat);
                              if (var100 != null) {
                                 if (var95 == var100) {
                                    continue;
                                 }
                              }

                              return false;
                           }
                        }

                        if (this.evs != null) {
                           val var35: java.lang.Iterable;
                           for (Object element$iv : var35) {
                              val var96: Int = ((var69 as Entry).getValue() as java.lang.Number).intValue();
                              val var101: Int = pokemon.getEvs().get((var69 as Entry).getKey() as Stat);
                              if (var101 != null) {
                                 if (var96 == var101) {
                                    continue;
                                 }
                              }

                              return false;
                           }
                        }

                        val var19: java.lang.String = this.teraType;
                        if (this.teraType != null
                           && (
                                 if (!(
                                       ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(this.teraType, null, 1, null)
                                          == pokemon.getTeraType().getId()
                                    ))
                                    var19
                                    else
                                    null
                              )
                              != null) {
                           return false;
                        } else {
                           var2 = this.dmaxLevel;
                           if (this.dmaxLevel != null) {
                              val var37: Int = if (this.dmaxLevel.intValue() != pokemon.getDmaxLevel()) var2 else null;
                              if (var37 != null) {
                                 val var54: Int = var37.intValue();
                                 return false;
                              }
                           }

                           val var21: java.lang.Boolean = this.gmaxFactor;
                           if (this.gmaxFactor != null) {
                              val var38: java.lang.Boolean = if (this.gmaxFactor != pokemon.getGmaxFactor()) var21 else null;
                              if (var38 != null) {
                                 val var53: Boolean = var38;
                                 return false;
                              }
                           }

                           val var22: java.lang.Boolean = this.tradeable;
                           if (this.tradeable != null) {
                              val var39: java.lang.Boolean = if (this.tradeable != pokemon.getTradeable()) var22 else null;
                              if (var39 != null) {
                                 val var52: Boolean = var39;
                                 return false;
                              }
                           }

                           val var23: java.lang.String = this.originalTrainer;
                           if (this.originalTrainer != null && (if (!(this.originalTrainer == pokemon.getOriginalTrainer())) var23 else null) != null) {
                              return false;
                           } else {
                              val var24: OriginalTrainerType = this.originalTrainerType;
                              return this.originalTrainerType == null
                                 || (if (this.originalTrainerType != pokemon.getOriginalTrainerType()) var24 else null) == null;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public fun isSubSetOf(properties: PokemonProperties): Boolean {
      label359: {
         var var2: Int = this.level;
         if (this.level != null) {
            var var10000: Boolean;
            label355: {
               val it: Int = this.level.intValue();
               if (properties.level != null) {
                  if (it == properties.level) {
                     var10000 = false;
                     break label355;
                  }
               }

               var10000 = true;
            }

            val var3: Int = if (var10000) var2 else null;
            if ((if (var10000) var2 else null) != null) {
               val var59: Int = var3.intValue();
               return false;
            }
         }

         val var14: java.lang.Boolean = this.shiny;
         if (this.shiny != null) {
            val var29: java.lang.Boolean = if (this.shiny != properties.aspects.contains("shiny")) var14 else null;
            if (var29 != null) {
               val var58: Boolean = var29;
               return false;
            }
         }

         if (this.gender != null && (if (this.gender != properties.gender) this.gender else null) != null) {
            return false;
         } else {
            if (this.species != null) {
               val evs: java.lang.String = this.species;

               try {
                  label343: {
                     val var100: Species;
                     if (evs == "random") {
                        var100 = CollectionsKt.random(PokemonSpecies.INSTANCE.getSpecies(), Random.Default as Random) as Species;
                     } else {
                        var100 = PokemonSpecies.INSTANCE
                           .getByIdentifier(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(evs, null, 1, null));
                        if (var100 == null) {
                           break label343;
                        }
                     }

                     if (!(properties.species == var100.toString())) {
                        return false;
                     }
                  }
               } catch (var13: ResourceLocationException) {
               }
            }

            val var16: MutableComponent = this.nickname;
            if (this.nickname != null
               && (if (!(this.nickname.getString() == (if (properties.nickname != null) properties.nickname.getString() else null))) var16 else null) != null) {
               return false;
            } else {
               val var17: java.lang.String = this.form;
               if (this.form != null && (if (!StringsKt.equals(this.form, properties.form, true)) var17 else null) != null) {
                  return false;
               } else {
                  var2 = this.friendship;
                  if (this.friendship != null) {
                     var var101: Boolean;
                     label333: {
                        val var48: Int = this.friendship.intValue();
                        if (properties.friendship != null) {
                           if (var48 == properties.friendship) {
                              var101 = false;
                              break label333;
                           }
                        }

                        var101 = true;
                     }

                     val var33: Int = if (var101) var2 else null;
                     if ((if (var101) var2 else null) != null) {
                        val var57: Int = var33.intValue();
                        return false;
                     }
                  }

                  val var19: java.lang.String = this.pokeball;
                  if (this.pokeball != null && (if (!(this.pokeball == properties.pokeball)) var19 else null) != null) {
                     return false;
                  } else {
                     val var20: java.lang.String = this.nature;
                     if (this.nature != null && (if (!(this.nature == properties.nature)) var20 else null) != null) {
                        return false;
                     } else {
                        val var21: java.lang.String = this.ability;
                        if (this.ability != null && (if (!(this.ability == properties.ability)) var21 else null) != null) {
                           return false;
                        } else {
                           val var22: java.lang.String = this.status;
                           if (this.status != null && (if (!(this.status == properties.status)) var22 else null) != null) {
                              return false;
                           } else {
                              if (this.ivs != null) {
                                 val var70: java.lang.Iterable;
                                 for (Object element$iv : var70) {
                                    val stat: Entry = `element$iv` as Entry;
                                    if (properties.ivs == null) {
                                       return false;
                                    }

                                    val propertiesEVs: IVs = properties.ivs;
                                    val var102: Int = (stat.getValue() as java.lang.Number).intValue();
                                    val var10001: Int = propertiesEVs.get(stat.getKey() as Stat);
                                    if (var10001 != null) {
                                       if (var102 == var10001) {
                                          continue;
                                       }
                                    }

                                    return false;
                                 }
                              }

                              if (this.evs != null) {
                                 val var71: java.lang.Iterable;
                                 for (Object element$iv : var71) {
                                    val statx: Entry = var96 as Entry;
                                    if (properties.evs == null) {
                                       return false;
                                    }

                                    val var99: EVs = properties.evs;
                                    val var103: Int = (statx.getValue() as java.lang.Number).intValue();
                                    val var105: Int = var99.get(statx.getKey() as Stat);
                                    if (var105 != null) {
                                       if (var103 == var105) {
                                          continue;
                                       }
                                    }

                                    return false;
                                 }
                              }

                              val var23: java.lang.String = this.teraType;
                              if (this.teraType != null && (if (!(this.teraType == properties.teraType)) var23 else null) != null) {
                                 return false;
                              } else {
                                 var2 = this.dmaxLevel;
                                 if (this.dmaxLevel != null) {
                                    var var104: Boolean;
                                    label291: {
                                       val var51: Int = this.dmaxLevel.intValue();
                                       if (properties.dmaxLevel != null) {
                                          if (var51 == properties.dmaxLevel) {
                                             var104 = false;
                                             break label291;
                                          }
                                       }

                                       var104 = true;
                                    }

                                    val var39: Int = if (var104) var2 else null;
                                    if ((if (var104) var2 else null) != null) {
                                       val var56: Int = var39.intValue();
                                       return false;
                                    }
                                 }

                                 val var25: java.lang.Boolean = this.gmaxFactor;
                                 if (this.gmaxFactor != null) {
                                    val var40: java.lang.Boolean = if (!(this.gmaxFactor == properties.gmaxFactor)) var25 else null;
                                    if (var40 != null) {
                                       val var55: Boolean = var40;
                                       return false;
                                    }
                                 }

                                 val var26: java.lang.Boolean = this.tradeable;
                                 if (this.tradeable != null) {
                                    val var41: java.lang.Boolean = if (!(this.tradeable == properties.tradeable)) var26 else null;
                                    if (var41 != null) {
                                       val var54: Boolean = var41;
                                       return false;
                                    }
                                 }

                                 val var27: java.lang.String = this.originalTrainer;
                                 if (this.originalTrainer != null && (if (!(this.originalTrainer == properties.originalTrainer)) var27 else null) != null) {
                                    return false;
                                 } else {
                                    return this.originalTrainerType == null
                                       || (if (this.originalTrainerType != properties.originalTrainerType) this.originalTrainerType else null) == null;
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public fun create(): Pokemon {
      val pokemon: Pokemon = new Pokemon();
      this.apply(pokemon);
      pokemon.initialize();
      this.roll(pokemon);
      return pokemon;
   }

   public fun roll(pokemon: Pokemon) {
      val baseTypes: java.util.List = CollectionsKt.toList(pokemon.getForm().getTypes());
      if (this.shiny == null) {
         pokemon.setShiny(this.checkRate(Cobblemon.INSTANCE.getConfig().getShinyRate()));
      }

      if (this.teraType == null) {
         pokemon.setTeraType(
            if (this.checkRate(Cobblemon.INSTANCE.getConfig().getTeraTypeRate()))
               TeraTypes.random(true)
               else
               TeraTypes.forElementalType(CollectionsKt.random(baseTypes, Random.Default as Random) as ElementalType)
         );
      }
   }

   public fun createEntity(world: Level): PokemonEntity {
      return new PokemonEntity(world, this.create(), null, 4, null);
   }

   public fun saveToNBT(): CompoundTag {
      val nbt: CompoundTag = new CompoundTag();
      nbt.m_128359_("OriginalText", this.originalString);
      if (this.level != null) {
         nbt.m_128405_("Level", this.level.intValue());
      }

      if (this.shiny != null) {
         nbt.m_128379_("Shiny", this.shiny);
      }

      if (this.gender != null) {
         nbt.m_128359_("Gender", this.gender.name());
      }

      if (this.species != null) {
         nbt.m_128359_("SpeciesText", this.species);
      }

      if (this.nickname != null) {
         nbt.m_128359_("Nickname", Serializer.m_130703_(this.nickname as Component));
      }

      if (this.form != null) {
         nbt.m_128359_("FormId", this.form);
      }

      if (this.friendship != null) {
         nbt.m_128405_("Friendship", this.friendship.intValue());
      }

      if (this.pokeball != null) {
         nbt.m_128359_("CaughtBall", this.pokeball);
      }

      if (this.nature != null) {
         nbt.m_128359_("Nature", this.nature);
      }

      if (this.ability != null) {
         nbt.m_128359_("Ability", this.ability);
      }

      if (this.status != null) {
         nbt.m_128359_("StatusName", this.status);
      }

      if (this.ivs != null) {
         nbt.m_128365_("IVs", this.ivs.saveToNBT(new CompoundTag()) as Tag);
      }

      if (this.evs != null) {
         nbt.m_128365_("EVs", this.evs.saveToNBT(new CompoundTag()) as Tag);
      }

      if (this.teraType != null) {
         nbt.m_128359_("TeraType", this.teraType);
      }

      if (this.dmaxLevel != null) {
         nbt.m_128405_("DmaxLevel", this.dmaxLevel.intValue());
      }

      if (this.gmaxFactor != null) {
         nbt.m_128379_("GmaxFactor", this.gmaxFactor);
      }

      if (this.tradeable != null) {
         nbt.m_128379_("Tradeable", this.tradeable);
      }

      if (this.originalTrainerType != null) {
         nbt.m_128405_("PokemonOriginalTrainerType", this.originalTrainerType.ordinal());
      }

      if (this.originalTrainer != null) {
         nbt.m_128359_("PokemonOriginalTrainer", this.originalTrainer);
      }

      val custom: ListTag = new ListTag();
      val var13: java.lang.Iterable = this.customProperties;
      val `element$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(this.customProperties, 10));

      for (Object item$iv$iv : $this$map$iv) {
         `element$iv`.add(StringTag.m_129297_((`item$iv$iv` as CustomPokemonProperty).asString()));
      }

      for (Object element$ivx : $this$map$iv) {
         custom.add(`element$ivx` as StringTag);
      }

      nbt.m_128365_("CustomProperties", custom as Tag);
      return nbt;
   }

   public fun loadFromNBT(tag: CompoundTag): PokemonProperties {
      var var10001: java.lang.String = tag.m_128461_("OriginalText");
      this.originalString = var10001;
      this.level = if (tag.m_128441_("Level")) tag.m_128451_("Level") else null;
      this.shiny = if (tag.m_128441_("Shiny")) tag.m_128471_("Shiny") else null;
      val var10: Gender;
      if (tag.m_128441_("Gender")) {
         var10001 = tag.m_128461_("Gender");
         var10 = Gender.valueOf(var10001);
      } else {
         var10 = null;
      }

      this.gender = var10;
      this.species = if (tag.m_128441_("SpeciesText")) tag.m_128461_("SpeciesText") else null;
      this.nickname = if (tag.m_128441_("Nickname")) Serializer.m_130701_(tag.m_128461_("Nickname")) else null;
      this.form = if (tag.m_128441_("FormId")) tag.m_128461_("FormId") else null;
      this.friendship = if (tag.m_128441_("Friendship")) tag.m_128451_("Friendship") else null;
      this.pokeball = if (tag.m_128441_("CaughtBall")) tag.m_128461_("CaughtBall") else null;
      this.nature = if (tag.m_128441_("Nature")) tag.m_128461_("Nature") else null;
      this.ability = if (tag.m_128441_("Ability")) tag.m_128461_("Ability") else null;
      this.status = if (tag.m_128441_("StatusName")) tag.m_128461_("StatusName") else null;
      val var13: IVs;
      if (tag.m_128441_("IVs")) {
         val var11: IVs = this.ivs;
         val var12: PokemonStats;
         if (this.ivs != null) {
            val var10002: CompoundTag = tag.m_128469_("IVs");
            var12 = var11.loadFromNBT(var10002);
         } else {
            var12 = null;
         }

         var13 = var12 as IVs;
      } else {
         var13 = null;
      }

      this.ivs = var13;
      val var16: EVs;
      if (tag.m_128441_("EVs")) {
         val var14: EVs = this.evs;
         val var15: PokemonStats;
         if (this.evs != null) {
            val var20: CompoundTag = tag.m_128469_("EVs");
            var15 = var14.loadFromNBT(var20);
         } else {
            var15 = null;
         }

         var16 = var15 as EVs;
      } else {
         var16 = null;
      }

      this.evs = var16;
      this.teraType = if (tag.m_128441_("TeraType")) tag.m_128461_("TeraType") else null;
      this.dmaxLevel = if (tag.m_128441_("DmaxLevel")) tag.m_128451_("DmaxLevel") else null;
      this.gmaxFactor = if (tag.m_128441_("GmaxFactor")) tag.m_128471_("GmaxFactor") else null;
      this.tradeable = if (tag.m_128441_("Tradeable")) tag.m_128471_("Tradeable") else null;
      val var18: OriginalTrainerType;
      if (tag.m_128441_("PokemonOriginalTrainerType")) {
         var10001 = tag.m_128461_("PokemonOriginalTrainerType");
         var18 = OriginalTrainerType.valueOf(var10001);
      } else {
         var18 = null;
      }

      this.originalTrainerType = var18;
      this.originalTrainer = if (tag.m_128441_("PokemonOriginalTrainer")) tag.m_128461_("PokemonOriginalTrainer") else null;

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val it: Tag = `element$iv` as Tag;
         val var10000: java.util.List = this.customProperties;
         val var19: PokemonProperties.Companion = Companion;
         val var21: java.lang.String = it.m_7916_();
         var10000.addAll(PokemonProperties.Companion.parse$default(var19, var21, null, null, 6, null).customProperties);
      }

      this.updateAspects();
      return this;
   }

   public fun saveToJSON(): JsonObject {
      val json: JsonObject = new JsonObject();
      json.addProperty("OriginalText", this.originalString);
      if (this.level != null) {
         json.addProperty("Level", this.level.intValue());
      }

      if (this.shiny != null) {
         json.addProperty("Shiny", this.shiny);
      }

      if (this.gender != null) {
         json.addProperty("Gender", this.gender.name());
      }

      if (this.species != null) {
         json.addProperty("SpeciesText", this.species);
      }

      if (this.nickname != null) {
         json.addProperty("Nickname", Serializer.m_130703_(this.nickname as Component));
      }

      if (this.form != null) {
         json.addProperty("FormId", this.form);
      }

      if (this.friendship != null) {
         json.addProperty("Friendship", this.friendship.intValue());
      }

      if (this.pokeball != null) {
         json.addProperty("CaughtBall", this.pokeball);
      }

      if (this.nature != null) {
         json.addProperty("Nature", this.nature);
      }

      if (this.ability != null) {
         json.addProperty("Ability", this.ability);
      }

      if (this.status != null) {
         json.addProperty("StatusName", this.status);
      }

      if (this.ivs != null) {
         json.add("IVs", this.ivs.saveToJSON(new JsonObject()) as JsonElement);
      }

      if (this.evs != null) {
         json.add("EVs", this.evs.saveToJSON(new JsonObject()) as JsonElement);
      }

      if (this.teraType != null) {
         json.addProperty("TeraType", this.teraType);
      }

      if (this.dmaxLevel != null) {
         json.addProperty("DmaxLevel", this.dmaxLevel.intValue());
      }

      if (this.gmaxFactor != null) {
         json.addProperty("GmaxFactor", this.gmaxFactor);
      }

      if (this.tradeable != null) {
         json.addProperty("Tradeable", this.tradeable);
      }

      if (this.originalTrainerType != null) {
         json.addProperty("PokemonOriginalTrainerType", this.originalTrainerType.name());
      }

      if (this.originalTrainer != null) {
         json.addProperty("PokemonOriginalTrainer", this.originalTrainer);
      }

      val custom: JsonArray = new JsonArray();
      val var13: java.lang.Iterable = this.customProperties;
      val `element$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(this.customProperties, 10));

      for (Object item$iv$iv : $this$map$iv) {
         `element$iv`.add((`item$iv$iv` as CustomPokemonProperty).asString());
      }

      for (Object element$ivx : $this$map$iv) {
         custom.add(`element$ivx` as java.lang.String);
      }

      json.add("CustomProperties", custom as JsonElement);
      return json;
   }

   public fun loadFromJSON(json: JsonObject): PokemonProperties {
      var var10001: JsonElement = json.get("OriginalText");
      var var22: java.lang.String = if (var10001 != null) var10001.getAsString() else null;
      if (var22 == null) {
         var22 = "";
      }

      var var10000: PokemonProperties;
      label134: {
         this.originalString = var22;
         var10001 = json.get("Level");
         this.level = if (var10001 != null) var10001.getAsInt() else null;
         var10001 = json.get("Shiny");
         this.shiny = if (var10001 != null) var10001.getAsBoolean() else null;
         var10000 = this;
         var10001 = json.get("Gender");
         if (var10001 != null) {
            val var26: java.lang.String = var10001.getAsString();
            if (var26 != null) {
               var27 = Gender.valueOf(var26);
               var10000 = this;
               break label134;
            }
         }

         var27 = null;
      }

      label128: {
         var10000.gender = var27;
         var10001 = json.get("SpeciesText");
         this.species = if (var10001 != null) var10001.getAsString() else null;
         var10000 = this;
         var10001 = json.get("Nickname");
         if (var10001 != null) {
            val var30: java.lang.String = var10001.getAsString();
            if (var30 != null) {
               var31 = Serializer.m_130701_(var30);
               var10000 = this;
               break label128;
            }
         }

         var31 = null;
      }

      var10000.nickname = var31;
      var10001 = json.get("FormId");
      this.form = if (var10001 != null) var10001.getAsString() else null;
      var10001 = json.get("Friendship");
      this.friendship = if (var10001 != null) var10001.getAsInt() else null;
      var10001 = json.get("CaughtBall");
      this.pokeball = if (var10001 != null) var10001.getAsString() else null;
      var10001 = json.get("Nature");
      this.nature = if (var10001 != null) var10001.getAsString() else null;
      var10001 = json.get("Ability");
      this.ability = if (var10001 != null) var10001.getAsString() else null;
      var10001 = json.get("StatusName");
      this.status = if (var10001 != null) var10001.getAsString() else null;
      val var17: IVs = this.ivs;
      if (this.ivs != null) {
         val var38: JsonObject = json.getAsJsonObject("IVs");
         var17.loadFromJSON(var38);
      }

      val var18: EVs = this.evs;
      if (this.evs != null) {
         val var39: JsonObject = json.getAsJsonObject("EVs");
         var18.loadFromJSON(var39);
      }

      label122: {
         var10001 = json.get("TeraType");
         this.teraType = if (var10001 != null) var10001.getAsString() else null;
         var10001 = json.get("DmaxLevel");
         this.dmaxLevel = if (var10001 != null) var10001.getAsInt() else null;
         var10001 = json.get("GmaxFactor");
         this.gmaxFactor = if (var10001 != null) var10001.getAsBoolean() else null;
         var10001 = json.get("Tradeable");
         this.tradeable = if (var10001 != null) var10001.getAsBoolean() else null;
         var10000 = this;
         var10001 = json.get("PokemonOriginalTrainerType");
         if (var10001 != null) {
            val var45: java.lang.String = var10001.getAsString();
            if (var45 != null) {
               var46 = OriginalTrainerType.valueOf(var45);
               var10000 = this;
               break label122;
            }
         }

         var46 = null;
      }

      var10000.originalTrainerType = var46;
      var10001 = json.get("PokemonOriginalTrainer");
      this.originalTrainer = if (var10001 != null) var10001.getAsString() else null;
      val var20: JsonElement = json.get("CustomProperties");
      if ((if (var20 != null) var20.getAsJsonArray() else null) != null) {
         val `$this$forEach$iv`: java.lang.Iterable;
         for (Object element$iv : $this$forEach$iv) {
            val it: JsonElement = var15 as JsonElement;
            val var21: java.util.List = this.customProperties;
            val var48: PokemonProperties.Companion = Companion;
            val var10002: java.lang.String = it.getAsString();
            var21.addAll(PokemonProperties.Companion.parse$default(var48, var10002, null, null, 6, null).customProperties);
         }
      }

      this.updateAspects();
      return this;
   }

   public fun asString(separator: String = " "): String {
      val pieces: java.util.List = new ArrayList();
      if (this.species != null) {
         pieces.add(this.species);
      }

      if (this.nickname != null) {
         pieces.add("nickname=\$${this.nickname.getString()}");
      }

      if (this.form != null) {
         pieces.add("form=${this.form}");
      }

      if (this.level != null) {
         pieces.add("level=${this.level.intValue()}");
      }

      if (this.shiny != null) {
         pieces.add("shiny=${this.shiny}");
      }

      if (this.gender != null) {
         pieces.add("gender=${this.gender}");
      }

      if (this.friendship != null) {
         pieces.add("friendship=${this.friendship.intValue()}");
      }

      if (this.pokeball != null) {
         pieces.add("pokeball=${this.pokeball}");
      }

      if (this.nature != null) {
         pieces.add("nature=${this.nature}");
      }

      if (this.ability != null) {
         pieces.add("ability=${this.ability}");
      }

      if (this.status != null) {
         pieces.add("status=${this.status}");
      }

      if (this.ivs != null) {
         val `$i$f$forEach`: java.lang.Iterable;
         for (Object element$iv : $i$f$forEach) {
            pieces.add("${(it as Entry).getKey()}_iv=${(it as Entry).getValue()}");
         }
      }

      if (this.evs != null) {
         val var10: java.lang.Iterable;
         for (Object element$iv : var10) {
            pieces.add("${(var50 as Entry).getKey()}_ev=${(var50 as Entry).getValue()}");
         }
      }

      if (this.teraType != null) {
         pieces.add("tera_type=${this.teraType}");
      }

      if (this.dmaxLevel != null) {
         pieces.add("dmax_level=${this.dmaxLevel.intValue()}");
      }

      if (this.gmaxFactor != null) {
         pieces.add("gmax_factor=${this.gmaxFactor}");
      }

      if (this.tradeable != null) {
         pieces.add("tradeable=${this.tradeable}");
      }

      if (this.originalTrainerType != null) {
         pieces.add("originaltrainertype=${this.originalTrainerType.name()}");
      }

      if (this.originalTrainer != null) {
         pieces.add("originaltrainer=${this.originalTrainer}");
      }

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         pieces.add((var49 as CustomPokemonProperty).asString());
      }

      return CollectionsKt.joinToString$default(pieces, separator, null, null, 0, null, null, 62, null);
   }

   public fun asStruct(): VariableStruct {
      val struct: VariableStruct = new VariableStruct();
      if (this.species != null) {
         struct.setDirectly("species", new StringValue(this.species));
      }

      if (this.level != null) {
         struct.setDirectly("level", new DoubleValue(this.level.intValue()));
      }

      if (this.shiny != null) {
         struct.setDirectly("shiny", new DoubleValue(this.shiny));
      }

      if (this.gender != null) {
         struct.setDirectly("gender", new StringValue(this.gender.name()));
      }

      if (this.friendship != null) {
         struct.setDirectly("friendship", new DoubleValue(this.friendship.intValue()));
      }

      return struct;
   }

   public fun updateAspects() {
      val aspects: java.util.Set = new LinkedHashSet();

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         aspects.addAll((`element$iv` as AspectProvider).provide(this));
      }

      this.aspects = CollectionsKt.toSet(aspects);
   }

   public fun copy(): PokemonProperties {
      return new PokemonProperties().loadFromJSON(this.saveToJSON());
   }

   private fun Float.checkRate(): Boolean {
      return `$this$checkRate` >= 1.0F && Random.Default.nextFloat() < 1 / `$this$checkRate`;
   }

   private fun createAbility(id: String, form: FormData): Ability? {
      var var10000: AbilityTemplate = Abilities.INSTANCE.get(id);
      if (var10000 == null) {
         return null;
      } else {
         label27: {
            val ability: AbilityTemplate = var10000;
            val var7: java.util.Iterator = form.getAbilities().iterator();

            while (true) {
               if (var7.hasNext()) {
                  val `element$iv`: Any = var7.next();
                  if (!((`element$iv` as PotentialAbility).getTemplate() == ability)) {
                     continue;
                  }

                  var10000 = (AbilityTemplate)`element$iv`;
                  break;
               }

               var10000 = null;
               break;
            }

            return if (var10000 as PotentialAbility == null) ability.create(true) else (var10000 as PotentialAbility).getTemplate().create(false);
         }
      }
   }

   @SourceDebugExtension(["SMAP\nPokemonProperties.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonProperties.kt\ncom/cobblemon/mod/common/api/pokemon/PokemonProperties$Companion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,675:1\n1603#2,9:676\n1855#2:685\n1856#2:688\n1612#2:689\n1855#2,2:690\n288#2:692\n1747#2,3:693\n289#2:696\n223#2,2:697\n1#3:686\n1#3:687\n*S KotlinDebug\n*F\n+ 1 PokemonProperties.kt\ncom/cobblemon/mod/common/api/pokemon/PokemonProperties$Companion\n*L\n62#1:676,9\n62#1:685\n62#1:688\n62#1:689\n103#1:690,2\n252#1:692\n252#1:693,3\n252#1:696\n255#1:697,2\n62#1:687\n*E\n"])
   public companion object {
      @JvmOverloads
      public fun parse(string: String, delimiter: String = " ", assigner: String = "="): PokemonProperties {
         val props: PokemonProperties = new PokemonProperties();
         props.setOriginalString(string);
         val keyPairs: java.util.List = StringExtensionsKt.splitMap(string, delimiter, assigner);
         val maybeIVs: java.lang.Iterable = CustomPokemonProperty.Companion.getProperties();
         val `$i$f$forEach`: java.util.Collection = new ArrayList();

         for (Object element$iv$iv$iv : maybeIVs) {
            val property: CustomPokemonPropertyType = statName as CustomPokemonPropertyType;
            val var20: java.util.Iterator = keyPairs.iterator();

            var var54: Any;
            while (true) {
               if (!var20.hasNext()) {
                  var54 = null;
                  break;
               }

               val var21: Any = var20.next();
               val it: Pair = var21 as Pair;
               var54 = property.getKeys();
               val var10001: java.lang.String = (it.getFirst() as java.lang.String).toLowerCase(Locale.ROOT);
               if (CollectionsKt.contains((java.lang.Iterable)var54, var10001)) {
                  var54 = var21;
                  break;
               }
            }

            val matchedKeyPair: Pair = var54 as Pair;
            if (var54 as Pair != null) {
               keyPairs.remove(matchedKeyPair);
               var54 = property.fromString(matchedKeyPair.getSecond() as java.lang.String);
            } else if (property.getNeedsKey()) {
               var54 = null;
            } else {
               var savedProperty: Any = null;
               val var52: java.util.Iterator = keyPairs.iterator();

               while (true) {
                  if (var52.hasNext()) {
                     val var53: Any = var52.next();
                     savedProperty = property.fromString((var53 as Pair).getSecond() as java.lang.String);
                     if (savedProperty == null) {
                        continue;
                     }

                     var54 = var53;
                     break;
                  }

                  var54 = null;
                  break;
               }

               val var50: Pair = var54 as Pair;
               if (var54 as Pair != null) {
                  keyPairs.remove(var50);
               }

               var54 = savedProperty;
            }

            if (var54 != null) {
               `$i$f$forEach`.add(var54);
            }
         }

         props.setCustomProperties(CollectionsKt.toMutableList(`$i$f$forEach` as java.util.List));
         props.setGender(
            this.parsePropertyOfCollection(ArraysKt.toList(Gender.values()), keyPairs, CollectionsKt.listOf("gender"), true, <unrepresentable>.INSTANCE)
         );
         var var60: Int = this.parseIntProperty(keyPairs, CollectionsKt.listOf(new java.lang.String[]{"level", "lvl", "l"}));
         props.setLevel(if (var60 != null) RangesKt.coerceIn(var60, 1, Cobblemon.INSTANCE.getConfig().getMaxPokemonLevel()) else null);
         props.setShiny(this.parseBooleanProperty(keyPairs, CollectionsKt.listOf(new java.lang.String[]{"shiny", "s"})));
         props.setSpecies(this.parseSpeciesIdentifier(keyPairs));
         props.setForm(this.parseForm(keyPairs));
         var60 = this.parseIntProperty(keyPairs, CollectionsKt.listOf("friendship"));
         props.setFriendship(if (var60 != null) RangesKt.coerceIn(var60, 0, Cobblemon.INSTANCE.getConfig().getMaxPokemonFriendship()) else null);
         props.setPokeball(this.parseIdentifierOfRegistry(keyPairs, CollectionsKt.listOf("pokeball"), <unrepresentable>.INSTANCE));
         props.setNature(this.parseIdentifierOfRegistry(keyPairs, CollectionsKt.listOf("nature"), <unrepresentable>.INSTANCE));
         props.setAbility(this.parseStringOfRegistry(keyPairs, CollectionsKt.listOf("ability"), <unrepresentable>.INSTANCE));
         props.setStatus(this.parseStringOfRegistry(keyPairs, CollectionsKt.listOf("status"), <unrepresentable>.INSTANCE));
         props.setNickname(this.parseText(keyPairs, CollectionsKt.listOf(new java.lang.String[]{"nickname", "nick"})));
         props.setTeraType(this.parseStringOfRegistry(keyPairs, CollectionsKt.listOf(new java.lang.String[]{"tera_type", "tera"}), <unrepresentable>.INSTANCE));
         var60 = this.parseIntProperty(keyPairs, CollectionsKt.listOf(new java.lang.String[]{"dmax_level", "dmax"}));
         props.setDmaxLevel(if (var60 != null) RangesKt.coerceIn(var60, 0, Cobblemon.INSTANCE.getConfig().getMaxDynamaxLevel()) else null);
         props.setGmaxFactor(this.parseBooleanProperty(keyPairs, CollectionsKt.listOf(new java.lang.String[]{"gmax_factor", "gmax"})));
         props.setTradeable(this.parseBooleanProperty(keyPairs, CollectionsKt.listOf(new java.lang.String[]{"tradeable", "tradable"})));
         props.setOriginalTrainerType(
            this.parsePropertyOfCollection(
               ArraysKt.toList(OriginalTrainerType.values()),
               keyPairs,
               CollectionsKt.listOf(new java.lang.String[]{"originaltrainertype", "ottype"}),
               true,
               <unrepresentable>.INSTANCE
            )
         );
         props.setOriginalTrainer(this.parsePlayerProperty(keyPairs, CollectionsKt.listOf(new java.lang.String[]{"originaltrainer", "ot"})));
         val var38: IVs = new IVs();
         val var41: EVs = new EVs();

         val `$this$forEach$iv`: java.lang.Iterable;
         for (Object element$iv : $this$forEach$iv) {
            val var44: Stat = `element$iv` as Stat;
            val var57: java.lang.String = (`element$iv` as Stat).toString().toLowerCase(Locale.ROOT);
            val var58: Int = PokemonProperties.Companion.parseIntProperty(keyPairs, CollectionsKt.listOf("$var57_iv"));
            if (var58 != null) {
               var38.set(var44, RangesKt.coerceIn(var58, 0, 31));
            }

            val var59: Int = PokemonProperties.Companion.parseIntProperty(keyPairs, CollectionsKt.listOf("$var57_ev"));
            if (var59 != null) {
               var41.set(var44, RangesKt.coerceIn(var59, 0, 252));
            }
         }

         props.setIvs(var38);
         props.setEvs(var41);
         props.updateAspects();
         return props;
      }

      private fun getMatchedKeyPair(keyPairs: MutableList<Pair<String, String?>>, labels: Iterable<String>): Pair<String, String?>? {
         val var4: java.util.ListIterator = keyPairs.listIterator(keyPairs.size());

         var var10000: Any;
         while (true) {
            if (var4.hasPrevious()) {
               val var5: Any = var4.previous();
               if (!CollectionsKt.contains(labels, (var5 as Pair).getFirst())) {
                  continue;
               }

               var10000 = var5;
               break;
            }

            var10000 = null;
            break;
         }

         return var10000 as Pair<java.lang.String, java.lang.String>;
      }

      private fun parseText(keyPairs: MutableList<Pair<String, String?>>, labels: Iterable<String>): MutableComponent? {
         val var10000: Pair = this.getMatchedKeyPair(keyPairs, labels);
         if (var10000 == null) {
            return null;
         } else {
            val value: java.lang.String = var10000.getSecond() as java.lang.String;
            return if (value as java.lang.CharSequence == null || StringsKt.isBlank(value)) null else Component.m_237115_(value);
         }
      }

      private fun parseIntProperty(keyPairs: MutableList<Pair<String, String?>>, labels: Iterable<String>): Int? {
         val var10000: Pair = this.getMatchedKeyPair(keyPairs, labels);
         if (var10000 == null) {
            return null;
         } else {
            val value: java.lang.String = var10000.getSecond() as java.lang.String;
            return if (value != null && MiscUtilsKt.isInt(value)) Integer.parseInt(value) else null;
         }
      }

      private fun parseIdentifierOfRegistry(keyPairs: MutableList<Pair<String, String?>>, validKeys: List<String>, valueFetcher: (ResourceLocation) -> String?): String? {
         val var10000: Pair = this.getMatchedKeyPair(keyPairs, validKeys);
         if (var10000 == null) {
            return null;
         } else {
            val var10: java.lang.String = var10000.getSecond() as java.lang.String;
            if (var10 != null) {
               val var11: java.lang.String = var10.toLowerCase(Locale.ROOT);
               if (var11 != null) {
                  val value: java.lang.String = var11;

                  var identifier: java.lang.String;
                  try {
                     identifier = valueFetcher.invoke(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(value, null, 1, null)) as java.lang.String;
                  } catch (var8: ResourceLocationException) {
                     identifier = null;
                  }

                  return identifier;
               }
            }

            return null;
         }
      }

      private fun parseStringOfRegistry(keyPairs: MutableList<Pair<String, String?>>, validKeys: List<String>, valueFetcher: (String) -> String?): String? {
         val var10000: Pair = this.getMatchedKeyPair(keyPairs, validKeys);
         if (var10000 == null) {
            return null;
         } else {
            val var9: java.lang.String = var10000.getSecond() as java.lang.String;
            if (var9 != null) {
               val var10: java.lang.String = var9.toLowerCase(Locale.ROOT);
               if (var10 != null) {
                  val value: java.lang.String = var10;

                  var var6: java.lang.String;
                  try {
                     var6 = valueFetcher.invoke(value) as java.lang.String;
                  } catch (var8: ResourceLocationException) {
                     var6 = null;
                  }

                  return var6;
               }
            }

            return null;
         }
      }

      private fun parseSpeciesIdentifier(keyPairs: MutableList<Pair<String, String?>>): String? {
         val matched: Pair = this.getMatchedKeyPair(keyPairs, CollectionsKt.listOf("species"));
         if (matched != null) {
            var var24: java.lang.String = matched.getSecond() as java.lang.String;
            if (var24 != null) {
               var24 = parseSpeciesIdentifier$cleanSpeciesName(var24);
               if (var24 != null) {
                  val var15: java.lang.String = var24;
                  var24 = var24.toLowerCase(Locale.ROOT);
                  if (var24 == "random") {
                     return "random";
                  }

                  try {
                     val var27: Species = PokemonSpecies.INSTANCE
                        .getByIdentifier(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(var15, null, 1, null));
                     if (var27 == null) {
                        return null;
                     }

                     return if (var27.getResourceIdentifier().m_135827_() == "cobblemon")
                        var27.getResourceIdentifier().m_135815_()
                        else
                        var27.getResourceIdentifier().toString();
                  } catch (var13: ResourceLocationException) {
                     return null;
                  }
               }
            }

            return null;
         } else {
            var species: Any = null;
            val p0: java.util.Iterator = keyPairs.iterator();

            var var23: Any;
            while (true) {
               if (!p0.hasNext()) {
                  var23 = null;
                  break;
               }

               var var8: Any;
               label76: {
                  label85: {
                     var8 = p0.next();
                     val pair: Pair = var8 as Pair;
                     if ((var8 as Pair).getSecond() == null) {
                        var23 = (pair.getFirst() as java.lang.String).toLowerCase(Locale.ROOT);
                        if (var23 == "random") {
                           var23 = "random";
                           break label85;
                        }
                     }

                     var var19: java.lang.String;
                     try {
                        var23 = PokemonSpecies.INSTANCE
                           .getByIdentifier(
                              ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(
                                 parseSpeciesIdentifier$cleanSpeciesName(pair.getFirst() as java.lang.String), null, 1, null
                              )
                           );
                        if (var23 == null) {
                           var20 = false;
                           break label76;
                        }

                        var19 = if (((Species)var23).getResourceIdentifier().m_135827_() == "cobblemon")
                           ((Species)var23).getResourceIdentifier().m_135815_()
                           else
                           ((Species)var23).getResourceIdentifier().toString();
                     } catch (var14: ResourceLocationException) {
                        var20 = false;
                        break label76;
                     }

                     var23 = var19;
                  }

                  species = var23;
                  var20 = var23 != null;
               }

               if (var20) {
                  var23 = var8;
                  break;
               }
            }

            val keyPair: Pair = var23 as Pair;
            if (var23 as Pair != null) {
               keyPairs.remove(keyPair);
            }

            return (java.lang.String)species;
         }
      }

      private fun parseForm(keyPairs: MutableList<Pair<String, String?>>): String? {
         val var10000: Pair = this.getMatchedKeyPair(keyPairs, CollectionsKt.listOf("form"));
         if (var10000 == null) {
            return null;
         } else {
            keyPairs.remove(var10000);
            return var10000.getSecond() as java.lang.String;
         }
      }

      private fun parseBooleanProperty(keyPairs: MutableList<Pair<String, String?>>, labels: Iterable<String>): Boolean? {
         val var10000: Pair = this.getMatchedKeyPair(keyPairs, labels);
         if (var10000 == null) {
            return null;
         } else {
            keyPairs.remove(var10000);
            val var5: java.lang.String = var10000.getSecond() as java.lang.String;
            val var6: java.lang.String;
            if (var5 != null) {
               var6 = var5.toLowerCase(Locale.ROOT);
            } else {
               var6 = null;
            }

            val var7: java.lang.Boolean;
            if (var6 != null) {
               label49: {
                  switch (var6.hashCode()) {
                     case 3521:
                        if (var6.equals("no")) {
                           return false;
                        }
                        break;
                     case 119527:
                        if (var6.equals("yes")) {
                           break label49;
                        }
                        break;
                     case 3569038:
                        if (var6.equals("true")) {
                           break label49;
                        }
                        break;
                     case 97196323:
                        if (var6.equals("false")) {
                           return false;
                        }
                     default:
                  }

                  return null;
               }

               var7 = true;
            } else {
               var7 = true;
            }

            return var7;
         }
      }

      private fun parsePlayerProperty(keyPairs: MutableList<Pair<String, String?>>, labels: Iterable<String>): String? {
         val var10000: Pair = this.getMatchedKeyPair(keyPairs, labels);
         if (var10000 == null) {
            return null;
         } else {
            keyPairs.remove(var10000);
            if (var10000.getSecond() == null) {
               return null;
            } else {
               val var6: Any = var10000.getSecond();
               val string: java.lang.String = var6 as java.lang.String;
               val var5: Int = (var6 as java.lang.String).length();
               return if ((3 > var5 || var5 >= 17) && ((var6 as java.lang.String).length() != 36 || !MiscUtilsKt.isUuid(var6 as java.lang.String)))
                  null
                  else
                  var6 as java.lang.String;
            }
         }
      }

      private fun <T> Iterable<Any>.parsePropertyOfCollection(
         keyPairs: MutableList<Pair<String, String?>>,
         labels: Iterable<String>,
         labelsOptional: Boolean = false,
         stringer: (Any) -> String
      ): Any? {
         val matchingKeyPair: Pair = this.getMatchedKeyPair(keyPairs, labels);
         if (matchingKeyPair != null) {
            var var31: java.lang.String = matchingKeyPair.getSecond() as java.lang.String;
            if (var31 != null) {
               var31 = var31.toLowerCase(Locale.ROOT);
            } else {
               var31 = null;
            }

            val var20: java.lang.String = var31;
            var var33: Any;
            if (var31 == null) {
               var33 = null;
            } else {
               val var25: java.util.Iterator = `$this$parsePropertyOfCollection`.iterator();

               while (true) {
                  if (!var25.hasNext()) {
                     var33 = null;
                     break;
                  }

                  val var26: Any = var25.next();
                  var33 = (stringer.invoke(var26) as java.lang.String).toLowerCase(Locale.ROOT);
                  if (var33 == var20) {
                     var33 = var26;
                     break;
                  }
               }

               if (var33 != null) {
                  keyPairs.remove(matchingKeyPair);
               }

               var33 = var33;
            }

            return (T)var33;
         } else {
            if (labelsOptional) {
               val var10: java.util.Iterator = keyPairs.iterator();

               var var29: Any;
               while (true) {
                  if (!var10.hasNext()) {
                     var29 = null;
                     break;
                  }

                  val `element$iv`: Any = var10.next();
                  val it: Pair = `element$iv` as Pair;
                  var var28: Boolean;
                  if (`$this$parsePropertyOfCollection` is java.util.Collection && (`$this$parsePropertyOfCollection` as java.util.Collection).isEmpty()) {
                     var28 = false;
                  } else {
                     val var16: java.util.Iterator = `$this$parsePropertyOfCollection`.iterator();

                     while (true) {
                        if (!var16.hasNext()) {
                           var28 = false;
                           break;
                        }

                        var29 = (stringer.invoke(var16.next()) as java.lang.String).toLowerCase(Locale.ROOT);
                        if (var29 == it.getFirst()) {
                           var28 = true;
                           break;
                        }
                     }
                  }

                  if (var28) {
                     var29 = `element$iv`;
                     break;
                  }
               }

               val keyPair: Pair = var29 as Pair;
               if (var29 as Pair != null) {
                  keyPairs.remove(keyPair);

                  for (Object element$ivx : $this$parsePropertyOfCollection) {
                     var29 = (stringer.invoke(`element$ivx`) as java.lang.String).toLowerCase(Locale.ROOT);
                     if (var29 == keyPair.getFirst()) {
                        return (T)`element$ivx`;
                     }
                  }

                  throw new NoSuchElementException("Collection contains no element matching the predicate.");
               }
            }

            return null;
         }
      }

      @JvmOverloads
      fun parse(string: java.lang.String, delimiter: java.lang.String): PokemonProperties {
         return parse$default(this, string, delimiter, null, 4, null);
      }

      @JvmOverloads
      fun parse(string: java.lang.String): PokemonProperties {
         return parse$default(this, string, null, null, 6, null);
      }

      @JvmStatic
      fun `parseSpeciesIdentifier$cleanSpeciesName`(string: java.lang.String): java.lang.String {
         val var10000: java.lang.String = string.toLowerCase(Locale.ROOT);
         return new Regex("[^a-z0-9_:]").replace(var10000, "");
      }
   }
}
