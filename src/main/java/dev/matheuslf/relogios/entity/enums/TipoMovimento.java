package dev.matheuslf.relogios.entity.enums;

public enum TipoMovimento {
    QUARTZO, AUTOMATICO, MANUAL;

    public static TipoMovimento fromApi(final String valor) {
        if (valor == null || valor.isBlank()) return null;
        return switch (valor) {
            case "quartz" -> QUARTZO;
            case "automatic" -> AUTOMATICO;
            case "manual" -> MANUAL;
            default -> throw new IllegalArgumentException("Tipo de Movimento inválido "+valor);
        };
    }

    public String toApi() {
        return switch (this) {
            case QUARTZO -> "quartz";
            case AUTOMATICO -> "automatico";
            case MANUAL -> "manual";
        };
    }

}
