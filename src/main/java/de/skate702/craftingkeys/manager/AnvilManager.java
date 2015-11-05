package de.skate702.craftingkeys.manager;

import de.skate702.craftingkeys.util.Logger;
import net.minecraft.inventory.Container;

public class AnvilManager extends ContainerManager {
    private static AnvilManager instance = null;

    private AnvilManager(Container container) {
        super(container);
    }

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
        return mapKeyToSlot(-1, -1, -1, 0, 1, -1 , -1, -1, -1);
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
        leftClick(2);
    }


}
