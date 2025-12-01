// Arquivo: tests/cadastro-produtos.spec.ts

import { test, expect } from '@playwright/test';

// Define a URL da página de produtos, baseada na sua estrutura.
// Assumindo que você pode acessá-la diretamente:
const PRODUTOS_PAGE_URL = 'http://localhost:8080/produtos.html'; 

test.describe('Caminho Feliz 1: Cadastro de Produto', () => {

  test('Deve cadastrar um novo produto e receber a confirmação de sucesso (alerta)', async ({ page }) => {
    
    // --- 1. Acessar a página de produtos ---
    // Nota: O teste assume que o servidor Java/Spring Boot está rodando.
    await page.goto(PRODUTOS_PAGE_URL); 

    // --- 2. Geração de dados de teste (EAN único) ---
    // Usamos Date.now() para garantir um código de barras que não existirá no MySQL
    const eanUnico = `789123456789${Date.now()}`; 
    const nomeProduto = 'Cerveja Artesanal Puro Malte';
    const valorProduto = '25,50'; // O JS do front converte '25,50' para 25.50
    const tipoProduto = 'Destilada'; // Usando um dos tipos válidos
    
    // --- 3. Manipular o Alerta de Sucesso (Assertiva) ---
    // Como o seu JavaScript usa 'alert()', precisamos interceptar e confirmar o diálogo.
    let mensagemSucessoRecebida = false;
    
    // Registra um listener para o evento de diálogo (alert, confirm, prompt)
    page.on('dialog', async dialog => {
      // Verifica se o texto do alerta é o esperado
      if (dialog.message() === 'Produto cadastrado com sucesso!') {
        mensagemSucessoRecebida = true;
      }
      // Garante que o teste continue fechando o alerta (necessário)
      await dialog.accept(); 
    });


    // --- 4. Preencher o formulário usando os IDs identificados ---
    
    // Nome do Produto: #nome-produto
    await page.fill('#nome-produto', nomeProduto);
    
    // Valor: #valor-produto
    await page.fill('#valor-produto', valorProduto);
    
    // EAN (Código de Barras): #codigo-produto
    await page.fill('#codigo-produto', eanUnico);
    
    // Tipo: #tipo-produto
    await page.fill('#tipo-produto', tipoProduto); 

    // --- 5. Clicar no botão Salvar ---
    // Botão: .btn_slv_prdts
    await page.click('.btn_slv_prdts'); 
    
    // --- 6. Verificação (Assertiva) de Sucesso ---
    // Aguarda um pequeno período para o alerta aparecer e ser processado pelo listener
    await page.waitForTimeout(500); 

    // Verifica se a flag foi alterada, confirmando que o alerta correto foi disparado
    expect(mensagemSucessoRecebida).toBe(true);

  });
});