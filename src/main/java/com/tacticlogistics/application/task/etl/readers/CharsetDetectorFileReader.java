package com.tacticlogistics.application.task.etl.readers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.any23.encoding.TikaEncodingDetector;
import org.springframework.stereotype.Component;

@Component
public class CharsetDetectorFileReader implements Reader<File, String> {

	@Override
	public String read(File input) throws IOException {
		Charset charset = null;
		try (InputStream is = new FileInputStream(input)) {
			charset = guessCharset(is);
		}

		if (charset != null) {
			try (InputStreamReader reader = new InputStreamReader(new FileInputStream(input), charset)) {
				StringBuilder sb = new StringBuilder();
				int c = 0;
				while ((c = reader.read()) != -1) {
					if (c != 65279) {
						sb.append((char) c);
					}
				}
				return sb.toString();
			}
		}
		return null;
	}

	public static Charset guessCharset(InputStream is) throws IOException {
		return Charset.forName(new TikaEncodingDetector().guessEncoding(is));
	}
}
