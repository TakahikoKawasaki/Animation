/*
 * Copyright (C) 2012 Neo Visionaries Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.neovisionaries.animation;


/**
 * SLERP interpolator.
 *
 * <p>
 * Note that the component count of from[], to[] and output[] must be 4
 * or greater.
 * </p>
 *
 * @author Takahiko Kawasaki
 */
public class SlerpInterpolator extends InterpolatorBase
{
    @Override
    protected final void doInterpolate(float[] from, float[] to, int componentCount, float timeRatio, float[] output)
    {
        if (componentCount < 4)
        {
            throw new IllegalArgumentException("componentCount < 4");
        }

        final int xIndex = 0;
        final int yIndex = 1;
        final int zIndex = 2;
        final int wIndex = 3;

        float x0 = from[xIndex];
        float y0 = from[yIndex];
        float z0 = from[zIndex];
        float w0 = from[wIndex];
        float x1 = to[xIndex];
        float y1 = to[yIndex];
        float z1 = to[zIndex];
        float w1 = to[wIndex];

        float cosOmega = w0 * w1 + x0 * x1 + y0 * y1 + z0 * z1;

        if (cosOmega < 0)
        {
            w1 = -w1;
            x1 = -x1;
            y1 = -y1;
            z1 = -z1;
            cosOmega = -cosOmega;
        }

        float k0;
        float k1;

        if (0.9999f < cosOmega)
        {
            k0 = 1 - timeRatio;
            k1 = timeRatio;
        }
        else
        {
            double sinOmega = Math.sqrt(1 - cosOmega * cosOmega);
            double omega = Math.atan2(sinOmega, cosOmega);
            double oneOverSinOmega = 1 / sinOmega;

            k0 = (float)(Math.sin((1 - timeRatio) * omega) * oneOverSinOmega);
            k1 = (float)(Math.sin(timeRatio * omega) * oneOverSinOmega);
        }

        output[wIndex] = w0 * k0 + w1 * k1;
        output[xIndex] = x0 * k0 + x1 * k1;
        output[yIndex] = y0 * k0 + y1 * k1;
        output[zIndex] = z0 * k0 + z1 * k1;
    }
}
