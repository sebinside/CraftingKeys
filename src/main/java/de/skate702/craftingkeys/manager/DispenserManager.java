package de.skate702.craftingkeys.manager;

import de.skate702.craftingkeys.util.Logger;
import net.minecraft.inventory.Container;

/**
 * Manages a Dispenser GUI Inventory.
 */
public class DispenserManager extends ContainerManager {

    private static DispenserManager instance = null;

    /**
     * Creates a new Dispenser Manager with the given container.
     *
     * @param container The container from a crafting GUI
     */
    private DispenserManager(Container container) {
        super(container);
    }

    /**
     * Returns a Dispenser Manager Instance operating on the given container
     *
     * @param container A container from a GUI
     * @return manager-singleton
     */
    public static DispenserManager getInstance(Container container) {
        if (instance == null) {
            instance = new DispenserManager(container);
        } else {
            instance.container = container;
        }
        return instance;
    }

    @Override
    protected int specificKeyToSlotIndex() {
        return mapKeyToSlot(0, 1, 2, 3, 4, 5, 6, 7, 8);
    }

    @Override
    protected int getInventoryStartIndex() {
        return 9;
    }

    @Override
    protected int getInteractionSlotIndex() {
        return -1;
    }

    @Override
    protected void onInteractionKeyPressed() {
        Logger.info("onInteractionKeyPressed()", "Dispenser have no interaction!");
    }

    @Override
    protected int[] getDropSlots() {
        return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
    }

    @Override
    protected void interact() {

    }
}
