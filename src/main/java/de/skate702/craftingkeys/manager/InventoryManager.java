package de.skate702.craftingkeys.manager;

import de.skate702.craftingkeys.util.Logger;
import net.minecraft.inventory.Container;

/**
 * Manages a Inventory GUI Inventory.
 */
public class InventoryManager extends ContainerManager {

    private static InventoryManager instance = null;

    /**
     * Creates a new Inventory Manager with the given container.
     *
     * @param container The container from a crafting GUI
     */
    private InventoryManager(Container container) {
        super(container);
    }

    /**
     * Returns a Inventory Manager Instance operating on the given container
     *
     * @param container A container from a GUI
     * @return manager-singleton
     */
    public static InventoryManager getInstance(Container container) {
        if (instance == null) {
            instance = new InventoryManager(container);
        } else {
            instance.container = container;
        }
        return instance;
    }

    @Override
    protected int specificKeyToSlotIndex() {

        return mapKeyToSlot(-1, 1, 2, -1, 3, 4, -1, -1, -1);
    }

    @Override
    protected int getInventoryStartIndex() {
        return 9;
    }

    @Override
    protected int getInteractionSlotIndex() {
        return 0;
    }

    @Override
    protected int[] getDropSlots() {
        return new int[]{1, 2, 3, 4};
    }

    @Override
    protected void interact() {
        clickOnCraftingOutput();
    }

    /**
     * Sends a click on the crafting output
     */
    private void clickOnCraftingOutput() {

        Logger.info("clickOnCraftingOutput()", "Clicked on Crafing Output.");
        leftClick(0);

    }
}
