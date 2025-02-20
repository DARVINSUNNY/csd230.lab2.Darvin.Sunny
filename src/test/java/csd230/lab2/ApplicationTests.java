package csd230.lab2;

import csd230.lab2.respositories.BookRepository;
import csd230.lab2.respositories.CartItemRepository;
import csd230.lab2.respositories.CartRepository;
import csd230.lab2.respositories.DiscMagRepository;
import csd230.lab2.respositories.MagazineRepository;
import csd230.lab2.respositories.TicketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ApplicationTests {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private MagazineRepository magazineRepository;

	@Autowired
	private DiscMagRepository discMagRepository;

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Test
	public void contextLoads() {
		// Check that all repositories are injected successfully
		assertNotNull(bookRepository, "BookRepository should not be null");
		assertNotNull(magazineRepository, "MagazineRepository should not be null");
		assertNotNull(discMagRepository, "DiscMagRepository should not be null");
		assertNotNull(ticketRepository, "TicketRepository should not be null");
		assertNotNull(cartRepository, "CartRepository should not be null");
		assertNotNull(cartItemRepository, "CartItemRepository should not be null");
	}

	@Test
	public void testBookDataPopulated() {
		long count = bookRepository.count();
		// We expect at least 5 books
		assertTrue(count >= 5, "There should be at least 5 books, but found: " + count);
	}

	@Test
	public void testMagazineDataPopulated() {
		long count = magazineRepository.count();
		// We expect at least 5 magazines
		assertTrue(count >= 5, "There should be at least 5 magazines, but found: " + count);
	}

	@Test
	public void testDiscMagDataPopulated() {
		long count = discMagRepository.count();
		// We expect at least 5 disc mags
		assertTrue(count >= 5, "There should be at least 5 disc mags, but found: " + count);
	}

	@Test
	public void testTicketDataPopulated() {
		long count = ticketRepository.count();
		// We expect at least 5 tickets
		assertTrue(count >= 5, "There should be at least 5 tickets, but found: " + count);
	}

	@Test
	public void testCartDataPopulated() {
		long count = cartRepository.count();
		// We expect at least one cart
		assertTrue(count >= 1, "There should be at least 1 cart, but found: " + count);
	}

	@Test
	public void testCartItemsPopulated() {
		long count = cartItemRepository.count();

		assertTrue(count >= 20, "There should be at least 20 cart items, but found: " + count);
	}
}
