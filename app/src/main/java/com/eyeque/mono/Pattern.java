package com.eyeque.mono;

/**
 * Created by georgez on 2/1/16.
 */
import java.lang.Math;
import android.content.Context;
import android.view.View;
import android.util.Log;

public class Pattern {

    private static int[] centerPoint = {SingletonDataHolder.centerX, SingletonDataHolder.centerY};
    private static int[] centerOffset = {0, 0};
    private static int lineLength;
    private static int lineSpace;

    private static int redStartX;
    private static int redEndX;
    private static int redStartY;
    private static int redEndY;

    private static int greenStartX;
    private static int greenEndX;
    private static int greenStartY;
    private static int greenEndY;

    private static Boolean toggleLine;
    private static double powerValue;
    private static int angleValue = 0;

    private static int patternIndex;
    private static int numOfPattern;

    private static final int[] PATTERN_ANGLE_LIST_DEVICE_1 = {0, 30, -30, 0, 30, -30};
    // private static final int[] PATTERN_ANGLE_LIST_DEVICE_3 = {0, 150, 120, 90, 60, 30};
    // private static final int[] PATTERN_ANGLE_LIST_DEVICE_3 = {0, 320, 280, 240, 200, 160, 120, 80, 40};
    private static final int[] PATTERN_ANGLE_LIST_DEVICE_3 = SingletonDataHolder.patternAngleList;
    private static final int[] PATTERN_ANGLE_LIST_DEVICE_6 = {20, 0, -10, -20, 20, 10, 0, -10, -20};
    private static final double[] PATTERN_CALC_ANGLE_LIST_DEVICE_1 = {90.0, 120.0, 60.0, 0.0, 30.0, 150.0};
    // private static final double[] PATTERN_CALC_ANGLE_LIST_DEVICE_3 = {0.0, 150.0, 120.0, 90.0, 60.0, 30.0};
    // private static final double[] PATTERN_CALC_ANGLE_LIST_DEVICE_3 = {0.0, 320.0, 280.0, 240.0, 200.0, 160.0, 120.0, 80.0, 40.0};
    private static final double[] PATTERN_CALC_ANGLE_LIST_DEVICE_3 = SingletonDataHolder.calcAngleList;
    private static final double[] PATTERN_CALC_ANGLE_LIST_DEVICE_6 = {20.0, 0.0, 170.0, 160.0, 110.0, 100.0, 90.0, 80.0, 70.0};
    private static final int[] PATTERN_ROTATE_ANGLE_LIST_DEVICE_1 = {0, 0, 0, 0, 0, 0};
    // private static final int[] PATTERN_ROTATE_ANGLE_LIST_DEVICE_3 = {180, 30, 60, 90, 120, 150};
    // private static final int[] PATTERN_ROTATE_ANGLE_LIST_DEVICE_3 = {180, 40, 80, 120, 160, 200, 240, 280, 320};
    private static final int[] PATTERN_ROTATE_ANGLE_LIST_DEVICE_3 = SingletonDataHolder.rotateAngleList;
    private static final int[] PATTERN_ROTATE_ANGLE_LIST_DEVICE_6 = {270, 270, 270, 270, 0, 0, 0, 0, 0};

    private static final int INIT_DISTANCE_DEVICE_1 = 299;
    private static final int INIT_DISTANCE_DEVICE_3 = SingletonDataHolder.initDistance;
    private static final int INIT_DISTANCE_DEVICE_5 = SingletonDataHolder.initDistance;
    private static final int INIT_DISTANCE_DEVICE_6 = 205;

    private static final int MAX_DISTANCE_DEVICE_1 = 330;
    private static final int MAX_DISTANCE_DEVICE_3 = SingletonDataHolder.maxDistance;
    // private static final int MAX_DISTANCE_DEVICE_5 = SingletonDataHolder.maxDistance;
    private static int MAX_DISTANCE_DEVICE_5 = (int) (INIT_DISTANCE_DEVICE_5 + 2/0.1428405590);
    private static final int MAX_DISTANCE_DEVICE_6 = 270;

