package br.com.trier.springmatutino.Utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
	
	private static DateTimeFormatter formatacao = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public static ZonedDateTime strToZonedDateTime(String dateStr) {
		DateTimeFormatter formatacao = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime localDate = LocalDateTime.parse(dateStr, formatacao);
		return ZonedDateTime.of(localDate, ZoneId.systemDefault());
	}

	public static String zonedDateTimeToStr(ZonedDateTime data) {
		return formatacao.format(data);
	}
}
