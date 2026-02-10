package dev.matheuslf.relogios.service;

import dev.matheuslf.relogios.dto.AtualizarRelogioRequest;
import dev.matheuslf.relogios.dto.CriarRelogioRequest;
import dev.matheuslf.relogios.dto.PaginaRelogiosDto;
import dev.matheuslf.relogios.dto.RelogioDto;
import dev.matheuslf.relogios.entity.Relogio;
import dev.matheuslf.relogios.entity.enums.MaterialCaixa;
import dev.matheuslf.relogios.entity.enums.TipoMovimento;
import dev.matheuslf.relogios.entity.enums.TipoVidro;
import dev.matheuslf.relogios.exception.NaoEncontradoException;
import dev.matheuslf.relogios.mapper.RelogioMapper;
import dev.matheuslf.relogios.repository.RelogioRepository;
import dev.matheuslf.relogios.service.enums.OrdenacaoRelogios;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

import static dev.matheuslf.relogios.service.specification.RelogioSpecs.*;

@Service
@RequiredArgsConstructor
public class RelogioService {

    private final RelogioRepository repositorio;
    private final RelogioMapper mapeador;

    public PaginaRelogiosDto listar(
            int pagina,
            int porPagina,
            String busca,
            String marca,
            String tipoMovimento,
            String materialCaixa,
            String tipoVidro,
            Integer resistenciaMin,
            Integer resistenciaMax,
            Long precoMin,
            Long precoMax,
            Integer diametroMin,
            Integer diametroMax,
            String ordenar
    ) {
        int paginaSegura = Math.max(1, pagina);
        int porPaginaSeguro = Math.min(60, Math.max(1, porPagina));

        TipoMovimento movimento = TipoMovimento.fromApi(tipoMovimento);
        MaterialCaixa material = MaterialCaixa.fromApi(materialCaixa);
        TipoVidro vidro = TipoVidro.fromApi(tipoVidro);

        OrdenacaoRelogios ordenacao = OrdenacaoRelogios.fromApi(ordenar);

        Sort sort = switch (ordenacao) {
            case MAIS_RECENTES -> Sort.by(Sort.Direction.DESC, "criadoEm");
            case PRECO_CRESC -> Sort.by(Sort.Direction.ASC, "precoEmCentavos");
            case PRECO_DESC -> Sort.by(Sort.Direction.DESC, "precoEmCentavos");
            case DIAMETRO_CRESC -> Sort.by(Sort.Direction.ASC, "diametroMm");
            case RESISTENCIA_DESC -> Sort.by(Sort.Direction.DESC, "resistenciaAguaM");
        };

        Pageable pageable = PageRequest.of(paginaSegura - 1, porPaginaSeguro, sort);

        Specification<Relogio> spec  = Specification.where(busca(busca))
                .and(marcaIgual(marca))
                .and(tipoMovimentoIgual(movimento))
                .and(materialCaixaIgual(material))
                .and(tipoVidroIgual(vidro))
                .and(resistenciaAguaEntre(resistenciaMin, resistenciaMax))
                .and(precoEntre(precoMin, precoMax))
                .and(diametroEntre(diametroMin, diametroMax));

        Page<Relogio> resultado = repositorio.findAll(spec, pageable);

        return new PaginaRelogiosDto(
                resultado.getContent().stream().map(mapeador::toDto).toList(),
                resultado.getTotalElements()
        );
    }

    public RelogioDto buscarPorId(UUID id) {
        Relogio r = repositorio.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Relógio não encontrado: " + id));
        return mapeador.toDto(r);
    }

    public RelogioDto criar(CriarRelogioRequest req) {
        Relogio r = Relogio.builder()
                .id(UUID.randomUUID())
                .marca(req.marca())
                .modelo(req.modelo())
                .referencia(req.referencia())
                .tipoMovimento(TipoMovimento.fromApi(req.tipoMovimento()))
                .materialCaixa(MaterialCaixa.fromApi(req.materialCaixa()))
                .tipoVidro(TipoVidro.fromApi(req.tipoVidro()))
                .resistenciaAguaM(req.resistenciaAguaM())
                .diametroMm(req.diametroMm())
                .lugToLugMm(req.lugToLugMm())
                .espessuraMm(req.espessuraMm())
                .larguraLugMm(req.larguraLugMm())
                .precoEmCentavos(req.precoEmCentavos())
                .urlImagem(req.urlImagem())
                .criadoEm(Instant.now())
                .build();

        Relogio salvo = repositorio.save(r);
        return mapeador.toDto(salvo);
    }

    public RelogioDto atualizar(UUID id, AtualizarRelogioRequest req) {
        Relogio r = repositorio.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Relógio não encontrado: " + id));

        r.setMarca(req.marca());
        r.setModelo(req.modelo());
        r.setReferencia(req.referencia());
        r.setTipoMovimento(TipoMovimento.fromApi(req.tipoMovimento()));
        r.setMaterialCaixa(MaterialCaixa.fromApi(req.materialCaixa()));
        r.setTipoVidro(TipoVidro.fromApi(req.tipoVidro()));
        r.setResistenciaAguaM(req.resistenciaAguaM());
        r.setDiametroMm(req.diametroMm());
        r.setLugToLugMm(req.lugToLugMm());
        r.setEspessuraMm(req.espessuraMm());
        r.setLarguraLugMm(req.larguraLugMm());
        r.setPrecoEmCentavos(req.precoEmCentavos());
        r.setUrlImagem(req.urlImagem());

        Relogio salvo = repositorio.save(r);
        return mapeador.toDto(salvo);
    }

    public void remover(UUID id) {
        if (!repositorio.existsById(id)) {
            throw new NaoEncontradoException("Relógio não encontrado: " + id);
        }
        repositorio.deleteById(id);
    }
}
