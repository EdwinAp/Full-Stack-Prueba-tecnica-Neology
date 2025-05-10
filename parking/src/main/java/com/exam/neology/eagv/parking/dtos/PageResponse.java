package com.exam.neology.eagv.parking.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PageResponse<T> implements Serializable {

    private List<T> content;
    private Integer currentPage;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;

}
