package com.brandaoti.escolar.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.brandaoti.escolar.domain.Tabela;


public class ProcessaExcel {
	
	
	public List<Tabela> processar(@ModelAttribute MultipartFile file) throws Exception {
		List<Tabela> tabelas = new ArrayList<Tabela>();
		int linha = 1;
		int coluna = 0;
		try {
			InputStream in = file.getInputStream();
		    XSSFWorkbook workbook = new XSSFWorkbook(in);
		    XSSFSheet sheet = workbook.getSheet("Sheet1");
		    if(sheet == null) {
				sheet = workbook.getSheet("Plan1");
			}
		    if(sheet == null) {
				sheet = workbook.getSheet("Planilha1");
			}
			Iterator<Row> rowIterator = sheet.rowIterator();
			while (rowIterator.hasNext()) {
				coluna = 0;
				Row row = rowIterator.next();
				if (row.getRowNum() == 0) {
					continue;
				}
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					cell.setCellType(CellType.STRING);
					Tabela tabela = new Tabela();
					tabela.setLinha(linha);
					tabela.setConteudo(cell.getStringCellValue());
					tabela.setColuna(coluna);
					tabelas.add(tabela);
					coluna++;
				}
				linha++;
			}
		    in.close();
		    workbook.close();
        } catch (Exception e) {
            System.out.println("ERRO: " + e);
        }
		
		return tabelas;
	}
	
	
	
	
}
