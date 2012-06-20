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
 * Step interpolator.
 *
 * <p>
 * The value of from[] is always copied to output[] whatever timeRatio is.
 * </p>
 *
 * @author Takahiko Kawasaki
 */
public class StepInterpolator extends InterpolatorBase
{
    @Override
    protected final void doInterpolate(float[] from, float[] to, int componentCount, float timeRatio, float[] output)
    {
        System.arraycopy(from, 0, output, 0, componentCount);
    }
}
