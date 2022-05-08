package ru.otus.web.controllrs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.model.Client;
import ru.otus.services.ClientService;
import ru.otus.web.mappers.WebMapper;
import ru.otus.web.requests.ClientCreateRequest;
import ru.otus.web.requests.ClientUpdateRequest;
import ru.otus.web.views.ClientView;

@RestController
@RequestMapping("/api/v1/client")
public class ClientController {
    @Autowired
    ClientService clientService;

    @Autowired
    WebMapper mapper;

    @PostMapping("/create")
    public ResponseEntity<ClientView> create(@RequestBody ClientCreateRequest request) {
        return ResponseEntity.ok(mapper.toView(clientService.createClient(Client.builder()
                .name(request.getName())
                .address(request.getAddress())
                .phones(request.getPhones())
                .build())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientView> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toView(clientService.getClientById(id)));
    }

    @PutMapping("/update")
    public ResponseEntity<ClientView> update(@RequestBody ClientUpdateRequest request) {
        return ResponseEntity.ok(mapper.toView(clientService.updateClient(Client.builder()
                .id(request.getId())
                .name(request.getName())
                .address(request.getAddress())
                .phones(request.getPhones())
                .build())));
    }

    @DeleteMapping("/{id}")
    public void removeClientById(@PathVariable Long id) {
        clientService.deleteClientById(id);
    }
}
