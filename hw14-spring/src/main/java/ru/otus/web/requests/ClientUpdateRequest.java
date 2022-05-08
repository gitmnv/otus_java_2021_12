package ru.otus.web.requests;

import lombok.Data;
import ru.otus.model.Address;
import ru.otus.model.Phone;

import java.util.List;

@Data
public class ClientUpdateRequest {

    private Long id;
    private String name;
    private Address address;
    private List<Phone> phones;
}
