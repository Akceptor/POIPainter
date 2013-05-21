import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class POIWrite {

    public HSSFColor setColor(HSSFWorkbook workbook, byte r, byte g, byte b) {
        HSSFPalette palette = workbook.getCustomPalette();
        HSSFColor hssfColor = null;
        try {
            hssfColor = palette.findSimilarColor(r, g, b);
            if (hssfColor == null) {
                System.err.println("null " + r + " " + g + " " + b);
                palette.setColorAtIndex(HSSFColor.RED.index, r, g, b);
                hssfColor = palette.getColor(HSSFColor.RED.index);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hssfColor;
    }

    public void write(Map<String, Object[]> data) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Picture");
        //sheet.setDefaultColumnWidth(1);
        //using sheet.setColumnWidth for every cell instead
        //sheet.setDefaultRowHeightInPoints(1);
        //using row.setHeight foe every row instead
        Map<String, HSSFCellStyle> colorToStyle = new HashMap<String, HSSFCellStyle>();
        HSSFCellStyle style;

        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            row.setHeight((short) 50);
            Object[] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                sheet.setColumnWidth(cellnum, 100);
                Cell cell = row.createCell(cellnum++);
                RGBColor rgb = (RGBColor) obj;
                try {
                    style = colorToStyle.get(rgb.toString());
                    cell.setCellStyle(style);
                } catch (Exception e) {
                    style = workbook.createCellStyle();
                    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                    style.setFillForegroundColor(setColor(workbook, rgb.getR(), rgb.getG(), rgb.getB()).getIndex());
                    colorToStyle.put(rgb.toString(), style);
                    cell.setCellStyle(style);
                }

            }
        }

        try {
            FileOutputStream out =
                    new FileOutputStream(new File("C:\\picture.xls"));
            workbook.write(out);
            out.close();
            System.out.println("Excel written successfully... Check C:\\picture.xls");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