    private static final int LINE_LENGTH_DEVICE_1 = 80;
    private static final int LINE_LENGTH_DEVICE_3 = 130;
    private static final int LINE_LENGTH_DEVICE_5 = SingletonDataHolder.lineLength;
    private static final int LINE_LENGTH_DEVICE_6 = 120;

    private static int maxDist;
    private static int initDist;
    private static int[] patternAngleList;
    private static double[] patternCalcAngleList;
    private static int[] patternRotateAngleList;
    private static long patternStartTs;
    private static long patternEndTs;
    public static double[] rightDurationList = {10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0};
    public static double[] leftDurationList = {10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0};
    private static double[] rightPowerValueList = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    private static double[] leftPowerValueList = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    private static int[] rightDistValueList = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private static int[] leftDistValueList = {0, 0, 0, 0, 0, 0, 0, 0, 0};

    private static boolean whichEye;  // true: right eye   false: left eye
    private static int whichPattern;
    private static boolean completeAllPatterns;
    private static int deviceId;

    // Tag for log message
    private static final String TAG = Pattern.class.getSimpleName();

    public Pattern(int deviceId, int patternId) {

        switch (deviceId) {
            case 0:
                numOfPattern = 6;
                break;
            case 2:
                if (SingletonDataHolder.testMode == 1)
                    numOfPattern = 3;
                else
                    numOfPattern = 9;
                break;
            case 3:
                if (SingletonDataHolder.testMode == 1)
                    numOfPattern = 3;
                else
                    numOfPattern = 9;
                break;
            case 4:
                if (SingletonDataHolder.testMode == 1)
                    numOfPattern = 3;
                else
                    numOfPattern = 9;
                break;
            default:
                break;
        }

        /* Base drawing parameters */
        centerPoint[0] += centerOffset[0];
        centerPoint[1] += centerOffset[1];
        lineLength = 80;
        lineSpace = maxDist;

        redStartX = centerPoint[0] - lineLength / 2;
        redStartY = centerPoint[1] - lineSpace / 2;
        redEndX = redStartX + lineLength;
        redEndY = redStartY;

        greenStartX = centerPoint[0] - lineLength / 2;
        greenStartY = centerPoint[1] + lineSpace / 2;
        greenEndX = greenStartX + lineLength;
        greenEndY = greenStartY;

        toggleLine = true;
        patternIndex = 0;
        whichPattern = 0;
        whichEye = true;
        completeAllPatterns = false;
        // Power value
        // powerValue = -15.75f;
    }

    public void setDeviceId(int id) {
        deviceId = id;
    }

    public int getRedStartX()   { return redStartX; }
    public int getRedEndX()     { return redEndX; }
    public int getRedStartY()   { return redStartY; }
    public int getRedEndY()     { return redEndY; }

    public int getGreenStartX() { return greenStartX; }
    public int getGreenEndX()   { return greenEndX; }
    public int getGreenStartY() { return greenStartY; }
    public int getGreenEndY()   { return greenEndY; }

    public int getAngle() {
        return angleValue;
    }
    public void setAngle(int value) {
        angleValue = value;
    }

    public boolean isAllPatternComplete() { return completeAllPatterns; }
    public int getRotateAngle() {
        return patternRotateAngleList[patternIndex];
    }
    public int getPattenIndex() { return patternIndex; }
    public double[] getRightPowerValueList() { return rightPowerValueList; }
    public double[] getLeftPowerValueList() { return leftPowerValueList; }
    public int[] getRightDistValueList() { return rightDistValueList; }
    public int[] getLeftDistValueList() { return leftDistValueList; }
    public int[] getPatternAngleList() { return patternAngleList; }
    public double[] getPatternCalcAngleList() { return patternCalcAngleList; }

