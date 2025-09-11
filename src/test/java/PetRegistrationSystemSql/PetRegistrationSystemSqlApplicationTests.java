package PetRegistrationSystemSql;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import PetRegistrationSystem.exception.ResourceNotFoundException;
import PetRegistrationSystem.model.Pergunta;
import PetRegistrationSystem.model.Pet;
import PetRegistrationSystem.repository.PerguntaRepository;
import PetRegistrationSystem.repository.PetRepository;
import PetRegistrationSystem.service.PerguntaService;
import PetRegistrationSystem.service.PetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PetRegistrationSystemSqlApplicationTests {

    @Mock
    PetRepository repositoryPet;

    @InjectMocks
    PetService servicePet;

    @Mock
    PerguntaRepository repositoryPergunta;

    @InjectMocks
    PerguntaService servicePergunta;

    @Test
    void SalvarPet() {
        Pet pet = new Pet();
        pet.setNome("Rex");

        when(repositoryPet.save(pet)).thenReturn(pet);

        Pet salvo = servicePet.salvarPet(pet);
        assertEquals("Rex", salvo.getNome());
    }

    @Test
    void PetNaoEncontrado() {
        when(repositoryPet.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> servicePet.buscarId(1L));
        assertEquals("Pet não encontrado", exception.getMessage());
    }

    @Test
    void ListarPets() {
        List<Pet> pets = List.of(new Pet(), new Pet());
        when(repositoryPet.findAll()).thenReturn(pets);

        List<Pet> resultado = servicePet.listarPets();
        assertEquals(2, resultado.size());
    }

    @Test
    void AtualizarPet() {
        Pet petExistente = new Pet();
        petExistente.setId(1L);
        petExistente.setNome("Rex");

        Pet petAtualizado = new Pet();
        petAtualizado.setNome("Max");

        when(repositoryPet.findById(1L)).thenReturn(Optional.of(petExistente));
        when(repositoryPet.save(any(Pet.class))).thenReturn(petExistente);

        Pet resultado = servicePet.atualizarPet(1L, petAtualizado);

        assertEquals("Max", resultado.getNome());
    }

    @Test
    void DeletarPet() {
        servicePet.deletarPet(1L);
        verify(repositoryPet, times(1)).deleteById(1L);
    }

    @Test
    void SalvarPergunta() {
        Pergunta pergunta = new Pergunta();
        pergunta.setPergunta("O pet é castrado?");

        when(repositoryPergunta.save(pergunta)).thenReturn(pergunta);

        Pergunta salva = servicePergunta.salvarPergunta(pergunta);
        assertEquals("O pet é castrado?", salva.getPergunta());
    }

    @Test
    void PerguntaNaoEncontrada() {
        when(repositoryPergunta.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> servicePergunta.buscarId(1L));
        assertEquals("Pergunta não encontrada", exception.getMessage());
    }

    @Test
    void AtualizarPergunta() {
        Pergunta existente = new Pergunta();
        existente.setId(1L);
        existente.setPergunta("Original");

        Pergunta atualizada = new Pergunta();
        atualizada.setPergunta("Atualizada");

        when(repositoryPergunta.findById(1L)).thenReturn(Optional.of(existente));
        when(repositoryPergunta.save(any(Pergunta.class))).thenReturn(existente);

        Pergunta resultado = servicePergunta.atualizarPergunta(1L, atualizada);

        assertEquals("Atualizada", resultado.getPergunta());
    }

    @Test
    void ListarPerguntas() {
        List<Pergunta> perguntas = List.of(new Pergunta(), new Pergunta());
        when(repositoryPergunta.findAll()).thenReturn(perguntas);

        List<Pergunta> resultado = servicePergunta.listarPerguntas();
        assertEquals(2, resultado.size());
    }

    @Test
    void DeveDeletarPergunta() {
        servicePergunta.deletarPergunta(1L);
        verify(repositoryPergunta, times(1)).deleteById(1L);
    }
}

