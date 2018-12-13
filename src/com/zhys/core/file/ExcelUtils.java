package com.zhys.core.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.zhys.core.util.StringUtil;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelUtils {

	public static List<String[]> read(File file) throws BiffException, IOException {
		List<String[]> retVal = new ArrayList<>();
		InputStream is = new FileInputStream(file);
		Workbook workbook = Workbook.getWorkbook(is);
		Sheet sheet = workbook.getSheet(0);
		int columnSize = sheet.getColumns();
		for(int i=1; i<sheet.getRows(); i++) {
			String[] arr = new String[columnSize];
			for(int j=0; j<sheet.getColumns(); j++) {
				Cell cell = sheet.getCell(j, i);
				arr[j] = cell.getContents();
			}
			if(!isEmptyRow(arr)) {
				retVal.add(arr);
			}
		}
		return retVal;
	}
	
	public static boolean isEmptyRow(String[] arr) {
		boolean isEmpty = true;
		int length = arr.length;
		for(int i=0; i<length; i++) {
			if(StringUtil.isNotEmpty(arr[i])) {
				isEmpty = false;
				break;
			}
		}
		return isEmpty;
	}
	
	public static void main(String[] args) {
		String path = "F:/Test/ebm_drugs.xls";
		File file = new File(path);
		try {
			List<String[]> list = read(file);
			System.out.println("==========="+list.size());
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
