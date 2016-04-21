package com.lib.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * application.properties属性获取工具类
 */
public final class ApplicationUtil {
    private static final String REGION;
    private static final String APPLICATION_PATH;
    private static final Logger logger = Logger.getLogger(ApplicationUtil.class);
    private static Properties bundle = new Properties();// 防止异常空取。
    static {
        String dir = System.getProperty("conf.dir");
        if (!dir.endsWith("/")) {
            dir += "/";
        }
        String region = null;
        try {
            region = PropertiesUtil.getInstance().getPropertiesByFilePath(dir + "region.properties")
                    .getProperty("region");
        } catch (Exception e) {
        }
        if (region == null) {
            region = "cn";
        }
        REGION = region;
        dir = dir + "application.properties";
        APPLICATION_PATH = dir;
//        load();
        loadClassPath(region);
    }

    /**
     * 根据wcode返回region
     * @param wcode
     * @return
     */
    public static String getRegion(String wcode){
        if(StringUtils.isNotBlank(wcode)){
            return wcode;
        }
        return REGION;
    }
    
    /**
     * 根据key获取value
     * @param key
     * @return
     */
    public static final String get(String key, Map<String, Object> params) {
        String value = bundle.getProperty(key);
        if (value != null && params != null) {
            for (String k : params.keySet()) {
                Object v = params.get(k);
                if (v != null) {
                    value = value.replaceAll("\\{" + k + "\\}", v + "");
                }
            }
        }
        return value;
    }

    /**
     * 根据key获取value,整型
     * @param key
     * @return
     */
    public static final Integer getInt(String key) {
        String value = bundle.getProperty(key);
        Integer v = null;
        if (value != null) {
            try {
                v = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                logger.warn("ApllicationUtils.getInt error.key:" + key);
            }
        }
        return v;
    }

    /**
     * 根据key获取value,整型
     * @param key
     * @return
     */
    public static final Boolean getBoolean(String key) {
        String value = bundle.getProperty(key);
        Boolean v = null;
        if (value != null) {
            try {
                v = Boolean.parseBoolean(value);
            } catch (NumberFormatException e) {
                logger.warn("ApllicationUtils.getBoolean error.key:" + key);
            }
        }
        return v;
    }

    /**
     * 根据key获取value
     * @param key
     * @return
     */
    public static final String get(String key) {
        String value = bundle.getProperty(key);
        return value;
    }

    /**
     * 根据key获取value，如果为null，则返回defaultValue
     * @param key
     * @param defaultValue
     * @return
     */
    public static final String get(String key, String defaultValue) {
        String value = bundle.getProperty(key);
        return value != null ? value : defaultValue;
    }

    public static final Properties getProperties() {
        return bundle;
    }

    /**
     * 加载属性文件，到内存
     */
    private static void load() {
        Properties tmp = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(APPLICATION_PATH);
            tmp.load(inputStream);
            bundle = tmp;
        } catch (Exception e) {
            throw new RuntimeException(APPLICATION_PATH + " is not found!", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                }
            }
        }
    }
    private static void loadClassPath(String region) {
        try {
            bundle = PropertiesUtil.getInstance().getPropertiesByClassPath(
                    "/conf/" + region + "/application.properties");
        } catch (Exception e) {
            throw new RuntimeException(APPLICATION_PATH + " is not found!", e);
        }
    }

    /**
     * 刷新内存数据，重新加载
     */
    public static final boolean reload() {
        try {
            load();
        } catch (Exception e) {
            logger.error(" reload application.properties error.", e);
            return false;
        }
        return true;
    }
}
