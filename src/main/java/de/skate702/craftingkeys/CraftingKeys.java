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
import cpw.mods.fml.common.network.NetworkRegistry;
import de.skate702.craftingkeys.config.Config;
import de.skate702.craftingkeys.config.ConfigFile;
import de.skate702.craftingkeys.config.GuiConfig;
import de.skate702.craftingkeys.config.GuiConfigHandler;
import de.skate702.craftingkeys.manager.*;
import de.skate702.craftingkeys.proxies.CraftingKeysProxy;
import de.skate702.craftingkeys.util.LanguageLocalizer;
import de.skate702.craftingkeys.util.Logger;
import de.skate702.craftingkeys.util.Util;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.Sys;

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

    public static ConfigFile config;

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
        Logger.info("preInit(e)", "Loading Config now.");
        Config.loadConfig(event);
        Logger.info("preInit(e)", "Finished loading Config.");

        config = new ConfigFile("CraftingKeys","config.txt");
        if(!config.load()){
            Logger.warn("preInit(e)", "Couldn't load config! Setting keybindings to default...");
        }else{
            Logger.info("preInit(e)", "Loaded Config.");

        }

    }

    /**
     * This method will be executed while loading.
     *
     * @param event Input Event from FML
     */
    @EventHandler
    public void load(FMLInitializationEvent event) {

        // Registering
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiConfigHandler());
        Logger.info("load(e)", "Registered Mod.");

    }

    /**
     * This method will be executed after Init.
     *
     * @param event Input Event from FML
     */
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

    /**
     * This method will be executed when the Config is changed. Update!
     *
     * @param eventArgs Input event from FML
     */
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if (eventArgs.modID.equals(MODID)) {
            Config.syncConfig();
            Logger.info("onConfigChanged(e)", "Changed config.");
        }
    }

    /**
     * This method listens on GUIs open. Just to test my own gui!
     *
     * @param event Some Forge input event
     */
    @SubscribeEvent
    public void onGuiOpened(GuiOpenEvent event) {
        if (event.gui instanceof GuiMainMenu) {
           // event.gui = new GuiConfig(); // Delete this!
        }
    }

    /**
     * This method will be executed every Ingame Tick.
     *
     * @param tick This is a tick. What did you think about it?
     */
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent tick) {

        if (Util.client.currentScreen == null) {
            //  Util.client.thePlayer.openGui(instance, 702, Util.client.theWorld, 0, 0, 0);
        }

        // Get current Screen, then test
        GuiScreen currentScreen = Util.client.currentScreen;

        if (currentScreen != null) {

            if (currentScreen instanceof GuiCrafting) {

                ContainerManager con = CraftingManager.getInstance(
                        ((GuiCrafting) currentScreen).inventorySlots);
                con.acceptKey();

            } else if (currentScreen instanceof GuiInventory) {

                ContainerManager con = InventoryManager.getInstance(
                        ((GuiInventory) currentScreen).inventorySlots);
                con.acceptKey();

            } else if (currentScreen instanceof GuiMerchant) {

                ContainerManager con = VillagerManager.getInstance(
                        ((GuiMerchant) currentScreen).inventorySlots);
                con.acceptKey();

            } else if (currentScreen instanceof GuiFurnace) {

                ContainerManager con = FurnaceManager.getInstance(
                        ((GuiFurnace) currentScreen).inventorySlots);
                con.acceptKey();

            } else if (currentScreen instanceof GuiDispenser) {

                ContainerManager con = DispenserManager.getInstance(
                        ((GuiDispenser) currentScreen).inventorySlots);
                con.acceptKey();

            } else if (currentScreen instanceof GuiBrewingStand) {

                ContainerManager con = BrewingManager.getInstance(
                        ((GuiBrewingStand) currentScreen).inventorySlots);
                con.acceptKey();

            } else if (currentScreen instanceof GuiEnchantment) {

                ContainerManager con = EnchantmentManager.getInstance(
                        ((GuiEnchantment) currentScreen).inventorySlots);
                con.acceptKey();

            } else if (currentScreen instanceof GuiRepair){
                ContainerManager con = AnvilManager.getInstance(
                        ((GuiRepair) currentScreen).inventorySlots);
                con.acceptKey();
            }

        }

    }
}