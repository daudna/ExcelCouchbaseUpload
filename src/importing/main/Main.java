package importing.main;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;

import java.util.Scanner;




import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



import importing.main.convert.ExcelToJson;
import importing.main.upload.ConnectAndUpload;
import importing.main.upload.Test;
import importing.main.zip.Zip;



public class Main {
	public static void main(String[] args) throws IOException{
		ExcelToJson excelToJson = new ExcelToJson();
//		Zip zip = new Zip();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please give the fullpath of your Excel (.xlsx) File: ");
		
		String filepath = scanner.nextLine();
		File myFile = new File(filepath);
		FileInputStream fis = new FileInputStream(myFile);
		
		XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
		XSSFSheet mySheet = myWorkBook.getSheetAt(0);
		
		String[] split = filepath.split("\\\\");
		String filename = split[split.length - 1];
		String foldername = filename.substring(0, filename.length()-5);
		
		
		try {
			excelToJson.convert(mySheet, foldername);
//			zip.zip(foldername);
//			upload.connectAndUpload(folder);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		scanner.close();
		myWorkBook.close();
	}
	
}
