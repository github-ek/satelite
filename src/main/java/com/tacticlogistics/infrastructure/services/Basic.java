package com.tacticlogistics.infrastructure.services;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Basic {
	public static <T> T coalesce(T one, T two) {
		return one != null ? one : two;
	}

	public static String substringSafe(String string, int beginIndex, int endIndex) {
		return string.substring(beginIndex, Math.min(string.length(), endIndex));
	}

	// --------------------------------------------------------------------------------------------
	public static LocalDate toFecha(String value, LocalDate defaultValue, DateTimeFormatter format)
			throws ParseException {
		value = Basic.coalesce(value, "");
		if (value.trim().isEmpty()) {
			return defaultValue;
		} else {
			return parseFecha(value, format);
		}
	}

	public static LocalDateTime toFechaHora(String value, LocalDateTime defaultValue, DateTimeFormatter format)
			throws ParseException {
		value = Basic.coalesce(value, "");
		if (value.trim().isEmpty()) {
			return defaultValue;
		} else {
			return parseFechaHora(value, format);
		}
	}

	public static LocalTime toHora(String value, LocalTime defaultValue, DateTimeFormatter format)
			throws ParseException {
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
				// Number n = format.parse(texto);
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

	private static LocalDate parseFecha(String texto, DateTimeFormatter format) throws ParseException {
		LocalDate value = null;

		if (texto != null) {
			if (!texto.isEmpty()) {
				value = LocalDate.parse(texto, format);
			}
		}
		return value;
	}

	private static LocalDateTime parseFechaHora(String texto, DateTimeFormatter format) throws ParseException {
		LocalDateTime value = null;

		if (texto != null) {
			if (!texto.isEmpty()) {
				value = LocalDateTime.parse(texto, format);
			}
		}
		return value;
	}

	private static LocalTime parseHora(String texto, DateTimeFormatter format) throws ParseException {
		LocalTime value = null;

		if (texto != null) {
			if (!texto.isEmpty()) {
				value = LocalTime.parse(texto, format);
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
}
