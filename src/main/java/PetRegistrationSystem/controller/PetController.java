package PetRegistrationSystem.controller;

import PetRegistrationSystem.model.Pet;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import PetRegistrationSystem.service.PetService;
import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {
    private final PetService service;

    public PetController(PetService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Pet> criarPet(@Valid @RequestBody Pet pet) {
        return ResponseEntity.ok(service.salvarPet(pet));
    }

    @GetMapping
    public ResponseEntity<List<Pet>> listarPets() {
        return ResponseEntity.ok(service.listarPets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> buscarPet(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pet> atualizarPet(@PathVariable Long id,@Valid @RequestBody Pet pet) {
        return ResponseEntity.ok(service.atualizarPet(id, pet));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPet(@PathVariable Long id) {
        service.deletarPet(id);
        return ResponseEntity.noContent().build();
    }
}
