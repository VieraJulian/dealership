package com.example.clients.infra.inputAdapter;

import com.example.clients.infra.dto.ClientCarDTO;
import com.example.clients.infra.dto.ClientDTO;
import com.example.clients.infra.inputport.IClientInputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    @Autowired
    private IClientInputPort clientInputPort;

    @GetMapping("/{id}")
    public ResponseEntity<ClientCarDTO> getClientById(@PathVariable Long id){
        try {
            ClientCarDTO clientCarDTO = clientInputPort.getClientById(id);

            return new ResponseEntity<>(clientCarDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClientDTO>> getClientList(@RequestParam int page, @RequestParam int size){
        try {
            List<ClientDTO> clientList = clientInputPort.getClientList(page, size);

            return new ResponseEntity<>(clientList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO client){
        try {
            ClientDTO clientDTO = clientInputPort.createClient(client);

            return new ResponseEntity<>(clientDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ClientDTO> editClient(@PathVariable Long id, @RequestBody ClientDTO client){
        try {
            ClientDTO clientDTO = clientInputPort.editClient(id, client);

            return new ResponseEntity<>(clientDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/buyCar/{clientId}/{carId}")
    public ResponseEntity<ClientCarDTO> buyCar(@PathVariable Long clientId, @PathVariable Long carId){
        try {
            ClientCarDTO clientCarDTO = clientInputPort.buyCar(clientId, carId);

            return new ResponseEntity<>(clientCarDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> editClient(@PathVariable Long id){
        try {
            String msj = clientInputPort.deleteClient(id);

            return new ResponseEntity<>(msj, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
