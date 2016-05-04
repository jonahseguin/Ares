package com.mcares.ares.check.checker.checkers;

import com.mcares.ares.check.CheckType;
import com.mcares.ares.check.checker.Checker;
import com.mcares.ares.check.checker.CheckerData;
import com.mcares.ares.check.checker.CheckerSimilarity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckerAutoClick extends Checker {

    @CheckerData
    private double[] clicksPerSecond = new double[]{0,0,0,0};// We will be keeping track of the past 4 seconds worth of cps

    public CheckerAutoClick() {
        super(CheckType.AUTO_CLICK);
    }

    @Override
    public CheckerSimilarity getSimilarityTo(Checker checker) {
        if(checker instanceof CheckerAutoClick) {
            CheckerAutoClick test = (CheckerAutoClick) checker;

            double a = difference(test.getClicksPerSecond()[3], clicksPerSecond[3]);
            double b = difference(test.getClicksPerSecond()[2], clicksPerSecond[2]);
            double c = difference(test.getClicksPerSecond()[1], clicksPerSecond[1]);
            double d = difference(test.getClicksPerSecond()[0], clicksPerSecond[0]);

            //the closer to 0, the more similar

            double score = sub(a, 25) + sub(b, 25) + sub(c, 25) + sub(d, 25);
            double maxScore = 100;

            double result = score / maxScore;

            return new CheckerSimilarity(result, (result >= 60));//TODO: Make the threshold for whether it passed configurable

        }
        else{
            throw new RuntimeException("Checker is not of same type");
        }
    }

    private double sub(double a, int percent) {
        double ret = percent - a;
        if(ret < 0) {
            ret = 0;
        }
        return ret;
    }

    private double difference(double a, double b) {
        double ret = (a > b ? a - b : b - a);
        if(ret <= 0) {
            ret = 1;
        }
        return ret;
    }

}
