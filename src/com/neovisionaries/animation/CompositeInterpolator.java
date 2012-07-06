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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * A composite interpolator.
 *
 * <p>
 * This class combines multiple interpolators.
 * </p>
 *
 * <p>
 * When {@link #doInterpolate(float[], float[], int, float, float[]) doInterpolator()}
 * is called, {@link Interpolator#interpolate(float[], float[], int, float, float[])
 * interpolator()} method of each registered interpolator is executed one by one and
 * the calculated values are accumulated. The logic of the accumulation is implemented
 * in {@link #accumulate(float[], float, float[]) accumulate()} method and the method
 * can be overridden if necessary.
 * </p>
 *
 * <p>
 * If no interpolator is registered when {@link
 * #doInterpolate(float[], float[], int, float, float[]) doInterpolator()} is called,
 * CompositeInterpolator behaves in the same way as {@link LinearInterpolator}.
 * </p>
 *
 * <pre style="background-color: lightgray;">
 *
 * {@link Interpolator} interpolatorA = ......;
 * {@link Interpolator} interpolatorB = ......;
 *
 * {@link CompositeInterpolator} ci = new {@link #CompositeInterpolator()};
 * {@link CompositeInterpolator.Entry Entry} entryA = ci.{@link #add(Interpolator, float) add}(interpolatorA, 1.0F);
 * {@link CompositeInterpolator.Entry Entry} entryB = ci.{@link #add(Interpolator, float) add}(interpolatorB, 0.0F);
 *
 * for (int i = 0; i <= 10; ++i)
 * {
 *     <span style="color: darkgreen;">// Change weights.</span>
 *     entryA.{@link CompositeInterpolator.Entry#setWeight(float) setWeight}((10 - i) * 0.1F);
 *     entryB.{@link CompositeInterpolator.Entry#setWeight(float) setWeight}(      i  * 0.1F);
 *
 *     <span style="color: darkgreen;">// Use the composite interpolator.</span>
 *     ......
 * }
 * </pre>
 *
 * @author Takahiko Kawasaki
 */
public class CompositeInterpolator extends InterpolatorBase
{
    /**
     * The default interpolator used when no interpolator is registered.
     */
    private static final Interpolator DEFAULT_INTERPOLATOR = new LinearInterpolator();


    /**
     * Interpolators registered into this CompositeInterpolator.
     */
    private List<Entry> entryList;


    /**
     * A pair of {@link Interpolator} and weight.
     *
     * @author Takahiko Kawasaki
     */
    public static class Entry
    {
        private final Interpolator interpolator;
        private float weight;


        /**
         * A constructor with {@link Interpolator} and weight.
         *
         * @param interpolator
         * @param weight
         *
         * @throws IllegalArgumentException
         *         'interpolator' is null.
         */
        public Entry(Interpolator interpolator, float weight)
        {
            if (interpolator == null)
            {
                throw new IllegalArgumentException("interpolator is null");
            }

            this.interpolator = interpolator;
            this.weight = weight;
        }


        /**
         * Get the interpolator.
         *
         * @return
         *         The interpolator given to the {@link
         *         CompositeInterpolator.Entry#CompositeInterpolator.Entry(Interpolator, float)
         *         constructor}.
         */
        public Interpolator getInterpolator()
        {
            return interpolator;
        }


        /**
         * Get the weight.
         *
         * @return
         *         Weight.
         */
        public float getWeight()
        {
            return weight;
        }


        /**
         * Set the weight.
         *
         * @param weight
         */
        public void setWeight(float weight)
        {
            this.weight = weight;
        }
    }


    @Override
    protected final void doInterpolate(float[] from, float[] to, int componentCount, float timeRatio, float[] output)
    {
        if (entryList == null)
        {
            // No interpolator is registered, so use the default interpolator.
            DEFAULT_INTERPOLATOR.interpolate(from, to, componentCount, timeRatio, output);

            // Interpolation was done.
            return;
        }

        // Clear the output array before starting to accumulate interpolated values.
        Arrays.fill(output, 0, componentCount, 0.0F);

        // A work buffer to hold one interpolated value.
        float[] work = new float[componentCount];

        // For each interpolator.
        for (Entry entry : entryList)
        {
            // Interpolator.
            Interpolator interpolator = entry.getInterpolator();

            // Weight.
            float weight = entry.getWeight();

            // Clear the work buffer.
            Arrays.fill(work, 0.0F);

            // Let the interpolater interpolate and store the output to 'work'.
            interpolator.interpolate(from, to, componentCount, timeRatio, work);

            // Accumulate the interpolated value and update the output array.
            accumulate(work, weight, output);
        }
    }


    /**
     * Accumulate values calculated by registered interpolators.
     *
     * <p>
     * The default implementation of this method does the following.
     * </p>
     *
     * <pre style="background-color: lightgray;">
     *
     * for (int i = 0; i < value.length; ++i)
     * {
     *     output[i] += value[i] * weight;
     * }
     * </pre>
     *
     * <p>
     * The above implementation assumes that the possible range of each weight
     * is in between 0.0F (no influence rate) and 1.0F (100%) and that the sum
     * of all weights is 1.0F.
     * </p>
     *
     * @param value
     *         An interpolated value by an interpolator.
     *
     * @param weight
     *         A weight value specified when the interpolator was registered
     *         by {@link #add(Interpolator, float)}.
     *
     * @param output
     *         The 'output' argument passed to {@link #doInterpolate(float[],
     *         float[], int, float, float[]) doInterpolate()}.
     */
    protected void accumulate(float[] value, float weight, float[] output)
    {
        // The default accumulation behavior.
        for (int i = 0; i < value.length; ++i)
        {
            output[i] += value[i] * weight;
        }
    }


    /**
     * Add a pair of interpolator and weight.
     *
     * <p>
     * The way that the weight is used depends on the implementation of
     * {@link #accumulate(float[], float, float[]) accumulate()} method.
     * </p>
     *
     * @param interpolator
     *         An interpolator.
     *
     * @param weight
     *         Weight given to the interpolator.
     * 
     * @return
     *         An {@link CompositeInterpolator.Entry Entry} instance that
     *         represents the pair of the interpolator and the weight.
     *         By using the instance, the weight can be changed after the
     *         interpolator was added to this CompositeInterpolator.
     *
     * @throws IllegalArgumentException
     *         'interpolator' is null.
     */
    public Entry add(Interpolator interpolator, float weight)
    {
        if (interpolator == null)
        {
            throw new IllegalArgumentException("interpolator is null");
        }

        Entry entry = new Entry(interpolator, weight);

        return add(entry);
    }


    /**
     * Add a pair of interpolator and weight.
     *
     * @param entry
     *
     * @return
     *         The given entry.
     *
     * @throws IllegalArgumentException
     *         The given argument is null.
     */
    public Entry add(Entry entry)
    {
        if (entry == null)
        {
            throw new IllegalArgumentException("entry is null");
        }

        if (entryList == null)
        {
            entryList = new ArrayList<Entry>();
        }

        entryList.add(entry);

        return entry;
    }


    /**
     * Remove a pair of interpolator and weight.
     *
     * @param entry
     *         A pair to remove.
     */
    public void remove(Entry entry)
    {
        if (entry == null)
        {
            return;
        }

        if (entryList == null)
        {
            return;
        }

        entryList.remove(entry);

        if (entryList.size() == 0)
        {
            entryList = null;
        }
    }


    /**
     * Remove all pairs of interpolator and weight.
     */
    public void removeAll()
    {
        entryList = null;
    }


    /**
     * Get a pair of interpolator and weight at the specified index.
     *
     * @param index
     *         The position of the pair.
     *
     * @return
     *         A pair of interpolator and weight at the specified index.
     *
     * @throws IndexOutOfBoundsException
     */
    public Entry get(int index)
    {
        if (entryList == null || index < 0 || entryList.size() <= index)
        {
            throw new IndexOutOfBoundsException();
        }

        return entryList.get(index);
    }


    /**
     * Get the list of all pairs of interpolator and weight (immutable).
     *
     * @return
     *         A list of all pairs of interpolator and weight. If no
     *         pair is registered, an empty list is returned.
     */
    public List<Entry> getAll()
    {
        if (entryList == null)
        {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(entryList);
    }
}
