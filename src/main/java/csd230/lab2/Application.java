package csd230.lab2;

import com.github.javafaker.Faker;
import csd230.lab2.entities.Book;
import csd230.lab2.entities.Cart;
import csd230.lab2.entities.Magazine;
import csd230.lab2.entities.DiscMag;
import csd230.lab2.entities.Ticket;
import csd230.lab2.respositories.BookRepository;
import csd230.lab2.respositories.CartItemRepository;
import csd230.lab2.respositories.CartRepository;
import csd230.lab2.respositories.MagazineRepository;
import csd230.lab2.respositories.DiscMagRepository;
import csd230.lab2.respositories.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private MagazineRepository magazineRepository;

	@Autowired
	private DiscMagRepository discMagRepository;

	@Autowired
	private TicketRepository ticketRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner demo(BookRepository bookRepository) {
		return (args) -> {

			Cart cart = new Cart();
			cartRepository.save(cart);

			Faker faker = new Faker();


			com.github.javafaker.Book fakeBook = faker.book();
			for (int i = 0; i < 5; i++) {
				double price = faker.number().randomDouble(2, 10, 100);
				int copies = faker.number().numberBetween(5, 20);
				int quantity = faker.number().numberBetween(1, 50);
				String title = fakeBook.title();
				String author = fakeBook.author();
				String isbn = faker.code().isbn10();
				String description = "Book: " + title;

				Book book = new Book(
						price,
						quantity,
						description,
						title,
						copies,
						author,
						isbn
				);
				cart.addItem(book);
				bookRepository.save(book);
			}


			for (int i = 0; i < 5; i++) {
				double price = faker.number().randomDouble(2, 5, 30);
				int copies = faker.number().numberBetween(10, 50);
				int quantity = faker.number().numberBetween(1, 20);
				int orderQty = faker.number().numberBetween(1, 10);
				Date currIssue = faker.date().past(30, TimeUnit.DAYS);
				String title = faker.commerce().productName();
				String description = "Magazine: " + title;


				Magazine magazine = new Magazine(
						price,
						quantity,
						description,
						title,
						copies,
						orderQty,
						currIssue
				);
				cart.addItem(magazine);
				magazineRepository.save(magazine);
			}


			for (int i = 0; i < 5; i++) {
				double price = faker.number().randomDouble(2, 5, 50);
				int copies = faker.number().numberBetween(10, 40);
				int quantity = faker.number().numberBetween(1, 30);
				int orderQty = faker.number().numberBetween(1, 10);
				Date currIssue = faker.date().past(30, TimeUnit.DAYS);
				String title = faker.commerce().productName();
				String description = "DiscMag: " + title;
				boolean hasDisc = faker.bool().bool();


				DiscMag discMag = new DiscMag(price, quantity, description, title, copies, orderQty, currIssue, hasDisc);
				cart.addItem(discMag);
				discMagRepository.save(discMag);
			}


			for (int i = 0; i < 5; i++) {
				double price = faker.number().randomDouble(2, 5, 20);
				int quantity = faker.number().numberBetween(1, 10);
				String description = "Ticket: " + faker.lorem().sentence();
				String text = faker.lorem().sentence();


				Ticket ticket = new Ticket();
				ticket.setPrice(price);
				ticket.setQuantity(quantity);
				ticket.setDescription(description);
				ticket.setText(text);

				cart.addItem(ticket);
				ticketRepository.save(ticket);
			}

			// Save cart again
			cartRepository.save(cart);


			log.info("Books found with findAll():");
			log.info("------------------------");
			bookRepository.findAll().forEach(book -> log.info(book.toString()));

			log.info("\nMagazines found with findAll():");
			log.info("------------------------");
			magazineRepository.findAll().forEach(mag -> log.info(mag.toString()));

			log.info("\nDiscMags found with findAll():");
			log.info("------------------------");
			discMagRepository.findAll().forEach(dm -> log.info(dm.toString()));

			log.info("\nTickets found with findAll():");
			log.info("------------------------");
			ticketRepository.findAll().forEach(ticket -> log.info(ticket.toString()));

			log.info("\nCart Items found with findAll():");
			log.info("------------------------");
			cartRepository.findAll().forEach(cartItem -> {
				log.info(cartItem.toString());
				cartItemRepository.findAll().forEach(item -> {
					log.info("-------------------------------");
					log.info(item.toString());
				});
			});
		};
	}
}
