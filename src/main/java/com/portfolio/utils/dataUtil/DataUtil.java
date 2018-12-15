package com.portfolio.utils.dataUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil
{
	public static Date stringToDate(String formato, String dataEntrada)
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formato);
		
		try {
			Date dataSaida = simpleDateFormat.parse(dataEntrada);
			return dataSaida;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
