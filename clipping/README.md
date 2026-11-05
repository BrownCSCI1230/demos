# Why clip before dividing?

Open `index.html` directly in a modern browser. No build step, server, network connection, or third-party libraries are needed. WebGL must be available.

Start with **Crossing the camera**, then move vertex C's eye-space z through zero. The eye-space side view shows the frustum; drag the NDC view to inspect the canonical cube. Toggle the two polygons independently. The two image panels always compare the same geometry. Use the other presets to compare a fully visible triangle, a triangle entirely behind the camera, and one beyond the far plane.

## What this demonstrates

With OpenGL's usual perspective matrix, a behind-camera point has negative clip w and divided z greater than +1. It does **not** independently project into the entire canonical cube. The failure occurs for a primitive crossing w = 0: connecting its divided vertices with finite straight edges creates a different primitive, which can intersect the canonical cube. The default orange false image is below the original base edge, while the correct green image extends above it.

The matrix uses a 90-degree vertical field of view, aspect ratio 1, adjustable near and far distances (initially 1 and 8). Eye space looks down -Z; NDC depth is [-1,+1]. Both final images are square and retain this aspect ratio regardless of panel size.

The correct WebGL path outputs clip coordinates as `gl_Position`. The intentionally incorrect path outputs `vec4(clip.xyz / clip.w, 1)`, so WebGL clips the already-divided, reconnected triangle. WebGL clipping itself cannot be disabled; this shader bypasses its access to the original homogeneous coordinates. Face culling and depth testing are off in both paths.

The 3D NDC inspection view uses a separate orthographic observer. Its green polygon comes from Sutherland–Hodgman clipping against all six homogeneous planes; its orange triangle is the original divided triangle, including portions outside the cube. It is an inspection view, not the original camera image. Very large coordinates near w = 0 may extend beyond this inspection view. At exactly w = 0 the incorrect drawing pauses rather than substituting an arbitrary finite value.

A/B have x = ±1.5, y = -0.8, and share an adjustable z. C has x = 0 and adjustable y/z. A and B coincide in the side-view diagram.

## Verification

Run `node check.cjs`. Checks cover near/far mapping, the behind-camera depth bound, the default false intersection, expected presets, and clipping invariants across 324 configurations including zero w and multiple far distances. JavaScript syntax and browser interaction checks were also performed, including all four presets and the exact singularity.

Reference: [Khronos: vertex post-processing / clipping](https://wikis.khronos.org/opengl/Vertex_Post-Processing#Clipping).
