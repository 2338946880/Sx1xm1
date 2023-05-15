package com.example.repofitutils.model;

import com.example.repofitutils.entity.ReporfitData;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @author:zhangyue
 * @date:2020/9/11
 */
public final class CustomGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;
    private Type type;
    CustomGsonResponseBodyConverter(Gson gson, Type _type, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
        this.type=_type;
    }

    @Override public T convert(ResponseBody value) throws IOException {

        String string = value.string();

        ReporfitData baseEntity=new Gson().fromJson(string,ReporfitData.class);
        if (baseEntity!=null&&baseEntity.getCode()==-1){
            return (T) baseEntity;
        }

        return gson.fromJson(string,type);

    }
}

