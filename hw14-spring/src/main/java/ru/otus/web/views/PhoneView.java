package ru.otus.web.views;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PhoneView {

    private Long id;
    private String number;
}
