package de.skate702.craftingkeys;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import de.skate702.craftingkeys.config.Config;
import de.skate702.craftingkeys.manager.ContainerManager;
import de.skate702.craftingkeys.manager.CraftingManager;
import de.skate702.craftingkeys.proxies.CraftingKeysProxy;
import de.skate702.craftingkeys.util.Logger;
import de.skate702.craftingkeys.util.Util;
import net.minecraft.client.gui.inventory.GuiCrafting;

/**
 * The Main Class of the Mod with the important onTick-Method. Some Methods are
 * based on the open-source Inventory Tweaks. Big Thanks for that!
 *
 * @author skate702
 */
@Mod(modid = CraftingKeys.MODID, name = CraftingKeys.NAME, version = CraftingKeys.VERSION,
        guiFactory = "de.skate702.craftingkeys.config.ConfigGuiFactory")
public class CraftingKeys {

    public static final String MODID = "craftingkeys";
    public static final String VERSION = "1.0.0";
    public static final String NAME = "Crafting Keys";

    /**
     * Current Instance of CraftingKeys.
     */
    @Instance(value = MODID)
    public static CraftingKeys instance;

    /**
     * Current Proxy (Common or Client)
     */
    @SidedProxy(clientSide = "de.skate702.craftingkeys.proxies.CraftingKeysClientProxy",
            serverSide = "de.skate702.craftingkeys.proxies.CraftingKeysProxy")
    public static CraftingKeysProxy proxy;

    /**
     * This method will be executed before Init.
     *
     * @param event Input Event from FML
     */
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.loadConfig(event);
    }

    /**
     * This method will be executed while loading.
     *
     * @param event Input Event from FML
     */
    @EventHandler
    public void load(FMLInitializationEvent event) {

        // Registering
        proxy.registerRenderers();
        FMLCommonHandler.instance().bus().register(this);

    }

    /**
     * This method will be executed after Init.
     *
     * @param event Input Event from FML
     */
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if (eventArgs.modID.equals(MODID))
            Config.syncConfig();
    }

    /**
     * This method will be executed every Ingame Tick.
     *
     * @param tick This is a tick. What did you think about it?
     */
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent tick) {

        // Message
        if (Util.isFirstInWorldTick()) {
            Util.printWarning();
        }

        if (Util.isCraftingGUI(Util.client.currentScreen)) {

            ContainerManager con = new CraftingManager(
                    ((GuiCrafting) Util.client.currentScreen).inventorySlots);
            con.acceptKey();

        } else if (Util.isInventoryGUI(Util.client.currentScreen)) {
            // do do do
            Logger.warn("onTick()", "Inventory");
        } else if (Util.isVillagerGUI(Util.client.currentScreen)) {
            // do do do
            //((GuiMerchant) Util.client.currentScreen).inventorySlots
            Logger.warn("onTick()", "Villager");
        }

    }
}