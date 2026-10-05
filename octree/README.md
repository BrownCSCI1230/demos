# Octree construction demo

Open `index.html` directly in a browser. No build or external dependencies are needed.

Use Next step, Play, or the timeline to follow depth-first construction. Drag the scene to orbit; scroll to zoom. Select tree rows to highlight cells and their objects. Change the scene, maximum depth, or leaf node capacity to restart with different rules.

The root is the tight axis-aligned scene bounding box (it need not be a cube). Splitting bisects each axis, making exactly eight children, including empty leaf nodes. Sphere–cell intersection uses the exact closest-point test; boxes use axis-aligned overlap. Boundary contact counts as intersection. Spanning objects produce duplicate object references, not cut geometry. Internal nodes clear their object references after assignment. Recursion stops at the chosen depth or when the object reference count is within capacity. The root has depth zero.

The viewer’s projection control switches between orthographic (the default) and perspective without changing the construction step or camera orientation. Perspective makes nearer objects appear larger; zoom adjusts the image scale in both modes.

Rendering uses Canvas 2D projection of 3D geometry with translucent objects and wireframes. It is an explanatory view, not a physically based renderer.

The “Deep cluster (depth 3)” scene contains ten spheres. Set maximum depth to 3 and maximum objects per leaf node to 1: eight clustered spheres share one depth-two cell and separate into its eight depth-three children. Two distant spheres establish the scene bounds. At maximum depth 2, the clustered leaf node still contains eight object references.
