package Engine.Models;

public class Square{

    private float[] vertices = {
            //POSITIONS               //COLOURS                  //UV COORDS
            0.5f, -0.5f, 0.0f,       1.0f, 0.0f, 0.0f, 1.0f,    1,1,// Bottom right
            -0.5f,  0.5f, 0.0f,       0.0f, 1.0f, 0.0f, 1.0f,   0,0, // Top left
            0.5f,  0.5f, 0.0f ,      1.0f, 0.0f, 1.0f, 1.0f,    1,0,// Top right
            -0.5f, -0.5f, 0.0f,       1.0f, 1.0f, 0.0f, 1.0f,   0,1, // Bottom left
    };

    private int[] triangles = {
            2,1,0, //Top right triangle
             0,1,3, //Bottom left triangle
    };

    public float[] GetVertices(){
        return this.vertices;
    }

    public int[] GetTriangles(){
        return this.triangles;
    }

}