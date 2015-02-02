package de.skate702.craftingkeys;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiCrafting;
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
	 * Returns true, if the current Screen is a instance of GUI-Screen.
	 * 
	 * @param screen
	 *            The input Screen
	 * @return True, if GuiScreen; False if null
	 */
	public static boolean isCraftingGUI(GuiScreen screen) {

		if (screen != null) {
			if (screen instanceof GuiCrafting) {
				return true;
			}
		}
		return false;
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

	/**
	 * Reads the current Keyboard-Input and converts it to a Inventory-Slot.
	 * 
	 * @return A Inventory-Slot (based on CraftingGUI)
	 */
	public static int craftingKeyDownToSlotNumber() {

		// TODO: Make this dynamic! // Use Settings

		int returnValue = -1;

		if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			returnValue = 1;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			returnValue = 2;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
			returnValue = 3;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			returnValue = 4;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			returnValue = 5;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			returnValue = 6;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_Y)) {
			returnValue = 7;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
			returnValue = 8;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
			returnValue = 9;
		}

		return returnValue;
	}

	/**
	 * Saves the last Key that was Pressed
	 */
	private static int lastKeyDown = -1;

	/**
	 * Returns, if the current KeyValue is the same
	 * 
	 * @param currentKey
	 *            the new input value, saved in the method
	 * @return True, if these are the same keys
	 */
	public static boolean isSameKey(int currentKey) {

		boolean returnValue = false;

		if (lastKeyDown == currentKey && currentKey != -1) {
			returnValue = true;
		}

		lastKeyDown = currentKey;
		return returnValue;

	}
}
