package com.fcamara.util;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import com.fcamara.exception.ResourceNotFoundException;

import java.time.Year;

/**
 * Classe utilitária responsável por validações relacionadas ao usuário/cliente.
 */
public class ClientValidateUtil {

    /**
     * Valida o formato do CPF usando a biblioteca Stella da Caelum.
     *
     * @param cpf o CPF a ser validado.
     * @throws IllegalArgumentException se o CPF for inválido.
     */
    public static void isValidCpf(String cpf) {
        CPFValidator cpfValidator = new CPFValidator();
        try {
            cpfValidator.assertValid(cpf);
        } catch (InvalidStateException e) {
            throw new IllegalArgumentException("CPF inválido: " + cpf);
        }
    }

    /**
     * Verifica se os dois CPFs informados são válidos e correspondem entre si.
     *
     * @param inputCpf    o CPF externo a ser validado.
     * @param dbCpf       o CPF armazenado no banco de dados.
     * @throws IllegalArgumentException    se qualquer CPF tiver formato inválido.
     * @throws ResourceNotFoundException   se os CPFs não forem iguais.
     */
    public static void isCpfExistInDataBase(String inputCpf, String dbCpf) {
        isValidCpf(inputCpf);
        isValidCpf(dbCpf);

        if (!inputCpf.equals(dbCpf)) {
            throw new ResourceNotFoundException(
                    "O CPF informado (" + inputCpf + ") não corresponde ao CPF cadastrado (" + dbCpf + ")."
            );
        }
    }

    /**
     * Valida se o ano informado está dentro de um intervalo plausível.
     *
     * @param year o ano a ser validado
     * @throws IllegalArgumentException se o ano for inválido
     */
    public static void isValidYear(int year) {
        int currentYear = Year.now().getValue();
        if (year < 1900 || year > currentYear) {
            throw new IllegalArgumentException("Ano inválido: " + year);
        }
    }
}
