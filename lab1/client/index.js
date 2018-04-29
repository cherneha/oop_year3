var camera, scene, renderer;
var geometry, material, hedgehog;
var apples = [];
var hedgehogRadius = 1;
var appleRadius = 0.25;
var appleCounter = 0;

var socket = new WebSocket("ws://localhost:8080/game");
socket.onmessage = onMessage;
socket.onopen = function(){
    socket.send("3");
    //socket.send("8");
}

function onMessage(event) {
    if(apples.length == 0){
    var device = JSON.parse(event.data);
    console.log(device);
    generateAllApples(device.x, device.y);
    }
}

window.onload = function(){
    init();	
    //animate();
}

function init() {


 
    camera = new THREE.PerspectiveCamera( 100, window.innerWidth / window.innerHeight, 0.01, 10 );
    camera.position.z = 4;
    camera.position.y += 3;
    camera.rotation.x -= 1;
 
    scene = new THREE.Scene();


    var light = new THREE.DirectionalLight( 0xff0000, 10, 100 );
    light.position.set( 5, -2, 2 );
    light.castShadow = true;
    light.shadow.mapSize.width = 20;  
    light.shadow.mapSize.height = 20;
    scene.add( light );

 
    renderer = new THREE.WebGLRenderer( { antialias: true } );
    renderer.setSize( window.innerWidth, window.innerHeight );
    renderer.shadowMap.enabled = true;

    document.body.appendChild( renderer.domElement );

    var loader2 = new THREE.TextureLoader();
    loader2.load('grass.jpg', function ( texture ) {
        var grassGeo = new THREE.PlaneGeometry(20, 20);
        var grassMaterial = new THREE.MeshBasicMaterial({map: texture, overdraw: 0.5});
        grass = new THREE.Mesh(grassGeo, grassMaterial);
        grass.rotation.x -= (Math.PI / 2);
        grass.position.y -= 1;
        scene.add(grass);
        renderer.render( scene, camera );
    });
    loader2.load('sky.jpg', function ( texture ) {
        var skyGeo = new THREE.PlaneGeometry(20, 25);
        var skyMaterial = new THREE.MeshBasicMaterial({map: texture, overdraw: 0.5});
        var sky = new THREE.Mesh(skyGeo, skyMaterial);
        sky.rotation.y = (Math.PI / 2);
        sky.position.x = -10;
        scene.add(sky);

        skyGeo = new THREE.PlaneGeometry(20, 20);
        var sky3 = new THREE.Mesh(skyGeo, skyMaterial);
        sky3.position.x = 10;
        sky3.rotation.y -= (Math.PI / 2);
        //sky3.position.z = -6;
        scene.add(sky3);

        skyGeo = new THREE.PlaneGeometry(40, 20);
        var sky2 = new THREE.Mesh(skyGeo, skyMaterial);
        sky2.position.z = -6;
        sky2.rotation.x -= (Math.PI / 8);
        scene.add(sky2);



        renderer.render( scene, camera );
    });
    var loader = new THREE.TextureLoader();
    loader.load('hedgehog.jpeg', function ( texture ) {

        geometry = new THREE.SphereGeometry(hedgehogRadius, 7, 7);
        material = new THREE.MeshBasicMaterial({map: texture, overdraw: 0.5});
        hedgehog = new THREE.Mesh(geometry, material);
        hedgehog.position.y = 0;
        scene.add(hedgehog);
        renderer.render( scene, camera );
});


    document.body.addEventListener("keyup", function(event){
        if (event.keyCode === 38) {
            if(hedgehog.position.z > -3.5){
                hedgehog.position.z -= 0.5;
                roll();
            }
        }
        else if (event.keyCode === 40) {
            if(hedgehog.position.z < 2){
                hedgehog.position.z += 0.5;
                roll();
            }
        }
        else if (event.keyCode === 37) {
            if(hedgehog.position.x > -8){
                hedgehog.position.x -= 0.5;
                roll();
            }
        }
        else if (event.keyCode === 39) {
            if(hedgehog.position.x < 8){
                hedgehog.position.x += 0.5;
                roll();
            }
        }
        checkIntersection();
    })

}
 
function checkIntersection(){
    apples.forEach(function(apple, i, arr){
        if(distance(apple, hedgehog)){
            var toDelete = apples[i];
            apples.splice(i, 1);
            removeEntity(toDelete);
            return;
        }
    });
}

function addApples(x, z){
        var appleGeometry = new THREE.SphereGeometry(appleRadius, 7, 7);
        var appleMaterial = new THREE.MeshBasicMaterial({color: 0xE30000, overdraw: 0.5});
        var ap = new THREE.Mesh(appleGeometry, appleMaterial);
        ap.position.y = 0;
        ap.position.x = x;
        ap.position.z = z;
        ap.name = appleCounter.toString();
        appleCounter++;
        apples.push(ap);
        scene.add(apples[apples.length  - 1]);

}

function distance(obj1, obj2){
    var distance = Math.sqrt(Math.pow(obj2.position.x - obj1.position.x, 2) 
        + Math.pow(obj2.position.y - obj1.position.y, 2) + Math.pow(obj2.position.z - obj1.position.z, 2));
    if(distance < (hedgehogRadius + appleRadius)){
        return true;
    }
    return false;
}

function removeEntity(object) {
    console.log("in rem");
    var selectedObject = scene.getObjectByName(object.name);
    scene.remove( selectedObject );
    renderer.render(scene, camera);
    console.log("after render");
    if(apples.length == 0){
console.log("in if");
renderer.render(scene, camera);
        var answer = prompt("Play again? Choose apple quantity from 1 to 20!");
        if(answer != null && answer != ""){
            console.log(answer);
            console.log(parseInt(answer));
            var n = parseInt(answer);
            if(n > 0 && n <= 20){
                socket.send(answer);
            }
            else{
                socket.send("5");
            }
        }
    }
}

function roll() {

    //requestAnimationFrame( roll );
 
    hedgehog.rotation.x += 0.1;
    hedgehog.rotation.z += 0.2;
 
    renderer.render( scene, camera );
 
}

function generateAllApples(x, y){
    console.log(x);
    console.log(y);
    for(var i = 0; i < x.length; i++){
        addApples(parseFloat(x[i].x), parseFloat(y[i].y));
    }
    renderer.render(scene, camera);
}