    public int getDistance3() {
        return Math.abs(greenStartX - redStartX);
    }

    public int getDistance() {
        int retVal = 0;
        switch (deviceId) {
            case 0:
                retVal = Math.abs(greenStartY - redStartY);
                break;
            case 2:
                retVal = Math.abs(greenStartX - redStartX);
                break;
            case 3:
                retVal = Math.abs(greenStartX - redStartX);
                break;
            case 4:
                retVal = Math.abs(greenStartY - redStartY);
                break;
            default:
                break;
        }
        return retVal;
    }

    private void drawDevice3() {

        double degrees;
        double radians;
        double xRatio;
        double xDelta;
        double yRatio;
        double yDelta;

        Log.d(TAG, String.valueOf(lineSpace));
        redStartX = centerPoint[0] - lineSpace / 2;
        redStartY = centerPoint[1] - lineLength / 2;
        redEndX = redStartX;
        redEndY = redStartY + lineLength;

        greenStartX = centerPoint[0] + lineSpace / 2;
        greenStartY = centerPoint[1] - lineLength / 2;
        greenEndX = greenStartX;
        greenEndY = greenStartY + lineLength;

        setAngle(patternAngleList[patternIndex]);
    }

    private void drawDevice4() {

        double degrees;
        double radians;
        double xRatio;
        double xDelta;
        double yRatio;
        double yDelta;

        if (patternIndex < numOfPattern) {
            degrees = patternAngleList[patternIndex];
            radians = Math.toRadians(degrees);
            xRatio = Math.cos(radians);
            xDelta = (double) 1 - xRatio;
            yDelta = Math.sin(radians);

            redStartX = centerPoint[0] - lineLength / 2 + (int) (((double) lineLength/2 * xDelta));
            redStartY = centerPoint[1] - lineSpace / 2  + (int) ((double) lineLength/2 * yDelta);
            redEndX = redStartX + (int) ((double) lineLength * xRatio);
            redEndY = centerPoint[1] - lineSpace / 2  - (int) ((double) lineLength/2*yDelta);

            // cv.drawLine(20, 0, 20, cv.getHeight(), p);
            greenStartX = redStartX;
            greenStartY = redStartY + lineSpace;
            greenEndX = redEndX;
            greenEndY = redEndY + lineSpace;
            setAngle(patternAngleList[patternIndex]);
        }
    }

    private void drawDevice1() {

        double degrees;
        double radians;
        double xRatio;
        double xDelta;
        double yRatio;
        double yDelta;

        if (patternIndex < numOfPattern) {
            degrees = patternAngleList[patternIndex];
            radians = Math.toRadians(degrees);
            xRatio = Math.cos(radians);
            xDelta = (double) 1 - xRatio;
            yDelta = Math.sin(radians);

            redStartX = centerPoint[0] - lineLength / 2 + (int) (((double) lineLength/2 * xDelta));
            redStartY = centerPoint[1] - lineSpace / 2  + (int) ((double) lineLength/2 * yDelta);
            redEndX = redStartX + (int) ((double) lineLength * xRatio);
            redEndY = centerPoint[1] - lineSpace / 2  - (int) ((double) lineLength/2*yDelta);

            // cv.drawLine(20, 0, 20, cv.getHeight(), p);
            greenStartX = redStartX;
            greenStartY = redStartY + lineSpace;
            greenEndX = redEndX;
            greenEndY = redEndY + lineSpace;
            setAngle(patternAngleList[patternIndex]);
        }
        /* else {
            degrees = patternAngleList[patternIndex];
            radians = Math.toRadians(degrees);
            yRatio = Math.cos(radians);
            yDelta = (double) 1 - yRatio;
            xDelta = Math.sin(radians);

            redStartX = centerPoint[0] - lineSpace / 2 + (int) ((double) lineLength/2*xDelta);
            redStartY = centerPoint[1] + lineLength / 2  - (int) ((double) lineLength/2*yDelta);
            redEndX = centerPoint[0] - lineSpace / 2 - (int) ((double) lineLength/2*xDelta);
            redEndY = redStartY - lineLength + (int) yDelta;

            // cv.drawLine(20, 0, 20, cv.getHeight(), p);
            greenStartX = redStartX + lineSpace;
            greenStartY = redStartY;
            greenEndX = redEndX + lineSpace;
            greenEndY = redEndY;
            setAngle(patternAngleList[patternIndex] + 90);
        }
        */
    }

