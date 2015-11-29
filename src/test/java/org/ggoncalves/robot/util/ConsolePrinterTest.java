package org.ggoncalves.robot.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ConsolePrinterTest {
	
	@Before
	public void setUp() {
		// Inicializa os campos para que não haja interferência entre os testes.
		ConsolePrinter.setVerbose(false);
		System.setProperty("org.ggoncalves.robot.util.isVerbose", "");
	}

	@Test
	public void testIsVerbosePropertyDefault() {
		System.setProperty("org.ggoncalves.robot.util.isVerbose", "");
		assertFalse(ConsolePrinter.isVerbose());
	}
	
	@Test
	public void testIsVerbosePropertyDefaultAndSetTrue() {
		System.setProperty("org.ggoncalves.robot.util.isVerbose", "");
		ConsolePrinter.setVerbose(true);
		assertTrue(ConsolePrinter.isVerbose());
	}
	
	@Test
	public void testIsVerbosePropertySetTrue() {
		System.setProperty("org.ggoncalves.robot.util.isVerbose", "true");
		assertTrue(ConsolePrinter.isVerbose());
	}
	
	@Test
	public void testIsVerbosePropertySetFalse() {
		System.setProperty("org.ggoncalves.robot.util.isVerbose", "false");
		assertFalse(ConsolePrinter.isVerbose());
	}
	
	@Test
	public void testIsVerbosePropertySetFalseFieldTrue() {
		System.setProperty("org.ggoncalves.robot.util.isVerbose", "false");
		ConsolePrinter.setVerbose(true);
		assertFalse(ConsolePrinter.isVerbose());
	}
	
	@Test
	public void testIsVerbosePropertySetTrueFieldFalse() {
		System.setProperty("org.ggoncalves.robot.util.isVerbose", "true");
		ConsolePrinter.setVerbose(false);
		assertTrue(ConsolePrinter.isVerbose());
	}
	
	@Test
	public void testIsVerboseDefault() {
		assertFalse(ConsolePrinter.isVerbose());
	}
	
	@Test
	public void testIsVerboseSetTrue() {
		ConsolePrinter.setVerbose(true);
		assertTrue(ConsolePrinter.isVerbose());
	}
	
	@Test
	public void testIsVerboseSetFalse() {
		ConsolePrinter.setVerbose(false);
		assertFalse(ConsolePrinter.isVerbose());
	}
}
