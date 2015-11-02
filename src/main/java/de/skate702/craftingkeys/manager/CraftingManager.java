package de.skate702.craftingkeys.manager;

import de.skate702.craftingkeys.util.Logger;
import net.minecraft.inventory.Container;

/**
 * Manages a Crafting GUI Inventory.
 */
public class CraftingManager extends ContainerManager {

    private static CraftingManager instance = null;

    /**
     * Creates a new Crafting Manager with the given container.
     *
     * @param container The container from a crafting GUI
     */
    private CraftingManager(Container container) {
        super(container);
    }

    /**
     * Returns a Crafting Manager Instance operating on the given container
     *
     * @param container A container from a GUI
     * @return manager-singleton
     */
    public static CraftingManager getInstance(Container container) {
        if (instance == null) {
            instance = new CraftingManager(container);
        } else {
            instance.container = container;
        }
        return instance;
    }

    @Override
    protected int specificKeyToSlotIndex() {

        return mapKeyToSlot(1, 2, 3, 4, 5, 6, 7, 8, 9);

    }

    @Override
    protected int getInventoryStartIndex() {
        return 10;
    }

    @Override
    protected int getInteractionSlotIndex() {
        return 0;
    }

    @Override
    protected int[] getDropSlots() {
        return new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
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
