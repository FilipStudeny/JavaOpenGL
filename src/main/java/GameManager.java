import Engine.Camera;

import Engine.GameObject;
import Engine.Input.KeyboardListener;
import Engine.Input.MouseListener;
import Engine.Renderer;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class GameManager {

    private long window;
    private Renderer renderer;

    private float CAMERA_MOVE_SPEED = 0.5f;

    public GameManager(){
        this.renderer = new Renderer();
    }

    public void SetWindow(long window){
        this.window = window;
    }
    public void INIT(){

        GameObject object1 = new GameObject();
            object1.SetPosition(0f,0f,-20f);
      /*  GameObject object2 = new GameObject();
            object2.SetPosition(-5f,0f,-15f);
        GameObject object3 = new GameObject();
            object3.SetPosition(-5f,0f,15f);
        GameObject object4 = new GameObject();
            object4.SetPosition(-5f,15f,-15f);
        GameObject object5 = new GameObject();
            object5.SetPosition(-5f,-15f,-15f);
        GameObject object6 = new GameObject();
            object6.SetPosition(10f,0f,15f);
*/
        ArrayList<GameObject> objects = new ArrayList<GameObject>();
        objects.add(object1);
       /* objects.add(object2);
        objects.add(object3);
        objects.add(object4);
        objects.add(object5);
        objects.add(object6);*/
        renderer.InitializeObjects(objects);
    }

    public void LOOP(){
        Input();

        renderer.RenderObjects();
    }

    void Input(){

        Camera.SetLookDirection(MouseListener.GetCursorPosX() * -0.1f, MouseListener.GetCursorPosY() * 0.1f );

        if(KeyboardListener.isKeyPressed(GLFW_KEY_W)) {
            Camera.TranslateAlongYaw(0,0,-CAMERA_MOVE_SPEED);
        }

        if(KeyboardListener.isKeyPressed(GLFW_KEY_D)){
            Camera.TranslateAlongYaw(CAMERA_MOVE_SPEED,0,0);
        }

        if(KeyboardListener.isKeyPressed(GLFW_KEY_A)){
            Camera.TranslateAlongYaw(-CAMERA_MOVE_SPEED,0,0);
        }

        if(KeyboardListener.isKeyPressed(GLFW_KEY_S)){
            Camera.TranslateAlongYaw(0,0,CAMERA_MOVE_SPEED);
        }

        if(KeyboardListener.isKeyPressed(GLFW_KEY_E)){
            Camera.Translate(0,CAMERA_MOVE_SPEED,0);
        }

        if (KeyboardListener.isKeyPressed(GLFW_KEY_Q)){
            Camera.Translate(0,-CAMERA_MOVE_SPEED,0);
        }

        if(KeyboardListener.isKeyPressed(GLFW_KEY_ESCAPE)){
            glfwSetWindowShouldClose(window,true);
        }

    }
}
