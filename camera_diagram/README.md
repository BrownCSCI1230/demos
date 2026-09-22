# Focus & aperture

Open `index.html` directly in a modern browser. No server, build step, network access, or third-party dependencies are needed. Keep the HTML, CSS, and JavaScript files together. They can also be served by any static web host.

- Drag the green object point horizontally, or use the object distance slider.
- Adjust Focus (lens–sensor distance) to move the sensor and the corresponding plane in focus. Increasing this distance focuses nearer; decreasing it focuses farther.
- Adjust focal length (lens property) and aperture diameter independently. Shorter focal lengths darken the lens shading to indicate stronger light bending (optical power ∝ 1/f). This is a symbolic cue, not light absorption or a simulated refractive index; lens shape stays unchanged.
- Focus on point moves the sensor to bring the current object into focus.
- Return point to focus moves the point to the current plane in focus; Reset all restores all defaults.
- The point supports arrow keys (10 mm steps), Shift + arrow (100 mm steps), Home, and End. The sliders support standard keyboard interaction.

## Optics

All distances are in millimeters. The sensor initially sits at s = 50 × 600 / (600 − 50) ≈ 54.545 mm from the lens. The focus control changes s at fixed focal length f, moving the object plane in focus to u₀ = f s / (s − f). The lens is kept stationary in the diagram, with the sensor moving to illustrate their changing separation. The focal length range is 43–51.5 mm. The focus control bounds depend on f so the focus plane stays within 200–1000 mm. Changing f preserves s unless it falls outside those bounds, in which case s is clamped to the nearest bound. At the default f = 50 mm, u₀ = 600 mm.

For object distance u, rays converge at v = f u / (u − f). A circular aperture of diameter D produces a circle of confusion with diameter c = D |1 − s/v| on the sensor. The f-number is f/D.
Object space uses a fixed linear scale (0.5 diagram units per millimeter), so changing an object or focus distance always changes its position. The view never recenters or rescales. Object distance ranges from 200 to 1000 mm. Image space and transverse distances also use fixed linear scales, different from object space. The sensor view stays at a fixed 40 × 40 mm scale. Disk luminance above the background is scaled by (D/60)², with linear-light scaling converted to sRGB for display. This is an explicitly illustrative aperture-throughput cue, not exact point-source irradiance: in the geometric defocused point model, both collected flux and blur area scale as D², so irradiance inside the disk can stay constant. The cue deliberately makes smaller apertures look darker to demonstrate the light-collection trade-off. The focused point has a minimum visible radius. This model excludes diffraction and aberrations. The object remains on the optical axis.

Unlike the reference slide's simplified labels, focal length is distinguished from lens-to-sensor distance, and object distance is measured from the lens.

## Verification

Run `node check.cjs` to check the thin-lens equation, ray intersections and circle diameters across the control range, plus control handlers. This uses a minimal DOM stand-in; it does not replace visual browser testing.
