package de.skate702.craftingkeys.manager;

import de.skate702.craftingkeys.CraftingKeys;
import de.skate702.craftingkeys.config.Config;
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
    public ContainerManager(Container container) {
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
            System.out.println("moveAll(): source == null");
        } else {
            System.out.println("moveAll(): Redirected to move()");
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
            System.out.println("Move(): srcIndex == destIndex OR source == null");
            return;
        } else if (srcIndex == destIndex) {
            System.out.println("Move(): srcIndex == destIndex OR source == null");
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

            System.out.println("move(): Moved " + movedAmount + " from " + srcIndex + " to " + destIndex + "!");

        } else {
            System.out.println("Move(): Not the same block type!");
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

            System.out.println("getItemStack(): Invalid index");
            return null;

        }

    }

    /**
     * Takes all items from a slot and moves them to the next empty slot or
     * drops them.
     *
     * @param sourceIndex   The index of the slot to move items from
     * @param isCraftingGUI true, if the craftingGUI is opened
     */
    protected void putStackToNextEmptySlot(int sourceIndex, boolean isCraftingGUI, boolean isHeld) {

        // Check for Item-Type
        ItemStack stackToMove;
        if (isHeld) {
            stackToMove = Util.client.thePlayer.inventory.getItemStack();
        } else {
            putItemAway(isCraftingGUI);
            stackToMove = getItemStack(sourceIndex);
        }

        // Test for empty crafting table slot
        if (!isHeld && getItemStack(sourceIndex) == null) {

            System.out.println("putStackToNextEmptySlot(): No Item Stack @ " + sourceIndex + ".");
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

    protected void putItemAway(boolean isCraftingGUI) {
        // Put current Item away
        if (Util.client.thePlayer.inventory.getItemStack() != null) {
            putStackToNextEmptySlot(-1, isCraftingGUI, true);
        }
    }

    /**
     * Returns the first free index in a inventory
     *
     * @param isCraftingGUI true, if the craftingGUI is opened
     * @return a slot index
     */
    protected int getFirstPropperSlotIndex(boolean isCraftingGUI, ItemStack movingItem) {

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
            System.out.println("getFirstProperSlotIndex(): No Propper / Empty Slot found!");
            return -1;
        } else {
            // TODO: Same for inventory
            return -1;
        }
    }

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

        System.out.println("slotClick(): Clicked @ Slot " + index + " with data " + rightClick + ".");

        int rightClickData = (rightClick) ? 1 : 0;

        CraftingKeys.proxy.sendSlotClick(Util.client.playerController, container.windowId, index,
                rightClickData, 0, Util.client.thePlayer);

    }

}
