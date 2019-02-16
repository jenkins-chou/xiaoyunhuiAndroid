package com.jenking.xiaoyunhui.tools;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.jenking.xiaoyunhui.models.base.ProjectBean;
import com.jenking.xiaoyunhui.models.main.scoreForExcel.AllScoreIntegral;
import com.jenking.xiaoyunhui.models.main.scoreForExcel.ScoreExcelModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelTools {
    private static WritableFont arial14font = null;

    private static WritableCellFormat arial14format = null;
    private static WritableFont arial10font = null;
    private static WritableCellFormat arial10format = null;
    private static WritableFont arial12font = null;
    private static WritableCellFormat arial12format = null;
    private final static String UTF8_ENCODING = "UTF-8";


    /**
     * 单元格的格式设置 字体大小 颜色 对齐方式、背景颜色等...
     */
    private static void format() {
        try {
            arial14font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
            arial14font.setColour(jxl.format.Colour.LIGHT_BLUE);
            arial14format = new WritableCellFormat(arial14font);
            arial14format.setAlignment(jxl.format.Alignment.CENTRE);
            arial14format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial14format.setBackground(jxl.format.Colour.VERY_LIGHT_YELLOW);

            arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            arial10format = new WritableCellFormat(arial10font);
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            arial10format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial10format.setBackground(Colour.GRAY_25);

            arial12font = new WritableFont(WritableFont.ARIAL, 10);
            arial12format = new WritableCellFormat(arial12font);
            //对齐格式
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            //设置边框
            arial12format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化Excel
     *
     * @param fileName 导出excel存放的地址（目录）
     * @param colName excel中包含的列名（可以有多个）
     */
    public static void initExcel(String fileName, String[] colName) {
        format();
        WritableWorkbook workbook = null;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            workbook = Workbook.createWorkbook(file);
            //设置表格的名字
            WritableSheet sheet = workbook.createSheet("账单", 0);
            //创建标题栏
            sheet.addCell((WritableCell) new Label(0, 0, fileName, arial14format));
            for (int col = 0; col < colName.length; col++) {
                sheet.addCell(new Label(col, 0, colName[col], arial10format));
            }
            //设置行高
            sheet.setRowView(0, 340);
            workbook.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> void writeObjListToExcel(List<T> objList, String fileName, Context c) {

        if (objList != null && objList.size() > 0) {
            WritableWorkbook writebook = null;
            InputStream in = null;
            try {
                WorkbookSettings setEncode = new WorkbookSettings();
                setEncode.setEncoding(UTF8_ENCODING);
                in = new FileInputStream(new File(fileName));
                Workbook workbook = Workbook.getWorkbook(in);
                writebook = Workbook.createWorkbook(new File(fileName), workbook);
                WritableSheet sheet = writebook.getSheet(0);

                for (int j = 0; j < objList.size(); j++) {
//                    String[] title = {"比赛名称", "比赛时间", "比赛地点", "比赛简介", "比赛说明","参赛者","参赛者成绩"};
                    List<String> list = new ArrayList<>();
                    if (objList.get(j) instanceof ScoreExcelModel){
                        ScoreExcelModel scoreExcelModel = (ScoreExcelModel) objList.get(j);
                        Log.e("scoreExcelModel",scoreExcelModel.toString());
                        list.add(scoreExcelModel.getMatch_title());
                        list.add(scoreExcelModel.getMatch_time());
                        list.add(scoreExcelModel.getMatch_address());
                        list.add(scoreExcelModel.getMatch_abstract());
                        list.add(scoreExcelModel.getMatch_detail());
                        list.add(scoreExcelModel.getUser_name());
                        list.add(scoreExcelModel.getScore_value());
                        list.add(scoreExcelModel.getScore_unit());
                        list.add(scoreExcelModel.getScore_integral());
                    }else if (objList.get(j) instanceof AllScoreIntegral){
                        String[] title = {"比赛名称", "比赛时间", "比赛地点", "团队名称", "参赛者名称","参赛者成绩","成绩单位","得分"};
                        AllScoreIntegral allScoreIntegral = (AllScoreIntegral) objList.get(j);
                        list.add(allScoreIntegral.getMatch_title());
                        list.add(StringUtil.getStrTime(allScoreIntegral.getMatch_time(),"yyyy-MM-dd HH:mm:ss"));
                        list.add(allScoreIntegral.getMatch_address());
                        list.add(allScoreIntegral.getTeam_name());
                        list.add(allScoreIntegral.getUser_name());
                        list.add(allScoreIntegral.getScore_value());
                        list.add(allScoreIntegral.getScore_unit());
                        list.add(allScoreIntegral.getScore_integral());
                    }

                    for (int i = 0; i < list.size(); i++) {
                        sheet.addCell(new Label(i, j + 1, list.get(i), arial12format));
                        if (list.get(i).length() <= 4) {
                            //设置列宽
                            sheet.setColumnView(i, list.get(i).length() + 8);
                        } else {
                            //设置列宽
                            sheet.setColumnView(i, list.get(i).length() + 5);
                        }
                    }
                    //设置行高
                    sheet.setRowView(j + 1, 350);
                }

                writebook.write();
//                Toast.makeText(c, "导出Excel成功", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (writebook != null) {
                    try {
                        writebook.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
}
