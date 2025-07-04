package io.sparkedember.juliaset.shaders

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

val JuliaSetShader = """
    uniform float2 iResolution; // The width and height of the component
    uniform float2 center;      // The center of the view in the complex plane (centerX, centerY)
    uniform float zoomRange;    // The zoom level
    uniform float2 constant;           // The Julia constant (x, y)
    uniform int maxIterations;  // The number of iterations
    uniform int coloringMethod;
    
    half3 getPaletteColor(int i) {
        if (i == 0) return half3(0.027, 0.015, 0.039);
        if (i == 1) return half3(0.172, 0.043, 0.054);
        if (i == 2) return half3(0.415, 0.047, 0.058);
        if (i == 3) return half3(0.9, 0.535, 0.09);
        if (i == 4) return half3(1.0, 0.916, 0.1);
        return half3(0.0); // Return black as a fallback
    }
    
    half3 hsv2rgb(half3 c) {
        half3 rgb = clamp(abs(mod(c.x * 6.0 + half3(0.0, 4.0, 2.0), 6.0) - 3.0) - 1.0, 0.0, 1.0);
        return c.z * mix(half3(1.0), rgb, c.y);
    }
    
    half3 defaultcolormethod(int i) {
        float hue_360 = mod(float(i * 10), 360.0);
        float t = hue_360 / 360.0; 
    
        half3 hsv = half3(t, 0.9, 1.0);
        return hsv2rgb(hsv);
    }
    
    half3 blackandwhite(int i) {
        if (mod(float(i), 2.0) == 0.0) return half3(1.0, 1.0, 1.0);
        return half3(0.0, 0.0, 0.0);
    }
    
    half3 palette(int i) {
        int palette_index = int(mod(float(i), 5.0));
        return getPaletteColor(palette_index);
    }
    
    half3 nocolorbanding(float2 z, int i) {
        float logZn = log2(dot(z, z));
        float nu = log2(logZn / 2.0);
        float smoothValue = float(i) + 1.0 - nu;
        float hue_360 = mod(smoothValue * 15.0, 360.0);
        
        float t = max(0.0, hue_360) / 360.0;
        half3 hsv = half3(t, 0.9, 1.0);
        return hsv2rgb(hsv);
    }
    
    half4 main(float2 fragCoord) {
        float aspectRatio = iResolution.x / iResolution.y;
        float2 correctedZoom = float2(zoomRange * aspectRatio, zoomRange);
        float2 uv = fragCoord.xy / iResolution.xy;
        float zx = uv.x * correctedZoom.x + (center.x - correctedZoom.x / 2.0);
        float zy = uv.y * correctedZoom.y + (center.y - correctedZoom.y / 2.0);
    
        int iteration = 0;
        // Since we can't have a changing variable as the iterator, we will clamp to 1000
        for (int i = 0; i < 1000; i++) {
            iteration = i;
            
            float tempzx = zx;
            zx = zx * zx - zy * zy + constant.x;
            zy = 2 * tempzx * zy + constant.y;
    
            if (zx * zx + zy * zy > 4.0) {
                break;
            }
        }
    
        if (iteration >= maxIterations - 1) {
            return half4(0.0, 0.0, 0.0, 1.0);
        }
        
        half3 rgb;
        if (coloringMethod == 0) {
            rgb = defaultcolormethod(iteration);
        } else if (coloringMethod == 1) {
            rgb = nocolorbanding(float2(zx, zy), iteration);
        } else if (coloringMethod == 2) {
            rgb = blackandwhite(iteration);
        } else if (coloringMethod == 3) {
            rgb = palette(iteration);
        }
        // Swap the blue and red channels for neat colors :)
        return half4(rgb.b, rgb.g, rgb.r, 1.0);
    }
"""

data class JuliaSetShaderParams(
    val canvasSize: Size,
    val center: Offset,
    val constant: Offset,
    val zoomRange: Float,
    val maxIterations: Int,
    val coloringMethod: Int
)