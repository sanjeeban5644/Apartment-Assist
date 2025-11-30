package com.sanjeeban.AdminService.helper;

import com.netflix.discovery.converters.Auto;
import com.sanjeeban.AdminService.entity.Resident;
import com.sanjeeban.AdminService.entity.ResidentKey;
import com.sanjeeban.AdminService.service.ResidentService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelHelper {

    private static ResidentService residentService;

    @Autowired
    public ExcelHelper(ResidentService residentService){
        this.residentService = residentService;
    }

    public  static boolean checkExcelFormat(MultipartFile file){
        String contentType= file.getContentType();

        return contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }


    // excel to list of products

    public static List<Resident> convertExcelToListOfResidents(InputStream inputStream){
        List<Resident> list = new ArrayList<>();

        try{
           XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
           XSSFSheet sheet = workbook.getSheet("Residents");

           int rowNumber = 0;

           Iterator<Row> iterator = sheet.iterator();

           while (iterator.hasNext()){
               Row row = iterator.next();
               if(rowNumber==0){
                   rowNumber++; continue;
               }

               Iterator<Cell> cells = row.iterator();

               int cellId = 0;
               ResidentKey residentKey = new ResidentKey();
               Resident resident = new Resident();
               while(cells.hasNext()){
                   Cell currCell = cells.next();

                   switch (cellId)
                   {
                       case 0 :
                           residentKey.setBlockNumber(currCell.getStringCellValue());
                           break;
                       case 1:
                           residentKey.setFloorNumber(currCell.getStringCellValue());
                           break;
                       case 2:
                           residentKey.setApartmentNumber(currCell.getStringCellValue());
                           break;
                       case 3:
                           resident.setFirstName(currCell.getStringCellValue());
                           break;
                       case 4:
                           resident.setLastName(currCell.getStringCellValue());
                           break;
                       case 5:
                           resident.setFamilyNumber(getCellValueAsString(currCell));
                           break;
                       default:
                           break;
                   }
                   cellId++;
               }
               resident.setResidentId(residentKey);
               resident.setResidentUniqueId(residentService.generateNewUniqueIdForResident(residentKey.getBlockNumber(), residentKey.getFloorNumber(),residentKey.getApartmentNumber()));
               list.add(resident);
           }



        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }


    private static String getCellValueAsString(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();

            case NUMERIC:
                // For numbers like 1001 → "1001"
                // If it's a date, detect and format:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue()
                            .toLocalDate()
                            .toString();
                }
                return String.valueOf((long) cell.getNumericCellValue());

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            case FORMULA:
                return cell.getCellFormula();

            case BLANK:
            default:
                return "";
        }
    }





}
