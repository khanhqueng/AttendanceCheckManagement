package com.security.Jwt_service.mapper;

import java.util.List;

public interface GenericMapper<E,I,O> {
    E requestToEntity (I request);

    O entityToResponse(E entity);

    List<E> requestToEntity(List<I> requests);

    List<O> entityToResponse(List<E> entities);
}
