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

    private int tmpKeyUpLeft;
    private int tmpKeyUpMiddle;
    private int tmpKeyUpRight;

    private int tmpKeyMiddleLeft;
    private int tmpKeyMiddleMiddle;
    private int tmpKeyMiddleRight;

    private int tmpKeyLowLeft;
    private int tmpKeyLowMiddle;
    private int tmpKeyLowRight;

    private int tmpKeyInteract;

    private int tmpKeyStack;
    private int tmpKeyDrop;

    private GuiButton selectedButton;

    //TODO:Give all buttons the name you think are good for the eventhandling
    private int buttonSaveID = 901;
    private int buttonAbortID = 902;

    private GuiButton buttonDrop;
    private GuiButton buttonStack;

    /*private int buttonAnvilID = 301;
    private int buttonBrewingstandID = 302;
    private int buttonDispenserID = 303;
    private int buttonFurnaceID = 304;
    private int buttonEnchantmentID = 305;
    private int buttonInventoryID = 306;
    private int buttonVillagerID = 307;*/

    private GuiButton buttonUpLeft;
    private GuiButton buttonUpMiddle;
    private GuiButton buttonUpRight;

    private GuiButton buttonMiddleLeft;
    private GuiButton buttonMiddleMiddle;
    private GuiButton buttonMiddleRight;

    private GuiButton buttonLowLeft;
    private GuiButton buttonLowMiddle;
    private GuiButton buttonLowRight;

    private GuiButton buttonInteract;


    public GuiConfig(){
        CraftingKeys.config.load();
        tmpKeyUpLeft = CraftingKeys.config.keyUpLeft;
        tmpKeyUpMiddle = CraftingKeys.config.keyUpMiddle;
        tmpKeyUpRight = CraftingKeys.config.keyUpRight;

        tmpKeyMiddleLeft = CraftingKeys.config.keyMiddleLeft;
        tmpKeyMiddleMiddle = CraftingKeys.config.keyMiddleMiddle;
        tmpKeyMiddleRight = CraftingKeys.config.keyMiddleRight;

        tmpKeyLowLeft = CraftingKeys.config.keyLowLeft;
        tmpKeyLowMiddle = CraftingKeys.config.keyLowMiddle;
        tmpKeyLowRight = CraftingKeys.config.keyLowRight;

        tmpKeyInteract = CraftingKeys.config.keyInteract;

        tmpKeyStack = CraftingKeys.config.keyStack;
        tmpKeyDrop = CraftingKeys.config.keyDrop;
    }

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
            save();
        }

        //Set selected Button
        if(button.id == buttonInteract.id){
            selectedButton = buttonInteract;
            selectedButton.displayString = "?";
        }else if(button.id == buttonStack.id){
            selectedButton = buttonStack;
            selectedButton.displayString = "?";
        }else if(button.id == buttonDrop.id){
            selectedButton = buttonDrop;
            selectedButton.displayString = "?";
        }else if(button.id == buttonUpLeft.id){
            selectedButton = buttonUpLeft;
            selectedButton.displayString = "?";
        }else if(button.id == buttonUpMiddle.id){
            selectedButton = buttonUpMiddle;
            selectedButton.displayString = "?";
        }else if(button.id == buttonUpRight.id){
            selectedButton = buttonUpRight;
            selectedButton.displayString = "?";
        }else if(button.id == buttonMiddleLeft.id){
            selectedButton = buttonMiddleLeft;
            selectedButton.displayString = "?";
        }else if(button.id == buttonMiddleMiddle.id){
            selectedButton = buttonMiddleMiddle;
            selectedButton.displayString = "?";
        }else if(button.id == buttonMiddleRight.id){
            selectedButton = buttonMiddleRight;
            selectedButton.displayString = "?";
        }else if(button.id == buttonLowLeft.id){
            selectedButton = buttonLowLeft;
            selectedButton.displayString = "?";
        }else if(button.id == buttonLowMiddle.id){
            selectedButton = buttonLowMiddle;
            selectedButton.displayString = "?";
        }else if(button.id == buttonLowRight.id){
            selectedButton = buttonLowRight;
            selectedButton.displayString = "?";
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

    @Override
    public void keyTyped(char character, int keyCode){
        if(selectedButton == null) return;
        selectedButton.displayString = Keyboard.getKeyName(keyCode);

        if(selectedButton.id == buttonInteract.id){
            tmpKeyInteract = keyCode;
        }else if(selectedButton.id == buttonStack.id){
            tmpKeyStack = keyCode;
        }else if(selectedButton.id == buttonDrop.id){
            tmpKeyDrop = keyCode;
        }else if(selectedButton.id == buttonUpLeft.id){
            tmpKeyUpLeft = keyCode;
        }else if(selectedButton.id == buttonUpMiddle.id){
            tmpKeyUpMiddle = keyCode;
        }else if(selectedButton.id == buttonUpRight.id){
            tmpKeyUpRight = keyCode;
        }else if(selectedButton.id == buttonMiddleLeft.id){
            tmpKeyMiddleLeft = keyCode;
        }else if(selectedButton.id == buttonMiddleMiddle.id){
            tmpKeyMiddleMiddle = keyCode;
        }else if(selectedButton.id == buttonMiddleRight.id){
            tmpKeyMiddleRight = keyCode;
        }else if(selectedButton.id == buttonLowLeft.id){
            tmpKeyLowLeft = keyCode;
        }else if(selectedButton.id == buttonLowMiddle.id){
            tmpKeyLowMiddle = keyCode;
        }else if(selectedButton.id == buttonLowRight.id){
            tmpKeyLowRight = keyCode;
        }
        selectedButton = null;
    }

    private void save(){
        CraftingKeys.config.keyUpLeft = tmpKeyUpLeft;
        CraftingKeys.config.keyUpMiddle = tmpKeyUpMiddle ;
        CraftingKeys.config.keyUpRight = tmpKeyUpRight;

        CraftingKeys.config.keyMiddleLeft = tmpKeyMiddleLeft;
        CraftingKeys.config.keyMiddleMiddle = tmpKeyMiddleMiddle;
        CraftingKeys.config.keyMiddleRight = tmpKeyMiddleRight;

        CraftingKeys.config.keyLowLeft = tmpKeyLowLeft;
        CraftingKeys.config.keyLowMiddle = tmpKeyLowMiddle;
        CraftingKeys.config.keyLowRight = tmpKeyLowRight;

        CraftingKeys.config.keyInteract = tmpKeyInteract;

        CraftingKeys.config.keyStack = tmpKeyStack;
        CraftingKeys.config.keyDrop = tmpKeyDrop;

        CraftingKeys.config.save();
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
        drawString(fontRendererObj, "Anvil", guiShowBasePosition - 10, guiShowBaseHeight + 3, lightGray.getRGB());
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
        drawString(fontRendererObj, "Dispenser/Dropper", guiShowBasePosition - 48, guiShowBaseHeight + 3, lightGray.getRGB());
    }

    private void addStandardButtons() {
        // Add control buttons
        buttonList.add((new GuiButton(buttonAbortID, width - 58, 3, 55, 20, LanguageLocalizer.localize("craftingkeys.config.button.abort"))));
        buttonList.add((new GuiButton(buttonSaveID, width - 58, 26, 55, 20, LanguageLocalizer.localize("craftingkeys.config.button.save"))));
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
        buttonUpLeft = new GuiButton(101, guiBasePosition - 60, height / 2 - 87, 20, 20, Keyboard.getKeyName(tmpKeyUpLeft));
        buttonUpMiddle = new GuiButton(102, guiBasePosition - 41, height / 2 - 87, 20, 20, Keyboard.getKeyName(tmpKeyUpMiddle));
        buttonUpRight = new GuiButton(103, guiBasePosition - 22, height / 2 - 87, 20, 20, Keyboard.getKeyName(tmpKeyUpRight));

        buttonMiddleLeft = new GuiButton(111, guiBasePosition - 60, height / 2 - 68, 20, 20, Keyboard.getKeyName(tmpKeyMiddleLeft));
        buttonMiddleMiddle = new GuiButton(112, guiBasePosition - 41, height / 2 - 68, 20, 20, Keyboard.getKeyName(tmpKeyMiddleMiddle));
        buttonMiddleRight = new GuiButton(113, guiBasePosition - 22, height / 2 - 68, 20, 20, Keyboard.getKeyName(tmpKeyMiddleRight));

        buttonLowLeft = new GuiButton(121, guiBasePosition - 60, height / 2 - 49, 20, 20, Keyboard.getKeyName(tmpKeyLowLeft));
        buttonLowMiddle = new GuiButton(122, guiBasePosition - 41, height / 2 - 49, 20, 20, Keyboard.getKeyName(tmpKeyLowMiddle));
        buttonLowRight = new GuiButton(123, guiBasePosition - 22, height / 2 - 49, 20, 20, Keyboard.getKeyName(tmpKeyLowRight));

        buttonInteract = new GuiButton(100, guiBasePosition + 26, height / 2 - 67, 50, 20, Keyboard.getKeyName(tmpKeyInteract));

        buttonStack = new GuiButton(401, guiBasePosition + 105, height / 2 - 84, 50, 20, Keyboard.getKeyName(tmpKeyStack));
        buttonDrop = new GuiButton(402, guiBasePosition + 105, height / 2 - 46, 50, 20, Keyboard.getKeyName(tmpKeyDrop));

        buttonList.add(buttonUpLeft);
        buttonList.add(buttonUpMiddle);
        buttonList.add(buttonUpRight);

        buttonList.add(buttonMiddleLeft);
        buttonList.add(buttonMiddleMiddle);
        buttonList.add(buttonMiddleRight);

        buttonList.add(buttonLowLeft);
        buttonList.add(buttonLowMiddle);
        buttonList.add(buttonLowRight);

        buttonList.add(buttonInteract);

        buttonList.add(buttonStack);
        buttonList.add(buttonDrop);
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
