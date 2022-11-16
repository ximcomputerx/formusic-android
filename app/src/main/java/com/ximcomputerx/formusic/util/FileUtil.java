package com.ximcomputerx.formusic.util;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {

    private static final String TAG = "FileUtil";

    public static void writeText(@NonNull File file, String text) {
        writeBytes(file, text.getBytes(), false);
    }

    public static void writeText(@NonNull File file, String text, boolean append) {
        writeBytes(file, text.getBytes(), append);
    }

    public static void writeBytes(@NonNull File file, byte[] bytes) {
        writeBytes(file, bytes, false);
    }

    public static void writeBytes(@NonNull File file, byte[] bytes, boolean append) {
        try{
            file.createNewFile();
        } catch (Exception e) {
            Log.i(TAG, "Cannot write to file " + file.getName());
        }

        if (file.canWrite()) {
            FileOutputStream stream = null;
            try {
                stream = new FileOutputStream(file, append);
                stream.write(bytes);
            } catch (IOException e) {
                Log.w(TAG, "Error writing to file " + file.getName());
            }
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException ignored) {
            }
        } else {
            Log.i(TAG, "Cannot write to file " + file.getName());
        }
    }

    public static String readFileText(@NonNull File file) {
        return new String(readFileBytes(file));
    }

    public static byte[] readFileBytes(@NonNull File file) {
        if (file.canRead()) {
            int length = (int) file.length();
            byte[] bytes = new byte[length];
            FileInputStream stream = null;
            try {
                stream = new FileInputStream(file);
                stream.read(bytes);
                return bytes;
            } catch (IOException e) {
                Log.w(TAG, "Error reading from file " + file.getName());
            }
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException ignored) {
            }
        } else {
            Log.w(TAG, "Cannot read file " + file.getName());
        }
        return new byte[0];
    }

    public static byte[] readStreamBytes(InputStream inputStream) {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int length;
        try {
            while ((length = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, length);
            }
        } catch (IOException e) {
            Log.w(TAG, "Error while reading from input stream");
        }
        try {
            byteBuffer.close();
        } catch (IOException ignored) {
        }
        return byteBuffer.toByteArray();
    }

    public static void createFile(@NonNull File file) {
        if (!file.exists()) {
            try {
                boolean created = file.createNewFile();
                if (created) {
                    Log.i(TAG, "File " + file.getName() + " created successfully");
                }
            } catch (IOException e) {
                Log.w(TAG, "Failed creating file " + file.getName());
            }
        } else {
            Log.w(TAG, "File " + file.getName() + " already exists");
        }
    }

    public static void deleteFile(@NonNull File file) {
        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                Log.i(TAG, "File " + file.getName() + " deleted successfully");
            }
        } else {
            Log.i(TAG, "File " + file.getName() + " does not exist");
        }
    }

    public static void copyFile(@NonNull File source, @NonNull File destination) {
        if (!source.canRead()) {
            Log.w(TAG, "Cannot read from file " + source.getName());
            return;
        }
        createFile(destination);
        if (!destination.canWrite()) {
            Log.w(TAG, "Cannot write to file " + destination.getName());
            return;
        }

        try {
            InputStream input = new FileInputStream(source);
            OutputStream output = new FileOutputStream(destination);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } catch (IOException e) {
            Log.w(TAG, "Error while copying from file " + source.getName());
        }
    }
}