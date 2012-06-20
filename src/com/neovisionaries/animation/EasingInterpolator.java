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
 * Base implementation of easing interpolators.
 *
 * @author Takahiko
 */
public abstract class EasingInterpolator extends InterpolatorBase
{
    private EasingMode easingMode = EasingMode.OUT;


    /**
     * The default constructor with the default easing mode,
     * {@link EasingMode#OUT}.
     */
    public EasingInterpolator()
    {
    }


    /**
     * A constructor with an easing mode.
     *
     * @param easingMode
     *         An easing mode.
     *
     * @throws IllegalArgumentException
     *         The argument is null.
     */
    public EasingInterpolator(EasingMode easingMode)
    {
        this.easingMode = checkEasingMode(easingMode);
    }


    /**
     * Get the easing mode. The default value is {@link EasingMode#OUT}.
     *
     * @return
     *         The easing mode.
     */
    public EasingMode getEasingMode()
    {
        return easingMode;
    }


    /**
     * Set an easing mode.
     *
     * @param easingMode
     *         An easing mode.
     *
     * @throws IllegalArgumentException
     *         The argument is null.
     */
    public void setEasingMode(EasingMode easingMode)
    {
        this.easingMode = checkEasingMode(easingMode);
    }


    @Override
    protected final void doInterpolate(float[] from, float[] to, int componentCount, float timeRatio, float[] output)
    {
        // EasingMode.IN
        if (easingMode == EasingMode.IN)
        {
            timeRatio = doEasing(timeRatio);
        }
        // EasingMode.OUT
        else if(easingMode == EasingMode.OUT)
        {
            timeRatio = 1 - doEasing(1 - timeRatio);
        }
        // EasingMode.IN_OUT (timeRatio < 0.5)
        else if (timeRatio < 0.5f)
        {
            timeRatio = doEasing(timeRatio * 2) * 0.5f;
        }
        // EasingMode.IN_OUT (0.5 <= timeRatio)
        else
        {
            timeRatio = 1 - doEasing((1 - timeRatio) * 2) * 0.5f + 0.5f;
        }

        for (int i = 0; i < componentCount; ++i)
        {
            output[i] = from[i] * (1 - timeRatio) + to[i] * timeRatio;
        }
    }


    protected abstract float doEasing(float timeRatio);


    private static EasingMode checkEasingMode(EasingMode easingMode)
    {
        if (easingMode == null)
        {
            throw new IllegalArgumentException("easingMode == null");
        }

        return easingMode;
    }
}
