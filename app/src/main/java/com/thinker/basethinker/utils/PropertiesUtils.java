package com.thinker.basethinker.utils;

import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangjinyi on 2017/3/24.
 */

public class PropertiesUtils {

    private static <T> Object getObjProperty(String fieldName, T apiModel)
    {
        try {
            Field field = apiModel.getClass().getDeclaredField(fieldName);
            if (field != null)
            {
                fieldName = fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
                Object result = apiModel.getClass().getMethod("get"+fieldName).invoke(apiModel);
                return result;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static <T>  List<T> copyBeanListProperties(List<? extends Object> sourceList, Class<T> targetClazz)
    {
        if (sourceList == null || targetClazz == null || sourceList.size() < 1)
            return null;

        List<T> targetList = new ArrayList<>();

        for (Object object: sourceList)
        {
            T t = copyBeanProperties(object, targetClazz);
            targetList.add(t);
        }
        return targetList;
    }

    public static <T>  T copyBeanProperties(Object source, Class<T> targetClazz)
    {
        if (source == null || targetClazz == null)
            return null;

        Gson gson = new Gson();
        String sourceString = gson.toJson(source);
        T t = gson.fromJson(sourceString, targetClazz);
        return t;
    }
}
