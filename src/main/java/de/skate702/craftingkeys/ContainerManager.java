package de.skate702.craftingkeys;

import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Managing Class to move Items in Inventory Containers.
 * 
 * @author skate702
 *
 */
public class ContainerManager {

	/**
	 * The Container to work with.
	 */
	private Container container;

	public ContainerManager(Container container) {

		this.container = container;

	}

	/**
	 * Moves a full Stack from a slot to another.
	 * 
	 * @param srcIndex
	 *            The Source Slot Index of the Container
	 * @param destIndex
	 *            The Destination Slot Index of the Container
	 */
	public void moveAll(int srcIndex, int destIndex) {

		ItemStack source = getItemStack(srcIndex);

		if (source == null) {
			Helper.debugPrint("moveAll(): source == null");
		} else {
			Helper.debugPrint("moveAll(): Redirected to move()");
			move(srcIndex, destIndex, source.stackSize);
		}

	}

	/**
	 * Moves a specified amount of Items from a slot to another. [Based on
	 * INVTW]
	 * 
	 * @param srcIndex
	 *            The Source Slot Index of the Container
	 * @param destIndex
	 *            The Destination Slot Index of the Container
	 * @param amount
	 *            The amount of items to move (can be bigger then Stack Size)
	 */
	public void move(int srcIndex, int destIndex, int amount) {

		// TODO: What if not same type? Fallback Solution (Stack out of the way)
		// or swap // if (Helper.client.thePlayer.inventory.getItemStack() !=

		// Stacks
		ItemStack source = getItemStack(srcIndex);
		ItemStack destination = getItemStack(destIndex);

		// Same Location?
		if (source == null || srcIndex == destIndex) {
			Helper.debugPrint("Move(): srcIndex == destIndex OR source == null");
			return;
		}

		// Test for max. moving Amount
		int sourceSize = source.stackSize;
		int movedAmount = Math.min(amount, sourceSize);

		// Move some
		if (destination == null || source.isItemEqual(destination)) {

			leftClick(srcIndex);
			for (int i = 0; i < movedAmount; i++) {
				rightClick(destIndex);
			}

			// Move back
			if (movedAmount < sourceSize) {
				leftClick(srcIndex);
			}

			Helper.debugPrint("move(): Moved " + movedAmount + " from " + srcIndex + " to " + destIndex + "!");

		} else {
			Helper.debugPrint("Move(): Not the same block type!");
		}

	}

	/**
	 * Returns the ItemStack in a slot [Based on INVTW]
	 * 
	 * @param index
	 *            The index of the slot in the container
	 * @return Returns the ItemStack
	 */
	private ItemStack getItemStack(int index) {

		if (index >= 0 && index < container.inventorySlots.size()) {

			Slot slot = (Slot) (container.inventorySlots.get(index));
			return (slot == null) ? null : slot.getStack();

		} else {

			Helper.debugPrint("getItemStack(): Invalid index");
			return null;

		}

	}

	/**
	 * Sends a click on the crafting output (craftingGUI or Inventory)
	 * 
	 * @param isCraftingGUI
	 *            true, if the craftingGUI is opened
	 */
	public void clickOnCraftingOutput(boolean isCraftingGUI) {

		// TODO: Put current Item away

		if (isCraftingGUI) {

			// Click on crafting output
			Helper.debugPrint("clickOnCraftingOutput(): Clicked on Crafing Output.");
			leftClick(0);

		}

	}

	/**
	 * Takes all items from a slot and moves them to the next empty slot or
	 * drops them.
	 * 
	 * @param index
	 *            The index of the slot to move items from
	 */
	private void putStackToNextEmptySlot(int index) {

		// TODO: Method (maybe from INVTW...?)

	}

	/**
	 * Executes a left mouse click on a slot. [Based on INVTW]
	 * 
	 * @param index
	 *            The index of the slot in the container
	 */
	private void leftClick(int index) {
		slotClick(index, false);
	}

	/**
	 * Executes a right mouse click on a slot. [Based on INVTW]
	 * 
	 * @param index
	 *            The index of the slot in the container
	 */
	private void rightClick(int index) {
		slotClick(index, true);
	}

	/**
	 * Executes a mouse click on a slot. [Based on INVTW]
	 * 
	 * @param index
	 *            The index of the slot in the container
	 * @param rightClick
	 *            True, if the click is with the right mouse button
	 */
	private void slotClick(int index, boolean rightClick) {

		Helper.debugPrint("slotClick(): Clicked @ Slot " + index + " with data " + rightClick + ".");

		int rightClickData = (rightClick) ? 1 : 0;

		CraftingKeys.instance.proxy.sendSlotClick(Helper.client.playerController, container.windowId, index,
				rightClickData, 0, Helper.client.thePlayer);

	}

}
