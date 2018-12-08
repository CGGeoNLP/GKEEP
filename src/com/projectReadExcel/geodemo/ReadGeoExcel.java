package com.projectReadExcel.geodemo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.projectReadExcel.Common;
import com.projectReadExcel.Util;

public class ReadGeoExcel {
	
	 static  String SetUpNUll = "NAN";
	
	 /**
     * read the Excel file
     * @param path the path of the Excel file
     * @return
     * @throws IOException
     */
    public List<GeoOntology> readExcel(String path) throws IOException {
        if (path == null || Common.EMPTY.equals(path)) {
            return null;
        } else {
            String postfix = Util.getPostfix(path);
            if (!Common.EMPTY.equals(postfix)) {
                if (Common.OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
                    return readXls(path);
                } else if (Common.OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
                    return readXlsx(path);
                }
            } else {
                System.out.println(path + Common.NOT_EXCEL_FILE);
            }
        }
        return null;
    }

    /**
     * Read the Excel 2010
     * @param path the path of the excel file
     * @return
     * @throws IOException
     */
    public List<GeoOntology> readXlsx(String path) throws IOException {
        System.out.println(Common.PROCESSING + path);
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        GeoOntology geoOntology = null;
        List<GeoOntology> list = new ArrayList<GeoOntology>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                	geoOntology = new GeoOntology();
                    XSSFCell code = xssfRow.getCell(0);
                    XSSFCell broader_term =xssfRow.getCell(1);
                    XSSFCell where = xssfRow.getCell(2);
                    XSSFCell define = xssfRow.getCell(3);
                	XSSFCell equivalence = xssfRow.getCell(4);
                    XSSFCell used_For = xssfRow.getCell(5);
                    XSSFCell related = xssfRow.getCell(6);
                    XSSFCell abbreviation = xssfRow.getCell(7);
                    XSSFCell english_name = xssfRow.getCell(8);
                    XSSFCell english_Equivalence = xssfRow.getCell(9);
                    XSSFCell note = xssfRow.getCell(10);
                    XSSFCell remark = xssfRow.getCell(11);
                    
                    geoOntology.setCode(getValue(code));
                    geoOntology.setBroader_Term(getValue(broader_term));
                    geoOntology.setWhere(getValue(where ));
                    geoOntology.setDefine(getValue(define));
                    geoOntology.setEquivalence(getValue(equivalence));
                    geoOntology.setUsed_For(getValue(used_For));
                    geoOntology.setRelated(getValue(related));
                    geoOntology.setAbbreviation(getValue(abbreviation));
                    geoOntology.setEnglish_name(getValue(english_name));
                    geoOntology.setEnglish_Equivalence(getValue(english_Equivalence));
                    geoOntology.setNote(getValue(note));
                    geoOntology.setRemark(getValue(remark ));
                    
                    list.add(geoOntology);
                }
            }
        }
        return list;
    }

    /**
     * Read the Excel 2003-2007
     * @param path the path of the Excel
     * @return
     * @throws IOException
     */
    public List<GeoOntology> readXls(String path) throws IOException {
        System.out.println(Common.PROCESSING + path);
        InputStream is = new FileInputStream(path);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        GeoOntology geoOntology = null;
        List<GeoOntology> list = new ArrayList<GeoOntology>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                 	geoOntology = new GeoOntology();
                 	
                 	
                 	HSSFCell code = hssfRow.getCell(0);
                 	if(code!= null)
                        geoOntology.setCode(getValue(code));
                 	else {
                        geoOntology.setCode(SetUpNUll);
					}

                 	HSSFCell broader_term = hssfRow.getCell(1);
                 	if (broader_term != null) {
                        geoOntology.setBroader_Term(getValue(broader_term));
					} else {
						 geoOntology.setBroader_Term(SetUpNUll);
					}
                 	
                 	HSSFCell name = hssfRow.getCell(2);
                 	if (name != null) {
                        geoOntology.setName(getValue(name ));
					} else {
	                    geoOntology.setName(SetUpNUll);
					}
                 	                 	                	
                 	HSSFCell where = hssfRow.getCell(3);
                 	if (where != null) {
                        geoOntology.setWhere(getValue(where ));
					} else {
	                    geoOntology.setWhere(SetUpNUll);
					}
                 	
                 	HSSFCell define = hssfRow.getCell(4);
                 	if (define != null) {
                        geoOntology.setDefine(getValue(define));
					} else {
	                    geoOntology.setDefine(SetUpNUll);
					}
                 	
                 	HSSFCell equivalence = hssfRow.getCell(5);
                 	if (equivalence != null) {
                        geoOntology.setEquivalence(getValue(equivalence));
					} else {
	                    geoOntology.setEquivalence(SetUpNUll);
					}
                 	
                 	
                 	HSSFCell used_For = hssfRow.getCell(6);
                 	if (used_For != null) {
                        geoOntology.setUsed_For(getValue(used_For));
					} else {
	                    geoOntology.setUsed_For(SetUpNUll);
					}
                 	
                 	HSSFCell related = hssfRow.getCell(7);
                 	if (related != null) {
                        geoOntology.setRelated(getValue(related));
					} else {
	                    geoOntology.setRelated(SetUpNUll);
					}
                 	
                 	
                 	HSSFCell abbreviation = hssfRow.getCell(8);
                 	if (abbreviation != null) {
                        geoOntology.setAbbreviation(getValue(abbreviation));

					} else {
	                    geoOntology.setAbbreviation(SetUpNUll);

					}
                 	
                 	HSSFCell english_name = hssfRow.getCell(9);
                 	if (english_name != null ) {
                        geoOntology.setEnglish_name(getValue(english_name));
					} else {
	                    geoOntology.setEnglish_name(SetUpNUll);
					}
                 	
                 	
                 	HSSFCell english_Equivalence = hssfRow.getCell(10);
                 	if (english_Equivalence != null) {
                 		  geoOntology.setEnglish_Equivalence(getValue(english_Equivalence));
					} else {
						  geoOntology.setEnglish_Equivalence(SetUpNUll);
					}
                 	
                 	HSSFCell note = hssfRow.getCell(11);
                 	if (note != null) {
                        geoOntology.setNote(getValue(note));
					} else {
	                    geoOntology.setNote(SetUpNUll);

					}
                 	
                 	HSSFCell remark = hssfRow.getCell(11);
                    if (remark != null) {
                    	 geoOntology.setRemark(getValue(remark ));
					} else {
						 geoOntology.setRemark(SetUpNUll);
					}
                   
                    
                    list.add(geoOntology);
                	
                }
            }
        }
        return list;
    }

    @SuppressWarnings("static-access")
    private String getValue(XSSFCell xssfRow) {
        if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfRow.getBooleanCellValue());
        } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
            return String.valueOf(xssfRow.getNumericCellValue());
        } else {
            return String.valueOf(xssfRow.getStringCellValue());
        }
    }

    @SuppressWarnings("static-access")
    private String getValue(HSSFCell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }

}
