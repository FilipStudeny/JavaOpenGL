package Engine.Input;

import org.joml.Vector2d;
import org.joml.Vector2f;
import static org.lwjgl.glfw.GLFW.*;

public class MouseListener {

    public static MouseListener instance;

    private  Vector2d previousPosition;
    private  Vector2d currentPosition;
    private  Vector2f displacementPosition;

    private boolean inWindow = false;

    private MouseListener() {
        this.previousPosition = new Vector2d(-1, -1);
        this.currentPosition = new Vector2d(0, 0);
        this.displacementPosition = new Vector2f();
    }

    public static MouseListener get(){
        if(instance == null){
            instance = new MouseListener();
        }
        return instance;
    }

    public static void Init(long window) {
        glfwSetCursorPosCallback(window, (windowHandle, xpos, ypos) -> {
            get().currentPosition.x = xpos;
            get().currentPosition.y = ypos;
        });

        glfwSetCursorEnterCallback(window, (windowHandle, entered) -> {
            get().inWindow = entered;
        });

    }

    public static Vector2f GetDisplacementVector() {
        return  get().displacementPosition;
    }

    public static void Input() {
        get().displacementPosition.x = 0;
        get().displacementPosition.y = 0;
        if ( get().previousPosition.x > 0 &&  get().previousPosition.y > 0 &&  get().inWindow) {
            double deltax =  get().currentPosition.x -  get().previousPosition.x;
            double deltay =  get().currentPosition.y -  get().previousPosition.y;
            boolean rotateX = deltax != 0;
            boolean rotateY = deltay != 0;
            if (rotateX) {
                get().displacementPosition.y = (float) deltax;
            }
            if (rotateY) {
                get().displacementPosition.x = (float) deltay;
            }
        }
        get().previousPosition.x =  get().currentPosition.x;
        get().previousPosition.y =  get().currentPosition.y;
    }

}