package ru.otus.web.mappers;

import org.springframework.stereotype.Component;
import ru.otus.model.Client;
import ru.otus.model.Phone;
import ru.otus.web.views.ClientView;
import ru.otus.web.views.PhoneView;

import java.util.ArrayList;
import java.util.List;

@Component
public class WebMapper {
    public ClientView toView(Client client){
        return ClientView.builder()
                .id(client.getId())
                .address(client.getAddress())
                .phones(phoneToView(client.getPhones()))
                .build();
    }
    private List<PhoneView> phoneToView(List<Phone> phones){
        List<PhoneView> views = new ArrayList<>();
        phones.forEach(v -> views.add(PhoneView.builder()
                .id(v.getId())
                .number(v.getNumber())
                .build()));
        return views;
    }
}
