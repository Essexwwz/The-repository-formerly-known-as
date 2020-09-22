/*
  (c) Copyright 2018, 2019 Phasmid Software
 */
package edu.neu.coe.huskySort.sort.huskySortUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.LongBuffer;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Date;

/**
 * Factory class for HuskyCoders.
 */
public final class HuskyCoderFactory {

    private static final int BITS_LONG = 64;

    private static final int BIT_WIDTH_ASCII = 7;
    private static final int MAX_LENGTH_ASCII = BITS_LONG / BIT_WIDTH_ASCII;
    private static final int MASK_ASCII = 0x7F;

    private static final int BIT_WIDTH_ENGLISH = 6;
    private static final int MAX_LENGTH_ENGLISH = BITS_LONG / BIT_WIDTH_ENGLISH;
    private static final int MASK_ENGLISH = 0x3F;

    private static final int BIT_WIDTH_UNICODE = 16;
    private static final int MAX_LENGTH_UNICODE = BITS_LONG / BIT_WIDTH_UNICODE;
    private static final int MASK_UNICODE = 0xFFFF;

    private static final int BIT_WIDTH_UTF8 = 8;
    private static final int MAX_LENGTH_UTF8 = BITS_LONG / BIT_WIDTH_UTF8;
    private static final int MASK_UTF8 = 0xFF;

    /**
     * Method to create a generic HuskyCoder for a class which is HuskySortable.
     *
     * @param <X> a class which is HuskySortable.
     * @return a HuskyCoder&lt;X&gt;.
     */
    public static <X extends HuskySortable<X>> HuskyCoder<X> createGenericCoder() {
        return HuskySortable::huskyCode;
    }

    /**
     * A Husky Coder for ASCII Strings.
     * <p>
     * This should work correctly for all 7-bit ASCII characters including all English letters (upper and lower case),
     * as well as the following all punctuation.
     * Additionally, many ASCII codes (non-printing) are included.
     * <p>
     * But, in any case, we are only optimizing for printable ascii characters here.
     * If the long encoding is off for some reason (like there's a number embedded in the name),
     * it's no big deal.
     * It just means that the final pass will have to work a bit harder to fix the extra inversion.
     */
    public final static HuskySequenceCoder<String> asciiCoder = new BaseHuskySequenceCoder<String>("ASCII", MAX_LENGTH_ASCII) {


        /**
         * Encode x as a long.
         * As much as possible, if x > y, huskyEncode(x) > huskyEncode(y).
         * If this cannot be guaranteed, then the result of imperfect(z) will be true.
         *
         * @param str the X value to encode.
         * @return a long which is, as closely as possible, monotonically increasing with the domain of X values.
         */
        public long huskyEncode(final String str) {
            return asciiToLong(str);
        }

    };

    /**
     * A Husky Coder for English Strings.
     * <p>
     * This should work correctly for all 52 English characters (upper and lower case),
     * as well as the following 11 characters: @ [ \ ] ^ _ ` { | } ~
     * <p>
     * But, in any case, we are only optimizing for printable ascii characters here.
     * If the long encoding is off for some reason (like there's a number embedded in the name),
     * it's no big deal.
     * It just means that the final pass will have to work a bit harder to fix the extra inversion.
     */
    public final static HuskySequenceCoder<String> englishCoder = new BaseHuskySequenceCoder<String>("English", MAX_LENGTH_ENGLISH) {


        public long huskyEncode(final String str) {
            return englishToLong(str);
        }
    };

    /**
     * A Husky Coder for unicode Strings.
     */
    public final static HuskySequenceCoder<String> unicodeCoder = new BaseHuskySequenceCoder<String>("Unicode", MAX_LENGTH_UNICODE-1) {


        // TEST
        @Override
        public long huskyEncode(final String str) {
            return unicodeToLong(str);
        }
    };

    /**
     * A Husky Coder for UTF Strings.
     */
    public final static HuskySequenceCoder<String> utf8Coder = new BaseHuskySequenceCoder<String>("UTF8", 0) {


        // TEST
        @Override
        public long huskyEncode(final String str) {
            return utf8ToLong(str);
        }
    };

    /**
     * A Husky Coder for Dates.
     */
    public final static HuskyCoder<Date> dateCoder = new HuskyCoder<Date>() {
        @Override
        public long huskyEncode(final Date date) {
            return date.getTime();
        }

        /**
         * Method to determine if this Husky Coder is perfect for all Dates.
         *
         * @return true.
         */
        @Override
        public boolean perfect() {
            return true;
        }
    };

    /**
     * A Husky Coder for ChronoLocalDateTimes.
     */
    public final static HuskyCoder<ChronoLocalDateTime<?>> chronoLocalDateTimeCoder = new HuskyCoder<ChronoLocalDateTime<?>>() {
        @Override
        public long huskyEncode(final ChronoLocalDateTime<?> x) {
            return x.toEpochSecond(ZoneOffset.UTC);
        }

        /**
         * Method to determine if this Husky Coder is perfect for all ChronoLocalDateTimes.
         *
         * @return true.
         */
        @Override
        public boolean perfect() {
            return true;
        }
    };

    /**
     * A Husky Coder for Doubles.
     */
    public final static HuskyCoder<Double> doubleCoder = HuskyCoderFactory::doubleToLong;

