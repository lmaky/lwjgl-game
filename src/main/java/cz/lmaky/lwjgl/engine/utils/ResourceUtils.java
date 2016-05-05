package cz.lmaky.lwjgl.engine.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class ResourceUtils {

    private ResourceUtils() {
    }

    /**
     * Reads the specified resource and returns the raw data as a ByteBuffer.
     * @param resource the resource to read
     * @return the resource data
     * @throws IOException if an IO error occurs
     */
    public static ByteBuffer getResourceAsByteBuffer(String resource) throws IOException {
        ByteBuffer buffer = null;
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource)) {
            if (is != null) {
                byte[] bytes = IOUtils.toByteArray(is);
                buffer = ByteBuffer.allocateDirect(bytes.length);
                buffer.put(bytes);
                buffer.flip();
            }
        }
        if (buffer == null) {
            throw new IOException("Data is null.");
        }
        return buffer;
    }

    /**
     * Reads the specified resource and returns the raw data as String.
     * @param resource
     * @return the resource data
     * @throws IOException if an IO error occurs
     */
    public static String getResourceAsString(String resource) {
        String buffer = null;
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource)) {
            if (is != null) {
                buffer = IOUtils.toString(is);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Error while loading resource -" + resource + ".", e);
        }
        return buffer;
    }
}
