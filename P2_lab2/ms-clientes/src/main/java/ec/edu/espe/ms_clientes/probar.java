package ec.edu.espe.ms_clientes;

import ec.edu.espe.ms_clientes.dto.mapper.AutomovilMapper;
import ec.edu.espe.ms_clientes.dto.mapper.MotoMapper;
import ec.edu.espe.ms_clientes.dto.mapper.PersonaMapper;
import ec.edu.espe.ms_clientes.dto.request.AutomovilRequestDto;
import ec.edu.espe.ms_clientes.dto.request.MotoRequestDto;
import ec.edu.espe.ms_clientes.dto.request.PersonaNaturalRequestDto;
import ec.edu.espe.ms_clientes.dto.request.PersonaJuridicaRequestDto;
import ec.edu.espe.ms_clientes.dto.response.*;
import ec.edu.espe.ms_clientes.model.*;

import java.time.LocalDate;
import java.util.UUID;

public class probar {

    public static void main(String[] args) {

        // ========================================
        // 1) VALIDAR IDENTIFICACIONES
        // ========================================
        System.out.println("\n--- Validación de Identificación ---");

        PersonaNatural nat = new PersonaNatural();
        nat.setIdentificacion("0603977224");
        System.out.println("Cédula válida?: " + nat.validarIdentificacion());

        PersonaNatural natRuc = new PersonaNatural();
        natRuc.setIdentificacion("0802518894001");
        System.out.println("RUC natural válido?: " + natRuc.validarIdentificacion());

        PersonaJuridica jur = new PersonaJuridica();
        jur.setIdentificacion("1719138818001");
        System.out.println("RUC jurídico válido?: " + jur.validarIdentificacion());


        // ========================================
        // 2) TEST DE MAPPER  PERSONA NATURAL
        // ========================================
        System.out.println("\n Persona Natural ");

        PersonaNatural personaNat = PersonaNatural.builder()
                .id(UUID.randomUUID())
                .nombre("Juan")
                .apellido("Perez")
                .genero(Genero.Masculino)
                .fechaNacimiento(LocalDate.of(2000, 1, 1))
                .email("nat@mail.com")
                .telefono("0991111111")
                .direccion("Quito")
                .activo(true)
                .build();

        PersonaMapper personaMapper = new PersonaMapper();
        PersonaResponseDto dtoNat = personaMapper.toDto(personaNat);
        System.out.println(dtoNat);


        // ========================================
        // 3) TEST DE MAPPER → PERSONA JURIDICA
        // ========================================
        System.out.println("\n--- Mapper Persona Jurídica ---");

        PersonaJuridica personaJur = PersonaJuridica.builder()
                .id(UUID.randomUUID())
                .razonSocial("Empresa XYZ S.A.")
                .representanteLegal("Ana López")
                .actividadEconomica("Servicios")
                .email("jur@mail.com")
                .telefono("022345678")
                .direccion("Guayaquil")
                .activo(true)
                .build();

        PersonaResponseDto dtoJur = personaMapper.toDto(personaJur);

        System.out.println(dtoJur);



        System.out.println("\n--- Mapper Automóvil ---");

        // Request DTO de prueba
        AutomovilRequestDto autoReq = new AutomovilRequestDto();
        autoReq.setPlaca("ABC-1234");
        autoReq.setMarca("Toyota");
        autoReq.setModelo("Corolla");
        autoReq.setColor("Rojo");
        autoReq.setAnioFabricacion(2020);
        autoReq.setIdPersona(dtoNat.getId().toString());
        autoReq.setTipoAutomovil("Sedan");
        autoReq.setMarcaAutomovil("Toyota");
        autoReq.setCilindraje(1.8);
        autoReq.setTipoCombustible("Gasolina");
        autoReq.setNumeroPuertas(4);
        autoReq.setTipoTransmision("Automática");

        AutomovilMapper autoMapper = new AutomovilMapper();
        Automovil auto = autoMapper.toEntity(autoReq, personaNat);

        AutomovilResponseDto autoDto = autoMapper.toDto(auto);
        System.out.println(autoDto);


        // ========================================
        // 5) TEST DE MOTO → ENTITY & DTO
        // ========================================
        System.out.println("\n--- Mapper Moto ---");

        MotoRequestDto motoReq = new MotoRequestDto();
        motoReq.setPlaca("XYZ-9876");
        motoReq.setMarca("Honda");
        motoReq.setModelo("CBR600");
        motoReq.setColor("Negro");
        motoReq.setAnioFabricacion(2022);
        motoReq.setIdPersona(dtoJur.getId().toString());
        motoReq.setCilindraje(600);
        motoReq.setTipoMoto("SPORT");
        motoReq.setTieneCasco(true);
        motoReq.setMarcaMoto("HONDA");
        motoReq.setTipoFrenos("ABS");
        motoReq.setTieneParrilla(false);

        MotoMapper motoMapper = new MotoMapper();
        Moto moto = motoMapper.toEntity(motoReq, personaJur);

        MotoResponseDto motoDto = motoMapper.toDto(moto);
        System.out.println(motoDto);
    }
}
