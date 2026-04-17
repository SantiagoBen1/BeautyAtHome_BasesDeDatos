import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
		"api",
		"application",
		"config",
		"domain",
		"infrastructure",
		"ui"
})
public class BeautyAtHomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeautyAtHomeApplication.class, args);
	}
}
