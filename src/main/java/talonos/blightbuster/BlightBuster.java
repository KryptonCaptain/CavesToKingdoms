package talonos.blightbuster;

import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import talonos.blightbuster.blocks.BBBlock;
import talonos.blightbuster.entities.EntitySilverPotion;
import talonos.blightbuster.handlers.PurityFocusEventHandler;
import talonos.blightbuster.items.BBItems;
import talonos.blightbuster.network.BlightbusterNetwork;

@Mod(modid = BlightBuster.MODID, name = BlightBuster.MODNAME, version = BlightBuster.VERSION, dependencies = BlightBuster.DEPS)
public class BlightBuster
{
	
	public static final String MODID = "blightbuster";
	public static final String MODNAME = "BlightBuster";
    public static final String VERSION = "0.1.0";
    public static final String DEPS = "before:UndergroundBiomes;after:ThermalFoundation;after:appliedenergistics2;after:Thaumcraft";
	public static final String COMMONPROXYLOCATION = "talonos."+MODID+".CommonProxy";
	public static final String CLIENTPROXYLOCATION = "talonos."+MODID+".ClientProxy";
	
	
	
	@SidedProxy(clientSide = BlightBuster.CLIENTPROXYLOCATION, serverSide = BlightBuster.COMMONPROXYLOCATION)
	public static CommonProxy proxy;
	
	public static BlightBuster instance;
	
	@Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event)
    {
		BBBlock.init();
		BBItems.init();
		proxy.registerTileEntities();
        EntityRegistry.registerModEntity(EntitySilverPotion.class, "silverPotion", 0, MODID, 250, 5, true);
    }
 
    @Mod.EventHandler
    public static void init(FMLInitializationEvent event)
    {
        BlightbusterNetwork.init();
    	MinecraftForge.EVENT_BUS.register(new PurityFocusEventHandler());
        FMLCommonHandler.instance().bus().register(new PurityFocusEventHandler());
    }
 
    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event)
    {
    	AddedResearch.initResearch();
        proxy.registerRenderers();
    }
}
