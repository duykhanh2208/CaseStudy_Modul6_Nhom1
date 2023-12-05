package com.houserenting.service;

import java.util.Optional;

public interface GeneralService<E>{
    Iterable<E> showAll();
    Optional<E> findOne(Long id);
    E save(E e);
    Long delete(Long id);
}
