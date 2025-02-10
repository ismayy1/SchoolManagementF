package com.techproed.service.helper;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PageableHelper {

    public Pageable getPageable(int page, int size, String sort, String type) {

        if (Objects.equals(type, "asc")) {
            return PageRequest.of(page, size, Sort.by(sort).ascending());
        } else {
            return PageRequest.of(page, size, Sort.by(sort).descending());
        }

    }
}
