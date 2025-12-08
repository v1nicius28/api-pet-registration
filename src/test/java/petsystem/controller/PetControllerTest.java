package petsystem.controller;

import petsystem.model.Pet;
import petsystem.service.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PetController.class)
public class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PetService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarPet() throws Exception {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setNome("Rex");
        pet.setTipo(Pet.TipoPet.CACHORRO);
        pet.setSexo(Pet.SexoPet.MACHO);
        pet.setRaca("Vira-lata");

        when(service.salvarPet(any(Pet.class))).thenReturn(pet);

        mockMvc.perform(MockMvcRequestBuilders.post("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pet)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Rex"));

        verify(service, times(1)).salvarPet(any(Pet.class));
    }

    @Test
    void deveListarPets() throws Exception {
        List<Pet> pets = List.of(new Pet(1L, "Rex", Pet.TipoPet.CACHORRO, Pet.SexoPet.MACHO, null, 0, 0, "vira-lata"));
        when(service.listarPets()).thenReturn(pets);

        mockMvc.perform(MockMvcRequestBuilders.get("/pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nome").value("Rex"));

        verify(service, times(1)).listarPets();
    }

    @Test
    void deveBuscarPetPorId() throws Exception {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setNome("Rex");

        when(service.buscarId(1L)).thenReturn(pet);

        mockMvc.perform(MockMvcRequestBuilders.get("/pets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Rex"));

        verify(service, times(1)).buscarId(1L);
    }

    @Test
    void deveAtualizarPet() throws Exception {
        Pet petAtualizado = new Pet();
        petAtualizado.setId(1L);
        petAtualizado.setNome("Max");
        petAtualizado.setTipo(Pet.TipoPet.CACHORRO);
        petAtualizado.setSexo(Pet.SexoPet.MACHO);
        petAtualizado.setRaca("Vira-lata");

        when(service.atualizarPet(eq(1L), any(Pet.class))).thenReturn(petAtualizado);

        mockMvc.perform(MockMvcRequestBuilders.put("/pets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(petAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Max"));

        verify(service, times(1)).atualizarPet(eq(1L), any(Pet.class));
    }

    @Test
    void deveDeletarPet() throws Exception {
        doNothing().when(service).deletarPet(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/pets/1"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).deletarPet(1L);
    }
}
