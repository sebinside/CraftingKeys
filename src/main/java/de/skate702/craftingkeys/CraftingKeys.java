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
import de.skate702.craftingkeys.proxies.CraftingKeysProxy;
import de.skate702.craftingkeys.util.Helper;
import de.skate702.craftingkeys.util.InputUtil;
import de.skate702.craftingkeys.util.Util;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.inventory.Slot;

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

        // TODO: Helper!
        Helper.debugPrint("load(): Loaded CraftingKeys successful");

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
     * @param tick
     */
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent tick) {

        // Message
        if (Util.isFirstInWorldTick()) {
            Util.printWarning();
        }


        // Case 1: Classic GUI Screen
        if (Util.isCraftingGUI(Util.client.currentScreen)) {

            GuiCrafting guiCrafting = (GuiCrafting) Util.client.currentScreen;
            Slot currentHoveredSlot = InputUtil.getSlotAtMousePosition(guiCrafting);
            int keyDown = Helper.craftingKeyDownToSlotNumber();

            ContainerManager con = new ContainerManager(guiCrafting.inventorySlots);

            // Block Key Interval (avoid multiple Runs)
            if (!InputUtil.isSameKey(keyDown)) {

                // Moving item to crafting table
                if (keyDown > 0 && currentHoveredSlot != null) {

                    // Shift = Move all
                    if (guiCrafting.isShiftKeyDown()) {
                        con.moveAll(currentHoveredSlot.slotNumber, keyDown);
                    } else {
                        con.move(currentHoveredSlot.slotNumber, keyDown, 1);
                    }

                }

                if (keyDown == -2) {

                    // Space = Move all back
                    Helper.debugPrint("onTick(): Move all items back or drop them.");

                    for (int i = 1; i < 10; i++) {

                        con.putStackToNextEmptySlot(i, true, false);

                    }
                }

                // Strg = Take the output
                if (guiCrafting.isCtrlKeyDown()) {

                    if (guiCrafting.isShiftKeyDown()) {

                        // Strg + Shift = Move all (resp. faster!)
                        con.clickOnCraftingOutput(true);

                    } else {

                        // Send mouse click on crafting output (accept also
                        // holding)
                        int ticksdown = Helper.getStrgTimesDown(true);
                        if (ticksdown == 2 || ticksdown % 15 == 0 || (ticksdown > 60 && ticksdown % 8 == 0)) {
                            con.clickOnCraftingOutput(true);
                        }

                    }

                } else {

                    // Reset Strg
                    Helper.getStrgTimesDown(false);
                }

            }
        }

        // Case 2: Inventory (2x2 Crafting, Quick-Armor)

        // TODO: Case 2

    }
}