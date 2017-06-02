package de.skate702.craftingkeys;


import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import de.skate702.craftingkeys.config.Config;
import de.skate702.craftingkeys.config.GuiConfig;
import de.skate702.craftingkeys.config.GuiConfigHandler;
import de.skate702.craftingkeys.config.KeyBindings;
import de.skate702.craftingkeys.manager.*;
import de.skate702.craftingkeys.proxies.CraftingKeysProxy;
import de.skate702.craftingkeys.util.Logger;
import de.skate702.craftingkeys.util.Util;
import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.*;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;

/**
 * The Main Class of the Mod with the important onTick-Method. Some Methods are
 * based on the open-source Inventory Tweaks. Big Thanks for that!
 *
 * @author skate702
 */
@Mod(modid = CraftingKeys.MODID, name = CraftingKeys.NAME, version = CraftingKeys.VERSION)
public class CraftingKeys {

    public static final String MODID = "craftingkeys";
    public static final String VERSION = "1.0.2";
    public static final String NAME = "Crafting Keys";

    /**
     * Current Instance of CraftingKeys.
     */
    @SuppressWarnings("WeakerAccess")
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
        KeyBindings.init();
        Logger.info("load(e)", "Registered Mod.");

    }

    /**
     * This method will be executed after Init.
     *
     * @param event Input Event from FML
     */
    @SuppressWarnings("EmptyMethod")
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

    @SuppressWarnings("UnusedParameters")
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (KeyBindings.openGuiBinding.isPressed()) {
            Logger.info("onKeyInput(e)", "Open Crafting Keys Config Gui.");
            Util.client.player.openGui(instance, GuiConfig.GuiID, Util.client.world,
                    ((int) Util.client.player.posX), (int) Util.client.player.posY,
                    (int) Util.client.player.posZ);
        }

    }

    /**
     * This method will be executed when the Config is changed. Update!
     *
     * @param eventArgs Input event from FML
     */
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if (eventArgs.getModID().equals(MODID)) { 
            Config.syncConfig();
            Logger.info("onConfigChanged(e)", "Changed config.");
        }
    }

    /**
     * This method listens on GUIs open. Just to test my own gui!
     *
     * @param event Some Forge input event
     */
    @SuppressWarnings({"EmptyMethod", "UnusedParameters"})
    @SubscribeEvent
    public void onGuiOpened(GuiOpenEvent event) {
        //if (event.gui instanceof GuiMainMenu) {
        //event.gui = new GuiConfig(); // (Only for testing)
        //}
    }

    /**
     * This method will be executed every Ingame Tick.
     *
     * @param tick This is a tick. What did you think about it?
     */
    @SuppressWarnings("UnusedParameters")
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent tick) {

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

            } else if (currentScreen instanceof GuiRepair) {
                ContainerManager con = AnvilManager.getInstance(
                        ((GuiRepair) currentScreen).inventorySlots);
                con.acceptKey();
            }

        }

    }
}
