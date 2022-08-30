package com.leniolabs.challenge.calculator.factory;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import com.leniolabs.challenge.calculator.FeeCalculatorIF;
import com.leniolabs.challenge.custom.AccountType;

@Component
public class FeeCalculatorFactory {

	private final Reflections REFLECTIONS = new Reflections("com.leniolabs.challenge");

	/**
	 * Creates a Fee Calculator instance
	 * 
	 * @param annotation
	 * @param accountType
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public FeeCalculatorIF createFeeCalculator(Class<AccountType> annotation, String calculatorType)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		FeeCalculatorIF calculator = null;

		if (annotation != null && calculatorType != null && !calculatorType.isEmpty()) {
			Set<Class<?>> annotatedClasses = REFLECTIONS.getTypesAnnotatedWith(annotation);

			for (Class<?> annotatedClass : annotatedClasses) {
				AccountType accountType = annotatedClass.getAnnotation(annotation);
				if (accountType.value().equals(calculatorType)) {
					calculator = (FeeCalculatorIF) annotatedClass.getDeclaredConstructor().newInstance();
					break;
				}
			}
		}

		return calculator;
	}

}
