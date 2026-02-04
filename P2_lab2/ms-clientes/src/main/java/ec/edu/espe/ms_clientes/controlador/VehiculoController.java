package ec.edu.espe.ms_clientes.controlador;

import ec.edu.espe.ms_clientes.dto.request.AutomovilRequestDto;
import ec.edu.espe.ms_clientes.dto.request.MotoRequestDto;
import ec.edu.espe.ms_clientes.dto.response.VehiculoResponseDto;
import ec.edu.espe.ms_clientes.servicio.VehiculoServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/vehiculos")
@RequiredArgsConstructor
public class VehiculoController {

    private final VehiculoServicio vehiculoServicio;


    @PostMapping("/automovil")
    public ResponseEntity<VehiculoResponseDto> crearAutomovil(
            @Valid @RequestBody AutomovilRequestDto dto) {
        return ResponseEntity.ok(vehiculoServicio.crearAutomovil(dto));
    }

    @PutMapping("/automovil/{id}")
    public ResponseEntity<VehiculoResponseDto> actualizarAutomovil(
            @PathVariable UUID id,
            @Valid @RequestBody AutomovilRequestDto dto) {
        return ResponseEntity.ok(vehiculoServicio.actualizarAutomovil(id, dto));
    }




    @PostMapping("/moto")
    public ResponseEntity<VehiculoResponseDto> crearMoto(
            @Valid @RequestBody MotoRequestDto dto) {
        return ResponseEntity.ok(vehiculoServicio.crearMoto(dto));
    }

    @PutMapping("/moto/{id}")
    public ResponseEntity<VehiculoResponseDto> actualizarMoto(
            @PathVariable UUID id,
            @Valid @RequestBody MotoRequestDto dto) {
        return ResponseEntity.ok(vehiculoServicio.actualizarMoto(id, dto));
    }



    @GetMapping
    public ResponseEntity<List<VehiculoResponseDto>> listarVehiculos() {
        return ResponseEntity.ok(vehiculoServicio.findAllVehiculos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVehiculo(@PathVariable UUID id) {
        vehiculoServicio.eliminarVehiculo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/placa/{placa}")
    public VehiculoResponseDto obtenerPorPlaca(@PathVariable String placa) {
        return vehiculoServicio.obtenerVehiculoPorPlaca(placa);
    }
    @GetMapping("/{vehiculoId}/belongs-to/{personaId}")
    public ResponseEntity<Boolean> validateVehiculoBelongsToPersona(
            @PathVariable UUID vehiculoId,
            @PathVariable UUID personaId
    ) {
        return ResponseEntity.ok(
                vehiculoServicio.validateVehiculoBelongsToPersona(vehiculoId, personaId)
        );
    }

}
