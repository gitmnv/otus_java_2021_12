package ru.otus.web.views;

import lombok.Builder;
import lombok.Data;
import ru.otus.model.Address;

import java.util.List;

@Data
@Builder
public class ClientView {
    private Long id;
    private Address address;
    private List<PhoneView> phones;
}
