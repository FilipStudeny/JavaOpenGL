

import Engine.Input.KeyboardListener;
import Engine.Input.MouseListener;
import Engine.Models.Square;
import Engine.Models.Triangle;
import Engine.Time;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
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

    private Triangle triangle;
    private Square square;

    public Window(int height, int width){
        this.height = height;
        this.width = width;
        fadeColor = false;


        red = 0.2f;
        blue = 0.3f;
        green = 0.3f;
        alpha = 1f;

        triangle = new Triangle();
        square = new Square();
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
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        //glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);


        //Create window and crash if there is an error
        window = glfwCreateWindow(width,height,"LWJGL Window", NULL,NULL);
        if(window == NULL){
            throw new IllegalStateException("FAILED TO CREATE GLFW WINDOW");
        }

        //Set callbacks
        glfwSetCursorPosCallback(window, MouseListener::MousePositionCallback);
        glfwSetMouseButtonCallback(window, MouseListener::MouseButtonCallback);
        glfwSetScrollCallback(window,MouseListener::MouseScrollCallback);
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


        square.SetVertices(square.vertices);
        square.SetTriangles(square.triangles);
        square.Init();


    }


    private void Loop(){

        float frameBeginTime = Time.GetTime();
        float frameEndTime;
        float deltaTime = -1.0f;

        //Render frames until window is closed
        while(!glfwWindowShouldClose(window)){

            glfwPollEvents(); // Poll events -> Event input listeners
            glClearColor(red,green,blue,alpha); //Set background color
            glClear(GL_COLOR_BUFFER_BIT); // Tells OpenGL how to clear color buffer


            if(deltaTime >= 0){
                System.out.println("FPS: " + 1.0f / deltaTime);
                square.Render();
                FadeToBlack();
            }

            Input();
            glfwSwapBuffers(window); // Swaps buffers

            frameEndTime = Time.GetTime();
            deltaTime = frameEndTime - frameBeginTime;
            frameBeginTime = frameEndTime;
        }
    }

    void Input(){
        if(KeyboardListener.isKeyPressed(GLFW_KEY_E)){
            fadeColor = true;
        }

        if(KeyboardListener.isKeyPressed(GLFW_KEY_ESCAPE)){
            glfwSetWindowShouldClose(window,true);
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
