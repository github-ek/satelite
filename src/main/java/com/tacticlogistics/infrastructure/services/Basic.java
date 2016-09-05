package com.tacticlogistics.infrastructure.services;

import java.math.BigDecimal;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Basic {
	public static <T> T coalesce(T one, T two) {
		return one != null ? one : two;
	}

	public static String substringSafe(String string, int beginIndex, int endIndex) {
		return string.substring(beginIndex, Math.min(string.length(), endIndex));
	}

	// --------------------------------------------------------------------------------------------
	public static Date toFecha(String value, Date defaultValue, SimpleDateFormat format) throws ParseException {
		value = Basic.coalesce(value, "");
		if (value.trim().isEmpty()) {
			return defaultValue;
		} else {
			return parseFecha(value, format);
		}
	}

	public static Time toHora(String value, Time defaultValue, SimpleDateFormat format) throws ParseException {
		value = Basic.coalesce(value, "");
		if (value.trim().isEmpty()) {
			return defaultValue;
		} else {
			return parseHora(value, format);
		}
	}

	public static Integer toEntero(String value, Integer defaultValue, DecimalFormat format) throws ParseException {
		value = Basic.coalesce(value, "");
		if (value.trim().isEmpty()) {
			return defaultValue;
		} else {
			return parseEntero(value, format);
		}
	}

	public static BigDecimal toBigDecimal(String value, BigDecimal defaultValue, DecimalFormat format)
			throws ParseException {
		value = Basic.coalesce(value, "");
		if (value.trim().isEmpty()) {
			return defaultValue;
		} else {
			return parseBigDecimal(value, format);
		}
	}

	public static Integer parseEntero(String texto, DecimalFormat format) throws ParseException {
		Integer value = null;

		if (texto != null) {
			if (!texto.isEmpty()) {
				format.setParseBigDecimal(true);
				//Number n = format.parse(texto);
				value = format.parse(texto).intValue();
			}
		}
		return value;
	}

	public static BigDecimal parseBigDecimal(String texto, DecimalFormat format) throws ParseException {
		BigDecimal value = null;

		if (texto != null) {
			if (!texto.isEmpty()) {
				if (format.isParseBigDecimal()) {
					value = (BigDecimal) format.parse(texto);
				}
			}
		}
		return value;
	}

	public static Date parseFecha(String texto, DateFormat format) throws ParseException {
		Date value = null;

		if (texto != null) {
			if (!texto.isEmpty()) {
				value = format.parse(texto);
			}
		}
		return value;
	}

	public static Time parseHora(String texto, DateFormat format) throws ParseException {
		Time value = null;

		if (texto != null) {
			if (!texto.isEmpty()) {
				value = new Time(format.parse(texto).getTime());
			}
		}
		return value;
	}

	// --------------------------------------------------------------------------------------------
	public static String limpiarEspacios(final String texto) {
		StringBuffer sb = new StringBuffer();

		String[] lineas = texto.split("\n");
		for (String s : lineas) {
			sb.append(s.replaceAll("\\s+", " ").trim()).append("\n");
		}

		return sb.toString();
	}

	public static String limpiarCaracterEspecialDeEspacioDeExcel(final String texto) {
		if (texto == null) {
			return texto;
		} else {
			return texto.replace(String.valueOf((char) 160), "");
		}
	}

	public static String ajustarNumero(String texto) {
		return texto.replaceAll("\\$", "").replaceAll("\\,", ";").replaceAll("\\.", ",").replaceAll(";", ".");
	}

	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); // minus number would decrement the days

		// while (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
		// cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
		// cal.add(Calendar.DATE, 1);
		// }

		return cal.getTime();
	}

	public static Date truncate(Date date) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}
}
