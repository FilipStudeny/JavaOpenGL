package Engine.Lighting;

import org.joml.Vector3f;

public class Spotlight {

    private PointLight pointLight;

    private Vector3f coneDirection;
    private float coneCutoff;

    public Spotlight(PointLight pointLight, Vector3f coneDirection, float coneCutoff){
        this.pointLight = pointLight;
        this.coneCutoff = coneCutoff;
        this.coneDirection = coneDirection;
    }

    public Spotlight(Spotlight spotlight){
        this.pointLight = spotlight.getPointLight();
        this.coneDirection = spotlight.getConeDirection();
        setConeCutoff(spotlight.getConeCutoff());
    }

    public PointLight getPointLight() {
        return pointLight;
    }

    public void setPointLight(PointLight pointLight) {
        this.pointLight = pointLight;
    }

    public Vector3f getConeDirection() {
        return coneDirection;
    }

    public void setConeDirection(Vector3f coneDirection) {
        this.coneDirection = coneDirection;
    }

    public float getConeCutoff() {
        return coneCutoff;
    }

    public void setConeCutoff(float coneCutoff) {
        this.coneCutoff = coneCutoff;
    }
}
