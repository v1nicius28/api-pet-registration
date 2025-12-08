package petsystem.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import petsystem.exception.ResourceNotFoundException;
import petsystem.model.Pet;
import petsystem.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PetServiceTest {

    @Mock
    private PetRepository repository;

    @InjectMocks
    private PetService service;

    @Test
    void deveSalvarPet() {
        Pet pet = new Pet();
        pet.setNome("Rex");

        when(repository.save(pet)).thenReturn(pet);

        Pet salvo = service.salvarPet(pet);

        assertEquals("Rex", salvo.getNome());
        verify(repository, times(1)).save(pet);
    }

    @Test
    void deveListarTodosPets() {
        List<Pet> pets = List.of(new Pet(), new Pet());
        when(repository.findAll()).thenReturn(pets);

        List<Pet> resultado = service.listarPets();

        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void deveBuscarPetPorIdComSucesso() {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setNome("Rex");

        when(repository.findById(1L)).thenReturn(Optional.of(pet));

        Pet encontrado = service.buscarId(1L);

        assertEquals("Rex", encontrado.getNome());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void deveLancarExcecaoSePetNaoExistir() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> service.buscarId(1L)
        );

        assertEquals("Pet não encontrado", exception.getMessage());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void deveAtualizarPetComCamposExistentes() {
        Pet existente = new Pet();
        existente.setId(1L);
        existente.setNome("Rex");
        existente.setRaca("Vira-lata");

        Pet atualizado = new Pet();
        atualizado.setNome("Max");
        atualizado.setPeso(10);

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.save(any(Pet.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pet resultado = service.atualizarPet(1L, atualizado);

        assertEquals("Max", resultado.getNome());
        assertEquals("Vira-lata", resultado.getRaca()); // campo não enviado permanece
        assertEquals(10, resultado.getPeso());
        verify(repository, times(1)).save(existente);
    }

    @Test
    void deveLancarExcecaoAoAtualizarPetInexistente() {
        Pet atualizado = new Pet();
        atualizado.setNome("Max");

        when(repository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> service.atualizarPet(99L, atualizado)
        );

        assertEquals("Pet não encontrado", exception.getMessage());
    }

    @Test
    void deveDeletarPet() {
        service.deletarPet(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}
