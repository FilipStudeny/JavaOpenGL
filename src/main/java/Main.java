

import Engine.Camera;
import Engine.GameObject;
import Engine.Input.KeyboardListener;
import Engine.Input.MouseListener;
import Engine.Models.Square;
import Engine.Models.Triangle;
import Engine.Renderer;
import Engine.Time;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11C.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main {
    public static void main(String[] args) {
        Window window = new Window(600,800);
        window.Run();
    }
}

class Window{

    //Window handle
    private long window; // Memory adress where the window is located
    private int height;
    private int width;

    private boolean fadeColor = false;
    public float red,blue,green,alpha;

    private Renderer renderer;
    private Camera camera;

    private Vector3f inputVector;
    private Vector3f mouseInput;

    private static float CAMERA_POS_STEP = 0.5f;
    private float MOUSE_SENSITIVITY = 0.1f;

    public Window(int height, int width){
        this.height = height;
        this.width = width;
        this.fadeColor = false;

        this.red = 0.2f;
        this.blue = 0.3f;
        this.green = 0.3f;
        this.alpha = 1f;
        this.inputVector = new Vector3f();
        this.mouseInput = new Vector3f();
        this.camera = new Camera(new Vector3f(0,0,0),new Vector3f(0,0,0));
        this.renderer = new Renderer(camera);

    }

    public void Run(){
        Init();
        Loop();

        //Free memory once Loop is closed
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        //Terminate GLFW and free error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();

    }

    private void Init(){
        //Setup error callback -> Where errors will be printed -> In console
        GLFWErrorCallback.createPrint(System.err).set();

        //Initiaize GLFW, If can't initialize -> crash
        if(!glfwInit()){
            throw new RuntimeException("ERROR -> GLFW COULDN'T BE INITIALIZED !");
        }

        //Configure GLFW
        //Set window parameters
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE,GLFW_FALSE); // Don't create window before its created
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        //glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        //Create window and crash if there is an error
        window = glfwCreateWindow(width,height,"LWJGL Window", NULL,NULL);
        if(window == NULL){
            throw new IllegalStateException("FAILED TO CREATE GLFW WINDOW");
        }

        //Initialize mouse input
        MouseListener.Init(window);

        //Set callbacks
        glfwSetKeyCallback(window,KeyboardListener::KeyCallback);

        //Center window
        try(MemoryStack stack = MemoryStack.stackPush()){
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(window,pWidth,pHeight);

            GLFWVidMode monitor = glfwGetVideoMode(glfwGetPrimaryMonitor());
            int positionX = (monitor.width() - pWidth.get(0)) / 2;
            int positionY = (monitor.height() - pHeight.get(0)) / 2;
            glfwSetWindowPos(window,positionX,positionY);
        }

        //Make OpenGL context current
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1); // Enable V-sync
        glfwShowWindow(window); // Show the window

        // Sets LWJGL binfing to OpenGL
        GL.createCapabilities();
        glClearColor(1,1,1,1); //Set background color

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

        glfwSetCursorPos(window, width/2, height/2); //Center cursor
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED); //Dissable cursor
    }

    private void Loop(){

        float frameBeginTime = Time.GetTime();
        float frameEndTime;
        float deltaTime = -1.0f;

        //Render frames until window is closed
        while(!glfwWindowShouldClose(window)){

            glEnable(GL_DEPTH_TEST);
            glDepthFunc(GL_LESS);
            glfwPollEvents(); // Poll events -> Event input listeners
            glClearColor(red,green,blue,alpha); //Set background color
            glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT); // Tells OpenGL how to clear color buffer

            if(deltaTime >= 0){
                Input();

                //CAMERA STUFF
                MouseListener.Init(window);
                MouseListener.Input();

                camera.movePosition(inputVector.x * CAMERA_POS_STEP, inputVector.y * CAMERA_POS_STEP, inputVector.z * CAMERA_POS_STEP);
                Vector2f cameraRotation = MouseListener.GetDisplacementVector();
                camera.moveRotation(cameraRotation.x * MOUSE_SENSITIVITY, cameraRotation.y * MOUSE_SENSITIVITY,0);

                System.out.println("FPS: " + 1.0f / deltaTime);
                renderer.RenderObjects();
                FadeToBlack();
            }

            glfwSwapBuffers(window); // Swaps buffers

            frameEndTime = Time.GetTime();
            deltaTime = frameEndTime - frameBeginTime;
            frameBeginTime = frameEndTime;
        }
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

        if(KeyboardListener.isKeyPressed(GLFW_KEY_F)){
            fadeColor = true;
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

    void FadeToBlack(){
        if(fadeColor){
            blue = Math.max(blue - 0.01f,0);
            red = Math.max(red - 0.01f,0);
            green = Math.max(green - 0.01f,0);
            alpha = Math.max(alpha - 0.01f,0);
        }
    }
}
