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
 * Easing back interpolator.
 *
 * @author Takahiko Kawasaki
 */
public class EasingBackInterpolator extends EasingInterpolator
{
    private float amplitude = 1.0f;


    public EasingBackInterpolator()
    {
        super();
    }


    public EasingBackInterpolator(EasingMode easingMode)
    {
        super(easingMode);
    }


    /**
     * Get the value of amplitute. The default value is 1.0F.
     *
     * @return
     *         The value of amplitude.
     */
    public float getAmplitude()
    {
        return amplitude;
    }


    /**
     * Set the value of amplitude.
     *
     * @param amplitude
     *
     * @throws IllegalArgumentException
     *         The given value is less than 0.
     */
    public void setAmplitude(float amplitude)
    {
        this.amplitude = checkAmplitude(amplitude);
    }


    @Override
    protected final float doEasing(float timeRatio)
    {
        return (float)(timeRatio * timeRatio * timeRatio - timeRatio * amplitude * Math.sin(timeRatio * Math.PI));
    }


    private static float checkAmplitude(float amplitude)
    {
        if (amplitude < 0)
        {
            throw new IllegalArgumentException("amplitude < 0");
        }

        return amplitude;
    }
}
