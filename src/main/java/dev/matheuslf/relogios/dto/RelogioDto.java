package dev.matheuslf.relogios.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record RelogioDto(
        UUID id,
        String marca,
        String modelo,
        String referencia,
        String tipoMovimento,
        String materialCaixa,
        String tipoVidro,
        int resistenciaAguaM,
        int diametroMm,
        int lugToLugMm,
        int espessuraMm,
        int larguraLugMm,
        long precoEmCentavos,
        String urlImagem,
        String etiquetaResistenciaAgua,
        int pontuacaoColecionador
) {}
