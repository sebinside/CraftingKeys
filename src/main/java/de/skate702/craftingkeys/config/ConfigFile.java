package de.skate702.craftingkeys.config;

import org.lwjgl.input.Keyboard;


import java.io.*;


public class ConfigFile {

    private File optionsFile;
    private File path;

    public int keyUpLeft;
    public int keyUpMiddle;
    public int keyUpRight;

    public int keyMiddleLeft;
    public int keyMiddleMiddle;
    public int keyMiddleRight;

    public int keyLowLeft;
    public int keyLowMiddle;
    public int keyLowRight;

    public int keyInteract;

    public int keyStack;
    public int keyDrop;

    public boolean enableNumpad;

    public ConfigFile(String path, String name){
        this.path = new File(path);
        optionsFile = new File(this.path, name);
        setVarsToDefault();
    }

    public boolean load(){
        boolean success = false;
        try {

            if(createIfNotExists()) return true;

            BufferedReader reader = new BufferedReader(new FileReader(this.optionsFile));
            String line = "";

            while((line = reader.readLine()) != null){
                String[] parts = line.split("=");

                if(parts[0].equals("keyUpLeft")){
                    keyUpLeft = Integer.parseInt(parts[1]);
                }
                if(parts[0].equals("keyUpMiddle")){
                    keyUpMiddle = Integer.parseInt(parts[1]);
                }
                if(parts[0].equals("keyUpRight")){
                    keyUpRight = Integer.parseInt(parts[1]);
                }
                if(parts[0].equals("keyMiddleLeft")){
                    keyMiddleLeft = Integer.parseInt(parts[1]);
                }
                if(parts[0].equals("keyMiddleMiddle")){
                    keyMiddleMiddle = Integer.parseInt(parts[1]);
                }
                if(parts[0].equals("keyMiddleRight")){
                    keyMiddleRight = Integer.parseInt(parts[1]);
                }
                if(parts[0].equals("keyLowLeft")){
                    keyLowLeft = Integer.parseInt(parts[1]);
                }
                if(parts[0].equals("keyLowMiddle")){
                    keyLowMiddle = Integer.parseInt(parts[1]);
                }
                if(parts[0].equals("keyLowRight")){
                    keyLowRight = Integer.parseInt(parts[1]);
                }
                if(parts[0].equals("keyInteract")){
                    keyInteract = Integer.parseInt(parts[1]);
                }
                if(parts[0].equals("keyStack")){
                    keyStack = Integer.parseInt(parts[1]);
                }
                if(parts[0].equals("keyDrop")){
                    keyDrop = Integer.parseInt(parts[1]);
                }
                if(parts[0].equals("enableNumpad")){
                    enableNumpad = parts[1].equals("true");
                }

            }

            reader.close();

            success = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return  success;
    }

    public boolean save(){
        boolean success = false;
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(optionsFile));

            writer.println("keyUpLeft=" + keyUpLeft);
            writer.println("keyUpMiddle=" + keyUpMiddle);
            writer.println("keyUpRight=" + keyUpRight);

            writer.println("keyMiddleLeft=" + keyMiddleLeft);
            writer.println("keyMiddleMiddle=" + keyMiddleMiddle);
            writer.println("keyMiddleRight=" + keyMiddleRight);

            writer.println("keyLowLeft=" + keyLowLeft);
            writer.println("keyLowMiddle=" + keyLowMiddle);
            writer.println("keyLowRight=" + keyLowRight);

            writer.println("keyInteract=" + keyInteract);

            writer.println("keyStack=" + keyStack);
            writer.println("keyDrop=" + keyDrop);

            writer.println("enableNumpad=" + enableNumpad);

            writer.close();

            success = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return success;
    }

    private boolean createIfNotExists() throws Exception{
        boolean created = false;
        if(!path.exists()){
            path.mkdirs();
            created = true;
        }
        if(!optionsFile.exists()){
                optionsFile.createNewFile();
                setVarsToDefault();
                save();
            created = true;
        }
        return created;
    }

    private void setVarsToDefault(){
        keyUpLeft = Keyboard.KEY_Q;
        keyUpMiddle = Keyboard.KEY_W;
        keyUpRight = Keyboard.KEY_E;

        keyMiddleLeft = Keyboard.KEY_A;
        keyMiddleMiddle = Keyboard.KEY_S;
        keyMiddleRight = Keyboard.KEY_D;

        keyLowLeft = Keyboard.KEY_Y;
        keyLowMiddle = Keyboard.KEY_X;
        keyLowRight = Keyboard.KEY_C;

        keyInteract = Keyboard.KEY_LCONTROL;

        keyStack = Keyboard.KEY_LSHIFT;
        keyDrop = Keyboard.KEY_SPACE;

        enableNumpad = true;
    }

    public boolean isKeyDown(KeyType key){
        boolean isDown = false;
        switch (key){
            case UpLEFT:
                isDown = Keyboard.isKeyDown(keyUpLeft) || enableNumpad && Keyboard.isKeyDown(71);
                break;
            case UpMIDDLE:
                isDown = Keyboard.isKeyDown(keyUpMiddle) || enableNumpad && Keyboard.isKeyDown(72);
                break;
            case UpRIGHT:
                isDown = Keyboard.isKeyDown(keyUpRight) || enableNumpad && Keyboard.isKeyDown(73);
                break;
            case MiddleLEFT:
                isDown = Keyboard.isKeyDown(keyMiddleLeft) || enableNumpad && Keyboard.isKeyDown(75);
                break;
            case MiddleMIDDLE:
                isDown = Keyboard.isKeyDown(keyMiddleMiddle) || enableNumpad && Keyboard.isKeyDown(76);
                break;
            case MiddleRIGHT:
                isDown = Keyboard.isKeyDown(keyMiddleRight) || enableNumpad && Keyboard.isKeyDown(77);
                break;
            case LowLEFT:
                isDown = Keyboard.isKeyDown(keyLowLeft) || enableNumpad && Keyboard.isKeyDown(79);
                break;
            case LowMIDDLE:
                isDown = Keyboard.isKeyDown(keyLowMiddle) || enableNumpad && Keyboard.isKeyDown(80);
                break;
            case LowRIGHT:
                isDown = Keyboard.isKeyDown(keyLowRight) || enableNumpad && Keyboard.isKeyDown(81);
                break;
            case INTERACT:
                isDown = Keyboard.isKeyDown(keyInteract);
                break;
            case STACK:
                isDown = Keyboard.isKeyDown(keyStack);
                break;
            case DROP:
                isDown = Keyboard.isKeyDown(keyDrop);
                break;

        }

        return isDown;
    }

    public enum KeyType{
        UpLEFT,
        UpMIDDLE,
        UpRIGHT,

        MiddleLEFT,
        MiddleMIDDLE,
        MiddleRIGHT,

        LowLEFT,
        LowMIDDLE,
        LowRIGHT,

        INTERACT,

        STACK,
        DROP;
    }
}
