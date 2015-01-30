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

		Helper.debugPrint("load(): Loaded Mod successful");

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

		// if (mc.currentScreen != null) {
		// if (mc.currentScreen instanceof
		// net.minecraft.client.gui.inventory.GuiCrafting) {
		// Keyboard.isKeyDown(Keyboard.KEY_Q)

		// DEBUGGIN TEST START

		if (Helper.client.currentScreen != null) {
			if (Helper.client.currentScreen instanceof GuiCrafting) {
				if (Keyboard.isKeyDown(Keyboard.KEY_C)) {

					GuiCrafting guiCrafting = (GuiCrafting) Helper.client.currentScreen;
					Slot currentHoveredSlot = Helper.getSlotAtMousePosition(guiCrafting);

					ContainerManager con = new ContainerManager(guiCrafting.inventorySlots);
					if (currentHoveredSlot != null) {
						con.move(currentHoveredSlot.slotNumber, 8, 1);
					} else {
						Helper.debugPrint("onTick(): Outside of inventory");
					}
				}
			}
		}

		// DEBUGGIN TEST END

	}
}
