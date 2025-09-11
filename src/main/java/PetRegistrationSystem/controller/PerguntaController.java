package PetRegistrationSystem.controller;

import PetRegistrationSystem.model.Pergunta;
import PetRegistrationSystem.service.PerguntaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perguntas")
public class PerguntaController {
    private final PerguntaService service;

    public PerguntaController(PerguntaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Pergunta> criarPergunta(@Valid @RequestBody Pergunta pergunta) {
        return ResponseEntity.ok(service.salvarPergunta(pergunta));
    }

    @GetMapping
    public ResponseEntity<List<Pergunta>> listarPerguntas() {
        return ResponseEntity.ok(service.listarPerguntas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pergunta> buscarPergunta(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pergunta> atualizarPergunta(@PathVariable Long id, @RequestBody Pergunta pergunta) {
        return ResponseEntity.ok(service.atualizarPergunta(id, pergunta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPergunta(@PathVariable Long id) {
        service.deletarPergunta(id);
        return ResponseEntity.noContent().build();
    }
}
