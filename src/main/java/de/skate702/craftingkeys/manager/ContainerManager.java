package de.skate702.craftingkeys.manager;

import de.skate702.craftingkeys.CraftingKeys;
import de.skate702.craftingkeys.config.Config;
import de.skate702.craftingkeys.util.Logger;
import de.skate702.craftingkeys.util.Util;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

public abstract class ContainerManager {

    /**
     * The Container to work with.
     */
    protected Container container;

    /**
     * Creates a new ContainerManager with the given container.
     *
     * @param container The container to work with
     */
    protected ContainerManager(Container container) {
        this.container = container;
    }

    /**
     * Checks the current keyDown-Value and does the work!
     */
    public abstract void acceptKey();

    /**
     * Converts the first specific pressed Key to the slot in a given Inventory.
     * Does also accept Interaction (mapped to -101) and drop key (mapped to -102).
     *
     * @return The slot index in the currently managed inventory gui
     */
    protected abstract int specificKeyDownToSlotIndex();

    /**
     * Returns, if the stack key is pressed
     *
     * @return True, if pressed
     */
    protected boolean isStackKeyDown() {
        return Keyboard.isKeyDown(Config.getKeyStack());
    }

    /**
     * Returns, if the Interaction key is pressed
     *
     * @return True, if pressed
     */
    protected boolean isInteractionKeyDown() {
        return Keyboard.isKeyDown(Config.getKeyInteract());
    }

    /**
     * Returns, if the Drop key is pressed
     *
     * @return True, if pressed
     */
    protected boolean isDropKeyDown() {
        return Keyboard.isKeyDown(Config.getKeyDrop());
    }

    /**
     * Moves a full Stack from a slot to another.
     *
     * @param srcIndex  The Source Slot Index of the Container
     * @param destIndex The Destination Slot Index of the Container
     */
    public void moveAll(int srcIndex, int destIndex) {

        ItemStack source = getItemStack(srcIndex);

        if (source == null) {
            Logger.debug("moveAll(i,i)", "Source ItemStack from Index == null");
        } else {
            move(srcIndex, destIndex, source.stackSize);
        }

    }

    /**
     * Moves a specified amount of Items from a slot to another. [Based on
     * INVTW]
     *
     * @param srcIndex  The Source Slot Index of the Container
     * @param destIndex The Destination Slot Index of the Container
     * @param amount    The amount of items to move (can be bigger then Stack Size)
     */
    public void move(int srcIndex, int destIndex, int amount) {

        // Stacks
        ItemStack source = getItemStack(srcIndex);
        ItemStack destination = getItemStack(destIndex);

        // Same Location?
        if (source == null) {
            return;
        } else if (srcIndex == destIndex) {
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

            Logger.info("move(i,i,i)", "Moved " + movedAmount + " from " + srcIndex + " to " + destIndex + "!");

        } else {
            Logger.info("move(i,i,i)", "Not the same block type!");
        }
    }

    /**
     * Returns the ItemStack in a slot [Based on INVTW]
     *
     * @param index The index of the slot in the container
     * @return Returns the ItemStack
     */
    private ItemStack getItemStack(int index) {

        if (index >= 0 && index < container.inventorySlots.size()) {

            Slot slot = (Slot) (container.inventorySlots.get(index));
            return (slot == null) ? null : slot.getStack();

        } else {

            Logger.debug("getItemStack(i)", "Invalid index");
            return null;

        }

    }

    /**
     * Moves a stack (held or not) to the next fitting inventory slot.
     *
     * @param sourceIndex A slot index of the source items
     */
    protected void moveStackToInventory(int sourceIndex) {

        // Moving Stack
        ItemStack stackToMove = null;

        // Get the stack, index or held, cleanup held stack
        if (sourceIndex == -1) {
            if (Util.client.thePlayer.inventory.getItemStack() != null) {
                stackToMove = Util.client.thePlayer.inventory.getItemStack();
            }
        } else {
            stackToMove = getItemStack(sourceIndex);

            // Is there a currently held stack?
            if (Util.client.thePlayer.inventory.getItemStack() != null) {
                moveStackToInventory(-1);
            }
        }

        // Test stack
        if (stackToMove == null) {
            Logger.debug("moveStackToInvetory(i)", "Stack at sourceIndex not found.");
            return;
        }

        // Get destination index
        int destIndex = calcInventoryDestination(stackToMove);

        // Additional click on source index, if not held
        if (sourceIndex != -1) {
            leftClick(sourceIndex);
        }

        // Move the item
        if (destIndex == -1) { // -1 means: Found none, drop item
            leftClick(-999); // Nice one, InvTweaks!
        } else {
            leftClick(destIndex);
        }

    }

    /**
     * Calculates the next fitting or free inventory slot.
     *
     * @param stackToMove The ItemType and sized Stack to move
     * @return A super cool inventory slot index! Or -1, if you are to dumb
     * to keep your bloody inventory sorted! WHY U NO USE INV TWEAKS?!
     */
    private int calcInventoryDestination(ItemStack stackToMove) {

        // First run: Try to find a nice stack to put items on additionally
        for (int i = getInventoryStartIndex(); i < container.inventorySlots.size(); i++) {

            ItemStack potentialGoalStack = getItemStack(i);

            if (potentialGoalStack != null && stackToMove != null) {
                if (potentialGoalStack.isItemEqual(stackToMove)) {
                    if (potentialGoalStack.stackSize + stackToMove.stackSize <= stackToMove.getMaxStackSize()) {
                        return i;
                    }
                }
            }
        }

        // Second run: Find a free slot
        for (int i = getInventoryStartIndex(); i < container.inventorySlots.size(); i++) {
            if (getItemStack(i) == null) {
                return i;
            }
        }

        // Third run: No slot found. Drop this shit!
        return -1;

    }

    /**
     * Returns the start index of the user inventory in the current Gui.
     *
     * @return A slot index
     */
    protected abstract int getInventoryStartIndex();

    /**
     * Executes a left mouse click on a slot. [Based on INVTW]
     *
     * @param index The index of the slot in the container
     */
    protected void leftClick(int index) {
        slotClick(index, false);
    }

    /**
     * Executes a right mouse click on a slot. [Based on INVTW]
     *
     * @param index The index of the slot in the container
     */
    protected void rightClick(int index) {
        slotClick(index, true);
    }

    /**
     * Executes a mouse click on a slot. [Based on INVTW]
     *
     * @param index      The index of the slot in the container
     * @param rightClick True, if the click is with the right mouse button
     */
    protected void slotClick(int index, boolean rightClick) {

        Logger.info("slotClick(i,b)", "Clicked @ Slot " + index + " with data " + rightClick + ".");

        int rightClickData = (rightClick) ? 1 : 0;

        CraftingKeys.proxy.sendSlotClick(Util.client.playerController, container.windowId, index,
                rightClickData, 0, Util.client.thePlayer);

    }

}
