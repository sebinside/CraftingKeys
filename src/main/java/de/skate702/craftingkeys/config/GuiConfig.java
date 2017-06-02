package de.skate702.craftingkeys.config;

import de.skate702.craftingkeys.CraftingKeys;
import de.skate702.craftingkeys.util.LanguageLocalizer;
import de.skate702.craftingkeys.util.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

public class GuiConfig extends GuiScreen {

    public static final int GuiID = 702;

    private static final Color pureWhite = new Color(255, 255, 255, 255);
    private static final Color lightGray = new Color(128, 128, 128, 255);
    private static final Color highlight = new Color(86, 144, 72, 255);
    private final int buttonSaveID = 901;
    private final int buttonAbortID = 902;
    /**
     * 0,1,2 = top,
     * 3,4,5 = mid,
     * 6,7,8 = low,
     * 9     = interact,
     * 10    = stack,
     * 11    = drop
     */
    private int[] keyValues = new int[]{};
    private int guiBasePosition;
    private int guiShowBasePosX;
    private int guiShowBasePosY;
    private GuiType guiShowType;
    private int guiShowState;
    private long lastTime = 0;
    private long currentTime;
    @SuppressWarnings("FieldCanBeLocal")
    private ArrayList<GuiButton> configButtons;
    private int selectedButtonID = -1;

