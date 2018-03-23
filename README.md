# Bezier-Curves
Basic GUI application for drawing Bezier Curves, and modeling lighting from a chosen point light source.


Part of the design requirements specified to only illuminate the “left side, relative to the curve”. In other words, your left if you were traveling along the curve. i.e. moving from P-start to P-end. This means if you draw it upside down (top to bottom), the left side of the curve is actually right of the screen, and the other side will be completely darkened which is not a mistake.


The drawing of the control points also has a unique feature, where the you can continually update the curve by draing new points. If, for example, a cubic order curve was selected, after 4 points were drawn you can specify a new 4th point, and the old point 1 is deleted. Therefore the old p2 becomes new p1, p3 becomes new p2, p4 becomes new p3, which frees up p4 for the the control point.