    public void start() {

        patternIndex = 0;
        whichPattern = 0;
        whichEye = true;
        completeAllPatterns = false;

        patternStartTs = System.currentTimeMillis()/1000;

        // Initialize the base parameters
        switch (deviceId) {
            case 0:
                numOfPattern = 6;
                initDist = INIT_DISTANCE_DEVICE_1;
                maxDist = MAX_DISTANCE_DEVICE_1;
                lineLength = LINE_LENGTH_DEVICE_1;
                patternAngleList = PATTERN_ANGLE_LIST_DEVICE_1;
                patternCalcAngleList = PATTERN_CALC_ANGLE_LIST_DEVICE_1;
                patternRotateAngleList = PATTERN_ROTATE_ANGLE_LIST_DEVICE_1;
                break;
            case 2:
                if (SingletonDataHolder.testMode == 1)
                    numOfPattern = 3;
                else
                    numOfPattern = 9;
                initDist = INIT_DISTANCE_DEVICE_3;
                maxDist = MAX_DISTANCE_DEVICE_3;
                lineLength = LINE_LENGTH_DEVICE_3;
                patternAngleList = PATTERN_ANGLE_LIST_DEVICE_3;
                patternCalcAngleList = PATTERN_CALC_ANGLE_LIST_DEVICE_3;
                patternRotateAngleList = PATTERN_ROTATE_ANGLE_LIST_DEVICE_3;
                break;
            case 3:
                if (SingletonDataHolder.testMode == 1)
                    numOfPattern = 3;
                else
                    numOfPattern = 9;
                initDist = INIT_DISTANCE_DEVICE_5;
                maxDist = MAX_DISTANCE_DEVICE_5;
                lineLength = LINE_LENGTH_DEVICE_5;
                patternAngleList = PATTERN_ANGLE_LIST_DEVICE_3;
                patternCalcAngleList = PATTERN_CALC_ANGLE_LIST_DEVICE_3;
                patternRotateAngleList = PATTERN_ROTATE_ANGLE_LIST_DEVICE_3;
                break;
            case 4:
                if (SingletonDataHolder.testMode == 1)
                    numOfPattern = 3;
                else
                    numOfPattern = 9;
                initDist = INIT_DISTANCE_DEVICE_6;
                maxDist = MAX_DISTANCE_DEVICE_6;
                lineLength = LINE_LENGTH_DEVICE_6;
                patternAngleList = PATTERN_ANGLE_LIST_DEVICE_6;
                patternCalcAngleList = PATTERN_CALC_ANGLE_LIST_DEVICE_6;
                patternRotateAngleList = PATTERN_ROTATE_ANGLE_LIST_DEVICE_6;
                break;
            default:
                break;
        }
        lineSpace = maxDist;
        drawPatternByDevice();
    }

    public void drawPatternByDevice() {
        switch (deviceId) {
            case 0:
                drawDevice1();
                break;
            case 2:
                drawDevice3();
                break;
            case 3:
                drawDevice3();
                break;
            case 4:
                drawDevice4();
                break;
            default:
                break;
        }
    }


