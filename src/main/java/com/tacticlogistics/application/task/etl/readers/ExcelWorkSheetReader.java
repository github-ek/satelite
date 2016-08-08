package com.tacticlogistics.application.task.etl.readers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.extractor.XSSFExcelExtractor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.tacticlogistics.infrastructure.services.Basic;

@Component
public class ExcelWorkSheetReader implements Reader<File, String> {

	private String workSheetName;

	public String getWorkSheetName() {
		return workSheetName;
	}

	public void setWorkSheetName(String value) {
		this.workSheetName = Basic.coalesce(value, "");
	}

	@Override
	public String read(File archivo) throws IOException {
		List<String> names = new ArrayList<>();
		InputStream in = new FileInputStream(archivo.getAbsolutePath());
		Workbook workbook = null;

		try {
			workbook = WorkbookFactory.create(in);
		} catch (EncryptedDocumentException e1) {
			throw new RuntimeException(e1);
		} catch (InvalidFormatException e1) {
			throw new RuntimeException(e1);
		}

		int size = workbook.getNumberOfSheets();
		for (int i = 0; i < size; i++) {
			String name = workbook.getSheetName(i);
			if (!name.equalsIgnoreCase(getWorkSheetName())) {
				names.add(name);
			}
		}

		for (String name : names) {
			workbook.removeSheetAt(workbook.getSheetIndex(name));
		}

		String texto = "";

		if (workbook instanceof HSSFWorkbook) {
			try (ExcelExtractor extractor = new ExcelExtractor((HSSFWorkbook) workbook)) {
				extractor.setFormulasNotResults(false);
				extractor.setIncludeSheetNames(false);
				texto = extractor.getText();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			try (XSSFExcelExtractor extractor = new XSSFExcelExtractor((XSSFWorkbook) workbook)) {
				extractor.setFormulasNotResults(false);
				extractor.setIncludeSheetNames(false);
				texto = extractor.getText();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		workbook.close();

		return texto;
	}

	public String read2(File archivo,final int NUMERO_MAXIMO_COLUMNAS) throws IOException {
		List<String> names = new ArrayList<>();
		InputStream in = new FileInputStream(archivo.getAbsolutePath());
		Workbook workbook = null;

		try {
			workbook = WorkbookFactory.create(in);
		} catch (EncryptedDocumentException e1) {
			throw new RuntimeException(e1);
		} catch (InvalidFormatException e1) {
			throw new RuntimeException(e1);
		}

		int size = workbook.getNumberOfSheets();
		for (int i = 0; i < size; i++) {
			String name = workbook.getSheetName(i);
			if (!name.equalsIgnoreCase(getWorkSheetName())) {
				names.add(name);
			}
		}

		for (String name : names) {
			workbook.removeSheetAt(workbook.getSheetIndex(name));
		}
		
		Sheet sheet = workbook.getSheetAt(0);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		FormulaEvaluator formulaEval = workbook.getCreationHelper().createFormulaEvaluator();
		
		int rowStart = Math.min(0, sheet.getFirstRowNum());
		int rowEnd = Math.max(100, sheet.getLastRowNum());
		StringBuffer sb = new StringBuffer();
		
		for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
			Row r = sheet.getRow(rowNum);
			if (r == null) {
				// This whole row is empty
				// Handle it as needed
				continue;
			}

			//int lastColumn = Math.max(r.getLastCellNum(), NUMERO_MAXIMO_COLUMNAS);
			int lastColumn = NUMERO_MAXIMO_COLUMNAS;
			
			for (int cn = 0; cn < lastColumn; cn++) {
				Cell c = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
				
				sb.append(getCellValueAsString(sdf, formulaEval, c));
				
				if (cn + 1 < lastColumn) {
					sb.append("\t ");
				} else {
					sb.append("\n");
				}
			}
		}

		return sb.toString();
	}

	private String getCellValueAsString(SimpleDateFormat sdf, FormulaEvaluator formulaEval, Cell c) {
		String value;
		if (c == null) {
			// The spreadsheet is empty in this cell
			value = "";
		} else {
			// Do something useful with the cell's contents
			switch (c.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				value = c.getRichStringCellValue().getString().replaceAll("[\\t\\n\\r]+", " ");
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(c)) {
					value = sdf.format(c.getDateCellValue());
				} else {
					BigDecimal bigDecimalValue = BigDecimal.valueOf(c.getNumericCellValue());
					BigDecimal fractionalPart = bigDecimalValue.remainder( BigDecimal.ONE );

					if(fractionalPart.compareTo(BigDecimal.ZERO) == 0){
						value = String.valueOf(bigDecimalValue.longValue());
					}else{
						value = String.valueOf(bigDecimalValue.toPlainString());
					}
				}
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				value = String.valueOf(c.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				value = formulaEval.evaluate(c).formatAsString();
				break;
			case Cell.CELL_TYPE_BLANK:
			case Cell.CELL_TYPE_ERROR:
				value = "";
				break;
			default:
				value = "";
				System.out.println(c.getAddress().formatAsString() + " = " + c.getCellType());
			}
		}
		return value;
	}
}
