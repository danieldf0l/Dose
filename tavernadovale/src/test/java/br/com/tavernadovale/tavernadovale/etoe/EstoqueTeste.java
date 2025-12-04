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

public class EstoqueTeste {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testarCadastroEstoque() {
        try {
            driver.get("file:///CAMINHO/estoque.html");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("campos_estq")));

            System.out.println("--- Iniciando Cadastro de Estoque (Delay 0.5s) ---");

            WebElement campoCodigo = driver.findElement(By.id("codigo-estoque"));
            campoCodigo.clear();
            campoCodigo.sendKeys("2343541234234234");
            Thread.sleep(500);
            System.out.println("Código de barras preenchido.");

            WebElement campoQuantidade = driver.findElement(By.id("quatidade-estoque"));
            campoQuantidade.clear();
            campoQuantidade.sendKeys("40");
            Thread.sleep(500);
            System.out.println("Quantidade preenchida.");

            WebElement campoData = driver.findElement(By.id("data-estoque"));
            campoData.sendKeys("01012025"); 
            Thread.sleep(500);
            System.out.println("Data de validade preenchida.");

            WebElement campoLote = driver.findElement(By.id("lote-estoque"));
            campoLote.clear();
            campoLote.sendKeys("1231298371lkglkjh");
            Thread.sleep(500);
            System.out.println("Lote preenchido.");

            WebElement botaoSalvar = driver.findElement(By.cssSelector(".btn_slv_estq"));
            botaoSalvar.click();
            Thread.sleep(500);
            System.out.println("Botão Salvar clicado.");

            Thread.sleep(3000);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao testar estoque: " + e.getMessage());
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}