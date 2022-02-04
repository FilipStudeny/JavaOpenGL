import Engine.Camera;

import Engine.GameObject;
import Engine.Input.KeyboardListener;
import Engine.Input.MouseListener;
import Engine.Renderer;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class GameManager {

    private long window;
    private Renderer renderer;
    private Camera camera;

    private Vector3f inputVector;
    private Vector3f mouseInput;

    private static float CAMERA_POS_STEP = 0.5f;
    private float MOUSE_SENSITIVITY = 0.1f;

    public GameManager(){
        this.inputVector = new Vector3f();
        this.mouseInput = new Vector3f();
        this.camera = new Camera(new Vector3f(0,0,0),new Vector3f(0,0,0));
        this.renderer = new Renderer(camera);
    }

    public void SetWindow(long window){
        this.window = window;
    }
    public void INIT(){
        //Initialize mouse input
        MouseListener.Init(window);

        GameObject object1 = new GameObject();
        object1.SetPosition(5f,0f,-15f);
        GameObject object2 = new GameObject();
        object2.SetPosition(-5f,0f,-15f);
        GameObject object3 = new GameObject();
        object3.SetPosition(-5f,0f,15f);
        GameObject object4 = new GameObject();
        object4.SetPosition(-5f,15f,-15f);
        GameObject object5 = new GameObject();
        object5.SetPosition(-5f,-15f,-15f);
        GameObject object6 = new GameObject();
        object6.SetPosition(10f,0f,15f);

        ArrayList<GameObject> objects = new ArrayList<GameObject>();
        objects.add(object1);
        objects.add(object2);
        objects.add(object3);
        objects.add(object4);
        objects.add(object5);
        objects.add(object6);
        renderer.InitializeObjects(objects);
    }

    public void LOOP(){
        Input();

        //CAMERA STUFF
        MouseListener.Init(window);
        MouseListener.Input();

        camera.movePosition(inputVector.x * CAMERA_POS_STEP, inputVector.y * CAMERA_POS_STEP, inputVector.z * CAMERA_POS_STEP);
        Vector2f cameraRotation = MouseListener.GetDisplacementVector();
        camera.moveRotation(cameraRotation.x * MOUSE_SENSITIVITY, cameraRotation.y * MOUSE_SENSITIVITY,0);

        renderer.RenderObjects();
    }

    void Input(){
        this.inputVector.set(0,0,0);

        if(KeyboardListener.isKeyPressed(GLFW_KEY_W)){
            inputVector.z = -1;
        }else if(KeyboardListener.isKeyPressed(GLFW_KEY_S)){
            inputVector.z = 1;
        }

        if(KeyboardListener.isKeyPressed(GLFW_KEY_A)){
            inputVector.x = -1;
        }else if(KeyboardListener.isKeyPressed(GLFW_KEY_D)){
            inputVector.x = 1;
        }

        if(KeyboardListener.isKeyPressed(GLFW_KEY_Q)){
            inputVector.y = -1;
        }else if(KeyboardListener.isKeyPressed(GLFW_KEY_E)){
            inputVector.y = 1;
        }


        if(KeyboardListener.isKeyPressed(GLFW_KEY_ESCAPE)){
            glfwSetWindowShouldClose(window,true);
        }

        if(KeyboardListener.isKeyPressed(GLFW_KEY_RIGHT)){
            mouseInput.y = 1;
        }else if(KeyboardListener.isKeyPressed(GLFW_KEY_LEFT)){
            mouseInput.y = -1;
        }
    }
}
