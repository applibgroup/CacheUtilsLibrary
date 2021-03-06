package com.lifeofcoding.cacheutlislibrary;

import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ohos.app.Context;
import ohos.agp.render.render3d.BuildConfig;

/**
 * Created by Wesley Lin on 9/5/15.
 */

public class CacheUtils {

    private static final String ENCODING = "utf8";
    private static final String FILE_SUFFIX = ".txt";
    private static final String TAG = "CACHE_UTILS";
    private static final String FAIL_READ_MSG = "failed to read json";
    private static final String FAIL_WRITE_MSG = "failed to write json";
    public static String BASE_CACHE_PATH;

    private CacheUtils() {
    }

    /**
     * Method to configure cache.
     *
     * @param context Context instance
     */
    public static void configureCache(Context context) {
        BASE_CACHE_PATH = context.getCacheDir().getPath()
                + File.separator + "files" + File.separator + "CacheUtils";
        File fileCacheDir = new File(BASE_CACHE_PATH);
        boolean cacheDirCreated = fileCacheDir.mkdirs();

        if (BuildConfig.DEBUG) {
            LogUtil.debug(TAG,  cacheDirCreated ? BASE_CACHE_PATH + " created." : BASE_CACHE_PATH + " NOT created.");
        }
    }

    /*
     * Returns path for cache entry
     * @param name
     * @return
     */
    private static String pathForCacheEntry(String name) {
        return BASE_CACHE_PATH + File.separator + name + FILE_SUFFIX;
    }

    /*
     * Returns List of data from JSON
     *
     * @param dataString
     * @param <T>
     * @return
     **/
    private static <T> List<Map<String, T>> dataMapsFromJson(String dataString) {
        if (TextUtils.isEmpty(dataString)) {
            return new ArrayList<>();
        }
        try {
            Type listType = new TypeToken<List<Map<String, T>>>() {
            }.getType();
            return GsonHelper.buildGson().fromJson(dataString, listType);
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                LogUtil.debug(TAG, FAIL_READ_MSG + e.toString());
            }
            return new ArrayList<>();
        }
    }

    /*
     * Returns JSON from list data
     * @param dataMaps
     * @param <T>
     * @return
     */
    private static <T> String dataMapsToJson(List<Map<String, T>> dataMaps) {
        try {
            return GsonHelper.buildGson().toJson(dataMaps);
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                LogUtil.debug(TAG, FAIL_READ_MSG + e.toString());
            }
            return "[]";
        }
    }

    /**
     * Method to read file.
     *
     * @param fileName the name of the file
     * @return the content of the file, null if there is no such file
     */
    public static String readFile(String fileName) {
        try {
            return IOUtils.toString(new FileInputStream(pathForCacheEntry(fileName)), ENCODING);
        } catch (IOException e) {
            if (BuildConfig.DEBUG) {
                LogUtil.debug(TAG, "read cache file failure" + e.toString());
            }
            return null;
        }
    }

    /**
     * Method to write file content in the specified file.
     *
     * @param fileName    the name of the file
     * @param fileContent you want to store
     */
    public static void writeFile(String fileName, String fileContent) {
        try {
            IOUtils.write(fileContent, new FileOutputStream(pathForCacheEntry(fileName)), ENCODING);
        } catch (IOException e) {
            if (BuildConfig.DEBUG) {
                LogUtil.debug(TAG, "write cache file failure" + e.toString());
            }
        }
    }

    /*
     * @param fileName the name of the file
     * @param dataMaps the map list you want to store
     */
    public static <T> void writeDataMapsFile(String fileName, List<Map<String, T>> dataMaps) {
        writeFile(fileName, dataMapsToJson(dataMaps));
    }

    /*
     * @param fileName the name of the file
     * @return the map list you previous stored, an empty {@link List} will be returned if there is no such file
     */
    public static <T> List<Map<String, T>> readDataMapsFile(String fileName) {
        return dataMapsFromJson(readFile(fileName));
    }

    private static <T> T objectFromJson(String dataString, Type t) {
        try {
            return GsonHelper.buildGson().fromJson(dataString, t);
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                LogUtil.info(TAG, FAIL_READ_MSG + e.toString());
            }
            return null;
        }
    }

    private static <T> String objectToJson(T o) {
        try {
            return GsonHelper.buildGson().toJson(o);
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                LogUtil.info(TAG, FAIL_WRITE_MSG + e.toString());
            }
            return null;
        }
    }

    /*
     * @param fileName the name of the file
     * @param object the object you want to store
     * @param <T> a class extends from {@link Object}
     */
    public static <T> void writeObjectFile(String fileName, T object) {
        writeFile(fileName, objectToJson(object));
    }

    /**
     * Method to return the Objects previously stored.
     *
     * @param fileName the name of the file
     * @param t the type of the object you previous stored
     *
     * @return the {@link T} type object you previous stored
     */
    public static <T> T readObjectFile(String fileName, Type t) {
        return objectFromJson(readFile(fileName), t);
    }

    private static <T> Map<String, T> dataMapFromJson(String dataString) {
        if (TextUtils.isEmpty(dataString)) {
            return new HashMap<>();
        }
        try {
            Type t = new TypeToken<Map<String, T>>() {
            }.getType();
            return GsonHelper.buildGson().fromJson(dataString, t);
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                LogUtil.info(TAG, FAIL_READ_MSG + e.toString());
            }
            return new HashMap<>();
        }
    }

    private static <T> String dataMapToJson(Map<String, T> dataMap) {
        try {
            return GsonHelper.buildGson().toJson(dataMap);
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                LogUtil.info(TAG, FAIL_WRITE_MSG + e.toString());
            }
            return "{}";
        }
    }

    /*
     * @param fileName the name of the file
     * @param dataMap the map data you want to store
     */
    public static <T> void writeDataMapFile(String fileName, Map<String, T> dataMap) {
        writeFile(fileName, dataMapToJson(dataMap));
    }

    /*
     * @param fileName the name of the file
     * @return the map data you previous stored
     */
    public static <T> Map<String, T> readDataMapFile(String fileName) {
        return dataMapFromJson(readFile(fileName));
    }

    /*
     * delete the file with fileName
     * @param fileName the name of the file
     **/
    public static void deleteFile(String fileName) {
        FileUtils.deleteQuietly(new File(pathForCacheEntry(fileName)));
    }

    /*
     * check if there is a cache file with fileName
     * @param fileName the name of the file
     * @return true if the file exits, false otherwise
     **/
    public static boolean hasCache(String fileName) {
        return new File(pathForCacheEntry(fileName)).exists();
    }
}
