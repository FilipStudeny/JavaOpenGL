package Engine.Models;

public class Triangle{

    private float[] vertices = {
            //Position              //Colours
            -0.5f, -0.5f, 0.0f,       1.0f, 1.0f, 0.0f, 1.0f, //Bottom left
             0.5f, -0.5f, 0.0f,       1.0f, 0.0f, 0.0f, 1.0f, //Bottom right
               0f,  0.5f, 0.0f,       0.0f, 1.0f, 0.0f, 1.0f, //Top Left
    };

    private int[] triangles = {
            2,1,0, //Top right triangle
           // 0,1,3, //Bottom left triangle
    };

    public float[] GetVertices(){
        return this.vertices;
    }

    public int[] GetTriangles(){
        return this.triangles;
    }

}
