package vendasproject02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vendasproject02.domain.entity.Cliente;
import vendasproject02.domain.repository.ClienteRepository;

@SpringBootApplication

public class VendasProject02Application {

	public static void main(String[] args) {
		SpringApplication.run(VendasProject02Application.class, args);
	}


}
