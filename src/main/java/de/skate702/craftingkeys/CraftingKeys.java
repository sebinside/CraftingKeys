package de.skate702.craftingkeys;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.inventory.Slot;
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
import de.skate702.craftingkeys.proxies.CraftingKeysProxy;

/**
 * The Main Class of the Mod with the important onTick-Method. Some Methods are
 * base on the open-source Inventory Tweaks. Big Thanks for that!
 * 
 * @author skate702
 *
 */
@Mod(modid = "CraftingKeysID", name = "Crafting Keys", version = "1.0.0")
public class CraftingKeys {

	/**
	 * Current Instance of CraftingKeys.
	 */
	@Instance(value = "CraftingKeysID")
	public static CraftingKeys instance;

	/**
	 * Current Proxy (Common or Client)
	 */
	@SidedProxy(clientSide = "de.skate702.craftingkeys.proxies.CraftingKeysClientProxy", serverSide = "de.skate702.craftingkeys.proxies.CraftingKeysProxy")
	public static CraftingKeysProxy proxy;

	/**
	 * This method will be executed before Init.
	 * 
	 * @param event
	 *            Input Event from FML
	 */
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
	}

	/**
	 * This method will be executed while loading.
	 * 
	 * @param event
	 *            Input Event from FML
	 */
	@EventHandler
	public void load(FMLInitializationEvent event) {

		// Regeistring
		proxy.registerRenderers();
		FMLCommonHandler.instance().bus().register(this);

		Helper.debugPrint("load(): Loaded CraftingKeys successful");

	}

	/**
	 * This method will be executed after Init.
	 * 
	 * @param event
	 *            Input Event from FML
	 */
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	}

	/**
	 * This method will be executed every Ingame Tick.
	 * 
	 * @param tick
	 */
	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent tick) {

		// Case 1: Classic GUI Screen
		if (Helper.isCraftingGUI(Helper.client.currentScreen)) {

			GuiCrafting guiCrafting = (GuiCrafting) Helper.client.currentScreen;
			Slot currentHoveredSlot = Helper.getSlotAtMousePosition(guiCrafting);
			int keyDown = Helper.craftingKeyDownToSlotNumber();

			// Block Key Interval (avoid multiple Runs)
			if (!Helper.isSameKey(keyDown)) {

				ContainerManager con = new ContainerManager(guiCrafting.inventorySlots);

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

					// TODO: Space = Move all back
					Helper.debugPrint("onTick(): [TODO] Move all items back or drop them.");

				}

				// Strg = Take the output
				if (guiCrafting.isCtrlKeyDown()) {

					if (guiCrafting.isShiftKeyDown()) {

						//Strg + Shift = Move all (resp. faster!)
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
