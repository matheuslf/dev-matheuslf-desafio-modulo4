package dev.matheuslf.relogios.config;

import dev.matheuslf.relogios.entity.Relogio;
import dev.matheuslf.relogios.entity.enums.MaterialCaixa;
import dev.matheuslf.relogios.entity.enums.TipoMovimento;
import dev.matheuslf.relogios.entity.enums.TipoVidro;
import dev.matheuslf.relogios.repository.RelogioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class CarregadorDadosInicial {

    private final RelogioRepository repositorio;

    @Bean
    CommandLineRunner seedRelogios() {
        return args -> {
            if (repositorio.count() > 0) return;

            Instant agora = Instant.now();

            List<Relogio> relogios = List.of(
                    Relogio.builder()
                            .id(UUID.randomUUID())
                            .marca("Casio")
                            .modelo("F-91W")
                            .referencia("F-91W-1")
                            .tipoMovimento(TipoMovimento.QUARTZO)
                            .materialCaixa(MaterialCaixa.RESINA)
                            .tipoVidro(TipoVidro.ACRILICO)
                            .resistenciaAguaM(30)
                            .diametroMm(35)
                            .lugToLugMm(38)
                            .espessuraMm(9)
                            .larguraLugMm(18)
                            .precoEmCentavos(12990)
                            .urlImagem("https://picsum.photos/seed/relogio1/800/800")
                            .criadoEm(agora.minusSeconds(50000))
                            .build(),

                    Relogio.builder()
                            .id(UUID.randomUUID())
                            .marca("Seiko")
                            .modelo("Diver 200m")
                            .referencia("SKX-Style")
                            .tipoMovimento(TipoMovimento.AUTOMATICO)
                            .materialCaixa(MaterialCaixa.ACO)
                            .tipoVidro(TipoVidro.MINERAL)
                            .resistenciaAguaM(200)
                            .diametroMm(42)
                            .lugToLugMm(46)
                            .espessuraMm(13)
                            .larguraLugMm(22)
                            .precoEmCentavos(159990)
                            .urlImagem("https://picsum.photos/seed/relogio2/800/800")
                            .criadoEm(agora.minusSeconds(30000))
                            .build(),

                    Relogio.builder()
                            .id(UUID.randomUUID())
                            .marca("Citizen")
                            .modelo("Eco-Drive Field")
                            .referencia("BM8180-03E")
                            .tipoMovimento(TipoMovimento.QUARTZO)
                            .materialCaixa(MaterialCaixa.ACO)
                            .tipoVidro(TipoVidro.MINERAL)
                            .resistenciaAguaM(100)
                            .diametroMm(37)
                            .lugToLugMm(44)
                            .espessuraMm(11)
                            .larguraLugMm(18)
                            .precoEmCentavos(99990)
                            .urlImagem("https://picsum.photos/seed/relogio3/800/800")
                            .criadoEm(agora.minusSeconds(10000))
                            .build()
            );

            repositorio.saveAll(relogios);
        };
    }
}
