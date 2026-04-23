package bryanthedragon.morph.cobblemonmorph.helper;

import bryanthedragon.morph.cobblemonmorph.CobblemonMorph;
import bryanthedragon.morph.cobblemonmorph.helper.tabs.CobblemonMorphModTabHelper;
import bryanthedragon.morph.cobblemonmorph.helper.mixins.CobblemonMorphMixinsHelper;
import bryanthedragon.morph.cobblemonmorph.helper.logger.CobblemonMorphLoggerHelper;
import bryanthedragon.morph.cobblemonmorph.helper.item.CobblemonMorphItemHelper;
import bryanthedragon.morph.cobblemonmorph.helper.client.CobblemonMorphClientHelper;
import bryanthedragon.morph.cobblemonmorph.helper.dependency.CobblemonMorphDependencyHelper;

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
