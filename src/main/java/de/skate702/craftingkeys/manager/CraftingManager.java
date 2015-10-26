package de.skate702.craftingkeys.manager;

import de.skate702.craftingkeys.config.Config;
import de.skate702.craftingkeys.util.InputUtil;
import de.skate702.craftingkeys.util.Logger;
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
                    moveStackToInventory(i);
                }

            } else if (isInteractionKeyDown()) {

                if (isStackKeyDown()) {

                    int oldStackSize = -1;
                    clickOnCraftingOutput();

                    while (Util.client.thePlayer.inventory.getItemStack() != null &&
                            oldStackSize != Util.client.thePlayer.inventory.getItemStack().stackSize) {

                        oldStackSize = Util.client.thePlayer.inventory.getItemStack().stackSize;
                        clickOnCraftingOutput();
                    }

                } else {
                    clickOnCraftingOutput();
                }

            } else if (slotIndex > 0 && currentHoveredSlot != null) {

                if (isStackKeyDown()) {
                    moveAll(currentHoveredSlot.slotNumber, slotIndex);
                    moveStackToInventory(-1);
                } else {
                    move(currentHoveredSlot.slotNumber, slotIndex, 1);
                }
            }
        }
    }

    @Override
    protected int specificKeyDownToSlotIndex() {

        if (Keyboard.isKeyDown(Config.getKeyTopLeft()) ||
                Config.isNumPadEnabled() && Keyboard.isKeyDown(71)) {
            return 1;
        } else if (Keyboard.isKeyDown(Config.getkeyTopCenter()) ||
                Config.isNumPadEnabled() && Keyboard.isKeyDown(72)) {
            return 2;
        } else if (Keyboard.isKeyDown(Config.getKeyTopRight()) ||
                Config.isNumPadEnabled() && Keyboard.isKeyDown(73)) {
            return 3;
        } else if (Keyboard.isKeyDown(Config.getKeyCenterLeft()) ||
                Config.isNumPadEnabled() && Keyboard.isKeyDown(75)) {
            return 4;
        } else if (Keyboard.isKeyDown(Config.getKeyCenterCenter()) ||
                Config.isNumPadEnabled() && Keyboard.isKeyDown(76)) {
            return 5;
        } else if (Keyboard.isKeyDown(Config.getKeyCenterRight()) ||
                Config.isNumPadEnabled() && Keyboard.isKeyDown(77)) {
            return 6;
        } else if (Keyboard.isKeyDown(Config.getKeyLowerLeft()) ||
                Config.isNumPadEnabled() && Keyboard.isKeyDown(79)) {
            return 7;
        } else if (Keyboard.isKeyDown(Config.getKeyLowerCenter()) ||
                Config.isNumPadEnabled() && Keyboard.isKeyDown(80)) {
            return 8;
        } else if (Keyboard.isKeyDown(Config.getKeyLowerRight()) ||
                Config.isNumPadEnabled() && Keyboard.isKeyDown(81)) {
            return 9;
        } else if (Keyboard.isKeyDown(Config.getKeyInteract())) {
            return -101;
        } else if (Keyboard.isKeyDown(Config.getKeyDrop())) {
            return -102;
        } else {
            return -1;
        }

    }

    @Override
    protected int getInventoryStartIndex() {
        return 10;
    }

    /**
     * Sends a click on the crafting output (craftingGUI or Inventory)
     */
    private void clickOnCraftingOutput() {

        // Click on crafting output
        Logger.info("clickOnCraftingOutput()", "Clicked on Crafing Output.");
        leftClick(0);

    }
}
