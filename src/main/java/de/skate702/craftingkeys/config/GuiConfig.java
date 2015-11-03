package de.skate702.craftingkeys.config;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GuiConfig extends GuiScreen {

    public static final int GuiID = 702;
    private static final Color pureWhite = new Color(255, 255, 255, 255);
    private static final Color lightGray = new Color(128, 128, 128, 255);
    private static final Color highlight = new Color(86, 144, 72, 255);
    private int craftingBasePosition;
    private int craftingBaseOffset;

    @Override
    public void initGui() {

        // Fill vars
        craftingBasePosition = width / 2 - craftingBaseOffset;
        craftingBaseOffset = 35;

        // Add crafting Buttons
        buttonList.add((new GuiButton(0, craftingBasePosition - 60, height / 2 - 87, 20, 20, "Q")));
        buttonList.add((new GuiButton(1, craftingBasePosition - 41, height / 2 - 87, 20, 20, "W")));
        buttonList.add((new GuiButton(2, craftingBasePosition - 22, height / 2 - 87, 20, 20, "E")));
        buttonList.add((new GuiButton(3, craftingBasePosition - 60, height / 2 - 68, 20, 20, "A")));
        buttonList.add((new GuiButton(4, craftingBasePosition - 41, height / 2 - 68, 20, 20, "S")));
        buttonList.add((new GuiButton(5, craftingBasePosition - 22, height / 2 - 68, 20, 20, "D")));
        buttonList.add((new GuiButton(6, craftingBasePosition - 60, height / 2 - 49, 20, 20, "Y")));
        buttonList.add((new GuiButton(7, craftingBasePosition - 41, height / 2 - 49, 20, 20, "X")));
        buttonList.add((new GuiButton(8, craftingBasePosition - 22, height / 2 - 49, 20, 20, "C")));

        buttonList.add((new GuiButton(9, craftingBasePosition + 34, height / 2 - 67, 22, 20, "Ctrl")));
        buttonList.add((new GuiButton(10, craftingBasePosition + 105, height / 2 - 84, 50, 20, "Shift")));
        buttonList.add((new GuiButton(11, craftingBasePosition + 105, height / 2 - 46, 50, 20, "Space")));

        // Add control buttons
        buttonList.add((new GuiButton(12, width - 53, 3, 50, 20, "Abort")));
        buttonList.add((new GuiButton(13, width - 53, 26, 50, 20, "Save")));

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        // Fill vars
        int infoPositionLeft = 20;
        int infoPositionRight = width - 194;
        int infoPositionHeight = height / 2 + 25;

        // Reset the GL-Color to pure white
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        // Init the Background
        drawDefaultBackground(); //drawWorldBackground(0);

        // Title
        drawCenteredString(fontRendererObj, "Crafting Keys Config", width / 2, height / 2 - 115, pureWhite.getRGB());

        // Crafting Table texture
        mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/crafting_table.png"));
        drawTexturedModalRect(craftingBasePosition - 86, height / 2 - 100, 1, 0, 174, 80);

        // Key Info
        drawCenteredString(fontRendererObj, "Stack Key", craftingBasePosition + 130, height / 2 - 96, pureWhite.getRGB());
        drawCenteredString(fontRendererObj, "Drop Key", craftingBasePosition + 130, height / 2 - 58, pureWhite.getRGB());

        // Line under crafting table texture
        drawHorizontalLine(craftingBasePosition - 100, width / 2 + 100 + craftingBaseOffset,
                height / 2 - 20, pureWhite.getRGB());

        // Info-text and fake line
        drawCenteredString(fontRendererObj, "Configure your keys here. Click \"Save\" when finished!",
                width / 2, height / 2 - 10, pureWhite.getRGB());
        drawCenteredString(fontRendererObj, "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -",
                width / 2, height / 2 + 8, lightGray.getRGB());

        // Insert Info screens
        genFurnaceInfo(infoPositionRight, infoPositionHeight);
        genBrewingStandInfo(infoPositionLeft, infoPositionHeight);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void genFurnaceInfo(int xBase, int yBase) {
        GL11.glColor4f(0.5F, 0.5F, 0.5F, 1.0F);
        mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/furnace.png"));
        drawTexturedModalRect(xBase, yBase, 1, 0, 174, 80);
        drawString(fontRendererObj, "Furnace", xBase + 5, yBase + 5, lightGray.getRGB());
        drawCenteredString(fontRendererObj, "W", xBase + 63, yBase + 21, highlight.getRGB());
        drawCenteredString(fontRendererObj, "S", xBase + 63, yBase + 57, highlight.getRGB());
        drawCenteredString(fontRendererObj, "Ctrl", xBase + 123, yBase + 39, highlight.getRGB());
    }

    private void genBrewingStandInfo(int xBase, int yBase) {
        GL11.glColor4f(0.5F, 0.5F, 0.5F, 1.0F);
        mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/brewing_stand.png"));
        drawTexturedModalRect(xBase, yBase, 1, 0, 174, 80);
        drawString(fontRendererObj, "Brewing Stand", xBase + 5, yBase + 5, lightGray.getRGB());
        drawCenteredString(fontRendererObj, "W", xBase + 86, yBase + 21, highlight.getRGB());
        drawCenteredString(fontRendererObj, "S", xBase + 86, yBase + 58, highlight.getRGB());
        drawCenteredString(fontRendererObj, "A", xBase + 63, yBase + 50, highlight.getRGB());
        drawCenteredString(fontRendererObj, "D", xBase + 109, yBase + 50, highlight.getRGB());
    }

    // TODO: generators for all other textures. But... I hate this job. Does anyone wants to do this?
    // TODO: We need volunteers! Let's go! Make a pull request. THANK YOU!

}
