package org.drew.carcenter.utils;

import java.sql.Timestamp;
import java.util.Random;

/**
 * Utility class to hold functions that generatae random values
 */
public final class RandomUtils
{
    /**
     * Creates a random double within bounds
     * @param min the min value
     * @param max the max value
     * @return a random double
     */
    public static double randomDouble(double min, double max)
    {
        Random r = new Random();
        return (min + (max - min) * r.nextDouble());
    }

    /**
     * Creates a random timestamp within bounds
     * @param min the min value
     * @param max the max value
     * @return a random timestamp
     */
    public static Timestamp randomTimestamp(Timestamp min, Timestamp max)
    {
        long offset = min.getTime();
        long end = max.getTime();
        long diff = end - offset + 1;
        return new Timestamp(offset + (long)(Math.random() * diff));
    }
}
