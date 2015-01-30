package de.skate702.craftingkeys;

import org.lwjgl.input.Mouse;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

/**
 * This helper class provides some static helping Methods and Constants.
 * 
 * @author sebastian
 *
 */
public class Helper {

	/**
	 * Private Constructor. This is a helping class!
	 */
	private Helper() {
		// Nope.
	}

	/**
	 * If true, there are Debug-Output-Prints from debugPrint(msg).
	 */
	public static final boolean DEBUG = true;

	/**
	 * Current Instance Client, used for a lot of operations.
	 */
	public static Minecraft client = FMLClientHandler.instance().getClient();

	/**
	 * Standart Output for Debug-Messages - Depends on DEBUG-Constant.
	 * 
	 * @param message
	 *            The Debug-Message. Syntax: "methodname(): Message"
	 */
	public static void debugPrint(String message) {

		if (DEBUG) {
			System.out.println("CK-DEBUG: " + message);
		}

	}

	/**
	 * Returns the Slot at the current Mouse Position. [FROM GUICONTAINER]
	 * 
	 * @param guiContainer
	 *            The (Inventory) Container to work with.
	 * @return Returns the slot (or null)
	 */
	public static Slot getSlotAtMousePosition(GuiContainer guiContainer) {
		if (guiContainer != null) {
			Container container = guiContainer.inventorySlots;

			int x = getMouseX(guiContainer);
			int y = getMouseY(guiContainer);
			for (int k = 0; k < container.inventorySlots.size(); k++) {
				Slot slot = (Slot) container.inventorySlots.get(k);
				if (getIsMouseOverSlot(guiContainer, slot, x, y)) {
					return slot;
				}
			}
			return null;
		} else {
			Helper.debugPrint("getSlotAtMousePosition(): guiContainer == null");
			return null;
		}
	}

	/**
	 * Returns, if the mouse cursor is over a specified slot [FROM GUICONTAINER]
	 * 
	 * @param guiContainer
	 *            the GuiContainer to work with
	 * @param slot
	 *            The spcified slot
	 * @param x
	 *            The Mouse x-Position
	 * @param y
	 *            The Mouse y-Position
	 * @return True, if the mouse is positioned over this slot. Otherwise false
	 */
	private static boolean getIsMouseOverSlot(GuiContainer guiContainer, Slot slot, int x, int y) {
		if (guiContainer != null) {

			// Constants from Minecraft Source Code
			x -= (guiContainer.width - 176) / 2;
			y -= (guiContainer.height - 166) / 2;
			return x >= slot.xDisplayPosition - 1 && x < slot.xDisplayPosition + 16 + 1
					&& y >= slot.yDisplayPosition - 1 && y < slot.yDisplayPosition + 16 + 1;
		} else {
			return false;
		}
	}

	/**
	 * Returns a calculated Mouse Position for getSlotAtMousePosition().
	 * 
	 * @param guiContainer
	 *            the GuiContainer to work with
	 * @return The relative Mouse-Position
	 */
	private static int getMouseX(GuiContainer guiContainer) {
		return (Mouse.getEventX() * guiContainer.width) / client.displayWidth;
	}

	/**
	 * Returns a calculated Mouse Position for getSlotAtMousePosition().
	 * 
	 * @param guiContainer
	 *            the GuiContainer to work with
	 * @return The relative Mouse-Position
	 */
	private static int getMouseY(GuiContainer guiContainer) {
		return guiContainer.height - (Mouse.getEventY() * guiContainer.height) / client.displayHeight - 1;
	}

}
