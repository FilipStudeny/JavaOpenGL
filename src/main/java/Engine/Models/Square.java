package Engine.Models;

public class Square{

    private float[] vertices = {
            //Position              //Colours
             50f, -50f, -10f,       1.0f, 0.0f, 0.0f, 1.0f, //Bottom right
            -50f,  50f, -10f,       0.0f, 1.0f, 0.0f, 1.0f, //Top Left
             50f,  50f, -10f,       0.0f, 0.0f, 1.0f, 1.0f, //Top Right
            -50f, -50f, -10f,       1.0f, 1.0f, 0.0f, 1.0f, //Bottom left
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