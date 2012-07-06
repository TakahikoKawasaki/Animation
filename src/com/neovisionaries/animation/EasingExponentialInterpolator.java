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
 * Easing exponential interpolator.
 *
 * @author Takahiko Kawasaki
 */
public class EasingExponentialInterpolator extends EasingInterpolator
{
    private float exponent = 2.0f;


    public EasingExponentialInterpolator()
    {
        super();
    }


    public EasingExponentialInterpolator(EasingMode easingMode)
    {
        super(easingMode);
    }


    /**
     * Get the value of exponent. The default value is 2.0F.
     *
     * @return
     *         The value of exponent.
     */
    public float getExponent()
    {
        return exponent;
    }


    /**
     * Set the value of exponent.
     *
     * @param exponent
     */
    public void setExponent(float exponent)
    {
        this.exponent = exponent;
    }


    @Override
    protected final float doEasing(float timeRatio)
    {
        if (exponent == 0)
        {
            return timeRatio;
        }
        else
        {
            return (float)((Math.exp(exponent * timeRatio) - 1) / (Math.exp(exponent) - 1));
        }
    }
}
