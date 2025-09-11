package PetRegistrationSystem.service;

import PetRegistrationSystem.exception.ResourceNotFoundException;
import PetRegistrationSystem.model.Pergunta;
import PetRegistrationSystem.repository.PerguntaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerguntaService {
    private final PerguntaRepository repository;

    public PerguntaService(PerguntaRepository repository) {
        this.repository = repository;
    }

    public Pergunta salvarPergunta(Pergunta pergunta) {
        return repository.save(pergunta);
    }

    public List<Pergunta> listarPerguntas() {
        return repository.findAll();
    }

    public Pergunta buscarId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pergunta não encontrada"));
    }

    public Pergunta atualizarPergunta(Long id, Pergunta nova) {
        Pergunta pergunta = buscarId(id);

        if (nova.getPergunta() != null) {
            pergunta.setPergunta(nova.getPergunta());
        }
        return repository.save(pergunta);
    }

    public void deletarPergunta(Long id) {
        repository.deleteById(id);
    }
}
