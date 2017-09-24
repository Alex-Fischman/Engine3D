# Engine3D
A few Java classes that can be used within an android app to provide 3D functionality. Uses OpenGL 2.0.
# Environment3D
An extension of the GLSurfaceView class. You can move around the environment by pinching, and rotate by dragging. If you don't want these functionalities, extend the GLSurfaceView class yourself.
>Special needs: Renderer3D
# Renderer3D
An implementation of GLSurfaceView.Renderer. Does all the OpenGL handling. Holds a shader program, a projection and view matrix, and an Object3DHolder (see below).
>Special needs: Object3DHolder
# Object3D
An interface to be extended by any object you want to be held in an Object3DHolder, and/or rendered.
>Special needs: None
# Point3D
A struct-like class that simply provides base functionality to the Object3D interface.
>Special needs: Object3D
# Triangle3D
Another struct-like class that holds 3 Point3D's while also satisfying the Object3D interface.
>Special needs: Point3D, Object3D
# Object3DHolder
Holds an ArrayList of Object3D's. Provides FloatBuffer returning methods that can be called by a Renderer3D. These methods allow the Renderer3D to access vertex and color data from all the Object3D's contained in the ArrayList.
>Special needs: Object3D
