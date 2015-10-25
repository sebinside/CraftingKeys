package de.skate702.craftingkeys.manager;

import de.skate702.craftingkeys.config.Config;
import de.skate702.craftingkeys.util.InputUtil;
import de.skate702.craftingkeys.util.Util;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import org.lwjgl.input.Keyboard;


public class CraftingManager extends ContainerManager {

    /**
     * Creates a new Crafting Manager with the given container.
     *
     * @param container The container from a crafting GUI
     */
    public CraftingManager(Container container) {
        super(container);
    }

    @Override
    public void acceptKey() {

        Slot currentHoveredSlot = InputUtil.getSlotAtMousePosition((GuiCrafting) Util.client.currentScreen);

        int slotIndex = specificKeyDownToSlotIndex();

        if (!InputUtil.isSameKey(slotIndex)) {

            if (isDropKeyDown()) {

                for (int i = 1; i < 10; i++) {
                    putStackToNextEmptySlot(i, true, false);
                }

            } else if (isInteractionKeyDown()) {

                if (isStackKeyDown()) {
                    // TODO: Get for all
                    System.out.println("Blabla");
                } else {
                    clickOnCraftingOutput(); // TODO: Change that shit!
                }

            } else if (slotIndex > 0 && currentHoveredSlot != null) {

                if (isStackKeyDown()) {
                    moveAll(currentHoveredSlot.slotNumber, slotIndex);
                } else {
                    move(currentHoveredSlot.slotNumber, slotIndex, 1);
                }

            }

        }

    }

    @Override
    protected int specificKeyDownToSlotIndex() {

        if (Keyboard.isKeyDown(Config.getKeyTopLeft())) {
            return 1;
        } else if (Keyboard.isKeyDown(Config.getkeyTopCenter())) {
            return 2;
        } else if (Keyboard.isKeyDown(Config.getKeyTopRight())) {
            return 3;
        } else if (Keyboard.isKeyDown(Config.getKeyCenterLeft())) {
            return 4;
        } else if (Keyboard.isKeyDown(Config.getKeyCenterCenter())) {
            return 5;
        } else if (Keyboard.isKeyDown(Config.getKeyCenterRight())) {
            return 6;
        } else if (Keyboard.isKeyDown(Config.getKeyLowerLeft())) {
            return 7;
        } else if (Keyboard.isKeyDown(Config.getKeyLowerCenter())) {
            return 8;
        } else if (Keyboard.isKeyDown(Config.getKeyLowerRight())) {
            return 9;
        } else if (Keyboard.isKeyDown(Config.getKeyInteract())) {
            return -101;
        } else if (Keyboard.isKeyDown(Config.getKeyDrop())) {
            return -102;
        } else {
            return -1;
        }

    }

    /**
     * Sends a click on the crafting output (craftingGUI or Inventory)
     */
    public void clickOnCraftingOutput() {

        //putItemAway(); // TODO!

        // Click on crafting output
        System.out.println("clickOnCraftingOutput(): Clicked on Crafing Output.");
        leftClick(0);


    }
}
