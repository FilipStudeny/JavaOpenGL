package Engine;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

    public static Camera instance;
    private Vector3f position;
    private Vector3f orientation;

    private float yaw;
    private float pitch;
    private float roll;

    private Camera(){
        this.position = new Vector3f(0,0,0);
        this.orientation = new Vector3f(0,1,0);

        this.yaw = 0;
        this.pitch = 0;
        this.roll = 0;
    }

    public static Camera get(){
        if(instance == null){
            instance = new Camera();
        }

        return instance;
    }

    public static void SetLookDirection(float yaw, float pitch){
        get().pitch = pitch;
        get().yaw = yaw;
    }

    public static void RotateOrientation(float rotation){
        get().roll += rotation;
    }
    public static void SetOrientation(float rotation){
        get().roll = rotation;
    }
    public static void SetPosition(float x, float y, float z){
        get().position = new Vector3f(x, y, z);
    }
    public static void Translate(float x, float y, float z){
        get().position.add(new Vector3f(x, y, z));
    }

    public static void TranslateAlongYaw(float x, float y, float z){
        Vector3f offset = new Vector3f(x, y, z);
        offset.rotateY( Math.toRadians(get().yaw), offset);
        get().position.add(offset);
    }

    public static void TranslateAlongPitch(float x, float y, float z){
        Vector3f offset = new Vector3f(x, y, z);
        offset.rotateY( Math.toRadians(get().pitch), offset);
        get().position.add(offset);
    }

    public static Vector3f GetPostion(){
        return get().position;
    }
    public static float GetYaw(){
        return get().yaw;
    }
    public static float GetPitch(){
        return get().pitch;
    }
    public static float GetRoll(){
        return get().roll;
    }

    public static Matrix4f GetLookMatrix(){
        Matrix4f matrix = new Matrix4f();
        matrix.identity();

        Vector3f lookPoint = new Vector3f(0,0,-1);
        lookPoint.rotateX(Math.toRadians(get().pitch), lookPoint);
        lookPoint.rotateY(Math.toRadians(get().yaw), lookPoint);
        lookPoint.add(get().position, lookPoint);

        get().orientation.rotateZ(Math.toRadians(get().roll), get().orientation);

        matrix.lookAt(get().position, lookPoint, get().orientation, matrix);
        return matrix;
    }
}