    /**
     * A Husky Coder for Integers.
     */
    public final static HuskyCoder<Integer> integerCoder = new HuskyCoder<Integer>() {
        @Override
        public long huskyEncode(final Integer x) {
            return x.longValue();
        }

        /**
         * Method to determine if this Husky Coder is perfect for a class of objects (Integer).
         *
         * @return true.
         */
        @Override
        public boolean perfect() {
            return true;
        }
    };

    /**
     * A Husky Coder for Longs.
     */
    public final static HuskyCoder<Long> longCoder = new HuskyCoder<Long>() {
        @Override
        public long huskyEncode(final Long x) {
            return x;
        }

        /**
         * Method to determine if this Husky Coder is perfect for a class of objects (Long).
         *
         * @return true.
         */
        @Override
        public boolean perfect() {
            return true;
        }
    };

    /**
     * A Husky Coder for BigIntegers.
     */
    public final static HuskyCoder<BigInteger> bigIntegerCoder = x -> doubleToLong(x.doubleValue());

    /**
     * A Husky Coder for BigDecimals.
     */
    public final static HuskyCoder<BigDecimal> bigDecimalCoder = BigDecimal::longValue;

    /**
     * A Husky Coder for scaled BigDecimals.
     * NOTE: use this if you know that your range of BigDecimals is particularly large or small.
     *
     * @param scale the power of ten by which each BigDecimal will be increased before conversion to long.
     *              Thus, if you have say numbers in the range 0 to 1, you might want to choose a scale of 18.
     *              Alternatively, if your numbers are in the range -1E100 through 1aE100, you should choose a scale of -82.
     * @return a HuskyCoder&lt;BigDecimal@gt;
     */
    public static HuskyCoder<BigDecimal> scaledBigDecimalCoder(final int scale) {
        return x -> x.movePointRight(scale).longValue();
    }

    // CONSIDER making this private
    public static long asciiToLong(final String str) {
        return stringToLong(str, MAX_LENGTH_ASCII, BIT_WIDTH_ASCII, MASK_ASCII);
    }

    static long utf8ToLong(final String str) {
        // TODO Need to test that the mask value is correct. I think it might not be.
        return longArrayToLong(toUTF8Array(str), MAX_LENGTH_UTF8, BIT_WIDTH_UTF8, MASK_UTF8) >>> 1;
    }

    private static long unicodeToLong(final String str) {
        return stringToLong(str, MAX_LENGTH_UNICODE, BIT_WIDTH_UNICODE, MASK_UNICODE) >>> 1;
    }

    private static long stringToLong(final String str, final int maxLength, final int bitWidth, final int mask) {
        final int length = Math.min(str.length(), maxLength);
        final int padding = maxLength - length;
        long result = 0L;
        if (((mask ^ 0xFFFF) & 0xFFFF) == 0)
            for (int i = 0; i < length; i++) result = result << bitWidth | str.charAt(i);
        else
            for (int i = 0; i < length; i++) result = result << bitWidth | str.charAt(i) & mask;

        result = result << bitWidth * padding;
        return result;
    }

    private static long englishToLong(final String str) {
        return stringToLong(str, MAX_LENGTH_ENGLISH, BIT_WIDTH_ENGLISH, MASK_ENGLISH);
    }

    @SuppressWarnings("SameParameterValue")
    private static long longArrayToLong(final long[] xs, final int maxLength, final int bitWidth, final int mask) {
        final int length = Math.min(xs.length, maxLength);
        long result = 0;
        if (((~mask)) == 0)
            for (int i = 0; i < length; i++) result = result << bitWidth | xs[i];
        else
            for (int i = 0; i < length; i++) result = result << bitWidth | xs[i] & mask;
        result = result << (bitWidth * (maxLength - length));
        return result;
    }

    private static long[] toUTF8Array(final String str) {
        final int length = str.length();
        final LongBuffer byteBuffer = LongBuffer.allocate(length << 2);
        int count = 0;
        final char[] codes = str.toCharArray();
        for (int i = 0; i < length; i++) {
            final char code = codes[i];
            if (code < 0x80) {
                count++;
                byteBuffer.put(code);
            } else if (code < 0x800) {
                count += 2;
                byteBuffer.put(0xC0 | (code >> 6));
                byteBuffer.put(0x80 | (code & 0x3F));
            } else if (code < 0xD800 || code >= 0xE000) {
                count += 3;
                byteBuffer.put(0xE0 | (code >> 12));
                byteBuffer.put(0x80 | ((code >> 6) & 0x3F));
                byteBuffer.put(0x80 | (code & 0x3F));
            } else {
                i++;
                final int tempCode = 0x10000 + (((code & 0x3FF) << 10) | codes[i] & 0x3FF);
                count += 4;
                byteBuffer.put(0xF0 | (tempCode >> 18));
                byteBuffer.put(0x80 | ((tempCode >> 12) & 0x3F));
                byteBuffer.put(0x80 | ((tempCode >> 6) & 0x3F));
                byteBuffer.put(0x80 | (tempCode & 0x3F));
            }
        }
        final long[] result = new long[count];
        byteBuffer.rewind();
        byteBuffer.get(result);
        return result;
    }

    /**
     * This method is required because doubleToLongBits does not increase monotonically with its input value.
     *
     * @param value a double.
     * @return an appropriate long value.
     */
    private static long doubleToLong(final double value) {
        final long doubleToLongBits = Double.doubleToLongBits(value);
        final long sign = doubleToLongBits & 0x8000000000000000L;
        final long result = doubleToLongBits & 0x7FFFFFFFFFFFFFFFL;
        return sign == 0 ? result : -result;
    }



}
