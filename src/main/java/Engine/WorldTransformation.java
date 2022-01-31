package Engine;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class WorldTransformation {

    private Matrix4f projectionMatrix;
    private Matrix4f worldMatrix;

    public WorldTransformation(){
        this.worldMatrix = new Matrix4f();
        this.projectionMatrix = new Matrix4f();
    }

    public Matrix4f GetProjectionMatrix(float FOV, float width, float height, float nearPlane, float farPlane){
        float aspectRatio = width / height;
        projectionMatrix.identity();
        projectionMatrix.perspective(FOV, aspectRatio, nearPlane, farPlane);
        return projectionMatrix;
    }

    public Matrix4f GetWorldMatrix(Vector3f offset, Vector3f rotation, float scale){
        return worldMatrix.identity().translate(offset)
                .rotateX(Math.toRadians(rotation.x))
                .rotateY(Math.toRadians(rotation.y))
                .rotateZ(Math.toRadians(rotation.z))
                .scale(scale);
    }
}
