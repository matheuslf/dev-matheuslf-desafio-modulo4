package dev.matheuslf.relogios.entity.enums;

public enum TipoVidro {
    MINERAL, SAFIRA, ACRILICO;

    public static TipoVidro fromApi(String valor) {
        if (valor == null || valor.isBlank()) return null;
        return switch (valor) {
            case "mineral" -> MINERAL;
            case "sapphire" -> SAFIRA;
            case "acrylic" -> ACRILICO;
            default -> throw new IllegalArgumentException("Tipo de vidro inválido: " + valor);
        };
    }

    public String toApi() {
        return switch (this) {
            case MINERAL -> "mineral";
            case SAFIRA -> "sapphire";
            case ACRILICO -> "acrylic";
        };
    }
}