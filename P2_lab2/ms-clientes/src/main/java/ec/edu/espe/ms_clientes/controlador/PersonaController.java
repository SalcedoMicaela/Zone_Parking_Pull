package ec.edu.espe.ms_clientes.controlador;

import ec.edu.espe.ms_clientes.dto.request.PersonaNaturalRequestDto;
import ec.edu.espe.ms_clientes.dto.request.PersonaJuridicaRequestDto;
import ec.edu.espe.ms_clientes.dto.response.PersonaResponseDto;
import ec.edu.espe.ms_clientes.servicio.PersonaServicio;
import ec.edu.espe.ms_clientes.servicio.VehiculoServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/personas")
@RequiredArgsConstructor
public class PersonaController {

    private final PersonaServicio personaServicio;

    // PERSONA NATURAL
    @PostMapping("/natural")
    public ResponseEntity<PersonaResponseDto> crearPersonaNatural(@Valid @RequestBody PersonaNaturalRequestDto dto) {
        return ResponseEntity.ok(personaServicio.crearPersonaNatural(dto));
    }

    @PutMapping("/natural/{id}")
    public ResponseEntity<PersonaResponseDto> actualizarPersonaNatural(
            @PathVariable UUID id, @Valid @RequestBody PersonaNaturalRequestDto dto) {
        return ResponseEntity.ok(personaServicio.actualizarPersonaNatural(id, dto));
    }

    // PERSONA JURÍDICA
    @PostMapping("/juridica")
    public ResponseEntity<PersonaResponseDto> crearPersonaJuridica(@Valid @RequestBody PersonaJuridicaRequestDto dto) {
        return ResponseEntity.ok(personaServicio.crearPersonaJuridica(dto));
    }

    @PutMapping("/juridica/{id}")
    public ResponseEntity<PersonaResponseDto> actualizarPersonaJuridica(
            @PathVariable UUID id, @Valid @RequestBody PersonaJuridicaRequestDto dto) {
        return ResponseEntity.ok(personaServicio.actualizarPersonaJuridica(id, dto));
    }

    @GetMapping
    public ResponseEntity<List<PersonaResponseDto>> listarPersonas() {
        return ResponseEntity.ok(personaServicio.findAllPersona());
    }

    @GetMapping("/naturales")
    public ResponseEntity<List<PersonaResponseDto>> listarNaturalesActivas() {
        return ResponseEntity.ok(personaServicio.listarPersonasNaturales());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPersona(@PathVariable UUID id) {
        personaServicio.eliminarPersona(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/identificacion/{identificacion}")
    public PersonaResponseDto obtenerPorIdentificacion(
            @PathVariable String identificacion) {
        return personaServicio.obtenerPersonaPorIdentificacion(identificacion);
    }




}