    public void nextPattern() {

        patternEndTs = System.currentTimeMillis()/1000;
        if (whichEye) {
            rightPowerValueList[patternIndex] = getPowerValue5();
            rightDistValueList[patternIndex] = getDistance();
            rightDurationList[patternIndex] = patternEndTs - patternStartTs;
        }
        else {
            leftPowerValueList[patternIndex] = getPowerValue5();
            leftDistValueList[patternIndex] = getDistance();
            leftDurationList[patternIndex] = patternEndTs - patternStartTs;
        }

        patternStartTs = patternEndTs;
        /***
        for (int i = 0; i < 9; i++)
            Log.i("**** Right Duration ***", Integer.toString((int) rightDurationList[i]));
        for (int i = 0; i < 9; i++)
            Log.i("**** Left Duration ***", Integer.toString((int) leftDurationList[i]));
         ***/
        patternIndex++;
        whichPattern++;
        if (patternIndex > numOfPattern - 1) {
            patternIndex = 0;
            whichEye = !whichEye;
        }
        if (whichPattern >= numOfPattern*2 -1) {
            completeAllPatterns = true;
        }
        if (whichPattern > numOfPattern*2 -1) {
            whichPattern = 0;
        }

        if (patternIndex > 0) {
            if (whichEye)
                lineSpace = (int) (rightDistValueList[patternIndex-1] + 1/0.1428405590);
            else
                lineSpace = (int) (leftDistValueList[patternIndex-1] + 1/0.1428405590);
        } else
            lineSpace =maxDist;
        drawPatternByDevice();
    }

    public boolean isAllPatternsComplete() {
        return completeAllPatterns;
    }

    public boolean getWhichEye() {
        return whichEye;
    }

    public void moveCloser3(int step) {
        for (int i = 0; i < step; i++) {
            toggleLine = !toggleLine;
            if (toggleLine) {
                redStartX += 1;
                redEndX += 1;
            } else {
                greenStartX -= 1;
                greenEndX -= 1;
            }
        }
    }

    public void moveFurther3(int step) {
        for (int i = 0; i < step; i++) {
            toggleLine = !toggleLine;
            if (toggleLine) {
                redStartX -= 1;
                redEndX -= 1;
            } else {
                greenStartX += 1;
                greenEndX += 1;
            }
        }
    }

