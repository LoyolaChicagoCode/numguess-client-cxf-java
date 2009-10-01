/**
 * 
 */
package numguess;

import static org.junit.Assert.*;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPFaultException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author laufer
 * 
 */
public class TestGeneratedClient {

	private NumguessService client;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		client = new NumguessServiceService()
				.getNumguessServicePort();
		((BindingProvider) client).getRequestContext().put(
				BindingProvider.SESSION_MAINTAIN_PROPERTY, true);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		client = null;
	}

	/**
	 * Test method for {@link numguess.Guess#guess(int)}.
	 */
	@Test
	public void testGuess() {
		client.reset(1, 100);
		GuessResult result = client.guess(50);
		assertEquals(1, result.getNumGuesses().intValue());
	}

	/**
	 * Test method for {@link numguess.Guess#guess(int)}.
	 */
	@Test
	public void testMultipleGuesses() {
		client.reset(1, 100);
		GuessResult result = client.guess(50);
		assertEquals(1, result.getNumGuesses().intValue());
		result = client.guess(50);
		assertEquals(2, result.getNumGuesses().intValue());
		result = client.guess(50);
		assertEquals(3, result.getNumGuesses().intValue());
	}

	/**
	 * Test method for {@link numguess.Guess#guess(int)}.
	 */
	@Test
	public void testComplexSequential() {
		final int min = 1, max = 100;
		client.reset(min, max);
		GuessResult result = null;
		for (int i = min; i <= max; i++) {
			result = client.guess(i);
			assertEquals(i, result.getNumGuesses().intValue());
			if (result.getComparison() == 0)
				break;
		}
		assertEquals(0, result.getComparison().intValue());
	}

	/**
	 * Test method for {@link numguess.Guess#guess(int)}.
	 */
	@Test
	public void testComplexBinary() {
		final int MIN = 1, MAX = 100;
		int min = MIN, max = MAX;
		client.reset(min, max);
		GuessResult result;
		while (true) {
			final int middle = min + (max - min) / 2;
			result = client.guess(middle);
			if (result.getComparison() == 0)
				break;
			else if (result.getComparison() > 0) {
				max = middle;
			} else {
				min = middle;
			}
		}
		assertEquals(0, result.getComparison().intValue());
		assertTrue(result.getNumGuesses() <= Math.ceil(Math.log(MAX - MIN)
				/ Math.log(2)));
	}

	/**
	 * Test method for {@link numguess.Guess#reset(int, int)}.
	 */
	@Test
	public void testReset() {
		client.reset(1, 100);
	}

	/**
	 * Test method for {@link numguess.Guess#reset(int, int)}.
	 */
	@Test
	public void testResetBad() {
		try {
			client.reset(100, 1);
			fail("should have raised SOAPFaultException");
		} catch (SOAPFaultException e) {
		}
	}
}
