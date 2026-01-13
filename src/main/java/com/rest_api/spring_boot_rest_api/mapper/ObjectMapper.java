package com.rest_api.spring_boot_rest_api.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

public class ObjectMapper {

    private static final Mapper mapper = DozerBeanMapperBuilder.buildDefault();

//    O -> origin object || D -> destination object || Data conversion of two values. ( Dto <-> Entity )
    public static <O, D> D parseObject(O originObject, Class<D> destinationObject){
        return mapper.map(originObject, destinationObject);
    }

    public static <O, D> List<D> parseListObjects(List<O> originObject, Class<D> destinationObject){

        List<D> destinationObjects = new ArrayList<D>();
        for (Object o : originObject){
            destinationObjects.add(mapper.map(o, destinationObject));
        }

        return destinationObjects;
    }
}
