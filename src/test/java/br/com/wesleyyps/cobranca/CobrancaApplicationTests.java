package br.com.wesleyyps.cobranca;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.EnabledIf;

@SpringBootTest
@EnabledIf(
  expression = "${app.test.enabled}", 
  loadContext = true
)
class CobrancaApplicationTests {

}
