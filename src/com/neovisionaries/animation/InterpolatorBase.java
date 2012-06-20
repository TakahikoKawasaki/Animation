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
 * Base implementation of {@link Interpolator}
 *
 * @author Takahiko Kawasaki
 */
public abstract class InterpolatorBase implements Interpolator
{
    @Override
    public final void interpolate(float[] from, float[] to, int componentCount, float timeRatio, float[] output)
    {
        if (timeRatio < 0 || 1 < timeRatio)
        {
            throw new IllegalArgumentException("ratio < 0 || 1 < ratio");
        }

        if (componentCount < 1)
        {
            throw new IllegalArgumentException("componentCount < 1");
        }

        if (output == null)
        {
            throw new IllegalArgumentException("output == null");
        }

        if (output.length < componentCount)
        {
            throw new IllegalArgumentException("output.length < size");
        }

        if (timeRatio != 0)
        {
            if (from == null)
            {
                throw new IllegalArgumentException("from == null");
            }
            else if (from.length < componentCount)
            {
                throw new IllegalArgumentException("from.length < componentCount");
            }
        }

        if (timeRatio != 1)
        {
            if (to == null)
            {
                throw new IllegalArgumentException("to == null");
            }
            else if (to.length < componentCount)
            {
                throw new IllegalArgumentException("to.length < componentCount");
            }
        }

        if (timeRatio == 0 || from == to)
        {
            System.arraycopy(from, 0, output, 0, componentCount);
        }
        else if (timeRatio == 1)
        {
            System.arraycopy(to, 0, output, 0, componentCount);
        }
        else
        {
            doInterpolate(from, to, componentCount, timeRatio, output);
        }
    }


    protected abstract void doInterpolate(float[] from, float[] to, int componentCount, float timeRatio, float[] output);
}
