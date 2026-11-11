# Effect of Perspective Projection on Z values

Open `index.html` in a browser. No server, build, dependencies, or network connection is needed.

Adjust positive near and far distances using the sliders or numeric inputs. Reset restores near = 0.1 and far = 1. Hover over the curve to inspect z and z′.

Input z is already in the canonical view volume; no additional normalization is performed. With c = -near/far, matrix multiplication gives z_h = (z-c)/(1+c) and w_h = -z. After division by w, z′ = (c-z)/(z+zc). Far z=-1 maps to -1; near z=c maps to 0. The dashed line shows z′=z.

Numeric inputs require 0.001 ≤ near < far ≤ 10000. Invalid edits retain the last valid graph and display a message.

Run `node check.cjs` for math and control checks. Browser visual verification was unavailable because the preview browser blocks local file URLs.
