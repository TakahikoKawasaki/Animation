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
 * Easing elastic interpolator.
 *
 * @author Takahiko Kawasaki
 */
public class EasingElasticInterpolator extends EasingInterpolator
{
    private int oscillationCount = 3;
    private float springiness = 3.0f;


    public EasingElasticInterpolator()
    {
        super();
    }


    public EasingElasticInterpolator(EasingMode easingMode)
    {
        super(easingMode);
    }


    /**
     * Get the count of oscillations. The default value is 3.
     *
     * @return
     *         The count of oscillations.
     */
    public int getOscillationCount()
    {
        return oscillationCount;
    }


    /**
     * Set the count of oscillations.
     *
     * @param oscillationCount
     *
     * @throws IllegalArgumentException
     *         The given value is less than 0.
     */
    public void setOscillationCount(int oscillationCount)
    {
        this.oscillationCount = checkOscillationCount(oscillationCount);
    }


    /**
     * Get the value of springiness. The default value is 3.0F.
     *
     * @return
     *         The value of springiness.
     */
    public float getSpringiness()
    {
        return springiness;
    }


    /**
     * Set the value of springiness.
     *
     * @param springiness
     *
     * @throws IllegalArgumentException
     *         The given value is less than 0.
     */
    public void setSpringiness(float springiness)
    {
        this.springiness = checkSpringiness(springiness);
    }


    @Override
    protected final float doEasing(float timeRatio)
    {
        if (springiness == 0)
        {
            return timeRatio;
        }
        else
        {
            return (float)((Math.exp(springiness * timeRatio) - 1) / (Math.exp(springiness) - 1));
        }
    }


    private static int checkOscillationCount(int oscillationCount)
    {
        if (oscillationCount < 0)
        {
            throw new IllegalArgumentException("oscillationCount < 0");
        }

        return oscillationCount;
    }


    private static float checkSpringiness(float springiness)
    {
        if (springiness < 0)
        {
            throw new IllegalArgumentException("springiness < 0");
        }

        return springiness;
    }
}
