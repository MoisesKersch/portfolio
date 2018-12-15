package com.portfolio.utils.documentosPessoais;

import com.portfolio.utils.texto.StringUtil;

public class CNP
{
	public static final int CPF = 0;
	public static final int CNPJ = 1;
	private static final int[] PESO_CPF =
	{ 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
	private static final int[] PESO_CNPJ =
	{ 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

	private static int calculaDigito(String str, int[] peso) throws Exception
	{
		int soma = 0;
		for (int indice = str.length() - 1, digito; indice >= 0; indice--)
		{
			digito = Integer.parseInt(str.substring(indice, indice + 1));
			soma += digito * peso[peso.length - str.length() + indice];
		}
		soma = 11 - soma % 11;
		return soma > 9 ? 0 : soma;
	}

	public static boolean validaCPF(String cpf) throws Exception
	{
		cpf = StringUtil.removeNaoNumericos(cpf);
		if ((cpf == null) || (cpf.length() != 11))
			return false;
		Integer digito1 = calculaDigito(cpf.substring(0, 9), PESO_CPF);
		Integer digito2 = calculaDigito(cpf.substring(0, 9) + digito1, PESO_CPF);
		return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
	}

	public static boolean validaCNPJ(String cnpj) throws Exception
	{
		cnpj = StringUtil.removeNaoNumericos(cnpj);
		if ((cnpj == null) || (cnpj.length() != 14))
			return false;
		Integer digito1 = calculaDigito(cnpj.substring(0, 12), PESO_CNPJ);
		Integer digito2 = calculaDigito(cnpj.substring(0, 12) + digito1, PESO_CNPJ);
		return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2.toString());
	}

	public static String formataCPF(String cpf) throws Exception
	{
		if (cpf == null)
			return "";
		cpf = StringUtil.removeNaoNumericos(cpf);
		if (cpf.length() == 11)
			return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-"
					+ cpf.substring(9, 11);
		return cpf;
	}

	public static String formataCNPJ(String cnpj) throws Exception
	{
		if (cnpj == null)
			return "";
		cnpj = StringUtil.removeNaoNumericos(cnpj);
		if (cnpj.length() == 14)
			return cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." + cnpj.substring(5, 8) + "/"
					+ cnpj.substring(8, 12) + "-" + cnpj.substring(12, 14);
		return cnpj;
	}

	public static int getTipoDocumento(String numero) throws Exception
	{
		numero = StringUtil.removeNaoNumericos(numero);
		if (numero.length() == 11)
			return CPF;
		else if (numero.length() == 14)
			return CNPJ;
		throw new Exception("Não foi possível determinar o tipo de documento pessoal para o número: " + numero);
	}
}