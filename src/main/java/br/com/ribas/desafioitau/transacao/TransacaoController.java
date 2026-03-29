package br.com.ribas.desafioitau.transacao;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Slf4j
@RestController
@RequestMapping(value = "/transacao", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Transações", description = "Operações relacionadas ao gerenciamento de transações")
public record TransacaoController(TransacaoRepository transacaoRepository) {

    @Operation(
            summary = "Adiciona uma nova transação",
            description = "Registra uma transação financeira na memória. O valor não pode ser negativo e a data não pode ser no futuro."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transação criada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados inválidos: valor negativo ou data/hora no futuro"),
            @ApiResponse(responseCode = "400", description = "Erro genérico na requisição, como JSON malformado")
    })
    @PostMapping
    public ResponseEntity adicionar(@RequestBody TransacaoRequest transacaoRequest) {
        log.info("Adicionando Transação");
        try {
            //se a transação for válida:
            validarTransacao(transacaoRequest);
            transacaoRepository.add(transacaoRequest);
            return ResponseEntity.status(HttpStatus.CREATED).build();

            //se a transação não for válida. Erros:
        } catch (IllegalArgumentException illegalArgumentException) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @Operation(
            summary = "Remove todas as transações",
            description = "Limpa todas as transações armazenadas em memória. A operação não pode ser desfeita."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transações removidas com sucesso")
    })
    @DeleteMapping
    public ResponseEntity limpar() {
        log.info("Limpando Transações");
        transacaoRepository.limpar();

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private void validarTransacao(TransacaoRequest transacaoRequest) {
        if (transacaoRequest.getValor().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Valor da transação inválido.");
        }
        if (transacaoRequest.getDataHora().isAfter(OffsetDateTime.now())) {
            throw new IllegalArgumentException("Data da transação inválida");
        }
    }


}
