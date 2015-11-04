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
    private int guiBasePosition;
    private int guiBaseOffset;
    private GuiType guiType;

    //TODO:Give all buttons the name you think are good for the eventhandling
    private int buttonSaveID = 901;
    private int buttonAbortID = 902;
    private int buttonSpaceID = 903;
    private int buttonShiftID = 904;

    private int buttonCraftingID = 301;
    private int buttonBrewingstandID = 302;
    private int buttonDispenserID = 303;
    private int buttonFurnaceID = 304;
    private int buttonEnchantmentID = 305;
    private int buttonInventoryID = 306;
    private int buttonVillagerID = 307;

    public enum GuiType{
        CRAFTING,
        FURNACE,
        BREWINGSTAND,
        ENCHANTMENT,
        INVENTORY,
        VILAGER,
        DISPENSER;
    }

    @Override
    public void initGui() {

        // Fill vars
        guiBaseOffset = 35;
        guiBasePosition = width / 2 - guiBaseOffset;

        //Button Init
        addStandardButtons();
        guiType = GuiType.CRAFTING;
        addCraftingButtons();
    }

    /*
    Hallo Skate vielleicht gefällt dir ja meine Idee - mit Buttons - oben das GUI auszuwechseln.
     */

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        //Setting up
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawDefaultBackground(); //drawWorldBackground(0);
            //Title
        drawCenteredString(fontRendererObj, "Crafting Keys Config", width / 2, height / 2 - 115, pureWhite.getRGB());
            // Info-text and fake line
        drawCenteredString(fontRendererObj, "Configure your keys here. Click \"Save\" when finished!", width / 2, height / 2 - 10, pureWhite.getRGB());
        drawCenteredString(fontRendererObj, "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -", width / 2, height / 2 + 8, lightGray.getRGB());
        drawCenteredString(fontRendererObj, "Use the buttons to set the GUI which you want to edit the Keybindings for!", width / 2, height / 2 + 23, pureWhite.getRGB());
            // Key Info
        drawCenteredString(fontRendererObj, "Stack Key", guiBasePosition + 130, height / 2 - 96, pureWhite.getRGB());
        drawCenteredString(fontRendererObj, "Drop Key", guiBasePosition + 130, height / 2 - 58, pureWhite.getRGB());

        //drawHorizontalLine(craftingBasePosition - 100, width / 2 + 100 + craftingBaseOffset, height / 2 - 20, pureWhite.getRGB());

        // Insert Gui by selected Type
        if(guiType == GuiType.CRAFTING){
            genCraftingInfo();
        }else if(guiType == GuiType.FURNACE){
            genFurnaceInfo();
        }else if(guiType == GuiType.BREWINGSTAND){
            genBrewingStandInfo();
        }else if(guiType == GuiType.DISPENSER){

        }else if(guiType == GuiType.ENCHANTMENT){

        }else if(guiType == GuiType.INVENTORY){

        }else if(guiType == GuiType.VILAGER){

        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    //TODO: Set here the buttons with the function
    @Override
    public void actionPerformed(GuiButton button) {
        if (button.id == buttonAbortID) {

        }else if (button.id == buttonSaveID) {

        }else if(button.id == buttonCraftingID){
            guiType = GuiType.CRAFTING;
            addCraftingButtons();
        }else if(button.id == buttonInventoryID) {
            guiType = GuiType.INVENTORY;
        }else if(button.id == buttonEnchantmentID){
            guiType = GuiType.ENCHANTMENT;
        }else if(button.id == buttonBrewingstandID){
            guiType = GuiType.BREWINGSTAND;
            addBrewingStandButtons();
        }else if(button.id == buttonDispenserID){
            guiType = GuiType.DISPENSER;
        }else if(button.id == buttonFurnaceID){
            guiType = GuiType.FURNACE;
            addFurnaceButtons();
        }else if(button.id == buttonVillagerID){
            guiType = GuiType.VILAGER;
        }
    }

    //GUI GENERATORS:
    /*
    TODO: Fix everything for changing the gui...
    TODO: Create buttons
    TODO: Create generators for all other textures
     */

    private void genCraftingInfo(){
        mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/crafting_table.png"));
        drawTexturedModalRect(guiBasePosition - 86, height / 2 - 100, 1, 0, 174, 80);
    }

    private void addCraftingButtons(){
        buttonList.clear();
        buttonList.add((new GuiButton(0, guiBasePosition - 60, height / 2 - 87, 20, 20, "Q")));
        buttonList.add((new GuiButton(1, guiBasePosition - 41, height / 2 - 87, 20, 20, "W")));
        buttonList.add((new GuiButton(2, guiBasePosition - 22, height / 2 - 87, 20, 20, "E")));
        buttonList.add((new GuiButton(3, guiBasePosition - 60, height / 2 - 68, 20, 20, "A")));
        buttonList.add((new GuiButton(4, guiBasePosition - 41, height / 2 - 68, 20, 20, "S")));
        buttonList.add((new GuiButton(5, guiBasePosition - 22, height / 2 - 68, 20, 20, "D")));
        buttonList.add((new GuiButton(6, guiBasePosition - 60, height / 2 - 49, 20, 20, "Y")));
        buttonList.add((new GuiButton(7, guiBasePosition - 41, height / 2 - 49, 20, 20, "X")));
        buttonList.add((new GuiButton(8, guiBasePosition - 22, height / 2 - 49, 20, 20, "C")));
        buttonList.add((new GuiButton(9, guiBasePosition + 34, height / 2 - 67, 22, 20, "Ctrl")));
        addStandardButtons();
    }

    private void genFurnaceInfo() {
        GL11.glColor4f(1F, 1F, 1F, 1F);
        mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/furnace.png"));
        drawTexturedModalRect(guiBasePosition - 86, height / 2 - 100, 1, 0, 174, 80);

        /*drawString(fontRendererObj, "Furnace", xBase + 5, yBase + 5, lightGray.getRGB());
        drawCenteredString(fontRendererObj, "W", xBase + 63, yBase + 21, highlight.getRGB());
        drawCenteredString(fontRendererObj, "S", xBase + 63, yBase + 57, highlight.getRGB());
        drawCenteredString(fontRendererObj, "Ctrl", xBase + 123, yBase + 39, highlight.getRGB());*/
    }

    private void addFurnaceButtons(){
        buttonList.clear();



        addStandardButtons();
    }

    private void genBrewingStandInfo() {
        GL11.glColor4f(1F, 1F, 1F, 1.0F);
        mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/brewing_stand.png"));
        drawTexturedModalRect(guiBasePosition - 86, height / 2 - 100, 1, 0, 174, 80);

        /*drawString(fontRendererObj, "Brewing Stand", guiBasePosition + 5, height / 2 - 100 + 5, lightGray.getRGB());
        drawCenteredString(fontRendererObj, "W", guiBasePosition + 86, height / 2 - 100 + 21, highlight.getRGB());
        drawCenteredString(fontRendererObj, "S", guiBasePosition + 86, height / 2 - 100 + 58, highlight.getRGB());
        drawCenteredString(fontRendererObj, "A", guiBasePosition + 63, height / 2 - 100 + 50, highlight.getRGB());
        drawCenteredString(fontRendererObj, "D", guiBasePosition + 109, height / 2 - 100 + 50, highlight.getRGB());*/
    }

    public void addBrewingStandButtons(){

    }


    private void addStandardButtons(){
        // Add control buttons
        buttonList.add((new GuiButton(buttonAbortID, width - 53, 3, 50, 20, "Abort")));
        buttonList.add((new GuiButton(buttonSaveID, width - 53, 26, 50, 20, "Save")));
        //I don't know if you need to change those for every gui
        buttonList.add((new GuiButton(buttonShiftID, guiBasePosition + 105, height / 2 - 84, 50, 20, "Shift")));
        buttonList.add((new GuiButton(buttonSpaceID, guiBasePosition + 105, height / 2 - 46, 50, 20, "Space")));
        //Add Switch Buttons
        buttonList.add((new GuiButton(buttonCraftingID, 15, height / 2 + 40, 70, 20, "Crafting")));
        buttonList.add((new GuiButton(buttonInventoryID, 15 + 70 + 5, height / 2 + 40, 70, 20, "Inventory")));
        buttonList.add((new GuiButton(buttonFurnaceID, 15 + 2*70 + 2*5, height / 2 + 40, 70, 20, "Furnace")));
        buttonList.add((new GuiButton(buttonBrewingstandID, 15 + 3*70 + 3*5, height / 2 + 40, 90, 20, "Brewing Stand")));
        buttonList.add((new GuiButton(buttonDispenserID, 15 + 4*70 + 20 + 4*5, height / 2 + 40, 70, 20, "Dispenser")));
        buttonList.add((new GuiButton(buttonVillagerID, 15, height / 2 + 40 + 20 + 4, 70, 20, "Villager")));
        buttonList.add((new GuiButton(buttonEnchantmentID, 15 + 70 + 5, height / 2 + 40 + 20 + 4, 70, 20, "Enchantment")));
    }

    //TODO:Get strings from language file(Localizer)...

}
