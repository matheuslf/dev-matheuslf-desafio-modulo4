package dev.matheuslf.relogios.service.enums;

public enum OrdenacaoRelogios {
    MAIS_RECENTES,
    PRECO_CRESC,
    PRECO_DESC,
    DIAMETRO_CRESC,
    RESISTENCIA_DESC;

    public static OrdenacaoRelogios fromApi(String valor) {
        if (valor == null || valor.isBlank()) return MAIS_RECENTES;
        return switch (valor) {
            case "newest" -> MAIS_RECENTES;
            case "price_asc" -> PRECO_CRESC;
            case "price_desc" -> PRECO_DESC;
            case "diameter_asc" -> DIAMETRO_CRESC;
            case "wr_desc" -> RESISTENCIA_DESC;
            default -> throw new IllegalArgumentException("Ordenação inválida: " + valor);
        };
    }
}
