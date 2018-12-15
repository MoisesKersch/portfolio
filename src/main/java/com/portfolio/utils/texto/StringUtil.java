package com.portfolio.utils.texto;

public class StringUtil
{
	public static String removeAcentos(String texto) throws Exception
	{
		texto = texto.replaceAll("[ÂÀÁÄÃ]", "A");
		texto = texto.replaceAll("[âãàáä]", "a");
		texto = texto.replaceAll("[ÊÈÉË]", "E");
		texto = texto.replaceAll("[êèéë]", "e");
		texto = texto.replaceAll("ÎÍÌÏ", "I");
		texto = texto.replaceAll("îíìï", "i");
		texto = texto.replaceAll("[ÔÕÒÓÖ]", "O");
		texto = texto.replaceAll("[ôõòóö]", "o");
		texto = texto.replaceAll("[ÛÙÚÜ]", "U");
		texto = texto.replaceAll("[ûúùü]", "u");
		texto = texto.replaceAll("Ç", "C");
		texto = texto.replaceAll("ç", "c");
		texto = texto.replaceAll("[ýÿ]", "y");
		texto = texto.replaceAll("Ý", "Y");
		texto = texto.replaceAll("ñ", "n");
		texto = texto.replaceAll("Ñ", "N");
		texto = texto.replaceAll("['<>\\|/!@#$%*]", "");
		return texto;
	}

	// converte a primeira letra de um frase para maiusculo :firstLetterUppercase
	static String firstLetterUC(String str)
	{
		// Create a char array of given String
		char ch[] = str.toCharArray();
		for (int i = 0; i < str.length(); i++) {

			// If first character of a word is found
			if (i == 0 && ch[i] != ' ' || ch[i] != ' ' && ch[i - 1] == ' ') {

				// If it is in lower-case
				if (ch[i] >= 'a' && ch[i] <= 'z') {

					// Convert into Upper-case
					ch[i] = (char) (ch[i] - 'a' + 'A');
				}
			}

			// If apart from first character
			// Any one is in Upper-case
			else if (ch[i] >= 'A' && ch[i] <= 'Z')

				// Convert into Lower-Case
				ch[i] = (char) (ch[i] + 'a' - 'A');
		}

		// Convert the char array to equivalent String
		String st = new String(ch);
		return st;
	}

	public static String removeNaoNumericos(String texto, char... caracteresIgnorados)
	{

		if (texto == null) {
			return null;
		}

		StringBuffer strBuff = new StringBuffer();
		char c;

		for (int i = 0; i < texto.length(); i++) {
			c = texto.charAt(i);

			if (Character.isDigit(c))
				strBuff.append(c);
			else {
				for (int j = 0; j < caracteresIgnorados.length; j++) {
					if (caracteresIgnorados[j] == c)
						strBuff.append(c);
				}
			}
		}
		return strBuff.toString();
	}

}