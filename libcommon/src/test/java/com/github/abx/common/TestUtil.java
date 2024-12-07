package com.github.abx.common;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class TestUtil {

	@Test
	public  void testNonTermintingDecimal() {
		assertDoesNotThrow(()->Util.divide(BigDecimal.valueOf(22), BigDecimal.valueOf(7)));
	}
}
