package Engine.Input;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyboardListener {

    private  static KeyboardListener Instance;
    private final boolean[] KeyPressed = new boolean[350];

    private  KeyboardListener(){ }

    public  static KeyboardListener get(){
        if(Instance == null){
            Instance = new KeyboardListener();
        }
        return Instance;
    }

    public  static void KeyCallback(long window, int key, int scanCode, int action, int mods){
        if(action == GLFW_PRESS){
            get().KeyPressed[key] = true;
        }else if(action == GLFW_RELEASE){
            get().KeyPressed[key] = false;
        }
    }

    public static boolean isKeyPressed(int keyCode){
        return get().KeyPressed[keyCode];
    }
}
