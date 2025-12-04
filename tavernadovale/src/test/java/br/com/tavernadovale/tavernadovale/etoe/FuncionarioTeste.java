package br.com.tavernadovale.tavernadovale.etoe;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FuncionarioTeste {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testarCadastroFuncionario() {
        try {
            driver.get("file:///C:/Users/Larry/Desktop/Dose/Front%20-%20PI/funcionario.html");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("campos_funcn")));

            System.out.println("--- Iniciando Teste Java (Com delay de 0.5s) ---");

            WebElement campoNome = driver.findElement(By.id("nome-funcionario"));
            campoNome.clear();
            campoNome.sendKeys("Claudinei");
            Thread.sleep(500); // Aguarda 0.5s
            System.out.println("Nome preenchido.");

            WebElement campoEntrada = driver.findElement(By.id("horario-entrada"));
            campoEntrada.sendKeys("0900");
            Thread.sleep(500);
            System.out.println("Entrada preenchida.");

            WebElement campoCargo = driver.findElement(By.id("cargo-funcionario"));
            campoCargo.clear();
            campoCargo.sendKeys("Vendedor");
            Thread.sleep(500);
            System.out.println("Cargo preenchido.");

            WebElement campoSaida = driver.findElement(By.id("horario-saida"));
            campoSaida.sendKeys("1700");
            Thread.sleep(500);
            System.out.println("Saída preenchida.");

            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
            
            WebElement botaoSalvar = driver.findElement(By.cssSelector(".btn_slv_funcn"));
            botaoSalvar.click();
            Thread.sleep(500);
            System.out.println("Botão Salvar clicado.");

            Thread.sleep(3000);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao executar teste Selenium: " + e.getMessage());
        }
    }

    @AfterEach
    public void tearDown() {
        // Fecha o navegador após o teste
        if (driver != null) {
            driver.quit();
        }
    }
}