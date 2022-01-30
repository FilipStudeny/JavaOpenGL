package Engine.Input;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {
    private static  MouseListener instace;

    private double scrollX;
    private double scrollY;
    private double posX, posY, lastPosX, lastPosY;
    private boolean[] buttonPressed = new boolean[3];

    private boolean isDraggingMouse;

    private MouseListener(){
        this.scrollX = 0.0;
        this.scrollY = 0.0;
        this.posX = 0.0;
        this.posY = 0.0;
        this.lastPosX = 0.0;
        this.lastPosY = 0.0;
    }

    public static MouseListener get(){
        if(instace == null){
            instace = new MouseListener();
        }

        return  instace;
    }

    public static void MousePositionCallback(long window, double posX, double posY){
        get().lastPosX = get().posX;
        get().lastPosY = get().posY;
        get().posX = posX;
        get().posY = posY;
        get().isDraggingMouse = get().buttonPressed[0] || get().buttonPressed[1] || get().buttonPressed[2];
    }

    public  static void MouseButtonCallback(long window, int button, int action, int mods){
        if(action == GLFW_PRESS){
            if(button < get().buttonPressed.length){
                get().buttonPressed[button] = true;
            }

        }else if(action == GLFW_RELEASE){
            if(button < get().buttonPressed.length){
                get().buttonPressed[button] = false;
                get().isDraggingMouse = false;
            }
        }
    }

    public static void MouseScrollCallback(long window, double scrollX, double scrollY){
        get().scrollX = scrollX;
        get().scrollY = scrollY;
    }

    public  static void endFrame(){
        get().scrollY = 0;
        get().scrollX = 0;
        get().lastPosY = get().posY;
        get().lastPosX = get().posX;
    }

    public static float getPositionX(){
        return (float) get().posX;
    }

    public static float getPositionY(){
        return (float) get().posY;
    }

    public static float getDeltaPositionX(){
        return (float) (get().lastPosX - get().posX);
    }

    public static float getDeltaPositionY(){
        return (float) (get().lastPosY - get().posY);
    }

    public static float getScrollX(){
        return (float) get().scrollX;
    }

    public static float getScrollY(){
        return (float) get().scrollY;
    }

    public static boolean isDraggingMouse(){
        return get().isDraggingMouse;
    }

    public static boolean mouseButtonDown(int button){
        if(button < get().buttonPressed.length){
            return get().buttonPressed[button];
        }else{
            return false;
        }
    }
}