    public void moveCloser(int step) {
        switch (deviceId) {
            case 0:
                for (int i = 0; i < step; i++) {
                    toggleLine = !toggleLine;
                    if (patternIndex < numOfPattern) {
                        if (toggleLine) {
                            redStartY += 1;
                            redEndY += 1;
                        } else {
                            greenStartY -= 1;
                            greenEndY -= 1;
                        }
                    }
                }
                break;
            case 2:
                for (int i = 0; i < step; i++) {
                    toggleLine = !toggleLine;
                    if (toggleLine) {
                        redStartX += 1;
                        redEndX += 1;
                    } else {
                        greenStartX -= 1;
                        greenEndX -= 1;
                    }
                }
                break;
            case 3:
                for (int i = 0; i < step; i++) {
                    toggleLine = !toggleLine;
                    if (toggleLine) {
                        redStartX += 1;
                        redEndX += 1;
                    } else {
                        greenStartX -= 1;
                        greenEndX -= 1;
                    }
                }
                break;
            case 4:
                for (int i = 0; i < step; i++) {
                    toggleLine = !toggleLine;
                    if (patternIndex < numOfPattern) {
                        if (toggleLine) {
                            redStartY += 1;
                            redEndY += 1;
                        } else {
                            greenStartY -= 1;
                            greenEndY -= 1;
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    public void moveFurther(int step) {
        switch (deviceId) {
            case 0:
                for (int i = 0; i < step; i++) {
                    toggleLine = !toggleLine;
                    if (patternIndex < numOfPattern) {
                        if (toggleLine) {
                            redStartY -= 1;
                            redEndY -= 1;
                        } else {
                            greenStartY += 1;
                            greenEndY += 1;
                        }
                    }
                }
                break;
            case 2:
                for (int i = 0; i < step; i++) {
                    toggleLine = !toggleLine;
                    if (toggleLine) {
                        redStartX -= 1;
                        redEndX -= 1;
                    } else {
                        greenStartX += 1;
                        greenEndX += 1;
                    }
                }
                break;
            case 3:
                for (int i = 0; i < step; i++) {
                    toggleLine = !toggleLine;
                    if (toggleLine) {
                        redStartX -= 1;
                        redEndX -= 1;
                    } else {
                        greenStartX += 1;
                        greenEndX += 1;
                    }
                }
                break;
            case 4:
                for (int i = 0; i < step; i++) {
                    toggleLine = !toggleLine;
                    if (patternIndex < numOfPattern) {
                        if (toggleLine) {
                            redStartY -= 1;
                            redEndY -= 1;
                        } else {
                            greenStartY += 1;
                            greenEndY += 1;
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    public double getPowerValue() {

        switch (deviceId) {
            case 0:
                getPowerValue1();
                break;
            case 2:
                getPowerValue3();
                break;
            case 3:
                getPowerValue5();
                break;
            case 4:
                getPowerValue6();
                break;
            default:
                powerValue = 0;
                break;
        }
        return powerValue;
    }


    public double getPowerValue1() {

        double[] SphericalStep0 = {2.09272580e-01, 1.83930883e-01, -1.23999816e-01, 2.09272580e-01, 1.83930883e-01, -1.23999816e-01};
        double[] SphericalStep1 = {-5.44310321e-01, -4.40462159e-01, -4.55950360e-01, -5.44310321e-01, -4.40462159e-01, -4.55950360e-01};
        double[] SphericalStep2 = {5.90952019e-04, -1.56745684e-03, -3.60909385e-03, 5.90952019e-04, -1.56745684e-03, -3.60909385e-03};
        double[] SphericalStep3 = {1.44623238e-04, -8.88602824e-05, -1.49620452e-04, 1.44623238e-04, -8.88602824e-05, -1.49620452e-04};

        int dist = getDistance();
        int diff = initDist - dist;
        powerValue = SphericalStep0[patternIndex] + SphericalStep1[patternIndex]*diff
                + SphericalStep2[patternIndex]*diff*diff + SphericalStep3[patternIndex]*diff*diff*diff;

        return powerValue;
    }

    public double getPowerValue3() {

        /*
        final double SphericalStep0 = -0.01157814480679;
        final double SphericalStep1 = 0.121861836795073;
        final double SphericalStep2 = 0.00014547630349013;
        final double SphericalStep3 = 3.45806173446284E-07;
        */

        // final double SphericalStep0 = 1.291059697E-01;
        // final double SphericalStep1 = 1.187528027E-01;

        double SphericalStep0 = 1.329E-01;
        double SphericalStep1 = 1.1879E-01;

        int dist = getDistance();
        int diff = dist - initDist;   // initDist = 330

        /* Previous Caliculation
        powerValue = SphericalStep0 + SphericalStep1*deltaDist
                + SphericalStep2*deltaDist*deltaDist + SphericalStep3*deltaDist*deltaDist*deltaDist;
        */
        if (diff > 0)
            powerValue = SphericalStep0*diff;
        else
            powerValue = SphericalStep1*diff;

        return powerValue;
    }

    public double getPowerValue5() {

        /*
        final double SphericalStep0 = -0.01157814480679;
        final double SphericalStep1 = 0.121861836795073;
        final double SphericalStep2 = 0.00014547630349013;
        final double SphericalStep3 = 3.45806173446284E-07;
        */

        // final double SphericalStep0 = 1.291059697E-01;
        // final double SphericalStep1 = 1.187528027E-01;
        double SphericalStep0 = 1.428405590E-01;
        double SphericalStep1 = 1.276384330E-01;
        double SphericalStep2 = 6.454937531E-05;

        int dist = getDistance();
        int diff = dist - initDist;   // initDist = 330
        /* Previous Caliculation
        powerValue = SphericalStep0 + SphericalStep1*deltaDist
                + SphericalStep2*deltaDist*deltaDist + SphericalStep3*deltaDist*deltaDist*deltaDist;
        */
        if (diff > 0)
            powerValue = SphericalStep0*diff;
        else
            powerValue = SphericalStep1*diff + SphericalStep2*diff*diff;

        // Power correction EQ101
        powerValue = powerValue/(1+0.012*powerValue);

        return powerValue;
    }

    public double getPowerValue6() {

        double[] SphericalStep0 = {0.5, 0.5, 0.5, 0.5, -0.61, -0.61, -0.61, -0.61, -0.61};
        double[] SphericalStep1 = {0.16, 0.16, 0.16, 0.16, 0.16, 0.16, 0.16, 0.16, 0.16};
        double[] SphericalStep2 = {0.00082947, 0.00082947, 0.00082947, 0.00082947, 0.00016239, 0.00016239, 0.00016239, 0.00016239, 0.00016239};
        double[] SphericalStep3 = {4.72320739e-05, 4.72320739e-05, 4.72320739e-05, 4.72320739e-05, 4.72320739e-05, 4.72320739e-05, 4.72320739e-05, 4.72320739e-05, 4.72320739e-05};

        int dist = getDistance();
        int diff = dist - initDist;
        powerValue = SphericalStep0[patternIndex] + SphericalStep1[patternIndex]*diff;
        // + SphericalStep2[patternIndex]*diff*diff + SphericalStep3[patternIndex]*diff*diff*diff;

        return powerValue;
    }

    public double[] curveFittingv0(double[]  angl, double[]  P, int nm)
    {
        double M_PI=Math.PI;

        double coss2=0,coss=0,cossp=0,sph=0,cyl=0,sph2=0,cyl2=0;
        double   kkk=1000;
        double   kk=0;
        int   axis=0;
        double sum=0;

        for(int ii=0;ii<nm;ii++)
        {
            sum+=P[ii];
        }

        for(float i=0;i<180;i++)
        {
            kk=0;
            coss2=0;
            coss=0;
            cossp=0;
            for(int ii=0;ii<nm;ii++)
            {
                coss+=Math.cos(2*(angl[ii]-i)/180.0*M_PI);
                coss2+=(Math.cos(2*(angl[ii]-i)/180.0*M_PI))*(Math.cos(2*(angl[ii]-i)/180.0*M_PI));
                cossp+=Math.cos(2*(angl[ii]-i)/180.0*M_PI)*P[ii];
            }

            double dNM=(double) nm;
            cyl=(cossp-coss*sum/dNM)/(coss2-coss*coss/dNM);
            sph=sum/nm-cyl*coss/dNM+cyl;
            cyl=-2*cyl;
            if(cyl<=0)
            {
                for(int ii=0;ii<nm;ii++)
                    kk+=(sph+cyl*Math.sin((i-angl[ii])/180.0*M_PI)*Math.sin((i-angl[ii])/180.0*M_PI)-P[ii])*(sph+cyl*Math.sin((i-angl[ii])/180.0*M_PI)*Math.sin((i-angl[ii])/180.0*M_PI)-P[ii]);
                if (kk<=kkk)
                {
                    kkk=kk;
                    sph2=sph;
                    cyl2=cyl;
                    axis=(int)i;
                }
            }
        }

        double [] retDouble = new double[3];

        retDouble[0]=sph2;
        if (Math.abs(cyl2) < 0.25) {
            cyl2 = 0.0;
            axis = 0;
        }
        retDouble[1]=cyl2;
        retDouble[2]=axis;

        return retDouble;
    }

    public static double[] curveFitting(double []  angl, double []  P, int nm)
    {
        double M_PI=Math.PI;
        double coss2=0,coss=0,cossp=0,sph=0,cyl=0,sph2=0,cyl2=0,sphE2=0;
        double kkk=1000;
        double kk=0;
        int   axis=0;
        double sum=0;
        double rmse=0;
        double aa,cP;

        for(int ii=0;ii<nm;ii++)
        {
            sum+=P[ii];
        }

        sphE2=sum/nm;

        for(float i=0;i<180;i++)
        {
            kk=0;
            coss2=0;
            coss=0;
            cossp=0;
            for(int ii=0;ii<nm;ii++)
            {
                coss+=Math.cos(2*(angl[ii]-i)/180.0*M_PI);
                coss2+=(Math.cos(2*(angl[ii]-i)/180.0*M_PI))*(Math.cos(2*(angl[ii]-i)/180.0*M_PI));
                cossp+=Math.cos(2*(angl[ii]-i)/180.0*M_PI)*P[ii];
            }

            double dNM=(double) nm;
            cyl=(cossp-coss*sum/dNM)/(coss2-coss*coss/dNM);
            sph=sum/nm-cyl*coss/dNM+cyl;
            cyl=-2*cyl;
            if(cyl<=0)
            {
                for(int ii=0;ii<nm;ii++)
                    kk+=(sph+cyl*Math.sin((i-angl[ii])/180.0*M_PI)*Math.sin((i-angl[ii])/180.0*M_PI)-P[ii])*(sph+cyl*Math.sin((i-angl[ii])/180.0*M_PI)*Math.sin((i-angl[ii])/180.0*M_PI)-P[ii]);
                if (kk<=kkk)
                {
                    kkk=kk;
                    sph2=sph;
                    cyl2=cyl;
                    axis=(int)i;
                }
            }
        }

        if(Math.abs(cyl2)<0.25)
        {
            axis=0;
            cyl2=0.0;
            sphE2 =Math.round(sphE2*4.00)/4.00;
            sph2 = sphE2;
        }
        else
        {
            axis=180-axis;
            cyl2=Math.round(cyl2*4.00)/4.00;
            sph2=Math.round(sph2*4.00)/4.00;
            sphE2=Math.round(sphE2*4.00)/4.00;
        }

        for(int i=0;i<nm;i++)
        {
            aa = (angl[i]-180.0+axis);
            cP = sph2+cyl2*Math.sin(aa*M_PI/180.0)*Math.sin(aa*M_PI/180.0);
            rmse+=(P[i]-cP)*(P[i]-cP);
        }
        rmse = Math.sqrt(rmse/(double)nm);

        double [] retDouble = new double[5];
        retDouble[0]=sph2;
        retDouble[1]=cyl2;
        retDouble[2]=axis;
        retDouble[3]=sphE2;
        retDouble[4]=rmse;

        return retDouble;
    }

    public double[] calculateResults() {
        double[] allResults = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        double[] results;

        if (deviceId >= 2)
            results =  curveFitting(patternCalcAngleList, rightPowerValueList, 9);
        else
            results =  curveFitting(patternCalcAngleList, rightPowerValueList, 6);

        allResults[0] = results[0];
        allResults[1] = results[1];
        allResults[2] = results[2];
        allResults[3] = results[3];
        allResults[4] = results[4];

        if (deviceId >= 2)
            results =  curveFitting(patternCalcAngleList, leftPowerValueList, 9);
        else
            results =  curveFitting(patternCalcAngleList, leftPowerValueList, 6);

        allResults[5] = results[0];
        allResults[6] = results[1];
        allResults[7] = results[2];
        allResults[8] = results[3];
        allResults[9] = results[4];

        // Log.i("OD Spherical:  ", Double.toString(results[0]));
        // Log.i("OD Cylindrical:  ", Double.toString(results[1]));
        // Log.i("OD Axis:  ", Double.toString(results[2]));
        // Log.i("OD SE:  ", Double.toString(results[3]));

        return allResults;
    }
}
