package de.skate702.craftingkeys.config;

// NEW_1_9 cpw.mods.fml => net.minecraftforge.fml

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiConfigHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == GuiConfig.GuiID) {
            return new GuiConfig();
        }

        return null;
    }
}

