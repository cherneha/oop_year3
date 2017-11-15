package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.material.Material;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.*;
import com.jme3.input.controls.*;
import com.jme3.math.*;
import com.jme3.light.DirectionalLight;
import java.util.ArrayList;
import java.lang.*;
import java.util.List;
import java.util.Random;
//import com.jme3.util.BufferUtils;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    protected Spatial cone;
    volatile ArrayList<Spatial> lamborginis;
    Random random = new Random();

    @Override
    public void simpleInitApp() {

        lamborginis = new ArrayList<Spatial>();
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-.5f, -.5f, -.5f).normalizeLocal());
        rootNode.addLight(sun);

       cone = assetManager.loadModel("Models/cone.j3o");
//        Spatial carModel = assetManager.loadModel("Models/Avent/Avent.j3o");
//        lamborginis.add(carModel);
//        for(Spatial car: lamborginis){
//            rootNode.attachChild(car);
//        }
        generateCar();

//        Material lambMat = assetManager.loadMaterial("Material/Avent.mtl");
//        lamborgini.setMaterial(lambMat);
       

        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Diffuse", ColorRGBA.Orange);
        mat.setColor("Specular", ColorRGBA.Orange);

        rootNode.attachChild(cone);

        cone.setMaterial(mat);
        float scale = 0.25f;
        cone.scale(scale);
        initKeys();

        Vector3f camPos = new Vector3f(0f, 4f, 10f);
        this.cam.setLocation(camPos);

    }
    
    public void generateCar(){
        Spatial car = assetManager.loadModel("Models/Car Obj/Car Obj.j3o");
        lamborginis.add(car);
        rootNode.attachChild(car);
            int x = random.nextInt(21) -10;
            Vector3f vLamborgini = car.getLocalTranslation();
            car.setLocalTranslation(vLamborgini.x + x, vLamborgini.y, vLamborgini.z - 70);
            
            Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
            car.setMaterial(mat);
             mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Diffuse", ColorRGBA.Red);
        mat.setColor("Specular", ColorRGBA.Red);
            //Quaternion carRot = new Quaternion();
            //carRot.fromAngleAxis(FastMath.PI / 2, new Vector3f(0, 1, 0));
            //car.setLocalRotation(carRot);
    }

    public void translate(Spatial object, Vector3f coordinatesChanges) {
        Vector3f v = object.getLocalTranslation();
        object.setLocalTranslation(v.x + coordinatesChanges.x, v.y + coordinatesChanges.y, v.z + coordinatesChanges.z);

    }

    @Override
    public void simpleUpdate(float tpf) {
        float minZ = 3; 
        //Iterator <Spatial> iter = lamborginis.iterator();
        int i = 0;
        List<Integer> toDel = new ArrayList<Integer>();
        ArrayList<Spatial> noNeeded = new ArrayList<Spatial>();
       
        for (Spatial car : lamborginis) {
            //int speed =random.nextInt(5) + 1;
            translate(car, new Vector3f(0f, 0f, 30f * tpf));
            Vector3f currentCoordinates = car.getLocalTranslation();
            if (currentCoordinates.z < minZ){
                minZ = currentCoordinates.z;
            }
            if (car.getLocalTranslation().z > 8f) {
                noNeeded.add(car);
                rootNode.detachChild(car);
                //rootNode.updateGeometricState();
            }
            i++;
        }
        
        if(minZ > -60){
            generateCar();
        }   
        
        for(Spatial delCar: noNeeded){
        lamborginis.remove(delCar);
        }

        int diff = 0;

    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    private void initKeys() {

        KeyTrigger keyLeft = new KeyTrigger(KeyInput.KEY_A);
        KeyTrigger keyRight = new KeyTrigger(KeyInput.KEY_D);
        KeyTrigger keyUp = new KeyTrigger(KeyInput.KEY_W);
        KeyTrigger keyDown = new KeyTrigger(KeyInput.KEY_X);
        inputManager.addMapping("Left", keyLeft);
        inputManager.addMapping("Right", keyRight);
        inputManager.addMapping("Up", keyUp);
        inputManager.addMapping("Down", keyDown);
        AnalogListener keyListener = new AnalogListener() {
            @Override
            public void onAnalog(String name, float value, float tpf) {

                if (name.equals("Right")) {
                    Vector3f v = cone.getLocalTranslation();
                    cone.setLocalTranslation(v.x + value * speed * 13, v.y, v.z);
                }
                if (name.equals("Left")) {
                    Vector3f v = cone.getLocalTranslation();
                    cone.setLocalTranslation(v.x - value * speed * 13, v.y, v.z);
                }
                if (name.equals("Up")) {
                    Vector3f v = cone.getLocalTranslation();
                    cone.setLocalTranslation(v.x, v.y + value * speed * 13, v.z);
                }
                if (name.equals("Down")) {
                    Vector3f v = cone.getLocalTranslation();
                    if(v.x > 0){
                    cone.setLocalTranslation(v.x, v.y - value * speed * 13, v.z);
                    }
                }

            }
        };
        inputManager.addListener(keyListener, "Left", "Right", "Up", "Down");
    }
}
