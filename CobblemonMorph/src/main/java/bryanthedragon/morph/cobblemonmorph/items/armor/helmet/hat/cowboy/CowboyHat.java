package bryanthedragon.morph.cobblemonmorph.items.armor.helmet.hat.cowboy;

import bryanthedragon.morph.cobblemonmorph.items.armor.helmet.hat.Hats;

public class CowboyHat extends Hats
{
    private static CowboyHat instance;

    public static CowboyHat create()
    {
        return new CowboyHat();
    }

    public static CowboyHat getInstance()
    {
        if (instance == null)
        {
            instance = new CowboyHat();
        }
        return instance;
    }
}