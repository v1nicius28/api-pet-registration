package petsystem.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import petsystem.exception.ResourceNotFoundException;
import petsystem.model.Pergunta;
import petsystem.repository.PerguntaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class PerguntaServiceTest {

    @Mock
    private PerguntaRepository repository;

    @InjectMocks
    private PerguntaService service;

    @Test
    void deveSalvarPergunta() {
        Pergunta pergunta = new Pergunta();
        pergunta.setPergunta("O pet é castrado?");

        when(repository.save(pergunta)).thenReturn(pergunta);

        Pergunta salva = service.salvarPergunta(pergunta);

        assertEquals("O pet é castrado?", salva.getPergunta());
        verify(repository, times(1)).save(pergunta);
    }

    @Test
    void deveListarTodasPerguntas() {
        List<Pergunta> perguntas = List.of(new Pergunta(), new Pergunta());
        when(repository.findAll()).thenReturn(perguntas);

        List<Pergunta> resultado = service.listarPerguntas();

        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void deveBuscarPerguntaPorIdComSucesso() {
        Pergunta pergunta = new Pergunta();
        pergunta.setId(1L);
        pergunta.setPergunta("É castrado?");

        when(repository.findById(1L)).thenReturn(Optional.of(pergunta));

        Pergunta encontrada = service.buscarId(1L);

        assertEquals("É castrado?", encontrada.getPergunta());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void deveLancarExcecaoSePerguntaNaoExistir() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> service.buscarId(1L)
        );

        assertEquals("Pergunta não encontrada", exception.getMessage());
    }

    @Test
    void deveAtualizarPergunta() {
        Pergunta existente = new Pergunta();
        existente.setId(1L);
        existente.setPergunta("Original");

        Pergunta nova = new Pergunta();
        nova.setPergunta("Atualizada");

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.save(any(Pergunta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pergunta resultado = service.atualizarPergunta(1L, nova);

        assertEquals("Atualizada", resultado.getPergunta());
        verify(repository, times(1)).save(existente);
    }

    @Test
    void deveLancarExcecaoAoAtualizarPerguntaInexistente() {
        Pergunta nova = new Pergunta();
        nova.setPergunta("Atualizada");

        when(repository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> service.atualizarPergunta(99L, nova)
        );

        assertEquals("Pergunta não encontrada", exception.getMessage());
    }

    @Test
    void deveDeletarPergunta() {
        service.deletarPergunta(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}
