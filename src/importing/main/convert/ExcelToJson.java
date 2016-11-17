package importing.main.convert;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.ss.usermodel.*;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;

import importing.main.upload.ConnectAndUpload;

public class ExcelToJson {
	static List<Object> spaltenName;
	@SuppressWarnings("deprecation")
	public void convert(XSSFSheet mySheet, String filename) throws Exception {		
		ConnectAndUpload upload = new ConnectAndUpload();
		Iterator<Row> rowIterator = mySheet.iterator();
		int rowPos = 1;
		while(rowIterator.hasNext()){
			
			Row row = rowIterator.next();
			
			if(spaltenName == null){
				spaltenName = new ArrayList<Object>();
				Iterator<Cell> cellIterator = row.cellIterator();
				
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        spaltenName.add(cell.getStringCellValue());
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        spaltenName.add(cell.getNumericCellValue());
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        spaltenName.add(cell.getBooleanCellValue());
                        break;
                    default :
                 
                    }
				}
				
			}else{
//				JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
				JsonObject content = JsonObject.empty();
				
				for(int x=0;x<spaltenName.size();x++){
					if(row.getCell(x) == null){
//						jsonBuilder.add((String)spaltenName.get(x), "");
					}else{
						Cell cell = row.getCell(x);
						
						switch (cell.getCellType()) {
	                    case Cell.CELL_TYPE_STRING:
//	                        jsonBuilder.add((String)spaltenName.get(x), cell.getStringCellValue());
	                    	content.put((String)spaltenName.get(x), cell.getStringCellValue());
	                        break;
	                    case Cell.CELL_TYPE_NUMERIC:
//	                    	jsonBuilder.add((String)spaltenName.get(x), cell.getNumericCellValue());
	                    	content.put((String)spaltenName.get(x), cell.getNumericCellValue());
	                        break;
	                    case Cell.CELL_TYPE_BOOLEAN:
//	                    	jsonBuilder.add((String)spaltenName.get(x), cell.getBooleanCellValue());
	                    	content.put((String)spaltenName.get(x), cell.getBooleanCellValue());
	                        break;
	                    case Cell.CELL_TYPE_BLANK:
//	                    	jsonBuilder.add((String)spaltenName.get(x), "");
	                    	content.put((String)spaltenName.get(x), "");
	                    default :
						}
					}
				}
				
//				JsonObject jsonObject = jsonBuilder.build();
//				OutputStream os = new FileOutputStream(filename + "\\" + filename + "_" + rowPos + ".json");
//				JsonWriter jsonWriter = Json.createWriter(os);
				JsonDocument doc = JsonDocument.create(filename + "_" + rowPos, content);
				
				upload.connectAndUpload(doc);
//				jsonWriter.writeObject(jsonObject);
//				jsonWriter.close();
						
				rowPos++;
			}
		}
	}
}
