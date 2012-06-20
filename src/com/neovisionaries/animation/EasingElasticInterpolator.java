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


    public int getOscillationCount()
    {
        return oscillationCount;
    }


    public void setOscillationCount(int oscillationCount)
    {
        this.oscillationCount = checkOscillationCount(oscillationCount);
    }


    public float getSpringiness()
    {
        return springiness;
    }


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
