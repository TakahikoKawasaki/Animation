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
 * Easing power interpolator.
 *
 * @author Takahiko Kawasaki
 */
public class EasingPowerInterpolator extends EasingInterpolator
{
    private float power = 2.0f;


    public EasingPowerInterpolator()
    {
        super();
    }


    public EasingPowerInterpolator(EasingMode easingMode)
    {
        super(easingMode);
    }


    public float getPower()
    {
        return power;
    }


    public void setPower(float power)
    {
        this.power = checkPower(power);
    }


    @Override
    protected final float doEasing(float timeRatio)
    {
        return (float)Math.pow(timeRatio, power);
    }


    private static float checkPower(float power)
    {
        if (power < 0)
        {
            throw new IllegalArgumentException("power < 0");
        }

        return power;
    }
}
