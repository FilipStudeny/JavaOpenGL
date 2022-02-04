package Engine;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class WorldTransformation {

    private Matrix4f projectionMatrix;
    private Matrix4f modelViewMatrix;

    public WorldTransformation(){
        this.projectionMatrix = new Matrix4f();
        this.modelViewMatrix = new Matrix4f();

    }

    public Matrix4f GetProjectionMatrix(float FOV, float width, float height, float nearPlane, float farPlane){
        float aspectRatio = width / height;
        projectionMatrix.identity();
        projectionMatrix.perspective(FOV, aspectRatio, nearPlane, farPlane);
        return projectionMatrix;
    }


    public Matrix4f GetModelViewMatrix(GameObject gameObject, Matrix4f viewMatrixS){
        Vector3f rotation = gameObject.GetRotation();
        modelViewMatrix.identity().translate(gameObject.GetPosition()).
                rotateX((float)Math.toRadians(-rotation.x)).
                rotateY((float)Math.toRadians(-rotation.y)).
                rotateZ((float)Math.toRadians(-rotation.z)).
                scale(gameObject.GetScale());
        Matrix4f viewCurr = new Matrix4f(viewMatrixS);
        return viewCurr.mul(modelViewMatrix);
    }
}