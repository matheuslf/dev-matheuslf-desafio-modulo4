package dev.matheuslf.relogios.dto;

import java.util.List;

public record PaginaRelogiosDto(
        List<RelogioDto> itens,
        long total
) {}
