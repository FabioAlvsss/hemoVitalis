package serviceUnitTest;

import com.HemoVitalis.HemoVitalis.DTO.ListagemPacienteDTO;
import com.HemoVitalis.HemoVitalis.entities.ListagemPaciente;
import com.HemoVitalis.HemoVitalis.repository.ListagemPacienteRepository;
import com.HemoVitalis.HemoVitalis.repository.PacienteRepository;
import com.HemoVitalis.HemoVitalis.service.ListagemPacienteService;
import com.HemoVitalis.HemoVitalis.service.PacienteService;
import com.HemoVitalis.HemoVitalis.service.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ListagemPacienteServiceTest {

        @InjectMocks
        private ListagemPacienteService listagemPacienteService;

        @Mock
        private ListagemPacienteRepository listagemPacienteRepository;

        @Mock
        private PacienteRepository pacienteRepository;

        @Mock
        private PacienteService pacienteService;

        private ListagemPaciente listagemPaciente;
        private ListagemPacienteDTO listagemPacienteDTO;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
            listagemPaciente = new ListagemPaciente();
            listagemPacienteDTO = new ListagemPacienteDTO(listagemPaciente);
        }

        @Test
        public void testSave() {
            when(listagemPacienteRepository.save(any(ListagemPaciente.class))).thenReturn(listagemPaciente);

            ListagemPacienteDTO result = listagemPacienteService.save(listagemPacienteDTO);

            assertNotNull(result);
            verify(listagemPacienteRepository, times(1)).save(any(ListagemPaciente.class));
        }

        @Test
        public void testFindAll() {
            when(listagemPacienteRepository.findAll()).thenReturn(List.of(listagemPaciente));

            List<ListagemPacienteDTO> result = listagemPacienteService.findAll();

            assertEquals(1, result.size());
            verify(listagemPacienteRepository, times(1)).findAll();
        }

        @Test
        public void testFindById_Success() {
            when(listagemPacienteRepository.findById(1L)).thenReturn(Optional.of(listagemPaciente));

            ListagemPacienteDTO result = listagemPacienteService.findById(1L);

            assertNotNull(result);
            verify(listagemPacienteRepository, times(1)).findById(1L);
        }

        @Test
        public void testFindById_NotFound() {
            when(listagemPacienteRepository.findById(1L)).thenReturn(Optional.empty());

            ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
                listagemPacienteService.findById(1L);
            });

            assertEquals("Lista não encontrada", exception.getMessage());
            verify(listagemPacienteRepository, times(1)).findById(1L);
        }

        @Test
        public void testDelete_Success() {
            doNothing().when(listagemPacienteRepository).deleteById(1L);

            listagemPacienteService.delete(1L);

            verify(listagemPacienteRepository, times(1)).deleteById(1L);
        }

        @Test
        public void testDelete_NotFound() {
            doThrow(new ResourceNotFoundException("Id não encontrado")).when(listagemPacienteRepository).deleteById(1L);

            ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
                listagemPacienteService.delete(1L);
            });

            assertEquals("Id não encontrado", exception.getMessage());
            verify(listagemPacienteRepository, times(1)).deleteById(1L);
        }

        @Test
        public void testUpdate_Success() {
            when(listagemPacienteRepository.getOne(1L)).thenReturn(listagemPaciente);
            when(listagemPacienteRepository.save(any(ListagemPaciente.class))).thenReturn(listagemPaciente);

            ListagemPacienteDTO result = listagemPacienteService.update(1L, listagemPacienteDTO);

            assertNotNull(result);
            verify(listagemPacienteRepository, times(1)).save(any(ListagemPaciente.class));
        }

        @Test
        public void testUpdate_NotFound() {
            when(listagemPacienteRepository.getOne(1L)).thenThrow(new ResourceNotFoundException("id não encontrado"));

            ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
                listagemPacienteService.update(1L, listagemPacienteDTO);
            });

            assertEquals("id não encontrado", exception.getMessage());
        }

        @Test
        public void testFindAllPage() {
            PageRequest pageable = PageRequest.of(0, 10);
            when(listagemPacienteRepository.findAll(pageable)).thenReturn(Page.empty());

            Page<ListagemPacienteDTO> result = listagemPacienteService.findAllPage(pageable);

            assertNotNull(result);
            verify(listagemPacienteRepository, times(1)).findAll(pageable);
        }
    }

