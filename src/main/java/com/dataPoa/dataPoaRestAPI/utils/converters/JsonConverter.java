package com.dataPoa.dataPoaRestAPI.utils.converters;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class JsonConverter<T> {

    /**
     * Converte o json retornado do serviço para uma {@link java.util.ArrayList}.
     *
     * @param jsonString json retornado do serviço
     * @param clazz entidade compatível com os campos do JSON
     * @return {@link java.util.ArrayList}
     * @throws IOException
     */
    public List<T> convertJsonToArrayList(String jsonString, Class clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        return objectMapper.readValue(jsonString,
                objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }
}
