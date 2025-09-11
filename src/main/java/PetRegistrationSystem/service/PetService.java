package PetRegistrationSystem.service;

import PetRegistrationSystem.exception.ResourceNotFoundException;
import PetRegistrationSystem.model.Pet;
import PetRegistrationSystem.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
    private final PetRepository repository;

    public PetService(PetRepository repository) {
        this.repository = repository;
    }

    public Pet salvarPet(Pet pet) {
        return repository.save(pet);
    }

    public List<Pet> listarPets() {
        return repository.findAll();
    }

    public Pet buscarId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado"));
    }

    public Pet atualizarPet(Long id, Pet petAtualizado) {
        Pet pet = buscarId(id);

        if (petAtualizado.getNome() != null) {
            pet.setNome(petAtualizado.getNome());
        }
        if (petAtualizado.getTipo() != null) {
            pet.setTipo(petAtualizado.getTipo());
        }
        if (petAtualizado.getSexo() != null) {
            pet.setSexo(petAtualizado.getSexo());
        }
        if (petAtualizado.getIdade() != null) {
            pet.setIdade(petAtualizado.getIdade());
        }
        if (petAtualizado.getPeso() != null) {
            pet.setPeso(petAtualizado.getPeso());
        }
        if (petAtualizado.getRaca() != null) {
            pet.setRaca(petAtualizado.getRaca());
        }
        if (petAtualizado.getEndereco() != null) {
            pet.setEndereco(petAtualizado.getEndereco());
        }
        return repository.save(pet);
    }

    public void deletarPet(Long id) {
        repository.deleteById(id);
    }
}
