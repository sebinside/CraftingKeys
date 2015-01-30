package de.skate702.craftingkeys.proxies;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * The common proxy.
 * 
 * @author skate702
 *
 */
public class CraftingKeysProxy {

	/**
	 * Register Renderers (not needed here).
	 */
	public void registerRenderers() {

	}

	/**
	 * The Minecraft call to send a mouse click to a GUI. [Based on INVTW]
	 * 
	 * @param controller
	 *            The playerController (from Client)
	 * @param windowId
	 *            The current Windows ID (from GUI)
	 * @param slot
	 *            The slot to click on (slot index)
	 * @param rightClick
	 *            1, if right click. Otherwise 0
	 * @param action
	 *            0
	 * @param player
	 *            The current player (from Client)
	 */
	@SideOnly(Side.CLIENT)
	public void sendSlotClick(PlayerControllerMP controller, int windowId, int slot, int rightClick, int action,
			EntityPlayer player) {

	}

}
