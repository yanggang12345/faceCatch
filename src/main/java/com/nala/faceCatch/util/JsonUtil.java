package com.nala.faceCatch.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author: heshangqiu
 * Created Date:  2018/3/14
 * <p>
 * 简单封装Jackson，实现JSON String<->Java Object的Mapper.
 * <p/>
 * 封装不同的输出风格, 使用不同的builder函数创建实例.
 */
public class JsonUtil {

    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    /**
     * 日期格式化为 yyyy-MM-dd HH:mm:ss
     * 创建只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper
     * 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
     */
    private static ObjectMapper mapper = new ObjectMapper().setDateFormat(new SimpleDateFormat(DateUtil.DATETIME_FORMAT))
            .setSerializationInclusion(Include.NON_EMPTY)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    /**
     * Object可以是POJO，也可以是Collection或数组。
     * 如果对象为Null, 返回"null".
     * 如果集合为空集合, 返回"[]".
     */
    public static String toJson(Object object) {

        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            logger.warn("write to json string error:" + object, e);
            return null;
        }
    }

    /**
     * 反序列化POJO或简单Collection如List<String>.
     * <p/>
     * 如果JSON字符串为Null或"null"字符串, 返回Null.
     * 如果JSON字符串为"[]", 返回空集合.
     * <p/>
     * 如需反序列化复杂Collection如List<MyBean>, 请使用fromJson(String, JavaType)
     *
     * @see #fromJson(String, JavaType)
     */
    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            logger.warn("parse json string error:" + jsonString, e);
            System.out.println(e);
            return null;
        }
    }

    /**
     * 反序列化复杂 Collection 如 List<Bean>
     *
     * @param jsonString      JSON 字符串
     * @param collectionClass 集合类型
     * @param elementClass    集合元素类型
     * @return
     */
    public static <T> T fromJson(String jsonString, Class<? extends Collection> collectionClass, Class<?> elementClass) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return (T) mapper.readValue(jsonString, contructCollectionType(collectionClass, elementClass));
        } catch (IOException e) {
            logger.warn("parse json string error:" + jsonString, e);
            System.out.print(e);
            return null;
        }
    }

    /**
     * 反序列化复杂 Map 如 Map<String,Bean>
     *
     * @param jsonString
     * @param mapClass
     * @param keyClass
     * @param valueClass
     * @return
     */
    public static <T> T fromJson(String jsonString, Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return (T) mapper.readValue(jsonString, contructMapType(mapClass, keyClass, valueClass));
        } catch (IOException e) {
            logger.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 反序列化复杂Collection如List<Bean>, 先使用contructCollectionType()或contructMapType()构造类型, 然后调用本函数.
     *
     * @see #contructCollectionType(Class, Class)
     */
    public static <T> T fromJson(String jsonString, JavaType javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return (T) mapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            logger.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 反序列化泛型的对象如Result<List<User>>, JsonUtil.fromJson(param, new TypeReference<DadaResult<List<DadaCityRepVO>>>(){})
     * @param jsonString
     * @param valueTypeRef
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String jsonString, TypeReference valueTypeRef){
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return (T) mapper.readValue(jsonString, valueTypeRef);
        } catch (IOException e) {
            logger.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 构造Collection类型.
     */
    public static JavaType contructCollectionType(Class<? extends Collection> collectionClass, Class<?> elementClass) {
        return mapper.getTypeFactory().constructCollectionType(collectionClass, elementClass);
    }

    /**
     * 构造Map类型.
     */
    public static JavaType contructMapType(Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) {
        return mapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
    }

    /**
     * 当JSON里只含有Bean的部分屬性時，更新一個已存在Bean，只覆蓋該部分的屬性.
     */
    public static void update(String jsonString, Object object) {
        try {
            mapper.readerForUpdating(object).readValue(jsonString);
        } catch (JsonProcessingException e) {
            logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
        } catch (IOException e) {
            logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
        }
    }

    /**
     * 輸出JSONP格式數據.
     */
    public static String toJsonP(String functionName, Object object) {
        return toJson(new JSONPObject(functionName, object));
    }

    /**
     * 設定是否使用Enum的toString函數來讀寫Enum,
     * 為False時時使用Enum的name()函數來讀寫Enum, 默認為False.
     * 注意本函數一定要在Mapper創建後, 所有的讀寫動作之前調用.
     */
    public void enableEnumUseToString() {
        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
    }



    /**
     * 取出Mapper做进一步的设置或使用其他序列化API.
     */
    public static ObjectMapper getMapper() {
        return mapper;
    }

    /**
     * 将 JSON 文件转为 Map
     *
     * @param file
     * @param <K>
     * @param <V>
     * @return
     * @throws IOException
     */
    public static <K, V> Map<K, V> file2map(File file) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Map<K, V> map = mapper.readValue(
                file,
                new TypeReference<Map<String, Object>>() {
                }
        );
        return map;
    }

    /**
     * 将 JSON 字符串转为 Map
     *
     * @param json
     * @param <K>
     * @param <V>
     * @return
     * @throws IOException
     */
    public static <K, V> Map<K, V> json2map(String json) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Map<K, V> map = mapper.readValue(
                json,
                new TypeReference<Map<String, Object>>() {
                }
        );
        return map;
    }


    /**
     * JSON 转 List
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> toList(String json, Class<T> clazz) {
        return JsonUtil.fromJson(json, List.class, clazz);
    }



}
