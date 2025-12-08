package petsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import petsystem.model.Pergunta;
import petsystem.service.PerguntaService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PerguntaController.class)
public class PerguntaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PerguntaService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarPergunta() throws Exception {
        Pergunta pergunta = new Pergunta();
        pergunta.setId(1L);
        pergunta.setPergunta("É castrado?");

        when(service.salvarPergunta(any(Pergunta.class))).thenReturn(pergunta);

        mockMvc.perform(MockMvcRequestBuilders.post("/perguntas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pergunta)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.pergunta").value("É castrado?"));

        verify(service, times(1)).salvarPergunta(any(Pergunta.class));
    }

    @Test
    void deveListarPerguntas() throws Exception {
        List<Pergunta> perguntas = List.of(new Pergunta(1L, "É castrado?"));
        when(service.listarPerguntas()).thenReturn(perguntas);

        mockMvc.perform(MockMvcRequestBuilders.get("/perguntas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].pergunta").value("É castrado?"));

        verify(service, times(1)).listarPerguntas();
    }

    @Test
    void deveBuscarPerguntaPorId() throws Exception {
        Pergunta pergunta = new Pergunta();
        pergunta.setId(1L);
        pergunta.setPergunta("É castrado?");

        when(service.buscarId(1L)).thenReturn(pergunta);

        mockMvc.perform(MockMvcRequestBuilders.get("/perguntas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pergunta").value("É castrado?"));

        verify(service, times(1)).buscarId(1L);
    }

    @Test
    void deveAtualizarPergunta() throws Exception {
        Pergunta perguntaAtualizada = new Pergunta();
        perguntaAtualizada.setId(1L);
        perguntaAtualizada.setPergunta("Atualizada");

        when(service.atualizarPergunta(eq(1L), any(Pergunta.class))).thenReturn(perguntaAtualizada);

        mockMvc.perform(MockMvcRequestBuilders.put("/perguntas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(perguntaAtualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pergunta").value("Atualizada"));

        verify(service, times(1)).atualizarPergunta(eq(1L), any(Pergunta.class));
    }

    @Test
    void deveDeletarPergunta() throws Exception {
        doNothing().when(service).deletarPergunta(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/perguntas/1"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).deletarPergunta(1L);
    }
}
