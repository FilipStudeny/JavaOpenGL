package Engine.Models;

public class Square extends Mesh{

    public float[] vertices = {
            //Position              //Colours
             0.5f, -0.5f, -2f,       1.0f, 0.0f, 0.0f, 1.0f, //Bottom right
            -0.5f,  0.5f, -2f,       0.0f, 1.0f, 0.0f, 1.0f, //Top Left
             0.5f,  0.5f, -2f,       0.0f, 0.0f, 1.0f, 1.0f, //Top Right
            -0.5f, -0.5f, -2f,       1.0f, 1.0f, 0.0f, 1.0f, //Bottom left
    };

    public int[] triangles = {
            2,1,0, //Top right triangle
            0,1,3, //Bottom left triangle
    };

}