package br.com.tavernadovale.tavernadovale.etoe;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProdutoTeste {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testarCadastroProduto() {
        try {
            driver.get("file:///CAMINHO/produtos.html");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("campos_prdts")));

            System.out.println("--- Iniciando Cadastro de Produto (Delay 0.5s) ---");

            WebElement campoNome = driver.findElement(By.id("nome-produto"));
            campoNome.clear();
            campoNome.sendKeys("Fanta uva 600ml");
            Thread.sleep(500);
            System.out.println("Nome preenchido.");

            WebElement campoValor = driver.findElement(By.id("valor-produto"));
            campoValor.clear();
            campoValor.sendKeys("9");
            Thread.sleep(500);
            System.out.println("Valor preenchido.");

            WebElement campoCodigo = driver.findElement(By.id("codigo-produto"));
            campoCodigo.clear();
            campoCodigo.sendKeys("2343541234234234");
            Thread.sleep(500);
            System.out.println("Código preenchido.");

            WebElement campoTipo = driver.findElement(By.id("tipo-produto"));
            campoTipo.clear();
            campoTipo.sendKeys("Refrigerante");
            Thread.sleep(500);
            System.out.println("Tipo preenchido.");

            WebElement botaoSalvar = driver.findElement(By.cssSelector(".btn_slv_prdts"));
            botaoSalvar.click();
            Thread.sleep(500);
            System.out.println("Botão Salvar clicado.");

            Thread.sleep(3000);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao testar produtos: " + e.getMessage());
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}