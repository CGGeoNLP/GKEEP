package com.projectReadExcel.geodemo;

import static java.lang.Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS;
import static java.lang.Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS;
import static java.lang.Character.UnicodeBlock.CJK_RADICALS_SUPPLEMENT;
import static java.lang.Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS;
import static java.lang.Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A;
import static java.lang.Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import com.projectReadExcel.Common;
import com.projectReadExcel.ReadExcel;
import com.projectReadExcel.Student;

public class DemoGeoExcel {
	
	
	//判断是否包括中文
	/**
	 * 
	 * @param checkChar 传入字符
	 * @return
	 */
	 public static boolean checkCharContainChinese(char checkChar){  
	        Character.UnicodeBlock ub = Character.UnicodeBlock.of(checkChar);  
	        if(CJK_UNIFIED_IDEOGRAPHS == ub || CJK_COMPATIBILITY_IDEOGRAPHS == ub || CJK_COMPATIBILITY_FORMS == ub ||  
	                CJK_RADICALS_SUPPLEMENT == ub || CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A == ub || CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B == ub){  
	            return true;  
	        }  
	        return false;  
	  } 
	 
	 
	 public static String processOntologyGetChinese(String temp) throws IOException{
		 
		 String  tempTxt = null ;//用于生成新的字符串，写入到txt中
		 char[] checkChars = temp.toCharArray();
			for (int i = 0; i < checkChars.length; i++) {
				char checkChar = checkChars[i];
				if (checkCharContainChinese(checkChar)) {//如果包括中文
					tempTxt = tempTxt + checkChar;//将中文保留，其他的不管
				}
			}		
	
     	
		 return tempTxt;
	 }
	
	
	public static void main(String[] args) throws IOException {
		String excel2003_2007 = Common.STUDENT_INFO_XLS_PATH;
	    String excel2010 = Common.STUDENT_INFO_XLSX_PATH;
	    
	    System.out.println("==excel2003_2007==" + excel2003_2007);
	    System.out.println("==excel2010==" + excel2010);

	    
	    
	    // read the 2003-2007 excel
	    List<GeoOntology> list = new ReadGeoExcel().readExcel(excel2003_2007);
	    System.out.println("countlist = " + list.size());
		String newTxtString = null ;
	    if (list != null ) {
	        for (GeoOntology geoOntology : list) {
	        	
				newTxtString = newTxtString + "\r\n" + processOntologyGetChinese(geoOntology.getCode())
						+"\r\n"+processOntologyGetChinese(geoOntology.getBroader_Term())
						+"\r\n"+processOntologyGetChinese(geoOntology.getName())
						+"\r\n"+processOntologyGetChinese(geoOntology.getWhere())
						+"\r\n"+processOntologyGetChinese(geoOntology.getDefine())
						+"\r\n"+processOntologyGetChinese(geoOntology.getEquivalence())
						+"\r\n"+processOntologyGetChinese(geoOntology.getUsed_For())
						+"\r\n"+processOntologyGetChinese(geoOntology.getRelated())
						+"\r\n"+processOntologyGetChinese(geoOntology.getAbbreviation())
						+"\r\n"+processOntologyGetChinese(geoOntology.getEnglish_name())
						+"\r\n"+processOntologyGetChinese(geoOntology.getEnglish_Equivalence())
						+"\r\n"+processOntologyGetChinese(geoOntology.getNote())
						+"\r\n"+processOntologyGetChinese(geoOntology.getRemark());
				// 处理字符串，生成新的文件
				Writer w = new FileWriter("e:/NewReadMe.txt");
				BufferedWriter buffWriter = new BufferedWriter(w);
				buffWriter.write(newTxtString);
				buffWriter.close();
				w.close();	        	
	        	
/*	            System.out.println("code. : " + geoOntology.getCode() + 
	            		", Broader_Term : " + geoOntology.getBroader_Term() +
	            		", Name : " + geoOntology.getName()+
	            		", Where : " + geoOntology.getWhere()+
	            		", Define : " + geoOntology.getDefine()+
	            		", Equivalence : " + geoOntology.getEquivalence()+
	            		", Used_For : " + geoOntology.getUsed_For()+
	            		", Related : " + geoOntology.getRelated()+
	            		", Abbreviation : " + geoOntology.getAbbreviation()+
	            		", English_name : " + geoOntology.getEnglish_name()+
	            		", English_Equivalence : " + geoOntology.getEnglish_Equivalence()+
	            		", Note : " + geoOntology.getNote()+   
	            		", Remark : " + geoOntology.getRemark());*/
	        }
	    }
	    System.out.println("=====处理完毕=====");
	    // read the 2010 excel
	   /* List<GeoOntology> list1 = new ReadGeoExcel().readExcel(excel2010);
	    if (list1 != null) {
	        for (GeoOntology geoOntology: list1) {
	            System.out.println("code. : " + geoOntology.getCode() + 
	            		", Broader : " + geoOntology.getBroader() +
	            		", Term : " + geoOntology.getTerm() + 
	            		", Name : " + geoOntology.getName()+
	            		", Where : " + geoOntology.getWhere()+
	            		", Define : " + geoOntology.getDefine()+
	            		", Equivalence : " + geoOntology.getEquivalence()+
	            		", Used_For : " + geoOntology.getUsed_For()+
	            		", Related : " + geoOntology.getRelated()+
	            		", Abbreviation : " + geoOntology.getAbbreviation()+
	            		", English_name : " + geoOntology.getEnglish_name()+
	            		", English_Equivalence : " + geoOntology.getEnglish_Equivalence()+
	            		", Note : " + geoOntology.getNote()+   
	            		", Remark : " + geoOntology.getRemark());
	        }
	    }*/
	}


}
