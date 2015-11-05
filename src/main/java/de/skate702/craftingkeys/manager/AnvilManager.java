package de.skate702.craftingkeys.manager;

import de.skate702.craftingkeys.util.Logger;
import net.minecraft.inventory.Container;

/**
 * Manages a Anvil GUI Inventory.
 */
public class AnvilManager extends ContainerManager {
    private static AnvilManager instance = null;

    /**
     * Creates a new Anvil Manager with the given container.
     *
     * @param container The container from a crafting GUI
     */
    private AnvilManager(Container container) {
        super(container);
    }

    /**
     * Returns a Anvil Manager Instance operating on the given container
     *
     * @param container A container from a GUI
     * @return manager-singleton
     */
    public static AnvilManager getInstance(Container container) {
        if (instance == null) {
            instance = new AnvilManager(container);
        } else {
            instance.container = container;
        }
        return instance;
    }

    @Override
    protected int specificKeyToSlotIndex() {
        return mapKeyToSlot(-1, -1, -1, 0, 1, -1, -1, -1, -1);
    }

    @Override
    protected int getInventoryStartIndex() {
        return 3;
    }

    @Override
    protected int getInteractionSlotIndex() {
        return 2;
    }

    @Override
    protected void onInteractionKeyPressed() {
        interact();
    }

    @Override
    protected int[] getDropSlots() {
        return new int[]{0, 1};
    }

    @Override
    protected void interact() {

        Logger.info("interact()", "Clicked on Anvil Output.");
        leftClick(2);

    }


}
