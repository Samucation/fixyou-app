package com.fcamara.util;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import com.fcamara.exception.ResourceNotFoundException;
import com.fcamara.model.PersonData;
import com.fcamara.model.Profile;

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
     * Verifica se o CPF informado corresponde ao CPF do PersonData fornecido.
     * Também valida se o CPF possui formato válido.
     *
     * @param cpf         o CPF externo a ser validado.
     * @param personData  o PersonData com o CPF armazenado no banco.
     * @throws IllegalArgumentException    se o CPF tiver formato inválido.
     * @throws ResourceNotFoundException   se os CPFs não forem iguais ou PersonData for nulo.
     */
    public static void isCpfExistInDataBase(String cpf, PersonData personData) {
        isValidCpf(cpf);

        if (personData == null || personData.getCpf() == null) {
            throw new ResourceNotFoundException("PersonData não encontrado ou CPF não cadastrado.");
        }

        if (!cpf.equals(personData.getCpf())) {
            throw new ResourceNotFoundException(
                    "O CPF informado (" + cpf + ") não corresponde ao CPF cadastrado (" + personData.getCpf() + ")."
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
