package com.studbud.studbud;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.text.DecimalFormat;




/**
 * Created by Aldu on 31.08.16.
 */
public class Calculator {


    private static final int INFWISS_ID = 0;
    private static final int MEDINFO_ID = 1;
    private static final String KEY_CHOSEN_MODULE_4 = "INF4";
    private static final String KEY_CHOSEN_MODULE_5 = "INF5";
    private static final String KEY_CHOSEN_MODULE_6 = "INF6";



    public double calculateMarks(int subjectID, double[]inf01,double[] mei01,double[] mei03, double[] mei04, double[] mei05,double[] mei08,double[] mei10){
        if(subjectID == INFWISS_ID){
            return calculateInfwissAsMainSubject(inf01,mei01,mei03,mei05,mei08,mei10);
        }
        else{
            return calculateMedInfoAsMainSubject(inf01,mei03,mei04,mei05,mei10);
        }
    }

    private double calculateInfwissAsMainSubject(double[] inf01,double[] mei01,double[] mei03,double[] mei05,double[] mei08,double[] mei10){
        DecimalFormat df = new DecimalFormat("#0.0");
        double markBachelorInfWiss = 4.0;

        double inf1 = calculateInfwissM01(inf01) * 0.1;
        double inf2 = calculateInfwissM02() * 0.1;
        double med10 = calculateMediInfoM10(mei10) * 0.1;
        double inf4 = calculateInfwissM04() * 0.15;
        double inf5 = calculateInfwissM05() * 0.15;
        double inf6 = calculateInfwissM06() * 0.15;
        double inf7 = calculateInfwissM07() * 0.25;

        double markMedInfo = calculateMedInfoAsSecondSubject(mei01,mei03,mei05,mei08) * 0.3;
        double markInfwiss = (inf1 + inf2 + med10 + inf4 + inf5 + inf6 + inf7) * 0.5;

        double finalMark = markInfwiss + markMedInfo + (markBachelorInfWiss * 0.2);

        return finalMark;
    }

    private double calculateMedInfoAsMainSubject(double[] inf01,double[] mei03, double[] mei04, double[] mei05,double[] mei10) {
        DecimalFormat df = new DecimalFormat("#0.0");
        double markBachelorMedInfo = 4.0;

        double med3 = calculateMediInfoM03(mei03) * 0.25;
        double med4 = calculateMediInfoM04(mei04) * 0.25;
        double med5 = calculateMediInfoM05(mei05) * 0.25;
        double med10 = calculateMediInfoM10(mei10) * 0.25;

        double markMedInfo = (med3 + med4 + med5 + med10) * 0.5;
        double markInfWiss = calculateInfWissAsSecondSubject(inf01);

        double finalMark = markMedInfo + markInfWiss + (markBachelorMedInfo * 0.2);

        return finalMark;
    }

    private double calculateInfWissAsSecondSubject(double[] inf01){
        String chosenModuleOne = "INF4";
        String chosenModuleTwo = "INF5";
        double inf1 = calculateInfwissM01(inf01) * 0.25;
        double inf2 = calculateInfwissM02() * 0.25;
        double chosenModuleInf4 = calculateInfwissM04() * 0.25;
        double chosenModuleInf5 = calculateInfwissM05() * 0.25;
        double chosenModuleInf6 = calculateInfwissM06() * 0.25;

        double finalMark = inf1 + inf2;

        switch (chosenModuleOne){
            case KEY_CHOSEN_MODULE_4:
                finalMark += chosenModuleInf4;
                break;
            case KEY_CHOSEN_MODULE_5:
                finalMark += chosenModuleInf5;
                break;
            case KEY_CHOSEN_MODULE_6:
                finalMark += chosenModuleInf6;
                break;
            default:
                break;
        }

        switch (chosenModuleTwo){
            case KEY_CHOSEN_MODULE_4:
                finalMark += chosenModuleInf4;
                break;
            case KEY_CHOSEN_MODULE_5:
                finalMark += chosenModuleInf5;
                break;
            case KEY_CHOSEN_MODULE_6:
                finalMark += chosenModuleInf6;
                break;
            default:
                break;
        }

        return finalMark * 0.3;
    }

    private double calculateMedInfoAsSecondSubject(double[] mei01,double[] mei03,double[] mei05,double[] mei08){
        double med1 = calculateMediInfoM01(mei01) * 0.25;
        double med3 = calculateMediInfoM03(mei03) * 0.25;
        double med5 = calculateMediInfoM05(mei05) * 0.25;
        double med8 = calculateMediInfoM08(mei08) * 0.25;
        double mark = med1 + med3 + med5 + med8;
        return mark;
    }


    public double calculateInfwissM01(double[] inf01){

        double markInf11 = inf01[0] * 0.5;
        double markInf12 = inf01[1] * 0.5;
        Log.d("INF01", String.valueOf(markInf11 + markInf12));
        double finalInfM01 = markInf11 + markInf12;
        return finalInfM01;
    }

    private double calculateInfwissM02(){
        return 2.5;
    }

    private double calculateInfwissM04(){
        return 2.65;
    }

    private double calculateInfwissM05(){
        return 1.5;
    }

    private double calculateInfwissM06(){
        return 2.325;
    }

    private double calculateInfwissM07(){
        return 1.0;
    }

    public double calculateMediInfoM01(double[] mei01){
        double m1_1 = mei01[0] * 0.7;
        double m1_2 = mei01[1] * 0.3;
        double finalMarkM1 = m1_1 + m1_2;
        return finalMarkM1;
    }



    public double calculateMediInfoM03(double[] mei03){
        double m3_1 = mei03[0] * 0.25;
        double m3_2 = mei03[1] * 0.25;
        double m3_3 = mei03[2] * 0.5;
        double finalMarkM3 = m3_1 + m3_2 + m3_3;
        return finalMarkM3;
    }

    public double calculateMediInfoM04(double[] mei04){
        double m4_1 = mei04[0] * 0.25;
        double m4_2 = mei04[1] * 0.25;
        double m4_3 = mei04[2] * 0.5;
        double finalMarkM4 = m4_1 + m4_2 + m4_3;
        return finalMarkM4;
    }

    public double calculateMediInfoM05(double[] mei05){
        double m5_1 = mei05[0] * 0.25;
        double m5_2 = mei05[1] * 0.25;
        double m5_3 = mei05[2] * 0.5;
        double finalMarkM5 = m5_1 + m5_2 + m5_3;
        return finalMarkM5;
    }

    public double calculateMediInfoM08(double[] mei08){
        double m8_1 = mei08[0] * 0.5;
        double m8_2 = mei08[1] * 0.5;
        double finalMarkM3 = m8_1 + m8_2;
        return finalMarkM3;    }

    public double calculateMediInfoM10(double[] mei10){
        return mei10[2];
    }

}
