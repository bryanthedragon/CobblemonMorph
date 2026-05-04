package bryanthedragon.cobblemon.morph.helper;

import bryanthedragon.cobblemon.morph.CobblemonMorph;
import bryanthedragon.cobblemon.morph.helper.tabs.CobblemonMorphModTabHelper;
import bryanthedragon.cobblemon.morph.helper.mixins.CobblemonMorphMixinsHelper;
import bryanthedragon.cobblemon.morph.helper.logger.CobblemonMorphLoggerHelper;
import bryanthedragon.cobblemon.morph.helper.item.CobblemonMorphItemHelper;
import bryanthedragon.cobblemon.morph.helper.client.CobblemonMorphClientHelper;
import bryanthedragon.cobblemon.morph.helper.dependency.CobblemonMorphDependencyHelper;

public class CobblemonMorphHelper extends CobblemonMorph
{
    private static CobblemonMorphModTabHelper cobblemonmorphmodtabhelper;
    private static CobblemonMorphMixinsHelper cobblemonmorphmixinshelper;
    private static CobblemonMorphLoggerHelper cobblemonmorphloggerHelper;
    private static CobblemonMorphItemHelper cobblemonmorphitemhelper;
    private static CobblemonMorphClientHelper cobblemonmorphclienthelper;
    private static CobblemonMorphDependencyHelper cobblemonmorphdependencyhelper;

    public static CobblemonMorphClientHelper getCobblemonMorphClientHelper() 
    {
        return cobblemonmorphclienthelper;
    }

    public static CobblemonMorphDependencyHelper getCobblemonMorphDependencyHelper() 
    {
        return cobblemonmorphdependencyhelper;
    }

    public static CobblemonMorphLoggerHelper getCobblemonMorphLoggerHelper() 
    {
        return cobblemonmorphloggerHelper;
    }

    public static CobblemonMorphItemHelper getCobblemonMorphItemHelper() 
    {
        return cobblemonmorphitemhelper;
    }

    public static CobblemonMorphModTabHelper getCobblemonMorphModTabHelper() 
    {
        return cobblemonmorphmodtabhelper;
    }

    public static CobblemonMorphMixinsHelper getCobblemonMorphMixinsHelper() 
    {
        return cobblemonmorphmixinshelper;
    }

    public CobblemonMorphHelper() 
    {
        getCobblemonMorphModTabHelper();
        getCobblemonMorphMixinsHelper();
        getCobblemonMorphLoggerHelper();
        getCobblemonMorphItemHelper();
        getCobblemonMorphClientHelper();
        getCobblemonMorphDependencyHelper();
    }
}
