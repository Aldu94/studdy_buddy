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



    public double calculateMarks(int subjectID, double[]inf01){
        if(subjectID == INFWISS_ID){
            return calculateInfwissAsMainSubject(inf01);
        }
        else{
            return calculateMedInfoAsMainSubject(inf01);
        }
    }

    private double calculateInfwissAsMainSubject(double[] inf01){
        DecimalFormat df = new DecimalFormat("#0.0");
        double markBachelorInfWiss = 4.0;

        double inf1 = calculateInfwissM01(inf01) * 0.1;
        double inf2 = calculateInfwissM02() * 0.1;
        double med10 = calculateMediInfoM10() * 0.1;
        double inf4 = calculateInfwissM04() * 0.15;
        double inf5 = calculateInfwissM05() * 0.15;
        double inf6 = calculateInfwissM06() * 0.15;
        double inf7 = calculateInfwissM07() * 0.25;

        double markMedInfo = calculateMedInfoAsSecondSubject() * 0.3;
        double markInfwiss = (inf1 + inf2 + med10 + inf4 + inf5 + inf6 + inf7) * 0.5;

        double finalMark = markInfwiss + markMedInfo + (markBachelorInfWiss * 0.2);

        return finalMark;
    }

    private double calculateMedInfoAsMainSubject(double[] inf01) {
        DecimalFormat df = new DecimalFormat("#0.0");
        double markBachelorMedInfo = 4.0;

        double med3 = calculateMediInfoM03() * 0.25;
        double med4 = calculateMediInfoM04() * 0.25;
        double med5 = calculateMediInfoM05() * 0.25;
        double med10 = calculateMediInfoM10() * 0.25;

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

    private double calculateMedInfoAsSecondSubject(){
        double med1 = calculateMediInfoM01() * 0.25;
        double med3 = calculateMediInfoM03() * 0.25;
        double med5 = calculateMediInfoM05() * 0.25;
        double med8 = calculateMediInfoM08() * 0.25;
        double mark = med1 + med3 + med5 + med8;
        return mark;
    }


    private double calculateInfwissM01(double[] inf01){

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

    private double calculateMediInfoM01(){
        return 2.79;
    }



    private double calculateMediInfoM03(){
        return 3.325;
    }

    private double calculateMediInfoM04(){
        return 4.0;
    }

    private double calculateMediInfoM05(){
        return 3.325;

    }

    private double calculateMediInfoM08(){
        return 2.85;
    }

    private double calculateMediInfoM10(){
        return 1.0;
    }

}
