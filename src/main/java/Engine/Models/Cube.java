package Engine.Models;

public class Cube {
    private float[] vertices = {
            //Position              //Colours
            // VO
            -0.5f,  0.5f,  0.5f,    0.5f, 0.0f, 0.0f, 1.0f, //Bottom right
            // V1
            -0.5f, -0.5f,  0.5f,    0.0f, 0.5f, 0.0f, 1.0f, //Top Left
            // V2
            0.5f, -0.5f,  0.5f,     0.0f, 0.0f, 0.5f, 1.0f, //Top Right
            // V3
            0.5f,  0.5f,  0.5f,     0.0f, 0.5f, 0.5f, 1.0f, //Bottom left
            // V4
            -0.5f,  0.5f, -0.5f,    0.5f, 0.0f, 0.0f, 1.0f,
            // V5
            0.5f,  0.5f, -0.5f,     0.0f, 0.5f, 0.0f, 1.0f,
            // V6
            -0.5f, -0.5f, -0.5f,    0.0f, 0.0f, 0.5f, 1.0f,
            // V7
            0.5f, -0.5f, -0.5f,     0.0f, 0.5f, 0.5f, 1.0f,
    };

    private int[] triangles = {
            // Front face
            0, 1, 3, 3, 1, 2,
            // Top Face
            4, 0, 3, 5, 4, 3,
            // Right face
            3, 2, 7, 5, 3, 7,
            // Left face
            6, 1, 0, 6, 0, 4,
            // Bottom face
            2, 1, 6, 2, 6, 7,
            // Back face
            7, 6, 4, 7, 4, 5,
    };

    public float[] GetVertices(){
        return this.vertices;
    }

    public int[] GetTriangles(){
        return this.triangles;
    }
}
