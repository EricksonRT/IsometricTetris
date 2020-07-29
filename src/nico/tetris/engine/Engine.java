package nico.tetris.engine;

import nico.tetris.engine.gamestates.GameStateManager;
import nico.tetris.engine.input.Keyboard;
import org.lwjgl.Version;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.IntBuffer;

public class Engine {

    private final long window;

    public Engine() {
        System.out.println("Initializing LWJGL " + Version.getVersion());

        //Print error messages in System.err
        GLFWErrorCallback.createPrint(System.err).set();

        //Initialize GLFW
        if(!GLFW.glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

        //Configuration
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE); //Window not resizeable
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE); //Window will stay hidden

        //Create window
        this.window = GLFW.glfwCreateWindow(1000, 600, "Isometric Tetris", MemoryUtil.NULL, MemoryUtil.NULL);
        if(window == MemoryUtil.NULL) throw new RuntimeException("Failed to create the window");

        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);

            //Center the window
            GLFW.glfwGetWindowSize(window, w, h);
            GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
            GLFW.glfwSetWindowPos(window, (vidMode.width() - w.get(0)) / 2, (vidMode.height() - h.get(0)) / 2);
        }

        GLFW.glfwMakeContextCurrent(window); //Make OpenGL context current
        GLFW.glfwSwapInterval(1); //Enable v-sync
        GLFW.glfwShowWindow(window); //Make window visible

        GL.createCapabilities();
    }

    public boolean isRunning() {
        return !GLFW.glfwWindowShouldClose(window);
    }

    public void setupCallbacks(GameStateManager stateManagerReference) {
        GLFW.glfwSetKeyCallback(window, new Keyboard(stateManagerReference));
    }

    public void update() {
        GLFW.glfwSwapBuffers(window); //Swap color buffers
        GLFW.glfwPollEvents(); //Poll for window events (like key callback)
    }

    public void terminate() {
        Callbacks.glfwFreeCallbacks(window); //Free callbacks
        GLFW.glfwDestroyWindow(window); //Close window
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
        System.out.println("Terminated");
    }
}
