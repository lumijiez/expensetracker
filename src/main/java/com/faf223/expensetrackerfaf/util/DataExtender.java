package com.faf223.expensetrackerfaf.util;

import com.faf223.expensetrackerfaf.model.IMoneyTransaction;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DataExtender {

    public static List<BigDecimal> interpolate(List<? extends IMoneyTransaction> transactions) {
        List<BigDecimal> values = new ArrayList<>();

        // Perform linear interpolation on the amount field
        if (transactions.size() > 1) {
            LinearInterpolator interpolator = new LinearInterpolator();

            double[] xValues = new double[transactions.size()];
            double[] yValues = new double[transactions.size()];

            for (int i = 0; i < transactions.size(); i++) {
                xValues[i] = i;
                yValues[i] = transactions.get(i).getAmount().doubleValue();
            }

            PolynomialSplineFunction splineFunction = interpolator.interpolate(xValues, yValues);

            // Interpolate values between the first and last data points
            for (int i = 1; i < transactions.size() - 1; i++)
                values.add(BigDecimal.valueOf(splineFunction.value(i + 0.5))); // Interpolate at the midpoint
        }

        return values;
    }

    public static List<BigDecimal> extrapolate(List<? extends IMoneyTransaction> transactions, int count) {
        List<BigDecimal> values = new ArrayList<>();

        // Perform linear extrapolation on the amount field
        if (transactions.size() > 1) {
            LinearInterpolator interpolator = new LinearInterpolator();

            double[] xValues = new double[transactions.size()];
            double[] yValues = new double[transactions.size()];

            for (int i = 0; i < transactions.size(); i++) {
                xValues[i] = i;
                yValues[i] = transactions.get(i).getAmount().doubleValue();
            }

            PolynomialSplineFunction splineFunction = interpolator.interpolate(xValues, yValues);

            // Extrapolate values beyond the last data point
            for (int i = transactions.size(); i < transactions.size() + count; i++)
                values.add(BigDecimal.valueOf(splineFunction.value(i))); // Extrapolate by extending the x-axis
        }

        return values;
    }

}
