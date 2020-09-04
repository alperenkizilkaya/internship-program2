package com.etcbase.metadata.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Test18.class);
    private static final String xmlSourceFileName = "source"; //name of source xml file
    private static final String xmlTargetFileName = "target"; //name of target xml file
    private static final String readPath = System.getProperty("user.home") + "/Desktop/"; // xml files should be in Desktop directory
    private static final String writePath = System.getProperty("user.home") + "/Desktop/Outputs/"; // outputs will be written to Desktop/Outputs directory

    public static void main(String[] args) throws IOException {

        LOGGER.info("STARTED CONTROL");
        //Creating the outputs directory on Desktop
        createOutputDirectory();

        //enumValues ​​written to txt file (enumValue.txt)
        firstPart();

        //effectiveTenantIds and connected enumValues are written to txt file with (effectiveTenantId.txt)
        //organizationUnitIds and connected enumValues are written to txt file (orgUnitId.txt)
        secondPart();

        //comparing txt files with target.xml
        //missing values are written to excel file
        //excel format -> "uniqueKey","enumValue","columnName","missingValue"
        thirdPart();

        LOGGER.info("FINISHED CONTROL");

    }

    public static void createOutputDirectory(){

        //Creating the outputs directory on Desktop
        File file = new File(writePath);
        boolean bool = file.mkdir();

    }

    public static void firstPart() throws IOException {
        // reading two xml file to compare in Java program
        FileInputStream fis1 = new FileInputStream(readPath + xmlSourceFileName+".xml");

        // using BufferedReader for improved performance
        BufferedReader source = new BufferedReader(new InputStreamReader(fis1));

        Scanner scXml1 = new Scanner(source);
        String lineXml1;

        List<String> listXml = new ArrayList();

        while (scXml1.hasNextLine()) {

            lineXml1 = scXml1.nextLine();
            listXml.add(lineXml1);

        }

        //Source enumları txt'ye yazma
        String enumValue;

        PrintWriter out = new PrintWriter(new FileWriter(writePath+"enumValue.txt"));

        System.err.println("enumValues;");

        for(int i = 0; i < listXml.size(); i++) {

            if(listXml.get(i).matches("(.*)<enumValue>(.*)")) {

                enumValue = listXml.get(i);
                String[] arrOfEnumValue = enumValue.split(">");

                int lastLetter = arrOfEnumValue[1].indexOf("<");
                enumValue = arrOfEnumValue[1].substring(0,lastLetter);

                System.out.println(enumValue);
                out.write( enumValue + "\r");
            }
        }

        out.close();

    }

    public static void secondPart() throws IOException {

        FileInputStream fis1 = new FileInputStream(readPath+xmlSourceFileName+".xml");

        // using BufferedReader for improved performance
        BufferedReader source = new BufferedReader(new InputStreamReader(fis1));

        Scanner scXml1 = new Scanner(source);
        String lineXml1;

        List<String> listXml = new ArrayList();

        while (scXml1.hasNextLine()) {

            lineXml1 = scXml1.nextLine();
            listXml.add(lineXml1);

        }

        String enumValue;
        String efTenId;

        PrintWriter out = new PrintWriter(new FileWriter(writePath+"effectiveTenantId.txt"));

        System.err.println("______________");
        System.err.println("effectiveTenantIds;");

        for(int i = 0; i < listXml.size(); i++) {

            if(listXml.get(i).matches("(.*)<enumValue>(.*)")) {

                enumValue = listXml.get(i);
                String[] arrOfEnumValue = enumValue.split(">");

                int lastLetter = arrOfEnumValue[1].indexOf("<");
                enumValue = arrOfEnumValue[1].substring(0,lastLetter);

                System.out.println(enumValue);
                out.write( enumValue +":enum"+ "\r");
                for(int j = i+1; j<listXml.size(); j++) {
                    if(listXml.get(j).matches("(.*)<effectiveTenantId>(.*)")) {

                        efTenId = listXml.get(j);
                        String[] arrOfEfTenId = efTenId.split(">");

                        int lastLetterr = arrOfEfTenId[1].indexOf("<");
                        efTenId = arrOfEfTenId[1].substring(0,lastLetterr);

                        System.out.println(efTenId);
                        out.write( efTenId + "\r");
                    }else if(listXml.get(j).matches("(.*)<enumValue>(.*)")){
                        break;
                    }
                }
            }
        }
        out.close();

        System.err.println("______________");
        System.err.println("organizationUnitIds;");

        // organizationUnitId'leri txt'ye yazma
        String orgUnitId;

        PrintWriter orgUnitIdOut = new PrintWriter(new FileWriter(writePath+"orgUnitId.txt"));

        for(int i = 0; i < listXml.size(); i++) {

            if(listXml.get(i).matches("(.*)<enumValue>(.*)")) {

                enumValue = listXml.get(i);
                String[] arrOfEnumValue = enumValue.split(">");

                int lastLetter = arrOfEnumValue[1].indexOf("<");
                enumValue = arrOfEnumValue[1].substring(0,lastLetter);

                System.out.println(enumValue);
                orgUnitIdOut.write( enumValue +":enum"+ "\r");
                for(int j = i+1; j<listXml.size(); j++) {
                    if(listXml.get(j).matches("(.*)<organizationUnitId>(.*)")) {

                        orgUnitId = listXml.get(j);
                        String[] arrOfEfTenId = orgUnitId.split(">");

                        int firstLetter = arrOfEfTenId[1].indexOf("<");
                        orgUnitId = arrOfEfTenId[1].substring(0,firstLetter);

                        System.out.println(orgUnitId);
                        orgUnitIdOut.write( orgUnitId + "\r");
                    }else if(listXml.get(j).matches("(.*)<enumValue>(.*)")){
                        //System.out.println("______________");
                        break;
                    }
                }
            }
        }

        orgUnitIdOut.close();

        System.err.println("______________");
        System.err.println("ParameterLists;");

        // ParameterList'leri txt'ye yazma

        String ParamList = "";

        PrintWriter ParameterListOut = new PrintWriter(new FileWriter(writePath+"ParameterList.txt"));

        //List<String> parameterList = new ArrayList<>();
        for(int i = 0; i < listXml.size(); i++) {

            if(listXml.get(i).matches("(.*)<enumValue>(.*)")) {

                enumValue = listXml.get(i);
                String[] arrOfEnumValue = enumValue.split(">");

                int lastLetter = arrOfEnumValue[1].indexOf("<");
                enumValue = arrOfEnumValue[1].substring(0,lastLetter);

                System.out.println(enumValue);
                ParameterListOut.write( enumValue +":enum"+ "\r");
                for(int j = i+1; j<listXml.size(); j++) {
                    if(listXml.get(j).matches("(.*)<Parameter>(.*)")) {
                        j+=3;
                        for (int a = j; a < listXml.size(); a++) {
                            if (listXml.get(a).matches("(.*)</Parameter>(.*)")) {
                                break;
                            }else if (listXml.get(a).matches("(.*)<parameterValueList>(.*)") || listXml.get(a).matches("(.*)<entry>(.*)")
                                        || listXml.get(a).matches("(.*)</entry>(.*)") || listXml.get(a).matches("(.*)</parameterValueList>(.*)")){
                                continue;
                            }
                            ParamList = listXml.get(a);

                            System.out.println(ParamList);
                            ParameterListOut.write(ParamList + "\r");
                        }
                    }else if(listXml.get(j).matches("(.*)<enumValue>(.*)")){
                        break;
                    }
                }
            }
        }

        ParameterListOut.close();
    }

    public static void thirdPart() throws IOException {
        //excel
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("diffValues");
        FileOutputStream outputStream = new FileOutputStream(writePath+"diffValues.xlsx");

        //enum txt file
        FileInputStream enumValue = new FileInputStream(writePath+"enumValue.txt");
        BufferedReader enumTxt = new BufferedReader(new InputStreamReader(enumValue));
        Scanner scEnumValueTxt = new Scanner(enumTxt);

        String enumLineTxt;
        List<String> enumListTxt = new ArrayList<>();

        while (scEnumValueTxt.hasNextLine()) {
            enumLineTxt = scEnumValueTxt.nextLine();
            enumListTxt.add(enumLineTxt);
        }

        //efectiveTenantId txt file
        FileInputStream txt = new FileInputStream(writePath+"effectiveTenantId.txt");
        BufferedReader idTxt = new BufferedReader(new InputStreamReader(txt));
        Scanner scTxt = new Scanner(idTxt);

        String lineTxt;
        List<String> listTxt = new ArrayList<>();

        while (scTxt.hasNextLine()) {
            lineTxt = scTxt.nextLine();
            listTxt.add(lineTxt);
        }

        //organizationUnitId txt file
        FileInputStream orgUnitId = new FileInputStream(writePath+"orgUnitId.txt");
        BufferedReader orgUnitIdTxt = new BufferedReader(new InputStreamReader(orgUnitId));
        Scanner scOrgUnitId = new Scanner(orgUnitIdTxt);

        String orgUnitLineTxt;
        List<String> orgUnitListTxt = new ArrayList<>();

        while (scOrgUnitId.hasNextLine()) {
            orgUnitLineTxt = scOrgUnitId.nextLine();
            orgUnitListTxt.add(orgUnitLineTxt);
        }

        //parameterList txt file
        FileInputStream parameterTxt = new FileInputStream(writePath+"ParameterList.txt");
        BufferedReader parameterListTxt = new BufferedReader(new InputStreamReader(parameterTxt));
        Scanner scParameter = new Scanner(parameterListTxt);

        String lineParameter;
        List<String> listParameter = new ArrayList<>();

        while (scParameter.hasNextLine()) {
            lineParameter = scParameter.nextLine();
            listParameter.add(lineParameter);
        }

        //target xml
        FileInputStream fis2 = new FileInputStream(readPath+xmlTargetFileName+".xml");
        BufferedReader target = new BufferedReader(new InputStreamReader(fis2));
        Scanner scXml2 = new Scanner(target);

        String lineXml2;
        List<String> listXml2 = new ArrayList();

        while (scXml2.hasNextLine()) {
            lineXml2 = scXml2.nextLine();
            listXml2.add(lineXml2);
        }

        //targetEnum'ları yazma (sıralamalar farklı olabiliyor, bu sorunu çözmek için)
        PrintWriter targetEnum = new PrintWriter(new FileWriter(writePath+"targetEnums.txt"));

        String enumTarget;
        for(int i = 0; i < listXml2.size(); i++) {

            if(listXml2.get(i).matches("(.*)<enumValue>(.*)")) {

                enumTarget = listXml2.get(i);
                String[] arrOfEnumValue = enumTarget.split(">");

                int lastLetter = arrOfEnumValue[1].indexOf("<");
                enumTarget = arrOfEnumValue[1].substring(0,lastLetter);

                System.out.println(enumValue);
                targetEnum.write( enumTarget + "\r");
            }
        }

        targetEnum.close();

        //target ENUM'lardan liste
        FileInputStream targetEnums = new FileInputStream(writePath+"targetEnums.txt");
        BufferedReader targetEnumss = new BufferedReader(new InputStreamReader(targetEnums));
        Scanner targetEnumsss = new Scanner(targetEnumss);

        String lineTargetEnums;
        List<String> listTargetEnums = new ArrayList();

        while (targetEnumsss.hasNextLine()) {
            lineTargetEnums = targetEnumsss.nextLine();
            listTargetEnums.add(lineTargetEnums);
        }

        //target enumları sıralama (source'a göre)
        List<String> cleanListTargetEnum = new ArrayList<>();
        for(int k = 0; k < listTargetEnums.size();k++){
            for(int i = 0 ; i < enumListTxt.size(); i++){
                if(listTargetEnums.get(k).matches(enumListTxt.get(i))) {
                    cleanListTargetEnum.add(listTargetEnums.get(k));
                }
            }
        }

        //creating excel first row
        int rowCount = 0;
        excelFirstRow(sheet,rowCount);

        //starting to compare enum values
        System.err.println("cheking missing enumValues");
        compareEnumValue(listXml2, listTxt, enumListTxt, orgUnitListTxt, listParameter, cleanListTargetEnum,listTargetEnums, sheet);

        workbook.write(outputStream);
    }

    // ENUM COMPARE    (if there is any missing enumValue, all effectiveTenantIds that connected with enumValue, written to excel as missingValue)
    public static void compareEnumValue(List listXml2, List listTxt, List enumListTxt, List orgUnitListTxt, List listParameter, List cleanListTargetEnum, List listTargetEnums ,XSSFSheet sheet) throws IOException{

        int uniqueKey = 1;
        String missingEnum = "";
        int rowCount = 0;
        int j;

        //missing enumValue check
        for(j = 0; j < enumListTxt.size();){
            for(int i= 0; i < listXml2.size();i++){

                if(listXml2.get(i).toString().matches("(.*)"+enumListTxt.get(j).toString()+"(.*)")){
                    System.out.println("enum eşleşti : " + enumListTxt.get(j).toString());
                    j++;
                    break;

                }else if(listXml2.get(i).toString().matches("(.*)</ParameterXMLList>(.*)")) {
                    System.out.println("enum bulunamadı : " + enumListTxt.get(j).toString());
                    missingEnum = enumListTxt.get(j).toString();
                    j++;
                    ////////////////////////////////////////////
                    Object[] excelRowEnum = {uniqueKey++, missingEnum, "enumValue", missingEnum};
                    rowCount++;
                    writeExcel(sheet, excelRowEnum, rowCount);

                    //Kayıp enum'ların efectiveTenanId'leri excel'e yazılır
                    for(int a = 0; a < listTxt.size(); a++) {
                        if (listTxt.get(a).toString().matches("(.*)" + missingEnum + "(.*)")) {
                            listTxt.remove(a);
                            for (int b = a; b < listTxt.size(); ) {

                                if (listTxt.get(b).toString().matches("(.*)enum(.*)")) {
                                    break;
                                } else {
                                    System.out.println(missingEnum + " : " + listTxt.get(b).toString() + " bulunamadı");
////////////////////////////////    for excel   ////////////////////////////////////////////////////////////////////////
                                    Object[] excelRow = {uniqueKey++, missingEnum, "effectiveTenantId", listTxt.get(b).toString()};
                                    rowCount++;
                                    writeExcel(sheet, excelRow, rowCount);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                    listTxt.remove(b);
                                }
                            }
                        }
                    }

                    //Kayıp enum'ların organizationUnitId'leri excel'e yazılır
                    for(int a = 0; a < orgUnitListTxt.size(); a++){
                        if (orgUnitListTxt.get(a).toString().matches("(.*)" + missingEnum + "(.*)")) {
                            orgUnitListTxt.remove(a);
                            for (int b = a; b < orgUnitListTxt.size(); ) {

                                if (orgUnitListTxt.get(b).toString().matches("(.*)enum(.*)")) {
                                    break;
                                } else {
                                    System.out.println(missingEnum + " : " + orgUnitListTxt.get(b).toString() + " bulunamadı");
////////////////////////////////    for excel   //////////////////////////////////////////////////////////////////////////
                                    Object[] excelRow = {uniqueKey++, missingEnum, "organizationUnitId", orgUnitListTxt.get(b).toString()};
                                    rowCount++;
                                    writeExcel(sheet, excelRow, rowCount);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                    orgUnitListTxt.remove(b);
                                }
                            }
                        }

                    }

                    //Kayıp enum'ların parameterList'leri excel'e yazılır
                    String currentMissingTag;
                    String currentMissingValue;
                    for(int a = 0; a < listParameter.size(); a++){
                        if (listParameter.get(a).toString().matches("(.*)" + missingEnum + "(.*)")) {
                            listParameter.remove(a);
                            for (int b = a; b < listParameter.size(); ) {

                                if (listParameter.get(b).toString().matches("(.*)enum(.*)")) {
                                    break;
                                } else {
                                    System.out.println(missingEnum + " : " + listParameter.get(b).toString() + " bulunamadı");
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                    /////for excel
                                    currentMissingTag = listParameter.get(b).toString();
                                    String[] arrCurMisTag = currentMissingTag.split("<");
                                    int lastLetter = arrCurMisTag[1].indexOf(">");
                                    currentMissingTag = arrCurMisTag[1].substring(0, lastLetter);

                                    currentMissingValue = arrCurMisTag[1].substring(lastLetter+1,arrCurMisTag[1].length());
                                    Object[] excelRow = {uniqueKey++, missingEnum, "ParameterList -> "+ currentMissingTag, currentMissingValue};
                                    rowCount++;
                                    writeExcel(sheet, excelRow, rowCount);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                    listParameter.remove(b);
                                    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                }
                            }
                        }

                    }
                }
            }
        }

        //for effecTenId list and enum
        List cleanListTxt = new ArrayList();
        String cleanLine;

        for(int i = 0; i < listTxt.size();i++){
            if(listTxt.get(i).toString().matches("(.*)enum(.*)")) {
                cleanLine = listTxt.get(i).toString();
                String[] clearLine = cleanLine.split(":");
                cleanListTxt.add(clearLine[0]);
            }else{
                cleanListTxt.add(listTxt.get(i));
            }
        }

        //effTenId'leri targetEnum sırasına göre yazma
        List<String> cleanListTxtt = new ArrayList<>();
        for(int i = 0 ; i < cleanListTargetEnum.size(); i++){
            for(int k = 0; k < cleanListTxt.size(); k++){
                if(cleanListTargetEnum.get(i).toString().matches(cleanListTxt.get(k).toString())){
                    cleanListTxtt.add(cleanListTxt.get(k).toString());
                    for(int m = k+1 ; m < cleanListTxt.size(); m++) {
                        if(cleanListTxt.get(m).toString().matches("(.*)[a-zA-Z](.*)"))
                            break;
                        else
                            cleanListTxtt.add(cleanListTxt.get(m).toString());
                    }
                }
            }
        }

        //sıralanmış effTenId'leri(cleanListTxtt) ve listTargetEnums'ları birleştirme
        List<String> cleanListTxttt = new ArrayList<>();
        for(int i = 0 ; i < listTargetEnums.size(); i++){
            for(int k = 0; k < cleanListTxtt.size(); k++){
                if(listTargetEnums.get(i).toString().matches(cleanListTxtt.get(k))){
                    cleanListTxttt.add(cleanListTxtt.get(k));
                    for(int m = k+1 ; m < cleanListTxtt.size(); m++) {
                        if(cleanListTxtt.get(m).toString().matches("(.*)[a-zA-Z](.*)"))
                            break;
                        else
                            cleanListTxttt.add(cleanListTxtt.get(m).toString());
                    }break;
                }else if(k == cleanListTxtt.size()-1){
                    cleanListTxttt.add(listTargetEnums.get(i).toString());
                }
            }
        }

        //for orgUnitId list
        List cleanOrgUnitIdTxt = new ArrayList();
        String cleanOrgUnitIdLine;

        for(int i = 0; i < orgUnitListTxt.size();i++){
            if(orgUnitListTxt.get(i).toString().matches("(.*)enum(.*)")) {
                cleanOrgUnitIdLine = orgUnitListTxt.get(i).toString();
                String[] clearOrgUnIdLine = cleanOrgUnitIdLine.split(":");
                cleanOrgUnitIdTxt.add(clearOrgUnIdLine[0]);
            }else{
                cleanOrgUnitIdTxt.add(orgUnitListTxt.get(i));
            }
        }
        //orgUnitId'leri targetEnum sırasına göre yazma
        List<String> cleanOrgUnitIdTxtt = new ArrayList<>();
        for(int i = 0 ; i < cleanListTargetEnum.size(); i++){
            for(int k = 0; k < cleanOrgUnitIdTxt.size(); k++){
                if(cleanListTargetEnum.get(i).toString().matches(cleanOrgUnitIdTxt.get(k).toString())){
                    cleanOrgUnitIdTxtt.add(cleanOrgUnitIdTxt.get(k).toString());
                    for(int m = k+1 ; m < cleanOrgUnitIdTxt.size(); m++) {
                        if(cleanOrgUnitIdTxt.get(m).toString().matches("(.*)[a-zA-Z](.*)"))
                            break;
                        else
                            cleanOrgUnitIdTxtt.add(cleanOrgUnitIdTxt.get(m).toString());
                    }
                }
            }
        }
        //sıralanmış orgUnitId'leri(cleanOrgUnitIdTxtt) ve listTargetEnums'ları birleştirme
        List<String> cleanOrgUnitIdTxttt = new ArrayList<>();
        for(int i = 0 ; i < listTargetEnums.size(); i++){
            for(int k = 0; k < cleanOrgUnitIdTxtt.size(); k++){
                if(listTargetEnums.get(i).toString().matches(cleanOrgUnitIdTxtt.get(k))){
                    cleanOrgUnitIdTxttt.add(cleanOrgUnitIdTxtt.get(k));
                    for(int m = k+1 ; m < cleanOrgUnitIdTxtt.size(); m++) {
                        if(cleanOrgUnitIdTxtt.get(m).toString().matches("(.*)[a-zA-Z](.*)"))
                            break;
                        else
                            cleanOrgUnitIdTxttt.add(cleanOrgUnitIdTxtt.get(m).toString());
                    }break;
                }else if(k == cleanOrgUnitIdTxtt.size()-1){
                    cleanOrgUnitIdTxttt.add(listTargetEnums.get(i).toString());
                }
            }
        }

        //for parameterList and enum
        List cleanParameterList = new ArrayList();
        String cleanLineParameter;

        for(int i = 0; i < listParameter.size();i++){
            if(listParameter.get(i).toString().matches("(.*)enum(.*)")) {
                cleanLineParameter = listParameter.get(i).toString();
                String[] clearLine = cleanLineParameter.split(":");
                cleanParameterList.add(clearLine[0]);
            }else{
                cleanParameterList.add(listParameter.get(i));
            }
        }

        //parameterList'leri targetEnum sırasına göre yazma
        List<String> cleanParameterListt = new ArrayList<>();
        for(int i = 0 ; i < cleanListTargetEnum.size(); i++){
            for(int k = 0; k < cleanParameterList.size(); k++){
                if(cleanListTargetEnum.get(i).toString().matches(cleanParameterList.get(k).toString())){
                    cleanParameterListt.add(cleanParameterList.get(k).toString());
                    for(int m = k+1 ; m < cleanParameterList.size(); m++) {
                        if(!cleanParameterList.get(m).toString().matches("(.*)<(.*)"))
                            break;
                        else
                            cleanParameterListt.add(cleanParameterList.get(m).toString());
                    }
                }
            }
        }

        //sıralanmış parameterList'leri(cleanParameterListt) ve listTargetEnums'ları birleştirme
        List<String> cleanParameterListtt = new ArrayList<>();
        for(int i = 0 ; i < listTargetEnums.size(); i++){
            for(int k = 0; k < cleanParameterListt.size(); k++){
                if(listTargetEnums.get(i).toString().matches(cleanParameterListt.get(k))){
                    cleanParameterListtt.add(cleanParameterListt.get(k));
                    for(int m = k+1 ; m < cleanParameterListt.size(); m++) {
                        if(!cleanParameterListt.get(m).toString().matches("(.*)<(.*)"))
                            break;
                        else
                            cleanParameterListtt.add(cleanParameterListt.get(m).toString());
                    }break;
                }else if(k == cleanParameterListt.size()-1){
                    cleanParameterListtt.add(listTargetEnums.get(i).toString());
                }
            }
        }



        //starting to compare effectiveTenanId
        System.err.println("starting effective tenant id compare");
        compareEffTenId(listXml2, cleanListTxttt, sheet, uniqueKey, rowCount, cleanOrgUnitIdTxttt, cleanParameterListtt);


    }

    // effectiveTenantId COMPARE  (missing effectiveTenantIds written to excel)
    public static void compareEffTenId(List listXml2, List cleanListTxttt, XSSFSheet sheet, int uniqueKey, int rowCount, List cleanOrgUnitIdTxttt, List cleanParameterListtt) throws IOException{

        int i = 0;
        int z;
        String currentEnum = "";

        for(int j = 0; j < cleanListTxttt.size();){
            while( i < listXml2.size()){
                i++;
                if(listXml2.get(i).toString().matches("(.*)<enumValue>"+cleanListTxttt.get(j).toString()+"(.*)")){
                    currentEnum = cleanListTxttt.get(j).toString();
                    int currentLine = i;

                    System.out.println("enum eşleşti : " + cleanListTxttt.get(j).toString());
                    j++;
                    if(j==cleanListTxttt.size()){
                        break;
                    }
                    for(z = i+1; z < listXml2.size();z++){
                        if(listXml2.get(z).toString().matches("(.*)<enumValue>"+cleanListTxttt.get(j).toString()+"(.*)")){
                            break;
                        }else if(listXml2.get(z).toString().matches("(.*)<effectiveTenantId>"+cleanListTxttt.get(j).toString()+"(.*)")){
                            System.out.println(cleanListTxttt.get(j).toString()  + " eşleşti");
                            j++;
                            if(j==cleanListTxttt.size()){
                                break;
                            }
                            z= currentLine;
                        }else if(listXml2.get(z).toString().matches("(.*)<enumValue>(.*)") || listXml2.get(z).toString().matches("(.*)</ParameterXMLList>(.*)")){
                            System.out.println(cleanListTxttt.get(j)+" : bulunamadı");

/////////////////////////////////////////////////////////////////////////////////////////////////////////
                            /////for excel
                            Object[] excelRow = {uniqueKey++, currentEnum, "effectiveTenantId" ,cleanListTxttt.get(j)};
                            rowCount++;
                            writeExcel(sheet, excelRow, rowCount);
/////////////////////////////////////////////////////////////////////////////////////////////////////////
                            j++;
                            if(j==cleanListTxttt.size()){
                                break;
                            }
                            z= currentLine;

                        }
                    }
                }break;
            }

        }

        compareOrgUnitId(listXml2, cleanOrgUnitIdTxttt, cleanParameterListtt, sheet, uniqueKey, rowCount);

    }

    // organizationUnitId COMPARE (organizationUnitIds written to excel)
    public static void compareOrgUnitId(List listXml2, List cleanOrgUnitIdTxttt, List cleanParameterListtt, XSSFSheet sheet, int uniqueKey, int rowCount) throws IOException{

        System.err.println("starting organization unit id compare");
        int i = 0;
        int z;
        String currentEnum = "";

        for(int j = 0; j < cleanOrgUnitIdTxttt.size();){
            while( i < listXml2.size()){
                i++;
                if(listXml2.get(i).toString().matches("(.*)<enumValue>"+cleanOrgUnitIdTxttt.get(j).toString()+"(.*)")){
                    currentEnum = cleanOrgUnitIdTxttt.get(j).toString();
                    int currentLine = i;

                    System.out.println("enum eşleşti : " + cleanOrgUnitIdTxttt.get(j).toString());
                    j++;
                    if(j==cleanOrgUnitIdTxttt.size()){
                        break;
                    }
                    for(z = i+1; z < listXml2.size();z++){
                        if(listXml2.get(z).toString().matches("(.*)<enumValue>"+cleanOrgUnitIdTxttt.get(j).toString()+"(.*)")){
                            break;
                        }else if(listXml2.get(z).toString().matches("(.*)<organizationUnitId>"+cleanOrgUnitIdTxttt.get(j).toString()+"(.*)")){
                            System.out.println(cleanOrgUnitIdTxttt.get(j).toString()  + " eşleşti");
                            j++;
                            if(j==cleanOrgUnitIdTxttt.size()){
                                break;
                            }
                            z= currentLine;
                        }else if(listXml2.get(z).toString().matches("(.*)<enumValue>(.*)") || listXml2.get(z).toString().matches("(.*)</ParameterXMLList>(.*)")){
                            System.out.println(cleanOrgUnitIdTxttt.get(j)+" : bulunamadı");
/////////////////////////////////////////////////////////////////////////////////////////////////////////
                            /////for excel
                            Object[] excelRow = {uniqueKey++, currentEnum, "organizationUnitId" ,cleanOrgUnitIdTxttt.get(j)};
                            rowCount++;
                            writeExcel(sheet, excelRow, rowCount);

/////////////////////////////////////////////////////////////////////////////////////////////////////////
                            j++;
                            if(j==cleanOrgUnitIdTxttt.size()){
                                break;
                            }
                            z= currentLine;

                        }
                    }
                }break;
            }

        }

        compareParameterList(listXml2, cleanParameterListtt, sheet, uniqueKey, rowCount);

    }

    // parameterList COMPARE  (missing parameterList tag values written to excel)
    public static void compareParameterList(List listXml2, List cleanParameterListtt, XSSFSheet sheet, int uniqueKey, int rowCount) throws IOException{

        System.err.println("starting ParameterList tag");
        int i = 0;
        int z;
        String currentEnum = "";
        int k;

        for(int j = 0; j < cleanParameterListtt.size();){
            for( i = 0; i < listXml2.size(); i++){
                if(listXml2.get(i).toString().matches("(.*)<enumValue>"+cleanParameterListtt.get(j).toString()+"(.*)")){
                    currentEnum = cleanParameterListtt.get(j).toString();

                    System.out.println("enum eşleşti : " + cleanParameterListtt.get(j).toString());
                    j++; /*control */ if(j==cleanParameterListtt.size()){break;}
                    for(z = i+1; z < listXml2.size();z++){
                        if(listXml2.get(z).toString().matches("(.*)<enumValue>(.*)") ){
                            //i=z ;
                            break;
                        }
                        else if(listXml2.get(z).toString().matches("(.*)<Parameter>(.*)")){
                            /////////////////////////////////////////////////////////////////////
                            for( k = j ; cleanParameterListtt.get(k).toString().matches("(.*)<(.*)"); k++){
                                //if(k==cleanParameterListtt.size()){break;}
                                String currentTag = cleanParameterListtt.get(k).toString();
                                String[] arrCurMisTag = currentTag.split("<");
                                int lastLetter = arrCurMisTag[1].indexOf(">");
                                currentTag = arrCurMisTag[1].substring(0, lastLetter);
                                String currentValue = arrCurMisTag[1].substring(lastLetter + 1, arrCurMisTag[1].length());
                                for (int b = z+1; b < listXml2.size(); b++) {
//                                    if (listXml2.get(b).toString().matches("(.*)<parameterValueList>(.*)") || (listXml2.get(b).toString().matches("(.*)<entry>(.*)"))) {
//                                        continue;}else
                                    if(listXml2.get(b).toString().matches("(.*)"+ currentValue + "</" +currentTag+">")) {
                                        System.out.println(currentTag + " : " + currentValue + " eşleşti.");
                                        break;
                                    }
                                    else if (listXml2.get(b).toString().matches("(.*)</entry>(.*)")) {
                                        System.out.println(currentTag + " : " + currentValue + " bulunamadı.");
                                        Object[] excelRow = {uniqueKey++, currentEnum, "ParameterList -> " + currentTag, currentValue};
                                        rowCount++;
                                        writeExcel(sheet, excelRow, rowCount);
                                        break;
                                    }
                                    else if(currentValue.matches("(.*)\\|(.*)")){
                                        String[] arrOfValue = currentValue.split("\\|");
                                        String longValue = "";
                                        for(int x = 0 ; x < arrOfValue.length; x++){longValue += arrOfValue[x]+"(.*)";}
                                        if(listXml2.get(b).toString().matches("(.*)"+currentTag+">"+ longValue +"</" +currentTag + "(.*)")) {
                                            System.out.println(currentTag + " : " + currentValue + " eşleşti.");
                                            break;
                                        }
                                    }
                                    else if(currentValue.matches("(.*),(.*)")){  //currentValue içinde virgül varsa sorun çıkıyor, bu yüzden bu kontrol eklendi
                                        String[] arrOfValue = currentValue.split(",");
                                        String allDisplayValue="";
                                        String longDisplayValue = "";
                                        for(int x = 0 ; x < arrOfValue.length; x++){
                                            allDisplayValue += arrOfValue[x]+"(.*)";
                                        }
                                        if(allDisplayValue.matches("(.*) (.*)")){
                                            String[] arrOfValue1 = allDisplayValue.split(" ");
                                            for(int x = 0 ; x < arrOfValue1.length; x++){
                                                longDisplayValue += arrOfValue1[x]+"(.*)";
                                            }
                                            if(listXml2.get(b).toString().matches("(.*)"+currentTag+">"+ longDisplayValue +"</" +currentTag + "(.*)")) { //boşluk da sorun çıkartıyor. içinde aynı zamanda boşluk varsa diye bu kontrol eklendi
                                                System.out.println(currentTag + " : " + currentValue + " eşleşti.");
                                                break;
                                            }
                                        }
                                        if(listXml2.get(b).toString().matches("(.*)"+currentTag+">"+ longDisplayValue +"</" +currentTag + "(.*)")) {
                                            System.out.println(currentTag + " : " + currentValue + " eşleşti.");
                                            break;
                                        }
                                        break;
                                    }
                                    else if(currentValue.matches("(.*) (.*)")){  //currentValue içinde boşluk varsa sorun çıkıyor, bu yüzden bu kontrol eklendi
                                        String[] arrOfValue = currentValue.split(" ");
                                        String allDisplayValue="";
                                        String longDisplayValue = "";
                                        for(int x = 0 ; x < arrOfValue.length; x++){
                                            allDisplayValue += arrOfValue[x]+"(.*)";
                                        }
                                        if(allDisplayValue.matches("(.*),(.*)")){
                                            String[] arrOfValue1 = allDisplayValue.split(",");
                                            for(int x = 0 ; x < arrOfValue1.length; x++){
                                                longDisplayValue += arrOfValue1[x]+"(.*)";
                                            }
                                            if(listXml2.get(b).toString().matches("(.*)"+currentTag+">"+ longDisplayValue +"</" +currentTag + "(.*)")) { //virgül de sorun çıkartıyor. içinde aynı zamanda virgül varsa diye bu kontrol eklendi
                                                System.out.println(currentTag + " : " + currentValue + " eşleşti.");
                                                break;
                                            }
                                        }
                                        if(listXml2.get(b).toString().matches("(.*)"+currentTag+">"+ longDisplayValue +"</" +currentTag + "(.*)")) {
                                            System.out.println(currentTag + " : " + currentValue + " eşleşti.");
                                            break;
                                        }
                                        break;
                                    }
                                }
                                if(k==cleanParameterListtt.size()-1){break;}

                            }
                            if(k<cleanParameterListtt.size()){j=k++;}
                            else{break;}
                        }else if(listXml2.get(z).toString().matches("(.*)<ParameterList/>(.*)") && cleanParameterListtt.get(j).toString().matches("(.*)<(.*)")){
                            for( k = j ; cleanParameterListtt.get(k).toString().matches("(.*)<(.*)"); k++){
                                String currentTag = cleanParameterListtt.get(k).toString();
                                String[] arrCurMisTag = currentTag.split("<");
                                int lastLetter = arrCurMisTag[1].indexOf(">");
                                currentTag = arrCurMisTag[1].substring(0, lastLetter);
                                String currentValue = arrCurMisTag[1].substring(lastLetter + 1, arrCurMisTag[1].length());
                                for (int b = z+1; b < listXml2.size(); b++) {
                                    System.out.println(currentTag + " : " + currentValue + " bulunamadı.");
                                    Object[] excelRow = {uniqueKey++, currentEnum, "ParameterList -> " + currentTag, currentValue};
                                    rowCount++;
                                    writeExcel(sheet, excelRow, rowCount);
                                    break;
                                }
                            }
                            if(k<cleanParameterListtt.size()){j=k++;}
                            else{break;}
                        }
                        i=z;
                    }
                }

            }
        }

    }



    // writing excel first row
    public static void excelFirstRow(XSSFSheet sheet, int rowCount) throws IOException {

        Object[][] firstRow = {{"","enumValue","columnName","missingValue"}};

        Row row = sheet.createRow(rowCount);
        for (Object[] firstRoww : firstRow) {

            int columnCount = 0;

            for (Object field : firstRoww) {
                Cell cell = row.createCell(columnCount++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                }else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
        }
    }

    // writing excel
    public static void writeExcel(XSSFSheet sheet,Object[] addRows, int rowCount) throws IOException {


        Object[][] diffValues = {addRows};

        for (Object[] diff : diffValues) {
            Row row = sheet.createRow(rowCount++);

            int columnCount = 0;

            for (Object field : diff) {
                Cell cell = row.createCell(columnCount++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
        }
    }

}

