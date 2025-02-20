package csd230.lab2.respositories;

import csd230.lab2.entities.Cart;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartRepository extends CrudRepository<Cart, Long> {
    Cart findById(long id);

}
