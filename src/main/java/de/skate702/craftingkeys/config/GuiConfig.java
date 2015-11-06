package de.skate702.craftingkeys.config;

import de.skate702.craftingkeys.CraftingKeys;
import de.skate702.craftingkeys.util.LanguageLocalizer;
import de.skate702.craftingkeys.util.Logger;
import de.skate702.craftingkeys.util.Util;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GuiConfig extends GuiScreen {

    public static final int GuiID = 702;
    private static final Color pureWhite = new Color(255, 255, 255, 255);
    private static final Color lightGray = new Color(128, 128, 128, 255);
    private static final Color highlight = new Color(86, 144, 72, 255);
    private int guiBasePosition;
    private int guiBaseOffset;

    private int guiShowBasePosition;
    private int guiShowBaseHeight;
    private GuiType guiShowType;

    private int waitingTimeMS = 2000;
    private int guiShowState;
    private long lastTime = 0;
    private long currentTime;

    //TODO:Give all buttons the name you think are good for the eventhandling
    private int buttonSaveID = 901;
    private int buttonAbortID = 902;
    private int buttonDropID = 903;
    private int buttonStackID = 904;

    /*private int buttonAnvilID = 301;
    private int buttonBrewingstandID = 302;
    private int buttonDispenserID = 303;
    private int buttonFurnaceID = 304;
    private int buttonEnchantmentID = 305;
    private int buttonInventoryID = 306;
    private int buttonVillagerID = 307;*/

    private int buttonUpLeftID = 111;
    private int buttonUpMiddleID = 112;
    private int buttonUpRightID = 113;

    private int buttonMiddleLeftID = 121;
    private int buttonMiddleMiddleID = 122;
    private int buttonMiddleRightID = 123;

    private int buttonLowLeftID = 131;
    private int buttonLowMiddleID = 132;
    private int buttonLowRightID = 133;

    private int buttonInteractID = 100;

    @Override
    public void initGui() {

        // Fill vars
        guiBaseOffset = 35;
        guiBasePosition = width / 2 - guiBaseOffset;

        guiShowBasePosition = width / 2 - 35;
        guiShowBaseHeight = height / 2 + 35;

        guiShowType = GuiType.ANVIL;
        guiShowState = 0;
        //Button Init
        addStandardButtons();
        lastTime = Util.client.getSystemTime();
        currentTime = Util.client.getSystemTime();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        //Setting up
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawDefaultBackground(); //drawWorldBackground(0);
        //Title
        drawCenteredString(fontRendererObj, LanguageLocalizer.localize("craftingkeys.config.title"), width / 2, height / 2 - 115, pureWhite.getRGB());
        // Info-text and fake line
        drawCenteredString(fontRendererObj, LanguageLocalizer.localize("craftingkeys.config.description"), width / 2, height / 2 - 10, pureWhite.getRGB());
        drawCenteredString(fontRendererObj, "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -", width / 2, height / 2 + 8, lightGray.getRGB());
        drawCenteredString(fontRendererObj, LanguageLocalizer.localize("craftingkeys.config.info"), width / 2, height / 2 + 20, pureWhite.getRGB());
        // Key Info
        drawCenteredString(fontRendererObj, "Stack Key", guiBasePosition + 130, height / 2 - 96, pureWhite.getRGB());
        drawCenteredString(fontRendererObj, "Drop Key", guiBasePosition + 130, height / 2 - 58, pureWhite.getRGB());
        //Draw line to let it look better
        drawHorizontalLine(guiBasePosition - 86, guiBasePosition + 85, height / 2 - 20, pureWhite.getRGB());

        drawCraftingTable();
        // Insert Gui by selected Type
        currentTime = Util.client.getSystemTime();
        if(currentTime - lastTime > waitingTimeMS){
            nextShowGui();
            lastTime = Util.client.getSystemTime();
            currentTime = Util.client.getSystemTime();
        }

        switch (guiShowType) {

            case FURNACE:
                genFurnaceInfo();
                break;
            case BREWINGSTAND:
                genBrewingStandInfo();
                break;
            case DISPENSER:
                genDispenserInfo();
                break;
            case ENCHANTMENT:
                genEnchantmentInfo();
                break;
            case INVENTORY:
                genInventoryInfo();
                break;
            case VILAGER:
                genVillagerInfo();
                break;
            case ANVIL:
                genAnvilInfo();
                break;
        }

        //Draw line to let it look better
        drawHorizontalLine(guiShowBasePosition - 86, guiShowBasePosition + 85, guiShowBaseHeight + 80, lightGray.getRGB());
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if(button.id == buttonAbortID) {
            Logger.warn("actionPerformed(b)", "Not implemented yet");
        }else if(button.id == buttonSaveID) {
            Logger.warn("actionPerformed(b)", "Not implemented yet");
        }

        if(button.id == buttonInteractID){

        }else if(button.id == buttonStackID){

        }else if(button.id == buttonDropID){

        }else if(button.id == buttonUpLeftID){

        }else if(button.id == buttonUpMiddleID){

        }else if(button.id == buttonUpRightID){

        }else if(button.id == buttonMiddleLeftID){

        }else if(button.id == buttonMiddleMiddleID){

        }else if(button.id == buttonMiddleRightID){

        }else if(button.id == buttonLowLeftID){

        }else if(button.id == buttonLowMiddleID){

        }else if(button.id == buttonLowRightID){

        }
        /* else if (button.id == buttonInventoryID) {
            guiShowType = GuiType.INVENTORY;
        } else if (button.id == buttonEnchantmentID) {
            guiShowType = GuiType.ENCHANTMENT;
        } else if (button.id == buttonBrewingstandID) {
            guiShowType = GuiType.BREWINGSTAND;
        } else if (button.id == buttonDispenserID) {
            guiShowType = GuiType.DISPENSER;
        } else if (button.id == buttonFurnaceID) {
            guiShowType = GuiType.FURNACE;
        } else if (button.id == buttonVillagerID) {
            guiShowType = GuiType.VILAGER;
        } else if (button.id == buttonAnvilID) {
            guiShowType = GuiType.ANVIL;
        }*/


    }

    private void drawCraftingTable() {
        GL11.glColor4f(1F, 1F, 1F, 1F);
        mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/crafting_table.png"));
        drawTexturedModalRect(guiBasePosition - 86, height / 2 - 100, 1, 0, 174, 80);
        drawString(fontRendererObj, "Crafting Table", guiBasePosition - 33, height / 2 - 100 + 3, lightGray.getRGB());
    }

    //GUI GENERATORS:
    /*
    TODO: Create strings for showing the Keybindings
     */

    private void genFurnaceInfo() {
        GL11.glColor4f(0.5F, 0.5F, 0.5F, 1F);
        mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/furnace.png"));
        drawTexturedModalRect(guiShowBasePosition - 86, guiShowBaseHeight, 1, 0, 174, 80);

        drawString(fontRendererObj, "Furnace", guiShowBasePosition - 15, guiShowBaseHeight + 3, lightGray.getRGB());

        drawCenteredString(fontRendererObj, "W", guiShowBasePosition - 86 + 63, guiShowBaseHeight + 21, highlight.getRGB());
        drawCenteredString(fontRendererObj, "S", guiShowBasePosition - 86 + 63, guiShowBaseHeight + 57, highlight.getRGB());
        drawCenteredString(fontRendererObj, "Ctrl", guiShowBasePosition - 86 + 123, guiShowBaseHeight + 39, highlight.getRGB());
    }

    private void genBrewingStandInfo() {
        GL11.glColor4f(0.5F, 0.5F, 0.5F, 1F);
        mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/brewing_stand.png"));
        drawTexturedModalRect(guiShowBasePosition - 86, guiShowBaseHeight, 1, 0, 174, 80);

        drawString(fontRendererObj, "Brewing Stand", guiShowBasePosition - 33, guiShowBaseHeight + 3, lightGray.getRGB());

        drawCenteredString(fontRendererObj, "W", guiBasePosition, guiShowBaseHeight + 21, highlight.getRGB());
        drawCenteredString(fontRendererObj, "S", guiBasePosition, guiShowBaseHeight + 58, highlight.getRGB());
        drawCenteredString(fontRendererObj, "A", guiBasePosition - 86 + 63, guiShowBaseHeight + 50, highlight.getRGB());
        drawCenteredString(fontRendererObj, "D", guiBasePosition - 86 + 109, guiShowBaseHeight + 50, highlight.getRGB());
    }

    private void genEnchantmentInfo() {
        GL11.glColor4f(0.5F, 0.5F, 0.5F, 1F);
        mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/enchanting_table.png"));
        drawTexturedModalRect(guiShowBasePosition - 86, guiShowBaseHeight, 1, 0, 174, 80);
        drawString(fontRendererObj, "Enchanting", guiShowBasePosition - 20, guiShowBaseHeight + 3, lightGray.getRGB());
    }

    private void genAnvilInfo() {
        GL11.glColor4f(0.5F, 0.5F, 0.5F, 1F);
        mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/anvil.png"));
        drawTexturedModalRect(guiShowBasePosition - 86, guiShowBaseHeight, 1, 0, 174, 80);
        drawString(fontRendererObj, "Anvil", guiShowBasePosition - 17, guiShowBaseHeight + 3, lightGray.getRGB());
    }

    private void genVillagerInfo() {
        GL11.glColor4f(0.5F, 0.5F, 0.5F, 1F);
        mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/villager.png"));
        drawTexturedModalRect(guiShowBasePosition - 86, guiShowBaseHeight, 1, 0, 174, 80);
        drawString(fontRendererObj, "Villager", guiShowBasePosition - 17, guiShowBaseHeight + 3, lightGray.getRGB());
    }

    private void genInventoryInfo() {
        GL11.glColor4f(0.5F, 0.5F, 0.5F, 1F);
        mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
        drawTexturedModalRect(guiShowBasePosition - 86, guiShowBaseHeight, 1, 0, 174, 80);
        drawString(fontRendererObj, "Inventory", guiShowBasePosition - 20, guiShowBaseHeight + 3, lightGray.getRGB());
    }

    private void genDispenserInfo() {
        GL11.glColor4f(0.5F, 0.5F, 0.5F, 1F);
        mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/dispenser.png"));
        drawTexturedModalRect(guiShowBasePosition - 86, guiShowBaseHeight, 1, 0, 174, 80);
        drawString(fontRendererObj, "Dispenser", guiShowBasePosition - 23, guiShowBaseHeight + 3, lightGray.getRGB());
    }

    private void addStandardButtons() {
        // Add control buttons
        buttonList.add((new GuiButton(buttonAbortID, width - 53, 3, 50, 20, LanguageLocalizer.localize("craftingkeys.config.button.abort"))));
        buttonList.add((new GuiButton(buttonSaveID, width - 53, 26, 50, 20, LanguageLocalizer.localize("craftingkeys.config.button.save"))));
        buttonList.add((new GuiButton(buttonStackID, guiBasePosition + 105, height / 2 - 84, 50, 20, "Shift")));
        buttonList.add((new GuiButton(buttonDropID, guiBasePosition + 105, height / 2 - 46, 50, 20, "Space")));
        //Add Switch Buttons
        /*
        buttonList.add((new GuiButton(buttonInventoryID, guiShowBasePosition - 86 - 70 - 5, guiShowBaseHeight, 70, 20, "Inventory")));
        buttonList.add((new GuiButton(buttonFurnaceID, guiShowBasePosition - 86 - 70 - 5, guiShowBaseHeight + 20 + 1, 70, 20, "Furnace")));
        buttonList.add((new GuiButton(buttonBrewingstandID, guiShowBasePosition + 86 + 5, guiShowBaseHeight, 90, 20, "Brewing Stand")));
        buttonList.add((new GuiButton(buttonDispenserID, guiShowBasePosition - 86 - 70 - 5, guiShowBaseHeight + 2 * 20 + 2, 70, 20, "Dispenser")));
        buttonList.add((new GuiButton(buttonVillagerID, guiShowBasePosition + 86 + 5, guiShowBaseHeight + 20 + 1, 70, 20, "Villager")));
        buttonList.add((new GuiButton(buttonEnchantmentID, guiShowBasePosition + 86 + 5, guiShowBaseHeight + 2 * 20 + 2, 70, 20, "Enchanting")));
        buttonList.add((new GuiButton(buttonAnvilID, guiShowBasePosition - 86 - 70 - 5, guiShowBaseHeight + 3 * 20 + 3, 70, 20, "Anvil")));
        */
        //Adding Crafting Buttons
        addCraftingButtons();
    }

    private void addCraftingButtons() {
        buttonList.add((new GuiButton(buttonUpLeftID, guiBasePosition - 60, height / 2 - 87, 20, 20, Keyboard.getKeyName(CraftingKeys.config.keyUpLeft))));
        buttonList.add((new GuiButton(buttonUpMiddleID, guiBasePosition - 41, height / 2 - 87, 20, 20, "W")));
        buttonList.add((new GuiButton(buttonUpRightID, guiBasePosition - 22, height / 2 - 87, 20, 20, "E")));

        buttonList.add((new GuiButton(buttonMiddleLeftID, guiBasePosition - 60, height / 2 - 68, 20, 20, "A")));
        buttonList.add((new GuiButton(buttonMiddleMiddleID, guiBasePosition - 41, height / 2 - 68, 20, 20, "S")));
        buttonList.add((new GuiButton(buttonMiddleRightID, guiBasePosition - 22, height / 2 - 68, 20, 20, "D")));

        buttonList.add((new GuiButton(buttonLowLeftID, guiBasePosition - 60, height / 2 - 49, 20, 20, "Y")));
        buttonList.add((new GuiButton(buttonLowMiddleID, guiBasePosition - 41, height / 2 - 49, 20, 20, "X")));
        buttonList.add((new GuiButton(buttonLowRightID, guiBasePosition - 22, height / 2 - 49, 20, 20, "C")));

        buttonList.add((new GuiButton(buttonInteractID, guiBasePosition + 34, height / 2 - 67, 22, 20, "Ctrl")));
    }

    public void nextShowGui(){
        switch(guiShowState){
            case 0:
                guiShowType = GuiType.FURNACE;
                break;
            case 1:
                guiShowType = GuiType.BREWINGSTAND;
                break;
            case 2:
                guiShowType = GuiType.INVENTORY;
                break;
            case 3:
                guiShowType = GuiType.ENCHANTMENT;
                break;
            case 4:
                guiShowType = GuiType.DISPENSER;
                break;
            case 5:
                guiShowType = GuiType.VILAGER;
                break;
            case 6:
                guiShowType = GuiType.ANVIL;
                break;
        }
        if(guiShowState >= 6){
            guiShowState = 0;
        }else{
            guiShowState++;
        }


    }

    public enum GuiType {
        ANVIL,
        FURNACE,
        BREWINGSTAND,
        ENCHANTMENT,
        INVENTORY,
        VILAGER,
        DISPENSER
    }

    //TODO:Get strings from language file(Localizer)...
    //TODO:Add things that make this GUI an Config ... :P
}
