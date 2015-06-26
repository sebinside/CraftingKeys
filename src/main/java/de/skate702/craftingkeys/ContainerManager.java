package de.skate702.craftingkeys;

import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

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

		putItemAway(isCraftingGUI);

		if (isCraftingGUI) {

			// Click on crafting output
			Helper.debugPrint("clickOnCraftingOutput(): Clicked on Crafing Output.");
			leftClick(0);

		} else {
			// TODO: Same for inventory
		}

	}

	/**
	 * Takes all items from a slot and moves them to the next empty slot or
	 * drops them.
	 *
	 * @param sourceIndex
	 *            The index of the slot to move items from
	 * @param isCraftingGUI
	 *            true, if the craftingGUI is opened
	 */
	public void putStackToNextEmptySlot(int sourceIndex, boolean isCraftingGUI, boolean isHeld) {

		// Check for Item-Type
		ItemStack stackToMove;
		if (isHeld) {
			stackToMove = Helper.client.thePlayer.inventory.getItemStack();
		} else {
			putItemAway(isCraftingGUI);
			stackToMove = getItemStack(sourceIndex);
		}

		// Test for empty crafting table slot
		if (!isHeld && getItemStack(sourceIndex) == null) {

			Helper.debugPrint("putStackToNextEmptySlot(): No Item Stack @ " + sourceIndex + ".");
			return;
		}

		// Get Destination Index
		int destIndex = getFirstPropperSlotIndex(isCraftingGUI, stackToMove);

		// Additional click on source index, if not held
		if (!isHeld) {
			leftClick(sourceIndex);
		}

		// TODO: Optional (beta-like): Fill the items up. Let's become INVTW!

		// destIndex = -1 -> drop item
		if (destIndex == -1) {
			leftClick(-999); // Nice one, InvTweaks!
		} else {
			leftClick(destIndex);
		}

	}

	private void putItemAway(boolean isCraftingGUI) {
		// Put current Item away
		if (Helper.client.thePlayer.inventory.getItemStack() != null) {
			putStackToNextEmptySlot(-1, isCraftingGUI, true);
		}
	}

	/**
	 * Returns the first free index in a inventory
	 *
	 * @param isCraftingGUI
	 *            true, if the craftingGUI is opened
	 * @return a slot index
	 */
	private int getFirstPropperSlotIndex(boolean isCraftingGUI, ItemStack movingItem) {

		if (isCraftingGUI) {

			for (int i = 10; i < container.inventorySlots.size(); i++) {

				if (getItemStack(i) != null) {
					if (getItemStack(i).isItemEqual(movingItem)) {
						if (getItemStack(i).stackSize + movingItem.stackSize <= movingItem.getMaxStackSize()) {
							return i;
						}
					}
				}
			}

			for (int i = 10; i < container.inventorySlots.size(); i++) {

				if (getItemStack(i) == null) {
					return i;
				}
			}
			Helper.debugPrint("getFirstProperSlotIndex(): No Propper / Empty Slot found!");
			return -1;
		} else {
			// TODO: Same for inventory
			return -1;
		}
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