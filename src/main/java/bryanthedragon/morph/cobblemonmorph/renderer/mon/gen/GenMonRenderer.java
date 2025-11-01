package bryanthedragon.morph.cobblemonmorph.renderer.mon.gen;

import bryanthedragon.morph.cobblemonmorph.renderer.mon.gen.one.Gen1MonRenderer;
import bryanthedragon.morph.cobblemonmorph.renderer.mon.gen.two.Gen2MonRenderer;
import bryanthedragon.morph.cobblemonmorph.renderer.mon.gen.three.Gen3MonRenderer;
import bryanthedragon.morph.cobblemonmorph.renderer.mon.gen.four.Gen4MonRenderer;
import bryanthedragon.morph.cobblemonmorph.renderer.mon.gen.five.Gen5MonRenderer;
import bryanthedragon.morph.cobblemonmorph.renderer.mon.gen.six.Gen6MonRenderer;
import bryanthedragon.morph.cobblemonmorph.renderer.mon.gen.seven.Gen7MonRenderer;
import bryanthedragon.morph.cobblemonmorph.renderer.mon.gen.eight.Gen8MonRenderer;
import bryanthedragon.morph.cobblemonmorph.renderer.mon.gen.nine.Gen9MonRenderer;


public class GenMonRenderer 
{
    private Gen1MonRenderer Gen1MonRenderer;
    private Gen2MonRenderer Gen2MonRenderer;
    private Gen3MonRenderer Gen3MonRenderer;
    private Gen4MonRenderer Gen4MonRenderer;
    private Gen5MonRenderer Gen5MonRenderer;
    private Gen6MonRenderer Gen6MonRenderer;
    private Gen7MonRenderer Gen7MonRenderer;
    private Gen8MonRenderer Gen8MonRenderer;
    private Gen9MonRenderer Gen9MonRenderer;

    public GenMonRenderer() 
    {
        getGen1MonRenderer();
        getGen2MonRenderer();
        getGen3MonRenderer();
        getGen4MonRenderer();
        getGen5MonRenderer();
        getGen6MonRenderer();
        getGen7MonRenderer();
        getGen8MonRenderer();
        getGen9MonRenderer();
    }
    private Gen1MonRenderer getGen1MonRenderer() 
    {
        if (Gen1MonRenderer == null)
        {
            Gen1MonRenderer = new Gen1MonRenderer();
        }
        return Gen1MonRenderer;
    }
    private Gen2MonRenderer getGen2MonRenderer() 
    {
        if (Gen2MonRenderer == null)
        {
            Gen2MonRenderer = new Gen2MonRenderer();
        }
        return Gen2MonRenderer;
    }
    private Gen3MonRenderer getGen3MonRenderer() 
    {
        if (Gen3MonRenderer == null)
        {
            Gen3MonRenderer = new Gen3MonRenderer();
        }
        return Gen3MonRenderer;
    }
    private Gen4MonRenderer getGen4MonRenderer() 
    {
        if (Gen4MonRenderer == null)
        {
            Gen4MonRenderer = new Gen4MonRenderer();
        }
        return Gen4MonRenderer;
    }
    private Gen5MonRenderer getGen5MonRenderer() 
    {
        if (Gen5MonRenderer == null)
        {
            Gen5MonRenderer = new Gen5MonRenderer();
        }
        return Gen5MonRenderer;
    }
    private Gen6MonRenderer getGen6MonRenderer() 
    {
        if (Gen6MonRenderer == null)
        {
            Gen6MonRenderer = new Gen6MonRenderer();
        }
        return Gen6MonRenderer;
    }
    private Gen7MonRenderer getGen7MonRenderer() 
    {
        if (Gen7MonRenderer == null)
        {
            Gen7MonRenderer = new Gen7MonRenderer();
        }
        return Gen7MonRenderer;
    }
    private Gen8MonRenderer getGen8MonRenderer() 
    {
        if (Gen8MonRenderer == null)
        {
            Gen8MonRenderer = new Gen8MonRenderer();
        }
        return Gen8MonRenderer;
    }
    private Gen9MonRenderer getGen9MonRenderer() 
    {
        if (Gen9MonRenderer == null)
        {
            Gen9MonRenderer = new Gen9MonRenderer();
        }
        return Gen9MonRenderer;
    }
}
