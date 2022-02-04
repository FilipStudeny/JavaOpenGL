package Engine.Input;

import org.joml.Vector2d;
import org.joml.Vector2f;
import static org.lwjgl.glfw.GLFW.*;

public class MouseListener {

    public static MouseListener instance;

    private float cursorPosX;
    private float cursorPosY;

    private float cursorOffsetX;
    private float cursorOffsetY;

    private MouseListener() {
        this.cursorPosX = 0;
        this.cursorPosY = 0;
        this.cursorOffsetX = 0;
        this.cursorOffsetY = 0;
    }

    public static MouseListener get(){
        if(instance == null){
            instance = new MouseListener();
        }
        return instance;
    }

    public static void MouseCursorPositionCallback(long window, double posX, double posY){
        get().cursorOffsetX = (int)posX - get().cursorPosX;
        get().cursorOffsetY = (int)posY - get().cursorPosY;

        get().cursorPosX = (int)posX;
        get().cursorPosY = (int)posY * -1;
    }

    public static float GetCursorPosX(){
        return get().cursorPosX;
    }

    public static float GetCursorPosY(){
        return get().cursorPosY;
    }

}