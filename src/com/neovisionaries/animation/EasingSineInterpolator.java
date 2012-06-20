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
 * Easing sine interpolator.
 *
 * @author Takahiko Kawasaki
 */
public class EasingSineInterpolator extends EasingInterpolator
{

    public EasingSineInterpolator()
    {
        super();
    }


    public EasingSineInterpolator(EasingMode easingMode)
    {
        super(easingMode);
    }


    @Override
    protected final float doEasing(float timeRatio)
    {
        return (float)(1 - (Math.sin(1 - timeRatio) * Math.PI / 2));
    }
}
