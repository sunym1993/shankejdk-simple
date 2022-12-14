/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
 */

package sun.java2d.cmm;

public class ProfileDataVerifier {
    /**
     * Throws an IllegalArgumentException if the data does not correspond
     * to a valid ICC Profile.
     *
     * @param data the specified profile data.
     */
    public static void verify(byte[] data) {
        if (data == null) {
            throw new IllegalArgumentException("Invalid ICC Profile Data");
        }

        if (data.length < TOC_OFFSET) {
            // not enough data for profile header
            throw new IllegalArgumentException("Invalid ICC Profile Data");
        }

        // check profile size
        final int size = readInt32(data, 0);
        final int tagCount = readInt32(data, HEADER_SIZE);

        if (tagCount < 0 || tagCount > MAX_TAG_COUNT) {
            throw new IllegalArgumentException("Invalid ICC Profile Data");
        }

        if (size < (TOC_OFFSET + (tagCount * TOC_RECORD_SIZE)) ||
            size > data.length)
        {
            throw new IllegalArgumentException("Invalid ICC Profile Data");
        }

        final int sig = readInt32(data, 36);

        if (PROFILE_FILE_SIGNATURE != sig) {
            throw new IllegalArgumentException("Invalid ICC Profile Data");
        }

        // verify table of content
        for (int i = 0; i < tagCount; i++) {
            final int tag_offset = getTagOffset(i, data);
            final int tag_size = getTagSize(i, data);

            if (tag_offset < TOC_OFFSET || tag_offset > size) {
                throw new IllegalArgumentException("Invalid ICC Profile Data");
            }

            if (tag_size < 0 ||
                tag_size > (Integer.MAX_VALUE - tag_offset) ||
                tag_size + tag_offset > size)
            {
                throw new IllegalArgumentException("Invalid ICC Profile Data");
            }
        }
    }

    private static int getTagOffset(int idx, byte[] data) {
        final int pos = TOC_OFFSET + idx * TOC_RECORD_SIZE + 4;
        return readInt32(data, pos);
    }

    private static int getTagSize(int idx, byte[] data) {
        final int pos = TOC_OFFSET + idx * TOC_RECORD_SIZE + 8;
        return readInt32(data, pos);
    }

    private static int readInt32(byte[] data, int off) {
        int res = 0;
        for (int i = 0; i < 4; i++) {
            res = res << 8;

            res |= (0xff & data[off++]);
        }
        return res;
    }

    /**
     * Lcms limit for the number of tags: 100
     * Kcms limit for the number of tags: N/A
     */
    private static final int MAX_TAG_COUNT = 100;

    private static final int HEADER_SIZE = 128;
    private static final int TOC_OFFSET = HEADER_SIZE + 4;
    private static final int TOC_RECORD_SIZE = 12;

    private static final int PROFILE_FILE_SIGNATURE = 0x61637370;
}