    @Override
    public void initGui() {

        // Fill vars
        int guiBaseOffset = 35;
        guiBasePosition = width / 2 - guiBaseOffset;

        guiShowBasePosX = width / 2 - 35;
        guiShowBasePosY = height / 2 + 25;

        guiShowType = GuiType.ANVIL;
        guiShowState = 0;
        lastTime = Minecraft.getSystemTime();
        currentTime = Minecraft.getSystemTime();

        // Init Config
        initKeyValues();

        // Init buttons
        addStandardButtons();
        addCraftingButtons();
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

        // Key Info
        drawCenteredString(fontRendererObj, LanguageLocalizer.localize("craftingkeys.config.stack"), guiBasePosition + 130, height / 2 - 96, pureWhite.getRGB());
        drawCenteredString(fontRendererObj, LanguageLocalizer.localize("craftingkeys.config.drop"), guiBasePosition + 130, height / 2 - 58, pureWhite.getRGB());

        //Draw line to let it look better
        drawHorizontalLine(guiBasePosition - 86, guiBasePosition + 85, height / 2 - 20, pureWhite.getRGB());

        // Draw Crafting Table
        drawCraftingTable();

        // Draw info
        drawInfo();

        //Draw line to let it look better
        drawHorizontalLine(guiShowBasePosX - 86, guiShowBasePosX + 85, guiShowBasePosY + 80, lightGray.getRGB());

        // Super
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawInfo() {

        // Insert Gui by selected Type
        currentTime = Minecraft.getSystemTime();
        int waitingTimeMS = 3000;
        if (currentTime - lastTime > waitingTimeMS) {
            showNextGui();
            GL11.glColor4f(0.5F, 0.5F, 0.5F, 1F);
            lastTime = Minecraft.getSystemTime();
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
            case VILLAGER:
                genVillagerInfo();
                break;
            case ANVIL:
                genAnvilInfo();
                break;
        }
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if (button.id == buttonAbortID) {
            Logger.info("actionPerformed(b)", "Closing Crafting Keys GUI now!");
            mc.player.closeScreen();
        } else if (button.id == buttonSaveID) {
            save();
            Logger.info("actionPerformed(b)", "Saving & closing Crafting Keys GUI now!");
            mc.player.closeScreen();
        } else if (button.id >= 0 && button.id <= 11) {
            if (selectedButtonID == -1) {
                selectedButtonID = button.id;
                configButtons.get(selectedButtonID).displayString = "...";
            }
        }
    }

    private void save() {
        Config.keyTopLeft.set(keyValues[0]);
        Config.keyTopCenter.set(keyValues[1]);
        Config.keyTopRight.set(keyValues[2]);
        Config.keyCenterLeft.set(keyValues[3]);
        Config.keyCenterCenter.set(keyValues[4]);
        Config.keyCenterRight.set(keyValues[5]);
        Config.keyLowerLeft.set(keyValues[6]);
        Config.keyLowerCenter.set(keyValues[7]);
        Config.keyLowerRight.set(keyValues[8]);
        Config.keyInteract.set(keyValues[9]);
        Config.keyStack.set(keyValues[10]);
        Config.keyDrop.set(keyValues[11]);
        Config.syncConfig();
    }

    @Override
    public void keyTyped(char character, int keyCode) {

        if (keyCode == Keyboard.KEY_ESCAPE) {
            if (selectedButtonID != -1) {
                selectedButtonID = -1;
                drawKeyValues();
            } else {
                Logger.info("keyTyped(c,i)", "Trying to close inventory with esc.");

                try {
                    super.keyTyped(character, keyCode); // Enable standard gui closing
                } catch (Exception ignored) {
                    // support for newer versions, just do nothing
                }
            }

        } else if (selectedButtonID != -1) {
            if (!ArrayUtils.contains(keyValues, keyCode) || keyValues[selectedButtonID] == keyCode) { // No double keys
                keyValues[selectedButtonID] = keyCode;
                selectedButtonID = -1;
                drawKeyValues();
            }
        }

    }

    private void drawCraftingTable() {
        GL11.glColor4f(1F, 1F, 1F, 1F);
        mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/crafting_table.png"));
        drawTexturedModalRect(guiBasePosition - 86, height / 2 - 100, 1, 0, 174, 80);
    }

    private void genFurnaceInfo() {

        GL11.glColor4f(0.5F, 0.5F, 0.5F, 1F);
        mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/furnace.png"));
        drawTexturedModalRect(guiShowBasePosX - 86, guiShowBasePosY, 1, 0, 174, 80);

        GL11.glColor4f(1F, 1F, 1F, 1F);
        mc.renderEngine.bindTexture(new ResourceLocation(CraftingKeys.MODID, "textures/gui/symbols.png"));
        drawTexturedModalRect(guiShowBasePosX + 105, guiShowBasePosY + 17, 0, 100, 50, 50);

        drawInfoString(1, 63, 21);
        drawInfoString(4, 63, 57);
        drawInfoString(9, 123, 39);
    }

    private void genBrewingStandInfo() {

        GL11.glColor4f(0.5F, 0.5F, 0.5F, 1F);
        mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/brewing_stand.png"));
        drawTexturedModalRect(guiShowBasePosX - 86, guiShowBasePosY, 1, 0, 174, 80);

        GL11.glColor4f(1F, 1F, 1F, 1F);
        mc.renderEngine.bindTexture(new ResourceLocation(CraftingKeys.MODID, "textures/gui/symbols.png"));
        drawTexturedModalRect(guiShowBasePosX + 105, guiShowBasePosY + 17, 200, 0, 50, 50);
        
        drawInfoString(0, 24, 21);
        drawInfoString(4, 86, 63);
        drawInfoString(3, 64, 56);
        drawInfoString(5, 109, 56);
        
    }

    private void genEnchantmentInfo() {

        GL11.glColor4f(0.5F, 0.5F, 0.5F, 1F);
        mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/enchanting_table.png"));
        drawTexturedModalRect(guiShowBasePosX - 86, guiShowBasePosY, 1, 0, 174, 80);

        GL11.glColor4f(1F, 1F, 1F, 1F);
        mc.renderEngine.bindTexture(new ResourceLocation(CraftingKeys.MODID, "textures/gui/symbols.png"));
        drawTexturedModalRect(guiShowBasePosX + 105, guiShowBasePosY + 17, 150, 50, 50, 50);

        drawInfoString(1, 22, 51);
        drawInfoString(2, 42, 51);
    }


    private void genAnvilInfo() {

        GL11.glColor4f(0.5F, 0.5F, 0.5F, 1F);
        mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/anvil.png"));
        drawTexturedModalRect(guiShowBasePosX - 86, guiShowBasePosY, 1, 0, 174, 80);

        drawRect(guiShowBasePosX + -30, guiShowBasePosY + 17, guiShowBasePosX + 83, guiShowBasePosY + 36, Color.black.getRGB());

        GL11.glColor4f(1F, 1F, 1F, 1F);
        mc.renderEngine.bindTexture(new ResourceLocation(CraftingKeys.MODID, "textures/gui/symbols.png"));
        drawTexturedModalRect(guiShowBasePosX + 105, guiShowBasePosY + 17, 100, 0, 50, 50);

        drawInfoString(4, 34, 51);
        drawInfoString(5, 83, 51);
        drawInfoString(9, 141, 51);
    }

    private void genVillagerInfo() {

        GL11.glColor4f(0.5F, 0.5F, 0.5F, 1F);
        mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/villager.png"));
        drawTexturedModalRect(guiShowBasePosX - 86, guiShowBasePosY, 1, 0, 174, 80);

        GL11.glColor4f(1F, 1F, 1F, 1F);
        mc.renderEngine.bindTexture(new ResourceLocation(CraftingKeys.MODID, "textures/gui/symbols.png"));
        drawTexturedModalRect(guiShowBasePosX + 105, guiShowBasePosY + 17, 100, 100, 50, 50);
        
        drawInfoString(4, 43, 57);
        drawInfoString(5, 69, 57);
        drawInfoString(9, 126, 58);
    }

    private void genInventoryInfo() {

        GL11.glColor4f(0.5F, 0.5F, 0.5F, 1F);
        mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
        drawTexturedModalRect(guiShowBasePosX - 86, guiShowBasePosY, 1, 0, 174, 80);

        GL11.glColor4f(1F, 1F, 1F, 1F);
        mc.renderEngine.bindTexture(new ResourceLocation(CraftingKeys.MODID, "textures/gui/symbols.png"));
        drawTexturedModalRect(guiShowBasePosX + 105, guiShowBasePosY + 17, 0, 0, 50, 50);
        
        drawInfoString(1, 105, 22);
        drawInfoString(2, 123, 22);
        drawInfoString(4, 105, 40);
        drawInfoString(5, 123, 40);
        drawInfoString(9, 164, 32);

    }

    private void genDispenserInfo() {

        GL11.glColor4f(0.5F, 0.5F, 0.5F, 1F);
        mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/dispenser.png"));
        drawTexturedModalRect(guiShowBasePosX - 86, guiShowBasePosY, 1, 0, 174, 80);

        GL11.glColor4f(1F, 1F, 1F, 1F);
        mc.renderEngine.bindTexture(new ResourceLocation(CraftingKeys.MODID, "textures/gui/symbols.png"));
        drawTexturedModalRect(guiShowBasePosX + 105, guiShowBasePosY + 17, 50, 50, 50, 50);

        drawInfoString(0, 69, 21);
        drawInfoString(1, 87, 21);
        drawInfoString(2, 105, 21);

        drawInfoString(3, 69, 39);
        drawInfoString(4, 87, 39);
        drawInfoString(5, 105, 39);

        drawInfoString(6, 69, 57);
        drawInfoString(7, 87, 57);
        drawInfoString(8, 105, 57);
    }

    private void drawInfoString(int index, int posX, int posY) {
        drawCenteredString(fontRendererObj, Keyboard.getKeyName(keyValues[index]),
                guiShowBasePosX + posX - 86, guiShowBasePosY + posY, highlight.getRGB());
    }

    private void addStandardButtons() {
        // Add control buttons
        buttonList.add((new GuiButton(buttonAbortID, width - 53, 3, 50, 20, LanguageLocalizer.localize("craftingkeys.config.button.abort"))));
        buttonList.add((new GuiButton(buttonSaveID, width - 53, 26, 50, 20, LanguageLocalizer.localize("craftingkeys.config.button.save"))));
    }

    private void addCraftingButtons() {
        configButtons = new ArrayList<GuiButton>();
        configButtons.add((new GuiButton(0, guiBasePosition - 60, height / 2 - 87, 20, 20, "")));
        configButtons.add((new GuiButton(1, guiBasePosition - 41, height / 2 - 87, 20, 20, "")));
        configButtons.add((new GuiButton(2, guiBasePosition - 22, height / 2 - 87, 20, 20, "")));
        configButtons.add((new GuiButton(3, guiBasePosition - 60, height / 2 - 68, 20, 20, "")));
        configButtons.add((new GuiButton(4, guiBasePosition - 41, height / 2 - 68, 20, 20, "")));
        configButtons.add((new GuiButton(5, guiBasePosition - 22, height / 2 - 68, 20, 20, "")));
        configButtons.add((new GuiButton(6, guiBasePosition - 60, height / 2 - 49, 20, 20, "")));
        configButtons.add((new GuiButton(7, guiBasePosition - 41, height / 2 - 49, 20, 20, "")));
        configButtons.add((new GuiButton(8, guiBasePosition - 22, height / 2 - 49, 20, 20, "")));
        configButtons.add((new GuiButton(9, guiBasePosition + 34, height / 2 - 67, 22, 20, "")));
        configButtons.add((new GuiButton(10, guiBasePosition + 105, height / 2 - 84, 50, 20, "")));
        configButtons.add((new GuiButton(11, guiBasePosition + 105, height / 2 - 46, 50, 20, "")));
        buttonList.addAll(configButtons);
        drawKeyValues();
    }

    private void initKeyValues() {
        if (keyValues.length == 0) {
            keyValues = new int[]{
                    Config.keyTopLeft.getInt(),      // 0
                    Config.keyTopCenter.getInt(),    // 1
                    Config.keyTopRight.getInt(),     // 2
                    Config.keyCenterLeft.getInt(),   // 3
                    Config.keyCenterCenter.getInt(), // 4
                    Config.keyCenterRight.getInt(),  // 5
                    Config.keyLowerLeft.getInt(),    // 6
                    Config.keyLowerCenter.getInt(),  // 7
                    Config.keyLowerRight.getInt(),   // 8
                    Config.keyInteract.getInt(),     // 9
                    Config.keyStack.getInt(),        // 10
                    Config.keyDrop.getInt()};        // 11
        }
    }

    private void drawKeyValues() {
        for (int i = 0; i < keyValues.length; i++) {
            configButtons.get(i).displayString = Keyboard.getKeyName(keyValues[i]);
        }
    }

    private void showNextGui() {
        switch (guiShowState) {
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
                guiShowType = GuiType.VILLAGER;
                break;
            case 6:
                guiShowType = GuiType.ANVIL;
                break;
        }
        if (guiShowState >= 6) {
            guiShowState = 0;
        } else {
            guiShowState++;
        }

    }

    public enum GuiType {
        ANVIL,
        FURNACE,
        BREWINGSTAND,
        ENCHANTMENT,
        INVENTORY,
        VILLAGER,
        DISPENSER
    }
}
