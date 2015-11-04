package de.skate702.craftingkeys.config;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class GuiConfig extends GuiScreen {

    public static final int GuiID = 702;
    private GuiTextField testText;

    @Override
    public void initGui() {

        buttonList.add((new GuiButton(0, width / 2 - 58, height / 2 - 84, 19, 18, "Q")));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        //GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        //GL11.glTranslatef(0.0f, 0.0f, -1.0f);

        Color pureWhite = new Color(255, 255, 255, 255);
        //drawWorldBackground(0);
        drawDefaultBackground();

        drawCenteredString(fontRendererObj, "Crafting Keys Config", width / 2, height / 2 - 115, pureWhite.getRGB());

        mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/crafting_table.png"));
        drawTexturedModalRect(width / 2 - 86, height / 2 - 100, 1, 0, 174, 80);

        drawHorizontalLine(width / 2 - 110, width / 2 + 110, height / 2 - 20, pureWhite.getRGB());

        drawCenteredString(fontRendererObj, "Configure your keys here. Click \"Save\" when finished!", width / 2, height / 2 - 10, pureWhite.getRGB());

        //drawRect(width / 2 - 100, width / 2 + 100, height / 2 - 5, height / 2 + 5, pureWhite.getRGB());

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

}
