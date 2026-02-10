package dev.matheuslf.relogios.service.specification;

import dev.matheuslf.relogios.entity.Relogio;
import dev.matheuslf.relogios.entity.enums.MaterialCaixa;
import dev.matheuslf.relogios.entity.enums.TipoMovimento;
import dev.matheuslf.relogios.entity.enums.TipoVidro;
import org.springframework.data.jpa.domain.Specification;

public final class RelogioSpecs {
    private RelogioSpecs() {}

    /** Spec neutra: não filtra nada (WHERE 1=1) */
    public static Specification<Relogio> tudo() {
        return (root, q, cb) -> cb.conjunction();
    }

    private static boolean blank(String s) {
        return s == null || s.isBlank();
    }

    /**
     * WHERE
     * LOWER(marca)      LIKE '%termo%'
     * OR LOWER(modelo)     LIKE '%termo%'
     * OR LOWER(referencia) LIKE '%termo%'
     */
    public static Specification<Relogio> busca(String termo) {
        if (blank(termo)) return tudo();
        String like = "%" + termo.trim().toLowerCase() + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(root.get("marca")), like),
                cb.like(cb.lower(root.get("modelo")), like),
                cb.like(cb.lower(root.get("referencia")), like)
        );
    }

    public static Specification<Relogio> marcaIgual(String marca) {
        if (blank(marca)) return tudo();
        String v = marca.trim().toLowerCase();
        return (root, q, cb) -> cb.equal(cb.lower(root.get("marca")), v);
    }

    public static Specification<Relogio> tipoMovimentoIgual(TipoMovimento tipo) {
        if (tipo == null) return tudo();
        return (root, q, cb) -> cb.equal(root.get("tipoMovimento"), tipo);
    }

    public static Specification<Relogio> materialCaixaIgual(MaterialCaixa material) {
        if (material == null) return tudo();
        return (root, q, cb) -> cb.equal(root.get("materialCaixa"), material);
    }

    public static Specification<Relogio> tipoVidroIgual(TipoVidro tipo) {
        if (tipo == null) return tudo();
        return (root, q, cb) -> cb.equal(root.get("tipoVidro"), tipo);
    }

    public static Specification<Relogio> resistenciaAguaEntre(Integer min, Integer max) {
        if (min == null && max == null) return tudo();
        return (root, q, cb) -> {
            if (min != null && max != null) return cb.between(root.get("resistenciaAguaM"), min, max);
            if (min != null) return cb.greaterThanOrEqualTo(root.get("resistenciaAguaM"), min);
            return cb.lessThanOrEqualTo(root.get("resistenciaAguaM"), max);
        };
    }

    public static Specification<Relogio> precoEntre(Long min, Long max) {
        if (min == null && max == null) return tudo();
        return (root, q, cb) -> {
            if (min != null && max != null) return cb.between(root.get("precoEmCentavos"), min, max);
            if (min != null) return cb.greaterThanOrEqualTo(root.get("precoEmCentavos"), min);
            return cb.lessThanOrEqualTo(root.get("precoEmCentavos"), max);
        };
    }

    public static Specification<Relogio> diametroEntre(Integer min, Integer max) {
        if (min == null && max == null) return tudo();
        return (root, q, cb) -> {
            if (min != null && max != null) return cb.between(root.get("diametroMm"), min, max);
            if (min != null) return cb.greaterThanOrEqualTo(root.get("diametroMm"), min);
            return cb.lessThanOrEqualTo(root.get("diametroMm"), max);
        };
    }
}
