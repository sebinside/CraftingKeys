package de.skate702.craftingkeys.proxies;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;

/**
 * The common proxy.
 *
 * @author skate702
 */
public class CraftingKeysProxy {

    /**
     * The Minecraft call to send a mouse click to a GUI. [Based on INVTW]
     *
     * @param controller The playerController (from Client)
     * @param windowId   The current Windows ID (from GUI)
     * @param slot       The slot to click on (slot index)
     * @param rightClick 1, if right click. Otherwise 0
     * @param action     0
     * @param player     The current player (from Client)
     */
    @SideOnly(Side.CLIENT)
    public void sendSlotClick(PlayerControllerMP controller, int windowId, int slot, int rightClick, @SuppressWarnings("SameParameterValue") int action,
                              EntityPlayer player) {

    }

}